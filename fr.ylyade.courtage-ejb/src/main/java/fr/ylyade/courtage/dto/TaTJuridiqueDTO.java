package fr.ylyade.courtage.dto;

public class TaTJuridiqueDTO extends ModelObject implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4424757562612044790L;

	private Integer id;
	
	private String codeTJuridique;
	private String liblTJuridique;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeTJuridique() {
		return codeTJuridique;
	}

	public void setCodeTJuridique(String codeTJuridique) {
		this.codeTJuridique = codeTJuridique;
	}

	public String getLiblTJuridique() {
		return liblTJuridique;
	}

	public void setLiblTJuridique(String liblTJuridique) {
		this.liblTJuridique = liblTJuridique;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
