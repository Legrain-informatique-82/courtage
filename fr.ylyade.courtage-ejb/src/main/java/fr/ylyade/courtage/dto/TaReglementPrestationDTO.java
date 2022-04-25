package fr.ylyade.courtage.dto;

import java.math.BigDecimal;
import java.util.Date;

import fr.ylyade.courtage.model.TaEcheance;
import fr.ylyade.courtage.model.TaHistoriquePrestation;
import fr.ylyade.courtage.model.TaTReglement;

public class TaReglementPrestationDTO extends ModelObject implements java.io.Serializable {

	
	private static final long serialVersionUID = 8806244015900749187L;

	private Integer id;
	
	private String refReglement;
    private Boolean validationPaiement = false; //paiement validé par ylyade
    private Boolean defautPaiement = false; //paiement refusé par la banque
    
    private String codeTReglement; //-- cle étrangère
    
    private Date dateReglement;
    private BigDecimal montant;
	
	private TaEcheance taEcheance;

	private TaTReglement taTReglement;
	private TaHistoriquePrestation taHistoriquePrestation; 
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public TaHistoriquePrestation getTaHistoriquePrestation() {
		return taHistoriquePrestation;
	}

	public void setTaHistoriquePrestation(TaHistoriquePrestation taHistoriquePrestation) {
		this.taHistoriquePrestation = taHistoriquePrestation;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
