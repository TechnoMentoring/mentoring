package com.technomentoring.mentoringapi.config;

import com.technomentoring.mentoringapi.dto.*;
import com.technomentoring.mentoringapi.model.*;
import org.modelmapper.*;
import org.springframework.context.annotation.*;

@Configuration
public class MapperConfig {
    @Primary
    @Bean("studentMapper")
    public ModelMapper studentMapper(){
        ModelMapper mapper = new ModelMapper();
        TypeMap<StudentDTO,Student> typeMap1 = mapper.createTypeMap(StudentDTO.class,Student.class);
        TypeMap<Student,StudentDTO> typeMap2 = mapper.createTypeMap(Student.class, StudentDTO.class);

        typeMap1.addMapping(StudentDTO::getName, (dest,v) -> dest.setName((String)v));
        typeMap2.addMapping(Student::getName, (dest, v) -> dest.setName((String) v));

        typeMap1.addMapping(StudentDTO::getEmail, (dest,v) -> dest.setEmail((String)v));
        typeMap2.addMapping(Student::getEmail, (dest, v) -> dest.setEmail((String) v));

        typeMap1.addMapping(StudentDTO::getPassword, (dest,v) -> dest.setPassword((String)v));
        typeMap2.addMapping(Student::getPassword, (dest, v) -> dest.setPassword((String) v));

        typeMap1.addMapping(StudentDTO::getDNI, (dest,v) -> dest.setDNI((String)v));
        typeMap2.addMapping(Student::getDNI, (dest, v) -> dest.setDNI((String) v));

        return mapper;
    }
}
