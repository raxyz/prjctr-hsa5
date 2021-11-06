package com.prjctr.hsa.currency.model.dto;

import java.util.Date;
import lombok.Data;

@Data
public class MonoCurrencyDto {
    private Integer currencyCodeA;
    private Integer currencyCodeB;
    private Date date;
    private Float rateSell;
    private Float rateBuy;
    private Float rateCross;
}
