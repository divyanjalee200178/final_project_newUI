package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.models.CustomerDTO;
import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
   // private static CustomerDAOImpl customerDAOImpl;
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);

    }
    public boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(dto.getId(),dto.getName(),dto.getAddress(),dto.getEmail(),dto.getTel()));
    }

    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getId(),dto.getName(),dto.getTel(),dto.getAddress(),dto.getEmail()));
    }

    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers= new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for (Customer c : all) {
            allCustomers.add(new CustomerDTO(c.getId(),c.getName(),c.getAddress(),c.getEmail(),c.getTel()));
        }
        return allCustomers;
    }


    public Customer searchCustomer(String id) throws SQLException, ClassNotFoundException {
            return customerDAO.search(id);
        }

    public Customer searchByNumber(String tele) throws SQLException, ClassNotFoundException {
        return customerDAO.searchContact(tele);
    }

    @Override
    public int getCustomerCounts() throws SQLException {
        return customerDAO.CustomerCount();
    }

    public Customer searchById(String id) throws SQLException, ClassNotFoundException {
        if (customerDAO == null) {
            throw new IllegalStateException("CustomerDAO has not been initialized.");
        }

        Customer c = customerDAO.search(id);
        return new Customer(c.getId(), c.getName(), c.getTel(), c.getAddress(), c.getEmail());
    }
}
