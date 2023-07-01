package com.technomentoring.mentoringapi.service.impl;

import com.technomentoring.mentoringapi.exception.DataAlreadyExistsException;
import com.technomentoring.mentoringapi.exception.ModelNotFoundException;
import com.technomentoring.mentoringapi.model.Request;
import com.technomentoring.mentoringapi.model.Schedule;
import com.technomentoring.mentoringapi.repository.*;
import com.technomentoring.mentoringapi.service.IScheduleService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl extends CRUDImpl<Schedule,Integer> implements IScheduleService {
    private final IScheduleRepository repo;
    private final IUsuariosRepository repositoryUsuario;

    @Override
    protected IGenericRepository<Schedule, Integer> getRepo() {
        return repo;
    }


    @Override
    public Schedule save(Schedule schedule) throws Exception {
        Long idUser = schedule.getUsuarios().getIdUser();
        boolean userExists = this.existUsuariosById(idUser);
        LocalTime hourStart = schedule.getHourStart();
        LocalTime hourEnd = schedule.getHourEnd();

        if (userExists){
                if (isScheduleDuplicate(schedule.getTitle())){
                    throw new DataAlreadyExistsException("El horario con título ''" + schedule.getTitle()+ "'' ya existe.");
                }
        }else {
            throw new ModelNotFoundException("El usuario con id " + idUser +" no existe");
        }
        if (hourStart.compareTo(hourEnd) >= 0) {
            // Aquí puedes manejar el caso en el que la hora de inicio sea mayor o igual a la hora de fin
            throw new ModelNotFoundException("La hora de inicio debe ser menor a la hora de fin");
        } else {
            return super.save(schedule);
        }
    }

    @Override
    public Schedule update(Schedule schedule, Integer idSchedule) throws Exception {
        Long idUser = schedule.getUsuarios().getIdUser();

        boolean userExists = this.existUsuariosById(idUser);
        getRepo().findById(idSchedule).orElseThrow( () -> new ModelNotFoundException("El horario con id "+idSchedule+" no existe."));

        if(userExists){
                return super.update(schedule, idSchedule);
        }else {
            throw new ModelNotFoundException("El usuario con id :" + idUser + "no existe.");
        }
    }

    @Override
    public boolean isScheduleDuplicate(String title) {
        return repo.existsByTitle(title);
    }

    @Override
    public boolean existUsuariosById(Long idUser) {
        return repositoryUsuario.existsByIdUser(idUser);
    }

    @Override
    public List<Schedule> findScheduleByTitle(String title) {
        return repo.findByTitle(title);
    }


    public List<Schedule> getSchedulesByUserId(Long idUser) {
        return repo.findByUsuariosIdUser(idUser);
    }

    public Schedule readById(Integer id) {
        // Implementación de la lógica para leer un horario por su ID
        Optional<Schedule> scheduleOptional = repo.findById(id);
        return scheduleOptional.orElse(null);
    }





}
