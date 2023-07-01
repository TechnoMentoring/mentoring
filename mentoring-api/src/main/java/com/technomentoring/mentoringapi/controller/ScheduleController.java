package com.technomentoring.mentoringapi.controller;

import com.technomentoring.mentoringapi.dto.MentorDTO;
import com.technomentoring.mentoringapi.dto.RequestDTO;
import com.technomentoring.mentoringapi.dto.ScheduleDTO;
import com.technomentoring.mentoringapi.dto.UsuariosDTO;
import com.technomentoring.mentoringapi.model.Mentor;
import com.technomentoring.mentoringapi.model.Request;
import com.technomentoring.mentoringapi.model.Schedule;
import com.technomentoring.mentoringapi.model.Usuarios;
import com.technomentoring.mentoringapi.service.IScheduleService;
import com.technomentoring.mentoringapi.service.IUsuariosService;
import com.technomentoring.mentoringapi.service.impl.RequestServiceImpl;
import com.technomentoring.mentoringapi.service.impl.SchedulerServiceImpl;
import com.technomentoring.mentoringapi.service.impl.UsuariosServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/user/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final IScheduleService service;
    private final UsuariosServiceImpl usuariosService;
    private final SchedulerServiceImpl schedulerService;
    private final RequestServiceImpl requestService;
    private final ModelMapper modelMapper;

    @Qualifier("scheduleMapper")
    private final ModelMapper mapper;

    private ScheduleDTO convertToDto(Schedule obj){
        return mapper.map(obj,ScheduleDTO.class);
    }
    private Schedule convertToEntity(ScheduleDTO dto) throws UserPrincipalNotFoundException {
        Schedule schedule = mapper.map(dto, Schedule.class);
        Usuarios usuarios = new Usuarios();
        usuarios.setIdUser(dto.getIdUser()); // Asigna el id de usuario desde el DTO

        // Carga el objeto Usuarios existente de la base de datos
        Usuarios existingUser = usuariosService.findById(dto.getIdUser());
        usuarios.setUsername(existingUser.getUsername());
        usuarios.setPassword(existingUser.getPassword());
        usuarios.setName(existingUser.getName());
        usuarios.setEmail(existingUser.getEmail());
        usuarios.setDni(existingUser.getDni());
        usuarios.setProfile(existingUser.getProfile());
        usuarios.setRoles(existingUser.getRoles());

        schedule.setUsuarios(usuarios); // Establece el objeto Usuarios en el Schedule
        return schedule;
    }



    @PostMapping("/create")
    public ResponseEntity<ScheduleDTO> create(@Valid @RequestBody ScheduleDTO dto) throws Exception{
        Schedule obj = service.save(convertToEntity(dto));
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ScheduleDTO> update(@Valid @PathVariable("id") Integer idSchedule, @RequestBody ScheduleDTO dto)throws Exception{
        dto.setIdSchedule(idSchedule);
        Schedule obj  = service.update(convertToEntity(dto),idSchedule);
        return new ResponseEntity<>(convertToDto(obj),HttpStatus.OK);
    }

    @GetMapping("/readall")
    public ResponseEntity<List<ScheduleDTO>> readAll() throws Exception{
        List<ScheduleDTO> list = service.readAll().stream().map(this::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<ScheduleDTO> readById(@PathVariable("id") Integer id)throws Exception{
        Schedule obj = service.readById(id);
        return new ResponseEntity<>(convertToDto(obj),HttpStatus.OK);

    }
    @GetMapping("/title/{title}")
    public ResponseEntity<List<ScheduleDTO>> findByTitle(@PathVariable("title") String title)throws Exception{
        List<Schedule> schedules = service.findScheduleByTitle(title);
        List<ScheduleDTO> dtos = schedules.stream().map(this::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByUserId(@PathVariable("id") Long userId) {
        List<Schedule> schedules = schedulerService.getSchedulesByUserId(userId);
        List<ScheduleDTO> scheduleDTOs = schedules.stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(scheduleDTOs, HttpStatus.OK);
    }


    @GetMapping("/getSchedule-Mentor/{scheduleId}")
    public ResponseEntity<UsuariosDTO> getMentorByRequestAndSchedule(@PathVariable("scheduleId") Integer scheduleId) throws Exception {
        // Obtener la solicitud por su ID
        Schedule schedule = service.readById(scheduleId);

        // Verificar si la solicitud existe
        if (schedule == null) {
            throw new Exception("el horario no existe");
        }

        // Obtener el usuario mentor vinculado a la solicitud
        Usuarios mentor = schedule.getUsuarios();

        // Verificar si el usuario mentor existe
        if (mentor == null) {
            throw new Exception("el horario no tiene un usuario mentor asignado");
        }

        // Mapear el usuario mentor a un DTO para devolverlo en la respuesta
        UsuariosDTO mentorDTO = mapper.map(mentor, UsuariosDTO.class);

        return new ResponseEntity<>(mentorDTO, HttpStatus.OK);
    }

///////////////////////////////Muestra la solicitudes de un mentor
    @GetMapping("/getRequestsMentor/{idUser}")
    public ResponseEntity<List<RequestDTO>> getRequestsByUserId(@PathVariable("idUser") Long idUser) {
        List<Schedule> schedules = schedulerService.getSchedulesByUserId(idUser);
        List<Integer> scheduleIds = schedules.stream()
                .map(Schedule::getIdSchedule)
                .collect(Collectors.toList());

        List<Request> requests = requestService.getRequestsByScheduleIds(scheduleIds);
        List<RequestDTO> requestDTOs = requests.stream()
                .map(request -> modelMapper.map(request, RequestDTO.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(requestDTOs, HttpStatus.OK);
    }




}
