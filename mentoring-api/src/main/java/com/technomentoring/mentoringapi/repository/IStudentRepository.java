package com.technomentoring.mentoringapi.repository;

import com.technomentoring.mentoringapi.model.Student;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IStudentRepository extends IGenericRepository<Student,Integer>{
    boolean existsByIdStudent(Integer idStudent);
    boolean existsByNameOrDNI(String name, String DNI);
    boolean existsByNameAndDNI(String name, String DNI);
    boolean existsByEmailOrPassword(String email,String password);
    boolean existsByEmailAndPassword(String email,String password);

    @Query("FROM Student m WHERE m.name = ?1 AND m.DNI LIKE ?2")
    List<Student> getMentorByNameAndDNIStudent(String name, String DNI);

    @Query("FROM Student m WHERE m.email = ?1 AND m.password LIKE ?2")
    List<Student> getMentorByEmailAndPasswordStudent(String email,String password);
}