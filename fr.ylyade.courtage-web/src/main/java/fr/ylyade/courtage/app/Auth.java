package fr.ylyade.courtage.app;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.FinderException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import fr.ylyade.courtage.droits.model.TaAuthURL;
import fr.ylyade.courtage.droits.model.TaRole;
import fr.ylyade.courtage.droits.model.TaUtilisateur;
import fr.ylyade.courtage.droits.service.interfaces.remote.ITaAuthURLServiceRemote;
import fr.ylyade.courtage.droits.service.interfaces.remote.ITaUtilisateurServiceRemote;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.model.TaCourtier;
import fr.ylyade.courtage.model.TaTUtilisateur;
import fr.ylyade.courtage.service.interfaces.remote.ITaCourtierServiceRemote;

@Named
//@ViewScoped
@SessionScoped
public class Auth implements Serializable{

	private static final long serialVersionUID = -4307307357015100899L;
	
	private String username;
	private String password;
	private String theme = "icarus-blue"; //"metroui", "legrain", "icarus-green"
	private String themeCouleur = "blue";
	private String originalURL;
	
	
	//private Integer sessionTimeIdle = 1800000; //milliseconde
	//Penser à changer dans le web.xml <session-config><session-timeout> avec un temps légèrement supérieur
//	private Integer sessionTimeIdle = 3500000; //milliseconde 3600000 = 60 minutes
	private Integer sessionTimeIdle = 14000000; //milliseconde 14400000 = 240 minutes
	
	private String username_logindb;

	private String loginMaint;
	private boolean versionMobile=false;
	
	@Inject @Named("aa")
	private transient ResourceBundle cLangue;

	private TaUtilisateur user;
	@Inject private	SessionInfo sessionInfo;
	@Inject private	TenantInfo tenantInfo;
	@EJB private ITaUtilisateurServiceRemote userService;
	@EJB private ITaAuthURLServiceRemote authURLService;
	@EJB private ITaCourtierServiceRemote taCourtierService;

	@PostConstruct
	public void init() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		originalURL = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
		
		if (sessionInfo.getSessionLangue()!= null) {
			FacesContext.getCurrentInstance().getViewRoot().setLocale(sessionInfo.getSessionLangue());
		}
		if (originalURL == null) {
			originalURL = externalContext.getRequestContextPath() + "/home.xhtml";
		} else {
			String originalQuery = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);

			if (originalQuery != null) {
				originalURL += "?" + originalQuery;
			}
		}
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(origRequest.getRequestURL().substring(1).contains("/m") || origRequest.getRequestURL().substring(1).contains("/m/")){
			theme="icarus-green";
			versionMobile=true;
		};
	}
	
	
	
	
	
	
	

	public String login() throws IOException {

		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		try {
			//log4jLogger.info("LOGIN");

			
			//http://stackoverflow.com/questions/6589138/using-http-request-login-with-jboss-jaas
			Principal userPrincipal = request.getUserPrincipal();
			if (request.getUserPrincipal() != null) {
				request.logout();
			}

			boolean siteEnMaintenance = false;
			if(ConstWeb.maintenance && !isDev(username)) {
				siteEnMaintenance = true;
			}
			
				
			
			if(!siteEnMaintenance) {
				//TODO pour éviter les erreurs si on accède directement au domaine principal d'un des serveurs ylyade, on 'simule' un sous domaine 'www' pour que le code lié à l'extraction du tenant dans l'url fonctionne
				//en cas de 'vrai multi tenant' sur se projet il faut supprimer ça (cf : bdg)
				HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
				System.out.println("dddd : "+origRequest.getServerName());
				//String tenant = ("www"+origRequest.getServerName()).substring(0,+("www"+origRequest.getServerName()).lastIndexOf("."));//supprime le tld (dev.demo.promethee.biz => dev.demo.promethee)
				String tenant = origRequest.getServerName().substring(0,+origRequest.getServerName().lastIndexOf("."));//supprime le tld (dev.demo.promethee.biz => dev.demo.promethee)
				tenant = tenant.substring(0,tenant.lastIndexOf("."));//supprime le domaine principal (dev.demo.promethee => dev.demo)
				tenant = tenant.substring(tenant.lastIndexOf(".")+1);//conserve uniquement le premier niveau de sous domaine (dev.demo => demo)
				System.out.println("Dossier/tenant : "+tenant);
				tenantInfo.setTenantId(tenant);
				try {
					TaCourtier courtier = new TaCourtier();
					courtier = taCourtierService.findByIdentifiantAndByPassword(username, password);
					if(courtier != null) {//Si c'est un courtier
						
						if(courtier.getSuspendu()) {//Si ce courtier est suspendu, on le bloque
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Vous avez été suspendu, merci de Contacter Novarisks.",""));
							return "/login/error.xhtml";
						}else {// Si ce courtier n'est pas suspendu, on log normalement
							request.login(username, password);
							
							userPrincipal = request.getUserPrincipal();
							user = userService.findByCode(username);

							sessionInfo.setUtilisateur(user);
							sessionInfo.setIpAddress(findUserIPAddress());
							sessionInfo.setSessionID(request.getSession().getId());

							System.out.println("Utilisateur "+user.getIdentifiant()+" *** "+user.getIdentifiant()+" connecté avec succès ...");
							//System.out.println("Utilisateur "+user.getIdentifiant()+" ("+user.getNom()!=null?user.getNom():""+" "+user.getPrenom()!=null?user.getPrenom():""+") "+user.getTaEntreprise()!=null?user.getTaEntreprise().getNom():""+" ** IP : "+findUserIPAddress());
							Date now = new Date();
							user.setDateDerniereVisite(now);

							
							user.setIpAcces(sessionInfo.getIpAddress());


							userService.enregistrerMerge(user);


							for (TaRole r : user.getRoles()) {
								System.out.println(r.getLiblRole());
							}
							
							
							externalContext.getSessionMap().put("userSession", user);
							
							ouvertureAutoOnglet(context);
							
						}
					}else {//SI c'est pas un courtier, on log normalement
						request.login(username, password);
						
						userPrincipal = request.getUserPrincipal();
						user = userService.findByCode(username);

						sessionInfo.setUtilisateur(user);
						sessionInfo.setIpAddress(findUserIPAddress());
						sessionInfo.setSessionID(request.getSession().getId());

						System.out.println("Utilisateur "+user.getIdentifiant()+" *** "+user.getIdentifiant()+" connecté avec succès ...");
						//System.out.println("Utilisateur "+user.getIdentifiant()+" ("+user.getNom()!=null?user.getNom():""+" "+user.getPrenom()!=null?user.getPrenom():""+") "+user.getTaEntreprise()!=null?user.getTaEntreprise().getNom():""+" ** IP : "+findUserIPAddress());
						Date now = new Date();
						user.setDateDerniereVisite(now);

						
						user.setIpAcces(sessionInfo.getIpAddress());


						userService.enregistrerMerge(user);


						for (TaRole r : user.getRoles()) {
							System.out.println(r.getLiblRole());
						}
						
						
						externalContext.getSessionMap().put("userSession", user);
						
						ouvertureAutoOnglet(context);
						
					}
				} catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Identifiant ou mot de passe incorrect.", e.getMessage()));
					return "/login/error.xhtml";
					
				}

				
				
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "alerte_site_en_maintenance",""));
				return "/login/error.xhtml";
			}

			//          externalContext.redirect(originalURL);
		} catch (ServletException e) {
			System.out.println("Auth.login() - erreur : "+(username!=null && !username.equals("") ?username:"username vide"));
			e.printStackTrace();
			//log4jLogger.info("DENIED");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Identifiant ou mot de passe incorrect.", e.getMessage()));
			return "/login/error.xhtml";
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(versionMobile){
			return "/m/index.xhtml";
		};
		
		return "/index.xhtml";
	}

	public void logout(ActionEvent a) throws IOException {
		logout();
	}

	public void logout() throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.invalidateSession();
		//System.out.println("Logout : User "+user.getUsername()+" ("+user.getNom()+" "+user.getPrenom()+") "+user.getTaEntreprise()!=null?user.getTaEntreprise().getNom():""+" ** IP : "+findUserIPAddress());
		System.out.println("Logout : User "+user.getIdentifiant()+" ** IP : "+findUserIPAddress());
		if(versionMobile)
			externalContext.redirect(externalContext.getRequestContextPath() + "/m/login/login.xhtml");
		else externalContext.redirect(externalContext.getRequestContextPath() + "/login/login.xhtml");
	}
	
	public void timeout() throws IOException {
		System.out.println("Timeout, fermeture automatique de la session");
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.invalidateSession();
		//System.out.println("Logout : User "+user.getUsername()+" ("+user.getNom()+" "+user.getPrenom()+") "+user.getTaEntreprise()!=null?user.getTaEntreprise().getNom():""+" ** IP : "+findUserIPAddress());
		System.out.println("Logout : User "+user.getIdentifiant()+" ** IP : "+findUserIPAddress());
		if(versionMobile)externalContext.redirect(externalContext.getRequestContextPath() + "/m/login/login.xhtml?timeout=1");
		else externalContext.redirect(externalContext.getRequestContextPath() + "/login/login.xhtml?timeout=1");
	}

	public String findUserIPAddress() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		System.out.println("ipAddress:" + ipAddress);
		return ipAddress;
	}
	
	public void ouvertureAutoOnglet(FacesContext context) {
		//Tant que qu'aucune vue ne les a appelés les ManagedBean JSF ne son pas encore créés,
		//Il faut les "appelés" pour forcer leur création dans le contexte courant
		TabListModelBean tabsCenter = (TabListModelBean) context.getApplication().evaluateExpressionGet(context, "#{tabListModelCenterBean}", TabListModelBean.class);
		TabListModelBean tabsLeft = (TabListModelBean) context.getApplication().evaluateExpressionGet(context, "#{tabListModelLeftBean}", TabListModelBean.class);
		TabListModelBean tabsRight = (TabListModelBean) context.getApplication().evaluateExpressionGet(context, "#{tabListModelRightBean}", TabListModelBean.class);
		TabListModelBean tabsBottom = (TabListModelBean) context.getApplication().evaluateExpressionGet(context, "#{tabListModelBottomBean}", TabListModelBean.class);
		LeftBean LeftBean = (LeftBean) context.getApplication().evaluateExpressionGet(context, "#{leftBean}", LeftBean.class);
		TabViewsBean tabViews = (TabViewsBean) context.getApplication().evaluateExpressionGet(context, "#{tabViewsBean}", TabViewsBean.class);
		
		MenuBean bean = (MenuBean) context.getApplication().evaluateExpressionGet(context, "#{menuBean}", MenuBean.class);
		
		
		if(isCourtier()) {
			bean.openDashBoardAccueilCourtier(null);
		} else if(isAssure()) {
			bean.openDashBoardAccueilAssure(null);
		} else if(isSuperAssureur()) {
			bean.openDashBoardAccueilAssureur(null);
		} else if(isYlyade()) {
			bean.openDashBoardAccueilYlyade(null);
		}
		
	}
	

	public boolean hasRole(String role) {
		if(user.isDevLgr()) {
			return true;
		}
		return user.hasRole(role);
	}
	
	public boolean isCourtier() {
		if(user.isDevLgr()) {
			return true;
		}
		if(user.getTaTUtilisateur()!=null 
				&& user.getTaTUtilisateur().getCodeTUtilisateur()!=null 
				&& user.getTaTUtilisateur().getCodeTUtilisateur().equals(TaTUtilisateur.TYPE_UTILISATEUR_COURTIER)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isYlyade() {
		if(user.isDevLgr()) {
			return true;
		}
		if(user.getTaTUtilisateur()!=null 
				&& user.getTaTUtilisateur().getCodeTUtilisateur()!=null 
				&& user.getTaTUtilisateur().getCodeTUtilisateur().equals(TaTUtilisateur.TYPE_UTILISATEUR_YLYADE)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isSuperAssureur() {
		if(user.isDevLgr()) {
			return true;
		}
		if(user.getTaTUtilisateur()!=null 
				&& user.getTaTUtilisateur().getCodeTUtilisateur()!=null 
				&& user.getTaTUtilisateur().getCodeTUtilisateur().equals(TaTUtilisateur.TYPE_UTILISATEUR_SUPER_ASSUREUR)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isAssure() {
		if(user.isDevLgr()) {
			return true;
		}
		if(user.getTaTUtilisateur()!=null 
				&& user.getTaTUtilisateur().getCodeTUtilisateur()!=null 
				&& user.getTaTUtilisateur().getCodeTUtilisateur().equals(TaTUtilisateur.TYPE_UTILISATEUR_ASSURE)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isAdministrateur() {
		if(user.isDevLgr()) {
			return true;
		}
		if(user.getTaTUtilisateur()!=null 
				&& user.getTaTUtilisateur().getCodeTUtilisateur()!=null 
				&& user.getTaTUtilisateur().getCodeTUtilisateur().equals(TaTUtilisateur.TYPE_UTILISATEUR_ADMINISTRATEUR)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isAdmin() {
		if(user.isDevLgr()) {
			return true;
		}
		if(user.getAdmin()!=null && user.getAdmin()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isDev() {
		return user.isDev();
	}

	public boolean isDev(String username) {
		return user.isDev(username);
	}

	public boolean isDevLgr() {
		return user.isDevLgr();
	}

	public boolean isDevLgr(String username) {
		return user.isDevLgr(username);
	}

	static public TaUtilisateur findUserInSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		return (TaUtilisateur) context.getApplication().evaluateExpressionGet(context,"#{auth.user}", TaUtilisateur.class);
	}

	public List<TaAuthURL> restrictedURL() {
		List<TaAuthURL> l = new ArrayList<TaAuthURL>();
		return l;
	}
	
	public void retour() throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.redirect(externalContext.getRequestContextPath()+"/login/login.xhtml");
	}
	
	public void mdpOublie() throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.redirect(externalContext.getRequestContextPath()+"/login/mdp_oublie.xhtml");
	}

	public String getLoginMaint() {
		return loginMaint;
	}

	public void setLoginMaint(String loginMaint) {
		this.loginMaint = loginMaint;
	}

	public ResourceBundle getcLangue() {
		return cLangue;
	}

	public void setcLangue(ResourceBundle cLangue) {
		this.cLangue = cLangue;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOriginalURL() {
		return originalURL;
	}

	public void setOriginalURL(String originalURL) {
		this.originalURL = originalURL;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public TaUtilisateur getUser() {
		return user;
	}

	public void setUser(TaUtilisateur user) {
		this.user = user;
	}

	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}

	public void setSessionInfo(SessionInfo sessionInfo) {
		this.sessionInfo = sessionInfo;
	}

	public TenantInfo getTenantInfo() {
		return tenantInfo;
	}

	public void setTenantInfo(TenantInfo tenantInfo) {
		this.tenantInfo = tenantInfo;
	}

	public Integer getSessionTimeIdle() {
		return sessionTimeIdle;
	}

	public String getThemeCouleur() {
		return themeCouleur;
	}

	public void setThemeCouleur(String themeCouleur) {
		this.themeCouleur = themeCouleur;
	}


}