import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.Semaphore;

import static java.lang.Math.abs;

public class Plansza extends JFrame implements ActionListener {
    private Random rand = new Random();
    Semaphore semafor = new Semaphore(0);
    public boolean bicie;
    public int rozmiar;
    public int ile; //ilosc rzedow, na ktorych ustawione sa  piony
    public int actioncount = 0;
    public int x1, y1, x2, y2;
    public Pion[][] matrix;
    public Pion p1, p2;
    public Color kolor2; //kolor pol nieaktywnych
    public ImageIcon pionb, pioncz, kolor1, dbialy, dcz;
    public int inne, biale; //ilosc pionow
    public int czasnaruch = 0; //dla gry blyskawicznej
    public int tura = 1;
    public int komputer; //czy gra z komputerem
    public Timer glowny = new Timer(1000, new updateClockAction());
    public Timer blyskawiczny = new Timer(1000, new graBlyskawiczna());
    public ImageIcon czarny1 = new ImageIcon("src/zestaw1/zestaw1czmaly.jpg");
    public ImageIcon bialy1 = new ImageIcon("src/zestaw1/zestaw1bmaly.jpg");
    public ImageIcon pusty1 = new ImageIcon("src/zestaw1/zestaw1puste.png");
    public ImageIcon pusty2 = new ImageIcon("src/zestaw2/zestaw2pustepole.png");
    public ImageIcon czerwony2 = new ImageIcon("src/zestaw2/pionek2czmaly.png");
    public ImageIcon bialy2 = new ImageIcon("src/zestaw2/pionek2bmaly.png");
    public ImageIcon czerwony = new ImageIcon("src/zestaw2/czerwony.png");
    public ImageIcon bialyd2 = new ImageIcon("src/zestaw2/zestaw1bdmala.png");
    public ImageIcon czerwonyd2 = new ImageIcon("src/zestaw2/zestaw2czdmala.png");
    public ImageIcon bialyd1 = new ImageIcon("src/zestaw1/zestaw2bdmala.png");
    public ImageIcon czarnyd1 = new ImageIcon("src/zestaw1/zestaw1czdmala.png");
    public static int rozmiarA = 100;
    public static int rozmiarB = 90;
    private final int kolumnywiersze;
    public static Color brazowy = new Color(255, 222, 173);
    public static Color bialy = new Color(255, 255, 255);
    public static Color tlo = new Color(205,133,63);
    public JFrame frame = new JFrame("Warcaby");
    public JPanel panel1 = new JPanel();
    public JPanel panel2 = new JPanel();
    public JLabel koniecgry = new JLabel();
    public JLabel czyjatura = new JLabel();
    public JButton powrot = new JButton("Powrót");
    public String sekundy = "0";
    public int minuty = 0;
    public JLabel czas = new JLabel("Czas gry: 0 : 00");
    public JLabel pozostalyczas = new JLabel("Czas na ruch: 10");
    public String username;

    public class updateClockAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int sec;
            sec = Integer.parseInt(sekundy);
            sec++;
            if (sec < 10){
                sekundy = "0" + sec;
                czas.setText("Czas gry: " + minuty + " : " + sekundy);
            }
            else if (sec < 60){
                sekundy = String.valueOf(sec);
                czas.setText("Czas gry: " + minuty + " : " + sekundy);
            }
            else if (sec == 60){
                sekundy = "0";
                minuty++;
                czas.setText("Czas gry: " + minuty + " : " + sekundy);
            }

        }
    }

    public class graBlyskawiczna implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            czasnaruch++;
            pozostalyczas.setText("Czas na ruch: " + (10-czasnaruch));
            if (czasnaruch > 9) {
                if (tura != 1) {
                    inne = 0;
                } else {
                    biale = 0;
                }
                czywygrana(biale, inne);
                blyskawiczny.stop();
            }
        }
    }

    public void update(Pion[][] matrix) {
        for (int i = 0; i < kolumnywiersze; i++) {
            for (int j = 0; j < kolumnywiersze; j++) {
                if ((i + j) % 2 == 1) {
                    if (matrix[i][j].getRodzaj() == RodzajPiona.BIALY) {
                        if (matrix[i][j].isDamka()) {
                            matrix[i][j].setIcon(dbialy);
                        } else {
                            matrix[i][j].setIcon(pionb);
                        }
                    } else if (matrix[i][j].getRodzaj() == RodzajPiona.INNY) {
                        if (matrix[i][j].isDamka()) {
                            matrix[i][j].setIcon(dcz);
                        } else {
                            matrix[i][j].setIcon(pioncz);
                        }
                    } else if (matrix[i][j].getRodzaj() == RodzajPiona.PUSTY) {
                        matrix[i][j].setIcon(kolor1);
                    }
                }
                matrix[i][j].setBounds(rozmiar * j, rozmiar * i, rozmiar, rozmiar);
            }
        }
        System.out.println("Zaktualizowalem");
    }

    public void ktorelegalne(Pion p, Pion[][] matrix) {
        int x = p.getOldx();
        int y = p.getOldy();
        int kierunek = p.getRodzaj().kierunekRuchu;
        int rzad, koll, kolp;
        rzad = x + kierunek;
        koll = y - 1;
        kolp = y + 1;
        for (int i = 0; i < kolumnywiersze; i++) {
            for (int j = 0; j < kolumnywiersze; j++) {
                if ((i + j) % 2 == 1) {
                    matrix[i][j].setEnabled(false);
                    if (matrix[i][j].getRodzaj() == RodzajPiona.BIALY) {
                        if (matrix[i][j].isDamka()) {
                            matrix[i][j].setDisabledIcon(dbialy);
                        } else {
                            matrix[i][j].setDisabledIcon(pionb);
                        }
                    } else if (matrix[i][j].getRodzaj() == RodzajPiona.INNY) {
                        if (matrix[i][j].isDamka()) {
                            matrix[i][j].setDisabledIcon(dcz);
                        } else {
                            matrix[i][j].setDisabledIcon(pioncz);
                        }
                    } else if (matrix[i][j].getRodzaj() == RodzajPiona.PUSTY) {
                        matrix[i][j].setDisabledIcon(kolor1);
                    }
                }
                matrix[i][j].setBounds(rozmiar * j, rozmiar * i, rozmiar, rozmiar);
            }
        }

        if (p.isDamka()) {
            if ((x + 1 < kolumnywiersze) && (x - 1 > -1)) {
                if (y > 0 && matrix[x + 1][koll].getRodzaj() == RodzajPiona.PUSTY) {
                    matrix[x + 1][koll].setIcon(czerwony);
                    matrix[x + 1][koll].setEnabled(true);
                }
                if (y > 0 && matrix[x - 1][koll].getRodzaj() == RodzajPiona.PUSTY) {
                    matrix[x - 1][koll].setIcon(czerwony);
                    matrix[x - 1][koll].setEnabled(true);
                }
                if (y + 1 < kolumnywiersze && matrix[x + 1][kolp].getRodzaj() == RodzajPiona.PUSTY) {
                    matrix[x + 1][kolp].setIcon(czerwony);
                    matrix[x + 1][kolp].setEnabled(true);
                }
                if (y + 1 < kolumnywiersze && matrix[x - 1][kolp].getRodzaj() == RodzajPiona.PUSTY) {
                    matrix[x - 1][kolp].setIcon(czerwony);
                    matrix[x - 1][kolp].setEnabled(true);
                }
                if ((x + 2) < kolumnywiersze ) {
                    if (koll - 1 > -1) {
                        if (matrix[x + 1][koll].getRodzaj().kierunekRuchu == -kierunek && matrix[x + 2][koll - 1].getRodzaj() == RodzajPiona.PUSTY) {
                            matrix[x + 2][koll - 1].setIcon(czerwony);
                            matrix[x + 2][koll - 1].setEnabled(true);
                        }
                    }
                    if (kolp + 1 < kolumnywiersze) {
                        if (matrix[x + 1][kolp].getRodzaj().kierunekRuchu == -kierunek && matrix[x + 2][kolp + 1].getRodzaj() == RodzajPiona.PUSTY) {
                            matrix[x + 2][kolp + 1].setIcon(czerwony);
                            matrix[x + 2][kolp + 1].setEnabled(true);
                        }
                    }
                }
                if ((x - 2) > -1){
                    if (koll - 1 > -1) {
                        if (matrix[x - 1][koll].getRodzaj().kierunekRuchu == -kierunek && matrix[x - 2][koll - 1].getRodzaj() == RodzajPiona.PUSTY) {
                            matrix[x - 2][koll - 1].setIcon(czerwony);
                            matrix[x - 2][koll - 1].setEnabled(true);
                        }
                    }
                    if (kolp + 1 < kolumnywiersze) {
                        if (matrix[x - 1][kolp].getRodzaj().kierunekRuchu == -kierunek && matrix[x - 2][kolp + 1].getRodzaj() == RodzajPiona.PUSTY) {
                            matrix[x - 2][kolp + 1].setIcon(czerwony);
                            matrix[x - 2][kolp + 1].setEnabled(true);
                        }
                    }
                }
            }
            else if (x == kolumnywiersze-1) {
                if (y > 0 && matrix[x - 1][koll].getRodzaj() == RodzajPiona.PUSTY) {
                    matrix[x - 1][koll].setIcon(czerwony);
                    matrix[x - 1][koll].setEnabled(true);
                }
                if (y + 1 < kolumnywiersze && matrix[x - 1][kolp].getRodzaj() == RodzajPiona.PUSTY) {
                    matrix[x - 1][kolp].setIcon(czerwony);
                    matrix[x - 1][kolp].setEnabled(true);
                }
                if (koll - 1 > -1) {
                    if (matrix[x - 1][koll].getRodzaj().kierunekRuchu == -kierunek && matrix[x - 2][koll - 1].getRodzaj() == RodzajPiona.PUSTY) {
                        matrix[x - 2][koll - 1].setIcon(czerwony);
                        matrix[x - 2][koll - 1].setEnabled(true);
                    }
                }
                if (kolp + 1 < kolumnywiersze) {
                    if (matrix[x - 1][kolp].getRodzaj().kierunekRuchu == -kierunek && matrix[x - 2][kolp + 1].getRodzaj() == RodzajPiona.PUSTY) {
                        matrix[x - 2][kolp + 1].setIcon(czerwony);
                        matrix[x - 2][kolp + 1].setEnabled(true);
                    }
                }
            }
            else if (x == 0) {
                if (y > 0 && matrix[x + 1][koll].getRodzaj() == RodzajPiona.PUSTY) {
                    matrix[x + 1][koll].setIcon(czerwony);
                    matrix[x + 1][koll].setEnabled(true);
                }
                if (kolp < kolumnywiersze && matrix[x + 1][kolp].getRodzaj() == RodzajPiona.PUSTY) {
                    matrix[x + 1][kolp].setIcon(czerwony);
                    matrix[x + 1][kolp].setEnabled(true);
                }
                if (koll - 1 > -1) {
                    if (matrix[x + 1][koll].getRodzaj().kierunekRuchu == -kierunek && matrix[x + 2][koll - 1].getRodzaj() == RodzajPiona.PUSTY) {
                        matrix[x + 2][koll - 1].setIcon(czerwony);
                        matrix[x + 2][koll - 1].setEnabled(true);
                    }
                }
                if (kolp + 1 < kolumnywiersze) {
                    if (matrix[x + 1][kolp].getRodzaj().kierunekRuchu == -kierunek && matrix[x + 2][kolp + 1].getRodzaj() == RodzajPiona.PUSTY) {
                        matrix[x + 2][kolp + 1].setIcon(czerwony);
                        matrix[x + 2][kolp + 1].setEnabled(true);
                    }
                }

            }
        }
        else {
            if (rzad + kierunek < kolumnywiersze && rzad + kierunek > -1) {
                if (koll - 1 > -1) {
                    if (matrix[rzad][koll].getRodzaj().kierunekRuchu == -kierunek && matrix[rzad + kierunek][koll - 1].getRodzaj() == RodzajPiona.PUSTY) {
                        matrix[rzad + kierunek][koll - 1].setIcon(czerwony);
                        matrix[rzad + kierunek][koll - 1].setEnabled(true);
                    }
                }
                if (kolp + 1 < kolumnywiersze) {
                    if (kolp + 1 < kolumnywiersze && matrix[rzad][kolp].getRodzaj().kierunekRuchu == -kierunek && matrix[rzad + kierunek][kolp + 1].getRodzaj() == RodzajPiona.PUSTY) {
                        matrix[rzad + kierunek][kolp + 1].setIcon(czerwony);
                        matrix[rzad + kierunek][kolp + 1].setEnabled(true);
                    }
                }
            }
            if (rzad < kolumnywiersze && rzad > -1) {
                if (y > 0 && matrix[rzad][koll].getRodzaj() == RodzajPiona.PUSTY) {
                    matrix[rzad][koll].setIcon(czerwony);
                    matrix[rzad][koll].setEnabled(true);
                }
                if (y + 1 < kolumnywiersze && matrix[rzad][kolp].getRodzaj() == RodzajPiona.PUSTY) {
                    matrix[rzad][kolp].setIcon(czerwony);
                    matrix[rzad][kolp].setEnabled(true);
                }
            }
        }
    }



    public boolean mozesieruszyc(Pion p, Pion[][] matrix) {
        int x = p.getOldx();
        int y = p.getOldy();
        int kierunek = p.getRodzaj().kierunekRuchu;
        int rzad, koll, kolp;
        rzad = x + kierunek;
        koll = y - 1;
        kolp = y + 1;
        if (p.isDamka()) {
            if ((x + 1 < kolumnywiersze) && (x - 1 > -1)) {
                if (y > 0 && matrix[x + 1][koll].getRodzaj() == RodzajPiona.PUSTY) {
                    return true;
                }
                if (y > 0 && matrix[x - 1][koll].getRodzaj() == RodzajPiona.PUSTY) {
                    return true;
                }
                if (y + 1 < kolumnywiersze && matrix[x + 1][kolp].getRodzaj() == RodzajPiona.PUSTY) {
                    return true;
                }
                if (y + 1 < kolumnywiersze && matrix[x - 1][kolp].getRodzaj() == RodzajPiona.PUSTY) {
                    return true;
                }
                if ((x + 2) < kolumnywiersze) {
                    if (koll - 1 > -1) {
                        if (matrix[x + 1][koll].getRodzaj().kierunekRuchu == -kierunek && matrix[x + 2][koll - 1].getRodzaj() == RodzajPiona.PUSTY) {
                            return true;
                        }
                        if (kolp + 1 < kolumnywiersze) {
                            if (matrix[x + 1][kolp].getRodzaj().kierunekRuchu == -kierunek && matrix[x + 2][kolp + 1].getRodzaj() == RodzajPiona.PUSTY) {
                                return true;
                            }
                        }
                    }
                }
                if ((x - 2) < kolumnywiersze) {
                    if (koll - 1 > -1) {
                        if (matrix[x - 1][koll].getRodzaj().kierunekRuchu == -kierunek && matrix[x - 2][koll - 1].getRodzaj() == RodzajPiona.PUSTY) {
                            return true;
                        }
                        if (kolp + 1 < kolumnywiersze) {
                            return matrix[x - 1][kolp].getRodzaj().kierunekRuchu == -kierunek && matrix[x - 2][kolp + 1].getRodzaj() == RodzajPiona.PUSTY;
                        }
                    }
                }
                return false;
            }

        else if (x == kolumnywiersze - 1) {
                if (y > 0 && matrix[x - 1][koll].getRodzaj() == RodzajPiona.PUSTY) {
                    return true;
                }
                if (y + 1 < kolumnywiersze && matrix[x - 1][kolp].getRodzaj() == RodzajPiona.PUSTY) {
                    return true;
                }
                if (koll - 1 > -1) {
                    if (matrix[x - 1][koll].getRodzaj().kierunekRuchu == -kierunek && matrix[x - 2][koll - 1].getRodzaj() == RodzajPiona.PUSTY) {
                        return true;
                    }
                }
                if (kolp + 1 < kolumnywiersze) {
                    return matrix[x - 1][kolp].getRodzaj().kierunekRuchu == -kierunek && matrix[x - 2][kolp + 1].getRodzaj() == RodzajPiona.PUSTY;
                }
                return false;
            }
        else if (x == 0) {
                if (y > 0 && matrix[x + 1][koll].getRodzaj() == RodzajPiona.PUSTY) {
                    return true;
                }
                if (kolp < kolumnywiersze && matrix[x + 1][kolp].getRodzaj() == RodzajPiona.PUSTY) {
                    return true;
                }
                if (koll - 1 > -1) {
                    if (matrix[x + 1][koll].getRodzaj().kierunekRuchu == -kierunek && matrix[x + 2][koll - 1].getRodzaj() == RodzajPiona.PUSTY) {
                        return true;
                    }
                }
                if (kolp + 1 < kolumnywiersze) {
                    return matrix[x + 1][kolp].getRodzaj().kierunekRuchu == -kierunek && matrix[x + 2][kolp + 1].getRodzaj() == RodzajPiona.PUSTY;
                }
                return false;
            }
        }
        else {
            if ((rzad + kierunek < kolumnywiersze) && (rzad + kierunek > -1)) {
                if (koll - 1 > -1) {
                    if (matrix[rzad][koll].getRodzaj().kierunekRuchu == -kierunek && matrix[rzad + kierunek][koll - 1].getRodzaj() == RodzajPiona.PUSTY) {
                        return true;
                    }
                }
                if (kolp + 1 < kolumnywiersze) {
                    if (matrix[rzad][kolp].getRodzaj().kierunekRuchu == -kierunek && matrix[rzad + kierunek][kolp + 1].getRodzaj() == RodzajPiona.PUSTY) {
                        return true;
                    }
                }
                if (y > 0 && matrix[rzad][koll].getRodzaj() == RodzajPiona.PUSTY) {
                    return true;
                }
                return y + 1 < kolumnywiersze && matrix[rzad][kolp].getRodzaj() == RodzajPiona.PUSTY;
            }
            else if ((rzad < kolumnywiersze) && (rzad > -1)) {
                if (y > 0 && matrix[rzad][koll].getRodzaj() == RodzajPiona.PUSTY) {
                    return true;
                }
                return y + 1 < kolumnywiersze && matrix[rzad][kolp].getRodzaj() == RodzajPiona.PUSTY;
            }
            return false;
        }
        return false;
    }

    public void przywroc(Pion[][] matrix){
        for (int i = 0; i < kolumnywiersze; i++) {
            for (int j = 0; j < kolumnywiersze; j++) {
                if ((i + j) % 2 == 1) {
                    matrix[i][j].setEnabled(true);
                }
            }
        }
    }

    public boolean czyzbito(int x1, int y1, int x2, int y2){
        return abs(x2 - x1) == 2 && abs(y1 - y2) == 2;
    }

    public boolean czydobrykolor(Pion p, int gracz){
        if (gracz == 1){
            return p.getRodzaj() == RodzajPiona.BIALY;
        }
        else{
            return p.getRodzaj() == RodzajPiona.INNY;
        }
    }

    public void czywygrana(int biale, int inne){
        if (biale == 0){
            koniecgry.setBounds(rozmiar*kolumnywiersze + 20,100,140,25);
            koniecgry.setText("Wygrywa Gość!");
            czyjatura.setText("");
            powrot.setBounds(rozmiar*kolumnywiersze + 20,400,140,25);
            powrot.addActionListener(this);
            glowny.stop();
        }
        else if(inne == 0){
            koniecgry.setBounds(rozmiar*kolumnywiersze + 20,100,140,25);
            koniecgry.setText("Wygrywa " + username + " !");
            czyjatura.setText("");
            powrot.setBounds(rozmiar*kolumnywiersze + 20,400,140,25);
            powrot.addActionListener(this);
            glowny.stop();
        }
    }

    public void damka(Pion p){
        if (p.getRodzaj() == RodzajPiona.BIALY){
            if (p.getOldx() == kolumnywiersze-1){
                p.setDamka(true);
            }
        }
        if (p.getRodzaj() == RodzajPiona.INNY){
            if (p.getOldx() == 0){
                p.setDamka(true);
            }
        }
    }

    public void setTura(int tura){
        if (tura == 1){
            czyjatura.setText("Tura gracza: " + username);
        }
        else{
            czyjatura.setText("Tura gracza: Gość");
        }
    }

    public void posuniecie(Pion p1, Pion p2){
        przywroc(matrix);
        actioncount = actioncount + 1;
        x1 = p1.getOldx();
        y1 = p1.getOldy();
        x2 = p2.getOldx();
        y2 = p2.getOldy();
        p1.ruch(x2,y2);
        p2.ruch(x1,y1);
        matrix[x2][y2] = p1;
        matrix[x1][y1] = p2;
        bicie = czyzbito(x1,y1,x2,y2);
        if (bicie){
            if(matrix[(x1+x2)/2][(y1+y2)/2].getRodzaj() == RodzajPiona.BIALY){
                biale = biale-1;
            }
            else{
                inne = inne-1;
            }
            matrix[(x1+x2)/2][(y1+y2)/2].setRodzaj(RodzajPiona.PUSTY);
        }
        update(matrix);
        czasnaruch = 0;
        tura = -tura;
        setTura(tura);
        damka(p1);
        update(matrix);
        czywygrana(biale,inne);
    }

    public void ruchkomputera(Pion[][] matrix){
        int iterator=0;
        int r,r2;
        Pion[] mogasieruszyc = new Pion[kolumnywiersze*kolumnywiersze];
        Pion[] mozliwepola = new Pion[4];
        for (int i = 0; i < kolumnywiersze; i++) {
            for (int j = 0; j < kolumnywiersze; j++) {
                if ((i + j) % 2 == 1) {
                    if (czydobrykolor(matrix[i][j],tura)){
                        if (mozesieruszyc(matrix[i][j], matrix)) {
                            mogasieruszyc[iterator] = matrix[i][j];
                            iterator++;
                        }
                    }
                }
            }
        }
        r = rand.nextInt(iterator);
        ktorelegalne(mogasieruszyc[r],matrix);
        iterator = 0;
        for (int i = 0; i < kolumnywiersze; i++) {
            for (int j = 0; j < kolumnywiersze; j++) {
                if ((i + j) % 2 == 1) {
                    if (matrix[i][j].isEnabled()){
                        mozliwepola[iterator] = matrix[i][j];
                        iterator++;
                    }
                }
            }
        }
        r2 = rand.nextInt(iterator);
        System.out.println(r + " " + r2);
        posuniecie(mogasieruszyc[r],mozliwepola[r2]);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        przywroc(matrix);
        update(matrix);
    }

    Plansza(Zestaw zestaw, String username, int czyblyskawiczna, int komputer) {
        this.username = username;
        this.komputer = komputer;
        glowny.start();
        if (czyblyskawiczna == 2) {
            blyskawiczny.start();
        }
        if (zestaw == Zestaw.ZESTAWA) {
            ile = 2;
            rozmiar = rozmiarA;
            kolor1 = pusty1;
            kolor2 = brazowy;
            pionb = bialy1;
            pioncz = czarny1;
            dbialy = bialyd1;
            dcz = czarnyd1;
            kolumnywiersze = 6;
            biale = 6;
            inne = 6;

        } else {
            ile = 3;
            rozmiar = rozmiarB;
            kolor1 = pusty2;
            kolor2 = bialy;
            pionb = bialy2;
            pioncz = czerwony2;
            dbialy = bialyd2;
            dcz = czerwonyd2;
            kolumnywiersze = 8;
            biale = 12;
            inne = 12;
        }
        matrix = new Pion[kolumnywiersze][kolumnywiersze];


        frame.setSize(rozmiar*kolumnywiersze + 200, rozmiar*kolumnywiersze+35);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel1.setBounds(0, 0, rozmiar * kolumnywiersze, rozmiar * kolumnywiersze);
        panel2.setBounds(rozmiar*kolumnywiersze, 0, (100 - rozmiar * kolumnywiersze), rozmiar * kolumnywiersze);
        frame.add(panel1);
        frame.add(panel2);
        panel1.setLayout(null);
        panel2.setLayout(null);
        panel2.setBackground(tlo);
        panel2.add(koniecgry);
        czyjatura.setBounds(rozmiar*kolumnywiersze + 50,150,140,25);
        czas.setBounds(rozmiar*kolumnywiersze + 50,190,140,25);
        panel2.add(czyjatura);
        panel2.add(czas);
        if (czyblyskawiczna != 1){
            pozostalyczas.setBounds(rozmiar*kolumnywiersze + 50,230,140,25);
            panel2.add(pozostalyczas);
        }
        panel2.add(powrot);
        for (int i = 0; i < kolumnywiersze; i++) {
            for (int j = 0; j < kolumnywiersze; j++) {
                if ((i + j) % 2 == 1) {
                    if (i < ile) {
                        matrix[i][j] = new Pion(zestaw, RodzajPiona.BIALY, i, j);
                    } else if (i > ile + 1) {
                        matrix[i][j] = new Pion(zestaw, RodzajPiona.INNY, i, j);
                    } else {
                        matrix[i][j] = new Pion(zestaw, RodzajPiona.PUSTY, i, j);
                    }
                } else {
                    matrix[i][j] = new Pion(zestaw, RodzajPiona.NIEAKTYWNY, i, j);
                }
                matrix[i][j].setBounds(rozmiar*j,rozmiar*i,rozmiar,rozmiar);
                matrix[i][j].addActionListener(this);
                panel1.add(matrix[i][j]);
            }
        }
        frame.setVisible(true);
        setTura(tura);
        try {
            semafor.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Wybor(username);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == powrot){
            semafor.release();
            if (biale != 0) {
                new Nadpisz(username);
            }
            frame.dispose();
        }
        if(actioncount % 2 == 0) {
            p1 = (Pion) e.getSource();
            if (czydobrykolor(p1, tura)) {
                if (mozesieruszyc(p1, matrix)) {
                    actioncount++;
                    ktorelegalne(p1, matrix);
                }
            }
        }
        else{
            p2 = (Pion) e.getSource();
            posuniecie(p1, p2);
            if (komputer != 1) {
                ruchkomputera(matrix);
                actioncount++;
            }
        }
    }
}
