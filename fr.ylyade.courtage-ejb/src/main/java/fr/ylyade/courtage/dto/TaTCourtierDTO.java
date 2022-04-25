package fr.ylyade.courtage.dto;

public class TaTCourtierDTO extends ModelObject implements java.io.Serializable {

	
	private static final long serialVersionUID = 116073481946649375L;

	private Integer id;
	
	private String codeTCourtier;
	private String liblTCourtier;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeTCourtier() {
		return codeTCourtier;
	}

	public void setCodeTCourtier(String codeTCourtier) {
		this.codeTCourtier = codeTCourtier;
	}

	public String getLiblTCourtier() {
		return liblTCourtier;
	}

	public void setLiblTCourtier(String liblTCourtier) {
		this.liblTCourtier = liblTCourtier;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
