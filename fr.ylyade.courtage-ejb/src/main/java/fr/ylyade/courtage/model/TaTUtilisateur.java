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
@Table(name = "ta_t_utilisateur")
public class TaTUtilisateur implements Serializable {

	private static final long serialVersionUID = -2568340809519823659L;
	
	public static final String TYPE_UTILISATEUR_ASSURE 			= "ASSURE";
	public static final String TYPE_UTILISATEUR_COURTIER 		= "COURTIER";
	public static final int    ID_TYPE_UTILISATEUR_COURTIER 	= 3;
	public static final String TYPE_UTILISATEUR_YLYADE 			= "YLYADE";
	public static final String TYPE_UTILISATEUR_ADMINISTRATEUR 	= "ADMINISTRATEUR";
	public static final String TYPE_UTILISATEUR_SUPER_ASSUREUR 	= "SUPERASSUREUR";
	
	private Integer idTUtilisateur;
	private String codeTUtilisateur;
	private String liblTUtilisateur;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_t_utilisateur", unique = true, nullable = false)
	public Integer getIdTUtilisateur() {
		return idTUtilisateur;
	}

	public void setIdTUtilisateur(Integer idTUtilisateur) {
		this.idTUtilisateur = idTUtilisateur;
	}

	@Column(name = "libl_t_utilisateur")
	public String getLiblTUtilisateur() {
		return liblTUtilisateur;
	}
	
	public void setLiblTUtilisateur(String libl) {
		this.liblTUtilisateur = libl;
	}

	@Column(name = "code_t_utilisateur")
	public String getCodeTUtilisateur() {
		return codeTUtilisateur;
	}

	public void setCodeTUtilisateur(String code) {
		this.codeTUtilisateur = code;
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
