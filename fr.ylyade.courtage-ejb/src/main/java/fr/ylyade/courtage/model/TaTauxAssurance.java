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
@Table(name = "ta_taux_assurance")
public class TaTauxAssurance implements Serializable {

	private static final long serialVersionUID = 1272614605448106574L;

	public static final String RESILIATION_NON_PAIEMENT = "Rnp";
	public static final String RESILISATION_FAUSSE_DECLARATION = "Rfd";
	public static final String REPRISE_DU_PASSE = "Rdp";
	public static final String TAUX_CALCUL_PRIME_BRUTE_GFA = "Gfa";
	public static final String TAUX_CALCUL_RPIME_BRUTE_DOMMAGE_OUVRAGE = "DomOuv";
	public static final String PRIME_BRUTE_MINIMUM_HT_DOMMAGE_OUVRAGE = "PmDO";
	public static final String TAXE_FISCALE_DOMMAGE_OUVRAGE ="TfDO";
	public static final String TAXE_ATTENTAT_DOMAGE_OUVRAGE ="TaDO";
	public static final String TAXE_FISCALE_RC_PRO_DECENNALE ="TfRcPro";	
	public static final String MONTANT_PROTECTION_JURIDIQUE ="Pj";
	public static final String MONTANT_DPRSA = "DPRSA";
	
	//PIB
	public static final String RESILIATION_NON_PAIEMENT_PIB = "Rnp_PIB";
	
	private Integer idTauxAssurance; 
	private String codeTauxAssurance;
	private String libelleTauxAssurance;
	private BigDecimal tauxTauxAssurance;
	private BigDecimal tauxHtTauxAssurance;
	
	private TaTAssurance taTAssurance;
			
//	private BigDecimal antecedentResilieNonPaiement;
//	private BigDecimal antecedentResilieFausseDeclaration;
//	private BigDecimal antecedentReprisePasse;
//	private BigDecimal dommageOuvrageTauxPourcent;
//	private BigDecimal gfaTauxPourcent;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_taux_assurance", unique = true, nullable = false)
	public Integer getIdTauxAssurance() {
		return idTauxAssurance;
	}

	public void setIdTauxAssurance(Integer idTauxAssurance) {
		this.idTauxAssurance = idTauxAssurance;
	}

	@Column(name = "code_taux_assurance")
	public String getCodeTauxAssurance() {
		return codeTauxAssurance;
	}

	public void setCodeTauxAssurance(String codeTauxAssurance) {
		this.codeTauxAssurance = codeTauxAssurance;
	}

	@Column(name = "libelle_taux_assurance")
	public String getLibelleTauxAssurance() {
		return libelleTauxAssurance;
	}

	public void setLibelleTauxAssurance(String libelleTauxAssurance) {
		this.libelleTauxAssurance = libelleTauxAssurance;
	}

	@Column(name = "taux_taux_assurance")
	public BigDecimal getTauxTauxAssurance() {
		return tauxTauxAssurance;
	}

	public void setTauxTauxAssurance(BigDecimal tauxTauxAssurance) {
		this.tauxTauxAssurance = tauxTauxAssurance;
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
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_assurance_ta_t_assurance")
	public TaTAssurance getTaTAssurance() {
		return taTAssurance;
	}

	public void setTaTAssurance(TaTAssurance taTAssurance) {
		this.taTAssurance = taTAssurance;
	}

	@Column(name = "taux_ht_taux_assurance")
	public BigDecimal getTauxHtTauxAssurance() {
		return tauxHtTauxAssurance;
	}

	public void setTauxHtTauxAssurance(BigDecimal tauxHtTauxAssurance) {
		this.tauxHtTauxAssurance = tauxHtTauxAssurance;
	}
}
