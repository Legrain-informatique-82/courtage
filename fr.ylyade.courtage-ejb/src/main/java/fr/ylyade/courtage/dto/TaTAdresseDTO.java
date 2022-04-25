package fr.ylyade.courtage.dto;

public class TaTAdresseDTO extends ModelObject implements java.io.Serializable {

	private static final long serialVersionUID = -9092126592851738050L;

	private Integer id;
	
	private String codeTAdresse;
	private String liblTAdresse;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeTAdresse() {
		return codeTAdresse;
	}

	public void setCodeTAdresse(String codeTAdresse) {
		this.codeTAdresse = codeTAdresse;
	}

	public String getLiblTAdresse() {
		return liblTAdresse;
	}

	public void setLiblTAdresse(String liblTAdresse) {
		this.liblTAdresse = liblTAdresse;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
