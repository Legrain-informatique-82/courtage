package fr.ylyade.courtage.dto;

public class TaTLotRealiseDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8735970901654355386L;

	private Integer id;
	
	private String codeLotRealise;
	private String liblLotRealise;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getCodeLotRealise() {
		return codeLotRealise;
	}

	public void setCodeLotRealise(String codeLotRealise) {
		this.codeLotRealise = codeLotRealise;
	}

	public String getLiblLotRealise() {
		return liblLotRealise;
	}

	public void setLiblLotRealise(String liblLotRealise) {
		this.liblLotRealise = liblLotRealise;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
