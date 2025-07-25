package inflearn_java_advanced2.io.text;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static inflearn_java_advanced2.io.text.TestConst.FILE_NAME;
import static java.nio.charset.StandardCharsets.*;

public class ReaderWriterMainV2 {
    public static void main(String[] args) throws IOException {

        String writeString ="ABC";
        System.out.println("writeString = " + writeString);

        FileOutputStream fos = new FileOutputStream(FILE_NAME);
        OutputStreamWriter osw = new OutputStreamWriter(fos, UTF_8);

        osw.write(writeString);
        osw.close();

        FileInputStream fis = new FileInputStream(FILE_NAME);
        InputStreamReader isr = new InputStreamReader(fis, UTF_8);

        StringBuilder content = new StringBuilder();
        int ch;
        while((ch = isr.read()) != -1) {
            content.append((char)ch);
        }
        isr.close();

        System.out.println("read String = " + content);
    }
}
