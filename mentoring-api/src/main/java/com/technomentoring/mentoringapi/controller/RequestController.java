package com.technomentoring.mentoringapi.controller;

import com.technomentoring.mentoringapi.dto.RequestDTO;
import com.technomentoring.mentoringapi.dto.ScheduleDTO;
import com.technomentoring.mentoringapi.dto.UsuariosDTO;
import com.technomentoring.mentoringapi.model.Request;
import com.technomentoring.mentoringapi.model.Schedule;
import com.technomentoring.mentoringapi.model.Usuarios;
import com.technomentoring.mentoringapi.service.IRequestService;
import com.technomentoring.mentoringapi.service.impl.RequestServiceImpl;
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
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user/request")
@RequiredArgsConstructor
public class RequestController {
    private final IRequestService service;
    private final UsuariosServiceImpl usuariosService;
    private final RequestServiceImpl requestService;
    private final ModelMapper modelMapper;

    @Qualifier("requestMapper")
    private final ModelMapper mapper;

    private RequestDTO convertToDto(Request obj){return mapper.map(obj,RequestDTO.class);
    }

    private Request convertToEntity(RequestDTO dto) throws UserPrincipalNotFoundException {
        Request request =  mapper.map(dto, Request.class);
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

        request.setUsuarios(usuarios); // Establece el objeto Usuarios en el Schedule
        return request;

    }

    @PostMapping("/create")
    public ResponseEntity<RequestDTO> create(@Valid @RequestBody RequestDTO dto) throws Exception{
        Request obj = service.save(convertToEntity(dto));
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RequestDTO> update(@Valid @PathVariable("id") Integer idRequest, @RequestBody RequestDTO dto)throws Exception{
        dto.setIdRequest(idRequest);
        Request obj  = service.update(convertToEntity(dto),idRequest);
        return new ResponseEntity<>(convertToDto(obj),HttpStatus.OK);
    }

    @GetMapping("/readall")
    public ResponseEntity<List<RequestDTO>> readAll() throws Exception{
        List<RequestDTO> list = service.readAll().stream().map(this::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<RequestDTO> readById(@PathVariable("id") Integer id)throws Exception{
        Request obj = service.readById(id);
        return new ResponseEntity<>(convertToDto(obj),HttpStatus.OK);

    }
    @GetMapping("/title/{title}")
    public ResponseEntity<List<RequestDTO>> findByTitle(@PathVariable("title") String title)throws Exception{
        List<Request> requests = service.findRequestByTitle(title);
        List<RequestDTO> dtos = requests.stream().map(this::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


///////////////////////obtener el estudiante por el id de la solicitud
    @GetMapping("/getRequest-Student/{requestId}")
    public ResponseEntity<UsuariosDTO> getMentorByRequestAndSchedule(@PathVariable("requestId") Integer requestId) throws Exception {
        // Obtener la solicitud por su ID
        Request request = service.readById(requestId);

        // Verificar si la solicitud existe
        if (request == null) {
            throw new Exception("La solicitud no existe");
        }

        // Obtener el usuario vinculado a la solicitud
        Usuarios mentor = request.getUsuarios();

        // Verificar si el usuario exist
        if (mentor == null) {
            throw new Exception("La solicitud no tiene un usuario estudiante asignado");
        }

        // Mapear el usuario a un DTO para devolverlo en la respuesta
        UsuariosDTO mentorDTO = mapper.map(mentor, UsuariosDTO.class);

        return new ResponseEntity<>(mentorDTO, HttpStatus.OK);
    }

/////////////////////MUESTRA LAS SOLICITUDES DEL ESTUDIANTE DANDOLE SU ID
    @GetMapping("/getRequests-Student/{id}")
    public ResponseEntity<List<RequestDTO>> getRequestByUserId(@PathVariable("id") Long userId) {
        List<Request> requests = requestService.getRequestByUserId(userId);
        List<RequestDTO> requestDTOS = requests.stream()
                .map(request -> modelMapper.map(request, RequestDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(requestDTOS, HttpStatus.OK);
    }






}
