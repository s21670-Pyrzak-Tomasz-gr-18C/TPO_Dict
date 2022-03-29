package zad1;

import zad1.dictinaries.PL_EN_Dictionary;
import zad1.servers.ENServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
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

    public void start() throws IOException {
        runServer();
    runDictionaryServers();

    }

    private void addNewDictionaryServer(String languageCode, String filePath){
        mapOfDictionaryFiles.put(languageCode,filePath);
    }

    private void runServer() {
        //start MainDictionaryServer
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try (Socket socket = serverSocket.accept()) {

//                        String messageFromGui = receiveMessageFromGui(socket);
//                        sendMessageToDictionaryServer(messageFromGui);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void runDictionaryServers() throws IOException {

        ServerSocket ss = null;
        String dictionaryFile = "src/PL-EN.txt";
        try{

            String host = "";
            int port = 6300;

            InetSocketAddress isa = new InetSocketAddress(host,port);
            ss = new ServerSocket();
            ss.bind(isa);

        }catch (Exception ex){
            ex.printStackTrace();
            System.exit(1);
        }
          new ENServer(dictionaryFile,ss);
    }

//        for (DictionaryServer dictServer : dictionaryServers) {
//            dictServer.runServer();
//        }
//            dictionaryServerPortsByLangs.put(dictServer.getLang(), dictServer.getDictServerPort());
//        }
            //   }


        }

