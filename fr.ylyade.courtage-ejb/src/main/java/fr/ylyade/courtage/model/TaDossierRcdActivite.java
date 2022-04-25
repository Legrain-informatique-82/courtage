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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "ta_dossier_rcd_activite")
public class TaDossierRcdActivite implements Serializable {

	private static final long serialVersionUID = 6944782804011002119L;
	
	private int idDossierRcdActivite;
	
	private String activite;
	private BigDecimal franchise;
	private Integer classeAssocie;
	private TaActivite taActivite;
	private TaDossierRcd taDossierRcd; 
	
	private BigDecimal palierMontantMin;
	private BigDecimal palierMontantMax;
	private BigDecimal montantPrimeBase;
	
	private String commentaire;
	private BigDecimal pourcentExerceEntreprise;
	private BigDecimal pourcentSousTraite;
	
	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_dossier_rcd_activite", unique = true, nullable = false)
	public int getIdDossierRcdActivite() {
		return idDossierRcdActivite;
	}

	public void setIdDossierRcdActivite(int idHistoriqueActivite) {
		this.idDossierRcdActivite = idHistoriqueActivite;
	}

	@Column(name = "activite")
	public String getActivite() {
		return activite;
	}

	public void setActivite(String activite) {
		this.activite = activite;
	}

	@Column(name = "franchise")
	public BigDecimal getFranchise() {
		return franchise;
	}

	public void setFranchise(BigDecimal franchise) {
		this.franchise = franchise;
	}

	@Column(name = "classe_associe")
	public Integer getClasseAssocie() {
		return classeAssocie;
	}

	public void setClasseAssocie(Integer classeAssocie) {
		this.classeAssocie = classeAssocie;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_activite_ta_activite")
	public TaActivite getTaActivite() {
		return taActivite;
	}

	public void setTaActivite(TaActivite taActivite) {
		this.taActivite = taActivite;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_dossier_rcd_ta_dossier_rcd")
	public TaDossierRcd getTaDossierRcd() {
		return taDossierRcd;
	}

	public void setTaDossierRcd(TaDossierRcd taDevisRcProDetail) {
		this.taDossierRcd = taDevisRcProDetail;
	}

	@Column(name = "palier_montant_min")
	public BigDecimal getPalierMontantMin() {
		return palierMontantMin;
	}

	public void setPalierMontantMin(BigDecimal palierMontantMin) {
		this.palierMontantMin = palierMontantMin;
	}

	@Column(name = "palier_montant_max")
	public BigDecimal getPalierMontantMax() {
		return palierMontantMax;
	}

	public void setPalierMontantMax(BigDecimal palierMontantMax) {
		this.palierMontantMax = palierMontantMax;
	}

	@Column(name = "montant_prime_base")
	public BigDecimal getMontantPrimeBase() {
		return montantPrimeBase;
	}

	public void setMontantPrimeBase(BigDecimal montantPrimeBase) {
		this.montantPrimeBase = montantPrimeBase;
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

	@Column(name = "commentaire")
	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	@Column(name = "pourcent_exerce_par_entreprise")
	public BigDecimal getPourcentExerceEntreprise() {
		return pourcentExerceEntreprise;
	}

	public void setPourcentExerceEntreprise(BigDecimal pourcentExerceEntreprise) {
		this.pourcentExerceEntreprise = pourcentExerceEntreprise;
	}

	@Column(name = "pourcent_sous_traite")
	public BigDecimal getPourcentSousTraite() {
		return pourcentSousTraite;
	}

	public void setPourcentSousTraite(BigDecimal pourcentSousTraite) {
		this.pourcentSousTraite = pourcentSousTraite;
	}
}
