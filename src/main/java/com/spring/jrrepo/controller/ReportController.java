package com.spring.jrrepo.controller;

import com.spring.jrrepo.model.requests.ExportReportRequest;
import com.spring.jrrepo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService service;

    @GetMapping()
    public String getReport(@RequestBody ExportReportRequest request) throws FileNotFoundException {

        return service.genReport(request);
    }

}
