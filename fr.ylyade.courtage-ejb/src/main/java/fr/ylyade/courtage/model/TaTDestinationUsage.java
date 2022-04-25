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
@Table(name = "ta_t_destination_usage")
public class TaTDestinationUsage implements Serializable {

	private static final long serialVersionUID = -8883479565387699832L;
	
	private Integer idTDestinationUsage;
	private String codeTDestinationUsage;
	private String liblTDestinationUsage;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_t_destination_usage", unique = true, nullable = false)
	public Integer getIdTDestinationUsage() {
		return idTDestinationUsage;
	}

	public void setIdTDestinationUsage(Integer idTDestinationUsage) {
		this.idTDestinationUsage = idTDestinationUsage;
	}

	@Column(name = "code_usage")
	public String getCodeTDestinationUsage() {
		return codeTDestinationUsage;
	}

	public void setCodeTDestinationUsage(String codeUsage) {
		this.codeTDestinationUsage = codeUsage;
	}

	@Column(name = "libl_usage")
	public String getLiblTDestinationUsage() {
		return liblTDestinationUsage;
	}

	public void setLiblTDestinationUsage(String liblUsage) {
		this.liblTDestinationUsage = liblUsage;
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
