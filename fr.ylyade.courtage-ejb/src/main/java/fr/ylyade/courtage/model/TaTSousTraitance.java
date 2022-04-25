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
@Table(name = "ta_t_sous_traitance")
public class TaTSousTraitance implements Serializable {

	private static final long serialVersionUID = 3975581044569720639L;
	
	public static final String ST_0_30 = "ST_0_30";
	public static final String ST_30_100 = "ST_30_100";
//	public static final String ST_31_50 = "ST_31_50";
//	public static final String ST_51_100 = "ST_51_100";
	
	private Integer idTSousTraitance;
	private String codeTSousTraitance;
	private String liblTSousTraitance; 
	private BigDecimal taux;
	private Integer baseMin;
	private Integer baseMax;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_t_sous_traitance", unique = true, nullable = false)
	public Integer getIdTSousTraitance() {
		return idTSousTraitance;
	}

	public void setIdTSousTraitance(Integer idTSousTraitance) {
		this.idTSousTraitance = idTSousTraitance;
	}

	@Column(name = "taux")
	public BigDecimal getTaux() {
		return taux;
	}

	public void setTaux(BigDecimal taux) {
		this.taux = taux;
	}

	@Column(name = "base_min")
	public Integer getBaseMin() {
		return baseMin;
	}

	public void setBaseMin(Integer baseMin) {
		this.baseMin = baseMin;
	}

	@Column(name = "base_max")
	public Integer getBaseMax() {
		return baseMax;
	}

	public void setBaseMax(Integer baseMax) {
		this.baseMax = baseMax;
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

	@Column(name = "code_t_sous_traitance")
	public String getCodeTSousTraitance() {
		return codeTSousTraitance;
	}

	public void setCodeTSousTraitance(String codeTSousTraitance) {
		this.codeTSousTraitance = codeTSousTraitance;
	}
	
	@Column(name = "libl_t_sous_traitance")
	public String getLiblTSousTraitance() {
		return liblTSousTraitance;
	}

	public void setLiblTSousTraitance(String liblTSousTraitance) {
		this.liblTSousTraitance = liblTSousTraitance;
	}
}
