package fr.ylyade.courtage.droits.model.dto;

import fr.ylyade.courtage.dto.ModelObject;

public class TaAuthViewDTO extends ModelObject implements java.io.Serializable {
	

	private static final long serialVersionUID = 1666467105013716474L;

	private Integer id;
	private TaRoleDTO role; 
	private String url;
	private Integer versionObj;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TaRoleDTO getRole() {
		return role;
	}

	public void setRole(TaRoleDTO role) {
		this.role = role;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

}
