package com.technomentoring.mentoringapi.repository;

import com.technomentoring.mentoringapi.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuariosRepository extends JpaRepository<Usuarios, Long> {
    Optional<Usuarios> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByIdUser(Long idUser);




}
