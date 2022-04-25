package fr.ylyade.courtage.dto;

import java.math.BigDecimal;
import java.util.Date;

import fr.ylyade.courtage.model.TaDossierRcd;

public class TaSinistreAntecedentDTO extends ModelObject implements java.io.Serializable {

	private static final long serialVersionUID = 8887031100670061404L;

	private Integer id;
	private Date dateSinistre;
	private String liblSinistre;
	private BigDecimal montantSinistre;
	private TaDossierRcd taDevisRcPro;
	
	private Integer versionObj;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateSinistre() {
		return dateSinistre;
	}

	public void setDateSinistre(Date dateSinistre) {
		this.dateSinistre = dateSinistre;
	}

	public String getLiblSinistre() {
		return liblSinistre;
	}

	public void setLiblSinistre(String liblSinistre) {
		this.liblSinistre = liblSinistre;
	}

	public TaDossierRcd getTaDevisRcPro() {
		return taDevisRcPro;
	}

	public void setTaDevisRcPro(TaDossierRcd taDevisRcPro) {
		this.taDevisRcPro = taDevisRcPro;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public BigDecimal getMontantSinistre() {
		return montantSinistre;
	}

	public void setMontantSinistre(BigDecimal montantSinistre) {
		this.montantSinistre = montantSinistre;
	}

	
}
