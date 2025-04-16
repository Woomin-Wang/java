package inflearn_java_middle.exception.ex4.exception;

public class SendExceptionV4 extends NetworkClientExceptionV4{
    private String sendData;

    public SendExceptionV4(String sendData, String message) {
        super(message);
        this.sendData = sendData;
    }

    public String getSendData() {
        return sendData;
    }
}
