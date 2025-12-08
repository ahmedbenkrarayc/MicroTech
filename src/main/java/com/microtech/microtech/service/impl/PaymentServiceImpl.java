package com.microtech.microtech.service.impl;

import com.microtech.microtech.dto.request.payment.CreateChequeDto;
import com.microtech.microtech.dto.request.payment.CreateEspeceDto;
import com.microtech.microtech.dto.request.payment.CreatePaymentRequest;
import com.microtech.microtech.dto.request.payment.CreateVirementDto;
import com.microtech.microtech.dto.response.payment.*;
import com.microtech.microtech.event.UpdateFidelityLevelEvent;
import com.microtech.microtech.exception.ActionNotAllowedException;
import com.microtech.microtech.exception.ResourceNotFoundException;
import com.microtech.microtech.model.*;
import com.microtech.microtech.model.enums.OrderStatus;
import com.microtech.microtech.model.enums.PaymentStatus;
import com.microtech.microtech.repository.OrderRepository;
import com.microtech.microtech.repository.PaymentMethodRepository;
import com.microtech.microtech.repository.PaymentRepository;
import com.microtech.microtech.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public PaymentResponse create(CreatePaymentRequest request) {
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with Number : " + request.orderId()));

        if(order.getStatus() == OrderStatus.CONFIRMED || order.getStatus() == OrderStatus.REJECTED || order.getStatus() == OrderStatus.CANCELED)
            throw new ActionNotAllowedException("This order cannot be modified! status = "+ order.getStatus());

        Optional<Payment> latestPayment = paymentRepository.findFirstByOrderIdOrderByPaymentDateDesc(order.getId());

        int paymentNumber = 0;
        if(latestPayment.isPresent()){
            paymentNumber = latestPayment.get().getPaymentNumber() + 1;
        }

        double totalPaymentsPrice = 0;
        if(paymentNumber > 0){
            totalPaymentsPrice = paymentRepository.sumPricesByOrderId(order.getId());
        }


        paymentNumber = paymentNumber == 0 ? 1 : paymentNumber + 1;

        if(order.getTotal() < (totalPaymentsPrice + request.price())){
            throw new IllegalArgumentException("The paid price is more than rest price, rest price is : " + (order.getTotal() - totalPaymentsPrice));
        }

        if(order.getTotal() == (totalPaymentsPrice + request.price())){
            order.setStatus(OrderStatus.CONFIRMED);
            eventPublisher.publishEvent(
                    new UpdateFidelityLevelEvent(order.getClient().getId())
            );
        }

        Payment payment = Payment.builder()
                .price(request.price())
                .paymentDate(LocalDate.now())
                .order(order)
                .paymentNumber(paymentNumber)
                .dateEncaissement(LocalDate.now())
                .build();

        paymentRepository.save(payment);

        PaymentMethod payMethod;
        PaymentMethodResponse methodResponse;
        if(request.method() instanceof CreateVirementDto method){
            Virement virement = new Virement();
            virement.setReference(method.reference());
            virement.setBank(method.bank());
            virement.setStatus(PaymentStatus.CASHED);
            payMethod = virement;
            methodResponse = new VirementResponse(method.reference(), PaymentStatus.CASHED, method.bank());
        }else if(request.method() instanceof CreateChequeDto method){
            Cheque cheque = new Cheque();
            cheque.setReference(method.reference());
            cheque.setBank(method.bank());
            cheque.setStatus(PaymentStatus.CASHED);
            cheque.setEcheance(method.echeance());
            payMethod = cheque;
            methodResponse = new ChequeResponse(method.reference(), PaymentStatus.CASHED, method.bank(), method.echeance());
        }else if(request.method() instanceof CreateEspeceDto method){
            Espece espece = new Espece();
            espece.setReference(method.reference());
            espece.setStatus(PaymentStatus.CASHED);
            payMethod = espece;
            methodResponse = new EspeceResponse(method.reference(), PaymentStatus.CASHED);
        }else{
            throw new IllegalArgumentException("Payment method is invalid!");
        }

        payMethod.setPayment(payment);

        paymentMethodRepository.save(payMethod);

        return PaymentResponse.builder()
                .id(payment.getId())
                .price(payment.getPrice())
                .paymentDate(payment.getPaymentDate())
                .dateEncaissement(payment.getDateEncaissement())
                .paymentNumber(payment.getPaymentNumber())
                .orderId(order.getId())
                .method(methodResponse)
                .build();
    }
}
