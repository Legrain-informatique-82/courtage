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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "ta_reglement_assurance")
public class TaReglementAssurance implements Serializable {

	private static final long serialVersionUID = 4372298064965784504L;
	
	private int idReglementAssurance;

    private String refReglement;
    private String refExtReglement; //reference de ce paiement sur un service externe (Stripe)
    private String numCheque;
    private Boolean validationManuellePaiement = false; //paiement validé par ylyade
    private Boolean validationAutomatiquePaiement = false; //paiement validé par un service externe (Stripe)
    private Boolean defautPaiement = false; //paiement refusé par la banque
    private Date dateEnvoiChequeParCourtier;
    private Date dateReceptionCheque;
    private Date dateDepotCheque;
    private Date dateEncaissementCheque;
    private Date dateVirementEffectue;
    private Date dateVirementRecu;
    private String codeTReglement; //-- cle étrangère
    private Date dateReglement;
    private BigDecimal montant;
	
	private TaEcheance taEcheance;
	private TaTReglement taTReglement;
	private TaQuittance taQuittance;
	private TaTalonComptable taTalonComptable;
	
//	private TaDossierRcd taDossierRcd;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_reglement_assurance", unique = true, nullable = false)
	public int getIdReglementAssurance() {
		return idReglementAssurance;
	}

	public void setIdReglementAssurance(int idReglementAssurance) {
		this.idReglementAssurance = idReglementAssurance;
	}

	@Column(name = "ref_reglement")
	public String getRefReglement() {
		return refReglement;
	}

	public void setRefReglement(String refReglement) {
		this.refReglement = refReglement;
	}

	@Column(name = "validation_manuelle_paiement")
	public Boolean getValidationManuellePaiement() {
		return validationManuellePaiement;
	}

	public void setValidationManuellePaiement(Boolean validationPaiement) {
		this.validationManuellePaiement = validationPaiement;
	}

	@Column(name = "defaut_paiement")
	public Boolean getDefautPaiement() {
		return defautPaiement;
	}

	public void setDefautPaiement(Boolean defautPaiement) {
		this.defautPaiement = defautPaiement;
	}

	@Column(name = "code_t_reglement")
	public String getCodeTReglement() {
		return codeTReglement;
	}

	public void setCodeTReglement(String codeTReglement) {
		this.codeTReglement = codeTReglement;
	}

	@Column(name = "date_reglement")
	public Date getDateReglement() {
		return dateReglement;
	}

	public void setDateReglement(Date dateReglement) {
		this.dateReglement = dateReglement;
	}

	@Column(name = "montant")
	public BigDecimal getMontant() {
		return montant;
	}

	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_echeance_ta_echeance")
	public TaEcheance getTaEcheance() {
		return taEcheance;
	}

	public void setTaEcheance(TaEcheance taEcheance) {
		this.taEcheance = taEcheance;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_reglement_ta_t_reglement")
	public TaTReglement getTaTReglement() {
		return taTReglement;
	}

	public void setTaTReglement(TaTReglement taTReglement) {
		this.taTReglement = taTReglement;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "id_quittance_ta_quittance")
	public TaQuittance getTaQuittance() {
		return taQuittance;
	}

	public void setTaQuittance(TaQuittance taQuittance) {
		this.taQuittance = taQuittance;
	}
	
	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "id_talon_comptable_ta_talon_comptable")
	public TaTalonComptable getTaTalonComptable() {
		return taTalonComptable;
	}

	public void setTaTalonComptable(TaTalonComptable taTalonComptable) {
		this.taTalonComptable = taTalonComptable;
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

	@Column(name = "ref_ext_reglement")
	public String getRefExtReglement() {
		return refExtReglement;
	}

	public void setRefExtReglement(String refExtReglement) {
		this.refExtReglement = refExtReglement;
	}

	@Column(name = "validation_automatique_paiement")
	public Boolean getValidationAutomatiquePaiement() {
		return validationAutomatiquePaiement;
	}

	public void setValidationAutomatiquePaiement(Boolean validationAutomatiquePaiement) {
		this.validationAutomatiquePaiement = validationAutomatiquePaiement;
	}

	@Column(name = "date_reception_cheque")
	public Date getDateReceptionCheque() {
		return dateReceptionCheque;
	}

	public void setDateReceptionCheque(Date dateReceptionCheque) {
		this.dateReceptionCheque = dateReceptionCheque;
	}

	@Column(name = "date_encaissement_cheque")
	public Date getDateEncaissementCheque() {
		return dateEncaissementCheque;
	}

	public void setDateEncaissementCheque(Date dateEncaissementCheque) {
		this.dateEncaissementCheque = dateEncaissementCheque;
	}

	@Column(name = "date_envoi_cheque_par_courtier")
	public Date getDateEnvoiChequeParCourtier() {
		return dateEnvoiChequeParCourtier;
	}

	public void setDateEnvoiChequeParCourtier(Date dateEnvoiChequeParCourtier) {
		this.dateEnvoiChequeParCourtier = dateEnvoiChequeParCourtier;
	}

	@Column(name = "date_depot_cheque")
	public Date getDateDepotCheque() {
		return dateDepotCheque;
	}

	public void setDateDepotCheque(Date dateDepotCheque) {
		this.dateDepotCheque = dateDepotCheque;
	}

	@Column(name = "num_cheque")
	public String getNumCheque() {
		return numCheque;
	}

	public void setNumCheque(String numCheque) {
		this.numCheque = numCheque;
	}
	@Column(name = "date_virement_effectue")
	public Date getDateVirementEffectue() {
		return dateVirementEffectue;
	}

	public void setDateVirementEffectue(Date dateVirementEffectue) {
		this.dateVirementEffectue = dateVirementEffectue;
	}
	@Column(name = "date_virement_recu")
	public Date getDateVirementRecu() {
		return dateVirementRecu;
	}

	public void setDateVirementRecu(Date dateVirementRecu) {
		this.dateVirementRecu = dateVirementRecu;
	}
//	@OneToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "id_echeance_ta_echeance")
//	public TaDossierRcd getTaDossierRcd() {
//		return taDossierRcd;
//	}
//
//	public void setTaDossierRcd(TaDossierRcd taDossierRcd) {
//		this.taDossierRcd = taDossierRcd;
//	}

}
