package lk.ijse.entity;

import lk.ijse.view.OrderDetail;

import java.util.List;

public class PlaceOrder {
    private Order order;
    private List<OrderDetail> oderList;

    @Override
    public String toString() {
        return "PlaceOrder{" +
                "order=" + order +
                ", oderList=" + oderList +
                '}';
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderDetail> getOderList() {
        return oderList;
    }

    public void setOderList(List<OrderDetail> oderList) {
        this.oderList = oderList;
    }

    public PlaceOrder(Order order, List<OrderDetail> oderList) {
        this.order = order;
        this.oderList = oderList;
    }

    public PlaceOrder() {
    }
}
