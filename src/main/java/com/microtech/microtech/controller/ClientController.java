package com.microtech.microtech.controller;

import com.microtech.microtech.dto.request.auth.CreateClientRequest;
import com.microtech.microtech.dto.response.auth.ClientResponse;
import com.microtech.microtech.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponse> create(
            @Valid @RequestBody CreateClientRequest request
    ) {
        ClientResponse response = clientService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


}
