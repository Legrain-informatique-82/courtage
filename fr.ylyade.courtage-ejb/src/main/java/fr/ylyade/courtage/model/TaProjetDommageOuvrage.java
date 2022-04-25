package fr.ylyade.courtage.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "taProjetDommageOuvrage")

@NamedQueries(value = { 
		@NamedQuery(name=TaProjetDommageOuvrage.QN.COUNT_ACTIVE_BY_ID_COURTIER,
		query = "select count(f.idProjetDommageOuvrage)" +
		        " from TaProjetDommageOuvrage f left join f.taAssure a left join a.taCourtier c" +
				" where c.idCourtier = :idCourtier and (f.taContratDommageOuvrage IS NULL or f.dateFinValiditeProjet < :now )")
		
		
})

public class TaProjetDommageOuvrage implements Serializable {

	private static final long serialVersionUID = 103776945084495382L;
	
	public static class QN {
		public static final String COUNT_ACTIVE_BY_ID_COURTIER = "TaProjetDommageOuvrage.countActiveByIdCourtier";
	}
	
	private Integer idProjetDommageOuvrage;
	
	

	private String numProjetDommageOuvrage;
	private BigDecimal tauxCommissionCourtier;
	private BigDecimal montantCommissionCourtier;
	private BigDecimal fraisCourtier;
	private BigDecimal tauxDommageOuvrage;
	private BigDecimal primeassuranceNue;
	private BigDecimal tauxTaxeFiscale;
	private BigDecimal valeurTaxeAttentat;
	private byte[] imgProjetDommageOuvrage;
	private String numClient;
	private String nomProposant;
	private String prenomProposant;
	private String qualitePreposant;
	private String adresseProposant;
	private String nomMaitreOuvrage;
	private String prenomMaitreOuvrage;
	private String adresseOperationConstruction;
	private String adresseMaitreOuvrage;
	private Integer nbBatiments;
	private Integer nbLogements;
	private Integer nbSousSols;
	private Integer nbEtages;
	private Boolean cnr = false;
	private Date dateOuvertureChantier;
	private Date dateReceptionTravaux;
	private BigDecimal coutTotalConstruction;
	private String numPermisConstruire;
	private String descriptionOperation;
	
	private Date dateFinValiditeProjet;
	
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	
	private TaAssure taAssure;
	private TaContratDommageOuvrage taContratDommageOuvrage;
	private TaTAssurance taTAssurance;
	private TaTConstruction taTConstruction;
	private TaTTravaux taTTravaux;
	private TaTDestinationUsage taTDestinationUsage;
	private TaTMaitriseOeuvre taTMaitriseOeuvre;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProjetDommageOuvrage", unique = true, nullable = false)
	public Integer getIdProjetDommageOuvrage() {
		return idProjetDommageOuvrage;
	}

	public void setIdProjetDommageOuvrage(Integer idProjetDommageOuvrage) {
		this.idProjetDommageOuvrage = idProjetDommageOuvrage;
	}

	@Column(name = "num_projet_dommage_ouvrage")
	public String getNumProjetDommageOuvrage() {
		return numProjetDommageOuvrage;
	}

	public void setNumProjetDommageOuvrage(String numProjetDommageOuvrage) {
		this.numProjetDommageOuvrage = numProjetDommageOuvrage;
	}

	@Column(name = "taux_commission_courtier")
	public BigDecimal getTauxCommissionCourtier() {
		return tauxCommissionCourtier;
	}

	public void setTauxCommissionCourtier(BigDecimal tauxCommissionCourtier) {
		this.tauxCommissionCourtier = tauxCommissionCourtier;
	}

	@Column(name = "montant_commission_courtier")
	public BigDecimal getMontantCommissionCourtier() {
		return montantCommissionCourtier;
	}

	public void setMontantCommissionCourtier(BigDecimal montantCommissionCourtier) {
		this.montantCommissionCourtier = montantCommissionCourtier;
	}

	@Column(name = "frais_courtier")
	public BigDecimal getFraisCourtier() {
		return fraisCourtier;
	}

	public void setFraisCourtier(BigDecimal fraisCourtier) {
		this.fraisCourtier = fraisCourtier;
	}

	@Column(name = "taux_dommage_ouvrage")
	public BigDecimal getTauxDommageOuvrage() {
		return tauxDommageOuvrage;
	}

	public void setTauxDommageOuvrage(BigDecimal tauxDommageOuvrage) {
		this.tauxDommageOuvrage = tauxDommageOuvrage;
	}

	@Column(name = "prime_assurance_nue")
	public BigDecimal getPrimeassuranceNue() {
		return primeassuranceNue;
	}

	public void setPrimeassuranceNue(BigDecimal primeassuranceNue) {
		this.primeassuranceNue = primeassuranceNue;
	}

	@Column(name = "taux_taxe_fiscale")
	public BigDecimal getTauxTaxeFiscale() {
		return tauxTaxeFiscale;
	}

	public void setTauxTaxeFiscale(BigDecimal tauxTaxeFiscale) {
		this.tauxTaxeFiscale = tauxTaxeFiscale;
	}

	@Column(name = "valeur_taxe_attentat")
	public BigDecimal getValeurTaxeAttentat() {
		return valeurTaxeAttentat;
	}

	public void setValeurTaxeAttentat(BigDecimal valeurTaxeAttentat) {
		this.valeurTaxeAttentat = valeurTaxeAttentat;
	}

	@Column(name = "img_projet_dommage_ouvrage")
	public byte[] getImgProjetDommageOuvrage() {
		return imgProjetDommageOuvrage;
	}

	public void setImgProjetDommageOuvrage(byte[] imgProjetDommageOuvrage) {
		this.imgProjetDommageOuvrage = imgProjetDommageOuvrage;
	}

	@Column(name = "num_client")
	public String getNumClient() {
		return numClient;
	}

	public void setNumClient(String numClient) {
		this.numClient = numClient;
	}

	@Column(name = "nom_proposant")
	public String getNomProposant() {
		return nomProposant;
	}

	public void setNomProposant(String nomProposant) {
		this.nomProposant = nomProposant;
	}

	@Column(name = "prenom_proposant")
	public String getPrenomProposant() {
		return prenomProposant;
	}

	public void setPrenomProposant(String prenomProposant) {
		this.prenomProposant = prenomProposant;
	}

	@Column(name = "qualite_preposant")
	public String getQualitePreposant() {
		return qualitePreposant;
	}

	public void setQualitePreposant(String qualitePreposant) {
		this.qualitePreposant = qualitePreposant;
	}

	@Column(name = "adresse_proposant")
	public String getAdresseProposant() {
		return adresseProposant;
	}

	public void setAdresseProposant(String adresseProposant) {
		this.adresseProposant = adresseProposant;
	}

	@Column(name = "nom_maitre_ouvrage")
	public String getNomMaitreOuvrage() {
		return nomMaitreOuvrage;
	}

	public void setNomMaitreOuvrage(String nomMaitreOuvrage) {
		this.nomMaitreOuvrage = nomMaitreOuvrage;
	}

	@Column(name = "prenom_maitre_ouvrage")
	public String getPrenomMaitreOuvrage() {
		return prenomMaitreOuvrage;
	}

	public void setPrenomMaitreOuvrage(String prenomMaitreOuvrage) {
		this.prenomMaitreOuvrage = prenomMaitreOuvrage;
	}

	@Column(name = "adresse_operation_construction")
	public String getAdresseOperationConstruction() {
		return adresseOperationConstruction;
	}

	public void setAdresseOperationConstruction(String adresseOperationConstruction) {
		this.adresseOperationConstruction = adresseOperationConstruction;
	}

	@Column(name = "adresse_maitre_ouvrage")
	public String getAdresseMaitreOuvrage() {
		return adresseMaitreOuvrage;
	}

	public void setAdresseMaitreOuvrage(String adresseMaitreOuvrage) {
		this.adresseMaitreOuvrage = adresseMaitreOuvrage;
	}

	@Column(name = "nb_batiments")
	public Integer getNbBatiments() {
		return nbBatiments;
	}

	public void setNbBatiments(Integer nbBatiments) {
		this.nbBatiments = nbBatiments;
	}

	@Column(name = "nb_logements")
	public Integer getNbLogements() {
		return nbLogements;
	}

	public void setNbLogements(Integer nbLogements) {
		this.nbLogements = nbLogements;
	}

	@Column(name = "nb_sous_sols")
	public Integer getNbSousSols() {
		return nbSousSols;
	}

	public void setNbSousSols(Integer nbSousSols) {
		this.nbSousSols = nbSousSols;
	}

	@Column(name = "nb_etages")
	public Integer getNbEtages() {
		return nbEtages;
	}

	public void setNbEtages(Integer nbEtages) {
		this.nbEtages = nbEtages;
	}

	@Column(name = "cnr")
	public Boolean getCnr() {
		return cnr;
	}

	public void setCnr(Boolean cnr) {
		this.cnr = cnr;
	}

	@Column(name = "date_ouverture_chantier")
	public Date getDateOuvertureChantier() {
		return dateOuvertureChantier;
	}

	public void setDateOuvertureChantier(Date dateOuvertureChantier) {
		this.dateOuvertureChantier = dateOuvertureChantier;
	}

	@Column(name = "date_reception_travaux")
	public Date getDateReceptionTravaux() {
		return dateReceptionTravaux;
	}

	public void setDateReceptionTravaux(Date dateReceptionTravaux) {
		this.dateReceptionTravaux = dateReceptionTravaux;
	}

	@Column(name = "cout_total_construction")
	public BigDecimal getCoutTotalConstruction() {
		return coutTotalConstruction;
	}

	public void setCoutTotalConstruction(BigDecimal coutTotalConstruction) {
		this.coutTotalConstruction = coutTotalConstruction;
	}

	@Column(name = "num_permis_construire")
	public String getNumPermisConstruire() {
		return numPermisConstruire;
	}

	public void setNumPermisConstruire(String numPermisConstruire) {
		this.numPermisConstruire = numPermisConstruire;
	}

	@Column(name = "description_operation")
	public String getDescriptionOperation() {
		return descriptionOperation;
	}

	public void setDescriptionOperation(String descriptionOperation) {
		this.descriptionOperation = descriptionOperation;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_assure_ta_assure")
	public TaAssure getTaAssure() {
		return taAssure;
	}

	public void setTaAssure(TaAssure taAssure) {
		this.taAssure = taAssure;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_contrat_dommage_ouvrage_ta_contrat_dommage_ouvrage")
	public TaContratDommageOuvrage getTaContratDommageOuvrage() {
		return taContratDommageOuvrage;
	}

	public void setTaContratDommageOuvrage(TaContratDommageOuvrage taContratDommageOuvrage) {
		this.taContratDommageOuvrage = taContratDommageOuvrage;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_assurance_ta_t_assurance")
	public TaTAssurance getTaTAssurance() {
		return taTAssurance;
	}

	public void setTaTAssurance(TaTAssurance taTAssurance) {
		this.taTAssurance = taTAssurance;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_construction_ta_t_construction")
	public TaTConstruction getTaTConstruction() {
		return taTConstruction;
	}

	public void setTaTConstruction(TaTConstruction taTConstruction) {
		this.taTConstruction = taTConstruction;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_travaux_ta_t_travaux")
	public TaTTravaux getTaTTravaux() {
		return taTTravaux;
	}

	public void setTaTTravaux(TaTTravaux taTTravaux) {
		this.taTTravaux = taTTravaux;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_destination_usage_ta_t_destination_usage")
	public TaTDestinationUsage getTaTDestinationUsage() {
		return taTDestinationUsage;
	}

	public void setTaTDestinationUsage(TaTDestinationUsage taTDestinationUsage) {
		this.taTDestinationUsage = taTDestinationUsage;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_maitrise_oeuvre_ta_t_maitrise_oeuvre")
	public TaTMaitriseOeuvre getTaTMaitriseOeuvre() {
		return taTMaitriseOeuvre;
	}

	public void setTaTMaitriseOeuvre(TaTMaitriseOeuvre taTMaitriseOeuvre) {
		this.taTMaitriseOeuvre = taTMaitriseOeuvre;
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

	@Column(name = "quiModif", length = 50)
	public String getQuiModif() {
		return this.quiModif;
	}

	public void setQuiModif(String quiModifTCivilite) {
		this.quiModif = quiModifTCivilite;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "quandModif", length = 19)
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
	
	@Column(name = "date_fin_validite_projet")
	public Date getDateFinValiditeProjet() {
		return dateFinValiditeProjet;
	}

	public void setDateFinValiditeProjet(Date dateFinValiditeProjet) {
		this.dateFinValiditeProjet = dateFinValiditeProjet;
	}

}
