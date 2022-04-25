package fr.ylyade.courtage.dto;

import fr.ylyade.courtage.model.TaTAdresse;

public class TaAdresseDTO extends ModelObject implements java.io.Serializable {

	

	private Integer id;
	
	
	private String adresseLigne1;
	private String adresseLigne2;
	private String adresseLigne3;
	private String codePostal;
	private String ville;
	private String pays;
	private TaTAdresse taTAdresse;
	
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

	public String getAdresseLigne1() {
		return adresseLigne1;
	}

	public void setAdresseLigne1(String adresseLigne1) {
		this.adresseLigne1 = adresseLigne1;
	}

	public String getAdresseLigne2() {
		return adresseLigne2;
	}

	public void setAdresseLigne2(String adresseLigne2) {
		this.adresseLigne2 = adresseLigne2;
	}

	public String getAdresseLigne3() {
		return adresseLigne3;
	}

	public void setAdresseLigne3(String adresseLigne3) {
		this.adresseLigne3 = adresseLigne3;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public TaTAdresse getTaTAdresse() {
		return taTAdresse;
	}

	public void setTaTAdresse(TaTAdresse taTAdresse) {
		this.taTAdresse = taTAdresse;
	}

	
}
