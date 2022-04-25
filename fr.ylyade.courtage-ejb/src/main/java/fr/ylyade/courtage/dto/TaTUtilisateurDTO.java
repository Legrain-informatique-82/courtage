package fr.ylyade.courtage.dto;

public class TaTUtilisateurDTO extends ModelObject implements java.io.Serializable {

	private static final long serialVersionUID = 2255197297369840306L;

	private Integer id;
	
	private String codeTUtilisateur;
	private String liblTUtilisateur;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public String getCodeTUtilisateur() {
		return codeTUtilisateur;
	}

	public void setCodeTUtilisateur(String codeTUtilisateur) {
		this.codeTUtilisateur = codeTUtilisateur;
	}

	public String getLiblTUtilisateur() {
		return liblTUtilisateur;
	}

	public void setLiblTUtilisateur(String liblTUtilisateur) {
		this.liblTUtilisateur = liblTUtilisateur;
	}

	
}
