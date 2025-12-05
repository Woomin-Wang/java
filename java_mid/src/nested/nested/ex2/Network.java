package inflearn_java_middle.nested.nested.ex2;

public class Network {
    public void sendMessage(String text) {
        NetworkMessage networkMessage = new NetworkMessage(text);
        networkMessage.print();
    }

    private static class NetworkMessage {
        private String text;

        public NetworkMessage(String text) {
            this.text = text;
        }

        public void print() {
            System.out.println(text);
        }
    }
}
