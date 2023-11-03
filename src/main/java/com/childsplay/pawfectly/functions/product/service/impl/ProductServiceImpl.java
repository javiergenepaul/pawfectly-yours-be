package com.childsplay.pawfectly.functions.product.service.impl;

import com.childsplay.pawfectly.common.model.Product;
import com.childsplay.pawfectly.functions.product.repository.ProductRepository;
import com.childsplay.pawfectly.functions.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    /**
     * Controller method for retrieving a list of all products.
     *
     * @return An ApiResultModel containing the list of products, along with a message and an HTTP status code.
     */
    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
