package mx.com.paralife;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import mx.com.paralife.dao.jdbc.CertificadoJdbcDao;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class UnileverCM {
	
	private static final List<String> compuestos = Arrays.asList("DE", "DEL", "LA", "LAS", "LOS", "S", "A", "C", "V");
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static final File DIR = new File("/Users/martinni/Dropbox/Code");
	private static final String[] callesIncorrectas = {"KM31,6","KM6,5", "KM52,5"};
	private static final String[] callesCorrectas = {"KM31 6","KM6 5", "KM52 5"};
	@Test
	public void load(){
		try{
			List<String> csvCert = new ArrayList<>();
			//csvCert.add(escribirColumnas("ColumnasCert.txt"));
			List<String> csvHist = new ArrayList<>();
			//csvHist.add(escribirColumnas("ColumnasHist.txt"));
			List<String> lines = FileUtils.readLines(new File(DIR, "danos-tenderos.csv"), "ISO-8859-1");
			lines.remove(0);
			int i = 0;
			int consecutivo = 1;
			int maxCertId = CertificadoJdbcDao.nextCertificadoId();
			System.out.println("formando sql statements...");
			for (String line : lines) {
				line = line.replaceAll("\"", "");
				if (line.contains(callesIncorrectas[0])) {
					line=line.replaceAll(callesIncorrectas[0], callesCorrectas[0]);
				} else if (line.contains(callesIncorrectas[1])) {
					line=line.replaceAll(callesIncorrectas[1], callesCorrectas[1]);
				} else if (line.contains(callesIncorrectas[2])) {
					line=line.replaceAll(callesIncorrectas[2], callesCorrectas[2]);
				}
				String[] tokens = line.split(",");
				String certId = (maxCertId++) + "";
				String certClienteCve = "'"+tokens[0]+"'";
				List<String> nombreCompleto = dispersarNombre(tokens[1].trim());
				String certNombre = "'"+nombreCompleto.get(0)+"'";
				String certApPaterno = "'"+nombreCompleto.get(1)+"'";
				String certApMaterno = "'"+nombreCompleto.get(2)+"'";
				String certDireccion = "'"+tokens[2].replaceAll("'", "''")+"'";
				String certCalle = "'"+tokens[3].replaceAll("'", "''")+"'";
					
				String certColonia = "'"+tokens[4].replaceAll("'", "''")+"'";
				String certMunicipio = "'"+tokens[5]+"'";
				String certCP = "'"+tokens[6]+"'";
				String certSexo = "0";
				String certFechaNacimiento = "CAST('01/01/1970' AS SMALLDATETIME)";
				String ocupacionId = "2428";
				String certVigIni = "CAST('01/01/2015' AS SMALLDATETIME)";
				String certVigFin = "CAST('01/01/2016' AS SMALLDATETIME)";
				String operacionTipoId = "1";
				String certEstatus = "1";
				String polizaMaestraId = "243";
				String polizaMaestraPeriodoNum = "1";
				String usuarioId = "8846";
				String certRegFec = "CAST('"+sdf.format(new Date())+"' AS SMALLDATETIME)";
				String certFijaSA = "45000.00"; 
				String certFijaPrima = "146.16"; 
				String certSISA = "10000.00"; 
				String certSIPrima = "147.32"; 
				String certVariableSA = "3000.00";
				String certVariablePrima = "133.98"; 
				String certCISA = "50000.00";
				String certCIPrima = "58.00";
				String certEndoso = "0";
				String certUltimaVersion = "1";
				String certRFC = " dbo.fObtieneRFC(" + certNombre + "," + certApPaterno + "," + certApMaterno + ", " + certFechaNacimiento + ") ";
				//String certRFC = "'XXXX'";
				String certOrigenId = certId;
				String certFolio = "'"+"01" + StringUtils.leftPad(Integer.toString(consecutivo++), 8, '0')+"'";
				String distribuidorSucursalId = CertificadoJdbcDao.getDistribuidorSucursalId(tokens[7]) + "";
				String certEstado = "'"+tokens[9]+"'";
				
				List<String> lineaDelimitada = new ArrayList<>();
				lineaDelimitada.add(certId);
				lineaDelimitada.add(polizaMaestraId);
				lineaDelimitada.add(polizaMaestraPeriodoNum);
				lineaDelimitada.add(distribuidorSucursalId);
				lineaDelimitada.add(operacionTipoId);
				lineaDelimitada.add(usuarioId);
				lineaDelimitada.add(certFolio);
				lineaDelimitada.add(certEstatus);
				lineaDelimitada.add(certRegFec);
				lineaDelimitada.add(certVigIni);
				lineaDelimitada.add(certVigFin);
				lineaDelimitada.add(certClienteCve);
				lineaDelimitada.add(certNombre);
				lineaDelimitada.add(certApPaterno);
				lineaDelimitada.add(certApMaterno);
				lineaDelimitada.add(certFechaNacimiento);
				lineaDelimitada.add(certSexo);
				lineaDelimitada.add(ocupacionId);
				lineaDelimitada.add(certFijaSA);
				lineaDelimitada.add(certFijaPrima);
				lineaDelimitada.add(certVariableSA);
				lineaDelimitada.add(certVariablePrima);
				lineaDelimitada.add(certSISA);
				lineaDelimitada.add(certSIPrima);
				lineaDelimitada.add(certCISA);
				lineaDelimitada.add(certCIPrima);
				lineaDelimitada.add(certEndoso);
				lineaDelimitada.add(certUltimaVersion);
				lineaDelimitada.add(certRFC);
				lineaDelimitada.add(certDireccion);
				lineaDelimitada.add(certOrigenId);
				lineaDelimitada.add(certCalle);
				lineaDelimitada.add(certColonia);
				lineaDelimitada.add(certCP);
				lineaDelimitada.add(certMunicipio);
				lineaDelimitada.add(certEstado);
				lineaDelimitada.add(certFolio);
				
				String sql = generarCertificadoSqlInsert(lineaDelimitada);
				//csvCert.add(StringUtils.join(lineaDelimitada, ","));
				csvCert.add(sql);
				
				lineaDelimitada.clear();
				// historico
				String histId = "1";
				String histCertId = certId;
				String histAnioMes = "201501";
				String histSA = String.format( "%.2f", (new Double(certFijaSA) + new Double(certSISA) + new Double(certCISA) + new Double(certVariableSA)));
				String histCertSI = "0";
				String histCertPrima = String.format( "%.2f", (new Double(certFijaPrima) + new Double(certSIPrima) + new Double(certCIPrima) + new Double(certVariablePrima)));
				String histContra = "0";
				String histSemana = "0";
				String histAnioMesEstadistico = "201501";
				String histVigIni = certVigIni;
				String histVigFin = certVigFin;
				String histUltVersion = "1";
				String histPolMaestraPeriodoNum = polizaMaestraPeriodoNum;
				String histFolioAtlas = certFolio;
				String histPolizaAtlas = "'E-00-2-49-808'";
				
				lineaDelimitada.add(histId);
				lineaDelimitada.add(histCertId);
				lineaDelimitada.add(histAnioMes);
				lineaDelimitada.add(histSA);
				lineaDelimitada.add(histCertPrima);
				lineaDelimitada.add(histContra);
				lineaDelimitada.add(histCertSI);
				lineaDelimitada.add(histSemana);
				lineaDelimitada.add(histAnioMesEstadistico);
				lineaDelimitada.add(histVigIni);
				lineaDelimitada.add(histVigFin);
				lineaDelimitada.add(histUltVersion);
				lineaDelimitada.add(histPolMaestraPeriodoNum);
				lineaDelimitada.add(histFolioAtlas);
				lineaDelimitada.add(histPolizaAtlas);
				
				//csvHist.add(StringUtils.join(lineaDelimitada, ","));
				sql = generarHistoricoSqlInsert(lineaDelimitada);
				csvHist.add(sql);
				lineaDelimitada = null;
				if (++i > 4){
					System.out.println("Executing for certificado...");
					if(!CertificadoJdbcDao.insertBatch(csvCert)){ Assert.fail("la insercion de datos en la tabla Certificado ha fallado..."); return;};
					csvCert.clear();
					System.out.println("Executing for historico...");
					if(!CertificadoJdbcDao.insertBatch(csvHist)){ Assert.fail("la insercion de datos en la tabla Certificado_Hist ha fallado..."); return;};
					csvHist.clear();
					i = 0;
				}
					
			}
			System.out.println("sending last registries for certificado...");
			if(!CertificadoJdbcDao.insertBatch(csvCert)){ Assert.fail("la insercion de datos en la tabla Certificado ha fallado..."); return;};
			System.out.println("sending last registries for historico...");
			if(!CertificadoJdbcDao.insertBatch(csvHist)){ Assert.fail("la insercion de datos en la tabla Certificado_Hist ha fallado..."); return;};
			//FileUtils.writeLines(new File("dispersion.csv"), csvCert);
			//FileUtils.writeLines(new File("dispersion_hist.csv"), csvHist);
			CertificadoJdbcDao.testDateTimes();
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
	
	private String generarCertificadoSqlInsert(List<String> values) throws IOException {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO Certificado (");
		sql.append(escribirColumnas("ColumnasCert.txt"));
		sql.append(") VALUES (");
		sql.append(StringUtils.join(values, ","));
		sql.append(");");
		return sql.toString();
	}
	
	private String generarHistoricoSqlInsert(List<String> values) throws IOException {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO Certificado_Hist (");
		sql.append(escribirColumnas("ColumnasHist.txt"));
		sql.append(") VALUES (");
		sql.append(StringUtils.join(values, ","));
		sql.append(");");
		return sql.toString();
	}

	private String escribirColumnas(String filename) throws IOException {
		List<String> lines = FileUtils.readLines(new File(DIR, filename));
		return StringUtils.join(lines, ",");
	}

	//@Test
	public void testDispersaNombre(){
		String nombreCompleto = "LUISA MONTES DE OCA NAVARRO";
		dispersarNombre(nombreCompleto);
	}

	private List<String> dispersarNombre(String nombreCompleto) {
		LinkedList<String> nombres = new LinkedList<>(Arrays.asList(nombreCompleto.split("\\s")));
		Collections.reverse(nombres);
		String apMaterno = construirNombre(nombres, new LinkedList<String>());
		String apPaterno = construirNombre(nombres, new LinkedList<String>());
		Collections.reverse(nombres);
		String nombre    = StringUtils.join(nombres, " ");
		if(StringUtils.isNotBlank(nombre)){
			return Arrays.asList(nombre, apPaterno, apMaterno);
		} else if(StringUtils.isNotBlank(apPaterno)){
			return Arrays.asList(apPaterno, apMaterno, null);
		} else{
			return Arrays.asList(apMaterno, null, null);
		}
	}

	private String construirNombre(LinkedList<String> nombreCompleto, LinkedList<String> nombreParcial) {
		String parteNombre = nombreCompleto.pollFirst();
		nombreParcial.add(parteNombre);
		if(compuestos.contains(nombreCompleto.peekFirst())){
			construirNombre(nombreCompleto, nombreParcial);
		} else {
			Collections.reverse(nombreParcial);
		}
		return StringUtils.join(nombreParcial, " ");
	}

}
