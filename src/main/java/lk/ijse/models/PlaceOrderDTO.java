package lk.ijse.models;

import lk.ijse.view.tdm.OrderTm;
import lk.ijse.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaceOrderDTO implements Serializable {
    private OrderTm order;
    private List<OrderDetail> oderList;


}
