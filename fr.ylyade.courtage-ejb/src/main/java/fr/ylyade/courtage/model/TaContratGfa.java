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
@Table(name = "ta_contrat_gfa")

@NamedQueries(value = {  

		
		@NamedQuery(name=TaContratGfa.QN.COUNT_ACTIVE_BY_ID_COURTIER,
		query = "select count(f.idContratGfa)" +
		        " from TaContratGfa f left join f.taAssure a left join a.taCourtier c" +
		        " where c.idCourtier = :idCourtier ")
		
		
				
					

})
public class TaContratGfa implements Serializable {

	private static final long serialVersionUID = 3724632876549291523L;
	public static class QN {
		
		public static final String COUNT_ACTIVE_BY_ID_COURTIER = "TaContratGfa.countActiveByIdCourtier";
	}

	private int idContratGfa;
	
	private TaAssure taAssure;
	
	private BigDecimal fraisCourtier; //frais et montant different ?
	private BigDecimal tauxCommissionCourtier;
	private BigDecimal montantCommissionCourtier; //frais et montant different ? cumul ?
	private BigDecimal primeAssuranceNue;                     
    private byte[] imgContratGfa;
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
	private String numPolice;
	private Boolean contratActif = false; 
	private Date dateSouscription;
	private Date dateRetractation;
	private Boolean retractation = false;
	
	private Date dateFinContrat;
	
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	
	
	private TaProjetGfa taProjetGfa;
	private TaEcheance taEcheance;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_contrat_gfa", unique = true, nullable = false)
	public int getIdContratGfa() {
		return idContratGfa;
	}

	public void setIdContratGfa(int idContratGfa) {
		this.idContratGfa = idContratGfa;
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

	@Column(name = "img_contrat_gfa")
	public byte[] getImgContratGfa() {
		return imgContratGfa;
	}

	public void setImgContratGfa(byte[] imgContratGfa) {
		this.imgContratGfa = imgContratGfa;
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

	@Column(name = "prix_vente_previsionnel")
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

	@Column(name = "num_police")
	public String getNumPolice() {
		return numPolice;
	}

	public void setNumPolice(String numPolice) {
		this.numPolice = numPolice;
	}

	@Column(name = "contrat_actif")
	public Boolean getContratActif() {
		return contratActif;
	}

	public void setContratActif(Boolean contratActif) {
		this.contratActif = contratActif;
	}

	@Column(name = "date_souscription")
	public Date getDateSouscription() {
		return dateSouscription;
	}

	public void setDateSouscription(Date dateSouscription) {
		this.dateSouscription = dateSouscription;
	}

	@Column(name = "date_retractation")
	public Date getDateRetractation() {
		return dateRetractation;
	}

	public void setDateRetractation(Date dateRetractation) {
		this.dateRetractation = dateRetractation;
	}

	@Column(name = "retractation")
	public Boolean getRetractation() {
		return retractation;
	}

	public void setRetractation(Boolean retractation) {
		this.retractation = retractation;
	}

//	@Column(name = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")xxxxxxxx
//	public byte[] getImgContratDommageOuvrage() {
//		return imgContratDommageOuvrage;
//	}
//
//	public void setImgContratDommageOuvrage(byte[] imgContratDommageOuvrage) {
//		this.imgContratDommageOuvrage = imgContratDommageOuvrage;
//	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_projet_gfa_ta_projet_gfa",unique = true)
	public TaProjetGfa getTaProjetGfa() {
		return taProjetGfa;
	}

	public void setTaProjetGfa(TaProjetGfa taProjetGfa) {
		this.taProjetGfa = taProjetGfa;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_echeance_ta_echeance",unique = true)
	public TaEcheance getTaEcheance() {
		return taEcheance;
	}

	public void setTaEcheance(TaEcheance taEcheance) {
		this.taEcheance = taEcheance;
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
	@JoinColumn(name = "id_assure_ta_assure")
	public TaAssure getTaAssure() {
		return taAssure;
	}

	public void setTaAssure(TaAssure taAssure) {
		this.taAssure = taAssure;
	}
	@Column(name = "date_fin_contrat")
	public Date getDateFinContrat() {
		return dateFinContrat;
	}

	public void setDateFinContrat(Date dateFinContrat) {
		this.dateFinContrat = dateFinContrat;
	}

	
}
