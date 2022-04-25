package fr.ylyade.courtage.dto;

public class TaTGedSinistreDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3423016947629186729L;

	private Integer id;
	
	private String codeTGedSinistre;
	private String liblTGedSinistre;
	private String decriptionTGedSinistre;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeTGedSinistre() {
		return codeTGedSinistre;
	}

	public void setCodeTGedSinistre(String codeTGedSinistre) {
		this.codeTGedSinistre = codeTGedSinistre;
	}

	public String getLiblTGedSinistre() {
		return liblTGedSinistre;
	}

	public void setLiblTGedSinistre(String liblTGedSinistre) {
		this.liblTGedSinistre = liblTGedSinistre;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public String getDecriptionTGedSinistre() {
		return decriptionTGedSinistre;
	}

	public void setDecriptionTGedSinistre(String decription) {
		this.decriptionTGedSinistre = decription;
	}

	
}
