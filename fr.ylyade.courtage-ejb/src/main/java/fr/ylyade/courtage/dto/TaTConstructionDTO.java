package fr.ylyade.courtage.dto;

public class TaTConstructionDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6374728803459004684L;

	private Integer id;
	
	private String codeConstruction;
	private String liblConstruction;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeConstruction() {
		return codeConstruction;
	}

	public void setCodeConstruction(String codeConstruction) {
		this.codeConstruction = codeConstruction;
	}

	public String getLiblConstruction() {
		return liblConstruction;
	}

	public void setLiblConstruction(String liblConstruction) {
		this.liblConstruction = liblConstruction;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
