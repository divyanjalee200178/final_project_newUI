package lk.ijse.view.tdm;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class OrderTm {
    private String orderId;
    private String description;
    private String customerId;



}
