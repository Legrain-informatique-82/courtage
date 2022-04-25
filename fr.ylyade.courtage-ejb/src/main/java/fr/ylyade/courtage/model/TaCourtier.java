package fr.ylyade.courtage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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

import fr.ylyade.courtage.droits.model.TaUtilisateur;

/*
 * this.id = id;
		c.codeCourtier = codeCourtier;
		tce.nom = nom;
		tce.prenom = prenom;
		c.logo = logo;
		c.actif = actif;
		c.suspendu = suspendu;
		tgt.idTGroupeTarif = idTGroupeTarif;
		tgt.codeGroupe = codeGroupe;
		tgt.nomGroupe = nomGroupe;
		tgt.tauxGroupe = tauxGroupe;
		tc.idTCourtier = idTCourtier;
		tc.codeTCourtier = codeTCourtier;
 */

//Integer id, String codeCourtier, String nom, String prenom, byte[] logo, Boolean actif,
//Boolean suspendu, Integer idTGroupeTarif, String codeGroupe, String nomGroupe, BigDecimal tauxGroupe,
//Integer idTCourtier, String codeTCourtier


@Entity
@Table(name = "ta_courtier")
@NamedQueries(value = { 
		@NamedQuery(name=TaCourtier.QN.FIND_ALL_LIGHT_ACTIF, 
			query="select new fr.ylyade.courtage.dto.TaCourtierDTO(c.idCourtier,c.codeCourtier,tce.nom,tce.prenom,c.actif,c.suspendu,tgt.idTGroupeTarif,"
					+ "tgt.codeGroupe,tgt.nomGroupe,tgt.tauxGroupe,tc.idTCourtier,tc.codeTCourtier, c.nomDenominationSociale) "
					+ "from TaCourtier c left join c.taTCourtier tc left join c.taTGroupeTarif tgt left join c.taContactEntreprise tce "
					+ "where c.actif=true order by c.codeCourtier"),
		@NamedQuery(name=TaCourtier.QN.FIND_BY_CODE_LIGHT, 
			query="select new fr.ylyade.courtage.dto.TaCourtierDTO(c.idCourtier,c.codeCourtier,tce.nom,tce.prenom,c.actif,c.suspendu,tgt.idTGroupeTarif,"
				+ "tgt.codeGroupe,tgt.nomGroupe,tgt.tauxGroupe,tc.idTCourtier,tc.codeTCourtier, c.nomDenominationSociale) "
				+ "from TaCourtier c left join c.taTCourtier tc left join c.taTGroupeTarif tgt left join c.taContactEntreprise tce "
				+ "where c.codeCourtier like :codeCourtier order by c.codeCourtier"),//and c.actif=true
		@NamedQuery(name=TaCourtier.QN.FIND_ALL_LIGHT_PLUS, 
			query="select new fr.ylyade.courtage.dto.TaCourtierDTO(c.idCourtier,c.codeCourtier,tce.nom,tce.prenom,c.actif,c.suspendu,tgt.idTGroupeTarif,"
				+ "tgt.codeGroupe,tgt.nomGroupe,tgt.tauxGroupe,tc.idTCourtier,tc.codeTCourtier, c.nomDenominationSociale, c.numOrias, u.identifiant) "
				+ "from TaCourtier c left join c.taTCourtier tc left join c.taTGroupeTarif tgt left join c.taContactEntreprise tce left join c.taUtilisateur u "
				+ "order by c.codeCourtier"),//where c.actif=true
		@NamedQuery(name=TaCourtier.QN.FIND_ALL_INACTIF, 
		query="select new fr.ylyade.courtage.dto.TaCourtierDTO(c.idCourtier,c.codeCourtier,tce.nom,tce.prenom,c.actif,c.suspendu,tgt.idTGroupeTarif,"
			+ "tgt.codeGroupe,tgt.nomGroupe,tgt.tauxGroupe,tc.idTCourtier,tc.codeTCourtier, c.nomDenominationSociale, c.numOrias, u.identifiant) "
			+ "from TaCourtier c left join c.taTCourtier tc left join c.taTGroupeTarif tgt left join c.taContactEntreprise tce left join c.taUtilisateur u "
			+ "where c.actif = false or c.actif is null "
			+ "order by c.codeCourtier"),
		@NamedQuery(name=TaCourtier.QN.FIND_ALL_ACTIF, 
		query="select new fr.ylyade.courtage.dto.TaCourtierDTO(c.idCourtier,c.codeCourtier,tce.nom,tce.prenom,c.actif,c.suspendu,tgt.idTGroupeTarif,"
			+ "tgt.codeGroupe,tgt.nomGroupe,tgt.tauxGroupe,tc.idTCourtier,tc.codeTCourtier, c.nomDenominationSociale, c.numOrias, u.identifiant) "
			+ "from TaCourtier c left join c.taTCourtier tc left join c.taTGroupeTarif tgt left join c.taContactEntreprise tce left join c.taUtilisateur u "
			+ "where c.actif = true "
			+ "order by c.codeCourtier"),
		})
public class TaCourtier implements Serializable {

	private static final long serialVersionUID = 8206503866476341329L;
	
	public static class QN {
		public static final String FIND_ALL_LIGHT_ACTIF = "TaCourtier.findAllLightActif";
		public static final String FIND_BY_CODE_LIGHT = "TaCourtier.findByCodeLight";
		public static final String FIND_ALL_LIGHT_PLUS = "TaCourtier.findAllLightPlus";
		public static final String FIND_ALL_INACTIF= "TaCourtier.findAllInactif";
		public static final String FIND_ALL_ACTIF= "TaCourtier.findAllActif";
	}
	
	private Integer idCourtier;
	
	private String codeCourtier;
//	private String titreFonction;
	private String codeSiret;
	private String codeSiren;
	private String codeApe;
	private String tvaIntraComm;
	private byte[] logo;
	private String qualite;
	private String numOrias;
	private String nomDenominationSociale;
	private String numRcs;
	private String numRcPro;
	private String numGreffe;
	private BigDecimal capitalSocial;
	private Boolean actif = false;
	private Boolean suspendu = false;
	private Integer effectifTotal;
	private Date dateAccordTarif; //-- historique ? table de relation ?
	private TaTGroupeTarif taTGroupeTarif;
//	private TaYlyade taYlyade; //-- bizarre
	private TaUtilisateur taUtilisateur;
//	private TaTCivilite taTCivilite;
	
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	
	private BigDecimal comBrutes;
	private BigDecimal comBrutesIard;
	private String connuYlyade;
	private BigDecimal caIardPourcentEntreprise;
	private BigDecimal caIardPourcentParticulier;
    private TaTCourtier taTCourtier;
    private TaTJuridique taTJuridique; 
    
	private TaContactEntreprise taContactEntreprise;
	private List<TaContactEntreprise> taContactEntreprises = new ArrayList<>(0);
	
	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_courtier", unique = true, nullable = false)
	public Integer getIdCourtier() {
		return idCourtier;
	}

	public void setIdCourtier(Integer idActivite) {
		this.idCourtier = idActivite;
	}

	@Column(name = "code_courtier")
	public String getCodeCourtier() {
		return codeCourtier;
	}

	public void setCodeCourtier(String codeCourtier) {
		this.codeCourtier = codeCourtier;
	}


//	@Column(name = "titre_fonction")
//	public String getTitreFonction() {
//		return titreFonction;
//	}
//
//	public void setTitreFonction(String titreFonction) {
//		this.titreFonction = titreFonction;
//	}

	@Column(name = "code_siret")
	public String getCodeSiret() {
		return codeSiret;
	}

	public void setCodeSiret(String codeSiret) {
		this.codeSiret = codeSiret;
	}

	@Column(name = "code_siren")
	public String getCodeSiren() {
		return codeSiren;
	}

	public void setCodeSiren(String codeSiren) {
		this.codeSiren = codeSiren;
	}

	@Column(name = "code_ape")
	public String getCodeApe() {
		return codeApe;
	}

	public void setCodeApe(String codeApe) {
		this.codeApe = codeApe;
	}

	@Column(name = "tva_intracom")
	public String getTvaIntraComm() {
		return tvaIntraComm;
	}

	public void setTvaIntraComm(String tvaIntraComm) {
		this.tvaIntraComm = tvaIntraComm;
	}

	@Column(name = "logo")
	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	@Column(name = "qualite")
	public String getQualite() {
		return qualite;
	}

	public void setQualite(String qualite) {
		this.qualite = qualite;
	}

	@Column(name = "num_orias")
	public String getNumOrias() {
		return numOrias;
	}

	public void setNumOrias(String numOrias) {
		this.numOrias = numOrias;
	}

	@Column(name = "nom_denomination_sociale")
	public String getNomDenominationSociale() {
		return nomDenominationSociale;
	}

	public void setNomDenominationSociale(String nomDenominationSociale) {
		this.nomDenominationSociale = nomDenominationSociale;
	}

	@Column(name = "num_rcs")
	public String getNumRcs() {
		return numRcs;
	}

	public void setNumRcs(String numRcs) {
		this.numRcs = numRcs;
	}

	@Column(name = "num_rc_pro")
	public String getNumRcPro() {
		return numRcPro;
	}

	public void setNumRcPro(String numRcPro) {
		this.numRcPro = numRcPro;
	}

	@Column(name = "num_greffe")
	public String getNumGreffe() {
		return numGreffe;
	}

	public void setNumGreffe(String numGreffe) {
		this.numGreffe = numGreffe;
	}

	@Column(name = "capital_social")
	public BigDecimal getCapitalSocial() {
		return capitalSocial;
	}

	public void setCapitalSocial(BigDecimal capitalSocial) {
		this.capitalSocial = capitalSocial;
	}

	@Column(name = "actif")
	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	@Column(name = "suspendu")
	public Boolean getSuspendu() {
		return suspendu;
	}

	public void setSuspendu(Boolean suspendu) {
		this.suspendu = suspendu;
	}

	@Column(name = "effectif_total")
	public Integer getEffectifTotal() {
		return effectifTotal;
	}

	public void setEffectifTotal(Integer effectifTotal) {
		this.effectifTotal = effectifTotal;
	}

	@Column(name = "date_accord_tarif_accorde_tarif")
	public Date getDateAccordTarif() {
		return dateAccordTarif;
	}

	public void setDateAccordTarif(Date dateAccordTarif) {
		this.dateAccordTarif = dateAccordTarif;
	}
	
	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL} , orphanRemoval=true)
	@JoinColumn(name = "id_contact_entreprise_ta_contact_entreprise")
	public TaContactEntreprise getTaContactEntreprise() {
		return taContactEntreprise;
	}

	public void setTaContactEntreprise(TaContactEntreprise taContactEntreprise) {
		this.taContactEntreprise = taContactEntreprise;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "taCourtier", orphanRemoval=true)
	@Fetch(FetchMode.SUBSELECT)
	public List<TaContactEntreprise> getTaContactEntreprises() {
		return taContactEntreprises;
	}

	public void setTaContactEntreprises(List<TaContactEntreprise> taContactEntreprises) {
		this.taContactEntreprises = taContactEntreprises;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_groupe_tarif_ta_t_groupe_tarif")
	public TaTGroupeTarif getTaTGroupeTarif() {
		return taTGroupeTarif;
	}

	public void setTaTGroupeTarif(TaTGroupeTarif taTGroupeTarif) {
		this.taTGroupeTarif = taTGroupeTarif;
	}

//	@Column(name = "id_ylyade_ta_ylyade")xxxxxxxx
//	public TaYlyade getTaYlyade() {
//		return taYlyade;
//	}
//
//	public void setTaYlyade(TaYlyade taYlyade) {
//		this.taYlyade = taYlyade;
//	}

	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "id_utilisateur_ta_utilisateur")
	public TaUtilisateur getTaUtilisateur() {
		return taUtilisateur;
	}

	public void setTaUtilisateur(TaUtilisateur taUtilisateur) {
		this.taUtilisateur = taUtilisateur;
	}

//	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
//	@JoinColumn(name = "id_t_civilite_ta_t_civilite")
//	public TaTCivilite getTaTCivilite() {
//		return taTCivilite;
//	}
//
//	public void setTaTCivilite(TaTCivilite taTCivilite) {
//		this.taTCivilite = taTCivilite;
//	}

	@Column(name = "com_brutes")
	public BigDecimal getComBrutes() {
		return comBrutes;
	}

	public void setComBrutes(BigDecimal comBrutes) {
		this.comBrutes = comBrutes;
	}

	@Column(name = "com_brutes_iard")
	public BigDecimal getComBrutesIard() {
		return comBrutesIard;
	}

	public void setComBrutesIard(BigDecimal comBrutesIard) {
		this.comBrutesIard = comBrutesIard;
	}

	@Column(name = "connu_ylyade")
	public String getConnuYlyade() {
		return connuYlyade;
	}

	public void setConnuYlyade(String connuYlyade) {
		this.connuYlyade = connuYlyade;
	}

	@Column(name = "ca_iard_pourcent_entreprise")
	public BigDecimal getCaIardPourcentEntreprise() {
		return caIardPourcentEntreprise;
	}

	public void setCaIardPourcentEntreprise(BigDecimal caIardPourcentEntreprise) {
		this.caIardPourcentEntreprise = caIardPourcentEntreprise;
	}

	@Column(name = "ca_iard_pourcent_particulier")
	public BigDecimal getCaIardPourcentParticulier() {
		return caIardPourcentParticulier;
	}

	public void setCaIardPourcentParticulier(BigDecimal caIardPourcentParticulier) {
		this.caIardPourcentParticulier = caIardPourcentParticulier;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_courtier_ta_t_courtier")
	public TaTCourtier getTaTCourtier() {
		return taTCourtier;
	}

	public void setTaTCourtier(TaTCourtier taTCourtier) {
		this.taTCourtier = taTCourtier;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_juridique_ta_t_juridique")
	public TaTJuridique getTaTJuridique() {
		return taTJuridique;
	}

	public void setTaTJuridique(TaTJuridique taTJuridique) {
		this.taTJuridique = taTJuridique;
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

	public void initContactEntrepriseCourtier(Object value) {
		if(this.getTaContactEntreprise()==null && (value!=null&&!value.equals(""))) {
			TaContactEntreprise p = new TaContactEntreprise();
			
			p.setTaCourtier(this);
			this.setTaContactEntreprise(p);
			this.addContactEntreprise(p);
		}else if(this.getTaContactEntreprise()!=null && (value==null||value.equals(""))) {
			this.removeContactEntreprise(this.getTaContactEntreprise());
			this.setTaContactEntreprise(null);
		}
	}
	
	public void addContactEntreprise(TaContactEntreprise taContactEntreprise){
		if(!this.getTaContactEntreprises().contains(taContactEntreprise))
			this.getTaContactEntreprises().add(taContactEntreprise);
		if(this.getTaContactEntreprise()==null)this.setTaContactEntreprise(taContactEntreprise);
	}
	
	public void removeContactEntreprise(TaContactEntreprise taContactEntreprise){
		boolean estDefaut=(this.taContactEntreprise!=null)&& this.taContactEntreprise.getIdContactEntreprise()==taContactEntreprise.getIdContactEntreprise();

		this.getTaContactEntreprises().remove(taContactEntreprise);
		if(estDefaut && !this.getTaContactEntreprises().isEmpty())
			this.setTaContactEntreprise(this.getTaContactEntreprises().iterator().next());
		else if(this.getTaContactEntreprises().isEmpty()){
			this.setTaContactEntreprise(null);
		}  
	}
	
}
