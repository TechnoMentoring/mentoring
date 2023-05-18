package com.technomentoring.mentoringapi.service;

import com.technomentoring.mentoringapi.model.Schedule;

import java.util.List;

public interface IScheduleService extends ICRUD<Schedule, Integer> {
    boolean isScheduleDuplicate(String title);
}
