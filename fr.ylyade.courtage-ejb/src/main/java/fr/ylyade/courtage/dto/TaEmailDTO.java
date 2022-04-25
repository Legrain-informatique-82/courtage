package fr.ylyade.courtage.dto;

import fr.ylyade.courtage.model.TaContactEntreprise;
import fr.ylyade.courtage.model.TaTEmail;

public class TaEmailDTO extends ModelObject implements java.io.Serializable {

	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3416154205243678574L;
	private Integer id;
	private String adresseEmail;
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	
	private TaTEmail taTEmail;
	private TaContactEntreprise taContactEntreprise;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getAdresseEmail() {
		return adresseEmail;
	}

	public void setAdresseEmail(String adresseEmail) {
		this.adresseEmail = adresseEmail;
	}

	public TaTEmail getTaTEmail() {
		return taTEmail;
	}

	public void setTaTEmail(TaTEmail taTEmail) {
		this.taTEmail = taTEmail;
	}

	public TaContactEntreprise getTaContactEntreprise() {
		return taContactEntreprise;
	}

	public void setTaContactEntreprise(TaContactEntreprise taContactEntreprise) {
		this.taContactEntreprise = taContactEntreprise;
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
