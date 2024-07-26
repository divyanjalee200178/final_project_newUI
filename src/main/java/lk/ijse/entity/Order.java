package lk.ijse.entity;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class Order {
    private String orderId;
    private String description;
    private String customerId;



}
