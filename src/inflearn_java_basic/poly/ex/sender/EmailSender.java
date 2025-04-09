package inflearn_java_basic.poly.ex.sender;

public class EmailSender implements Sender{
    @Override
    public void sendMessage(String content) {
        System.out.println("메일을 발송합니다: " + content);
    }
}
