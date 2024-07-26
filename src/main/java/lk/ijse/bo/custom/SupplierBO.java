package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Employee;
import lk.ijse.entity.Supplier;
import lk.ijse.models.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {
    public boolean saveSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException;

    public boolean existSupplier(String id) throws SQLException, ClassNotFoundException;

    public boolean updateSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException;

    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException;

    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException;

    public Supplier searchSupplier(String id)throws SQLException, ClassNotFoundException;

    public Supplier searchByNumber(String tele) throws SQLException, ClassNotFoundException;

}
