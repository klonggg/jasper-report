package com.spring.jrrepo.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.jrrepo.model.Reports;
import com.spring.jrrepo.model.reports.CTT17;
import com.spring.jrrepo.model.reports.CTT18;
import com.spring.jrrepo.model.reports.CTT19;
import com.spring.jrrepo.model.reports.CTT20;
import com.spring.jrrepo.model.requests.ExportReportRequest;
import com.spring.jrrepo.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {
	@Autowired
	private ReportRepository reportRepository;
	
	@Value("${jasper.report.path}")
	private String jrRepoPath;

	private File fileTemplate;
	private Map<String, Object> params;
	private JRBeanCollectionDataSource dataSources;
	private final SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");
	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public String genReport(ExportReportRequest request) throws FileNotFoundException {
		Reports reports = reportRepository.getReport(request.getExportCode().toString(),
				sm.format(request.getFromDate()), sm.format(request.getToDate()));
		if (reports == null) {
			switch (request.getExportCode()) {
				case CTT17:
					params = new HashMap<>();
					params.put("fromDate", sm.format(request.getFromDate()));
					params.put("toDate", sm.format(request.getToDate()));
					params.put("templateCode", "CTT17");
					params.put("templateName", "Báo cáo doanh thu giao dịch cổng thanh toán điện tử".toUpperCase(Locale.ROOT));
					fileTemplate = ResourceUtils.getFile("classpath:templates/ctt17.jrxml");
					List<CTT17> data17 = mapper.convertValue(request.getData(), new TypeReference<List<CTT17>>() {});
					int sum17_1 = data17.stream().map(CTT17::getNumOfTransaction).reduce(0, Integer::sum);
					double sum17_2 = data17.stream().map(CTT17::getValueOfTransaction).reduce(0.0d, Double::sum);
					double sum17_3 = data17.stream().map(CTT17::getRevenue).reduce(0.0d, Double::sum);
					params.put("sum1", sum17_1);
					params.put("sum2", sum17_2);
					params.put("sum3", sum17_3);
					dataSources = new JRBeanCollectionDataSource(data17);
					break;
				case CTT18:
					params = new HashMap<>();
					params.put("fromDate", sm.format(request.getFromDate()));
					params.put("toDate", sm.format(request.getToDate()));
					params.put("templateCode", "CTT18");
					params.put("templateName", "Báo cáo doanh thu phát triển ĐVCNTT".toUpperCase(Locale.ROOT));
					fileTemplate = ResourceUtils.getFile("classpath:templates/ctt18.jrxml");
					List<CTT18> data18 = mapper.convertValue(request.getData(), new TypeReference<List<CTT18>>() {});
					double sum18_2 = data18.stream().map(CTT18::getRevenue).reduce(0.0d, Double::sum);
					params.put("sum1", data18.size());
					params.put("sum2", sum18_2);
					dataSources = new JRBeanCollectionDataSource(data18);
					break;
				case CTT19:
					params = new HashMap<>();
					params.put("fromDate", sm.format(request.getFromDate()));
					params.put("toDate", sm.format(request.getToDate()));
					params.put("templateCode", "CTT19");
					params.put("templateName", "Báo cáo tổng hợp doanh thu dịch vụ cổng thanh toán điện tử".toUpperCase(Locale.ROOT));
					fileTemplate = ResourceUtils.getFile("classpath:templates/ctt19.jrxml");
					List<CTT19> data19 = mapper.convertValue(request.getData(), new TypeReference<List<CTT19>>() {});
					int sum19_1 = data19.stream().map(CTT19::getNumOfTransaction).reduce(0, Integer::sum);
					double sum19_2 = data19.stream().map(CTT19::getValueOfTransaction).reduce(0.0d, Double::sum);
					double sum19_3 = data19.stream().map(CTT19::getRevenue).reduce(0.0d, Double::sum);
					double sum19_4 = data19.stream().map(CTT19::getRevenueDevelop).reduce(0.0d, Double::sum);
					double sum19_5 = data19.stream().map(CTT19::getSumRevenue).reduce(0.0d, Double::sum);
					params.put("sum1", sum19_1);
					params.put("sum2", sum19_2);
					params.put("sum3", sum19_3);
					params.put("sum4", sum19_4);
					params.put("sum5", sum19_5);
					dataSources = new JRBeanCollectionDataSource(data19);
					break;
				case CTT20:
					params = new HashMap<>();
					params.put("fromDate", sm.format(request.getFromDate()));
					params.put("toDate", sm.format(request.getToDate()));
					params.put("templateCode", "CTT20");
					params.put("templateName", "Báo cáo nhận tiền thanh toán cho đại lý".toUpperCase(Locale.ROOT));
					fileTemplate = ResourceUtils.getFile("classpath:templates/ctt20.jrxml");
					List<CTT20> data20 = mapper.convertValue(request.getData(), new TypeReference<List<CTT20>>() {});
					double sum20 = data20.stream().map(CTT20::getAmount).reduce(0.0d, Double::sum);
					params.put("sum1", sum20);
					dataSources = new JRBeanCollectionDataSource(data20);
					break;
				default:
					return "Không tìm thấy báo cáo";
			}
			return genReport();
		} else {
			System.out.println("has!!!!!");
			return reports.getUri();
		}

	}

	private String genReport(){
		try {
			JasperReport jasperReport = JasperCompileManager.compileReport(fileTemplate.getAbsolutePath());
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSources);
			String desFile = getDestinationFile();
			JasperExportManager.exportReportToPdfFile(jasperPrint, desFile);
			reportRepository.save(new Reports(params.get("templateCode").toString(), params.get("fromDate").toString(), params.get("toDate").toString(), desFile));
			return desFile;
		} catch (JRException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getDestinationFile() {
		return jrRepoPath +
				"Report-" +
				params.get("templateCode") +
				"-" +
				params.get("fromDate").toString().replaceAll("/", "") +
				params.get("toDate").toString().replaceAll("/", "") +
				".pdf";
	}


}
