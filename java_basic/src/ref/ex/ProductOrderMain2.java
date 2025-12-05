package inflearn_java_basic.ref.ex;

import java.util.Scanner;

public class ProductOrderMain2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("입력할 주문의 개수를 입력하세요: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        ProductOrder[] productOrders = new ProductOrder[n];

        for (int i = 0; i < productOrders.length; i++) {
            System.out.println((i + 1) + "번째 주문 정보를 입력하세요.");
            String productName = scanner.nextLine();
            int price = scanner.nextInt();
            scanner.nextLine();
            int grade = scanner.nextInt();
            scanner.nextLine();
            productOrders[i] = createOrder(productName, price, grade);
        }

        printOrders(productOrders);

        System.out.println("총 결제 금액: " + getTotalAmount(productOrders));

    }

    static ProductOrder createOrder(String productName, int price, int quantity) {
        ProductOrder productOrder = new ProductOrder();

        productOrder.productName = productName;
        productOrder.price = price;
        productOrder.quantity = quantity;

        return productOrder;
    }

    static void printOrders(ProductOrder[] orders) {
        for (ProductOrder productOrder : orders) {
            System.out.println("상품명: " + productOrder.productName + " 가격: " + productOrder.price + " 수량: " + productOrder.quantity);
        }
    }

    static int getTotalAmount(ProductOrder[] orders) {
        int sum = 0;

        for (ProductOrder productOrder : orders) {
            sum += (productOrder.price * productOrder.quantity);
        }
        return sum;
    }
}
