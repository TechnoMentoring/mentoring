package com.technomentoring.mentoringapi.service;

import java.util.List;

public interface ICRUD<T,ID> {
    T save(T t) throws Exception;
}