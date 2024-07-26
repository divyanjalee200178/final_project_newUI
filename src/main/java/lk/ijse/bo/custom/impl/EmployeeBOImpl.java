package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.models.EmployeeDTO;
import lk.ijse.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO= (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    public boolean saveEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(dto.getId(),dto.getName(),dto.getAddress(),dto.getEmail(),dto.getTel()));
    }

    public boolean existEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.exist(id);
    }

    public boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(dto.getId(),dto.getName(),dto.getTel(),dto.getAddress(),dto.getEmail()));
    }
    public ArrayList<EmployeeDTO> getAllEmployees() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDTO> allEmployee= new ArrayList<>();
        ArrayList<Employee> all = employeeDAO.getAll();
        for (Employee e : all) {
            allEmployee.add(new EmployeeDTO(e.getId(),e.getName(),e.getAddress(),e.getEmail(),e.getTel()));
        }
        return allEmployee;
    }

    public Employee searchEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.search(id);
    }

    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    public Employee searchByNumber(String tele) throws SQLException, ClassNotFoundException {
        return employeeDAO.searchContact(tele);
    }

    public int getEmployeeCounts() throws SQLException {
        return employeeDAO.EmployeeCount();
    }
}
