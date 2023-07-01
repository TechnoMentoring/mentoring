package com.technomentoring.mentoringapi.service;

import com.technomentoring.mentoringapi.model.Usuarios;

public interface IUsuariosService {
    Usuarios findByUsername(String username);
}
