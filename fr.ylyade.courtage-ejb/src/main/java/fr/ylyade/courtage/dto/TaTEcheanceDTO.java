package fr.ylyade.courtage.dto;

import java.math.BigDecimal;

public class TaTEcheanceDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4019548098568680567L;

	private Integer id;
	
	private BigDecimal tauxEcheance;
	private String liblTEcheance;
	
	private String codeTEcheance;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getLiblTEcheance() {
		return liblTEcheance;
	}

	public void setLiblTEcheance(String liblTEcheance) {
		this.liblTEcheance = liblTEcheance;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public BigDecimal getTauxEcheance() {
		return tauxEcheance;
	}

	public void setTauxEcheance(BigDecimal tauxEcheance) {
		this.tauxEcheance = tauxEcheance;
	}

	public String getCodeTEcheance() {
		return codeTEcheance;
	}

	public void setCodeTEcheance(String codeTEcheance) {
		this.codeTEcheance = codeTEcheance;
	}

	
}
