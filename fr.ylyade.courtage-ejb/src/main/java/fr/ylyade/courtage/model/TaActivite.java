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
@Table(name = "ta_activite")
public class TaActivite implements Serializable {

	private static final long serialVersionUID = -3171686229985481059L;

	private int idActivite;
	private String liblActivite;
	private String codeActivite;
	private String descriptionActivite;
	private Integer position;
	private Boolean actif = false;
	private Boolean commentaireObligatoire = false;
	private TaTFranchise taTFranchise;
	private TaFamilleActivite taFamilleActivite;
	private TaClasse taClasse;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;
	
	private BigDecimal minCa;
	private BigDecimal maxCa;
	private BigDecimal primeBase;
	
	

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_activite", unique = true, nullable = false)
	public int getIdActivite() {
		return idActivite;
	}

	public void setIdActivite(int idActivite) {
		this.idActivite = idActivite;
	}

	@Column(name = "libl_activite")
	public String getLiblActivite() {
		return liblActivite;
	}

	public void setLiblActivite(String liblActivite) {
		this.liblActivite = liblActivite;
	}

	@Column(name = "actif")
	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_franchise_ta_t_franchise")
	public TaTFranchise getTaTFranchise() {
		return taTFranchise;
	}

	public void setTaTFranchise(TaTFranchise franchise) {
		this.taTFranchise = franchise;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_famille_activite_ta_famille_activite")
	public TaFamilleActivite getTaFamilleActivite() {
		return taFamilleActivite;
	}

	public void setTaFamilleActivite(TaFamilleActivite taFamilleActivite) {
		this.taFamilleActivite = taFamilleActivite;
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
	@Column(name = "code_activite")
	public String getCodeActivite() {
		return codeActivite;
	}

	public void setCodeActivite(String codeActivite) {
		this.codeActivite = codeActivite;
	}

	@Column(name = "description_activite")
	public String getDescriptionActivite() {
		return descriptionActivite;
	}

	public void setDescriptionActivite(String descriptionActivite) {
		this.descriptionActivite = descriptionActivite;
	}
	@Column(name = "min_ca")
	public BigDecimal getMinCa() {
		return minCa;
	}

	public void setMinCa(BigDecimal minCa) {
		this.minCa = minCa;
	}
	@Column(name = "max_ca")
	public BigDecimal getMaxCa() {
		return maxCa;
	}

	public void setMaxCa(BigDecimal maxCa) {
		this.maxCa = maxCa;
	}
	@Column(name = "prime_base")
	public BigDecimal getPrimeBase() {
		return primeBase;
	}

	public void setPrimeBase(BigDecimal primeBase) {
		this.primeBase = primeBase;
	}

	@Column(name = "commentaire_obligatoire")
	public Boolean getCommentaireObligatoire() {
		return commentaireObligatoire;
	}

	public void setCommentaireObligatoire(Boolean commentaire_obligatoire) {
		this.commentaireObligatoire = commentaire_obligatoire;
	}
	@Column(name = "position")
	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}


	
}
