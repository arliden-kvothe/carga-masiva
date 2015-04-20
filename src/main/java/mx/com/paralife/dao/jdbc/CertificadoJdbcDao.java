/**
 * 
 */
package mx.com.paralife.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * @author martinni
 *
 */
public class CertificadoJdbcDao extends GenericJdbcDao {
	
	private CertificadoJdbcDao(){}
	
	private static Map<String, Integer> sucursales = new HashMap<String, Integer>();
	
	private static Connection connection;
	
	static{
		try {
			connection = getConnectionPooled();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static int getDistribuidorSucursalId(String udn){
		if(sucursales.containsKey(udn)){
			System.out.println("getting sucursal Id from cache..");
			return sucursales.get(udn);
		}
		System.out.println("searching sucursal for " + udn);
		StringBuffer sql = new StringBuffer();
		sql.append("select Distribuidor_Sucursal_Id ");
		sql.append("from distribuidor_sucursal ");
		sql.append("where distribuidor_empresa_id in (select distribuidor_empresa_id");
		sql.append("								  from distribuidor_empresa ");
		sql.append("								  where distribuidor_id = 80) ");
		sql.append("AND Distribuidor_empresa_id != 232");
		sql.append("AND Distribuidor_Sucursal_Nombre LIKE ?");
		PreparedStatement stmt;
		int distribuidorSucursalId = 0;
		try {
			//System.out.println("connection closed ? " + connection.isClosed() + "["+connection+"]");
			stmt = connection.prepareStatement(sql.toString());
			stmt.setString(1, "%" + udn + "%");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				sucursales.put(udn, new Integer(rs.getInt(1)));
				distribuidorSucursalId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return distribuidorSucursalId;
	}
	
	public static int nextCertificadoId() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT MAX(Certificado_Id) + 1 as Certificado_Id from Certificado");
		PreparedStatement stmt;
		int certificadoId = 0;
		try {
			//System.out.println("connection closed ? " + connection.isClosed() + "[" + connection + "]");
			stmt = connection.prepareStatement(sql.toString());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				certificadoId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return certificadoId;
	}
	
	public static int nextCertificadoTempId() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT MAX(Certificado_Id) + 1 as Certificado_Id from Certificado_Temp");
		PreparedStatement stmt;
		int certificadoId = 0;
		try {
			stmt = connection.prepareStatement(sql.toString());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				certificadoId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return certificadoId;
	}
	
	public static boolean insertBatch(List<String> queries){
		Statement statement;
		int i = 0;
		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			
			for (String query : queries) {
				
				statement.addBatch(query);
				//if(statement.execute(query)) i++;
				//i += statement.executeUpdate(query);
			}
			int[] results = statement.executeBatch();
			connection.commit();
			System.out.println("Number of affected raws [" + results.length + "]");
			return true;
		} catch (SQLException e) {
			System.err.println("Error in query = ["+queries.get(i)+"]");
			e.printStackTrace();
		}
		return false;
	}
	
	public static void testDateTimes(){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT CAST('13/11/2013' AS SMALLDATETIME)");
		PreparedStatement stmt;
		try {
			//System.out.println("connection closed ? " + connection.isClosed() + "[" + connection + "]");
			stmt = connection.prepareStatement(sql.toString());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				System.out.println(rs.getDate(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, Integer> getCertificadosByCertificadoFolioTemp(List<String> folios) {
		StringBuffer sql = new StringBuffer();
		sql.append("select C.Certificado_Id, T.Certificado_Folio ");
		sql.append("from Certificado C, Certificado_Temp T ");
		sql.append("where T.Certificado_Folio in (");
		sql.append(StringUtils.join(folios, ','));
		sql.append(") ");
		sql.append("and C.Poliza_Maestra_Id = 183 ");
		sql.append("and C.Certificado_Vig_Fin > getdate() ");
		sql.append("and Upper(C.Certificado_Nombre) = Upper(T.Certificado_Nombre) ");
		sql.append("and Upper(C.Certificado_Ap_pat) = Upper(T.Certificado_Ap_Pat) ");
		sql.append("and Upper(C.Certificado_Ap_Mat) = Upper(T.Certificado_Ap_Mat)");
		PreparedStatement stmt;
		Map<String, Integer> certificados = new HashMap<>();
		try {
			//System.out.println("connection closed ? " + connection.isClosed() + "[" + connection + "]");
			stmt = connection.prepareStatement(sql.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				certificados.put(rs.getString(2), rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return certificados;
	}
	
	public static Map<String, Integer> getAllCertificadosIdsByCertificadoFolioTemp() {
		StringBuffer sql = new StringBuffer();
		sql.append("select C.Certificado_Id, T.Certificado_Folio ");
		sql.append("from Certificado C, Certificado_Temp T ");
		sql.append("where C.Certificado_Folio = T.Certificado_Folio ");
		sql.append("and C.Poliza_Maestra_Id = 183 ");
		sql.append("and C.Certificado_Vig_Fin > getdate() ");
		sql.append("and C.Certificado_Estatus_Id = 1");
		PreparedStatement stmt;
		Map<String, Integer> certificados = new HashMap<>();
		try {
			//System.out.println("connection closed ? " + connection.isClosed() + "[" + connection + "]");
			stmt = connection.prepareStatement(sql.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				certificados.put(rs.getString(2), rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return certificados;
	}
}
