package fr.ylyade.courtage.dto;

public class TaTListeRefDocDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4190350431673771136L;

	private Integer id;
	
	private String liblTListeRefDoc;
	private String codeTListeRefDoc;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLiblTListeRefDoc() {
		return liblTListeRefDoc;
	}

	public void setLiblTListeRefDoc(String liblTListeRefDoc) {
		this.liblTListeRefDoc = liblTListeRefDoc;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public String getCodeTListeRefDoc() {
		return codeTListeRefDoc;
	}

	public void setCodeTListeRefDoc(String codeTListeRefDoc) {
		this.codeTListeRefDoc = codeTListeRefDoc;
	}

	
}
