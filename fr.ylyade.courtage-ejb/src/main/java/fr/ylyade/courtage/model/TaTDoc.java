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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import fr.ylyade.courtage.dto.TaTAssuranceDTO;

@Entity
@Table(name = "ta_t_doc")
public class TaTDoc implements Serializable {

	private static final long serialVersionUID = -4413274026923504381L;

	private Integer idTDoc;
	private String liblTDoc;
	private String codeTDoc; //a rajouter dans bdd
	private String descriptionTDoc;
	
	private TaTAssurance taTAssurance;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_t_doc", unique = true, nullable = false)
	public Integer getIdTDoc() {
		return idTDoc;
	}

	public void setIdTDoc(Integer idTDoc) {
		this.idTDoc = idTDoc;
	}
	
	@Column(name = "libl_doc")
	public String getLiblTDoc() {
		return liblTDoc;
	}

	public void setLiblTDoc(String liblDoc) {
		this.liblTDoc = liblDoc;
	}

	@Column(name = "description_doc")
	public String getDescriptionTDoc() {
		return descriptionTDoc;
	}

	public void setDescriptionTDoc(String descriptionDoc) {
		this.descriptionTDoc = descriptionDoc;
	}
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_assurance_ta_t_assurance")
	public TaTAssurance getTaTAssurance() {
		return taTAssurance;
	}

	public void setTaTAssurance(TaTAssurance taTAssurance) {
		this.taTAssurance = taTAssurance;
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
	@Column(name = "code_t_doc")
	public String getCodeTDoc() {
		return codeTDoc;
	}

	public void setCodeTDoc(String codeTDoc) {
		this.codeTDoc = codeTDoc;
	}
}
