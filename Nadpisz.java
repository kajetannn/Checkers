import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Nadpisz {
    File plik = new File("Statystyki.txt");
    File plik1 = new File("Pomoc.txt");
    String gracz;
    Nadpisz(String username ){
        gracz =username;
        try {
            Scanner read1= new Scanner(plik);
            FileWriter writer1=new FileWriter(plik1,true);
            String var1;
            while(read1.hasNextLine()){
                String data=read1.nextLine();
                String[] dane=data.split(" ");
                int var;
                if(dane[0].equals(gracz)){
                    var=Integer.parseInt(dane[1]);
                    var++;
                    var1=dane[0]+" "+var + "\n";
                }
                else {
                    var1= data + "\n";
                }
                writer1.write(var1);
            }
            read1.close();
            writer1.close();
            FileWriter writer = new FileWriter(plik);
            writer.write("");
            writer.close();
        }
        catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        try {
            Scanner read2= new Scanner(plik1);
            FileWriter writer2= new FileWriter(plik);
            String data;
            while(read2.hasNextLine()) {
                data= read2.nextLine();
                data=data+"\n";
                writer2.write(data);
            }
            read2.close();
            writer2.close();
            FileWriter writer = new FileWriter(plik1);
            writer.write("");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

