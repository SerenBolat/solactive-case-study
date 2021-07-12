package com.serenbo.repository;

import com.serenbo.models.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Seren Bolat
 */
@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, String> {
    ProductEntity findByAsOfAndId(int asOf, String id);
}
