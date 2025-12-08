package com.microtech.microtech.service;

import com.microtech.microtech.dto.request.auth.CreateClientRequest;
import com.microtech.microtech.dto.request.auth.UpdateClientRequest;
import com.microtech.microtech.dto.response.auth.ClientResponse;
import com.microtech.microtech.dto.response.order.OrderResponse;
import com.microtech.microtech.dto.response.statistics.ClientStatisticResponse;

import java.util.List;

public interface ClientService {
    ClientResponse create(CreateClientRequest request);
    ClientResponse update(Long id, UpdateClientRequest request);
    void delete(Long id);
    ClientResponse viewByCin(String Cin);
    ClientStatisticResponse statistics();
    List<OrderResponse> history();
}
