package id.co.xl.task.subscribertransaction.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Getter
@NoArgsConstructor
@Accessors(chain = true)
public class TransactionDetail {
    private String month;
    private int totalAmount;
    private int totalTransaction;
}
