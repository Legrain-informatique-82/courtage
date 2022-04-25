package fr.ylyade.courtage.dto;

import java.math.BigDecimal;

import fr.ylyade.courtage.model.TaReglementAssurance;
import fr.ylyade.courtage.model.TaReglementPrestation;

public class TaQuittanceDTO extends ModelObject implements java.io.Serializable {

	
	private static final long serialVersionUID = -6208156943331838252L;

	private Integer id;
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	
	
	private String codePrestation;
	private String liblPrestation;
	private BigDecimal montant;
	private byte[] imgQuittance;
	private TaReglementAssurance taReglementAssurance; //-- table de relation ?
	private TaReglementPrestation taReglementPrestation; //-- table de relation 
	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getCodePrestation() {
		return codePrestation;
	}

	public void setCodePrestation(String codePrestation) {
		this.codePrestation = codePrestation;
	}

	public String getLiblPrestation() {
		return liblPrestation;
	}

	public void setLiblPrestation(String liblPrestation) {
		this.liblPrestation = liblPrestation;
	}

	public BigDecimal getMontant() {
		return montant;
	}

	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}

	public byte[] getImgQuittance() {
		return imgQuittance;
	}

	public void setImgQuittance(byte[] imgQuittance) {
		this.imgQuittance = imgQuittance;
	}

	public TaReglementAssurance getTaReglementAssurance() {
		return taReglementAssurance;
	}

	public void setTaReglementAssurance(TaReglementAssurance taReglementAssurance) {
		this.taReglementAssurance = taReglementAssurance;
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

	public String getNomFichier() {
		return nomFichier;
	}

	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}

	public Integer getTaille() {
		return taille;
	}

	public void setTaille(Integer taille) {
		this.taille = taille;
	}

	public String getTypeMime() {
		return typeMime;
	}

	public void setTypeMime(String typeMime) {
		this.typeMime = typeMime;
	}

	
}
