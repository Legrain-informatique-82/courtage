package fr.ylyade.courtage.dto;

import java.math.BigDecimal;
import java.util.Date;

import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaReglementPrestation;
import fr.ylyade.courtage.model.TaTarifPrestation;

public class TaAttestationNominativeDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7136156221045953000L;

	private Integer id;
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	
	private Date dateEffet;
	private Date dateDebut;
	private Date dateFin;
	private String policeAssurance;
	private String adresseChantier;
	private String nomMaitreOuvrage;
	private String natureOuvrage;
	private Date dateOuvertureChantier;
	private Date dateReceptionDefinitive;
	private Date dateInterventionAssure;
	private String qualiteAssure;
	private String travauxEffectue;
	private BigDecimal montantMarcheHt;
	private Integer effectifChantier;
	private BigDecimal montantPaye;
	
	
	private String codeTarifPrestation; //cf TaTarifPrestation
	private BigDecimal montantPrestation;
	private String liblPrestation;
	
	
	private boolean validationCourtier = false;
	private boolean validationYlyade = false;
	
	
	
	private BigDecimal coutTotalConstructionTtc;
	private byte[] imgNominative;
	private TaDossierRcd taDossierRcd;
	private Integer versionObj;
	

	

	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Date getDateEffet() {
		return dateEffet;
	}

	public void setDateEffet(Date dateEffet) {
		this.dateEffet = dateEffet;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public String getPoliceAssurance() {
		return policeAssurance;
	}

	public void setPoliceAssurance(String policeAssurance) {
		this.policeAssurance = policeAssurance;
	}

	public String getAdresseChantier() {
		return adresseChantier;
	}

	public void setAdresseChantier(String adresseChantier) {
		this.adresseChantier = adresseChantier;
	}

	public String getNomMaitreOuvrage() {
		return nomMaitreOuvrage;
	}

	public void setNomMaitreOuvrage(String nomMaitreOuvrage) {
		this.nomMaitreOuvrage = nomMaitreOuvrage;
	}

	public String getNatureOuvrage() {
		return natureOuvrage;
	}

	public void setNatureOuvrage(String natureOuvrage) {
		this.natureOuvrage = natureOuvrage;
	}

	public Date getDateOuvertureChantier() {
		return dateOuvertureChantier;
	}

	public void setDateOuvertureChantier(Date dateOuvertureChantier) {
		this.dateOuvertureChantier = dateOuvertureChantier;
	}

	public Date getDateReceptionDefinitive() {
		return dateReceptionDefinitive;
	}

	public void setDateReceptionDefinitive(Date dateReceptionDefinitive) {
		this.dateReceptionDefinitive = dateReceptionDefinitive;
	}

	public Date getDateInterventionAssure() {
		return dateInterventionAssure;
	}

	public void setDateInterventionAssure(Date dateInterventionAssure) {
		this.dateInterventionAssure = dateInterventionAssure;
	}

	public String getQualiteAssure() {
		return qualiteAssure;
	}

	public void setQualiteAssure(String qualiteAssure) {
		this.qualiteAssure = qualiteAssure;
	}

	public String getTravauxEffectue() {
		return travauxEffectue;
	}

	public void setTravauxEffectue(String travauxEffectue) {
		this.travauxEffectue = travauxEffectue;
	}

	public BigDecimal getMontantMarcheHt() {
		return montantMarcheHt;
	}

	public void setMontantMarcheHt(BigDecimal montantMarcheHt) {
		this.montantMarcheHt = montantMarcheHt;
	}

	public String getCodeTarifPrestation() {
		return codeTarifPrestation;
	}

	public void setCodeTarifPrestation(String codeTarifPrestation) {
		this.codeTarifPrestation = codeTarifPrestation;
	}

	public BigDecimal getMontantPrestation() {
		return montantPrestation;
	}

	public void setMontantPrestation(BigDecimal montantPrestation) {
		this.montantPrestation = montantPrestation;
	}

	public BigDecimal getCoutTotalConstructionTtc() {
		return coutTotalConstructionTtc;
	}

	public void setCoutTotalConstructionTtc(BigDecimal coutTotalConstructionTtc) {
		this.coutTotalConstructionTtc = coutTotalConstructionTtc;
	}

	public byte[] getImgNominative() {
		return imgNominative;
	}

	public void setImgNominative(byte[] imgNominative) {
		this.imgNominative = imgNominative;
	}

	public TaDossierRcd getTaDossierRcd() {
		return taDossierRcd;
	}

	public void setTaDossierRcd(TaDossierRcd taDossierRcd) {
		this.taDossierRcd = taDossierRcd;
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


	public String getLiblPrestation() {
		return liblPrestation;
	}

	public void setLiblPrestation(String liblPrestation) {
		this.liblPrestation = liblPrestation;
	}

	public BigDecimal getMontantPaye() {
		return montantPaye;
	}

	public void setMontantPaye(BigDecimal montantPaye) {
		this.montantPaye = montantPaye;
	}

	public Integer getEffectifChantier() {
		return effectifChantier;
	}

	public void setEffectifChantier(Integer effectifChantier) {
		this.effectifChantier = effectifChantier;
	}

	public boolean isValidationCourtier() {
		return validationCourtier;
	}

	public void setValidationCourtier(boolean validationCourtier) {
		this.validationCourtier = validationCourtier;
	}

	public boolean isValidationYlyade() {
		return validationYlyade;
	}

	public void setValidationYlyade(boolean validationYlyade) {
		this.validationYlyade = validationYlyade;
	}

	
}
