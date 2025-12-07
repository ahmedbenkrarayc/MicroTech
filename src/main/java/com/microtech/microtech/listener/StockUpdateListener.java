package com.microtech.microtech.listener;

import com.microtech.microtech.event.UpdateStockEvent;
import com.microtech.microtech.model.Product;
import com.microtech.microtech.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StockUpdateListener {
    private final ProductRepository productRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handle(UpdateStockEvent event){
        Optional<Product> productOptional = productRepository.findById(event.getOrderProduct().id());
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            int result = (product.getStock() - event.getOrderProduct().quantity());
            product.setStock(result);
            productRepository.save(product);
        }
    }
}
