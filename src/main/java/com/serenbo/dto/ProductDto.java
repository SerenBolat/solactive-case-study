package com.serenbo.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.serenbo.enums.BusinessType;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Seren Bolat
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String id;
    @NotNull
    private String name;
    @NotNull
    private BusinessType business;
    @NotNull
    private String currency;
    @NotNull
    private JsonNode attributes;
    @Min(value = 1, message = "asOf value incorrectly specified")
    private int asOf;
}
