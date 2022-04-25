package fr.ylyade.courtage.droits.model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import fr.ylyade.courtage.model.TaTUtilisateur;
//import fr.legrain.bdg.compteclient.model.TaMesFournisseurs;
import sun.misc.BASE64Encoder;

@Entity
@Table(name = "ta_utilisateur")
@NamedQueries(value = { 
		@NamedQuery(name=TaUtilisateur.QN.FIND_BY_IDENTIFIANT, query="select f from TaUtilisateur f where f.identifiant= :code")
		})
public class TaUtilisateur implements Serializable {
	
	private static final long serialVersionUID = 8501805659367719874L;
	
	public static class QN {
		public static final String FIND_BY_IDENTIFIANT = "User.findByIdentifiant";
	}
//	@OneToMany (fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "taUtilisateur")
//	private List <TaMesFournisseurs> listeMesFournisseurs;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_utilisateur")
	private Integer idUtilisateur;
	
	@Column(name="identifiant")
	private String identifiant;
	

	@Column(name="password")
	private String password;
	
	@Column(name="admin")
	private Boolean admin;
	
	@Column(name="systeme")
	private Boolean systeme;
	

	//@OneToMany(mappedBy="taUtilisateur", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ta_r_role_utilisateur",
			joinColumns = {@JoinColumn(name = "id_utilisateur_ta_utilisateur")},inverseJoinColumns = {@JoinColumn(name = "id_roles_ta_roles")})
	@Fetch(value = FetchMode.SUBSELECT)
	private List<TaRole> roles;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_utilisateur_ta_t_utilisateur")
	private TaTUtilisateur taTUtilisateur;
	
//	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
//	@JoinColumn(name = "id_entreprise")
//	private TaEntrepriseClient taEntreprise;
	
//
//	@Column(name="nom")
//	private String nom;
//	
//
//	@Column(name="prenom")
//	private String prenom;
//	
//
//	@Column(name="email")
//	private String email;
//	
//	@Column(name="autorisations")
//	private String autorisations;
	
	@Column(name="date_derniere_visite")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateDerniereVisite;
	
	@Column(name="date_creation")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreation;
	
	@Column(name="actif")
	private Boolean actif;
	
	@Column(name="quand_cree")
	@Temporal(TemporalType.TIMESTAMP)
	private Date quandCree;
	
	@Column(name="quand_modif")
	@Temporal(TemporalType.TIMESTAMP)
	private Date quandModif;
	
	@Column(name="qui_cree")
	private String quiCree;
	
	@Column(name="qui_modif")
	private String quiModif;
	
	@Column(name = "ip_acces")
	private String ipAcces;
	
	@Version
	@Column(name = "version_obj")
	private Integer versionObj;
	
	public String passwordHashSHA256_Base64(String originalPassword) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");

			BASE64Encoder enc = new sun.misc.BASE64Encoder();

			byte[] digest = md.digest(originalPassword.getBytes()); // Missing charset
			String base64 = enc.encode(digest);

			return base64;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean hasRole(String role) {
		if(roles!=null) {
			for (TaRole r : roles) {
				if(r.getLiblRole().equals(role))
					return true;
			}
		}
        return false;
    }
	
	
	
	public boolean isDev() {
    	return isDev(getIdentifiant());
    }
    
    public boolean isDev(String username) {
    	boolean isDev = false;
    	if(username!=null && !username.equals("")) {
    		if(username.equals("nicolas")
    				//|| username.equals("ppottier")
    				) {
    			isDev = true;
    		}
    	}
    	return isDev;
    }
    
    public boolean isDevLgr() {
    	return isDevLgr(getIdentifiant());
    }
    
    public boolean isDevLgr(String username) {
    	boolean isDev = false;
    	if(username!=null && !username.equals("")) {
    		if(username.equals("adminlgr") //pwdlgr
    				) {
    			isDev = true;
    		}
//    		else if(systeme!=null && admin!=null && systeme && admin) {
//    			isDev = true;
//    		}
    	}
    	return isDev;
    }
	
	
	
	public List<TaRole> getRoles() {
		return roles;
	}

	public void setRoles(List<TaRole> roles) {
		this.roles = roles;
	}

	

	public Date getQuandCree() {
		return quandCree;
	}

	public Date getQuandModif() {
		return quandModif;
	}

	public String getQuiCree() {
		return quiCree;
	}

	public String getQuiModif() {
		return quiModif;
	}

	public String getIpAcces() {
		return ipAcces;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setQuandCree(Date quanCree) {
		this.quandCree = quanCree;
	}

	public void setQuandModif(Date quandModif) {
		this.quandModif = quandModif;
	}

	public void setQuiCree(String quiCree) {
		this.quiCree = quiCree;
	}

	public void setQuiModif(String quiModif) {
		this.quiModif = quiModif;
	}

	public void setIpAcces(String ip) {
		this.ipAcces = ip;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	public Integer getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(Integer idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDateDerniereVisite() {
		return dateDerniereVisite;
	}

	public void setDateDerniereVisite(Date dateDerniereVisite) {
		this.dateDerniereVisite = dateDerniereVisite;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public Boolean getSysteme() {
		return systeme;
	}

	public void setSysteme(Boolean systeme) {
		this.systeme = systeme;
	}

	public TaTUtilisateur getTaTUtilisateur() {
		return taTUtilisateur;
	}

	public void setTaTUtilisateur(TaTUtilisateur taTUtilisateur) {
		this.taTUtilisateur = taTUtilisateur;
	}

}
