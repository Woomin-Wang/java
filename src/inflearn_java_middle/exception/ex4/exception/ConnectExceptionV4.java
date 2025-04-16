package inflearn_java_middle.exception.ex4.exception;

public class ConnectExceptionV4 extends NetworkClientExceptionV4 {
    private String address;

    public ConnectExceptionV4(String address, String message) {
        super(message);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
