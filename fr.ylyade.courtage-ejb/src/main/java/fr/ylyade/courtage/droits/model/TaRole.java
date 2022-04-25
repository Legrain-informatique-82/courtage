package fr.ylyade.courtage.droits.model;

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
@Table(name = "ta_roles")
public class TaRole implements Serializable {
	
	private static final long serialVersionUID = 2058493601328154001L;
	
	public static final String ROLE_ADMIN = "admin";
//	public static final String ROLE_UTILISATEUR = "utilisateur";
//	public static final String ROLE_MANAGER = "manager";
//	public static final String ROLE_VENDEUR = "vendeur";
//	public static final String ROLE_STOCKEUR = "stockeur";
	
//	 id_roles            SERIAL NOT NULL ,
//	    libl_role           dlib100,
//	    id_t_role_ta_t_role did4,

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_roles")
	private Integer idRoles;
	
	@Column(name = "libl_role")
	private String liblRole; 
	
	//private Integer idTRole; //-- cle etrangère
	
	@Column(name="description")
	private String description;
	
//	@Column(name="autorisations")
//	private String autorisations;
	
	@Column(name="quand_cree")
	@Temporal(TemporalType.TIMESTAMP)
	private Date quandCree;
	
	@Column(name="quand_modif")
	@Temporal(TemporalType.TIMESTAMP)
	private Date quandModif;
	
	@Column(name="qui_cree")
	private Integer quiCree;
	
	@Column(name="qui_modif")
	private Integer quiModif;
	
	@Column(name = "ip_acces")
	private String ipAcces;
	
	@Version
	@Column(name = "version_obj")
	private Integer versionObj;

	public int getIdRoles() {
		return idRoles;
	}

	public void setIdRoles(int idRoles) {
		this.idRoles = idRoles;
	}

	
	
	public void setIdRoles(Integer idRoles) {
		this.idRoles = idRoles;
	}

	public String getLiblRole() {
		return liblRole;
	}

	public void setLiblRole(String liblRole) {
		this.liblRole = liblRole;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getQuandCree() {
		return quandCree;
	}

	public void setQuandCree(Date quandCree) {
		this.quandCree = quandCree;
	}

	public Date getQuandModif() {
		return quandModif;
	}

	public void setQuandModif(Date quandModif) {
		this.quandModif = quandModif;
	}

	public Integer getQuiCree() {
		return quiCree;
	}

	public void setQuiCree(Integer quiCree) {
		this.quiCree = quiCree;
	}

	public Integer getQuiModif() {
		return quiModif;
	}

	public void setQuiModif(Integer quiModif) {
		this.quiModif = quiModif;
	}

	public String getIpAcces() {
		return ipAcces;
	}

	public void setIpAcces(String ipAcces) {
		this.ipAcces = ipAcces;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
