package this_is_java.ch18.sec03.exam01;

import java.io.*;

public class ReadExample {
    public static void main(String[] args) {
        try {
            InputStream is = new FileInputStream("temp/test1.db");

            while(true) {
                int data = is.read(); // 1byte씩 읽기
                if(data == -1) break; // 파일 끝에 도달했을 경우
                System.out.println(data);
            }

            is.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





