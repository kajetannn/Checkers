import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;

public class Wybor extends JFrame implements ActionListener{
    Semaphore semafor = new Semaphore(0);
    int b,p,g,gra,stat;
    Zestaw zestaw;
    JFrame frame = new JFrame("Wybierz tryb gry");
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JLabel plan1 = new JLabel("Plansza 1");
    JLabel plan2 = new JLabel("Plansza 2");
    JLabel nazwa = new JLabel();
    JLabel plan1o = new JLabel();
    JLabel plan2o = new JLabel();
    JButton gramy = new JButton("Graj");
    JButton statystyki = new JButton("Statystyki");
    JButton wyjscie = new JButton("Wyjscie");
    JRadioButton p1 = new JRadioButton();
    JRadioButton p2 = new JRadioButton();
    JRadioButton g1 = new JRadioButton();
    JRadioButton g2 = new JRadioButton();
    JRadioButton b1 = new JRadioButton();
    JRadioButton b2 = new JRadioButton();
    ButtonGroup gr = new ButtonGroup();
    ButtonGroup gr2 = new ButtonGroup();
    ButtonGroup gr3 = new ButtonGroup();
    ImageIcon plansza1 = new ImageIcon("src/zestaw1/plansza1miniatura.jpg");
    ImageIcon plansza2 = new ImageIcon("src/zestaw2/plansza2miniatura.png");
    Color tlo = new Color(205,133,63);

    Wybor(String username){
        frame.setSize(450, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel1.setBounds(0,0,450,199);
        panel2.setBounds(0,200,450,501);

        frame.add(panel1);
        panel1.setLayout(null);
        panel1.setBackground(tlo);

        frame.add(panel2);
        panel2.setLayout(null);
        panel2.setBackground(tlo);

        nazwa.setBounds(280,10,100,15);
        nazwa.setText("Witaj " + username + " !");
        panel1.add(nazwa);

        gramy.setBounds(280,40,130,25);
        gramy.addActionListener(this);
        statystyki.setBounds(280,80,130,25);
        statystyki.addActionListener(this);
        wyjscie.setBounds(280,120,130,25);
        wyjscie.addActionListener(this);
        panel1.add(gramy);
        panel1.add(statystyki);
        panel1.add(wyjscie);

        p1.setText("plansza 1 6x6");
        p2.setText("plansza 2 8x8");

        p1.setBounds(10,10,150,25);
        p2.setBounds(10,30,150,25);

        panel1.add(p1);
        panel1.add(p2);


        gr.add(p1);
        gr.add(p2);

        g1.setText("Gra z innym graczem");
        g2.setText("Gra z komputerem");

        g1.setBounds(10,70,150,25);
        g2.setBounds(10,90,150,25);

        panel1.add(g1);
        panel1.add(g2);

        gr2.add(g1);
        gr2.add(g2);

        b1.setText("Gra zwykła");
        b2.setText("Gra błyskawiczna");

        b1.setBounds(10,130,150,25);
        b2.setBounds(10,150,150,25);

        panel1.add(b1);
        panel1.add(b2);

        gr3.add(b1);
        gr3.add(b2);

        plan1.setBounds(90,200, 80, 20);
        plan2.setBounds(300,200,80,20);
        plan1o.setBounds(10,250,200,200);
        plan2o.setBounds(230,250,200,200);
        panel2.add(plan1);
        panel2.add(plan2);
        panel2.add(plan1o);
        panel2.add(plan2o);

        plan1o.setIcon(plansza1);
        plan2o.setIcon(plansza2);

        p1.addActionListener(this);
        p2.addActionListener(this);
        b1.addActionListener(this);
        b2.addActionListener(this);
        g1.addActionListener(this);
        g2.addActionListener(this);

        frame.setVisible(true);

        try {
            semafor.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (gra == 1){
            frame.dispose();
            new Plansza(zestaw, username, b, g);
        }
        else if (stat == 1){
            frame.dispose();
            new Statystyki(username);
        }
        else{
            frame.dispose();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == p1) {
            p = 1;
            zestaw = Zestaw.ZESTAWA;

        }
        if (e.getSource() == p2) {
            p = 2;
            zestaw = Zestaw.ZESTAWB;
        }
        if (e.getSource() == g1) {
            g = 1;
        }
        if (e.getSource() == g2) {
            g = 2;
        }
        if (e.getSource() == b1) {
            b = 1;
        }
        if (e.getSource() == b2) {
            b = 2;
        }
        if (e.getSource() == gramy) {
            gra = 1;
            semafor.release();
        }
        if (e.getSource() == statystyki) {
            stat = 1;
            semafor.release();
        }
        if (e.getSource() == wyjscie){
            semafor.release();
        }
    }
}