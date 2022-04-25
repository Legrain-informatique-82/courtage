package fr.ylyade.courtage.dto;

import java.math.BigDecimal;

import fr.ylyade.courtage.model.TaClasse;

public class TaPalierClasseDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6416071636115097139L;

	private Integer id;
    private String codePalierClasse;
	private BigDecimal palierMontantMin;
	private BigDecimal palierMontantMax;
	private BigDecimal montantPrimeBase;
	
	private Integer idClasse;
	private String codeClasse;
	private String liblClasse;
	
	private Integer versionObj;
	
	public Integer getIdClasse() {
		return idClasse;
	}

	public void setIdClasse(Integer idClasse) {
		this.idClasse = idClasse;
	}

	public String getCodeClasse() {
		return codeClasse;
	}

	public void setCodeClasse(String codeClasse) {
		this.codeClasse = codeClasse;
	}

	public String getLiblClasse() {
		return liblClasse;
	}

	public void setLiblClasse(String liblClasse) {
		this.liblClasse = liblClasse;
	}	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public BigDecimal getPalierMontantMin() {
		return palierMontantMin;
	}

	public void setPalierMontantMin(BigDecimal palierMontantMin) {
		this.palierMontantMin = palierMontantMin;
	}

	public BigDecimal getPalierMontantMax() {
		return palierMontantMax;
	}

	public void setPalierMontantMax(BigDecimal palierMontantMax) {
		this.palierMontantMax = palierMontantMax;
	}

	public BigDecimal getMontantPrimeBase() {
		return montantPrimeBase;
	}

	public void setMontantPrimeBase(BigDecimal montantPrimeBase) {
		this.montantPrimeBase = montantPrimeBase;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public String getCodePalierClasse() {
		return codePalierClasse;
	}

	public void setCodePalierClasse(String codePalierClasse) {
		this.codePalierClasse = codePalierClasse;
	}

	
}
