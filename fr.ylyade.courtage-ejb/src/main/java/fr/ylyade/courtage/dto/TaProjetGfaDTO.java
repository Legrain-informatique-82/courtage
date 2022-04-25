package fr.ylyade.courtage.dto;

import java.math.BigDecimal;
import java.util.Date;

import fr.ylyade.courtage.model.TaAssure;
import fr.ylyade.courtage.model.TaContratGfa;
import fr.ylyade.courtage.model.TaTAssurance;

public class TaProjetGfaDTO extends ModelObject implements java.io.Serializable {

	
	private static final long serialVersionUID = -4978830336305080024L;

	private Integer id;
	
	private Integer idAssure;
	private String codeAssure;
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	
	private String numProjetGfa;
	private BigDecimal fraisCourtier; //frais et montant different ?
	private BigDecimal tauxCommissionCourtier;
	private BigDecimal montantCommissionCourtier; //frais et montant different ? cumul ?
	private BigDecimal primeAssuranceNue;                     
    private byte[] imgProjetGfa;
    private BigDecimal tauxGfa;
    private BigDecimal montantChantierGfa;
    private Date dateRealisation;
	private String communeAssietteFonciere;
	private Integer nbBatiments;
	private Integer nbLogements;
	private String usageDe;
	private Date dateDelivrance;
	private String beneficiaire;
	private String numDelivrance;
	private BigDecimal surfacePlancher;
	private Integer nbTerrains;
	private Integer nbCommerces;
	private String destination;
	private String prescriptionsParticulieres;
	private BigDecimal prixRevientPrevisionnel;
	private BigDecimal prixVentePrevisionnel;
	private BigDecimal montantPreCommercialisation;
	private String terrainCadastre;
	
	private Date dateFinValiditeProjet;
	
	private TaContratGfa taContratGfa;
	private TaTAssurance taTAssurance;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getNumProjetGfa() {
		return numProjetGfa;
	}

	public void setNumProjetGfa(String numProjetGfa) {
		this.numProjetGfa = numProjetGfa;
	}

	public BigDecimal getFraisCourtier() {
		return fraisCourtier;
	}

	public void setFraisCourtier(BigDecimal fraisCourtier) {
		this.fraisCourtier = fraisCourtier;
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

	public BigDecimal getPrimeAssuranceNue() {
		return primeAssuranceNue;
	}

	public void setPrimeAssuranceNue(BigDecimal primeAssuranceNue) {
		this.primeAssuranceNue = primeAssuranceNue;
	}

	public byte[] getImgProjetGfa() {
		return imgProjetGfa;
	}

	public void setImgProjetGfa(byte[] imgProjetGfa) {
		this.imgProjetGfa = imgProjetGfa;
	}

	public BigDecimal getTauxGfa() {
		return tauxGfa;
	}

	public void setTauxGfa(BigDecimal tauxGfa) {
		this.tauxGfa = tauxGfa;
	}

	public BigDecimal getMontantChantierGfa() {
		return montantChantierGfa;
	}

	public void setMontantChantierGfa(BigDecimal montantChantierGfa) {
		this.montantChantierGfa = montantChantierGfa;
	}

	public Date getDateRealisation() {
		return dateRealisation;
	}

	public void setDateRealisation(Date dateRealisation) {
		this.dateRealisation = dateRealisation;
	}


	public String getCommuneAssietteFonciere() {
		return communeAssietteFonciere;
	}

	public void setCommuneAssietteFonciere(String communeAssietteFonciere) {
		this.communeAssietteFonciere = communeAssietteFonciere;
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

	public String getUsageDe() {
		return usageDe;
	}

	public void setUsageDe(String usageDe) {
		this.usageDe = usageDe;
	}

	public Date getDateDelivrance() {
		return dateDelivrance;
	}

	public void setDateDelivrance(Date dateDelivrance) {
		this.dateDelivrance = dateDelivrance;
	}

	public String getBeneficiaire() {
		return beneficiaire;
	}

	public void setBeneficiaire(String beneficiaire) {
		this.beneficiaire = beneficiaire;
	}

	public String getNumDelivrance() {
		return numDelivrance;
	}

	public void setNumDelivrance(String numDelivrance) {
		this.numDelivrance = numDelivrance;
	}

	public BigDecimal getSurfacePlancher() {
		return surfacePlancher;
	}

	public void setSurfacePlancher(BigDecimal surfacePlancher) {
		this.surfacePlancher = surfacePlancher;
	}

	public Integer getNbTerrains() {
		return nbTerrains;
	}

	public void setNbTerrains(Integer nbTerrains) {
		this.nbTerrains = nbTerrains;
	}

	public Integer getNbCommerces() {
		return nbCommerces;
	}

	public void setNbCommerces(Integer nbCommerces) {
		this.nbCommerces = nbCommerces;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getPrescriptionsParticulieres() {
		return prescriptionsParticulieres;
	}

	public void setPrescriptionsParticulieres(String prescriptionsParticulieres) {
		this.prescriptionsParticulieres = prescriptionsParticulieres;
	}

	public BigDecimal getPrixRevientPrevisionnel() {
		return prixRevientPrevisionnel;
	}

	public void setPrixRevientPrevisionnel(BigDecimal prixRevientPrevisionnel) {
		this.prixRevientPrevisionnel = prixRevientPrevisionnel;
	}

	public BigDecimal getPrixVentePrevisionnel() {
		return prixVentePrevisionnel;
	}

	public void setPrixVentePrevisionnel(BigDecimal prixVentePrevisionnel) {
		this.prixVentePrevisionnel = prixVentePrevisionnel;
	}

	public BigDecimal getMontantPreCommercialisation() {
		return montantPreCommercialisation;
	}

	public void setMontantPreCommercialisation(BigDecimal montantPreCommercialisation) {
		this.montantPreCommercialisation = montantPreCommercialisation;
	}

	public String getTerrainCadastre() {
		return terrainCadastre;
	}

	public void setTerrainCadastre(String terrainCadastre) {
		this.terrainCadastre = terrainCadastre;
	}

	public TaContratGfa getTaContratGfa() {
		return taContratGfa;
	}

	public void setTaContratGfa(TaContratGfa taContratGfa) {
		this.taContratGfa = taContratGfa;
	}

	public TaTAssurance getTaTAssurance() {
		return taTAssurance;
	}

	public void setTaTAssurance(TaTAssurance taTAssurance) {
		this.taTAssurance = taTAssurance;
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
