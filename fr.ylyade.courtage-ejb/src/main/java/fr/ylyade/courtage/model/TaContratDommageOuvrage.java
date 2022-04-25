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
@Table(name = "ta_contrat_dommage_ouvrage")

@NamedQueries(value = {  

		
		@NamedQuery(name=TaContratDommageOuvrage.QN.COUNT_ACTIVE_BY_ID_COURTIER,
		query = "select count(f.idContratDommageOuvrage)" +
		        " from TaContratDommageOuvrage f left join f.taAssure a left join a.taCourtier c" +
		        " where c.idCourtier = :idCourtier ")
		
		
				
					

})
public class TaContratDommageOuvrage implements Serializable {

	private static final long serialVersionUID = 2288939659934574144L;
	
	public static class QN {
		
		public static final String COUNT_ACTIVE_BY_ID_COURTIER = "TaContratDommageOuvrage.countActiveByIdCourtier";
	}

	private int idContratDommageOuvrage;
	
	private TaAssure taAssure;
	
	private String numPolice;
	private BigDecimal tauxCommissionCourtier;
	private BigDecimal montantCommissionCourtier;
	private BigDecimal fraisCourtier;
	private BigDecimal tauxDommageOuvrage;
	private BigDecimal primeassuranceNue;
	private BigDecimal tauxTaxeFiscale ;
	private BigDecimal valeurTaxeAttentat;
	private String numClient;
	private String nomProposant;
	private String prenomProposant;
	private String qualiteProposant;
	private String adresseProposant;
	private String nomMaitreOuvrage;
	private String prenomMaitreOuvrage;
	private String adresseMaitreOuvrage;
	private Integer nbBatiments;
	private Integer nbLogements;
	private Integer nbSousSols;
	private Integer nbEtages;
	private Boolean cnr = false;
	private Date dateOuvertureChantier;
	private Date dateReceptionChantier;
	private BigDecimal coutTotalConstruction;
	private String numPermisConstruire;
	private String descriptionOperation;
	private Date dateSouscription;
	private byte[] imgContratDommageOuvrage;
	private Boolean contratactif = false;
	private Boolean retractation = false;
	private Date dateRetractation;
	
	private Date dateFinContrat;
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	
	private TaProjetDommageOuvrage taProjetDommageOuvrage;
	private TaEcheance taEcheance;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_contrat_dommage_ouvrage", unique = true, nullable = false)
	public int getIdContratDommageOuvrage() {
		return idContratDommageOuvrage;
	}

	public void setIdContratDommageOuvrage(int idContratDommageOuvrage) {
		this.idContratDommageOuvrage = idContratDommageOuvrage;
	}

	@Column(name = "num_police")
	public String getNumPolice() {
		return numPolice;
	}

	public void setNumPolice(String numPolice) {
		this.numPolice = numPolice;
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

	@Column(name = "qualite_proposant")
	public String getQualiteProposant() {
		return qualiteProposant;
	}

	public void setQualiteProposant(String qualiteProposant) {
		this.qualiteProposant = qualiteProposant;
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

	@Column(name = "date_reception_chantier")
	public Date getDateReceptionChantier() {
		return dateReceptionChantier;
	}

	public void setDateReceptionChantier(Date dateReceptionChantier) {
		this.dateReceptionChantier = dateReceptionChantier;
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

	@Column(name = "date_souscription")
	public Date getDateSouscription() {
		return dateSouscription;
	}

	public void setDateSouscription(Date dateSouscription) {
		this.dateSouscription = dateSouscription;
	}

	@Column(name = "img_contrat_dommage_ouvrage")
	public byte[] getImgContratDommageOuvrage() {
		return imgContratDommageOuvrage;
	}

	public void setImgContratDommageOuvrage(byte[] imgContratDommageOuvrage) {
		this.imgContratDommageOuvrage = imgContratDommageOuvrage;
	}

	@Column(name = "contrat_actif")
	public Boolean getContratactif() {
		return contratactif;
	}

	public void setContratactif(Boolean contratactif) {
		this.contratactif = contratactif;
	}

	@Column(name = "retractation")
	public Boolean getRetractation() {
		return retractation;
	}

	public void setRetractation(Boolean retractation) {
		this.retractation = retractation;
	}

	@Column(name = "date_retractation")
	public Date getDateRetractation() {
		return dateRetractation;
	}

	public void setDateRetractation(Date dateRetractation) {
		this.dateRetractation = dateRetractation;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_projet_dommage_ouvrage_ta_projet_dommage_ouvrage",unique = true)
	public TaProjetDommageOuvrage getTaProjetDommageOuvrage() {
		return taProjetDommageOuvrage;
	}

	public void setTaProjetDommageOuvrage(TaProjetDommageOuvrage taProjetDommageOuvrage) {
		this.taProjetDommageOuvrage = taProjetDommageOuvrage;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_echeance_ta_echeance",unique = true)
	public TaEcheance getTaEcheance() {
		return taEcheance;
	}

	public void setTaEcheance(TaEcheance taEcheance) {
		this.taEcheance = taEcheance;
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
	@JoinColumn(name = "id_assure_ta_assure")
	public TaAssure getTaAssure() {
		return taAssure;
	}

	public void setTaAssure(TaAssure taAssure) {
		this.taAssure = taAssure;
	}
	@Column(name = "date_fin_contrat")
	public Date getDateFinContrat() {
		return dateFinContrat;
	}

	public void setDateFinContrat(Date dateFinContrat) {
		this.dateFinContrat = dateFinContrat;
	}
}
