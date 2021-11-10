package com.spring.jrrepo.model.reports;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class CTT20 {
    private int id;
    private String name;
    private String transactionId;
    private String bankName;
    private String addressBank;
    private String accountNumber;
    private double amount;
    private String note;
    private Date timeTransfer;
    private String status;
}
