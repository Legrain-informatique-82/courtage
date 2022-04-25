package fr.ylyade.courtage.dto;

import java.util.Date;



public class TaGedUtilisateurDTO extends ModelObject implements java.io.Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2480607661632693977L;

	private Integer id;
	
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	
	
	
	private byte[] fichierDoc;
	//private Integer fichierDoc;
	private Boolean validationCourtier = null;
	private String commentaireCourtier;
	private Boolean validationYlyade = null;
	private String commentaireYlyade;
	private Boolean validationSuperAssureur=null;
	private String commentaireSuperAssureur;
	private Date dateControleCourtier;
	private Date dateControleYlyade;
	private Date dateDepot;

    
    private Integer idCourtier;
    private String codeCourtier;
    
    private Integer idDevisRcPro;
    private String numDevis;
    
    private Integer idListeRefDoc;
    private String codeListeRefDoc;
    private String liblDoc;
    private String description;
    private Boolean obligatoire;
    
    
    
    
    
    
	private Integer versionObj;

	
	public TaGedUtilisateurDTO() {}
	
	public TaGedUtilisateurDTO(
			Integer id,
			String nomFichier,
			Integer taille,
			String typeMime,
			byte[] fichierDoc,
			Boolean validationCourtier,
			String commentaireCourtier,
			Boolean validationYlyade,
			String commentaireYlyade,
			Boolean validationSuperAssureur,
			String commentaireSuperAssureur,
			Date dateControleCourtier,
			Date dateControleYlyade,
			Date dateDepot,
			Integer idCourtier,
			String codeCourtier,
//		    Integer idAssure,
//		    String codeAssure,
		    Integer idListeRefDoc,
		    String codeListeRefDoc,
		    String liblDoc
	 		
			 ) {
		this.id = id;
		this.nomFichier = nomFichier;
		this.taille = taille;
		this.typeMime = typeMime;
		this.fichierDoc = fichierDoc;
		this.validationCourtier = validationCourtier;
		this.commentaireCourtier = commentaireCourtier;
		this.validationYlyade= validationYlyade;
		this.commentaireYlyade = commentaireYlyade;
		this.validationSuperAssureur = validationSuperAssureur;
		this.commentaireSuperAssureur = commentaireSuperAssureur;
		this.dateControleCourtier = dateControleCourtier;
		this.dateControleYlyade = dateControleYlyade;
		this.dateDepot= dateDepot;
		this.idCourtier=idCourtier;
		this.codeCourtier=codeCourtier;
//		this.idAssure=idAssure;
//		this.codeAssure=codeAssure;
		this.idListeRefDoc=idListeRefDoc;
		this.codeListeRefDoc=codeListeRefDoc;
		this.liblDoc=liblDoc;
	}
	
	public TaGedUtilisateurDTO(
			Integer id,
			String nomFichier,
			Integer taille,
			String typeMime,
			byte[] fichierDoc,
			Boolean validationCourtier,
			String commentaireCourtier,
			Boolean validationYlyade,
			String commentaireYlyade,
			Boolean validationSuperAssureur,
			String commentaireSuperAssureur,
			Date dateControleCourtier,
			//Date dateControleYlyade,
			Date dateDepot,
		    Integer idDevisRcPro,
		    String numDevis,
		    Integer idListeRefDoc,
		    String codeListeRefDoc,
		    String liblDoc
	 		
			 ) {
		this.id = id;
		this.nomFichier = nomFichier;
		this.taille = taille;
		this.typeMime = typeMime;
		this.fichierDoc = fichierDoc;
		this.validationCourtier = validationCourtier;
		this.commentaireCourtier = commentaireCourtier;
		this.validationYlyade= validationYlyade;
		this.commentaireYlyade = commentaireYlyade;
		this.validationSuperAssureur = validationSuperAssureur;
		this.commentaireSuperAssureur = commentaireSuperAssureur;
		this.dateControleCourtier = dateControleCourtier;
		//this.dateControleYlyade = dateControleYlyade;
		this.dateDepot= dateDepot;
		this.idDevisRcPro=idDevisRcPro;
		this.numDevis=numDevis;
		this.idListeRefDoc=idListeRefDoc;
		this.codeListeRefDoc=codeListeRefDoc;
		this.liblDoc=liblDoc;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

//	public byte[] getFichierDoc() {
//		return fichierDoc;
//	}
//
//	public void setFichierDoc(byte[] fichierDoc) {
//		this.fichierDoc = fichierDoc;
//	}

	public Boolean getValidationCourtier() {
		return validationCourtier;
	}

	public void setValidationCourtier(Boolean validationCourtier) {
		this.validationCourtier = validationCourtier;
	}

	public String getCommentaireCourtier() {
		return commentaireCourtier;
	}

	public void setCommentaireCourtier(String commentaireCourtier) {
		this.commentaireCourtier = commentaireCourtier;
	}

	public Boolean getValidationYlyade() {
		return validationYlyade;
	}

	public void setValidationYlyade(Boolean validationYlyade) {
		this.validationYlyade = validationYlyade;
	}

	public String getCommentaireYlyade() {
		return commentaireYlyade;
	}

	public void setCommentaireYlyade(String commentaireYlyade) {
		this.commentaireYlyade = commentaireYlyade;
	}

	public Date getDateControleCourtier() {
		return dateControleCourtier;
	}

	public void setDateControleCourtier(Date dateControleCourtier) {
		this.dateControleCourtier = dateControleCourtier;
	}

	public Date getDateControleYlyade() {
		return dateControleYlyade;
	}

	public void setDateControleYlyade(Date dateControleYlyade) {
		this.dateControleYlyade = dateControleYlyade;
	}

	public Date getDateDepot() {
		return dateDepot;
	}

	public void setDateDepot(Date dateDepot) {
		this.dateDepot = dateDepot;
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


	public Integer getIdListeRefDoc() {
		return idListeRefDoc;
	}

	public void setIdListeRefDoc(Integer idListeRefDoc) {
		this.idListeRefDoc = idListeRefDoc;
	}

	public String getCodeListeRefDoc() {
		return codeListeRefDoc;
	}

	public void setCodeListeRefDoc(String codeListeRefDoc) {
		this.codeListeRefDoc = codeListeRefDoc;
	}

	public String getLiblDoc() {
		return liblDoc;
	}

	public void setLiblDoc(String liblDoc) {
		this.liblDoc = liblDoc;
	}

//	public Integer getFichierDoc() {
//		return fichierDoc;
//	}
//
//	public void setFichierDoc(Integer fichierDoc) {
//		this.fichierDoc = fichierDoc;
//	}
	
	public byte[] getFichierDoc() {
		return fichierDoc;
	}

	public void setFichierDoc(byte[] fichierDoc) {
		this.fichierDoc = fichierDoc;
	}

	public Integer getIdDevisRcPro() {
		return idDevisRcPro;
	}

	public void setIdDevisRcPro(Integer idDevisRcPro) {
		this.idDevisRcPro = idDevisRcPro;
	}

	public String getNumDevis() {
		return numDevis;
	}

	public void setNumDevis(String numDevis) {
		this.numDevis = numDevis;
	}

	public Boolean getValidationSuperAssureur() {
		return validationSuperAssureur;
	}

	public void setValidationSuperAssureur(Boolean validationSuperAssureur) {
		this.validationSuperAssureur = validationSuperAssureur;
	}

	public String getCommentaireSuperAssureur() {
		return commentaireSuperAssureur;
	}

	public void setCommentaireSuperAssureur(String commentaireSuperAssureur) {
		this.commentaireSuperAssureur = commentaireSuperAssureur;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getObligatoire() {
		return obligatoire;
	}

	public void setObligatoire(Boolean obligatoire) {
		this.obligatoire = obligatoire;
	}

	
}
