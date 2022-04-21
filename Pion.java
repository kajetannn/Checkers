import javax.swing.*;
import java.awt.*;

public class Pion extends JButton{
    private RodzajPiona rodzaj;
    private int oldx;
    private int oldy;
    private boolean damka = false;
    public static Color br = new Color(255,222,173);
    static ImageIcon czarny1 = new ImageIcon("src/zestaw1/zestaw1czmaly.jpg");
    static ImageIcon bialy1 = new ImageIcon("src/zestaw1/zestaw1bmaly.jpg");
    static ImageIcon pusty1 = new ImageIcon("src/zestaw1/zestaw1puste.png");
    static ImageIcon pusty2 = new ImageIcon("src/zestaw2/zestaw2pustepole.png");
    static ImageIcon czerwony2 = new ImageIcon("src/zestaw2/pionek2czmaly.png");
    static ImageIcon bialy2 = new ImageIcon("src/zestaw2/pionek2bmaly.png");

    public RodzajPiona getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(RodzajPiona rodzaj){
        this.rodzaj = rodzaj;
    }

    public int getOldx() {
        return oldx;
    }

    public int getOldy() {
        return oldy;
    }

    public boolean isDamka() {
        return damka;
    }

    public void setDamka(boolean damka) {
        this.damka = damka;
    }

    public void ruch(int x, int y){
        oldx = x;
        oldy = y;
    }
    public Pion(Zestaw zestaw, RodzajPiona rodzaj ,int x, int y){
        this.rodzaj = rodzaj;
        ruch(x,y);
        if(zestaw == Zestaw.ZESTAWA){
            if(rodzaj == RodzajPiona.BIALY){
                this.setIcon(bialy1);
            }
            else if (rodzaj == RodzajPiona.PUSTY){
                this.setIcon(pusty1);
            }
            else if(rodzaj == RodzajPiona.NIEAKTYWNY) {
                this.setBackground(br);
                this.setEnabled(false);
            }
            else{
                this.setIcon(czarny1);
            }
        }
        if(zestaw == Zestaw.ZESTAWB){
            if(rodzaj == RodzajPiona.BIALY){
                this.setIcon(bialy2);
            }
            else if (rodzaj == RodzajPiona.PUSTY){
                this.setIcon(pusty2);
            }
            else if(rodzaj == RodzajPiona.NIEAKTYWNY) {
                this.setBackground(Color.white);
                this.setEnabled(false);
            }
            else{
                this.setIcon(czerwony2);
            }
        }

        }

    }

