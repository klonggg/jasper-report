package com.spring.jrrepo.model.reports;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class CTT18 {
    private int id;
    private String name;
    private String code;
    private Date date;
    private double revenue;
}
