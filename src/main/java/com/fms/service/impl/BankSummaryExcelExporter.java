package com.fms.service.impl;

import com.fms.models.BankSummaryReportModel;
import com.fms.service.ExportableExcelService;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BankSummaryExcelExporter implements ExportableExcelService<BankSummaryReportModel> {

    @Override
    public String[] getHeaders() {
        return new String[] {
                "Bank Name", "Transaction Date", "Fund In", "Fund Out", "Balance Transfer In",
                "Balance Transfer Out", "Unclaimed Amount", "Claimed Amount", "Manual Entry", "Current Balance"
        };
    }

    @Override
    public void populateRow(Row row, BankSummaryReportModel model) {
        row.createCell(0).setCellValue(model.getBankName());
        row.createCell(1).setCellValue(model.getTransactionDateTime() != null ? model.getTransactionDateTime().toString() : "");
        row.createCell(2).setCellValue(toDouble(model.getFundIn()));
        row.createCell(3).setCellValue(toDouble(model.getFundOut()));
        row.createCell(4).setCellValue(toDouble(model.getBalanceTransferIn()));
        row.createCell(5).setCellValue(toDouble(model.getBalanceTransferOut()));
        row.createCell(6).setCellValue(toDouble(model.getUnclaimedAmount()));
        row.createCell(7).setCellValue(toDouble(model.getClaimedAmount()));
        row.createCell(8).setCellValue(toDouble(model.getManualEntry()));
        row.createCell(9).setCellValue(toDouble(model.getCurrentBalance()));
    }

    @Override
    public String getSheetName() {
        return "Bank Summary";
    }

    private double toDouble(BigDecimal value) {
        return value != null ? value.doubleValue() : 0.0;
    }
}

