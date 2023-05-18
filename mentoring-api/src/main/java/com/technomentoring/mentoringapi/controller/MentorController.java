package com.technomentoring.mentoringapi.controller;

import com.technomentoring.mentoringapi.dto.MentorDTO;
import com.technomentoring.mentoringapi.model.Mentor;
import com.technomentoring.mentoringapi.service.IMentorService;
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
@RequestMapping("/mentor")
@RequiredArgsConstructor
public class MentorController {
    private final IMentorService service;
    @Qualifier("mentorMapper")
    private final ModelMapper mapper;

    private MentorDTO convertToDto(Mentor obj){
        return mapper.map(obj,MentorDTO.class);
    }
    private Mentor convertToEntity(MentorDTO dto){
        return mapper.map(dto,Mentor.class);
    }
    @PostMapping("/create")
    public ResponseEntity<MentorDTO> create(@Valid @RequestBody MentorDTO dto)throws Exception{
        Mentor obj = service.save(convertToEntity(dto));
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<MentorDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody MentorDTO dto)throws Exception{
        dto.setIdMentor(id);
        Mentor obj = service.update(convertToEntity(dto),id);
        return new ResponseEntity<>(convertToDto(obj),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer id)throws Exception{
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/readall")
    public ResponseEntity<List<MentorDTO>>  readAll() throws Exception{
        List<MentorDTO> list = service.readAll().stream().map(this::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
