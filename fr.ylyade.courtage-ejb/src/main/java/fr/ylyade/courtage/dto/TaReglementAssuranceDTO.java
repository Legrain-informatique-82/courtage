package fr.ylyade.courtage.dto;

import java.math.BigDecimal;
import java.util.Date;

import fr.ylyade.courtage.model.TaEcheance;
import fr.ylyade.courtage.model.TaQuittance;
import fr.ylyade.courtage.model.TaTReglement;

public class TaReglementAssuranceDTO extends ModelObject implements java.io.Serializable {

	
	private static final long serialVersionUID = 4061167634580750766L;

	private Integer id;
	
	private String refReglement;
    private Boolean validationPaiement = false; //paiement validé par ylyade
    private Boolean defautPaiement = false; //paiement refusé par la banque
    private String codeTReglement; //-- cle étrangère
    private Date dateReglement;
    private Date dateVirementEffectue;
    private Date dateVirementRecu;
    private BigDecimal montant;
    
    private String codeDossier;
    
    private String lettrePjNumPolice;
    private Integer numeroAvenant;
	
	private TaEcheance taEcheance;
	private TaTReglement taTReglement;
	private TaQuittance taQuittance;

	
	private Integer versionObj;
	
	
	public TaReglementAssuranceDTO() {
	}
	
	
	public TaReglementAssuranceDTO(String refReglement,
     Boolean defautPaiement,
     String codeTReglement,
     Date dateReglement,
     Date dateVirementEffectue,
     Date dateVirementRecu,
     BigDecimal montant,
     String codeDossier,
     String lettrePjNumPolice,
     Integer numeroAvenant,
     TaEcheance taEcheance) {
		
		this.refReglement = refReglement;
		//this.validationPaiement = validationPaiement;
		this.defautPaiement = defautPaiement;
		this.codeTReglement = codeTReglement;
		this.dateReglement = dateReglement;
		this.dateVirementEffectue = dateVirementEffectue;
		this.dateVirementRecu = dateVirementRecu;
	    this.montant = montant;
	    this.codeDossier = codeDossier;
	    this.lettrePjNumPolice = lettrePjNumPolice;
	    this.numeroAvenant = numeroAvenant;
	    this.taEcheance = taEcheance;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String numDossierFull() {
		String numDossierFull = "";
		if(codeDossier != null) {
			numDossierFull = codeDossier;
			if(lettrePjNumPolice != null) {
				numDossierFull+= lettrePjNumPolice;
				if(numeroAvenant != null) {
					numDossierFull+= " - "+numeroAvenant;
				}
			}
		}
		return numDossierFull;
	}

	public String getRefReglement() {
		return refReglement;
	}

	public void setRefReglement(String refReglement) {
		this.refReglement = refReglement;
	}

	public Boolean getValidationPaiement() {
		return validationPaiement;
	}

	public void setValidationPaiement(Boolean validationPaiement) {
		this.validationPaiement = validationPaiement;
	}

	public Boolean getDefautPaiement() {
		return defautPaiement;
	}

	public void setDefautPaiement(Boolean defautPaiement) {
		this.defautPaiement = defautPaiement;
	}

	public String getCodeTReglement() {
		return codeTReglement;
	}

	public void setCodeTReglement(String codeTReglement) {
		this.codeTReglement = codeTReglement;
	}

	public Date getDateReglement() {
		return dateReglement;
	}

	public void setDateReglement(Date dateReglement) {
		this.dateReglement = dateReglement;
	}

	public BigDecimal getMontant() {
		return montant;
	}

	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}

	public TaEcheance getTaEcheance() {
		return taEcheance;
	}

	public void setTaEcheance(TaEcheance taEcheance) {
		this.taEcheance = taEcheance;
	}

	public TaTReglement getTaTReglement() {
		return taTReglement;
	}

	public void setTaTReglement(TaTReglement taTReglement) {
		this.taTReglement = taTReglement;
	}

	public TaQuittance getTaQuittance() {
		return taQuittance;
	}

	public void setTaQuittance(TaQuittance taQuittance) {
		this.taQuittance = taQuittance;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
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

	public String getCodeDossier() {
		return codeDossier;
	}

	public void setCodeDossier(String codeDossier) {
		this.codeDossier = codeDossier;
	}

	public String getLettrePjNumPolice() {
		return lettrePjNumPolice;
	}

	public void setLettrePjNumPolice(String lettrePjNumPolice) {
		this.lettrePjNumPolice = lettrePjNumPolice;
	}

	public Integer getNumeroAvenant() {
		return numeroAvenant;
	}

	public void setNumeroAvenant(Integer numeroAvenant) {
		this.numeroAvenant = numeroAvenant;
	}

	
}
