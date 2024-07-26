package lk.ijse.models;
import lk.ijse.entity.OrderDetail;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;



public class OrderDTO implements Serializable {
    private String orderId;

    private  String description;
    private String customerId;


    List<OrderDetail> oderList;

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", description='" + description + '\'' +
                ", customerId='" + customerId + '\'' +
                ", oderList=" + oderList +
                '}';
    }



    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public List<OrderDetail> getOderList() {
        return oderList;
    }

    public void setOderList(List<OrderDetail> oderList) {
        this.oderList = oderList;
    }

    public OrderDTO(String orderId, String customerId, String description, List<OrderDetail> oderList) {
        this.orderId = orderId;
        this.description=description;
        this.customerId = customerId;
        this.oderList = oderList;
    }

    public OrderDTO(String orderId, String description, String customerId) {
        this.orderId = orderId;
        this.description = description;
        this.customerId = customerId;
    }

    public OrderDTO() {
    }
}