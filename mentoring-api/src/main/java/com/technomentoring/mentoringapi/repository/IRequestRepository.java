package com.technomentoring.mentoringapi.repository;

import com.technomentoring.mentoringapi.model.Request;
import com.technomentoring.mentoringapi.model.Schedule;
import com.technomentoring.mentoringapi.model.Usuarios;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IRequestRepository extends IGenericRepository<Request, Integer> {
    boolean existsByTitle(String title);
    List<Request> findByTitle(String title);


    boolean existsByIdRequest(Integer idRequest);

    List<Request> findByUsuarios(Usuarios usuarios);

    List<Request> findByUsuariosIdUser(Long idUser);

    Long findByIdRequest(Integer idRequest);

    @Query("SELECT r FROM Request r WHERE r.schedule.idSchedule IN :scheduleIds")
    List<Request> findByScheduleIds(List<Integer> scheduleIds);
}
