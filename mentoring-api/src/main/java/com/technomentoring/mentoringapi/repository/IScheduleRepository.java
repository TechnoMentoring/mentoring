package com.technomentoring.mentoringapi.repository;

import com.technomentoring.mentoringapi.model.Schedule;

import java.util.List;

public interface IScheduleRepository extends IGenericRepository<Schedule, Integer>{
    boolean existsByTitle(String title);
    List<Schedule> findByTitle(String title);
}
