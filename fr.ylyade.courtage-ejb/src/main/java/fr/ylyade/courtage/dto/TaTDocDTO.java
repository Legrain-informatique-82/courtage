package fr.ylyade.courtage.dto;

public class TaTDocDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6967035245921284596L;

	private Integer id;
	private String codeTDoc;
	private String descriptionTDoc;
	private String liblTDoc;
	
	private Integer idTAssurance;
	private String codeTAssurance;
	private String liblTAssurance;
	
	private Integer versionObj;
	
	
	
	public Integer getIdTAssurance() {
		return idTAssurance;
	}

	public void setIdTAssurance(Integer idTAssurance) {
		this.idTAssurance = idTAssurance;
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

	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getLiblTDoc() {
		return liblTDoc;
	}

	public void setLiblTDoc(String liblTDoc) {
		this.liblTDoc = liblTDoc;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	

	public String getDescriptionTDoc() {
		return descriptionTDoc;
	}

	public void setDescriptionTDoc(String descriptionDoc) {
		this.descriptionTDoc = descriptionDoc;
	}

	public String getCodeTDoc() {
		return codeTDoc;
	}

	public void setCodeTDoc(String codeTDoc) {
		this.codeTDoc = codeTDoc;
	}

	

	
}
