package com.technomentoring.mentoringapi.controller;

import com.technomentoring.mentoringapi.dto.PaymentDTO;
import com.technomentoring.mentoringapi.dto.StudentDTO;
import com.technomentoring.mentoringapi.model.Payment;
import com.technomentoring.mentoringapi.model.Student;
import com.technomentoring.mentoringapi.service.IPaymentService;
import com.technomentoring.mentoringapi.service.IStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final IPaymentService service;
    @Qualifier("paymentMapper")
    private final ModelMapper mapper;

    public Payment convertToEntity(PaymentDTO dto) {
        return mapper.map(dto, Payment.class);
    }

    public PaymentDTO convertToDto(Payment obj) {
        return mapper.map(obj, PaymentDTO.class);
    }

    @PostMapping("/create")
    public ResponseEntity<Payment> create(@Valid @RequestBody PaymentDTO dto) throws Exception {
        Payment obj = service.save(convertToEntity(dto));
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }
}
