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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "ta_sinistre")
public class TaSinistre implements Serializable {

	private static final long serialVersionUID = -1260188123909960582L;

	private Integer idSinistre;
	private String refSinistre;    
    private Date dateSinistre;
    private Date dateDeclaration;
    private String codeAssure; //--doublon avec l'id, sauf si pour garder un historique mais dans ce cas il faut garder plus d'infos (nom, prenom, adresse...)
    private String codeAssurance; //-- c'est quoi ?
    private String codeContrat; //-- cle etrangere vers contrat ?
    private String liblSinistre;
    private String description;
    
    private String adresseconstruction;
    private String objetTravauxAssure;
	private String coordonneesClient;
	private Boolean renovation;
	private Boolean extension;
	private Boolean constructionNeuve;
	private String travauxEtudeRealAssure;
	private Date debutTravaux;
	private Date dateReceptionTravaux;
	private Date dateReglDefinitifTravaux;
	private Date dateAchevementTravaux;
	private String commentaireReserve;
	private String assuranceDommageSouscrite;
	private String numContratDommageOuvrage;
	private String compagnieAssurance;
	private String assurePasseContratNom;
	private String assurePasseContratAdresse;
	private Boolean autresEntrepriseIntervenus;
	private Boolean contratPasseAvecSousTraitant;
	private String descriptionInterventionSousTraitant;
	private String adresseSousTraitant;
	private String assuranceSousTraitanceNom;
	private String assuranceSousTraitanceAdresse;
	private String nomPersonneSubitPrejudice;
	private String adressePersonneSubitPrejudice;
	private String natureDateDommages;
    
    private Boolean clotureSinistre = false;
    
	private TaAssure taAssure;
	private TaDossierRcd taDossierRcd;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_sinistre", unique = true, nullable = false)
	public Integer getIdSinistre() {
		return idSinistre;
	}

	public void setIdSinistre(Integer idSinistre) {
		this.idSinistre = idSinistre;
	}

	@Column(name = "ref_sinistre")
	public String getRefSinistre() {
		return refSinistre;
	}

	public void setRefSinistre(String refSinistre) {
		this.refSinistre = refSinistre;
	}

	@Column(name = "date_sinistre")
	public Date getDateSinistre() {
		return dateSinistre;
	}

	public void setDateSinistre(Date dateSinistre) {
		this.dateSinistre = dateSinistre;
	}

	@Column(name = "date_declaration")
	public Date getDateDeclaration() {
		return dateDeclaration;
	}

	public void setDateDeclaration(Date dateDeclaration) {
		this.dateDeclaration = dateDeclaration;
	}

	@Column(name = "code_assure")
	public String getCodeAssure() {
		return codeAssure;
	}

	public void setCodeAssure(String codeAssure) {
		this.codeAssure = codeAssure;
	}

	@Column(name = "code_assurance")
	public String getCodeAssurance() {
		return codeAssurance;
	}

	public void setCodeAssurance(String codeAssurance) {
		this.codeAssurance = codeAssurance;
	}

	@Column(name = "code_contrat")
	public String getCodeContrat() {
		return codeContrat;
	}

	public void setCodeContrat(String codeContrat) {
		this.codeContrat = codeContrat;
	}

	@Column(name = "libl_sinistre")
	public String getLiblSinistre() {
		return liblSinistre;
	}

	public void setLiblSinistre(String liblSinistre) {
		this.liblSinistre = liblSinistre;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "adresse_construction")
	public String getAdresseconstruction() {
		return adresseconstruction;
	}

	public void setAdresseconstruction(String adresseconstruction) {
		this.adresseconstruction = adresseconstruction;
	}

	@Column(name = "objet_travaux_assure")
	public String getObjetTravauxAssure() {
		return objetTravauxAssure;
	}

	public void setObjetTravauxAssure(String objetTravauxAssure) {
		this.objetTravauxAssure = objetTravauxAssure;
	}

	@Column(name = "coordonnees_client")
	public String getCoordonneesClient() {
		return coordonneesClient;
	}

	public void setCoordonneesClient(String coordonneesClient) {
		this.coordonneesClient = coordonneesClient;
	}

	@Column(name = "renovation")
	public Boolean getRenovation() {
		return renovation;
	}

	public void setRenovation(Boolean renovation) {
		this.renovation = renovation;
	}

	@Column(name = "extension")
	public Boolean getExtension() {
		return extension;
	}

	public void setExtension(Boolean extension) {
		this.extension = extension;
	}

	@Column(name = "construction_neuve")
	public Boolean getConstructionNeuve() {
		return constructionNeuve;
	}

	public void setConstructionNeuve(Boolean constructionNeuve) {
		this.constructionNeuve = constructionNeuve;
	}

	@Column(name = "travaux_etude_real_assure")
	public String getTravauxEtudeRealAssure() {
		return travauxEtudeRealAssure;
	}

	public void setTravauxEtudeRealAssure(String travauxEtudeRealAssure) {
		this.travauxEtudeRealAssure = travauxEtudeRealAssure;
	}

	@Column(name = "debut_travaux")
	public Date getDebutTravaux() {
		return debutTravaux;
	}

	public void setDebutTravaux(Date debutTravaux) {
		this.debutTravaux = debutTravaux;
	}

	@Column(name = "date_reception_travaux")
	public Date getDateReceptionTravaux() {
		return dateReceptionTravaux;
	}

	public void setDateReceptionTravaux(Date dateReceptionTravaux) {
		this.dateReceptionTravaux = dateReceptionTravaux;
	}

	@Column(name = "date_regl_definitif_travaux")
	public Date getDateReglDefinitifTravaux() {
		return dateReglDefinitifTravaux;
	}

	public void setDateReglDefinitifTravaux(Date dateReglDefinitifTravaux) {
		this.dateReglDefinitifTravaux = dateReglDefinitifTravaux;
	}

	@Column(name = "date_achevement_travaux")
	public Date getDateAchevementTravaux() {
		return dateAchevementTravaux;
	}

	public void setDateAchevementTravaux(Date dateAchevementTravaux) {
		this.dateAchevementTravaux = dateAchevementTravaux;
	}

	@Column(name = "commentaire_reserve")
	public String getCommentaireReserve() {
		return commentaireReserve;
	}

	public void setCommentaireReserve(String commentaireReserve) {
		this.commentaireReserve = commentaireReserve;
	}

	@Column(name = "assurance_dommage_souscrite")
	public String getAssuranceDommageSouscrite() {
		return assuranceDommageSouscrite;
	}

	public void setAssuranceDommageSouscrite(String assuranceDommageSouscrite) {
		this.assuranceDommageSouscrite = assuranceDommageSouscrite;
	}

	@Column(name = "num_contrat_dommage_ouvrage")
	public String getNumContratDommageOuvrage() {
		return numContratDommageOuvrage;
	}

	public void setNumContratDommageOuvrage(String numContratDommageOuvrage) {
		this.numContratDommageOuvrage = numContratDommageOuvrage;
	}

	@Column(name = "compagnie_assurance")
	public String getCompagnieAssurance() {
		return compagnieAssurance;
	}

	public void setCompagnieAssurance(String compagnieAssurance) {
		this.compagnieAssurance = compagnieAssurance;
	}

	@Column(name = "assure_passe_contrat_nom")
	public String getAssurePasseContratNom() {
		return assurePasseContratNom;
	}

	public void setAssurePasseContratNom(String assurePasseContratNom) {
		this.assurePasseContratNom = assurePasseContratNom;
	}

	@Column(name = "assure_passe_contrat_adresse")
	public String getAssurePasseContratAdresse() {
		return assurePasseContratAdresse;
	}

	public void setAssurePasseContratAdresse(String assurePasseContratAdresse) {
		this.assurePasseContratAdresse = assurePasseContratAdresse;
	}

	@Column(name = "assure_passe_contrat_intervenus")
	public Boolean getAutresEntrepriseIntervenus() {
		return autresEntrepriseIntervenus;
	}

	public void setAutresEntrepriseIntervenus(Boolean autresEntrepriseIntervenus) {
		this.autresEntrepriseIntervenus = autresEntrepriseIntervenus;
	}

	@Column(name = "contrat_passe_avec_sous_traitant")
	public Boolean getContratPasseAvecSousTraitant() {
		return contratPasseAvecSousTraitant;
	}

	public void setContratPasseAvecSousTraitant(Boolean contratPasseAvecSousTraitant) {
		this.contratPasseAvecSousTraitant = contratPasseAvecSousTraitant;
	}

	@Column(name = "description_intervention_sous_traitant")
	public String getDescriptionInterventionSousTraitant() {
		return descriptionInterventionSousTraitant;
	}

	public void setDescriptionInterventionSousTraitant(String descriptionInterventionSousTraitant) {
		this.descriptionInterventionSousTraitant = descriptionInterventionSousTraitant;
	}

	@Column(name = "adresse_sous_traitant")
	public String getAdresseSousTraitant() {
		return adresseSousTraitant;
	}

	public void setAdresseSousTraitant(String adresseSousTraitant) {
		this.adresseSousTraitant = adresseSousTraitant;
	}

	@Column(name = "assurance_sous_traitance_nom")
	public String getAssuranceSousTraitanceNom() {
		return assuranceSousTraitanceNom;
	}

	public void setAssuranceSousTraitanceNom(String assuranceSousTraitanceNom) {
		this.assuranceSousTraitanceNom = assuranceSousTraitanceNom;
	}

	@Column(name = "assurance_sous_traitance_adresse")
	public String getAssuranceSousTraitanceAdresse() {
		return assuranceSousTraitanceAdresse;
	}

	public void setAssuranceSousTraitanceAdresse(String assuranceSousTraitanceAdresse) {
		this.assuranceSousTraitanceAdresse = assuranceSousTraitanceAdresse;
	}

	@Column(name = "nom_personne_subit_prejudice")
	public String getNomPersonneSubitPrejudice() {
		return nomPersonneSubitPrejudice;
	}

	public void setNomPersonneSubitPrejudice(String nomPersonneSubitPrejudice) {
		this.nomPersonneSubitPrejudice = nomPersonneSubitPrejudice;
	}

	@Column(name = "adresse_personne_subit_prejudice")
	public String getAdressePersonneSubitPrejudice() {
		return adressePersonneSubitPrejudice;
	}

	public void setAdressePersonneSubitPrejudice(String adressePersonneSubitPrejudice) {
		this.adressePersonneSubitPrejudice = adressePersonneSubitPrejudice;
	}

	@Column(name = "nature_date_dommages")
	public String getNatureDateDommages() {
		return natureDateDommages;
	}

	public void setNatureDateDommages(String natureDateDommages) {
		this.natureDateDommages = natureDateDommages;
	}

	@Column(name = "cloture_sinistre")
	public Boolean getClotureSinistre() {
		return clotureSinistre;
	}

	public void setClotureSinistre(Boolean clotureSinistre) {
		this.clotureSinistre = clotureSinistre;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_assure_ta_assure")
	public TaAssure getTaAssure() {
		return taAssure;
	}

	public void setTaAssure(TaAssure taAssure) {
		this.taAssure = taAssure;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_dossier_rcd_ta_dossier_rcd")
	public TaDossierRcd getTaDossierRcd() {
		return taDossierRcd;
	}

	public void setTaDossierRcd(TaDossierRcd taDossierRcd) {
		this.taDossierRcd = taDossierRcd;
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
	
	@PrePersist
	@PreUpdate
	public void beforePost ()throws Exception{
		if(natureDateDommages!= null) {
			  this.setNatureDateDommages(natureDateDommages.toUpperCase());
		}

	}
}
