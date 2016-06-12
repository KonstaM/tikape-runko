package tikape.runko.database;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import tikape.runko.domain.*;

public class AlueDao implements Dao<Alue, Integer> {
    
    private Tietokanta tietokanta;
    private KeskusteluDao keskusteluDao;
    
    public AlueDao(Tietokanta tietokanta, KeskusteluDao keskusteluDao) {
        this.tietokanta = tietokanta;
        this.keskusteluDao = keskusteluDao;
    }

    @Override
    public Alue findOne(Integer key) throws SQLException {
        // palauttaa alueen avaimen perusteella
        
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Alue WHERE id = ?");
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();
        
        if (!rs.next()) {
            return null;
        }
        
        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");
        
        rs.close();
        stmt.close();
        conn.close();
        
        Alue alue = new Alue(id, nimi);
        lisaaKeskustelut(alue);
        
        return alue;
    }
    
    public Alue findOne(String alueenNimi) throws SQLException {
        // palauttaa alueen nimen perusteella
        
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Alue WHERE nimi = ?");
        stmt.setString(1, alueenNimi);
        
        ResultSet rs = stmt.executeQuery();
        
        if (!rs.next()) {
            return null;
        }
        
        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");
        
        rs.close();
        stmt.close();
        conn.close();
        
        Alue alue = new Alue(id, nimi);
        lisaaKeskustelut(alue);
        
        return alue;
    }

    @Override
    public ArrayList<Alue> findAll() throws SQLException {
        // palauttaa kaikki alueet
        
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Alue");
        
        ResultSet rs = stmt.executeQuery();
        ArrayList<Alue> alueet = new ArrayList<>();
                
        boolean more = rs.next();
        
        if (!more) {
            return alueet;
        }
        
        while(more){
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            Alue alue = new Alue(id, nimi);
            lisaaKeskustelut(alue);
            alueet.add(alue);
            more = rs.next();
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return alueet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // poistaa alueen tietokannasta
        
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Alue WHERE id = ?");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }

    @Override
    public List<Alue> findAllIn(Collection<Integer> keys) throws SQLException {
        // etsii parametreina saaduista avaimista alueet. tätä ei varmaan tarvita???
        
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Alue WHERE id = ?");
        ArrayList<Alue> alueet = new ArrayList<>();
        
        for (Integer key : keys) {
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
        
            if (!rs.next()) {
                continue;
            }
            
            Alue alue = new Alue(rs.getInt("id"), rs.getString("nimi"));
            lisaaKeskustelut(alue);
            alueet.add(alue);
            rs.close();
        }
        stmt.close();
        conn.close();
        
        return alueet;
    }

    @Override
    public void create(Alue alue) throws SQLException {
        // luo uuden alueen tietokantaan
        
        Connection conn = tietokanta.getConnection();
        Integer id = alue.getId();
        String nimi = alue.getNimi();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Alue VALUES (?, ?)");
        stmt.setInt(1, id);
        stmt.setString(2, nimi);
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }
    
    public void lisaaKeskustelut(Alue alue) throws SQLException {
        // lisää parametrissa saatuun alueeseen siihen kuuluvat keskustelut
        // käytetään findAll() ja findOne() metodeissa
        
        List<Keskustelu> keskustelut = keskusteluDao.alueenKeskustelut(alue.getId());
        for (Keskustelu keskustelu : keskustelut) {
            alue.addKeskustelu(keskustelu);
        }
    }
    
    public Integer laskeKeskustelut(Alue alue) throws SQLException {
        // laskee paremtrina saadun alueen keskustelujen lukumäärän
        
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT() AS määrä FROM Keskustelu WHERE alue = ?");
        stmt.setInt(1, alue.getId());
        ResultSet rs = stmt.executeQuery();
        Integer maara = rs.getInt("määrä");
        
        rs.close();
        stmt.close();
        conn.close();
        
        return maara;
    }
    
    public boolean exists(String nimi) throws SQLException {
        // tarkistaa onko parametrin nimistä aluetta olemassa
        
        for (Alue alue : findAll()) {
            if (alue.getNimi().equals(nimi)) {
                return true;
            }
        }
        return false;
    }
    
    public Integer nextId() throws SQLException {
        // palauttaa vapaana olevan PK:n alueelle
        
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT id FROM Alue ORDER BY id DESC LIMIT 1");
        ResultSet rs = stmt.executeQuery();
        
        if (rs.isClosed()) {
            return 1;
        }
        
        Integer id = rs.getInt("id") + 1;
        rs.close();
        
        return id;
    }
}