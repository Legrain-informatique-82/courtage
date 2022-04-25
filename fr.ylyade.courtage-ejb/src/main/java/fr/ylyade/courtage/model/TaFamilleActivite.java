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
@Table(name = "ta_famille_activite")
public class TaFamilleActivite implements Serializable {

	private static final long serialVersionUID = -2032918765826630665L;
	
	public static final String CODE_FAMILLE_ACTIVITE_1 = "Fa1";
	public static final String CODE_FAMILLE_ACTIVITE_2 = "Fa2";
	public static final String CODE_FAMILLE_ACTIVITE_3 = "Fa3";
	public static final String CODE_FAMILLE_ACTIVITE_4 = "Fa4";
	public static final String CODE_FAMILLE_ACTIVITE_5 = "Fa5";
	
	private Integer idFamilleActivite;
	
	private String codeFamilleActivite;
	private String liblFamilleActivite;
	private Integer position;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_famille_activite", unique = true, nullable = false)
	public Integer getIdFamilleActivite() {
		return idFamilleActivite;
	}

	public void setIdFamilleActivite(Integer idFamilleActivite) {
		this.idFamilleActivite = idFamilleActivite;
	}

	@Column(name = "code_famille")
	public String getCodeFamilleActivite() {
		return codeFamilleActivite;
	}

	public void setCodeFamilleActivite(String codeFamille) {
		this.codeFamilleActivite = codeFamille;
	}

	@Column(name = "libl_famille")
	public String getLiblFamilleActivite() {
		return liblFamilleActivite;
	}

	public void setLiblFamilleActivite(String liblFamille) {
		this.liblFamilleActivite = liblFamille;
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

	@Column(name = "position")
	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
}
