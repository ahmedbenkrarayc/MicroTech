package com.microtech.microtech.dto.response.payment;

import com.microtech.microtech.model.enums.PaymentStatus;

public interface PaymentMethodResponse {
    String reference();
    PaymentStatus status();
}
