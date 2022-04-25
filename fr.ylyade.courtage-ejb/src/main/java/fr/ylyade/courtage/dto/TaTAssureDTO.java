package fr.ylyade.courtage.dto;

public class TaTAssureDTO extends ModelObject implements java.io.Serializable {

	private static final long serialVersionUID = -1027899011572912085L;

	private Integer id;
	
	private String codeTAssure;
	private String liblTAssure;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeTAssure() {
		return codeTAssure;
	}

	public void setCodeTAssure(String codeTAssure) {
		this.codeTAssure = codeTAssure;
	}

	public String getLiblTAssure() {
		return liblTAssure;
	}

	public void setLiblTAssure(String liblTAssure) {
		this.liblTAssure = liblTAssure;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
