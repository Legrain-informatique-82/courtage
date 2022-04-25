package fr.ylyade.courtage.model;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;



@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "ta_liste_ref_doc")
//@SequenceGenerator(name = "gen_proforma", sequenceName = "num_id_proforma", allocationSize = 1)
@NamedQueries(value = { 
	@NamedQuery(name=TaListeRefDoc.QN.FIND_BY_TYPE, 
		query="select new fr.ylyade.courtage.dto.TaListeRefDocDTO( f.idListeRefDoc, f.codeListeRefDoc, f.liblDoc, f.description, f.obligatoire, f.taTListeRefDoc )"
				+ "from TaListeRefDoc f where f.taTListeRefDoc.idTListeRefDoc = :idTListeRefDoc and f.actif=true"),
	@NamedQuery(name=TaListeRefDoc.QN.FIND_BY_TYPE_NOT_IN_GED_UTILISATEUR, 
	query="select new fr.ylyade.courtage.dto.TaListeRefDocDTO( l.idListeRefDoc, l.codeListeRefDoc, l.liblDoc, l.description, l.obligatoire, l.taTListeRefDoc )"
			+ "from TaListeRefDoc l "
			+ "where "
			+ "l.taTListeRefDoc.idTListeRefDoc = :idTListeRefDoc "
			+ "and l.actif=true "
			+ "and l.idListeRefDoc "
			+ "not in (select g.taListeRefDoc.idListeRefDoc from TaGedUtilisateur g where g.taDossierRcd.idDossierRcd = :idDossierRcd)" ),
	
	@NamedQuery(name=TaListeRefDoc.QN.FIND_BY_TYPE_IN_GED_UTILISATEUR, 
			query="select new fr.ylyade.courtage.dto.TaListeRefDocDTO( l.idListeRefDoc, l.codeListeRefDoc, l.liblDoc, l.description, l.obligatoire, l.taTListeRefDoc )"
					+ "from TaListeRefDoc l "
					+ "where "
					+ "l.taTListeRefDoc.idTListeRefDoc = :idTListeRefDoc "
					+ "and l.actif=true "
					+ "and l.idListeRefDoc "
					+ "in (select g.taListeRefDoc.idListeRefDoc from TaGedUtilisateur g where g.taDossierRcd.idDossierRcd = :idDossierRcd)" )

})	

//select l.id_liste_ref_doc, l.libl_doc
//from ta_liste_ref_doc l 
//WHERE l.id_t_liste_ref_doc_ta_t_liste_ref_doc= 1 
//and l.actif=true
//and l.id_liste_ref_doc 
// not in( select 
//id_liste_ref_doc_ta_liste_ref_doc 
//from ta_ged_utilisateur g  
//WHERE g.id_devis_rc_pro_ta_devis_rc_pro = 48 ) 
		
public class TaListeRefDoc implements Serializable {

	private static final long serialVersionUID = 7759531771339500150L;
	
	public static final String CODE_PROPOSITION_SIGNE = "PieceRcPro11";
	
	
	
	public static class QN {
		public static final String FIND_BY_TYPE = "TaListeRefDoc.findByType";
		public static final String FIND_BY_TYPE_NOT_IN_GED_UTILISATEUR = "TaListeRefDoc.findByTypeNotInGedUtilisateur";
		public static final String FIND_BY_TYPE_IN_GED_UTILISATEUR = "TaListeRefDoc.findByTypeInGedUtilisateur";
	}
	
	private Integer idListeRefDoc;
	private String codeListeRefDoc;
	private String liblDoc;
	private String description;
	private TaTListeRefDoc taTListeRefDoc;
	private Boolean actif;
	private Boolean obligatoire;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_liste_ref_doc", unique = true, nullable = false)
	public Integer getIdListeRefDoc() {
		return idListeRefDoc;
	}

	public void setIdListeRefDoc(Integer idListeRefDoc) {
		this.idListeRefDoc = idListeRefDoc;
	}

	@Column(name = "libl_doc")
	public String getLiblDoc() {
		return liblDoc;
	}

	public void setLiblDoc(String liblDoc) {
		this.liblDoc = liblDoc;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_liste_ref_doc_ta_t_liste_ref_doc")
	public TaTListeRefDoc getTaTListeRefDoc() {
		return taTListeRefDoc;
	}

	public void setTaTListeRefDoc(TaTListeRefDoc taTListeRefDoc) {
		this.taTListeRefDoc = taTListeRefDoc;
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
	@Column(name = "code_liste_ref_doc")
	public String getCodeListeRefDoc() {
		return codeListeRefDoc;
	}

	public void setCodeListeRefDoc(String codeListeRefDoc) {
		this.codeListeRefDoc = codeListeRefDoc;
	}

	@Column(name = "actif")
	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	@Column(name = "obligatoire")
	public Boolean getObligatoire() {
		return obligatoire;
	}

	public void setObligatoire(Boolean obligatoire) {
		this.obligatoire = obligatoire;
	}
}
