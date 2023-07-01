package com.technomentoring.mentoringapi.repository;

import com.technomentoring.mentoringapi.model.Payment;
import com.technomentoring.mentoringapi.model.Student;
import com.technomentoring.mentoringapi.repository.IGenericRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPaymentRepository extends IGenericRepository<Payment,Integer> {
    boolean existsByIdPayment(Integer idPayment);
    boolean existsByAmountOrCodPayment(String amount, String codPayment);
    boolean existsByAmountAndCodPayment(String amount, String codPayment);

    @Query("FROM Payment m WHERE m.amount = ?1 AND m.codPayment LIKE ?2")
    List<Payment> getPaymentByAmountAndCodPayment(String amount, String cod_Payment);

}
