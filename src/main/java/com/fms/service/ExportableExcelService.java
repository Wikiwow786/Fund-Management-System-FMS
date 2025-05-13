package com.fms.service;

import org.apache.poi.ss.usermodel.Row;

public interface ExportableExcelService<T> {
    String[] getHeaders();
    void populateRow(Row row, T data);
    String getSheetName();
}
