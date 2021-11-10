package com.spring.jrrepo.model.requests;

import com.spring.jrrepo.constant.enums.ExportCode;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
public class ExportReportRequest {
    private ExportCode exportCode;
    private Date fromDate;
    private Date toDate;
    private String dataJson;
    private List<Object> data;
}
