package fr.ylyade.courtage.dto;

import java.math.BigDecimal;

public class TaTarifPrestationDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2294541959110567503L;

	private Integer id;
	
	private String codeTarifPrestation;
	private String liblPrestation;
	private BigDecimal montantPrestation;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeTarifPrestation() {
		return codeTarifPrestation;
	}

	public void setCodeTarifPrestation(String codeTarifPrestation) {
		this.codeTarifPrestation = codeTarifPrestation;
	}



	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public BigDecimal getMontantPrestation() {
		return montantPrestation;
	}

	public void setMontantPrestation(BigDecimal montantPrestation) {
		this.montantPrestation = montantPrestation;
	}

	public String getLiblPrestation() {
		return liblPrestation;
	}

	public void setLiblPrestation(String liblPrestation) {
		this.liblPrestation = liblPrestation;
	}

	
}
