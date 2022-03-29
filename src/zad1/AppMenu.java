package zad1;

import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AppMenu {
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    int height = Toolkit.getDefaultToolkit().getScreenSize().height;

    LineBorder blackline = (LineBorder) BorderFactory.createLineBorder(Color.black);

    JTextField wordToTranslateField = new JTextField();
    JTextField languageCodeField = new JTextField();
    JTextField portNumberField = new JTextField();
    JTextField newServerLanguageCodeField = new JTextField();
    JButton translateButton = new JButton();
    JButton createNewServer = new JButton();
    JLabel responseLabel = new JLabel();

    public AppMenu() {
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setTitle("TPO2 Zad 1");
        frame.setLocation(width/4,height/4);
        frame.setSize(width/2,height/2);
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

        wordToTranslateField.setBounds(50,50,400,50);
        TitledBorder titleWord = BorderFactory.createTitledBorder(blackline,"word to translate");
        titleWord.setTitleJustification(TitledBorder.CENTER);
        wordToTranslateField.setBorder(titleWord);

        languageCodeField.setBounds(500,50,120,50);
        TitledBorder titleLanguageCode = BorderFactory.createTitledBorder(blackline,"language code");
        titleLanguageCode.setTitleJustification(TitledBorder.CENTER);
        languageCodeField.setBorder(titleLanguageCode);

        portNumberField.setBounds(670,50,120,50);
        TitledBorder titlePort = BorderFactory.createTitledBorder(blackline,"port number");
        titlePort.setTitleJustification(TitledBorder.CENTER);
        portNumberField.setBorder(titlePort);

        responseLabel.setBounds(50,120,400,50);
        responseLabel.setBackground(Color.WHITE);
        responseLabel.setOpaque(true);
        TitledBorder result = BorderFactory.createTitledBorder(blackline,"result of translation");
        result.setTitleJustification(TitledBorder.CENTER);
        responseLabel.setBorder(result);


        translateButton.setBounds(850,120,120,50);
        translateButton.setText("Translate");
        translateButton.addActionListener((ActionEvent e)->{
            String word = wordToTranslateField.getText();
            String code = languageCodeField.getText();
            int portNumber = Integer.valueOf(portNumberField.getText());
            Client client = new Client(portNumber,code,word);


        });


        newServerLanguageCodeField.setBounds(50,300,120,50);
        newServerLanguageCodeField.setBorder(titleLanguageCode);

        createNewServer.setBounds(850,300,120,50);
        createNewServer.setText("Create server");
        createNewServer.addActionListener((ActionEvent e)->{
            String code = newServerLanguageCodeField.getText();
        });




    }
}
