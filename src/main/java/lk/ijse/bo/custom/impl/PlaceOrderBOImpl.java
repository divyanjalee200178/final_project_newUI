package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PlaceOrderBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.OrderDeatilDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.entity.*;
import lk.ijse.models.CustomerDTO;
import lk.ijse.models.ItemDTO;
import lk.ijse.models.OrderDTO;
import lk.ijse.models.OrderDetailDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.lowagie.text.pdf.PdfName.PO;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
   OrderDeatilDAO orderDetailDAO = (OrderDeatilDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);


    public  boolean placeOrder(OrderDTO dto, List<OrderDetailDTO> orderDeatails) throws SQLException {
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();

            connection.setAutoCommit(false);

            boolean isSaved = orderDAO.save(new Order(dto.getOrderId(), dto.getCustomerId(), dto.getDescription()));

            if (!isSaved) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            for (OrderDetailDTO detail : orderDeatails) {
                OrderDetail orderDetail = new OrderDetail(detail.getOrderId(), detail.getCode(), detail.getQty(), detail.getUnitPrice());
                boolean isDetailSaved = orderDetailDAO.save(orderDetail);

                if (!isDetailSaved) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }


                ItemDTO itemDTO = findItem(detail.getCode());
                itemDTO.setQtyOnHand(itemDTO.getQtyOnHand() - detail.getQty());

                boolean isUpdated = itemDAO.update(new Item(itemDTO.getCode(),itemDTO.getDescription(),itemDTO.getUnitPrice(),itemDTO.getQtyOnHand(),itemDTO.getLocation()));

                if (!isUpdated) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        }catch (ClassNotFoundException e) {
            throw new RuntimeException(e);


        /*Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            boolean b1 = orderDAO.exist(dto.getOrderId());
            /*if order id already exist
            if (b1) {
                return false;
            }

            connection.setAutoCommit(false);
            //Save the Order to the order table
            boolean b2 = orderDAO.save(new Order(dto.getOrderId(),dto.getCustomerId() ,dto.getOrderDate()));
            if (!b2) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            for (OrderDetail d : dto.getOderList()) {
                OrderDetail orderDetail = new OrderDetail(d.getOrderId(),d.getCode(),d.getQty(),d.getUnitPrice());
                boolean b3 = orderDetailDAO.save(orderDetail);
                if (!b3) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
                //Search & Update Item
                ItemDTO item = findItem(d.getCode());
                item.setQtyOnHand(item.getQtyOnHand() - d.getQty());

                //update item
                boolean b = itemDAO.update(new Item(item.getCode(), item.getDescription(), item.getUnitPrice(),item.getQtyOnHand(),item.getLocation()));

                if (!b) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
*/
        }
    }

    public ItemDTO findItem(String code)throws SQLException, ClassNotFoundException {
        try {
            Item i = itemDAO.search(code);
            return new ItemDTO(i.getCode(),i.getDescription(),i.getUnitPrice(),i.getQtyOnHand(),i.getLocation());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + code, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String generateOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewID();
    }

    public boolean placeOrder(PlaceOrder po) throws SQLException, ClassNotFoundException {
        return false;
    }

    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.search(id);
        return new CustomerDTO(c.getId(),c.getName(),c.getAddress(),c.getEmail(),c.getTel());
    }



    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item i = itemDAO.search(code);
        return new ItemDTO(i.getCode(),i.getDescription(),i.getUnitPrice(),i.getQtyOnHand(),i.getLocation());
    }


    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }


    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customerEntityData = customerDAO.getAll();
        ArrayList<CustomerDTO> convertToDto= new ArrayList<>();
        for (Customer c : customerEntityData) {
            convertToDto.add(new CustomerDTO(c.getId(),c.getName(),c.getAddress(),c.getEmail(),c.getTel()));
        }
        return convertToDto;
    }


    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> entityTypeData = itemDAO.getAll();
        ArrayList<ItemDTO> dtoTypeData= new ArrayList<>();
        for (Item i : entityTypeData) {
            dtoTypeData.add(new ItemDTO(i.getCode(),i.getDescription(),i.getUnitPrice(),i.getQtyOnHand(),i.getLocation()));
        }
        return dtoTypeData;
    }
    public String getCurrentId() throws SQLException {
        return orderDAO.currentId();
    }

    @Override
    public int getCustomerCounts() throws SQLException {
        return orderDAO.OrderCount();
    }
}
