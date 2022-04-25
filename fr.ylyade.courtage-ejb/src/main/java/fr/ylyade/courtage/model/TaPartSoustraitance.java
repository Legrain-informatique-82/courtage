//package fr.ylyade.courtage.model;
//
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.persistence.Version;
//
//@Entity
//@Table(name = "ta_part_soustraitance")
//public class TaPartSoustraitance implements Serializable {
//
//	private static final long serialVersionUID = 6246277813295714911L;
//	
//	private Integer idPartSousTraitance;
//	private String codePartSousTraitance;
//	private String liblPartSousTraitance;
//	private BigDecimal partMin;
//	private BigDecimal partMax;
//	private BigDecimal tauxPartSousTraitance;
////	private TaTAssure taTAssure; //id_t_assure_ta_t_assure
//
//	private Integer versionObj;
//	private String quiCree;
//	private Date quandCree;
//	private String quiModif;
//	private Date quandModif;
//	private String ipAcces;
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id_part_soustraitance", unique = true, nullable = false)
//	public Integer getIdPartSousTraitance() {
//		return idPartSousTraitance;
//	}
//
//	public void setIdPartSousTraitance(Integer idTAssure) {
//		this.idPartSousTraitance = idTAssure;
//	}
//
//	@Column(name = "code_part_soustraitance")
//	public String getCodePartSousTraitance() {
//		return codePartSousTraitance;
//	}
//
//	public void setCodePartSousTraitance(String codeAssure) {
//		this.codePartSousTraitance = codeAssure;
//	}
//
//	@Column(name = "libl_part_soustraitance")
//	public String getLiblPartSousTraitance() {
//		return liblPartSousTraitance;
//	}
//
//	public void setLiblPartSousTraitance(String liblAssure) {
//		this.liblPartSousTraitance = liblAssure;
//	}
//
//	@Column(name = "qui_cree", length = 50)
//	public String getQuiCree() {
//		return this.quiCree;
//	}
//
//	public void setQuiCree(String quiCreeTCivilite) {
//		this.quiCree = quiCreeTCivilite;
//	}
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "quand_cree", length = 19)
//	public Date getQuandCree() {
//		return this.quandCree;
//	}
//
//	public void setQuandCree(Date quandCreeTCivilite) {
//		this.quandCree = quandCreeTCivilite;
//	}
//
//	@Column(name = "qui_modif", length = 50)
//	public String getQuiModif() {
//		return this.quiModif;
//	}
//
//	public void setQuiModif(String quiModifTCivilite) {
//		this.quiModif = quiModifTCivilite;
//	}
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "quand_modif", length = 19)
//	public Date getQuandModif() {
//		return this.quandModif;
//	}
//
//	public void setQuandModif(Date quandModifTCivilite) {
//		this.quandModif = quandModifTCivilite;
//	}
//
//	@Column(name = "ip_acces", length = 50)
//	public String getIpAcces() {
//		return this.ipAcces;
//	}
//
//	public void setIpAcces(String ipAcces) {
//		this.ipAcces = ipAcces;
//	}
//
//	@Version
//	@Column(name = "version_obj")
//	public Integer getVersionObj() {
//		return this.versionObj;
//	}
//
//	public void setVersionObj(Integer versionObj) {
//		this.versionObj = versionObj;
//	}
//
//	@Column(name = "part_min")
//	public BigDecimal getPartMin() {
//		return partMin;
//	}
//
//	public void setPartMin(BigDecimal partMin) {
//		this.partMin = partMin;
//	}
//
//	@Column(name = "part_max")
//	public BigDecimal getPartMax() {
//		return partMax;
//	}
//
//	public void setPartMax(BigDecimal partMax) {
//		this.partMax = partMax;
//	}
//
//	@Column(name = "taux_part_soustraitance")
//	public BigDecimal getTauxPartSousTraitance() {
//		return tauxPartSousTraitance;
//	}
//
//	public void setTauxPartSousTraitance(BigDecimal tauxPartSousTraitance) {
//		this.tauxPartSousTraitance = tauxPartSousTraitance;
//	}
//}
