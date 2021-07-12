package com.serenbo.enums;

import lombok.Getter;

/**
 * @author Seren Bolat
 */
@Getter
public enum BusinessType {
    EQUITY("EQUITY"),
    FIXED_INCOME("FIXED_INCOME"),
    OTHER("OTHER");

    private String value;

    BusinessType(String value) {
        this.value = value;
    }
}
