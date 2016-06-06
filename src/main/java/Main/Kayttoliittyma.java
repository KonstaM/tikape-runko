package Main;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.database.*;
import tikape.runko.domain.*;

public class Kayttoliittyma {
    private Tietokanta tietokanta;
    
    public Kayttoliittyma(Tietokanta tietokanta) {
        this.tietokanta = tietokanta;
    }
    
    public void kaynnista() {
        
        try {
            tietokanta.createDatabase();
        } catch (SQLException e) {
            System.out.println("Error creating database");
            System.out.println(e);
            System.out.println("---");
        }
        
        testi();
    }
    
    public void testi() {
        AlueDao dao = new AlueDao(tietokanta);
        Alue alue1 = new Alue(1, "ASD");
        Alue alue2 = new Alue(2, "WASD");
        Alue alue3 = new Alue(3, "QWASD");
        try {
            dao.create(alue1);
            dao.create(alue2);
            dao.create(alue3);
            
            ArrayList<Integer> keys = new ArrayList<>();
            
            for (Alue alue : dao.findAll()) {
                keys.add(alue.getId());
            }
            
            List<Alue> alueet = dao.findAllIn(keys);
            
            for (Alue alue : alueet) {
                System.out.println(alue.getId());
                System.out.println(alue.getNimi());
                System.out.println("-------");
            }
            
            dao.delete(1);
            dao.delete(2);
            dao.delete(3);
        } catch(SQLException e) {
            System.out.println(e);
        }
        
    }
}