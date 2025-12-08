package com.microtech.microtech.controller;

import com.microtech.microtech.dto.request.auth.CreateClientRequest;
import com.microtech.microtech.dto.request.auth.UpdateClientRequest;
import com.microtech.microtech.dto.response.auth.ClientResponse;
import com.microtech.microtech.dto.response.order.OrderResponse;
import com.microtech.microtech.dto.response.statistics.ClientStatisticResponse;
import com.microtech.microtech.security.annotation.RolesAllowed;
import com.microtech.microtech.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @RolesAllowed(roles = {"ADMIN"})
    @PostMapping
    public ResponseEntity<ClientResponse> create(
            @Valid @RequestBody CreateClientRequest request
    ) {
        ClientResponse response = clientService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @RolesAllowed(roles = {"ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(
        @PathVariable Long id,
        @Valid @RequestBody UpdateClientRequest request
    ) {
        ClientResponse response = clientService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @RolesAllowed(roles = {"ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }

    @RolesAllowed(roles = {"ADMIN"})
    @GetMapping("/{cin}")
    public ResponseEntity<ClientResponse> getByCin(@PathVariable String cin) {
        ClientResponse response = clientService.viewByCin(cin);
        return ResponseEntity.ok(response);
    }

    @RolesAllowed(roles = {"CLIENT"})
    @GetMapping("/statistics")
    public ResponseEntity<ClientStatisticResponse> statistics() {
        ClientStatisticResponse response = clientService.statistics();
        return ResponseEntity.ok(response);
    }

    @RolesAllowed(roles = {"CLIENT"})
    @GetMapping("/history")
    public ResponseEntity<List<OrderResponse>> history() {
        List<OrderResponse> response = clientService.history();
        return ResponseEntity.ok(response);
    }
}
