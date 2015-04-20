/**
 * 
 */
package mx.com.paralife.service;

import java.util.List;

import mx.com.paralife.dao.jdbc.CertificadoCMJdbcDao;
import mx.com.paralife.vo.ModeloCertificadoVO;
import mx.com.paralife.vo.PersonaVO;

/**
 * @author Eduardo Barcenas
 *
 */
public class ParalifeServiceImpl {
	
	private static CertificadoCMJdbcDao dao;
	
	static {
		dao = new CertificadoCMJdbcDao();
	}
	
	public static ModeloCertificadoVO contruyeCertificadoModeloXFolio(String folio){
		ModeloCertificadoVO certificadoVO = dao.getCertificadoModeloXFolio(folio);
		List<PersonaVO> coasegurados = dao.getCoaseguradosXFolio(folio);
		certificadoVO.setCoasegurados(coasegurados);
		List<PersonaVO> beneficiarios = dao.getBeneficiariosXFolio(folio);
		certificadoVO.setBeneficiarios(beneficiarios);
		System.out.println(certificadoVO);
		return certificadoVO;
	}

}
