package inflearn_java_advanced2.io.text;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static inflearn_java_advanced2.io.text.TestConst.FILE_NAME;
import static java.nio.charset.StandardCharsets.*;

public class ReaderWriterMainV3 {
    public static void main(String[] args) throws IOException {

        String writeString = "ABC";
        System.out.println("write String: " + writeString);

        FileWriter fw = new FileWriter(FILE_NAME, UTF_8);
        fw.write(writeString);
        fw.close();

        StringBuilder content = new StringBuilder();
        FileReader fr = new FileReader(FILE_NAME, UTF_8);

        int ch;
        while((ch = fr.read()) != -1) {
            content.append((char)ch);
        }
        fr.close();

        System.out.println("read String: " + content);
    }
}
