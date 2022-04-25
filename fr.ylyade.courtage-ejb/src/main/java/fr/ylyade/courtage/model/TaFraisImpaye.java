package fr.ylyade.courtage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "ta_frais_impaye")
public class TaFraisImpaye implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4849582453384338827L;
	
	
	private int idFraisImpaye;
	private Date dateFrais;
	private BigDecimal montantFrais;
	
	private TaTFraisImpaye taTFraisImpaye;
	private TaEcheance taEcheance;
	
	
	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;
	
	
	@Override
	public String toString() {
	    return String.format(montantFrais+" €, "+taTFraisImpaye.getLiblFactureFraisImpaye()+" , le "+dateFrais);
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_frais_impaye", unique = true, nullable = false)
	public int getIdFraisImpaye() {
		return idFraisImpaye;
	}
	public void setIdFraisImpaye(int idFraisImpaye) {
		this.idFraisImpaye = idFraisImpaye;
	}
	@Column(name = "date_frais")
	public Date getDateFrais() {
		return dateFrais;
	}
	public void setDateFrais(Date dateFrais) {
		this.dateFrais = dateFrais;
	}
	@Column(name = "montant_frais")
	public BigDecimal getMontantFrais() {
		return montantFrais;
	}
	public void setMontantFrais(BigDecimal montantFrais) {
		this.montantFrais = montantFrais;
	}
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_frais_impaye_ta_t_frais_impaye")
	public TaTFraisImpaye getTaTFraisImpaye() {
		return taTFraisImpaye;
	}
	public void setTaTFraisImpaye(TaTFraisImpaye taTFraisImpaye) {
		this.taTFraisImpaye = taTFraisImpaye;
	}
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_echeance_ta_echeance")
	public TaEcheance getTaEcheance() {
		return taEcheance;
	}
	public void setTaEcheance(TaEcheance taEcheance) {
		this.taEcheance = taEcheance;
	}
	@Version
	@Column(name = "version_obj")
	public Integer getVersionObj() {
		return versionObj;
	}
	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}
	@Column(name = "qui_cree", length = 50)
	public String getQuiCree() {
		return quiCree;
	}
	public void setQuiCree(String quiCree) {
		this.quiCree = quiCree;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "quand_cree", length = 19)
	public Date getQuandCree() {
		return quandCree;
	}
	public void setQuandCree(Date quandCree) {
		this.quandCree = quandCree;
	}
	@Column(name = "qui_modif", length = 50)
	public String getQuiModif() {
		return quiModif;
	}
	public void setQuiModif(String quiModif) {
		this.quiModif = quiModif;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "quand_modif", length = 19)
	public Date getQuandModif() {
		return quandModif;
	}
	public void setQuandModif(Date quandModif) {
		this.quandModif = quandModif;
	}
	@Column(name = "ip_acces", length = 50)
	public String getIpAcces() {
		return ipAcces;
	}
	public void setIpAcces(String ipAcces) {
		this.ipAcces = ipAcces;
	}

}
