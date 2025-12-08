package com.microtech.microtech.listener;

import com.microtech.microtech.event.UpdateFidelityLevelEvent;
import com.microtech.microtech.exception.ResourceNotFoundException;
import com.microtech.microtech.model.Client;
import com.microtech.microtech.model.enums.CustomerTier;
import com.microtech.microtech.repository.ClientRepository;
import com.microtech.microtech.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class FidelityLevelCalculateListener {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handle(UpdateFidelityLevelEvent event){
        Long totalOrders = orderRepository.countConfirmedOrdersByClientId(event.getOrderId());
        Double totalMoney = orderRepository.getTotalConfirmedOrdersByClientId(event.getOrderId());
        Client client = clientRepository.findById(event.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id : " + event.getOrderId()));

        if(totalOrders >= 20 || totalMoney >= 15000){
            client.setTier(CustomerTier.PLATINUM);
            clientRepository.save(client);
        }else if(totalOrders >= 10 || totalMoney >= 5000){
            client.setTier(CustomerTier.GOLD);
            clientRepository.save(client);
        }else if(totalOrders >= 3 || totalMoney >= 1000){
            client.setTier(CustomerTier.SILVER);
            clientRepository.save(client);
        }
    }
}
