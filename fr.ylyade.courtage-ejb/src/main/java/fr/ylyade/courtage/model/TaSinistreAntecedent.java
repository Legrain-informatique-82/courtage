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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "ta_sinistre_antecedent")
public class TaSinistreAntecedent implements Serializable {

	private static final long serialVersionUID = 7960692534113447579L;
	
	private Integer idSinistreAntecedent;
	private Date dateSinistre;
	private String liblSinistre;
	private BigDecimal montantSinistre;
	private TaDossierRcd taDossierRcd;
	
	private Integer anneeDeclaration;
	private Integer nbrSinistres;
	private String natureDommages;
	private BigDecimal partResponsabilite;
	private TaTGarantieSinistre taTGarantieSinistre;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_sinistre_antecedent", unique = true, nullable = false)
	public Integer getIdSinistreAntecedent() {
		return idSinistreAntecedent;
	}

	public void setIdSinistreAntecedent(Integer idSinistreAntecedent) {
		this.idSinistreAntecedent = idSinistreAntecedent;
	}

	@Column(name = "date_sinistre_old")
	public Date getDateSinistre() {
		return dateSinistre;
	}

	public void setDateSinistre(Date dateSinistre) {
		this.dateSinistre = dateSinistre;
	}

	@Column(name = "libl_sinistre_old")
	public String getLiblSinistre() {
		return liblSinistre;
	}

	public void setLiblSinistre(String liblSinistre) {
		this.liblSinistre = liblSinistre;
	}
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_devis_rc_pro_ta_devis_rc_pro")
	public TaDossierRcd getTaDossierRcd() {
		return taDossierRcd;
	}

	public void setTaDossierRcd(TaDossierRcd taDevisRcPro) {
		this.taDossierRcd = taDevisRcPro;
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

	@Column(name = "montant_sinistre")
	public BigDecimal getMontantSinistre() {
		return montantSinistre;
	}

	public void setMontantSinistre(BigDecimal montantSinistre) {
		this.montantSinistre = montantSinistre;
	}

	@Column(name = "nbr_sinistres")
	public Integer getNbrSinistres() {
		return nbrSinistres;
	}

	public void setNbrSinistres(Integer nbr_sinistres) {
		this.nbrSinistres = nbr_sinistres;
	}

	@Column(name = "nature_dommages")
	public String getNatureDommages() {
		return natureDommages;
	}

	public void setNatureDommages(String nature_dommages) {
		this.natureDommages = nature_dommages;
	}

	@Column(name = "part_responsabilite")
	public BigDecimal getPartResponsabilite() {
		return partResponsabilite;
	}

	public void setPartResponsabilite(BigDecimal part_responsabilite) {
		this.partResponsabilite = part_responsabilite;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_type_garantie_sinistre")
	public TaTGarantieSinistre getTaTGarantieSinistre() {
		return taTGarantieSinistre;
	}

	public void setTaTGarantieSinistre(TaTGarantieSinistre id_type_garantie_sinistre) {
		this.taTGarantieSinistre = id_type_garantie_sinistre;
	}

	@Column(name = "annee_declaration")
	public Integer getAnneeDeclaration() {
		return anneeDeclaration;
	}

	public void setAnneeDeclaration(Integer annee_declaration) {
		this.anneeDeclaration = annee_declaration;
	}
	
	@PrePersist
	@PreUpdate
	public void beforePost ()throws Exception{
		if(natureDommages != null) {
			  this.setNatureDommages(natureDommages.toUpperCase());
		}

	}
}
