package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Employee;
import lk.ijse.models.SupplierDTO;
import lk.ijse.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO= (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    public boolean saveSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(new Supplier(dto.getId(),dto.getName(),dto.getAddress(),dto.getEmail(),dto.getTel()));
    }

    public boolean existSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.exist(id);
    }

    public boolean updateSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(dto.getId(),dto.getName(),dto.getTel(),dto.getAddress(),dto.getEmail()));
    }

    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(id);
    }

    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDTO> allSupplier= new ArrayList<>();
        ArrayList<Supplier> all = supplierDAO.getAll();
        for (Supplier s : all) {
            allSupplier.add(new SupplierDTO(s.getId(),s.getName(),s.getAddress(),s.getEmail(),s.getTel()));
        }
        return allSupplier;
    }

    public Supplier searchSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.search(id);
    }

    public Supplier searchByNumber(String tele) throws SQLException, ClassNotFoundException {
        return supplierDAO.searchContact(tele);
    }
}
