package fr.ylyade.courtage.controller.droits;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.FinderException;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.apache.commons.beanutils.PropertyUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import fr.legrain.courtage.service.interfaces.remote.general.ThrowableExceptionLgr;
import fr.legrain.data.EnumModeObjet;
import fr.legrain.data.ModeObjetEcranServeur;
import fr.ylyade.courtage.app.ConstWeb;
import fr.ylyade.courtage.droits.model.TaRole;
import fr.ylyade.courtage.droits.model.TaUtilisateur;
import fr.ylyade.courtage.droits.model.dto.TaRoleDTO;
import fr.ylyade.courtage.droits.model.dto.TaUtilisateurDTO;
import fr.ylyade.courtage.droits.service.interfaces.remote.ITaRoleServiceRemote;
import fr.ylyade.courtage.droits.service.interfaces.remote.ITaUtilisateurServiceRemote;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTCourtierDTO;
import fr.ylyade.courtage.dto.TaTUtilisateurDTO;
import fr.ylyade.courtage.model.Const;
import fr.ylyade.courtage.model.TaTCourtier;
import fr.ylyade.courtage.model.TaTUtilisateur;
import fr.ylyade.courtage.model.mapping.LgrDozerMapper;

@Named
@ViewScoped  
public class UtilisateurController implements Serializable {  
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1287995668024824663L;
	private List<TaUtilisateurDTO> values; 
	private List<TaUtilisateurDTO> filteredValues; 
	private TaUtilisateurDTO nouveau;
	private TaUtilisateurDTO selection;
//	private TaAutorisationsDossier autorisation;
	
	private TaUtilisateur taUtilisateur = new TaUtilisateur();

	private String modeEcranDefaut;
	private static final String C_DIALOG = "dialog";
	
	@Inject private	TenantInfo tenantInfo;
	
	private List<TaRoleDTO> listeRoles; 
	private List<TaRoleDTO> listeChoixRoles; 
	
	private List<String> listeModuleDispo; 
	private List<String> listeChoixModule; 
	
	private String pwd;
	private String pwd2;
	
	private ModeObjetEcranServeur modeEcran = new ModeObjetEcranServeur();

	@EJB private ITaUtilisateurServiceRemote taUtilisateurService;
	@EJB private ITaRoleServiceRemote taRoleService;
//	@EJB private ITaAutorisationsDossierServiceRemote autorisationsDossierService;
//	@EJB private ITaUtilisateurLoginServiceRemote utilisateurLoginService;
	
	private LgrDozerMapper<TaUtilisateurDTO,TaUtilisateur> mapperUIToModel  = new LgrDozerMapper<TaUtilisateurDTO,TaUtilisateur>();
	private LgrDozerMapper<TaUtilisateur,TaUtilisateurDTO> mapperModelToUI  = new LgrDozerMapper<TaUtilisateur,TaUtilisateurDTO>();

	@PostConstruct
	public void postConstruct(){
		try {
			refresh();
			if(values != null && !values.isEmpty()){
				selection = values.get(0);	
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setFilteredValues(values);
	}
	
	public void refresh(){
		values = taUtilisateurService.selectAllDTO();
		listeRoles = taRoleService.selectAllDTO();
		
//		autorisation = autorisationsDossierService.findInstance();
//		listeModuleDispo = new ArrayList<String>();
//		ListeModules listeXml = new ListeModules();
//		listeXml = listeXml.recupModulesXml(autorisation.getFichier());
//		for (Module m : listeXml.module) {
//			listeModuleDispo.add(m.id);
//		}
		
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		modeEcranDefaut = params.get("modeEcranDefaut");
		if(modeEcranDefaut!=null && modeEcranDefaut.equals(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION))) {
			modeEcranDefaut = C_DIALOG;
			actInserer(null);
		} else if(modeEcranDefaut!=null && modeEcranDefaut.equals(modeEcran.modeString(EnumModeObjet.C_MO_EDITION))) {
			modeEcranDefaut = C_DIALOG;
			if(params.get("idEntity")!=null) {
				try {
					selection = taUtilisateurService.findByIdDTO(Integer.parseInt(params.get("idEntity")));
					taUtilisateur = taUtilisateurService.findById(selection.getId());
				} catch (FinderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			actModifier(null);
		} else {
			modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
		}
	}

	public UtilisateurController() {  
	}  
	
	public void autoCompleteMapUIToDTO() {
		try {
			//Roles
			TaRoleDTO role;
			for (TaRoleDTO roleChoisi : listeChoixRoles) {
				if(nouveau.getRoles()==null) {
					nouveau.setRoles(new ArrayList<TaRoleDTO>());
				}
				//if(!taUtilisateur.hasRole(roleChoisi.getLiblRole())){ 
					//ajout des nouveau roles
					role = taRoleService.findByCodeDTO(roleChoisi.getLiblRole());
					nouveau.getRoles().add(role);
				//}
			}
			List<TaRoleDTO> l = new ArrayList<>();
			if(nouveau.getRoles()!=null) {
				for (TaRoleDTO r : nouveau.getRoles()) { //recherche des roles à supprimer
					if(!listeChoixRoles.contains(r)) {
						l.add(r);
					}
				}
				for(TaRoleDTO a : l) { //supression des roles
					nouveau.getRoles().remove(a);
				}
			}
			
			System.out.println("UtilisateurController.autoCompleteMapUIToDTO()");
			
			//Modules
//			ListeModules listeXml = new ListeModules();
//			for (String r : listeChoixModule) {
//				Module m = new Module(r);
//				listeXml.module.add(m);
//			}
//			nouveau.setAutorisations(listeXml.creeXmlModuleString(listeXml));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void autoCompleteMapDTOtoUI() {
		try {
			//Roles
			listeChoixRoles = new ArrayList<TaRoleDTO>();
			if(taUtilisateur.getRoles()!=null && !taUtilisateur.getRoles().isEmpty()) {
				for (TaRole ru : taUtilisateur.getRoles()) {
					for (TaRoleDTO rdto : listeRoles) {
						if(ru.getIdRoles()==rdto.getId()) {
							listeChoixRoles.add(rdto);
						}
					}
				}
			}
//			if(nouveau.getRoles()!=null) {
//				for (TaRoleDTO r : nouveau.getRoles()) {
//					listeChoixRoles.add(r);
//				}
//			}
			
			//Modules
//			listeChoixModule = new ArrayList<String>();
//			if(nouveau.getAutorisations()!=null) {
//				ListeModules listeXml = new ListeModules();
//				listeXml = listeXml.recupModulesXml(nouveau.getAutorisations());
//				for (Module m : listeXml.module) {
//					listeChoixModule.add(m.id);
////					listeModuleDispo.add(m);
//				}
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actAnnuler(ActionEvent actionEvent){
		//values= taTAdrService.selectAll();
		
		if(values.size()>= 1){
			selection = values.get(0);
		}		
		nouveau = new TaUtilisateurDTO();
		
		modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
	}
	
	public void actFermerDialog(ActionEvent actionEvent) {
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	public void actEnregistrer(ActionEvent actionEvent){

		try {

			autoCompleteMapUIToDTO();
			if(pwd!=null && !pwd.equals("")) {
				nouveau.setPassword(taUtilisateur.passwordHashSHA256_Base64(pwd));
			}
			
//			TaUtilisateurLoginMapper mapper =  new TaUtilisateurLoginMapper();

			if(taUtilisateurService.findByCode(nouveau.getIdentifiant()) == null){
				mapperUIToModel.map(nouveau, taUtilisateur);
				
				if(nouveau.getRoles()!=null && !nouveau.getRoles().isEmpty()) {
					if(taUtilisateur.getRoles()==null) {
						taUtilisateur.setRoles(new ArrayList<TaRole>());
					} else {
						taUtilisateur.getRoles().clear();
					}
					for (TaRoleDTO r : nouveau.getRoles()) {
						taUtilisateur.getRoles().add(taRoleService.findById(r.getId()));
					}
				}
				
				taUtilisateur = taUtilisateurService.merge(taUtilisateur, ITaUtilisateurServiceRemote.validationContext);
				mapperModelToUI.map(taUtilisateur, nouveau);
				
				String username_logindb = nouveau.getIdentifiant()+"_"+tenantInfo.getTenantId();
//				TaUtilisateurLogin taUtilisateurLogin = new TaUtilisateurLogin();
//				taUtilisateurLogin = mapper.mapTaUtilisateurToTaUtilisateurLogin(taUtilisateur, taUtilisateurLogin);
//				taUtilisateurLogin.setUsername(username_logindb);
//				utilisateurLoginService.merge(taUtilisateurLogin, ITaUtilisateurLoginServiceRemote.validationContext);

				values= taUtilisateurService.selectAllDTO();
				nouveau = values.get(0);
				nouveau = new TaUtilisateurDTO();

				modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
			}else{
				if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_EDITION)==0){
					
					taUtilisateur = taUtilisateurService.findById(nouveau.getId());
					mapperUIToModel.map(nouveau, taUtilisateur);
					
					if(nouveau.getRoles()!=null && !nouveau.getRoles().isEmpty()) {
						if(taUtilisateur.getRoles()==null) {
							taUtilisateur.setRoles(new ArrayList<TaRole>());
						} else {
							taUtilisateur.getRoles().clear();
						}
						for (TaRoleDTO r : nouveau.getRoles()) {
							taUtilisateur.getRoles().add(taRoleService.findById(r.getId()));
						}
					}
					
					taUtilisateur = taUtilisateurService.merge(taUtilisateur, ITaUtilisateurServiceRemote.validationContext);
					mapperModelToUI.map(taUtilisateur, nouveau);
					

					String username_logindb = nouveau.getIdentifiant()+"_"+tenantInfo.getTenantId();
//					TaUtilisateurLogin taUtilisateurLogin = utilisateurLoginService.findByCode(username_logindb);
//					taUtilisateurLogin = mapper.mapTaUtilisateurToTaUtilisateurLogin(taUtilisateur, taUtilisateurLogin);
//					taUtilisateurLogin.setUsername(username_logindb);
//					utilisateurLoginService.merge(taUtilisateurLogin, ITaUtilisateurLoginServiceRemote.validationContext);
					
					values= taUtilisateurService.selectAllDTO();
					nouveau = values.get(0);
					
					nouveau = new TaUtilisateurDTO();

					modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
				} else{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Code déja utilisé"));
				}
			}
			if(modeEcranDefaut!=null && modeEcranDefaut.equals(C_DIALOG)) {
				RequestContext.getCurrentInstance().closeDialog(taUtilisateur);
			}
		} catch(Exception e) {
			e=ThrowableExceptionLgr.renvoieCauseRuntimeException(e);
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Utilisateur", e.getMessage()));
		}
	}

	public void actInserer(ActionEvent actionEvent){
		nouveau = new TaUtilisateurDTO();
	
		modeEcran.setMode(EnumModeObjet.C_MO_INSERTION);
	}

	public void actModifier(ActionEvent actionEvent){
		nouveau = selection;
		
		autoCompleteMapDTOtoUI();
		
		modeEcran.setMode(EnumModeObjet.C_MO_EDITION);
	}

	public void actSupprimer(ActionEvent actionEvent){
		TaUtilisateur taUtilisateur = new TaUtilisateur();
		try {
			if(selection!=null && selection.getId()!=null){
				taUtilisateur = taUtilisateurService.findById(selection.getId());
			}

			taUtilisateurService.remove(taUtilisateur);
			//values.remove(selection);
			values = taUtilisateurService.selectAllDTO();
			
			if(!values.isEmpty()) {
				selection = values.get(0);
			}else {
				selection=new TaUtilisateurDTO();
			}

			modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);

			if(ConstWeb.DEBUG) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage("Utilisateur", "actSupprimer"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "TVA", e.getCause().getCause().getCause().getCause().getMessage()));
		}
	}
	
	public void onRowSelect(SelectEvent event) {  
		if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_CONSULTATION)==0) {
			nouveau = selection;
		}
	}

	public boolean etatBouton(String bouton) {
		boolean retour = true;
		switch (modeEcran.getMode()) {
		case C_MO_INSERTION:
			switch (bouton) {
			case "annuler":
			case "enregistrer":
			case "fermer":
				retour = false;
				break;
			default:
				break;
			}
			break;
		case C_MO_EDITION:
			switch (bouton) {
			case "annuler":
			case "enregistrer":
			case "fermer":
				retour = false;
				break;
			default:
				break;
			}
			break;
		case C_MO_CONSULTATION:
			switch (bouton) {
			case "supprimer":
			case "modifier":
			case "inserer":
			case "imprimer":
			case "fermer":
				retour = false;
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}

		return retour;
	}
	
	public void validateObject(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String messageComplet = "";
		try {
			String nomChamp =  (String) component.getAttributes().get("nomChamp");
			//validation automatique via la JSR bean validation
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Set<ConstraintViolation<TaUtilisateurDTO>> violations = factory.getValidator().validateValue(TaUtilisateurDTO.class,nomChamp,value);
			if (violations.size() > 0) {
				messageComplet = "Erreur de validation : ";
				for (ConstraintViolation<TaUtilisateurDTO> cv : violations) {
					messageComplet+=" "+cv.getMessage()+"\n";
				}
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, messageComplet, messageComplet));
			} else {
				//aucune erreur de validation "automatique", on déclanche les validations du controller
				validateUIField(nomChamp,value);
			}
		} catch(Exception e) {
			//messageComplet += e.getMessage();
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, messageComplet, messageComplet));
		}
	}

	public boolean validateUIField(String nomChamp,Object value) {
		
		boolean changement=false;
		try {
			
			
			if(nomChamp.equals(Const.C_IDENTIFIANT)) {

				if(value!=null && !value.equals("")) {
						PropertyUtils.setSimpleProperty(nouveau, nomChamp, value);
				} 
										

			}
//			else if(nomChamp.equals(Const.C_PASSWORD)) {
//				if(value!=null && !value.equals("")) {
//					PropertyUtils.setSimpleProperty(nouveau, nomChamp, taUtilisateur.passwordHashSHA256_Base64((String)value));
//				}
//			
//			}else if(nomChamp.equals(Const.C_PASSWORD+"_CONFIRMATION")) {
//				PropertyUtils.setSimpleProperty(nouveau,"passwordConfirm", taUtilisateur.passwordHashSHA256_Base64((String)value));
//
//				value = taUtilisateur.passwordHashSHA256_Base64((String)value);
//
//				if(!value.equals(taUtilisateur.getPassword())) {
//
//					FacesContext context = FacesContext.getCurrentInstance();  
//					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Utilisateurs", "les champs mot de passe et confirmation de mot de passe doivent être identiques"));
//					
//				}
//			}
			
			
			
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void actDialogTypes(ActionEvent actionEvent) {

		Map<String,Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("draggable", true);
		options.put("resizable", false);
		options.put("contentHeight", 620);
		options.put("modal", true);

		Map<String,List<String>> params = new HashMap<String,List<String>>();
		List<String> list = new ArrayList<String>();
		list.add(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION));
		params.put("modeEcranDefaut", list);

		RequestContext.getCurrentInstance().openDialog("admin/dialog_utilisateurs", options, params);

	}

	public void handleReturnDialogTypes(SelectEvent event) {
		if(event!=null && event.getObject()!=null) {
			taUtilisateur = (TaUtilisateur) event.getObject();
			
		}
		refresh();
	}

	public void actDialogModifier(ActionEvent actionEvent){

		nouveau = selection;
		//		modeEcran.setMode(EnumModeObjet.C_MO_EDITION);

		Map<String,Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("draggable", true);
		options.put("resizable", true);
		options.put("contentHeight", 620);

		Map<String,List<String>> params = new HashMap<String,List<String>>();
		List<String> list = new ArrayList<String>();
		list.add(modeEcran.modeString(EnumModeObjet.C_MO_EDITION));
		params.put("modeEcranDefaut", list);
		List<String> list2 = new ArrayList<String>();
		list2.add(new Integer(selection.getId()).toString());
		params.put("idEntity", list2);

		RequestContext.getCurrentInstance().openDialog("admin/dialog_utilisateurs", options, params);

	}

	public List<TaUtilisateurDTO> getValues(){  
		return values;
	}

	public TaUtilisateurDTO getNouveau() {
		return nouveau;
	}

	public void setNouveau(TaUtilisateurDTO newTaUtilisateur) {
		this.nouveau = newTaUtilisateur;
	}

	public TaUtilisateurDTO getSelection() {
		return selection;
	}

	public void setSelection(TaUtilisateurDTO selectedTaUtilisateur) {
		this.selection = selectedTaUtilisateur;
	}

	public void setValues(List<TaUtilisateurDTO> values) {
		this.values = values;
	}

	public List<TaUtilisateurDTO> getFilteredValues() {
		return filteredValues;
	}

	public void setFilteredValues(List<TaUtilisateurDTO> filteredValues) {
		this.filteredValues = filteredValues;
	}

	public ModeObjetEcranServeur getModeEcran() {
		return modeEcran;
	}

	public List<TaRoleDTO> getListeRoles() {
		return listeRoles;
	}

	public void setListeRoles(List<TaRoleDTO> listeRoles) {
		this.listeRoles = listeRoles;
	}

	public List<TaRoleDTO> getListeChoixRoles() {
		return listeChoixRoles;
	}

	public void setListeChoixRoles(List<TaRoleDTO> listeChoixRoles) {
		this.listeChoixRoles = listeChoixRoles;
	}

	public List<String> getListeModuleDispo() {
		return listeModuleDispo;
	}

	public void setListeModuleDispo(List<String> listeModuleDispo) {
		this.listeModuleDispo = listeModuleDispo;
	}

	public List<String> getListeChoixModule() {
		return listeChoixModule;
	}

	public void setListeChoixModule(List<String> listeChoixModule) {
		this.listeChoixModule = listeChoixModule;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwd2() {
		return pwd2;
	}

	public void setPwd2(String pwd2) {
		this.pwd2 = pwd2;
	}

}  
