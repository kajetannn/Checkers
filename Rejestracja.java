import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Rejestracja implements ActionListener {
    Semaphore semafor = new Semaphore(0);
    int okno;
    JFrame frame = new JFrame("Rejestracja");
    JPanel panel = new JPanel();
    JLabel userLabel = new JLabel("Login");
    JTextField userText = new JTextField(20);
    JLabel passwordLabel = new JLabel("Hasło");
    JPasswordField passwordText = new JPasswordField(20);
    JButton newuserButton = new JButton("Utwórz konto");
    JButton goback = new JButton("Powrót");
    JLabel glog = new JLabel("");
    Color tlo = new Color(205,133,63);

    Rejestracja(){

        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.add(panel);
        panel.setLayout(null);
        panel.setBackground(tlo);


        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);



        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);



        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);


        glog.setBounds(10, 110, 300, 30);
        panel.add(glog);


        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        newuserButton.setBounds(100, 80, 150, 25);
        newuserButton.addActionListener(this);
        panel.add(newuserButton);

        goback.setBounds(100, 115, 150, 25);
        goback.addActionListener(this);
        panel.add(goback);

        // Setting the frame visibility to true
        frame.setVisible(true);

        try {
            semafor.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (okno == 0){
            new Start();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newuserButton) {
            if(!czywolne(userText.getText())) {
                glog.setText("Login zajęty");
            } else {
                zapiszlogin(userText.getText(), String.valueOf(passwordText.getPassword()));
                zapiszstatystyki(userText.getText(), "0");
                glog.setText("Utworzono");
            }

        }
        else{
            frame.dispose();
            semafor.release();
        }
    }
    public boolean czywolne(String s1) {
        try {
            File file = new File("Loginy.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String temp = sc.nextLine();
                String[] log = temp.split(" ");
                if (log[0].equals(s1)) {
                    sc.close();
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku z loginami");
            e.printStackTrace();
        }
    return true;}
    public void zapiszlogin(String l, String p){
        String s = l+ " " + p;
        try{
            File file = new File("Loginy.txt");
            FileWriter wr = new FileWriter(file,true);
            s = s + "\n";
            wr.write(s);
            wr.close();
            glog.setText("Rejestracja pomyślna");
        } catch (IOException e){
            System.out.println("Nieudany zapis");
            e.printStackTrace();
        }
    }
    public void zapiszstatystyki(String l, String p){
        String s = l+ " " + p;
        try{
            File file = new File("Statystyki.txt");
            FileWriter wr = new FileWriter(file,true);
            s = s + "\n";
            wr.write(s);
            wr.close();
        } catch (IOException e){
            System.out.println("Nieudany zapis");
            e.printStackTrace();
        }
    }
}


