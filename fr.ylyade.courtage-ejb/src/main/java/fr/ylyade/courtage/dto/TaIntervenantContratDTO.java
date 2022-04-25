package fr.ylyade.courtage.dto;

import fr.ylyade.courtage.model.TaContratDommageOuvrage;
import fr.ylyade.courtage.model.TaTLotRealise;

public class TaIntervenantContratDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6827784436564475006L;

	private Integer id;
	
	private String nomEntreprise;
	private String numSiret;
	private String adresseEntreprise;
	private String assureurDecennale;
	private String numContrat;
	private String codeLot;
	private TaTLotRealise taTLotRealise;
	private TaContratDommageOuvrage taContratDommageOuvrage;
	

	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	


	public String getNomEntreprise() {
		return nomEntreprise;
	}

	public void setNomEntreprise(String nomEntreprise) {
		this.nomEntreprise = nomEntreprise;
	}

	public String getNumSiret() {
		return numSiret;
	}

	public void setNumSiret(String numSiret) {
		this.numSiret = numSiret;
	}

	public String getAdresseEntreprise() {
		return adresseEntreprise;
	}

	public void setAdresseEntreprise(String adresseEntreprise) {
		this.adresseEntreprise = adresseEntreprise;
	}

	public String getAssureurDecennale() {
		return assureurDecennale;
	}

	public void setAssureurDecennale(String assureurDecennale) {
		this.assureurDecennale = assureurDecennale;
	}

	public String getNumContrat() {
		return numContrat;
	}

	public void setNumContrat(String numContrat) {
		this.numContrat = numContrat;
	}

	public String getCodeLot() {
		return codeLot;
	}

	public void setCodeLot(String codeLot) {
		this.codeLot = codeLot;
	}

	public TaTLotRealise getTaTLotRealise() {
		return taTLotRealise;
	}

	public void setTaTLotRealise(TaTLotRealise taTLotRealise) {
		this.taTLotRealise = taTLotRealise;
	}

	public TaContratDommageOuvrage getTaContratDommageOuvrage() {
		return taContratDommageOuvrage;
	}

	public void setTaContratDommageOuvrage(TaContratDommageOuvrage taContratDommageOuvrage) {
		this.taContratDommageOuvrage = taContratDommageOuvrage;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
