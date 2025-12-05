package inflearn_java_basic.class1.ex2;

public class ProductOrderMain {
    public static void main(String[] args) {
        ProductOrder productOrder1 = new ProductOrder();
        ProductOrder productOrder2 = new ProductOrder();
        ProductOrder productOrder3 = new ProductOrder();

        productOrder1.productName = "두부";
        productOrder1.price = 2000;
        productOrder1.quantity = 2;

        productOrder2.productName = "김치";
        productOrder2.price = 5000;
        productOrder2.quantity = 1;

        productOrder3.productName = "콜라";
        productOrder3.price = 1500;
        productOrder3.quantity = 2;

        ProductOrder[] productOrders = {productOrder1, productOrder2, productOrder3};

        int sum = 0;

        for (ProductOrder productOrder: productOrders) {
            sum += (productOrder.price * productOrder.quantity);
        }

        System.out.println("총 결제 금액: " + sum);
    }
}
