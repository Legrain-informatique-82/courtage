package fr.ylyade.courtage.app;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.Serializable;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.FinderException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.view.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;

import fr.legrain.courtage.controle.service.interfaces.remote.general.ITaGenCodeExServiceRemote;
import fr.ylyade.courtage.droits.model.TaAuthURL;
import fr.ylyade.courtage.droits.model.TaUtilisateur;
import fr.ylyade.courtage.droits.service.interfaces.remote.ITaAuthURLServiceRemote;
import fr.ylyade.courtage.droits.service.interfaces.remote.ITaUtilisateurServiceRemote;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaAssureDTO;
import fr.ylyade.courtage.dto.TaCourtierDTO;
import fr.ylyade.courtage.dto.TaDossierRcdDTO;
import fr.ylyade.courtage.dto.TaTAssureDTO;
import fr.ylyade.courtage.dto.TaTCiviliteDTO;
import fr.ylyade.courtage.dto.TaTEcheanceDTO;
import fr.ylyade.courtage.dto.TaTJuridiqueDTO;
import fr.ylyade.courtage.dto.TaTReglementDTO;
import fr.ylyade.courtage.dto.TaTSousTraitanceDTO;
import fr.ylyade.courtage.model.Const;
import fr.ylyade.courtage.model.ConstPreferences;
import fr.ylyade.courtage.model.TaAdresse;
import fr.ylyade.courtage.model.TaAssure;
import fr.ylyade.courtage.model.TaContactEntreprise;
import fr.ylyade.courtage.model.TaCourtier;
import fr.ylyade.courtage.model.TaDocumentPdf;
import fr.ylyade.courtage.model.TaEmail;
import fr.ylyade.courtage.model.TaTAdresse;
import fr.ylyade.courtage.model.TaTAssure;
import fr.ylyade.courtage.model.TaTCivilite;
import fr.ylyade.courtage.model.TaTEcheance;
import fr.ylyade.courtage.model.TaTEmail;
import fr.ylyade.courtage.model.TaTJuridique;
import fr.ylyade.courtage.model.TaTReglement;
import fr.ylyade.courtage.model.TaTSousTraitance;
import fr.ylyade.courtage.model.TaTTel;
import fr.ylyade.courtage.model.TaTUtilisateur;
import fr.ylyade.courtage.model.TaTel;
import fr.ylyade.courtage.service.TaEmailService;
import fr.ylyade.courtage.service.interfaces.remote.ITaContactEntrepriseServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaCourtierServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaDocumentPdfServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaEmailServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaEnvoiMailTestServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaLgrMailjetServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTUtilisateurServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTelServiceRemote;

@Named
@ViewScoped
//@SessionScoped
public class CreationCourtierController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4912144696322877914L;
	private String username;
	private String password;
	private String theme = "icarus-orange"; //"metroui", "legrain", "icarus-green"
	private String themeCouleur = "orange";
	private String originalURL;

	public static final String CHAMP_NOM_CABINET       = "nomCabinet";
	public static final String CHAMP_PRENOM    = "prenomGerant";
	public static final String CHAMP_NOM    = "nomGerant";
	public static final String CHAMP_NOM_DENOMINATION_SOCIALE    = "nomDenominationSociale";
	public static final String CHAMP_SIRET    = "siret";
	public static final String CHAMP_TELEPHONE    = "telephone";
	public static final String CHAMP_FAX    = "fax";
	public static final String CHAMP_ORIAS    = "orias";
	public static final String CHAMP_EMAIL     = "email";
	public static final String CHAMP_IDENTIFIANT    = "identifiant";
	public static final String CHAMP_PASS      = "password";
	public static final String CHAMP_CONF      = "confirmPassword";
	public static final String ATT_ERREURS     = "erreurs";
	public static final String ATT_RESULTAT    = "resultat";
	
	

	private String resultat;
	private String confirmPassword;


	private String username_logindb;

	private String loginMaint;
	private boolean versionMobile=false;

	//	@Inject ("#{c_langue}")
	//	private ResourceBundle cLangue;

	private TaUtilisateur user = new TaUtilisateur();
	private TaCourtier courtier = new TaCourtier();
	private TaContactEntreprise contactEntreprise = new TaContactEntreprise();
	private TaEmail email = new TaEmail();
	private TaTel tel = new TaTel();

	private Date now = new Date();


	// Map pour stocker les erreurs
	private Map<String, String> erreurs = new HashMap<String, String>();

	@Inject private	Auth auth;
	@Inject private	SessionInfo sessionInfo;
	@Inject private	TenantInfo tenantInfo;
	@EJB private ITaUtilisateurServiceRemote taUtilisateurService;
	@EJB private ITaAuthURLServiceRemote taAuthURLService;
	@EJB private ITaCourtierServiceRemote taCourtierService;
	@EJB private ITaContactEntrepriseServiceRemote taContactEntrepriseService;
	@EJB private ITaTUtilisateurServiceRemote taTUtilisateurService;
	@EJB private ITaEmailServiceRemote taEmailService;
	@EJB private ITaTelServiceRemote taTelService;
	@EJB private ITaGenCodeExServiceRemote taGenCodeExService;
	@EJB private  ITaDocumentPdfServiceRemote taDocumentPdfService;
	
	@EJB private ITaLgrMailjetServiceRemote lgrMailjetService;
	
	@EJB private ITaEnvoiMailTestServiceRemote envoiMailTestService;
	
	

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
		TaAdresse adr = new TaAdresse();
		contactEntreprise.setTaAdresse(adr);
		adr.setTaContactEntreprise(contactEntreprise);
	}

	public void login() throws IOException, ServletException {
		if(this.email.getAdresseEmail()!=null) {
			TaEmail adresse;
			try {
				if(this.email.getAdresseEmail()!=null) {
					adresse = taEmailService.findByAdresseEmail(this.email.getAdresseEmail());
					if(adresse != null) {
						FacesContext context = FacesContext.getCurrentInstance();  
						context.addMessage(null, new FacesMessage("Courtier", "Adresse E-mail existante !"));
					}else {
						if(user.getPassword().equals(this.confirmPassword)) {
			        		

				        	
							email.setTaContactEntreprise(contactEntreprise); 
							tel.setTaContactEntreprise(contactEntreprise);
							courtier.setTaContactEntreprise(contactEntreprise);
							courtier.setTaUtilisateur(user);
							
					
							courtier.getTaContactEntreprise().setTaCourtier(courtier);
					
							List<TaEmail> emailList = new ArrayList<TaEmail>();
							emailList.add(email);
							courtier.getTaContactEntreprise().setTaEmails(emailList);
							courtier.getTaContactEntreprise().setTaEmail(email);
							user.setIdentifiant(email.getAdresseEmail()); //identifiant == adresse email
					
					
							List<TaTel> taTels = new ArrayList<TaTel>();
							taTels.add(tel);
							courtier.getTaContactEntreprise().setTaTels(taTels);
							courtier.getTaContactEntreprise().setTaTel(tel);
					
							// Crypte le string password en Hash
							String mdp= user.getPassword();
							user.setPassword(user.passwordHashSHA256_Base64(user.getPassword()));
					
							//Date now = new Date();
							user.setDateCreation(now);
							
							TaTUtilisateur taTUtilisateur = null; 
							try {
								taTUtilisateur = taTUtilisateurService.findByCode(TaTUtilisateur.TYPE_UTILISATEUR_COURTIER);
							} catch (FinderException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							user.setTaTUtilisateur(taTUtilisateur);
							
							taGenCodeExService.libereVerrouTout(new ArrayList<String>(SessionListener.getSessions().keySet()));
							courtier.setCodeCourtier(taCourtierService.genereCode(null)); //ejb
					//		validateUIField(Const.C_CODE_COURTIER,courtier.getCodeCourtier());//permet de verrouiller le code genere
					
							user.setAdmin(false);
							user.setSysteme(false);
					
								//Enregistre dans bdd le nouvel utilisateur + nouveau courtier
					
					//			courtier.setCodeCourtier("code");
					//			courtier.setCodeSiren("00000");
					//			courtier.setCodeApe("00000");
					//			courtier.setTvaIntraComm("tva");
					//			courtier.setNomDenominationSociale("nomDenominationSociale");
					
					
								//		email.setTaContactEntreprise(contactEntreprise); 
								//		tel.setTaContactEntreprise(contactEntreprise);
								//		courtier.setTaContactEntreprise(contactEntreprise);
					
					
								courtier = taCourtierService.merge(courtier);
								user = courtier.getTaUtilisateur();
					
								System.out.println("Succès de l'inscription du courtier dans la bdd.");
					
					
					
								/**************************************COPIE DE LOGIN DE AUTH***********************************************/		
					
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
					
										HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
										System.out.println("dddd : "+origRequest.getServerName());
										String tenant = origRequest.getServerName().substring(0,origRequest.getServerName().lastIndexOf("."));//supprime le tld (dev.demo.promethee.biz => dev.demo.promethee)
										tenant = tenant.substring(0,tenant.lastIndexOf("."));//suprime le domaine principal (dev.demo.promethee => dev.demo)
										tenant = tenant.substring(tenant.lastIndexOf(".")+1);//conserve uniquement le premier niveau de sous domaine (dev.demo => demo)
										System.out.println("Dossier/tenant : "+tenant);
										tenantInfo.setTenantId(tenant);
					
					
										request.login(user.getIdentifiant(), mdp);
										
					//					Auth auth = null;
								        if(origRequest.getSession().getAttribute("auth")!=null) {
								        	auth = (Auth) origRequest.getSession().getAttribute("auth");//session scoped managed bean
								        }
										auth.setUser(user);
										auth.setUsername(user.getIdentifiant());
										System.out.println("Succès du login et redirection");
					
										userPrincipal = request.getUserPrincipal();
										//user = taUtilisateurService.findByCode(username);
					
										sessionInfo.setUtilisateur(user);
										sessionInfo.setIpAddress(findUserIPAddress());
										sessionInfo.setSessionID(request.getSession().getId());
					
										//System.out.println("Utilisateur "+user.getIdentifiant()+" *** "+user.getIdentifiant()+" connecté avec succès ...");
										//System.out.println("Utilisateur "+user.getIdentifiant()+" ("+user.getNom()!=null?user.getNom():""+" "+user.getPrenom()!=null?user.getPrenom():""+") "+user.getTaEntreprise()!=null?user.getTaEntreprise().getNom():""+" ** IP : "+findUserIPAddress());
					
					
										Date now = new Date();
										user.setDateDerniereVisite(now);
					
					
										user.setIpAcces(sessionInfo.getIpAddress());
					
					
										taUtilisateurService.enregistrerMerge(user);
										
										//envoi de mail
										envoiMailTestService.envoiMailCreationCourtier(courtier);

										externalContext.getSessionMap().put("userSession", user);
										
										externalContext.redirect(externalContext.getRequestContextPath()+"/index.xhtml");
										
										auth.ouvertureAutoOnglet(context);
										
									} else {
										FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ealerte_site_en_maintenance",""));
										//return "/login/error.xhtml";
										externalContext.redirect(externalContext.getRequestContextPath()+"/index.xhtml");
									}
					
									//          externalContext.redirect(originalURL);
								} catch (ServletException e) {
									e.printStackTrace();
									//log4jLogger.info("DENIED");
									FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "alerte_inconnu_email",/*e.getClass().getName()*/ e.getMessage()));
									//return "/login/error.xhtml";
									externalContext.redirect(externalContext.getRequestContextPath()+"/login/error.xhtml");
								} catch (FinderException e) {
									FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
											FacesMessage.SEVERITY_FATAL, e.getClass().getName(), e.getMessage()));
									e.printStackTrace();
								} catch (Exception e) {
									e.printStackTrace();
								}
								HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
								if(versionMobile){
									//return "/m/index.xhtml";
									externalContext.redirect(externalContext.getRequestContextPath()+"/m/index.xhtml");
								};
					
					
					//		} else {        	
					//			resultat = "creer_un_compte_alerte_echec_inscription";
					//			System.out.println("Échec de l'inscription.");
					//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erreur!", "creer_un_compte_alerte_echec_inscription"));
					//			//setoK(true);
					//		}
							//return "/index.xhtml";
			        }else {
			    		System.out.println("user.getPassword = "+user.getPassword());
			    		System.out.println("this.confirmPassword = "+this.confirmPassword);
			        	FacesContext context = FacesContext.getCurrentInstance();  
						context.addMessage(null, new FacesMessage("Courtier", "Le mot de passe est différent de sa confirmation"));
			        } 
						
					}
				}
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		
		
		
        	
        	
	  }else {
		  FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Courtier", "Veuillez renseigner une adresse mail"));
	  }

	}	
	
	public String passwordComplex () {
		String password = user.getPassword();
		String niveauComplex="Faible";
		String expressionForte ="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
//		(?=.*[0-9])       # a digit must occur at least once
//		(?=.*[a-z])       # a lower case letter must occur at least once
//		(?=.*[A-Z])       # an upper case letter must occur at least once
//		(?=.*[@#$%^&+=])  # a special character must occur at least once
//		(?=\S+$)          # no whitespace allowed in the entire string
//		.{8,}             # anything, at least eight places though
		
		String expressionMoyenne ="(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}";
		
		if(password != null) {
			 Pattern pattFort = Pattern.compile(expressionForte);
			 Pattern pattMoyenne = Pattern.compile(expressionMoyenne);
			 Matcher mFort = pattFort.matcher(password);
			 Matcher mMoyenne = pattMoyenne.matcher(password);
			 
			
			 if(mFort.find()) {
				 niveauComplex = "Fort";
			 }else if(mMoyenne.find() && !mFort.find()) {
				 niveauComplex = "Moyen";
			 }
		}
		
		return niveauComplex;
		
	}
	
	
	
	
	/*****************************VALIDATION ******************************************/
	
	public void validateCourtier(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String nomChamp =  (String) component.getAttributes().get("nomChamp");
		 validateUIField(nomChamp,value);
	}
	
	
	public boolean validateUIField(String nomChamp,Object value) {
			
			boolean changement=false;
			
			
	
			try {				
				if(nomChamp.equals("email")) {
					TaEmail adresse;
					try {
						if(value!=null) {
							adresse = taEmailService.findByAdresseEmail((String) value);
							if(adresse != null) {
								FacesContext context = FacesContext.getCurrentInstance();  
								context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Courtier", "Adresse E-mail existante !"));
							}
						}
					} catch (Exception e) {
						
						e.printStackTrace();
					}
											
				}
					
				return false;
	
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
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

	public boolean hasRole(String role) {

		return user.hasRole(role);
	}

	public boolean isAdmin() {
		//		if(user.getAdminDossier()!=null && user.getAdminDossier()) {
		//			return true;
		//		} else {
		return false;
		//		}
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

	//	public ResourceBundle getcLangue() {
	//		return cLangue;
	//	}
	//
	//	public void setcLangue(ResourceBundle cLangue) {
	//		this.cLangue = cLangue;
	//	}

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



	public String getThemeCouleur() {
		return themeCouleur;
	}

	public void setThemeCouleur(String themeCouleur) {
		this.themeCouleur = themeCouleur;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmpasswd) {
		this.confirmPassword = confirmpasswd;
	}

	public String getResultat() {
		return resultat;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}

	public String getUsername_logindb() {
		return username_logindb;
	}

	public void setUsername_logindb(String username_logindb) {
		this.username_logindb = username_logindb;
	}

	public TaCourtier getCourtier() {
		return courtier;
	}

	public void setCourtier(TaCourtier courtier) {
		this.courtier = courtier;
	}

	public TaEmail getEmail() {
		return email;
	}

	public void setEmail(TaEmail email) {
		this.email = email;
	}

	public TaTel getTel() {
		return tel;
	}

	public void setTel(TaTel tel) {
		this.tel = tel;
	}

	public TaContactEntreprise getContactEntreprise() {
		return contactEntreprise;
	}

	public void setContactEntreprise(TaContactEntreprise contactEntreprise) {
		this.contactEntreprise = contactEntreprise;
	}




	/*************************************************************VALIDATION************************************************/


	// méthode de verification des champs du formulaire nouvel utilisateur 
	private void verifieNom(String nom) throws Exception {
		if (courtier.getTaContactEntreprise().getNom().isEmpty()) {
			throw new Exception( "creer_un_compte_alerte_nom_vide" );
		}
	}

	private void verifiePrenom(String prenom) throws Exception {
		if (courtier.getTaContactEntreprise().getPrenom().isEmpty()) {
			throw new Exception( "creer_un_compte_alerte_prenom_vide" );
		}
	}

	//		public void verifieEmail( String email ) throws Exception {
	//			if (!courtier.getTaContactEntreprise().getTaEmails().isEmpty()) {
	//				if (!courtier.getTaContactEntreprise().getTaEmails().matches( "^[_a-z0-9-\\.\\+]+(\\[_a-z0-9-]+)*@"
	//						+ "[a-z0-9-]+(\\[a-z0-9]+)*(\\.[a-z]{2,})$" ) ) {
	//					throw new Exception( "creer_un_compte_alerte_erreur_email" );
	//				}
	//			} else {
	//				throw new Exception( "creer_un_compte_alerte_email_vide");
	//			}
	//		}

	private void verifieMotsDePasse(String passwd, String champConf) throws Exception{
		if (courtier.getTaUtilisateur().getPassword() != null 
				&& courtier.getTaUtilisateur().getPassword().trim().length() != 0 
				&& getConfirmPassword() != null 
				&& confirmPassword.trim().length() != 0) {
			if (!courtier.getTaUtilisateur().getPassword().equals(getConfirmPassword())) {
				throw new Exception("creer_un_compte_alerte_erreur_confirmation_mdp");
			} else if (courtier.getTaUtilisateur().getPassword().trim().length() < 6) {
				throw new Exception("creer_un_compte_alerte_erreur_mdp_inf_6_caract");
			} else if (!courtier.getTaUtilisateur().getPassword().matches( "^(?=(.*[a-zA-Z]))(?=(.*[A-Z]){1})(?=(.*[0-9]){1}).{6,}"
					) ) {
				throw new Exception("creer_un_compte_alerte_erreur_mdp__maj_chiffre");
			}
		} else {
			throw new Exception("creer_un_compte_alerte_mdp_vide");
		}

	}

}