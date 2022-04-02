package zad1.dictinaries;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Dictionary {
    private Map <String,String>dictionaryMap = new HashMap();
    private String fileName;

    public Dictionary(String fileName) {
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
}
