package inflearn_java_basic.poly.ex.sender;

public class SmsSender implements Sender{
    @Override
    public void sendMessage(String content) {
        System.out.println("SMS를 발송합니다: " + content);
    }
}
