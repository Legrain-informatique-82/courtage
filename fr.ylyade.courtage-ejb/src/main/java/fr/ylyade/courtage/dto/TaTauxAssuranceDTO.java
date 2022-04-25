package fr.ylyade.courtage.dto;

import java.math.BigDecimal;

public class TaTauxAssuranceDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4392005797469684711L;

	private Integer id;
	
	private String codeTauxAssurance;
	private String libelleTauxAssurance;
	private BigDecimal tauxTauxAssurance;
	private BigDecimal tauxHtTauxAssurance;
	
	private Integer idTAssurance;
	private String codeTAssurance;
	private String liblTAssurance;
	
	public Integer getIdTAssurance() {
		return idTAssurance;
	}

	public void setIdTAssurance(Integer idTAssurance) {
		this.idTAssurance = idTAssurance;
	}

	public String getCodeTAssurance() {
		return codeTAssurance;
	}

	public void setCodeTAssurance(String codeTAssurance) {
		this.codeTAssurance = codeTAssurance;
	}

	public String getLiblTAssurance() {
		return liblTAssurance;
	}

	public void setLiblTAssurance(String liblTAssurance) {
		this.liblTAssurance = liblTAssurance;
	}

	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeTauxAssurance() {
		return codeTauxAssurance;
	}

	public void setCodeTauxAssurance(String codeTauxAssurance) {
		this.codeTauxAssurance = codeTauxAssurance;
	}

	public String getLibelleTauxAssurance() {
		return libelleTauxAssurance;
	}

	public void setLibelleTauxAssurance(String libelleTauxAssurance) {
		this.libelleTauxAssurance = libelleTauxAssurance;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public BigDecimal getTauxTauxAssurance() {
		return tauxTauxAssurance;
	}

	public void setTauxTauxAssurance(BigDecimal tauxTauxAssurance) {
		this.tauxTauxAssurance = tauxTauxAssurance;
	}

	public BigDecimal getTauxHtTauxAssurance() {
		return tauxHtTauxAssurance;
	}

	public void setTauxHtTauxAssurance(BigDecimal tauxHtTauxAssurance) {
		this.tauxHtTauxAssurance = tauxHtTauxAssurance;
	}

	
}
