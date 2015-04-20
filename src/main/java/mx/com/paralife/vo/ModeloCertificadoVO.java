/**
 * 
 */
package mx.com.paralife.vo;

import java.util.List;

/**
 * @author Eduardo Barcenas
 * 
 */
public class ModeloCertificadoVO extends BaseVO {

	private String certificadoFolio;

	private String categoria;

	private String numeroCertificado;

	private String razonSocial;

	private String domicilio;

	private List<PersonaVO> coasegurados;

	private List<PersonaVO> beneficiarios;

	/**
	 * @return the certificadoFolio
	 */
	public final String getCertificadoFolio() {
		return certificadoFolio;
	}

	/**
	 * @param certificadoFolio
	 *            the certificadoFolio to set
	 */
	public final void setCertificadoFolio(String certificadoFolio) {
		this.certificadoFolio = certificadoFolio;
	}

	/**
	 * @return the categoria
	 */
	public final String getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria
	 *            the categoria to set
	 */
	public final void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	/**
	 * @return the numeroCertificado
	 */
	public final String getNumeroCertificado() {
		return numeroCertificado;
	}

	/**
	 * @param numeroCertificado
	 *            the numeroCertificado to set
	 */
	public final void setNumeroCertificado(String numeroCertificado) {
		this.numeroCertificado = numeroCertificado;
	}

	/**
	 * @return the razonSocial
	 */
	public final String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param razonSocial
	 *            the razonSocial to set
	 */
	public final void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * @return the domicilio
	 */
	public final String getDomicilio() {
		return domicilio;
	}

	/**
	 * @param domicilio
	 *            the domicilio to set
	 */
	public final void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	/**
	 * @return the coasegurados
	 */
	public final List<PersonaVO> getCoasegurados() {
		return coasegurados;
	}

	/**
	 * @param coasegurados
	 *            the coasegurados to set
	 */
	public final void setCoasegurados(List<PersonaVO> coasegurados) {
		this.coasegurados = coasegurados;
	}

	/**
	 * @return the beneficiarios
	 */
	public final List<PersonaVO> getBeneficiarios() {
		return beneficiarios;
	}

	/**
	 * @param beneficiarios
	 *            the beneficiarios to set
	 */
	public final void setBeneficiarios(List<PersonaVO> beneficiarios) {
		this.beneficiarios = beneficiarios;
	}

}
