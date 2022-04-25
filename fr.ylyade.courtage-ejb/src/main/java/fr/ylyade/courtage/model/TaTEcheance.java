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
@Table(name = "ta_t_echeance")
public class TaTEcheance implements Serializable {

	private static final long serialVersionUID = -643247170280086393L;
	
	public static final String ANNUEL = "ANNUEL";
	public static final String SEMESTRIEL = "SEMESTRIEL";
	public static final String TRIMESTRIEL = "TRIMESTRIEL";
	
	private Integer idTEcheance;
	private BigDecimal tauxEcheance;
	private String liblTEcheance;
	private String codeTEcheance;//champs dans la base à creer
	

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_t_echeance", unique = true, nullable = false)
	public Integer getIdTEcheance() {
		return idTEcheance;
	}

	public void setIdTEcheance(Integer idTEcheance) {
		this.idTEcheance = idTEcheance;
	}

	@Column(name = "taux_echeance")
	public BigDecimal getTauxEcheance() {
		return tauxEcheance;
	}

	public void setTauxEcheance(BigDecimal tauxEcheance) {
		this.tauxEcheance = tauxEcheance;
	}

	@Column(name = "libl_t_echeance")
	public String getLiblTEcheance() {
		return liblTEcheance;
	}

	public void setLiblTEcheance(String liblTEcheance) {
		this.liblTEcheance = liblTEcheance;
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

	@Column(name = "code_t_echeance")
	public String getCodeTEcheance() {
		return codeTEcheance;
	}

	public void setCodeTEcheance(String codeTEcheance) {
		this.codeTEcheance = codeTEcheance;
	}
}
