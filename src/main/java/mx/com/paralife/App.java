package mx.com.paralife;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Hello world!
 * 
 */
public class App {
	
	public static void testConnection(String filename, String sheetName){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO import_data SELECT * FROM OPENROWSET('Microsoft.ACE.OLEDB.12.0', 'Excel 12.0;Database="
					+ filename
					+ ";HDR=NO', 'SELECT * FROM ["
					+ sheetName
					+ "$]')";
			/*String query = "INSERT INTO import_data SELECT * FROM OPENROWSET('Microsoft.Jet.OLEDB.4.0', 'Excel 8.0;Database="
					+ filename
					+ ";HDR=No', 'SELECT * FROM ["
					+ sheetName
					+ "$]')";*/
			//rs = stmt.executeQuery(excelQuery);
			stmt.execute(query);

			/*while (rs.next()) {
				System.out.println(rs.getInt("agencia_id") + " "
						+ rs.getBigDecimal("cliente_modelo") + " "
						+ rs.getString("categoria"));
			}*/
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
				if (null != stmt) {
					stmt.close();
				}
				if (null != conn) {
					conn.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static Connection getConnection() throws Exception {
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = "jdbc:sqlserver://192.168.49.2:1433;databaseName=Pruebas_Excel";
		String username = "sa";
		String password = "Admin.Paralife";
		Class.forName(driver);
		return DriverManager.getConnection(url, username, password);
	}
	
	public static List readCsv(String filename) throws IOException {
		System.out.println("begining to read file...");
		CSVReader reader = new CSVReader(new FileReader(filename));
	    List myEntries = reader.readAll();
		System.out.println("file has been read, number of rows["
				+ myEntries.size() + "]");
		System.out.println("first element = ["+((String[])(myEntries.get(0))).length+"]");
		return myEntries;
	}
	
	public static List<String> readCsvIO(String filename) throws IOException {
		System.out.println("begining to read file...");
		List<String> lines = FileUtils.readLines(new File(filename));
		System.out.println("file has been read, number of rows["
				+ lines.size() + "]");
		System.out.println("first element = [" + lines.get(0) + "]");
		return lines;
	}
	
	public static void uploadFile(String filename, String sql) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		List<String> lines = readCsvIO(filename);
		try {
		    connection = getConnection();
		    statement = connection.prepareStatement(sql);
		    Date start = new Date();
		    System.out.println("process starting at " + start + "... and runing");
		    System.out.print("...");
		    try {
		    	for (int i = 1; i < lines.size(); i++) {
		    		String line = lines.get(i);
		    		String[] elements = line.split(",");
		    		statement.setInt(1, Integer.parseInt(elements[0]));
		    		statement.setLong(2, Long.parseLong(elements[1]));
		    		statement.setString(3, elements[2]);
		    		statement.addBatch();
		    		
		    		if ((i) % 1000 == 0) {
		    			System.out.print(".");
		    			statement.executeBatch(); // Execute every 100 items.
		    		}
		    	}
		    	System.out.print(".");
		    	statement.executeBatch();
		    } catch (ArrayIndexOutOfBoundsException e) {
		    	System.out.print(".");
		    	statement.executeBatch();
		    }
		    Date end = new Date();
		    Date total = new Date(end.getTime() - start.getTime());
			System.out.println("\nFile has been uploaded in ["
					+ total.getMinutes() + ": " + total.getSeconds() + "]");
		} finally {
		    if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
		    if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
		}
	}

	public static void main(String[] args) throws Exception {
		String filename = "/Users/martinni/projects/GM_2014_07.csv";
		//ExcelHandler.readXlsx(new File(filename));
		//testConnection(filename, "ImportData");
		//readCsvIO(filename);
		uploadFile(filename, "INSERT INTO import_data VALUES (?,?,?)");
	}
	
	
}
