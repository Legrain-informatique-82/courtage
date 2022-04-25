package fr.ylyade.courtage.dto;

import java.util.Date;

import fr.ylyade.courtage.model.TaAssure;
import fr.ylyade.courtage.model.TaDossierRcd;

public class TaSinistreDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -9085740499614914648L;

	private Integer id;
	
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
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getRefSinistre() {
		return refSinistre;
	}

	public void setRefSinistre(String refSinistre) {
		this.refSinistre = refSinistre;
	}

	public Date getDateSinistre() {
		return dateSinistre;
	}

	public void setDateSinistre(Date dateSinistre) {
		this.dateSinistre = dateSinistre;
	}

	public Date getDateDeclaration() {
		return dateDeclaration;
	}

	public void setDateDeclaration(Date dateDeclaration) {
		this.dateDeclaration = dateDeclaration;
	}

	public String getCodeAssure() {
		return codeAssure;
	}

	public void setCodeAssure(String codeAssure) {
		this.codeAssure = codeAssure;
	}

	public String getCodeAssurance() {
		return codeAssurance;
	}

	public void setCodeAssurance(String codeAssurance) {
		this.codeAssurance = codeAssurance;
	}

	public String getCodeContrat() {
		return codeContrat;
	}

	public void setCodeContrat(String codeContrat) {
		this.codeContrat = codeContrat;
	}

	public String getLiblSinistre() {
		return liblSinistre;
	}

	public void setLiblSinistre(String liblSinistre) {
		this.liblSinistre = liblSinistre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAdresseconstruction() {
		return adresseconstruction;
	}

	public void setAdresseconstruction(String adresseconstruction) {
		this.adresseconstruction = adresseconstruction;
	}

	public String getObjetTravauxAssure() {
		return objetTravauxAssure;
	}

	public void setObjetTravauxAssure(String objetTravauxAssure) {
		this.objetTravauxAssure = objetTravauxAssure;
	}

	public String getCoordonneesClient() {
		return coordonneesClient;
	}

	public void setCoordonneesClient(String coordonneesClient) {
		this.coordonneesClient = coordonneesClient;
	}

	public Boolean getRenovation() {
		return renovation;
	}

	public void setRenovation(Boolean renovation) {
		this.renovation = renovation;
	}

	public Boolean getExtension() {
		return extension;
	}

	public void setExtension(Boolean extension) {
		this.extension = extension;
	}

	public Boolean getConstructionNeuve() {
		return constructionNeuve;
	}

	public void setConstructionNeuve(Boolean constructionNeuve) {
		this.constructionNeuve = constructionNeuve;
	}

	public String getTravauxEtudeRealAssure() {
		return travauxEtudeRealAssure;
	}

	public void setTravauxEtudeRealAssure(String travauxEtudeRealAssure) {
		this.travauxEtudeRealAssure = travauxEtudeRealAssure;
	}

	public Date getDebutTravaux() {
		return debutTravaux;
	}

	public void setDebutTravaux(Date debutTravaux) {
		this.debutTravaux = debutTravaux;
	}

	public Date getDateReceptionTravaux() {
		return dateReceptionTravaux;
	}

	public void setDateReceptionTravaux(Date dateReceptionTravaux) {
		this.dateReceptionTravaux = dateReceptionTravaux;
	}

	public Date getDateReglDefinitifTravaux() {
		return dateReglDefinitifTravaux;
	}

	public void setDateReglDefinitifTravaux(Date dateReglDefinitifTravaux) {
		this.dateReglDefinitifTravaux = dateReglDefinitifTravaux;
	}

	public Date getDateAchevementTravaux() {
		return dateAchevementTravaux;
	}

	public void setDateAchevementTravaux(Date dateAchevementTravaux) {
		this.dateAchevementTravaux = dateAchevementTravaux;
	}

	public String getCommentaireReserve() {
		return commentaireReserve;
	}

	public void setCommentaireReserve(String commentaireReserve) {
		this.commentaireReserve = commentaireReserve;
	}

	public String getAssuranceDommageSouscrite() {
		return assuranceDommageSouscrite;
	}

	public void setAssuranceDommageSouscrite(String assuranceDommageSouscrite) {
		this.assuranceDommageSouscrite = assuranceDommageSouscrite;
	}

	public String getNumContratDommageOuvrage() {
		return numContratDommageOuvrage;
	}

	public void setNumContratDommageOuvrage(String numContratDommageOuvrage) {
		this.numContratDommageOuvrage = numContratDommageOuvrage;
	}

	public String getCompagnieAssurance() {
		return compagnieAssurance;
	}

	public void setCompagnieAssurance(String compagnieAssurance) {
		this.compagnieAssurance = compagnieAssurance;
	}

	public String getAssurePasseContratNom() {
		return assurePasseContratNom;
	}

	public void setAssurePasseContratNom(String assurePasseContratNom) {
		this.assurePasseContratNom = assurePasseContratNom;
	}

	public String getAssurePasseContratAdresse() {
		return assurePasseContratAdresse;
	}

	public void setAssurePasseContratAdresse(String assurePasseContratAdresse) {
		this.assurePasseContratAdresse = assurePasseContratAdresse;
	}

	public Boolean getAutresEntrepriseIntervenus() {
		return autresEntrepriseIntervenus;
	}

	public void setAutresEntrepriseIntervenus(Boolean autresEntrepriseIntervenus) {
		this.autresEntrepriseIntervenus = autresEntrepriseIntervenus;
	}

	public Boolean getContratPasseAvecSousTraitant() {
		return contratPasseAvecSousTraitant;
	}

	public void setContratPasseAvecSousTraitant(Boolean contratPasseAvecSousTraitant) {
		this.contratPasseAvecSousTraitant = contratPasseAvecSousTraitant;
	}

	public String getDescriptionInterventionSousTraitant() {
		return descriptionInterventionSousTraitant;
	}

	public void setDescriptionInterventionSousTraitant(String descriptionInterventionSousTraitant) {
		this.descriptionInterventionSousTraitant = descriptionInterventionSousTraitant;
	}

	public String getAdresseSousTraitant() {
		return adresseSousTraitant;
	}

	public void setAdresseSousTraitant(String adresseSousTraitant) {
		this.adresseSousTraitant = adresseSousTraitant;
	}

	public String getAssuranceSousTraitanceNom() {
		return assuranceSousTraitanceNom;
	}

	public void setAssuranceSousTraitanceNom(String assuranceSousTraitanceNom) {
		this.assuranceSousTraitanceNom = assuranceSousTraitanceNom;
	}

	public String getAssuranceSousTraitanceAdresse() {
		return assuranceSousTraitanceAdresse;
	}

	public void setAssuranceSousTraitanceAdresse(String assuranceSousTraitanceAdresse) {
		this.assuranceSousTraitanceAdresse = assuranceSousTraitanceAdresse;
	}

	public String getNomPersonneSubitPrejudice() {
		return nomPersonneSubitPrejudice;
	}

	public void setNomPersonneSubitPrejudice(String nomPersonneSubitPrejudice) {
		this.nomPersonneSubitPrejudice = nomPersonneSubitPrejudice;
	}

	public String getAdressePersonneSubitPrejudice() {
		return adressePersonneSubitPrejudice;
	}

	public void setAdressePersonneSubitPrejudice(String adressePersonneSubitPrejudice) {
		this.adressePersonneSubitPrejudice = adressePersonneSubitPrejudice;
	}

	public String getNatureDateDommages() {
		return natureDateDommages;
	}

	public void setNatureDateDommages(String natureDateDommages) {
		this.natureDateDommages = natureDateDommages;
	}

	public Boolean getClotureSinistre() {
		return clotureSinistre;
	}

	public void setClotureSinistre(Boolean clotureSinistre) {
		this.clotureSinistre = clotureSinistre;
	}

	public TaAssure getTaAssure() {
		return taAssure;
	}

	public void setTaAssure(TaAssure taAssure) {
		this.taAssure = taAssure;
	}

	public TaDossierRcd getTaDossierRcd() {
		return taDossierRcd;
	}

	public void setTaDossierRcd(TaDossierRcd taDossierRcd) {
		this.taDossierRcd = taDossierRcd;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
