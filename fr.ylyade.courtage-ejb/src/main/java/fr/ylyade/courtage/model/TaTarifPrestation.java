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
@Table(name = "ta_tarif_prestation")
public class TaTarifPrestation implements Serializable {

	private static final long serialVersionUID = 2280232301968273134L;
	
	public static final String ATTESTATION_NOMINATIVE = "AtestNomin";
	public static final String ATTESTATION_NOMINATIVE_PIB = "AtestNominPIB";

	private Integer idTarifPrestation;
	private String codeTarifPrestation;
	private String liblPrestation;
	private BigDecimal montantPrestation;
	
	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tarif_prestation", unique = true, nullable = false)
	public Integer getIdTarifPrestation() {
		return idTarifPrestation;
	}

	public void setIdTarifPrestation(Integer idTarifPrestation) {
		this.idTarifPrestation = idTarifPrestation;
	}

	@Column(name = "code_tarif_prestation")
	public String getCodeTarifPrestation() {
		return codeTarifPrestation;
	}

	public void setCodeTarifPrestation(String codeTarifPrestation) {
		this.codeTarifPrestation = codeTarifPrestation;
	}

	@Column(name = "libl_prestation")
	public String getLiblPrestation() {
		return liblPrestation;
	}

	public void setLiblPrestation(String liblPrestation) {
		this.liblPrestation = liblPrestation;
	}

	@Column(name = "montant_prestation")
	public BigDecimal getMontantPrestation() {
		return montantPrestation;
	}

	public void setMontantPrestation(BigDecimal montantPrestation) {
		this.montantPrestation = montantPrestation;
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
