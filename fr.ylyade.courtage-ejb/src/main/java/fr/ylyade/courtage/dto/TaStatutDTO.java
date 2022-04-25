package fr.ylyade.courtage.dto;

import fr.ylyade.courtage.model.TaTStatut;

public class TaStatutDTO extends ModelObject implements java.io.Serializable {

	private static final long serialVersionUID = -9092126592851738050L;

	private Integer id;
	
	private String statut;
	private TaTStatut taTStatut;
	
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

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public TaTStatut getTaTStatut() {
		return taTStatut;
	}

	public void setTaTStatut(TaTStatut taTStatut) {
		this.taTStatut = taTStatut;
	}

	
}
