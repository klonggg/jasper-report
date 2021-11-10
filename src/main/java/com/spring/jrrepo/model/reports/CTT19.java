package com.spring.jrrepo.model.reports;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class CTT19 {
    private int id;
    private String name;
    private String code;
    private int numOfTransaction;
    private double valueOfTransaction;
    private double revenue;
    private double revenueDevelop;
    private double sumRevenue;
}
