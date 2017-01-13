package com.wsc.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author:WangShiChao
 * @createTime:2014-12-2上午11:38:01
 * @version:1.0
 * @Description	: 
 */
public class ExcelFileUtil {

	/**
	 * @author：WangShiChao
	 * @Description	: 将数据写入到工作表中
	 * @param sheetName
	 * @param dataList
	 * @return
	 */
	public static HSSFWorkbook createSheet(String sheetName,List<List<String>> dataList)
	{
		//创建新的EXCEL工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建字体，黑色、粗体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFFont.COLOR_NORMAL);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//创建单元格的格式，如剧中、左对齐等
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		//水平方向上居中对齐
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
		//设置字体
		cellStyle.setFont(font);
		//创建工作表
		HSSFSheet sheet = workbook.createSheet(sheetName);
		if(dataList == null || dataList.size() == 0)
		{
			return null;
		}
		for(int rowNum=0;rowNum<dataList.size();rowNum++)
		{
			//创建当前记录行
			HSSFRow row = sheet.createRow(rowNum);
			//当前行的数据
			List<String> rowData = dataList.get(rowNum);
			int colSize = rowData.size();
			//创建单元格
			HSSFCell cell = null;
			for(int colNum=0;colNum<colSize;colNum++)
			{
				cell = row.createCell(colNum);
				//定义单元格字符类型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				//设置单元格格式
				cell.setCellStyle(cellStyle);
				//设置单元格内容
				String value = rowData.get(colNum);
				cell.setCellValue(value);
			}
		}
		return workbook;
	}
	
	/**
	 * @author：WangShiChao
	 * @Description	: 将内存中的表数据写到文件中
	 * @param fileName
	 * @param workbook
	 * @return
	 */
	public static boolean writeToExcelFile(String fileName,HSSFWorkbook workbook)
	{
		//目标文件
		File file = new File(fileName);
		FileOutputStream fout = null;		
		try {
			//工作薄建立完成，输出到文件流
			fout = new FileOutputStream(file);
			//把相应的excel工作薄存放到输出流
			workbook.write(fout);
			fout.flush();
			//操作结束,关闭文件
			fout.close();
			//返回工作表对象
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}
