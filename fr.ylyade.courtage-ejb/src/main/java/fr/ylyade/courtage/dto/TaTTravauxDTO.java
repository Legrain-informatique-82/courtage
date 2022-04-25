package fr.ylyade.courtage.dto;

public class TaTTravauxDTO extends ModelObject implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4814883870474107052L;
	private Integer id;
	private String codeTTravaux;
	private String liblTTravaux;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id; 
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeTTravaux() {
		return codeTTravaux;
	}

	public void setCodeTTravaux(String codeTTravaux) {
		this.codeTTravaux = codeTTravaux;
	}

	public String getLiblTTravaux() {
		return liblTTravaux;
	}

	public void setLiblTTravaux(String liblTTravaux) {
		this.liblTTravaux = liblTTravaux;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
