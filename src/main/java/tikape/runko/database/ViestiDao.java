package tikape.runko.database;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import tikape.runko.domain.*;

public class ViestiDao implements Dao<Viesti, Integer> {
    
    private Tietokanta tietokanta;
    
    public ViestiDao(Tietokanta tietokanta) {
        this.tietokanta = tietokanta;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        // etsii viestin avaimen perusteella
        
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Viesti WHERE id = ?");
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();
        
        if (!rs.next()) {
            return null;
        }
        
        Integer id = rs.getInt("id");
        String sisalto = rs.getString("sisältö");
        Timestamp aika = rs.getTimestamp("lähetysaika");
        String lahettaja = rs.getString("lähettäjä");
        Integer keskustelu = rs.getInt("keskustelu");
        
        rs.close();
        stmt.close();
        conn.close();
        
        return new Viesti(id, sisalto, aika, lahettaja, keskustelu);
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        // palauttaa kaikki viestit tietokannsta
        
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Viesti");
        
        ResultSet rs = stmt.executeQuery();
        ArrayList<Viesti> viestit = new ArrayList<>();
                
        boolean more = rs.next();
        
        if (!more) {
            return viestit;
        }
        
        while(more){
            Integer id = rs.getInt("id");
            String sisalto = rs.getString("sisältö");
            Timestamp aika = rs.getTimestamp("lähetysaika");
            String lahettaja = rs.getString("lahettaja");
            Integer keskustelu = rs.getInt("keskustelu");
            
            viestit.add(new Viesti(id, sisalto, aika, lahettaja, keskustelu));
            more = rs.next();
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return viestit;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // poistaa viestin tietokannasta
        
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Viesti WHERE id = ?");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }

    @Override
    public List<Viesti> findAllIn(Collection<Integer> keys) throws SQLException {
        // palauttaa viestit joiden id on parametrina
        
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Viesti WHERE id = ?");
        ArrayList<Viesti> viestit = new ArrayList<>();
        
        for (Integer key : keys) {
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
        
            if (!rs.next()) {
                continue;
            }
        
            viestit.add(new Viesti(rs.getInt("id"), rs.getString("sisältö"), rs.getTimestamp("lähetysaika"), rs.getString("lähettäjä"), rs.getInt("keskustelu")));
            rs.close();
        }
        stmt.close();
        conn.close();
        
        return viestit;
    }

    @Override
    public void create(Viesti viesti) throws SQLException {
        // lisää tietokantaan parametrina saadun viestin
        
        Connection conn = tietokanta.getConnection();
        Integer id = viesti.getId();
        String sisalto = viesti.getSisalto();
        Timestamp aika = viesti.getLahetysAika();
        String lahettaja = viesti.getLahettaja();
        Integer keskustelu = viesti.getKeskustelu();
        
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Viesti VALUES (?, ?, ?, ?, ?)");
        stmt.setInt(1, id);
        stmt.setString(2, sisalto);
        stmt.setString(3, lahettaja);
        stmt.setTimestamp(4, aika);
        stmt.setInt(5, keskustelu);
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }
    
    public List<Viesti> keskustelunViestit(Integer keskusteluId) throws SQLException{
        // palauttaa listan viestejä, jotka kuuluvat keskutelulle jonka id on parametrissa
        
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Viesti WHERE keskustelu = ? ORDER BY lähetysaika DESC");
        stmt.setInt(1, keskusteluId);
        ResultSet rs = stmt.executeQuery();
        
        boolean more = rs.next();
        ArrayList<Viesti> viestit = new ArrayList<>();
        
        if(!more) {
            return viestit;
        }
        
        while(more) {
            viestit.add(new Viesti(rs.getInt("id"), rs.getString("sisältö"), rs.getTimestamp("lähetysaika"), rs.getString("lähettäjä"), rs.getInt("keskustelu")));
            more = rs.next();
        }
        return viestit;
    }
    
    public Integer nextId() throws SQLException {
        // palautaa käyttämättömän id:n
        
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT id FROM Viesti ORDER BY id DESC LIMIT 1");
        ResultSet rs = stmt.executeQuery();
        
        if (rs.isClosed()) {
            return 1;
        }
        
        Integer id = rs.getInt("id") + 1;
        rs.close();
        System.out.println(id);
        
        return id;
    }
    
}