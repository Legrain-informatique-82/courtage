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
@Table(name = "ta_palier_classe")
public class TaPalierClasse implements Serializable {

	private static final long serialVersionUID = -3160474170172214325L;
	
	private Integer idPalierClasse;
	private String codePalierClasse;//a rajouter dans bdd
	
	private BigDecimal palierMontantMin;
	private BigDecimal palierMontantMax;
	private BigDecimal montantPrimeBase;
	
	private TaClasse taClasse;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_palier_classe", unique = true, nullable = false)
	public Integer getIdPalierClasse() {
		return idPalierClasse;
	}

	public void setIdPalierClasse(Integer idPalierClasse) {
		this.idPalierClasse = idPalierClasse;
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
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_classe_ta_classe")
	public TaClasse getTaClasse() {
		return taClasse;
	}

	public void setTaClasse(TaClasse taClasse) {
		this.taClasse = taClasse;
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
	@Column(name = "code_palier_classe")
	public String getCodePalierClasse() {
		return codePalierClasse;
	}

	public void setCodePalierClasse(String codePalierClasse) {
		this.codePalierClasse = codePalierClasse;
	}
}
