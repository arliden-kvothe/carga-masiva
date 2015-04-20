/**
 * 
 */
package mx.com.paralife.io;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static java.lang.System.out;

/**
 * @author Eduardo Barcenas
 * 
 */
public class ExcelHandler {

	private ExcelHandler() {
	}

	public static void readXlsx(File inputFile) {
		try {
			out.println("reading file...");
			// Get the workbook instance for XLSX file
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(inputFile));
			SXSSFWorkbook book = new SXSSFWorkbook(wb);
			out.println("Get first sheet from the workbook...");
			// Get first sheet from the workbook
			//XSSFSheet sheet = wb.getSheetAt(0);
			Sheet sheet = book.getSheetAt(0);
			//sheet.
			Row row;
			Cell cell;
			out.println("Iterate through each rows from first sheet...");
			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				row = rowIterator.next();

				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				out.println(row.getLastCellNum());
				while (cellIterator.hasNext()) {
					cell = cellIterator.next();

					switch (cell.getCellType()) {

					case Cell.CELL_TYPE_BOOLEAN:
						out.println(cell.getBooleanCellValue());
						break;

					case Cell.CELL_TYPE_NUMERIC:
						out.println(cell.getNumericCellValue());
						break;

					case Cell.CELL_TYPE_STRING:
						out.println(cell.getStringCellValue());
						break;

					case Cell.CELL_TYPE_BLANK:
						out.println(" ");
						break;

					default:
						out.println(cell);

					}
				}
			}
		} catch (Exception e) {
			System.err.println("Exception :" + e.getMessage());
		}
	}

}
