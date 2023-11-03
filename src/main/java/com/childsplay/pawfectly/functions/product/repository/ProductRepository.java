package com.childsplay.pawfectly.functions.product.repository;

import com.childsplay.pawfectly.common.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
