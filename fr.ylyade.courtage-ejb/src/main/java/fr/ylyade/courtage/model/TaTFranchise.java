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
@Table(name = "ta_t_franchise")
public class TaTFranchise implements Serializable {

	private static final long serialVersionUID = -580494294214627281L;
	
	public static final String FR3000 = "FR3000";
	public static final String FR2500 = "FR2000";
	public static final String FR1500 = "FR1500";
//	public static final String FR5000 = "FR5000";
//	public static final String FR10000 = "FR10000";
	
	//PIB
	public static final String FR2000_PIB = "FR2000_PIB";
	public static final String FR5000_PIB = "FR5000_PIB";
	
	
	private int idTFranchise;
	private String codeTFranchise;
	private String liblTFranchise;
	private BigDecimal montant;
	private BigDecimal tauxMontant;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_t_franchise", unique = true, nullable = false)
	public int getIdTFranchise() {
		return idTFranchise;
	}

	public void setIdTFranchise(int idTFranchise) {
		this.idTFranchise = idTFranchise;
	}

	@Column(name = "code_t_franchise")
	public String getCodeTFranchise() {
		return codeTFranchise;
	}

	public void setCodeTFranchise(String codeTFranchise) {
		this.codeTFranchise = codeTFranchise;
	}

	@Column(name = "libl_t_franchise")
	public String getLiblTFranchise() {
		return liblTFranchise;
	}

	public void setLiblTFranchise(String liblTFranchise) {
		this.liblTFranchise = liblTFranchise;
	}

	@Column(name = "montant")
	public BigDecimal getMontant() {
		return montant;
	}

	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}

	@Column(name = "taux_montant")
	public BigDecimal getTauxMontant() {
		return tauxMontant;
	}

	public void setTauxMontant(BigDecimal tauxMontant) {
		this.tauxMontant = tauxMontant;
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
