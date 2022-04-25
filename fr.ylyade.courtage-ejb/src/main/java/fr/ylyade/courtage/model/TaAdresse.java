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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "ta_adresse")
public class TaAdresse implements Serializable {

	private static final long serialVersionUID = 9130450977692285927L;
	
	private int idAdresse;
	private String adresseLigne1;
	private String adresseLigne2;
	private String adresseLigne3;
	private String codePostal;
	private String ville;
	private String pays;
	private TaTAdresse taTAdresse;
	private TaContactEntreprise taContactEntreprise; 

	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_adresse", unique = true, nullable = false)
	public int getIdAdresse() {
		return idAdresse;
	}

	public void setIdAdresse(int idAdresse) {
		this.idAdresse = idAdresse;
	}

	@Column(name = "adresse_ligne_1")
	public String getAdresseLigne1() {
		return adresseLigne1;
	}

	public void setAdresseLigne1(String adresseLigne1) {
		this.adresseLigne1 = adresseLigne1;
	}

	@Column(name = "adresse_ligne_2")
	public String getAdresseLigne2() {
		return adresseLigne2;
	}

	public void setAdresseLigne2(String adresseLigne2) {
		this.adresseLigne2 = adresseLigne2;
	}

	@Column(name = "adresse_ligne_3")
	public String getAdresseLigne3() {
		return adresseLigne3;
	}

	public void setAdresseLigne3(String adresseLigne3) {
		this.adresseLigne3 = adresseLigne3;
	}

	@Column(name = "code_postal")
	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	@Column(name = "ville")
	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}
	
	@Column(name = "pays")
	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_adresse_ta_t_adresse")
	public TaTAdresse getTaTAdresse() {
		return taTAdresse;
	}

	public void setTaTAdresse(TaTAdresse taTAdresse) {
		this.taTAdresse = taTAdresse;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "id_contact_entreprise_ta_contact_entreprise")
	public TaContactEntreprise getTaContactEntreprise() {
		return taContactEntreprise;
	}

	public void setTaContactEntreprise(TaContactEntreprise taContactEntreprise) {
		this.taContactEntreprise = taContactEntreprise;
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
	
	@PrePersist
	@PreUpdate
	public void beforePost ()throws Exception{
			  if(adresseLigne1!= null) {
					this.setAdresseLigne1(adresseLigne1.toUpperCase());
	
			  }
			 if(adresseLigne2!= null) {
				 this.setAdresseLigne2(adresseLigne2.toUpperCase());
				  
					  }
			 if(adresseLigne3!= null) {
				 this.setAdresseLigne3(adresseLigne3.toUpperCase());
			
			 }
			 if(codePostal!= null) {
				 this.setCodePostal(codePostal.toUpperCase());
			
			 }
			 if(ville != null) {
				 this.setVille(ville.toUpperCase());
			 }
			 if(pays != null) {
				 this.setPays(pays.toUpperCase());
			 }
		 
		 

	}
	
	
}
