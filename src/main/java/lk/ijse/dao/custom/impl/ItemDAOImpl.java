package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.entity.Item;
import lk.ijse.entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import static lk.ijse.repository.ItemRepo.updateQty;

public class ItemDAOImpl implements ItemDAO {
    public boolean delete(String code) throws SQLException {
        return SQLUtil.execute("DELETE FROM Item WHERE code=?", code);
    }

    @Override
    public boolean exist(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT code FROM Item WHERE code=?", code);
        return rst.next();
    }

    public boolean save(Item entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO Item (code,description,unitPrice,qtyOnHand,location) VALUES (?, ?, ?, ?, ?)", entity.getCode(), entity.getDescription(), entity.getUnitPrice(), entity.getQtyOnHand(), entity.getLocation());
    }

    public boolean update(Item entity) throws SQLException {
        return SQLUtil.execute("UPDATE Item SET description=? ,unitPrice=? ,qtyOnHand=? ,location=? WHERE code=?", entity.getDescription(), entity.getUnitPrice(), entity.getQtyOnHand(), entity.getLocation(), entity.getCode());
    }

    public Item search(String code) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Item WHERE code=?", code);
        rst.next();
        return new Item(rst.getString("code"),rst.getString("description"),rst.getDouble("unitPrice"),rst.getInt("qtyOnHand"),rst.getString("location"));

    }


    public String generateNewId() throws SQLException, ClassNotFoundException {
        return null;
    }

    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItem = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Item");
        while (rst.next()) {
            Item item = new Item(rst.getString("code"), rst.getString("description"), rst.getDouble("unitPrice"), rst.getInt("qtyOnHand"), rst.getString("location"));
            allItem.add(item);
        }
        return allItem;
    }

    @Override
    public Item searchContact(String tel) throws SQLException, ClassNotFoundException {
        return null;
    }

    public boolean updateQty(String itemCode, int qty) throws SQLException {
        ResultSet rst = SQLUtil.execute("UPDATE Item SET qtyOnHand = qtyOnHand - ? WHERE code = ?", itemCode, qty);
        return rst.next();
    }

    public boolean searchByCode(String code) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Item WHERE code = ?", code);
        return rst.next();

    }

    public boolean placeOrderUpdate(List<OrderDetail> oderList) throws SQLException {
        for(OrderDetail orderDetail : oderList){
            System.out.println("place order update >>>" +oderList);
            boolean isupdated = updateQty(orderDetail.getCode(),orderDetail.getQty());
            if (isupdated){
                return true;
            }
        }

        return false;

    }
    public int ItemCount() throws SQLException {
        ResultSet rst=SQLUtil.execute("SELECT COUNT(*) AS ItemCount FROM Item");
        if(rst.next()){
            return rst.getInt("ItemCount");
        }
        return 0;
    }
}
