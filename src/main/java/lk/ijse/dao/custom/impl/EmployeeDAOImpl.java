package lk.ijse.dao.custom.impl;

import jdk.jfr.RecordingState;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    public boolean save(Employee entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO Employee (id, name, address, email,number) VALUES (?, ?, ?, ?, ?)", entity.getId(), entity.getName(), entity.getAddress(),entity.getEmail(),entity.getTel());
    }

    public boolean exist(String id) throws SQLException {
        ResultSet rst=SQLUtil.execute("SELECT id FROM employee WHERE id=?", id);
        return rst.next();
    }

    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM employee WHERE id=?",id);
    }

    public boolean update(Employee entity) throws SQLException {
        return SQLUtil.execute("UPDATE employee SET name=?,number=? ,address=? ,email=? WHERE id=?", entity.getName(),entity.getTel(), entity.getAddress(),entity.getEmail(), entity.getId());
    }

    public Employee search(String id) throws SQLException {
            ResultSet rst= SQLUtil.execute("SELECT * FROM employee WHERE id=?", id);
            rst.next();
            return new Employee(rst.getString("id"), rst.getString("name"), rst.getString("address"),rst.getString("email"),rst.getString("number"));

        }
    public Employee searchContact(String tele) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee WHERE number = ?", tele+ "");
        rst.next();
        return new Employee(/*tele + ""*/rst.getString("id"), rst.getString("name"), rst.getString("address"), rst.getString("email"),tele + "");
    }

    public String generateNewId() throws SQLException, ClassNotFoundException {
        return null;
    }

    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> allEmployees = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Employee");
        while (rst.next()) {
            Employee employee = new Employee(rst.getString("id"), rst.getString("name"), rst.getString("address"),rst.getString("email"),rst.getString("number"));
            allEmployees.add(employee);
        }
        return allEmployees;
    }

    public int EmployeeCount() throws SQLException {
        ResultSet rst=SQLUtil.execute("SELECT COUNT(*) AS EmployeeCount FROM employee");
        if(rst.next()){
            return rst.getInt("EmployeeCount");
        }
        return 0;
    }


}
