package fr.ylyade.courtage.dto;

import fr.ylyade.courtage.model.TaContactEntreprise;

public class TaTelDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7587317808168145869L;

	private Integer id;
	
	private String posteTel;
	private String numeroTel;
	private TaContactEntreprise taContactEntreprise;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getPosteTel() {
		return posteTel;
	}

	public void setPosteTel(String posteTel) {
		this.posteTel = posteTel;
	}

	public String getNumeroTel() {
		return numeroTel;
	}

	public void setNumeroTel(String numeroTel) {
		this.numeroTel = numeroTel;
	}

	public TaContactEntreprise getTaContactEntreprise() {
		return taContactEntreprise;
	}

	public void setTaContactEntreprise(TaContactEntreprise taContactEntreprise) {
		this.taContactEntreprise = taContactEntreprise;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
