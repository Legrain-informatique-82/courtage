package fr.ylyade.courtage.dto;

import java.util.Date;

import fr.ylyade.courtage.model.TaTActionDoc;

public class TaCourrierDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1491511885131325372L;

	private Integer id;
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	
	private String liblCourrier;
	private Date dateEnvoi;
	private byte[] courrier;
	private TaTActionDoc taTActionDoc;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getLiblCourrier() {
		return liblCourrier;
	}

	public void setLiblCourrier(String liblCourrier) {
		this.liblCourrier = liblCourrier;
	}

	public Date getDateEnvoi() {
		return dateEnvoi;
	}

	public void setDateEnvoi(Date dateEnvoi) {
		this.dateEnvoi = dateEnvoi;
	}

	public byte[] getCourrier() {
		return courrier;
	}

	public void setCourrier(byte[] courrier) {
		this.courrier = courrier;
	}

	public TaTActionDoc getTaTActionDoc() {
		return taTActionDoc;
	}

	public void setTaTActionDoc(TaTActionDoc taTActionDoc) {
		this.taTActionDoc = taTActionDoc;
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

	
}
