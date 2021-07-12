package com.serenbo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serenbo.dto.ProductDto;
import com.serenbo.exception.DataNotFoundException;
import com.serenbo.models.ProductEntity;
import com.serenbo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Seren Bolat
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ProductDto createProduct(ProductDto request) throws JsonProcessingException {
        ProductEntity entity = ProductEntity.builder()
                .name(request.getName())
                .business(request.getBusiness())
                .currency(request.getCurrency())
                .attributes(request.getAttributes().toPrettyString())
                .asOf(request.getAsOf()).build();

        ProductEntity savedEntity = productRepository.save(entity);
        JsonNode attributes = objectMapper.readTree(savedEntity.getAttributes());

        return ProductDto.builder()
                .id(savedEntity.getId())
                .name(savedEntity.getName())
                .business(savedEntity.getBusiness())
                .currency(savedEntity.getCurrency())
                .attributes(attributes)
                .asOf(savedEntity.getAsOf()).build();
    }

    @Override
    public ProductDto getProduct(String id, int timestamp) throws JsonProcessingException {
        ProductEntity productEntity = Optional
                .ofNullable(productRepository.findByAsOfAndId(timestamp, id))
                .orElseThrow(DataNotFoundException::new);

        JsonNode attributes = objectMapper.readTree(productEntity.getAttributes());
        return ProductDto.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .business(productEntity.getBusiness())
                .currency(productEntity.getCurrency())
                .attributes(attributes)
                .asOf(productEntity.getAsOf()).build();
    }
}
