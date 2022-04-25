package fr.ylyade.courtage.model;

import java.io.Serializable;
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
@Table(name = "ta_t_courtier")
public class TaTCourtier implements Serializable {

	private static final long serialVersionUID = -3642755195986269327L;
	
	public static final String AGENT_ASSURANCE = "Pr1";
	public static final String COURTIER_ASSURANCE = "Pr2";
	
	private Integer idTCourtier;
	private String codeTCourtier;
	private String liblTCourtier;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_t_courtier", unique = true, nullable = false)
	public Integer getIdTCourtier() {
		return idTCourtier;
	}

	public void setIdTCourtier(Integer idTCourtier) {
		this.idTCourtier = idTCourtier;
	}

	@Column(name = "code")
	public String getCodeTCourtier() {
		return codeTCourtier;
	}

	public void setCodeTCourtier(String code) {
		this.codeTCourtier = code;
	}

	@Column(name = "libl_courtier")
	public String getLiblTCourtier() {
		return liblTCourtier;
	}

	public void setLiblTCourtier(String liblTCourtier) {
		this.liblTCourtier = liblTCourtier;
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
}
