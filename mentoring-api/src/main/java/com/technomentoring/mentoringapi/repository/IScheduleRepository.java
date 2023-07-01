package com.technomentoring.mentoringapi.repository;

import com.technomentoring.mentoringapi.model.Schedule;
import com.technomentoring.mentoringapi.model.Student;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IScheduleRepository extends IGenericRepository<Schedule, Integer>{
    boolean existsByTitle(String title);
    List<Schedule> findByTitle(String title);
    boolean existsByIdSchedule(Integer idSchedule);
    boolean existsByIdScheduleAndTitle(Integer idSchedule, String title);

    List<Schedule> findByUsuariosIdUser(Long idUser);
}
