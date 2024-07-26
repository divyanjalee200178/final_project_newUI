package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Item;
import lk.ijse.models.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    public boolean existItem(String code) throws SQLException, ClassNotFoundException;

    public boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException;

    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException;

    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException;
    public Item searchByCode(String code) throws SQLException, ClassNotFoundException ;

    //public Item searchItem(String code)throws SQLException, ClassNotFoundException;
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    public int getItemCounts() throws SQLException;
}
