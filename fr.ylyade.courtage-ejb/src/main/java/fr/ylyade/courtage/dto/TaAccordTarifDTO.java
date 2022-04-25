package fr.ylyade.courtage.dto;

import java.util.Date;

import fr.ylyade.courtage.model.TaCourtier;
import fr.ylyade.courtage.model.TaTGroupeTarif;

public class TaAccordTarifDTO extends ModelObject implements java.io.Serializable {

	
	private static final long serialVersionUID = 469510112869608632L;

	private Integer id;
	
	private Date dateAccordTarif;
	private TaCourtier taCourtier;
	private TaTGroupeTarif taTGroupeTarif;
	
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

	public Date getDateAccordTarif() {
		return dateAccordTarif;
	}

	public void setDateAccordTarif(Date dateAccordTarif) {
		this.dateAccordTarif = dateAccordTarif;
	}

	public TaCourtier getTaCourtier() {
		return taCourtier;
	}

	public void setTaCourtier(TaCourtier taCourtier) {
		this.taCourtier = taCourtier;
	}

	public TaTGroupeTarif getTaTGroupeTarif() {
		return taTGroupeTarif;
	}

	public void setTaTGroupeTarif(TaTGroupeTarif taTGroupeTarif) {
		this.taTGroupeTarif = taTGroupeTarif;
	}

	
}
