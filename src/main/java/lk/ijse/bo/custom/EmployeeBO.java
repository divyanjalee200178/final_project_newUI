package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Employee;
import lk.ijse.models.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;

    public boolean existEmployee(String id) throws SQLException, ClassNotFoundException;

    public boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException;

    public ArrayList<EmployeeDTO> getAllEmployees() throws SQLException, ClassNotFoundException;
    public  boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;

    public Employee searchEmployee(String id)throws SQLException, ClassNotFoundException;

    public Employee searchByNumber(String tele) throws SQLException, ClassNotFoundException;

    public int getEmployeeCounts() throws SQLException;
}
