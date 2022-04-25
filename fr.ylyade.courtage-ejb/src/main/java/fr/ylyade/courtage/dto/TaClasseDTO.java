package fr.ylyade.courtage.dto;

import java.util.Set;

import fr.ylyade.courtage.model.TaPalierClasse;

public class TaClasseDTO extends ModelObject implements java.io.Serializable {



	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3780072075939761906L;

	private Integer id;
	private String codeClasse;
	private String liblClasse;
	
	private Set<TaPalierClasse> taPalierClasse; //--liste de palier ?
	
	
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<TaPalierClasse> getTaPalierClasse() {
		return taPalierClasse;
	}

	public void setTaPalierClasse(Set<TaPalierClasse> taPalierClasse) {
		this.taPalierClasse = taPalierClasse;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public String getCodeClasse() {
		return codeClasse;
	}

	public void setCodeClasse(String codeClasse) {
		this.codeClasse = codeClasse;
	}

	public String getLiblClasse() {
		return liblClasse;
	}

	public void setLiblClasse(String liblClasse) {
		this.liblClasse = liblClasse;
	}

	
}
