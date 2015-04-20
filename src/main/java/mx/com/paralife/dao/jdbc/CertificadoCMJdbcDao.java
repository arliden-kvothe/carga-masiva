/**
 * 
 */
package mx.com.paralife.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mx.com.paralife.vo.ModeloCertificadoVO;
import mx.com.paralife.vo.PersonaVO;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author Eduardo Barcenas
 *
 */
public class CertificadoCMJdbcDao extends GenericJdbcDao {
	
	public CertificadoCMJdbcDao() {
	}
	
	public ModeloCertificadoVO getCertificadoModeloXFolio(final String folio){
		StringBuffer sql = new StringBuffer();
		sql.append("{call spSL_ConsultarCertificado_Impresion_Masiva_Modelo(?,?,?,?,?)}");
		ModeloCertificadoVO certificado = null;
		try {
			CallableStatement stmt = getConnection().prepareCall(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// date,date,int,int,string
			stmt.setDate(1, null);
			stmt.setDate(2, null);
			stmt.setInt(3, 0);
			stmt.setInt(4, 0);
			stmt.setString(5, folio);
			
			ResultSet rs = stmt.executeQuery();
			
			System.out.println("getting sumary data to build certificado ->");
			
			if (rs.next()) {
				
				certificado = new ModeloCertificadoVO();
				certificado.setCertificadoFolio(rs.getString("Certificado_Folio"));
				certificado.setCategoria(rs.getString("Poliza_Maestra_Dsc"));
				certificado.setRazonSocial(rs.getString("Nom_Completo"));
				certificado.setNumeroCertificado(rs.getString("Certificado_Id"));
				StringBuffer domicilio = new StringBuffer();
				domicilio.append(rs.getString("Certificado_Calle"));
				if(StringUtils.isNotBlank(rs.getString("Certificado_Colonia"))) {
					domicilio.append(", ").append(rs.getString("Certificado_Colonia"));
				}
				if(StringUtils.isNotBlank(rs.getString("Certificado_Municipio"))) {
					domicilio.append(", ").append(rs.getString("Certificado_Municipio"));
				}
				if(StringUtils.isNotBlank(rs.getString("Certificado_CP"))) {
					domicilio.append(", C.P. ").append(rs.getString("Certificado_CP"));
				}
				if(StringUtils.isNotBlank(rs.getString("Certificado_Estado"))) {
					domicilio.append(", ").append(rs.getString("Certificado_Estado"));
				}
				certificado.setDomicilio(domicilio.toString());
			}
			
			releaseDBResources(stmt, rs, true);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return certificado;
	}
	
	public List<PersonaVO> getCoaseguradosXFolio(final String folio){
		StringBuffer sql = new StringBuffer();
		sql.append("{call spSL_Impr_Mas_Coasegurados(?,?)}");
		List<PersonaVO> personas = new ArrayList<>();
		try {
			CallableStatement stmt = getConnection().prepareCall(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// date,date,int,int,string
			stmt.setString(1, folio);
			stmt.setInt(2, 0);
			
			ResultSet rs = stmt.executeQuery();
			
			System.out.println("getting coasegurados... ->");
			
			while (rs.next()) {
				PersonaVO personaVO = new PersonaVO();
				personaVO.setNombre(rs.getString("Persona_Nombre"));
				personaVO.setApellidoPaterno(rs.getString("Persona_Ape_Paterno"));
				personaVO.setApellidoMaterno(rs.getString("Persona_Ape_Materno"));
				personaVO.setGenero(rs.getString("Persona_Sexo"));
				personaVO.setParentesco(rs.getString("Parentesco"));
				Date date = rs.getDate("Persona_Fecha_Nacimiento");
				personaVO.setFechaNacimiento(DateFormatUtils.format(date, "dd/MM/yyyy"));
				
				personas.add(personaVO);
			}
			
			releaseDBResources(stmt, rs, true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return personas;
	}

	public List<PersonaVO> getBeneficiariosXFolio(String folio) {
		StringBuffer sql = new StringBuffer();
		sql.append("{call spSL_Impr_Mas_Beneficiarios(?,?)}");
		List<PersonaVO> personas = new ArrayList<>();
		try {
			CallableStatement stmt = getConnection().prepareCall(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// date,date,int,int,string
			stmt.setString(1, folio);
			stmt.setInt(2, 0);
			
			ResultSet rs = stmt.executeQuery();
			
			System.out.println("result after executing SP ->");
			
			while (rs.next()) {
				PersonaVO personaVO = new PersonaVO();
				personaVO.setNombre(rs.getString("Beneficiario_VCD_Nombre"));
				personaVO.setApellidoPaterno(rs.getString("Beneficiario_VCD_Ap_Paterno"));
				personaVO.setApellidoMaterno(rs.getString("Beneficiario_VCD_Ap_Materno"));
				personaVO.setParentesco(rs.getString("Parentesco_Tipo_dsc"));
				personaVO.setPorcentaje(""+rs.getInt("Beneficiario_VCD_Porc"));
				
				personas.add(personaVO);
			}
			
			releaseDBResources(stmt, rs, true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return personas;
	}

}
