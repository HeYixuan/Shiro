/**
 * 
 */
package org.springframe.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 导入Excel文件（支持“XLS”和“XLSX”格式）
 * 
 * @author HeYixuan
 * @version 2016-07-15
 */
public class ImportExcel {
	private int totalRows = 0; // 总行数
	private int totalCells = 0; // 总列数
	private OutputStream out = null;
	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null;
	private HSSFRow row = null; // 定制浮点数格式
	private static String NUMBER_FORMAT = "#,##0.00";// 定制日期格式
	private static String DATE_FORMAT = "m/d/yy"; // "m/d/yy h:mm"

	// 根据文件名读取excel文件
	public List<ArrayList<String>> read(String fileName, int sheetNo, int beginRowNo, int endRowNo) {
		List<ArrayList<String>> dataLst = new ArrayList<ArrayList<String>>();
		// 检查文件名是否为空或者是否是Excel格式的文件
		if (fileName == null || !fileName.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
			return dataLst;
		}
		boolean isExcel2003 = true; // 对文件的合法性进行验
		if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
			isExcel2003 = false;
		} // 检查文件是否存在
		File file = new File(fileName);
		if (file == null || !file.exists()) {
			return dataLst;
		}
		try { // 读取excel
			dataLst = read(new FileInputStream(file), isExcel2003, sheetNo, beginRowNo, endRowNo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dataLst;
	}

	// 根据流读取Excel文件
	public List<ArrayList<String>> read(InputStream inputStream, boolean isExcel2003, int sheetNo, int beginRowNo,
			int endRowNo) {
		List<ArrayList<String>> dataList = null;
		try {
			// 根据版本选择创建Workbook的方式
			//HSSFWorkbook XSSFWorkbook
			Workbook wb = isExcel2003 ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream);
			dataList = read(wb, sheetNo, beginRowNo, endRowNo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataList;
	}

	// 得到总行数
	public int getTotalRows() {
		return totalRows;
	}

	// 得到总列数
	public int getTotalCells() {
		return totalCells;
	}

	// 读取数据
	private List<ArrayList<String>> read(Workbook wb, int sheetNo, int beginRowNo, int endRowNo) {
		List<ArrayList<String>> dataLst = new ArrayList<ArrayList<String>>();

		Sheet sheet = wb.getSheetAt(sheetNo - 1);// 得到第一个shell
		this.totalRows = sheet.getPhysicalNumberOfRows();
		if (this.totalRows >= 1 && sheet.getRow(0) != null) {
			this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}
		// 循环Excel的行
		// for (int r = 0; r < this.totalRows; r++) {
		for (int r = beginRowNo - 1; r < endRowNo; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			ArrayList<String> rowLst = new ArrayList<String>();
			// 循环Excel的列
			for (short c = 0; c < this.getTotalCells(); c++) {
				Cell cell = row.getCell(c);
				String cellValue = "";
				if (cell == null) {
					rowLst.add(cellValue);
					continue;
				}
				// 处理数字型的,自动去零
				if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
					// 在excel里,日期也是数字,在此要进行判断
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						cellValue = DateUtils.get4yMdHms(cell.getDateCellValue());
					} else {
						cellValue = getRightStr(cell.getNumericCellValue() + "");
					}
				} else if (Cell.CELL_TYPE_STRING == cell.getCellType()) { // 处理字符串型
					cellValue = cell.getStringCellValue();
				} else if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) { // 处理布尔型
					cellValue = cell.getBooleanCellValue() + "";
				} else { // 其它数据类型
					cellValue = cell.toString() + "";
				}
				rowLst.add(cellValue);
			}
			dataLst.add(rowLst);
		}
		return dataLst;
	}

	// 正确地处理整数后自动加零的情况
	private String getRightStr(String sNum) {
		DecimalFormat decimalFormat = new DecimalFormat("#.000000");
		String resultStr = decimalFormat.format(new Double(sNum));
		if (resultStr.matches("^[-+]?\\d+\\.[0]+$")) {
			resultStr = resultStr.substring(0, resultStr.indexOf("."));
		}
		return resultStr;
	}

	/**
	 * 初始化write
	 */
	public void initWrite(OutputStream out) {
		this.out = out;
		this.workbook = new HSSFWorkbook();
		this.sheet = workbook.createSheet();
	}

	/**
	 * 导出Excel文件
	 * 
	 * @throws Exception
	 */
	public void export() throws Exception {
		try {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			throw new Exception("生成导出Excel文件出错! ", e);
		} catch (IOException e) {
			throw new Exception("写入Excel文件出错! ", e);
		}
	}

	/**
	 * 增加一行
	 * 
	 * @param index
	 *            行号
	 */
	public void createRow(int index) {
		this.row = this.sheet.createRow(index);
	}

	/**
	 * 设置单元格
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, int value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
	}

	/**
	 * 设置单元格
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, double value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		HSSFDataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // 设置cell样式为定制的浮点数格式
		cell.setCellStyle(cellStyle); // 设置该cell浮点数的显示格式
	}

	/**
	 * 设置单元格
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, String value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(value);
	}
	
	/**
	 * 设置单元格头部
	 * @param e
	 * @param title
	 */
	public static void setCell(ImportExcel e,String [] title){

		e.createRow(0);
		for (int i = 0; i < title.length; i++) {
	        e.setCell(i, title[i]);
		}
		
	}
	
	/*public static void setCell(ImportExcel e,List<Calendar>list){
		for (int i = 0; i < list.size(); i++) {
			e.createRow(i);
			e.setCell(i+1, list.get(i).toString());
		}
	}*/

	/**
	 * 设置单元格
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, Calendar value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellValue(value.getTime());
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(DATE_FORMAT)); // 设置cell样式为定制的日期格式
		cell.setCellStyle(cellStyle); // 设置该cell日期的显示格式
	}
	
	
	public static void main(String[] args) {
		ImportExcel e = new ImportExcel();
		

        System.out.println(" 开始导出Excel文件 ");
        File f = new File("d://哈喽.xlsx");
        try {
            e.initWrite(new FileOutputStream(f));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        
        String title[] = {"编号","成绩","搞什么","不清楚","烦死了","郁闷"};
        List<String>objects = new ArrayList<String>();
        objects.add("A1");
        objects.add("A1");
        objects.add("A1");
        objects.add("A1");
        objects.add("A1");
        objects.add("A1");
        
        
        /*e.createRow(0);
        e.setCell(0, "试题编码 ");
        e.setCell(1, "题型");
        e.setCell(2, "分值");
        e.setCell(3, "难度");
        e.setCell(4, "级别");
        e.setCell(5, "知识点");*/
        e.setCell(e, title);
        
        /*for (int i = 1; i < objects.size(); i++) {
			e.createRow(i);
			e.setCell(i,objects.get(i).toString() );
		}*/

        for(int i=1;i<10;i++)
        {
            e.createRow(i);
            e.setCell(0, "t"+i);
            e.setCell(1, 1+i);
            e.setCell(2, 3.0+i);
            e.setCell(3, 1+i);
            e.setCell(4, "重要"+i);
            e.setCell(5, "专业"+i);
        }
        try {
            e.export();
            System.out.println(" 导出Excel文件[成功] ");
        } catch (Exception ex) {
            System.out.println(" 导出Excel文件[失败] ");
            ex.printStackTrace();
        }
    }
}
