package mx.com.paralife;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.paralife.dao.jdbc.CertificadoJdbcDao;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author martinni
 * 
 */
public class PepsicoCM {

	private static final List<String> columnas = Arrays.asList(
			"Certificado_Id", "Poliza_Maestra_Id", "Distribuidor_Sucursal",
			"Certificado_Folio", "Certificado_Estatus_Id",
			"Certificado_Reg_Fec", "Certificado_Vig_Ini", "Certificado_Nombre",
			"Certificado_Ap_Pat", "Certificado_Ap_Mat",
			"Certificado_Fec_Nac", "Certificado_Sexo_Masculino",
			"Certificado_Direccion", "Certificado_CP");

	private static final File DIR = new File("/Users/martinni/Dropbox/Code");

	@Test
	public void firstTest() {
		try {
			List<String> csvCert = new ArrayList<>();
			List<String> lines = FileUtils.readLines(new File(DIR,
					"Prod_PepsiCo_NORTE_20150320.csv"), "ISO-8859-1");
			lines.remove(0); // eliminando cabecera
			int i = 0;
			int maxCertId = CertificadoJdbcDao.nextCertificadoTempId();
			System.out.println("formando sql statements...");
			for (String line : lines) {
				String[] tokens = line.split("\\[");
				System.out.println("tokens = " + tokens.length);
				
				String certId = (++maxCertId) + "";
				String certEstatus = "1";
				String certNombre = "'" + tokens[4].trim().toUpperCase() + "'";
				String certApPaterno = "'" + tokens[5].trim().toUpperCase() + "'";
				String certApMaterno = "'" + tokens[6].trim().toUpperCase() + "'";
				String certDireccion = "'" + tokens[18].replaceAll("'", "''")
						+ "'";

				String certCP = "'" + validarCP(tokens[12].trim()) + "'";
				String certSexo = (tokens[7].trim().equals("M") ? 1 : 0) + "";
				String certFechaNacimiento = "CAST('" + tokens[8].trim() + "' AS SMALLDATETIME)";
				String certRegFec = "CAST('" + tokens[19].trim() + "' AS SMALLDATETIME)";
				String certVigIni = "CAST('" + tokens[9].trim() + "' AS SMALLDATETIME)";
				String polizaMaestraId = "183";

				String certFolio = "'" + tokens[1].trim() + "'";
				//String distribuidorSucursalId = CertificadoJdbcDao.getDistribuidorSucursalId(tokens[3].trim()) + "";
				String distribuidorSucursalId = "'" + tokens[2].trim() + "'";

				List<String> lineaDelimitada = new ArrayList<>();
				lineaDelimitada.add(certId);
				lineaDelimitada.add(polizaMaestraId);
				lineaDelimitada.add(distribuidorSucursalId);
				lineaDelimitada.add(certFolio);
				lineaDelimitada.add(certEstatus);
				lineaDelimitada.add(certRegFec);
				lineaDelimitada.add(certVigIni);
				lineaDelimitada.add(certNombre);
				lineaDelimitada.add(certApPaterno);
				lineaDelimitada.add(certApMaterno);
				lineaDelimitada.add(certFechaNacimiento);
				lineaDelimitada.add(certSexo);
				lineaDelimitada.add(certDireccion);
				lineaDelimitada.add(certCP);

				String sql = generarCertificadoSqlInsert(lineaDelimitada);
				// csvCert.add(StringUtils.join(lineaDelimitada, ","));
				csvCert.add(sql);

				lineaDelimitada = null;
				if (++i > 4) {
					System.out.println("Executing for certificado...");
					System.out.println(csvCert);
					if(!CertificadoJdbcDao.insertBatch(csvCert)){
						Assert.fail("la insercion de datos en la tabla Certificado ha fallado...");
						return;
					}
					csvCert.clear();
					i = 0;
				}
			}
			System.out.println("sending last registries for certificado...");
			if (!CertificadoJdbcDao.insertBatch(csvCert)) {
				Assert.fail("la insercion de datos en la tabla Certificado ha fallado...");
				return;
			}
			// FileUtils.writeLines(new File("dispersion.csv"), csvCert);
			// FileUtils.writeLines(new File("dispersion_hist.csv"), csvHist);
			//CertificadoJdbcDao.testDateTimes();
			Assert.assertTrue(true);
		} catch (IOException e) {
			Assert.fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} finally {
			CertificadoJdbcDao.releasePool();
		}
	}

	private String validarCP(String cp) {
		if (!StringUtils.isNumeric(cp)) {
			return "";
		}
		if (cp.length() > 5) {
			return "";
		}
		return cp;
	}

	private String generarCertificadoSqlInsert(List<String> values) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO Certificado_Temp ");
		sql.append(" VALUES (");
		sql.append(StringUtils.join(values, ","));
		sql.append(");");
		return sql.toString();
	}
	
	//@Test
	public void updateCertificadoFolios() {
		String[] foliosArray = { "601025226", "601027055", "601026069", "528025806",
				"601026940", "528025448", "528027273", "541025677",
				"601025202", "528026681", "541026024", "541025134",
				"541025842", "528026273", "541025519", "528026859",
				"601026394", "528026732", "528026716", "541027049",
				"541025676", "528026575", "528027283", "541026053",
				"528025132", "601027299", "541025576", "541025170",
				"601026082", "528026892", "603025362", "528025410",
				"528025133", "601026527", "541026092", "528026089",
				"541025456", "541026198", "528025591", "541026005",
				"528026548", "541025160", "528026399", "528026366",
				"601025490", "601025438", "541025341", "601025364",
				"519027384", "601026076", "528026576", "541026577",
				"528025349", "541026452", "519025999", "541025622",
				"528025792", "541027250", "541025853", "528025971",
				"601026378", "519025386", "541025692", "601025223",
				"541026950", "519026341", "601025600", "541026860",
				"601026149", "541026661", "541026603", "528027330",
				"601025459", "541025638", "601026570", "601025127" };
		List<String> folios = Arrays.asList(foliosArray);
		System.out.println(folios.size());
		
		Map<String, Integer> certificados = CertificadoJdbcDao.getCertificadosByCertificadoFolioTemp(folios);
		
		Assert.assertNotEquals(0, certificados.size());
		Assert.assertEquals(76, certificados.size());
		System.out.println(certificados);
		
		List<String> sqls = generarCertificadoSqlUpdate(certificados);
		
		Assert.assertNotEquals(0, sqls.size());
		Assert.assertEquals(76, sqls.size());
		
		System.out.println(sqls);
		
		if (!CertificadoJdbcDao.insertBatch(sqls)) {
			Assert.fail("la insercion de datos en la tabla Certificado ha fallado...");
		} else {
			Assert.assertTrue(true);
		}
	}
	
	//@Test
	public void updateCertificadoSucursales() {
		try {
			List<String> lines = FileUtils.readLines(new File(DIR,
					"Prod_PepsiCo_20150321.csv"), "ISO-8859-1");
			Map<String, String> cedisMap = new HashMap<String, String>();
			for (String line : lines) {
				String[] tokens = line.split("\\|");
				cedisMap.put(tokens[1], tokens[2]);
			}
			Assert.assertNotEquals(0, cedisMap.size());
			Assert.assertEquals(2622, cedisMap.size());
			
			Map<String, Integer> certificados = CertificadoJdbcDao.getAllCertificadosIdsByCertificadoFolioTemp();
			Assert.assertNotEquals(0, certificados.size());
			Assert.assertEquals(2619, certificados.size());
			System.out.println(certificados);
			List<String> sqls = new ArrayList<>();
			int i = 0;
			int j = 1;
			for (String folio : certificados.keySet()) {
				String cedis = cedisMap.get(folio);
				sqls.add(generarSucursalSqlUpdate(certificados.get(folio), cedis));
				if (++i > 4) {
					System.out.println("Executing batch ["+(j++)+"]...");
					System.out.println(sqls);
					if(!CertificadoJdbcDao.insertBatch(sqls)){
						Assert.fail("la insercion de datos en la tabla Certificado ha fallado...");
						return;
					}
					sqls.clear();
					i = 0;
				}
			}
			System.out.println("Executing batch ["+(j++)+"]...");
			System.out.println(sqls);
			if(!sqls.isEmpty() && !CertificadoJdbcDao.insertBatch(sqls)){
				Assert.fail("la insercion de datos en la tabla Certificado ha fallado...");
				return;
			}
			Assert.assertTrue(true);
		} catch (IOException e) {
			Assert.fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} finally {
			CertificadoJdbcDao.releasePool();
		}
	}
	
	private String generarSucursalSqlUpdate(Integer certificadoId, String cedis) {
		StringBuilder sql = new StringBuilder();
		sql.append("update Certificado ");
		sql.append("set Distribuidor_Sucursal_Id = ( ");
		sql.append("	select Distribuidor_Sucursal_Id  ");
		sql.append("	from Distribuidor_Sucursal  ");
		sql.append("	where Distribuidor_Empresa_Id = 286 ");
		sql.append("	and Distribuidor_Sucursal_Cve = '").append(cedis).append("') ");
		sql.append("where Certificado_Id = ").append(certificadoId).append(";");
		return sql.toString();
	}

	private List<String> generarCertificadoSqlUpdate(Map<String, Integer> values) {
		List<String> sqls = new ArrayList<>();
		for(String folio : values.keySet()){
			StringBuffer sql = new StringBuffer();
			sql.append("Update Certificado ");
			sql.append("set Certificado_Folio = '").append(folio).append("', ");
			sql.append("	Certificado_Cliente_Cve = '").append(folio).append("' ");
			sql.append("where Certificado_Id = ").append(values.get(folio));
			sql.append(";");
			sqls.add(sql.toString());
			sql = null;
		}
		return sqls;
	}

}
