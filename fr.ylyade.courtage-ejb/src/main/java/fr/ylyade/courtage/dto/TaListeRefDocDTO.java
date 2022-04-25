package fr.ylyade.courtage.dto;

import fr.ylyade.courtage.model.TaTListeRefDoc;

public class TaListeRefDocDTO extends ModelObject implements java.io.Serializable {

	private static final long serialVersionUID = -8916422648523331916L;

	private Integer id;
	private String codeListeRefDoc; 
	private String liblDoc;
	private String description;
	private Boolean actif;
	private Boolean obligatoire;
	
	private Integer idTListeRefDoc;
	private String codeTListeRefDoc;
	private String liblTListeRefDoc;
	
	private Integer versionObj;
	
	
	public TaListeRefDocDTO(
			Integer idListeRefDoc,
	 		String codeListeRefDoc,
	 		String liblDoc,
	 		String description,
	 		Boolean obligatoire,
	 		TaTListeRefDoc taTListeRefDoc
			 ) {
		this.id = idListeRefDoc;
		this.codeListeRefDoc = codeListeRefDoc;
		this.liblDoc = liblDoc;
		this.description = description;
		this.obligatoire = obligatoire;
		this.idTListeRefDoc = taTListeRefDoc.getIdTListeRefDoc();
		this.codeTListeRefDoc= taTListeRefDoc.getCodeTListeRefDoc();
		this.liblTListeRefDoc= taTListeRefDoc.getLiblTListeRefDoc();
	}
	
	
	public TaListeRefDocDTO() {
		// TODO Auto-generated constructor stub
	}


	public Integer getIdTListeRefDoc() {
		return idTListeRefDoc;
	}

	public void setIdTListeRefDoc(Integer idTListeRefDoc) {
		this.idTListeRefDoc = idTListeRefDoc;
	}

	public String getCodeTListeRefDoc() {
		return codeTListeRefDoc;
	}

	public void setCodeTListeRefDoc(String codeTListeRefDoc) {
		this.codeTListeRefDoc = codeTListeRefDoc;
	}

	public String getLiblTListeRefDoc() {
		return liblTListeRefDoc;
	}

	public void setLiblTListeRefDoc(String liblTListeRefDoc) {
		this.liblTListeRefDoc = liblTListeRefDoc;
	}

	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getLiblDoc() {
		return liblDoc;
	}

	public void setLiblDoc(String liblDoc) {
		this.liblDoc = liblDoc;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public String getCodeListeRefDoc() {
		return codeListeRefDoc;
	}

	public void setCodeListeRefDoc(String codeListeRefDoc) {
		this.codeListeRefDoc = codeListeRefDoc;
	}


	public Boolean getActif() {
		return actif;
	}


	public void setActif(Boolean actif) {
		this.actif = actif;
	}


	public Boolean getObligatoire() {
		return obligatoire;
	}


	public void setObligatoire(Boolean obligatoire) {
		this.obligatoire = obligatoire;
	}

	
}
