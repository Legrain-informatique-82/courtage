package fr.ylyade.courtage.dto;

import java.math.BigDecimal;

public class TaTFranchiseDTO extends ModelObject implements java.io.Serializable {

	
	private static final long serialVersionUID = -7554997482511715705L;

	private Integer id;
	
	private String codeTFranchise;
	private String liblTFranchise;
	private BigDecimal montant;
	private BigDecimal tauxMontant;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeTFranchise() {
		return codeTFranchise;
	}

	public void setCodeTFranchise(String codeTFranchise) {
		this.codeTFranchise = codeTFranchise;
	}

	public String getLiblTFranchise() {
		return liblTFranchise;
	}

	public void setLiblTFranchise(String liblTFranchise) {
		this.liblTFranchise = liblTFranchise;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public BigDecimal getMontant() {
		return montant;
	}

	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}

	public BigDecimal getTauxMontant() {
		return tauxMontant;
	}

	public void setTauxMontant(BigDecimal tauxMontant) {
		this.tauxMontant = tauxMontant;
	}

	
}
