import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;


public class Start extends JPanel implements ActionListener {
    Semaphore semafor = new Semaphore(0);
    int okno;
    JFrame frame = new JFrame("Start");
    JLabel userLabel = new JLabel("Login");
    JTextField userText = new JTextField(20);
    JLabel passwordLabel = new JLabel("Hasło");
    JPasswordField passwordText = new JPasswordField(20);
    JButton loginButton = new JButton("Zaloguj");
    JButton newuserButton = new JButton("Nie mam jeszcze konta");
    JButton gameButton = new JButton("Zagraj");
    JLabel glog = new JLabel("");
    Color tlo = new Color(205,133,63);
    JPanel panel = new JPanel();

    Start() {
        frame.setSize(330, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panel);
        panel.setLayout(null);
        panel.setBackground(tlo);

        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText.setBounds(100, 20, 180, 25);
        panel.add(userText);

        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        glog.setBounds(100, 115, 200, 30);
        panel.add(glog);

        passwordText.setBounds(100, 50, 180, 25);
        panel.add(passwordText);

        loginButton.setBounds(10, 80, 80, 25);
        loginButton.addActionListener(this);
        panel.add(loginButton);


        newuserButton.setBounds(100, 80, 180, 25);
        newuserButton.addActionListener(this);
        panel.add(newuserButton);


        gameButton.addActionListener(this);
        panel.add(gameButton);

        // Setting the frame visibility to true
        frame.setVisible(true);


        try {
            semafor.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (okno == 1) {
            new Rejestracja();
        }
        else if (okno == 2) {
            new Wybor(userText.getText());
        }

        }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            if(sprawdz(userText.getText(), String.valueOf(passwordText.getPassword()))) {
                glog.setText("Udało Ci się zalogować");
                gameButton.setBounds(10, 115, 80, 25);
            } else {
                glog.setText("Błąd logowania");
            }

        }
        else if (e.getSource() == newuserButton) {
            okno = 1;
            semafor.release();
            frame.dispose();
            }
        else if (e.getSource() == gameButton){
            okno = 2;
            semafor.release();
            frame.dispose();
        }
        }
    public boolean sprawdz(String s1, String s2) {
        String s = s1 + " " + s2;
        try {
            File file = new File("Loginy.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String temp = sc.nextLine();
                if (temp.equals(s)) {
                    sc.close();
                    return true;
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("Nie znaleziono pliku z loginami");
            e.printStackTrace();
        }
        return false;
    }
}