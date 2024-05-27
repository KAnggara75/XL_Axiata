package id.co.xl.task.subscribertransaction.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetMonthlyTransactionRq {

    @NotBlank
    @NotNull
    private String msisdn;
    
    @NotBlank
    @NotNull
    private String pin;
}
