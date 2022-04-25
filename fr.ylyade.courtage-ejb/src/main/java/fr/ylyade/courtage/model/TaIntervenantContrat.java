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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "ta_intervenant_contrat")
public class TaIntervenantContrat implements Serializable {

	private static final long serialVersionUID = 3405982191513012675L;

	//--faire 2 table de relation ?
	
	private Integer idIntervenantContrat;
	private String nomEntreprise;
	private String numSiret;
	private String adresseEntreprise;
	private String assureurDecennale;
	private String numContrat;
	private String codeLot;
	private TaTLotRealise taTLotRealise;
	private TaContratDommageOuvrage taContratDommageOuvrage;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_intervenant_contrat", unique = true, nullable = false)
	public Integer getIdIntervenantContrat() {
		return idIntervenantContrat;
	}

	public void setIdIntervenantContrat(Integer idIntervenantContrat) {
		this.idIntervenantContrat = idIntervenantContrat;
	}

	@Column(name = "nom_entreprise")
	public String getNomEntreprise() {
		return nomEntreprise;
	}

	public void setNomEntreprise(String nomEntreprise) {
		this.nomEntreprise = nomEntreprise;
	}

	@Column(name = "num_siret")
	public String getNumSiret() {
		return numSiret;
	}

	public void setNumSiret(String numSiret) {
		this.numSiret = numSiret;
	}

	@Column(name = "adresse_entreprise")
	public String getAdresseEntreprise() {
		return adresseEntreprise;
	}

	public void setAdresseEntreprise(String adresseEntreprise) {
		this.adresseEntreprise = adresseEntreprise;
	}

	@Column(name = "assureur_rc_decennale")
	public String getAssureurDecennale() {
		return assureurDecennale;
	}

	public void setAssureurDecennale(String assureurDecennale) {
		this.assureurDecennale = assureurDecennale;
	}

	@Column(name = "num_contrat")
	public String getNumContrat() {
		return numContrat;
	}

	public void setNumContrat(String numContrat) {
		this.numContrat = numContrat;
	}

	@Column(name = "code_lot")
	public String getCodeLot() {
		return codeLot;
	}

	public void setCodeLot(String codeLot) {
		this.codeLot = codeLot;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_lot_realise_ta_t_lot_realise")
	public TaTLotRealise getTaTLotRealise() {
		return taTLotRealise;
	}

	public void setTaTLotRealise(TaTLotRealise taTLotRealise) {
		this.taTLotRealise = taTLotRealise;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_contrat_dommage_ouvrage_ta_contrat_dommage_ouvrage")
	public TaContratDommageOuvrage getTaContratDommageOuvrage() {
		return taContratDommageOuvrage;
	}

	public void setTaContratDommageOuvrage(TaContratDommageOuvrage taContratDommageOuvrage) {
		this.taContratDommageOuvrage = taContratDommageOuvrage;
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
