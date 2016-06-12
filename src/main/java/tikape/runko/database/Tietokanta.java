package tikape.runko.database;

import java.sql.*;

public class Tietokanta {
    
    private String tietokannanOsoite;
    
    public Tietokanta(String osoite) throws ClassNotFoundException{
        tietokannanOsoite = osoite;
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(tietokannanOsoite);
    }
    
    public void createDatabase() throws SQLException {
        // luo tietokannan
        
        Connection conn = DriverManager.getConnection(tietokannanOsoite);
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("CREATE TABLE Alue (id integer PRIMARY KEY, nimi varchar(500) NOT NULL);");
        stmt.executeUpdate("CREATE TABLE Keskustelu (id integer PRIMARY KEY, otsikko varchar(500) NOT NULL, alue integer NOT NULL, FOREIGN KEY(alue) REFERENCES Alue(id));");
        stmt.executeUpdate("CREATE TABLE Viesti (id integer PRIMARY KEY, sisältö varchar(5000) NOT NULL, lähettäjä varchar(50) NOT NULL, lähetysaika timestamp NOT NULL, keskustelu integer NOT NULL, FOREIGN KEY(keskustelu) REFERENCES Keskustelu(id));");
        stmt.close();
        conn.close();
    }
}