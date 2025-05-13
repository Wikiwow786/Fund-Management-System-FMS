package com.fms.service.impl;

import com.fms.models.CommissionSummaryReportModel;
import com.fms.service.ExportableExcelService;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CommissionSummaryExcelExporter implements ExportableExcelService<CommissionSummaryReportModel> {

    @Override
    public String[] getHeaders() {
        return new String[] { "Revenue Account", "Summary Date", "Commission" };
    }

    @Override
    public void populateRow(Row row, CommissionSummaryReportModel model) {
        row.createCell(0).setCellValue(model.getRevenueAccount());
        row.createCell(1).setCellValue(model.getSummaryDate() != null ? model.getSummaryDate().toString() : "");
        row.createCell(2).setCellValue(toDouble(model.getCommissionAmount()));
    }

    @Override
    public String getSheetName() {
        return "Commission Summary";
    }

    private double toDouble(BigDecimal value) {
        return value != null ? value.doubleValue() : 0.0;
    }
}

