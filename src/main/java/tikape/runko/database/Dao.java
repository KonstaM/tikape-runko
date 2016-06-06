package tikape.runko.database;

import java.sql.*;
import java.util.List;
import java.util.Collection;

public interface Dao<T, K> {
    
    T findOne(K key) throws SQLException;
    List<T> findAll() throws SQLException;
    void delete(K key) throws SQLException;
    List<T> findAllIn(Collection<K> keys) throws SQLException;
    void create(T object) throws SQLException;
}
