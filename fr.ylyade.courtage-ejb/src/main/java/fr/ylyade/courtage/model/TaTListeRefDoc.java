package fr.ylyade.courtage.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "ta_t_liste_ref_doc")
public class TaTListeRefDoc implements Serializable {

	private static final long serialVersionUID = 6947466829841850561L;
	
	public static final String TYPE_LISTE_REF_DOC_COURTIER = "court";
	public static final Integer ID_TYPE_LISTE_REF_DOC_RCPRO= 1;
	public static final Integer ID_TYPE_LISTE_REF_DOC_COURTIER= 2;
	public static final Integer ID_TYPE_LISTE_REF_DOC_GFA= 4;
	public static final Integer ID_TYPE_LISTE_REF_DOC_DOMMAGE_OUVRAGE= 5;
	
	
	private Integer idTListeRefDoc;
	private String codeTListeRefDoc; 
	private String liblTListeRefDoc;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_t_liste_ref_doc", unique = true, nullable = false)
	public Integer getIdTListeRefDoc() {
		return idTListeRefDoc;
	}

	public void setIdTListeRefDoc(Integer idTListeRefDoc) {
		this.idTListeRefDoc = idTListeRefDoc;
	}

	@Column(name = "libl")
	public String getLiblTListeRefDoc() {
		return liblTListeRefDoc;
	}

	public void setLiblTListeRefDoc(String libl) {
		this.liblTListeRefDoc = libl;
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

	@Column(name = "code_t_liste_ref_doc")
	public String getCodeTListeRefDoc() {
		return codeTListeRefDoc;
	}

	public void setCodeTListeRefDoc(String codeTListeRefDoc) {
		this.codeTListeRefDoc = codeTListeRefDoc;
	}
}
