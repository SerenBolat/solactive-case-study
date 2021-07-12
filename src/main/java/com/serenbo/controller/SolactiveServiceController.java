package com.serenbo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.serenbo.dto.ProductDto;
import com.serenbo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Seren Bolat
 */
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class SolactiveServiceController {

    private final ProductService productService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/product")
    public String productCreate(@Valid @RequestBody ProductDto request) throws JsonProcessingException {
        return productService.createProduct(request).getId();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/product/{id}")
    public Object productRead(@PathVariable String id, @RequestParam int timestamp) throws JsonProcessingException {
        return productService.getProduct(id, timestamp).getAttributes();
    }
}
