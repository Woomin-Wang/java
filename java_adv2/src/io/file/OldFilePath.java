package inflearn_java_advanced2.io.file;

import java.io.File;
import java.io.IOException;

public class OldFilePath {

    public static void main(String[] args) throws IOException {

        File file = new File("temp/..");
        System.out.println("path=" + file.getPath());

        System.out.println("Absolute path= " + file.getAbsolutePath());

        System.out.println("Canonical path=" + file.getCanonicalPath());

        File[] files = file.listFiles();
        for (File f : files) {
            System.out.println((f.isFile() ? "F" : "D") + " | " + f.getName());
        }
    }
}
