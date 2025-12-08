package com.microtech.microtech.service.impl;

import com.microtech.microtech.dto.request.auth.CreateClientRequest;
import com.microtech.microtech.dto.request.auth.UpdateClientRequest;
import com.microtech.microtech.dto.response.auth.ClientResponse;
import com.microtech.microtech.dto.response.order.OrderResponse;
import com.microtech.microtech.dto.response.statistics.ClientStatisticResponse;
import com.microtech.microtech.exception.ResourceNotFoundException;
import com.microtech.microtech.exception.UniqueResourceException;
import com.microtech.microtech.mapper.ClientMapper;
import com.microtech.microtech.model.Client;
import com.microtech.microtech.repository.ClientRepository;
import com.microtech.microtech.repository.OrderRepository;
import com.microtech.microtech.repository.UserRepository;
import com.microtech.microtech.security.exception.ForbiddenException;
import com.microtech.microtech.security.service.PasswordService;
import com.microtech.microtech.security.service.SessionService;
import com.microtech.microtech.service.ClientService;
import com.microtech.microtech.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ClientMapper clientMapper;
    private final PasswordService passwordService;
    private final OrderRepository orderRepository;
    private final SessionService session;
    private final OrderService orderService;

    @Override
    public ClientResponse create(CreateClientRequest request) {
        if(userRepository.existsByEmail(request.email())){
            throw new UniqueResourceException("The email you chose is already reserved email : "+ request.email());
        }

        if(clientRepository.findByCin(request.cin()).isPresent()){
            throw new UniqueResourceException("The cin you chose is already reserved cin : "+ request.cin());
        }

        String hashed = passwordService.hash(request.password());
        Client client = clientMapper.toEntity(request);
        client.setPassword(hashed);
        Client saved = clientRepository.save(client);

        return clientMapper.toResponse(saved);
    }

    @Override
    public ClientResponse update(Long id, UpdateClientRequest request) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with this id : "+ id));

        clientMapper.updateEntityFromDto(request, client);

        return clientMapper.toResponse(client);
    }

    @Override
    public void delete(Long id) {
        if(!clientRepository.existsById(id)){
            throw new ResourceNotFoundException("Client not found with id : "+id);
        }

        clientRepository.deleteById(id);
    }

    @Override
    public ClientResponse viewByCin(String Cin) {
        Client client = clientRepository.findByCin(Cin)
                .orElseThrow(() -> new ResourceNotFoundException("No client found with this cin : "+Cin));
        return clientMapper.toResponse(client);
    }

    @Override
    public ClientStatisticResponse statistics() {
        Long id = session.getUserId();

        if(!clientRepository.existsById(id)){
            throw new ForbiddenException();
        }

        Long countOrders = orderRepository.countOrdersByClientId(id);
        double montant = orderRepository.getTotalConfirmedOrdersByClientId(id);
        LocalDateTime firstDate = orderRepository.getFirstOrderDate(id).orElse(null);
        LocalDateTime lastDate = orderRepository.getLastOrderDate(id).orElse(null);

        return new ClientStatisticResponse(
                countOrders,
                montant,
                firstDate,
                lastDate
        );
    }

    @Override
    public List<OrderResponse> history() {
        Long id = session.getUserId();
        if(!clientRepository.existsById(id)){
            throw new ForbiddenException();
        }

        return orderService.ordersOfClient(id);
    }
}
