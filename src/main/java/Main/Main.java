package Main;
import tikape.runko.database.Tietokanta;

public class Main {
    public static void main(String[] args) {
        try {
            Tietokanta tietokanta = new Tietokanta("jdbc:sqlite:tietokanta");
            Kayttoliittyma kayttoliittyma = new Kayttoliittyma(tietokanta);
            kayttoliittyma.kaynnista();
            
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }
}