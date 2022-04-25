package fr.ylyade.courtage.dto;

import fr.ylyade.courtage.model.TaSinistre;
import fr.ylyade.courtage.model.TaTGedSinistre;

public class TaGedSinistreDTO extends ModelObject implements java.io.Serializable {

	
	private static final long serialVersionUID = -3840508075032345648L;

	private Integer id;
	
	private byte[] imgDocSinistre;
	private String refSinistre;
	private String commentaire;
	private TaTGedSinistre taTGedSinistre;
	private TaSinistre taSinistre;
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public byte[] getImgDocSinistre() {
		return imgDocSinistre;
	}

	public void setImgDocSinistre(byte[] imgDocSinistre) {
		this.imgDocSinistre = imgDocSinistre;
	}

	public String getRefSinistre() {
		return refSinistre;
	}

	public void setRefSinistre(String refSinistre) {
		this.refSinistre = refSinistre;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public TaTGedSinistre getTaTGedSinistre() {
		return taTGedSinistre;
	}

	public void setTaTGedSinistre(TaTGedSinistre taTGedSinistre) {
		this.taTGedSinistre = taTGedSinistre;
	}

	public TaSinistre getTaSinistre() {
		return taSinistre;
	}

	public void setTaSinistre(TaSinistre taSinistre) {
		this.taSinistre = taSinistre;
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
