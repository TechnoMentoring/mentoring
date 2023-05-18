package com.technomentoring.mentoringapi.service;

import com.technomentoring.mentoringapi.model.Student;

public interface IStudentService extends ICRUD<Student,Integer>{
    public boolean isStudentDuplicate(String name,String DNI,String email,String password);
}
