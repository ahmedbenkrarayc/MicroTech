package com.microtech.microtech.service;

import com.microtech.microtech.dto.request.payment.CreatePaymentRequest;
import com.microtech.microtech.dto.response.payment.PaymentResponse;

public interface PaymentService {
    PaymentResponse create(CreatePaymentRequest request);
}
