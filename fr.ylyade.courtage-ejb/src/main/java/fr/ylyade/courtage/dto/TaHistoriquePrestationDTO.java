package fr.ylyade.courtage.dto;

import java.util.Date;

import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaReglementPrestation;

public class TaHistoriquePrestationDTO extends ModelObject implements java.io.Serializable {

	
	private static final long serialVersionUID = -3583503140315414228L;


	private Integer id;
	
	
	private String liblPrestation;
	private Date datePrestation;
	
	private TaDossierRcd taDossierRcd;
	private TaReglementPrestation taReglementPrestation;
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLiblPrestation() {
		return liblPrestation;
	}

	public void setLiblPrestation(String liblPrestation) {
		this.liblPrestation = liblPrestation;
	}

	public Date getDatePrestation() {
		return datePrestation;
	}

	public void setDatePrestation(Date datePrestation) {
		this.datePrestation = datePrestation;
	}

	public TaDossierRcd getTaDossierRcd() {
		return taDossierRcd;
	}

	public void setTaDossierRcd(TaDossierRcd taDossierRcd) {
		this.taDossierRcd = taDossierRcd;
	}

	public TaReglementPrestation getTaReglementPrestation() {
		return taReglementPrestation;
	}

	public void setTaReglementPrestation(TaReglementPrestation taReglementPrestation) {
		this.taReglementPrestation = taReglementPrestation;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
