package fr.ylyade.courtage.model;

import java.io.Serializable;
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
@Table(name = "ta_attestation_competences")
public class TaAttestationCompetences implements Serializable {

	private static final long serialVersionUID = 7790922320091529024L;
	
	private Integer idAttestationCompetences;
	private String exEmployeurNom;
	private String exEmployeurAdresse;
	private String exEmployeurSiren;
	private String exEmployeurDateDebut;
	private String exEmployeurDateFin;
	private String exEmploiQualite;
	private String exEmploiActiviteExercee;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_attestation_competences", unique = true, nullable = false)
	public Integer getIdAttestationCompetences() {
		return idAttestationCompetences;
	}

	public void setIdAttestationCompetences(Integer idAttestationCompetences) {
		this.idAttestationCompetences = idAttestationCompetences;
	}

	@Column(name = "ex_employeur_nom")
	public String getExEmployeurNom() {
		return exEmployeurNom;
	}

	public void setExEmployeurNom(String exEmployeurNom) {
		this.exEmployeurNom = exEmployeurNom;
	}

	@Column(name = "ex_employeur_adresse")
	public String getExEmployeurAdresse() {
		return exEmployeurAdresse;
	}

	public void setExEmployeurAdresse(String exEmployeurAdresse) {
		this.exEmployeurAdresse = exEmployeurAdresse;
	}

	@Column(name = "ex_employeur_siren")
	public String getExEmployeurSiren() {
		return exEmployeurSiren;
	}

	public void setExEmployeurSiren(String exEmployeurSiren) {
		this.exEmployeurSiren = exEmployeurSiren;
	}

	@Column(name = "ex_employeur_date_debut")
	public String getExEmployeurDateDebut() {
		return exEmployeurDateDebut;
	}

	public void setExEmployeurDateDebut(String exEmployeurDateDebut) {
		this.exEmployeurDateDebut = exEmployeurDateDebut;
	}

	@Column(name = "ex_employeur_date_fin")
	public String getExEmployeurDateFin() {
		return exEmployeurDateFin;
	}

	public void setExEmployeurDateFin(String exEmployeurDateFin) {
		this.exEmployeurDateFin = exEmployeurDateFin;
	}

	@Column(name = "ex_emploie_qualite")
	public String getExEmploiQualite() {
		return exEmploiQualite;
	}

	public void setExEmploiQualite(String exEmploiQualite) {
		this.exEmploiQualite = exEmploiQualite;
	}

	@Column(name = "ex_emploie_activite_exerce")
	public String getExEmploiActiviteExercee() {
		return exEmploiActiviteExercee;
	}

	public void setExEmploiActiviteExercee(String exEmploiActiviteExercee) {
		this.exEmploiActiviteExercee = exEmploiActiviteExercee;
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
