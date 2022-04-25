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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "ta_projet_gfa")

@NamedQueries(value = { 
						@NamedQuery(name=TaProjetGfa.QN.COUNT_ACTIVE_BY_ID_COURTIER,
						query = "select count(f.idProjetGfa)" +
						        " from TaProjetGfa f left join f.taAssure a left join a.taCourtier c" +
								" where c.idCourtier = :idCourtier and (f.taContratGfa IS NULL or f.dateFinValiditeProjet < :now )")
						
						
})

public class TaProjetGfa implements Serializable {

	private static final long serialVersionUID = 5117240470014380454L;
	
	public static class QN {
	public static final String COUNT_ACTIVE_BY_ID_COURTIER = "TaProjetGfa.countActiveByIdCourtier";
	}
	
	private int idProjetGfa;

	private String numProjetGfa;
	private BigDecimal fraisCourtier; //frais et montant different ?
	private BigDecimal tauxCommissionCourtier;
	private BigDecimal montantCommissionCourtier; //frais et montant different ? cumul ?
	private BigDecimal primeAssuranceNue;                     
    private byte[] imgProjetGfa;
    private BigDecimal tauxGfa;
    private BigDecimal montantChantierGfa;
    private Date dateRealisation;
	private String communeAssietteFonciere;
	private Integer nbBatiments;
	private Integer nbLogements;
	private String usageDe;
	private Date dateDelivrance;
	private String beneficiaire;
	private String numDelivrance;
	private BigDecimal surfacePlancher;
	private Integer nbTerrains;
	private Integer nbCommerces;
	private String destination;
	private String prescriptionsParticulieres;
	private BigDecimal prixRevientPrevisionnel;
	private BigDecimal prixVentePrevisionnel;
	private BigDecimal montantPreCommercialisation;
	private String terrainCadastre;
	
	private Date dateFinValiditeProjet;
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	
	private TaAssure taAssure;
	private TaContratGfa taContratGfa;
	private TaTAssurance taTAssurance;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_projet_gfa", unique = true, nullable = false)
	public int getIdProjetGfa() {
		return idProjetGfa;
	}

	public void setIdProjetGfa(int idProjetGfa) {
		this.idProjetGfa = idProjetGfa;
	}

	@Column(name = "num_projet_gfa")
	public String getNumProjetGfa() {
		return numProjetGfa;
	}

	public void setNumProjetGfa(String numProjetGfa) {
		this.numProjetGfa = numProjetGfa;
	}

	@Column(name = "frais_courtier")
	public BigDecimal getFraisCourtier() {
		return fraisCourtier;
	}

	public void setFraisCourtier(BigDecimal fraisCourtier) {
		this.fraisCourtier = fraisCourtier;
	}

	@Column(name = "taux_commission_courtier")
	public BigDecimal getTauxCommissionCourtier() {
		return tauxCommissionCourtier;
	}

	public void setTauxCommissionCourtier(BigDecimal tauxCommissionCourtier) {
		this.tauxCommissionCourtier = tauxCommissionCourtier;
	}

	@Column(name = "montant_commission_courtier")
	public BigDecimal getMontantCommissionCourtier() {
		return montantCommissionCourtier;
	}

	public void setMontantCommissionCourtier(BigDecimal montantCommissionCourtier) {
		this.montantCommissionCourtier = montantCommissionCourtier;
	}

	@Column(name = "prime_assurance_nue")
	public BigDecimal getPrimeAssuranceNue() {
		return primeAssuranceNue;
	}

	public void setPrimeAssuranceNue(BigDecimal primeAssuranceNue) {
		this.primeAssuranceNue = primeAssuranceNue;
	}

	@Column(name = "img_projet_gfa")
	public byte[] getImgProjetGfa() {
		return imgProjetGfa;
	}

	public void setImgProjetGfa(byte[] imgProjetGfa) {
		this.imgProjetGfa = imgProjetGfa;
	}

	@Column(name = "taux_gfa")
	public BigDecimal getTauxGfa() {
		return tauxGfa;
	}

	public void setTauxGfa(BigDecimal tauxGfa) {
		this.tauxGfa = tauxGfa;
	}

	@Column(name = "montant_chantier_gfa")
	public BigDecimal getMontantChantierGfa() {
		return montantChantierGfa;
	}

	public void setMontantChantierGfa(BigDecimal montantChantierGfa) {
		this.montantChantierGfa = montantChantierGfa;
	}

	@Column(name = "date_realisation")
	public Date getDateRealisation() {
		return dateRealisation;
	}

	public void setDateRealisation(Date dateRealisation) {
		this.dateRealisation = dateRealisation;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_assure_ta_assure")
	public TaAssure getTaAssure() {
		return taAssure;
	}

	public void setTaAssure(TaAssure taAssure) {
		this.taAssure = taAssure;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_contrat_gfa_ta_contrat_gfa")
	public TaContratGfa getTaContratGfa() {
		return taContratGfa;
	}

	public void setTaContratGfa(TaContratGfa taContratGfa) {
		this.taContratGfa = taContratGfa;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_assurance_ta_t_assurance")
	public TaTAssurance getTaTAssurance() {
		return taTAssurance;
	}

	public void setTaTAssurance(TaTAssurance taTAssurance) {
		this.taTAssurance = taTAssurance;
	}

	@Column(name = "commune_assiette_fonciere")
	public String getCommuneAssietteFonciere() {
		return communeAssietteFonciere;
	}

	public void setCommuneAssietteFonciere(String communeAssietteFonciere) {
		this.communeAssietteFonciere = communeAssietteFonciere;
	}

	@Column(name = "nb_batiments")
	public Integer getNbBatiments() {
		return nbBatiments;
	}

	public void setNbBatiments(Integer nbBatiments) {
		this.nbBatiments = nbBatiments;
	}

	@Column(name = "nb_logements")
	public Integer getNbLogements() {
		return nbLogements;
	}

	public void setNbLogements(Integer nbLogements) {
		this.nbLogements = nbLogements;
	}

	@Column(name = "usage_de")
	public String getUsageDe() {
		return usageDe;
	}

	public void setUsageDe(String usageDe) {
		this.usageDe = usageDe;
	}

	@Column(name = "date_delivrance")
	public Date getDateDelivrance() {
		return dateDelivrance;
	}

	public void setDateDelivrance(Date dateDelivrance) {
		this.dateDelivrance = dateDelivrance;
	}

	@Column(name = "beneficiaire")
	public String getBeneficiaire() {
		return beneficiaire;
	}

	public void setBeneficiaire(String beneficiaire) {
		this.beneficiaire = beneficiaire;
	}

	@Column(name = "num_delivrance")
	public String getNumDelivrance() {
		return numDelivrance;
	}

	public void setNumDelivrance(String numDelivrance) {
		this.numDelivrance = numDelivrance;
	}

	@Column(name = "surface_plancher")
	public BigDecimal getSurfacePlancher() {
		return surfacePlancher;
	}

	public void setSurfacePlancher(BigDecimal surfacePlancher) {
		this.surfacePlancher = surfacePlancher;
	}

	@Column(name = "nb_terrains")
	public Integer getNbTerrains() {
		return nbTerrains;
	}

	public void setNbTerrains(Integer nbTerrains) {
		this.nbTerrains = nbTerrains;
	}

	@Column(name = "nb_commerces")
	public Integer getNbCommerces() {
		return nbCommerces;
	}

	public void setNbCommerces(Integer nbCommerces) {
		this.nbCommerces = nbCommerces;
	}

	@Column(name = "destination")
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Column(name = "prescritions_particulieres")
	public String getPrescriptionsParticulieres() {
		return prescriptionsParticulieres;
	}

	public void setPrescriptionsParticulieres(String prescriptionsParticulieres) {
		this.prescriptionsParticulieres = prescriptionsParticulieres;
	}

	@Column(name = "prix_revient_previsionnel")
	public BigDecimal getPrixRevientPrevisionnel() {
		return prixRevientPrevisionnel;
	}

	public void setPrixRevientPrevisionnel(BigDecimal prixRevientPrevisionnel) {
		this.prixRevientPrevisionnel = prixRevientPrevisionnel;
	}

	@Column(name = "prix_ventre_previsionnel")
	public BigDecimal getPrixVentePrevisionnel() {
		return prixVentePrevisionnel;
	}

	public void setPrixVentePrevisionnel(BigDecimal prixVentePrevisionnel) {
		this.prixVentePrevisionnel = prixVentePrevisionnel;
	}

	@Column(name = "montant_pre_commecialisation")
	public BigDecimal getMontantPreCommercialisation() {
		return montantPreCommercialisation;
	}

	public void setMontantPreCommercialisation(BigDecimal montantPreCommercialisation) {
		this.montantPreCommercialisation = montantPreCommercialisation;
	}

	@Column(name = "terrain_cadastre")
	public String getTerrainCadastre() {
		return terrainCadastre;
	}

	public void setTerrainCadastre(String terrainCadastre) {
		this.terrainCadastre = terrainCadastre;
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
	
	@Column(name = "date_fin_validite_projet")
	public Date getDateFinValiditeProjet() {
		return dateFinValiditeProjet;
	}

	public void setDateFinValiditeProjet(Date dateFinValiditeProjet) {
		this.dateFinValiditeProjet = dateFinValiditeProjet;
	}
}
