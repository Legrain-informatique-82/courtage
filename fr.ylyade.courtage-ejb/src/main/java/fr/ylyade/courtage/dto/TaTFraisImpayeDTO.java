package fr.ylyade.courtage.dto;

import java.math.BigDecimal;

public class TaTFraisImpayeDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4711192354237092276L;

	private Integer id;
	private String codeTFraisImpaye;
	private String liblFactureFraisImpaye;
	private BigDecimal montant;
	private BigDecimal montantHtFraisImpaye;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getLiblFactureFraisImpaye() {
		return liblFactureFraisImpaye;
	}

	public void setLiblFactureFraisImpaye(String liblFactureFraisImpaye) {
		this.liblFactureFraisImpaye = liblFactureFraisImpaye;
	}

	public BigDecimal getMontant() {
		return montant;
	}

	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public String getCodeTFraisImpaye() {
		return codeTFraisImpaye;
	}

	public void setCodeTFraisImpaye(String codeTFraisImpaye) {
		this.codeTFraisImpaye = codeTFraisImpaye;
	}

	public BigDecimal getMontantHtFraisImpaye() {
		return montantHtFraisImpaye;
	}

	public void setMontantHtFraisImpaye(BigDecimal montantHtFraisImpaye) {
		this.montantHtFraisImpaye = montantHtFraisImpaye;
	}

	
}
