package com.technomentoring.mentoringapi.controller;

import com.technomentoring.mentoringapi.dto.ScheduleDTO;
import com.technomentoring.mentoringapi.model.Schedule;
import com.technomentoring.mentoringapi.service.IScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final IScheduleService service;

    @Qualifier("scheduleMapper")
    private final ModelMapper mapper;

    private ScheduleDTO convertToDto(Schedule obj){
        return mapper.map(obj,ScheduleDTO.class);
    }

    private Schedule convertToEntity(ScheduleDTO dto){
        return mapper.map(dto,Schedule.class);
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
}
