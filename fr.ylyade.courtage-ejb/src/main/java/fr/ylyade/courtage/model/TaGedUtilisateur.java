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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "ta_ged_utilisateur")
//@SequenceGenerator(name = "gen_proforma", sequenceName = "num_id_proforma", allocationSize = 1)
@NamedQueries(value = { 
			    
//		@NamedQuery(name=TaGedUtilisateur.QN.FIND_BY_ID_DOC_AND_BY_ID_COURTIER,
//				query="select new fr.ylyade.courtage.dto.TaGedUtilisateurDTO(" + 
//						" f.idGedUtilisateur, f.nomFichier, f.taille, f.typeMime, f.fichierDoc, f.validationCourtier," + 
//						" f.commentaireCourtier, f.validationYlyade, f.commentaireYlyade, f.dateControleCourtier," + 
//						" f.dateControleYlyade, f.dateDepot, f.taCourtier.idCourtier, f.taCourtier.codeCourtier, " + 
//						" 0, '', f.taListeRefDoc.idListeRefDoc, f.taListeRefDoc.codeListeRefDoc, f.taListeRefDoc.liblDoc ) " + //si document courtier, pas d'id et de code assuré  
//						" from TaGedUtilisateur f " + 
//						" where f.taCourtier.idCourtier = :idCourtier and f.taListeRefDoc.idListeRefDoc = :idDoc")
		
		@NamedQuery(name=TaGedUtilisateur.QN.FIND_BY_ID_DOC_AND_BY_ID_COURTIER,
		query="select new fr.ylyade.courtage.dto.TaGedUtilisateurDTO(" + 
				" f.idGedUtilisateur, f.nomFichier, f.taille, f.typeMime, f.fichierDoc, f.validationCourtier," + 
				" f.commentaireCourtier, f.validationYlyade, f.commentaireYlyade,f.validationSuperAssureur, f.commentaireSuperAssureur, f.dateControleCourtier," + 
				" f.dateControleYlyade, f.dateDepot, f.taCourtier.idCourtier, f.taCourtier.codeCourtier, " + 
				" f.taListeRefDoc.idListeRefDoc, f.taListeRefDoc.codeListeRefDoc, f.taListeRefDoc.liblDoc ) " + //si document courtier, pas d'id et de code assuré  
				" from TaGedUtilisateur f " + 
				" where f.taCourtier.idCourtier = :idCourtier and f.taListeRefDoc.idListeRefDoc = :idDoc"),
		
//		@NamedQuery(name=TaGedUtilisateur.QN.FIND_BY_ID_DOC_AND_BY_ID_ASSURE,
//		query="select new fr.ylyade.courtage.dto.TaGedUtilisateurDTO(" + 
//				" f.idGedUtilisateur, f.nomFichier, f.taille, f.typeMime, f.fichierDoc, f.validationCourtier," + 
//				" f.commentaireCourtier, f.commentaireYlyade, f.dateControleCourtier," + 
//				" f.dateControleYlyade, f.dateDepot, f.taAssure.idAssure, f.taAssure.codeAssure, " + 
//				" f.taListeRefDoc.idListeRefDoc, f.taListeRefDoc.codeListeRefDoc, f.taListeRefDoc.liblDoc ) " + //si document courtier, pas d'id et de code assuré  
//				" from TaGedUtilisateur f " + 
//				" where f.taAssure.idAssure = :idAssure and f.taListeRefDoc.idListeRefDoc = :idDoc")
		
		
		
		@NamedQuery(name=TaGedUtilisateur.QN.FIND_BY_ID_DOC_AND_BY_ID_DOSSIER_RCD,
		query="select new fr.ylyade.courtage.dto.TaGedUtilisateurDTO(" + 
				" f.idGedUtilisateur, f.nomFichier, f.taille, f.typeMime, f.fichierDoc, f.validationCourtier," + 
				" f.commentaireCourtier,f.validationYlyade, f.commentaireYlyade, f.validationSuperAssureur, f.commentaireSuperAssureur, f.dateControleCourtier," + 
				" f.dateDepot, f.taDossierRcd.idDossierRcd, f.taDossierRcd.numDossierPolice, " + 
				" f.taListeRefDoc.idListeRefDoc, f.taListeRefDoc.codeListeRefDoc, f.taListeRefDoc.liblDoc ) " +
				" from TaGedUtilisateur f " + 
				" where f.taDossierRcd.idDossierRcd = :idDossierRcd and f.taListeRefDoc.idListeRefDoc = :idDoc and f.taListeRefDoc.actif = true")
	
})
public class TaGedUtilisateur implements Serializable {

	private static final long serialVersionUID = 2268184424293101106L;
	
	
	public static class QN {
		public static final String FIND_BY_ID_DOC_AND_BY_ID_COURTIER = "TaGedUtilisateur.findByIdDocAndByIdCourtierDTO";
		public static final String FIND_BY_ID_DOC_AND_BY_ID_DOSSIER_RCD = "TaGedUtilisateur.findByIdDocAndByIdDossierRcd";
	}

	private Integer idGedUtilisateur;
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;

	private byte[] fichierDoc;
	//private Integer fichierDoc;
	private Boolean validationCourtier = null;
	private String commentaireCourtier;
	private Boolean validationYlyade = null;
	private String commentaireYlyade;
	private Boolean validationSuperAssureur=null;
	private String commentaireSuperAssureur;
	private Date dateControleCourtier;
	private Date dateControleYlyade;
	private Date dateDepot;

    private TaCourtier taCourtier;
//    private TaAssure taAssure;
    private TaDossierRcd taDossierRcd;
    
    private TaListeRefDoc taListeRefDoc;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ged_utilisateur", unique = true, nullable = false)
	public Integer getIdGedUtilisateur() {
		return idGedUtilisateur;
	}

	public void setIdGedUtilisateur(Integer idGedUtilisateur) {
		this.idGedUtilisateur = idGedUtilisateur;
	}

	@Column(name = "fichier_doc")	
//	public Integer getFichierDoc() {
//	return fichierDoc;
//}
//
//public void setFichierDoc(Integer fichierDoc) {
//	this.fichierDoc = fichierDoc;
//}
	public byte[] getFichierDoc() {
		return fichierDoc;
	}

	public void setFichierDoc(byte[] fichierDoc) {
		this.fichierDoc = fichierDoc;
	}

	@Column(name = "validation_courtier")
	public Boolean getValidationCourtier() {
		return validationCourtier;
	}

	public void setValidationCourtier(Boolean validationCourtier) {
		this.validationCourtier = validationCourtier;
	}

	@Column(name = "commentaire_courtier")
	public String getCommentaireCourtier() {
		return commentaireCourtier;
	}

	public void setCommentaireCourtier(String commentaireCourtier) {
		this.commentaireCourtier = commentaireCourtier;
	}

	@Column(name = "validation_ylyade")
	public Boolean getValidationYlyade() {
		return validationYlyade;
	}

	public void setValidationYlyade(Boolean validationYlyade) {
		this.validationYlyade = validationYlyade;
	}

	@Column(name = "commentaire_ylyade")
	public String getCommentaireYlyade() {
		return commentaireYlyade;
	}

	public void setCommentaireYlyade(String commentaireYlyade) {
		this.commentaireYlyade = commentaireYlyade;
	}

	@Column(name = "date_controle_courtier")
	public Date getDateControleCourtier() {
		return dateControleCourtier;
	}

	public void setDateControleCourtier(Date dateControleCourtier) {
		this.dateControleCourtier = dateControleCourtier;
	}

	@Column(name = "date_controle_ylyade")
	public Date getDateControleYlyade() {
		return dateControleYlyade;
	}

	public void setDateControleYlyade(Date dateControleYlyade) {
		this.dateControleYlyade = dateControleYlyade;
	}

	@Column(name = "date_depot")
	public Date getDateDepot() {
		return dateDepot;
	}

	public void setDateDepot(Date dateDepot) {
		this.dateDepot = dateDepot;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_courtier_ta_courtier")
	public TaCourtier getTaCourtier() {
		return taCourtier;
	}

	public void setTaCourtier(TaCourtier taCourtier) {
		this.taCourtier = taCourtier;
	}

//	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
//	@JoinColumn(name = "id_assure_ta_assure")
//	public TaAssure getTaAssure() {
//		return taAssure;
//	}
//
//	public void setTaAssure(TaAssure taAssure) {
//		this.taAssure = taAssure;
//	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_liste_ref_doc_ta_liste_ref_doc")
	public TaListeRefDoc getTaListeRefDoc() {
		return taListeRefDoc;
	}

	public void setTaListeRefDoc(TaListeRefDoc taListeRefDoc) {
		this.taListeRefDoc = taListeRefDoc;
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

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_devis_rc_pro_ta_devis_rc_pro")
	public TaDossierRcd getTaDossierRcd() {
		return taDossierRcd;
	}

	public void setTaDossierRcd(TaDossierRcd taDevisRcPro) {
		this.taDossierRcd = taDevisRcPro;
	}
	@Column(name = "validation_super_assureur")
	public Boolean getValidationSuperAssureur() {
		return validationSuperAssureur;
	}

	public void setValidationSuperAssureur(Boolean validationSuperAssureur) {
		this.validationSuperAssureur = validationSuperAssureur;
	}
	@Column(name = "commentaire_super_assureur")
	public String getCommentaireSuperAssureur() {
		return commentaireSuperAssureur;
	}

	public void setCommentaireSuperAssureur(String commentaireSuperAssureur) {
		this.commentaireSuperAssureur = commentaireSuperAssureur;
	}
}
