package fr.ylyade.courtage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "ta_taux_rcpro_pib")
public class TaTauxRcproPib implements Serializable {

	private static final long serialVersionUID = -7568385891331563752L;
	
	private Integer idTauxRcproPib;
	private String codeTauxRcproPib;
	private String liblTauxRcproPib;
	private String descriptionActivite;
	private BigDecimal tauxRcproPib;
	private BigDecimal primeMin;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_taux_rcpro_pib", unique = true, nullable = false)
	public Integer getIdTauxRcproPib() {
		return idTauxRcproPib;
	}

	public void setIdTauxRcproPib(Integer idTAssure) {
		this.idTauxRcproPib = idTAssure;
	}

	@Column(name = "code_taux_rcpro_pib")
	public String getCodeTauxRcproPib() {
		return codeTauxRcproPib;
	}

	public void setCodeTauxRcproPib(String codeAssure) {
		this.codeTauxRcproPib = codeAssure;
	}

	@Column(name = "libl_taux_rcpro_pib")
	public String getLiblTauxRcproPib() {
		return liblTauxRcproPib;
	}

	public void setLiblTauxRcproPib(String liblAssure) {
		this.liblTauxRcproPib = liblAssure;
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
	
	@Column(name = "taux_rcpro_pib")
	public BigDecimal getTauxRcproPib() {
		return tauxRcproPib;
	}

	public void setTauxRcproPib(BigDecimal tauxRcproPib) {
		this.tauxRcproPib = tauxRcproPib;
	}
	
	@Column(name = "description_activite")
	public String getDescriptionActivite() {
		return descriptionActivite;
	}

	public void setDescriptionActivite(String descriptionActivite) {
		this.descriptionActivite = descriptionActivite;
	}
	@Column(name = "prime_min")
	public BigDecimal getPrimeMin() {
		return primeMin;
	}

	public void setPrimeMin(BigDecimal primeMin) {
		this.primeMin = primeMin;
	}
}
