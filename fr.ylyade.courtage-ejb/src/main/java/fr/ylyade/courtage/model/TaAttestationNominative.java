package fr.ylyade.courtage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "ta_attestation_nominative")
@NamedQueries(value = { 		
//		@NamedQuery(name=TaAttestationNominative.QN.FIND_ALL_BY_ID_DOSSIER,
//		query = "select a"
//		       + " from TaDossierRcd d left join d.taAttestationNominative a"
//		       + " where a.taDossierRcd.idDossierRcd = :idDossierRcd")})
		@NamedQuery(name=TaAttestationNominative.QN.FIND_ALL_BY_ID_DOSSIER,
				query = "select a"
				       + " from TaAttestationNominative a"
				       + " where a.taDossierRcd.idDossierRcd = :idDossierRcd")})

public class TaAttestationNominative implements Serializable {
	
	public static class QN {
		public static final String FIND_ALL_BY_ID_DOSSIER = "TaAttestationNominative.findAllByIdDossier";
		}

	private static final long serialVersionUID = 6906300374999768768L;
	
	private Integer idAttestationNominative;
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
	
	private TaTReglement taTReglement;
	
	
//	private String codeTarifPrestation; //cf TaTarifPrestation
//	private BigDecimal montantPrestation;
	
	private BigDecimal montantPaye;
	private BigDecimal coutTotalConstructionTtc;
	private byte[] imgNominative;
	private TaDossierRcd taDossierRcd;
	
	private TaTarifPrestation taTarifPrestation;
	
	private TaReglementPrestation taReglementPrestation;
	
	private List<TaAttestationNominativeActivite> taAttestationActivites;
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	
	private boolean validationCourtier = false;
	private boolean validationYlyade = false;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_attestation_nominative", unique = true, nullable = false)
	public Integer getIdAttestationNominative() {
		return idAttestationNominative;
	}

	public void setIdAttestationNominative(Integer idAttestationNominative) {
		this.idAttestationNominative = idAttestationNominative;
	}

	@Column(name = "date_effet")
	public Date getDateEffet() {
		return dateEffet;
	}

	public void setDateEffet(Date dateEffet) {
		this.dateEffet = dateEffet;
	}

	@Column(name = "date_debut")
	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	@Column(name = "date_fin")
	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	@Column(name = "police_assurance")
	public String getPoliceAssurance() {
		return policeAssurance;
	}

	public void setPoliceAssurance(String policeAssurance) {
		this.policeAssurance = policeAssurance;
	}

	@Column(name = "adresse_chantier")
	public String getAdresseChantier() {
		return adresseChantier;
	}

	public void setAdresseChantier(String adresseChantier) {
		this.adresseChantier = adresseChantier;
	}

	@Column(name = "nom_maitre_ouvrage")
	public String getNomMaitreOuvrage() {
		return nomMaitreOuvrage;
	}

	public void setNomMaitreOuvrage(String nomMaitreOuvrage) {
		this.nomMaitreOuvrage = nomMaitreOuvrage;
	}

	@Column(name = "nature_ouvrage")
	public String getNatureOuvrage() {
		return natureOuvrage;
	}

	public void setNatureOuvrage(String natureOuvrage) {
		this.natureOuvrage = natureOuvrage;
	}

	@Column(name = "date_ouverture_chantier")
	public Date getDateOuvertureChantier() {
		return dateOuvertureChantier;
	}

	public void setDateOuvertureChantier(Date dateOuvertureChantier) {
		this.dateOuvertureChantier = dateOuvertureChantier;
	}

	@Column(name = "date_reception_definitive")
	public Date getDateReceptionDefinitive() {
		return dateReceptionDefinitive;
	}

	public void setDateReceptionDefinitive(Date dateReceptionDefinitive) {
		this.dateReceptionDefinitive = dateReceptionDefinitive;
	}

	@Column(name = "date_intervention_assure")
	public Date getDateInterventionAssure() {
		return dateInterventionAssure;
	}

	public void setDateInterventionAssure(Date dateInterventionAssure) {
		this.dateInterventionAssure = dateInterventionAssure;
	}

	@Column(name = "qualite_assure")
	public String getQualiteAssure() {
		return qualiteAssure;
	}

	public void setQualiteAssure(String qualiteAssure) {
		this.qualiteAssure = qualiteAssure;
	}

	@Column(name = "travaux_effectue")
	public String getTravauxEffectue() {
		return travauxEffectue;
	}

	public void setTravauxEffectue(String travauxEffectue) {
		this.travauxEffectue = travauxEffectue;
	}

	@Column(name = "montant_marche_ht")
	public BigDecimal getMontantMarcheHt() {
		return montantMarcheHt;
	}

	public void setMontantMarcheHt(BigDecimal montantMarcheHt) {
		this.montantMarcheHt = montantMarcheHt;
	}


	@Column(name = "coup_total_construction_ttc")
	public BigDecimal getCoutTotalConstructionTtc() {
		return coutTotalConstructionTtc;
	}

	public void setCoutTotalConstructionTtc(BigDecimal coutTotalConstructionTtc) {
		this.coutTotalConstructionTtc = coutTotalConstructionTtc;
	}

	@Column(name = "img_nominative")
	public byte[] getImgNominative() {
		return imgNominative;
	}

	public void setImgNominative(byte[] imgNominative) {
		this.imgNominative = imgNominative;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_dossier_rcd_ta_dossier_rcd")
	public TaDossierRcd getTaDossierRcd() {
		return taDossierRcd;
	}

	public void setTaDossierRcd(TaDossierRcd taContratRcPro) {
		this.taDossierRcd = taContratRcPro;
	}

	@Column(name = "qui_cree", length = 50)
	public String getQuiCree() {
		return this.quiCree;
	}

	public void setQuiCree(String quiCreeTCivilite) {
		this.quiCree = quiCreeTCivilite;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "quand_cree", length = 19)
	public Date getQuandCree() {
		return this.quandCree;
	}

	public void setQuandCree(Date quandCreeTCivilite) {
		this.quandCree = quandCreeTCivilite;
	}

	@Column(name = "qui_modif", length = 50)
	public String getQuiModif() {
		return this.quiModif;
	}

	public void setQuiModif(String quiModifTCivilite) {
		this.quiModif = quiModifTCivilite;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "quand_modif", length = 19)
	public Date getQuandModif() {
		return this.quandModif;
	}

	public void setQuandModif(Date quandModifTCivilite) {
		this.quandModif = quandModifTCivilite;
	}

	@Column(name = "ip_acces", length = 50)
	public String getIpAcces() {
		return this.ipAcces;
	}

	public void setIpAcces(String ipAcces) {
		this.ipAcces = ipAcces;
	}

	@Version
	@Column(name = "version_obj")
	public Integer getVersionObj() {
		return this.versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	@Column(name = "nom_fichier")
	public String getNomFichier() {
		return nomFichier;
	}

	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}
	@Column(name = "taille")
	public Integer getTaille() {
		return taille;
	}

	public void setTaille(Integer taille) {
		this.taille = taille;
	}
	@Column(name = "type_mime")
	public String getTypeMime() {
		return typeMime;
	}

	public void setTypeMime(String typeMime) {
		this.typeMime = typeMime;
	}
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_tarif_prestation_ta_tarif_prestation")
	public TaTarifPrestation getTaTarifPrestation() {
		return taTarifPrestation;
	}

	public void setTaTarifPrestation(TaTarifPrestation taTarifPrestation) {
		this.taTarifPrestation = taTarifPrestation;
	}
	@Column(name = "montant_paye")
	public BigDecimal getMontantPaye() {
		return montantPaye;
	}

	public void setMontantPaye(BigDecimal montantPaye) {
		this.montantPaye = montantPaye;
	}
	
	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "id_reglement_prestation_ta_reglement_prestation")
	public TaReglementPrestation getTaReglementPrestation() {
		return taReglementPrestation;
	}

	public void setTaReglementPrestation(TaReglementPrestation taReglementPrestation) {
		this.taReglementPrestation = taReglementPrestation;
	}
	@Column(name = "effectif_chantier")
	public Integer getEffectifChantier() {
		return effectifChantier;
	}

	public void setEffectifChantier(Integer effectifChantier) {
		this.effectifChantier = effectifChantier;
	}
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_reglement_ta_t_reglement")
	public TaTReglement getTaTReglement() {
		return taTReglement;
	}

	public void setTaTReglement(TaTReglement taTReglement) {
		this.taTReglement = taTReglement;
	}
	@Column(name = "validation_courtier")
	public boolean isValidationCourtier() {
		return validationCourtier;
	}

	public void setValidationCourtier(boolean validationCourtier) {
		this.validationCourtier = validationCourtier;
	}
	@Column(name = "validation_ylyade")
	public boolean isValidationYlyade() {
		return validationYlyade;
	}

	public void setValidationYlyade(boolean validationYlyade) {
		this.validationYlyade = validationYlyade;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "taAttestationNominative", orphanRemoval=true)
	@Fetch(FetchMode.SUBSELECT)
	public List<TaAttestationNominativeActivite> getTaAttestationActivites() {
		return taAttestationActivites;
	}

	public void setTaAttestationActivites(List<TaAttestationNominativeActivite> taAttestationActivites) {
		this.taAttestationActivites = taAttestationActivites;
	}
}
