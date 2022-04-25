package fr.ylyade.courtage.droits.model.dto;

import fr.ylyade.courtage.dto.ModelObject;

public class TaRoleDTO extends ModelObject implements java.io.Serializable {
	
	private static final long serialVersionUID = -281580838426593009L;

	private Integer id;
	private String liblRole; 
	private String description;
	private Integer versionObj;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLiblRole() {
		return liblRole;
	}

	public void setLiblRole(String role) {
		this.liblRole = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((liblRole == null) ? 0 : liblRole.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaRoleDTO other = (TaRoleDTO) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (liblRole == null) {
			if (other.liblRole != null)
				return false;
		} else if (!liblRole.equals(other.liblRole))
			return false;
		return true;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

}
