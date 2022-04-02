package zad1;

import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class AppMenu {
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    int height = Toolkit.getDefaultToolkit().getScreenSize().height;

   private LineBorder blackline = (LineBorder) BorderFactory.createLineBorder(Color.black);

   private JTextField wordToTranslateField = new JTextField();
   private JTextField languageCodeField = new JTextField();
   private JTextField portNumberField = new JTextField();
   private JTextField newServerLanguageCodeField = new JTextField();
   private JTextField newServerDictionaryPath = new JTextField();
   private JButton translateButton = new JButton();
   private JButton createNewServer = new JButton();
   private JButton clear = new JButton();
   static JLabel responseLabel = new JLabel();

    public AppMenu() {
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setTitle("TPO2 Zad 1");
        frame.setLocation(width/4,height/4);
        frame.setSize(650,430);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        frame.add(wordToTranslateField);
        frame.add(languageCodeField);
        frame.add(portNumberField);
        frame.add(newServerLanguageCodeField);
        frame.add(translateButton);
        frame.add(createNewServer);
        frame.add(responseLabel);
        frame.add(newServerDictionaryPath);
        frame.add(clear);

        wordToTranslateField.setBounds(50,50,200,50);
        TitledBorder titleWord = BorderFactory.createTitledBorder(blackline,"word to translate");
        titleWord.setTitleJustification(TitledBorder.CENTER);
        wordToTranslateField.setBorder(titleWord);


        languageCodeField.setBounds(300,50,120,50);
        TitledBorder titleLanguageCode = BorderFactory.createTitledBorder(blackline,"language code");
        titleLanguageCode.setTitleJustification(TitledBorder.CENTER);
        languageCodeField.setBorder(titleLanguageCode);

        portNumberField.setBounds(470,50,120,50);
        TitledBorder titlePort = BorderFactory.createTitledBorder(blackline,"port number");
        titlePort.setTitleJustification(TitledBorder.CENTER);
        portNumberField.setBorder(titlePort);

        responseLabel.setBounds(50,120,200,50);
        responseLabel.setBackground(Color.WHITE);
        responseLabel.setOpaque(true);
        TitledBorder result = BorderFactory.createTitledBorder(blackline,"result of translation");
        result.setTitleJustification(TitledBorder.CENTER);
        responseLabel.setBorder(result);


        translateButton.setBounds(470,120,120,50);
        translateButton.setText("Translate");
        translateButton.addActionListener((ActionEvent e)->{
            String word = wordToTranslateField.getText();
            String code = languageCodeField.getText();
            int portNumber = Integer.valueOf(portNumberField.getText());
            Client client = new Client(portNumber,code,word);
            try {
                client.translate();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        clear.setBounds(470,220,120,50);
        clear.setText("Clear");
        clear.addActionListener((ActionEvent e)->{
            wordToTranslateField.setText("");
            languageCodeField.setText("");
            portNumberField.setText("");
            newServerLanguageCodeField.setText("");
            newServerDictionaryPath.setText("");
            responseLabel.setText("");
        });


        newServerLanguageCodeField.setBounds(50,300,120,50);
        newServerLanguageCodeField.setBorder(titleLanguageCode);


        newServerDictionaryPath.setBounds(180,300,280,50);
        TitledBorder titleServerPath = BorderFactory.createTitledBorder(blackline,"path to dictionary file");
        titleServerPath.setTitleJustification(TitledBorder.CENTER);
        newServerDictionaryPath.setBorder(titleServerPath);

        createNewServer.setBounds(470,300,120,50);
        createNewServer.setText("Create server");
        createNewServer.addActionListener((ActionEvent e)->{
            String code = newServerLanguageCodeField.getText();
            String path = newServerDictionaryPath.getText();
            Client client = new Client(code,path);
            try{
                client.createNewDictionaryServer();
            }catch (Exception exception){
                exception.printStackTrace();
            }
        });




    }
}
