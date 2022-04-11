package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;


import java.util.*;

public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        List<Product> productList = basket.getProducts();
        Map<Product, Integer> frequencyMap = new HashMap<>();
        for (Product s: productList)
        {
            Integer count = frequencyMap.get(s);
            if (count == null) {
                count = 0;
            }
            frequencyMap.put(s, count + 1);
        }
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        for(Map.Entry<Product, Integer> val : frequencyMap.entrySet())
        {
            receiptEntries.add(new ReceiptEntry(val.getKey(), val.getValue()));
        }
        Receipt newReceipt = new Receipt(receiptEntries);

        return newReceipt;
    }
}
