package com.technomentoring.mentoringapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
<<<<<<< HEAD
public interface IGenericRepository<T, ID>extends JpaRepository<T, ID> {
=======
public interface IGenericRepository <T, ID>extends JpaRepository<T, ID> {
>>>>>>> bca6ad126b394b55df38ae507306b9aab96eea20
}
