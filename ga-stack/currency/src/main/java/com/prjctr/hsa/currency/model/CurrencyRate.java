package com.prjctr.hsa.currency.model;

import java.util.Currency;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrencyRate {
    private Currency sell;
    private Currency buy;
    private Date date;
    private Float rateSell;
    private Float rateBuy;
}
