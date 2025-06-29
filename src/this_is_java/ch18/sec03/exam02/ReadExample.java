package this_is_java.ch18.sec03.exam02;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ReadExample {
    public static void main(String[] args) {
        try {
            InputStream is = new FileInputStream("temp/test2.db");

            byte[] data = new byte[100];

            while(true) {
                int num = is.read(data); // 최대 100byte를 읽고 읽은 바이트는 data 저장, 읽은 수는 리턴
                if(num == -1) break; // EOF 발생 했을 경우

                for (int i = 0; i < num; i++) {  // 읽은 바이트 출력
                    System.out.println(data[i]);
                }
            }

            is.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



