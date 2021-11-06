package com.prjctr.hsa.currency.model;

import java.lang.reflect.Field;

import lombok.Builder;
import lombok.SneakyThrows;

@Builder
public class GAEvent {
    @GAValueFormat(key = "v")
    private String version;
    
    @GAValueFormat(key = "tid")
    private String trackingId;

    @GAValueFormat(key = "ds")
    private String dataSource;

    @GAValueFormat(key = "cid")
    private String clientId;

    @GAValueFormat(key = "t")
    private GAEventType type;

    @GAValueFormat(key = "ec")
    private String eventCategory;

    @GAValueFormat(key = "ea")
    private String eventAction;

    @GAValueFormat(key = "el")
    private String eventLabel;

    @GAValueFormat(key = "ev")
    private String eventValue;

    @GAValueFormat(key = "dp")
    private String documentPath;

    @GAValueFormat(key = "dt")
    private String documentTitle;

    @SneakyThrows
    public String toRequestString() {
        StringBuilder sb = new StringBuilder();
        var fields = this.getClass().getDeclaredFields();        
        for (Field f : fields) {
            var fieldValue = f.get(this);
            if (fieldValue != null && !fieldValue.toString().isEmpty()) {
                String fieldName;
                String strValue = fieldValue.toString();
                if (f.isAnnotationPresent(GAValueFormat.class)) {
                    fieldName = f.getAnnotation(GAValueFormat.class).key();
                } else {
                    fieldName = f.getName();
                }

                if (f.getType().equals(GAEventType.class)) {
                    strValue = strValue.toLowerCase();
                }

                sb.append(fieldName)
                    .append("=")
                    .append(strValue)
                    .append("&");
            }
        }

        var res = sb.toString();
        if (res.length() < 1) {
            return res;
        }
        return res.substring(0, res.length() - 1);
    }
}
