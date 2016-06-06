package tikape.runko.database;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import tikape.runko.domain.*;

public class AlueDao implements Dao<Alue, Integer> {
    
    private Tietokanta tietokanta;
    
    public AlueDao(Tietokanta tietokanta) {
        this.tietokanta = tietokanta;
    }

    @Override
    public Alue findOne(Integer key) throws SQLException {
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
        
        return new Alue(id, nimi);
    }

    @Override
    public List<Alue> findAll() throws SQLException {
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Alue");
        
        ResultSet rs = stmt.executeQuery();
        
        if (!rs.next()) {
            return null;
        }
        
        ArrayList<Alue> alueet = new ArrayList<>();
        
        alueet.add(new Alue(rs.getInt("id"), rs.getString("nimi")));
        
        while(rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            
            alueet.add(new Alue(id, nimi));
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return alueet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Alue WHERE id = ?");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }

    @Override
    public List<Alue> findAllIn(Collection<Integer> keys) throws SQLException {
        Connection conn = tietokanta.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Alue WHERE id = ?");
        ArrayList<Alue> alueet = new ArrayList<>();
        
        for (Integer key : keys) {
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
        
            if (!rs.next()) {
                continue;
            }
        
            alueet.add(new Alue(rs.getInt("id"), rs.getString("nimi")));
        }
        stmt.close();
        conn.close();
        
        return alueet;
    }

    @Override
    public void create(Alue alue) throws SQLException {
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

}