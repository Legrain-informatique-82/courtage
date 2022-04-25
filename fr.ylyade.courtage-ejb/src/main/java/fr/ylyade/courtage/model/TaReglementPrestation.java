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
@Table(name = "ta_reglement_prestation")
public class TaReglementPrestation implements Serializable {

	private static final long serialVersionUID = 8591352055018332844L;

	private int idReglementPrestation;

    private String refReglement;
    private Boolean validationPaiement = false; //paiement validé par ylyade
    private Boolean defautPaiement = false; //paiement refusé par la banque
//    private String codeTReglement; //-- cle étrangère
    private Date dateReglement;
    private BigDecimal montant;
	

    private Boolean validationAutomatiquePaiement = false; //paiement validé par un service externe (Stripe)
    private Date dateEnvoiChequeParCourtier;
    private Date dateReceptionCheque;
    private Date dateDepotCheque;
    private Date dateEncaissementCheque;
    private Date dateVirementEffectue;
    private Date dateVirementRecu;
    
	private TaEcheance taEcheance;
	private TaTReglement taTReglement;
	private TaHistoriquePrestation taHistoriquePrestation; //-- pas le meme type de liaison que ta_reglement_assurance ?
	
	private String refExtReglement;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_reglement_prestation", unique = true, nullable = false)
	public int getIdReglementPrestation() {
		return idReglementPrestation;
	}

	public void setIdReglementPrestation(int idReglementPrestation) {
		this.idReglementPrestation = idReglementPrestation;
	}

	@Column(name = "ref_reglement")
	public String getRefReglement() {
		return refReglement;
	}

	public void setRefReglement(String refReglement) {
		this.refReglement = refReglement;
	}

	@Column(name = "validation_paiement")
	public Boolean getValidationPaiement() {
		return validationPaiement;
	}

	public void setValidationPaiement(Boolean validationPaiement) {
		this.validationPaiement = validationPaiement;
	}

	@Column(name = "defaut_paiement")
	public Boolean getDefautPaiement() {
		return defautPaiement;
	}

	public void setDefautPaiement(Boolean defautPaiement) {
		this.defautPaiement = defautPaiement;
	}

//	@Column(name = "code_t_reglement")
//	public String getCodeTReglement() {
//		return codeTReglement;
//	}
//
//	public void setCodeTReglement(String codeTReglement) {
//		this.codeTReglement = codeTReglement;
//	}

	@Column(name = "date_prestation")
	public Date getDateReglement() {
		return dateReglement;
	}

	public void setDateReglement(Date dateReglement) {
		this.dateReglement = dateReglement;
	}

	public BigDecimal getMontant() {
		return montant;
	}

	@Column(name = "montant")
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

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_historique_prestation_ta_historique_prestation")
	public TaHistoriquePrestation getTaHistoriquePrestation() {
		return taHistoriquePrestation;
	}

	public void setTaHistoriquePrestation(TaHistoriquePrestation taHistoriquePrestation) {
		this.taHistoriquePrestation = taHistoriquePrestation;
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
	@Column(name = "date_envoi_cheque_par_courtier")
	public Date getDateEnvoiChequeParCourtier() {
		return dateEnvoiChequeParCourtier;
	}
	
	public void setDateEnvoiChequeParCourtier(Date dateEnvoiChequeParCourtier) {
		this.dateEnvoiChequeParCourtier = dateEnvoiChequeParCourtier;
	}
	@Column(name = "date_reception_cheque")
	public Date getDateReceptionCheque() {
		return dateReceptionCheque;
	}

	public void setDateReceptionCheque(Date dateReceptionCheque) {
		this.dateReceptionCheque = dateReceptionCheque;
	}
	@Column(name = "date_depot_cheque")
	public Date getDateDepotCheque() {
		return dateDepotCheque;
	}

	public void setDateDepotCheque(Date dateDepotCheque) {
		this.dateDepotCheque = dateDepotCheque;
	}
	@Column(name = "date_encaissement_cheque")
	public Date getDateEncaissementCheque() {
		return dateEncaissementCheque;
	}

	public void setDateEncaissementCheque(Date dateEncaissementCheque) {
		this.dateEncaissementCheque = dateEncaissementCheque;
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
}
