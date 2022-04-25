package fr.ylyade.courtage.dto;

import java.math.BigDecimal;
import java.util.Date;

import fr.ylyade.courtage.model.TaAssure;
import fr.ylyade.courtage.model.TaContratDommageOuvrage;
import fr.ylyade.courtage.model.TaTAssurance;
import fr.ylyade.courtage.model.TaTConstruction;
import fr.ylyade.courtage.model.TaTDestinationUsage;
import fr.ylyade.courtage.model.TaTMaitriseOeuvre;
import fr.ylyade.courtage.model.TaTTravaux;

public class TaProjetDommageOuvrageDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7756850476443436000L;



	private Integer id;
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	
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
	
	private Integer idAssure;
	private String codeAssure;
	
	private TaContratDommageOuvrage taContratDommageOuvrage;
	private TaTAssurance taTAssurance;
	private TaTConstruction taTConstruction;
	private TaTTravaux taTTravaux;
	private TaTDestinationUsage taTDestinationUsage;
	private TaTMaitriseOeuvre taTMaitriseOeuvre;
	
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getNumProjetDommageOuvrage() {
		return numProjetDommageOuvrage;
	}

	public void setNumProjetDommageOuvrage(String numProjetDommageOuvrage) {
		this.numProjetDommageOuvrage = numProjetDommageOuvrage;
	}

	public BigDecimal getTauxCommissionCourtier() {
		return tauxCommissionCourtier;
	}

	public void setTauxCommissionCourtier(BigDecimal tauxCommissionCourtier) {
		this.tauxCommissionCourtier = tauxCommissionCourtier;
	}

	public BigDecimal getMontantCommissionCourtier() {
		return montantCommissionCourtier;
	}

	public void setMontantCommissionCourtier(BigDecimal montantCommissionCourtier) {
		this.montantCommissionCourtier = montantCommissionCourtier;
	}

	public BigDecimal getFraisCourtier() {
		return fraisCourtier;
	}

	public void setFraisCourtier(BigDecimal fraisCourtier) {
		this.fraisCourtier = fraisCourtier;
	}

	public BigDecimal getTauxDommageOuvrage() {
		return tauxDommageOuvrage;
	}

	public void setTauxDommageOuvrage(BigDecimal tauxDommageOuvrage) {
		this.tauxDommageOuvrage = tauxDommageOuvrage;
	}

	public BigDecimal getPrimeassuranceNue() {
		return primeassuranceNue;
	}

	public void setPrimeassuranceNue(BigDecimal primeassuranceNue) {
		this.primeassuranceNue = primeassuranceNue;
	}

	public BigDecimal getTauxTaxeFiscale() {
		return tauxTaxeFiscale;
	}

	public void setTauxTaxeFiscale(BigDecimal tauxTaxeFiscale) {
		this.tauxTaxeFiscale = tauxTaxeFiscale;
	}

	public BigDecimal getValeurTaxeAttentat() {
		return valeurTaxeAttentat;
	}

	public void setValeurTaxeAttentat(BigDecimal valeurTaxeAttentat) {
		this.valeurTaxeAttentat = valeurTaxeAttentat;
	}

	public byte[] getImgProjetDommageOuvrage() {
		return imgProjetDommageOuvrage;
	}

	public void setImgProjetDommageOuvrage(byte[] imgProjetDommageOuvrage) {
		this.imgProjetDommageOuvrage = imgProjetDommageOuvrage;
	}

	public String getNumClient() {
		return numClient;
	}

	public void setNumClient(String numClient) {
		this.numClient = numClient;
	}

	public String getNomProposant() {
		return nomProposant;
	}

	public void setNomProposant(String nomProposant) {
		this.nomProposant = nomProposant;
	}

	public String getPrenomProposant() {
		return prenomProposant;
	}

	public void setPrenomProposant(String prenomProposant) {
		this.prenomProposant = prenomProposant;
	}

	public String getQualitePreposant() {
		return qualitePreposant;
	}

	public void setQualitePreposant(String qualitePreposant) {
		this.qualitePreposant = qualitePreposant;
	}

	public String getAdresseProposant() {
		return adresseProposant;
	}

	public void setAdresseProposant(String adresseProposant) {
		this.adresseProposant = adresseProposant;
	}

	public String getNomMaitreOuvrage() {
		return nomMaitreOuvrage;
	}

	public void setNomMaitreOuvrage(String nomMaitreOuvrage) {
		this.nomMaitreOuvrage = nomMaitreOuvrage;
	}

	public String getPrenomMaitreOuvrage() {
		return prenomMaitreOuvrage;
	}

	public void setPrenomMaitreOuvrage(String prenomMaitreOuvrage) {
		this.prenomMaitreOuvrage = prenomMaitreOuvrage;
	}

	public String getAdresseOperationConstruction() {
		return adresseOperationConstruction;
	}

	public void setAdresseOperationConstruction(String adresseOperationConstruction) {
		this.adresseOperationConstruction = adresseOperationConstruction;
	}

	public String getAdresseMaitreOuvrage() {
		return adresseMaitreOuvrage;
	}

	public void setAdresseMaitreOuvrage(String adresseMaitreOuvrage) {
		this.adresseMaitreOuvrage = adresseMaitreOuvrage;
	}

	public Integer getNbBatiments() {
		return nbBatiments;
	}

	public void setNbBatiments(Integer nbBatiments) {
		this.nbBatiments = nbBatiments;
	}

	public Integer getNbLogements() {
		return nbLogements;
	}

	public void setNbLogements(Integer nbLogements) {
		this.nbLogements = nbLogements;
	}

	public Integer getNbSousSols() {
		return nbSousSols;
	}

	public void setNbSousSols(Integer nbSousSols) {
		this.nbSousSols = nbSousSols;
	}

	public Integer getNbEtages() {
		return nbEtages;
	}

	public void setNbEtages(Integer nbEtages) {
		this.nbEtages = nbEtages;
	}

	public Boolean getCnr() {
		return cnr;
	}

	public void setCnr(Boolean cnr) {
		this.cnr = cnr;
	}

	public Date getDateOuvertureChantier() {
		return dateOuvertureChantier;
	}

	public void setDateOuvertureChantier(Date dateOuvertureChantier) {
		this.dateOuvertureChantier = dateOuvertureChantier;
	}

	public Date getDateReceptionTravaux() {
		return dateReceptionTravaux;
	}

	public void setDateReceptionTravaux(Date dateReceptionTravaux) {
		this.dateReceptionTravaux = dateReceptionTravaux;
	}

	public BigDecimal getCoutTotalConstruction() {
		return coutTotalConstruction;
	}

	public void setCoutTotalConstruction(BigDecimal coutTotalConstruction) {
		this.coutTotalConstruction = coutTotalConstruction;
	}

	public String getNumPermisConstruire() {
		return numPermisConstruire;
	}

	public void setNumPermisConstruire(String numPermisConstruire) {
		this.numPermisConstruire = numPermisConstruire;
	}

	public String getDescriptionOperation() {
		return descriptionOperation;
	}

	public void setDescriptionOperation(String descriptionOperation) {
		this.descriptionOperation = descriptionOperation;
	}

	public TaContratDommageOuvrage getTaContratDommageOuvrage() {
		return taContratDommageOuvrage;
	}

	public void setTaContratDommageOuvrage(TaContratDommageOuvrage taContratDommageOuvrage) {
		this.taContratDommageOuvrage = taContratDommageOuvrage;
	}

	public TaTAssurance getTaTAssurance() {
		return taTAssurance;
	}

	public void setTaTAssurance(TaTAssurance taTAssurance) {
		this.taTAssurance = taTAssurance;
	}

	public TaTConstruction getTaTConstruction() {
		return taTConstruction;
	}

	public void setTaTConstruction(TaTConstruction taTConstruction) {
		this.taTConstruction = taTConstruction;
	}

	public TaTTravaux getTaTTravaux() {
		return taTTravaux;
	}

	public void setTaTTravaux(TaTTravaux taTTravaux) {
		this.taTTravaux = taTTravaux;
	}

	public TaTDestinationUsage getTaTDestinationUsage() {
		return taTDestinationUsage;
	}

	public void setTaTDestinationUsage(TaTDestinationUsage taTDestinationUsage) {
		this.taTDestinationUsage = taTDestinationUsage;
	}

	public TaTMaitriseOeuvre getTaTMaitriseOeuvre() {
		return taTMaitriseOeuvre;
	}

	public void setTaTMaitriseOeuvre(TaTMaitriseOeuvre taTMaitriseOeuvre) {
		this.taTMaitriseOeuvre = taTMaitriseOeuvre;
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

	public Integer getIdAssure() {
		return idAssure;
	}

	public void setIdAssure(Integer idAssure) {
		this.idAssure = idAssure;
	}

	public String getCodeAssure() {
		return codeAssure;
	}

	public void setCodeAssure(String codeAssure) {
		this.codeAssure = codeAssure;
	}

	public Date getDateFinValiditeProjet() {
		return dateFinValiditeProjet;
	}

	public void setDateFinValiditeProjet(Date dateFinValiditeProjet) {
		this.dateFinValiditeProjet = dateFinValiditeProjet;
	}

	
}
