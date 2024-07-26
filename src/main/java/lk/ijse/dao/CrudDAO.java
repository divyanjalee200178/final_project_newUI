package lk.ijse.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {
    public boolean delete(String id)throws SQLException, ClassNotFoundException;
    public boolean exist(String id) throws SQLException, ClassNotFoundException;
    public boolean save(T entity) throws SQLException, ClassNotFoundException;
    public boolean update(T entity)throws SQLException, ClassNotFoundException;
    public T search(String id) throws SQLException, ClassNotFoundException;
   // public String generateNewId()throws SQLException,ClassNotFoundException;
    public ArrayList<T> getAll() throws SQLException, ClassNotFoundException;
    public T searchContact(String tel) throws SQLException,ClassNotFoundException;
}
