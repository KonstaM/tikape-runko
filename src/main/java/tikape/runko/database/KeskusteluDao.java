package tikape.runko.database;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import tikape.runko.domain.*;

public class KeskusteluDao implements Dao<Keskustelu, Integer> {
    
    private Tietokanta tietokanta;
    private Dao<Viesti, Integer> viestiDao;
    private Dao<Alue, Integer> alueDao;
    
    public KeskusteluDao(Tietokanta tietokanta, Dao<Viesti, Integer> viestiDao, Dao<Alue, Integer> alueDao) {
        this.tietokanta = tietokanta;
        this.viestiDao = viestiDao;
        this.alueDao = alueDao;
    }

    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Keskustelu> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Keskustelu> findAllIn(Collection<Integer> keys) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Keskustelu object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}