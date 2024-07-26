package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Order> {
   public String  currentId() throws SQLException ;
    public String generateNewID() throws SQLException, ClassNotFoundException;

    public int OrderCount() throws SQLException;
    //boolean save(Order order) throws SQLException,ClassNotFoundException;

    //boolean save(Order entity) throws SQLException;
}
