package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.User;
import lk.ijse.models.UserDTO;

import java.sql.SQLException;

public interface PasswordChangeBO extends SuperBO {
    public boolean updateUser(UserDTO dto) throws SQLException, ClassNotFoundException;
    public User checkCreden(String id,String password) throws SQLException, ClassNotFoundException;

    public boolean saveUser(UserDTO userDTO) throws SQLException, ClassNotFoundException;

}
