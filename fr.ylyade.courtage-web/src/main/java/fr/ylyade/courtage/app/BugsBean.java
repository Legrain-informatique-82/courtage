package fr.ylyade.courtage.app;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;

import fr.legrain.bugzilla.rest.model.Bug;
import fr.legrain.bugzilla.rest.model.Comment;
import fr.legrain.bugzilla.rest.util.BugzillaUtil;
import fr.ylyade.courtage.droits.model.TaUtilisateur;
import fr.ylyade.courtage.droits.service.interfaces.remote.ITaUtilisateurServiceRemote;

@ManagedBean
@ViewScoped 
public class BugsBean {

//	@ManagedProperty(value="#{tabListModelCenterBean}")
//	private TabListModelBean tabsCenter;
	
	private BugzillaUtil bz;
	
	public static final String apiKey = "XNS81ITtIwpK2hJjU6rxEHYmw0B9GfjOJu985xUG"; 
	public  static final String bugzillaURL = "http://bugs.legrain.fr";
	
	private String login = "ylyade.bugs@legrain.fr"; 
//	private String password = "paeT8uF8"; //"lgr006"; shaePh0e
	
	private String bzProduct = "Ylyade"; //TestProduct 
	private String bzComponent = "général"; //TestComponent
	private String bzVersion = "non spécifiée";
//	private String bzProduct = "TestProduct"; //TestProduct 
//	private String bzComponent = "TestComponent"; //TestComponent
//	private String bzVersion = "unspecified"; //unspecified
	private String bzPlatform = "All";
	private String bzOpSys = "All";
	
	private List<Bug> listeBugs;
	private List<Comment> listeCommentaire;
	private Bug bug;
	private Bug nouveauBug;
	private String nouveauCommentaire;
	
	private String complMessageErreur;
	private String complDateConstatation;
	private String complCodeDossierRCD;
	private String complEtat;
	private String complCodeCourtier;
	private String complCodeAssure;
	private String complTypeAcces;
	
	private TaUtilisateur u = null;
	
	@EJB
    private ITaUtilisateurServiceRemote userService;
	
	@PostConstruct
	public void init() {
		u = Auth.findUserInSession();
		
		bz = new BugzillaUtil(bugzillaURL,apiKey);
		//listeBugs = bz.findBugs(bzProduct);
		//listeBugs = bz.findBugs(bzProduct,login);
		findBug();
		nouveauBug = new Bug();
	}
	
	public void createBug(ActionEvent event) { 
		
//		u.setEmail("test@test1.fr");
//		u.setNom("a");
//		u.setPrenom("b");
//		u.setUsername("123456");
			
		if(bz.findUser(login)==null) {
			//créé un utilisateur avant de poster un nouveau bug, pour pouvoir l'ajouter en CC
			//bz.createUser(u.getEmail(),u.getNom()+" "+u.getPrenom(),u.getUsername());
		}
		
		//propriétés à remplir automatiquement ET OBLIGATOIRE
		nouveauBug.setProduct(bzProduct);
		nouveauBug.setComponent(bzComponent);
		nouveauBug.setVersion(bzVersion);
		nouveauBug.setPlatform(bzPlatform);
		nouveauBug.setOpSys(bzOpSys);
		
		//propriétés à remplir automatiquement
		/*
		if(nouveauBug.getCc()==null) {
			nouveauBug.setCc(new ArrayList<Object>());
		}
		nouveauBug.getCc().add(u.getEmail());
		*/
		
		//propriétés par l'utilisateur
		//nouveauBug.setSummary("Test bug appli"+new Date().getTime());
		//nouveauBug.setDescription("sdfsfbh");
		
		//Ajout infos dans Description
		String detailDebug = "\n\n\n -----------------------------------"
				+ "\n Informations complémentaires "
				+ "\n "
				+ (complMessageErreur!=null ? ("\n Message d'erreur : "+complMessageErreur) : "")
				+ (complDateConstatation!=null ? ("\n Date constatation : "+complDateConstatation) : "") 
				+ (complCodeDossierRCD!=null ? ("\n Code dossier RCD : "+complCodeDossierRCD) : "") 
				+ (complEtat!=null ? ("\n Etat : "+complEtat) : "") 
				+ (complCodeCourtier!=null ? ("\n Code courtier : "+complCodeCourtier) : "") 
				+ (complCodeAssure!=null ? ("\n Code assure : "+complCodeAssure) : "")
				+ (complTypeAcces!=null ? ("\n Type acces : "+complTypeAcces) : "") 
				;
		nouveauBug.setDescription(nouveauBug.getDescription()+detailDebug);
		
		Integer bugID = bz.createBug(nouveauBug);
		
		nouveauBug = new Bug();
		
		complMessageErreur = "";
		complDateConstatation = "";
		complCodeDossierRCD = "";
		complEtat = "";
		complCodeCourtier = "";
		complCodeAssure = "";
		complTypeAcces = "";
		
		//listeBugs = bz.findBugs(bzProduct);
		//listeBugs = bz.findBugs(bzProduct,login);
		findBug();
		
		Integer commentID = null;
//		if(nouveauCommentaire!=null && !nouveauCommentaire.equals("")) {
//			commentID = bz.createComment(nouveauCommentaire, bugID);
//			nouveauCommentaire = null;
//		}
		
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("createBug", 
				"Nouveau ticket créé : "+bugID+""
				)); 
	}
	
	public void findBug() { 
		findBug(null);
	}
	
	public void findBug(ActionEvent event) { 
		
		//listeBugs = bz.findBugs(bzProduct);
		listeBugs = bz.findBugs(bzProduct,login);
		for (Bug bug : listeBugs) {
			bug.setCreationTime(BugzillaUtil.bugzillaStringDateToString(bug.getCreationTime()));
		}
		
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("findBug", 
					"Recherche dans Bugzilla pour "+bzProduct
					)); 
		}
	}
	
	public void detailBug(SelectEvent event) { 
		
		listeCommentaire = bz.findBugComment(bug.getId());
		
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("findBug", 
					"Recherche dans Bugzilla pour "+bzProduct
					)); 
		}
	}
	
	public void createComment(ActionEvent event) {
		Integer idCom = bz.createComment(nouveauCommentaire, bug.getId());
		nouveauCommentaire = "";
		listeCommentaire = bz.findBugComment(bug.getId());
	}

	public List<Bug> getListeBugs() {
		return listeBugs;
	}

	public void setListeBugs(List<Bug> listeBugs) {
		this.listeBugs = listeBugs;
	}

	public Bug getBug() {
		return bug;
	}

	public void setBug(Bug bug) {
		this.bug = bug;
	}

	public Bug getNouveauBug() {
		return nouveauBug;
	}

	public void setNouveauBug(Bug nouveauBug) {
		this.nouveauBug = nouveauBug;
	}

	public String getBugzillaURL() {
		return bugzillaURL;
	}

	public ITaUtilisateurServiceRemote getUserService() {
		return userService;
	}

	public void setUserService(ITaUtilisateurServiceRemote userService) {
		this.userService = userService;
	}

	public String getNouveauCommentaire() {
		return nouveauCommentaire;
	}

	public void setNouveauCommentaire(String nouveauCommentaire) {
		this.nouveauCommentaire = nouveauCommentaire;
	}

	public List<Comment> getListeCommentaire() {
		return listeCommentaire;
	}

	public void setListeCommentaire(List<Comment> listeCommentaire) {
		this.listeCommentaire = listeCommentaire;
	}

	public String getComplMessageErreur() {
		return complMessageErreur;
	}

	public void setComplMessageErreur(String complMessageErreur) {
		this.complMessageErreur = complMessageErreur;
	}

	public String getComplDateConstatation() {
		return complDateConstatation;
	}

	public void setComplDateConstatation(String complDateConstation) {
		this.complDateConstatation = complDateConstation;
	}

	public String getComplCodeDossierRCD() {
		return complCodeDossierRCD;
	}

	public void setComplCodeDossierRCD(String complCodeDossierRCD) {
		this.complCodeDossierRCD = complCodeDossierRCD;
	}

	public String getComplCodeCourtier() {
		return complCodeCourtier;
	}

	public void setComplCodeCourtier(String complCodeCourtier) {
		this.complCodeCourtier = complCodeCourtier;
	}

	public String getComplCodeAssure() {
		return complCodeAssure;
	}

	public void setComplCodeAssure(String complCodeAssure) {
		this.complCodeAssure = complCodeAssure;
	}

	public String getComplTypeAcces() {
		return complTypeAcces;
	}

	public void setComplTypeAcces(String complTypeAcces) {
		this.complTypeAcces = complTypeAcces;
	}

	public String getComplEtat() {
		return complEtat;
	}

	public void setComplEtat(String complEtat) {
		this.complEtat = complEtat;
	}
	
}
