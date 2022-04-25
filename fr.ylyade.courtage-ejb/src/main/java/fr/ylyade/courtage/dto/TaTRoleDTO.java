package fr.ylyade.courtage.dto;

public class TaTRoleDTO extends ModelObject implements java.io.Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = -4080003146872510245L;


	private Integer id;
	
	
	private String liblTRole;
	private String codeTRole;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getLiblTRole() {
		return liblTRole;
	}

	public void setLiblTRole(String liblTRole) {
		this.liblTRole = liblTRole;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public String getCodeTRole() {
		return codeTRole;
	}

	public void setCodeTRole(String codeTRole) {
		this.codeTRole = codeTRole;
	}

	
}
