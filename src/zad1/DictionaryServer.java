package zad1;

import zad1.dictinaries.Dictionary;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class DictionaryServer {

    private String ip;
    private Dictionary dictionary = null;
    private String languageCode;
    ServerSocket ss = null;
    private BufferedReader in = null;
    private PrintWriter out = null;


    public DictionaryServer(String languageCode,String dictionaryFile,String ip) throws IOException {
        this.languageCode = languageCode;
        this.dictionary = new Dictionary(dictionaryFile);
        this.ip=ip;
        this.ss = new ServerSocket(0);
    }


    public void runServer() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try (Socket socket = ss.accept()) {

                        String clientMessage = MessageFromServer(socket);
                        sendMessageToClient(clientMessage);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        System.out.println("Uruchomiony serwer: "+ languageCode+" " + getDictServerPort());
        Thread thread = new Thread(runnable);
        thread.start();
    }


    private void sendMessageToClient(String messageFromServer) throws Exception {
        if (messageFromServer == null) {
            throw new Exception("no required data in message");
        }
            String [] messageData = messageFromServer.split(" ");
            String wordToTranslate = messageData[0];
            int clientPort = Integer.parseInt(messageData[1]);

        try (Socket clientSocket = new Socket(ip, clientPort);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream())) {

            String translation = dictionary.getTranslatedWord(wordToTranslate);

            objectOutputStream.writeObject(translation);
            objectOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String MessageFromServer(Socket socket) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
            return (String) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    int getDictServerPort() {
        return this.ss.getLocalPort();
    }

    public String getLanguageCode() {
        return languageCode;
    }
}
