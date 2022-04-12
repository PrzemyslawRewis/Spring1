package com.virtuslab.internship.controller;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class BasketController {

    public final String beginHTML = """
            <!doctype html>
            <html lang="pl">
            <head>
                 <meta charset="utf-8">
                 <meta name="viewport" content="width=device-width">
                 <title> VirtusLab Task</title>
                 <style>
                                    body{
                                       
                                    	background-color: #4b4e53;
                                    	text-align: center;
                                    	font-family: Verdana, Geneva, Tahoma, sans-serif ;
                                    }
                                    header {
                                        position: fixed;
                                        padding: 15px;
                                        left:0px;
                                        top:0px;
                                        width: 100%;
                                        text-align: center;
                                        background: rgb(90, 95, 126);
                                        color: white;
                                        font-size: 30px;
                                    }
                                    
                                    a
                                    {
                                      padding: 5px 10px;
                                      font-size: 10px;
                                      background-color: rgb(239, 230, 247);;
                                      border: none;
                                      color: black;
                                      padding: 10px 24px;
                                      text-align: center;
                                      text-decoration: none;
                                      display: inline-block;
                                      font-size: 16px;
                                      margin: 4px 2px;
                                      border-radius: 12px;
                                      cursor: pointer;
                                        
                                    }
                                    a:hover
                                    {
                                        color: black;
                                        background:  rgba(239, 230, 247, 0.603);
                                        
                                    }
                                    
                             
                                    h1,h3{
                                        color: rgb(239, 230, 247);
                                        display: block;
                                        margin-left: 25%;
                                        margin-right: 25%;
                                    }
                                    h4{
                                        display: block;
                                        color: rgb(105, 156, 185);
                                    }
                                        
                                    p{
                                        display: block;
                                        color: rgb(105, 156, 185);
                                        text-align:  justify;
                                        margin-left: 25%;
                                        margin-right: 25%;
                                    }
                                    
                                    .textContent ul, li, ol{
                                        color: rgb(123, 164, 189);
                                        text-align: justify;
                                        margin-left: 25%;
                                        margin-right: 25%;
                                    }
                                    
                                    table{
                                        display: inline-block;
                                        margin-left: 25%;
                                        margin-right: 25%;
                                        border: 1px solid;
                                        border-radius: 6px;
                                        color: rgb(105, 156, 185);
                                        
                                    }
                                        
                                  
                                    thead,td,tr{
                                       
                                        padding: 5px;
                                        border : 1px solid;
                                        border-radius: 6px;
                                    }
                                    th{
                                        padding: 5px;
                                      
                                    }
                                    td.blank{
                                        border : 0px;
                                    }
                                      
                                        
                                    footer {
                                        position: fixed;
                                        padding-top: 20px;
                                        padding-bottom: 20px;
                                        left: 0px;
                                        bottom: 0px;
                                        right: 5px;
                                        width: 100%;
                                        text-align: center;
                                        padding: 7px;
                                        background: rgb(90, 95, 126);
                                        color: rgb(239, 230, 247);
                                    }
                        </style>
                        </head>
                        <body>
                
            """;
    public final String endHTML= """
            <footer>Przemysław Rewiś 04.2022 </footer>
            </body>
            </html>
            """;

    @RequestMapping("/")
    public String welcomePage() {
        return beginHTML+"""
              
                <h1>
                VirtusLab Intership Task
                </h1>
                <a href="/receipt">Accept basket information and then show the receipt data.</a>      
                """+endHTML;
    }
    @RequestMapping("/receipt")
    public String receiptPage() {
        var sampleBasket = new Basket();
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        var bread = productDb.getProduct("Bread");
        var apple = productDb.getProduct("Apple");
        sampleBasket.addProduct(bread);
        sampleBasket.addProduct(bread);
        sampleBasket.addProduct(bread);
        sampleBasket.addProduct(cheese);
        sampleBasket.addProduct(cheese);
        sampleBasket.addProduct(apple);
        sampleBasket.addProduct(apple);
        ResponseEntity<Receipt> responseEntity=getReceiptFromBasket(sampleBasket);
        Receipt rec = responseEntity.getBody();
        String result= rec.toString();
        result=result.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(",","");
            assert rec != null;
            return beginHTML+" <h1>Your receipt data:<br></h1>"+result+""+endHTML;
    }



    @PostMapping("/post")
    public ResponseEntity<Receipt> getReceiptFromBasket(@RequestBody Basket basket) {
        try {
            ReceiptGenerator receiptGenerator = new ReceiptGenerator();
            Receipt receipt = receiptGenerator.generate(basket);
            TenPercentDiscount tenPercentDiscount = new TenPercentDiscount();
            FifteenPercentDiscount fifteenPercentDiscount = new FifteenPercentDiscount();
            receipt = fifteenPercentDiscount.apply(receipt);
            receipt = tenPercentDiscount.apply(receipt);
            return new ResponseEntity<>(receipt, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


}

