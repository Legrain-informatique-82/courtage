package fr.ylyade.courtage.dto;

public class TaTTelDTO extends ModelObject implements java.io.Serializable {

	private static final long serialVersionUID = -3939449244387443627L;

	private Integer id;
	
	private String codeTTel;
	private String liblTTel;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeTTel() {
		return codeTTel;
	}

	public void setCodeTTel(String codeTTel) {
		this.codeTTel = codeTTel;
	}

	public String getLiblTTel() {
		return liblTTel;
	}

	public void setLiblTTel(String liblTTel) {
		this.liblTTel = liblTTel;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
