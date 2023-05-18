package com.technomentoring.mentoringapi.repository;

import com.technomentoring.mentoringapi.model.Mentor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IMentorRepository extends IGenericRepository<Mentor, Integer>{
    boolean existsByIdMentor(Integer idMentor);
    boolean existsByNameOrDNI(String name, String DNI);
    boolean existsByNameAndDNI(String name, String DNI);
    boolean existsByEmailOrPassword(String email,String password);
    boolean existsByEmailAndPassword(String email,String password);

    @Query("FROM Mentor m WHERE m.name = ?1 AND m.DNI LIKE ?2")
    List<Mentor> getMentorByNameAndDNI(String name,String DNI);

    @Query("FROM Mentor m WHERE m.email = ?1 AND m.password LIKE ?2")
    List<Mentor> getMentorByEmailAndPassword(String email,String password);
}
