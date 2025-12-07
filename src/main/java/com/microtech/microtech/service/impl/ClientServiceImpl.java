package com.microtech.microtech.service.impl;

import com.microtech.microtech.dto.request.auth.CreateClientRequest;
import com.microtech.microtech.dto.request.auth.UpdateClientRequest;
import com.microtech.microtech.dto.response.auth.ClientResponse;
import com.microtech.microtech.exception.ResourceNotFoundException;
import com.microtech.microtech.exception.UniqueResourceException;
import com.microtech.microtech.mapper.ClientMapper;
import com.microtech.microtech.model.Client;
import com.microtech.microtech.repository.ClientRepository;
import com.microtech.microtech.repository.UserRepository;
import com.microtech.microtech.security.service.PasswordService;
import com.microtech.microtech.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ClientMapper clientMapper;
    private final PasswordService passwordService;

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
}
