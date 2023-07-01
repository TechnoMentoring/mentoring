package com.technomentoring.mentoringapi.service.impl;

import com.technomentoring.mentoringapi.exception.DataAlreadyExistsException;
import com.technomentoring.mentoringapi.exception.ModelNotFoundException;
import com.technomentoring.mentoringapi.model.Request;
import com.technomentoring.mentoringapi.model.Schedule;
import com.technomentoring.mentoringapi.model.Usuarios;
import com.technomentoring.mentoringapi.repository.*;
import com.technomentoring.mentoringapi.service.IRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl extends CRUDImpl<Request ,Integer> implements IRequestService{
    private final IRequestRepository repo;
    private final IScheduleRepository repositorySchedule;
    private final IUsuariosRepository repositoryUsuario;



    @Override
    protected IGenericRepository<Request, Integer> getRepo() {
        return repo;
    }

    public List<Request> findRequestsByUser(Usuarios user) {
        return repo.findByUsuarios(user);
    }

    public List<Request> getRequestByUserId(Long idUser) {
        return repo.findByUsuariosIdUser(idUser);
    }

    public Long getUserByRequest(Integer idRequest){
        return  repo.findByIdRequest(idRequest);
    }

    public Request findById(Integer id) {
        Optional<Request> requestOptional = repo.findById(id);
        return requestOptional.orElse(null);
    }




    @Override
    public Request save(Request request) throws Exception {
        Integer idSchedule = request.getSchedule().getIdSchedule();
        boolean scheduleExists = this.existScheduleById(idSchedule);

        Long idUser = request.getUsuarios().getIdUser();
        boolean userExists = this.existUsuariosById(idUser);

        if (userExists){
            if (scheduleExists){
                    if (isRequestDuplicate(request.getTitle())){
                        throw new DataAlreadyExistsException("La solicitud con título ''" + request.getTitle()+ "'' ya existe.");
                    }
            }else {
                throw new ModelNotFoundException("El horario con id " + idSchedule +" no existe");
            }
        }else {
            throw new ModelNotFoundException("El usuario con id " + idUser +" no existe");
        }

        return super.save(request);
    }

    @Override
    public Request update(Request request, Integer idRequest) throws Exception {
        Integer idSchedule = request.getSchedule().getIdSchedule();

        Long idUser = request.getUsuarios().getIdUser();
        boolean userExists = this.existUsuariosById(idUser);

        boolean scheduleExists = this.existScheduleById(idSchedule);
        getRepo().findById(idRequest).orElseThrow( () -> new ModelNotFoundException("La solicitud con id "+idRequest+" no existe."));

        if (userExists){
            if(scheduleExists){
                    return super.update(request, idRequest);

            }else {
                throw new ModelNotFoundException("El horario con id :" + idSchedule + "no existe.");
            }
        }else {
            throw new ModelNotFoundException("El usuario con id " + idUser +" no existe");
        }
    }



    @Override
    public boolean isRequestDuplicate(String title) {
        return repo.existsByTitle(title);
    }

    @Override
    public boolean existScheduleById(Integer idSchedule) {
        return repositorySchedule.existsByIdSchedule(idSchedule);
    }

    @Override
    public boolean existUsuariosById(Long idUser) {
        return repositoryUsuario.existsByIdUser(idUser);
    }
    @Override
    public List<Request> findRequestByTitle(String title) {
        return repo.findByTitle(title);
    }


    public List<Request> getRequestsByScheduleIds(List<Integer> scheduleIds) {
        // Obtener las solicitudes por los identificadores de los horarios utilizando una consulta en lote
        List<Request> requests = repo.findByScheduleIds(scheduleIds);

        // Filtrar las solicitudes para asegurarse de que solo se devuelvan las que coinciden con los identificadores de los horarios
        List<Request> filteredRequests = requests.stream()
                .filter(request -> scheduleIds.contains(request.getSchedule().getIdSchedule()))
                .collect(Collectors.toList());

        return filteredRequests;
    }
}
