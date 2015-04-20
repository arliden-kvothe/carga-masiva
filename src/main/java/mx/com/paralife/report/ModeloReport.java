/**
 * 
 */
package mx.com.paralife.report;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import mx.com.paralife.service.ParalifeServiceImpl;
import mx.com.paralife.utils.DescripcionCertificado;
import mx.com.paralife.vo.ModeloCertificadoVO;
import mx.com.paralife.vo.PersonaVO;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * @author Eduardo Barcenas
 *
 */
public class ModeloReport {
	
	private static final String RESOURCES_LOCATION = "/Users/martinni/Dropbox/Code/ireport/v1";
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
	
	public JasperPrint getAvisoPrivacidad() throws JRException, IOException {
		File dir = new File(RESOURCES_LOCATION);
		File templateFile = new File(dir, "aviso-privacidad.jrxml");
		File imageFile = new File(dir, "cobertura-documentos.PNG");
		BufferedImage image = ImageIO.read(imageFile);
		if (!templateFile.exists()) {
			System.out.println("el archivo no pudo ser encontrado...");
			//return;
		}
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("cobertura_documentos_img", image);
		JasperDesign jd = JRXmlLoader.load(templateFile);
		JasperReport jr = JasperCompileManager.compileReport(jd);
		JasperPrint jasperPrint =JasperFillManager.fillReport(
                jr,
                parameters,
                new JREmptyDataSource());
		return jasperPrint;
		//JasperExportManager.exportReportToPdfFile(jasperPrint, "/Users/martinni/projects/Paralife/carga-masiva/src/main/resources/output/aviso-privacidad.pdf");
	}
	
	public JasperPrint getDiamante(ModeloCertificadoVO certificado) throws JRException, IOException {
		File dir = new File(RESOURCES_LOCATION);
		File templateFile = new File(dir, "diamante_template.jrxml");
		File atlasImage = new File(dir, "atlas-logo.png");
		BufferedImage image1 = ImageIO.read(atlasImage);
		File firmaImage = new File(dir, "firma.png");
		BufferedImage image2 = ImageIO.read(firmaImage);
		File paralifeImage = new File(dir, "paralife-logo.png");
		BufferedImage image3 = ImageIO.read(paralifeImage);
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("N_CLIENTE", certificado.getCertificadoFolio());
		parameters.put("CATEGORIA", certificado.getCategoria());
		parameters.put("CERTIFICADO_DESCRIPCION", DescripcionCertificado.INDEMNIZACION_QUIRURGICA.getDescripcion());
		parameters.put("RAZON_SOCIAL", certificado.getRazonSocial());
		parameters.put("DOMICILIO", certificado.getDomicilio());
		parameters.put("N_CERTIFICADO", certificado.getNumeroCertificado());
		
		parameters.put("COASEGURADOS", (ArrayList<PersonaVO>) certificado.getCoasegurados());
		parameters.put("BENEFICIARIOS", (ArrayList<PersonaVO>) certificado.getBeneficiarios());
		
		// images ->
		parameters.put("ATLAS_LOGO_IMG", image1);
		parameters.put("FIRMA_IMG", image2);
		parameters.put("PARALIFE_LOGO_IMG", image3);
		
		JasperDesign jd = JRXmlLoader.load(templateFile);
		JasperReport jr = JasperCompileManager.compileReport(jd);
		JasperPrint jasperPrint =JasperFillManager.fillReport(
                jr,
                parameters,
                new JREmptyDataSource());
		
		return jasperPrint;
		//JasperExportManager.exportReportToPdfFile(jasperPrint, "/Users/martinni/projects/Paralife/carga-masiva/src/main/resources/output/aviso-privacidad.pdf");
	}
	
	public void generateDiamante(ModeloCertificadoVO certificado) throws JRException, IOException {
		JasperPrint jpDiamante = getDiamante(certificado);
		JasperPrint jpAvisoPrivacidad = getAvisoPrivacidad();
		List<JRPrintPage> avisoPrivacidadPaginas = jpAvisoPrivacidad.getPages();
		for (JRPrintPage page : avisoPrivacidadPaginas) {
			jpDiamante.addPage(page);
		}
		String time = sdf.format(new Date());
		JasperExportManager.exportReportToPdfFile(jpDiamante, "/Users/martinni/projects/Paralife/carga-masiva/src/main/resources/output/certificado-diamante_"+time+".pdf");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			ModeloCertificadoVO certificado = ParalifeServiceImpl.contruyeCertificadoModeloXFolio("100401434");
			new ModeloReport().generateDiamante(certificado);
		} catch (JRException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
