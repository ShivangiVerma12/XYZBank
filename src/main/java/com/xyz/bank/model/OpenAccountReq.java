package com.xyz.bank.model;

import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OpenAccountReq {

    @NotNull(message = "customer Id can not be null")
    @Min(value = 1, message = "customer Id can not be less than 1")
    private Long customerId;

    @NotNull(message = "initial credit can not be null")
    @Min(value = 0, message = "initial credit should be greater than 0")
    private BigDecimal initialCredit;
}
