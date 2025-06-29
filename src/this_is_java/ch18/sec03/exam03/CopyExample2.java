package this_is_java.ch18.sec03.exam03;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyExample2 {
    public static void main(String[] args) throws Exception {

        String originalFileName = "temp/test1.db";
        String targetFileName = "temp/test2.db";

        InputStream is = new FileInputStream(originalFileName);
        OutputStream os = new FileOutputStream(targetFileName);

        is.transferTo(os);

        os.flush(); // 내부 버퍼 잔류 바이트를 출력하고 버퍼를 비움
        os.close();
        is.close();

        System.out.println("복사 성공");
    }
}



