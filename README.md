#Spring task

My solution for task:

The code includes very basic domain implementation (Product, Basket, Receipt, etc.) and stub for the Product database to be used as a base for an e-commerce application.
To be more specific, you will find the skeleton for creating baskets with products and the generation of receipts. The rule for calculating the basket price is very simple. Every product’s cost should be multiplied by its quantity and then summed. However, there is an extra thing.
A discount may be applied on receipt. If the receipt’s total cost is greater or equal to 50 then a 10% discount is applied. For example, if the total cost is 60, then the discount is applied and the final price is 54.
But here come the challenges:
- The current tests fail. Therefore, the tests should pass as they check the expected outcome of the subjects. Fix the source code to make the tests pass. The test code can be changed, however, the cases must stay the same.
- The product owner came and created a story - a 15% discount on the total price, which should be applied only if at least 3 grain products (products have categories) are in the basket. It applies before the 10% discount. So first you apply 15% to get the new total price and then if it is applicable to the new price, a 10% discount is applied. Implement the described discount.
- The app is not going to work in the void. So the app needs a REST-like endpoint(s).
Please use the framework of your choice (e.g. Spring, Micronaut) to make the
app accept basket information and then return the receipt data.
- Remember, we must be sure that the app works as expected. Please write alll tests
that you consider required.
