package this_is_java.ch18.sec08;

import java.io.*;

public class DataInputOutputStreamExample {
    public static void main(String[] args) throws Exception {

        FileOutputStream fos = new FileOutputStream("temp/primitive.db");
        DataOutputStream dos = new DataOutputStream(fos);

        dos.writeUTF("홍길동");
        dos.writeDouble(95.5);
        dos.writeInt(1);

        dos.writeUTF("김자바");
        dos.writeDouble(90.3);
        dos.writeInt(2);

        dos.flush();
        dos.close();
        fos.close();

        FileInputStream fis = new FileInputStream("temp/primitive.db");
        DataInputStream dis = new DataInputStream(fis);

        for (int i = 0; i < 2; i++) {
            String name = dis.readUTF();
            double score = dis.readDouble();
            int order = dis.readInt();

            System.out.println(name + " : " + score + " : " + order);
        }

        dis.close();
        fis.close();
    }
}



