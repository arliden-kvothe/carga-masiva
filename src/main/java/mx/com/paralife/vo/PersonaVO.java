/**
 * 
 */
package mx.com.paralife.vo;

/**
 * @author Eduardo Barcenas
 * 
 */
public class PersonaVO extends BaseVO {

	private String parentesco;

	private String apellidoPaterno;

	private String apellidoMaterno;

	private String nombre;

	private String genero;

	private String fechaNacimiento;

	private String porcentaje;

	/**
	 * @return the parentesco
	 */
	public final String getParentesco() {
		return parentesco;
	}

	/**
	 * @param parentesco
	 *            the parentesco to set
	 */
	public final void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}

	/**
	 * @return the apellidoPaterno
	 */
	public final String getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * @param apellidoPaterno
	 *            the apellidoPaterno to set
	 */
	public final void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * @return the apellidoMaterno
	 */
	public final String getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * @param apellidoMaterno
	 *            the apellidoMaterno to set
	 */
	public final void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	/**
	 * @return the nombre
	 */
	public final String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the genero
	 */
	public final String getGenero() {
		return genero;
	}

	/**
	 * @param genero
	 *            the genero to set
	 */
	public final void setGenero(String genero) {
		this.genero = genero;
	}

	/**
	 * @return the fechaNacimiento
	 */
	public final String getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * @param fechaNacimiento
	 *            the fechaNacimiento to set
	 */
	public final void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * @return the porcentaje
	 */
	public final String getPorcentaje() {
		return porcentaje;
	}

	/**
	 * @param porcentaje
	 *            the porcentaje to set
	 */
	public final void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

}
