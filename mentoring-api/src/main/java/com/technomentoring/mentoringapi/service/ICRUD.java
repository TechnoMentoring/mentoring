package com.technomentoring.mentoringapi.service;

import java.util.List;

public interface ICRUD<T,ID> {
    T save(T t) throws Exception;
    T update(T t, ID id) throws Exception;
<<<<<<< HEAD
=======

>>>>>>> ac4509164e3ae56c3fe79ade993a7f850d23087b
    List<T> readAll()throws Exception;
    T readById(ID id) throws Exception;
    void delete(ID id) throws Exception;

}
