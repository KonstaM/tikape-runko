package tikape.runko.database;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import tikape.runko.domain.*;

public class ViestiDao implements Dao<Viesti, Integer> {
    
    private Tietokanta tietokanta;
    private Dao<Keskustelu, Integer> keskusteluDao;
    
    public ViestiDao(Tietokanta tietokanta, Dao<Keskustelu, Integer> keskusteluDao) {
        this.tietokanta = tietokanta;
        this.keskusteluDao = keskusteluDao;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Viesti> findAllIn(Collection<Integer> keys) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Viesti object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}