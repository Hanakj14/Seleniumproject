package UtilityPackage;



	
	import org.apache.poi.ss.usermodel.Row;
	import org.apache.poi.ss.usermodel.Sheet;
	import org.apache.poi.ss.usermodel.Workbook;
	import org.apache.poi.ss.usermodel.WorkbookFactory;

	import java.io.FileInputStream;
	import java.io.IOException;

	public class ExcelUtil {
	    private Workbook workbook;
	    private Sheet sheet;

	    public ExcelUtil(String filePath, String sheetName) {
	        try {
	            FileInputStream file = new FileInputStream(filePath);
	            workbook = WorkbookFactory.create(file);
	            sheet = workbook.getSheet(sheetName);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public String getCellData(int row, int col) {
	        Row currentRow = sheet.getRow(row);
	        if (currentRow != null) {
	            org.apache.poi.ss.usermodel.Cell cell = currentRow.getCell(col);
	            if (cell != null) {
	                return cell.toString();
	            }
	        }
	        return "";
	    }

	    public void closeWorkbook() {
	        try {
	            if (workbook != null) {
	                workbook.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

	
