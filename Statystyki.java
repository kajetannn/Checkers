import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Statystyki implements ActionListener {
    Semaphore semafor = new Semaphore(0);
    File file = new File("Statystyki.txt");
    String gracz;
    String[][] names =new String[50][2];
    JFrame frame= new JFrame();
    JPanel panel = new JPanel();
    JLabel label1= new JLabel("Najlepsza trójka:");
    JLabel[] labels =new JLabel[3];
    JButton button=new JButton("Powrót");
    Color tlo = new Color(205,133,63);
    int i=0;
    Statystyki(String username ) {
        for (int j = 0; j < 50; j++) {
            for (int k = 0; k < 2; k++) {
                names[j][k] = "0";
            }
        }
        gracz = username;
        try {
            Scanner read1 = new Scanner(file);
            while (read1.hasNextLine()) {
                String data = read1.nextLine();
                String[] dane = data.split(" ");
                names[i][0] = dane[0];
                names[i][1] = dane[1];
                i++;
            }
            read1.close();
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        for (int i = 0; i < 50; i++) {
            for (int k = i + 1; k < 50; k++) {
                if(names[k][0]!=null){
                    if (Integer.parseInt(names[i][1])<(Integer.parseInt(names[k][1]))) {
                        String temp = names[i][0];
                        String temp2 = names[i][1];
                        names[i][0] = names[k][0];
                        names[i][1] = names[k][1];
                        names[k][0] = temp;
                        names[k][1] = temp2;
                    }
                }}
        }
        frame.setBounds(0,0,500,500);
        panel.setBounds(0,0,500,500);
        frame.add(panel);
        panel.setLayout(null);
        panel.setBackground(tlo);
        frame.setLayout(null);
        frame.setVisible(true);
        label1.setBounds(150,100,100,20);
        panel.add(label1);
        JLabel player=new JLabel("Twój wynik:");
        player.setBounds(150,300,100,20);
        panel.add(player);
        for(int i=0;i<3;i++){
            labels[i]=new JLabel(names[i][0]+" "+ names[i][1]);
            labels[i].setBounds(150,130+i*30,100,20);
            panel.add(labels[i]);
        }
        int pom=-1;
        for(int i = 0; i< names.length; i++)
        {
            if(gracz.equals(names[i][0]))
            {
                pom=i;
            }
        }
        JLabel stats=new JLabel();
        if(-1 != pom){
            stats.setText(names[pom][0]+" "+ names[pom][1]);
        }
        stats.setBounds(150,330,200,20);
        panel.add(stats);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        button.setBounds(175,400,100,25);
        button.addActionListener(this);
        panel.add(button);

        try {
            semafor.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Wybor(gracz);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button){
            semafor.release();
            frame.dispose();
        }
    }
}
