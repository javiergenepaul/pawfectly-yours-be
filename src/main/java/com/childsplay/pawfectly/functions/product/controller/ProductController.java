package com.childsplay.pawfectly.functions.product.controller;

import com.childsplay.pawfectly.common.dto.ApiResultModel;
import com.childsplay.pawfectly.functions.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private final ProductService productService;

    /**
     * Controller method for retrieving a list of all products.
     *
     * @return An ApiResultModel containing the list of products, along with a message and an HTTP status code.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResultModel getAll() {
        return ApiResultModel.builder()
                .message("Product List")
                .resultData(productService.getAll())
                .build();
    }

}
