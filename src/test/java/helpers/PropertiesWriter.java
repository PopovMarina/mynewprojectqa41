package helpers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesWriter {

    public static void main(String[] args) {
        writeProperties("myKey4","myValue276", false);
    }
    private static final String PRORERTIES_FILE_PATH = "src/test/resources/data.properties";
    public static void writeProperties(String key, String value, boolean cleanFile) {

        Properties properties = new Properties();
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            if (!Files.exists(Paths.get(PRORERTIES_FILE_PATH))) {
                Files.createFile(Paths.get(PRORERTIES_FILE_PATH));

            }
            fileInputStream = new FileInputStream(PRORERTIES_FILE_PATH);
            properties.load(fileInputStream);
            if (cleanFile) {//если он тру
                properties.clear();
            }
            properties.setProperty(key, value);
            fileOutputStream = new FileOutputStream(PRORERTIES_FILE_PATH);
            properties.store(fileOutputStream, null);

        }catch (IOException e) {
            e.printStackTrace();
        }
finally {
            try{
                if(fileInputStream != null) {
                    fileInputStream.close();
                }
                if(fileOutputStream != null) {
                    fileOutputStream.close();
                }
            }catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
