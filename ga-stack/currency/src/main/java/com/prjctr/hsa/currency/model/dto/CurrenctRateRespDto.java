package com.prjctr.hsa.currency.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrenctRateRespDto {
    private String sell;
    private String buy;
    private Date date;
    private Float rateSell;
    private Float rateBuy;
}
