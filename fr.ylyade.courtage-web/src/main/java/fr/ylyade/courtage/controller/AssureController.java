package fr.ylyade.courtage.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.apache.commons.beanutils.PropertyUtils;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.ToggleEvent;

import fr.legrain.courtage.controle.service.interfaces.remote.general.ITaGenCodeExServiceRemote;
import fr.legrain.lib.data.EnumModeObjet;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModeObjetEcranServeur;
import fr.ylyade.courtage.app.AbstractController;
import fr.ylyade.courtage.app.Auth;
import fr.ylyade.courtage.app.ConstWeb;
import fr.ylyade.courtage.app.LgrTab;
import fr.ylyade.courtage.app.LgrTabEvent;
import fr.ylyade.courtage.app.OuvertureDocumentBean;
import fr.ylyade.courtage.app.SessionListener;
import fr.ylyade.courtage.app.TabListModelBean;
import fr.ylyade.courtage.app.TabViewsBean;
import fr.ylyade.courtage.droits.model.TaUtilisateur;
import fr.ylyade.courtage.droits.service.interfaces.remote.ITaUtilisateurServiceRemote;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.dto.TaAssureDTO;
import fr.ylyade.courtage.dto.TaCourtierDTO;
import fr.ylyade.courtage.dto.TaTAssureDTO;
import fr.ylyade.courtage.dto.TaTCiviliteDTO;
import fr.ylyade.courtage.dto.TaTCourtierDTO;
import fr.ylyade.courtage.dto.TaTGroupeTarifDTO;
import fr.ylyade.courtage.dto.TaTJuridiqueDTO;
import fr.ylyade.courtage.model.Const;
import fr.ylyade.courtage.model.ConstPreferences;
import fr.ylyade.courtage.model.TaAssure;
import fr.ylyade.courtage.model.TaCourtier;
import fr.ylyade.courtage.model.TaTAdresse;
import fr.ylyade.courtage.model.TaTAssure;
import fr.ylyade.courtage.model.TaTCivilite;
import fr.ylyade.courtage.model.TaTEmail;
import fr.ylyade.courtage.model.TaTGroupeTarif;
import fr.ylyade.courtage.model.TaTJuridique;
import fr.ylyade.courtage.model.TaTTel;
import fr.ylyade.courtage.model.TaTUtilisateur;
import fr.ylyade.courtage.model.mapping.LgrDozerMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaAdresseServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaAssureServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaCourtierServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaEmailServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTAdresseServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTAssureServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTCiviliteServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTCourtierServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTEmailServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTGroupeTarifServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTJuridiqueServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTTelServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTUtilisateurServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTelServiceRemote;

@Named
@ViewScoped  
public class AssureController extends AbstractController implements Serializable {  

	/**
	 * 
	 */
	private static final long serialVersionUID = -6353486458770843623L;

	@Inject @Named(value="tabListModelCenterBean")
	private TabListModelBean tabsCenter;
	
	@Inject @Named(value="tabViewsBean")
	private TabViewsBean tabViews;
	
//	@Inject(value="#{ouvertureDocumentBean}")
	@Inject @Named(value="ouvertureDocumentBean")
	private OuvertureDocumentBean ouvertureDocumentBean;

	private String paramId;
	
	private TabView tabViewAssure; //Le Binding sur les onglets "secondaire" semble ne pas fonctionné, les onglets ne sont pas générés au bon moment avec le c:forEach

	private List<TaAssureDTO> valuesExtraction; 
	private List<TaAssureDTO> values; 
	//private TaGedUtilisateur selectedTaGedUtilisateur;
	//private TaListeRefDoc selectedTaListeRefDoc;
	
	private @Inject SessionInfo sessionInfo;
	private @Inject Auth auth;
//	private @Inject GedAssureController gedAssureController;
	
	protected ModeObjetEcranServeur modeEcran = new ModeObjetEcranServeur();
	
	private @EJB ITaTCiviliteServiceRemote taTCiviliteService;
	private @EJB ITaTJuridiqueServiceRemote taTJuridiqueService;
	private @EJB ITaTGroupeTarifServiceRemote taTGroupeTarifService;
	private @EJB ITaTCourtierServiceRemote taTCourtierService;
	
	private @EJB ITaTelServiceRemote taTelService;	
	private @EJB ITaEmailServiceRemote taEmailService;
	private @EJB ITaAdresseServiceRemote taAdresseService;
	private @EJB ITaTAdresseServiceRemote taTAdresseService;
	private @EJB ITaTEmailServiceRemote taTEmailService;
	private @EJB ITaTTelServiceRemote taTTelService;
	private @EJB ITaTAssureServiceRemote taTAssureService;
	private @EJB ITaAssureServiceRemote taAssureService;
	private @EJB ITaTUtilisateurServiceRemote taTUtilisateurService;
	private @EJB ITaGenCodeExServiceRemote taGenCodeExService;
	
	
	private @EJB ITaCourtierServiceRemote taCourtierService;
	
	
	private @EJB ITaUtilisateurServiceRemote taUtilisateurService;
	//private @EJB ITaGedUtilisateurServiceRemote taGedUtilisateurService;
	
	//private @EJB ITaListeRefDocServiceRemote taListeRefDocService;
	
	private TaAssureDTO[] selectedTaAssureDTOs; 
	private TaAssure taAssure = new TaAssure();
	private TaAssureDTO newTaAssureDTO = new TaAssureDTO();
	private TaAssureDTO selectedTaAssureDTO = new TaAssureDTO();
	
	private TaCourtier taCourtier = null;
	
	private TaTAssure taTAssure;
	private TaTCivilite taTCivilite;
	private TaTJuridique taTJuridique;
//	private TaTGroupeTarif taTGroupeTarif;
	
	private LgrDozerMapper<TaAssureDTO,TaAssure> mapperUIToModel  = new LgrDozerMapper<TaAssureDTO,TaAssure>();
	private LgrDozerMapper<TaAssure,TaAssureDTO> mapperModelToUI  = new LgrDozerMapper<TaAssure,TaAssureDTO>();
	
	private TaTAssureDTO taTAssureDTO;
	private TaTCiviliteDTO taTCiviliteDTO;
	private TaTJuridiqueDTO taTJuridiqueDTO;
//	private TaTGroupeTarifDTO taTGroupeTarifDTO;
	
	private TaCourtierDTO taCourtierDTO;
	private TaTCourtierDTO taTCourtierDTO;
	
	private BigDecimal montantCaHt = BigDecimal.ZERO;
	private Date dateDebut;
	private Date dateFin;
	
	private boolean modifierMotDePasse = false;
	private boolean creationCompteClientAssure = false;
	
	public AssureController() {  
		//values = getValues();
	}  

	@PostConstruct
	public void postConstruct(){
		refresh();
		//ouvertureDocumentBean.setAssureController(this);
	}
	
	public void refresh(){
		if(auth.getUser()==null) {
			auth.setUser(sessionInfo.getUtilisateur());
		}
		if(auth.isAssure() && !auth.isDevLgr()) {
			try {
				taAssure = taAssureService.findByIdUtilisateur(auth.getUser().getIdUtilisateur());
				
				selectedTaAssureDTO = taAssureService.findByIdDTO(taAssure.getIdAssure());
				updateTab();
				scrollToTop();
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if(auth.isCourtier()) {
			taCourtier = taCourtierService.findByIdUtilisateur(auth.getUser().getIdUtilisateur());
			values = taAssureService.findAllByIdCourtier(taCourtier.getIdCourtier());
		} else if(auth.isYlyade())   {
			values = taAssureService.findAllLight();
			valuesExtraction = taAssureService.findAllLightPlusExtraction();
			}
		
		
		modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
//    	infoEntreprise = taInfoEntrepriseService.findInstance();
//    	dateDebut = infoEntreprise.getDatedebInfoEntreprise();
//    	dateFin = infoEntreprise.getDatefinInfoEntreprise();
//
//    	dateDebutProforma = infoEntreprise.getDatedebInfoEntreprise();
//    	dateFinProforma = infoEntreprise.getDatefinInfoEntreprise();

	}
	
	public List<TaAssureDTO> getValues(){  
		return values;
	}

	public void actAnnuler(ActionEvent actionEvent) {
		try {
			switch (modeEcran.getMode()) {
			case C_MO_INSERTION:
				if(selectedTaAssureDTO.getCodeAssure()!=null) {
					taAssureService.annuleCode(selectedTaAssureDTO.getCodeAssure());
				}
				taAssure = new TaAssure();
				mapperModelToUI.map(taAssure,selectedTaAssureDTO );
				selectedTaAssureDTO=new TaAssureDTO();
				
				if(!values.isEmpty()) selectedTaAssureDTO = values.get(0);
				onRowSelect(null);
				break;
			case C_MO_EDITION:
				if(selectedTaAssureDTO.getId()!=null && selectedTaAssureDTO.getId()!=0){
					taAssure = taAssureService.findById(selectedTaAssureDTO.getId());
					selectedTaAssureDTO=taAssureService.findByIdDTO(selectedTaAssureDTO.getId());
				}				
				break;

			default:
				break;
			}
			modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
			mapperModelToUI.map(taAssure, selectedTaAssureDTO);

			if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Assure", "actAnnuler"));
			}
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void autoCompleteMapUIToDTO() {
		if(taTAssureDTO!=null) {
			validateUIField(Const.C_CODE_T_COURTIER,taTAssureDTO.getCodeTAssure());
			selectedTaAssureDTO.setCodeTAssure(taTAssureDTO.getCodeTAssure());
		}else selectedTaAssureDTO.setCodeTAssure(null);
		
		if(taTCiviliteDTO!=null) {
			validateUIField(Const.C_CODE_T_CIVILITE,taTCiviliteDTO.getCodeTCivilite());
			selectedTaAssureDTO.setCodeTCivilite(taTCiviliteDTO.getCodeTCivilite());
		}else selectedTaAssureDTO.setCodeTAssure(null);
		
		if(taTJuridiqueDTO!=null) {
			validateUIField(Const.C_CODE_T_JURIDIQUE,taTJuridiqueDTO.getCodeTJuridique());
			selectedTaAssureDTO.setCodeTJuridique(taTJuridiqueDTO.getCodeTJuridique());
		}else selectedTaAssureDTO.setCodeTAssure(null);
		
//		if(taTGroupeTarifDTO!=null) {
//			validateUIField(Const.C_CODE_GROUPE,taTGroupeTarifDTO.getCodeGroupe());
//			selectedTaAssureDTO.setCodeGroupe(taTGroupeTarifDTO.getCodeGroupe());
//		}else selectedTaAssureDTO.setCodeGroupe(null);
		
	}
	
	public void autoCompleteMapDTOtoUI() {
		try {
			taTAssure = null;
			taTCivilite = null;
			taTJuridique = null;
			//taCourtier = null;
			
			taTAssureDTO = null;
			taTCiviliteDTO = null;
			taTJuridiqueDTO = null;
			taCourtierDTO = null;

			if(selectedTaAssureDTO.getCodeTAssure()!=null && !selectedTaAssureDTO.getCodeTAssure().equals("")) {
				taTAssure = taTAssureService.findByCode(selectedTaAssureDTO.getCodeTAssure());
				taTAssureDTO = taTAssureService.findByCodeDTO(selectedTaAssureDTO.getCodeTAssure());
			}
			if(selectedTaAssureDTO.getCodeTCivilite()!=null && !selectedTaAssureDTO.getCodeTCivilite().equals("")) {
				taTCivilite = taTCiviliteService.findByCode(selectedTaAssureDTO.getCodeTCivilite());
				taTCiviliteDTO = taTCiviliteService.findByCodeDTO(selectedTaAssureDTO.getCodeTCivilite());
			}
			if(selectedTaAssureDTO.getCodeTJuridique()!=null && !selectedTaAssureDTO.getCodeTJuridique().equals("")) {
				taTJuridique = taTJuridiqueService.findByCode(selectedTaAssureDTO.getCodeTJuridique());
				taTJuridiqueDTO = taTJuridiqueService.findByCodeDTO(selectedTaAssureDTO.getCodeTJuridique());
			}
//			if(selectedTaAssureDTO.getCodeGroupe()!=null && !selectedTaAssureDTO.getCodeGroupe().equals("")) {
//				taTGroupeTarif = taTGroupeTarifService.findByCode(selectedTaAssureDTO.getCodeGroupe());
//				taTGroupeTarifDTO = taTGroupeTarifService.findByCodeDTO(selectedTaAssureDTO.getCodeGroupe());
//			}
			if(taCourtier!=null) {
				taCourtierDTO = taCourtierService.findByCodeDTO(taCourtier.getCodeCourtier());
			}
			
		} catch (FinderException e) {
			e.printStackTrace();
		}
	}
	
	public void actNouveauDevisRcd(ActionEvent actionEvent) {
		ouvreDocument(null,LgrTab.CSS_CLASS_TAB_DEVIS_RCPRO);
	}
	
	public void ouvreDocument(String valeurIdentifiant,String  tabEcran) {
//		if(valeurIdentifiant!=null ) {					
			ouvertureDocumentBean.setEvent(new LgrTabEvent());
			ouvertureDocumentBean.getEvent().setCodeObjet(valeurIdentifiant);
			ouvertureDocumentBean.getEvent().setData(taAssure);
			ouvertureDocumentBean.getEvent().setCssLgrTab(tabEcran);
			ouvertureDocumentBean.getEvent().setAfficheDoc(true);/* A remettre à true dès que l'on pourra afficher le document à la suite d'un dialogue*/
			ouvertureDocumentBean.creerDocument(null);
//		}
	}

	public void actEnregistrer(ActionEvent actionEvent) {	
		
		
		boolean mdpOK = false;
		
		try {
			
		if(modifierMotDePasse && selectedTaAssureDTO.getPassword()!=null 
				&& !selectedTaAssureDTO.getPassword().equals("") 
				&& selectedTaAssureDTO.getPassword().equals(selectedTaAssureDTO.getPasswordConfirm()) ) {
				//TODO verifier la complexité minimum
				
				System.out.println("mot de passe : "+selectedTaAssureDTO.getPassword()+" et le confirm : "+selectedTaAssureDTO.getPasswordConfirm());
				if(selectedTaAssureDTO.getPassword().equals(selectedTaAssureDTO.getPasswordConfirm())) {
					taAssure.getTaUtilisateur().setPassword(taAssure.getTaUtilisateur().passwordHashSHA256_Base64(selectedTaAssureDTO.getPassword()));
					System.out.println("ils sont equals, donc ont insere");
				}
				mdpOK = true;
		} else if(!modifierMotDePasse) {
			mdpOK = true;
		}
		if(selectedTaAssureDTO.getAdresseEmail()!=null) {
			if(taAssure.getTaUtilisateur()!=null) {//on vient de créer un utilisateur (bloc au dessus) ou on est en modif d'un assure qui a deja un compte utilisateur
				taAssure.getTaUtilisateur().setIdentifiant(selectedTaAssureDTO.getAdresseEmail());
			}
		}
		
			
	//		taAssure.initAdresseAssure(null,adresseEstRempli(),ConstPreferencesAssure.CORRESPONDANCE_ADRESSE_ADMINISTRATIF_DEFAUT,
	//				ConstPreferencesAssure.CORRESPONDANCE_ADRESSE_COMMERCIAL_DEFAUT);
	//		if(taAssure.getTaAdresse()!=null){
	//			try {
	//				if(taAssure.getTaAdresse()!=null && taAssure.getTaAdresse().getTaTAdr()==null){
	//				TaTAdr tAdr= taTAdresseService.findByCode(ConstPreferencesAssure.TADR_DEFAUT);
	//				taAssure.getTaAdresse().setTaTAdr(tAdr);
	//				}
	//				int ordre=taAdresseService.rechercheOdrePourType(taAssure.getTaAdresse().getTaTAdr().getCodeTAdr(),taAssure.getCodeAssure());
	//				if(taAssure.getTaAdresse().getOrdre()==null || taAssure.getTaAdresse().getOrdre()==0)taAssure.getTaAdresse().setOrdre(ordre);
	//			} catch (FinderException e) {
	//				
	//			}
	//		}
		if(mdpOK) {
			autoCompleteMapUIToDTO();
	//		initAdresseNull();
	//		if(taAssure.getTaEmail()==null){
	//			selectedTaAssureDTO.setAdresseEmail(null);
	//		}
	//		if(taAssure.getTaTelephone()==null){
	//			selectedTaAssureDTO.setNumeroTelephone(null);
	//		}
	//		if(taAssure.getTaWeb()==null){
	//			selectedTaAssureDTO.setAdresseWeb(null);
	//		}
			
			mapperUIToModel.map(selectedTaAssureDTO, taAssure);
			if(selectedTaAssureDTO.getTitreFonction()!=null) {
				taAssure.getTaContactEntreprise().setFonction(selectedTaAssureDTO.getTitreFonction());
			}
			/*
			 * A faire, mapper les objets dans taAssure avant le merge :
			 * mapping de tous les objets avec des "codes", typeAssure, typeCvilite, ...
			 * tous les objets imbriqués par défaut, adresse défaut, email défaut, ...
			 * 
			 * Pour les converter, essayé de faire fonctionner les injections via omniface
			 * ou, récupérer dans le client RCP le système pour générer les chaines JNDI
			 * 
			 * Ma questions sur les forums
			 * http://forum.primefaces.org/viewtopic.php?f=3&t=40677&sid=fc4d0075c1e57d0433bd8a8d3bdf0393
			 * http://stackoverflow.com/questions/27551615/primefaces-autocomplete-using-a-list-pojo-and-a-string-as-default-value
			 * 
			 */
			
			
			
			
	//			if(taAssure.getCodeCompta()==null 
	//					|| (taAssure.getCodeCompta()!=null && taAssure.getCodeCompta().equals(""))
	//					) {
	//					taAssure.setCodeCompta(taAssure.getCodeAssure().substring(0, 7));
	//				if(taAssure.getCodeAssure().length()>6) {
	//					taAssure.setCodeCompta(taAssure.getCodeAssure().substring(0, 7));
	//				} else  {
	//					taAssure.setCodeCompta(taAssure.getCodeAssure().substring(0, taAssure.getCodeAssure().length()));
	//				}
	//			}
	
				taAssure = taAssureService.merge(taAssure,ITaAssureServiceRemote.validationContext);
				
				if(selectedTaAssureDTO.getCodeAssure()!=null) {
					taAssureService.annuleCode(selectedTaAssureDTO.getCodeAssure());
				}
				
				mapperModelToUI.map(taAssure, selectedTaAssureDTO);
				autoCompleteMapDTOtoUI();
				
				if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_INSERTION)==0) {
					values.add(selectedTaAssureDTO);
					selectedTaAssureDTOs = null;
				}
	//			adresseController.refresh(null);
				modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
	
			}else {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage("Assure", "Le mot de passe est différent de sa confirmation"));
			}
		
		} catch(Exception e) {
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			//context.addMessage(null, new FacesMessage("Assure", "actEnregistrer")); 
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Assure", e.getMessage()));
		}

		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Assure", "actEnregistrer"));
		}
	}

	public void actInserer(ActionEvent actionEvent) {
		try {
			selectedTaAssureDTOs= new TaAssureDTO[]{};
			selectedTaAssureDTO = new TaAssureDTO();
			taAssure = new TaAssure();

			modifierMotDePasse = true;
			String codeTAssureDefaut = TaTAssure.ENTREPRISE_DU_BATIMENT;

			taGenCodeExService.libereVerrouTout(new ArrayList<String>(SessionListener.getSessions().keySet()));
			selectedTaAssureDTO.setCodeAssure(taAssureService.genereCode(null)); //ejb
			validateUIField(Const.C_CODE_COURTIER,selectedTaAssureDTO.getCodeAssure());//permet de verrouiller le code genere

			selectedTaAssureDTO.setCodeTAssure(codeTAssureDefaut);

//			selectedTaAssureDTO.setCodeCompta(selectedTaAssureDTO.getCodeAssure()); //ejb
//			validateUIField(Const.C_CODE_COMPTA,selectedTaAssureDTO.getCodeCompta()); //ejb
//			selectedTaAssureDTO.setCompte("411"); //ejb
//			validateUIField(Const.C_COMPTE,selectedTaAssureDTO.getCompte()); //ejb

			//TaTAssureDAO daoTAssure = new TaTAssureDAO(getEm());
			TaTAssure taTAssure;

			taTAssure = taTAssureService.findByCode(codeTAssureDefaut);
			
			TaUtilisateur taUtilisateur = new TaUtilisateur();
			TaTUtilisateur taTUtilisateur = null; 
			taTUtilisateur = taTUtilisateurService.findByCode(TaTUtilisateur.TYPE_UTILISATEUR_ASSURE);
			taUtilisateur.setTaTUtilisateur(taTUtilisateur);
			taUtilisateur.setAdmin(false);
			taUtilisateur.setSysteme(false);
			taAssure.setTaUtilisateur(taUtilisateur);
			taAssure.setTaCourtier(taCourtier);
			
			taAssure.setCodeAssure(taCourtier.getCodeCourtier());

//			if(taTAssure!=null && taTAssure.getCompteTAssure()!=null) {
//				selectedTaAssureDTO.setCompte(taTAssure.getCompteTAssure());
//				this.taTAssure = taTAssure;
//			} else {
//				//selectedTaAssureDTO.setCompte(AssurePlugin.getDefault().getPreferenceStore().
//				//getString(PreferenceConstants.TIERS_COMPTE_COMPTALE_DEFAUT));
//			}
			selectedTaAssureDTO.setActif(true);
			selectedTaAssureDTO.setClient(false);
//			selectedTaAssureDTO.setCodeTTvaDoc("F");
			
			autoCompleteMapDTOtoUI();
			
			modeEcran.setMode(EnumModeObjet.C_MO_INSERTION);
			scrollToTop();
			if(tabViewAssure!=null) {
				tabViewAssure.setActiveIndex(0);
				updateTab();
//				adresseController.setMasterEntity(taAssure);
//				adresseController.refresh(null);
			}
			if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Assure", "actInserer"));
			}

		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void actModifier() {
		actModifier(null);
	}

	public void actModifier(ActionEvent actionEvent) {
		
		modeEcran.setMode(EnumModeObjet.C_MO_EDITION);
		
		modifierMotDePasse = false;
		selectedTaAssureDTO.setPassword(null);
		selectedTaAssureDTO.setPasswordConfirm(null);

		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Assure", "actModifier"));
		}
	}
	
	public void actAide(ActionEvent actionEvent) {
		
//		RequestContext.getCurrentInstance().openDialog("aide");
    
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 320);
        Map<String,List<String>> params = null;
        RequestContext.getCurrentInstance().openDialog("aide", options, params);
		
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Assure", "actAide"));
		}
	}
    
    public void nouveau(ActionEvent actionEvent) {  
    	LgrTab b = new LgrTab();
    	b.setTypeOnglet(LgrTab.TYPE_TAB_ASSURE);
		b.setTitre("Assure");
		b.setTemplate("assure/assure.xhtml");
		b.setIdTab(LgrTab.ID_TAB_ASSURE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_ASSURE);
		b.setParamId("0");
		
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
		actInserer(actionEvent);
		
		if(ConstWeb.DEBUG) {
    	FacesContext context = FacesContext.getCurrentInstance();  
	    context.addMessage(null, new FacesMessage("Assure", 
	    		"Nouveau"
	    		)); }
    } 
    
    public void supprimer(ActionEvent actionEvent) {
    	actSupprimer(actionEvent);
    }
    
    public void detail() {
    	detail(null);
    }
    
    public void detail(ActionEvent actionEvent) {
    	onRowSelect(null);
    }
	
	public void actSupprimer() {
		actSupprimer(null);
	}
	
	public void actSupprimer(ActionEvent actionEvent) {
		TaAssure taAssure = new TaAssure();
		try {
			if(selectedTaAssureDTO!=null && selectedTaAssureDTO.getId()!=null){
				taAssure = taAssureService.findById(selectedTaAssureDTO.getId());
			}

			taAssureService.remove(taAssure);
			values.remove(selectedTaAssureDTO);
			
			if(!values.isEmpty()) {
				selectedTaAssureDTO = values.get(0);
			}else {
				selectedTaAssureDTO=new TaAssureDTO();
			}
			updateTab();
			modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);

			if(ConstWeb.DEBUG) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage("Assure", "actSupprimer"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Assure", e.getCause().getCause().getCause().getCause().getMessage()));
		} 	    
	}
	
 

	public void actFermer(ActionEvent actionEvent) {
		
		switch (modeEcran.getMode()) {
		case C_MO_INSERTION:
			actAnnuler(actionEvent);
			break;
		case C_MO_EDITION:
			actAnnuler(actionEvent);							
			break;

		default:
			break;
		}
		selectedTaAssureDTO=new TaAssureDTO();
		selectedTaAssureDTOs=new TaAssureDTO[]{selectedTaAssureDTO};

	}
	
	public void actImprimerFiche(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("assure", "actImprimer"));
		}
		try {
			
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.put("assure", taAssureService.findById(selectedTaAssureDTO.getId()));
		
		} catch (FinderException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}    
	
	public void actImprimer(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Assure", "actImprimer"));
		}
		try {
			
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.put("listeAssure", values);
		
		
		// taAssureService.findById(selectedTaAssureDTO.getId())
		
		////////////////////////////////////////////////////////////////////////////////////////
		//Test génération de fichier PDF
		
//		HashMap<String,Object> hm = new HashMap<>();
//		hm.put( "tiers", taAssureService.findById(selectedTaAssureDTO.getId()));
//		BirtUtil.setAppContextEdition(hm);
//		
//		BirtUtil.startReportEngine();
//		
////		BirtUtil.renderReportToFile(
////				"/donnees/Projet/Java/Eclipse/GestionCommerciale_branche_2_0_13_JEE_E46/fr.legrain.solstyce.webapp/src/main/webapp/reports/tiers/FicheAssure.rptdesign", 
////				"/tmp/tiers.pdf", 
////				new HashMap<>(), 
////				BirtUtil.PDF_FORMAT);
//		
//		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("/reports/tiers/FicheAssure.rptdesign");
//		BirtUtil.renderReportToFile(
//				inputStream, 
//				"/tmp/tiers.pdf", 
//				new HashMap<>(), 
//				BirtUtil.PDF_FORMAT);
		////////////////////////////////////////////////////////////////////////////////////////
		//java.net.URL.setURLStreamHandlerFactory();
//		taAssureService.generePDF(selectedTaAssureDTO.getId());
		////////////////////////////////////////////////////////////////////////////////////////
		
//			session.setAttribute("tiers", taAssureService.findById(selectedTaAssureDTO.getId()));
			System.out.println("PaiementCbController.actImprimer()");
//		} catch (FinderException e) {
//			
//			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}    

	public boolean pasDejaOuvert() {
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_ASSURE);
		return tabsCenter.ongletDejaOuvert(b)==null;
	} 
	
	public void onRowSimpleSelect(SelectEvent event) {

		if(pasDejaOuvert()==false){
			onRowSelect(event);

			autoCompleteMapDTOtoUI();
			if(ConstWeb.DEBUG) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage("Assure", 
						"Chargement du tiers N°"+selectedTaAssureDTO.getCodeAssure()
						)); }
		} else {
			tabViews.selectionneOngletCentre(LgrTab.TYPE_TAB_TIERS);
		}
	} 
	
	public void updateTab(){
		try {

			if(selectedTaAssureDTOs!=null && selectedTaAssureDTOs.length>0) {
				selectedTaAssureDTO = selectedTaAssureDTOs[0];
			}
			if(selectedTaAssureDTO.getId()!=null && selectedTaAssureDTO.getId()!=0) {
				taAssure = taAssureService.findById(selectedTaAssureDTO.getId());
			}
			mapperModelToUI.map(taAssure, selectedTaAssureDTO);	
			
//			adresseController.setMasterEntity(taAssure);
//			adresseController.refresh(null);
//			
//			emailController.setMasterEntity(taAssure);
//			emailController.refresh();
//			
//			banqueController.setMasterEntity(taAssure);
//			banqueController.refresh();
			
//			gedAssureController.setMasterEntity(taAssure);
//			gedAssureController.refresh();
			
			autoCompleteMapDTOtoUI();
			montantCaHt = BigDecimal.ZERO;
//			if(selectedTaAssureDTO.getCodeAssure()!=null) {
//				listeCaPeriodeFactureAssure = taAssureService.findChiffreAffaireTotalParCodeAssureDTO(dateDebut, dateFin, selectedTaAssureDTO.getCodeAssure());
//				if (listeCaPeriodeFactureAssure != null && ! listeCaPeriodeFactureAssure.isEmpty()) {
//					montantCaHt = listeCaPeriodeFactureAssure.get(0).getMtHtCalc();
//					if (montantCaHt == null) {
//						montantCaHt = BigDecimal.ZERO;
//					}
//				}
//			}
			
		} catch (FinderException e) {
			e.printStackTrace();
		}
	}
	
	public void onTabShow() {
		System.out.println("ArticleController.onTabShow()");
	}
	
	public void onTabChange(TabChangeEvent event) {
//		FacesMessage msg = null;
//		if(event.getTab()!=null) {
//			msg = new FacesMessage("Tab Changed ArticleController", "Active Tab: " + event.getTab().getTitle());
//		} else {
//			msg = new FacesMessage("Tab Changed ArticleController", "Active Tab: ");
//		}
//        FacesContext.getCurrentInstance().addMessage(null, msg);
		
		/*
		 * Si trop lent voir au cas par cas pour :
		 * 	- soit mettre à jour uniquement l'onglet qui va s'afficher, 
		 *  - soit mettre à jour les propriété du masterEntity en fonction de ce qui vient d'être fait sur l'onlget précendent
		 * Par exemple mettre à jour la liste des controle dans l'article courant au lieu de recharger l'article entièrement
		 * 
		 * Sinon chercher ce que l'on peu faire avec un refresh JPA
		 */
		updateTab(); 
	}
	
	public boolean disabledTab(String nomTab) {
		//return modeEcran.dataSetEnModif() ||  selectedTaAssureDTO.getId()==0;
		return true;
	}
	
	public void onRowSelect(SelectEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_ASSURE);
		b.setTitre("Assure");
		b.setTemplate("assure/assure.xhtml");
		b.setIdTab(LgrTab.ID_TAB_ASSURE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_ASSURE);
		b.setParamId(LibConversion.integerToString(selectedTaAssureDTO.getId()));

		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
		
		modifierMotDePasse = false;
		selectedTaAssureDTO.setPassword(null);
		selectedTaAssureDTO.setPasswordConfirm(null);
		
		updateTab();
		scrollToTop();
		
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Assure", 
				"Chargement du tiers N°"+selectedTaAssureDTO.getCodeAssure()
				)); 
		}
	} 
	
	public void onRowToggle(ToggleEvent event) {

	
	}
	
	public void initListeLigneFacture() {
		
	}
	

	
	public boolean editable() {
		return !modeEcran.dataSetEnModif();
	}
	public boolean editableIfNovarisksOnly() {
		//si l'utilisateur connecté est Novarisks
		if(auth.isYlyade()) {
			//si on est pas en modif
			return !modeEcran.dataSetEnModif();
		}
		//renvoi true pour bloquer la modification ou false pour l'autoriser si c'est Novarisks et qu'on est en modif
		return true;
			
		
	}
	//champs editable que si en modif par le courtier si assure (et par novarisks)
	public boolean editableIfAssureOnly() {
		//si l'utilisateur connecté est Novarisks ou un courtier
		if(auth.isYlyade()) {
			//si on est pas en modif
			return !modeEcran.dataSetEnModif();
		
		//si c'est un courtier
		}else if(auth.isCourtier()) {
			//si l'assure n'est pas un client
			if(!taAssure.getClient()) {
				//on bloque pas si on est en modif
				return !modeEcran.dataSetEnModif();
			}else {
				//on bloque car c'est un client
				return true;
			}
		}
		//renvoi true pour bloquer la modification ou false pour l'autoriser si c'est Novarisks et qu'on est en modif
		return true;
			
		
	}
	

	
	public void actDialogCourtier(ActionEvent actionEvent) {
		
//		RequestContext.getCurrentInstance().openDialog("aide");
    
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 640);
        options.put("modal", true);
        
        //Map<String,List<String>> params = null;
        Map<String,List<String>> params = new HashMap<String,List<String>>();
        List<String> list = new ArrayList<String>();
        list.add(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION));
        params.put("modeEcranDefaut", list);
        
        RequestContext.getCurrentInstance().openDialog("courtier/dialog_courtier", options, params);
	}
	
	public void actDialogTypeAssure(ActionEvent actionEvent) {
		
//		RequestContext.getCurrentInstance().openDialog("aide");
    
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 640);
        options.put("modal", true);
        
        //Map<String,List<String>> params = null;
        Map<String,List<String>> params = new HashMap<String,List<String>>();
        List<String> list = new ArrayList<String>();
        list.add(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION));
        params.put("modeEcranDefaut", list);
        
        RequestContext.getCurrentInstance().openDialog("parametres/dialog_type_courtier", options, params);
		
//		FacesContext context = FacesContext.getCurrentInstance();  
//		context.addMessage(null, new FacesMessage("Assure", "actAbout")); 	    
	}
	
	public void handleReturnDialogTypeAssure(SelectEvent event) {
		if(event!=null && event.getObject()!=null) {
			taTAssure = (TaTAssure) event.getObject();
			try {
				taTAssureDTO = taTAssureService.findByCodeDTO(taTAssure.getCodeTAssure());
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void handleReturnDialogCourtier(SelectEvent event) {
		if(event!=null && event.getObject()!=null) {
			taCourtier = (TaCourtier) event.getObject();
			try {
				taCourtierDTO = taCourtierService.findByCodeDTO(taCourtier.getCodeCourtier());
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void actDialogTypeCivilite(ActionEvent actionEvent) {
		
//		RequestContext.getCurrentInstance().openDialog("aide");
    
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 640);
        options.put("modal", true);
        
        //Map<String,List<String>> params = null;
        Map<String,List<String>> params = new HashMap<String,List<String>>();
        List<String> list = new ArrayList<String>();
        list.add(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION));
        params.put("modeEcranDefaut", list);
        
        RequestContext.getCurrentInstance().openDialog("parametres/dialog_type_civilite", options, params);
		
//		FacesContext context = FacesContext.getCurrentInstance();  
//		context.addMessage(null, new FacesMessage("Assure", "actAbout")); 	    
	}
	
	public void handleReturnDialogTypeCivilite(SelectEvent event) {
		if(event!=null && event.getObject()!=null) {
			taTCivilite = (TaTCivilite) event.getObject();
			try {
				taTCiviliteDTO = taTCiviliteService.findByCodeDTO(taTCivilite.getCodeTCivilite());
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			case "inserer":
			case "fermer":
				retour = false;
				break;
			case "supprimerListe":retour = false;break;	
			case "supprimer":
			case "modifier":
			case "imprimer":
				if(selectedTaAssureDTO!=null && selectedTaAssureDTO.getId()!=null  && selectedTaAssureDTO.getId()!=0 ) retour = false;
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

	public TaAssureDTO[] getSelectedTaAssureDTOs() {
		return selectedTaAssureDTOs;
	}

	public void setSelectedTaAssureDTOs(TaAssureDTO[] selectedTaAssureDTOs) {
		this.selectedTaAssureDTOs = selectedTaAssureDTOs;
	}

	public TaAssureDTO getNewTaAssureDTO() {
		return newTaAssureDTO;
	}

	public void setNewTaAssureDTO(TaAssureDTO newTaAssureDTO) {
		this.newTaAssureDTO = newTaAssureDTO;
	}

	public TaAssureDTO getSelectedTaAssureDTO() {
		return selectedTaAssureDTO;
	}

	public void setSelectedTaAssureDTO(TaAssureDTO selectedTaAssureDTO) {
		this.selectedTaAssureDTO = selectedTaAssureDTO;
	}

	public TabListModelBean getTabsCenter() {
		return tabsCenter;
	}

	public void setTabsCenter(TabListModelBean tabsCenter) {
		this.tabsCenter = tabsCenter;
	}

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	} 

	public void validateAssure(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	
		String messageComplet = "";
		
		try {
		  //String selectedRadio = (String) value;
		 
		  String nomChamp =  (String) component.getAttributes().get("nomChamp");
		  
		  //msg = "Mon message d'erreur pour : "+nomChamp;
		  
//		  validateUIField(nomChamp,value);
//		  TaAssureDTO dtoTemp=new TaAssureDTO();
//		  PropertyUtils.setSimpleProperty(dtoTemp, nomChamp, value);
//		  taAssureService.validateDTOProperty(dtoTemp, nomChamp, ITaAssureServiceRemote.validationContext );
		  
//			//validation automatique via la JSR bean validation
		  ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		  Set<ConstraintViolation<TaAssureDTO>> violations = factory.getValidator().validateValue(TaAssureDTO.class,nomChamp,value);
			if (violations.size() > 0) {
				messageComplet = "Erreur de validation : ";
//				List<IStatus> statusList = new ArrayList<IStatus>();
				for (ConstraintViolation<TaAssureDTO> cv : violations) {
//					statusList.add(ValidationStatus.error(cv.getMessage()));
					messageComplet+=" "+cv.getMessage()+"\n";
				}
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, messageComplet, messageComplet));
//				return new MultiStatus(LibrairiesEcranPlugin.PLUGIN_ID, IStatus.ERROR,
//						//statusList.toArray(new IStatus[statusList.size()]), "Validation errors", null);
//						statusList.toArray(new IStatus[statusList.size()]), messageComplet, null);
			} else {
				//aucune erreur de validation "automatique", on déclanche les validations du controller
//				if(controller!=null) {
					 validateUIField(nomChamp,value);
//					if(!s.isOK()) {
//						return s;
//					}
//				}
			}
//			return ValidationStatus.ok();
		  
		  //selectedTaAssureDTO.setAdresse1Adresse("abcd");
		  
//		  if(selectedRadio.equals("aa")) {
//			  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
//		  }

		} catch(Exception e) {
			//messageComplet += e.getMessage();
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, messageComplet, messageComplet));
		}
	}
	
	public void autcompleteSelection(SelectEvent event) {
		Object value = event.getObject();
//		FacesMessage msg = new FacesMessage("Selected", "Item:" + value);
		
		String nomChamp =  (String) event.getComponent().getAttributes().get("nomChamp");

		if(value!=null) {
			if(value instanceof TaTAssureDTO && ((TaTAssureDTO) value).getCodeTAssure()!=null && ((TaTAssureDTO) value).getCodeTAssure().equals(Const.C_AUCUN))value=null;
		}
		
		validateUIField(nomChamp,value);
	}
	
	
	public List<TaCourtierDTO> courtierAutoCompleteLight(String query) {
        List<TaCourtierDTO> allValues = taCourtierService.selectAllDTO();
        List<TaCourtierDTO> filteredValues = new ArrayList<TaCourtierDTO>();
         
        for (int i = 0; i < allValues.size(); i++) {
        	TaCourtierDTO civ = allValues.get(i);
            if(query.equals("*") || civ.getCodeCourtier().toLowerCase().contains(query.toLowerCase())) {
                filteredValues.add(civ);
            }
        }
         
        return filteredValues;
    }
	
public boolean validateUIField(String nomChamp,Object value) {
		
		boolean changement=false;

		try {				
			if(nomChamp.equals(Const.C_CODE_T_ASSURE)) {
				TaTAssure entity = null;
				if(value!=null) {
					if(value instanceof TaTAssure) {
						entity=(TaTAssure) value;
					}else if (value instanceof TaTAssureDTO){
						entity = taTAssureService.findByCode(((TaTAssureDTO)value).getCodeTAssure());	
					}else{
						entity = taTAssureService.findByCode((String)value);
					}
				} else {
					selectedTaAssureDTO.setCodeTAssure("");
					taTAssureDTO.setCodeTAssure("");
					taTAssure=null;
				}						
				taAssure.setTaTAssure(entity);
				//taTAssure=entity;
				
			}else if (nomChamp.equals(Const.C_CODE_COURTIER)){ /**********************************************objet courtier********************************/

				TaCourtier entity = null;
				if(value!=null) {
					if(value instanceof TaCourtier) {
						entity=(TaCourtier) value;
					}else if (value instanceof TaCourtierDTO){
						entity = taCourtierService.findByCode(((TaCourtierDTO)value).getCodeCourtier());	
					}else{
						entity = taCourtierService.findByCode((String)value);
					}
				} else {
					selectedTaAssureDTO.setCodeCourtier("");
					taCourtierDTO.setCodeCourtier("");
					taCourtier=null;
				}						
				taAssure.setTaCourtier(entity);
				//taTAssure=entity;

				
				
				
			} else if(nomChamp.equals(Const.C_CODE_T_CIVILITE)) {
				TaTCivilite entity = null;
				if(value!=null) {
					if(value instanceof TaTCivilite) {
						entity=(TaTCivilite) value;
					}else if (value instanceof TaTCiviliteDTO){
						entity = taTCiviliteService.findByCode(((TaTCivilite)value).getCodeTCivilite());	
					}else{
						entity = taTCiviliteService.findByCode((String)value);
					}
				} else {
					selectedTaAssureDTO.setCodeTCivilite("");
					taTCiviliteDTO.setCodeTCivilite("");
					taTCivilite=null;
				}						
				taAssure.getTaContactEntreprise().setTaTCivilite(entity);
				
//			}else if(nomChamp.equals(Const.C_CODE_T_CIVILITE)) {
//					TaTCivilite entity = null;
//					if(value!=null) {
//						if(value instanceof TaTCivilite) {
//							entity=(TaTCivilite) value;
//						}else if (value instanceof TaTCiviliteDTO){
//							entity = taTCiviliteService.findByCode(((TaTCivilite)value).getCodeTCivilite());	
//						}else{
//							entity = taTCiviliteService.findByCode((String)value);
//						}
//					} else {
//						selectedTaAssureDTO.setCodeTCivilite("");
//						taTCiviliteDTO.setCodeTCivilite("");
//						taTCivilite=null;
//					}						
//					taAssure.getTaContactEntreprise().setTaTCivilite(entity);
					
				} else if(nomChamp.equals(Const.C_CODE_T_JURIDIQUE)) {
				TaTJuridique entity = null;
				if(value!=null) {
					if(value instanceof TaTJuridique) {
						entity=(TaTJuridique) value;
					}else if (value instanceof TaTJuridiqueDTO){
						entity = taTJuridiqueService.findByCode(((TaTJuridiqueDTO)value).getCodeTJuridique());
					}else{
						entity = taTJuridiqueService.findByCode((String)value);
					}
				} else {
					selectedTaAssureDTO.setCodeTJuridique("");
					taTJuridiqueDTO.setCodeTJuridique("");
					taTJuridique=null;
				}						
				taAssure.setTaTJuridique(entity);
//			} else if(nomChamp.equals(Const.C_CODE_GROUPE)) {
//				TaTGroupeTarif entity = null;
//				if(value!=null) {
//					if(value instanceof TaTGroupeTarif) {
//						entity=(TaTGroupeTarif) value;
//					}else if (value instanceof TaTGroupeTarif){
//						entity = taTGroupeTarifService.findByCode(((TaTGroupeTarifDTO)value).getCodeGroupe());
//					}else{
//						entity = taTGroupeTarifService.findByCode((String)value);
//					}
//				} else {
//					selectedTaAssureDTO.setCodeGroupe("");
//					taTGroupeTarifDTO.setCodeGroupe("");
//					taTGroupeTarif=null;
//				}						
//				taAssure.setTaTGroupeTarif(entity);
			} else if(nomChamp.equals(Const.C_NUMERO_TEL)) {
				taAssure.initContactEntrepriseAssure("test"); //création du ContactEntreprise par defaut s'il n'existe pas
				taAssure.getTaContactEntreprise().initTelephoneDefaut(value);
				if(value!=null && !value.equals("")) { 
					PropertyUtils.setSimpleProperty(taAssure.getTaContactEntreprise().getTaTel(), nomChamp, value);
				}
				if(taAssure.getTaContactEntreprise().getTaTel()==null)selectedTaAssureDTO.setNumeroTel(null);
				if(taAssure.getTaContactEntreprise().getTaTel()!=null &&
						taAssure.getTaContactEntreprise().getTaTel().getTaTTel()==null){
					TaTTel taTTel = new TaTTel();
					taTTel.setCodeTTel(ConstPreferences.TTEL_DEFAUT);
					if(!taTTel.getCodeTTel().equals("")){
						//TaTTelDAO taTTelDAO = new TaTTelDAO(getEm());
						taTTel=taTTelService.findByCode(taTTel.getCodeTTel());
						taAssure.getTaContactEntreprise().getTaTel().setTaTTel(taTTel);
					}
				}					
			}else if(nomChamp.equals(Const.C_ADRESSE_EMAIL)) {
				taAssure.initContactEntrepriseAssure("test"); //création du ContactEntreprise par defaut s'il n'existe pas
				taAssure.getTaContactEntreprise().initEmailDefaut(value);
				if(value!=null && !value.equals("")) {
					PropertyUtils.setSimpleProperty(taAssure.getTaContactEntreprise().getTaEmail(), nomChamp, value);
				}
				if(taAssure.getTaContactEntreprise().getTaEmail()==null)selectedTaAssureDTO.setAdresseEmail(null);
				if(taAssure.getTaContactEntreprise().getTaEmail()!=null &&
						taAssure.getTaContactEntreprise().getTaEmail().getTaTEmail()==null){
					TaTEmail taTEmail = new TaTEmail();
					taTEmail.setCodeTEmail(ConstPreferences.TEMAIL_DEFAUT);
					if(!taTEmail.getCodeTEmail().equals("")){
						taTEmail=taTEmailService.findByCode(taTEmail.getCodeTEmail());
						taAssure.getTaContactEntreprise().getTaEmail().setTaTEmail(taTEmail);
					}
				}
				taAssure.getTaUtilisateur().setIdentifiant((String)value);
			}else if(nomChamp.equals(Const.C_NOM)
					|| nomChamp.equals(Const.C_PRENOM)) {
				taAssure.initContactEntrepriseAssure("test"); //création du ContactEntreprise par defaut s'il n'existe pas
				if(value!=null && !value.equals("")) {
					PropertyUtils.setSimpleProperty(taAssure.getTaContactEntreprise(), nomChamp, value);
				}
//				if(taAssure.getTaContactEntreprise()==null)selectedTaAssureDTO.setAdresseEmail(null);
				
			}else if(nomChamp.equals(Const.C_ADRESSE_LIGNE_1)
					|| nomChamp.equals(Const.C_ADRESSE_LIGNE_2)
					|| nomChamp.equals(Const.C_ADRESSE_LIGNE_3)
					|| nomChamp.equals(Const.C_CODE_POSTAL)
					|| nomChamp.equals(Const.C_VILLE)
					|| nomChamp.equals(Const.C_PAYS)) {
				taAssure.initContactEntrepriseAssure("test"); //création du ContactEntreprise par defaut s'il n'existe pas
				
				taAssure.getTaContactEntreprise().initAdresseDefaut(value,adresseEstRempli());
				if(value!=null) {	
					if(taAssure.getTaContactEntreprise().getTaAdresse()!=null) {
						PropertyUtils.setSimpleProperty(taAssure.getTaContactEntreprise().getTaAdresse(), nomChamp, value);
					}
				}
				if(taAssure.getTaContactEntreprise().getTaAdresse()==null){
					initAdresseNull();
				}
				if(taAssure.getTaContactEntreprise().getTaAdresse()!=null &&
						taAssure.getTaContactEntreprise().getTaAdresse().getTaTAdresse()==null){
					TaTAdresse taTAdr = new TaTAdresse();
					taTAdr.setCodeTAdresse(ConstPreferences.TADR_DEFAUT);
					if(!taTAdr.getCodeTAdresse().equals("")){
						taTAdr = taTAdresseService.findByCode(taTAdr.getCodeTAdresse());
						taAssure.getTaContactEntreprise().getTaAdresse().setTaTAdresse(taTAdr);
					}
				}			
			} else if(nomChamp.equals(Const.C_IDENTIFIANT)
					|| nomChamp.equals(Const.C_PASSWORD)
					|| nomChamp.equals(Const.C_PASSWORD+"_CONFIRMATION")) {
//				FacesContext context = FacesContext.getCurrentInstance();  
//				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Tiers", "sfs"));
				if(modifierMotDePasse && taAssure.getTaUtilisateur()==null) {
					
					TaUtilisateur taUtilisateur = new TaUtilisateur();
					TaTUtilisateur taTUtilisateur = null; 
					taTUtilisateur = taTUtilisateurService.findByCode(TaTUtilisateur.TYPE_UTILISATEUR_ASSURE);
					taUtilisateur.setTaTUtilisateur(taTUtilisateur);
					taUtilisateur.setAdmin(false);
					taUtilisateur.setSysteme(false);
					taAssure.setTaUtilisateur(taUtilisateur);
				}
					
				if(nomChamp.equals(Const.C_IDENTIFIANT)) {
					if(value!=null && !value.equals("")) { 
						PropertyUtils.setSimpleProperty(taAssure.getTaUtilisateur(), nomChamp, value);
					} 
				}else if(nomChamp.equals(Const.C_PASSWORD)) {
					if(value!=null && !value.equals("")) {
						PropertyUtils.setSimpleProperty(taAssure.getTaUtilisateur(), nomChamp, taAssure.getTaUtilisateur().passwordHashSHA256_Base64((String)value));
					}
				}else if(nomChamp.equals(Const.C_PASSWORD+"_CONFIRMATION")) {
					PropertyUtils.setSimpleProperty(selectedTaAssureDTO,"passwordConfirm", taAssure.getTaUtilisateur().passwordHashSHA256_Base64((String)value));
					System.out.println("valeur de value avant hash "+value);
					value = taAssure.getTaUtilisateur().passwordHashSHA256_Base64((String)value);
					System.out.println("valeur de value aprés hash "+value);
					System.out.println("valeur de getPassword "+taAssure.getTaUtilisateur().getPassword());
					if(!value.equals(taAssure.getTaUtilisateur().getPassword())) {
						System.out.println("les hashs sont différents");
						FacesContext context = FacesContext.getCurrentInstance();  
						context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Tiers", "les champs mot de passe et confirmation de mot de passe doivent être identiques"));
						
					}
				}
				
			
				
			}
			return false;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void initAdresseNull() {
		if(taAssure.getTaContactEntreprise().getTaAdresse()==null){
			selectedTaAssureDTO.setAdresseLigne1(null);
			selectedTaAssureDTO.setAdresseLigne2(null);
			selectedTaAssureDTO.setAdresseLigne3(null);
			selectedTaAssureDTO.setCodePostal(null);
			selectedTaAssureDTO.setVille(null);
			selectedTaAssureDTO.setPays(null);
		}
	}
	
	private boolean adresseEstRempli() {
		boolean retour=false;
		if(selectedTaAssureDTO.getAdresseLigne1()!=null && !selectedTaAssureDTO.getAdresseLigne1().equals(""))return true;
		if(selectedTaAssureDTO.getAdresseLigne2()!=null && !selectedTaAssureDTO.getAdresseLigne2().equals(""))return true;
		if(selectedTaAssureDTO.getAdresseLigne3()!=null && !selectedTaAssureDTO.getAdresseLigne3().equals(""))return true;
		if(selectedTaAssureDTO.getCodePostal()!=null && !selectedTaAssureDTO.getCodePostal().equals(""))return true;
		if(selectedTaAssureDTO.getVille()!=null && !selectedTaAssureDTO.getVille().equals(""))return true;
		if(selectedTaAssureDTO.getPays()!=null && !selectedTaAssureDTO.getPays().equals(""))return true;
		return retour;
	}

	public List<TaTAssure> typeAssureAutoComplete(String query) {
        List<TaTAssure> allValues = taTAssureService.selectAll();
        List<TaTAssure> filteredValues = new ArrayList<TaTAssure>();
         
        for (int i = 0; i < allValues.size(); i++) {
        	TaTAssure civ = allValues.get(i);
            if(query.equals("*") || civ.getCodeTAssure().toLowerCase().contains(query.toLowerCase())) {
                filteredValues.add(civ);
            }
        }
         
        return filteredValues;
    }
	public List<TaTAssureDTO> typeAssureAutoCompleteLight(String query) {
        List<TaTAssureDTO> allValues = taTAssureService.selectAllDTO();
        List<TaTAssureDTO> filteredValues = new ArrayList<TaTAssureDTO>();
         
        for (int i = 0; i < allValues.size(); i++) {
        	TaTAssureDTO civ = allValues.get(i);
            if(query.equals("*") || civ.getCodeTAssure().toLowerCase().contains(query.toLowerCase())) {
                filteredValues.add(civ);
            }
        }
         
        return filteredValues;
    }
	
	public List<TaTGroupeTarif> typeGroupeTarifAutoComplete(String query) {
        List<TaTGroupeTarif> allValues = taTGroupeTarifService.selectAll();
        List<TaTGroupeTarif> filteredValues = new ArrayList<TaTGroupeTarif>();
         
        for (int i = 0; i < allValues.size(); i++) {
        	TaTGroupeTarif civ = allValues.get(i);
            if(query.equals("*") || civ.getCodeGroupe().toLowerCase().contains(query.toLowerCase())) {
                filteredValues.add(civ);
            }
        }
         
        return filteredValues;
    }
	public List<TaTGroupeTarifDTO> typeGroupeTarifAutoCompleteLight(String query) {
        List<TaTGroupeTarifDTO> allValues = taTGroupeTarifService.selectAllDTO();
        List<TaTGroupeTarifDTO> filteredValues = new ArrayList<TaTGroupeTarifDTO>();
         
        for (int i = 0; i < allValues.size(); i++) {
        	TaTGroupeTarifDTO civ = allValues.get(i);
            if(query.equals("*") || civ.getCodeGroupe().toLowerCase().contains(query.toLowerCase())) {
                filteredValues.add(civ);
            }
        }
         
        return filteredValues;
    }
	
	public List<TaTJuridique> typeJuridiqueAutoComplete(String query) {
        List<TaTJuridique> allValues = taTJuridiqueService.selectAll();
        List<TaTJuridique> filteredValues = new ArrayList<TaTJuridique>();
         
        for (int i = 0; i < allValues.size(); i++) {
        	TaTJuridique civ = allValues.get(i);
            if(query.equals("*") || civ.getCodeTJuridique().toLowerCase().contains(query.toLowerCase())) {
                filteredValues.add(civ);
            }
        }
         
        return filteredValues;
    }
	public List<TaTJuridiqueDTO> typeJuridiqueAutoCompleteLight(String query) {
        List<TaTJuridiqueDTO> allValues = taTJuridiqueService.selectAllDTO();
        List<TaTJuridiqueDTO> filteredValues = new ArrayList<TaTJuridiqueDTO>();
         
        for (int i = 0; i < allValues.size(); i++) {
        	TaTJuridiqueDTO civ = allValues.get(i);
            if(query.equals("*") || civ.getCodeTJuridique().toLowerCase().contains(query.toLowerCase())) {
                filteredValues.add(civ);
            }
        }
         
        return filteredValues;
    }
	
	public List<TaTCivilite> typeCiviliteAutoComplete(String query) {
        List<TaTCivilite> allValues = taTCiviliteService.selectAll();
        List<TaTCivilite> filteredValues = new ArrayList<TaTCivilite>();
         
        for (int i = 0; i < allValues.size(); i++) {
        	TaTCivilite civ = allValues.get(i);
            if(query.equals("*") || civ.getCodeTCivilite().toLowerCase().contains(query.toLowerCase())) {
                filteredValues.add(civ);
            }
        }
         
        return filteredValues;
    }
	public List<TaTCiviliteDTO> typeCiviliteAutoCompleteLight(String query) {
        List<TaTCiviliteDTO> allValues = taTCiviliteService.selectAllDTO();
        List<TaTCiviliteDTO> filteredValues = new ArrayList<TaTCiviliteDTO>();
         
        for (int i = 0; i < allValues.size(); i++) {
        	TaTCiviliteDTO civ = allValues.get(i);
            if(query.equals("*") || civ.getCodeTCivilite().toLowerCase().contains(query.toLowerCase())) {
                filteredValues.add(civ);
            }
        }
         
        return filteredValues;
    }

	public TaTAssure getTaTAssure() {
		return taTAssure;
	}

	public void setTaTAssure(TaTAssure taTAssure) {
		this.taTAssure = taTAssure;
	}

	public TaTCivilite getTaTCivilite() {
		return taTCivilite;
	}

	public void setTaTCivilite(TaTCivilite taTCivilite) {
		this.taTCivilite = taTCivilite;
	}

	public TabViewsBean getTabViews() {
		return tabViews;
	}

	public void setTabViews(TabViewsBean tabViews) {
		this.tabViews = tabViews;
	}

	public ModeObjetEcranServeur getModeEcran() {
		return modeEcran;
	}

	public void setModeEcran(ModeObjetEcranServeur modeEcran) {
		this.modeEcran = modeEcran;
	}

	public TaAssure getTaAssure() {
		return taAssure;
	}

	public void setTaAssure(TaAssure taAssure) {
		this.taAssure = taAssure;
	}

	public TaAssureDTO rempliDTO(){
		if(taAssure!=null){			
			try {
				taAssure=taAssureService.findByCode(taAssure.getCodeAssure());
				mapperModelToUI.map(taAssure, selectedTaAssureDTO);
				if(values.contains(selectedTaAssureDTO))values.add(selectedTaAssureDTO);
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return selectedTaAssureDTO;
	}


	public TabView getTabViewAssure() {
		return tabViewAssure;
	}

	public void setTabViewAssure(TabView tabViewAssure) {
		this.tabViewAssure = tabViewAssure;
	}


	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	
	public TaTJuridique getTaTJuridique() {
		return taTJuridique;
	}

	public void setTaTJuridique(TaTJuridique taTJuridique) {
		this.taTJuridique = taTJuridique;
	}

//	public TaTGroupeTarif getTaTGroupeTarif() {
//		return taTGroupeTarif;
//	}
//
//	public void setTaTGroupeTarif(TaTGroupeTarif taTGroupeTarif) {
//		this.taTGroupeTarif = taTGroupeTarif;
//	}

	public TaTJuridiqueDTO getTaTJuridiqueDTO() {
		return taTJuridiqueDTO;
	}

	public void setTaTJuridiqueDTO(TaTJuridiqueDTO taTJuridiqueDTO) {
		this.taTJuridiqueDTO = taTJuridiqueDTO;
	}

//	public TaTGroupeTarifDTO getTaTGroupeTarifDTO() {
//		return taTGroupeTarifDTO;
//	}
//
//	public void setTaTGroupeTarifDTO(TaTGroupeTarifDTO taTGroupeTarifDTO) {
//		this.taTGroupeTarifDTO = taTGroupeTarifDTO;
//	}
	
	public TaTAssureDTO getTaTAssureDTO() {
		return taTAssureDTO;
	}

	public void setTaTAssureDTO(TaTAssureDTO taTAssureDTO) {
		this.taTAssureDTO = taTAssureDTO;
	}

	public TaTCiviliteDTO getTaTCiviliteDTO() {
		return taTCiviliteDTO;
	}

	public void setTaTCiviliteDTO(TaTCiviliteDTO taTCiviliteDTO) {
		this.taTCiviliteDTO = taTCiviliteDTO;
	}

	public TaCourtierDTO getTaCourtierDTO() {
		return taCourtierDTO;
	}

	public void setTaCourtierDTO(TaCourtierDTO taCourtierDTO) {
		this.taCourtierDTO = taCourtierDTO;
	}

	public TaTCourtierDTO getTaTCourtierDTO() {
		return taTCourtierDTO;
	}

	public void setTaTCourtierDTO(TaTCourtierDTO taTCourtierDTO) {
		this.taTCourtierDTO = taTCourtierDTO;
	}



	public TaCourtier getTaCourtier() {
		return taCourtier;
	}

	public void setTaCourtier(TaCourtier taCourtier) {
		this.taCourtier = taCourtier;
	}

	public OuvertureDocumentBean getOuvertureDocumentBean() {
		return ouvertureDocumentBean;
	}

	public void setOuvertureDocumentBean(OuvertureDocumentBean ouvertureDocumentBean) {
		this.ouvertureDocumentBean = ouvertureDocumentBean;
//		this.ouvertureDocumentBean.setAssureController(this);
	}

	public boolean isModifierMotDePasse() {
		return modifierMotDePasse;
	}

	public void setModifierMotDePasse(boolean modifierMotDePasse) {
		this.modifierMotDePasse = modifierMotDePasse;
	}

	public boolean isCreationCompteClientAssure() {
		return creationCompteClientAssure;
	}

	public void setCreationCompteClientAssure(boolean creationCompteClientAssure) {
		this.creationCompteClientAssure = creationCompteClientAssure;
	}

	public List<TaAssureDTO> getValuesExtraction() {
		return valuesExtraction;
	}

	public void setValuesExtraction(List<TaAssureDTO> valuesExtraction) {
		this.valuesExtraction = valuesExtraction;
	}



//	public GedAssureController getGedAssureController() {
//		return gedAssureController;
//	}
//
//	public void setGedAssureController(GedAssureController gedAssureController) {
//		this.gedAssureController = gedAssureController;
//	}


}  
