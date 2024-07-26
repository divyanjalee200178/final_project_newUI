package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.entity.Customer;
import lk.ijse.models.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    //public boolean exsistCustomer(String id) throws SQLException, ClassNotFoundException ;

    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException ;
    public boolean deleteCustomer(String id)throws SQLException, ClassNotFoundException;

    public boolean saveCustomer(CustomerDTO customerDTO)throws SQLException, ClassNotFoundException;
    public boolean updateCustomer(CustomerDTO customerDTO)throws SQLException, ClassNotFoundException;
    public  ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;
    public  Customer searchCustomer(String id)throws SQLException, ClassNotFoundException;

    public Customer searchById(String id) throws SQLException, ClassNotFoundException ;

    public Customer searchByNumber(String tele) throws SQLException,ClassNotFoundException;

    public int getCustomerCounts() throws SQLException;


    // public  void setCustomerDAO(CustomerDAOImpl dao) throws SQLException,ClassNotFoundException;
}
