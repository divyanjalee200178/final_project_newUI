package lk.ijse.entity;

import lk.ijse.view.tdm.OrderTm;

import java.util.List;

public class OrderDetail {
    private String orderId;
    private String code;
    private int qty;
    private double unitPrice;

    private Order order;
    private List<OrderDetail> oderList;

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId='" + orderId + '\'' +
                ", code='" + code + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public OrderDetail(String orderId, String code, int qty, double unitPrice) {
        this.orderId = orderId;
        this.code = code;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public OrderDetail(OrderTm order, List<OrderDetail> odList) {
    }


    public List<OrderDetail> getOderList() {
        return oderList;
    }

    public void setOderList(List<OrderDetail> oderList) {
        this.oderList = oderList;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

