/**
 * 
 */
package mx.com.paralife.utils;

/**
 * @author Eduardo Barcenas
 *
 */
public enum DescripcionCertificado {
	
	SEG_VIDA_GASTOS_MEDICOS("Seguro de Vida/Gastos Médicos"),
	INDEMNIZACION_QUIRURGICA("Microseguro de Indemnización Quirúrgica");
	
	private String desc;
	
	private DescripcionCertificado(String desc) {
		this.desc = desc;
	}
	
	public String getDescripcion() {
		return this.desc;
	}
}
