package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    public User check(String id,String password) throws SQLException, ClassNotFoundException;

   // public boolean saveUserDetails(String userId, String name, String password) throws SQLException;

}
