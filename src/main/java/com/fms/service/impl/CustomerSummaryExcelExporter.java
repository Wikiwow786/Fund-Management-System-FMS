package com.fms.service.impl;

import com.fms.models.CustomerSummaryReportModel;
import com.fms.service.ExportableExcelService;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CustomerSummaryExcelExporter implements ExportableExcelService<CustomerSummaryReportModel> {
    @Override
    public String[] getHeaders() {
        return new String[] {
                "Customer Name", "Transaction Date", "Total Fund In", "Total Fund Out",
                "Balance Transfer In", "Balance Transfer Out", "Claimed Amount",
                "Total", "Total Fee", "Total Commission", "Current Balance"
        };
    }

    @Override
    public void populateRow(Row row, CustomerSummaryReportModel model) {
        row.createCell(0).setCellValue(model.getCustomerName());
        row.createCell(1).setCellValue(model.getTransactionDateTime().toString());
        row.createCell(2).setCellValue(toDouble(model.getTotalFundIn()));
        row.createCell(3).setCellValue(toDouble(model.getTotalFundOut()));
        row.createCell(4).setCellValue(toDouble(model.getBalanceTransferIn()));
        row.createCell(5).setCellValue(toDouble(model.getBalanceTransferOut()));
        row.createCell(6).setCellValue(toDouble(model.getClaimedAmount()));
        row.createCell(7).setCellValue(toDouble(model.getTotal()));
        row.createCell(8).setCellValue(toDouble(model.getTotalFee()));
        row.createCell(9).setCellValue(toDouble(model.getTotalCommission()));
        row.createCell(10).setCellValue(toDouble(model.getCurrentBalance()));
    }

    @Override
    public String getSheetName() {
        return "Customer Summary";
    }

    private double toDouble(BigDecimal value) {
        return value != null ? value.doubleValue() : 0.0;
    }
}
