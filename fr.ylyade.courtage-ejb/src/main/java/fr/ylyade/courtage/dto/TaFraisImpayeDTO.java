package fr.ylyade.courtage.dto;

import java.math.BigDecimal;
import java.util.Date;


public class TaFraisImpayeDTO extends ModelObject implements java.io.Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5597514647267323025L;
	
	
	private Integer idFraisImpaye;
	private Date dateFrais;
	private BigDecimal montantFrais;
	
	private Integer versionObj;
	
//	private TaTFraisImpaye taTFraisImpaye;
//	private TaEcheance taEcheance;
	
	public TaFraisImpayeDTO() {}
	
	
	

	public Integer getIdFraisImpaye() {
		return idFraisImpaye;
	}
	public void setIdFraisImpaye(Integer idFraisImpaye) {
		this.idFraisImpaye = idFraisImpaye;
	}
	
	public Date getDateFrais() {
		return dateFrais;
	}
	public void setDateFrais(Date dateFrais) {
		this.dateFrais = dateFrais;
	}
	public BigDecimal getMontantFrais() {
		return montantFrais;
	}
	public void setMontantFrais(BigDecimal montantFrais) {
		this.montantFrais = montantFrais;
	}




	public Integer getVersionObj() {
		return versionObj;
	}




	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

//	public TaTFraisImpaye getTaTFraisImpaye() {
//		return taTFraisImpaye;
//	}
//	public void setTaTFraisImpaye(TaTFraisImpaye taTFraisImpaye) {
//		this.taTFraisImpaye = taTFraisImpaye;
//	}
//
//	public TaEcheance getTaEcheance() {
//		return taEcheance;
//	}
//	public void setTaEcheance(TaEcheance taEcheance) {
//		this.taEcheance = taEcheance;
//	}

}
