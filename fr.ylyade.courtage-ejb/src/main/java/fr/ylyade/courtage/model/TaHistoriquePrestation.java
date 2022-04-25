package fr.ylyade.courtage.model;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "ta_historique_prestation")
public class TaHistoriquePrestation implements Serializable {

	private static final long serialVersionUID = 517388619243123976L;
	
	private int idHistoriquePrestation;
	private String liblPrestation;
	private Date datePrestation;
	
	private TaDossierRcd taDossierRcd;
	private TaReglementPrestation taReglementPrestation;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_historique_prestation", unique = true, nullable = false)
	public int getIdHistoriquePrestation() {
		return idHistoriquePrestation;
	}

	public void setIdHistoriquePrestation(int idHistoriquePresentation) {
		this.idHistoriquePrestation = idHistoriquePresentation;
	}

	@Column(name = "libl_prestation")
	public String getLiblPrestation() {
		return liblPrestation;
	}

	public void setLiblPrestation(String liblPrestation) {
		this.liblPrestation = liblPrestation;
	}

	@Column(name = "date_prestation")
	public Date getDatePrestation() {
		return datePrestation;
	}

	public void setDatePrestation(Date datePrestation) {
		this.datePrestation = datePrestation;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_dossier_rcd_ta_dossier_rcd")
	public TaDossierRcd getTaDossierRcd() {
		return taDossierRcd;
	}

	public void setTaDossierRcd(TaDossierRcd taContratRcPro) {
		this.taDossierRcd = taContratRcPro;
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
}
