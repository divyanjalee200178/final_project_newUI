package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT OrderId FROM `Orders` WHERE OrderId=?",id);
        return rst.next();
    }


    @Override
    public boolean save(Order entity) throws SQLException{
        return SQLUtil.execute("INSERT INTO orders VALUES(?, ?, ?)",entity.getOrderId(),entity.getDescription(),entity.getCustomerId());

    }


    @Override
    public boolean update(Order entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    public String generateNewID() throws SQLException {
        ResultSet rst= SQLUtil.execute("SELECT OrderId FROM 'Orders' ORDER BY OrderId DESC LIMIT 1;");
        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("oid").replace("OID-", "")) + 1)) : "OID-001";
    }

    @Override
    public int OrderCount() throws SQLException {
        ResultSet rst=SQLUtil.execute("SELECT COUNT(*) AS OrderCount FROM orders");
        if(rst.next()){
            return rst.getInt("OrderCount");
        }
        return 0;

    }

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public Order searchContact(String tel) throws SQLException, ClassNotFoundException {
        return null;
    }


    public String currentId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT orderId FROM Orders ORDER BY orderId DESC LIMIT 1");
        rst.next();
        return rst.getString("orderId");
       // return SQLUtil.execute("select concat('O',max(cast(substring(orderId,2)as unsigned)))as max_OrderId from orders");

    }

}
