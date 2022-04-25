package fr.ylyade.courtage.dto;

import java.util.Date;

public class TaTStatutDTO extends ModelObject implements java.io.Serializable {

	private static final long serialVersionUID = -7725527357175555335L;

	private Integer id;
	private String codeTStatut;
	private String liblTStatut;
	private Date dureeStatut;
	private int dureeNbJoursStatut;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeTStatut() {
		return codeTStatut;
	}

	public void setCodeTStatut(String codeStatut) {
		this.codeTStatut = codeStatut;
	}

	public String getLiblTStatut() {
		return liblTStatut;
	}

	public void setLiblTStatut(String liblStatut) {
		this.liblTStatut = liblStatut;
	}

	public Date getDureeStatut() {
		return dureeStatut;
	}

	public void setDureeStatut(Date dureeStatut) {
		this.dureeStatut = dureeStatut;
	}

	public int getDureeNbJoursStatut() {
		return dureeNbJoursStatut;
	}

	public void setDureeNbJoursStatut(int dureeNbJoursStatut) {
		this.dureeNbJoursStatut = dureeNbJoursStatut;
	}
	
	private Integer versionObj;
	
	
	

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
