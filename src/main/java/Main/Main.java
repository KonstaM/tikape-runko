package Main;
import tikape.runko.database.Tietokanta;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Tietokanta tietokanta = new Tietokanta("jdbc:sqlite:tietokanta");
            Kayttoliittyma kayttoliittyma = new Kayttoliittyma(tietokanta);
            
            try {
                kayttoliittyma.kaynnista();
            } catch (SQLException e) {
                System.out.println(e);
            }
            
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }
}