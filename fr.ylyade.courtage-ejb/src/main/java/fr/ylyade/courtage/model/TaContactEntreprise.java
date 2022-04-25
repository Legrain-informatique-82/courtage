package fr.ylyade.courtage.model;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "ta_contact_entreprise")
public class TaContactEntreprise implements Serializable {

	private static final long serialVersionUID = -1904445482220698716L;

	private Integer idContactEntreprise;
	
	
	private String nom;
	private String prenom;
	private String fonction;
	private TaTCivilite taTCivilite;
	private TaAdresse taAdresse;
	private TaTel taTel;
	private TaEmail taEmail;
	
	private TaCourtier taCourtier; //--faire une contrainte sur la base, une seule liaison possible à la foi
	private TaAssure taAssure;
	
	private List<TaAdresse> taAdresses = new ArrayList<>(0);
	private List<TaTel> taTels = new ArrayList<>(0);
	private List<TaEmail> taEmails = new ArrayList<>(0);
	
	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_contact_entreprise", unique = true, nullable = false)
	public Integer getIdContactEntreprise() {
		return idContactEntreprise;
	}

	public void setIdContactEntreprise(Integer idContactEntreprise) {
		this.idContactEntreprise = idContactEntreprise;
	}

	@Column(name = "nom")
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Column(name = "prenom")
	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Column(name = "fonction")
	public String getFonction() {
		return fonction;
	}

	public void setFonction(String fonction) {
		this.fonction = fonction;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_civilite_ta_t_civilite")
	public TaTCivilite getTaTCivilite() {
		return taTCivilite;
	}

	public void setTaTCivilite(TaTCivilite taTCivilite) {
		this.taTCivilite = taTCivilite;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_courtier_ta_courtier")
	public TaCourtier getTaCourtier() {
		return taCourtier;
	}

	public void setTaCourtier(TaCourtier taCourtier) {
		this.taCourtier = taCourtier;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_assure_ta_assure")
	public TaAssure getTaAssure() {
		return taAssure;
	}

	public void setTaAssure(TaAssure taAssure) {
		this.taAssure = taAssure;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "taContactEntreprise", orphanRemoval=true)
	@Fetch(FetchMode.SUBSELECT)
	public List<TaAdresse> getTaAdresses() {
		return taAdresses;
	}

	public void setTaAdresses(List<TaAdresse> taAdresses) {
		this.taAdresses = taAdresses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "taContactEntreprise", orphanRemoval=true)
	@Fetch(FetchMode.SUBSELECT)
	public List<TaTel> getTaTels() {
		return taTels;
	}

	public void setTaTels(List<TaTel> taTels) {
		this.taTels = taTels;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "taContactEntreprise", orphanRemoval=true)
	@Fetch(FetchMode.SUBSELECT)
	public List<TaEmail> getTaEmails() {
		return taEmails;
	}

	public void setTaEmails(List<TaEmail> taEmails) {
		this.taEmails = taEmails;
	}
	
	//@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.REFRESH} , orphanRemoval=true)
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL , orphanRemoval=true)
	@JoinColumn(name = "id_adresse_ta_adresse")
	public TaAdresse getTaAdresse() {
		return taAdresse;
	}

	public void setTaAdresse(TaAdresse taAdresse) {
		this.taAdresse = taAdresse;
	}

	//@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.REFRESH} , orphanRemoval=true)
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL , orphanRemoval=true)
	@JoinColumn(name = "id_tel_ta_tel")
	public TaTel getTaTel() {
		return taTel;
	}

	public void setTaTel(TaTel taTel) {
		this.taTel = taTel;
	}

	//@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.REFRESH} , orphanRemoval=true)
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL , orphanRemoval=true)
	@JoinColumn(name = "id_email_ta_email")
	public TaEmail getTaEmail() {
		return taEmail;
	}

	public void setTaEmail(TaEmail taEmail) {
		this.taEmail = taEmail;
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
	
	public void initTelephoneDefaut(Object value) {
		if(this.getTaTel()==null&& (value!=null&&!value.equals(""))) {
			TaTel p = new TaTel();
			
			p.setTaContactEntreprise(this);
			this.setTaTel(p);
			this.addTelephone(p);
		}else if(this.getTaTel()!=null&& (value==null||value.equals(""))) {
			this.removeTelephone(this.getTaTel());
			this.setTaTel(null);
		}
	}

	public void initAdresseDefaut(Object value,boolean adresseEstRempli) {
		if(this.getTaAdresse()==null && (adresseEstRempli || (value!=null && !value.equals("")))) {
			TaAdresse p = new TaAdresse();

			p.setTaContactEntreprise(this);
			this.setTaAdresse(p);
			this.addAdresse(p);
		}else if(this.getTaAdresse()!=null && !adresseEstRempli) {
			this.removeAdresse(this.getTaAdresse());
			this.setTaAdresse(null);
		}
	}

	public void initEmailDefaut(Object value) {
		if(this.getTaEmail()==null&& (value!=null&&!value.equals(""))) {
			TaEmail p = new TaEmail();
			
			p.setTaContactEntreprise(this);
			this.setTaEmail(p);
			this.addEmail(p);
		}else if(this.getTaEmail()!=null&& (value==null||value.equals(""))) {
			this.removeEmail(this.getTaEmail());
			this.setTaEmail(null);
		}
	}
	
	public void addAdresse(TaAdresse taAdresse){
		if(!this.getTaAdresses().contains(taAdresse))
			this.getTaAdresses().add(taAdresse);
		if(this.getTaAdresse()==null)this.setTaAdresse(taAdresse);
	}
	
	public void addTelephone(TaTel taTelephone){
		if(!this.getTaTels().contains(taTelephone))
			this.getTaTels().add(taTelephone);
		if(this.getTaTel()==null)this.setTaTel(taTelephone);
	}
	
	public void removeTelephone(TaTel taTelephone){
		boolean estDefaut=(this.taTel!=null)&& this.taTel.getIdTel()==taTelephone.getIdTel();

		this.getTaTels().remove(taTelephone);
		if(estDefaut && !this.getTaTels().isEmpty())
			this.setTaTel(this.getTaTels().iterator().next());
		else if(this.getTaTels().isEmpty()){
			this.setTaTel(null);
		}  
	}
	
	public void removeAdresse(TaAdresse taAdresse){
		boolean estDefaut=(this.taAdresse!=null)&& this.taAdresse.getIdAdresse()==taAdresse.getIdAdresse();

		this.getTaAdresses().remove(taAdresse);
		if(estDefaut && !this.getTaAdresses().isEmpty())
			this.setTaAdresse(this.getTaAdresses().iterator().next());
		else if(this.getTaAdresses().isEmpty()){
			this.setTaAdresse(null);
		}
	}
	
	public void addEmail(TaEmail taEmail){
		if(!this.getTaEmails().contains(taEmail))
			this.getTaEmails().add(taEmail);
		if(this.getTaEmail()==null)this.setTaEmail(taEmail);
	}
	
	public void removeEmail(TaEmail taEmail){
		boolean estDefaut=(this.taEmail!=null)&& this.taEmail.getIdEmail()==taEmail.getIdEmail();

		this.getTaEmails().remove(taEmail);
		if(estDefaut && !this.getTaEmails().isEmpty())
			this.setTaEmail(this.getTaEmails().iterator().next());
		else if(this.getTaEmails().isEmpty()){
			this.setTaEmail(null);
		}
	}
	
	@PrePersist
	@PreUpdate
	public void beforePost ()throws Exception{
		if(nom != null) {
			this.setNom(nom.toUpperCase()); 
		}
		if(prenom != null) {
			this.setPrenom(prenom.toUpperCase());
		}
		
	}


}
