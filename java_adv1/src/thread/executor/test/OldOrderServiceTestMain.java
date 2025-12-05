package inflearn_java_advanced1.thread.executor.test;

import java.util.concurrent.ExecutionException;

public class OldOrderServiceTestMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        String orderNo = "Order#1234";
//        OldOrderService oldOrderService = new OldOrderService();
//        oldOrderService.order(orderNo);

        NewOrderService newOrderService = new NewOrderService();
        newOrderService.order(orderNo);
    }
}
