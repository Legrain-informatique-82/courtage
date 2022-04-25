package fr.ylyade.courtage.dto;

public class TaTGarantieSinistreDTO extends ModelObject implements java.io.Serializable {

	private static final long serialVersionUID = 6118159140028041457L;

	private Integer id;
	
	private Integer idTGarantieSinistre;
	private String liblTGarantieSinistre;
	private String codeTGarantieSinistre;
	
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

	public Integer getIdTGarantieSinistre() {
		return idTGarantieSinistre;
	}

	public void setIdTGarantieSinistre(Integer idTGarantieSinistre) {
		this.idTGarantieSinistre = idTGarantieSinistre;
	}

	public String getLiblTGarantieSinistre() {
		return liblTGarantieSinistre;
	}

	public void setLiblTGarantieSinistre(String liblTGarantieSinistre) {
		this.liblTGarantieSinistre = liblTGarantieSinistre;
	}

	public String getCodeTGarantieSinistre() {
		return codeTGarantieSinistre;
	}

	public void setCodeTGarantieSinistre(String codeTGarantieSinistre) {
		this.codeTGarantieSinistre = codeTGarantieSinistre;
	}
	
}
