package zad1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private static final String LOCALHOSTIP = "127.0.0.1";
    private final ServerSocket serverSocket;
    private Map<String, String> mapOfDictionaryFiles = new HashMap<>();
    private List<DictionaryServer> dictionaryServers = new ArrayList<>();
    private int dictionaryServerPort;


    public Server() throws IOException {
        this.serverSocket = new ServerSocket(3000);
        createBaseMapOfDictionaryFiles();
        createDictionaryServers();
    }

    private void createBaseMapOfDictionaryFiles() {
        mapOfDictionaryFiles.put("EN", "src\\PL-EN.txt");
        mapOfDictionaryFiles.put("FR", "src\\PL-FR.txt");
        mapOfDictionaryFiles.put("RU", "src\\PL-RU.txt");
    }

    public void start() throws IOException {
        runServer();
        runDictionaryServers();

    }

    private void addNewDictionaryServer(String languageCode, String filePath) {
        mapOfDictionaryFiles.put(languageCode, filePath);
    }

    private void runServer() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try (Socket socket = serverSocket.accept()) {

                        String message = receiveMessage(socket);
                        sendToDictionaryServer(message);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void createDictionaryServers() throws IOException {
        for (String key : mapOfDictionaryFiles.keySet()) {
            dictionaryServers.add(new DictionaryServer(key, mapOfDictionaryFiles.get(key),LOCALHOSTIP));
        }
    }

    private void runDictionaryServers() {
        for (DictionaryServer dictServer : dictionaryServers) {
                     dictServer.runServer();
        }
    }

    private void sendToDictionaryServer(String messageFromClient) throws Exception {
        if (messageFromClient == null) {
            throw new Exception("no required data in message");
        }
        String[] messageData = messageFromClient.split(" ");
        if (messageData[2].equals("CREATE")) {
            addNewDictionaryServer(messageData[0],messageData[1]);
            createDictionaryServers();
            runDictionaryServers();
        } else {

            String wordToTranslate = messageData[2];
            int portOnWhichClientWaits = Integer.valueOf(messageData[0]);
            String language = messageData[1];


            for (DictionaryServer server : dictionaryServers) {
                if (language.equals(server.getLanguageCode())) {
                    dictionaryServerPort = server.ss.getLocalPort();
                }
            }

            try (Socket dictSocket = new Socket(LOCALHOSTIP, dictionaryServerPort);
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(dictSocket.getOutputStream())) {
                objectOutputStream.writeObject(wordToTranslate + " " + portOnWhichClientWaits);
                objectOutputStream.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String receiveMessage(Socket socket) {

        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
            return (String) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start();
    }

}