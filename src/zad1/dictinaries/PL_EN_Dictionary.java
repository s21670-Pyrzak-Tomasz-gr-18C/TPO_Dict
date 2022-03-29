package zad1.dictinaries;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class PL_EN_Dictionary {

    //słownik w postaci mapy
    private Map dictionaryMap = new HashMap();

    //konstruktor tworzy słownik z pliku
    public PL_EN_Dictionary(String fileName) {
      try{
          BufferedReader br = new BufferedReader(new FileReader(fileName));
          String line;
          while ((line= br.readLine())!=null){
              String [] data = line.split(" +", 2);
              dictionaryMap.put(data[0],data[1]);
          }
      }catch (Exception ex){
          ex.printStackTrace();
          System.exit(1);
      }
    }

    //zwraca przetłumaczone słowo
    public String getTranslatedWord(String word){
        return (String) dictionaryMap.get(word);
    }

    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary("src\\PL-EN.txt");
        System.out.println(dictionary.getTranslatedWord("słoń"));


    }
}
