package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Item;
import lk.ijse.entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item> {
    public  boolean placeOrderUpdate(List<OrderDetail> oderList) throws SQLException;
   public boolean updateQty(String itemCode, int qty) throws SQLException ;

    public int ItemCount() throws SQLException;

    }
