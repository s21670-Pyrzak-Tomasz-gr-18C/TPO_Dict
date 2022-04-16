package zad1;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Client {
    private  int clientWaitingPort;
    private String lang;
    private  String wordToTranslate;
    private ServerSocket serverSocket;
    private boolean translationNotReceived;
    private String path;
    private final String keyWord = "CREATE";



    public Client(int clientWaitingPort, String lang, String wordToTranslate) {
        this.clientWaitingPort = clientWaitingPort;
        this.lang = lang;
        this.wordToTranslate = wordToTranslate;
    }
    public Client(String lang, String path){
        this.lang = lang;
        this.path = path;
    }

    public void translate() throws IOException {
        this.serverSocket = new ServerSocket(clientWaitingPort);
        translationNotReceived = true;
        runClient();

        try{
            Socket clientSocket = new Socket("127.0.0.1",3000);
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.writeObject(clientWaitingPort+" "+lang+" "+wordToTranslate);
            out.flush();
            clientSocket.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void runClient() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (translationNotReceived) {
                    try (Socket socket = serverSocket.accept();
                         ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {

                         String response = (String) objectInputStream.readObject();

                         sendResponse(response);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    try{
                        serverSocket.close();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    translationNotReceived=false;

                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void sendResponse(String response) {
        AppMenu.responseLabel.setText(response);

    }

    public void createNewDictionaryServer() {
        try{
            Socket clientSocket = new Socket("127.0.0.1",3000);
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.writeObject(lang+" "+path+" "+keyWord);
            out.flush();
            clientSocket.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
