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
@Table(name = "ta_t_ged_sinistre")
public class TaTGedSinistre implements Serializable {

	private static final long serialVersionUID = -995905066918063515L;

	private Integer idTGedSinistre;
	private String codeTGedSinistre;
	private String liblTGedSinistre;
	private String decriptionTGedSinistre;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_t_ged_sinistre", unique = true, nullable = false)
	public Integer getIdTGedSinistre() {
		return idTGedSinistre;
	}

	public void setIdTGedSinistre(Integer idTGedSinistre) {
		this.idTGedSinistre = idTGedSinistre;
	}

	@Column(name = "code_doc")
	public String getCodeTGedSinistre() {
		return codeTGedSinistre;
	}

	public void setCodeTGedSinistre(String codeDoc) {
		this.codeTGedSinistre = codeDoc;
	}

	@Column(name = "libl_sinistre")
	public String getLiblTGedSinistre() {
		return liblTGedSinistre;
	}

	public void setLiblTGedSinistre(String liblSinistre) {
		this.liblTGedSinistre = liblSinistre;
	}

	@Column(name = "description")
	public String getDecriptionTGedSinistre() {
		return decriptionTGedSinistre;
	}

	public void setDecriptionTGedSinistre(String decription) {
		this.decriptionTGedSinistre = decription;
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
}
