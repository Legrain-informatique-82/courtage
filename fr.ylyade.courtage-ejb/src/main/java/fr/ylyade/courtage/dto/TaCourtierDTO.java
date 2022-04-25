package fr.ylyade.courtage.dto;

import java.math.BigDecimal;
import java.util.Date;

public class TaCourtierDTO extends ModelObject implements java.io.Serializable {

	private static final long serialVersionUID = 6816362588459165890L;

	private Integer id;
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	
	private String codeCourtier;
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
	
	private Integer idTGroupeTarif;
	private String codeGroupe;
	private String nomGroupe;
	private BigDecimal tauxGroupe;
	
	private Integer idContactEntreprise;
	private String nom;
	private String prenom;
	private String titreFonction;
	
	private Integer idUtilisateur;
	private String identifiant;
	private String password;
	private String passwordConfirm;
	
	private int idAdresse;
	private String adresseLigne1;
	private String adresseLigne2;
	private String adresseLigne3;
	private String codePostal;
	private String ville;
	private String pays;
	
	private Integer idTel;
	private String posteTel;
	private String numeroTel;
	
	private Integer idEmail;
	private String adresseEmail;

	private Integer idTCivilite;
	private String codeTCivilite;
	private String liblTCivilite;
	
	private Integer idTCourtier;
	private String codeTCourtier;
	private String liblTCourtier;
	
	private Integer idTJuridique;
	private String codeTJuridique;
	private String liblTJuridique;
	
	private BigDecimal comBrutes;
	private BigDecimal comBrutesIard;
	private String connuYlyade;
	private BigDecimal caIardPourcentEntreprise;
	private BigDecimal caIardPourcentParticulier;

	private Integer versionObj;
	
	public TaCourtierDTO() {
		
	}
	
	
	public TaCourtierDTO(Integer id, String codeCourtier, String nom, String prenom, Boolean actif,
			Boolean suspendu, Integer idTGroupeTarif, String codeGroupe, String nomGroupe, BigDecimal tauxGroupe,
			Integer idTCourtier, String codeTCourtier, String nomDenominationSociale) {
		super();
		this.id = id;
		this.codeCourtier = codeCourtier;
		this.nom = nom;
		this.prenom = prenom;
		this.actif = actif;
		this.suspendu = suspendu;
		this.idTGroupeTarif = idTGroupeTarif;
		this.codeGroupe = codeGroupe;
		this.nomGroupe = nomGroupe;
		this.tauxGroupe = tauxGroupe;
		this.idTCourtier = idTCourtier;
		this.codeTCourtier = codeTCourtier;
		this.nomDenominationSociale = nomDenominationSociale;
	}
	
	public TaCourtierDTO(Integer id, String codeCourtier, String nom, String prenom, Boolean actif,
			Boolean suspendu, Integer idTGroupeTarif, String codeGroupe, String nomGroupe, BigDecimal tauxGroupe,
			Integer idTCourtier, String codeTCourtier, String nomDenominationSociale, String numOrias) {
		super();
		this.id = id;
		this.codeCourtier = codeCourtier;
		this.nom = nom;
		this.prenom = prenom;
		this.actif = actif;
		this.suspendu = suspendu;
		this.idTGroupeTarif = idTGroupeTarif;
		this.codeGroupe = codeGroupe;
		this.nomGroupe = nomGroupe;
		this.tauxGroupe = tauxGroupe;
		this.idTCourtier = idTCourtier;
		this.codeTCourtier = codeTCourtier;
		this.nomDenominationSociale = nomDenominationSociale;
		this.numOrias = numOrias;
	}
	/*Rajout identifiant*/
	public TaCourtierDTO(Integer id, String codeCourtier, String nom, String prenom, Boolean actif,
			Boolean suspendu, Integer idTGroupeTarif, String codeGroupe, String nomGroupe, BigDecimal tauxGroupe,
			Integer idTCourtier, String codeTCourtier, String nomDenominationSociale, String numOrias, String identifiant) {
		super();
		this.id = id;
		this.codeCourtier = codeCourtier;
		this.nom = nom;
		this.prenom = prenom;
		this.actif = actif;
		this.suspendu = suspendu;
		this.idTGroupeTarif = idTGroupeTarif;
		this.codeGroupe = codeGroupe;
		this.nomGroupe = nomGroupe;
		this.tauxGroupe = tauxGroupe;
		this.idTCourtier = idTCourtier;
		this.codeTCourtier = codeTCourtier;
		this.nomDenominationSociale = nomDenominationSociale;
		this.numOrias = numOrias;
		this.identifiant = identifiant;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeCourtier() {
		return codeCourtier;
	}

	public void setCodeCourtier(String codeCourtier) {
		this.codeCourtier = codeCourtier;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTitreFonction() {
		return titreFonction;
	}

	public void setTitreFonction(String titreFonction) {
		this.titreFonction = titreFonction;
	}

	public String getCodeSiret() {
		return codeSiret;
	}

	public void setCodeSiret(String codeSiret) {
		this.codeSiret = codeSiret;
	}

	public String getCodeSiren() {
		return codeSiren;
	}

	public void setCodeSiren(String codeSiren) {
		this.codeSiren = codeSiren;
	}

	public String getCodeApe() {
		return codeApe;
	}

	public void setCodeApe(String codeApe) {
		this.codeApe = codeApe;
	}

	public String getTvaIntraComm() {
		return tvaIntraComm;
	}

	public void setTvaIntraComm(String tvaIntraComm) {
		this.tvaIntraComm = tvaIntraComm;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public String getQualite() {
		return qualite;
	}

	public void setQualite(String qualite) {
		this.qualite = qualite;
	}

	public String getNumOrias() {
		return numOrias;
	}

	public void setNumOrias(String numOrias) {
		this.numOrias = numOrias;
	}

	public String getNomDenominationSociale() {
		return nomDenominationSociale;
	}

	public void setNomDenominationSociale(String nomDenominationSociale) {
		this.nomDenominationSociale = nomDenominationSociale;
	}

	public String getNumRcs() {
		return numRcs;
	}

	public void setNumRcs(String numRcs) {
		this.numRcs = numRcs;
	}

	public String getNumRcPro() {
		return numRcPro;
	}

	public void setNumRcPro(String numRcPro) {
		this.numRcPro = numRcPro;
	}

	public String getNumGreffe() {
		return numGreffe;
	}

	public void setNumGreffe(String numGreffe) {
		this.numGreffe = numGreffe;
	}

	public BigDecimal getCapitalSocial() {
		return capitalSocial;
	}

	public void setCapitalSocial(BigDecimal capitalSocial) {
		this.capitalSocial = capitalSocial;
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	public Boolean getSuspendu() {
		return suspendu;
	}

	public void setSuspendu(Boolean suspendu) {
		this.suspendu = suspendu;
	}

	public Integer getEffectifTotal() {
		return effectifTotal;
	}

	public void setEffectifTotal(Integer effectifTotal) {
		this.effectifTotal = effectifTotal;
	}

	public Date getDateAccordTarif() {
		return dateAccordTarif;
	}

	public void setDateAccordTarif(Date dateAccordTarif) {
		this.dateAccordTarif = dateAccordTarif;
	}

	public Integer getIdTGroupeTarif() {
		return idTGroupeTarif;
	}

	public void setIdTGroupeTarif(Integer idTGroupeTarif) {
		this.idTGroupeTarif = idTGroupeTarif;
	}

	public String getCodeGroupe() {
		return codeGroupe;
	}

	public void setCodeGroupe(String codeGroupe) {
		this.codeGroupe = codeGroupe;
	}

	public String getNomGroupe() {
		return nomGroupe;
	}

	public void setNomGroupe(String nomGroupe) {
		this.nomGroupe = nomGroupe;
	}

	public BigDecimal getTauxGroupe() {
		return tauxGroupe;
	}

	public void setTauxGroupe(BigDecimal tauxGroupe) {
		this.tauxGroupe = tauxGroupe;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public Integer getIdTCivilite() {
		return idTCivilite;
	}

	public void setIdTCivilite(Integer idTCivilite) {
		this.idTCivilite = idTCivilite;
	}

	public String getCodeTCivilite() {
		return codeTCivilite;
	}

	public void setCodeTCivilite(String codeTCivilite) {
		this.codeTCivilite = codeTCivilite;
	}

	public String getLiblTCivilite() {
		return liblTCivilite;
	}

	public void setLiblTCivilite(String liblTCivilite) {
		this.liblTCivilite = liblTCivilite;
	}

	public Integer getIdTCourtier() {
		return idTCourtier;
	}

	public void setIdTCourtier(Integer idTCourtier) {
		this.idTCourtier = idTCourtier;
	}

	public String getCodeTCourtier() {
		return codeTCourtier;
	}

	public void setCodeTCourtier(String codeTCourtier) {
		this.codeTCourtier = codeTCourtier;
	}

	public String getLiblTCourtier() {
		return liblTCourtier;
	}

	public void setLiblTCourtier(String liblTCourtier) {
		this.liblTCourtier = liblTCourtier;
	}

	public Integer getIdTJuridique() {
		return idTJuridique;
	}

	public void setIdTJuridique(Integer idTJuridique) {
		this.idTJuridique = idTJuridique;
	}

	public String getCodeTJuridique() {
		return codeTJuridique;
	}

	public void setCodeTJuridique(String codeTJuridique) {
		this.codeTJuridique = codeTJuridique;
	}

	public String getLiblTJuridique() {
		return liblTJuridique;
	}

	public void setLiblTJuridique(String liblTJuridique) {
		this.liblTJuridique = liblTJuridique;
	}

	public BigDecimal getComBrutes() {
		return comBrutes;
	}

	public void setComBrutes(BigDecimal comBrutes) {
		this.comBrutes = comBrutes;
	}

	public BigDecimal getComBrutesIard() {
		return comBrutesIard;
	}

	public void setComBrutesIard(BigDecimal comBrutesIard) {
		this.comBrutesIard = comBrutesIard;
	}

	public String getConnuYlyade() {
		return connuYlyade;
	}

	public void setConnuYlyade(String connuYlyade) {
		this.connuYlyade = connuYlyade;
	}

	public BigDecimal getCaIardPourcentEntreprise() {
		return caIardPourcentEntreprise;
	}

	public void setCaIardPourcentEntreprise(BigDecimal caIardPourcentEntreprise) {
		this.caIardPourcentEntreprise = caIardPourcentEntreprise;
	}

	public BigDecimal getCaIardPourcentParticulier() {
		return caIardPourcentParticulier;
	}

	public void setCaIardPourcentParticulier(BigDecimal caIardPourcentParticulier) {
		this.caIardPourcentParticulier = caIardPourcentParticulier;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public String getAdresseLigne1() {
		return adresseLigne1;
	}

	public void setAdresseLigne1(String adresseLigne1) {
		this.adresseLigne1 = adresseLigne1;
	}

	public String getAdresseLigne2() {
		return adresseLigne2;
	}

	public void setAdresseLigne2(String adresseLigne2) {
		this.adresseLigne2 = adresseLigne2;
	}

	public String getAdresseLigne3() {
		return adresseLigne3;
	}

	public void setAdresseLigne3(String adresseLigne3) {
		this.adresseLigne3 = adresseLigne3;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getPosteTel() {
		return posteTel;
	}

	public void setPosteTel(String posteTel) {
		this.posteTel = posteTel;
	}

	public String getNumeroTel() {
		return numeroTel;
	}

	public void setNumeroTel(String numeroTel) {
		this.numeroTel = numeroTel;
	}

	public String getAdresseEmail() {
		return adresseEmail;
	}

	public void setAdresseEmail(String adresseEmail) {
		this.adresseEmail = adresseEmail;
	}
	
	public Integer getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(Integer idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Integer getIdContactEntreprise() {
		return idContactEntreprise;
	}


	public void setIdContactEntreprise(Integer idContactEntreprise) {
		this.idContactEntreprise = idContactEntreprise;
	}


	public int getIdAdresse() {
		return idAdresse;
	}


	public void setIdAdresse(int idAdresse) {
		this.idAdresse = idAdresse;
	}


	public Integer getIdTel() {
		return idTel;
	}


	public void setIdTel(Integer idTel) {
		this.idTel = idTel;
	}


	public Integer getIdEmail() {
		return idEmail;
	}


	public void setIdEmail(Integer idEmail) {
		this.idEmail = idEmail;
	}


	public String getNomFichier() {
		return nomFichier;
	}


	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}


	public Integer getTaille() {
		return taille;
	}


	public void setTaille(Integer taille) {
		this.taille = taille;
	}


	public String getTypeMime() {
		return typeMime;
	}


	public void setTypeMime(String typeMime) {
		this.typeMime = typeMime;
	}


	public String getPasswordConfirm() {
		return passwordConfirm;
	}


	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
}
