package fr.ylyade.courtage.dto;

public class TaTActionDocDTO extends ModelObject implements java.io.Serializable {

	private static final long serialVersionUID = -7075855453501630840L;

	private Integer id;
	
	private String codeTActionDoc;
	private String liblTActionDoc;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public String getCodeTActionDoc() {
		return codeTActionDoc;
	}

	public void setCodeTActionDoc(String codeTActionDoc) {
		this.codeTActionDoc = codeTActionDoc;
	}

	public String getLiblTActionDoc() {
		return liblTActionDoc;
	}

	public void setLiblTActionDoc(String liblTActionDoc) {
		this.liblTActionDoc = liblTActionDoc;
	}

	
}
