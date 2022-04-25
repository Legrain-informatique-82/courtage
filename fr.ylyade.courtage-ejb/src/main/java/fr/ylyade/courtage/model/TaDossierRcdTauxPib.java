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
@Table(name = "ta_dossier_rcd_taux_pib")
public class TaDossierRcdTauxPib implements Serializable {

	private static final long serialVersionUID = 6944782804011002119L;
	
	private int idDossierRcdTauxPib;
	
	private String codeTauxRcProPib;
	private String liblTauxRcProPib;
	private BigDecimal tauxRcProPib;
	private TaTauxRcproPib taTauxRcproPib;
	private TaDossierRcd taDossierRcd; 

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_dossier_rcd_taux_pib", unique = true, nullable = false)
	public int getIdDossierRcdTauxPib() {
		return idDossierRcdTauxPib;
	}

	public void setIdDossierRcdTauxPib(int idHistoriqueActivite) {
		this.idDossierRcdTauxPib = idHistoriqueActivite;
	}

	@Column(name = "code_taux_rcpro_pib")
	public String getCodeTauxRcProPib() {
		return codeTauxRcProPib;
	}

	public void setCodeTauxRcProPib(String activite) {
		this.codeTauxRcProPib = activite;
	}

	@Column(name = "taux_rcpro_pib")
	public BigDecimal getTauxRcProPib() {
		return tauxRcProPib;
	}

	public void setTauxRcProPib(BigDecimal franchise) {
		this.tauxRcProPib = franchise;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_taux_rcpro_pib_ta_taux_rcpro_pib")
	public TaTauxRcproPib getTaTauxRcproPib() {
		return taTauxRcproPib;
	}

	public void setTaTauxRcproPib(TaTauxRcproPib taTauxRcproPib) {
		this.taTauxRcproPib = taTauxRcproPib;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_devis_rc_pro_detail_ta_devis_rc_pro_detail")
	public TaDossierRcd getTaDossierRcd() {
		return taDossierRcd;
	}

	public void setTaDossierRcd(TaDossierRcd taDevisRcProDetail) {
		this.taDossierRcd = taDevisRcProDetail;
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

	@Column(name = "libl_taux_rcpro_pib")
	public String getLiblTauxRcProPib() {
		return liblTauxRcProPib;
	}

	public void setLiblTauxRcProPib(String liblTauxRcProPib) {
		this.liblTauxRcProPib = liblTauxRcProPib;
	}
}
