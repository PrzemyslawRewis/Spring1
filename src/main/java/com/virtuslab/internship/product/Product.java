package com.virtuslab.internship.product;

import java.math.BigDecimal;

public record Product(
        String name,
        Type type,
        BigDecimal price
) {
    public enum Type {
        DAIRY, FRUITS, VEGETABLES, MEAT, GRAINS
    }

    @Override
    public String toString() {
        return
                "<tr><td></td><td>" + name +
                "</td><td>" + type +
                "</td><td>" + price +
                "</td><td>";
    }
}
