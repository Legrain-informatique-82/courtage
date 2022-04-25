package fr.ylyade.courtage.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.ylyade.courtage.model.TaContratDommageOuvrage;
import fr.ylyade.courtage.model.TaContratGfa;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaFraisImpaye;
import fr.ylyade.courtage.model.TaReglementAssurance;

public class TaEcheanceDTO extends ModelObject implements java.io.Serializable {

	
	private static final long serialVersionUID = -4307626780160757362L;

	private Integer id;
	
	private String codeEcheance;
	private String liblEcheance;
	private Date dateDebutEcheance;
	
	private Date dateEcheance;
	private BigDecimal montantEcheance;
	private BigDecimal montantEcheancePlusFrais;
	private BigDecimal majorationFrais;
	private BigDecimal avoir;
	
	private TaDossierRcd taDossierRcd;
	private TaReglementAssurance taReglementAssurance;
	private TaContratDommageOuvrage taContratDommageOuvrage;
	private TaContratGfa taContratGfa;
	
	private Integer idTReglement;
	private String codeTReglement;
	private String liblTReglement;
	
	private Integer idTEcheance;
	private String liblTEcheance;
	private String codeTEcheance;
	
	private List<TaFraisImpaye> taFraisImpaye = new ArrayList<TaFraisImpaye>();
	
	private Integer idReglementAssurance;
    private String refReglement;
    private String refExtReglement; //reference de ce paiement sur un service externe (Stripe)
    private Boolean validationManuellePaiement = false; //paiement validé par ylyade
    private Boolean validationAutomatiquePaiement = false; //paiement validé par un service externe (Stripe)
    private Boolean defautPaiement = false; //paiement refusé par la banque
    private Date dateEnvoiChequeParCourtier;
    private Date dateReceptionCheque;
    private Date dateDepotCheque;
    private Date dateEncaissementCheque;
    private Date dateReglement;
    private Date dateVirementEffectue;
    private Date dateVirementRecu;
	
	public TaEcheanceDTO() {
		
	}
	
	public TaEcheanceDTO(Integer id, Date dateEcheance, BigDecimal montantEcheance){
		this.id = id;
		this.dateEcheance = dateEcheance;
		this.montantEcheance = montantEcheance;
	}
	
	public TaEcheanceDTO(Integer id, String codeEcheance, Date dateEcheance, Date dateDebutEcheance, BigDecimal montantEcheance, BigDecimal montantEcheancePlusFrais, Integer idTReglement, String codeTReglement, 
			String liblTReglement,Integer idTEcheance, String liblTEcheance, String codeTEcheance){
		this.id = id;
		this.codeEcheance = codeEcheance;
		this.dateEcheance = dateEcheance;
		this.dateDebutEcheance = dateDebutEcheance;
		this.montantEcheance = montantEcheance;
		this.montantEcheancePlusFrais = montantEcheancePlusFrais;
		this.idTReglement = idTReglement;
		this.codeTReglement = codeTReglement;
		this.liblTReglement = liblTReglement;
		this.idTEcheance = idTEcheance;
		this.liblTEcheance = liblTEcheance;
		this.codeTEcheance = codeTEcheance;
	}
	
	public TaEcheanceDTO(Integer id, String codeEcheance, Date dateEcheance, Date dateDebutEcheance, BigDecimal montantEcheance, BigDecimal montantEcheancePlusFrais, Integer idTReglement, String codeTReglement, 
			String liblTReglement,Integer idTEcheance, String liblTEcheance, String codeTEcheance,
			Integer idReglementAssurance, String refReglement, String refExtReglement, Boolean validationManuellePaiement, Boolean validationAutomatiquePaiement,
			Boolean defautPaiement, Date dateEnvoiChequeParCourtier, Date dateReceptionCheque, Date dateDepotCheque, Date dateEncaissementCheque, Date dateReglement, Date dateVirementEffectue, Date dateVirementRecu){
		this.id = id;
		this.codeEcheance = codeEcheance;
		this.dateEcheance = dateEcheance;
		this.dateDebutEcheance = dateDebutEcheance;
		this.montantEcheance = montantEcheance;
		this.montantEcheancePlusFrais = montantEcheancePlusFrais;
		this.idTReglement = idTReglement;
		this.codeTReglement = codeTReglement;
		this.liblTReglement = liblTReglement;
		this.idTEcheance = idTEcheance;
		this.liblTEcheance = liblTEcheance;
		this.codeTEcheance = codeTEcheance;
		this.idReglementAssurance = idReglementAssurance; 
		this.refReglement = refReglement; 
		this.refExtReglement = refExtReglement; 
		this.validationManuellePaiement = validationManuellePaiement; 
		this.validationAutomatiquePaiement = validationAutomatiquePaiement;
		this.defautPaiement = defautPaiement; 
		this.dateEnvoiChequeParCourtier = dateEnvoiChequeParCourtier; 
		this.dateReceptionCheque = dateReceptionCheque; 
		this.dateDepotCheque = dateDepotCheque;
		this.dateEncaissementCheque = dateEncaissementCheque;
		this.dateReglement = dateReglement;
		this.dateVirementEffectue = dateVirementEffectue;
		this.dateVirementRecu = dateVirementRecu;
	}
	
	public TaDossierRcd getTaDossierRcd() {
		return taDossierRcd;
	}

	public void setTaDossierRcd(TaDossierRcd taDossierRcd) {
		this.taDossierRcd = taDossierRcd;
	}

	public TaReglementAssurance getTaReglementAssurance() {
		return taReglementAssurance;
	}

	public void setTaReglementAssurance(TaReglementAssurance taReglementAssurance) {
		this.taReglementAssurance = taReglementAssurance;
	}

	public TaContratDommageOuvrage getTaContratDommageOuvrage() {
		return taContratDommageOuvrage;
	}

	public void setTaContratDommageOuvrage(TaContratDommageOuvrage taContratDommageOuvrage) {
		this.taContratDommageOuvrage = taContratDommageOuvrage;
	}

	public TaContratGfa getTaContratGfa() {
		return taContratGfa;
	}

	public void setTaContratGfa(TaContratGfa taContratGfa) {
		this.taContratGfa = taContratGfa;
	}

	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public Date getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public BigDecimal getMontantEcheance() {
		return montantEcheance;
	}

	public void setMontantEcheance(BigDecimal montantEcheance) {
		this.montantEcheance = montantEcheance;
	}

	public BigDecimal getMajorationFrais() {
		return majorationFrais;
	}

	public void setMajorationFrais(BigDecimal majorationFrais) {
		this.majorationFrais = majorationFrais;
	}

	public BigDecimal getAvoir() {
		return avoir;
	}

	public void setAvoir(BigDecimal avoir) {
		this.avoir = avoir;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
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

	public Integer getIdReglementAssurance() {
		return idReglementAssurance;
	}

	public void setIdReglementAssurance(Integer idReglementAssurance) {
		this.idReglementAssurance = idReglementAssurance;
	}

	public String getRefReglement() {
		return refReglement;
	}

	public void setRefReglement(String refReglement) {
		this.refReglement = refReglement;
	}

	public String getRefExtReglement() {
		return refExtReglement;
	}

	public void setRefExtReglement(String refExtReglement) {
		this.refExtReglement = refExtReglement;
	}

	public Boolean getValidationManuellePaiement() {
		return validationManuellePaiement;
	}

	public void setValidationManuellePaiement(Boolean validationManuellePaiement) {
		this.validationManuellePaiement = validationManuellePaiement;
	}

	public Boolean getValidationAutomatiquePaiement() {
		return validationAutomatiquePaiement;
	}

	public void setValidationAutomatiquePaiement(Boolean validationAutomatiquePaiement) {
		this.validationAutomatiquePaiement = validationAutomatiquePaiement;
	}

	public Boolean getDefautPaiement() {
		return defautPaiement;
	}

	public void setDefautPaiement(Boolean defautPaiement) {
		this.defautPaiement = defautPaiement;
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

	public Date getDateReglement() {
		return dateReglement;
	}

	public void setDateReglement(Date dateReglement) {
		this.dateReglement = dateReglement;
	}

	public String getCodeEcheance() {
		return codeEcheance;
	}

	public void setCodeEcheance(String codeEcheance) {
		this.codeEcheance = codeEcheance;
	}

	public String getLiblEcheance() {
		return liblEcheance;
	}

	public void setLiblEcheance(String liblEcheance) {
		this.liblEcheance = liblEcheance;
	}

	public Date getDateDebutEcheance() {
		return dateDebutEcheance;
	}

	public void setDateDebutEcheance(Date dateDebutEcheance) {
		this.dateDebutEcheance = dateDebutEcheance;
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

	public List<TaFraisImpaye> getTaFraisImpaye() {
		return taFraisImpaye;
	}

	public void setTaFraisImpaye(List<TaFraisImpaye> taFraisImpaye) {
		this.taFraisImpaye = taFraisImpaye;
	}

	public BigDecimal getMontantEcheancePlusFrais() {
		return montantEcheancePlusFrais;
	}

	public void setMontantEcheancePlusFrais(BigDecimal montantEcheancePlusFrais) {
		this.montantEcheancePlusFrais = montantEcheancePlusFrais;
	}

	
}
