package zad1;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static final String LOCALHOSTIP="127.0.0.1";
    private final ServerSocket serverSocket;
    private final Map<String,Integer> dictionaryServersPorts = new HashMap<>();
    private Map<String,String> mapOfDictionaryFiles = new HashMap<>();



    public Server() throws IOException {
        createBaseMapOfDictionaryFiles();
        this.serverSocket = new ServerSocket(3000);
    }


    //dodać pozostałe pliki języków
    private void createBaseMapOfDictionaryFiles() {
        mapOfDictionaryFiles.put("EN","src\\PL-EN.txt");
        mapOfDictionaryFiles.put("FR","");
        mapOfDictionaryFiles.put("RU","");
    }

    private void addNewDictionaryServer(String languageCode, String filePath){
        mapOfDictionaryFiles.put(languageCode,filePath);
    }


}
