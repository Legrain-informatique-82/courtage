package fr.legrain.controle.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import fr.legrain.validator.LgrHibernateValidated;


@Entity
@Table(name = "ta_verrou_modification")
//@SequenceGenerator(name = "gen_modification", sequenceName = "num_id_modification", allocationSize = 1)
public class TaVerrouModification implements java.io.Serializable {

	private static final long serialVersionUID = -6435271282111312447L;

	
	private int idVerrouModification;
	private String entite;
	private String champ;
	private String valeur;
	
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;
	private Integer versionObj;

	public TaVerrouModification() {
	}

	public TaVerrouModification(int idModification) {
		this.idVerrouModification = idModification;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_verrou_modification", unique = true, nullable = false)
	@LgrHibernateValidated(champBd = "id_verrou_modification",table = "ta_verrou_modification", champEntite="idVerrouModification", clazz = TaVerrouModification.class)
	public int getIdVerrouModification() {
		return this.idVerrouModification;
	}

	public void setIdVerrouModification(int idTPaiement) {
		this.idVerrouModification = idTPaiement;
	}
	
	@Column(name = "entite")
	public String getEntite() {
		return entite;
	}

	public void setEntite(String table) {
		this.entite = table;
	}

	@Column(name = "champ")
	public String getChamp() {
		return champ;
	}

	public void setChamp(String champ) {
		this.champ = champ;
	}

	@Column(name = "valeur")
	public String getValeur() {
		return valeur;
	}

	public void setValeur(String contexte) {
		this.valeur = contexte;
	}
	
	@Column(name = "qui_cree", length = 50)
	public String getQuiCree() {
		return this.quiCree;
	}

	public void setQuiCree(String quiCreeTPaiement) {
		this.quiCree = quiCreeTPaiement;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "quand_cree", length = 19)
	public Date getQuandCree() {
		return this.quandCree;
	}

	public void setQuandCree(Date quandCreeTPaiement) {
		this.quandCree = quandCreeTPaiement;
	}

	@Column(name = "qui_modif", length = 50)
	public String getQuiModif() {
		return this.quiModif;
	}

	public void setQuiModif(String quiModifTPaiement) {
		this.quiModif = quiModifTPaiement;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "quand_modif", length = 19)
	public Date getQuandModif() {
		return this.quandModif;
	}

	public void setQuandModif(Date quandModifTPaiement) {
		this.quandModif = quandModifTPaiement;
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

//	@PrePersist
//	@PreUpdate
	public void beforePost ()throws Exception{
//		this.setCodeTPaiement(codeTPaiement.toUpperCase());
	}

}
