package com.virtuslab.internship.receipt;

import com.virtuslab.internship.discount.TenPercentDiscount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record Receipt(
        List<ReceiptEntry> entries,
        List<String> discounts,
        BigDecimal totalPrice) {

    public Receipt(List<ReceiptEntry> entries) {
        this(entries, new ArrayList<>(),
                entries.stream()
                        .map(ReceiptEntry::totalPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }

    public String discountsReturner() {
        String discount = new String();
        if(discounts.isEmpty())
        {
            discount = "No discounts";
        }
        else if(discounts.contains("FifteenPercentDiscount"))
        {
            discount=(discounts.size()==2)?"15% and 10%":"15%";

        }
        else if (discounts.contains("TenPercentDiscount"))
        {
            discount= (discounts.size()==2)?"15% and 10%":"10%";
        }
        return discount;

    }

    @Override
    public String toString() {
        return "<table>"+
                "<tr><td></td><td>name</td><td>type</td><td>price</td><td>quantity</td><td>Total price</td></tr>"+entries+
                "<tr> <td>Discounts:</td><td colspan=5>"+discountsReturner()+"</td></tr>"+
                "<tr> <td>Total price:</td><td colspan=5>" + totalPrice +
                        "</td></tr></table>";
    }
}
