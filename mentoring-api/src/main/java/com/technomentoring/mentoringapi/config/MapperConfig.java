package com.technomentoring.mentoringapi.config;

import com.technomentoring.mentoringapi.dto.MentorDTO;
import com.technomentoring.mentoringapi.dto.ScheduleDTO;
import com.technomentoring.mentoringapi.dto.StudentDTO;
import com.technomentoring.mentoringapi.model.Mentor;
import com.technomentoring.mentoringapi.model.Schedule;
import com.technomentoring.mentoringapi.model.Student;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MapperConfig {
    @Primary
    @Bean("mentorMapper")
    public ModelMapper mentorMapper(){
        ModelMapper mapper = new ModelMapper();
        TypeMap<MentorDTO, Mentor> typeMap1 = mapper.createTypeMap(MentorDTO.class, Mentor.class);
        TypeMap<Mentor, MentorDTO> typeMap2 = mapper.createTypeMap(Mentor.class, MentorDTO.class);

        typeMap1.addMapping(MentorDTO::getName,(dest,v) -> dest.setName((String) v));
        typeMap2.addMapping(Mentor::getName,(dest,v) -> dest.setName((String) v));

        typeMap1.addMapping(MentorDTO::getEmail,(dest,v) -> dest.setEmail((String) v));
        typeMap2.addMapping(Mentor::getEmail,(dest,v) -> dest.setEmail((String) v));

        typeMap1.addMapping(MentorDTO::getPassword,(dest,v) -> dest.setPassword((String) v));
        typeMap2.addMapping(Mentor::getPassword,(dest,v) -> dest.setPassword((String) v));

        typeMap1.addMapping(MentorDTO::getDNI,(dest,v) -> dest.setDNI((String) v));
        typeMap2.addMapping(Mentor::getDNI,(dest,v) -> dest.setDNI((String) v));

        return mapper;
    }
    @Bean("scheduleMapper")
    public ModelMapper scheduleMapper(){
        ModelMapper mapper = new ModelMapper();
        TypeMap<ScheduleDTO,Schedule> typeMap1 = mapper.createTypeMap(ScheduleDTO.class, Schedule.class);
        TypeMap<Schedule,ScheduleDTO> typeMap2 = mapper.createTypeMap(Schedule.class, ScheduleDTO.class);

        typeMap1.addMapping(ScheduleDTO::getIdMentor, (dest, v) -> dest.getMentor().setIdMentor((Integer) v));
        typeMap2.addMapping(s -> s.getMentor().getIdMentor(),  (dest, v) -> dest.setIdMentor((Integer) v));


        typeMap1.addMapping(ScheduleDTO::getIdStudent, (dest, v) -> dest.getStudent().setIdStudent((Integer) v));
        typeMap2.addMapping(s -> s.getStudent().getIdStudent(),  (dest, v) -> dest.setIdStudent((Integer) v));


        return mapper;
    }

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
