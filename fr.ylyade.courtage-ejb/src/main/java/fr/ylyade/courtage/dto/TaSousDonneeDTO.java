package fr.ylyade.courtage.dto;


import fr.ylyade.courtage.model.TaDossierRcd;

public class TaSousDonneeDTO extends ModelObject implements java.io.Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = -7481251136482775180L;
	
	private Integer idSousDonnee;
	private String jsonData;
	private TaDossierRcd taDossierRcd;
	
	public Integer getIdSousDonnee() {
		return idSousDonnee;
	}
	public void setIdSousDonnee(Integer idSousDonnee) {
		this.idSousDonnee = idSousDonnee;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public TaDossierRcd getTaDossierRcd() {
		return taDossierRcd;
	}
	public void setTaDossierRcd(TaDossierRcd taDossierRcd) {
		this.taDossierRcd = taDossierRcd;
	}
	
	
	

	
}
