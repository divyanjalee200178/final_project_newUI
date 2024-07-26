package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Employee;
import lk.ijse.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    public boolean save(Supplier entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO Supplier (id, name, address, email,number) VALUES (?, ?, ?, ?, ?)", entity.getId(), entity.getName(), entity.getAddress(),entity.getEmail(),entity.getTel());
    }

    public boolean update(Supplier entity) throws SQLException {
        return SQLUtil.execute("UPDATE Supplier SET name=?,number=? ,address=? ,email=? WHERE id=?", entity.getName(),entity.getTel(), entity.getAddress(),entity.getEmail(), entity.getId());
    }

    public boolean exist(String id) throws SQLException {
        ResultSet rst=SQLUtil.execute("SELECT id FROM Supplier WHERE id=?", id);
        return rst.next();
    }

    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM Supplier WHERE id=?",id);
    }

    public Supplier search(String id) throws SQLException {
            ResultSet rst= SQLUtil.execute("SELECT * FROM supplier WHERE id=?", id);
            rst.next();
            return new Supplier(rst.getString("id"), rst.getString("name"), rst.getString("address"),rst.getString("email"),rst.getString("number"));


    }


    public String generateNewId() throws SQLException, ClassNotFoundException {
        return null;
    }

    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> allSupplier = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Supplier");
        while (rst.next()) {
            Supplier supplier = new Supplier(rst.getString("id"), rst.getString("name"), rst.getString("address"),rst.getString("email"),rst.getString("number"));
            allSupplier.add(supplier);
        }
        return allSupplier;
    }

    @Override
    public Supplier searchContact(String tele) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM supplier WHERE number = ?", tele+ "");
        rst.next();
        return new Supplier(/*tele + ""*/rst.getString("id"), rst.getString("name"), rst.getString("address"), rst.getString("email"),tele + "");
    }

}
