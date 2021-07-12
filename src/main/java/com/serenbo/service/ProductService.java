package com.serenbo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.serenbo.dto.ProductDto;

/**
 * @author Seren Bolat
 */
public interface ProductService {

    ProductDto createProduct(ProductDto request) throws JsonProcessingException;

    ProductDto getProduct(String id, int timestamp) throws JsonProcessingException;

}
