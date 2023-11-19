package service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LocalFileManager {

    public List<String> fileReader(String fileName ) throws IOException {
        List<String> content = new ArrayList<>();
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        InputStream inputStream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String str;
        while ((str=reader.readLine()) != null){
            content.add(str);
        }
        return content;
    }

    public void fileWriter(String fileName, List<String> content ,String pathToMainClass) throws IOException {
        File file = new File(pathToMainClass, fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : content) {
                writer.write(line);
                writer.newLine();
                writer.newLine();
            }
        }
    }
}
