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
@Table(name = "ta_t_frais_impaye")
public class TaTFraisImpaye implements Serializable {

	private static final long serialVersionUID = 6975822662786962207L;
		
	public static final String ANNULATION_DOSSIER_OU_RETRACTATION = "Frais_annulation";
	public static final String FRAIS_DOSSIER_YLYADE = "Frais_ylyade_affaire_nouvelle";
	public static final String FRAIS_DOSSIER_PIB = "Frais_pib";
	public static final String FRAIS_RESILIATION = "Frais_resiliation";
	public static final String FRAIS_AVENANT = "Frais_avenant_contractuel";
	public static final String FRAIS_MIS_DEMEURE= "Frais_mis_demeure";
	public static final String FRAIS_DOSSIER_COMPAGNIE_SUPERASSUREUR = "Frais_compagnie_affaire_nouvelle";

	private Integer idTFraisImpaye;
	private String liblFactureFraisImpaye;
	private BigDecimal montant;
	private BigDecimal montantHtFraisImpaye;
	private String codeTFraisImpaye; //champs a rajouter dans la bdd

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_t_frais_impaye", unique = true, nullable = false)
	public Integer getIdTFraisImpaye() {
		return idTFraisImpaye;
	}

	public void setIdTFraisImpaye(Integer idTFraisImpaye) {
		this.idTFraisImpaye = idTFraisImpaye;
	}

	@Column(name = "libl_facture_frais_impaye")
	public String getLiblFactureFraisImpaye() {
		return liblFactureFraisImpaye;
	}

	public void setLiblFactureFraisImpaye(String liblFactureFraisImpaye) {
		this.liblFactureFraisImpaye = liblFactureFraisImpaye;
	}

	@Column(name = "montant_frais_impaye")
	public BigDecimal getMontant() {
		return montant;
	}

	public void setMontant(BigDecimal montant) {
		this.montant = montant;
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
	@Column(name = "code_frais_impaye")
	public String getCodeTFraisImpaye() {
		return codeTFraisImpaye;
	}

	public void setCodeTFraisImpaye(String codeTFraisImpaye) {
		this.codeTFraisImpaye = codeTFraisImpaye;
	}

	@Column(name = "montant_ht_frais_impaye")
	public BigDecimal getMontantHtFraisImpaye() {
		return montantHtFraisImpaye;
	}

	public void setMontantHtFraisImpaye(BigDecimal montantHtFraisImpaye) {
		this.montantHtFraisImpaye = montantHtFraisImpaye;
	}
}
