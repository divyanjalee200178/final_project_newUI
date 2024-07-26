package lk.ijse.view.tdm;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString@EqualsAndHashCode

public class OrderDetailTm {
    private String orderId;
    private String code;
    private int qty;
    private double unitPrice;

}
