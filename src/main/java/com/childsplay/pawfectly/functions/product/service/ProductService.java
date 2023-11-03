package com.childsplay.pawfectly.functions.product.service;

import com.childsplay.pawfectly.common.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    /**
     * Controller method for retrieving a list of all products.
     *
     * @return An ApiResultModel containing the list of products, along with a message and an HTTP status code.
     */
    List<Product> getAll();
}
