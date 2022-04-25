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
@Table(name = "ta_t_assure")
public class TaTAssure implements Serializable {

	private static final long serialVersionUID = -3211707702735063750L;
	
	public static final String ENTREPRISE_DU_BATIMENT = "ENTREPRISE DU BATIMENT";
	public static final String PIB = "PIB";
	public static final String PISCINE = "PISCINE";
	
	private Integer idTAssure;
	private String codeTAssure;
	private String liblTAssure;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_t_assure", unique = true, nullable = false)
	public Integer getIdTAssure() {
		return idTAssure;
	}

	public void setIdTAssure(Integer idTAssure) {
		this.idTAssure = idTAssure;
	}

	@Column(name = "code_assure")
	public String getCodeTAssure() {
		return codeTAssure;
	}

	public void setCodeTAssure(String codeAssure) {
		this.codeTAssure = codeAssure;
	}

	@Column(name = "libl_assure")
	public String getLiblTAssure() {
		return liblTAssure;
	}

	public void setLiblTAssure(String liblAssure) {
		this.liblTAssure = liblAssure;
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
