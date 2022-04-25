package fr.ylyade.courtage.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "ta_ged_sinistre")
public class TaGedSinistre implements Serializable {

	private static final long serialVersionUID = 4667545791358824089L;
	
	private int idGedSinistre;
	private byte[] imgDocSinistre;
	private String refSinistre;
	private String commentaire;
	private TaTGedSinistre taTGedSinistre;
	private TaSinistre taSinistre;
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ged_sinistre", unique = true, nullable = false)
	public int getIdGedSinistre() {
		return idGedSinistre;
	}

	public void setIdGedSinistre(int idGedSinistre) {
		this.idGedSinistre = idGedSinistre;
	}

	@Column(name = "img_doc_sinistre")
	public byte[] getImgDocSinistre() {
		return imgDocSinistre;
	}

	public void setImgDocSinistre(byte[] imgDocSinistre) {
		this.imgDocSinistre = imgDocSinistre;
	}

	@Column(name = "ref_sinistre")
	public String getRefSinistre() {
		return refSinistre;
	}

	public void setRefSinistre(String refSinistre) {
		this.refSinistre = refSinistre;
	}

	@Column(name = "commentaire")
	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_ged_sinistre_ta_t_ged_sinistre")
	public TaTGedSinistre getTaTGedSinistre() {
		return taTGedSinistre;
	}

	public void setTaTGedSinistre(TaTGedSinistre taTGedSinistre) {
		this.taTGedSinistre = taTGedSinistre;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_sinistre_ta_sinistre")
	public TaSinistre getTaSinistre() {
		return taSinistre;
	}

	public void setTaSinistre(TaSinistre taSinistre) {
		this.taSinistre = taSinistre;
	}

	@Column(name = "qui_cree", length = 50)
	public String getQuiCree() {
		return this.quiCree;
	}

	public void setQuiCree(String quiCreeTCivilite) {
		this.quiCree = quiCreeTCivilite;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "quand_cree", length = 19)
	public Date getQuandCree() {
		return this.quandCree;
	}

	public void setQuandCree(Date quandCreeTCivilite) {
		this.quandCree = quandCreeTCivilite;
	}

	@Column(name = "qui_modif", length = 50)
	public String getQuiModif() {
		return this.quiModif;
	}

	public void setQuiModif(String quiModifTCivilite) {
		this.quiModif = quiModifTCivilite;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "quand_modif", length = 19)
	public Date getQuandModif() {
		return this.quandModif;
	}

	public void setQuandModif(Date quandModifTCivilite) {
		this.quandModif = quandModifTCivilite;
	}

	@Column(name = "ip_acces", length = 50)
	public String getIpAcces() {
		return this.ipAcces;
	}

	public void setIpAcces(String ipAcces) {
		this.ipAcces = ipAcces;
	}

	@Version
	@Column(name = "version_obj")
	public Integer getVersionObj() {
		return this.versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}
	
	@Column(name = "nom_fichier")
	public String getNomFichier() {
		return nomFichier;
	}

	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}
	@Column(name = "taille")
	public Integer getTaille() {
		return taille;
	}

	public void setTaille(Integer taille) {
		this.taille = taille;
	}
	@Column(name = "type_mime")
	public String getTypeMime() {
		return typeMime;
	}

	public void setTypeMime(String typeMime) {
		this.typeMime = typeMime;
	}
}
