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
        Connection connection = DriverManager.getConnection(tietokannanOsoite);
        Statement stmt = connection.createStatement();
        
        
        // Seuraavassa try-catch rakenteessa haetaan tietoa kustakin taulusta,
        // jos taulua ei ole (ErrorCode == 1) luodaan sellainen
                
        try {
            stmt.executeQuery("SELECT * FROM Alue");
        } catch (SQLException e) {
            if (e.getErrorCode() == 1) {
                stmt.executeQuery("CREATE TABLE Alue (id integer PRIMARY KEY NOT NULL, nimi varchar(500) NOT NULL);");
            }
        }
        
        try {
            stmt.executeQuery("SELECT * FROM Keskustelu");
        } catch (SQLException e) {
            if (e.getErrorCode() == 1) {
                stmt.executeQuery("CREATE TABLE Keskustelu (id integer PRIMARY KEY NOT NULL, otsikko varchar(500) NOT NULL, alue integer NOT NULL, FOREIGN KEY(alue) REFERENCES Alue(id));");
            }
        }

        try {
            stmt.executeQuery("SELECT * FROM Viesti");
        } catch (SQLException e) {
            if (e.getErrorCode() == 1) {
                stmt.executeQuery("CREATE TABLE Viesti (id integer PRIMARY KEY NOT NULL, sisältö varchar(5000) NOT NULL, lähettäjä varchar(50) NOT NULL, lähetysaika timestamp NOT NULL, keskustelu integer NOT NULL, FOREIGN KEY(keskustelu) REFERENCES Keskustelu(id));");
            }
        }
        
        connection.close();
    }
}