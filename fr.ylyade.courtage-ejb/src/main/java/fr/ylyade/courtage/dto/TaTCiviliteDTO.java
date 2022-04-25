package fr.ylyade.courtage.dto;

public class TaTCiviliteDTO extends ModelObject implements java.io.Serializable {


	private static final long serialVersionUID = -6777168887073676838L;

	private Integer id;
	
	private String codeTCivilite;
	private String liblTCivilite;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeTCivilite() {
		return codeTCivilite;
	}

	public void setCodeTCivilite(String codeTCivilite) {
		this.codeTCivilite = codeTCivilite;
	}

	public String getLiblTCivilite() {
		return liblTCivilite;
	}

	public void setLiblTCivilite(String liblTCivilite) {
		this.liblTCivilite = liblTCivilite;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
