package com.netsky.shop.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.netsky.shop.domain.base.Tbl14_column;

public class ExcelWrite {
	private Workbook workbook;
	private Sheet sheet;
	private Row row;
	private Cell cell;
	private CellStyle cellStyle;
	private Font font;
	private List<Tbl14_column> titleList;
	private List<List<Object>> rowList;
	private String title;

	public ExcelWrite() {
	}

	public ExcelWrite(Workbook workbook) {
		this.workbook = workbook;
	}

	public void setTitleList(List<Tbl14_column> titleList) {
		this.titleList = titleList;
	}

	public void setRowList(List<List<Object>> rowList) {
		this.rowList = rowList;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void write(String sheetname) {
		if (sheetname != null && !"".equals(sheetname))
			sheet = workbook.createSheet(sheetname);
		else
			sheet = workbook.createSheet();
		int i = 0;
		font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 450);
		cellStyle = workbook.createCellStyle();
		cellStyle.setBorderLeft(CellStyle.BORDER_NONE);
		cellStyle.setBorderRight(CellStyle.BORDER_NONE);
		cellStyle.setBorderBottom(CellStyle.BORDER_NONE);
		cellStyle.setBorderTop(CellStyle.BORDER_NONE);
		cellStyle.setFont(font);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		if (title != null && !"".equals(title)) {
			row = sheet.createRow(i++);
			row.setHeight((short) 500);
			if (titleList != null)
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, titleList.size()-1));
			cell = row.createCell(0);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(title);
		}
		row = sheet.createRow(i);
		row.setHeight((short) 400);

		if (titleList != null) {
			font = workbook.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			font.setFontName("宋体");
			font.setFontHeight((short) 250);
			cellStyle = workbook.createCellStyle();
			cellStyle.setFont(font);
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			cellStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
			cellStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
			cellStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
			cellStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
			for (int j = 0; j < titleList.size(); j++) {
				cell = row.createCell(j);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(titleList.get(j).getTitle());
				sheet.setColumnWidth(j, titleList.get(j).getWidth()*36);
			}
		}
		titleList = null;
		if (rowList != null)
			for (int rowNum = 0; rowNum < rowList.size(); rowNum++) {
				List<?> list = rowList.get(rowNum);
				row = sheet.createRow(++i);
				for (int j = 0; j < list.size(); j++) {
					cell = row.createCell(j);
					font.setFontHeight((short) 230);
					font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
					cellStyle = workbook.createCellStyle();
					cellStyle.setFont(font);
					cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
					cellStyle.setBorderRight(CellStyle.BORDER_THIN);
					cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
					cellStyle.setBorderTop(CellStyle.BORDER_THIN);
					cell.setCellStyle(cellStyle);
					if (list.get(j) instanceof Integer || list.get(j) instanceof Long || list.get(j) instanceof Double
							|| list.get(j) instanceof Float || list.get(j) instanceof BigDecimal|| list.get(j) instanceof BigInteger
							) {
						cell.setCellType(0);
						cell.setCellValue(ConvertUtil.toDouble(list.get(j)));
					} else if (list.get(j) instanceof String) {
						cell.setCellValue(ConvertUtil.toString(list.get(j)));
					}else{
						if (list.get(j)!=null)
						System.out.println(list.get(j).getClass());
						else {
							cell.setCellValue("");
						}
					}
				}
			}
		rowList = null;
	}

	public Workbook getWorkbook() {
		workbook.setSheetOrder("销售汇总", 0);
		return workbook;
	}

}
