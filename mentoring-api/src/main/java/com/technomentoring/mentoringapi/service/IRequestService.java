package com.technomentoring.mentoringapi.service;

import com.technomentoring.mentoringapi.model.Request;

import java.util.List;

public interface IRequestService extends ICRUD<Request, Integer> {
    boolean isRequestDuplicate(String title);
    boolean existScheduleById(Integer idSchedule);
    boolean existUsuariosById(Long idUser);
    List<Request> findRequestByTitle(String title);
}
