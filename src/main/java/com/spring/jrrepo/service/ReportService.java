package com.spring.jrrepo.service;

import com.spring.jrrepo.model.requests.ExportReportRequest;

import java.io.FileNotFoundException;

public interface ReportService {
	String genReport(ExportReportRequest request) throws FileNotFoundException;
}
