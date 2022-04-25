package fr.ylyade.courtage.dto;

public class TaTReglementDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 300760261215121079L;

	private Integer id;
	
	private String codeTReglement;
	private String liblTReglement;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeTReglement() {
		return codeTReglement;
	}

	public void setCodeTReglement(String codeTReglement) {
		this.codeTReglement = codeTReglement;
	}

	public String getLiblTReglement() {
		return liblTReglement;
	}

	public void setLiblTReglement(String liblTReglement) {
		this.liblTReglement = liblTReglement;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
