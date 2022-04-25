package fr.ylyade.courtage.dto;

public class TaTAssuranceDTO extends ModelObject implements java.io.Serializable {

	
	private static final long serialVersionUID = 3041470752974931536L;

	private Integer id;
	
	private String codeTAssurance;
	private String liblTAssurance;
	
	private Integer versionObj;
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeTAssurance() {
		return codeTAssurance;
	}

	public void setCodeTAssurance(String codeTAssurance) {
		this.codeTAssurance = codeTAssurance;
	}

	public String getLiblTAssurance() {
		return liblTAssurance;
	}

	public void setLiblTAssurance(String liblTAssurance) {
		this.liblTAssurance = liblTAssurance;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	

	
}
