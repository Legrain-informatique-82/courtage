package fr.ylyade.courtage.dto;

public class TaTMaitriseOeuvreDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7039520571222381663L;

	private Integer id;
	
	private String liblTitre;
	private String liblNature;
	private String codeTMaitriseOeuvre;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getLiblTitre() {
		return liblTitre;
	}

	public void setLiblTitre(String liblTitre) {
		this.liblTitre = liblTitre;
	}

	public String getLiblNature() {
		return liblNature;
	}

	public void setLiblNature(String liblNature) {
		this.liblNature = liblNature;
	}

	public String getCodeTMaitriseOeuvre() {
		return codeTMaitriseOeuvre;
	}

	public void setCodeTMaitriseOeuvre(String codeType) {
		this.codeTMaitriseOeuvre = codeType;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
