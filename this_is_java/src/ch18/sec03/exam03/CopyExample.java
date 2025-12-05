package this_is_java.ch18.sec03.exam03;

import java.io.*;

public class CopyExample {
    public static void main(String[] args) throws Exception {

        String originalFileName = "temp/test.jpg";
        String targetFileName = "temp/test2.jpg";

        InputStream is = new FileInputStream(originalFileName);
        OutputStream os = new FileOutputStream(targetFileName);

        byte[] data = new byte[1024]; // 읽은 바이틀르 저장할 배열 생성

        while(true) {
            int num = is.read(data); // 최대 1024 바이트를 읽고 배열에 저장, 읽은 바이트 리턴
            if (num == -1) break; // EOF 발생 시 종료
            os.write(data, 0, num); // 읽은 바이트 수만큼 출력
        }

        os.flush(); // 내부 버퍼 잔류 바이트를 출력하고 버퍼를 비움
        os.close();
        is.close();

        System.out.println("복사 성공");
    }
}



