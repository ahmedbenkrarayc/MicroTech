package com.microtech.microtech.service.impl;

import com.microtech.microtech.dto.request.order.CreateOrderRequest;
import com.microtech.microtech.dto.request.order.OrderProductDto;
import com.microtech.microtech.dto.response.order.OrderResponse;
import com.microtech.microtech.event.UpdateStockEvent;
import com.microtech.microtech.exception.ResourceNotFoundException;
import com.microtech.microtech.mapper.OrderMapper;
import com.microtech.microtech.model.*;
import com.microtech.microtech.model.enums.CustomerTier;
import com.microtech.microtech.model.enums.OrderStatus;
import com.microtech.microtech.repository.ClientRepository;
import com.microtech.microtech.repository.OrderItemRepository;
import com.microtech.microtech.repository.OrderRepository;
import com.microtech.microtech.repository.ProductRepository;
import com.microtech.microtech.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final OrderMapper orderMapper;
    @Value("${tva.value}")
    private float tva;

    @Override
    public String create(CreateOrderRequest request) {
        Client client = clientRepository.findById(request.clientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Client not found with id : " + request.clientId()
                ));

        List<Product> products = fetchProducts(request.products());

        float subtotal = 0;
        for (int i = 0; i < products.size(); i++) {
            subtotal += (float) (products.get(i).getPrice() * request.products().get(i).quantity());
        }

        Double totalOrdersMoney = Optional.ofNullable(orderRepository.getTotalOrdersByClientId(client.getId()))
                .orElse(0.0);

        int fideliteRemise = remiseBasedOnFidelityLevel(client.getTier(), totalOrdersMoney);
        float remise = subtotal * (fideliteRemise / 100.0f);

        if (request.promocode() != null) {
            remise += subtotal * 0.05f; // 5%
        }

        float total = (subtotal - remise) * (1 + tva / 100);

        boolean stockValid = isStockValid(products, request.products());

        Order order = Order.builder()
                .subtotal(subtotal)
                .remise(remise)
                .tva(tva)
                .total(total)
                .client(client)
                .promocode(request.promocode())
                .status(stockValid ? OrderStatus.PENDING : OrderStatus.REJECTED)
                .date(LocalDateTime.now())
                .build();

        Order saved = orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            OrderItemKey key = new OrderItemKey(order.getId(), products.get(i).getId());
            OrderItem orderItem = OrderItem.builder()
                    .id(key)
                    .order(saved)
                    .product(products.get(i))
                    .quantity(request.products().get(i).quantity())
                    .unitPrice((float) products.get(i).getPrice())
                    .build();

            orderItems.add(orderItem);

            // Push event to update stock
            if(stockValid)
                eventPublisher.publishEvent(
                  new UpdateStockEvent(request.products().get(i))
                );
        }

        orderItemRepository.saveAll(orderItems);
        return stockValid ? "Order Created Successfully Number : "+ order.getId() : "Order Rejected Because of stock, Number : "+ order.getId();
    }

    @Override
    public List<OrderResponse> ordersOfClient(Long clientId) {
        if(!clientRepository.existsById(clientId))
            throw new ResourceNotFoundException("Client not found with id : " + clientId);

        return orderRepository.findByClientId(clientId)
                .stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    private List<Product> fetchProducts(List<OrderProductDto> orderProducts) {
        List<Product> products = new ArrayList<>();

        orderProducts
                .forEach(p -> {
                    Product product = productRepository.findById(p.id())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException("Product not found with id : " + p.id())
                            );
                    products.add(product);
                });

        return products;
    }

    private boolean isStockValid(List<Product> products, List<OrderProductDto> dto) {
        for(int i = 0; i < products.size(); i++) {
            if(products.get(i).getStock() - dto.get(i).quantity() < 0){
                return false;
            }
        }

        return true;
    }

    private int remiseBasedOnFidelityLevel(CustomerTier tier, Double totalOrderMoney) {
        switch (tier) {
            case SILVER -> {
                if (totalOrderMoney >= 500) return 5;
            }
            case GOLD -> {
                if (totalOrderMoney >= 800) return 10;
                else return 5;
            }
            case PLATINUM -> {
                if (totalOrderMoney >= 1200) return 15;
                else return 10;
            }
            default -> {
                return 0;
            }
        }
        return 0;
    }
}
