package fr.ylyade.courtage.dto;

public class TaFamilleActiviteDTO extends ModelObject implements java.io.Serializable {

	private static final long serialVersionUID = 6052053122614959647L;

	private Integer id;
	
	private String codeFamilleActivite;
	private String liblFamilleActivite;
	private Integer position;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeFamilleActivite() {
		return codeFamilleActivite;
	}

	public void setCodeFamilleActivite(String codeFamilleActivite) {
		this.codeFamilleActivite = codeFamilleActivite;
	}

	public String getLiblFamilleActivite() {
		return liblFamilleActivite;
	}

	public void setLiblFamilleActivite(String liblFamilleActivite) {
		this.liblFamilleActivite = liblFamilleActivite;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	
}
