package com.technomentoring.mentoringapi.service.impl;

import com.technomentoring.mentoringapi.exception.DataAlreadyExistsException;
import com.technomentoring.mentoringapi.exception.ModelNotFoundException;
import com.technomentoring.mentoringapi.model.Schedule;
import com.technomentoring.mentoringapi.repository.IGenericRepository;
import com.technomentoring.mentoringapi.repository.IMentorRepository;
import com.technomentoring.mentoringapi.repository.IScheduleRepository;
import com.technomentoring.mentoringapi.repository.IStudentRepository;
import com.technomentoring.mentoringapi.service.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl extends CRUDImpl<Schedule,Integer> implements IScheduleService {
    private final IScheduleRepository repo;
    private final IMentorRepository repositoryMentor;
    private final IStudentRepository repositoryStudent;

    @Override
    protected IGenericRepository<Schedule, Integer> getRepo() {
        return repo;
    }


    @Override
    public Schedule save(Schedule schedule) throws Exception {
        Integer idMentor = schedule.getMentor().getIdMentor();
        Integer idStudent = schedule.getStudent().getIdStudent();
        boolean mentorExists = this.existMentorById(idMentor);
        boolean studentExists = this.existStudentById(idMentor);

        if (mentorExists){
            if (studentExists){
                if (isScheduleDuplicate(schedule.getTitle())){
                    throw new DataAlreadyExistsException("El Horario con Titulo " + schedule.getTitle() + "ya existe.");
                }
            }else {
                throw new ModelNotFoundException("EL estudiante con id: " + idStudent +" no existe");
            }
        }else {
            throw new ModelNotFoundException("EL mentor con id: " + idMentor +" no existe");
        }

        return super.save(schedule);
    }


    @Override
    public boolean isScheduleDuplicate(String title) {
        return repo.existsByTitle(title);
    }


}
