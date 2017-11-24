package io.dactilo.sumbawa.spring.spreadsheets.converter.excel;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XSFFWorkbokFactory {
    public XSSFWorkbook createInstance() {
        return new XSSFWorkbook();
    }
}
