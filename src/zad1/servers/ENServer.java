package zad1.servers;

import zad1.dictinaries.PL_EN_Dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Pattern;

public class ENServer {
    private PL_EN_Dictionary dictionary = null;
    private ServerSocket ss = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    public ENServer(String dictionary, ServerSocket ss) {
        this.dictionary = new PL_EN_Dictionary(dictionary);
        this.ss = ss;
        System.out.println("Server EN started");
        System.out.println("at port: " + ss.getLocalPort());
        System.out.println("bind address: " + ss.getInetAddress());

        serviceConnections();
    }

    private void serviceConnections() {
        boolean serverRunning = true;

        while (serverRunning) {
            try {
                Socket conn = ss.accept();
                System.out.println("Connection established");

                //obsługa zleceń dla tego połączenia
                serviceRequests(conn);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        //zamknięcie gniazda serwera
        try {
            ss.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //wzorzec rozbioru zlecenia
    private static Pattern reqPattern = Pattern.compile(" +",2);


    //obsługa zleceń
    private void serviceRequests(Socket connection)throws IOException {
      try{
        //utworzenie strumieni
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        out = new PrintWriter(connection.getOutputStream(),true);

        //odczytywanie zleceń
        for (String line;(line = in.readLine())!=null;){

          //odpowiedź
          String resp;

          //rozbiór zlecenia
          String[] req = reqPattern.split(line, 2);

          String word = req[0];
          if (req.length!=2){
            writeResp("Invalid request");
          }else{
            String translatedWord = dictionary.getTranslatedWord(word);
            if(translatedWord!=null){
              writeResp(translatedWord);
            }else{
              writeResp("the word: "+word+" not in the dictionary");
            }
          }
        }
      }catch (Exception ex){
        ex.printStackTrace();
      }finally {
        try {                                // zamknięcie strumieni
          in.close();                        // i gniazda
          out.close();
          connection.close();
          connection = null;
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }

    }

  // Przekazanie odpowiedzi klientowi poprzez zapis do strumienia
  // gniazda komuniakcyjnego
  private void writeResp(String info)throws IOException{
    out.println(info);
  }

  public static void main(String[] args) {
    PL_EN_Dictionary dictionary = null;
    ServerSocket ss = null;
    try{
      String dictionaryFile = "src/PL-EN.txt";
      String host = "";
      int port = 2300;

      dictionary = new PL_EN_Dictionary(dictionaryFile);
        System.out.println(dictionary.getTranslatedWord("słoń"));

        InetSocketAddress isa = new InetSocketAddress(host,port);
      ss = new ServerSocket();
      ss.bind(isa);

    }catch (Exception ex){
      ex.printStackTrace();
      System.exit(1);
    }
 //   new ENServer(dictionary,ss);
  }




}






