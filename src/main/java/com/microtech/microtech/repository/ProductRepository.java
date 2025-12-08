package com.microtech.microtech.repository;

import com.microtech.microtech.model.Product;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByNameIgnoreCase(String name);
    @Query("""
        SELECT p FROM Product p
        WHERE (:id IS NULL OR p.id = :id)
          AND (:name IS NULL OR TRIM(:name) = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND p.deleted = false
    """)
    Page<Product> findAllProductsFiltered(@Param("id") Long id, @Param("name") String name, Pageable pageable);

    @Override
    @NonNull
    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.deleted = false")
    Optional<Product> findById(@NonNull @Param("id") Long id);
}
