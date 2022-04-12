package com.virtuslab.internship.receipt;

import com.virtuslab.internship.product.Product;

import java.math.BigDecimal;


public record ReceiptEntry(
        Product product,
        int quantity,
        BigDecimal totalPrice) {

    public ReceiptEntry(Product product, int quantity) {
        this(product, quantity, product.price().multiply(BigDecimal.valueOf(quantity)));
    }

    @Override
    public String toString() {
        return product+
                "" + quantity +
                "</td><td>" + totalPrice +
                "</td>";
    }
}
