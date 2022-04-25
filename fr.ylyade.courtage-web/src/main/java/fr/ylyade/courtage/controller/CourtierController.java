package fr.ylyade.courtage.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import fr.ylyade.courtage.app.SessionListener;
import fr.ylyade.courtage.app.TabListModelBean;
import fr.ylyade.courtage.app.TabViewsBean;
import fr.ylyade.courtage.droits.model.TaUtilisateur;
import fr.ylyade.courtage.droits.service.interfaces.remote.ITaUtilisateurServiceRemote;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.dto.TaCourtierDTO;
import fr.ylyade.courtage.dto.TaReglementAssuranceDTO;
import fr.ylyade.courtage.dto.TaReglementPrestationDTO;
import fr.ylyade.courtage.dto.TaTCiviliteDTO;
import fr.ylyade.courtage.dto.TaTCourtierDTO;
import fr.ylyade.courtage.dto.TaTGroupeTarifDTO;
import fr.ylyade.courtage.dto.TaTJuridiqueDTO;
import fr.ylyade.courtage.model.Const;
import fr.ylyade.courtage.model.ConstPreferences;
import fr.ylyade.courtage.model.TaCourtier;
import fr.ylyade.courtage.model.TaDocumentPdf;
import fr.ylyade.courtage.model.TaTAdresse;
import fr.ylyade.courtage.model.TaTCivilite;
import fr.ylyade.courtage.model.TaTCourtier;
import fr.ylyade.courtage.model.TaTEmail;
import fr.ylyade.courtage.model.TaTGroupeTarif;
import fr.ylyade.courtage.model.TaTJuridique;
import fr.ylyade.courtage.model.TaTTel;
import fr.ylyade.courtage.model.TaTUtilisateur;
import fr.ylyade.courtage.model.mapping.LgrDozerMapper;
import fr.ylyade.courtage.service.TaReglementAssuranceService;
import fr.ylyade.courtage.service.envoiMailTestService;
import fr.ylyade.courtage.service.interfaces.remote.ITaAdresseServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaCourtierServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaDocumentPdfServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaEmailServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaEnvoiMailTestServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaLgrMailjetServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaReglementAssuranceServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTAdresseServiceRemote;
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
public class CourtierController extends AbstractController implements Serializable {  

	/**
	 * 
	 */
	private static final long serialVersionUID = 4065179592818038558L;

	@Inject @Named(value="tabListModelCenterBean")
	private TabListModelBean tabsCenter;
	
	@Inject @Named(value="tabViewsBean")
	private TabViewsBean tabViews;

	private String paramId;
	
	private TabView tabViewCourtier; //Le Binding sur les onglets "secondaire" semble ne pas fonctionné, les onglets ne sont pas générés au bon moment avec le c:forEach

	private List<TaCourtierDTO> values; 
	//private TaGedUtilisateur selectedTaGedUtilisateur;
	//private TaListeRefDoc selectedTaListeRefDoc;
	
	private @Inject SessionInfo sessionInfo;
	private @Inject Auth auth;
	private @Inject GedCourtierController gedCourtierController;
	private @Inject DialogUploadGedCourtierController dialogUploadGedCourtierController;
	
	protected ModeObjetEcranServeur modeEcran = new ModeObjetEcranServeur();
	
	private @EJB ITaTCiviliteServiceRemote taTCiviliteService;
	private @EJB ITaTJuridiqueServiceRemote taTJuridiqueService;
	private @EJB ITaTGroupeTarifServiceRemote taTGroupeTarifService;
	
	private @EJB ITaTelServiceRemote taTelService;	
	private @EJB ITaEmailServiceRemote taEmailService;
	private @EJB ITaAdresseServiceRemote taAdresseService;
	private @EJB ITaTAdresseServiceRemote taTAdresseService;
	private @EJB ITaTEmailServiceRemote taTEmailService;
	private @EJB ITaTTelServiceRemote taTTelService;
	private @EJB ITaTCourtierServiceRemote taTCourtierService;
	private @EJB ITaCourtierServiceRemote taCourtierService;
	private @EJB ITaTUtilisateurServiceRemote taTUtilisateurService;
	private @EJB ITaGenCodeExServiceRemote taGenCodeExService;
	
	private @EJB ITaUtilisateurServiceRemote taUtilisateurService;
	//private @EJB ITaGedUtilisateurServiceRemote taGedUtilisateurService;
	
	//private @EJB ITaListeRefDocServiceRemote taListeRefDocService;
	
	@EJB private  ITaDocumentPdfServiceRemote taDocumentPdfService;
	
	@EJB private ITaLgrMailjetServiceRemote lgrMailjetService;
	
	@EJB private  ITaEnvoiMailTestServiceRemote envoiMailTestService;
	
	@EJB private  ITaReglementAssuranceServiceRemote reglementAssuranceService;
	
	private TaCourtierDTO[] selectedTaCourtierDTOs; 
	private TaCourtier taCourtier = new TaCourtier();
	private TaCourtierDTO newTaCourtierDTO = new TaCourtierDTO();
	private TaCourtierDTO selectedTaCourtierDTO = new TaCourtierDTO();
	
	
	
	private TaTCourtier taTCourtier;
	private TaTCivilite taTCivilite;
	private TaTJuridique taTJuridique;
	private TaTGroupeTarif taTGroupeTarif;
	
	

	private LgrDozerMapper<TaCourtierDTO,TaCourtier> mapperUIToModel  = new LgrDozerMapper<TaCourtierDTO,TaCourtier>();
	private LgrDozerMapper<TaCourtier,TaCourtierDTO> mapperModelToUI  = new LgrDozerMapper<TaCourtier,TaCourtierDTO>();
	
	private TaTCourtierDTO taTCourtierDTO;
	private TaTCiviliteDTO taTCiviliteDTO;
	private TaTJuridiqueDTO taTJuridiqueDTO;
	private TaTGroupeTarifDTO taTGroupeTarifDTO;
	
	//Bordereaux reglement
	private List<TaReglementAssuranceDTO> listeReglementAssurance;
	private List<TaReglementPrestationDTO> listeReglementPrestation;
	
	private BigDecimal montantCaHt = BigDecimal.ZERO;
	private Date dateDebut;
	private Date dateFin;
	
	private boolean modifierMotDePasse = false;
	
	private String selectedMoisBordereaux = "";
	private String selectedAnneeBordereaux = "";
	
	private LinkedHashMap<String, String> listeMoisBordereaux = new LinkedHashMap<String, String>();
	private List<String> listeAnneeBordereaux = new ArrayList<String>();
	

	public CourtierController() {  
		//values = getValues();
	}  

	@PostConstruct
	public void postConstruct(){
		refresh();

	}
	
	public void actRechercheBordereaux() {
		if(listeReglementAssurance != null) {
			listeReglementAssurance.clear();
		}
		if(selectedMoisBordereaux != null && !selectedMoisBordereaux.isEmpty() && selectedAnneeBordereaux != null && !selectedAnneeBordereaux.isEmpty()) {
			listeReglementAssurance = reglementAssuranceService.findAllDTOByIdCourtierAndByMoisAndYear(taCourtier.getIdCourtier(), listeMoisBordereaux.get(selectedMoisBordereaux), selectedAnneeBordereaux);
		}else {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Bordereaux courtier", "Veuillez choisir un mois et une année pour afficher le borderaux de commission"));
		}
		
	}
	public void actChangementAnneeBordereaux() {
		actChangementAnneeBordereaux(null);
	}
	public void actChangementAnneeBordereaux(ActionEvent actionEvent) {
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		String monthstr = String.valueOf(month);
		String yearstr = String.valueOf(year);
		
		initListMoisBordereaux();
		
		if(selectedAnneeBordereaux.equals(yearstr)) {
			// Get the iterator over the HashMap 
	        Iterator<Map.Entry<String, String> > 
	            iterator = listeMoisBordereaux.entrySet().iterator(); 
	        // Iterate over the HashMap 
	        while (iterator.hasNext()) { 
	  
	            // Get the entry at this iteration 
	            Map.Entry<String, String> 
	                entry 
	                = iterator.next(); 
	  
	            // Check if this value is the required value 
	            if (monthstr.equals(entry.getValue().replace("0", ""))) { 
	  
	                // Remove this entry from HashMap 
	                iterator.remove(); 
	            } 
	        } 
			
		}
	}
	
	public void initListMoisBordereaux() {
		if(listeMoisBordereaux != null) {
			listeMoisBordereaux.clear();
			listeMoisBordereaux.put("janvier", "01");
			listeMoisBordereaux.put("février","02" );
			listeMoisBordereaux.put("mars","03");
			listeMoisBordereaux.put("avril","04");
			listeMoisBordereaux.put("mai","05");
			listeMoisBordereaux.put("juin","06");
			listeMoisBordereaux.put("juillet","07");
			listeMoisBordereaux.put("août","08");
			listeMoisBordereaux.put("septembre","09");
			listeMoisBordereaux.put("octobre","10");
			listeMoisBordereaux.put("novembre","11");
			listeMoisBordereaux.put("décembre","12");
		}
		
	}
	public void refresh(){
		if(auth.getUser()==null) {
			auth.setUser(sessionInfo.getUtilisateur());
		}
		if(auth.isCourtier() && !auth.isDevLgr()) {
			try {
				taCourtier = taCourtierService.findByIdUtilisateur(auth.getUser().getIdUtilisateur());
				selectedTaCourtierDTO = taCourtierService.findByIdDTO(taCourtier.getIdCourtier());
				updateTab();
				scrollToTop();
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			values = taCourtierService.findAllLight();
		}
		
		


		LocalDate now = LocalDate.now();
		LocalDate debut = LocalDate.of(2019, 01, 01);
		int year = now.getYear();
		int yearDebut = debut.getYear();
		int month = now.getMonthValue();
		String monthstr = String.valueOf(month);
		int nbAnneeDiff = year - yearDebut;
		listeAnneeBordereaux.clear();
		
		for (int i = 0; i < nbAnneeDiff+1; i++) {
			yearDebut = yearDebut+i;
			listeAnneeBordereaux.add(String.valueOf(yearDebut));
		}
		
		listeMoisBordereaux.clear();
		listeMoisBordereaux.put("janvier", "01");
		listeMoisBordereaux.put("février","02" );
		listeMoisBordereaux.put("mars","03");
		listeMoisBordereaux.put("avril","04");
		listeMoisBordereaux.put("mai","05");
		listeMoisBordereaux.put("juin","06");
		listeMoisBordereaux.put("juillet","07");
		listeMoisBordereaux.put("août","08");
		listeMoisBordereaux.put("septembre","09");
		listeMoisBordereaux.put("octobre","10");
		listeMoisBordereaux.put("novembre","11");
		listeMoisBordereaux.put("décembre","12");
		
		// Get the iterator over the HashMap 
        Iterator<Map.Entry<String, String> > 
            iterator = listeMoisBordereaux.entrySet().iterator(); 
        // Iterate over the HashMap 
        while (iterator.hasNext()) { 
  
            // Get the entry at this iteration 
            Map.Entry<String, String> 
                entry 
                = iterator.next(); 
  
            // Check if this value is the required value 
            if (monthstr.equals(entry.getValue().replace("0", ""))) { 
  
                // Remove this entry from HashMap 
                iterator.remove(); 
            } 
        } 
		
		
		modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
//    	infoEntreprise = taInfoEntrepriseService.findInstance();
//    	dateDebut = infoEntreprise.getDatedebInfoEntreprise();
//    	dateFin = infoEntreprise.getDatefinInfoEntreprise();
//
//    	dateDebutProforma = infoEntreprise.getDatedebInfoEntreprise();
//    	dateFinProforma = infoEntreprise.getDatefinInfoEntreprise();

	}
	
	public void actImprimerBordereaux(ActionEvent actionEvent) {

        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            sessionMap.remove("listeReglementCourtier");

            sessionMap.put("listeReglementCourtier", listeReglementAssurance);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	

	public List<TaCourtierDTO> getValues(){  
		return values;
	}

	public void actAnnuler(ActionEvent actionEvent) {
		try {
			switch (modeEcran.getMode()) {
			case C_MO_INSERTION:
				if(selectedTaCourtierDTO.getCodeCourtier()!=null) {
					taCourtierService.annuleCode(selectedTaCourtierDTO.getCodeCourtier());
				}
				taCourtier = new TaCourtier();
				mapperModelToUI.map(taCourtier,selectedTaCourtierDTO );
				selectedTaCourtierDTO=new TaCourtierDTO();
				
				if(!values.isEmpty()) selectedTaCourtierDTO = values.get(0);
				onRowSelect(null);
				break;
			case C_MO_EDITION:
				if(selectedTaCourtierDTO.getId()!=null && selectedTaCourtierDTO.getId()!=0){
					taCourtier = taCourtierService.findById(selectedTaCourtierDTO.getId());
					selectedTaCourtierDTO=taCourtierService.findByIdDTO(selectedTaCourtierDTO.getId());
				}				
				break;

			default:
				break;
			}
			modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
			mapperModelToUI.map(taCourtier, selectedTaCourtierDTO);

			if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Courtier", "actAnnuler"));
			}
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void autoCompleteMapUIToDTO() {
		if(taTCourtierDTO!=null) {
			validateUIField(Const.C_CODE_T_COURTIER,taTCourtierDTO.getCodeTCourtier());
			selectedTaCourtierDTO.setCodeTCourtier(taTCourtierDTO.getCodeTCourtier());
		}else selectedTaCourtierDTO.setCodeTCourtier(null);
		
		if(taTCiviliteDTO!=null) {
			validateUIField(Const.C_CODE_T_CIVILITE,taTCiviliteDTO.getCodeTCivilite());
			selectedTaCourtierDTO.setCodeTCivilite(taTCiviliteDTO.getCodeTCivilite());
		}else selectedTaCourtierDTO.setCodeTCourtier(null);
		
		if(taTJuridiqueDTO!=null) {
			validateUIField(Const.C_CODE_T_JURIDIQUE,taTJuridiqueDTO.getCodeTJuridique());
			selectedTaCourtierDTO.setCodeTJuridique(taTJuridiqueDTO.getCodeTJuridique());
		}else selectedTaCourtierDTO.setCodeTCourtier(null);
		
		if(taTGroupeTarifDTO!=null) {
			validateUIField(Const.C_CODE_GROUPE,taTGroupeTarifDTO.getCodeGroupe());
			selectedTaCourtierDTO.setCodeGroupe(taTGroupeTarifDTO.getCodeGroupe());
		}else selectedTaCourtierDTO.setCodeGroupe(null);
		
	}
	
	public void autoCompleteMapDTOtoUI() {
		try {
			taTCourtier = null;
			taTCivilite = null;
			taTJuridique = null;
			taTGroupeTarif = null;
			
			taTCourtierDTO = null;
			taTCiviliteDTO = null;
			taTJuridiqueDTO = null;
			taTGroupeTarifDTO = null;

			if(selectedTaCourtierDTO.getCodeTCourtier()!=null && !selectedTaCourtierDTO.getCodeTCourtier().equals("")) {
				taTCourtier = taTCourtierService.findByCode(selectedTaCourtierDTO.getCodeTCourtier());
				taTCourtierDTO = taTCourtierService.findByCodeDTO(selectedTaCourtierDTO.getCodeTCourtier());
			}
			if(selectedTaCourtierDTO.getCodeTCivilite()!=null && !selectedTaCourtierDTO.getCodeTCivilite().equals("")) {
				taTCivilite = taTCiviliteService.findByCode(selectedTaCourtierDTO.getCodeTCivilite());
				taTCiviliteDTO = taTCiviliteService.findByCodeDTO(selectedTaCourtierDTO.getCodeTCivilite());
			}
			if(selectedTaCourtierDTO.getCodeTJuridique()!=null && !selectedTaCourtierDTO.getCodeTJuridique().equals("")) {
				taTJuridique = taTJuridiqueService.findByCode(selectedTaCourtierDTO.getCodeTJuridique());
				taTJuridiqueDTO = taTJuridiqueService.findByCodeDTO(selectedTaCourtierDTO.getCodeTJuridique());
			}
			if(selectedTaCourtierDTO.getCodeGroupe()!=null && !selectedTaCourtierDTO.getCodeGroupe().equals("")) {
				taTGroupeTarif = taTGroupeTarifService.findByCode(selectedTaCourtierDTO.getCodeGroupe());
				taTGroupeTarifDTO = taTGroupeTarifService.findByCodeDTO(selectedTaCourtierDTO.getCodeGroupe());
			}
			
		} catch (FinderException e) {
			e.printStackTrace();
		}
	}
	public String passwordComplex () {
		String password = selectedTaCourtierDTO.getPassword();
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
	public void actEnregistrer(ActionEvent actionEvent) {
		
		boolean mdpOK = false;
		boolean mailOk = false;
		if(modifierMotDePasse && selectedTaCourtierDTO.getPassword()!=null 
				&& !selectedTaCourtierDTO.getPassword().equals("") 
				&& selectedTaCourtierDTO.getPassword().equals(selectedTaCourtierDTO.getPasswordConfirm()) ) {

				//TODO verifier la complexité minimum
				
				System.out.println("mot de passe : "+selectedTaCourtierDTO.getPassword()+" et le confirm : "+selectedTaCourtierDTO.getPasswordConfirm());
				if(selectedTaCourtierDTO.getPassword().equals(selectedTaCourtierDTO.getPasswordConfirm())) {
					taCourtier.getTaUtilisateur().setPassword(taCourtier.getTaUtilisateur().passwordHashSHA256_Base64(selectedTaCourtierDTO.getPassword()));
				System.out.println("ils sont equals, donc ont insere");
				}
				mdpOK = true;
		} else if(!modifierMotDePasse) {
			mdpOK = true;
		}
		
		if(selectedTaCourtierDTO.getAdresseEmail()!=null)  {
			if(!selectedTaCourtierDTO.getAdresseEmail().isEmpty()) {
				mailOk = true;
			}
		}
	//		taCourtier.initAdresseCourtier(null,adresseEstRempli(),ConstPreferencesCourtier.CORRESPONDANCE_ADRESSE_ADMINISTRATIF_DEFAUT,
	//				ConstPreferencesCourtier.CORRESPONDANCE_ADRESSE_COMMERCIAL_DEFAUT);
	//		if(taCourtier.getTaAdresse()!=null){
	//			try {
	//				if(taCourtier.getTaAdresse()!=null && taCourtier.getTaAdresse().getTaTAdr()==null){
	//				TaTAdr tAdr= taTAdresseService.findByCode(ConstPreferencesCourtier.TADR_DEFAUT);
	//				taCourtier.getTaAdresse().setTaTAdr(tAdr);
	//				}
	//				int ordre=taAdresseService.rechercheOdrePourType(taCourtier.getTaAdresse().getTaTAdr().getCodeTAdr(),taCourtier.getCodeCourtier());
	//				if(taCourtier.getTaAdresse().getOrdre()==null || taCourtier.getTaAdresse().getOrdre()==0)taCourtier.getTaAdresse().setOrdre(ordre);
	//			} catch (FinderException e) {
	//				
	//			}
	//		}
		if(mdpOK && mailOk) {
			autoCompleteMapUIToDTO();
	//		initAdresseNull();
	//		if(taCourtier.getTaEmail()==null){
	//			selectedTaCourtierDTO.setAdresseEmail(null);
	//		}
	//		if(taCourtier.getTaTelephone()==null){
	//			selectedTaCourtierDTO.setNumeroTelephone(null);
	//		}
	//		if(taCourtier.getTaWeb()==null){
	//			selectedTaCourtierDTO.setAdresseWeb(null);
	//		}
	
			//si le courtier n'était pas actif
			if(!taCourtier.getActif()) {
				//si le courtier vient d'être activé
				if(selectedTaCourtierDTO.getActif()) {
					envoiMailTestService.envoiMailActivationCourtier(taCourtier);
				}
			}
			mapperUIToModel.map(selectedTaCourtierDTO, taCourtier);
			
			/*
			 * A faire, mapper les objets dans taCourtier avant le merge :
			 * mapping de tous les objets avec des "codes", typeCourtier, typeCvilite, ...
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
	
			
			try {
	//			if(taCourtier.getCodeCompta()==null 
	//					|| (taCourtier.getCodeCompta()!=null && taCourtier.getCodeCompta().equals(""))
	//					) {
	//					taCourtier.setCodeCompta(taCourtier.getCodeCourtier().substring(0, 7));
	//				if(taCourtier.getCodeCourtier().length()>6) {
	//					taCourtier.setCodeCompta(taCourtier.getCodeCourtier().substring(0, 7));
	//				} else  {
	//					taCourtier.setCodeCompta(taCourtier.getCodeCourtier().substring(0, taCourtier.getCodeCourtier().length()));
	//				}
	//			}
	
				taCourtier = taCourtierService.merge(taCourtier,ITaCourtierServiceRemote.validationContext);
				
				if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_INSERTION)==0) {
				
				/***************Envoi du mail******************/
				
					envoiMailTestService.envoiMailCreationCourtier(taCourtier);
				
				
				/***************************Fin envoi du mail********************************/
				}
				
				
				
				if(selectedTaCourtierDTO.getCodeCourtier()!=null) {
					taCourtierService.annuleCode(selectedTaCourtierDTO.getCodeCourtier());
				}
				
				mapperModelToUI.map(taCourtier, selectedTaCourtierDTO);
				autoCompleteMapDTOtoUI();
				
				if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_INSERTION)==0) {
					values.add(selectedTaCourtierDTO);
					selectedTaCourtierDTOs = null;
				}
	//			adresseController.refresh(null);
				modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
	
			} catch(Exception e) {
				e.printStackTrace();
				FacesContext context = FacesContext.getCurrentInstance();  
				//context.addMessage(null, new FacesMessage("Courtier", "actEnregistrer")); 
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Courtier", e.getMessage()));
				  
			}
		}else {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Courtier", "Le mot de passe est différent de sa confirmation, ou l'adresse E-mail n'est pas renseignée."));
		}

		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Courtier", "actEnregistrer"));
		}
	}

	public void actInserer(ActionEvent actionEvent) {
		try {
			selectedTaCourtierDTOs= new TaCourtierDTO[]{};
			selectedTaCourtierDTO = new TaCourtierDTO();
			taCourtier = new TaCourtier();
			
			modifierMotDePasse = true;
			selectedTaCourtierDTO.setPassword(null);
			selectedTaCourtierDTO.setPasswordConfirm(null);

//			String codeTCourtierDefaut = TaTCourtier.COURTIER_ASSURANCE;
			String codeTCourtierDefaut = "C";

			taGenCodeExService.libereVerrouTout(new ArrayList<String>(SessionListener.getSessions().keySet()));
			selectedTaCourtierDTO.setCodeCourtier(taCourtierService.genereCode(null)); //ejb
			validateUIField(Const.C_CODE_COURTIER,selectedTaCourtierDTO.getCodeCourtier());//permet de verrouiller le code genere

			selectedTaCourtierDTO.setCodeTCourtier(codeTCourtierDefaut);

//			selectedTaCourtierDTO.setCodeCompta(selectedTaCourtierDTO.getCodeCourtier()); //ejb
//			validateUIField(Const.C_CODE_COMPTA,selectedTaCourtierDTO.getCodeCompta()); //ejb
//			selectedTaCourtierDTO.setCompte("411"); //ejb
//			validateUIField(Const.C_COMPTE,selectedTaCourtierDTO.getCompte()); //ejb

			//TaTCourtierDAO daoTCourtier = new TaTCourtierDAO(getEm());
			TaTCourtier taTCourtier;

			taTCourtier = taTCourtierService.findByCode(codeTCourtierDefaut);
			
			TaUtilisateur taUtilisateur = new TaUtilisateur();
			TaTUtilisateur taTUtilisateur = null; 
			taTUtilisateur = taTUtilisateurService.findByCode(TaTUtilisateur.TYPE_UTILISATEUR_COURTIER);
			taUtilisateur.setTaTUtilisateur(taTUtilisateur);
			taUtilisateur.setAdmin(false);
			taUtilisateur.setSysteme(false);
			taCourtier.setTaUtilisateur(taUtilisateur);

//			if(taTCourtier!=null && taTCourtier.getCompteTCourtier()!=null) {
//				selectedTaCourtierDTO.setCompte(taTCourtier.getCompteTCourtier());
//				this.taTCourtier = taTCourtier;
//			} else {
//				//selectedTaCourtierDTO.setCompte(CourtierPlugin.getDefault().getPreferenceStore().
//				//getString(PreferenceConstants.TIERS_COMPTE_COMPTALE_DEFAUT));
//			}
			selectedTaCourtierDTO.setActif(true);
//			selectedTaCourtierDTO.setCodeTTvaDoc("F");
			
			autoCompleteMapDTOtoUI();
			
			modeEcran.setMode(EnumModeObjet.C_MO_INSERTION);
			scrollToTop();
			if(tabViewCourtier!=null) {
				tabViewCourtier.setActiveIndex(0);
				updateTab();
//				adresseController.setMasterEntity(taCourtier);
//				adresseController.refresh(null);
			}
			if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Courtier", "actInserer"));
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
		selectedTaCourtierDTO.setPassword(null);
		selectedTaCourtierDTO.setPasswordConfirm(null);

		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Courtier", "actModifier"));
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
		context.addMessage(null, new FacesMessage("Courtier", "actAide"));
		}
	}
    
    public void nouveau(ActionEvent actionEvent) {  
    	LgrTab b = new LgrTab();
    	b.setTypeOnglet(LgrTab.TYPE_TAB_COURTIER);
		b.setTitre("Courtier");
		b.setTemplate("courtier/courtier.xhtml");
		b.setIdTab(LgrTab.ID_TAB_COURTIER);
		b.setStyle(LgrTab.CSS_CLASS_TAB_COURTIER);
		b.setParamId("0");
		
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
		actInserer(actionEvent);
		
		if(ConstWeb.DEBUG) {
    	FacesContext context = FacesContext.getCurrentInstance();  
	    context.addMessage(null, new FacesMessage("Courtier", 
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
		TaCourtier taCourtier = new TaCourtier();
		try {
			if(selectedTaCourtierDTO!=null && selectedTaCourtierDTO.getId()!=null){
				taCourtier = taCourtierService.findById(selectedTaCourtierDTO.getId());
			}
			taCourtierService.remove(taCourtier);
			int index = values.indexOf(selectedTaCourtierDTO);
			values.remove(selectedTaCourtierDTO);
			
			if(!values.isEmpty()) {
				selectedTaCourtierDTO = values.get(0);

			}else{
				selectedTaCourtierDTO=new TaCourtierDTO();
			}
			if (index>=1) {
				selectedTaCourtierDTO = values.get(index-1);	
			}
			else if(index<1) {
				selectedTaCourtierDTO = values.get(0);
			} else {
				// fermer les listes, en attente
			}
			updateTab();
			modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
			
			
			
			if(ConstWeb.DEBUG) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage("Courtier", "actSupprimer"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Courtier", e.getCause().getCause().getCause().getCause().getMessage()));
		} 	    
	}
	public void actBloquer() {
		actBloquer(null);
	}
	public void actBloquer(ActionEvent actionEvent) {
		TaCourtier taCourtier = new TaCourtier();
		if(selectedTaCourtierDTO != null && !selectedTaCourtierDTO.getSuspendu()) {
			
			try {
				taCourtier = taCourtierService.findById(selectedTaCourtierDTO.getId());
				taCourtier.setSuspendu(true);
				selectedTaCourtierDTO.setSuspendu(true);
				taCourtierService.merge(taCourtier);
				
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
	public void actDebloquer() {
		actDebloquer(null);
	}
	public void actDebloquer(ActionEvent actionEvent) {
		TaCourtier taCourtier = new TaCourtier();
		if(selectedTaCourtierDTO != null && selectedTaCourtierDTO.getSuspendu()) {
			
			try {
				taCourtier = taCourtierService.findById(selectedTaCourtierDTO.getId());
				taCourtier.setSuspendu(false);
				selectedTaCourtierDTO.setSuspendu(false);
				taCourtierService.merge(taCourtier);
				
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
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
		selectedTaCourtierDTO=new TaCourtierDTO();
		selectedTaCourtierDTOs=new TaCourtierDTO[]{selectedTaCourtierDTO};

	}
	
	public void actImprimerFiche(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("courtier", "actImprimer"));
		}
		try {
			
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.put("courtier", taCourtierService.findById(selectedTaCourtierDTO.getId()));
		
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
		context.addMessage(null, new FacesMessage("Courtier", "actImprimer"));
		}
		try {
			
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.put("listeCourtier", values);
		
//		sessionMap.put("courtier", taCourtierService.findById(selectedTaCourtierDTO.getId()));
		
		////////////////////////////////////////////////////////////////////////////////////////
		//Test génération de fichier PDF
		
//		HashMap<String,Object> hm = new HashMap<>();
//		hm.put( "tiers", taCourtierService.findById(selectedTaCourtierDTO.getId()));
//		BirtUtil.setAppContextEdition(hm);
//		
//		BirtUtil.startReportEngine();
//		
////		BirtUtil.renderReportToFile(
////				"/donnees/Projet/Java/Eclipse/GestionCommerciale_branche_2_0_13_JEE_E46/fr.legrain.solstyce.webapp/src/main/webapp/reports/tiers/FicheCourtier.rptdesign", 
////				"/tmp/tiers.pdf", 
////				new HashMap<>(), 
////				BirtUtil.PDF_FORMAT);
//		
//		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("/reports/tiers/FicheCourtier.rptdesign");
//		BirtUtil.renderReportToFile(
//				inputStream, 
//				"/tmp/tiers.pdf", 
//				new HashMap<>(), 
//				BirtUtil.PDF_FORMAT);
		////////////////////////////////////////////////////////////////////////////////////////
		//java.net.URL.setURLStreamHandlerFactory();
//		taCourtierService.generePDF(selectedTaCourtierDTO.getId());
		////////////////////////////////////////////////////////////////////////////////////////
		
//			session.setAttribute("tiers", taCourtierService.findById(selectedTaCourtierDTO.getId()));
			System.out.println("CourtierController.actImprimer()");
			
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
		b.setTypeOnglet(LgrTab.TYPE_TAB_TIERS);
		return tabsCenter.ongletDejaOuvert(b)==null;
	} 
	
	public void onRowSimpleSelect(SelectEvent event) {

		if(pasDejaOuvert()==false){
			onRowSelect(event);

			autoCompleteMapDTOtoUI();
			if(ConstWeb.DEBUG) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage("Courtier", 
						"Chargement du tiers N°"+selectedTaCourtierDTO.getCodeCourtier()
						)); }
		} else {
			tabViews.selectionneOngletCentre(LgrTab.TYPE_TAB_TIERS);
		}
	} 
	
	public void updateTab(){
		try {

			if(selectedTaCourtierDTOs!=null && selectedTaCourtierDTOs.length>0) {
				selectedTaCourtierDTO = selectedTaCourtierDTOs[0];
			}
			if(selectedTaCourtierDTO.getId()!=null && selectedTaCourtierDTO.getId()!=0) {
				taCourtier = taCourtierService.findById(selectedTaCourtierDTO.getId());
			}
			if(taCourtier != null) {
				mapperModelToUI.map(taCourtier, selectedTaCourtierDTO);	
			}
//			adresseController.setMasterEntity(taCourtier);
//			adresseController.refresh(null);
//			
//			emailController.setMasterEntity(taCourtier);
//			emailController.refresh();
//			
//			banqueController.setMasterEntity(taCourtier);
//			banqueController.refresh();
			
			gedCourtierController.setMasterEntity(taCourtier);
//			dialogUploadGedCourtierController.setMasterEntity(taCourtier);
			gedCourtierController.refresh();
			
			autoCompleteMapDTOtoUI();
			
			//listeReglementAssurance = reglementAssuranceService.findAllDTOByIdCourtier(taCourtier.getIdCourtier());
			montantCaHt = BigDecimal.ZERO;
			if (gedCourtierController != null) {
				gedCourtierController.refreshInfoDocGed();
			}			
//			if(selectedTaCourtierDTO.getCodeCourtier()!=null) {
//				listeCaPeriodeFactureCourtier = taCourtierService.findChiffreAffaireTotalParCodeCourtierDTO(dateDebut, dateFin, selectedTaCourtierDTO.getCodeCourtier());
//				if (listeCaPeriodeFactureCourtier != null && ! listeCaPeriodeFactureCourtier.isEmpty()) {
//					montantCaHt = listeCaPeriodeFactureCourtier.get(0).getMtHtCalc();
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
		gedCourtierController.refreshInfoDocGed();
		updateTab(); 
	}
	
	public boolean disabledTab(String nomTab) {
		//return modeEcran.dataSetEnModif() ||  selectedTaCourtierDTO.getId()==0;
		return true;
	}
	
	public void onRowSelect(SelectEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_COURTIER);
		b.setTitre("Courtier");
		b.setTemplate("courtier/courtier.xhtml");
		b.setIdTab(LgrTab.ID_TAB_COURTIER);
		b.setStyle(LgrTab.CSS_CLASS_TAB_COURTIER);
		b.setParamId(LibConversion.integerToString(selectedTaCourtierDTO.getId()));

		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
		
		modifierMotDePasse = false;
		selectedTaCourtierDTO.setPassword(null);
		selectedTaCourtierDTO.setPasswordConfirm(null);
		
		updateTab();
		scrollToTop();
		
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Courtier", 
				"Chargement du tiers N°"+selectedTaCourtierDTO.getCodeCourtier()
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
	
	public void actDialogTypeCourtier(ActionEvent actionEvent) {
		
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
//		context.addMessage(null, new FacesMessage("Courtier", "actAbout")); 	    
	}
	
	public void handleReturnDialogTypeCourtier(SelectEvent event) {
		if(event!=null && event.getObject()!=null) {
			taTCourtier = (TaTCourtier) event.getObject();
			try {
				taTCourtierDTO = taTCourtierService.findByCodeDTO(taTCourtier.getCodeTCourtier());
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
//		context.addMessage(null, new FacesMessage("Courtier", "actAbout")); 	    
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
				if(selectedTaCourtierDTO!=null && selectedTaCourtierDTO.getId()!=null  && selectedTaCourtierDTO.getId()!=0 ) retour = false;
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

	public TaCourtierDTO[] getSelectedTaCourtierDTOs() {
		return selectedTaCourtierDTOs;
	}

	public void setSelectedTaCourtierDTOs(TaCourtierDTO[] selectedTaCourtierDTOs) {
		this.selectedTaCourtierDTOs = selectedTaCourtierDTOs;
	}

	public TaCourtierDTO getNewTaCourtierDTO() {
		return newTaCourtierDTO;
	}

	public void setNewTaCourtierDTO(TaCourtierDTO newTaCourtierDTO) {
		this.newTaCourtierDTO = newTaCourtierDTO;
	}

	public TaCourtierDTO getSelectedTaCourtierDTO() {
		return selectedTaCourtierDTO;
	}

	public void setSelectedTaCourtierDTO(TaCourtierDTO selectedTaCourtierDTO) {
		this.selectedTaCourtierDTO = selectedTaCourtierDTO;
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

	public void validateCourtier(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	
		String messageComplet = "";
		
		try {
		  //String selectedRadio = (String) value;
		 
		  String nomChamp =  (String) component.getAttributes().get("nomChamp");
		  
		  //msg = "Mon message d'erreur pour : "+nomChamp;
		  
//		  validateUIField(nomChamp,value);
//		  TaCourtierDTO dtoTemp=new TaCourtierDTO();
//		  PropertyUtils.setSimpleProperty(dtoTemp, nomChamp, value);
//		  taCourtierService.validateDTOProperty(dtoTemp, nomChamp, ITaCourtierServiceRemote.validationContext );
		  
//			//validation automatique via la JSR bean validation
		  ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		  Set<ConstraintViolation<TaCourtierDTO>> violations = factory.getValidator().validateValue(TaCourtierDTO.class,nomChamp,value);
			if (violations.size() > 0) {
				messageComplet = "Erreur de validation : ";
//				List<IStatus> statusList = new ArrayList<IStatus>();
				for (ConstraintViolation<TaCourtierDTO> cv : violations) {
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
		  
		  //selectedTaCourtierDTO.setAdresse1Adresse("abcd");
		  
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
			if(value instanceof TaTCourtierDTO && ((TaTCourtierDTO) value).getCodeTCourtier()!=null && ((TaTCourtierDTO) value).getCodeTCourtier().equals(Const.C_AUCUN))value=null;
		}
		
		validateUIField(nomChamp,value);
	}
	
public boolean validateUIField(String nomChamp,Object value) {
		
		boolean changement=false;

		try {				
			if(nomChamp.equals(Const.C_CODE_T_COURTIER)) {
				TaTCourtier entity = null;
				if(value!=null) {
					if(value instanceof TaTCourtier) {
						entity=(TaTCourtier) value;
					}else if (value instanceof TaTCourtierDTO){
						entity = taTCourtierService.findByCode(((TaTCourtierDTO)value).getCodeTCourtier());	
					}else{
						entity = taTCourtierService.findByCode((String)value);
					}
				} else {
					selectedTaCourtierDTO.setCodeTCourtier("");
					taTCourtierDTO.setCodeTCourtier("");
					taTCourtier=null;
				}						
				taCourtier.setTaTCourtier(entity);
				//taTCourtier=entity;
				
				
				
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
					selectedTaCourtierDTO.setCodeTCivilite("");
					taTCiviliteDTO.setCodeTCivilite("");
					taTCivilite=null;
				}						
				taCourtier.getTaContactEntreprise().setTaTCivilite(entity);
				
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
					selectedTaCourtierDTO.setCodeTJuridique("");
					taTJuridiqueDTO.setCodeTJuridique("");
					taTJuridique=null;
				}						
				taCourtier.setTaTJuridique(entity);
			} else if(nomChamp.equals(Const.C_CODE_GROUPE)) {
				TaTGroupeTarif entity = null;
				if(value!=null) {
					if(value instanceof TaTGroupeTarif) {
						entity=(TaTGroupeTarif) value;
					}else if (value instanceof TaTGroupeTarif){
						entity = taTGroupeTarifService.findByCode(((TaTGroupeTarifDTO)value).getCodeGroupe());
					}else{
						entity = taTGroupeTarifService.findByCode((String)value);
					}
				} else {
					selectedTaCourtierDTO.setCodeGroupe("");
					taTGroupeTarifDTO.setCodeGroupe("");
					taTGroupeTarif=null;
				}						
				taCourtier.setTaTGroupeTarif(entity);
			} else if(nomChamp.equals(Const.C_NUMERO_TEL)) {
				taCourtier.initContactEntrepriseCourtier("test"); //création du ContactEntreprise par defaut s'il n'existe pas
				taCourtier.getTaContactEntreprise().initTelephoneDefaut(value);
				if(value!=null && !value.equals("")) { 
					PropertyUtils.setSimpleProperty(taCourtier.getTaContactEntreprise().getTaTel(), nomChamp, value);
				}
				if(taCourtier.getTaContactEntreprise().getTaTel()==null)selectedTaCourtierDTO.setNumeroTel(null);
				if(taCourtier.getTaContactEntreprise().getTaTel()!=null &&
						taCourtier.getTaContactEntreprise().getTaTel().getTaTTel()==null){
					TaTTel taTTel = new TaTTel();
					taTTel.setCodeTTel(ConstPreferences.TTEL_DEFAUT);
					if(!taTTel.getCodeTTel().equals("")){
						//TaTTelDAO taTTelDAO = new TaTTelDAO(getEm());
						taTTel=taTTelService.findByCode(taTTel.getCodeTTel());
						taCourtier.getTaContactEntreprise().getTaTel().setTaTTel(taTTel);
					}
				}					
			}else if(nomChamp.equals(Const.C_ADRESSE_EMAIL)) {
				taCourtier.initContactEntrepriseCourtier("test"); //création du ContactEntreprise par defaut s'il n'existe pas
				taCourtier.getTaContactEntreprise().initEmailDefaut(value);
				if(value!=null && !value.equals("")) {
					PropertyUtils.setSimpleProperty(taCourtier.getTaContactEntreprise().getTaEmail(), nomChamp, value);
				}
				if(taCourtier.getTaContactEntreprise().getTaEmail()==null)selectedTaCourtierDTO.setAdresseEmail(null);
				if(taCourtier.getTaContactEntreprise().getTaEmail()!=null &&
						taCourtier.getTaContactEntreprise().getTaEmail().getTaTEmail()==null){
					TaTEmail taTEmail = new TaTEmail();
					taTEmail.setCodeTEmail(ConstPreferences.TEMAIL_DEFAUT);
					if(!taTEmail.getCodeTEmail().equals("")){
						taTEmail=taTEmailService.findByCode(taTEmail.getCodeTEmail());
						taCourtier.getTaContactEntreprise().getTaEmail().setTaTEmail(taTEmail);
					}
				}	
				taCourtier.getTaUtilisateur().setIdentifiant((String)value);
			}else if(nomChamp.equals(Const.C_NOM)
					|| nomChamp.equals(Const.C_PRENOM)) {
				taCourtier.initContactEntrepriseCourtier("test"); //création du ContactEntreprise par defaut s'il n'existe pas
				if(value!=null && !value.equals("")) {
					PropertyUtils.setSimpleProperty(taCourtier.getTaContactEntreprise(), nomChamp, value);
				}
//				if(taCourtier.getTaContactEntreprise()==null)selectedTaCourtierDTO.setAdresseEmail(null);
				
			}else if(nomChamp.equals(Const.C_ADRESSE_LIGNE_1)
					|| nomChamp.equals(Const.C_ADRESSE_LIGNE_2)
					|| nomChamp.equals(Const.C_ADRESSE_LIGNE_3)
					|| nomChamp.equals(Const.C_CODE_POSTAL)
					|| nomChamp.equals(Const.C_VILLE)
					|| nomChamp.equals(Const.C_PAYS)) {
				taCourtier.initContactEntrepriseCourtier("test"); //création du ContactEntreprise par defaut s'il n'existe pas
				
				taCourtier.getTaContactEntreprise().initAdresseDefaut(value,adresseEstRempli());
				if(value!=null) {	
					if(taCourtier.getTaContactEntreprise().getTaAdresse()!=null) {
						PropertyUtils.setSimpleProperty(taCourtier.getTaContactEntreprise().getTaAdresse(), nomChamp, value);
					}
				}
				if(taCourtier.getTaContactEntreprise().getTaAdresse()==null){
					initAdresseNull();
				}
				if(taCourtier.getTaContactEntreprise().getTaAdresse()!=null &&
						taCourtier.getTaContactEntreprise().getTaAdresse().getTaTAdresse()==null){
					TaTAdresse taTAdr = new TaTAdresse();
					taTAdr.setCodeTAdresse(ConstPreferences.TADR_DEFAUT);
					if(!taTAdr.getCodeTAdresse().equals("")){
						taTAdr = taTAdresseService.findByCode(taTAdr.getCodeTAdresse());
						taCourtier.getTaContactEntreprise().getTaAdresse().setTaTAdresse(taTAdr);
					}
				}			
			} else if(nomChamp.equals(Const.C_IDENTIFIANT)
					|| nomChamp.equals(Const.C_PASSWORD)
					|| nomChamp.equals(Const.C_PASSWORD+"_CONFIRMATION")) {
				if(nomChamp.equals(Const.C_IDENTIFIANT)) {
					if(value!=null && !value.equals("")) { 
						PropertyUtils.setSimpleProperty(taCourtier.getTaUtilisateur(), nomChamp, value);
					} 
				}else if(nomChamp.equals(Const.C_PASSWORD)) {
					if(value!=null && !value.equals("")) {
						PropertyUtils.setSimpleProperty(taCourtier.getTaUtilisateur(), nomChamp, taCourtier.getTaUtilisateur().passwordHashSHA256_Base64((String)value));
					}
				}else if(nomChamp.equals(Const.C_PASSWORD+"_CONFIRMATION")) {
					PropertyUtils.setSimpleProperty(selectedTaCourtierDTO,"passwordConfirm", taCourtier.getTaUtilisateur().passwordHashSHA256_Base64((String)value));
//					System.out.println("valeur de value avant hash "+value);
					value = taCourtier.getTaUtilisateur().passwordHashSHA256_Base64((String)value);
//					System.out.println("valeur de value aprés hash "+value);
//					System.out.println("valeur de getPassword "+taCourtier.getTaUtilisateur().getPassword());
					if(!value.equals(taCourtier.getTaUtilisateur().getPassword())) {
//						System.out.println("les hashs sont différents");
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
		if(taCourtier.getTaContactEntreprise().getTaAdresse()==null){
			selectedTaCourtierDTO.setAdresseLigne1(null);
			selectedTaCourtierDTO.setAdresseLigne2(null);
			selectedTaCourtierDTO.setAdresseLigne3(null);
			selectedTaCourtierDTO.setCodePostal(null);
			selectedTaCourtierDTO.setVille(null);
			selectedTaCourtierDTO.setPays(null);
		}
	}
	
	private boolean adresseEstRempli() {
		boolean retour=false;
		if(selectedTaCourtierDTO.getAdresseLigne1()!=null && !selectedTaCourtierDTO.getAdresseLigne1().equals(""))return true;
		if(selectedTaCourtierDTO.getAdresseLigne2()!=null && !selectedTaCourtierDTO.getAdresseLigne2().equals(""))return true;
		if(selectedTaCourtierDTO.getAdresseLigne3()!=null && !selectedTaCourtierDTO.getAdresseLigne3().equals(""))return true;
		if(selectedTaCourtierDTO.getCodePostal()!=null && !selectedTaCourtierDTO.getCodePostal().equals(""))return true;
		if(selectedTaCourtierDTO.getVille()!=null && !selectedTaCourtierDTO.getVille().equals(""))return true;
		if(selectedTaCourtierDTO.getPays()!=null && !selectedTaCourtierDTO.getPays().equals(""))return true;
		return retour;
	}

	public List<TaTCourtier> typeCourtierAutoComplete(String query) {
        List<TaTCourtier> allValues = taTCourtierService.selectAll();
        List<TaTCourtier> filteredValues = new ArrayList<TaTCourtier>();
         
        for (int i = 0; i < allValues.size(); i++) {
        	TaTCourtier civ = allValues.get(i);
            if(query.equals("*") || civ.getCodeTCourtier().toLowerCase().contains(query.toLowerCase())) {
                filteredValues.add(civ);
            }
        }
         
        return filteredValues;
    }
	public List<TaTCourtierDTO> typeCourtierAutoCompleteLight(String query) {
        List<TaTCourtierDTO> allValues = taTCourtierService.selectAllDTO();
        List<TaTCourtierDTO> filteredValues = new ArrayList<TaTCourtierDTO>();
         
        for (int i = 0; i < allValues.size(); i++) {
        	TaTCourtierDTO civ = allValues.get(i);
            if(query.equals("*") || civ.getCodeTCourtier().toLowerCase().contains(query.toLowerCase())) {
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

	public TaTCourtier getTaTCourtier() {
		return taTCourtier;
	}

	public void setTaTCourtier(TaTCourtier taTCourtier) {
		this.taTCourtier = taTCourtier;
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

	public TaCourtier getTaCourtier() {
		return taCourtier;
	}

	public void setTaCourtier(TaCourtier taCourtier) {
		this.taCourtier = taCourtier;
	}

	public TaCourtierDTO rempliDTO(){
		if(taCourtier!=null){			
			try {
				taCourtier=taCourtierService.findByCode(taCourtier.getCodeCourtier());
				mapperModelToUI.map(taCourtier, selectedTaCourtierDTO);
				if(values.contains(selectedTaCourtierDTO))values.add(selectedTaCourtierDTO);
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return selectedTaCourtierDTO;
	}


	public TabView getTabViewCourtier() {
		return tabViewCourtier;
	}

	public void setTabViewCourtier(TabView tabViewCourtier) {
		this.tabViewCourtier = tabViewCourtier;
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

	public TaTGroupeTarif getTaTGroupeTarif() {
		return taTGroupeTarif;
	}

	public void setTaTGroupeTarif(TaTGroupeTarif taTGroupeTarif) {
		this.taTGroupeTarif = taTGroupeTarif;
	}

	public TaTJuridiqueDTO getTaTJuridiqueDTO() {
		return taTJuridiqueDTO;
	}

	public void setTaTJuridiqueDTO(TaTJuridiqueDTO taTJuridiqueDTO) {
		this.taTJuridiqueDTO = taTJuridiqueDTO;
	}

	public TaTGroupeTarifDTO getTaTGroupeTarifDTO() {
		return taTGroupeTarifDTO;
	}

	public void setTaTGroupeTarifDTO(TaTGroupeTarifDTO taTGroupeTarifDTO) {
		this.taTGroupeTarifDTO = taTGroupeTarifDTO;
	}
	
	public TaTCourtierDTO getTaTCourtierDTO() {
		return taTCourtierDTO;
	}

	public void setTaTCourtierDTO(TaTCourtierDTO taTCourtierDTO) {
		this.taTCourtierDTO = taTCourtierDTO;
	}

	public TaTCiviliteDTO getTaTCiviliteDTO() {
		return taTCiviliteDTO;
	}

	public void setTaTCiviliteDTO(TaTCiviliteDTO taTCiviliteDTO) {
		this.taTCiviliteDTO = taTCiviliteDTO;
	}



	public GedCourtierController getGedCourtierController() {
		return gedCourtierController;
	}

	public void setGedCourtierController(GedCourtierController gedCourtierController) {
		this.gedCourtierController = gedCourtierController;
	}

	public boolean isModifierMotDePasse() {
		return modifierMotDePasse;
	}

	public void setModifierMotDePasse(boolean modifierMotDePasse) {
		this.modifierMotDePasse = modifierMotDePasse;
	}

	public DialogUploadGedCourtierController getDialogUploadGedCourtierController() {
		return dialogUploadGedCourtierController;
	}

	public void setDialogUploadGedCourtierController(DialogUploadGedCourtierController dialogUploadGedCourtierController) {
		this.dialogUploadGedCourtierController = dialogUploadGedCourtierController;
	}

	public List<TaReglementAssuranceDTO> getListeReglementAssurance() {
		return listeReglementAssurance;
	}

	public void setListeReglementAssurance(List<TaReglementAssuranceDTO> listeReglementAssurance) {
		this.listeReglementAssurance = listeReglementAssurance;
	}

	public List<TaReglementPrestationDTO> getListeReglementPrestation() {
		return listeReglementPrestation;
	}

	public void setListeReglementPrestation(List<TaReglementPrestationDTO> listeReglementPrestation) {
		this.listeReglementPrestation = listeReglementPrestation;
	}

	public String getSelectedMoisBordereaux() {
		return selectedMoisBordereaux;
	}

	public void setSelectedMoisBordereaux(String selectedMoisBordereaux) {
		this.selectedMoisBordereaux = selectedMoisBordereaux;
	}

	public String getSelectedAnneeBordereaux() {
		return selectedAnneeBordereaux;
	}

	public void setSelectedAnneeBordereaux(String selectedAnneeBordereaux) {
		this.selectedAnneeBordereaux = selectedAnneeBordereaux;
	}

	public LinkedHashMap<String, String> getListeMoisBordereaux() {
		return listeMoisBordereaux;
	}

	public void setListeMoisBordereaux(LinkedHashMap<String, String> listeMoisBordereaux) {
		this.listeMoisBordereaux = listeMoisBordereaux;
	}

	public List<String> getListeAnneeBordereaux() {
		return listeAnneeBordereaux;
	}

	public void setListeAnneeBordereaux(List<String> listeAnneeBordereaux) {
		this.listeAnneeBordereaux = listeAnneeBordereaux;
	}


}  
