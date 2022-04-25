package fr.ylyade.courtage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import fr.ylyade.courtage.droits.model.TaUtilisateur;

@Entity
@Table(name = "ta_assure")
@NamedQueries(value = { 
		@NamedQuery(name=TaAssure.QN.FIND_ALL_LIGHT_ACTIF, 
			query="select new fr.ylyade.courtage.dto.TaAssureDTO(a.idAssure,a.codeAssure,a.codeSiret,a.raisonSociale,a.actif,a.dateNaissance,a.client,"
				+ "tce.nom, tce.prenom,"
				+ "ta.idTAssure, ta.codeTAssure,"
				+ "c.idCourtier, c.codeCourtier) "
				+ " from TaAssure a left join a.taTAssure ta left join a.taContactEntreprise tce left join a.taCourtier c "
				+ " where a.actif=true order by a.codeAssure"),
		@NamedQuery(name=TaAssure.QN.FIND_BY_CODE_LIGHT, 
			query="select new fr.ylyade.courtage.dto.TaAssureDTO(a.idAssure,a.codeAssure,a.codeSiret,a.raisonSociale,a.actif,a.dateNaissance,a.client,"
				+ "tce.nom, tce.prenom,"
				+ "ta.idTAssure, ta.codeTAssure,"
				+ "c.idCourtier, c.codeCourtier) "
				+ " from TaAssure a left join a.taTAssure ta left join a.taContactEntreprise tce left join a.taCourtier c "
				+ " where a.codeAssure like :codeAssure order by a.codeAssure"),
		@NamedQuery(name=TaAssure.QN.FIND_ALL_LIGHT_PLUS, 
			query="select new fr.ylyade.courtage.dto.TaAssureDTO(a.idAssure,a.codeAssure,a.codeSiret,a.raisonSociale,a.actif,a.dateNaissance,a.client,"
				+ "tce.nom, tce.prenom,"
				+ "ta.idTAssure, ta.codeTAssure,"
				+ "c.idCourtier, c.codeCourtier) "
				+ " from TaAssure a left join a.taTAssure ta left join a.taContactEntreprise tce left join a.taCourtier c "
				+ " order by a.codeAssure"),
		
		@NamedQuery(name=TaAssure.QN.FIND_ALL_LIGHT_PLUS_EXTRACTION, 
		query="select new fr.ylyade.courtage.dto.TaAssureDTO(a.idAssure,a.codeAssure,a.codeSiret,a.raisonSociale,a.actif,a.dateNaissance,a.client,"
			+ "tce.nom, tce.prenom,"
			+ "ta.idTAssure, ta.codeTAssure,"
			+ "c.idCourtier, c.codeCourtier,"
			+ "civ.liblTCivilite, mail.adresseEmail, tel.numeroTel, adr.adresseLigne1 , adr.adresseLigne2, adr.adresseLigne3, adr.ville, adr.pays, adr.codePostal) "
			+ " from TaAssure a left join a.taTAssure ta left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " left join tce.taTCivilite civ left join tce.taEmail mail left join tce.taTel tel left join tce.taAdresse adr"
			+ " order by a.codeAssure"),
		
		
		@NamedQuery(name=TaAssure.QN.FIND_ALL_BY_ID_COURTIER, 
		query="select new fr.ylyade.courtage.dto.TaAssureDTO(a.idAssure,a.codeAssure,a.codeSiret,a.raisonSociale,a.actif,a.dateNaissance,a.client,"
			+ "tce.nom, tce.prenom,"
			+ "ta.idTAssure, ta.codeTAssure,"
			+ "c.idCourtier, c.codeCourtier) "
			+ " from TaAssure a left join a.taTAssure ta left join a.taContactEntreprise tce left join a.taCourtier c "
			+ " where c.idCourtier = :idCourtier order by a.codeAssure"),
		
		@NamedQuery(name=TaAssure.QN.COUNT_ACTIVE_BY_ID_COURTIER, 
		query="select count(a.idAssure) "
			+ " from TaAssure a left join a.taCourtier c "
			+ " where c.idCourtier = :idCourtier and a.actif = true") 
		
 
		})
public class TaAssure implements Serializable {

	private static final long serialVersionUID = -8749957633143387379L;
	
	public static class QN {
		public static final String FIND_ALL_LIGHT_ACTIF = "TaAssure.findAllLightActif";
		public static final String FIND_BY_CODE_LIGHT = "TaAssure.findByCodeLight";
		public static final String FIND_ALL_LIGHT_PLUS = "TaAssure.findAllLightPlus";
		public static final String FIND_ALL_LIGHT_PLUS_EXTRACTION="TaAssure.findAllLightPlusExtraction";
		public static final String FIND_ALL_BY_ID_COURTIER = "TaAssure.findAllByIdCourtier";
		public static final String COUNT_ACTIVE_BY_ID_COURTIER = "TaAssure.countActiveByIdCourtier";
		
	}
	
	private Integer idAssure;
	private String codeAssure;
	
//	private String nomFichier;
//	private Integer taille;
//	private String typeMime;

	private String codeSiret;
	

	private String codeSiren;
	private String codeApe;
	private String tvaIntraComm;
	private String qualifications;
	private String diplome;
	private String raisonSociale;
	private Date dateCreation;
	private Date dateClotureBilan;
	private Boolean actif = false;
	private Boolean client= false;
	private Boolean domiciliationEtranger = false;
	private String activitePrincipale;
	private Date dateNaissance;
	private BigDecimal capital;
	private String imatRegistreCommSocieteVille;
	private String numImat;
	private BigDecimal chiffreAffaire;
//	private BigDecimal chiffreAffaireExerciceAnterieur;
	private BigDecimal chiffreAffaireExercicePrevisionnel;
	
	private Integer effectifTotalExercice;
//	private Integer effectifTotalExerciceAnterieur;
		
	private TaTAssure taTAssure;
	private TaCourtier taCourtier;
	private TaUtilisateur taUtilisateur;
	private TaTJuridique taTJuridique;
	private TaAttestationCompetences taAttestationCompetences;
	
	private TaContactEntreprise taContactEntreprise;
	private List<TaContactEntreprise> taContactEntreprises = new ArrayList<>(0);
	
	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;
	 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_assure", unique = true, nullable = false)
	public Integer getIdAssure() {
		return idAssure;
	}

	public void setIdAssure(Integer idAssure) {
		this.idAssure = idAssure;
	}

	@Column(name = "code_assure")
	public String getCodeAssure() {
		return codeAssure;
	}

	public void setCodeAssure(String codeAssure) {
		this.codeAssure = codeAssure;
	}

	@Column(name = "code_siret")
	public String getCodeSiret() {
		return codeSiret;
	}

	public void setCodeSiret(String codeSiret) {
		this.codeSiret = codeSiret;
	}

	@Column(name = "code_siren")
	public String getCodeSiren() {
		return codeSiren;
	}

	public void setCodeSiren(String codeSiren) {
		this.codeSiren = codeSiren;
	}

	@Column(name = "code_ape")
	public String getCodeApe() {
		return codeApe;
	}

	public void setCodeApe(String codeApe) {
		this.codeApe = codeApe;
	}

	@Column(name = "tva_intracom")
	public String getTvaIntraComm() {
		return tvaIntraComm;
	}

	public void setTvaIntraComm(String tvaIntraComm) {
		this.tvaIntraComm = tvaIntraComm;
	}

	@Column(name = "qualifications")
	public String getQualifications() {
		return qualifications;
	}

	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}

	@Column(name = "diplome")
	public String getDiplome() {
		return diplome;
	}

	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}

	@Column(name = "raison_sociale")
	public String getRaisonSociale() {
		return raisonSociale;
	}

	public void setRaisonSociale(String raisonSociale) {
		this.raisonSociale = raisonSociale;
	}

	@Column(name = "date_creation")
	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Column(name = "date_cloture_bilan")
	public Date getDateClotureBilan() {
		return dateClotureBilan;
	}

	public void setDateClotureBilan(Date dateClotureBilan) {
		this.dateClotureBilan = dateClotureBilan;
	}

	@Column(name = "actif")
	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	@Column(name = "activite_principale")
	public String getActivitePrincipale() {
		return activitePrincipale;
	}

	public void setActivitePrincipale(String activitePrincipale) {
		this.activitePrincipale = activitePrincipale;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_naissance")
	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	@Column(name = "capital")
	public BigDecimal getCapital() {
		return capital;
	}

	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}

	@Column(name = "imat_registre_comm_societe_ville")
	public String getImatRegistreCommSocieteVille() {
		return imatRegistreCommSocieteVille;
	}

	public void setImatRegistreCommSocieteVille(String imatRegistreCommSocieteVille) {
		this.imatRegistreCommSocieteVille = imatRegistreCommSocieteVille;
	}

	@Column(name = "num_imat")
	public String getNumImat() {
		return numImat;
	}

	public void setNumImat(String numImat) {
		this.numImat = numImat;
	}

	@Column(name = "chiffre_affaire")
	public BigDecimal getChiffreAffaire() {
		return chiffreAffaire;
	}

	public void setChiffreAffaire(BigDecimal chiffreAffaire) {
		this.chiffreAffaire = chiffreAffaire;
	}

//	@Column(name = "chiffre_affaire_exercice_anterieur")
//	public BigDecimal getChiffreAffaireExerciceAnterieur() {
//		return chiffreAffaireExerciceAnterieur;
//	}
//
//	public void setChiffreAffaireExerciceAnterieur(BigDecimal chiffreAffaireExerciceAnterieur) {
//		this.chiffreAffaireExerciceAnterieur = chiffreAffaireExerciceAnterieur;
//	}

	@Column(name = "chiffre_affaire_exercice_previsionnel")
	public BigDecimal getChiffreAffaireExercicePrevisionnel() {
		return chiffreAffaireExercicePrevisionnel;
	}

	public void setChiffreAffaireExercicePrevisionnel(BigDecimal chiffreAffaireExercicePrevisionnel) {
		this.chiffreAffaireExercicePrevisionnel = chiffreAffaireExercicePrevisionnel;
	}

	@Column(name = "effectif_total_exercice")
	public Integer getEffectifTotalExercice() {
		return effectifTotalExercice;
	}

	public void setEffectifTotalExercice(Integer effectifTotalExercice) {
		this.effectifTotalExercice = effectifTotalExercice;
	}

//	@Column(name = "effectif_total_exercice_anterieur")
//	public Integer getEffectifTotalExerciceAnterieur() {
//		return effectifTotalExerciceAnterieur;
//	}
//
//	public void setEffectifTotalExerciceAnterieur(Integer effectifTotalExerciceAnterieur) {
//		this.effectifTotalExerciceAnterieur = effectifTotalExerciceAnterieur;
//	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_assure_ta_t_assure")
	public TaTAssure getTaTAssure() {
		return taTAssure;
	}

	public void setTaTAssure(TaTAssure taTAssure) {
		this.taTAssure = taTAssure;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_courtier_ta_courtier")
	public TaCourtier getTaCourtier() {
		return taCourtier;
	}

	public void setTaCourtier(TaCourtier taCourtier) {
		this.taCourtier = taCourtier;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "id_utilisateur_ta_utilisateur")
	public TaUtilisateur getTaUtilisateur() {
		return taUtilisateur;
	}

	public void setTaUtilisateur(TaUtilisateur taUtilisateur) {
		this.taUtilisateur = taUtilisateur;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_juridique_ta_t_juridique")
	public TaTJuridique getTaTJuridique() {
		return taTJuridique;
	}

	public void setTaTJuridique(TaTJuridique taTJuridique) {
		this.taTJuridique = taTJuridique;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_attestation_competences_ta_attestation_competences")
	public TaAttestationCompetences getTaAttestationCompetences() {
		return taAttestationCompetences;
	}

	public void setTaAttestationCompetences(TaAttestationCompetences taAttestationCompetences) {
		this.taAttestationCompetences = taAttestationCompetences;
	}
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name = "id_ta_contact_entreprise_ta_contact_entreprise")
	public TaContactEntreprise getTaContactEntreprise() {
		return taContactEntreprise;
	}

	public void setTaContactEntreprise(TaContactEntreprise taContactEntreprise) {
		this.taContactEntreprise = taContactEntreprise;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "taAssure", orphanRemoval=true)
	@Fetch(FetchMode.SUBSELECT)
	public List<TaContactEntreprise> getTaContactEntreprises() {
		return taContactEntreprises;
	}

	public void setTaContactEntreprises(List<TaContactEntreprise> taContactEntreprises) {
		this.taContactEntreprises = taContactEntreprises;
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
	
//	@Column(name = "nom_fichier")
//	public String getNomFichier() {
//		return nomFichier;
//	}
//
//	public void setNomFichier(String nomFichier) {
//		this.nomFichier = nomFichier;
//	}
//	@Column(name = "taille")
//	public Integer getTaille() {
//		return taille;
//	}
//
//	public void setTaille(Integer taille) {
//		this.taille = taille;
//	}
//	@Column(name = "type_mime")
//	public String getTypeMime() {
//		return typeMime;
//	}
//
//	public void setTypeMime(String typeMime) {
//		this.typeMime = typeMime;
//	}
	
	public void initContactEntrepriseAssure(Object value) {
		if(this.getTaContactEntreprise()==null && (value!=null&&!value.equals(""))) {
			TaContactEntreprise p = new TaContactEntreprise();
			
			p.setTaAssure(this);
			this.setTaContactEntreprise(p);
			this.addContactEntreprise(p);
		}else if(this.getTaContactEntreprise()!=null && (value==null||value.equals(""))) {
			this.removeContactEntreprise(this.getTaContactEntreprise());
			this.setTaContactEntreprise(null);
		}
	}
	
	public void addContactEntreprise(TaContactEntreprise taContactEntreprise){
		if(!this.getTaContactEntreprises().contains(taContactEntreprise))
			this.getTaContactEntreprises().add(taContactEntreprise);
		if(this.getTaContactEntreprise()==null)this.setTaContactEntreprise(taContactEntreprise);
	}
	
	public void removeContactEntreprise(TaContactEntreprise taContactEntreprise){
		boolean estDefaut=(this.taContactEntreprise!=null)&& this.taContactEntreprise.getIdContactEntreprise()==taContactEntreprise.getIdContactEntreprise();

		this.getTaContactEntreprises().remove(taContactEntreprise);
		if(estDefaut && !this.getTaContactEntreprises().isEmpty())
			this.setTaContactEntreprise(this.getTaContactEntreprises().iterator().next());
		else if(this.getTaContactEntreprises().isEmpty()){
			this.setTaContactEntreprise(null);
		}  
	}
	@Column(name = "client")
	public Boolean getClient() {
		return client;
	}

	public void setClient(Boolean client) {
		this.client = client;
	}

	
	@Column(name = "domiciliation_etranger")
	public Boolean getDomiciliationEtranger() {
		return domiciliationEtranger;
	}

	public void setDomiciliationEtranger(Boolean domiciliationEtranger) {
		this.domiciliationEtranger = domiciliationEtranger;
	}
	
	@PrePersist
	@PreUpdate
	public void beforePost ()throws Exception{
		if(raisonSociale != null) {
			this.setRaisonSociale(raisonSociale.toUpperCase());
		}
		
	}
}
