package com.technomentoring.mentoringapi.controller;

import com.technomentoring.mentoringapi.dto.StudentDTO;
import com.technomentoring.mentoringapi.model.Student;
import com.technomentoring.mentoringapi.service.IStudentService;
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
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final IStudentService service;
    @Qualifier("studentMapper")
    private final ModelMapper mapper;

    public Student convertToEntity(StudentDTO dto){
        return mapper.map(dto, Student.class);
    }
    public StudentDTO convertToDto(Student obj){
        return mapper.map(obj,StudentDTO.class);
    }

    @PostMapping("/create")
    public ResponseEntity<Student> create(@Valid @RequestBody StudentDTO dto) throws Exception {
        Student obj = service.save(convertToEntity(dto));
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

}
