package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import tikape.runko.domain.*;

public class KeskusteluDao implements Dao<Keskustelu, Integer> {
    
    private Tietokanta tietokanta;
    private ViestiDao viestiDao;
    
    public KeskusteluDao(Tietokanta tietokanta, ViestiDao viestiDao) {
        this.tietokanta = tietokanta;
        this.viestiDao = viestiDao;
    }

    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        // etsii keskustelun avaimen perusteella
        
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Keskustelu WHERE id = ?");
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();
        
        if (!rs.next()) {
            return null;
        }
        
        Integer id = rs.getInt("id");
        String otsikko = rs.getString("otsikko");
        Integer alue = rs.getInt("alue");
        
        rs.close();
        stmt.close();
        conn.close();
        
        Keskustelu keskustelu = new Keskustelu(id, otsikko, alue);
        lisaaViestit(keskustelu);
        
        return keskustelu;
    }

    @Override
    public List<Keskustelu> findAll() throws SQLException {
        // palauttaa kaikki keskustelut tietokannasta
        
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Keskustelu");
        
        ResultSet rs = stmt.executeQuery();
        ArrayList<Keskustelu> keskustelut = new ArrayList<>();
                
        boolean more = rs.next();
        
        if (!more) {
            return keskustelut;
        }
        
        while(more){
            Integer id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");
            Integer alue = rs.getInt("alue");
            Keskustelu keskustelu = new Keskustelu(id, otsikko, alue);
            lisaaViestit(keskustelu);
            keskustelut.add(keskustelu);
            more = rs.next();
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return keskustelut;
    }
    
    public Keskustelu findOne(String keskustelunOtsikko) throws SQLException {
        // etsii keskutelun otsikon perusteella
        
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Keskustelu WHERE otsikko = ?");
        stmt.setString(1, keskustelunOtsikko);
        
        ResultSet rs = stmt.executeQuery();
        
        if (!rs.next()) {
            return null;
        }
        
        Integer id = rs.getInt("id");
        String otsikko = rs.getString("otsikko");
        Integer alue = rs.getInt("alue");
        
        rs.close();
        stmt.close();
        conn.close();
        
        Keskustelu keskustelu = new Keskustelu(id, otsikko, alue);
        lisaaViestit(keskustelu);
        
        return keskustelu;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // poistaa keskustelun tietokannasta
        
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Keskustelu WHERE id = ?");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }

    @Override
    public List<Keskustelu> findAllIn(Collection<Integer> keys) throws SQLException {
        // palauttaa keskustelut joiden id kuuluu parametrina saatuun kokoelmaan
        
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Keskustelu WHERE id = ?");
        ArrayList<Keskustelu> keskustelut = new ArrayList<>();
        
        for (Integer key : keys) {
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
        
            if (!rs.next()) {
                continue;
            }
            
            Keskustelu keskustelu = new Keskustelu(rs.getInt("id"), rs.getString("otsikko"), rs.getInt("alue"));
            lisaaViestit(keskustelu);
            keskustelut.add(keskustelu);
            rs.close();
        }
        stmt.close();
        conn.close();
        
        return keskustelut;
    }

    @Override
    public void create(Keskustelu keskustelu) throws SQLException {
        // lisää parametrina saadun keskustelun tietokantaan
        
        Connection conn = tietokanta.getConnection();
        Integer id = keskustelu.getId();
        String otsikko = keskustelu.getOtsikko();
        Integer alue = keskustelu.getAlue();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Keskustelu VALUES (?, ?, ?)");
        stmt.setInt(1, id);
        stmt.setString(2, otsikko);
        stmt.setInt(3, alue);
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }
    
    public void lisaaViestit(Keskustelu keskustelu) throws SQLException {
        // lisää viestit parametrina saatuun keskusteluun. käytetään ainakin find-metodoissa
        
        List<Viesti> viestit = viestiDao.keskustelunViestit(keskustelu.getId());
        
        for (Viesti viesti : viestit) {
            keskustelu.addViesti(viesti);
        }
    }
    
    public List<Keskustelu> alueenKeskustelut(Integer alueId) throws SQLException {
        // palauttaa parametrina saadulle alueelle kuuluvat keskustelut
        
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT Keskustelu.id, Keskustelu.otsikko, Keskustelu.alue FROM Keskustelu, Viesti WHERE Keskustelu.alue = ? AND Viesti.keskustelu = Keskustelu.id GROUP BY Keskustelu.id ORDER BY Viesti.lähetysaika DESC");
        stmt.setInt(1, alueId);
        ResultSet rs = stmt.executeQuery();
        
        boolean more = rs.next();
        ArrayList<Keskustelu> keskustelut = new ArrayList<>();
        
        if(!more) {
            return keskustelut;
        }
        
        while(more) {
            Keskustelu keskustelu = new Keskustelu(rs.getInt("id"), rs.getString("otsikko"), rs.getInt("alue"));
            lisaaViestit(keskustelu);
            keskustelut.add(keskustelu);
            more = rs.next();
        }
        return keskustelut;
    }
    
    public Integer laskeViestit(Keskustelu keskustelu) throws SQLException {
        // laskee keskutelun viestit?
        
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT() AS määrä FROM Viesti WHERE keskustelu = ?");
        stmt.setInt(1, keskustelu.getId());
        ResultSet rs = stmt.executeQuery();
        Integer maara = rs.getInt("määrä");
        
        rs.close();
        stmt.close();
        conn.close();
        
        return maara;
    }
    
    public boolean exists(String otsikko) throws SQLException {
        // onko samalla otsikolla jo keskutelu?
        
        
        for (Keskustelu keskustelu : findAll()) {
            if (keskustelu.getOtsikko().equals(otsikko)) {
                return true;
            }
        }
        return false;
    }
    
    public Integer nextId() throws SQLException {
        // palauttaa käyttämättömän id:n keskustelulle
        
        Connection conn = tietokanta.getConnection();
        System.out.println("connection made");
        PreparedStatement stmt = conn.prepareStatement("SELECT id FROM Keskustelu ORDER BY id DESC LIMIT 1");
        System.out.println("statement made");
        ResultSet rs = stmt.executeQuery();
        System.out.println("result set made");
        
        if (rs.isClosed()) {
            System.out.println("result set is closed");
            return 1;
        }        
        
        Integer id =  rs.getInt("id") + 1;
        rs.close();
        
        return id;
    }
    
}