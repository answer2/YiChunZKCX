package dev.answer.yichunzkcx.util;

import java.io.File;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelQueryTool {

    private Workbook workbook;

    public ExcelQueryTool() {}

    public ExcelQueryTool CreateByPath(String path) throws IOException {
        this.workbook = WorkbookFactory.create(new File(path));
        return this;
    }

    public ExcelQueryTool CreateXLS() {
        this.workbook = new XSSFWorkbook();
        return this;
    }

    public ExcelQueryTool CreateXLSX() {
        this.workbook = new HSSFWorkbook();
        return this;
    }
    
    public Sheet getSheet(int count){
        return workbook.getSheetAt(count);
    }
    
    public Sheet getSheet(){
        return workbook.getSheetAt(0);
    }
    
    public Row getProject_Row(){
        return getSheet().getRow(0);
    }

    public void close() throws IOException {
        workbook.close();
    }
}
