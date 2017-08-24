package com.atguigu.ems.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class EmsTest {
	
	//通过 CellStyle 来设定边框的样式
	@Test
	public void testBorders() throws IOException{
		Workbook wb = new HSSFWorkbook();
	    Sheet sheet = wb.createSheet("new sheet");

	    // Create a row and put some cells in it. Rows are 0 based.
	    Row row = sheet.createRow(1);

	    // Create a cell and put a value in it.
	    Cell cell = row.createCell(1);
	    cell.setCellValue(4);

	    // Style the cell with borders all around.
	    CellStyle style = wb.createCellStyle();
	    style.setBorderBottom(CellStyle.BORDER_THIN);
	    style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    style.setBorderLeft(CellStyle.BORDER_THIN);
	    style.setLeftBorderColor(IndexedColors.GREEN.getIndex());
	    style.setBorderRight(CellStyle.BORDER_THIN);
	    style.setRightBorderColor(IndexedColors.BLUE.getIndex());
	    style.setBorderTop(CellStyle.BORDER_MEDIUM_DASHED);
	    style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	    cell.setCellStyle(style);

	    // Write the output to a file
	    FileOutputStream fileOut = new FileOutputStream("i:\\workbook.xls");
	    wb.write(fileOut);
	    fileOut.close();
	}
	
	//可以通过调用 Row 的 setHeightInPoints 方法来设置单元格的行高
	//可以通过 CellStyle 来设置单元格中文本的对齐方式. 
	@Test
	public void testAlignmentOptions() throws IOException{
		Workbook wb = new XSSFWorkbook(); //or new HSSFWorkbook();

        Sheet sheet = wb.createSheet();
        Row row = sheet.createRow((short) 2);
        row.setHeightInPoints(30);

        createCell(wb, row, (short) 0, XSSFCellStyle.ALIGN_CENTER, XSSFCellStyle.VERTICAL_BOTTOM);
        createCell(wb, row, (short) 1, XSSFCellStyle.ALIGN_CENTER_SELECTION, XSSFCellStyle.VERTICAL_BOTTOM);
        createCell(wb, row, (short) 2, XSSFCellStyle.ALIGN_FILL, XSSFCellStyle.VERTICAL_CENTER);
        createCell(wb, row, (short) 3, XSSFCellStyle.ALIGN_GENERAL, XSSFCellStyle.VERTICAL_CENTER);
        createCell(wb, row, (short) 4, XSSFCellStyle.ALIGN_JUSTIFY, XSSFCellStyle.VERTICAL_JUSTIFY);
        createCell(wb, row, (short) 5, XSSFCellStyle.ALIGN_LEFT, XSSFCellStyle.VERTICAL_TOP);
        createCell(wb, row, (short) 6, XSSFCellStyle.ALIGN_RIGHT, XSSFCellStyle.VERTICAL_TOP);

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("i:\\xssf-align.xlsx");
        wb.write(fileOut);
        fileOut.close();
	}
	
	private static void createCell(Workbook wb, Row row, short column, short halign, short valign) {
        Cell cell = row.createCell(column);
        cell.setCellValue(new XSSFRichTextString("Align It"));
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cell.setCellStyle(cellStyle);
    }
	
	
	// 不能把 Date 对象作为参数直接传递给 Cell 的 setCellValue 方法.
	// 需要使用 CellStyle 来设置日期的格式. 而 CellStyle 需要由 Workbook 来进行创建
	// 设置日期格式的时候, 需要 CreationHelper
	@Test
	public void testCreateDateCell() throws IOException{
		Workbook wb = new HSSFWorkbook();
	    //Workbook wb = new XSSFWorkbook();
	    CreationHelper createHelper = wb.getCreationHelper();
	    Sheet sheet = wb.createSheet("new sheet");

	    // Create a row and put some cells in it. Rows are 0 based.
	    Row row = sheet.createRow(0);

	    // Create a cell and put a date value in it.  The first cell is not styled
	    // as a date.
	    Cell cell = row.createCell(0);
	    cell.setCellValue(new Date());

	    // we style the second cell as a date (and time).  It is important to
	    // create a new cell style from the workbook otherwise you can end up
	    // modifying the built in style and effecting not only this cell but other cells.
	    CellStyle cellStyle = wb.createCellStyle();
	    cellStyle.setDataFormat(
	        createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
	    cell = row.createCell(1);
	    cell.setCellValue(new Date());
	    cell.setCellStyle(cellStyle);

	    //you can also set date as java.util.Calendar
	    cell = row.createCell(2);
	    cell.setCellValue(Calendar.getInstance());
	    cell.setCellStyle(cellStyle);

	    // Write the output to a file
	    FileOutputStream fileOut = new FileOutputStream("i:\\workbook.xls");
	    wb.write(fileOut);
	    fileOut.close();
	}
	
	
	
	// Row: Excel 文档中的一行
	// Cell: Excel 文档中的一个单元格.
	// 可以通过调用 setCellValue 直接为单元格填充值
	@Test
	public void testCreateCell() throws IOException{
		Workbook wb = new HSSFWorkbook();
	    //Workbook wb = new XSSFWorkbook();
	    CreationHelper createHelper = wb.getCreationHelper();
	    Sheet sheet = wb.createSheet("new sheet");

	    // Create a row and put some cells in it. Rows are 0 based.
	    Row row = sheet.createRow((short)0);
	    // Create a cell and put a value in it.
	    Cell cell = row.createCell(0);
	    cell.setCellValue(1);

	    // Or do it on one line.
	    row.createCell(1).setCellValue(1.2);
	    row.createCell(2).setCellValue("This is a string");
	    row.createCell(3).setCellValue(true);

	    // Write the output to a file
	    FileOutputStream fileOut = new FileOutputStream("i:\\workbook.xls");
	    wb.write(fileOut);
	    fileOut.close();
	}
	
	// Sheet: 代表具体的工作表.
	// 可通过调用 Workbook 的 createSheet 方法来进行创建
	@Test
	public void testNewSheet() throws IOException{
	    Workbook wb = new HSSFWorkbook();
	    //Workbook wb = new XSSFWorkbook();
	    Sheet sheet1 = wb.createSheet("new sheet");
	    Sheet sheet2 = wb.createSheet("second sheet");
	    FileOutputStream fileOut = new FileOutputStream("i:\\workbook.xls");
	    wb.write(fileOut);
	    fileOut.close();
	}
	
	
	// Workbook 即为一个 Excel 文档.
	@Test
	public void testNewWorkbook() throws IOException {
		Workbook wb = new HSSFWorkbook();
		FileOutputStream fileOut = new FileOutputStream("i:\\workbook.xls");
		wb.write(fileOut);
		fileOut.close();
	}
	
	
	@Test
	public void testStringArray() {
		
		String[] value = new String[] { "  aaaaaa  ", "  bbccccc  " };
		
		//输出来看一看
		for (String val : value) {
			System.out.println("没去空格时候：--" + val + "--");
		}
		
		
    	//判断 value 是否为 String[] 类型.
    	if(value != null && value instanceof String[]){
    		//若 value 是 String[] 类型, 则把数组中的每一个元素都去除前后空格.
    		List<String> strList = new ArrayList<>();
    		String [] strs = (String[]) value;
    		for(String str: strs){
    			str = str.trim();
    			strList.add(str);
    		}
    		
    		//输出来看一看
    		System.out.println("去了空格数组是长这样："+strList);
    		
    		//再更新 value 值. 
    		value = strList.toArray(new String[strList.size()]);
    		
    		//输出来看一看
    		for (String val : value) {
    			System.out.println("去了空格后：--" + val + "--");
    		}
    		
    	}
		
	}

}
