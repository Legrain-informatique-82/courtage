package fr.ylyade.courtage.droits.model.dto;

import java.util.Date;
import java.util.List;

import fr.ylyade.courtage.dto.ModelObject;

public class TaUtilisateurLoginDTO extends ModelObject implements java.io.Serializable {

	private static final long serialVersionUID = -3822222970506646502L;


	private Integer id;
	private String username;
	private String passwd;
	private List<TaRRoleUtilisateurLoginDTO> roles;
//	private TaEntrepriseClientDTO taEntreprise;
	private String nom;
	private String prenom;
	private String email;
	private Date dernierAcces;
	private Boolean actif;
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public List<TaRRoleUtilisateurLoginDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<TaRRoleUtilisateurLoginDTO> roles) {
		this.roles = roles;
	}

//	public TaEntrepriseClientDTO getTaEntreprise() {
//		return taEntreprise;
//	}
//
//	public void setTaEntreprise(TaEntrepriseClientDTO userCompany) {
//		this.taEntreprise = userCompany;
//	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDernierAcces() {
		return dernierAcces;
	}

	public void setDernierAcces(Date dernierAcces) {
		this.dernierAcces = dernierAcces;
	}

	public Integer getVersionObj() {
		return versionObj;
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

}
