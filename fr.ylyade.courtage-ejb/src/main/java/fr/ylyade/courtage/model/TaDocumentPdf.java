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
@Table(name = "ta_document_pdf")
public class TaDocumentPdf implements Serializable {

	private static final long serialVersionUID = 2492669331846772400L;
	
	
	public static final String CODE_DOC_CO_COURTAGE = "co-courtage";
	
	private Integer idDocumentPdf;
	
	private Integer numeroVersion;
	private String codeDoc;
	private String liblDoc;
	private String description;
	private Integer positionListe;
	private byte[] imgDoc;
	
	private TaTDoc taTDoc;
	
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
	@Column(name = "id_document_pdf", unique = true, nullable = false)
	public Integer getIdDocumentPdf() {
		return idDocumentPdf;
	}

	public void setIdDocumentPdf(Integer idDocumentPdf) {
		this.idDocumentPdf = idDocumentPdf;
	}

	@Column(name = "numero_version")
	public Integer getNumeroVersion() {
		return numeroVersion;
	}

	public void setNumeroVersion(Integer numeroVersion) {
		this.numeroVersion = numeroVersion;
	}

	@Column(name = "code_doc")
	public String getCodeDoc() {
		return codeDoc;
	}

	public void setCodeDoc(String codeDoc) {
		this.codeDoc = codeDoc;
	}

	@Column(name = "libl_doc")
	public String getLiblDoc() {
		return liblDoc;
	}

	public void setLiblDoc(String liblDoc) {
		this.liblDoc = liblDoc;
	}

	@Column(name = "description_doc")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "img_doc")
	public byte[] getImgDoc() {
		return imgDoc;
	}

	public void setImgDoc(byte[] imgDoc) {
		this.imgDoc = imgDoc;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_doc_ta_t_doc")
	public TaTDoc getTaTDoc() {
		return taTDoc;
	}

	public void setTaTDoc(TaTDoc taTDoc) {
		this.taTDoc = taTDoc;
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
	@Column(name = "position_liste")
	public Integer getPositionListe() {
		return positionListe;
	}

	public void setPositionListe(Integer positionListe) {
		this.positionListe = positionListe;
	}
}
