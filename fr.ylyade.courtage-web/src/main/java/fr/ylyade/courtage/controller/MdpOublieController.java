package fr.ylyade.courtage.controller;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import fr.legrain.data.YlyadeProperties;
import fr.legrain.lib.data.LibCrypto;
import fr.legrain.lib.data.LibExecLinux;
import fr.ylyade.courtage.droits.model.TaUtilisateur;
import fr.ylyade.courtage.droits.service.interfaces.remote.ITaUtilisateurServiceRemote;
import fr.ylyade.courtage.service.LgrEmail;
import fr.ylyade.courtage.service.interfaces.remote.ITaEnvoiMailTestServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaLgrMailjetServiceRemote;





@Named
@ViewScoped 
public class MdpOublieController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4990365785413169645L;

	private YlyadeProperties ylyadeProperties;

	private String motDePasseActuel = "";
	private String nouveauMotDePasse = "";
	private String confirmationMotDePasse = "";

	private String param;

	private String identifiant = null;
	private String adresseEmail = null;

	private TaUtilisateur u = null;
	
	private Boolean actionOk = false;
	private String separateurChaineCryptee = "~";
	private	String tenantDossier;

	@EJB private ITaUtilisateurServiceRemote userService;
	@EJB private LgrEmail lgrEmail;
	
	@EJB private ITaLgrMailjetServiceRemote lgrMailjetService;
	
	@EJB private ITaEnvoiMailTestServiceRemote envoiMailService;

	@PostConstruct
	public void init() {
		ylyadeProperties = new YlyadeProperties();
		initTenant();
	}

	public void onload() {
//		try {
			System.out.println("param crypt : "+param);
			try {
				param = URLDecoder.decode(param, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String paramDecrypt = LibCrypto.decrypt(param);
			System.out.println("param decrypt : "+paramDecrypt);
//			tenantDossier = paramDecrypt.split(separateurChaineCryptee)[0];
//			identifiant = paramDecrypt.split(separateurChaineCryptee)[1];
//			adresseEmail = paramDecrypt.split(separateurChaineCryptee)[2];
			
			
			identifiant = paramDecrypt;
			adresseEmail = paramDecrypt;
			
			
			
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
	}

	public void initTenant() {
		//		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		//		System.out.println("dddd : "+origRequest.getServerName());
		//		String tenant = origRequest.getServerName().substring(0,origRequest.getServerName().lastIndexOf("."));//supprime le tld (dev.demo.promethee.biz => dev.demo.promethee)
		//		tenant = tenant.substring(0,tenant.lastIndexOf("."));//suprime le domaine principal (dev.demo.promethee => dev.demo)
		//		tenant = tenant.substring(tenant.lastIndexOf(".")+1);//conserve uniquement le premier niveau de sous domaine (dev.demo => demo)
		//		System.out.println("Dossier/tenant change mdp : "+tenant);
		//		tenantDossier = tenant;
		//		tenantInfo.setTenantId(tenant);
	}

	public void demandeMotDePasse(ActionEvent event) throws Exception {

		setIdentifiant(adresseEmail);
		initTenant();
		System.out.println(identifiant);
		u = userService.findByCode(identifiant);

		if(u!=null) {		
			
			envoiMailService.envoiMailChangementPass(u);

			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("", 
					"Vous allez recevoir un email pour changer votre mot de passe à l'adresse suivante : "+adresseEmail
					)); 

			actionOk = true;
		} else {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Aucun utilisateur avec cet identifiant n'existe. : ", 
					""
					)); 
		}

	}

	public void changeMotDePasse(ActionEvent event) throws Exception {

		String nouveauMdpGenere = LibExecLinux.pwgen();

		initTenant();
		u = userService.findByCode(identifiant);

		if(u!=null) {

			if(nouveauMotDePasse!=null && !nouveauMotDePasse.equals("") 
					&& confirmationMotDePasse!=null && !confirmationMotDePasse.equals("") 
					&& nouveauMotDePasse.equals(confirmationMotDePasse)) {
				//le nouveau mot de passe saisie est correct

				if(verifComplexiteMotDePasse(nouveauMotDePasse)) {
					u.setPassword(u.passwordHashSHA256_Base64(nouveauMotDePasse));
					userService.enregistrerMerge(u);
					
					envoiMailService.envoiMailConfirmationChangementPass(u);

					FacesContext context = FacesContext.getCurrentInstance();  
					context.addMessage(null, new FacesMessage("", 
							//"Votre nouveau mot de passe viens de vous être envoyé à l'adresse suivante : "+adresseEmail
							"Votre mot de passe a bien été changé. Un mail de confirmation a été envoyé à l'adresse suivante : "+adresseEmail
							)); 

					actionOk = true;


				} else {
					FacesContext context = FacesContext.getCurrentInstance();  
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Votre nouveau mot de passe est trop simple", 
							""
							)); 
				}
			} else {

				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Les valeurs saisie doivent être identique", 
						""
						)); 
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Aucun utilisateur avec cet identifiant n'existe : ", 
					""
					)); 
		}
	}

	public void actFermer() {

	}

	private boolean verifComplexiteMotDePasse(String pwd) {
		return true;
	}

	public String getMotDePasseActuel() {
		return motDePasseActuel;
	}

	public void setMotDePasseActuel(String motDePasseActuel) {
		this.motDePasseActuel = motDePasseActuel;
	}

	public String getNouveauMotDePasse() {
		return nouveauMotDePasse;
	}

	public void setNouveauMotDePasse(String nouveauMotDePasse) {
		this.nouveauMotDePasse = nouveauMotDePasse;
	}

	public String getConfirmationMotDePasse() {
		return confirmationMotDePasse;
	}

	public void setConfirmationMotDePasse(String confirmationMotDePasse) {
		this.confirmationMotDePasse = confirmationMotDePasse;
	}

	public TaUtilisateur getU() {
		return u;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getAdresseEmail() {
		return adresseEmail;
	}

	public void setAdresseEmail(String adresseEmail) {
		this.adresseEmail = adresseEmail;
	}

	public String getParam() {
		return param;
	}


	public void setParam(String param) {
		this.param = param;
	}

	public String getTenantDossier() {
		return tenantDossier;
	}

	public void setTenantDossier(String tenantDossier) {
		this.tenantDossier = tenantDossier;
	}

	public Boolean getActionOk() {
		return actionOk;
	}

	public void setActionOk(Boolean actionOk) {
		this.actionOk = actionOk;
	}

	public String getSeparateurChaineCryptee() {
		return separateurChaineCryptee;
	}

	public void setSeparateurChaineCryptee(String separateurChaineCryptee) {
		this.separateurChaineCryptee = separateurChaineCryptee;
	}

}
