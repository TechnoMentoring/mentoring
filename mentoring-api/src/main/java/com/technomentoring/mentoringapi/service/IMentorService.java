package com.technomentoring.mentoringapi.service;

import com.technomentoring.mentoringapi.model.Mentor;

public interface IMentorService extends ICRUD<Mentor, Integer> {
    public boolean isMentorDuplicate(String name,String DNI,String email,String password);

}