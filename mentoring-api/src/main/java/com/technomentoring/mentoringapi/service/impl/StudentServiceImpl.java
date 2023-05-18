package com.technomentoring.mentoringapi.service.impl;

import com.technomentoring.mentoringapi.exception.DataAlreadyExistsException;
import com.technomentoring.mentoringapi.exception.ModelNotFoundException;
import com.technomentoring.mentoringapi.model.Student;
import com.technomentoring.mentoringapi.repository.IGenericRepository;
import com.technomentoring.mentoringapi.repository.IStudentRepository;
import com.technomentoring.mentoringapi.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends CRUDImpl<Student, Integer> implements IStudentService {
    private final IStudentRepository repo;

    @Override
    protected IGenericRepository<Student, Integer> getRepo() {
        return repo;
    }

    @Override
    public Student save(Student student) throws Exception {
        String name = student.getName();
        String DNI = student.getDNI();
        String email = student.getEmail();
        String password = student.getPassword();
        if (isStudentDuplicate(name,DNI,email,password)){
            throw new DataAlreadyExistsException("El estudiante con los detalles que ingresaste ya existe.");
        }
        return super.save(student);
    }
    @Override
    public Student update(Student student, Integer idStudent) throws Exception {
        String name = student.getName();
        String DNI = student.getDNI();
        String email = student.getEmail();
        String password = student.getPassword();
        getRepo().findById(idStudent).orElseThrow(() -> new ModelNotFoundException("EL estudiante con id "+ idStudent + " ya existe."));
        if (isStudentDuplicateUpdate(name,DNI)){
            throw new DataAlreadyExistsException("El mentor con los detalles que ingresaste ya existe.");
        }
        return super.update(student, idStudent);
    }

    @Override
    public List<Student> readAll() throws Exception {
        return null;
    }

    @Override
    public boolean isStudentDuplicate(String name, String DNI, String email, String password) {
        return repo.existsByNameOrDNI(name,DNI);
    }
    public boolean isStudentDuplicateUpdate(String name, String DNI) {
        return repo.existsByNameAndDNI(name,DNI);
    }
}
