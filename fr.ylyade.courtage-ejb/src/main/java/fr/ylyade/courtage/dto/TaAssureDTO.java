package fr.ylyade.courtage.dto;

import java.math.BigDecimal;
import java.util.Date;

public class TaAssureDTO extends ModelObject implements java.io.Serializable {

	
	private static final long serialVersionUID = -8093592481387286988L;

	private Integer id;
	private String codeAssure;

	private String codeSiret;
	private String codeSiren;
	private String codeApe;
	private String tvaIntraComm;
	private String qualifications;
	private String diplome;
	private String raisonSociale;
	private Date dateCreation;
	private Date dateClotureBilan;
	private Boolean actif = false;
	private Boolean client = false;
	private Boolean domiciliationEtranger = false;
	private String activitePrincipale;
	private Date dateNaissance;
	private BigDecimal capital;
	private String imatRegistreCommSocieteVille;
	private String numImat;
	private BigDecimal chiffreAffaire;
	private BigDecimal chiffreAffaireExerciceAnterieur;
	private BigDecimal chiffreAffaireExercicePrevisionnel;
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	
	private Integer effectifTotalExercice;
	private Integer effectifTotalExerciceAnterieur;
	
	private Integer idContactEntreprise;
	private String nom;
	private String prenom;
	private String titreFonction;
	
	private Integer idUtilisateur;
	private String identifiant;
	private String password;
	private String passwordConfirm;
	
	private Integer idAdresse;
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
	
	private Integer idTAssure;
	private String codeTAssure;
	private String liblTAssure;
	
	private Integer idTJuridique;
	private String codeTJuridique;
	private String liblTJuridique;
	
	private Integer idCourtier;
	private String codeCourtier;
	private String nomDenominationSociale;

	private Integer versionObj;
	
	public TaAssureDTO() {
		
	}
	
	
	public TaAssureDTO(Integer id, String codeAssure, String codeSiret, String raisonSociale, Boolean actif,
			Date dateNaissance, Boolean client, String nom, String prenom, Integer idTAssure, String codeTAssure, Integer idCourtier,
			String codeCourtier) {
		super();
		this.id = id;
		this.codeAssure = codeAssure;
		this.codeSiret = codeSiret;
		this.raisonSociale = raisonSociale;
		this.actif = actif;
		this.dateNaissance = dateNaissance;
		this.client = client;
		this.nom = nom;
		this.prenom = prenom;
		this.idTAssure = idTAssure;
		this.codeTAssure = codeTAssure;
		this.idCourtier = idCourtier;
		this.codeCourtier = codeCourtier;
	}
	/**Constructeur pour liste extraction**/
	public TaAssureDTO(Integer id, String codeAssure, String codeSiret, String raisonSociale, Boolean actif,
			Date dateNaissance, Boolean client, String nom, String prenom, Integer idTAssure, String codeTAssure, Integer idCourtier,
			String codeCourtier, String liblTCivilite, String adresseEmail, String numeroTel,  String adresseLigne1 ,  String adresseLigne2,  String adresseLigne3,  String ville,  String pays,  String codePostal) {
		super();
		this.id = id;
		this.codeAssure = codeAssure;
		this.codeSiret = codeSiret;
		this.raisonSociale = raisonSociale;
		this.actif = actif;
		this.dateNaissance = dateNaissance;
		this.client = client;
		this.nom = nom;
		this.prenom = prenom;
		this.idTAssure = idTAssure;
		this.codeTAssure = codeTAssure;
		this.idCourtier = idCourtier;
		this.codeCourtier = codeCourtier;
		this.liblTCivilite = liblTCivilite;
		this.adresseEmail = adresseEmail;
		this.numeroTel = numeroTel;
		this.adresseLigne1 = adresseLigne1;
		this.adresseLigne2 = adresseLigne2;
		this.adresseLigne3 = adresseLigne3;
		this.ville = ville;
		this.pays = pays;
		this.codePostal = codePostal;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getCodeAssure() {
		return codeAssure;
	}

	public void setCodeAssure(String codeAssure) {
		this.codeAssure = codeAssure;
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

	public String getQualifications() {
		return qualifications;
	}

	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}

	public String getDiplome() {
		return diplome;
	}

	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}

	public String getRaisonSociale() {
		return raisonSociale;
	}

	public void setRaisonSociale(String raisonSociale) {
		this.raisonSociale = raisonSociale;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateClotureBilan() {
		return dateClotureBilan;
	}

	public void setDateClotureBilan(Date dateClotureBilan) {
		this.dateClotureBilan = dateClotureBilan;
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	public String getActivitePrincipale() {
		return activitePrincipale;
	}

	public void setActivitePrincipale(String activitePrincipale) {
		this.activitePrincipale = activitePrincipale;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public BigDecimal getCapital() {
		return capital;
	}

	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}

	public String getImatRegistreCommSocieteVille() {
		return imatRegistreCommSocieteVille;
	}

	public void setImatRegistreCommSocieteVille(String imatRegistreCommSocieteVille) {
		this.imatRegistreCommSocieteVille = imatRegistreCommSocieteVille;
	}

	public String getNumImat() {
		return numImat;
	}

	public void setNumImat(String numImat) {
		this.numImat = numImat;
	}

	public BigDecimal getChiffreAffaire() {
		return chiffreAffaire;
	}

	public void setChiffreAffaire(BigDecimal chiffreAffaire) {
		this.chiffreAffaire = chiffreAffaire;
	}

	public BigDecimal getChiffreAffaireExerciceAnterieur() {
		return chiffreAffaireExerciceAnterieur;
	}

	public void setChiffreAffaireExerciceAnterieur(BigDecimal chiffreAffaireExerciceAnterieur) {
		this.chiffreAffaireExerciceAnterieur = chiffreAffaireExerciceAnterieur;
	}

	public BigDecimal getChiffreAffaireExercicePrevisionnel() {
		return chiffreAffaireExercicePrevisionnel;
	}

	public void setChiffreAffaireExercicePrevisionnel(BigDecimal chiffreAffaireExercicePrevisionnel) {
		this.chiffreAffaireExercicePrevisionnel = chiffreAffaireExercicePrevisionnel;
	}

	public Integer getEffectifTotalExercice() {
		return effectifTotalExercice;
	}

	public void setEffectifTotalExercice(Integer effectifTotalExercice) {
		this.effectifTotalExercice = effectifTotalExercice;
	}

	public Integer getEffectifTotalExerciceAnterieur() {
		return effectifTotalExerciceAnterieur;
	}

	public void setEffectifTotalExerciceAnterieur(Integer effectifTotalExerciceAnterieur) {
		this.effectifTotalExerciceAnterieur = effectifTotalExerciceAnterieur;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
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

	public Integer getIdContactEntreprise() {
		return idContactEntreprise;
	}

	public void setIdContactEntreprise(Integer idContactEntreprise) {
		this.idContactEntreprise = idContactEntreprise;
	}

	public Integer getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(Integer idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getIdAdresse() {
		return idAdresse;
	}

	public void setIdAdresse(Integer idAdresse) {
		this.idAdresse = idAdresse;
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

	public Integer getIdTel() {
		return idTel;
	}

	public void setIdTel(Integer idTel) {
		this.idTel = idTel;
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

	public Integer getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(Integer idEmail) {
		this.idEmail = idEmail;
	}

	public String getAdresseEmail() {
		return adresseEmail;
	}

	public void setAdresseEmail(String adresseEmail) {
		this.adresseEmail = adresseEmail;
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

	public Integer getIdTAssure() {
		return idTAssure;
	}

	public void setIdTAssure(Integer idTAssure) {
		this.idTAssure = idTAssure;
	}

	public String getCodeTAssure() {
		return codeTAssure;
	}

	public void setCodeTAssure(String codeTAssure) {
		this.codeTAssure = codeTAssure;
	}

	public String getLiblTAssure() {
		return liblTAssure;
	}

	public void setLiblTAssure(String liblTAssure) {
		this.liblTAssure = liblTAssure;
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

	public Integer getIdCourtier() {
		return idCourtier;
	}

	public void setIdCourtier(Integer idCourtier) {
		this.idCourtier = idCourtier;
	}

	public String getCodeCourtier() {
		return codeCourtier;
	}

	public void setCodeCourtier(String codeCourtier) {
		this.codeCourtier = codeCourtier;
	}

	public Boolean getDomiciliationEtranger() {
		return domiciliationEtranger;
	}

	public void setDomiciliationEtranger(Boolean domiciliationEtranger) {
		this.domiciliationEtranger = domiciliationEtranger;
	}


	public String getPasswordConfirm() {
		return passwordConfirm;
	}


	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}


	public String getNomDenominationSociale() {
		return nomDenominationSociale;
	}


	public void setNomDenominationSociale(String nomDenominationSociale) {
		this.nomDenominationSociale = nomDenominationSociale;
	}


	public Boolean getClient() {
		return client;
	}


	public void setClient(Boolean client) {
		this.client = client;
	}

}
