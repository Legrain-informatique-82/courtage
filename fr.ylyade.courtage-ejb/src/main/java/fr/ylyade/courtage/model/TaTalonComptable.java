package fr.ylyade.courtage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "ta_talon_comptable")
public class TaTalonComptable implements Serializable {

	private static final long serialVersionUID = 6692961650660684838L;
	
	private int idTalonComptable;
	private String codePrestation;
	private String liblPrestation;
	private BigDecimal montant;
	private BigDecimal montantCommission;
	private BigDecimal montantSurCommission;
	private BigDecimal montantFraisDeDossier;
	private byte[] imgTalonComptable;
	private TaReglementAssurance taReglementAssurance; //-- table de relation ?
	private TaReglementPrestation taReglementPrestation; //-- table de relation ?
	
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_talon_comptable", unique = true, nullable = false)
	public int getIdTalonComptable() {
		return idTalonComptable;
	}

	public void setIdTalonComptable(int idActivite) {
		this.idTalonComptable = idActivite;
	}

	@Column(name = "code_prestation")
	public String getCodePrestation() {
		return codePrestation;
	}

	public void setCodePrestation(String codePrestation) {
		this.codePrestation = codePrestation;
	}

	@Column(name = "libl_prestation")
	public String getLiblPrestation() {
		return liblPrestation;
	}

	public void setLiblPrestation(String liblPrestation) {
		this.liblPrestation = liblPrestation;
	}

	@Column(name = "montant")
	public BigDecimal getMontant() {
		return montant;
	}

	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}

	@Column(name = "img_talon_comptable")
	public byte[] getImgTalonComptable() {
		return imgTalonComptable;
	}

	public void setImgTalonComptable(byte[] imgQuittance) {
		this.imgTalonComptable = imgQuittance;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_reglement_assurance_ta_reglement_assurance")
	public TaReglementAssurance getTaReglementAssurance() {
		return taReglementAssurance;
	}

	public void setTaReglementAssurance(TaReglementAssurance taReglementAssurance) {
		this.taReglementAssurance = taReglementAssurance;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_reglement_prestation_ta_reglement_prestation")
	public TaReglementPrestation getTaReglementPrestation() {
		return taReglementPrestation;
	}

	public void setTaReglementPrestation(TaReglementPrestation taReglementPrestation) {
		this.taReglementPrestation = taReglementPrestation;
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
	
	@Column(name = "nom_fichier")
	public String getNomFichier() {
		return nomFichier;
	}

	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}
	@Column(name = "taille")
	public Integer getTaille() {
		return taille;
	}

	public void setTaille(Integer taille) {
		this.taille = taille;
	}
	@Column(name = "type_mime")
	public String getTypeMime() {
		return typeMime;
	}

	public void setTypeMime(String typeMime) {
		this.typeMime = typeMime;
	}

	@Column(name = "montant_commission")
	public BigDecimal getMontantCommission() {
		return montantCommission;
	}

	public void setMontantCommission(BigDecimal montantCommission) {
		this.montantCommission = montantCommission;
	}

	@Column(name = "montant_sur_commission")
	public BigDecimal getMontantSurCommission() {
		return montantSurCommission;
	}

	public void setMontantSurCommission(BigDecimal montantSurCommission) {
		this.montantSurCommission = montantSurCommission;
	}

	@Column(name = "montant_frais_de_dossier")
	public BigDecimal getMontantFraisDeDossier() {
		return montantFraisDeDossier;
	}

	public void setMontantFraisDeDossier(BigDecimal montantFraisDeDossier) {
		this.montantFraisDeDossier = montantFraisDeDossier;
	}
}
