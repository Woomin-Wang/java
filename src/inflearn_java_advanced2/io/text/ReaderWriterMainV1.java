package inflearn_java_advanced2.io.text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static inflearn_java_advanced2.io.text.TestConst.*;
import static java.nio.charset.StandardCharsets.*;

public class ReaderWriterMainV1 {

    public static void main(String[] args) throws IOException {

        String writeString = "ABC";
        byte[] writeBytes = writeString.getBytes(UTF_8);

        System.out.println("writeString = " + writeString);
        System.out.println("writeBytes = " + Arrays.toString(writeBytes));

        FileOutputStream fos = new FileOutputStream(FILE_NAME);
        fos.write(writeBytes);
        fos.close();

        FileInputStream fis = new FileInputStream(FILE_NAME);
        byte[] readBytes = fis.readAllBytes();
        fis.close();

        String readString = new String(readBytes, UTF_8);

        System.out.println("readBytes = " + Arrays.toString(readBytes));
        System.out.println("readString = " + readString);
    }
}
