package fr.ylyade.courtage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
	
@Entity
@Table(name = "ta_echeance")
@NamedQueries(value = { 
		@NamedQuery(name=TaEcheance.QN.FIND_ALL_ECHEANCE_RCD_DTO, 
			query="select new fr.ylyade.courtage.dto.TaEcheanceDTO(e.idEcheance,e.codeEcheance, e.dateEcheance, e.dateDebutEcheance, e.montantEcheance,e.montantEcheancePlusFrais,"
					+ " tr.idTReglement, tr.codeTReglement, tr.liblTReglement, "
					+ " te.idTEcheance, te.liblTEcheance, te.codeTEcheance) "
					+ " from TaEcheance e join e.taDossierRcd d left join e.taTReglement tr left join e.taTEcheance te "
					+ " where d is not null order by e.dateEcheance"),
		@NamedQuery(name=TaEcheance.QN.FIND_ALL_ECHEANCE_RCD_ID_DTO, 
				query="select new fr.ylyade.courtage.dto.TaEcheanceDTO(e.idEcheance, e.codeEcheance, e.dateEcheance, e.dateDebutEcheance, e.montantEcheance, e.montantEcheancePlusFrais,"
					+ " tr.idTReglement, tr.codeTReglement, tr.liblTReglement, "
					+ " te.idTEcheance, te.liblTEcheance, te.codeTEcheance) "
					+ " from TaEcheance e join e.taDossierRcd d left join e.taTReglement tr left join e.taTEcheance te "
					+ " where d.idDossierRcd = :idDevis order by e.dateEcheance"),
		@NamedQuery(name=TaEcheance.QN.FIND_ALL_ECHEANCE_REGLEMENT_RCD_ID_DTO, 
				query="select new fr.ylyade.courtage.dto.TaEcheanceDTO(e.idEcheance, e.codeEcheance, e.dateEcheance, e.dateDebutEcheance, e.montantEcheance, e.montantEcheancePlusFrais,"
					+ " tr.idTReglement, tr.codeTReglement, tr.liblTReglement, "
					+ " te.idTEcheance, te.liblTEcheance, te.codeTEcheance, reg.idReglementAssurance, reg.refReglement, reg.refExtReglement, reg.validationManuellePaiement, reg.validationAutomatiquePaiement," 
					+ " reg.defautPaiement, reg.dateEnvoiChequeParCourtier, reg.dateReceptionCheque, reg.dateDepotCheque, reg.dateEncaissementCheque, reg.dateReglement, reg.dateVirementEffectue, reg.dateVirementRecu) "
					+ " from TaEcheance e join e.taDossierRcd d left join e.taTReglement tr left join e.taTEcheance te left join e.taReglementAssurance reg "
					+ " where d.idDossierRcd = :idDevis order by e.dateEcheance"),
		@NamedQuery(name=TaEcheance.QN.FIND_ECHEANCE_REGLEMENT_RCD_DTO, 
		query="select new fr.ylyade.courtage.dto.TaEcheanceDTO(e.idEcheance, e.codeEcheance, e.dateEcheance, e.dateDebutEcheance, e.montantEcheance, e.montantEcheancePlusFrais,"
			+ " tr.idTReglement, tr.codeTReglement, tr.liblTReglement, "
			+ " te.idTEcheance, te.liblTEcheance, te.codeTEcheance, reg.idReglementAssurance, reg.refReglement, reg.refExtReglement, reg.validationManuellePaiement, reg.validationAutomatiquePaiement," 
			+ " reg.defautPaiement, reg.dateEnvoiChequeParCourtier, reg.dateReceptionCheque, reg.dateDepotCheque, reg.dateEncaissementCheque, reg.dateReglement, reg.dateVirementEffectue, reg.dateVirementRecu) "
			+ " from TaEcheance e join e.taDossierRcd d left join e.taTReglement tr left join e.taTEcheance te left join e.taReglementAssurance reg "
			+ " where e.idEcheance = :idEcheance"),
		
		//ENTITE
		@NamedQuery(name=TaEcheance.QN.FIND_ECHEANCE_NON_PAYER_DEPUIS_20_JOURS, 
		query="select distinct e "
			+ " from TaEcheance e left join e.taReglementAssurance r left join e.taDossierRcd d"
			+ " where d.idDossierRcd = :idDossierRcd"
			+ " and r.dateReglement is null"
			+ " and e.dateDebutEcheance < :todayMoins20"),
		
		@NamedQuery(name=TaEcheance.QN.FIND_ECHEANCE_NON_PAYER_DEPUIS_X_JOURS, 
		query="select distinct e "
			+ " from TaEcheance e left join e.taReglementAssurance r left join e.taDossierRcd d"
			+ " where d.idDossierRcd = :idDossierRcd"
			+ " and r.dateReglement is null"
			+ " and e.dateDebutEcheance < :todayMoins41"),
		

		@NamedQuery(name=TaEcheance.QN.FIND_FIRST_ECHEANCE_NON_PAYER, 
		query="select e "
			+ " from TaEcheance e left join e.taDossierRcd d"
			+ " where d.idDossierRcd = :idDossierRcd"
			+ " and e.taReglementAssurance is null order by e.dateDebutEcheance asc"),
		
		@NamedQuery(name=TaEcheance.QN.FIND_ALL_ECHEANCES_A_TERMES_DANS_EXACTEMENT_X_JOURS_BY_ID_DOSSIER, 
		query="select new fr.ylyade.courtage.dto.TaEcheanceDTO(e.idEcheance,e.codeEcheance, e.dateEcheance, e.dateDebutEcheance, e.montantEcheance,e.montantEcheancePlusFrais," 
			+ " tr.idTReglement, tr.codeTReglement, tr.liblTReglement, " 
			+ " te.idTEcheance, te.liblTEcheance, te.codeTEcheance) " 
			+ " from TaEcheance e left join e.taReglementAssurance r left join e.taDossierRcd d left join e.taTReglement tr left join e.taTEcheance te "
			+ " where r.dateReglement is null"
			+ " and d.idDossierRcd = :idDossierRcd"
			+ " and date(e.dateDebutEcheance) = date(:dateX)"
			+ " order by d.dateEffet"),
		

		
		@NamedQuery(name=TaEcheance.QN.FIND_ALL_ECHEANCE_BY_ID_DOSSIER, 
		query="select e "
			+ " from TaEcheance e  left join e.taDossierRcd d"
			+ " where d.idDossierRcd = :idDossierRcd")
		

})
//@NamedNativeQueries(value = { 
//@NamedNativeQuery(name=TaEcheance.QN.FIND_FIRST_ECHEANCE_NON_PAYER, 
//query="select distinct e "
//	+ " from ta_echeance e left join e.ta_dossierRcd d on e.id_devis_rc_pro_ta_devis_rc_pro = d.id_devis_rc_pro_detail"
//	+ " where d.id_devis_rc_pro_detail = :idDossierRcd"
//	+ " and e.id_reglement_assurance_ta_reglement_assurance is null limit 1 ")
//
//})

public class TaEcheance implements Serializable {

	private static final long serialVersionUID = -2671973326523861760L;
	
	public static class QN {
		public static final String FIND_ALL_ECHEANCE_RCD_DTO = "TaEcheance.findAllEcheanceRCDDTO";
		public static final String FIND_ALL_ECHEANCE_RCD_ID_DTO = "TaEcheance.findAllEcheanceRCDIdDTO";
		public static final String FIND_ALL_ECHEANCE_REGLEMENT_RCD_ID_DTO = "TaEcheance.findAllEcheanceReglementRCDIdDTO";
		public static final String FIND_ECHEANCE_REGLEMENT_RCD_DTO = "TaEcheance.findEcheanceReglementRCDDTO";
		
		public static final String FIND_ECHEANCE_NON_PAYER_DEPUIS_20_JOURS= "TaEcheance.findEcheanceNonPayerDepuis20Jours";
		public static final String FIND_ECHEANCE_NON_PAYER_DEPUIS_X_JOURS="TaEcheance.findEcheanceNonPayerDepuis41Jour";
		public static final String FIND_FIRST_ECHEANCE_NON_PAYER="TaEcheance.findFirstEcheanceNonPayer";
		public static final String FIND_ALL_ECHEANCE_BY_ID_DOSSIER="TaEcheance.findAllEcheanceByIdDossier";
		
		public static final String FIND_ALL_ECHEANCES_A_TERMES_DANS_EXACTEMENT_X_JOURS_BY_ID_DOSSIER = "TaEcheance.findAllEcheancesATermesDansExactementXJoursByIdDossier";
		
	}
	
	private int idEcheance;
	private String codeEcheance;
	private String liblEcheance;
	private Date dateEcheance;
	private Date dateDebutEcheance;
	private BigDecimal montantEcheance;
	private BigDecimal montantEcheancePlusFrais;
	private BigDecimal majorationFrais;
	
	private BigDecimal montantCommission;
	private BigDecimal montantSurCommission;
	private BigDecimal montantFraisDeDossier;
	
	private BigDecimal avoir;
	
//	ALTER TABLE public.ta_echeance ADD date_debut_echeance DATE_HEURE_LGR NULL;
//	ALTER TABLE public.ta_echeance ADD code_echeance DLIB50 NULL;
//	ALTER TABLE public.ta_echeance ADD libl_echeance DLIB255 NULL;

	private TaDossierRcd taDossierRcd;
	private TaReglementAssurance taReglementAssurance;
	private TaContratDommageOuvrage taContratDommageOuvrage;
	private TaContratGfa taContratGfa;
	
	private TaTReglement taTReglement;
	private TaTEcheance taTEcheance;
	
	private List<TaFraisImpaye> taFraisImpaye = new ArrayList<TaFraisImpaye>();

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_echeance", unique = true, nullable = false)
	public int getIdEcheance() {
		return idEcheance;
	}

	public void setIdEcheance(int idEcheance) {
		this.idEcheance = idEcheance;
	}

	@Column(name = "date_fin_echeance")
	public Date getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	@Column(name = "montant_echeance")
	public BigDecimal getMontantEcheance() {
		return montantEcheance;
	}

	public void setMontantEcheance(BigDecimal montantEcheance) {
		this.montantEcheance = montantEcheance;
	}

	@Column(name = "majoration_frais")
	public BigDecimal getMajorationFrais() {
		return majorationFrais;
	}

	public void setMajorationFrais(BigDecimal majorationFrais) {
		this.majorationFrais = majorationFrais;
	}

	@Column(name = "avoir")
	public BigDecimal getAvoir() {
		return avoir;
	}

	public void setAvoir(BigDecimal avoir) {
		this.avoir = avoir;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_devis_rc_pro_ta_devis_rc_pro")
	public TaDossierRcd getTaDossierRcd() {
		return taDossierRcd;
	}

	public void setTaDossierRcd(TaDossierRcd taDevisRcPro) {
		this.taDossierRcd = taDevisRcPro;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "id_reglement_assurance_ta_reglement_assurance")
	public TaReglementAssurance getTaReglementAssurance() {
		return taReglementAssurance;
	}

	public void setTaReglementAssurance(TaReglementAssurance taReglementAssurance) {
		this.taReglementAssurance = taReglementAssurance;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_contrat_dommage_ouvrage_ta_contrat_dommage_ouvrage")
	public TaContratDommageOuvrage getTaContratDommageOuvrage() {
		return taContratDommageOuvrage;
	}

	public void setTaContratDommageOuvrage(TaContratDommageOuvrage taContratDommageOuvrage) {
		this.taContratDommageOuvrage = taContratDommageOuvrage;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_contrat_gfa_ta_contrat_gfa")
	public TaContratGfa getTaContratGfa() {
		return taContratGfa;
	}

	public void setTaContratGfa(TaContratGfa taContratGfa) {
		this.taContratGfa = taContratGfa;
	}
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_reglement_ta_t_reglement")
	public TaTReglement getTaTReglement() {
		return taTReglement;
	}

	public void setTaTReglement(TaTReglement taTReglement) {
		this.taTReglement = taTReglement;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_echeance_ta_t_echeance")
	public TaTEcheance getTaTEcheance() {
		return taTEcheance;
	}

	public void setTaTEcheance(TaTEcheance taTEcheance) {
		this.taTEcheance = taTEcheance;
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

	@Column(name = "code_echeance")
	public String getCodeEcheance() {
		return codeEcheance;
	}

	public void setCodeEcheance(String codeEcheance) {
		this.codeEcheance = codeEcheance;
	}

	@Column(name = "libl_echeance")
	public String getLiblEcheance() {
		return liblEcheance;
	}

	public void setLiblEcheance(String liblEcheance) {
		this.liblEcheance = liblEcheance;
	}

	@Column(name = "date_debut_echeance")
	public Date getDateDebutEcheance() {
		return dateDebutEcheance;
	}

	public void setDateDebutEcheance(Date dateDebutEcheance) {
		this.dateDebutEcheance = dateDebutEcheance;
	}
	
	@Column(name = "montant_commission")
	public BigDecimal getMontantCommission() {
		return montantCommission;
	}

	public void setMontantCommission(BigDecimal montantCommission) {
		this.montantCommission = montantCommission;
	}

	@Column(name = "montant_sur_commission")
	public BigDecimal getMontantSurCommission() {
		return montantSurCommission;
	}

	public void setMontantSurCommission(BigDecimal montantSurCommission) {
		this.montantSurCommission = montantSurCommission;
	}

	@Column(name = "montant_frais_de_dossier")
	public BigDecimal getMontantFraisDeDossier() {
		return montantFraisDeDossier;
	}

	public void setMontantFraisDeDossier(BigDecimal montantFraisDeDossier) {
		this.montantFraisDeDossier = montantFraisDeDossier;
	}
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER, mappedBy = "taEcheance", orphanRemoval=true)
	@Fetch(FetchMode.SUBSELECT)
	public List<TaFraisImpaye> getTaFraisImpaye() {
		return taFraisImpaye;
	}

	public void setTaFraisImpaye(List<TaFraisImpaye> taFraisImpaye) {
		this.taFraisImpaye = taFraisImpaye;
	}
	@Column(name = "montant_echeance_plus_frais")
	public BigDecimal getMontantEcheancePlusFrais() {
		return montantEcheancePlusFrais;
	}

	public void setMontantEcheancePlusFrais(BigDecimal montantEcheancePlusFrais) {
		this.montantEcheancePlusFrais = montantEcheancePlusFrais;
	}
}
