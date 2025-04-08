package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonFileHandler {

   public  static final String  ACCOUNTS_FILE_PATH=System.getProperty("user.dir") + File.separator + "Data" + File.separator + "Accounts.json";
    public  static final String  ACCOUNTS_SESSION_FILE_PATH=System.getProperty("user.dir") + File.separator + "Data" + File.separator + "AccountSession.json";
    public  static final String  COMMANDE_FILE_PATH=System.getProperty("user.dir") + File.separator + "Data" + File.separator + "Commande.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static <T> List<T> loadData(String filePath, TypeReference<List<T>> typeReference) throws IOException {
        return objectMapper.readValue(new File(filePath), typeReference);
    }
    public static <T> T loadDataObject(String filePath, TypeReference<T> typeReference) throws IOException {
        return objectMapper.readValue(new File(filePath), typeReference);
    }

    public static <T> void saveData(String filePath, List<T> data) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
    }
    public static <T> void saveDataObject(String filePath, T data) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
    }
}
