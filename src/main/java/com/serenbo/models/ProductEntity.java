package com.serenbo.models;

import com.serenbo.enums.BusinessType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Seren Bolat
 */

@Document(collection = "product")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    private String id;
    private String name;
    private BusinessType business;
    private String currency;
    private String attributes;
    private int asOf;

}
