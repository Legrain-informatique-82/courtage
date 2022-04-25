package fr.ylyade.courtage.dto;

public class TaTEmailDTO extends ModelObject implements java.io.Serializable {


	private static final long serialVersionUID = -4754564626377480230L;

	private Integer id;
	
	private String codeTEmail;
	private String liblTEmail;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeTEmail() {
		return codeTEmail;
	}

	public void setCodeTEmail(String codeTEmail) {
		this.codeTEmail = codeTEmail;
	}

	public String getLiblTEmail() {
		return liblTEmail;
	}

	public void setLiblTEmail(String liblTEmail) {
		this.liblTEmail = liblTEmail;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
