package fr.ylyade.courtage.model;
//package fr.ylyade.courtage.model;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.NamedQueries;
//import javax.persistence.NamedQuery;
//import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.persistence.Version;
//
//import org.hibernate.annotations.Fetch;
//import org.hibernate.annotations.FetchMode;
//
//@Entity
//@Table(name = "ta_devis_rc_pro")
//@NamedQueries(value = { 
//		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_LIGHT_ACTIF, 
//			query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDevisRcPro,d.numDevis,"
//					+ "a.idAssure,a.codeAssure,a.raisonSociale,"
//					+ "tce.idContactEntreprise,tce.nom,tce.prenom,crc.idContratRcPro,"
//					+ "crc.numPolice) "
//					+ "from TaDossierRcd d left join d.taAssure a left join a.taContactEntreprise tce left join d.taContratRcPro crc "
//					+ "order by d.numDevis"),
//		@NamedQuery(name=TaDossierRcd.QN.FIND_BY_CODE_LIGHT, 
//			query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDevisRcPro,d.numDevis,"
//				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
//				+ "tce.idContactEntreprise,tce.nom,tce.prenom,crc.idContratRcPro,"
//				+ "crc.numPolice) "
//				+ "from TaDossierRcd d left join d.taAssure a left join a.taContactEntreprise tce left join d.taContratRcPro crc "
//				+ "where d.numDevis like :numDevis order by d.numDevis"),
//		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_LIGHT_PLUS, 
//			query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDevisRcPro,d.numDevis,"
//				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
//				+ "tce.idContactEntreprise,tce.nom,tce.prenom,crc.idContratRcPro,"
//				+ "crc.numPolice) "
//				+ "from TaDossierRcd d left join d.taAssure a left join a.taContactEntreprise tce left join d.taContratRcPro crc "
//				+ " order by d.numDevis"),
//		
//		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_BY_ID_COURTIER, 
//		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDevisRcPro,d.numDevis,"
//				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
//				+ "tce.idContactEntreprise,tce.nom,tce.prenom,crc.idContratRcPro," 
//				+ "crc.numPolice) "
//			+ " from TaDossierRcd d left join d.taAssure a left join a.taContactEntreprise tce left join d.taContratRcPro crc   left join a.taCourtier c"
//			+ " where c.idCourtier = :idCourtier order by d.numDevis")
//		})
//public class TaDossierRcd implements Serializable {
//
//	private static final long serialVersionUID = 7137444485563896767L;
//	
//	public static class QN {
//		public static final String FIND_ALL_LIGHT_ACTIF = "TaDossierRcd.findAllLightActif";
//		public static final String FIND_BY_CODE_LIGHT = "TaDossierRcd.findByCodeLight";
//		public static final String FIND_ALL_LIGHT_PLUS = "TaDossierRcd.findAllLightPlus";
//		public static final String FIND_ALL_BY_ID_COURTIER = "TaDossierRcd.findAllByIdCourtier";
//	}
//	
//	private Integer idDevisRcPro;
//	
//	private String numDevis;
//	private Integer version;
//	
//	private TaAssure taAssure;
//	private TaCourtier taCourtier;
//	private TaContratRcPro taContratRcPro;
//	private List<TaDevisRcProDetail> taDevisRcProDetails;	
//	private List<TaSinistreAntecedent> taSinistreAntecedent;		
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
//	@Column(name = "id_devis_rc_pro", unique = true, nullable = false)
//	public Integer getIdDevisRcPro() {
//		return idDevisRcPro;
//	}
//
//	public void setIdDevisRcPro(Integer idDevisRcPro) {
//		this.idDevisRcPro = idDevisRcPro;
//	}
//
//	@Column(name = "num_devis")
//	public String getNumDevis() {
//		return numDevis;
//	}
//
//	public void setNumDevis(String numDevis) {
//		this.numDevis = numDevis;
//	}
//
//	@Column(name = "version")
//	public Integer getVersion() {
//		return version;
//	}
//
//	public void setVersion(Integer version) {
//		this.version = version;
//	}
//
//	//@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
//	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
//	@JoinColumn(name = "id_assure_ta_assure")
//	public TaAssure getTaAssure() {
//		return taAssure;
//	}
//
//	public void setTaAssure(TaAssure taAssure) {
//		this.taAssure = taAssure;
//	}
//
//	@OneToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "id_contrat_rc_pro_ta_contrat_rc_pro")
//	public TaContratRcPro getTaContratRcPro() {
//		return taContratRcPro;
//	}
//
//	public void setTaContratRcPro(TaContratRcPro taContratRcPro) {
//		this.taContratRcPro = taContratRcPro;
//	}
//	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "taDevisRcPro", orphanRemoval=true)
//	@Fetch(FetchMode.SUBSELECT)
//	public List<TaDevisRcProDetail> getTaDevisRcProDetails() {
//		return taDevisRcProDetails;
//	}
//
//	public void setTaDevisRcProDetails(List<TaDevisRcProDetail> taDevisRcProDetails) {
//		this.taDevisRcProDetails = taDevisRcProDetails;
//	}
//
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "taDevisRcPro", orphanRemoval=true)
//	@Fetch(FetchMode.SUBSELECT)
//	public List<TaSinistreAntecedent> getTaSinistreAntecedent() {
//		return taSinistreAntecedent;
//	}
//
//	public void setTaSinistreAntecedent(List<TaSinistreAntecedent> taSinistreAntecedent) {
//		this.taSinistreAntecedent = taSinistreAntecedent;
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
//	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
//	@JoinColumn(name = "id_courtier_ta_courtier")
//	public TaCourtier getTaCourtier() {
//		return taCourtier;
//	}
//
//	public void setTaCourtier(TaCourtier taCourtier) {
//		this.taCourtier = taCourtier;
//	}
//}
