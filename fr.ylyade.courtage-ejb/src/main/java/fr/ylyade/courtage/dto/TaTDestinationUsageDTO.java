package fr.ylyade.courtage.dto;

public class TaTDestinationUsageDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1265056671184675826L;

	private Integer id;
	
	private String codeTDestinationUsage;
	private String liblTDestinationUsage;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeTDestinationUsage() {
		return codeTDestinationUsage;
	}

	public void setCodeTDestinationUsage(String codeTDestinationUsage) {
		this.codeTDestinationUsage = codeTDestinationUsage;
	}

	public String getLiblTDestinationUsage() {
		return liblTDestinationUsage;
	}

	public void setLiblTDestinationUsage(String liblTDestinationUsage) {
		this.liblTDestinationUsage = liblTDestinationUsage;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
