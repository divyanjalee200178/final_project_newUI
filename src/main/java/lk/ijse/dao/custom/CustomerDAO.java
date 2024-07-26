package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
   //public Customer searchById(String id) throws SQLException;
    // public boolean searchById(String id) throws SQLException;
   public int CustomerCount() throws SQLException;

   }
