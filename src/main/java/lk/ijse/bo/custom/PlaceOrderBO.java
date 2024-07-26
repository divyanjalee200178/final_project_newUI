package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.OrderDetail;
import lk.ijse.models.CustomerDTO;
import lk.ijse.models.ItemDTO;
import lk.ijse.models.OrderDTO;
import lk.ijse.models.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PlaceOrderBO extends SuperBO {
    public String generateOrderID() throws SQLException, ClassNotFoundException;

    public boolean placeOrder(OrderDTO dto, List<OrderDetailDTO> orderDetailList) throws SQLException, ClassNotFoundException;

    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException;

    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException;

    public boolean existItem(String code) throws SQLException, ClassNotFoundException;

    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException;

    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;

    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    public ItemDTO findItem(String code) throws SQLException, ClassNotFoundException;

    public String getCurrentId() throws SQLException, ClassNotFoundException;

    public int getCustomerCounts() throws SQLException;
    // public  boolean placeOrder(OrderDTO dto, List<OrderDetail> orderDeatailList) throws SQLException;
}

