package fr.ylyade.courtage.dto;

import java.math.BigDecimal;

public class TaTGroupeTarifDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5256190983041837941L;
	private Integer id;
	private String codeGroupe;
	private String nomGroupe;
	private BigDecimal tauxGroupe;

	private Integer versionObj;
	
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeGroupe() {
		return codeGroupe;
	}

	public void setCodeGroupe(String codeGroupe) {
		this.codeGroupe = codeGroupe;
	}

	public String getNomGroupe() {
		return nomGroupe;
	}

	public void setNomGroupe(String nomGroupe) {
		this.nomGroupe = nomGroupe;
	}

	public BigDecimal getTauxGroupe() {
		return tauxGroupe;
	}

	public void setTauxGroupe(BigDecimal tauxGroupe) {
		this.tauxGroupe = tauxGroupe;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	

	
}
