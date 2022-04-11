package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FifteenPercentDiscountTest {

        @Test
        void shouldApply15PercentDiscountWhenMoreThan3GrainProducts() {
            // Given
            var productDb = new ProductDb();
            var bread = productDb.getProduct("Bread");
            var cereals = productDb.getProduct("Cereals");
            List<ReceiptEntry> receiptEntries = new ArrayList<>();
            receiptEntries.add(new ReceiptEntry(bread, 2));
            receiptEntries.add(new ReceiptEntry(cereals, 1));

            var receipt = new Receipt(receiptEntries);
            var discount = new FifteenPercentDiscount();
            var expectedTotalPrice = bread.price().multiply(BigDecimal.valueOf(2)).add(cereals.price()).multiply(BigDecimal.valueOf(0.85));

            // When
            var receiptAfterDiscount = discount.apply(receipt);

            // Then
            assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
            assertEquals(1, receiptAfterDiscount.discounts().size());
        }

        @Test
        void shouldNotApply15PercentDiscountWhenLessThan3GrainProducts() {
            // Given
            var productDb = new ProductDb();
            var bread = productDb.getProduct("Bread");
            List<ReceiptEntry> receiptEntries = new ArrayList<>();
            receiptEntries.add(new ReceiptEntry(bread, 2));

            var receipt = new Receipt(receiptEntries);
            var discount = new FifteenPercentDiscount();
            var expectedTotalPrice = bread.price().multiply(BigDecimal.valueOf(2));

            // When
            var receiptAfterDiscount = discount.apply(receipt);

            // Then
            assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
            assertEquals(0, receiptAfterDiscount.discounts().size());
        }

    @Test
    void shouldApply15PercentDiscountWhenMoreThan3GrainProductsAndShouldNotApply10Percent() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        var orange = productDb.getProduct("Orange");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 2));
        receiptEntries.add(new ReceiptEntry(cereals, 1));
        receiptEntries.add(new ReceiptEntry(orange,8));

        var receipt = new Receipt(receiptEntries);
        var discount1 = new FifteenPercentDiscount();
        var discount2 = new TenPercentDiscount();
        var expectedBreadsPrice = bread.price().multiply(BigDecimal.valueOf(2));
        var expectedCerealsPrice= cereals.price().multiply(BigDecimal.valueOf(1));
        var expectedOrangesPrice = orange.price().multiply(BigDecimal.valueOf(8));
        var expectedTotalPrice = (expectedBreadsPrice.add(expectedCerealsPrice)).add(expectedOrangesPrice).multiply(BigDecimal.valueOf(0.85));

        // When
        var receiptAfterFirstDiscount = discount1.apply(receipt);
        var receiptAfterSecondDiscount= discount2.apply(receiptAfterFirstDiscount);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterSecondDiscount.totalPrice());
        assertEquals(1, receiptAfterSecondDiscount.discounts().size());
    }

    @Test
    void shouldApply15PercentDiscountWhenMoreThan3GrainProductsAndShouldApply10Percent() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        var orange = productDb.getProduct("Orange");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 2));
        receiptEntries.add(new ReceiptEntry(orange,100));

        var receipt = new Receipt(receiptEntries);
        var discount1 = new FifteenPercentDiscount();
        var discount2 = new TenPercentDiscount();
        var expectedCerealsPrice = cereals.price().multiply(BigDecimal.valueOf(2));
        var expectedOrangesPrice = orange.price().multiply(BigDecimal.valueOf(100));
        var expectedTotalPrice = expectedCerealsPrice.add(bread.price()).add(expectedOrangesPrice).multiply(BigDecimal.valueOf(0.85)).multiply(BigDecimal.valueOf(0.9));

        // When
        var receiptAfterFirstDiscount = discount1.apply(receipt);
        var receiptAfterSecondDiscount= discount2.apply(receiptAfterFirstDiscount);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterSecondDiscount.totalPrice());
        assertEquals(2, receiptAfterSecondDiscount.discounts().size());
    }
}

