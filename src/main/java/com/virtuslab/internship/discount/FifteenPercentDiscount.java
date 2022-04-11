package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;

import java.math.BigDecimal;

public class FifteenPercentDiscount {

        public static String NAME = "FifteenPercentDiscount";

        public Receipt apply(Receipt receipt) {
            if (shouldApply(receipt)) {
                var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.85));
                var discounts = receipt.discounts();
                discounts.add(NAME);
                return new Receipt(receipt.entries(), discounts, totalPrice);
            }
            return receipt;
        }

        private boolean shouldApply(Receipt receipt) {
            int howManyGrainProducts=0;
            for (var i : receipt.entries())
            {
                if(i.product().type().equals(Product.Type.GRAINS))
                {
                    howManyGrainProducts+=i.quantity();
                }
            }
            return (howManyGrainProducts>=3)?true:false;
        }
}

