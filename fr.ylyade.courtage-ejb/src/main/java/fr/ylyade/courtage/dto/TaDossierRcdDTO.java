package fr.ylyade.courtage.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import fr.ylyade.courtage.model.IDonneStatut;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaTStatut;

public class TaDossierRcdDTO extends ModelObject implements java.io.Serializable, IDonneStatut {

	private static final long serialVersionUID = -1241217567058020673L;

	private Integer id;
	
	private Integer version;
	
	private Integer idAssure;
	private String codeAssure;
	private String raisonSociale;
	private String codeSiren;
	
	private Integer idCourtier;
	private String codeCourtier;
	private String nomDenominationSociale;
	
	private Integer idContratRcPro;
	private String numDossierPolice;
	private Integer numeroAvenant;
	
	/////////////////////////////////////////////////
	private Integer idDevisRcProDetail;
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	


	

	private Date dateEffet;
	private Date dateResiliation;
	// pas de motif de resiliation comme dans les autres contrat ?
	private TaDossierRcd devisDetailOrigine;
	private Integer versionDevisRcProDetail;
	//private Boolean dernierDevis = false; -- avec les dates ?
//	private byte[] imgDevisRcPro;
	private BigDecimal montantPrime; 
	
	private String codeSousTraitance;
	private BigDecimal pourcentSoustraitance;
	
	private BigDecimal fraisRcPro; //frais du courtier
	private BigDecimal tauxCommission;
	private Boolean dejaAssurer = false; //deja assure ailleur
	private Boolean contratEnCours = false;
	private Boolean reprisePasse = false;
	private Boolean contrat = false;
	
	private Integer nbSinistre5ans;	   
	private Integer nbSinistreTotal;
	private BigDecimal montantSinistre;
	
	private Boolean interventionChantierCoutMax = false;
	private Date dateResiliationContratPrecedent;
	private String motifResiliationContratPrecedent;
	private BigDecimal primeNette;
	private Date dateDevis;
	private BigDecimal montantPremierReglement;
	private Date dateDebutPremierePeriode;
	private Date dateFinPremierePeriode;
	private Date dateEcheance;
	private BigDecimal montantTaxesDiversesAssurance;
	private Integer idTReglement;
	private String codeTReglement;
	private String liblTReglement;
	
	private Date dateEnvoiChequeParCourtier;
	private Date dateReceptionCheque;
	private Date dateDepotCheque;
	private Date dateEncaissementCheque;
	
	private Date dateVirementEffectue;
	private Date dateVirementRecu;
	

	private Integer idTEcheance;
	private BigDecimal tauxEcheance;
	private String liblTEcheance;
	private String codeTEcheance;
	
	private BigDecimal montantCommissionCourtier;
	private BigDecimal tauxSurCommissionCourtier;
	private BigDecimal montantSurCommissionCourtier;
	private BigDecimal montantProtectionJuridique;
	private BigDecimal montantTaxeFiscale;
	private BigDecimal tauxTaxeFiscale;
	private BigDecimal montantFraisDeGestion;
	private BigDecimal primeAnnuelle;
	private BigDecimal primeAnnuelleComplete;
	private BigDecimal montantFraisFractionnement;
	
	private String logDetailCalculPrime;
	private String logDetailCalculPremierReglement;
	
	private Integer idTSousTraitance;
	private String codeTSousTraitance;
	private String liblTSousTraitance;
	private BigDecimal baseMin;
	private BigDecimal baseMax;
	private BigDecimal taux;
	
	private String activitePrincipale;
	private BigDecimal chiffreAffaireExerciceAnterieur;
	private BigDecimal chiffreAffaireExercicePrevisionnel;
	private Integer effectifTotalExercice;
	private Integer effectifTotalExerciceAnterieur;
	
	private Boolean resilieNonPaiement = false;
	private Boolean resilieFausseDeclaration = false;
	
	private Date resilieNonPaiementContrat = null;
	private Date resilieFausseDeclarationContrat  = null;
	private Date resilieEcheanceContrat  = null;
	private Date resilieAmiableContrat  = null;
	private Date resilieCessationActiviteContrat  = null;
	private Date resilieSansEffetContrat  = null;
	
	

	private String codeFranchise;
	private BigDecimal franchise;
	
	private String codeEcheance; //trimestriel/semestriel
			
	private String assureurPrecedentRcd;
	private Date dateEffetContratPrecendentRcd;
	private String policeContratPrecedentRcd;
	private BigDecimal coutGlobalSinistreRcd;
	private Integer idContratRcProDetail;
	
	private Integer idTauxAssurance; 
	private String codeTauxAssurance;
	private BigDecimal tauxTauxAssurance;
	private List<TaActiviteDTO> listeActivite = new ArrayList<TaActiviteDTO>();
	private List<TaTauxRcproPibDTO> listeTauxRcProPib = new ArrayList<TaTauxRcproPibDTO>();
	//////////////////////////////////////////////
	private TaAssureDTO taAssureDTO = new TaAssureDTO();
	//////////////////////////////////////////////

	/*
	 * Nouveaux champs 
	 */
	//ecran 1
	private Boolean experienceActivite3ans = false;
	private Boolean experienceActivte5ans = false;
	private Boolean exerceActiviteNomenclature = false;
	private Boolean coutOuvrageInferieur15k = false;
	private Boolean montantTravauxHtMax50k = false;
	private Boolean montantTravauxHtMax100k = false;
	private Boolean montantTravauxHtMax250k = false;
	private Boolean montantTravauxHtMax500k = false;
	private Boolean montantTravauxHtMax1m = false;
	private Boolean interventionConstructeurMaisonIndiv = false;
	private Boolean interventionContractantGeneral = false;
	private Boolean interventionBatiment = false;
	private Boolean interventionMaitreOeuvre = false;
	private Boolean interventionImmobilier = false;
	private Boolean interventionFabricantMatConstruction = false;
	private Boolean activitePrincNegoceFabrMatConstructionNonPose = false;
	private Boolean travauxTechniqueNonCourant = false;
	private Boolean interventionMonumentHistorique = false;
	private Boolean avisTechnique = false;
	private Boolean documentUnique = false;
	private Boolean respectRegles = false;
	private Boolean respectDispositionSousTraitance = null;
		
	//ecran 2
	private Integer anneesExperienceActivite;
	private Integer effectifSurChantier;
	private Integer effectifApprentis;
	private Integer effectifCommerciauxAdministratifs;
	private Integer effectifEncadrement;
	private Boolean qualibat = false;
	private BigDecimal pourcentCaSousTraitDernierExercice;
	private BigDecimal pourcentCaSousTraitExerciceNMoins1;
	private BigDecimal pourcentCaSousTraitExerciceNMoins2;
	private BigDecimal caTotalHtExerciceNMoins1;
	private BigDecimal caTotalHtExerciceNMoins2;

	//ecran 3
	private Boolean ancienneteMoins12mois = false;
	private Boolean anciennete12a36mois = false;
	private Boolean anciennetePlus36mois = false;
	private Boolean assureMemeRisque = false;
	private Boolean assureMemeRisqueRcd = false;
	private Boolean assureMemeRisqueRce = false;
	private Boolean antecedentRcdContratEnCours = false;
	private Boolean antecedentRcdContratResilie = false;
	private Boolean antecedentRceContratEnCours = false;
	private Boolean antecedentRceContratResilie = false;
	private Date antecedentRcdDateResiliation;
	private Date antecedentRceDateResiliation;
	private Boolean antecedentRcdResilieAssure = false;
	private Boolean antecedentRceResilieAssure = false;
	private Boolean antecedentRcdResilieEcheance = false;
	private Boolean antecedentRceResilieEcheance = false;
	private Boolean antecedentRcdHausseTarif = false;
	private Boolean antecedentRceHausseTarif = false;
	private Boolean antecedentRcdChangementActivite = false;
	private Boolean antecedentRceChangementActivite = false;
	private Boolean antecedentRcdResilieAmiable = false;
	private Boolean antecedentRceResilieAmiable = false;
	private Boolean antecedentRcdAssureur = false;
	private Boolean antecedentRceAssureur = false;
	private Boolean antecedentRcdNonPaiementPrime = false;
	private Boolean antecedentRceNonPaiementPrime = false;
	private Boolean antecedentRcdSinistre = false;
	private Boolean antecedentRceSinistre = false;
	private Boolean antecedentRcdModifActivite = false;
	private Boolean antecedentRceModifActivite = false;
	private Boolean antecedentRcdAutre = false;
	private Boolean antecedentRceAutre = false;
	private Boolean interruptionAssuranceMoins6mois = false;
	private Boolean interruptionAssurance6a12mois = false;
	private Boolean interruptionAssurance12a24mois = false;
	private Boolean interruptionAssurance24a36mois = false;
	private Boolean interruptionAssurance36a60mois = false;
	private Boolean interruptionAssurance60a84mois = false;
	private Boolean interruptionAssurancePlusDe84mois = false;
	
	private String antecedentRcdPoliceAssureur;
	private String antecedentRcePoliceAssureur;
	private String antecedentRcdNomAssureur;
	private String antecedentRceNomAssureur;
	
	//ecran 4
	private Boolean sinistraliteLiquidationSocieteDemandeuse = false;
	private Boolean sinistraliteLiquidationAutreSociete = false;
	private Boolean sinistraliteRisqueRefusAssurance = false;
	private Boolean sinistraliteMiseEnCause = false;
	private Boolean sinistraliteEvenementEngageantResp = false;
	private String sinistraliteEvenementFaits;
	
	//ecran 6
	private Boolean reprisePasseMoinsDe3mois = false;
	private Boolean reprisePasseDe3a6mois = false;
	private Boolean reprisePasseDe6a12mois = false;
	private Boolean territorialiteLieuFranceMetrop = false;
	private Boolean territorialiteLieuCorse = false;
	private Boolean territorialiteLieuDomtom = false;
	
	//ecran 7
	private Boolean informerCaractereObligatoire = false;
	private Boolean informationPropositionPartieIntegranteContrat = false;
	private Boolean autoriseAssureurCommuniquerReponses = false;
	private Boolean opposeUtilisationDonneesFinCommerciale = false;
	
	
	
	private Date dateRepriseDuPasse;
	private BigDecimal tauxRisqueParFamilleActivite;
	private BigDecimal tauxNombreActivite;
	private String codeInterruptionAssurance;
	private BigDecimal tauxInterruptionAssurance;
	private String codeReprisePasse;
	private BigDecimal tauxReprisePasse;
	private BigDecimal tauxResiliationNonPaiement;
	private BigDecimal tauxAntecedentSinistre;
	private Integer nbAntecedentSinistre;
	private String coutMaxPourNbAntecedentSinistre;
	 /*
	  * Nouveaux champs 
	  */
	private String commentaireCourtier;
	private Boolean soumisEtude = null;
	private Boolean soumisEtudeAssureur = null;
	private Boolean validationCourtier = null;
	private Boolean validationApresetudeYlyade = null;
	private Boolean validationAssureurApresetude = null;
	private Boolean validationSuperAssureur = null;
	private Boolean validationYlyade = null;
	private Boolean traite = false;
	private Boolean soumisSouscription = false;
	private Boolean validationGlobaleGedCourtier = null;
	private Boolean premierPaiementEffectue = false;
	
	private BigDecimal txRegulCaHt;
	private String commentaireValidationYlyade;
	private String commentaireApresExpertise;
	private String commentaireValidationAssureur;
	private Boolean inclusionFraisDossier = true;
	private BigDecimal montantDprsa;
	private BigDecimal montantFraisCompagnieSuperAssureur;
	
	private BigDecimal montantPrimeAnnuelleHT;
	private BigDecimal montantPrimeAnnuelleTTC;
	private BigDecimal montantPrimeEcheanceHT;
	private BigDecimal montantPrimeEcheanceTTC;
	private BigDecimal montantTaxeTotal;
	
	private BigDecimal montantTotalTaxes9pc;
	private BigDecimal montantTotalTaxes134pc;
	private BigDecimal tarifAnnuelTtcPlusFraisFractionnement;
	private BigDecimal pourcentSousTraitanceCalcul;
	
	private BigDecimal tarifAnnuelTotalHT;
	private BigDecimal tarifAnnuelTotalTTC;
	private Boolean validationGlobaleGedYlyade;
	private Boolean refusDefinitifYlyade;
	private Boolean refusDefinitifSuperAssureur;
	private String commentaireRefusDefinitifYlyade;
	private String commentaireRefusDefinitifSuperAssureur;
	
	
	private List<TaTStatut> taTStatut = new ArrayList<TaTStatut>();
	
	private BigDecimal primeNetteYlyade;
	private BigDecimal primeNetteAssureur;
	
	private Date misEnDemeure = null;
	private Date suspenduNonPaiement = null;
	
	private Boolean suspenduAvenant = false;
	
	private String lettrePjNumPolice;
	
//	private String jsonDATA;
	
	private Integer versionObj;
	
	public TaDossierRcdDTO() {
		
	}
	
	public TaDossierRcdDTO(Integer id, /*String numDevis,*/ Integer idAssure, String codeAssure, String raisonSociale,
			Integer idContactEntreprise, String nom, String prenom, Integer idContratRcPro, String numPolice, 
			Boolean contrat, Integer numeroAvenant, String nomDenominationSociale, Integer idTAssure, String codeTAssure) {
		super();
		this.id = id;
//		this.numDevis = numDevis;
		this.idAssure = idAssure;
		this.codeAssure = codeAssure;
		this.taAssureDTO = new TaAssureDTO();
		this.taAssureDTO.setRaisonSociale(raisonSociale); 
		this.taAssureDTO.setIdContactEntreprise(idContactEntreprise);
		this.taAssureDTO.setNom(prenom);
		this.taAssureDTO.setPrenom(prenom);
		this.idContratRcPro = idContratRcPro;
		this.numDossierPolice = numPolice;
		this.contrat = contrat;
		this.numeroAvenant = numeroAvenant;
		this.nomDenominationSociale = nomDenominationSociale;
		this.taAssureDTO.setIdTAssure(idTAssure);
		this.taAssureDTO.setCodeTAssure(codeTAssure);
	}
	
	public TaDossierRcdDTO(Integer id, /*String numDevis,*/ Integer idAssure, String codeAssure, String raisonSociale,
			Integer idContactEntreprise, String nom, String prenom, Integer idContratRcPro, String numPolice, String lettrePjNumPolice,
			Boolean contrat, Integer numeroAvenant, String nomDenominationSociale, Integer idTAssure, String codeTAssure) {
		super();
		this.id = id;
//		this.numDevis = numDevis;
		this.idAssure = idAssure;
		this.codeAssure = codeAssure;
		this.taAssureDTO = new TaAssureDTO();
		this.taAssureDTO.setRaisonSociale(raisonSociale); 
		this.taAssureDTO.setIdContactEntreprise(idContactEntreprise);
		this.taAssureDTO.setNom(prenom);
		this.taAssureDTO.setPrenom(prenom);
		this.idContratRcPro = idContratRcPro;
		this.numDossierPolice = numPolice;
		this.lettrePjNumPolice = lettrePjNumPolice;
		this.contrat = contrat;
		this.numeroAvenant = numeroAvenant;
		this.nomDenominationSociale = nomDenominationSociale;
		this.taAssureDTO.setIdTAssure(idTAssure);
		this.taAssureDTO.setCodeTAssure(codeTAssure);
	}
	public TaDossierRcdDTO(Integer id, /*String numDevis,*/ Integer idAssure, String codeAssure, String raisonSociale,
			Integer idContactEntreprise, String nom, String prenom, Integer idContratRcPro, String numPolice, String lettrePjNumPolice, 
			Boolean contrat, Integer numeroAvenant,Integer idCourtier, String codeCourtier, String nomDenominationSociale,
			Integer idTAssure, String codeTAssure, Date dateEffet, String codeSiren) {
		super();
		this.id = id;
//		this.numDevis = numDevis;
		this.idAssure = idAssure;
		this.codeAssure = codeAssure;
		this.taAssureDTO = new TaAssureDTO();
		this.taAssureDTO.setRaisonSociale(raisonSociale); 
		this.taAssureDTO.setIdContactEntreprise(idContactEntreprise);
		this.taAssureDTO.setNom(prenom);
		this.taAssureDTO.setPrenom(prenom);
		this.idContratRcPro = idContratRcPro;
		this.numDossierPolice = numPolice;
		this.lettrePjNumPolice = lettrePjNumPolice;
		this.contrat = contrat;
		this.numeroAvenant = numeroAvenant;
		this.idCourtier = idCourtier;
		this.codeCourtier= codeCourtier;
		this.nomDenominationSociale = nomDenominationSociale;
		this.taAssureDTO.setIdTAssure(idTAssure);
		this.taAssureDTO.setCodeTAssure(codeTAssure);
		this.dateEffet = dateEffet;
		this.taAssureDTO.setCodeSiren(codeSiren);
	}
	/**ICI ci-dessous Constructeur utilisé pour liste extraction dossier**/
	public TaDossierRcdDTO(Integer id, /*String numDevis,*/ Integer idAssure, String codeAssure, String raisonSociale,
			Integer idContactEntreprise, String nom, String prenom, Integer idContratRcPro, String numPolice, String lettrePjNumPolice, 
			Boolean contrat, Integer numeroAvenant,Integer idCourtier, String codeCourtier, String nomDenominationSociale,
			Integer idTAssure, String codeTAssure, Date dateEffet, String codeSiren,
			String liblTEcheance, Date dateEcheance, BigDecimal montantCommissionCourtier, BigDecimal montantPrimeAnnuelleTtc) {
		super();
		this.id = id;
//		this.numDevis = numDevis;
		this.idAssure = idAssure;
		this.codeAssure = codeAssure;
		this.taAssureDTO = new TaAssureDTO();
		this.taAssureDTO.setRaisonSociale(raisonSociale); 
		this.taAssureDTO.setIdContactEntreprise(idContactEntreprise);
		this.taAssureDTO.setNom(prenom);
		this.taAssureDTO.setPrenom(prenom);
		this.idContratRcPro = idContratRcPro;
		this.numDossierPolice = numPolice;
		this.lettrePjNumPolice = lettrePjNumPolice;
		this.contrat = contrat;
		this.numeroAvenant = numeroAvenant;
		this.idCourtier = idCourtier;
		this.codeCourtier= codeCourtier;
		this.nomDenominationSociale = nomDenominationSociale;
		this.taAssureDTO.setIdTAssure(idTAssure);
		this.taAssureDTO.setCodeTAssure(codeTAssure);
		this.dateEffet = dateEffet;
		this.taAssureDTO.setCodeSiren(codeSiren);
		/**rajout extraction**/
		this.liblTEcheance = liblTEcheance;
		this.dateEcheance = dateEcheance;
		this.montantCommissionCourtier = montantCommissionCourtier;
		this.montantPrimeAnnuelleTTC = montantPrimeAnnuelleTtc;
	}
	
	
	
	public TaDossierRcdDTO(Integer id, /*String numDevis,*/ Integer idAssure, String codeAssure, String raisonSociale,
			Integer idContactEntreprise, String nom, String prenom, Integer idContratRcPro, String numPolice, 
			Boolean contrat, Integer numeroAvenant,Integer idCourtier, String codeCourtier, String nomDenominationSociale,
			Integer idTAssure, String codeTAssure, Date dateEffet, String codeSiren) {
		super();
		this.id = id;
//		this.numDevis = numDevis;
		this.idAssure = idAssure;
		this.codeAssure = codeAssure;
		this.taAssureDTO = new TaAssureDTO();
		this.taAssureDTO.setRaisonSociale(raisonSociale); 
		this.taAssureDTO.setIdContactEntreprise(idContactEntreprise);
		this.taAssureDTO.setNom(prenom);
		this.taAssureDTO.setPrenom(prenom);
		this.idContratRcPro = idContratRcPro;
		this.numDossierPolice = numPolice;
		this.contrat = contrat;
		this.numeroAvenant = numeroAvenant;
		this.idCourtier = idCourtier;
		this.codeCourtier= codeCourtier;
		this.nomDenominationSociale = nomDenominationSociale;
		this.taAssureDTO.setIdTAssure(idTAssure);
		this.taAssureDTO.setCodeTAssure(codeTAssure);
		this.dateEffet = dateEffet;
		this.taAssureDTO.setCodeSiren(codeSiren);
	}
		
	
	public Boolean isResilieDepuisMoins30jours() {
		Boolean estResilieDepuisMoins30Jours = false;
		if(this.dateResiliation !=null ) {
			Date date = new Date();
			LocalDate localDateNow = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate localDateRes = this.dateResiliation.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			if(localDateNow.isBefore(localDateRes.plusDays(30)) ) {
				estResilieDepuisMoins30Jours = true;
			}
			
		}
		return estResilieDepuisMoins30Jours;
		
		
	}
	
	public boolean isValidationGlobaleGedYlyade() {
		
			if ((this.validationGlobaleGedYlyade!=null && this.validationGlobaleGedYlyade) ||
					(this.validationGlobaleGedCourtier==null || this.validationGlobaleGedCourtier==false)) {
				return true;
			}
		return false;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	public String getNumDevis() {
//		return numDevis;
//	}
//
//	public void setNumDevis(String numDevis) {
//		this.numDevis = numDevis;
//	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public Integer getIdAssure() {
		return idAssure;
	}

	public void setIdAssure(Integer idAssure) {
		this.idAssure = idAssure;
	}

	public String getCodeAssure() {
		System.out.println("code assuré : "+codeAssure);
		return codeAssure;
	}

	public void setCodeAssure(String codeAssure) {
		
		this.codeAssure = codeAssure;
	}

	public Integer getIdContratRcPro() {
		return idContratRcPro;
	}

	public void setIdContratRcPro(Integer idContratRcPro) {
		this.idContratRcPro = idContratRcPro;
	}

	public TaAssureDTO getTaAssureDTO() {
		return taAssureDTO;
	}

	public void setTaAssureDTO(TaAssureDTO taAssureDTO) {
		this.taAssureDTO = taAssureDTO;
	}

//	public TaDevisRcProDetailDTO getTaDevisRcProDetailDTO() {
//		return taDevisRcProDetailDTO;
//	}
//
//	public void setTaDevisRcProDetailDTO(TaDevisRcProDetailDTO taDevisRcProDetailDTO) {
//		this.taDevisRcProDetailDTO = taDevisRcProDetailDTO;
//	}

	public Integer getIdCourtier() {
		return idCourtier;
	}

	public void setIdCourtier(Integer idCourtier) {
		this.idCourtier = idCourtier;
	}

	public String getCodeCourtier() {
		System.out.println("code courtier : "+codeCourtier);
		return codeCourtier;
	}

	public void setCodeCourtier(String codeCourtier) {
		this.codeCourtier = codeCourtier;
	}

	public Integer getIdDevisRcProDetail() {
		return idDevisRcProDetail;
	}

	public void setIdDevisRcProDetail(Integer idDevisRcProDetail) {
		this.idDevisRcProDetail = idDevisRcProDetail;
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

	public Boolean getResilieNonPaiement() {
		return resilieNonPaiement;
	}

	public void setResilieNonPaiement(Boolean resilieNonPaiement) {
		this.resilieNonPaiement = resilieNonPaiement;
	}

	public Boolean getResilieFausseDeclaration() {
		return resilieFausseDeclaration;
	}

	public void setResilieFausseDeclaration(Boolean resilieFausseDeclaration) {
		this.resilieFausseDeclaration = resilieFausseDeclaration;
	}

	public Date getDateEffet() {
		return dateEffet;
	}

	public void setDateEffet(Date dateRealisation) {
		this.dateEffet = dateRealisation;
	}

	public Date getDateResiliation() {
		return dateResiliation;
	}

	public void setDateResiliation(Date dateResiliation) {
		this.dateResiliation = dateResiliation;
	}

	public TaDossierRcd getDevisDetailOrigine() {
		return devisDetailOrigine;
	}

	public void setDevisDetailOrigine(TaDossierRcd devisDetailOrigine) {
		this.devisDetailOrigine = devisDetailOrigine;
	}

	public Integer getVersionDevisRcProDetail() {
		return versionDevisRcProDetail;
	}

	public void setVersionDevisRcProDetail(Integer versionDevisRcProDetail) {
		this.versionDevisRcProDetail = versionDevisRcProDetail;
	}

//	public byte[] getImgDevisRcPro() {
//		return imgDevisRcPro;
//	}
//
//	public void setImgDevisRcPro(byte[] imgDevisRcPro) {
//		this.imgDevisRcPro = imgDevisRcPro;
//	}

	public BigDecimal getMontantPrime() {
		return montantPrime;
	}

	public void setMontantPrime(BigDecimal montantPrime) {
		this.montantPrime = montantPrime;
	}

	public String getCodeSousTraitance() {
		return codeSousTraitance;
	}

	public void setCodeSousTraitance(String codeSousTraitance) {
		this.codeSousTraitance = codeSousTraitance;
	}

	public BigDecimal getPourcentSoustraitance() {
		return pourcentSoustraitance;
	}

	public void setPourcentSoustraitance(BigDecimal pourcentSoustraitance) {
		this.pourcentSoustraitance = pourcentSoustraitance;
	}

	public BigDecimal getFraisRcPro() {
		return fraisRcPro;
	}

	public void setFraisRcPro(BigDecimal fraisRcPro) {
		this.fraisRcPro = fraisRcPro;
	}

	public BigDecimal getTauxCommission() {
		return tauxCommission;
	}

	public void setTauxCommission(BigDecimal tauxCommission) {
		this.tauxCommission = tauxCommission;
	}

	public Boolean getDejaAssurer() {
		return dejaAssurer;
	}

	public void setDejaAssurer(Boolean dejaAssurer) {
		this.dejaAssurer = dejaAssurer;
	}

	public Boolean getContratEnCours() {
		return contratEnCours;
	}

	public void setContratEnCours(Boolean contratEnCours) {
		this.contratEnCours = contratEnCours;
	}

	public Integer getNbSinistre5ans() {
		return nbSinistre5ans;
	}

	public void setNbSinistre5ans(Integer nbSinistre5ans) {
		this.nbSinistre5ans = nbSinistre5ans;
	}

	public Integer getNbSinistreTotal() {
		return nbSinistreTotal;
	}

	public void setNbSinistreTotal(Integer nbSinistreTotal) {
		this.nbSinistreTotal = nbSinistreTotal;
	}

	public BigDecimal getMontantSinistre() {
		return montantSinistre;
	}

	public void setMontantSinistre(BigDecimal montantSinistre) {
		this.montantSinistre = montantSinistre;
	}

	public String getCodeFranchise() {
		return codeFranchise;
	}

	public void setCodeFranchise(String codeFranchise) {
		this.codeFranchise = codeFranchise;
	}

	public BigDecimal getFranchise() {
		return franchise;
	}

	public void setFranchise(BigDecimal franchise) {
		this.franchise = franchise;
	}

	public String getCodeEcheance() {
		return codeEcheance;
	}

	public void setCodeEcheance(String codeEcheance) {
		this.codeEcheance = codeEcheance;
	}

//	public String getAssureurPrecedentRcp() {
//		return assureurPrecedentRcp;
//	}
//
//	public void setAssureurPrecedentRcp(String assureurPrecedentRcp) {
//		this.assureurPrecedentRcp = assureurPrecedentRcp;
//	}

	public String getAssureurPrecedentRcd() {
		return assureurPrecedentRcd;
	}

	public void setAssureurPrecedentRcd(String assureurPrecedentRcd) {
		this.assureurPrecedentRcd = assureurPrecedentRcd;
	}

//	public Date getDateEffetContratPrecendentRcp() {
//		return dateEffetContratPrecendentRcp;
//	}
//
//	public void setDateEffetContratPrecendentRcp(Date dateEffetContratPrecendentRcp) {
//		this.dateEffetContratPrecendentRcp = dateEffetContratPrecendentRcp;
//	}

	public Date getDateEffetContratPrecendentRcd() {
		return dateEffetContratPrecendentRcd;
	}

	public void setDateEffetContratPrecendentRcd(Date dateEffetContratPrecendentRcd) {
		this.dateEffetContratPrecendentRcd = dateEffetContratPrecendentRcd;
	}

//	public String getPoliceContratPrecedentRcp() {
//		return policeContratPrecedentRcp;
//	}
//
//	public void setPoliceContratPrecedentRcp(String policeContratPrecedentRcp) {
//		this.policeContratPrecedentRcp = policeContratPrecedentRcp;
//	}

	public String getPoliceContratPrecedentRcd() {
		return policeContratPrecedentRcd;
	}

	public void setPoliceContratPrecedentRcd(String policeContratPrecedentRcd) {
		this.policeContratPrecedentRcd = policeContratPrecedentRcd;
	}

//	public BigDecimal getMontantChantierPlusEleve() {
//		return montantChantierPlusEleve;
//	}
//
//	public void setMontantChantierPlusEleve(BigDecimal montantChantierPlusEleve) {
//		this.montantChantierPlusEleve = montantChantierPlusEleve;
//	}
//
//	public BigDecimal getParPourcentCaSousTraitance() {
//		return parPourcentCaSousTraitance;
//	}
//
//	public void setParPourcentCaSousTraitance(BigDecimal parPourcentCaSousTraitance) {
//		this.parPourcentCaSousTraitance = parPourcentCaSousTraitance;
//	}
//
//	public BigDecimal getParPourcentCaRenovation() {
//		return parPourcentCaRenovation;
//	}
//
//	public void setParPourcentCaRenovation(BigDecimal parPourcentCaRenovation) {
//		this.parPourcentCaRenovation = parPourcentCaRenovation;
//	}
//
//	public BigDecimal getParPourcentClientParticulier() {
//		return parPourcentClientParticulier;
//	}
//
//	public void setParPourcentClientParticulier(BigDecimal parPourcentClientParticulier) {
//		this.parPourcentClientParticulier = parPourcentClientParticulier;
//	}
//
//	public BigDecimal getParPourcentCaPrisSousTraitance() {
//		return parPourcentCaPrisSousTraitance;
//	}
//
//	public void setParPourcentCaPrisSousTraitance(BigDecimal parPourcentCaPrisSousTraitance) {
//		this.parPourcentCaPrisSousTraitance = parPourcentCaPrisSousTraitance;
//	}
//
//	public BigDecimal getParPourcentCaNeuf() {
//		return parPourcentCaNeuf;
//	}
//
//	public void setParPourcentCaNeuf(BigDecimal parPourcentCaNeuf) {
//		this.parPourcentCaNeuf = parPourcentCaNeuf;
//	}
//
//	public BigDecimal getParPourcentClientEntreprise() {
//		return parPourcentClientEntreprise;
//	}
//
//	public void setParPourcentClientEntreprise(BigDecimal parPourcentClientEntreprise) {
//		this.parPourcentClientEntreprise = parPourcentClientEntreprise;
//	}

	public BigDecimal getCoutGlobalSinistreRcd() {
		return coutGlobalSinistreRcd;
	}

	public void setCoutGlobalSinistreRcd(BigDecimal coutGlobalSinistreRcd) {
		this.coutGlobalSinistreRcd = coutGlobalSinistreRcd;
	}

//	public BigDecimal getCoutGlobalSinistreRcp() {
//		return coutGlobalSinistreRcp;
//	}
//
//	public void setCoutGlobalSinistreRcp(BigDecimal coutGlobalSinistreRcp) {
//		this.coutGlobalSinistreRcp = coutGlobalSinistreRcp;
//	}

	public Integer getIdContratRcProDetail() {
		return idContratRcProDetail;
	}

	public void setIdContratRcProDetail(Integer idContratRcProDetail) {
		this.idContratRcProDetail = idContratRcProDetail;
	}

	public Integer getIdTauxAssurance() {
		return idTauxAssurance;
	}

	public void setIdTauxAssurance(Integer idTauxAssurance) {
		this.idTauxAssurance = idTauxAssurance;
	}

	public String getCodeTauxAssurance() {
		return codeTauxAssurance;
	}

	public void setCodeTauxAssurance(String codeTauxAssurance) {
		this.codeTauxAssurance = codeTauxAssurance;
	}

	public BigDecimal getTauxTauxAssurance() {
		return tauxTauxAssurance;
	}

	public void setTauxTauxAssurance(BigDecimal tauxTauxAssurance) {
		this.tauxTauxAssurance = tauxTauxAssurance;
	}

	public List<TaActiviteDTO> getListeActivite() {
		return listeActivite;
	}

	public void setListeActivite(List<TaActiviteDTO> listeActivite) {
		this.listeActivite = listeActivite;
	}

	public Boolean getReprisePasse() {
		return reprisePasse;
	}

	public void setReprisePasse(Boolean reprisePasse) {
		this.reprisePasse = reprisePasse;
	}

	public Boolean getInterventionChantierCoutMax() {
		return interventionChantierCoutMax;
	}

	public void setInterventionChantierCoutMax(Boolean interventionchantierCoutMax) {
		this.interventionChantierCoutMax = interventionchantierCoutMax;
	}

	public Date getDateResiliationContratPrecedent() {
		return dateResiliationContratPrecedent;
	}

	public void setDateResiliationContratPrecedent(Date dateResiliationContratPrecedent) {
		this.dateResiliationContratPrecedent = dateResiliationContratPrecedent;
	}

	public String getMotifResiliationContratPrecedent() {
		return motifResiliationContratPrecedent;
	}

	public void setMotifResiliationContratPrecedent(String motifResiliationContratPrecedent) {
		this.motifResiliationContratPrecedent = motifResiliationContratPrecedent;
	}

	public BigDecimal getPrimeNette() {
		return primeNette;
	}

	public void setPrimeNette(BigDecimal primeNette) {
		this.primeNette = primeNette;
	}

	public Date getDateDevis() {
		return dateDevis;
	}

	public void setDateDevis(Date dateDevis) {
		this.dateDevis = dateDevis;
	}

	public BigDecimal getMontantPremierReglement() {
		return montantPremierReglement;
	}

	public void setMontantPremierReglement(BigDecimal montantPremierReglement) {
		this.montantPremierReglement = montantPremierReglement;
	}

	public Date getDateDebutPremierePeriode() {
		return dateDebutPremierePeriode;
	}

	public void setDateDebutPremierePeriode(Date dateDebutPremierePeriode) {
		this.dateDebutPremierePeriode = dateDebutPremierePeriode;
	}

	public Date getDateFinPremierePeriode() {
		return dateFinPremierePeriode;
	}

	public void setDateFinPremierePeriode(Date dateFinPremierePeriode) {
		this.dateFinPremierePeriode = dateFinPremierePeriode;
	}

	public BigDecimal getMontantTaxesDiversesAssurance() {
		return montantTaxesDiversesAssurance;
	}

	public void setMontantTaxesDiversesAssurance(BigDecimal montantTaxesDiversesAssurance) {
		this.montantTaxesDiversesAssurance = montantTaxesDiversesAssurance;
	}

	public Integer getIdTReglement() {
		return idTReglement;
	}

	public void setIdTReglement(Integer idTReglement) {
		this.idTReglement = idTReglement;
	}

	public String getCodeTReglement() {
		return codeTReglement;
	}

	public void setCodeTReglement(String codeTReglement) {
		this.codeTReglement = codeTReglement;
	}

	public String getLiblTReglement() {
		return liblTReglement;
	}

	public void setLiblTReglement(String liblTReglement) {
		this.liblTReglement = liblTReglement;
	}

	public Integer getIdTEcheance() {
		return idTEcheance;
	}

	public void setIdTEcheance(Integer idTEcheance) {
		this.idTEcheance = idTEcheance;
	}

	public BigDecimal getTauxEcheance() {
		return tauxEcheance;
	}

	public void setTauxEcheance(BigDecimal tauxEcheance) {
		this.tauxEcheance = tauxEcheance;
	}

	public String getLiblTEcheance() {
		return liblTEcheance;
	}

	public void setLiblTEcheance(String liblTEcheance) {
		this.liblTEcheance = liblTEcheance;
	}

	public String getCodeTEcheance() {
		return codeTEcheance;
	}

	public void setCodeTEcheance(String codeTEcheance) {
		this.codeTEcheance = codeTEcheance;
	}

	public Date getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public String getActivitePrincipale() {
		return activitePrincipale;
	}

	public void setActivitePrincipale(String activitePrincipale) {
		this.activitePrincipale = activitePrincipale;
	}

	public BigDecimal getChiffreAffaireExerciceAnterieur() {
		return chiffreAffaireExerciceAnterieur;
	}

	public void setChiffreAffaireExerciceAnterieur(BigDecimal chiffreAffaireExerciceAnterieur) {
		this.chiffreAffaireExerciceAnterieur = chiffreAffaireExerciceAnterieur;
	}

	public BigDecimal getChiffreAffaireExercicePrevisionnel() {
		return chiffreAffaireExercicePrevisionnel;
	}

	public void setChiffreAffaireExercicePrevisionnel(BigDecimal chiffreAffaireExercicePrevisionnel) {
		this.chiffreAffaireExercicePrevisionnel = chiffreAffaireExercicePrevisionnel;
	}

	public Integer getEffectifTotalExercice() {
		return effectifTotalExercice;
	}

	public void setEffectifTotalExercice(Integer effectifTotalExercice) {
		this.effectifTotalExercice = effectifTotalExercice;
	}

	public Integer getEffectifTotalExerciceAnterieur() {
		return effectifTotalExerciceAnterieur;
	}

	public void setEffectifTotalExerciceAnterieur(Integer effectifTotalExerciceAnterieur) {
		this.effectifTotalExerciceAnterieur = effectifTotalExerciceAnterieur;
	}

	public List<TaTauxRcproPibDTO> getListeTauxRcProPib() {
		return listeTauxRcProPib;
	}

	public void setListeTauxRcProPib(List<TaTauxRcproPibDTO> listeTauxRcProPib) {
		this.listeTauxRcProPib = listeTauxRcProPib;
	}

	public Integer getIdTSousTraitance() {
		return idTSousTraitance;
	}

	public void setIdTSousTraitance(Integer idTSousTraitance) {
		this.idTSousTraitance = idTSousTraitance;
	}

	public String getCodeTSousTraitance() {
		return codeTSousTraitance;
	}

	public void setCodeTSousTraitance(String codeTSousTraitance) {
		this.codeTSousTraitance = codeTSousTraitance;
	}

	public String getLiblTSousTraitance() {
		return liblTSousTraitance;
	}

	public void setLiblTSousTraitance(String liblTSousTraitance) {
		this.liblTSousTraitance = liblTSousTraitance;
	}

	public BigDecimal getBaseMin() {
		return baseMin;
	}

	public void setBaseMin(BigDecimal baseMin) {
		this.baseMin = baseMin;
	}

	public BigDecimal getBaseMax() {
		return baseMax;
	}

	public void setBaseMax(BigDecimal baseMax) {
		this.baseMax = baseMax;
	}

	public BigDecimal getTaux() {
		return taux;
	}

	public void setTaux(BigDecimal taux) {
		this.taux = taux;
	}

	public String getNumDossierPolice() {
		return numDossierPolice;
	}

	public void setNumDossierPolice(String numDossierPolice) {
		this.numDossierPolice = numDossierPolice;
	}

	public Boolean getContrat() {
		return contrat;
	}

	public void setContrat(Boolean contrat) {
		this.contrat = contrat;
	}

	public String getNomDenominationSociale() {
		return nomDenominationSociale;
	}

	public void setNomDenominationSociale(String nomDenominationSociale) {
		this.nomDenominationSociale = nomDenominationSociale;
	}

	public BigDecimal getMontantCommissionCourtier() {
		return montantCommissionCourtier;
	}

	public void setMontantCommissionCourtier(BigDecimal montantCommissionCourtier) {
		this.montantCommissionCourtier = montantCommissionCourtier;
	}

	public BigDecimal getTauxSurCommissionCourtier() {
		return tauxSurCommissionCourtier;
	}

	public void setTauxSurCommissionCourtier(BigDecimal tauxSurCommissionCourtier) {
		this.tauxSurCommissionCourtier = tauxSurCommissionCourtier;
	}

	public BigDecimal getMontantSurCommissionCourtier() {
		return montantSurCommissionCourtier;
	}

	public void setMontantSurCommissionCourtier(BigDecimal montantSurCommissionCourtier) {
		this.montantSurCommissionCourtier = montantSurCommissionCourtier;
	}

	public BigDecimal getMontantProtectionJuridique() {
		return montantProtectionJuridique;
	}

	public void setMontantProtectionJuridique(BigDecimal montantProtectionJuridique) {
		this.montantProtectionJuridique = montantProtectionJuridique;
	}

	public BigDecimal getMontantTaxeFiscale() {
		return montantTaxeFiscale;
	}

	public void setMontantTaxeFiscale(BigDecimal montantTaxeFiscale) {
		this.montantTaxeFiscale = montantTaxeFiscale;
	}

	public BigDecimal getTauxTaxeFiscale() {
		return tauxTaxeFiscale;
	}

	public void setTauxTaxeFiscale(BigDecimal tauxTaxeFiscale) {
		this.tauxTaxeFiscale = tauxTaxeFiscale;
	}

	public BigDecimal getMontantFraisDeGestion() {
		return montantFraisDeGestion;
	}

	public void setMontantFraisDeGestion(BigDecimal montantFraisDeGestion) {
		this.montantFraisDeGestion = montantFraisDeGestion;
	}

	public BigDecimal getPrimeAnnuelle() {
		return primeAnnuelle;
	}

	public void setPrimeAnnuelle(BigDecimal primeAnnuelle) {
		this.primeAnnuelle = primeAnnuelle;
	}

	public BigDecimal getPrimeAnnuelleComplete() {
		return primeAnnuelleComplete;
	}

	public void setPrimeAnnuelleComplete(BigDecimal primeAnnuelleComplete) {
		this.primeAnnuelleComplete = primeAnnuelleComplete;
	}

	public BigDecimal getMontantFraisFractionnement() {
		return montantFraisFractionnement;
	}

	public void setMontantFraisFractionnement(BigDecimal montantFraisFractionnement) {
		this.montantFraisFractionnement = montantFraisFractionnement;
	}

	public String getLogDetailCalculPrime() {
		return logDetailCalculPrime;
	}

	public void setLogDetailCalculPrime(String logDetailCalculPrime) {
		this.logDetailCalculPrime = logDetailCalculPrime;
	}

	public String getLogDetailCalculPremierReglement() {
		return logDetailCalculPremierReglement;
	}

	public void setLogDetailCalculPremierReglement(String logDetailCalculPremierReglement) {
		this.logDetailCalculPremierReglement = logDetailCalculPremierReglement;
	}

	public String getRaisonSociale() {
		return raisonSociale;
	}

	public void setRaisonSociale(String raisonSociale) {
		this.raisonSociale = raisonSociale;
	}

	public Integer getNumeroAvenant() {
		return numeroAvenant;
	}

	public void setNumeroAvenant(Integer numeroAvenant) {
		this.numeroAvenant = numeroAvenant;
	}

	public Boolean getExperienceActivite3ans() {
		return experienceActivite3ans;
	}

	public void setExperienceActivite3ans(Boolean experienceActivite3ans) {
		this.experienceActivite3ans = experienceActivite3ans;
	}

	public Boolean getExperienceActivte5ans() {
		return experienceActivte5ans;
	}

	public void setExperienceActivte5ans(Boolean experienceActivte5ans) {
		this.experienceActivte5ans = experienceActivte5ans;
	}

	public Boolean getExerceActiviteNomenclature() {
		return exerceActiviteNomenclature;
	}

	public void setExerceActiviteNomenclature(Boolean exerceActiviteNomenclature) {
		this.exerceActiviteNomenclature = exerceActiviteNomenclature;
	}

	public Boolean getCoutOuvrageInferieur15k() {
		return coutOuvrageInferieur15k;
	}

	public void setCoutOuvrageInferieur15k(Boolean coutOuvrageInferieur15k) {
		this.coutOuvrageInferieur15k = coutOuvrageInferieur15k;
	}

	public Boolean getMontantTravauxHtMax50k() {
		return montantTravauxHtMax50k;
	}

	public void setMontantTravauxHtMax50k(Boolean montantTravauxHtMax50k) {
		this.montantTravauxHtMax50k = montantTravauxHtMax50k;
	}

	public Boolean getMontantTravauxHtMax100k() {
		return montantTravauxHtMax100k;
	}

	public void setMontantTravauxHtMax100k(Boolean montantTravauxHtMax100k) {
		this.montantTravauxHtMax100k = montantTravauxHtMax100k;
	}

	public Boolean getMontantTravauxHtMax250k() {
		return montantTravauxHtMax250k;
	}

	public void setMontantTravauxHtMax250k(Boolean montantTravauxHtMax250k) {
		this.montantTravauxHtMax250k = montantTravauxHtMax250k;
	}

	public Boolean getMontantTravauxHtMax500k() {
		return montantTravauxHtMax500k;
	}

	public void setMontantTravauxHtMax500k(Boolean montantTravauxHtMax500k) {
		this.montantTravauxHtMax500k = montantTravauxHtMax500k;
	}

	public Boolean getMontantTravauxHtMax1m() {
		return montantTravauxHtMax1m;
	}

	public void setMontantTravauxHtMax1m(Boolean montantTravauxHtMax1m) {
		this.montantTravauxHtMax1m = montantTravauxHtMax1m;
	}

	public Boolean getInterventionConstructeurMaisonIndiv() {
		return interventionConstructeurMaisonIndiv;
	}

	public void setInterventionConstructeurMaisonIndiv(Boolean interventionConstructeurMaisonIndiv) {
		this.interventionConstructeurMaisonIndiv = interventionConstructeurMaisonIndiv;
	}

	public Boolean getInterventionContractantGeneral() {
		return interventionContractantGeneral;
	}

	public void setInterventionContractantGeneral(Boolean interventionContractantGeneral) {
		this.interventionContractantGeneral = interventionContractantGeneral;
	}

	public Boolean getInterventionBatiment() {
		return interventionBatiment;
	}

	public void setInterventionBatiment(Boolean interventionBatiment) {
		this.interventionBatiment = interventionBatiment;
	}

	public Boolean getInterventionMaitreOeuvre() {
		return interventionMaitreOeuvre;
	}

	public void setInterventionMaitreOeuvre(Boolean interventionMaitreOeuvre) {
		this.interventionMaitreOeuvre = interventionMaitreOeuvre;
	}

	public Boolean getInterventionImmobilier() {
		return interventionImmobilier;
	}

	public void setInterventionImmobilier(Boolean interventionImmobilier) {
		this.interventionImmobilier = interventionImmobilier;
	}

	public Boolean getInterventionFabricantMatConstruction() {
		return interventionFabricantMatConstruction;
	}

	public void setInterventionFabricantMatConstruction(Boolean interventionFabricantMatConstruction) {
		this.interventionFabricantMatConstruction = interventionFabricantMatConstruction;
	}

	public Boolean getActivitePrincNegoceFabrMatConstructionNonPose() {
		return activitePrincNegoceFabrMatConstructionNonPose;
	}

	public void setActivitePrincNegoceFabrMatConstructionNonPose(Boolean activitePrincNegoceFabrMatConstructionNonPose) {
		this.activitePrincNegoceFabrMatConstructionNonPose = activitePrincNegoceFabrMatConstructionNonPose;
	}

	public Boolean getTravauxTechniqueNonCourant() {
		return travauxTechniqueNonCourant;
	}

	public void setTravauxTechniqueNonCourant(Boolean travauxTechniqueNonCourant) {
		this.travauxTechniqueNonCourant = travauxTechniqueNonCourant;
	}

	public Boolean getInterventionMonumentHistorique() {
		return interventionMonumentHistorique;
	}

	public void setInterventionMonumentHistorique(Boolean interventionMonumentHistorique) {
		this.interventionMonumentHistorique = interventionMonumentHistorique;
	}

	public Boolean getAvisTechnique() {
		return avisTechnique;
	}

	public void setAvisTechnique(Boolean avisTechnique) {
		this.avisTechnique = avisTechnique;
	}

	public Boolean getDocumentUnique() {
		return documentUnique;
	}

	public void setDocumentUnique(Boolean documentUnique) {
		this.documentUnique = documentUnique;
	}

	public Boolean getRespectRegles() {
		return respectRegles;
	}

	public void setRespectRegles(Boolean respectRegles) {
		this.respectRegles = respectRegles;
	}

	public Boolean getRespectDispositionSousTraitance() {
		return respectDispositionSousTraitance;
	}

	public void setRespectDispositionSousTraitance(Boolean respectDispositionSousTraitance) {
		this.respectDispositionSousTraitance = respectDispositionSousTraitance;
	}

	public Integer getAnneesExperienceActivite() {
		return anneesExperienceActivite;
	}

	public void setAnneesExperienceActivite(Integer anneesExperienceActivite) {
		this.anneesExperienceActivite = anneesExperienceActivite;
	}

	public Integer getEffectifSurChantier() {
		return effectifSurChantier;
	}

	public void setEffectifSurChantier(Integer effectifSurChantier) {
		this.effectifSurChantier = effectifSurChantier;
	}

	public Integer getEffectifApprentis() {
		return effectifApprentis;
	}

	public void setEffectifApprentis(Integer effectifApprentis) {
		this.effectifApprentis = effectifApprentis;
	}

	public Integer getEffectifCommerciauxAdministratifs() {
		return effectifCommerciauxAdministratifs;
	}

	public void setEffectifCommerciauxAdministratifs(Integer effectifCommerciauxAdministratifs) {
		this.effectifCommerciauxAdministratifs = effectifCommerciauxAdministratifs;
	}

	public Integer getEffectifEncadrement() {
		return effectifEncadrement;
	}

	public void setEffectifEncadrement(Integer effectifEncadrement) {
		this.effectifEncadrement = effectifEncadrement;
	}

	public Boolean getQualibat() {
		return qualibat;
	}

	public void setQualibat(Boolean qualibat) {
		this.qualibat = qualibat;
	}

	public BigDecimal getPourcentCaSousTraitDernierExercice() {
		return pourcentCaSousTraitDernierExercice;
	}

	public void setPourcentCaSousTraitDernierExercice(BigDecimal pourcentCaSousTraitDernierExercice) {
		this.pourcentCaSousTraitDernierExercice = pourcentCaSousTraitDernierExercice;
	}

	public BigDecimal getPourcentCaSousTraitExerciceNMoins1() {
		return pourcentCaSousTraitExerciceNMoins1;
	}

	public void setPourcentCaSousTraitExerciceNMoins1(BigDecimal pourcentCaSousTraitExerciceNMoins1) {
		this.pourcentCaSousTraitExerciceNMoins1 = pourcentCaSousTraitExerciceNMoins1;
	}

	public BigDecimal getPourcentCaSousTraitExerciceNMoins2() {
		return pourcentCaSousTraitExerciceNMoins2;
	}

	public void setPourcentCaSousTraitExerciceNMoins2(BigDecimal pourcentCaSousTraitExerciceNMoins2) {
		this.pourcentCaSousTraitExerciceNMoins2 = pourcentCaSousTraitExerciceNMoins2;
	}

	public BigDecimal getCaTotalHtExerciceNMoins1() {
		return caTotalHtExerciceNMoins1;
	}

	public void setCaTotalHtExerciceNMoins1(BigDecimal caTotalHtExerciceNMoins1) {
		this.caTotalHtExerciceNMoins1 = caTotalHtExerciceNMoins1;
	}

	public BigDecimal getCaTotalHtExerciceNMoins2() {
		return caTotalHtExerciceNMoins2;
	}

	public void setCaTotalHtExerciceNMoins2(BigDecimal caTotalHtExerciceNMoins2) {
		this.caTotalHtExerciceNMoins2 = caTotalHtExerciceNMoins2;
	}

	public Boolean getAncienneteMoins12mois() {
		return ancienneteMoins12mois;
	}

	public void setAncienneteMoins12mois(Boolean ancienneteMoins12mois) {
		this.ancienneteMoins12mois = ancienneteMoins12mois;
	}

	public Boolean getAnciennete12a36mois() {
		return anciennete12a36mois;
	}

	public void setAnciennete12a36mois(Boolean anciennete12a36mois) {
		this.anciennete12a36mois = anciennete12a36mois;
	}

	public Boolean getAnciennetePlus36mois() {
		return anciennetePlus36mois;
	}

	public void setAnciennetePlus36mois(Boolean anciennetePlus36mois) {
		this.anciennetePlus36mois = anciennetePlus36mois;
	}

	public Boolean getAssureMemeRisque() {
		return assureMemeRisque;
	}

	public void setAssureMemeRisque(Boolean assureMemeRisque) {
		this.assureMemeRisque = assureMemeRisque;
	}

	public Boolean getAssureMemeRisqueRcd() {
		return assureMemeRisqueRcd;
	}

	public void setAssureMemeRisqueRcd(Boolean assureMemeRisqueRcd) {
		this.assureMemeRisqueRcd = assureMemeRisqueRcd;
	}

	public Boolean getAssureMemeRisqueRce() {
		return assureMemeRisqueRce;
	}

	public void setAssureMemeRisqueRce(Boolean assureMemeRisqueRce) {
		this.assureMemeRisqueRce = assureMemeRisqueRce;
	}

	public Boolean getAntecedentRcdContratEnCours() {
		return antecedentRcdContratEnCours;
	}

	public void setAntecedentRcdContratEnCours(Boolean antecedentRcdContratEnCours) {
		this.antecedentRcdContratEnCours = antecedentRcdContratEnCours;
	}

	public Boolean getAntecedentRcdContratResilie() {
		return antecedentRcdContratResilie;
	}

	public void setAntecedentRcdContratResilie(Boolean antecedentRcdContratResilie) {
		this.antecedentRcdContratResilie = antecedentRcdContratResilie;
	}

	public Boolean getAntecedentRceContratEnCours() {
		return antecedentRceContratEnCours;
	}

	public void setAntecedentRceContratEnCours(Boolean antecedentRceContratEnCours) {
		this.antecedentRceContratEnCours = antecedentRceContratEnCours;
	}

	public Boolean getAntecedentRceContratResilie() {
		return antecedentRceContratResilie;
	}

	public void setAntecedentRceContratResilie(Boolean antecedentRceContratResilie) {
		this.antecedentRceContratResilie = antecedentRceContratResilie;
	}

	public Date getAntecedentRcdDateResiliation() {
		return antecedentRcdDateResiliation;
	}

	public void setAntecedentRcdDateResiliation(Date antecedentRcdDateResiliation) {
		this.antecedentRcdDateResiliation = antecedentRcdDateResiliation;
	}

	public Date getAntecedentRceDateResiliation() {
		return antecedentRceDateResiliation;
	}

	public void setAntecedentRceDateResiliation(Date antecedentRceDateResiliation) {
		this.antecedentRceDateResiliation = antecedentRceDateResiliation;
	}

	public Boolean getAntecedentRcdResilieAssure() {
		return antecedentRcdResilieAssure;
	}

	public void setAntecedentRcdResilieAssure(Boolean antecedentRcdResilieAssure) {
		this.antecedentRcdResilieAssure = antecedentRcdResilieAssure;
	}

	public Boolean getAntecedentRceResilieAssure() {
		return antecedentRceResilieAssure;
	}

	public void setAntecedentRceResilieAssure(Boolean antecedentRceResilieAssure) {
		this.antecedentRceResilieAssure = antecedentRceResilieAssure;
	}

	public Boolean getAntecedentRcdResilieEcheance() {
		return antecedentRcdResilieEcheance;
	}

	public void setAntecedentRcdResilieEcheance(Boolean antecedentRcdResilieEcheance) {
		this.antecedentRcdResilieEcheance = antecedentRcdResilieEcheance;
	}

	public Boolean getAntecedentRceResilieEcheance() {
		return antecedentRceResilieEcheance;
	}

	public void setAntecedentRceResilieEcheance(Boolean antecedentRceResilieEcheance) {
		this.antecedentRceResilieEcheance = antecedentRceResilieEcheance;
	}

	public Boolean getAntecedentRcdHausseTarif() {
		return antecedentRcdHausseTarif;
	}

	public void setAntecedentRcdHausseTarif(Boolean antecedentRcdHausseTarif) {
		this.antecedentRcdHausseTarif = antecedentRcdHausseTarif;
	}

	public Boolean getAntecedentRceHausseTarif() {
		return antecedentRceHausseTarif;
	}

	public void setAntecedentRceHausseTarif(Boolean antecedentRceHausseTarif) {
		this.antecedentRceHausseTarif = antecedentRceHausseTarif;
	}

	public Boolean getAntecedentRcdChangementActivite() {
		return antecedentRcdChangementActivite;
	}

	public void setAntecedentRcdChangementActivite(Boolean antecedentRcdChangementActivite) {
		this.antecedentRcdChangementActivite = antecedentRcdChangementActivite;
	}

	public Boolean getAntecedentRceChangementActivite() {
		return antecedentRceChangementActivite;
	}

	public void setAntecedentRceChangementActivite(Boolean antecedentRceChangementActivite) {
		this.antecedentRceChangementActivite = antecedentRceChangementActivite;
	}

	public Boolean getAntecedentRcdResilieAmiable() {
		return antecedentRcdResilieAmiable;
	}

	public void setAntecedentRcdResilieAmiable(Boolean antecedentRcdResilieAmiable) {
		this.antecedentRcdResilieAmiable = antecedentRcdResilieAmiable;
	}

	public Boolean getAntecedentRceResilieAmiable() {
		return antecedentRceResilieAmiable;
	}

	public void setAntecedentRceResilieAmiable(Boolean antecedentRceResilieAmiable) {
		this.antecedentRceResilieAmiable = antecedentRceResilieAmiable;
	}

	public Boolean getAntecedentRcdAssureur() {
		return antecedentRcdAssureur;
	}

	public void setAntecedentRcdAssureur(Boolean antecedentRcdAssureur) {
		this.antecedentRcdAssureur = antecedentRcdAssureur;
	}

	public Boolean getAntecedentRceAssureur() {
		return antecedentRceAssureur;
	}

	public void setAntecedentRceAssureur(Boolean antecedentRceAssureur) {
		this.antecedentRceAssureur = antecedentRceAssureur;
	}

	public Boolean getAntecedentRcdNonPaiementPrime() {
		return antecedentRcdNonPaiementPrime;
	}

	public void setAntecedentRcdNonPaiementPrime(Boolean antecedentRcdNonPaiementPrime) {
		this.antecedentRcdNonPaiementPrime = antecedentRcdNonPaiementPrime;
	}

	public Boolean getAntecedentRceNonPaiementPrime() {
		return antecedentRceNonPaiementPrime;
	}

	public void setAntecedentRceNonPaiementPrime(Boolean antecedentRceNonPaiementPrime) {
		this.antecedentRceNonPaiementPrime = antecedentRceNonPaiementPrime;
	}

	public Boolean getAntecedentRcdSinistre() {
		return antecedentRcdSinistre;
	}

	public void setAntecedentRcdSinistre(Boolean antecedentRcdSinistre) {
		this.antecedentRcdSinistre = antecedentRcdSinistre;
	}

	public Boolean getAntecedentRceSinistre() {
		return antecedentRceSinistre;
	}

	public void setAntecedentRceSinistre(Boolean antecedentRceSinistre) {
		this.antecedentRceSinistre = antecedentRceSinistre;
	}

	public Boolean getAntecedentRcdModifActivite() {
		return antecedentRcdModifActivite;
	}

	public void setAntecedentRcdModifActivite(Boolean antecedentRcdModifActivite) {
		this.antecedentRcdModifActivite = antecedentRcdModifActivite;
	}

	public Boolean getAntecedentRceModifActivite() {
		return antecedentRceModifActivite;
	}

	public void setAntecedentRceModifActivite(Boolean antecedentRceModifActivite) {
		this.antecedentRceModifActivite = antecedentRceModifActivite;
	}

	public Boolean getAntecedentRcdAutre() {
		return antecedentRcdAutre;
	}

	public void setAntecedentRcdAutre(Boolean antecedentRcdAutre) {
		this.antecedentRcdAutre = antecedentRcdAutre;
	}

	public Boolean getAntecedentRceAutre() {
		return antecedentRceAutre;
	}

	public void setAntecedentRceAutre(Boolean antecedentRceAutre) {
		this.antecedentRceAutre = antecedentRceAutre;
	}

	public Boolean getInterruptionAssuranceMoins6mois() {
		return interruptionAssuranceMoins6mois;
	}

	public void setInterruptionAssuranceMoins6mois(Boolean interruptionAssuranceMoins6mois) {
		this.interruptionAssuranceMoins6mois = interruptionAssuranceMoins6mois;
	}

	public Boolean getInterruptionAssurance6a12mois() {
		return interruptionAssurance6a12mois;
	}

	public void setInterruptionAssurance6a12mois(Boolean interruptionAssurance6a12mois) {
		this.interruptionAssurance6a12mois = interruptionAssurance6a12mois;
	}

	public Boolean getInterruptionAssurance12a24mois() {
		return interruptionAssurance12a24mois;
	}

	public void setInterruptionAssurance12a24mois(Boolean interruptionAssurance12a24mois) {
		this.interruptionAssurance12a24mois = interruptionAssurance12a24mois;
	}

	public Boolean getInterruptionAssurance60a84mois() {
		return interruptionAssurance60a84mois;
	}

	public void setInterruptionAssurance60a84mois(Boolean interruptionAssurance60a84mois) {
		this.interruptionAssurance60a84mois = interruptionAssurance60a84mois;
	}

	public Boolean getInterruptionAssurancePlusDe84mois() {
		return interruptionAssurancePlusDe84mois;
	}

	public void setInterruptionAssurancePlusDe84mois(Boolean interruptionAssurancePlusDe84mois) {
		this.interruptionAssurancePlusDe84mois = interruptionAssurancePlusDe84mois;
	}

	public String getAntecedentRcdPoliceAssureur() {
		return antecedentRcdPoliceAssureur;
	}

	public void setAntecedentRcdPoliceAssureur(String antecedentRcdPoliceAssureur) {
		this.antecedentRcdPoliceAssureur = antecedentRcdPoliceAssureur;
	}

	public String getAntecedentRcePoliceAssureur() {
		return antecedentRcePoliceAssureur;
	}

	public void setAntecedentRcePoliceAssureur(String antecedentRcePoliceAssureur) {
		this.antecedentRcePoliceAssureur = antecedentRcePoliceAssureur;
	}

	public String getAntecedentRcdNomAssureur() {
		return antecedentRcdNomAssureur;
	}

	public void setAntecedentRcdNomAssureur(String antecedentRcdNomAssureur) {
		this.antecedentRcdNomAssureur = antecedentRcdNomAssureur;
	}

	public String getAntecedentRceNomAssureur() {
		return antecedentRceNomAssureur;
	}

	public void setAntecedentRceNomAssureur(String antecedentRceNomAssureur) {
		this.antecedentRceNomAssureur = antecedentRceNomAssureur;
	}

	public Boolean getSinistraliteLiquidationSocieteDemandeuse() {
		return sinistraliteLiquidationSocieteDemandeuse;
	}

	public void setSinistraliteLiquidationSocieteDemandeuse(Boolean sinistraliteLiquidationSocieteDemandeuse) {
		this.sinistraliteLiquidationSocieteDemandeuse = sinistraliteLiquidationSocieteDemandeuse;
	}

	public Boolean getSinistraliteLiquidationAutreSociete() {
		return sinistraliteLiquidationAutreSociete;
	}

	public void setSinistraliteLiquidationAutreSociete(Boolean sinistraliteLiquidationAutreSociete) {
		this.sinistraliteLiquidationAutreSociete = sinistraliteLiquidationAutreSociete;
	}

	public Boolean getSinistraliteRisqueRefusAssurance() {
		return sinistraliteRisqueRefusAssurance;
	}

	public void setSinistraliteRisqueRefusAssurance(Boolean sinistraliteRisqueRefusAssurance) {
		this.sinistraliteRisqueRefusAssurance = sinistraliteRisqueRefusAssurance;
	}

	public Boolean getSinistraliteMiseEnCause() {
		return sinistraliteMiseEnCause;
	}

	public void setSinistraliteMiseEnCause(Boolean sinistraliteMiseEnCause) {
		this.sinistraliteMiseEnCause = sinistraliteMiseEnCause;
	}

	public Boolean getSinistraliteEvenementEngageantResp() {
		return sinistraliteEvenementEngageantResp;
	}

	public void setSinistraliteEvenementEngageantResp(Boolean sinistraliteEvenementEngageantResp) {
		this.sinistraliteEvenementEngageantResp = sinistraliteEvenementEngageantResp;
	}

	public String getSinistraliteEvenementFaits() {
		return sinistraliteEvenementFaits;
	}

	public void setSinistraliteEvenementFaits(String sinistraliteEvenementFaits) {
		this.sinistraliteEvenementFaits = sinistraliteEvenementFaits;
	}

	public Boolean getReprisePasseMoinsDe3mois() {
		return reprisePasseMoinsDe3mois;
	}

	public void setReprisePasseMoinsDe3mois(Boolean reprisePasseMoinsDe3mois) {
		this.reprisePasseMoinsDe3mois = reprisePasseMoinsDe3mois;
	}

	public Boolean getReprisePasseDe3a6mois() {
		return reprisePasseDe3a6mois;
	}

	public void setReprisePasseDe3a6mois(Boolean reprisePasseDe3a6mois) {
		this.reprisePasseDe3a6mois = reprisePasseDe3a6mois;
	}

	public Boolean getReprisePasseDe6a12mois() {
		return reprisePasseDe6a12mois;
	}

	public void setReprisePasseDe6a12mois(Boolean reprisePasseDe6a12mois) {
		this.reprisePasseDe6a12mois = reprisePasseDe6a12mois;
	}

	public Boolean getTerritorialiteLieuFranceMetrop() {
		return territorialiteLieuFranceMetrop;
	}

	public void setTerritorialiteLieuFranceMetrop(Boolean territorialiteLieuFranceMetrop) {
		this.territorialiteLieuFranceMetrop = territorialiteLieuFranceMetrop;
	}

	public Boolean getTerritorialiteLieuCorse() {
		return territorialiteLieuCorse;
	}

	public void setTerritorialiteLieuCorse(Boolean territorialiteLieuCorse) {
		this.territorialiteLieuCorse = territorialiteLieuCorse;
	}

	public Boolean getTerritorialiteLieuDomtom() {
		return territorialiteLieuDomtom;
	}

	public void setTerritorialiteLieuDomtom(Boolean territorialiteLieuDomtom) {
		this.territorialiteLieuDomtom = territorialiteLieuDomtom;
	}

	public Boolean getInformerCaractereObligatoire() {
		return informerCaractereObligatoire;
	}

	public void setInformerCaractereObligatoire(Boolean informerCaractereObligatoire) {
		this.informerCaractereObligatoire = informerCaractereObligatoire;
	}

	public Boolean getInformationPropositionPartieIntegranteContrat() {
		return informationPropositionPartieIntegranteContrat;
	}

	public void setInformationPropositionPartieIntegranteContrat(Boolean informationPropositionPartieIntegranteContrat) {
		this.informationPropositionPartieIntegranteContrat = informationPropositionPartieIntegranteContrat;
	}

	public Boolean getAutoriseAssureurCommuniquerReponses() {
		return autoriseAssureurCommuniquerReponses;
	}

	public void setAutoriseAssureurCommuniquerReponses(Boolean autoriseAssureurCommuniquerReponses) {
		this.autoriseAssureurCommuniquerReponses = autoriseAssureurCommuniquerReponses;
	}

	public Boolean getOpposeUtilisationDonneesFinCommerciale() {
		return opposeUtilisationDonneesFinCommerciale;
	}

	public void setOpposeUtilisationDonneesFinCommerciale(Boolean opposeUtilisationDonneesFinCommerciale) {
		this.opposeUtilisationDonneesFinCommerciale = opposeUtilisationDonneesFinCommerciale;
	}

	public Boolean getInterruptionAssurance24a36mois() {
		return interruptionAssurance24a36mois;
	}

	public void setInterruptionAssurance24a36mois(Boolean interruptionAssurance24a36mois) {
		this.interruptionAssurance24a36mois = interruptionAssurance24a36mois;
	}

	public Boolean getInterruptionAssurance36a60mois() {
		return interruptionAssurance36a60mois;
	}

	public void setInterruptionAssurance36a60mois(Boolean interruptionAssurance36a60mois) {
		this.interruptionAssurance36a60mois = interruptionAssurance36a60mois;
	}

	public Boolean getSoumisEtude() {
		return soumisEtude;
	}

	public void setSoumisEtude(Boolean soumisEtude) {
		this.soumisEtude = soumisEtude;
	}

	public Boolean getValidationApresetudeYlyade() {
		return validationApresetudeYlyade;
	}

	public void setValidationApresetudeYlyade(Boolean validationApresetude) {
		this.validationApresetudeYlyade = validationApresetude;
	}

	public Boolean getValidationSuperAssureur() {
		return validationSuperAssureur;
	}

	public void setValidationSuperAssureur(Boolean validationSuperAssureur) {
		this.validationSuperAssureur = validationSuperAssureur;
	}

	public Boolean getValidationYlyade() {
		return validationYlyade;
	}

	public void setValidationYlyade(Boolean validationYlyade) {
		this.validationYlyade = validationYlyade;
	}

	public Date getDateRepriseDuPasse() {
		return dateRepriseDuPasse;
	}

	public void setDateRepriseDuPasse(Date dateRepriseDuPasse) {
		this.dateRepriseDuPasse = dateRepriseDuPasse;
	}

	public BigDecimal getTauxRisqueParFamilleActivite() {
		return tauxRisqueParFamilleActivite;
	}

	public void setTauxRisqueParFamilleActivite(BigDecimal tauxRisqueParFamilleActivite) {
		this.tauxRisqueParFamilleActivite = tauxRisqueParFamilleActivite;
	}

	public BigDecimal getTauxNombreActivite() {
		return tauxNombreActivite;
	}

	public void setTauxNombreActivite(BigDecimal tauxNombreActivite) {
		this.tauxNombreActivite = tauxNombreActivite;
	}

	public String getCodeInterruptionAssurance() {
		return codeInterruptionAssurance;
	}

	public void setCodeInterruptionAssurance(String codeInterruptionAssurance) {
		this.codeInterruptionAssurance = codeInterruptionAssurance;
	}

	public BigDecimal getTauxInterruptionAssurance() {
		return tauxInterruptionAssurance;
	}

	public void setTauxInterruptionAssurance(BigDecimal tauxInterruptionAssurance) {
		this.tauxInterruptionAssurance = tauxInterruptionAssurance;
	}

	public String getCodeReprisePasse() {
		return codeReprisePasse;
	}

	public void setCodeReprisePasse(String codeReprisePasse) {
		this.codeReprisePasse = codeReprisePasse;
	}

	public BigDecimal getTauxReprisePasse() {
		return tauxReprisePasse;
	}

	public void setTauxReprisePasse(BigDecimal tauxReprisePasse) {
		this.tauxReprisePasse = tauxReprisePasse;
	}

	public BigDecimal getTauxResiliationNonPaiement() {
		return tauxResiliationNonPaiement;
	}

	public void setTauxResiliationNonPaiement(BigDecimal tauxResiliationNonPaiement) {
		this.tauxResiliationNonPaiement = tauxResiliationNonPaiement;
	}

	public BigDecimal getTauxAntecedentSinistre() {
		return tauxAntecedentSinistre;
	}

	public void setTauxAntecedentSinistre(BigDecimal tauxAntecedentSinistre) {
		this.tauxAntecedentSinistre = tauxAntecedentSinistre;
	}

	public Integer getNbAntecedentSinistre() {
		return nbAntecedentSinistre;
	}

	public void setNbAntecedentSinistre(Integer nbAntecedentSinistre) {
		this.nbAntecedentSinistre = nbAntecedentSinistre;
	}

	public String getCoutMaxPourNbAntecedentSinistre() {
		return coutMaxPourNbAntecedentSinistre;
	}

	public void setCoutMaxPourNbAntecedentSinistre(String coutMaxPourNbAntecedentSinistre) {
		this.coutMaxPourNbAntecedentSinistre = coutMaxPourNbAntecedentSinistre;
	}

	public Boolean getValidationCourtier() {
		return validationCourtier;
	}

	public void setValidationCourtier(Boolean validationCourtier) {
		this.validationCourtier = validationCourtier;
	}

	public Boolean getTraite() {
		return traite;
	}

	public void setTraite(Boolean traite) {
		this.traite = traite;
	}

	public String getCommentaireCourtier() {
		return commentaireCourtier;
	}

	public void setCommentaireCourtier(String commentaireCourtier) {
		this.commentaireCourtier = commentaireCourtier;
	}

	public BigDecimal getTxRegulCaHt() {
		return txRegulCaHt;
	}

	public void setTxRegulCaHt(BigDecimal txRegulCaHt) {
		this.txRegulCaHt = txRegulCaHt;
	}

	public String getCommentaireValidationYlyade() {
		return commentaireValidationYlyade;
	}

	public void setCommentaireValidationYlyade(String commentaireValidationYlyade) {
		this.commentaireValidationYlyade = commentaireValidationYlyade;
	}

	public String getCommentaireApresExpertise() {
		return commentaireApresExpertise;
	}

	public void setCommentaireApresExpertise(String commentaireApresExpertise) {
		this.commentaireApresExpertise = commentaireApresExpertise;
	}

	public String getCommentaireValidationAssureur() {
		return commentaireValidationAssureur;
	}

	public void setCommentaireValidationAssureur(String commentaireValidationAssureur) {
		this.commentaireValidationAssureur = commentaireValidationAssureur;
	}

	public Boolean getInclusionFraisDossier() {
		return inclusionFraisDossier;
	}

	public void setInclusionFraisDossier(Boolean inclusionFraisDossier) {
		this.inclusionFraisDossier = inclusionFraisDossier;
	}

	public BigDecimal getMontantDprsa() {
		return montantDprsa;
	}

	public void setMontantDprsa(BigDecimal montantDprsa) {
		this.montantDprsa = montantDprsa;
	}

	public BigDecimal getMontantFraisCompagnieSuperAssureur() {
		return montantFraisCompagnieSuperAssureur;
	}

	public void setMontantFraisCompagnieSuperAssureur(BigDecimal montantFraisCompagnieSuperAssureur) {
		this.montantFraisCompagnieSuperAssureur = montantFraisCompagnieSuperAssureur;
	}

	public Boolean getSoumisSouscription() {
		return soumisSouscription;
	}

	public void setSoumisSouscription(Boolean soumisSouscription) {
		this.soumisSouscription = soumisSouscription;
	}

	public Boolean getValidationGlobaleGedCourtier() {
		return validationGlobaleGedCourtier;
	}

	public void setValidationGlobaleGedCourtier(Boolean validationGlobaleGedCourtier) {
		this.validationGlobaleGedCourtier = validationGlobaleGedCourtier;
	}

	public Boolean getPremierPaiementEffectue() {
		return premierPaiementEffectue;
	}

	public void setPremierPaiementEffectue(Boolean premierPaiementEffectue) {
		this.premierPaiementEffectue = premierPaiementEffectue;
	}

	public BigDecimal getMontantPrimeAnnuelleHT() {
		return montantPrimeAnnuelleHT;
	}

	public void setMontantPrimeAnnuelleHT(BigDecimal montantPrimeAnnuelleHT) {
		this.montantPrimeAnnuelleHT = montantPrimeAnnuelleHT;
	}

	public BigDecimal getMontantPrimeAnnuelleTTC() {
		return montantPrimeAnnuelleTTC;
	}

	public void setMontantPrimeAnnuelleTTC(BigDecimal montantPrimeAnnuelleTTC) {
		this.montantPrimeAnnuelleTTC = montantPrimeAnnuelleTTC;
	}

	public BigDecimal getMontantPrimeEcheanceHT() {
		return montantPrimeEcheanceHT;
	}

	public void setMontantPrimeEcheanceHT(BigDecimal montantPrimeEcheanceHT) {
		this.montantPrimeEcheanceHT = montantPrimeEcheanceHT;
	}

	public BigDecimal getMontantPrimeEcheanceTTC() {
		return montantPrimeEcheanceTTC;
	}

	public void setMontantPrimeEcheanceTTC(BigDecimal montantPrimeEcheanceTTC) {
		this.montantPrimeEcheanceTTC = montantPrimeEcheanceTTC;
	}

	public BigDecimal getTarifAnnuelTotalHT() {
		return tarifAnnuelTotalHT;
	}

	public void setTarifAnnuelTotalHT(BigDecimal tarifAnnuelTotalHT) {
		this.tarifAnnuelTotalHT = tarifAnnuelTotalHT;
	}

	public BigDecimal getTarifAnnuelTotalTTC() {
		return tarifAnnuelTotalTTC;
	}

	public void setTarifAnnuelTotalTTC(BigDecimal tarifAnnuelTotalTTC) {
		this.tarifAnnuelTotalTTC = tarifAnnuelTotalTTC;
	}

	public Boolean getValidationGlobaleGedYlyade() {
		return validationGlobaleGedYlyade;
	}

	public void setValidationGlobaleGedYlyade(Boolean validationGlobaleGedYlyade) {
		this.validationGlobaleGedYlyade = validationGlobaleGedYlyade;
	}

	public Boolean getRefusDefinitifYlyade() {
		return refusDefinitifYlyade;
	}

	public void setRefusDefinitifYlyade(Boolean refusDefinitifYlyade) {
		this.refusDefinitifYlyade = refusDefinitifYlyade;
	}

	public Boolean getRefusDefinitifSuperAssureur() {
		return refusDefinitifSuperAssureur;
	}

	public void setRefusDefinitifSuperAssureur(Boolean refusDefinitifSuperAssureur) {
		this.refusDefinitifSuperAssureur = refusDefinitifSuperAssureur;
	}

	public String getCommentaireRefusDefinitifYlyade() {
		return commentaireRefusDefinitifYlyade;
	}

	public void setCommentaireRefusDefinitifYlyade(String commentaireRefusDefinitifYlyade) {
		this.commentaireRefusDefinitifYlyade = commentaireRefusDefinitifYlyade;
	}

	public String getCommentaireRefusDefinitifSuperAssureur() {
		return commentaireRefusDefinitifSuperAssureur;
	}

	public void setCommentaireRefusDefinitifSuperAssureur(String commentaireRefusDefinitifSuperAssureur) {
		this.commentaireRefusDefinitifSuperAssureur = commentaireRefusDefinitifSuperAssureur;
	}

	public Boolean getSoumisEtudeAssureur() {
		return soumisEtudeAssureur;
	}

	public void setSoumisEtudeAssureur(Boolean soumisEtudeAssureur) {
		this.soumisEtudeAssureur = soumisEtudeAssureur;
	}

	public Boolean getValidationAssureurApresetude() {
		return validationAssureurApresetude;
	}

	public void setValidationAssureurApresetude(Boolean validationAssureurApresetude) {
		this.validationAssureurApresetude = validationAssureurApresetude;
	}

	public BigDecimal getMontantTaxeTotal() {
		return montantTaxeTotal;
	}

	public void setMontantTaxeTotal(BigDecimal montantTaxeTotal) {
		this.montantTaxeTotal = montantTaxeTotal;
	}

	public BigDecimal getMontantTotalTaxes9pc() {
		return montantTotalTaxes9pc;
	}

	public void setMontantTotalTaxes9pc(BigDecimal montantTotalTaxes9pc) {
		this.montantTotalTaxes9pc = montantTotalTaxes9pc;
	}

	public BigDecimal getMontantTotalTaxes134pc() {
		return montantTotalTaxes134pc;
	}

	public void setMontantTotalTaxes134pc(BigDecimal montantTotalTaxes134pc) {
		this.montantTotalTaxes134pc = montantTotalTaxes134pc;
	}

	public BigDecimal getTarifAnnuelTtcPlusFraisFractionnement() {
		return tarifAnnuelTtcPlusFraisFractionnement;
	}

	public void setTarifAnnuelTtcPlusFraisFractionnement(BigDecimal tarifAnnuelTtcPlusFraisFractionnement) {
		this.tarifAnnuelTtcPlusFraisFractionnement = tarifAnnuelTtcPlusFraisFractionnement;
	}

	public BigDecimal getPourcentSousTraitanceCalcul() {
		return pourcentSousTraitanceCalcul;
	}

	public void setPourcentSousTraitanceCalcul(BigDecimal pourcentSousTraitanceCalcul) {
		this.pourcentSousTraitanceCalcul = pourcentSousTraitanceCalcul;
	}

	public BigDecimal getPrimeNetteYlyade() {
		return primeNetteYlyade;
	}

	public void setPrimeNetteYlyade(BigDecimal primeNetteYlyade) {
		this.primeNetteYlyade = primeNetteYlyade;
	}

	public String getCodeSiren() {
		return codeSiren;
	}

	public void setCodeSiren(String codeSiren) {
		this.codeSiren = codeSiren;
	}

	public List<TaTStatut> getTaTStatut() {
		return taTStatut;
	}

	public void setTaTStatut(List<TaTStatut> taTStatut) {
		this.taTStatut = taTStatut;
	}

	public Date getDateEnvoiChequeParCourtier() {
		return dateEnvoiChequeParCourtier;
	}

	public void setDateEnvoiChequeParCourtier(Date dateEnvoiChequeParCourtier) {
		this.dateEnvoiChequeParCourtier = dateEnvoiChequeParCourtier;
	}

	public Date getDateReceptionCheque() {
		return dateReceptionCheque;
	}

	public void setDateReceptionCheque(Date dateReceptionCheque) {
		this.dateReceptionCheque = dateReceptionCheque;
	}

	public Date getDateDepotCheque() {
		return dateDepotCheque;
	}

	public void setDateDepotCheque(Date dateDepotCheque) {
		this.dateDepotCheque = dateDepotCheque;
	}

	public Date getDateEncaissementCheque() {
		return dateEncaissementCheque;
	}

	public void setDateEncaissementCheque(Date dateEncaissementCheque) {
		this.dateEncaissementCheque = dateEncaissementCheque;
	}

	public Date getDateVirementEffectue() {
		return dateVirementEffectue;
	}

	public void setDateVirementEffectue(Date dateVirementEffectue) {
		this.dateVirementEffectue = dateVirementEffectue;
	}

	public Date getDateVirementRecu() {
		return dateVirementRecu;
	}

	public void setDateVirementRecu(Date dateVirementRecu) {
		this.dateVirementRecu = dateVirementRecu;
	}

	public BigDecimal getPrimeNetteAssureur() {
		return primeNetteAssureur;
	}

	public void setPrimeNetteAssureur(BigDecimal primeNetteAssureur) {
		this.primeNetteAssureur = primeNetteAssureur;
	}
	
	public Date getResilieNonPaiementContrat() {
		return resilieNonPaiementContrat;
	}

	public void setResilieNonPaiementContrat(Date resilieNonPaiementContrat) {
		this.resilieNonPaiementContrat = resilieNonPaiementContrat;
	}

	public Date getResilieFausseDeclarationContrat() {
		return resilieFausseDeclarationContrat;
	}

	public void setResilieFausseDeclarationContrat(Date resilieFausseDeclarationContrat) {
		this.resilieFausseDeclarationContrat = resilieFausseDeclarationContrat;
	}

	public Date getResilieEcheanceContrat() {
		return resilieEcheanceContrat;
	}

	public void setResilieEcheanceContrat(Date resilieEcheanceContrat) {
		this.resilieEcheanceContrat = resilieEcheanceContrat;
	}

	public Date getResilieAmiableContrat() {
		return resilieAmiableContrat;
	}

	public void setResilieAmiableContrat(Date resilieAmiableContrat) {
		this.resilieAmiableContrat = resilieAmiableContrat;
	}

	public Date getResilieCessationActiviteContrat() {
		return resilieCessationActiviteContrat;
	}

	public void setResilieCessationActiviteContrat(Date resilieCessationActiviteContrat) {
		this.resilieCessationActiviteContrat = resilieCessationActiviteContrat;
	}

	public Date getResilieSansEffetContrat() {
		return resilieSansEffetContrat;
	}

	public void setResilieSansEffetContrat(Date resilieSansEffetContrat) {
		this.resilieSansEffetContrat = resilieSansEffetContrat;
	}

	public Date getMisEnDemeure() {
		return misEnDemeure;
	}

	public void setMisEnDemeure(Date misEnDemeure) {
		this.misEnDemeure = misEnDemeure;
	}

	public Date getSuspenduNonPaiement() {
		return suspenduNonPaiement;
	}

	public void setSuspenduNonPaiement(Date suspenduNonPaiement) {
		this.suspenduNonPaiement = suspenduNonPaiement;
	}

	public Boolean getSuspenduAvenant() {
		return suspenduAvenant;
	}

	public void setSuspenduAvenant(Boolean suspenduAvenant) {
		this.suspenduAvenant = suspenduAvenant;
	}

	public String getLettrePjNumPolice() {
		return lettrePjNumPolice;
	}

	public void setLettrePjNumPolice(String lettrePjNumPolice) {
		this.lettrePjNumPolice = lettrePjNumPolice;
	}

//	public String getJsonDATA() {
//		return jsonDATA;
//	}
//
//	public void setJsonDATA(String jsonDATA) {
//		this.jsonDATA = jsonDATA;
//	}





}
