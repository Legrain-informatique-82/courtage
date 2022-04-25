package fr.ylyade.courtage.dto;

import java.math.BigDecimal;

public class TaTSousTraitanceDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6795726993079904381L;
	private Integer id;
	private String codeTSousTraitance;
	private String liblTSousTraitance; 
	private BigDecimal taux;
	private Integer baseMin;
	private Integer baseMax;
	
	private Integer versionObj;
	
	
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getTaux() {
		return taux;
	}

	public void setTaux(BigDecimal taux) {
		this.taux = taux;
	}

	public Integer getBaseMin() {
		return baseMin;
	}

	public void setBaseMin(Integer baseMin) {
		this.baseMin = baseMin;
	}

	public Integer getBaseMax() {
		return baseMax;
	}

	public void setBaseMax(Integer baseMax) {
		this.baseMax = baseMax;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public String getCodeTSousTraitance() {
		return codeTSousTraitance;
	}

	public void setCodeTSousTraitance(String codeTSousTraitance) {
		this.codeTSousTraitance = codeTSousTraitance;
	}

	public String getLiblTSousTraitance() {
		return liblTSousTraitance;
	}

	public void setLiblTSousTraitance(String liblTSousTraitance) {
		this.liblTSousTraitance = liblTSousTraitance;
	}
	
}
