package com.technomentoring.mentoringapi.service;

import com.technomentoring.mentoringapi.model.Payment;
import com.technomentoring.mentoringapi.service.ICRUD;
import jakarta.persistence.Column;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;

public interface IPaymentService extends ICRUD<Payment,Integer> {
    boolean existRequestById(Integer idRequest);
    public boolean isPaymentDuplicate(String amount,String codPayment);
    public boolean isPaymentDuplicateUpdate(String amount,String cod_payment);
}
