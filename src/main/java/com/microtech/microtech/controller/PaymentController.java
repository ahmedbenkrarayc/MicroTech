package com.microtech.microtech.controller;

import com.microtech.microtech.dto.request.payment.CreatePaymentRequest;
import com.microtech.microtech.dto.response.payment.PaymentResponse;
import com.microtech.microtech.security.annotation.RolesAllowed;
import com.microtech.microtech.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @RolesAllowed(roles = {"ADMIN"})
    @PostMapping
    public ResponseEntity<PaymentResponse> create(
            @Valid @RequestBody CreatePaymentRequest request
    ) {
        PaymentResponse response = paymentService.create(request);
        return ResponseEntity.ok(response);
    }
}
