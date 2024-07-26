package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface OrderDeatilDAO extends CrudDAO<OrderDetail> {
    boolean save(List<OrderDetail> oderList) throws SQLException;
}
