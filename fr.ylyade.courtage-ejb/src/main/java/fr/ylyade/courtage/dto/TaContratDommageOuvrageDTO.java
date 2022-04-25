package fr.ylyade.courtage.dto;

import java.math.BigDecimal;
import java.util.Date;

import fr.ylyade.courtage.model.TaEcheance;
import fr.ylyade.courtage.model.TaProjetDommageOuvrage;

public class TaContratDommageOuvrageDTO extends ModelObject implements java.io.Serializable {

	
	private static final long serialVersionUID = 7989627580220726845L;

	private Integer id;
	
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	
	private Integer idAssure;
	private String codeAssure;
	
	private Date dateFinContrat;
	
	
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
	
	private TaProjetDommageOuvrage taProjetDommageOuvrage;
	private TaEcheance taEcheance;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getNumPolice() {
		return numPolice;
	}

	public void setNumPolice(String numPolice) {
		this.numPolice = numPolice;
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

	public String getQualiteProposant() {
		return qualiteProposant;
	}

	public void setQualiteProposant(String qualiteProposant) {
		this.qualiteProposant = qualiteProposant;
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

	public Date getDateReceptionChantier() {
		return dateReceptionChantier;
	}

	public void setDateReceptionChantier(Date dateReceptionChantier) {
		this.dateReceptionChantier = dateReceptionChantier;
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

	public Date getDateSouscription() {
		return dateSouscription;
	}

	public void setDateSouscription(Date dateSouscription) {
		this.dateSouscription = dateSouscription;
	}

	public byte[] getImgContratDommageOuvrage() {
		return imgContratDommageOuvrage;
	}

	public void setImgContratDommageOuvrage(byte[] imgContratDommageOuvrage) {
		this.imgContratDommageOuvrage = imgContratDommageOuvrage;
	}

	public Boolean getContratactif() {
		return contratactif;
	}

	public void setContratactif(Boolean contratactif) {
		this.contratactif = contratactif;
	}

	public Boolean getRetractation() {
		return retractation;
	}

	public void setRetractation(Boolean retractation) {
		this.retractation = retractation;
	}

	public Date getDateRetractation() {
		return dateRetractation;
	}

	public void setDateRetractation(Date dateRetractation) {
		this.dateRetractation = dateRetractation;
	}

	public TaProjetDommageOuvrage getTaProjetDommageOuvrage() {
		return taProjetDommageOuvrage;
	}

	public void setTaProjetDommageOuvrage(TaProjetDommageOuvrage taProjetDommageOuvrage) {
		this.taProjetDommageOuvrage = taProjetDommageOuvrage;
	}

	public TaEcheance getTaEcheance() {
		return taEcheance;
	}

	public void setTaEcheance(TaEcheance taEcheance) {
		this.taEcheance = taEcheance;
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

	public Date getDateFinContrat() {
		return dateFinContrat;
	}

	public void setDateFinContrat(Date dateFinContrat) {
		this.dateFinContrat = dateFinContrat;
	}

	
}
