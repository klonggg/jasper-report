package com.spring.jrrepo.model;

import com.spring.jrrepo.constant.enums.ExportCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Reports {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String code;
    private String fromDate;
    private String toDate;
    @Getter
    private String uri;

    public Reports() {
    }

    public Reports(String code, String fromDate, String toDate, String uri) {
        this.code = code;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.uri = uri;
    }
}
