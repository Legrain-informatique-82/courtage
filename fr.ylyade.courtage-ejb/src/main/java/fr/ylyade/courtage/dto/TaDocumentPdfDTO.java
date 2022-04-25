package fr.ylyade.courtage.dto;

import fr.ylyade.courtage.model.TaTDoc;

public class TaDocumentPdfDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5762835442471913989L;

	private Integer id;
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	
	private Integer numeroVersion;
	private String codeDoc;
	private String liblDoc;
	private String description;
	private Integer positionListe;
	private byte[] imgDoc;
	
	private TaTDoc taTDoc;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public Integer getNumeroVersion() {
		return numeroVersion;
	}

	public void setNumeroVersion(Integer numeroVersion) {
		this.numeroVersion = numeroVersion;
	}

	public String getCodeDoc() {
		return codeDoc;
	}

	public void setCodeDoc(String codeDoc) {
		this.codeDoc = codeDoc;
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

	public byte[] getImgDoc() {
		return imgDoc;
	}

	public void setImgDoc(byte[] imgDoc) {
		this.imgDoc = imgDoc;
	}

	public TaTDoc getTaTDoc() {
		return taTDoc;
	}

	public void setTaTDoc(TaTDoc taTDoc) {
		this.taTDoc = taTDoc;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public String getNomFichier() {
		return nomFichier;
	}

	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}

	public Integer getTaille() {
		return taille;
	}

	public void setTaille(Integer taille) {
		this.taille = taille;
	}

	public String getTypeMime() {
		return typeMime;
	}

	public void setTypeMime(String typeMime) {
		this.typeMime = typeMime;
	}

	public Integer getPositionListe() {
		return positionListe;
	}

	public void setPositionListe(Integer positionListe) {
		this.positionListe = positionListe;
	}

	
}
