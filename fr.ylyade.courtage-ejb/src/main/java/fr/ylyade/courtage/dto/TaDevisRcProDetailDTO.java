package fr.ylyade.courtage.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaTEcheance;
import fr.ylyade.courtage.model.TaTReglement;

public class TaDevisRcProDetailDTO extends ModelObject implements java.io.Serializable {

	private static final long serialVersionUID = -482293890024324492L;

	private Integer id;
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	
	private Boolean resilieNonPaiement = false;
	private Boolean resilieFausseDeclaration = false;
	private Date dateRealisation;
	private Date dateResiliation;
	// pas de motif de resiliation comme dans les autres contrat ?
	private TaDossierRcd devisDetailOrigine;
	private Integer version;
	//private Boolean dernierDevis = false; -- avec les dates ?
	private byte[] imgDevisRcPro;
	private BigDecimal montantPrime; 
	
	private String codeSousTraitance;
	private BigDecimal pourcentSoustraitance;
	
	private BigDecimal fraisRcPro; //frais du courtier
	private BigDecimal tauxCommission;
	private Boolean dejaAssurer = false; //deja assure ailleur
	private Boolean contratEnCours = false;
	
	private Integer nbSinistre5ans;	   
	private Integer nbSinistreTotal;
	private BigDecimal montantSinistre;
	
	private Boolean interventionchantierCoutMax;
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
	private Integer idTEcheance;
	private BigDecimal tauxEcheance;
	private String liblTEcheance;
	private String codeTEcheance;
	
	private String activitePrincipale;
	private BigDecimal chiffreAffaireExerciceAnterieur;
	private BigDecimal chiffreAffaireExercicePrevisionnel;
	private Integer effectifTotalExercice;
	private Integer effectifTotalExerciceAnterieur;
	
	private String codeFranchise;
	private BigDecimal franchise;
	
	private String codeEcheance; //trimestriel/semestriel
	//private String echeanceLibl; -- lien vers la table écheance ?
			
	private String assureurPrecedentRcp;
	private String assureurPrecedentRcd;
	private Date dateEffetContratPrecendentRcp;
	private Date dateEffetContratPrecendentRcd;
	private String policeContratPrecedentRcp;
	private String policeContratPrecedentRcd;
	private BigDecimal montantChantierPlusEleve;
	private Boolean interventionChantierCoutMax = false;
	private BigDecimal parPourcentCaSousTraitance;	
	private BigDecimal parPourcentCaRenovation;
	private BigDecimal parPourcentClientParticulier;
	private BigDecimal parPourcentCaPrisSousTraitance;
	private BigDecimal parPourcentCaNeuf;
	private BigDecimal parPourcentClientEntreprise;
	private BigDecimal coutGlobalSinistreRcd;
	private BigDecimal coutGlobalSinistreRcp;
			
	private Integer idContratRcProDetail;
	
	private Integer idTauxAssurance; 
	private String codeTauxAssurance;
	private BigDecimal tauxTauxAssurance;
	
	private Integer idDevisRcPro;
	private String numDevis;
	
	//////////////////////////////////////////
	private List<TaActiviteDTO> listeActivite = new ArrayList<TaActiviteDTO>();
	///////////////////////////////////////////
	
	private Integer versionObj;
	
	public TaDevisRcProDetailDTO() {
	}
	
	public TaDevisRcProDetailDTO(Integer id, Date dateEcheance) {
		this.id = id;
		this.dateEcheance = dateEcheance;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getDateRealisation() {
		return dateRealisation;
	}

	public void setDateRealisation(Date dateRealisation) {
		this.dateRealisation = dateRealisation;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public byte[] getImgDevisRcPro() {
		return imgDevisRcPro;
	}

	public void setImgDevisRcPro(byte[] imgDevisRcPro) {
		this.imgDevisRcPro = imgDevisRcPro;
	}

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

	public String getAssureurPrecedentRcp() {
		return assureurPrecedentRcp;
	}

	public void setAssureurPrecedentRcp(String assureurPrecedentRcp) {
		this.assureurPrecedentRcp = assureurPrecedentRcp;
	}

	public String getAssureurPrecedentRcd() {
		return assureurPrecedentRcd;
	}

	public void setAssureurPrecedentRcd(String assureurPrecedentRcd) {
		this.assureurPrecedentRcd = assureurPrecedentRcd;
	}

	public Date getDateEffetContratPrecendentRcp() {
		return dateEffetContratPrecendentRcp;
	}

	public void setDateEffetContratPrecendentRcp(Date dateEffetContratPrecendentRcp) {
		this.dateEffetContratPrecendentRcp = dateEffetContratPrecendentRcp;
	}

	public Date getDateEffetContratPrecendentRcd() {
		return dateEffetContratPrecendentRcd;
	}

	public void setDateEffetContratPrecendentRcd(Date dateEffetContratPrecendentRcd) {
		this.dateEffetContratPrecendentRcd = dateEffetContratPrecendentRcd;
	}

	public String getPoliceContratPrecedentRcp() {
		return policeContratPrecedentRcp;
	}

	public void setPoliceContratPrecedentRcp(String policeContratPrecedentRcp) {
		this.policeContratPrecedentRcp = policeContratPrecedentRcp;
	}

	public String getPoliceContratPrecedentRcd() {
		return policeContratPrecedentRcd;
	}

	public void setPoliceContratPrecedentRcd(String policeContratPrecedentRcd) {
		this.policeContratPrecedentRcd = policeContratPrecedentRcd;
	}

	public BigDecimal getMontantChantierPlusEleve() {
		return montantChantierPlusEleve;
	}

	public void setMontantChantierPlusEleve(BigDecimal montantChantierPlusEleve) {
		this.montantChantierPlusEleve = montantChantierPlusEleve;
	}

	public Boolean getInterventionChantierCoutMax() {
		return interventionChantierCoutMax;
	}

	public void setInterventionChantierCoutMax(Boolean interventionChantierCoutMax) {
		this.interventionChantierCoutMax = interventionChantierCoutMax;
	}

	public BigDecimal getParPourcentCaSousTraitance() {
		return parPourcentCaSousTraitance;
	}

	public void setParPourcentCaSousTraitance(BigDecimal parPourcentCaSousTraitance) {
		this.parPourcentCaSousTraitance = parPourcentCaSousTraitance;
	}

	public BigDecimal getParPourcentCaRenovation() {
		return parPourcentCaRenovation;
	}

	public void setParPourcentCaRenovation(BigDecimal parPourcentCaRenovation) {
		this.parPourcentCaRenovation = parPourcentCaRenovation;
	}

	public BigDecimal getParPourcentClientParticulier() {
		return parPourcentClientParticulier;
	}

	public void setParPourcentClientParticulier(BigDecimal parPourcentClientParticulier) {
		this.parPourcentClientParticulier = parPourcentClientParticulier;
	}

	public BigDecimal getParPourcentCaPrisSousTraitance() {
		return parPourcentCaPrisSousTraitance;
	}

	public void setParPourcentCaPrisSousTraitance(BigDecimal parPourcentCaPrisSousTraitance) {
		this.parPourcentCaPrisSousTraitance = parPourcentCaPrisSousTraitance;
	}

	public BigDecimal getParPourcentCaNeuf() {
		return parPourcentCaNeuf;
	}

	public void setParPourcentCaNeuf(BigDecimal parPourcentCaNeuf) {
		this.parPourcentCaNeuf = parPourcentCaNeuf;
	}

	public BigDecimal getParPourcentClientEntreprise() {
		return parPourcentClientEntreprise;
	}

	public void setParPourcentClientEntreprise(BigDecimal parPourcentClientEntreprise) {
		this.parPourcentClientEntreprise = parPourcentClientEntreprise;
	}

	public BigDecimal getCoutGlobalSinistreRcd() {
		return coutGlobalSinistreRcd;
	}

	public void setCoutGlobalSinistreRcd(BigDecimal coutGlobalSinistreRcd) {
		this.coutGlobalSinistreRcd = coutGlobalSinistreRcd;
	}

	public BigDecimal getCoutGlobalSinistreRcp() {
		return coutGlobalSinistreRcp;
	}

	public void setCoutGlobalSinistreRcp(BigDecimal coutGlobalSinistreRcp) {
		this.coutGlobalSinistreRcp = coutGlobalSinistreRcp;
	}

	public BigDecimal getTauxTauxAssurance() {
		return tauxTauxAssurance;
	}

	public void setTauxTauxAssurance(BigDecimal tauxTauxAssurance) {
		this.tauxTauxAssurance = tauxTauxAssurance;
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

	public List<TaActiviteDTO> getListeActivite() {
		return listeActivite;
	}

	public void setListeActivite(List<TaActiviteDTO> listeActivite) {
		this.listeActivite = listeActivite;
	}

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

	public Integer getIdDevisRcPro() {
		return idDevisRcPro;
	}

	public void setIdDevisRcPro(Integer idDevisRcPro) {
		this.idDevisRcPro = idDevisRcPro;
	}

	public String getNumDevis() {
		return numDevis;
	}

	public void setNumDevis(String numDevis) {
		this.numDevis = numDevis;
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

	public Boolean getInterventionchantierCoutMax() {
		return interventionchantierCoutMax;
	}

	public void setInterventionchantierCoutMax(Boolean interventionchantierCoutMax) {
		this.interventionchantierCoutMax = interventionchantierCoutMax;
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
}
