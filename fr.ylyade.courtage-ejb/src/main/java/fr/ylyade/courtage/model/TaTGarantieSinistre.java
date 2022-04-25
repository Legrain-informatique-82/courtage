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
@Table(name = "ta_t_garantie_sinistre")
public class TaTGarantieSinistre implements Serializable {
	
	private static final long serialVersionUID = 6081402864609339319L;
	
	public static final String GARANTIE_RD = "GRD";
	public static final String GARANTIE_AUTRES = "GA";
	
	private Integer idTGarantieSinistre;
	private String liblTGarantieSinistre;
	private String codeTGarantieSinistre;

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_t_garantie_sinistre", unique = true, nullable = false)
	public Integer getIdTGarantieSinistre() {
		return idTGarantieSinistre;
	}

	public void setIdTGarantieSinistre(Integer idTGarantieSinistre) {
		this.idTGarantieSinistre = idTGarantieSinistre;
	}
	
	@Column(name = "code_t_garantie_sinistre")
	public String getCodeTGarantieSinistre() {
		return codeTGarantieSinistre;
	}

	public void setCodeTGarantieSinistre(String codeTGarantieSinistre) {
		this.codeTGarantieSinistre = codeTGarantieSinistre;
	}

	@Column(name = "libl_t_garantie_sinistre")
	public String getLiblTGarantieSinistre() {
		return liblTGarantieSinistre;
	}

	public void setLiblTGarantieSinistre(String liblTGarantieSinistre) {
		this.liblTGarantieSinistre = liblTGarantieSinistre;
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
