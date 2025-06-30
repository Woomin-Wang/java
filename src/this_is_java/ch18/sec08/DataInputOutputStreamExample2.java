package this_is_java.ch18.sec08;

import java.io.*;

public class DataInputOutputStreamExample2 {
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



        try(FileInputStream fis = new FileInputStream("temp/primitive.db");
            DataInputStream dis = new DataInputStream(fis);
                ) {
            while (true) {
                String name = dis.readUTF();
                double score = dis.readDouble();
                int order = dis.readInt();

                System.out.println(name + " : " + score + " : " + order);
            }
        } catch (EOFException e) {
            System.out.println("모든 데이터를 읽었습니다.");
        }
    }
}



