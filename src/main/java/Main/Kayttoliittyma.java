// Tällä hetkellä käyttöliittymä on lähinnä testaamista varten



package Main;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.database.*;
import java.util.Scanner;
import tikape.runko.domain.*;

public class Kayttoliittyma {
    private Tietokanta tietokanta;
    private Scanner lukija;
    private ViestiDao viestiDao;
    private KeskusteluDao keskusteluDao;
    private AlueDao alueDao;
    private Kalenteri kalenteri;
    
    public Kayttoliittyma(Tietokanta tietokanta) {
        this.tietokanta = tietokanta;
        this.lukija = new Scanner(System.in);
        this.viestiDao = new ViestiDao(tietokanta);
        this.keskusteluDao = new KeskusteluDao(tietokanta, viestiDao);
        this.alueDao = new AlueDao(tietokanta, keskusteluDao);
        this.kalenteri = new Kalenteri();
    }
    
    public void kaynnista() throws SQLException {
        
        try {
            tietokanta.createDatabase();
        } catch (SQLException e) {
            System.out.println("Error creating database");
            System.out.println(e);
            System.out.println("---");
        }
        
        boolean jatka = true;
        
        while (jatka) {
            System.out.print("Uusi viesti (u), tulosta (t) tai lopeta (l): ");
            String komento = lukija.nextLine();
            
            if (komento.equals("l")) {
                jatka = false;
            } else if (komento.equals("u")) {
                kysele();
            } else if (komento.equals("t")) {
                tulosta();
            }
        }
        
        
    }
    
    public void kysele() throws SQLException {
        // Esimerkki uuden viestin kirjoittamisesta
        
        // Kysy käyttäjältä alue, keskustelu, nimimerkki ja viestin sisältö
        System.out.print("Anna alue: ");
        String alueenNimi = lukija.nextLine();
        System.out.print("Anna keskustelu: ");
        String keskustelunNimi = lukija.nextLine();
        System.out.print("Anna nimimerkki: ");
        String nimimerkki = lukija.nextLine();
        System.out.print("Kirjoita viesti: ");
        String sisalto = lukija.nextLine();
        
        // Tarkista, onko keskustelu ja/tai alue jo olemassa
        if (!alueDao.exists(alueenNimi)) {
            alueDao.create(new Alue(alueDao.nextId(), alueenNimi));
        }
        
        if (!keskusteluDao.exists(keskustelunNimi)) {
            keskusteluDao.create(new Keskustelu(keskusteluDao.nextId(), keskustelunNimi, alueDao.findOne(alueenNimi).getId()));
        }
        
        Alue alue = alueDao.findOne(alueenNimi);    // Luodaan alue- ja keskusteluoliot, sekä lisätään niihin
        alueDao.lisaaKeskustelut(alue);             // oikeat viestit ja keskustelut.
        Keskustelu keskustelu = keskusteluDao.findOne(keskustelunNimi);
        Viesti viesti = new Viesti(viestiDao.nextId(), sisalto, kalenteri.getTime() ,nimimerkki, keskusteluDao.findOne(keskustelunNimi).getId());
        viestiDao.create(viesti);
        keskusteluDao.lisaaViestit(keskustelu);
    }
    
    public void tulosta() throws SQLException {
        
        for (Alue alue : alueDao.findAll()) {
            System.out.println(alue.getNimi());
            System.out.println("***********************");
            
            for (Keskustelu keskustelu : keskusteluDao.alueenKeskustelut(alue.getId())) {
                System.out.println(keskustelu.getOtsikko());
                System.out.println(keskustelu.getId());
                System.out.println("-----------------------");
                
                for (Viesti viesti : viestiDao.keskustelunViestit(keskustelu.getId())) {
                    System.out.print("***");
                    System.out.print(viesti.getLahettaja());
                    System.out.println("***");
                    System.out.println("\n" + viesti.getSisalto() + "\n" + viesti.getLahetysAika() + "\n");
                }
            }
            System.out.println("\n\n");
        }
    }
}