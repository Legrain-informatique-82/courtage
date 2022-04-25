package fr.ylyade.courtage.controller;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.FinderException;
import javax.faces.application.FacesMessage;
//import javax.faces.view.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.SlideEndEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import fr.legrain.courtage.controle.service.interfaces.remote.general.ITaGenCodeExServiceRemote;
import fr.legrain.lib.data.EnumModeObjet;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
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
import fr.ylyade.courtage.dto.TaActiviteDTO;
import fr.ylyade.courtage.dto.TaAssureDTO;
import fr.ylyade.courtage.dto.TaCourtierDTO;
import fr.ylyade.courtage.dto.TaDossierRcdDTO;
import fr.ylyade.courtage.dto.TaEcheanceDTO;
import fr.ylyade.courtage.dto.TaGedUtilisateurDTO;
import fr.ylyade.courtage.dto.TaListeRefDocDTO;
import fr.ylyade.courtage.dto.TaTAssureDTO;
import fr.ylyade.courtage.dto.TaTCiviliteDTO;
import fr.ylyade.courtage.dto.TaTCourtierDTO;
import fr.ylyade.courtage.dto.TaTEcheanceDTO;
import fr.ylyade.courtage.dto.TaTGroupeTarifDTO;
import fr.ylyade.courtage.dto.TaTJuridiqueDTO;
import fr.ylyade.courtage.dto.TaTReglementDTO;
import fr.ylyade.courtage.dto.TaTSousTraitanceDTO;
import fr.ylyade.courtage.dto.TaTauxRcproPibDTO;
import fr.ylyade.courtage.model.Const;
import fr.ylyade.courtage.model.ConstPreferences;
import fr.ylyade.courtage.model.IDonneStatut;
import fr.ylyade.courtage.model.TaActivite;
import fr.ylyade.courtage.model.TaAssure;
import fr.ylyade.courtage.model.TaAttestationNominative;
import fr.ylyade.courtage.model.TaAttestationNominativeActivite;
import fr.ylyade.courtage.model.TaClasse;
import fr.ylyade.courtage.model.TaCourtier;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaDossierRcdActivite;
import fr.ylyade.courtage.model.TaDossierRcdTauxPib;
import fr.ylyade.courtage.model.TaEcheance;
import fr.ylyade.courtage.model.TaFamilleActivite;
import fr.ylyade.courtage.model.TaFraisImpaye;
import fr.ylyade.courtage.model.TaGedUtilisateur;
import fr.ylyade.courtage.model.TaListeRefDoc;
import fr.ylyade.courtage.model.TaPalierClasse;
import fr.ylyade.courtage.model.TaQuittance;
import fr.ylyade.courtage.model.TaReglementAssurance;
import fr.ylyade.courtage.model.TaReglementPrestation;
import fr.ylyade.courtage.model.TaSinistreAntecedent;
import fr.ylyade.courtage.model.TaSousDonnee;
import fr.ylyade.courtage.model.TaTAdresse;
import fr.ylyade.courtage.model.TaTAssure;
import fr.ylyade.courtage.model.TaTCivilite;
import fr.ylyade.courtage.model.TaTCourtier;
import fr.ylyade.courtage.model.TaTEcheance;
import fr.ylyade.courtage.model.TaTEmail;
import fr.ylyade.courtage.model.TaTFraisImpaye;
import fr.ylyade.courtage.model.TaTFranchise;
import fr.ylyade.courtage.model.TaTGarantieSinistre;
import fr.ylyade.courtage.model.TaTGroupeTarif;
import fr.ylyade.courtage.model.TaTJuridique;
import fr.ylyade.courtage.model.TaTListeRefDoc;
import fr.ylyade.courtage.model.TaTReglement;
import fr.ylyade.courtage.model.TaTSousTraitance;
import fr.ylyade.courtage.model.TaTStatut;
import fr.ylyade.courtage.model.TaTTel;
import fr.ylyade.courtage.model.TaTUtilisateur;
import fr.ylyade.courtage.model.TaTalonComptable;
import fr.ylyade.courtage.model.TaTarifPrestation;
import fr.ylyade.courtage.model.TaTauxAssurance;
import fr.ylyade.courtage.model.TaTauxRcproPib;
import fr.ylyade.courtage.model.mapper.TaDossierRcdMapper;
import fr.ylyade.courtage.model.mapping.LgrDozerMapper;
import fr.ylyade.courtage.service.TimerServiceYlyade;
import fr.ylyade.courtage.service.interfaces.remote.ITaActiviteServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaAdresseServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaAssureServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaCourtierServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaDossierRcdServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaEcheanceServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaEmailServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaEnvoiMailTestServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaGedUtilisateurServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaListeRefDocServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaQuittanceServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaReglementAssuranceServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaSousDonneeServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTAdresseServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTAssureServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTCiviliteServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTCourtierServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTEcheanceServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTEmailServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTFraisImpayeServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTFranchiseServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTGarantieSinistreServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTGroupeTarifServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTJuridiqueServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTReglementServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTSousTraitanceServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTTelServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTUtilisateurServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTalonComptableServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTauxAssuranceServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTauxRcproPibServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTelServiceRemote;

@Named
@ViewScoped  
public class ContratRcdController extends AbstractControllerRCD implements Serializable {  

	private static final long serialVersionUID = 6278107399024181110L;

	@Inject @Named(value="tabListModelCenterBean")
	private TabListModelBean tabsCenter;
	
	@Inject @Named(value="tabViewsBean")
	private TabViewsBean tabViews;
	
	private Boolean affichePremierOnglet = false;

	private String paramId;
	
	private TabView tabViewContratRcPro; //Le Binding sur les onglets "secondaire" semble ne pas fonctionné, les onglets ne sont pas générés au bon moment avec le c:forEach

	private List<TaDossierRcdDTO> values; 
	private List<TaDossierRcdDTO> valuesExtraction; 
	private TaGedUtilisateur selectedTaGedUtilisateur;
	private TaListeRefDoc selectedTaListeRefDoc;
	
	private @Inject SessionInfo sessionInfo;
	@Inject
	protected Auth auth;
	private @Inject GedContratRcdController gedContratRcProController;
	@Inject 
	@Named("devisRcdController")
	private DevisRcdController devisRcdController;
	
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
	private @EJB ITaAssureServiceRemote taAssureService;
//	protected @EJB ITaDossierRcdServiceRemote taDossierRcdService;
//	private @EJB ITaEcheanceServiceRemote taEcheanceService;
	@EJB
	protected ITaCourtierServiceRemote taCourtierService;
	private @EJB ITaTUtilisateurServiceRemote taTUtilisateurService;
	private @EJB ITaTAssureServiceRemote taTAssureService;
	private @EJB ITaTGarantieSinistreServiceRemote taTGarantieSinistre;
	private @EJB ITaGenCodeExServiceRemote taGenCodeExService;

	private @EJB ITaActiviteServiceRemote taActiviteService;
	private @EJB ITaTauxRcproPibServiceRemote taTauxRcproPibService;
	
	private @EJB ITaTReglementServiceRemote taTReglementService;
	private @EJB ITaTEcheanceServiceRemote taTEcheanceService;
	private @EJB ITaTSousTraitanceServiceRemote taTSousTraitanceService;
	private @EJB ITaTFranchiseServiceRemote taTFranchiseService;
//	private @EJB ITaTauxAssuranceServiceRemote taTauxAssuranceService;
	private @EJB ITaTFraisImpayeServiceRemote taTFraisImpayeService;
	
//	private @EJB ITaQuittanceServiceRemote taQuittanceService;
//	private @EJB ITaTalonComptableServiceRemote taTalonComptableService;
//	private @EJB ITaReglementAssuranceServiceRemote taReglementAssuranceService;
	
	private @EJB ITaUtilisateurServiceRemote taUtilisateurService;
	private @EJB ITaGedUtilisateurServiceRemote taGedUtilisateurService;
	
	private @EJB ITaListeRefDocServiceRemote taListeRefDocService;
	
	private @EJB ITaSousDonneeServiceRemote taSousDonneeService;
	
//	private @EJB TimerServiceYlyade timerService;
	
	@EJB private ITaEnvoiMailTestServiceRemote envoiMailTestService;
	
//	private TaDossierRcdDTO[] selectedTaDossierRcdDTOs; 
////	private TaDossierRcd taDossierRcd = new TaDossierRcd();
//	private TaDossierRcdDTO newTaDossierRcdDTO = new TaDossierRcdDTO();
//	private TaDossierRcdDTO selectedTaDossierRcdDTO = new TaDossierRcdDTO();
	private boolean etudePourCauseLimiteSinistre = false;
	
	private List<TaGedUtilisateurDTO> listeTaGedUtilisateurDTO;
	private List<TaGedUtilisateur> listeTaGedUtilisateur;
	
	private TimeZone timeZone;

//	private List<TaContratRcProDetailDTO> valuesDetail;
	
	private List<TaTStatut> listeTaTStatut = new ArrayList<TaTStatut>();
	
	private List<TaTStatut> selectedTStatut = new ArrayList<TaTStatut>();
	
	private List<TaActiviteDTO> selectedTaActiviteDTOs = new ArrayList<TaActiviteDTO>();
	private List<TaActiviteDTO> listeTaActiviteDTODetail = new ArrayList<TaActiviteDTO>();
	private List<TaActiviteDTO> selectedTaActiviteDTOAttestationNominative = new ArrayList<TaActiviteDTO>();
	
	private List<TaActiviteDTO> tmpselectedTaActiviteDTOs = new ArrayList<TaActiviteDTO>();
	
	private List<TaActiviteDTO> taActiviteDTODisponible = new ArrayList<TaActiviteDTO>();
	
	private List<TaTauxRcproPibDTO> selectedTaTauxRcproPibDTOs = new ArrayList<TaTauxRcproPibDTO>();
	private List<TaTauxRcproPibDTO> taTauxRcproPibDTODisponible = new ArrayList<TaTauxRcproPibDTO>();
	
//	private List<TaEcheanceDTO> listeTaEcheanceDTO = new ArrayList<TaEcheanceDTO>();
	private List<TaEcheance> listeTaEcheance= new ArrayList<TaEcheance>();
//	private List<TaDossierRcdDTO> listeAvenantTaDossierRcdDTO = new ArrayList<TaDossierRcdDTO>();
//	private TaEcheanceDTO selectedTaEcheanceDTO;
	
	private TaSinistreAntecedent selectedTaSinstreAntecedent;
	private List<TaSinistreAntecedent> listeTaSinstreAntecedent;
	
	private List<TaTGarantieSinistre> listeTaTGarantieSinistre;
	
	private List<TaTReglement> listeTaTReglement;
	private List<TaTEcheance> listeTaTEcheance;
	private List<TaTSousTraitance> listeTaTSousTraitance;
	private List<TaTFranchise> listeTaTFranchise;
	
	private TaAssure taAssure;
	private TaAssure taAssureParamInsertion;
	private TaTAssure taTAssure;
	private TaTAssure taTAssureParamInsertion;
	protected TaCourtier taCourtier;
	private TaTCourtier taTCourtier;
	private TaTCivilite taTCivilite;
	private TaTJuridique taTJuridique;
	private TaTGroupeTarif taTGroupeTarif;
	
	private TaTReglement taTReglement;
	private TaTEcheance taTEcheance;
	private TaTSousTraitance taTSousTraitance;
	private TaTFranchise taTFranchise;
	
	private LgrDozerMapper<TaDossierRcdDTO,TaDossierRcd> mapperUIToModel  = new LgrDozerMapper<TaDossierRcdDTO,TaDossierRcd>();
	private LgrDozerMapper<TaDossierRcd,TaDossierRcdDTO> mapperModelToUI  = new LgrDozerMapper<TaDossierRcd,TaDossierRcdDTO>();
	
	private LgrDozerMapper<TaAssureDTO,TaAssure> mapperUIToModelAssure  = new LgrDozerMapper<TaAssureDTO,TaAssure>();
	private LgrDozerMapper<TaAssure,TaAssureDTO> mapperModelToUIAssure  = new LgrDozerMapper<TaAssure,TaAssureDTO>();
	
	private LgrDozerMapper<TaDossierRcdDTO,TaDossierRcd> mapperUIToModelDetail  = new LgrDozerMapper<TaDossierRcdDTO,TaDossierRcd>();
	private LgrDozerMapper<TaDossierRcd,TaDossierRcdDTO> mapperModelToUIDetail  = new LgrDozerMapper<TaDossierRcd,TaDossierRcdDTO>();
	
	private LgrDozerMapper<TaGedUtilisateurDTO,TaGedUtilisateur> mapperUIToModelGedUtilisateur  = new LgrDozerMapper<TaGedUtilisateurDTO,TaGedUtilisateur>();
	private LgrDozerMapper<TaGedUtilisateur,TaGedUtilisateurDTO> mapperModelToUIGedUtilisateur  = new LgrDozerMapper<TaGedUtilisateur,TaGedUtilisateurDTO>();
	
	private TaAssureDTO taAssureDTO;
	private TaTAssureDTO taTAssureDTO;
	private TaCourtierDTO taCourtierDTO;
	private TaTCourtierDTO taTCourtierDTO;
	private TaTCiviliteDTO taTCiviliteDTO;
	private TaTJuridiqueDTO taTJuridiqueDTO;
	private TaTGroupeTarifDTO taTGroupeTarifDTO;
	
	private String logDebugCalculPrime;
	private String logDebugCalculPremierReglement;
	
	private static final int NB_ACTIVITE_MAX_DEFAUT = 15;
	private static final int NB_ACTIVITE_MAX_AUTO_ENTREPRENEUR_DEFAUT = 5;
	private static final int NB_ACTIVITE_MAX_AUTO_ENTREPRENEUR_LIMITE = 3;
	private int nbActiviteMax = NB_ACTIVITE_MAX_DEFAUT;
	
	private DataTable activiteDatatable;
	private Date now = new Date();
	private Date dateLimiteRepriseDuPasse = new Date();
	
	/*
	 * --------------------------------------------------------------------
	 */
	private StreamedContent file;
	private TaListeRefDocDTO selectedTaListeRefDocDTO;
	
	private TaGedUtilisateurDTO selectedTaGedUtilisateurDTO;
	
	private TaGedUtilisateur taGedUtilisateur;
	
	private TaDossierRcd masterEntity;
	private List<TaListeRefDocDTO> listeByType;
	
	private List<TaGedUtilisateurDTO> listTaGedUtilisateurDTO;
	/*
	 * --------------------------------------------------------------------
	 */
	
	private ResultatActivite r = null;
	private BigDecimal primeBaseNette;
	
	private BigDecimal montantCommisionPrevu;
	
	private BigDecimal totalPourcentageActiviteEntreprise;
	private BigDecimal totalPourcentageActiviteSousTraite;
	private BigDecimal totalPourcentageActivite;
	
	private static final String ID_ONGLET_1_DECLARATION_ENGAGEMENT = "idOngletDeclarationEngagement";
	private static final String ID_ONGLET_2_PROPOSANT_DECLARATION = "idOngletProposantDeclaration";
	private static final String ID_ONGLET_3_ANTECEDANT = "idOngletAntecedant";
	private static final String ID_ONGLET_4_SINISTRALITE = "idOngletSinistralite";
	private static final String ID_ONGLET_5_ACTIVITE_DECLARE = "idOngletActiviteDeclare";
	private static final String ID_ONGLET_6_OPTION_PAIEMENT = "idOngletOptionPaiement";
	private static final String ID_ONGLET_7_INFORMATION_LEGALE = "idOngletInformationLegale";
	private static final String ID_ONGLET_8_PRIME = "idOngletPrime";
	private static final String ID_ONGLET_9_GED_DOCUMENT = "idOngletGedDocument";
	
	private String idOngletActif;
	private String idOngletPrecedent;
	private Map<String, Integer> mapOngletIndex;
	
	private boolean onglet1Valide = true;
	private boolean onglet2Valide = true;
	private boolean onglet3Valide = true;
	private boolean onglet4Valide = true;
	private boolean onglet5Valide = true;
	private boolean onglet5_1Valide = true;
	private boolean onglet5_2Valide = true;
	private boolean onglet6Valide = true;
	private boolean onglet7Valide = true;
	private boolean onglet8Valide = true;
	private boolean onglet9Valide = true;
	
	private static final String CSS_ONGLET_DEFAUT = "";
	private static final String CSS_ONGLET_DEFAUT_VALIDE = "";
	private static final String CSS_ONGLET_DEFAUT_ERREUR = "";
	
	private String onglet1Css = "";
	private String onglet2Css = "";
	private String onglet3Css = "";
	private String onglet4Css = "";
	private String onglet5Css = "";
	private String onglet6Css = "";
	private String onglet7Css = "";
	private String onglet8Css = "";
	private String onglet9Css = "";
	
	private boolean afficheBaseAntecedentRcd = false;
	private boolean afficheResilieRcd = false;
	private boolean afficheMotifResilieAssureRcd = false;
	private boolean afficheMotifResilieAssureurRcd = false;
	
	private boolean afficheBaseAntecedentRce = false;
	private boolean afficheResilieRce = false;
	private boolean afficheMotifResilieAssureRce = false;
	private boolean afficheMotifResilieAssureurRce = false;
	
	
	public ContratRcdController() {  
		//values = getValues();
		mapOngletIndex = new HashMap<>();
		mapOngletIndex.put(ID_ONGLET_1_DECLARATION_ENGAGEMENT, 0);
		mapOngletIndex.put(ID_ONGLET_2_PROPOSANT_DECLARATION, 1);
		mapOngletIndex.put(ID_ONGLET_3_ANTECEDANT, 2);
		mapOngletIndex.put(ID_ONGLET_4_SINISTRALITE, 3);
		mapOngletIndex.put(ID_ONGLET_5_ACTIVITE_DECLARE, 4);
		mapOngletIndex.put(ID_ONGLET_6_OPTION_PAIEMENT, 5);
		mapOngletIndex.put(ID_ONGLET_7_INFORMATION_LEGALE, 6);
		mapOngletIndex.put(ID_ONGLET_8_PRIME, 7);
		mapOngletIndex.put(ID_ONGLET_9_GED_DOCUMENT, 8);
		
	}  

	@PostConstruct
	public void postConstruct(){
		refresh();
		donneTimeZone ();
	}
	
	public void refresh(){
		if(auth.getUser()==null) {
			auth.setUser(sessionInfo.getUtilisateur());
		}
		if(auth.isCourtier() && !auth.isDevLgr()) {
			try {
//				taCourtier = taDossierRcdService.findByIdUtilisateur(auth.getUser().getIdUtilisateur());
//				selectedTaDossierRcdDTO = taDossierRcdService.findByIdDTO(taCourtier.getIdCourtier());
				updateTab();
				scrollToTop();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
//			values = taDossierRcdService.findAllLight();
//			taActiviteDTODisponible = taActiviteService.selectAllDTO();
//			taTauxRcproPibDTODisponible = taTauxRcproPibService.selectAllDTO();
//			
//			listeTaTReglement = taTReglementService.selectAll();
//			listeTaTEcheance = taTEcheanceService.selectAll();
//			listeTaPartSoustraitance = taPartSoustraitanceService.selectAll();
//		}
			
		} 
		if(auth.isCourtier()) {
			taCourtier = taCourtierService.findByIdUtilisateur(auth.getUser().getIdUtilisateur());
			values = new ArrayList<TaDossierRcdDTO>();
//			values = taDossierRcdService.findAllContratByIdCourtier(taCourtier.getIdCourtier());
			
			List<TaDossierRcdDTO> listeTmpDossierDTO = taDossierRcdService.findAllContratByIdCourtier(taCourtier.getIdCourtier());
			
			if (values != null && !values.isEmpty()) {
				values.clear();
			}
			for (TaDossierRcdDTO dos : listeTmpDossierDTO) {
				List<TaTStatut> listStat = taTStatutService.findAllStatutByIdDossier(dos.getId());					
				dos.getTaTStatut().clear();
				dos.setTaTStatut(listStat);
				
				//on ne montre au courtier que les contrat qui n'ont pas était suspendu pour avenant
				boolean suspenduAvenant = false;
				
				for (TaTStatut stat : listStat) {
					if(stat.getCodeTStatut().equals(TaTStatut.suspenduAvenant)) {
						suspenduAvenant = true;
					}
				}
				if(!suspenduAvenant) {
					values.add(dos);
				}
				
				
			}
			
		}else if(auth.isAssure()) {
			taAssure = taAssureService.findByIdUtilisateur(auth.getUser().getIdUtilisateur());
			values = taDossierRcdService.findAllContratByIdAssure(taAssure.getIdAssure());
		} else if(auth.isYlyade())   {
			values = new ArrayList<TaDossierRcdDTO>();
			valuesExtraction = new ArrayList<TaDossierRcdDTO>();

			List<TaDossierRcdDTO> listeTmpDossierDTO = taDossierRcdService.findAllContratLight();
			List<TaDossierRcdDTO> listeTmpDossierDTOExtraction = taDossierRcdService.findAllContratLightPlusExtraction();
			if (values != null && !values.isEmpty()) {
				values.clear();
			}
			for (TaDossierRcdDTO dos : listeTmpDossierDTOExtraction) {
				List<TaTStatut> listStat = taTStatutService.findAllStatutByIdDossier(dos.getId());					
				dos.getTaTStatut().clear();
				dos.setTaTStatut(listStat);
				
				valuesExtraction.add(dos);
				}
			for (TaDossierRcdDTO dos : listeTmpDossierDTO) {
				List<TaTStatut> listStat = taTStatutService.findAllStatutByIdDossier(dos.getId());					
				dos.getTaTStatut().clear();
				dos.setTaTStatut(listStat);
				
				values.add(dos);
				}
			System.out.println(valuesExtraction);
		}else if(auth.isSuperAssureur())   {
			values = new ArrayList<TaDossierRcdDTO>();
			List<TaDossierRcdDTO> listeTmpDossierDTO = taDossierRcdService.findAllContratLight();
//			values = taDossierRcdService.findAllContratLight();
			if (values != null && !values.isEmpty()) {
				values.clear();
			}
			for (TaDossierRcdDTO dos : listeTmpDossierDTO) {
				List<TaTStatut> listStat = taTStatutService.findAllStatutByIdDossier(dos.getId());					
				dos.getTaTStatut().clear();
				dos.setTaTStatut(listStat);
				
				values.add(dos);
				}
		}
		
		listeTaTStatut = taTStatutService.selectAll();
		
		
		taActiviteDTODisponible = taActiviteService.selectAllDTO();
		taTauxRcproPibDTODisponible = taTauxRcproPibService.selectAllDTO();
		
		listeTaTReglement = taTReglementService.selectAll();
		listeTaTEcheance = taTEcheanceService.selectAll();
		listeTaTSousTraitance = taTSousTraitanceService.selectAll();
		listeTaTFranchise = taTFranchiseService.selectAll();
		listeTaTGarantieSinistre = taTGarantieSinistre.selectAll();
//		
		modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
//    	infoEntreprise = taInfoEntrepriseService.findInstance();
//    	dateDebut = infoEntreprise.getDatedebInfoEntreprise();
//    	dateFin = infoEntreprise.getDatefinInfoEntreprise();
//
//    	dateDebutProforma = infoEntreprise.getDatedebInfoEntreprise();
//    	dateFinProforma = infoEntreprise.getDatefinInfoEntreprise();
		
//		try {
////			timerService.doWork();
//		} catch (FinderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	public List<TaDossierRcdDTO> getValues(){  
		return values;
	}
	
	public void initAffichageAntecedent(AjaxBehaviorEvent e) {
		initAffichageAntecedent();
	}
	
	public void initAffichageAntecedent() {
		afficheBaseAntecedentRcd = selectedTaDossierRcdDTO.getAssureMemeRisqueRcd();
		afficheResilieRcd = afficheBaseAntecedentRcd && selectedAntecedantRCDEnCours.equals(ANTECEDANT_RCD_RESILIE);
		afficheMotifResilieAssureRcd = afficheResilieRcd && selectedAntecedantRCDResilieParAssure.equals(ANTECEDANT_RCD_RESILIE_PAR_ASSURE);
		afficheMotifResilieAssureurRcd = afficheResilieRcd && selectedAntecedantRCDResilieParAssureur.equals(ANTECEDANT_RCD_RESILIE_PAR_ASSUREUR);
		
		afficheBaseAntecedentRce = selectedTaDossierRcdDTO.getAssureMemeRisqueRce();
		afficheResilieRce = afficheBaseAntecedentRce && selectedAntecedantRCEEnCours.equals(ANTECEDANT_RCE_RESILIE);
		afficheMotifResilieAssureRce = afficheResilieRce && selectedAntecedantRCEResilieParAssure.equals(ANTECEDANT_RCE_RESILIE_PAR_ASSURE);
		afficheMotifResilieAssureurRce = afficheResilieRce && selectedAntecedantRCEResilieParAssureur.equals(ANTECEDANT_RCE_RESILIE_PAR_ASSUREUR);
	}
	
	public void donneTimeZone () {
		if(TimeZone.getDefault()==null) {
			timeZone = TimeZone.getTimeZone("Europe/Paris");
		}else {
			timeZone = TimeZone.getDefault();
		}
	}
	
	public void onActiviteSelect(SelectEvent event) {
		
		if(taTJuridiqueDTO!=null && taTJuridiqueDTO.getCodeTJuridique().equals("AE")) {
			//De 0 à 32 800 €, si une des activités est dans la famille 2, nombre d’activité maximum 3.
			//Sinon nombre d’activité maximum 5
			nbActiviteMax = NB_ACTIVITE_MAX_AUTO_ENTREPRENEUR_DEFAUT;
			
			if(selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel()!=null 
					&& selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel().compareTo(new BigDecimal(32800))<=0) {
				
				for (TaActiviteDTO actDTO : selectedTaActiviteDTOs) {
					if(actDTO.getCodeFamilleActivite().equals(TaFamilleActivite.CODE_FAMILLE_ACTIVITE_2)) {
						nbActiviteMax = NB_ACTIVITE_MAX_AUTO_ENTREPRENEUR_LIMITE;
					}
				}
			}
		} else {
			nbActiviteMax = NB_ACTIVITE_MAX_DEFAUT;
		}
		
		if (selectedTaActiviteDTOs.size() > nbActiviteMax) {
			selectedTaActiviteDTOs.remove(event.getObject());
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("Vous ne pouvez pas séléctionner plus de "+nbActiviteMax+" activités."));
    		System.out.println("get attribute "+ activiteDatatable.getAttributes());
            activiteDatatable.getAttributes().put("disabled",true);
            selectedTaActiviteDTOs = tmpselectedTaActiviteDTOs;
            return;
        }
		
		tmpselectedTaActiviteDTOs = selectedTaActiviteDTOs;
		
		//// Mise à jour de du second datatable
		//ajout
		for (TaActiviteDTO taActiviteDTO : selectedTaActiviteDTOs) {
			boolean trouve = false;
			for (TaActiviteDTO taActiviteDTO2 : listeTaActiviteDTODetail) {
				if(taActiviteDTO.getCodeActivite().equals(taActiviteDTO2.getCodeActivite())) {
					trouve = true;
				}
			}
			if(!trouve) {
				listeTaActiviteDTODetail.add(taActiviteDTO);
			}
		}
		
		//suppression
		List<TaActiviteDTO> aSuppr = new ArrayList<>();
		for (TaActiviteDTO taActiviteDTO : listeTaActiviteDTODetail) {
			boolean trouve = false;
			for (TaActiviteDTO taActiviteDTO2 : selectedTaActiviteDTOs) {
				if(taActiviteDTO.getCodeActivite().equals(taActiviteDTO2.getCodeActivite())) {
					trouve = true;
				}
			}
			if(!trouve) {
				aSuppr.add(taActiviteDTO);
			}
		}
		listeTaActiviteDTODetail.removeAll(aSuppr);
		////
		
//      System.out.println("attribut datatable " +activiteDatatable.getSelection());
		for (TaActiviteDTO a : selectedTaActiviteDTOs) {
			System.out.println("** "+a.getCodeActivite());
		}
		for (TaActiviteDTO a : selectedTaDossierRcdDTO.getListeActivite()) {
			System.out.println("-- "+a.getCodeActivite());
		}
		System.out.println("*********************************");
//		calculMontantPrime();
	}
 
    public void onActiviteUnselect(UnselectEvent event) {
    	 for (TaActiviteDTO a : selectedTaActiviteDTOs) {
 			System.out.println("** "+a.getCodeActivite());
 		}
 	    for (TaActiviteDTO a : selectedTaDossierRcdDTO.getListeActivite()) {
 			System.out.println("-- "+a.getCodeActivite());
 		}
 	    System.out.println("*********************************");
 	    
 	//// Mise à jour de du second datatable
 			//ajout
 			for (TaActiviteDTO taActiviteDTO : selectedTaActiviteDTOs) {
 				boolean trouve = false;
 				for (TaActiviteDTO taActiviteDTO2 : listeTaActiviteDTODetail) {
 					if(taActiviteDTO.getCodeActivite().equals(taActiviteDTO2.getCodeActivite())) {
 						trouve = true;
 					}
 				}
 				if(!trouve) {
 					listeTaActiviteDTODetail.add(taActiviteDTO);
 				}
 			}
 			
 			//suppression
 			List<TaActiviteDTO> aSuppr = new ArrayList<>();
 			for (TaActiviteDTO taActiviteDTO : listeTaActiviteDTODetail) {
 				boolean trouve = false;
 				for (TaActiviteDTO taActiviteDTO2 : selectedTaActiviteDTOs) {
 					if(taActiviteDTO.getCodeActivite().equals(taActiviteDTO2.getCodeActivite())) {
 						trouve = true;
 					}
 				}
 				if(!trouve) {
 					aSuppr.add(taActiviteDTO);
 				}
 			}
 			listeTaActiviteDTODetail.removeAll(aSuppr);
 			////
 			
 	    calculMontantPrime();
    }
    
    
	public void onActiviteSelectAttestationNominative(SelectEvent event) {
		
//		if(taTJuridiqueDTO!=null && taTJuridiqueDTO.getCodeTJuridique().equals("AE")) {
//			//De 0 à 32 800 €, si une des activités est dans la famille 2, nombre d’activité maximum 3.
//			//Sinon nombre d’activité maximum 5
//			nbActiviteMax = NB_ACTIVITE_MAX_AUTO_ENTREPRENEUR_DEFAUT;
//			
//			if(selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel()!=null 
//					&& selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel().compareTo(new BigDecimal(32800))<=0) {
//				
//				for (TaActiviteDTO actDTO : selectedTaActiviteDTOs) {
//					if(actDTO.getCodeFamilleActivite().equals(TaFamilleActivite.CODE_FAMILLE_ACTIVITE_2)) {
//						nbActiviteMax = NB_ACTIVITE_MAX_AUTO_ENTREPRENEUR_LIMITE;
//					}
//				}
//			}
//		} else {
//			nbActiviteMax = NB_ACTIVITE_MAX_DEFAUT;
//		}
//		
//		if (selectedTaActiviteDTOs.size() > nbActiviteMax) {
//			selectedTaActiviteDTOs.remove(event.getObject());
//            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("Vous ne pouvez pas séléctionner plus de "+nbActiviteMax+" activités."));
//    		System.out.println("get attribute "+ activiteDatatable.getAttributes());
//            activiteDatatable.getAttributes().put("disabled",true);
//            selectedTaActiviteDTOs = tmpselectedTaActiviteDTOs;
//            return;
//        }
//		
//		tmpselectedTaActiviteDTOs = selectedTaActiviteDTOs;
//		
//		//// Mise à jour de du second datatable
//		//ajout
//		for (TaActiviteDTO taActiviteDTO : selectedTaActiviteDTOs) {
//			boolean trouve = false;
//			for (TaActiviteDTO taActiviteDTO2 : listeTaActiviteDTODetail) {
//				if(taActiviteDTO.getCodeActivite().equals(taActiviteDTO2.getCodeActivite())) {
//					trouve = true;
//				}
//			}
//			if(!trouve) {
//				listeTaActiviteDTODetail.add(taActiviteDTO);
//			}
//		}
//		
//		//suppression
//		List<TaActiviteDTO> aSuppr = new ArrayList<>();
//		for (TaActiviteDTO taActiviteDTO : listeTaActiviteDTODetail) {
//			boolean trouve = false;
//			for (TaActiviteDTO taActiviteDTO2 : selectedTaActiviteDTOs) {
//				if(taActiviteDTO.getCodeActivite().equals(taActiviteDTO2.getCodeActivite())) {
//					trouve = true;
//				}
//			}
//			if(!trouve) {
//				aSuppr.add(taActiviteDTO);
//			}
//		}
//		listeTaActiviteDTODetail.removeAll(aSuppr);
//		////
//		
////      System.out.println("attribut datatable " +activiteDatatable.getSelection());
//		for (TaActiviteDTO a : selectedTaActiviteDTOs) {
//			System.out.println("** "+a.getCodeActivite());
//		}
//		for (TaActiviteDTO a : selectedTaDossierRcdDTO.getListeActivite()) {
//			System.out.println("-- "+a.getCodeActivite());
//		}
//		System.out.println("*********************************");
////		calculMontantPrime();
	}
 
    public void onActiviteUnselectAttestationNominative(UnselectEvent event) {
//    	 for (TaActiviteDTO a : selectedTaActiviteDTOs) {
// 			System.out.println("** "+a.getCodeActivite());
// 		}
// 	    for (TaActiviteDTO a : selectedTaDossierRcdDTO.getListeActivite()) {
// 			System.out.println("-- "+a.getCodeActivite());
// 		}
// 	    System.out.println("*********************************");
// 	    
// 	//// Mise à jour de du second datatable
// 			//ajout
// 			for (TaActiviteDTO taActiviteDTO : selectedTaActiviteDTOs) {
// 				boolean trouve = false;
// 				for (TaActiviteDTO taActiviteDTO2 : listeTaActiviteDTODetail) {
// 					if(taActiviteDTO.getCodeActivite().equals(taActiviteDTO2.getCodeActivite())) {
// 						trouve = true;
// 					}
// 				}
// 				if(!trouve) {
// 					listeTaActiviteDTODetail.add(taActiviteDTO);
// 				}
// 			}
// 			
// 			//suppression
// 			List<TaActiviteDTO> aSuppr = new ArrayList<>();
// 			for (TaActiviteDTO taActiviteDTO : listeTaActiviteDTODetail) {
// 				boolean trouve = false;
// 				for (TaActiviteDTO taActiviteDTO2 : selectedTaActiviteDTOs) {
// 					if(taActiviteDTO.getCodeActivite().equals(taActiviteDTO2.getCodeActivite())) {
// 						trouve = true;
// 					}
// 				}
// 				if(!trouve) {
// 					aSuppr.add(taActiviteDTO);
// 				}
// 			}
// 			listeTaActiviteDTODetail.removeAll(aSuppr);
// 			////
// 			
// 	    calculMontantPrime();
    }
	
    public void onCellEdit(CellEditEvent event) {
//        Object oldValue = event.getOldValue();
//        Object newValue = event.getNewValue();
        
        
    	//calculPourcentageSoustraitance();
    	
//        if(newValue != null && !newValue.equals(oldValue)) {
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
    }
    
    public void onCellEditFinish(ActionEvent e) {
    	calculPourcentageSoustraitance();
    }
    
    public void onRowEditPourcent(RowEditEvent event) {
    	calculPourcentageSoustraitance();
    }
   
    
    public void calculPourcentageSoustraitance() {
    	
    	totalPourcentageActiviteEntreprise = BigDecimal.ZERO;
    	totalPourcentageActiviteSousTraite = BigDecimal.ZERO;
    	totalPourcentageActivite = BigDecimal.ZERO;
    	
    	for (TaActiviteDTO taActiviteDTO : listeTaActiviteDTODetail) {
        	BigDecimal totalLigne = BigDecimal.ZERO;
        	if(taActiviteDTO.getPourcentExerceEntreprise()!=null) {
        		totalLigne = totalLigne.add(taActiviteDTO.getPourcentExerceEntreprise());
        		System.out.println(taActiviteDTO.getPourcentExerceEntreprise());
        		totalPourcentageActiviteEntreprise = totalPourcentageActiviteEntreprise.add(taActiviteDTO.getPourcentExerceEntreprise());
        	}
        	if(taActiviteDTO.getPourcentSousTraite()!=null) {
        		totalLigne = totalLigne.add(taActiviteDTO.getPourcentSousTraite());
        		totalPourcentageActiviteSousTraite = totalPourcentageActiviteSousTraite.add(taActiviteDTO.getPourcentSousTraite());
        	}
        	taActiviteDTO.setPourcentTotalRepartition(totalLigne);
		}
    	totalPourcentageActivite = totalPourcentageActiviteEntreprise.add(totalPourcentageActiviteSousTraite);
    }
    
	public void actModificationActivite(AjaxBehaviorEvent event) {
	    for (TaActiviteDTO a : selectedTaActiviteDTOs) {
			System.out.println("** "+a.getCodeActivite());
		}
	    for (TaActiviteDTO a : selectedTaDossierRcdDTO.getListeActivite()) {
			System.out.println("-- "+a.getCodeActivite());
		}
	    System.out.println("*********************************");
	    calculMontantPrime();
	}
	
	public void actModificationActivitePib(AjaxBehaviorEvent event) {
	    for (TaTauxRcproPibDTO a : selectedTaTauxRcproPibDTOs) {
			System.out.println("** "+a.getCodeTauxRcproPib());
		}
	    for (TaTauxRcproPibDTO a : selectedTaDossierRcdDTO.getListeTauxRcProPib()) {
			System.out.println("-- "+a.getCodeTauxRcproPib());
		}
	    System.out.println("*********************************");
	    calculMontantPrime();
	}
	
//	public void changeListener(ValueChangeEvent event) {
//	    Object oldValue = event.getOldValue();
//	    Object newValue = event.getNewValue();
//	    // ...
//	}
	
	public BigDecimal chercheTauxRisqueFamilleActivite() {
		BigDecimal taux = BigDecimal.ZERO;
//		Si les activités sont dans maximum 3 familles d’activités aucune incidence
//		Si les activités sont dans 4 familles, hors famille 2 + 10 % sur la prime de base
//		Si les activités sont dans 4 familles, dont famille 2 + 15 % sur la prime de base
//		Si les activités sont dans les 5 famille + 20% sur la prime de base
		int nbActFamille1 = 0;
		int nbActFamille2 = 0;
		int nbActFamille3 = 0;
		int nbActFamille4 = 0;
		int nbActFamille5 = 0;
		Set<String> a = new HashSet<>();
		for (TaActiviteDTO actDTO : selectedTaActiviteDTOs) {
			if(actDTO.getCodeFamilleActivite().equals(TaFamilleActivite.CODE_FAMILLE_ACTIVITE_1)) {
				nbActFamille1++;
				a.add(TaFamilleActivite.CODE_FAMILLE_ACTIVITE_1);
			} else if(actDTO.getCodeFamilleActivite().equals(TaFamilleActivite.CODE_FAMILLE_ACTIVITE_2)) {
				nbActFamille2++;
				a.add(TaFamilleActivite.CODE_FAMILLE_ACTIVITE_2);
			} else if(actDTO.getCodeFamilleActivite().equals(TaFamilleActivite.CODE_FAMILLE_ACTIVITE_3)) {
				nbActFamille3++;
				a.add(TaFamilleActivite.CODE_FAMILLE_ACTIVITE_3);
			} else if(actDTO.getCodeFamilleActivite().equals(TaFamilleActivite.CODE_FAMILLE_ACTIVITE_4)) {
				nbActFamille4++;
				a.add(TaFamilleActivite.CODE_FAMILLE_ACTIVITE_4);
			} else if(actDTO.getCodeFamilleActivite().equals(TaFamilleActivite.CODE_FAMILLE_ACTIVITE_5)) {
				nbActFamille5++;
				a.add(TaFamilleActivite.CODE_FAMILLE_ACTIVITE_5);
			}
		}
		if(a.size()<=3) {
			taux = BigDecimal.ZERO;
			//logCalculPrime("Risque par famille d'activité (taux "+tauxRisqueFamilleActivite+")");
		} else if(a.size()==4) {
			if(a.contains(TaFamilleActivite.CODE_FAMILLE_ACTIVITE_2)) {
				taux = new BigDecimal(15);
			} else {
				taux = new BigDecimal(10);
			}
		} else if(a.size()==5) {
		//if((nbActFamille1>0) && (nbActFamille2>0) && (nbActFamille3>0) && (nbActFamille4>0) && (nbActFamille5>0)) {
			taux = new BigDecimal(20);
		}
		selectedTaDossierRcdDTO.setTauxRisqueParFamilleActivite(taux);
		return taux;
	}
	
	public BigDecimal chercheTauxNombreActivite() {
		BigDecimal taux = BigDecimal.ZERO;
//		DE 0 à 10, aucune incidence
//		DE 11 à 15 + 5 % sur la prime de base
		if(selectedTaActiviteDTOs!=null && !selectedTaActiviteDTOs.isEmpty()) {
			if(selectedTaActiviteDTOs.size()>=11) {
				taux = new BigDecimal(5);
			}
		}
		selectedTaDossierRcdDTO.setTauxNombreActivite(taux);
		return taux;
	}
	
	public BigDecimal chercheTauxInterruptionAssurance() {
		BigDecimal taux = BigDecimal.ZERO;
//		De 0 à 6 mois sans assurance aucune incidence
//		De 6 mois à 12 mois sans assurance + 17,5 %
//		De 12 mois à 24 mois sans assurance + 12,5 %
//		De 24 mois à 36 mois sans assurance + 22,5 %
//		De 36 mois à 60 mois sans assurance + 15 %
//		De 60 mois à 84 mois sans assurance + 25 %
//		A partir de 84 mois + 50 %
		

//		if(selectedInteruptionAssurance!=null) {
//			if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_MOINS_6_MOIS)) {
//				taux = BigDecimal.ZERO;
//			} else if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_6_12_MOIS)) {
//				taux = new BigDecimal(17.5);
//			} else if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_12_24_MOIS)) {
//				taux = new BigDecimal(12.5);
//			} else if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_24_36_MOIS)) {
//				taux = new BigDecimal(22.5);
//			} else if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_36_60_MOIS)) {
//				taux = new BigDecimal(15);
//			} else if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_60_84_MOIS)) {
//				taux = new BigDecimal(25);
//			} else if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_PLUS_84_MOIS)) {
//				taux = new BigDecimal(50);
//			}
//		}
		
//Nouvelle règles Leader Underwriting
//      De 0 à 2 ans 0%
//      De 2 à 5% 15%
//      De 5 à 7 25%
//      Plus de 7% blocage
		if(selectedInteruptionAssurance!=null) {
		if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_0_2_ANS)) {
			taux = BigDecimal.ZERO;
		} else if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_2_5_ANS)) {
			taux = new BigDecimal(15);
		} else if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_5_7_ANS)) {
			taux = new BigDecimal(25);
		} 
	}
		selectedTaDossierRcdDTO.setTauxInterruptionAssurance(taux);
		return taux;
	}
	
	public BigDecimal chercheTauxSinistre36mois() {
		BigDecimal taux = BigDecimal.ZERO;
		boolean etude = false;
//		Si 1 sinistre coût maximum 15 000 € + 10%
//		Si 2 sinistres coût maximum 20 000 € + 15 %
//		Si 3 sinistre coût maximum 30 000 + 20 %
//		Au-delà de 4 sinistres, demande soumis à étude.
		if(taDossierRcd.getTaSinistreAntecedent()!=null && !taDossierRcd.getTaSinistreAntecedent().isEmpty()) {
			BigDecimal montantTotal = BigDecimal.ZERO;
			for (TaSinistreAntecedent taSinistreAntecedent : taDossierRcd.getTaSinistreAntecedent()) {
				montantTotal = montantTotal.add(taSinistreAntecedent.getMontantSinistre());
			}
			if(taDossierRcd.getTaSinistreAntecedent().size()==1) {
				if(montantTotal.compareTo(new BigDecimal(15000))<=0) {
					taux = new BigDecimal(10);
				} else {
					etude = true;
				}
			} else if(taDossierRcd.getTaSinistreAntecedent().size()==2) {
				if(montantTotal.compareTo(new BigDecimal(20000))<=0) {
					taux = new BigDecimal(15);
				} else {
					etude = true;
				}
			} else if(taDossierRcd.getTaSinistreAntecedent().size()==3) {
				if(montantTotal.compareTo(new BigDecimal(30000))<=0) {
					taux = new BigDecimal(20);
				} else {
					etude = true;
				}
			} else { // plus de 4
				etude = true;
			}
		}
		etudePourCauseLimiteSinistre = etude;
		selectedTaDossierRcdDTO.setTauxAntecedentSinistre(taux);
		return taux;
	}
	
	public BigDecimal chercheTauxReprisePasse() {
		BigDecimal taux = BigDecimal.ZERO;
//		De 0 à 3 mois, aucune incidence
//		De 4 à 6 mois + 5 % sur la prime de base
//		De 7 à 12 mois + 7,5 % sur la prime de base
//		if(selectedTaDossierRcdDTO.getReprisePasseMoinsDe3mois()) {
//			taux = BigDecimal.ZERO;
//    	} else if(selectedTaDossierRcdDTO.getReprisePasseDe3a6mois()) {
//    		taux = new BigDecimal(5);
//    	} else if(selectedTaDossierRcdDTO.getReprisePasseDe6a12mois()) {
//    		taux = new BigDecimal(7.5);
//    	} 
		
//		Nouvelle Règle Leader Underwriting
//		20% (1 ans MAX) (15% assureur, 5 % Ylyade)
		if(selectedTaDossierRcdDTO.getReprisePasse()) {
			taux = Const.C_TAUX_REPRISE_PASSE;
		}

		selectedTaDossierRcdDTO.setTauxReprisePasse(taux);
		return taux;
	}
	
	/*--------------------------------------------------------------*
	 * ***** DEBUT GESTION DES BOUTONS RADIO A BASE DE BOOLEEN  ***** 
	 *--------------------------------------------------------------*/
    public static final String MONTANT_MARCHE_TRAVAUX_HT_MIN_50_000 = "50_000";
    public static final String MONTANT_MARCHE_TRAVAUX_HT_MIN_100_000 = "100_000";
    public static final String MONTANT_MARCHE_TRAVAUX_HT_MIN_250_000 = "250_000";
    public static final String MONTANT_MARCHE_TRAVAUX_HT_MIN_500_000 = "500_000";
    public static final String MONTANT_MARCHE_TRAVAUX_HT_MIN_1_000_000 = "1_000_000";
    private String selectedMontantMarcheTravauxHTMin = null;
    public void initRadioMontantMarcheTravauxHTMin() {
    	if(selectedTaDossierRcdDTO.getMontantTravauxHtMax50k()) {
    		selectedMontantMarcheTravauxHTMin = MONTANT_MARCHE_TRAVAUX_HT_MIN_50_000;
    	} else if(selectedTaDossierRcdDTO.getMontantTravauxHtMax100k()) {
    		selectedMontantMarcheTravauxHTMin = MONTANT_MARCHE_TRAVAUX_HT_MIN_100_000;
    	} else if(selectedTaDossierRcdDTO.getMontantTravauxHtMax250k()) {
    		selectedMontantMarcheTravauxHTMin = MONTANT_MARCHE_TRAVAUX_HT_MIN_250_000;
    	} else if(selectedTaDossierRcdDTO.getMontantTravauxHtMax500k()) {
    		selectedMontantMarcheTravauxHTMin = MONTANT_MARCHE_TRAVAUX_HT_MIN_500_000;
    	} else if(selectedTaDossierRcdDTO.getMontantTravauxHtMax1m()) {
    		selectedMontantMarcheTravauxHTMin = MONTANT_MARCHE_TRAVAUX_HT_MIN_1_000_000;
    	}
    }
	public void actRadioMontantMarcheTravauxHTMin() {
		System.out.println("ContratRcdController.actRadioMontantMarcheTravauxHTMin() "+selectedMontantMarcheTravauxHTMin);
		if(selectedMontantMarcheTravauxHTMin!=null) {
			if(selectedMontantMarcheTravauxHTMin.equals(MONTANT_MARCHE_TRAVAUX_HT_MIN_50_000)) {
				selectedTaDossierRcdDTO.setMontantTravauxHtMax50k(true);
				selectedTaDossierRcdDTO.setMontantTravauxHtMax100k(false);
				selectedTaDossierRcdDTO.setMontantTravauxHtMax250k(false);
				selectedTaDossierRcdDTO.setMontantTravauxHtMax500k(false);
				selectedTaDossierRcdDTO.setMontantTravauxHtMax1m(false);
			} else if(selectedMontantMarcheTravauxHTMin.equals(MONTANT_MARCHE_TRAVAUX_HT_MIN_100_000)) {
				selectedTaDossierRcdDTO.setMontantTravauxHtMax50k(false);
				selectedTaDossierRcdDTO.setMontantTravauxHtMax100k(true);
				selectedTaDossierRcdDTO.setMontantTravauxHtMax250k(false);
				selectedTaDossierRcdDTO.setMontantTravauxHtMax500k(false);
				selectedTaDossierRcdDTO.setMontantTravauxHtMax1m(false);
			} else if(selectedMontantMarcheTravauxHTMin.equals(MONTANT_MARCHE_TRAVAUX_HT_MIN_250_000)) {
				selectedTaDossierRcdDTO.setMontantTravauxHtMax50k(false);
				selectedTaDossierRcdDTO.setMontantTravauxHtMax100k(false);
				selectedTaDossierRcdDTO.setMontantTravauxHtMax250k(true);
				selectedTaDossierRcdDTO.setMontantTravauxHtMax500k(false);
				selectedTaDossierRcdDTO.setMontantTravauxHtMax1m(false);
			} else if(selectedMontantMarcheTravauxHTMin.equals(MONTANT_MARCHE_TRAVAUX_HT_MIN_500_000)) {
				selectedTaDossierRcdDTO.setMontantTravauxHtMax50k(false);
				selectedTaDossierRcdDTO.setMontantTravauxHtMax100k(false);
				selectedTaDossierRcdDTO.setMontantTravauxHtMax250k(false);
				selectedTaDossierRcdDTO.setMontantTravauxHtMax500k(true);
				selectedTaDossierRcdDTO.setMontantTravauxHtMax1m(false);
			} else if(selectedMontantMarcheTravauxHTMin.equals(MONTANT_MARCHE_TRAVAUX_HT_MIN_1_000_000)) {
				selectedTaDossierRcdDTO.setMontantTravauxHtMax50k(false);
				selectedTaDossierRcdDTO.setMontantTravauxHtMax100k(false);
				selectedTaDossierRcdDTO.setMontantTravauxHtMax250k(false);
				selectedTaDossierRcdDTO.setMontantTravauxHtMax500k(false);
				selectedTaDossierRcdDTO.setMontantTravauxHtMax1m(true);
			}
		}
	}
	/*----------------------------------------------------------------------------------------------------------*/
	public static final String SOUS_TRAITANCE_1975_OUI = "oui";
    public static final String SOUS_TRAITANCE_1975_NON = "non";
    public static final String SOUS_TRAITANCE_1975_PAS_CONCERNE = "pas_concerne";
    private String selectedSousTraitance1975 = null;
    public void initRadioSousTraitance1975() {
    	if(selectedTaDossierRcdDTO.getRespectDispositionSousTraitance()!=null) {
	    	if(selectedTaDossierRcdDTO.getRespectDispositionSousTraitance()) {
	    		selectedSousTraitance1975 = SOUS_TRAITANCE_1975_OUI;
	    	} else {
	    		selectedSousTraitance1975 = SOUS_TRAITANCE_1975_NON;
	    	} 
    	} else {
    		selectedSousTraitance1975 = SOUS_TRAITANCE_1975_PAS_CONCERNE;
    	}
    }
	public void actRadioSousTraitance1975() {
		if(selectedSousTraitance1975!=null) {
			if(selectedSousTraitance1975.equals(SOUS_TRAITANCE_1975_OUI)) {
				selectedTaDossierRcdDTO.setRespectDispositionSousTraitance(true);
			} else if(selectedSousTraitance1975.equals(SOUS_TRAITANCE_1975_OUI)) {
				selectedTaDossierRcdDTO.setRespectDispositionSousTraitance(false);
			} else if(selectedSousTraitance1975.equals(SOUS_TRAITANCE_1975_PAS_CONCERNE)) {
				selectedTaDossierRcdDTO.setRespectDispositionSousTraitance(null);
			}
		} else {
			selectedTaDossierRcdDTO.setRespectDispositionSousTraitance(null);
		}
	}
	/*----------------------------------------------------------------------------------------------------------*/
	public void actRadioAncienneteEntreprise() {
		//3 booleen
		//-12, 12a36, +36
	}
	/*----------------------------------------------------------------------------------------------------------*/
//	public static final String INTERUPTION_ASSURANCE_MOINS_6_MOIS = "6mois";
//	public static final String INTERUPTION_ASSURANCE_6_12_MOIS    = "6a12mois";
//	public static final String INTERUPTION_ASSURANCE_12_24_MOIS   = "12a24mois";
//	public static final String INTERUPTION_ASSURANCE_24_36_MOIS   = "24a36mois";
//	public static final String INTERUPTION_ASSURANCE_36_60_MOIS   = "36a60mois";
//	public static final String INTERUPTION_ASSURANCE_60_84_MOIS   = "60a84mois";
//	public static final String INTERUPTION_ASSURANCE_PLUS_84_MOIS = "84mois";
	public static final String INTERUPTION_ASSURANCE_0_2_ANS = "0_2ans";
	public static final String INTERUPTION_ASSURANCE_2_5_ANS = "2_5ans";
	public static final String INTERUPTION_ASSURANCE_5_7_ANS = "5_7ans";
	public static final String INTERUPTION_ASSURANCE_PLUS_7_ANS = "plus_7ans";
	private String selectedInteruptionAssurance = null;
	public void initRadioInteruptionAssurance() {
//    	if(selectedTaDossierRcdDTO.getInterruptionAssuranceMoins6mois()) {
//    		selectedInteruptionAssurance = INTERUPTION_ASSURANCE_MOINS_6_MOIS;
//    	} else if(selectedTaDossierRcdDTO.getInterruptionAssurance6a12mois()) {
//    		selectedInteruptionAssurance = INTERUPTION_ASSURANCE_6_12_MOIS;
//    	} else if(selectedTaDossierRcdDTO.getInterruptionAssurance12a24mois()) {
//    		selectedInteruptionAssurance = INTERUPTION_ASSURANCE_12_24_MOIS;
//    	} else if(selectedTaDossierRcdDTO.getInterruptionAssurance24a36mois()) {
//    		selectedInteruptionAssurance = INTERUPTION_ASSURANCE_24_36_MOIS;
//    	} else if(selectedTaDossierRcdDTO.getInterruptionAssurance36a60mois()) {
//    		selectedInteruptionAssurance = INTERUPTION_ASSURANCE_36_60_MOIS;
//    	} else if(selectedTaDossierRcdDTO.getInterruptionAssurance60a84mois()) {
//    		selectedInteruptionAssurance = INTERUPTION_ASSURANCE_60_84_MOIS;
//    	} else if(selectedTaDossierRcdDTO.getInterruptionAssurancePlusDe84mois()) {
//    		selectedInteruptionAssurance = INTERUPTION_ASSURANCE_PLUS_84_MOIS;
//    	}
    	JSONObject jsonData;
		if(taSousDonnee.getJsonData()!=null) {
			 jsonData = new JSONObject(taSousDonnee.getJsonData());
		}else {
			 jsonData = new JSONObject();
		}
		
		if(!jsonData.has(TaSousDonnee.CLE_INTERRUP_ASSUR_0_A_2_ANS)) {
			jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_0_A_2_ANS, "false");
			jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_2_A_5_ANS,	"false");
			jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_5_A_7_ANS, "false");
			jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_PLUS_7_ANS, "false");
			
			taSousDonnee.setJsonData(jsonData.toString());
		}else {
			if(jsonData.get(TaSousDonnee.CLE_INTERRUP_ASSUR_0_A_2_ANS).equals("true")) {
				selectedInteruptionAssurance = INTERUPTION_ASSURANCE_0_2_ANS;
			} else if(jsonData.get(TaSousDonnee.CLE_INTERRUP_ASSUR_2_A_5_ANS).equals("true")) {
				selectedInteruptionAssurance = INTERUPTION_ASSURANCE_2_5_ANS;
			} else if(jsonData.get(TaSousDonnee.CLE_INTERRUP_ASSUR_5_A_7_ANS).equals("true")) {
				selectedInteruptionAssurance = INTERUPTION_ASSURANCE_5_7_ANS;
			} else if(jsonData.get(TaSousDonnee.CLE_INTERRUP_ASSUR_PLUS_7_ANS).equals("true")) {
				selectedInteruptionAssurance = INTERUPTION_ASSURANCE_PLUS_7_ANS;
			} 
			
		}
    }
	
	public void initRadioFranchise() {
		List<TaTFranchise> liste = new ArrayList<TaTFranchise>();
		if(assurePib()) {
			listeTaTFranchise.clear();
			listeTaTFranchise.add(fran2000);
			listeTaTFranchise.add(fran5000);
			listeTaTFranchise.sort(Comparator.comparing(TaTFranchise::getMontant));
			
		}else {
			
			liste = taTFranchiseService.selectAll();
			liste.sort(Comparator.comparing(TaTFranchise::getMontant));
			listeTaTFranchise = liste;	
		}	
		
	}
	public void initRadioProtectionJuridique(){
		JSONObject jsonDATA = new JSONObject(taSousDonnee.getJsonData());
		selectedPJ = jsonDATA.get(TaSousDonnee.CLE_PJ_CHOISIE).toString();
	}
	public void actRadioProtectionJuridique() {
		JSONObject jsonDATA = new JSONObject(taSousDonnee.getJsonData());
		jsonDATA.put(TaSousDonnee.CLE_PJ_CHOISIE, selectedPJ);
		taSousDonnee.setJsonData(jsonDATA.toString());
	}
	public void actRadioInteruptionAssurance() {
		JSONObject jsonData;
		if(taSousDonnee.getJsonData()!=null) {
			 jsonData = new JSONObject(taSousDonnee.getJsonData());
		}else {
			 jsonData = new JSONObject();
		}
		if(selectedInteruptionAssurance!=null) {
//			if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_MOINS_6_MOIS)) {
//				selectedTaDossierRcdDTO.setInterruptionAssuranceMoins6mois(true);
//				selectedTaDossierRcdDTO.setInterruptionAssurance6a12mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance12a24mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance24a36mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance36a60mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance60a84mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurancePlusDe84mois(false);
//			} else if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_6_12_MOIS)) {
//				selectedTaDossierRcdDTO.setInterruptionAssuranceMoins6mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance6a12mois(true);
//				selectedTaDossierRcdDTO.setInterruptionAssurance12a24mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance24a36mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance36a60mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance60a84mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurancePlusDe84mois(false);
//			} else if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_12_24_MOIS)) {
//				selectedTaDossierRcdDTO.setInterruptionAssuranceMoins6mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance6a12mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance12a24mois(true);
//				selectedTaDossierRcdDTO.setInterruptionAssurance24a36mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance36a60mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance60a84mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurancePlusDe84mois(false);
//			} else if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_24_36_MOIS)) {
//				selectedTaDossierRcdDTO.setInterruptionAssuranceMoins6mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance6a12mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance12a24mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance24a36mois(true);
//				selectedTaDossierRcdDTO.setInterruptionAssurance36a60mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance60a84mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurancePlusDe84mois(false);
//			} else if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_36_60_MOIS)) {
//				selectedTaDossierRcdDTO.setInterruptionAssuranceMoins6mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance6a12mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance12a24mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance24a36mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance36a60mois(true);
//				selectedTaDossierRcdDTO.setInterruptionAssurance60a84mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurancePlusDe84mois(false);
//			} else if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_60_84_MOIS)) {
//				selectedTaDossierRcdDTO.setInterruptionAssuranceMoins6mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance6a12mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance12a24mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance24a36mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance36a60mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance60a84mois(true);
//				selectedTaDossierRcdDTO.setInterruptionAssurancePlusDe84mois(false);
//			} else if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_PLUS_84_MOIS)) {
//				selectedTaDossierRcdDTO.setInterruptionAssuranceMoins6mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance6a12mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance12a24mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance24a36mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance36a60mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurance60a84mois(false);
//				selectedTaDossierRcdDTO.setInterruptionAssurancePlusDe84mois(true);
//			}
			if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_0_2_ANS)) {
				jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_0_A_2_ANS, "true");
				jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_2_A_5_ANS, "false");
				jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_5_A_7_ANS, "false");
				jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_PLUS_7_ANS, "false");
				
			} else if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_2_5_ANS)) {
				jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_0_A_2_ANS, "false");
				jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_2_A_5_ANS, "true");
				jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_5_A_7_ANS, "false");
				jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_PLUS_7_ANS, "false");
			} else if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_5_7_ANS)) {
				jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_0_A_2_ANS, "false");
				jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_2_A_5_ANS, "false");
				jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_5_A_7_ANS, "true");
				jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_PLUS_7_ANS, "false");
			} else if(selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_PLUS_7_ANS)) {
				jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_0_A_2_ANS, "false");
				jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_2_A_5_ANS, "false");
				jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_5_A_7_ANS, "false");
				jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_PLUS_7_ANS, "true");
			}
			taSousDonnee.setJsonData(jsonData.toString());
			
//			taSousDonnee = taSousDonneeService.merge(taSousDonnee);
		}
	}
	/*----------------------------------------------------------------------------------------------------------*/
	public static final String ANTECEDANT_RCD_EN_COURS = "en_cours";
    public static final String ANTECEDANT_RCD_RESILIE = "resilie";
    private String selectedAntecedantRCDEnCours = null;
    public void initRadioAntecedantRCDEnCours() {
    	if(selectedTaDossierRcdDTO.getAntecedentRcdContratEnCours()!=null) {
	    	if(selectedTaDossierRcdDTO.getAntecedentRcdContratEnCours()) {
	    		selectedAntecedantRCDEnCours = ANTECEDANT_RCD_EN_COURS;
	    	} else {
	    		selectedAntecedantRCDEnCours = ANTECEDANT_RCD_RESILIE;
	    	} 
    	} 
    }
	public void actRadioAntecedantRCDEnCours() {
		if(selectedAntecedantRCDEnCours!=null) {
			if(selectedAntecedantRCDEnCours.equals(ANTECEDANT_RCD_EN_COURS)) {
				selectedTaDossierRcdDTO.setAntecedentRcdContratEnCours(true);
				selectedTaDossierRcdDTO.setAntecedentRcdContratResilie(false);
			} else if(selectedAntecedantRCDEnCours.equals(ANTECEDANT_RCD_RESILIE)) {
				selectedTaDossierRcdDTO.setAntecedentRcdContratEnCours(false);
				selectedTaDossierRcdDTO.setAntecedentRcdContratResilie(true);
			}
		}
		calculeDureeInterruptionAssurance();
		initAffichageAntecedent();
	}
	/*----------------------------------------------------------------------------------------------------------*/
	public static final String ANTECEDANT_RCE_EN_COURS = "en_cours";
    public static final String ANTECEDANT_RCE_RESILIE = "resilie";
    private String selectedAntecedantRCEEnCours = null;
    public void initRadioAntecedantRCEEnCours() {
    	if(selectedTaDossierRcdDTO.getAntecedentRceContratEnCours()!=null) {
	    	if(selectedTaDossierRcdDTO.getAntecedentRceContratEnCours()) {
	    		selectedAntecedantRCEEnCours = ANTECEDANT_RCE_EN_COURS;
	    	} else {
	    		selectedAntecedantRCEEnCours = ANTECEDANT_RCE_RESILIE;
	    	} 
    	} 
    }
	public void actRadioAntecedantRCEEnCours() {
		if(selectedAntecedantRCEEnCours!=null) {
			if(selectedAntecedantRCEEnCours.equals(ANTECEDANT_RCE_EN_COURS)) {
				selectedTaDossierRcdDTO.setAntecedentRceContratEnCours(true);
				selectedTaDossierRcdDTO.setAntecedentRceContratResilie(false);
			} else if(selectedAntecedantRCEEnCours.equals(ANTECEDANT_RCE_RESILIE)) {
				selectedTaDossierRcdDTO.setAntecedentRceContratEnCours(false);
				selectedTaDossierRcdDTO.setAntecedentRceContratResilie(true);
			}
		}
		calculeDureeInterruptionAssurance();
		initAffichageAntecedent();
	}
	/*----------------------------------------------------------------------------------------------------------*/
	public static final String ANTECEDANT_RCD_RESILIE_PAR_ASSURE = "assure_rcd";
    public static final String ANTECEDANT_RCD_RESILIE_PAR_ASSUREUR = "assureur_rcd";
	private String selectedAntecedantRCDResilieParAssure = null;
	public void initRadioAntecedantRCDResilieParAssure() {
    	if(selectedTaDossierRcdDTO.getAntecedentRcdResilieAssure()!=null) {
	    	if(selectedTaDossierRcdDTO.getAntecedentRcdResilieAssure()) {
	    		selectedAntecedantRCDResilieParAssure = ANTECEDANT_RCD_RESILIE_PAR_ASSURE;
	    	} else {
	    		selectedAntecedantRCDResilieParAssure = ANTECEDANT_RCD_RESILIE_PAR_ASSUREUR;
	    	} 
    	} 
    }
	public void actRadioAntecedantRCDResilieParAssure() {
		if(selectedAntecedantRCDResilieParAssure!=null) {
			if(selectedAntecedantRCDResilieParAssure.equals(ANTECEDANT_RCD_RESILIE_PAR_ASSURE)) {
				selectedTaDossierRcdDTO.setAntecedentRcdResilieAssure(true);
				selectedTaDossierRcdDTO.setAntecedentRcdAssureur(false);
				selectedAntecedantRCDResilieParAssureur = ANTECEDANT_RCD_RESILIE_PAR_ASSURE;
				selectedAntecedantMotifRCDAssureur = null;
			} 
//			else if(selectedAntecedantRCDResilieParAssure.equals(ANTECEDANT_RCD_RESILIE_PAR_ASSUREUR)) {
//				selectedTaDossierRcdDTO.setAntecedentRcdResilieAssure(false);
//				selectedTaDossierRcdDTO.setAntecedentRcdAssureur(true);
//			}
		}
		initAffichageAntecedent();
	}
	/*----------------------------------------------------------------------------------------------------------*/
	private String selectedAntecedantRCDResilieParAssureur = null;
	public void initRadioAntecedantRCDResilieParAssureur() {
    	if(selectedTaDossierRcdDTO.getAntecedentRcdAssureur()!=null) {
	    	if(selectedTaDossierRcdDTO.getAntecedentRcdAssureur()) {
	    		selectedAntecedantRCDResilieParAssureur = ANTECEDANT_RCD_RESILIE_PAR_ASSUREUR;
	    	} else {
	    		selectedAntecedantRCDResilieParAssureur = ANTECEDANT_RCD_RESILIE_PAR_ASSURE;
	    	} 
    	} 
    }
	public void actRadioAntecedantRCDResilieParAssureur() {
		if(selectedAntecedantRCDResilieParAssureur!=null) {
//			if(selectedAntecedantRCDResilieParAssureur.equals(ANTECEDANT_RCD_RESILIE_PAR_ASSURE)) {
//				selectedTaDossierRcdDTO.setAntecedentRcdResilieAssure(true);
//				selectedTaDossierRcdDTO.setAntecedentRcdAssureur(false);
//				selectedAntecedantRCDResilieParAssure = ANTECEDANT_RCD_RESILIE_PAR_ASSUREUR;
//			} else
				if(selectedAntecedantRCDResilieParAssureur.equals(ANTECEDANT_RCD_RESILIE_PAR_ASSUREUR)) {
				selectedTaDossierRcdDTO.setAntecedentRcdResilieAssure(false);
				selectedTaDossierRcdDTO.setAntecedentRcdAssureur(true);
				selectedAntecedantRCDResilieParAssure = ANTECEDANT_RCD_RESILIE_PAR_ASSUREUR;
				selectedAntecedantMotifRCDAssure = null;
			}
		}
		initAffichageAntecedent();
	}
	/*----------------------------------------------------------------------------------------------------------*/
	public static final String ANTECEDANT_RCE_RESILIE_PAR_ASSURE = "assure_rce";
    public static final String ANTECEDANT_RCE_RESILIE_PAR_ASSUREUR = "assureur_rce";
	private String selectedAntecedantRCEResilieParAssure = null;
	public void initRadioAntecedantRCEResilieParAssure() {
    	if(selectedTaDossierRcdDTO.getAntecedentRceResilieAssure()!=null) {
	    	if(selectedTaDossierRcdDTO.getAntecedentRcdResilieAssure()) {
	    		selectedAntecedantRCEResilieParAssure = ANTECEDANT_RCE_RESILIE_PAR_ASSURE;
	    	} else {
	    		selectedAntecedantRCEResilieParAssure = ANTECEDANT_RCE_RESILIE_PAR_ASSUREUR;
	    	} 
    	} 
    }
	public void actRadioAntecedantRCEResilieParAssure() {
		if(selectedAntecedantRCEResilieParAssure!=null) {
			if(selectedAntecedantRCEResilieParAssure.equals(ANTECEDANT_RCE_RESILIE_PAR_ASSURE)) {
				selectedTaDossierRcdDTO.setAntecedentRceResilieAssure(true);
				selectedTaDossierRcdDTO.setAntecedentRceAssureur(false);
				selectedAntecedantRCEResilieParAssureur = ANTECEDANT_RCE_RESILIE_PAR_ASSURE;
				selectedAntecedantMotifRCEAssureur = null;
			} 
//			else if(selectedAntecedantRCEResilieParAssure.equals(ANTECEDANT_RCE_RESILIE_PAR_ASSUREUR)) {
//				selectedTaDossierRcdDTO.setAntecedentRceResilieAssure(false);
//				selectedTaDossierRcdDTO.setAntecedentRceAssureur(true);
//			}
		}
		initAffichageAntecedent();
	}
	/*----------------------------------------------------------------------------------------------------------*/
	private String selectedAntecedantRCEResilieParAssureur = null;
	public void initRadioAntecedantRCEResilieParAssureur() {
    	if(selectedTaDossierRcdDTO.getAntecedentRceAssureur()!=null) {
	    	if(selectedTaDossierRcdDTO.getAntecedentRceAssureur()) {
	    		selectedAntecedantRCEResilieParAssureur = ANTECEDANT_RCE_RESILIE_PAR_ASSUREUR;
	    	} else {
	    		selectedAntecedantRCEResilieParAssureur = ANTECEDANT_RCE_RESILIE_PAR_ASSURE;
	    	} 
    	} 
    }
	public void actRadioAntecedantRCEResilieParAssureur() {
		if(selectedAntecedantRCEResilieParAssureur!=null) {
//			if(selectedAntecedantRCEResilieParAssureur.equals(ANTECEDANT_RCD_RESILIE_PAR_ASSURE)) {
//				selectedTaDossierRcdDTO.setAntecedentRceResilieAssure(true);
//				selectedTaDossierRcdDTO.setAntecedentRceAssureur(false);
//			} else 
				if(selectedAntecedantRCEResilieParAssureur.equals(ANTECEDANT_RCE_RESILIE_PAR_ASSUREUR)) {
				selectedTaDossierRcdDTO.setAntecedentRceResilieAssure(false);
				selectedTaDossierRcdDTO.setAntecedentRceAssureur(true);
				selectedAntecedantRCEResilieParAssure = ANTECEDANT_RCE_RESILIE_PAR_ASSUREUR;
				selectedAntecedantMotifRCEAssure = null;
			}
		}
		initAffichageAntecedent();
	}
	/*----------------------------------------------------------------------------------------------------------*/
	public static final String MOTIF_RESILIATION_ASSURE_ECHEANCE = "echeance";
    public static final String MOTIF_RESILIATION_ASSURE_HAUSSE_TARIF = "hausse_tarif";
    public static final String MOTIF_RESILIATION_ASSURE_CHANGEMENT_ACTIVITE = "changement_act";
    public static final String MOTIF_RESILIATION_ASSURE_AMIABLE = "amiable";
    private String selectedAntecedantMotifRCDAssure = null;
    public void initRadioAntecedantMotifRCDAssure() {
    	if(selectedTaDossierRcdDTO.getAntecedentRcdResilieEcheance()) {
    		selectedAntecedantMotifRCDAssure = MOTIF_RESILIATION_ASSURE_ECHEANCE;
    	} else if(selectedTaDossierRcdDTO.getAntecedentRcdHausseTarif()) {
    		selectedAntecedantMotifRCDAssure = MOTIF_RESILIATION_ASSURE_HAUSSE_TARIF;
    	} else if(selectedTaDossierRcdDTO.getAntecedentRcdChangementActivite()) {
    		selectedAntecedantMotifRCDAssure = MOTIF_RESILIATION_ASSURE_CHANGEMENT_ACTIVITE;
    	} else if(selectedTaDossierRcdDTO.getAntecedentRcdResilieAmiable()) {
    		selectedAntecedantMotifRCDAssure = MOTIF_RESILIATION_ASSURE_AMIABLE;
    	} 
    }
	public void actRadioAntecedantMotifRCDAssure() {
		if(selectedAntecedantMotifRCDAssure!=null) {
			if(selectedAntecedantMotifRCDAssure.equals(MOTIF_RESILIATION_ASSURE_ECHEANCE)) {
				selectedTaDossierRcdDTO.setAntecedentRcdResilieEcheance(true);
				selectedTaDossierRcdDTO.setAntecedentRcdHausseTarif(false);
				selectedTaDossierRcdDTO.setAntecedentRcdChangementActivite(false);
				selectedTaDossierRcdDTO.setAntecedentRcdResilieAmiable(false);
			} else if(selectedAntecedantMotifRCDAssure.equals(MOTIF_RESILIATION_ASSURE_HAUSSE_TARIF)) {
				selectedTaDossierRcdDTO.setAntecedentRcdResilieEcheance(false);
				selectedTaDossierRcdDTO.setAntecedentRcdHausseTarif(true);
				selectedTaDossierRcdDTO.setAntecedentRcdChangementActivite(false);
				selectedTaDossierRcdDTO.setAntecedentRcdResilieAmiable(false);
			} else if(selectedAntecedantMotifRCDAssure.equals(MOTIF_RESILIATION_ASSURE_CHANGEMENT_ACTIVITE)) {
				selectedTaDossierRcdDTO.setAntecedentRcdResilieEcheance(false);
				selectedTaDossierRcdDTO.setAntecedentRcdHausseTarif(false);
				selectedTaDossierRcdDTO.setAntecedentRcdChangementActivite(true);
				selectedTaDossierRcdDTO.setAntecedentRcdResilieAmiable(false);
			} else if(selectedAntecedantMotifRCDAssure.equals(MOTIF_RESILIATION_ASSURE_AMIABLE)) {
				selectedTaDossierRcdDTO.setAntecedentRcdResilieEcheance(false);
				selectedTaDossierRcdDTO.setAntecedentRcdHausseTarif(false);
				selectedTaDossierRcdDTO.setAntecedentRcdChangementActivite(false);
				selectedTaDossierRcdDTO.setAntecedentRcdResilieAmiable(true);
				
			}
		}
	}
	/*----------------------------------------------------------------------------------------------------------*/
	public static final String MOTIF_RESILIATION_ASSUREUR_NON_PAIEMENT = "non_paiement";
    public static final String MOTIF_RESILIATION_ASSUREUR_SINISTRE = "sinistre";
    public static final String MOTIF_RESILIATION_ASSUREUR_MODIFICATION_ACTIVITE = "modification_activite";
    public static final String MOTIF_RESILIATION_ASSUREUR_AUTRE="autre";
    public static final String MOTIF_RESILIATION_ASSURE_AUTRE = "autre";
    private String selectedAntecedantMotifRCDAssureur = null;
    public void initRadioAntecedantMotifRCDAssureur() {
    	if(selectedTaDossierRcdDTO.getAntecedentRcdNonPaiementPrime()) {
    		selectedAntecedantMotifRCDAssureur = MOTIF_RESILIATION_ASSUREUR_NON_PAIEMENT;
    	} else if(selectedTaDossierRcdDTO.getAntecedentRcdSinistre()) {
    		selectedAntecedantMotifRCDAssureur = MOTIF_RESILIATION_ASSUREUR_SINISTRE;
    	} else if(selectedTaDossierRcdDTO.getAntecedentRcdModifActivite()) {
    		selectedAntecedantMotifRCDAssureur = MOTIF_RESILIATION_ASSUREUR_MODIFICATION_ACTIVITE;
    	} else if(selectedTaDossierRcdDTO.getAntecedentRcdAutre()) {
    		selectedAntecedantMotifRCDAssureur = MOTIF_RESILIATION_ASSUREUR_AUTRE;
    	} 
    }
	public void actRadioAntecedantMotifRCDAssureur() {
		if(selectedAntecedantMotifRCDAssureur!=null) {
			if(selectedAntecedantMotifRCDAssureur.equals(MOTIF_RESILIATION_ASSUREUR_NON_PAIEMENT)) {
				selectedTaDossierRcdDTO.setAntecedentRcdNonPaiementPrime(true);
				selectedTaDossierRcdDTO.setAntecedentRcdSinistre(false);
				selectedTaDossierRcdDTO.setAntecedentRcdModifActivite(false);
				selectedTaDossierRcdDTO.setAntecedentRcdAutre(false);
			} else if(selectedAntecedantMotifRCDAssureur.equals(MOTIF_RESILIATION_ASSUREUR_SINISTRE)) {
				selectedTaDossierRcdDTO.setAntecedentRcdNonPaiementPrime(false);
				selectedTaDossierRcdDTO.setAntecedentRcdSinistre(true);
				selectedTaDossierRcdDTO.setAntecedentRcdModifActivite(false);
				selectedTaDossierRcdDTO.setAntecedentRcdAutre(false);
			} else if(selectedAntecedantMotifRCDAssureur.equals(MOTIF_RESILIATION_ASSUREUR_MODIFICATION_ACTIVITE)) {
				selectedTaDossierRcdDTO.setAntecedentRcdNonPaiementPrime(false);
				selectedTaDossierRcdDTO.setAntecedentRcdSinistre(false);
				selectedTaDossierRcdDTO.setAntecedentRcdModifActivite(true);
				selectedTaDossierRcdDTO.setAntecedentRcdAutre(false);
			} else if(selectedAntecedantMotifRCDAssureur.equals(MOTIF_RESILIATION_ASSUREUR_AUTRE)) {
				selectedTaDossierRcdDTO.setAntecedentRcdNonPaiementPrime(false);
				selectedTaDossierRcdDTO.setAntecedentRcdSinistre(false);
				selectedTaDossierRcdDTO.setAntecedentRcdModifActivite(false);
				selectedTaDossierRcdDTO.setAntecedentRcdAutre(true);
				
			}
		}
	}
	/*----------------------------------------------------------------------------------------------------------*/
    private String selectedAntecedantMotifRCEAssure = null;
    public void initRadioAntecedantMotifRCEAssure() {
    	if(selectedTaDossierRcdDTO.getAntecedentRceResilieEcheance()) {
    		selectedAntecedantMotifRCEAssure = MOTIF_RESILIATION_ASSURE_ECHEANCE;
    	} else if(selectedTaDossierRcdDTO.getAntecedentRceHausseTarif()) {
    		selectedAntecedantMotifRCEAssure = MOTIF_RESILIATION_ASSURE_HAUSSE_TARIF;
    	} else if(selectedTaDossierRcdDTO.getAntecedentRceChangementActivite()) {
    		selectedAntecedantMotifRCEAssure = MOTIF_RESILIATION_ASSURE_CHANGEMENT_ACTIVITE;
    	} else if(selectedTaDossierRcdDTO.getAntecedentRceResilieAmiable()) {
    		selectedAntecedantMotifRCEAssure = MOTIF_RESILIATION_ASSURE_AMIABLE;
    	} 
    }
	public void actRadioAntecedantMotifRCEAssure() {
		if(selectedAntecedantMotifRCEAssure!=null) {
			if(selectedAntecedantMotifRCEAssure.equals(MOTIF_RESILIATION_ASSURE_ECHEANCE)) {
				selectedTaDossierRcdDTO.setAntecedentRceResilieEcheance(true);
				selectedTaDossierRcdDTO.setAntecedentRceHausseTarif(false);
				selectedTaDossierRcdDTO.setAntecedentRceChangementActivite(false);
				selectedTaDossierRcdDTO.setAntecedentRceResilieAmiable(false);
			} else if(selectedAntecedantMotifRCEAssure.equals(MOTIF_RESILIATION_ASSURE_HAUSSE_TARIF)) {
				selectedTaDossierRcdDTO.setAntecedentRceResilieEcheance(false);
				selectedTaDossierRcdDTO.setAntecedentRceHausseTarif(true);
				selectedTaDossierRcdDTO.setAntecedentRceChangementActivite(false);
				selectedTaDossierRcdDTO.setAntecedentRceResilieAmiable(false);
			} else if(selectedAntecedantMotifRCEAssure.equals(MOTIF_RESILIATION_ASSURE_CHANGEMENT_ACTIVITE)) {
				selectedTaDossierRcdDTO.setAntecedentRceResilieEcheance(false);
				selectedTaDossierRcdDTO.setAntecedentRceHausseTarif(false);
				selectedTaDossierRcdDTO.setAntecedentRceChangementActivite(true);
				selectedTaDossierRcdDTO.setAntecedentRceResilieAmiable(false);
			} else if(selectedAntecedantMotifRCEAssure.equals(MOTIF_RESILIATION_ASSURE_AMIABLE)) {
				selectedTaDossierRcdDTO.setAntecedentRceResilieEcheance(false);
				selectedTaDossierRcdDTO.setAntecedentRceHausseTarif(false);
				selectedTaDossierRcdDTO.setAntecedentRceChangementActivite(false);
				selectedTaDossierRcdDTO.setAntecedentRceResilieAmiable(true);
			}
		}
	}
	/*----------------------------------------------------------------------------------------------------------*/
	private String selectedAntecedantMotifRCEAssureur = null;
    public void initRadioAntecedantMotifRCEAssureur() {
    	if(selectedTaDossierRcdDTO.getAntecedentRceNonPaiementPrime()) {
    		selectedAntecedantMotifRCEAssureur = MOTIF_RESILIATION_ASSUREUR_NON_PAIEMENT;
    	} else if(selectedTaDossierRcdDTO.getAntecedentRceSinistre()) {
    		selectedAntecedantMotifRCEAssureur = MOTIF_RESILIATION_ASSUREUR_SINISTRE;
    	} else if(selectedTaDossierRcdDTO.getAntecedentRceModifActivite()) {
    		selectedAntecedantMotifRCEAssureur = MOTIF_RESILIATION_ASSUREUR_MODIFICATION_ACTIVITE;
    	} else if(selectedTaDossierRcdDTO.getAntecedentRceAutre()) {
    		selectedAntecedantMotifRCEAssureur = MOTIF_RESILIATION_ASSUREUR_AUTRE;
    	} 
    }
	public void actRadioAntecedantMotifRCEAssureur() {
		if(selectedAntecedantMotifRCEAssureur!=null) {
			if(selectedAntecedantMotifRCEAssureur.equals(MOTIF_RESILIATION_ASSUREUR_NON_PAIEMENT)) {
				selectedTaDossierRcdDTO.setAntecedentRceNonPaiementPrime(true);
				selectedTaDossierRcdDTO.setAntecedentRceSinistre(false);
				selectedTaDossierRcdDTO.setAntecedentRceModifActivite(false);
				selectedTaDossierRcdDTO.setAntecedentRceAutre(false);
			} else if(selectedAntecedantMotifRCEAssureur.equals(MOTIF_RESILIATION_ASSUREUR_SINISTRE)) {
				selectedTaDossierRcdDTO.setAntecedentRceNonPaiementPrime(false);
				selectedTaDossierRcdDTO.setAntecedentRceSinistre(true);
				selectedTaDossierRcdDTO.setAntecedentRceModifActivite(false);
				selectedTaDossierRcdDTO.setAntecedentRceAutre(false);
			} else if(selectedAntecedantMotifRCEAssureur.equals(MOTIF_RESILIATION_ASSUREUR_MODIFICATION_ACTIVITE)) {
				selectedTaDossierRcdDTO.setAntecedentRceNonPaiementPrime(false);
				selectedTaDossierRcdDTO.setAntecedentRceSinistre(false);
				selectedTaDossierRcdDTO.setAntecedentRceModifActivite(true);
				selectedTaDossierRcdDTO.setAntecedentRceAutre(false);
			} else if(selectedAntecedantMotifRCEAssureur.equals(MOTIF_RESILIATION_ASSUREUR_AUTRE)) {
				selectedTaDossierRcdDTO.setAntecedentRceNonPaiementPrime(false);
				selectedTaDossierRcdDTO.setAntecedentRceSinistre(false);
				selectedTaDossierRcdDTO.setAntecedentRceModifActivite(false);
				selectedTaDossierRcdDTO.setAntecedentRceAutre(true);
				
			}
		}
	}
	/*----------------------------------------------------------------------------------------------------------*/
	public static final String REPRISE_PASSE_MOINS_3_MOIS = "3mois";
	public static final String REPRISE_PASSE_3_6_MOIS = "3a6mois";
	public static final String REPRISE_PASSE_6_12_MOIS = "6a12mois";
	private String selectedReprisePasse = null;
    public void initRadioReprisePasse() {
    	if(selectedTaDossierRcdDTO.getReprisePasseMoinsDe3mois()) {
    		selectedReprisePasse = REPRISE_PASSE_MOINS_3_MOIS;
    	} else if(selectedTaDossierRcdDTO.getReprisePasseDe3a6mois()) {
    		selectedReprisePasse = REPRISE_PASSE_3_6_MOIS;
    	} else if(selectedTaDossierRcdDTO.getReprisePasseDe6a12mois()) {
    		selectedReprisePasse = REPRISE_PASSE_6_12_MOIS;
    	} 
    }
	public void actRadioReprisePasse() {
		if(selectedReprisePasse!=null) {
			if(selectedReprisePasse.equals(REPRISE_PASSE_MOINS_3_MOIS)) {
				selectedTaDossierRcdDTO.setReprisePasseMoinsDe3mois(true);
				selectedTaDossierRcdDTO.setReprisePasseDe3a6mois(false);
				selectedTaDossierRcdDTO.setReprisePasseDe6a12mois(false);
				
				LocalDate localDateNow = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				dateLimiteRepriseDuPasse = Date.from((localDateNow).minus(3, ChronoUnit.MONTHS).atStartOfDay(ZoneId.systemDefault()).toInstant());
					
			} else if(selectedReprisePasse.equals(REPRISE_PASSE_3_6_MOIS)) {
				selectedTaDossierRcdDTO.setReprisePasseMoinsDe3mois(false);
				selectedTaDossierRcdDTO.setReprisePasseDe3a6mois(true);
				selectedTaDossierRcdDTO.setReprisePasseDe6a12mois(false);
				
				LocalDate localDateNow = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				dateLimiteRepriseDuPasse = Date.from((localDateNow).minus(6, ChronoUnit.MONTHS).atStartOfDay(ZoneId.systemDefault()).toInstant());
				
			} else if(selectedReprisePasse.equals(REPRISE_PASSE_6_12_MOIS)) {
				selectedTaDossierRcdDTO.setReprisePasseMoinsDe3mois(false);
				selectedTaDossierRcdDTO.setReprisePasseDe3a6mois(false);
				selectedTaDossierRcdDTO.setReprisePasseDe6a12mois(true);
				
				LocalDate localDateNow = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				dateLimiteRepriseDuPasse = Date.from((localDateNow).minus(12, ChronoUnit.MONTHS).atStartOfDay(ZoneId.systemDefault()).toInstant());
			}
			
			if(selectedTaDossierRcdDTO.getTaAssureDTO().getDateCreation() !=null 
					&& dateLimiteRepriseDuPasse.before(selectedTaDossierRcdDTO.getTaAssureDTO().getDateCreation())) {
				dateLimiteRepriseDuPasse = selectedTaDossierRcdDTO.getTaAssureDTO().getDateCreation();
			}
		}
	}
	/*------------------------------------------------------------*
	 * ***** FIN GESTION DES BOUTONS RADIO A BASE DE BOOLEEN  ***** 
	 *------------------------------------------------------------*/
	
	public void logCalculPrime(String txt) {
		boolean log = true;
		if(log) {
			System.out.println(txt);
			logDebugCalculPrime+=txt+"<br/>";
		}
	}
	public void logCalculPremierReglement(String txt) {
		boolean log = true;
		if(log) {
			System.out.println(txt);
			setLogDebugCalculPremierReglement(getLogDebugCalculPremierReglement() + txt+"<br/>");
		}
	}
	
	public boolean assurePib() {
		boolean r = false;
		if(taDossierRcd!=null && taDossierRcd.getTaAssure()!=null && taDossierRcd.getTaAssure().getTaTAssure()!=null) {
			return taDossierRcd.getTaAssure().getTaTAssure().getCodeTAssure().equals(TaTAssure.PIB);
		}
//		return true;
//		return false;
		return r;
	}
	
	private class ResultatActivite {
		
		private TaActivite activiteRef = null;
		private TaPalierClasse palierReferencePourCa = null;
		private TaClasse classeReference = null;
		private BigDecimal montantPrimeBase = null;
		private TaTFranchise franchiseObligatoire;
		
		public ResultatActivite() {
		}
		
		public TaActivite getActiviteRef() {
			return activiteRef;
		}
		public void setActiviteRef(TaActivite activiteRef) {
			this.activiteRef = activiteRef;
		}
		public TaPalierClasse getPalierReferencePourCa() {
			return palierReferencePourCa;
		}
		public void setPalierReferencePourCa(TaPalierClasse palierReferencePourCa) {
			this.palierReferencePourCa = palierReferencePourCa;
		}
		public TaClasse getClasseReference() {
			return classeReference;
		}
		public void setClasseReference(TaClasse classeReference) {
			this.classeReference = classeReference;
		}
		public BigDecimal getMontantPrimeBase() {
			return montantPrimeBase;
		}
		public void setMontantPrimeBase(BigDecimal montantPrimeBase) {
			this.montantPrimeBase = montantPrimeBase;
		}
		public TaTFranchise getFranchiseObligatoire() {
			return franchiseObligatoire;
		}
		public void setFranchiseObligatoire(TaTFranchise franchiseObligatoire) {
			this.franchiseObligatoire = franchiseObligatoire;
		}
	}
	
	public ResultatActivite chercheActiviteDeReference(List<Integer> listeIdActiviteSelectionne, BigDecimal caPrevi) {
		ResultatActivite r = new ResultatActivite();
		
		if(caPrevi==null) {
			caPrevi = BigDecimal.ZERO;
		}
		
		List<TaClasse> listeClasse = new ArrayList<>();
		List<TaActivite> listeActivite = new ArrayList<>();
		List<TaActivite> listeActiviteAvecPrimeDeBase = new ArrayList<>();
		try {
			//préparation
			for (Integer idActivte : listeIdActiviteSelectionne) {
				TaActivite a = taActiviteService.findById(idActivte);
				
				//si l'activite a un minimum de prime, l'ajouter à ce traitement uniquement si le CA est > au palier minimum pour cette activite
				if(a.getPrimeBase()!=null) { //il y a une prime de base
					if(a.getMinCa().compareTo(caPrevi)<=0
							&& a.getMaxCa().compareTo(caPrevi)>=0) { //on est dans la fourchette de CA ou la prime de base s'applique
						listeActiviteAvecPrimeDeBase.add(a);
					} else {
						listeClasse.add(a.getTaClasse());
						listeActivite.add(a);
					}
				} else {
					listeClasse.add(a.getTaClasse());
					listeActivite.add(a);
				}
			}
			
			//recherche des paliers uniquement dans les classes concernées
			for (TaClasse c : listeClasse) {
				for (TaPalierClasse pc : c.getTaPalierClasse()) {
					//Ci-dessous Déclenche un null pointer exception à cause des paliers auto-entrepreneur (sans min et max), A GERER
					if(pc.getPalierMontantMin()!=null) {//Si ce n'est pas un palier pour auto entrepreneur
						if(pc.getPalierMontantMin().compareTo(caPrevi)<0
								&& pc.getPalierMontantMax().compareTo(caPrevi)>=0) { //on cherche le palier correspondant 
							if(r.getPalierReferencePourCa()==null || r.getPalierReferencePourCa().getMontantPrimeBase().compareTo(pc.getMontantPrimeBase())<0) {
								//on garde uniquement le palier donnant la prime de base la plus haute
								r.setPalierReferencePourCa(pc);
								r.setMontantPrimeBase(pc.getMontantPrimeBase());
							}
						}
					}
				}
			}
			
			////// 
			//Activité avec un minimum de prime (prime de base)
			for (TaActivite taActivitePB : listeActiviteAvecPrimeDeBase) {
				if(r.getPalierReferencePourCa()==null || r.getPalierReferencePourCa().getMontantPrimeBase().compareTo(taActivitePB.getPrimeBase())<0) {
					//on garde uniquement la prime de base donnant la prime de base la plus haute
					r.setPalierReferencePourCa(null);
					r.setMontantPrimeBase(taActivitePB.getPrimeBase());
				}
			}
			//////
			
			if(r.getPalierReferencePourCa()!=null) {
				//en fonction du palier retenu on peu obtenir la classe qui de référence
				r.setClasseReference(r.getPalierReferencePourCa().getTaClasse());
			}
			
//			//recherche inverse (recherche des activiés sélectionnées qui ont amenées à choisir cette classe)
//			//on supprime de la liste les activité qui n'appartiennent pas à la classe de référence
//			List<TaActivite> listeActiviteAsupprimer = new ArrayList<>();
//			for (TaActivite a : listeActivite) {
//				if(a.getTaClasse().getIdClasse()!=r.getClasseReference().getIdClasse()) {
//					listeActiviteAsupprimer.add(a);
//				}
//			}
//			listeActivite.removeAll(listeActiviteAsupprimer);
			
			//les activités avec prime de base ont une franchise obligatoire, elles doivent donc etre prise en compte dans la recherche des franchise obligatorie
			listeActivite.addAll(listeActiviteAvecPrimeDeBase); 
			for (TaActivite a : listeActivite) {
				if(a.getTaTFranchise()!=null) {
//					r.setFranchiseObligatoire(a.getTaTFranchise());
					if(r.getFranchiseObligatoire()==null || r.getFranchiseObligatoire().getMontant().compareTo(a.getTaTFranchise().getMontant())<0) {
						r.setFranchiseObligatoire(a.getTaTFranchise());
					}
				}
			}
			if(!listeActivite.isEmpty()) {
				r.setActiviteRef(listeActivite.get(0));
			}
			return r;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	public boolean validationOnglet1DeclarationEngagement() {
		boolean valide = true;
		String titreMessageOnglet = "Declaration et engagement :";
		
//		/*
//		Toutes les questions n’ont pas d’incidence sur le tarif mais peux être bloquant.
//		En effet sur les questions de 1, 2, 3, 4 si la réponse est non c’est bloquant (c’est-à-dire un
//		refus d’acceptation)
//		*/
//		if(!selectedTaDossierRcdDTO.getExperienceActivite3ans() 
////				|| !selectedTaDossierRcdDTO.getExperienceActivte5ans() 
//				|| !selectedTaDossierRcdDTO.getExerceActiviteNomenclature() 
//				|| !selectedTaDossierRcdDTO.getCoutOuvrageInferieur15k() 
//				) {
//			valide = false;
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Une des réponses est bloquante."));
//		}
//		
//		/*
//		 * Sur les questions 5, 6, 7, 8, 9 (Montant de vos marchés) un choix possible. 
//		 */
//		//selectedMontantMarcheTravauxHTMin
//
//		/*
//		 * Sur les question 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21 et 22(le proposant déclare) si
//		 * une réponse est non c’est bloquant (c’est-à-dire un refus d’acceptation)
//		 * Sur toutes les questions la réponse est obligatoire sinon blocage.
//		 */
//		
//		if(!selectedTaDossierRcdDTO.getInterventionConstructeurMaisonIndiv() 
//				|| !selectedTaDossierRcdDTO.getInterventionContractantGeneral() 
//				|| !selectedTaDossierRcdDTO.getInterventionBatiment() 
//				|| !selectedTaDossierRcdDTO.getInterventionMaitreOeuvre() 
//				|| !selectedTaDossierRcdDTO.getInterventionImmobilier() 
//				|| !selectedTaDossierRcdDTO.getInterventionFabricantMatConstruction() 
//				|| !selectedTaDossierRcdDTO.getActivitePrincNegoceFabrMatConstructionNonPose() 
//				|| !selectedTaDossierRcdDTO.getTravauxTechniqueNonCourant() 
//				|| !selectedTaDossierRcdDTO.getInterventionMonumentHistorique() 
//				|| !selectedTaDossierRcdDTO.getAvisTechnique()
////				|| !selectedTaDossierRcdDTO.getDocumentUnique() //non bloquant
//				|| !selectedTaDossierRcdDTO.getRespectRegles() 
//				//|| !selectedTaDossierRcdDTO.getRespectDispositionSousTraitance() 
//				) {
//			valide = false;
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Declaration et engagement :","Une des réponses est bloquante."));
//		}
		
		onglet1Valide = valide;
		return valide;
	}
	
	public boolean validationOnglet2ProposantDeclaration() {
		boolean valide = true;
		String titreMessageOnglet = "Proposant et déclaration :";
		
		/*
		Toutes les réponses sont obligatoires sinon blocage.
		Le chiffre d’affaire, on se base sur le dernier exercice ou le prévisionnel si c’est une création
		Sur toutes les questions la réponse est obligatoire sinon blocage.
		
		Auto-entrepreneur :
		De 0 à 32 800 €, si une des activités est dans la famille 2, nombre d’activité maximum 3.
		Sinon nombre d’activité maximum 5
		Chiffre d’affaire maximum 80 000 €.
		*/
		
//		if(selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel()==null
//				|| taTJuridiqueDTO == null
//				|| selectedTaDossierRcdDTO.getTaAssureDTO().getRaisonSociale()==null
//				|| selectedTaDossierRcdDTO.getTaAssureDTO().getCodeSiren()==null
//				
//				|| selectedTaDossierRcdDTO.getTaAssureDTO().getAdresseLigne1()==null
//				|| selectedTaDossierRcdDTO.getTaAssureDTO().getCodePostal()==null
//				|| selectedTaDossierRcdDTO.getTaAssureDTO().getVille()==null
//				|| selectedTaDossierRcdDTO.getTaAssureDTO().getNumeroTel()==null
//				|| selectedTaDossierRcdDTO.getTaAssureDTO().getAdresseEmail()==null
//				
////				|| taTCiviliteDTO == null
//				|| selectedTaDossierRcdDTO.getTaAssureDTO().getNom()==null
//				|| selectedTaDossierRcdDTO.getTaAssureDTO().getPrenom()==null
//				|| selectedTaDossierRcdDTO.getTaAssureDTO().getDateCreation()==null
//				
//				|| selectedTaDossierRcdDTO.getAnneesExperienceActivite()==null
//				|| selectedTaDossierRcdDTO.getEffectifTotalExercice()==null
//				|| selectedTaDossierRcdDTO.getEffectifSurChantier()==null
//				|| selectedTaDossierRcdDTO.getEffectifApprentis()==null
//				|| selectedTaDossierRcdDTO.getEffectifEncadrement()==null
//				|| selectedTaDossierRcdDTO.getEffectifCommerciauxAdministratifs()==null
//				
//				|| selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel()==null
//				) {
//			valide = false;
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,titreMessageOnglet,"Tous les champs sont obligatoires."));
//		}
		
		if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_INSERTION)==0) { //on ne modifie pas le mdp en modification de contrat
			if(taDossierRcd.getTaAssure().getIdAssure()==null ||  taDossierRcd.getTaAssure().getIdAssure()==0) { //on ne modifie pas le mdp d'un assure deja existant
				if(selectedTaDossierRcdDTO.getTaAssureDTO().getPassword()==null 
						|| selectedTaDossierRcdDTO.getTaAssureDTO().getPassword().equals("")
						|| !selectedTaDossierRcdDTO.getTaAssureDTO().getPassword().equals(selectedTaDossierRcdDTO.getTaAssureDTO().getPasswordConfirm())) {
					valide = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Mot de passe incomplet ou ne correspond pas à sa confirmation."));
				}	
			}
		}
		
		//vérification cohérence des effectifs
//		if(selectedTaDossierRcdDTO.getEffectifTotalExercice()!=null
//				|| selectedTaDossierRcdDTO.getEffectifSurChantier()!=null
//				|| selectedTaDossierRcdDTO.getEffectifApprentis()!=null
//				|| selectedTaDossierRcdDTO.getEffectifEncadrement()!=null
//				|| selectedTaDossierRcdDTO.getEffectifCommerciauxAdministratifs()!=null) {
//			
//			int total = 0;
//				total += selectedTaDossierRcdDTO.getEffectifSurChantier();
//				total += selectedTaDossierRcdDTO.getEffectifApprentis();
//				total += selectedTaDossierRcdDTO.getEffectifEncadrement();
//				total += selectedTaDossierRcdDTO.getEffectifCommerciauxAdministratifs();
//			
//			if(selectedTaDossierRcdDTO.getEffectifTotalExercice()!=total) {
//				valide = false;
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"La déclaration des effectifs de l'entreprise est incohérente."));
//			}
//		}
		
		//vérification CA de N-1 ou N-2 devrait être remplis par rapport à la date de création d'entreprise
//		if(selectedTaDossierRcdDTO.getTaAssureDTO().getDateCreation()!=null) {
//			LocalDate localDateMaintenant= new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//			LocalDate localDateCreationEts = selectedTaDossierRcdDTO.getTaAssureDTO().getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//			Period intervalPeriod = Period.between(localDateCreationEts, localDateMaintenant);
//		    int nbAnnee = intervalPeriod.getYears();
//		    if(nbAnnee>=1 && selectedTaDossierRcdDTO.getCaTotalHtExerciceNMoins1()==null) {
//		    	valide = false;
//		    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,titreMessageOnglet,"Votre entreprise existe depuis plus d'un an, veuillez saisir le chiffre d'affaire N-1"));
//		    }
//		    if(nbAnnee>=2 && (selectedTaDossierRcdDTO.getCaTotalHtExerciceNMoins1()==null || selectedTaDossierRcdDTO.getCaTotalHtExerciceNMoins2()==null)) {
//		    	valide = false;
//		    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,titreMessageOnglet,"Votre entreprise existe depuis plus de deux ans, veuillez saisir les chiffres d'affaire N-1 et N-2"));
//		    }
//		}
	    
		
		if(taTJuridiqueDTO!=null && taTJuridiqueDTO.getCodeTJuridique().equals("AE")) {
//			if(selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel()!=null && selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel().compareTo(new BigDecimal(80000))>0) {
//				valide = false;
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,titreMessageOnglet,"Pour les auto entrepreneurs le chiffre d'affaire maximum est de 80 000€"));
//			}
			nbActiviteMax = NB_ACTIVITE_MAX_AUTO_ENTREPRENEUR_DEFAUT;
		} else {
			nbActiviteMax = NB_ACTIVITE_MAX_DEFAUT;
		}
		
		actRadioReprisePasse(); //initialisation de la date "limite" de reprise du passé.
		calculeDureeInterruptionAssurance();
		
		onglet2Valide = valide;
		return valide;
	}
	
	public boolean validationOnglet3Antecedant() {
		boolean valide = true;
		String titreMessageOngletRCD = "Antécédents RCD :";
		String titreMessageOngletRCE = "Antécédents RCE :";
		String titreMessageOnglet = "Antécédents :";
		/*
		Sur la question Dans les 36 derniers mois
		- Êtes-vous, ou avez-vous été assuré pour le même risque ?
		Si réponse oui le reste doit obligatoirement être complété.
		Sur la question INTERUPTION D’ASSURANCE un seul choix possible.
		*/
//		if(selectedTaDossierRcdDTO.getAssureMemeRisque()) {
//			if(selectedTaDossierRcdDTO.getAssureMemeRisqueRcd()) {
//				if(selectedTaDossierRcdDTO.getAntecedentRcdNomAssureur()==null || selectedTaDossierRcdDTO.getAntecedentRcdNomAssureur().equals("")
//						|| selectedTaDossierRcdDTO.getAntecedentRcdPoliceAssureur()==null || selectedTaDossierRcdDTO.getAntecedentRcdPoliceAssureur().equals("")
//						) {
//					valide = false;
//					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,titreMessageOngletRCD,"Veuillez saisir le nom de votre ancien assureur ainsi que votre ancien numéro de police ."));
//				}
//				if(selectedTaDossierRcdDTO.getAntecedentRcdContratResilie() && selectedTaDossierRcdDTO.getAntecedentRcdDateResiliation()==null) {
//					valide = false;
//					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,titreMessageOngletRCD,"Veuillez saisir la date de résiliation de votre ancien contrat."));
//				}
//			} 
//			if (selectedTaDossierRcdDTO.getAssureMemeRisqueRce()) {
//				if(selectedTaDossierRcdDTO.getAntecedentRceNomAssureur()==null || selectedTaDossierRcdDTO.getAntecedentRceNomAssureur().equals("")
//						|| selectedTaDossierRcdDTO.getAntecedentRcePoliceAssureur()==null || selectedTaDossierRcdDTO.getAntecedentRcePoliceAssureur().equals("")
//						) {
//					valide = false;
//					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,titreMessageOngletRCE,"Veuillez saisir le nom de votre ancien assureur ainsi que votre ancien numéro de police ."));
//				}
//				if(selectedTaDossierRcdDTO.getAntecedentRceContratResilie() && selectedTaDossierRcdDTO.getAntecedentRceDateResiliation()==null) {
//					valide = false;
//					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,titreMessageOngletRCE,"Veuillez saisir la date de résiliation de votre ancien contrat."));
//				}
//			} 
////			else {
////				valide = false;
////			}
//		}
		
		//TODO vérifier la cohérence de "l'intéruption d'assurance" par rapport a la date de création d'entreprise et aux eventuelles date de résiliation
		if(selectedInteruptionAssurance!=null && selectedTaDossierRcdDTO.getTaAssureDTO().getDateCreation()!=null) {
//			LocalDate localDateVerif = null;
//			LocalDate localDateResiliationRcd = null;
//			LocalDate localDateResiliationRce = null;
//			LocalDate localDateMaintenant= new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//			LocalDate localDateCreationEts = selectedTaDossierRcdDTO.getTaAssureDTO().getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//			if(selectedTaDossierRcdDTO.getAntecedentRcdContratResilie() && selectedTaDossierRcdDTO.getAntecedentRcdDateResiliation()!=null) {
//				localDateResiliationRcd = selectedTaDossierRcdDTO.getAntecedentRcdDateResiliation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//			}
//			if(selectedTaDossierRcdDTO.getAntecedentRceContratResilie() && selectedTaDossierRcdDTO.getAntecedentRceDateResiliation()!=null) {
//				localDateResiliationRce = selectedTaDossierRcdDTO.getAntecedentRceDateResiliation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//			}
//			
//			localDateVerif = localDateCreationEts;
//			if(localDateResiliationRcd!=null) {
//				localDateVerif = localDateResiliationRcd;
//			}
//			if(localDateResiliationRce!=null && selectedTaDossierRcdDTO.getAntecedentRcdDateResiliation().before(selectedTaDossierRcdDTO.getAntecedentRceDateResiliation())) {
//				localDateVerif = localDateResiliationRce;
//			}
//			Period intervalPeriod = Period.between(localDateVerif, localDateMaintenant);
//		    int nbMois = intervalPeriod.getMonths();
//		    if(
//		    	(nbMois>=1  && selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_MOINS_6_MOIS)) 
//			    || (nbMois>=1 && selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_6_12_MOIS)) 
//			    || (nbMois>=1 && selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_12_24_MOIS)) 
//			    || (nbMois>=1 && selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_24_36_MOIS)) 
//			    || (nbMois>=1 && selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_36_60_MOIS)) 
//			    || (nbMois>=1 && selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_60_84_MOIS)) 
//			    || (nbMois>=1 && selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_PLUS_84_MOIS))
//			    ){
//		    	valide = false;
//		    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,titreMessageOnglet,"La période d'intérruption d'assurance est incohérente avec la date de création d'entreprise ou avec la date de résiliation du contrat précédent."));
//		    }
		}
		
		onglet3Valide = valide;
		return valide;
	}
	
	public boolean validationOnglet4Sinistralite() {
		boolean valide = true;
		String titreMessageOnglet = "Sinistralité :";
		
		/*
		Sur les trois premières questions si la réponse est oui c’est bloquant (c’est-à-dire un refus
				d’acceptation)
				Sur la quatrième question si la réponse est oui le tableau de sinistre doit obligatoirement est
				complété.
				Quand je dis un refus d’acceptation, c’est que à la fin il n’y aura pas de tarif mais il faut qu’on
				ait une possibilité que le dossier soit soumis à étude.
		*/
//		if(selectedTaDossierRcdDTO.getSinistraliteLiquidationSocieteDemandeuse()
//				|| selectedTaDossierRcdDTO.getSinistraliteLiquidationAutreSociete()
//				|| selectedTaDossierRcdDTO.getSinistraliteRisqueRefusAssurance()
//				) {
//			valide = false;
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Une des réponses est bloquante."));
//		}
//		
//		if(selectedTaDossierRcdDTO.getSinistraliteMiseEnCause()) {
//			if(taDossierRcd.getTaSinistreAntecedent()==null || taDossierRcd.getTaSinistreAntecedent().isEmpty()) {
//				valide = false;
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Veuillez saisir au moins 1 sinistre."));
//			}
//		}
//		
//		if(selectedTaDossierRcdDTO.getSinistraliteEvenementEngageantResp()) {
//			if(selectedTaDossierRcdDTO.getSinistraliteEvenementFaits()==null || selectedTaDossierRcdDTO.getSinistraliteEvenementFaits().equals("")) {
//				valide = false;
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Veuillez décrire les faits."));
//			}
//		}
//		if(assurePib()) {
//			if(selectedTaDossierRcdDTO.getSinistraliteLiquidationSocieteDemandeuse() || selectedTaDossierRcdDTO.getSinistraliteLiquidationAutreSociete()){
//				valide = false;
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Pour les PIB, blocage si il y a redressement judiciaire."));
//			}
//		}
		
		chercheTauxSinistre36mois();
		
		onglet4Valide = valide;
		return valide;
	}
	
	public boolean validationOnglet5ActiviteDeclare() {
		boolean valide = true;
		String titreMessageOnglet = "Activité déclaré :";
		onglet5_1Valide = true;
		onglet5_2Valide = true;
		
		if(!assurePib()) {
//			if(selectedTaActiviteDTOs==null || selectedTaActiviteDTOs.isEmpty()) {
//				onglet5_1Valide = false;
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Veuillez sélectionner au moins 1 activité."));
//			} 
//			
//			
//			for (TaActiviteDTO taActiviteDTO : listeTaActiviteDTODetail) {
//				//vérifier que tous les pourcentage de répartition sont bien remplis
//				if(taActiviteDTO.getPourcentTotalRepartition()==null) {
//					onglet5_2Valide = false;
//					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Veuillez renseigner les pourcentages de sous-traitance pour toutes les activités sélectionées."));
//				}
//				
//				//vérifier les commentaires obligatoires pour certaines activité
//				if(taActiviteDTO.getCommentaireObligatoire()!=null && taActiviteDTO.getCommentaireObligatoire()) {
//					if(taActiviteDTO.getCommentaire()==null || taActiviteDTO.getCommentaire().equals("")) {
//						onglet5_2Valide = false;
//						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Veuillez saisir un commentaire pour les activités sur lesquelle il est obligatoire."));
//					}
//				}
//			}
			calculeTauxSousTraitanceGlobal(null);
			
//			if(afficheQuestionExperienceActivte5ans() && !selectedTaDossierRcdDTO.getExperienceActivte5ans() ) {
//				onglet5_2Valide = false;
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Une des réponses est bloquante."));
//			}
//			
//			
//			if(totalPourcentageActiviteSousTraite!=null && totalPourcentageActiviteSousTraite.compareTo(new BigDecimal(100))>0) {
//				onglet5_2Valide = false;
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Le pourcentage total de sous-traitance dépasse 100%."));
//			}
//			if(totalPourcentageActiviteEntreprise!=null && totalPourcentageActiviteEntreprise.compareTo(new BigDecimal(100))>0) {
//				onglet5_2Valide = false;
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Le pourcentage total des activités 100%."));
//			}
			
			majPrimeNettesEtActiviteRef();
			if(r!=null) {
				try {
					if(r.franchiseObligatoire!=null) {
						if(taTFranchiseService.findByCode(selectedTaDossierRcdDTO.getCodeFranchise()).getMontant().compareTo(r.franchiseObligatoire.getMontant())<0) {
							selectedTaDossierRcdDTO.setCodeFranchise(r.franchiseObligatoire.getCodeTFranchise());
						}
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		}else {
			//BOSSER ICI LES FRANCHISES PIBS ET RECOPIER CODE DE DEVIS DANS CONTRAT CONTROLLER
			if(selectedTaTauxRcproPibDTOs != null && !selectedTaTauxRcproPibDTOs.isEmpty()) {
				majPrimeNettesEtActiviteRef();
				if(selectedtxPib != null) {
					switch (selectedtxPib.getCodeTauxRcproPib()) {
					case "1":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "1.1":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "2":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "2.2":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "3":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR5000_PIB);
						break;
					case "3.1":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "3.2":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "3.3":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "3.4":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "3.5":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "3.6":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR5000_PIB);
						break;
					case "3.7":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "3.8":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "3.9":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "3.10":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR5000_PIB);
						break;
					case "3.11":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR5000_PIB);
						break;
					case "3.12":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "3.14":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "4":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "5":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR5000_PIB);
						break;
					case "6":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "7":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "8":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "9":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "10":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "11":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR5000_PIB);
						break;
					case "12":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "13":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "14":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;
					case "15":
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR2000_PIB);
						break;

					default:
						break;
					}
				}
				
			}else {
				onglet5_2Valide = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Veuillez séléctionner au moins une activité."));									
			}
		}
		
		onglet5Valide = onglet5_1Valide && onglet5_2Valide;
		return valide;
	}
	
	public boolean validationOnglet6OptionPaiement() {
		boolean valide = true;
		String titreMessageOnglet = "Option/Paiement :";
		
		/*
		Sur la question reprise du passé, si la réponse est oui un seul choix est possible, une fois le
		choix coché, il faut demander la date de la reprise du passé et il faut qu’elle soit cohérente
		par rapport au choix.
		Sur la question sous-traitance un seul choix est possible, il faut que le choix soit cohérant
		avec l’écran 5 sur le pourcentage des activités sous-traiter.
		Sur la question TERRITORIALITÉ, plusieurs choix sont possibles.
		Sur la question franchise, un seul choix est possible.
		Sur la question fractionnement un seul choix est possible.
		Sur la question mode règlement, un seul choix est possible.
		Toutes les réponses sont obligatoires sauf concernant la commission et les frais de dossier.
		*/
//		if(selectedTaDossierRcdDTO.getReprisePasse()) {
//			//reprise du passe apres date creation
//			if(selectedTaDossierRcdDTO.getDateRepriseDuPasse().before(selectedTaDossierRcdDTO.getTaAssureDTO().getDateCreation())) {
//				valide = false;
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"La date de reprise du passé doit être postérieure à la date de création de l'entreprise."));
//			}
//			
//			//TODO vérifier que la fourchette de sous traitance choisi correspond bien aux totaux du tableau des activités déclarées.
//			/*
//			 * sous traitance du CA déclaré dans l'entreprise
//			 * sous traitance des activité déclarées
//			 * taux de sous traitance "global"
//			 */
//			if(selectedTaDossierRcdDTO.getPourcentCaSousTraitDernierExercice()!=null
//					&& totalPourcentageActiviteSousTraite!=null
//					&& selectedTaDossierRcdDTO.getCodeTSousTraitance()!=null) {
//				
//			}
//			
//			LocalDate localDateReprisePasse = selectedTaDossierRcdDTO.getDateRepriseDuPasse().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//			LocalDate localDateCreationEts = selectedTaDossierRcdDTO.getTaAssureDTO().getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//		    Period intervalPeriod = Period.between(localDateCreationEts, localDateReprisePasse);
//		    int nbMois = intervalPeriod.getMonths();
//		    for (int i = 0; i < intervalPeriod.getYears(); i++) {
//		    	nbMois = nbMois+12;
//			}
//		    if(selectedReprisePasse.equals(REPRISE_PASSE_MOINS_3_MOIS) ) {
//		    	if(nbMois<3) {
//		    		valide = false;
//		    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"La date de reprise du passé est incohérente avec la date de création d'entreprise."));
//		    	}
//		    } else if(selectedReprisePasse.equals(REPRISE_PASSE_3_6_MOIS) ) {
//		    	if(nbMois<6) {
//		    		valide = false;
//		    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"La date de reprise du passé est incohérente avec la date de création d'entreprise."));
//		    	}
//		    } else if(selectedReprisePasse.equals(REPRISE_PASSE_6_12_MOIS) ) {
//		    	if(nbMois<12) {
//		    		valide = false;
//		    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"La date de reprise du passé est incohérente avec la date de création d'entreprise."));
//		    	}
//		    }
//		}
//		
//		if(!selectedTaDossierRcdDTO.getTerritorialiteLieuFranceMetrop() 
//				&& !selectedTaDossierRcdDTO.getTerritorialiteLieuDomtom()
//				&& !selectedTaDossierRcdDTO.getTerritorialiteLieuCorse()) {
//			valide = false;
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Veuillez choisir au moins 1 emplacement pour vos interventions."));
//		}
//		
//		
//		
//		if(selectedTaDossierRcdDTO.getDateEffet()==null ) {
//			valide = false;
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Veuillez saisir une date d'effet."));
//		}
		if(selectedTaDossierRcdDTO.getCodeTSousTraitance().equals(TaTSousTraitance.ST_30_100)) {
			valide = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Refus : Sous traitance supérieure à 30 %."));
		}
	
	
	
	if(!selectedTaDossierRcdDTO.getTerritorialiteLieuFranceMetrop() 
			&& !selectedTaDossierRcdDTO.getTerritorialiteLieuDomtom()
			&& !selectedTaDossierRcdDTO.getTerritorialiteLieuCorse()) {
		valide = false;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Veuillez choisir au moins 1 emplacement pour vos interventions."));
	}
	
	
	

		
		onglet6Valide = valide;
		return valide;
	}
	
	public boolean validationOnglet7InformationLegale() {
		boolean valide = true;
		String titreMessageOnglet = "Information légale :";
		
		/*
		 * Toutes les réponses sont obligatoires.
		 */
//		if(!selectedTaDossierRcdDTO.getInformerCaractereObligatoire() 
//				|| !selectedTaDossierRcdDTO.getInformationPropositionPartieIntegranteContrat()
//				|| !selectedTaDossierRcdDTO.getAutoriseAssureurCommuniquerReponses()
//				|| !selectedTaDossierRcdDTO.getOpposeUtilisationDonneesFinCommerciale()) {
//			valide = false;
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Veuillez accepter toutes les informations légales."));
//		}
		
		onglet7Valide = valide;
		return valide;
	}
	
	public boolean validationOnglet8Prime() {
		boolean valide = true;
		String titreMessageOnglet = "Prime :";
		
		onglet8Valide = valide;
		return valide;
	}
	
	public boolean validationOnglet9GedDocument() {
		boolean valide = true;
		String titreMessageOnglet = "GED Document :";
		
		//TODO récuperer validation GED
		
		onglet9Valide = valide;
		return valide;
	}
	
	public boolean valideTousLesOnglets() {
		boolean valide = true;
		validationOnglet1DeclarationEngagement();
		validationOnglet2ProposantDeclaration();
		validationOnglet3Antecedant();
		validationOnglet4Sinistralite();
		validationOnglet5ActiviteDeclare();
		validationOnglet6OptionPaiement();
		validationOnglet7InformationLegale();
		validationOnglet8Prime();
		validationOnglet9GedDocument();
		return valide;
	}
	
	public boolean erreurBloquante() {
		return !(onglet1Valide && onglet2Valide && onglet3Valide && onglet4Valide && onglet5Valide && onglet6Valide && onglet7Valide && onglet8Valide && onglet1Valide && !etudePourCauseLimiteSinistre);
	}
	
	public void soumettreLeContratAEtude(ActionEvent e) {
		selectedTaDossierRcdDTO.setSoumisEtude(true);
	}
	
	public boolean franchiseDesactive(TaTFranchise f) {
		majPrimeNettesEtActiviteRef();
		boolean retour = false;
		if(r!=null) {
			if(r.franchiseObligatoire!=null) {
				if(f.getMontant().compareTo(r.franchiseObligatoire.getMontant())<0) {
					retour = true;
				}
			}
		}
		return retour;
	}
	
	public void calculMontantPrime() {
//		logDebugCalculPrime = "";
//		logDebugCalculPremierReglement="";
//		
//		selectedTaDossierRcdDTO.setMontantPrime(null);
//		selectedTaDossierRcdDTO.setMontantPremierReglement(null);
//		if(!erreurBloquante()) {
//			BigDecimal primeAnnuelle = BigDecimal.ZERO;
//			try {
//				
//				if(selectedTaDossierRcdDTO.getDateEffet()!=null) {
//					logCalculPrime("Date effet : " + LibDate.dateToString(selectedTaDossierRcdDTO.getDateEffet()));
//					//date d'échéance : 1an -1jour à partir de la date d'effet
//					LocalDate localDateEffet = selectedTaDossierRcdDTO.getDateEffet().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//					Date echeance = Date.from(LocalDate.of(localDateEffet.getYear(), localDateEffet.getMonthValue(),localDateEffet.getDayOfMonth()).plus(1, ChronoUnit.YEARS).minus(1, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault()).toInstant());
//					selectedTaDossierRcdDTO.setDateEcheance(echeance);
//					
//					logCalculPrime("Date échéance : " + LibDate.dateToString(echeance));
//					
//					//debut période
//					selectedTaDossierRcdDTO.setDateDebutPremierePeriode(selectedTaDossierRcdDTO.getDateEffet());
//					logCalculPrime("Date début 1ère période : " + LibDate.dateToString(echeance));
//				}
//				
//				majPrimeNettesEtActiviteRef();
//			
//				logCalculPrime("=====CALCUL DE LA PRIME============");
//				logCalculPremierReglement("=====CALCUL DU PREMIER REGLEMENT============");
//				
//				primeAnnuelle = primeBaseNette;
//				logCalculPrime("***");
//				logCalculPrime("Prime de base nette : "+primeAnnuelle);
//				logCalculPrime("***");
//					
//				if(primeAnnuelle!=null) {
//					
//					logCalculPrime("**** Début des calculs préléminaires sur la base de la prime nette : ");
//					
//						
//					//Commission courtier (0 à 50% de la base nette)	:		
//					BigDecimal commissionCourtier = BigDecimal.ZERO;
//					if(selectedTaDossierRcdDTO.getTauxCommission()!=null) {
//						commissionCourtier = primeBaseNette.multiply(selectedTaDossierRcdDTO.getTauxCommission()).divide(new BigDecimal(100));
//						selectedTaDossierRcdDTO.setMontantCommissionCourtier(commissionCourtier);
//						logCalculPrime("Commission courtier (taux "+selectedTaDossierRcdDTO.getTauxCommission()+"% montant :"+commissionCourtier+"€) ");
//					} else {
//						logCalculPrime("Commission courtier (taux 0%) : ");
//					}
//					
//					//Protection Judiciaire (PJ), (montant fixe) : 186€
//					BigDecimal montantProtectionJuridique = taTauxAssuranceService.findByCode(TaTauxAssurance.MONTANT_PROTECTION_JURIDIQUE).getTauxTauxAssurance();
//					//primeAnnuelle = primeAnnuelle.add(montantProtectionJuridique);
//					selectedTaDossierRcdDTO.setMontantProtectionJuridique(montantProtectionJuridique);
//					logCalculPrime("Protection juridique ("+montantProtectionJuridique+"€) ");
//					
////					//DPRSA, (montant fixe) : 150€
//					BigDecimal montantDPRSA = taTauxAssuranceService.findByCode(TaTauxAssurance.MONTANT_DPRSA).getTauxTauxAssurance();
//					selectedTaDossierRcdDTO.setMontantDprsa(montantDPRSA);
//					logCalculPrime("DPRSA ("+montantDPRSA+"€) ");
//					//logCalculPrime("Prime de base nette + taxe fiscale + PJ : "+primeAnnuelle);
//					
//					//Frais de gestion compagnie (Ylyade) (FG), (montant fixe) : 150€ ===> inclus uniquement dans le 1er paiement
//					BigDecimal montantFraisGestionCompagnieYlyade = taTFraisImpayeService.findByCode(TaTFraisImpaye.FRAIS_DOSSIER_YLYADE).getMontant();
//					//primeAnnuelle = primeAnnuelle.add(montantFraisGestionCompagnie);
//					selectedTaDossierRcdDTO.setMontantFraisDeGestion(montantFraisGestionCompagnieYlyade);
//					logCalculPrime("Frais de gestion compagnie (Ylyade) ("+montantFraisGestionCompagnieYlyade+"€) ");
//					
//	//				//Frais de gestion compagnie () Super Assureur (FG), (montant fixe) : 150€ ===> inclus uniquement dans le 1er paiement
//					BigDecimal montantFraisGestionCompagnieSuperAssureur = taTFraisImpayeService.findByCode(TaTFraisImpaye.FRAIS_DOSSIER_COMPAGNIE_SUPERASSUREUR).getMontant();
//					//primeAnnuelle = primeAnnuelle.add(montantFraisGestionCompagnie);
//					selectedTaDossierRcdDTO.setMontantFraisCompagnieSuperAssureur(montantFraisGestionCompagnieSuperAssureur);
//					logCalculPrime("Frais de gestion compagnie (Super assureur) ("+montantFraisGestionCompagnieSuperAssureur+"€) ");
//					
//					
//					
//					//Frais	de souscription, frais de dossier/courtier, montant fixe saisi par le courtier	
//					BigDecimal fraisCourtier = BigDecimal.ZERO;
//					if(selectedTaDossierRcdDTO.getFraisRcPro()!=null) {
//						fraisCourtier = selectedTaDossierRcdDTO.getFraisRcPro();
//						logCalculPrime("frais dossier/courtier ("+selectedTaDossierRcdDTO.getFraisRcPro()+"€) ");
//					} else {
//						logCalculPrime("frais dossier/courtier 0€ : "+fraisCourtier);
//					}
//					
//					//Assuré habite à l'étranger ? x2 -- on ne multipli plus par 2 mais on oblige le paiment ANNUEL
//	//				if(taAssure!=null && taAssure.getDomiciliationEtranger()) {
//	//					//primeAnnuelle = primeAnnuelle.multiply(new BigDecimal(2));
//	//					logCalculPrime("Domiciliation étranger OUI (Fractionnement annuel obligatoire) ");
//	//					selectedTaDossierRcdDTO.setCodeEcheance(TaTEcheance.ANNUEL);
//	//				} else {
//	//					logCalculPrime("Domiciliation étranger NON  ");
//	//				}
//					
//						
//					
//					//primeAnnuelle = primeBaseNette.add(montantTaxesFiscaleRcProAssurance).add(commissionCourtier).add(fraisFractionnement);
//					//primeAnnuelle = primeBaseNette.add(montantTaxesFiscaleRcProAssurance).add(commissionCourtier)/*.add(fraisFractionnement)*/.add(montantProtectionJuridique);
//	//				logCalculPrime("**** Fin des calculs préléminaires sur la base de la prime nette.");
//					//logCalculPrime("Prime anuelle (base + taxe fiscale + PJ + commission courtier  : "+primeAnnuelle);
//			
//					//Résilié pour non-paiement	? +20%
//					BigDecimal tauxResilieNonPaiement = taTauxAssuranceService.findByCode(TaTauxAssurance.RESILIATION_NON_PAIEMENT).getTauxTauxAssurance();
//					BigDecimal montantResilieNonPaiement = BigDecimal.ZERO;
//					//if(selectedTaDossierRcdDTO.getResilieNonPaiement()!=null && selectedTaDossierRcdDTO.getResilieNonPaiement()) {
//					
////					selectedTaDossierRcdDTO.assureMemeRisqueRcd
//					
//					if( selectedTaDossierRcdDTO.getAssureMemeRisque()
//						&& 
//						(
//							(
//								selectedTaDossierRcdDTO.getAssureMemeRisqueRcd()!=null && selectedTaDossierRcdDTO.getAssureMemeRisqueRcd()
//								&& selectedAntecedantRCDEnCours.equals(ANTECEDANT_RCD_RESILIE) 
//								&& selectedAntecedantRCDResilieParAssureur.equals(ANTECEDANT_RCD_RESILIE_PAR_ASSUREUR) 
//								&& selectedAntecedantMotifRCDAssureur.equals(MOTIF_RESILIATION_ASSUREUR_NON_PAIEMENT)
//							) || (
//								selectedTaDossierRcdDTO.getAssureMemeRisqueRce()!=null && selectedTaDossierRcdDTO.getAssureMemeRisqueRce()
//								&& selectedAntecedantRCEEnCours.equals(ANTECEDANT_RCE_RESILIE) 
//								&& selectedAntecedantRCEResilieParAssureur.equals(ANTECEDANT_RCE_RESILIE_PAR_ASSUREUR) 
//								&& selectedAntecedantMotifRCEAssureur.equals(MOTIF_RESILIATION_ASSUREUR_NON_PAIEMENT)
//							)
//						)
//					) {
//						selectedTaDossierRcdDTO.setResilieNonPaiement(true);
//						montantResilieNonPaiement = primeBaseNette.multiply(tauxResilieNonPaiement).divide(new BigDecimal(100));
//						logCalculPrime("Résilié pour non-paiement OUI (taux "+tauxResilieNonPaiement+") : "+montantResilieNonPaiement);
//					} else {
//						logCalculPrime("Résilié pour non-paiement NON : "+montantResilieNonPaiement);
//					}
//						
//					//Résilié pour fausse déclaration ?	+20%
//	//				BigDecimal tauxResilieFausseDeclaration = taTauxAssuranceService.findByCode(TaTauxAssurance.RESILISATION_FAUSSE_DECLARATION).getTauxTauxAssurance();
//	//				BigDecimal montantResilieFausseDeclaration = BigDecimal.ZERO;
//	//				if(selectedTaDossierRcdDTO.getResilieFausseDeclaration()!=null && selectedTaDossierRcdDTO.getResilieFausseDeclaration()) {
//	//					montantResilieFausseDeclaration = primeBaseNette.multiply(tauxResilieFausseDeclaration).divide(new BigDecimal(100));
//	//					logCalculPrime("Résilié pour fausse déclaration OUI (taux "+tauxResilieFausseDeclaration+") : "+montantResilieFausseDeclaration);
//	//				} else {
//	//					logCalculPrime("Résilié pour fausse déclaration NON : "+montantResilieFausseDeclaration);
//	//				}
//							
//					//Recours à	la sous-traitance : 0->30%	(0) x1 ** 30->50%	(+15%) x1.15 ** 50->100%	(+20%) x1.20
//					BigDecimal montantSousTraitance = BigDecimal.ZERO;
//					if(selectedTaDossierRcdDTO.getCodeTSousTraitance()!=null ) {
//						TaTSousTraitance st = taTSousTraitanceService.findByCode(selectedTaDossierRcdDTO.getCodeTSousTraitance());
//						taDossierRcd.setTaTSousTraitance(st);
//						if(st!=null && st.getTaux().compareTo(BigDecimal.ZERO)!=0) {
//							BigDecimal tauxSousTraitance = st.getTaux().divide(new BigDecimal(100));
//							montantSousTraitance = primeBaseNette.multiply(tauxSousTraitance);
//							logCalculPrime("Recours	à la sous-traitance OUI (plus de 30%) ("+selectedTaDossierRcdDTO.getCodeTSousTraitance()+" taux "+st.getTaux()+") : "+montantSousTraitance);
//						} else {
//							logCalculPrime("Recours	à la sous-traitance moins de 30%) : "+montantSousTraitance);
//						}
//		
//						if(!assurePib() && selectedTaDossierRcdDTO.getCodeTSousTraitance().equals(TaTSousTraitance.ST_51_100)) { //erreur
//							//La	sous-traitance	de	50	à	100	%	est	réservée	uniquement	aux	activités	de	PIB.
//						}
//					}
//					
//					//Risque par famille d'activité
//					BigDecimal montantRisqueParFamilleActivite = BigDecimal.ZERO;
//	//				if(selectedTaDossierRcdDTO.getReprisePasse()!=null && selectedTaDossierRcdDTO.getReprisePasse()) {
//						//BigDecimal tauxReprisePasse = taTauxAssuranceService.findByCode(TaTauxAssurance.REPRISE_DU_PASSE).getTauxTauxAssurance().divide(new BigDecimal(100));
//						BigDecimal tauxRisqueFamilleActivite = chercheTauxRisqueFamilleActivite().divide(new BigDecimal(100));
//						montantRisqueParFamilleActivite = primeBaseNette.multiply(tauxRisqueFamilleActivite);
//						logCalculPrime("Risque par famille d'activité (taux "+tauxRisqueFamilleActivite+") : "+montantRisqueParFamilleActivite);
//	//				} else {
//	//					logCalculPrime("Risque par famille d'activité : "+montantRisqueParFamilleActivite);
//	//				}
//						
//					//Nombre d'activité
//					BigDecimal montantNombreActivite = BigDecimal.ZERO;
//	//				if(selectedTaDossierRcdDTO.getReprisePasse()!=null && selectedTaDossierRcdDTO.getReprisePasse()) {
//						//BigDecimal tauxReprisePasse = taTauxAssuranceService.findByCode(TaTauxAssurance.REPRISE_DU_PASSE).getTauxTauxAssurance().divide(new BigDecimal(100));
//						BigDecimal tauxNombreActivite = chercheTauxNombreActivite().divide(new BigDecimal(100));
//						montantNombreActivite = primeBaseNette.multiply(tauxNombreActivite);
//						logCalculPrime("Nombre d'activité (taux "+tauxNombreActivite+") : "+montantNombreActivite);
//	//				} else {
//	//					logCalculPrime("Nombre d'activité : "+montantNombreActivite);
//	//				}
//					
//					//Interruption d'assurance
//					BigDecimal montantInteruptionAssurance = BigDecimal.ZERO;
//	//				if(selectedTaDossierRcdDTO.getReprisePasse()!=null && selectedTaDossierRcdDTO.getReprisePasse()) {
//						//BigDecimal tauxReprisePasse = taTauxAssuranceService.findByCode(TaTauxAssurance.REPRISE_DU_PASSE).getTauxTauxAssurance().divide(new BigDecimal(100));
//						BigDecimal tauxInteruptionAssurance = chercheTauxInterruptionAssurance().divide(new BigDecimal(100));
//						montantInteruptionAssurance = primeBaseNette.multiply(tauxInteruptionAssurance);
//						logCalculPrime("Interruption d'assurance (taux "+tauxInteruptionAssurance+") : "+montantInteruptionAssurance);
//	//				} else {
//	//					logCalculPrime("Interruption d'assurance : "+tauxInteruptionAssurance);
//	//				}
//					
//					//Antecedent sinistre
//					BigDecimal montantAntecedantSinistre = BigDecimal.ZERO;
//	//				if(selectedTaDossierRcdDTO.getReprisePasse()!=null && selectedTaDossierRcdDTO.getReprisePasse()) {
//						//BigDecimal tauxReprisePasse = taTauxAssuranceService.findByCode(TaTauxAssurance.REPRISE_DU_PASSE).getTauxTauxAssurance().divide(new BigDecimal(100));
//						BigDecimal tauxAntecedantSinistre = chercheTauxSinistre36mois().divide(new BigDecimal(100));
//						montantAntecedantSinistre = primeBaseNette.multiply(tauxAntecedantSinistre);
//						logCalculPrime("Antecedent sinistre (taux "+tauxAntecedantSinistre+") : "+montantAntecedantSinistre);
//	//				} else {
//	//					logCalculPrime("Antecedent sinistre : "+montantAntecedantSinistre);
//	//				}
//					
//					//Reprise du passé du passé //+15%
//					BigDecimal montantReprisePasse = BigDecimal.ZERO;
//					if(selectedTaDossierRcdDTO.getReprisePasse()!=null && selectedTaDossierRcdDTO.getReprisePasse()) {
//						//BigDecimal tauxReprisePasse = taTauxAssuranceService.findByCode(TaTauxAssurance.REPRISE_DU_PASSE).getTauxTauxAssurance().divide(new BigDecimal(100));
//						BigDecimal tauxReprisePasse = chercheTauxReprisePasse().divide(new BigDecimal(100));
//						montantReprisePasse = primeBaseNette.multiply(tauxReprisePasse);
//						logCalculPrime("Reprise	du passé du	passé OUI (taux "+tauxReprisePasse+") : "+montantReprisePasse);
//					} else {
//						logCalculPrime("Reprise	du passé du	passé NON : "+montantReprisePasse);
//					}
//					
//					
//					//Franchise : 4 000€ x1 *** 2 000€ (+15%) x1.15	 *** 1 000€ (+20%) x1.20	
//					//Si au moins une des activités sélectionnés possède une franchise obligatoire, on prend la franchise obligatoire la plus élevé 
//					//(même si elle ne corrrespond pas à l'activité  qui sert pour le calcul de base), dans ce cas il faudrait désactivé les boutons radios
//					BigDecimal montantFranchise = BigDecimal.ZERO;
//					if(selectedTaDossierRcdDTO.getCodeFranchise()!=null) {
//						TaTFranchise f = null;
//						if(!assurePib()) { //la franchise potentiellement fixe en fonction de l'activité qui sert au calcul de la prime de base, verification à faire
//		//					if(taActiviteDeReference!=null && taActiviteDeReference.getTaTFranchise()!=null) {
//		//						//il y a une franchise "fixe" pour cette activité, on prend cette franchise en compte et on met à jour le DTO/affichage si une franchise "non autorisé" etait sélectionnée
//		//						f = taActiviteDeReference.getTaTFranchise();
//		//						selectedTaDossierRcdDTO.setCodeFranchise(f.getCodeTFranchise());
//		//						logCalculPrime("Franchise 'fixe' "+f.getCodeTFranchise());
//		//					} 
//							if(r!=null && r.getFranchiseObligatoire()!=null) {
//								//il y a une franchise "fixe" pour cette activité, on prend cette franchise en compte et on met à jour le DTO/affichage si une franchise "non autorisé" etait sélectionnée
//								f = r.getFranchiseObligatoire();
//		
//								if(taTFranchiseService.findByCode(selectedTaDossierRcdDTO.getCodeFranchise()).getMontant().compareTo(f.getMontant())<0) {
//									//a prends la franchise minimum obligatoire suelement si la franchise selectionne actuellement est inferieure
//									selectedTaDossierRcdDTO.setCodeFranchise(f.getCodeTFranchise());
//								}
//								
//								//selectedTaDossierRcdDTO.setCodeFranchise(f.getCodeTFranchise());
//								logCalculPrime("Franchise 'fixe' "+f.getCodeTFranchise());
//							} 
//						} 
//						if(f==null) { //pas de franchise "fixe"
//							f = taTFranchiseService.findByCode(selectedTaDossierRcdDTO.getCodeFranchise());
//							selectedTaDossierRcdDTO.setFranchise(f.getMontant());
//							logCalculPrime("pas de franchise 'fixe' sur l'activité de de référence, choix libre");
//						}
//						if(f!=null && f.getTauxMontant().compareTo(BigDecimal.ZERO)!=0) {
//							BigDecimal tauxFranchise = f.getTauxMontant().divide(new BigDecimal(100));
//							montantFranchise = primeBaseNette.multiply(tauxFranchise);
//							logCalculPrime("Franchise ("+f.getCodeTFranchise()+" taux "+f.getTauxMontant()+") : "+montantFranchise);
//						} else {
//							logCalculPrime("Franchise de base (pas de majoration) : "+montantFranchise);
//						}
//					}
//					
//					
//					/*
//					 * Calcul de la taxe fiscale => 9 % de la prime de base + majoration s'il y en a :
//					 * -Résilié pour non paiement
//					 * -Recours à la sous-traitance
//					 * -Risque par famille d'activité
//					 * -Nombre d'activité
//					 * -Interruption d'assurance
//					 * -Antécédent sinistre
//					 * -Reprise du passé
//					 * -Franchise
//					 */				
//					//Taxe diverses assurance 9% de la base nette// Taxe fiscale RC PRO Décennale
//					BigDecimal tauxTaxesFiscaleRcProAssurance = taTauxAssuranceService.findByCode(TaTauxAssurance.TAXE_FISCALE_RC_PRO_DECENNALE).getTauxTauxAssurance().divide(new BigDecimal(100));
//					BigDecimal primeAvantTaxeDiverses = primeAnnuelle;
//					selectedTaDossierRcdDTO.setTauxTaxeFiscale(tauxTaxesFiscaleRcProAssurance);
//					//primeAnnuelle = primeBaseNette.multiply(tauxTaxesFiscaleRcProAssurance);
//					//BigDecimal montantTaxesFiscaleRcProAssurance = primeAnnuelle.subtract(primeAvantTaxeDiverses);
//					BigDecimal montantTaxesFiscaleRcProAssurance = (primeBaseNette
//																	.add(montantResilieNonPaiement)
//																	.add(montantSousTraitance)
//																	.add(montantRisqueParFamilleActivite)
//																	.add(montantNombreActivite)
//																	.add(montantInteruptionAssurance)
//																	.add(montantAntecedantSinistre)
//																	.add(montantReprisePasse)
//																	.add(montantFranchise)
//																	)
//																	.multiply(tauxTaxesFiscaleRcProAssurance);
//					logCalculPrime("Taxe fiscale (base prime nette + non paiement + soustrait + risque famille + nb act + interruption + antecedent + passé + franchise) (taux "+tauxTaxesFiscaleRcProAssurance+" montant :"+montantTaxesFiscaleRcProAssurance+"€) ");
//					
//					selectedTaDossierRcdDTO.setMontantTaxesDiversesAssurance(montantTaxesFiscaleRcProAssurance);
//					selectedTaDossierRcdDTO.setMontantTaxeFiscale(montantTaxesFiscaleRcProAssurance);
//					
//					//pour affichage on regroupe taxe fiscale et frais de gestion => taxes diverses
//					selectedTaDossierRcdDTO.setMontantTaxesDiversesAssurance(montantTaxesFiscaleRcProAssurance.add(montantFraisGestionCompagnieYlyade));
//					
//					//Taux de régulation applicable au CA HT = (Prime de base HT + les différent % de majoration[sous traite,reprise passé,...])/ (CA HT)
//					BigDecimal tauxRegulationApplicableCaHt = BigDecimal.ZERO;
//					tauxRegulationApplicableCaHt = (primeBaseNette
//																//.add(montantTaxesFiscaleRcProAssurance)
//																//.add(montantProtectionJuridique)
//																.add(montantRisqueParFamilleActivite)
//																.add(montantNombreActivite)
//																.add(montantInteruptionAssurance)
//																.add(montantAntecedantSinistre)
//																.add(montantFranchise)
//																.add(montantReprisePasse)
//																.add(montantSousTraitance)
//																.add(montantResilieNonPaiement)
//																//.add(commissionCourtier)
//																)
//																.divide(selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel(),2,BigDecimal.ROUND_HALF_UP)
//																;
//					selectedTaDossierRcdDTO.setTxRegulCaHt(tauxRegulationApplicableCaHt);
//					logCalculPrime("Taux de régulation applicable au CA HT : "+tauxRegulationApplicableCaHt);
//					
//					logCalculPrime("**** Fin des calculs préléminaires sur la base de la prime nette.");
//					logCalculPrime("******************************* : ");
//	
//					logCalculPrime("prime base nette : "+primeBaseNette);
//					logCalculPrime("montantTaxesFiscaleRcProAssurance : "+montantTaxesFiscaleRcProAssurance);
//					logCalculPrime("montantProtectionJuridique : "+montantProtectionJuridique);
//					logCalculPrime("montantDPRSA : "+montantDPRSA);
//					logCalculPrime("montantRisqueParFamilleActivite : "+montantRisqueParFamilleActivite);
//					logCalculPrime("montantNombreActivite : "+montantNombreActivite);
//					logCalculPrime("montantInteruptionAssurance : "+montantInteruptionAssurance);
//					logCalculPrime("montantAntecedantSinistre : "+montantAntecedantSinistre);
//					logCalculPrime("montantFranchise : "+montantFranchise);
//					logCalculPrime("montantReprisePasse : "+montantReprisePasse);
//					logCalculPrime("montantSousTraitance : "+montantSousTraitance);
//	//				logCalculPrime("montantResilieFausseDeclaration : "+montantResilieFausseDeclaration);
//					logCalculPrime("montantResilieNonPaiement : "+montantResilieNonPaiement);
//					logCalculPrime("commissionCourtier : "+commissionCourtier);
//					primeAnnuelle = primeBaseNette.add(montantTaxesFiscaleRcProAssurance)
//													.add(montantProtectionJuridique)
//													.add(montantDPRSA)
//													.add(montantRisqueParFamilleActivite)
//													.add(montantNombreActivite)
//													.add(montantInteruptionAssurance)
//													.add(montantAntecedantSinistre)
//													.add(montantFranchise)
//													.add(montantReprisePasse)
//													.add(montantSousTraitance)
//	//												.add(montantResilieFausseDeclaration)
//													.add(montantResilieNonPaiement)
//													.add(commissionCourtier);
//					//logCalculPrime("*Prime annuelle (base + taxe fisc + PJ + maj franchise + maj reprise passe + maj sous traitance + maj fausse decl + maj resiliation non paiement + comm courtier): "+primeAnnuelle);
//					logCalculPrime("*Prime annuelle (base + taxe fisc + PJ + maj franchise + maj reprise passe "
//							+ "+ maj sous traitance + maj risque par  + maj resiliation non paiement + comm courtier): "+primeAnnuelle);
//					selectedTaDossierRcdDTO.setPrimeAnnuelleComplete(primeAnnuelle);
//					logCalculPrime("***");
//					
//					logCalculPrime("***** Calcul des frais de fractionnement sur la base : (prime annuelle - PJ - DPRSA) ");
//					primeAnnuelle = primeAnnuelle.subtract(montantProtectionJuridique);
//					primeAnnuelle = primeAnnuelle.subtract(montantDPRSA);
//					
//					//Frais de fractionnement :	Annuel	x1 ** Semestriel (+4%)	x1.04 ** Trimestriel (+7%)	x1.07
//					BigDecimal fraisFractionnement = BigDecimal.ZERO;
//					if(selectedTaDossierRcdDTO.getCodeEcheance()==null ) {
//						selectedTaDossierRcdDTO.setCodeEcheance(TaTEcheance.ANNUEL);
//					}
//					TaTEcheance ech = taTEcheanceService.findByCode(selectedTaDossierRcdDTO.getCodeEcheance());
//					if(ech!=null && ech.getTauxEcheance().compareTo(BigDecimal.ZERO)!=0) {
//						//fraisFractionnement = primeBaseNette.multiply(ech.getTauxEcheance()).divide(new BigDecimal(100));
//						fraisFractionnement = primeAnnuelle.multiply(ech.getTauxEcheance()).divide(new BigDecimal(100));
//	
//						logCalculPrime("Echeance ("+selectedTaDossierRcdDTO.getCodeEcheance()+" taux "+ech.getTauxEcheance()+"% montant :"+fraisFractionnement+"€) ");
//					} else {
//						logCalculPrime("Echeance de base (annuelle, pas de majoration) : ");
//					}
//					selectedTaDossierRcdDTO.setMontantFraisFractionnement(fraisFractionnement);
//					if(ech!=null && ech.getLiblTEcheance()!=null) {
//						selectedTaDossierRcdDTO.setLiblTEcheance(ech.getLiblTEcheance());
//					}
//					
//					if(selectedTaDossierRcdDTO.getDateEffet()!=null && ech!=null) {
//						LocalDate localDateEffet = selectedTaDossierRcdDTO.getDateEffet().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//						Date fin1erPeriode = null;
//						if(ech.getCodeTEcheance().equals(TaTEcheance.ANNUEL)) {
//							fin1erPeriode = Date.from(LocalDate.of(localDateEffet.getYear(), localDateEffet.getMonthValue(),localDateEffet.getDayOfMonth()).plus(1, ChronoUnit.YEARS).atStartOfDay(ZoneId.systemDefault()).toInstant());
//						} else if(ech.getCodeTEcheance().equals(TaTEcheance.SEMESTRIEL)) {
//							fin1erPeriode = Date.from(LocalDate.of(localDateEffet.getYear(), localDateEffet.getMonthValue(),localDateEffet.getDayOfMonth()).plus(6, ChronoUnit.MONTHS).atStartOfDay(ZoneId.systemDefault()).toInstant());
//						} else if(ech.getCodeTEcheance().equals(TaTEcheance.TRIMESTRIEL)) {
//							fin1erPeriode = Date.from(LocalDate.of(localDateEffet.getYear(), localDateEffet.getMonthValue(),localDateEffet.getDayOfMonth()).plus(3, ChronoUnit.MONTHS).atStartOfDay(ZoneId.systemDefault()).toInstant());
//						}
//						selectedTaDossierRcdDTO.setDateFinPremierePeriode(fin1erPeriode);
//						logCalculPrime("Date Fin 1ère période : " + LibDate.dateToString(fin1erPeriode));
//					}
//					
//					//Calcul du montant du 1er reglement, qui correspond à la premiere échéance + les différents frais
//					logCalculPrime("***** Calcul du montant d'une échéance : (prime annuelle + PJ + DPRSA + frais de fractionnement) / (1 ou 2 ou 4) ");
//	//				logCalculPremierReglement("***** Calcul du montant d'une échéance : (prime annuelle + PJ + frais de fractionnement) / (1 ou 2 ou 4) ");
//					primeAnnuelle = primeAnnuelle.add(montantProtectionJuridique).add(montantDPRSA).add(fraisFractionnement);
//					BigDecimal uneEcheance = BigDecimal.ZERO;
//					BigDecimal montantCommissionCourtierUneEcheance = BigDecimal.ZERO;
//					if(selectedTaDossierRcdDTO.getCodeEcheance().equals(TaTEcheance.ANNUEL)) {
//						uneEcheance = primeAnnuelle;
//						montantCommissionCourtierUneEcheance = commissionCourtier;
//					} else if(selectedTaDossierRcdDTO.getCodeEcheance().equals(TaTEcheance.SEMESTRIEL)) {
//						uneEcheance = primeAnnuelle.divide(new BigDecimal(2));
//						montantCommissionCourtierUneEcheance = commissionCourtier.divide(new BigDecimal(2));
//					} else if(selectedTaDossierRcdDTO.getCodeEcheance().equals(TaTEcheance.TRIMESTRIEL)) {
//						uneEcheance = primeAnnuelle.divide(new BigDecimal(4));
//						montantCommissionCourtierUneEcheance = commissionCourtier.divide(new BigDecimal(4));
//					}
//					
//					taDossierRcd.setMontantPrime(uneEcheance);
//					taDossierRcd.setMontantCommissionCourtierUneEcheance(montantCommissionCourtierUneEcheance);
//					selectedTaDossierRcdDTO.setMontantPrime(uneEcheance);
//					logCalculPrime("Montant d'une écheance ("+uneEcheance+"€) ");
//					
//					BigDecimal montant1erReglement = BigDecimal.ZERO;
//					montant1erReglement = uneEcheance.add(montantFraisGestionCompagnieYlyade)
//														.add(montantFraisGestionCompagnieSuperAssureur)
//														//.add(montantProtectionJuridique)
//														//.add(fraisFractionnement)
//														;
//					if(selectedTaDossierRcdDTO.getInclusionFraisDossier()) {
//						montant1erReglement = montant1erReglement.add(fraisCourtier);
//						logCalculPremierReglement("Montant 1er règlement = une échéance :"+uneEcheance+" + les frais de gestion Ylyade :"+montantFraisGestionCompagnieYlyade+ " les frais de gestion Compagnie :"+montantFraisGestionCompagnieSuperAssureur+" + les frais de dossier (courtier) : "+fraisCourtier+" = "+montant1erReglement);
//					} else {
//						logCalculPremierReglement("Montant 1er règlement = une échéance :"+uneEcheance+" + les frais de gestion Ylyade :"+montantFraisGestionCompagnieYlyade+ " les frais de gestion Compagnie :"+montantFraisGestionCompagnieSuperAssureur+" = "+montant1erReglement);
//					}
//					
//					selectedTaDossierRcdDTO.setMontantPremierReglement(montant1erReglement);
//					//logCalculPrime("Montant 1er règlement (une échéance + frais gestion + PJ + frais dossier courtier + frais fractionnement): "+montant1erReglement);
//					
//					//logCalculPrime("Montant 1er règlement (une échéance + frais gestion (Ylyade et compagnie) + frais dossier courtier): "+montant1erReglement);
//	//				logCalculPremierReglement("Montant 1er règlement (une échéance + frais gestion + frais dossier courtier): "+montant1erReglement);
//					
//					logCalculPrime("***");
//					logCalculPremierReglement("***");
//					
//					logCalculPrime("=====FIN CALCUL DE LA PRIME============");	
//					selectedTaDossierRcdDTO.setLogDetailCalculPrime(logDebugCalculPrime);
//					selectedTaDossierRcdDTO.setLogDetailCalculPremierReglement(logDebugCalculPremierReglement);
//				}
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
//		} else {
//			logCalculPrime("=====Une ou plusieurs erreurs bloquante empeche le calcul de la prime============");	
//		}
//			
	}
	
	public boolean afficheQuestionExperienceActivte5ans() {
		boolean result = false;
		//Pour les activités : 3 ; 5 ; 6 ; 7 ; 8 ; 9 ; 11 ; 15 ; 16 ; 17 ; 28-1 ; 30-1 ; 36"
		List<String> listeCodeActiviteExp5ans = new ArrayList<>();
		listeCodeActiviteExp5ans.add("3");
		listeCodeActiviteExp5ans.add("5");
		listeCodeActiviteExp5ans.add("6");
		listeCodeActiviteExp5ans.add("7");
		listeCodeActiviteExp5ans.add("8");
		listeCodeActiviteExp5ans.add("9");
		listeCodeActiviteExp5ans.add("11");
		listeCodeActiviteExp5ans.add("15");
		listeCodeActiviteExp5ans.add("16");
		listeCodeActiviteExp5ans.add("17");
		listeCodeActiviteExp5ans.add("28.1");
		listeCodeActiviteExp5ans.add("30.1");
		listeCodeActiviteExp5ans.add("36");
		
		for (TaActiviteDTO a : listeTaActiviteDTODetail) {
			if(listeCodeActiviteExp5ans.contains(a.getCodeActivite())) {
				result = true;
			}
		}
		
		return result;
	}

	public void actAnnuler(ActionEvent actionEvent) {
		try {
			switch (modeEcran.getMode()) {
			case C_MO_INSERTION:
				if(selectedTaDossierRcdDTO.getNumDossierPolice()!=null) {
					taDossierRcdService.annuleCode(selectedTaDossierRcdDTO.getNumDossierPolice());
				}
				taDossierRcd = new TaDossierRcd();
				mapperModelToUI.map(taDossierRcd,selectedTaDossierRcdDTO );
				selectedTaDossierRcdDTO=new TaDossierRcdDTO();
				
				if(!values.isEmpty()) selectedTaDossierRcdDTO = values.get(0);
				onRowSelect(null);
				break;
			case C_MO_EDITION:
				if(selectedTaDossierRcdDTO.getId()!=null && selectedTaDossierRcdDTO.getId()!=0){
					taDossierRcd = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
					selectedTaDossierRcdDTO=taDossierRcdService.findByIdDTO(selectedTaDossierRcdDTO.getId());
					updateTab();
				}				
				break;

			default:
				break;
			}
			modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
			mapperModelToUI.map(taDossierRcd, selectedTaDossierRcdDTO);

			if(ConstWeb.DEBUG) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage("Courtier", "actAnnuler"));
			}
		} catch (FinderException e) {
			e.printStackTrace();
		}		
	}
	
	public void autoCompleteMapUIToDTO() {
		if(taAssureDTO!=null) {
			validateUIField(Const.C_CODE_T_ASSURE,taAssureDTO.getCodeTAssure());
			selectedTaDossierRcdDTO.getTaAssureDTO().setCodeTAssure(taAssureDTO.getCodeTAssure());
		}else selectedTaDossierRcdDTO.getTaAssureDTO().setCodeTAssure(null);
		
		if(taTCiviliteDTO!=null) {
			validateUIField(Const.C_CODE_T_CIVILITE,taTCiviliteDTO.getCodeTCivilite());
			selectedTaDossierRcdDTO.getTaAssureDTO().setCodeTCivilite(taTCiviliteDTO.getCodeTCivilite());
		}else selectedTaDossierRcdDTO.getTaAssureDTO().setCodeTAssure(null);
		
		if(taTJuridiqueDTO!=null) {
			validateUIField(Const.C_CODE_T_JURIDIQUE,taTJuridiqueDTO.getCodeTJuridique());
			selectedTaDossierRcdDTO.getTaAssureDTO().setCodeTJuridique(taTJuridiqueDTO.getCodeTJuridique());
		}else selectedTaDossierRcdDTO.getTaAssureDTO().setCodeTAssure(null);
		
		if(taCourtierDTO!=null) {
			validateUIField(Const.C_CODE_COURTIER,taCourtierDTO.getCodeCourtier());
			selectedTaDossierRcdDTO.setCodeCourtier(taCourtierDTO.getCodeCourtier());
		}else selectedTaDossierRcdDTO.setCodeCourtier(null);
		
//		if(taTAssureDTO!=null) {
//			validateUIField(Const.C_CODE_T_ASSURE,taTAssureDTO.getCodeTAssure());
//			selectedTaDossierRcdDTO.setCodeTAssure(taTAssureDTO.getCodeTAssure());
//		}else selectedTaDossierRcdDTO.setCodeTAssure(null);
		
		//Activité
		try {
			TaActiviteDTO activite;
//			for (TaActiviteDTO activiteChoisi : selectedTaActiviteDTOs) {
			for (TaActiviteDTO activiteChoisi : listeTaActiviteDTODetail) {
				if(selectedTaDossierRcdDTO.getListeActivite()==null) {
					selectedTaDossierRcdDTO.setListeActivite(new ArrayList<TaActiviteDTO>());
				}
				activite = taActiviteService.findByCodeDTO(activiteChoisi.getCodeActivite());
				activite.setPourcentExerceEntreprise(activiteChoisi.getPourcentExerceEntreprise());
				activite.setPourcentSousTraite(activiteChoisi.getPourcentSousTraite());
				activite.setCommentaire(activiteChoisi.getCommentaire());
				selectedTaDossierRcdDTO.getListeActivite().add(activite);
			}
			//supression des activités désélectionnées
			List<TaActiviteDTO> l = new ArrayList<>();
			if(selectedTaDossierRcdDTO.getListeActivite()!=null) {
				for (TaActiviteDTO r : selectedTaDossierRcdDTO.getListeActivite()) { //recherche des activités à supprimer
					if(!selectedTaActiviteDTOs.contains(r)) {
						l.add(r);
					}
				}
				for(TaActiviteDTO a : l) { //supression des activités
					selectedTaDossierRcdDTO.getListeActivite().remove(a);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		//Taux PIB
		try {
			TaTauxRcproPibDTO activite;
			for (TaTauxRcproPibDTO activiteChoisi : selectedTaTauxRcproPibDTOs) {
				if(selectedTaDossierRcdDTO.getListeTauxRcProPib()==null) {
					selectedTaDossierRcdDTO.setListeTauxRcProPib(new ArrayList<TaTauxRcproPibDTO>());
				}
				activite = taTauxRcproPibService.findByCodeDTO(activiteChoisi.getCodeTauxRcproPib());
				selectedTaDossierRcdDTO.getListeTauxRcProPib().add(activite);
			}
			//supression des activités désélectionnées
			List<TaTauxRcproPibDTO> l = new ArrayList<>();
			if(selectedTaDossierRcdDTO.getListeTauxRcProPib()!=null) {
				for (TaTauxRcproPibDTO r : selectedTaDossierRcdDTO.getListeTauxRcProPib()) { //recherche des activités à supprimer
					if(!selectedTaTauxRcproPibDTOs.contains(r)) {
						l.add(r);
					}
				}
				for(TaTauxRcproPibDTO a : l) { //supression des activités
					selectedTaDossierRcdDTO.getListeTauxRcProPib().remove(a);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void autoCompleteMapDTOtoUI() {
		try {
			taAssure = null;
			taTCourtier = null;
			taTCivilite = null;
			taTJuridique = null;
			taTGroupeTarif = null;
			//taCourtier = null;
			taTAssure = null;
			
			taAssureDTO = null;
			taTCourtierDTO = null;
			taTCiviliteDTO = null;
			taTJuridiqueDTO = null;
			taTGroupeTarifDTO = null;
			taCourtierDTO = null;
			taTAssureDTO = null;

			if(selectedTaDossierRcdDTO.getCodeAssure()!=null && !selectedTaDossierRcdDTO.getCodeAssure().equals("")) {
				taAssure = taAssureService.findByCode(selectedTaDossierRcdDTO.getCodeAssure());
				taAssureDTO = taAssureService.findByCodeDTO(selectedTaDossierRcdDTO.getCodeAssure());
				
				mapperModelToUIAssure.map(taAssure, taAssureDTO);
				selectedTaDossierRcdDTO.setTaAssureDTO(taAssureDTO);
			}
			if(selectedTaDossierRcdDTO.getTaAssureDTO().getCodeTAssure()!=null && !selectedTaDossierRcdDTO.getTaAssureDTO().getCodeTAssure().equals("")) {
				taTAssure = taTAssureService.findByCode(selectedTaDossierRcdDTO.getTaAssureDTO().getCodeTAssure());
				taTAssureDTO = taTAssureService.findByCodeDTO(selectedTaDossierRcdDTO.getTaAssureDTO().getCodeTAssure());
			}
			if(selectedTaDossierRcdDTO.getTaAssureDTO().getCodeTCivilite()!=null && !selectedTaDossierRcdDTO.getTaAssureDTO().getCodeTCivilite().equals("")) {
				taTCivilite = taTCiviliteService.findByCode(selectedTaDossierRcdDTO.getTaAssureDTO().getCodeTCivilite());
				taTCiviliteDTO = taTCiviliteService.findByCodeDTO(selectedTaDossierRcdDTO.getTaAssureDTO().getCodeTCivilite());
			}
			if(selectedTaDossierRcdDTO.getTaAssureDTO().getCodeTJuridique()!=null && !selectedTaDossierRcdDTO.getTaAssureDTO().getCodeTJuridique().equals("")) {
				taTJuridique = taTJuridiqueService.findByCode(selectedTaDossierRcdDTO.getTaAssureDTO().getCodeTJuridique());
				taTJuridiqueDTO = taTJuridiqueService.findByCodeDTO(selectedTaDossierRcdDTO.getTaAssureDTO().getCodeTJuridique());
			}
			if(taCourtier!=null) {
//			if(selectedTaDossierRcdDTO.getCodeCourtier()!=null && !selectedTaDossierRcdDTO.getCodeCourtier().equals("")) {
//				taCourtier = taCourtierService.findByCode(selectedTaDossierRcdDTO.getCodeCourtier());
				taCourtierDTO = taCourtierService.findByCodeDTO(taCourtier.getCodeCourtier());
//			}
			}
			
			//Activité
//			selectedTaActiviteDTOs = new ArrayList<TaActiviteDTO>();
			if(taDossierRcd.getTaDevisRcProActivites()!=null && !taDossierRcd.getTaDevisRcProActivites().isEmpty()) {
				//for (TaActivite ru : taDossierRcd.getTaDevisRcProActivites()) {
				for (TaDossierRcdActivite ru : taDossierRcd.getTaDevisRcProActivites()) {
					for (TaActiviteDTO rdto : taActiviteDTODisponible) {
						//if(ru.getIdActivite()==rdto.getId()) {
						//if(ru.getIdContratRcProActivite()==rdto.getId()) {
						if(ru.getActivite().equals(rdto.getCodeActivite())) {
							if(!selectedTaActiviteDTOs.contains(rdto)) {
								rdto.setPourcentExerceEntreprise(ru.getPourcentExerceEntreprise());
								rdto.setPourcentSousTraite(ru.getPourcentSousTraite());
								rdto.setCommentaire(ru.getCommentaire());
								selectedTaActiviteDTOs.add(rdto);
								listeTaActiviteDTODetail.add(rdto);
							}
						}
					}
				}
			}
			
			//Taux PIB
			if(taDossierRcd.getTaDevisRcProTauxPibs()!=null && !taDossierRcd.getTaDevisRcProTauxPibs().isEmpty()) {
				//for (TaActivite ru : taDossierRcd.getTaDevisRcProActivites()) {
				for (TaDossierRcdTauxPib ru : taDossierRcd.getTaDevisRcProTauxPibs()) {
					for (TaTauxRcproPibDTO rdto : taTauxRcproPibDTODisponible) {
						//if(ru.getIdActivite()==rdto.getId()) {
						//if(ru.getIdContratRcProActivite()==rdto.getId()) {
						if(ru.getCodeTauxRcProPib().equals(rdto.getCodeTauxRcproPib())) {
							if(!selectedTaTauxRcproPibDTOs.contains(rdto)) {
								selectedTaTauxRcproPibDTOs.add(rdto);
							}
						}
					}
				}
			}
			
		} catch (FinderException e) {
			e.printStackTrace();
		}
	}

	public void actEnregistrer(ActionEvent actionEvent) {

			try {
				
				autoCompleteMapUIToDTO();
//				donneStatut(selectedTaDossierRcdDTO);
				IDonneStatut Idossier = taDossierRcdService.donneStatut(selectedTaDossierRcdDTO);
				selectedTaDossierRcdDTO.getTaTStatut().clear();
	        	for (TaTStatut stat : Idossier.getTaTStatut()) {
	        		selectedTaDossierRcdDTO.getTaTStatut().add(stat);
				}
				
//				System.out.println("numero avenant  : "+selectedTaDossierRcdDTO.getNumeroAvenant()+" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				mapperUIToModel.map(selectedTaDossierRcdDTO, taDossierRcd);
				if(selectedTaDossierRcdDTO.getLettrePjNumPolice()!= null) {
					taDossierRcd.setLettrePjNumPolice(selectedTaDossierRcdDTO.getLettrePjNumPolice());
				}
				mapperUIToModelAssure.map(selectedTaDossierRcdDTO.getTaAssureDTO(), taDossierRcd.getTaAssure());
						
				mapperUIToModelDetail.map(selectedTaDossierRcdDTO, taDossierRcd);
	
				if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_INSERTION)==0) { //on ne modifie pas le mdp en modification de contrat
					if(taDossierRcd.getTaAssure().getIdAssure()==null ||  taDossierRcd.getTaAssure().getIdAssure()==0) { //on ne modifie pas le mdp d'un assure deja existant
						if(selectedTaDossierRcdDTO.getTaAssureDTO().getPassword()!=null 
								&& !selectedTaDossierRcdDTO.getTaAssureDTO().getPassword().equals("")
								&& selectedTaDossierRcdDTO.getTaAssureDTO().getPassword().equals(selectedTaDossierRcdDTO.getTaAssureDTO().getPasswordConfirm())) {
							//TODO faire la vérification avec le mot de passe de confirmation
							//TODO verifier la complexité minimum
							taDossierRcd.getTaAssure().getTaUtilisateur().setPassword(taDossierRcd.getTaAssure().getTaUtilisateur().passwordHashSHA256_Base64(selectedTaDossierRcdDTO.getTaAssureDTO().getPassword()));
						}else {
							throw new Exception("Mot de passe incomplet ou ne correspond pas à sa confirmation");
						}	
					}
				}
				if(selectedTaDossierRcdDTO.getTaAssureDTO().getAdresseEmail()!=null) {
					if(taDossierRcd.getTaAssure().getTaUtilisateur()!=null) {
					taDossierRcd.getTaAssure().getTaUtilisateur().setIdentifiant(selectedTaDossierRcdDTO.getTaAssureDTO().getAdresseEmail());
					}
				}
				
				if(!assurePib()) {
					if(selectedTaDossierRcdDTO.getListeActivite()!=null 
							&& !selectedTaDossierRcdDTO.getListeActivite().isEmpty()) {
						

						
						
						if(taDossierRcd.getTaDevisRcProActivites()==null) {
							taDossierRcd.setTaDevisRcProActivites(new ArrayList<TaDossierRcdActivite>());
						} else {
							for(TaDossierRcdTauxPib a :taDossierRcd.getTaDevisRcProTauxPibs()) {
								a.setTaDossierRcd(null);
							}
							taDossierRcd.getTaDevisRcProActivites().clear();
						}
						for (TaActiviteDTO r : selectedTaDossierRcdDTO.getListeActivite()) {
							boolean trouve = false;
							for(TaDossierRcdActivite actContrat : taDossierRcd.getTaDevisRcProActivites()) {
								if(actContrat.getActivite().equals(r.getCodeActivite())) {
									trouve = true;
								}
							}
							if(!trouve) {
								//taDossierRcd.getTaDevisRcProActivites().add(taActiviteService.findById(r.getId()));
								TaDossierRcdActivite contratRcProActivite = new TaDossierRcdActivite();
								contratRcProActivite.setActivite(r.getCodeActivite());
								contratRcProActivite.setPourcentExerceEntreprise(r.getPourcentExerceEntreprise());
								contratRcProActivite.setPourcentSousTraite(r.getPourcentSousTraite());
								contratRcProActivite.setCommentaire(r.getCommentaire());
								contratRcProActivite.setTaActivite(taActiviteService.findById(r.getId()));
	//							contratRcProActivite.setClasseAssocie(r.getCodeClasse());
								taDossierRcd.getTaDevisRcProActivites().add(contratRcProActivite);
								contratRcProActivite.setTaDossierRcd(taDossierRcd);
							}
						}
		//				//supression des activités désélectionnées
		//				List<TaDossierRcdActivite> l = new ArrayList<>();
		//				if(taDossierRcd.getTaDevisRcProActivites()!=null) {
		//					for(TaDossierRcdActivite actContrat : taDossierRcd.getTaDevisRcProActivites()) { //recherche des activités à supprimer
		//						boolean trouve = false;
		//						for (TaActiviteDTO r : selectedTaDossierRcdDTO.getListeActivite()) {
		//							if(actContrat.getActivite().equals(r.getCodeActivite())) {
		//								trouve = true;
		//							}
		//							if(!trouve) {
		//								l.add(actContrat);
		//							}
		//						}
		//					}
		//					for(TaDossierRcdActivite a : l) { //supression des activités
		//						taDossierRcd.getTaDevisRcProActivites().remove(a);
		//					}
		//				}
					}
				} else { //PIB
					if(selectedTaDossierRcdDTO.getListeTauxRcProPib()!=null 
							&& !selectedTaDossierRcdDTO.getListeTauxRcProPib().isEmpty()) {
						if(taDossierRcd.getTaDevisRcProTauxPibs()==null) {
							taDossierRcd.setTaDevisRcProTauxPibs(new ArrayList<TaDossierRcdTauxPib>());
						} else {
							taDossierRcd.getTaDevisRcProTauxPibs().clear();
						}
						for (TaTauxRcproPibDTO r : selectedTaDossierRcdDTO.getListeTauxRcProPib()) {
							boolean trouve = false;
							for(TaDossierRcdTauxPib actContrat : taDossierRcd.getTaDevisRcProTauxPibs()) {
								if(actContrat.getCodeTauxRcProPib().equals(r.getCodeTauxRcproPib())) {
									trouve = true;
								}
							}
							if(!trouve) {
								//taDossierRcd.getTaDevisRcProActivites().add(taActiviteService.findById(r.getId()));
								TaDossierRcdTauxPib contratRcProActivite = new TaDossierRcdTauxPib();
								contratRcProActivite.setCodeTauxRcProPib(r.getCodeTauxRcproPib());
								contratRcProActivite.setLiblTauxRcProPib(r.getLiblTauxRcproPib());
			//					contratRcProActivite.setClasseAssocie(r.getCodeClasse());
								taDossierRcd.getTaDevisRcProTauxPibs().add(contratRcProActivite);
								contratRcProActivite.setTaDossierRcd(taDossierRcd);
							}
						}
		//				//supression des activités désélectionnées
	//					List<TaDossierRcdTauxPib> l = new ArrayList<>();
	//					if(taDossierRcd.getTaDevisRcProTauxPibs()!=null) {
	//						for(TaDossierRcdTauxPib actContrat : taDossierRcd.getTaDevisRcProTauxPibs()) { //recherche des activités à supprimer
	//							boolean trouve = false;
	//							for (TaTauxRcproPibDTO r : selectedTaDossierRcdDTO.getListeTauxRcProPib()) {
	//								if(actContrat.getCodeTauxRcProPib().equals(r.getCodeTauxRcproPib())) {
	//									trouve = true;
	//								}
	//								if(!trouve) {
	//									l.add(actContrat);
	//								}
	//							}
	//						}
	//						for(TaDossierRcdTauxPib a : l) { //supression des activités
	//							taDossierRcd.getTaDevisRcProTauxPibs().remove(a);
	//						}
	//					}
					}
				}
				
				if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_INSERTION)==0) {
					this.setListeTaGedUtilisateurDTO(gedContratRcProController.getListTaGedUtilisateurDTO());
					//boucler ici pour transformer ma liste de dto en entity
//					listeTaGedUtilisateur = new ArrayList<TaGedUtilisateur>();
					if(taDossierRcd.getTaGedUtilisateur()!=null) {
						taDossierRcd.getTaGedUtilisateur().clear();
					} else {
						taDossierRcd.setTaGedUtilisateur(new ArrayList<TaGedUtilisateur>());
					}
					
					TaGedUtilisateur gedUtilisateur = null;
					for (TaGedUtilisateurDTO ged : listeTaGedUtilisateurDTO) {
						gedUtilisateur = new TaGedUtilisateur();
						mapperUIToModelGedUtilisateur.map(ged, gedUtilisateur);
						
						//listeTaGedUtilisateur.add(gedUtilisateur);
						gedUtilisateur.setTaDossierRcd(taDossierRcd);
						gedUtilisateur.setTaListeRefDoc(taListeRefDocService.findById(ged.getIdListeRefDoc()));
						taDossierRcd.getTaGedUtilisateur().add(gedUtilisateur);
						
					}
					//taDossierRcd.setTaGedUtilisateur(listeTaGedUtilisateur);
				}
				taDossierRcd.getTaTStatut().clear();
				if(selectedTaDossierRcdDTO.getTaTStatut()!=null 
						&& !selectedTaDossierRcdDTO.getTaTStatut().isEmpty()) {
					
		            for (TaTStatut s : selectedTaDossierRcdDTO.getTaTStatut()) {
		            	taDossierRcd.getTaTStatut().add(s);
					}
				}
				
				
				taDossierRcd = taDossierRcdService.merge(taDossierRcd,ITaDossierRcdServiceRemote.validationContext);
				if(taSousDonnee.getTaDossierRcd().getIdDossierRcd()==null) {
					taSousDonnee.setTaDossierRcd(taDossierRcd);
				}
				taSousDonnee = taSousDonneeService.merge(taSousDonnee);
				
				if(selectedTaDossierRcdDTO.getNumDossierPolice()!=null) {
					taDossierRcdService.annuleCode(selectedTaDossierRcdDTO.getNumDossierPolice());
				}
				
				mapperModelToUI.map(taDossierRcd, selectedTaDossierRcdDTO);
				if(assurePib()) {
					selectedTaDossierRcdDTO.getListeTauxRcProPib().clear();
					
					for(TaDossierRcdTauxPib actEntité : taDossierRcd.getTaDevisRcProTauxPibs()) {
						TaTauxRcproPibDTO actDTO = new TaTauxRcproPibDTO();
						actDTO.setCodeTauxRcproPib(actEntité.getCodeTauxRcProPib());
						if(actEntité.getTaTauxRcproPib()!= null && actEntité.getTaTauxRcproPib().getDescriptionActivite()!=null) {
							actDTO.setDescriptionActivite(actEntité.getTaTauxRcproPib().getDescriptionActivite());
							actDTO.setTauxRcproPib(actEntité.getTauxRcProPib());
						}
						
						actDTO.setLiblTauxRcproPib(actEntité.getLiblTauxRcProPib());
						
						selectedTaDossierRcdDTO.getListeTauxRcProPib().add(actDTO);
					}
				}
				
				selectedTaDossierRcdDTO.getTaTStatut().clear();
				if(taDossierRcd.getTaTStatut()!=null 
						&& !taDossierRcd.getTaTStatut().isEmpty()) {
					
		            for (TaTStatut s : taDossierRcd.getTaTStatut()) {
		            	selectedTaDossierRcdDTO.getTaTStatut().add(s);
					}
				}
				autoCompleteMapDTOtoUI();
				
				if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_INSERTION)==0) {
					values.add(selectedTaDossierRcdDTO);
					selectedTaDossierRcdDTOs = null;
					
					//pour activer l'onglet ged rcd suite au 1er enregistrement
					gedContratRcProController.setMasterEntity(taDossierRcd);
					gedContratRcProController.refresh();
					
				}
	//			adresseController.refresh(null);
				modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
	
			} catch(Exception e) {
				e.printStackTrace();
				FacesContext context = FacesContext.getCurrentInstance();  
				//context.addMessage(null, new FacesMessage("Courtier", "actEnregistrer")); 
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contrat Rcd", e.getMessage()));
			
			}
//		}else {
//			FacesContext context = FacesContext.getCurrentInstance();  
//			context.addMessage(null, new FacesMessage("Assure", "Le mot de passe est différent de sa confirmation"));
//		}

		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Contrat Rcd", "actEnregistrer"));
		}
	}

	public void actInserer(ActionEvent actionEvent) {
		try {
			selectedTaDossierRcdDTOs= new TaDossierRcdDTO[]{};
			selectedTaDossierRcdDTO = new TaDossierRcdDTO();
			taDossierRcd = new TaDossierRcd();

//			String codeTCourtierDefaut = TaTCourtier.COURTIER_ASSURANCE;
			if(taCourtier!=null) {
				taDossierRcd.setTaCourtier(taCourtier);
				selectedTaDossierRcdDTO.setCodeCourtier(taCourtier.getCodeCourtier());
			}

			taGenCodeExService.libereVerrouTout(new ArrayList<String>(SessionListener.getSessions().keySet()));
			
			
			
//			validateUIField(Const.C_CODE_COURTIER,selectedTaDossierRcdDTO.getNumContrat());//permet de verrouiller le code genere
			
			listeTaActiviteDTODetail = new ArrayList<>();
			selectedTaActiviteDTOs = new ArrayList<>();

			
			selectedTaDossierRcdDTO.setCodeTReglement(ConstWeb.codeTReglementDefaut);
			selectedTaDossierRcdDTO.setCodeTSousTraitance(ConstWeb.codeTSousTraitanceDefaut);
			selectedTaDossierRcdDTO.setCodeFranchise(ConstWeb.codeFranchiseDefaut);
			selectedTaDossierRcdDTO.setCodeEcheance(ConstWeb.codeTEcheanceDefaut);
			
			selectedMontantMarcheTravauxHTMin = MONTANT_MARCHE_TRAVAUX_HT_MIN_50_000;
			actRadioMontantMarcheTravauxHTMin();
			taDossierRcd.setMontantTravauxHtMax50k(true);
//			selectedTaDossierRcdDTO.setMontantTravauxHtMax50k(true);
////			initRadioMontantMarcheTravauxHTMin();
			
//			selectedInteruptionAssurance = INTERUPTION_ASSURANCE_MOINS_6_MOIS;
			selectedInteruptionAssurance = INTERUPTION_ASSURANCE_0_2_ANS;
			
			selectedAntecedantRCDEnCours = ANTECEDANT_RCD_EN_COURS;
			selectedAntecedantRCEEnCours = ANTECEDANT_RCE_EN_COURS;
			
			selectedAntecedantRCDResilieParAssure = ANTECEDANT_RCD_RESILIE_PAR_ASSURE;
			selectedAntecedantRCEResilieParAssure = ANTECEDANT_RCE_RESILIE_PAR_ASSURE;
			selectedAntecedantMotifRCDAssure = MOTIF_RESILIATION_ASSURE_ECHEANCE;
			selectedAntecedantMotifRCEAssure = MOTIF_RESILIATION_ASSURE_ECHEANCE;
			selectedReprisePasse = REPRISE_PASSE_MOINS_3_MOIS;
			
			TaTSousTraitance st = taTSousTraitanceService.findByCode(selectedTaDossierRcdDTO.getCodeTSousTraitance());
			taDossierRcd.setTaTSousTraitance(st);
			
			TaTReglement r = taTReglementService.findByCode(selectedTaDossierRcdDTO.getCodeTReglement());
			taDossierRcd.setTaTReglement(r);
			
			TaTEcheance e = taTEcheanceService.findByCode(selectedTaDossierRcdDTO.getCodeEcheance());
			taDossierRcd.setTaTEcheance(e);
			
//			TaTFranchise f = taTFranchiseService.findByCode(selectedTaDossierRcdDTO.getCodeFranchise());
//			taDossierRcd.setTaTFranchise(f);
			
			selectedTaDossierRcdDTO.setTauxCommission(BigDecimal.ZERO);
			selectedTaDossierRcdDTO.setFraisRcPro(BigDecimal.ZERO);
			
			selectedTaDossierRcdDTO.setCaTotalHtExerciceNMoins1(BigDecimal.ZERO);
			selectedTaDossierRcdDTO.setCaTotalHtExerciceNMoins2(BigDecimal.ZERO);
			
			//Création d'un TaSousDonnee pour ce nouveau dossier
			taSousDonnee = new TaSousDonnee();
			taSousDonnee.setTaDossierRcd(taDossierRcd);
			//initialisation des champs interruption assurance
			JSONObject jsonData;
			if(taSousDonnee.getJsonData()!=null) {
				 jsonData = new JSONObject(taSousDonnee.getJsonData());
			}else {
				 jsonData = new JSONObject();
			}
			jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_0_A_2_ANS, TaSousDonnee.VALEUR_FALSE);
			jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_2_A_5_ANS, TaSousDonnee.VALEUR_FALSE);
			jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_5_A_7_ANS, TaSousDonnee.VALEUR_FALSE);
			jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_PLUS_7_ANS, TaSousDonnee.VALEUR_FALSE);
			taSousDonnee.setJsonData(jsonData.toString());
			
			HashMap<String, String> map = new HashMap<>(); 
			
			if(assurePib()) {// SI PIB
				map.put("PIB", "true");
//				listeTaTFranchise.clear();
//				listeTaTFranchise.add(fran2000);
//				listeTaTFranchise.add(fran5000);
			}else {
				map.put("PIB", "false");	
			}
			//va mettre Z derriere numPolice si PIB, S si non PIB (voir classe GenCodeExDao ligne 346)
			selectedTaDossierRcdDTO.setNumDossierPolice(taDossierRcdService.genereCode(map)); //ejb
			
			
			autoCompleteMapDTOtoUI();
			initRadioFranchise();
			
			modeEcran.setMode(EnumModeObjet.C_MO_INSERTION);
			scrollToTop();
			
			actInsererAvenant(actionEvent);
			
			if(ConstWeb.DEBUG) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage("Contrat Rcd", "actInserer"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actInsererAvenant(ActionEvent actionEvent) {
		try {
//			taDossierRcd = new TaDossierRcd();
//			if(taDossierRcd.getTaContratRcProDetails()==null) {
//				taDossierRcd.setTaContratRcProDetails(new ArrayList<TaDossierRcd>());
//			}
//			taDossierRcd.getTaContratRcProDetails().add(taDossierRcd);
//			taDossierRcd.setTaContratRcPro(taDossierRcd);
//			selectedTaDossierRcdDTO = new TaDossierRcdDTO();
			
			if(taAssureParamInsertion==null) {
				taAssure = new TaAssure();
				taDossierRcd.setTaAssure(taAssure);
				
				////
				//taAssure = new TaAssure();
		
				if(taTAssureParamInsertion!=null) {
					//String codeTAssureDefaut = TaTAssure.ENTREPRISE_DU_BATIMENT;
					taAssure.setTaTAssure(taTAssureParamInsertion);
					String codeTAssureDefaut = taTAssureParamInsertion.getCodeTAssure();
					selectedTaDossierRcdDTO.getTaAssureDTO().setCodeTAssure(codeTAssureDefaut);
					validateUIField(Const.C_CODE_T_ASSURE,taTAssureParamInsertion.getCodeTAssure());
					taTAssureDTO = taTAssureService.findByCodeDTO(selectedTaDossierRcdDTO.getTaAssureDTO().getCodeTAssure());
					taTAssureParamInsertion = null;
				}
		
				taGenCodeExService.libereVerrouTout(new ArrayList<String>(SessionListener.getSessions().keySet()));
				selectedTaDossierRcdDTO.getTaAssureDTO().setCodeAssure(taAssureService.genereCode(null)); //ejb
				validateUIField(Const.C_CODE_COURTIER,selectedTaDossierRcdDTO.getTaAssureDTO().getCodeAssure());//permet de verrouiller le code genere
		
				
		
//				TaTAssure taTAssure;
//				taTAssure = taTAssureService.findByCode(codeTAssureDefaut);
				
				TaUtilisateur taUtilisateur = new TaUtilisateur();
				TaTUtilisateur taTUtilisateur = null; 
				taTUtilisateur = taTUtilisateurService.findByCode(TaTUtilisateur.TYPE_UTILISATEUR_ASSURE);
				taUtilisateur.setTaTUtilisateur(taTUtilisateur);
				taUtilisateur.setAdmin(false);
				taUtilisateur.setSysteme(false);
				taAssure.setTaUtilisateur(taUtilisateur);
				
				if(taCourtier!=null) {
					taAssure.setTaCourtier(taCourtier);
//					taAssure.setCodeAssure(taCourtier.getCodeCourtier());
				}
			} else {
				taAssure = taAssureParamInsertion;
				taDossierRcd.setTaAssure(taAssure);
				taAssureParamInsertion = null;
				//mapperModelToUIAssure.map(taAssure, selectedTaDossierRcdDTO.getTaAssureDTO());
			}
			/////
			
			
			mapperModelToUI.map(taDossierRcd, selectedTaDossierRcdDTO);	
			autoCompleteMapDTOtoUI();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void actCreerAvenant(ActionEvent actionEvent) {
		try {

//			listeAvenantTaDossierRcdDTO
			listeAvenantTaDossierRcdDTO = taDossierRcdService.findAllAvenantByNumPolice(taDossierRcd.getNumDossierPolice());
			if(taDossierRcd.getContrat()!=null && taDossierRcd.getContrat()) {
				int numeroAvenant = 1;
				if(listeAvenantTaDossierRcdDTO!=null && !listeAvenantTaDossierRcdDTO.isEmpty()) {
					numeroAvenant = listeAvenantTaDossierRcdDTO.size()+1;
				} 
				TaDossierRcd avenant = taDossierRcdService.genereAvenant(taDossierRcd);
				avenant.setNumeroAvenant(numeroAvenant);
				
				avenant = taDossierRcdService.merge(avenant,ITaDossierRcdServiceRemote.validationContext);
				
				LgrTab b = new LgrTab();  //ouvre un nouvel onglet devis
				b.setTypeOnglet(LgrTab.TYPE_TAB_DEVIS_RCPRO);
				b.setTitre("Avenant RCD");
				b.setTemplate("assurance/devis_rcd.xhtml");
				b.setIdTab(LgrTab.ID_TAB_DEVIS_RCPRO);
				b.setStyle(LgrTab.CSS_CLASS_TAB_DEVIS_RCPRO);
//				b.setParamId(LibConversion.integerToString(selectedTaDossierRcdDTO.getId()));

				tabsCenter.ajouterOnglet(b);
				tabViews.selectionneOngletCentre(b);
				
				
				
				
				devisRcdController.setTaDossierRcd(avenant);
				TaDossierRcdDTO dto = new TaDossierRcdDTO();
				mapperModelToUI.map(avenant, dto);

				devisRcdController.setSelectedTaDossierRcdDTO(dto);
				devisRcdController.refresh(false);
				devisRcdController.updateTab();
				
				envoiMailTestService.envoiMailEnregistrementDevisAvenant(avenant.getTaCourtier(), avenant);
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void actGenererContrat(ActionEvent actionEvent) {
		
		if(taDossierRcd.getContrat()!=null && !taDossierRcd.getContrat()) { //ce n'est pas deja un contrat
			taDossierRcd.setContrat(false);
			taDossierRcd.setSoumisSouscription(true);
			taDossierRcd.setDateSouscription(new Date());
			
			/////////////////////////////////////
			//généré les échéances à payer
			/////////////////////////////////////
			taDossierRcd.setTaEcheances(new ArrayList<TaEcheance>());
			//1ère échéance
			taDossierRcd.getMontantPremierReglement();
			//échéance suivantes
			taDossierRcd.getMontantPrime();
			
			Date dateDebEcheance;
			Date dateFinEcheance;
			LocalDate localDateDebEch = selectedTaDossierRcdDTO.getDateEffet().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate localDateFinEch = null;

			if(taDossierRcd.getTaTEcheance().getCodeTEcheance().equals(TaTEcheance.ANNUEL)) {
				//1 echeance
				dateDebEcheance = taDossierRcd.getDateEffet();
				dateFinEcheance = taDossierRcd.getDateEcheance();
//				dateDebEcheance = taDossierRcd.getDateDebutPremierePeriode();
//				dateFinEcheance = taDossierRcd.getDateFinPremierePeriode();
				
				TaEcheance e = new TaEcheance();
				e.setCodeEcheance(taEcheanceService.genereCode(null));
				e.setDateDebutEcheance(dateDebEcheance);
				e.setDateEcheance(dateFinEcheance);
				e.setMontantEcheance(taDossierRcd.getMontantPremierReglement());
				e.setMontantFraisDeDossier(taDossierRcd.getFraisRcPro());
				e.setMontantCommission(taDossierRcd.getMontantCommissionCourtierUneEcheance());
				e.setMontantSurCommission(taDossierRcd.getMontantSurCommissionCourtier());
				e.setTaTEcheance(taDossierRcd.getTaTEcheance());
				e.setTaTReglement(taDossierRcd.getTaTReglement());
				e.setTaDossierRcd(taDossierRcd);
				taDossierRcd.getTaEcheances().add(e);
				
			} else if(taDossierRcd.getTaTEcheance().getCodeTEcheance().equals(TaTEcheance.SEMESTRIEL)) {
				//2 echeances
				int nbMois = 6;
				//*1*
				dateDebEcheance = new Date(taDossierRcd.getDateDebutPremierePeriode().getTime());
				localDateDebEch = dateDebEcheance.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				dateFinEcheance = Date.from(LocalDate.of(localDateDebEch.getYear(), localDateDebEch.getMonthValue(),localDateDebEch.getDayOfMonth()).plus(nbMois, ChronoUnit.MONTHS).minus(1, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault()).toInstant());
				localDateFinEch = dateFinEcheance.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				TaEcheance e = new TaEcheance();
				e.setCodeEcheance(taEcheanceService.genereCode(null));
				e.setDateDebutEcheance(dateDebEcheance);
				e.setDateEcheance(dateFinEcheance);
				e.setMontantEcheance(taDossierRcd.getMontantPremierReglement());
				e.setMontantFraisDeDossier(taDossierRcd.getFraisRcPro());
				e.setMontantCommission(taDossierRcd.getMontantCommissionCourtierUneEcheance());
				e.setMontantSurCommission(taDossierRcd.getMontantSurCommissionCourtier());
				e.setTaTEcheance(taDossierRcd.getTaTEcheance());
				e.setTaTReglement(taDossierRcd.getTaTReglement());
				e.setTaDossierRcd(taDossierRcd);
				taDossierRcd.getTaEcheances().add(e);
				
				//*2*
				dateDebEcheance = Date.from(localDateFinEch.plus(1, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault()).toInstant());
				localDateDebEch = dateDebEcheance.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				dateFinEcheance = Date.from(LocalDate.of(localDateDebEch.getYear(), localDateDebEch.getMonthValue(),localDateDebEch.getDayOfMonth()).plus(nbMois, ChronoUnit.MONTHS).atStartOfDay(ZoneId.systemDefault()).toInstant());
				e = new TaEcheance();
				e.setCodeEcheance(taEcheanceService.genereCode(null));
				e.setDateDebutEcheance(dateDebEcheance);
				e.setDateEcheance(taDossierRcd.getDateEcheance());
				e.setMontantEcheance(taDossierRcd.getMontantPrime());
//				e.setMontantFraisDeDossier(taDossierRcd.getFraisRcPro());
				e.setMontantCommission(taDossierRcd.getMontantCommissionCourtierUneEcheance());
				e.setMontantSurCommission(taDossierRcd.getMontantSurCommissionCourtier());
				e.setTaTEcheance(taDossierRcd.getTaTEcheance());
				e.setTaTReglement(taDossierRcd.getTaTReglement());
				e.setTaDossierRcd(taDossierRcd);
				taDossierRcd.getTaEcheances().add(e);
			} else if(taDossierRcd.getTaTEcheance().getCodeTEcheance().equals(TaTEcheance.TRIMESTRIEL)) {
				//4 echeances
				int nbMois = 3;
				//*1*
				dateDebEcheance = new Date(taDossierRcd.getDateDebutPremierePeriode().getTime());
				localDateDebEch = dateDebEcheance.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				dateFinEcheance = Date.from(LocalDate.of(localDateDebEch.getYear(), localDateDebEch.getMonthValue(),localDateDebEch.getDayOfMonth()).plus(nbMois, ChronoUnit.MONTHS).minus(1, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault()).toInstant());
				localDateFinEch = dateFinEcheance.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				TaEcheance e = new TaEcheance();
				e.setCodeEcheance(taEcheanceService.genereCode(null));
				e.setDateDebutEcheance(taDossierRcd.getDateEffet());
				e.setDateEcheance(dateFinEcheance);
				e.setMontantEcheance(taDossierRcd.getMontantPremierReglement());
				e.setMontantFraisDeDossier(taDossierRcd.getFraisRcPro());
				e.setMontantCommission(taDossierRcd.getMontantCommissionCourtierUneEcheance());
				e.setMontantSurCommission(taDossierRcd.getMontantSurCommissionCourtier());
				e.setTaTEcheance(taDossierRcd.getTaTEcheance());
				e.setTaTReglement(taDossierRcd.getTaTReglement());
				e.setTaDossierRcd(taDossierRcd);
				taDossierRcd.getTaEcheances().add(e);
				
				//*2*
				dateDebEcheance = Date.from(localDateFinEch.plus(1, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault()).toInstant());
				localDateDebEch = dateDebEcheance.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				dateFinEcheance = Date.from(LocalDate.of(localDateDebEch.getYear(), localDateDebEch.getMonthValue(),localDateDebEch.getDayOfMonth()).plus(nbMois, ChronoUnit.MONTHS).minus(1, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault()).toInstant());
				localDateFinEch = dateFinEcheance.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				e = new TaEcheance();
				e.setCodeEcheance(taEcheanceService.genereCode(null));
				e.setDateDebutEcheance(dateDebEcheance);
				e.setDateEcheance(dateFinEcheance);
				e.setMontantEcheance(taDossierRcd.getMontantPrime());
//				e.setMontantFraisDeDossier(taDossierRcd.getFraisRcPro());
				e.setMontantCommission(taDossierRcd.getMontantCommissionCourtierUneEcheance());
				e.setMontantSurCommission(taDossierRcd.getMontantSurCommissionCourtier());
				e.setTaTEcheance(taDossierRcd.getTaTEcheance());
				e.setTaTReglement(taDossierRcd.getTaTReglement());
				e.setTaDossierRcd(taDossierRcd);
				taDossierRcd.getTaEcheances().add(e);
				
				//*3*
				dateDebEcheance = Date.from(localDateFinEch.plus(1, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault()).toInstant());
				localDateDebEch = dateDebEcheance.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				dateFinEcheance = Date.from(LocalDate.of(localDateDebEch.getYear(), localDateDebEch.getMonthValue(),localDateDebEch.getDayOfMonth()).plus(nbMois, ChronoUnit.MONTHS).minus(1, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault()).toInstant());
				localDateFinEch = dateFinEcheance.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				e = new TaEcheance();
				e.setCodeEcheance(taEcheanceService.genereCode(null));
				e.setDateDebutEcheance(dateDebEcheance);
				e.setDateEcheance(dateFinEcheance);
				e.setMontantEcheance(taDossierRcd.getMontantPrime());
//				e.setMontantFraisDeDossier(taDossierRcd.getFraisRcPro());
				e.setMontantCommission(taDossierRcd.getMontantCommissionCourtierUneEcheance());
				e.setMontantSurCommission(taDossierRcd.getMontantSurCommissionCourtier());
				e.setTaTEcheance(taDossierRcd.getTaTEcheance());
				e.setTaTReglement(taDossierRcd.getTaTReglement());
				e.setTaDossierRcd(taDossierRcd);
				taDossierRcd.getTaEcheances().add(e);
				
				//*4*
				dateDebEcheance = Date.from(localDateFinEch.plus(1, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault()).toInstant());
				localDateDebEch = dateDebEcheance.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				dateFinEcheance = Date.from(LocalDate.of(localDateDebEch.getYear(), localDateDebEch.getMonthValue(),localDateDebEch.getDayOfMonth()).plus(nbMois, ChronoUnit.MONTHS).minus(1, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault()).toInstant());
				localDateFinEch = dateFinEcheance.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				e = new TaEcheance();
				e.setCodeEcheance(taEcheanceService.genereCode(null));
				e.setDateDebutEcheance(dateDebEcheance);
				e.setDateEcheance(taDossierRcd.getDateEcheance());
				e.setMontantEcheance(taDossierRcd.getMontantPrime());
//				e.setMontantFraisDeDossier(taDossierRcd.getFraisRcPro());
				e.setMontantCommission(taDossierRcd.getMontantCommissionCourtierUneEcheance());
				e.setMontantSurCommission(taDossierRcd.getMontantSurCommissionCourtier());
				e.setTaTEcheance(taDossierRcd.getTaTEcheance());
				e.setTaTReglement(taDossierRcd.getTaTReglement());
				e.setTaDossierRcd(taDossierRcd);
				taDossierRcd.getTaEcheances().add(e);
			}			
			
			taDossierRcd = taDossierRcdService.merge(taDossierRcd,ITaDossierRcdServiceRemote.validationContext);
		}
	}
	
	
	
	public void actModifier() {
		actModifier(null);
	}

	public void actModifier(ActionEvent actionEvent) {
		
		modeEcran.setMode(EnumModeObjet.C_MO_EDITION);

		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Contrat Rcd", "actModifier"));
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
			context.addMessage(null, new FacesMessage("Contrat Rcd", "actAide"));
		}
	}
    
    public void nouveau(ActionEvent actionEvent) {  
    	LgrTab b = new LgrTab();
    	b.setTypeOnglet(LgrTab.TYPE_TAB_CONTRAT_RCPRO);
		b.setTitre("Contrat RCD");
		b.setTemplate("assurance/contrat_rcd.xhtml");
		b.setIdTab(LgrTab.ID_TAB_CONTRAT_RCPRO);
		b.setStyle(LgrTab.CSS_CLASS_TAB_CONTRAT_RCPRO);
		b.setParamId("0");
		
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
		actInserer(actionEvent);
		
		if(ConstWeb.DEBUG) {
	    	FacesContext context = FacesContext.getCurrentInstance();  
		    context.addMessage(null, new FacesMessage("Contrat Rcd", 
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
		TaDossierRcd taCourtier = new TaDossierRcd();
		try {
			if(selectedTaDossierRcdDTO!=null && selectedTaDossierRcdDTO.getId()!=null){
				taCourtier = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
			}

			taDossierRcdService.remove(taCourtier);
			values.remove(selectedTaDossierRcdDTO);
			Date now = new Date();
			String nowString;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à hh:mm:ss");
			nowString = dateFormat.format(now);

			
			
			
			System.out.println("******SUPPRESSION***********SUPPRESSION*********SUPPRESSION********** SUPPRESSION DOSSIER id:"+taDossierRcd.getIdDossierRcd()+" numPolice : "+taDossierRcd.getNumDossierPolice()+" le "+nowString+" , Pour l'assuré "+taDossierRcd.getTaAssure().getCodeAssure()+" "+taDossierRcd.getTaAssure().getRaisonSociale()+" depuis le ContratRCDController****************");
			
			
			if(!values.isEmpty()) {
				selectedTaDossierRcdDTO = values.get(0);
			}else {
				selectedTaDossierRcdDTO=new TaDossierRcdDTO();
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
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contrat Rcd", e.getCause().getCause().getCause().getCause().getMessage()));
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
		selectedTaDossierRcdDTO=new TaDossierRcdDTO();
		selectedTaDossierRcdDTOs=new TaDossierRcdDTO[]{selectedTaDossierRcdDTO};

	}
	
	public void actChangementPaiement() {
		System.out.println("Adresse chantier"+selectedTaAttestationNominative.getAdresseChantier());
		System.out.println("ContratRcdController.actChangementPaiement()");
	}
	
	public void actChangementEcheance() {
		System.out.println("ContratRcdController.actChangementEcheance()");
	}
	
	public void actChangementPartSousTraitance() {
		System.out.println("ContratRcdController.actChangementPartSousTraitance()");
	}
	
	public void actChangementFranchise() {
		System.out.println("ContratRcdController.actChangementEcheance()");
	}
	
	public void actChangementRedressementJudiciaire() {
		if(selectedTaDossierRcdDTO.getSinistraliteLiquidationSocieteDemandeuse()) {
			selectedTaDossierRcdDTO.setCodeEcheance(TaTEcheance.ANNUEL);
			try {
				TaTEcheance entity = taTEcheanceService.findByCode(TaTEcheance.ANNUEL);
				taDossierRcd.setTaTEcheance(entity);
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void actChangementRedressementJudiciaireAutre() {
		if(selectedTaDossierRcdDTO.getSinistraliteLiquidationAutreSociete()) {
			selectedTaDossierRcdDTO.setCodeEcheance(TaTEcheance.ANNUEL);
			try {
				TaTEcheance entity = taTEcheanceService.findByCode(TaTEcheance.ANNUEL);
				taDossierRcd.setTaTEcheance(entity);
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void actChangementAntecedantResilieNonPaiement() {
		//Si assure meme risque, resilier assureur et pour non paiement
		if( selectedTaDossierRcdDTO.getAssureMemeRisque()
						&& selectedTaDossierRcdDTO.getAssureMemeRisqueRcd()!=null && selectedTaDossierRcdDTO.getAssureMemeRisqueRcd()
						&& selectedAntecedantRCDEnCours.equals(ANTECEDANT_RCD_RESILIE) 
						&& selectedAntecedantRCDResilieParAssureur.equals(ANTECEDANT_RCD_RESILIE_PAR_ASSUREUR) 
						&& selectedAntecedantMotifRCDAssureur.equals(MOTIF_RESILIATION_ASSUREUR_NON_PAIEMENT)){
			
			selectedTaDossierRcdDTO.setCodeEcheance(TaTEcheance.ANNUEL);//On bloque le fractionnement à annuel uniquement
			try {
				TaTEcheance entity = taTEcheanceService.findByCode(TaTEcheance.ANNUEL);
				taDossierRcd.setTaTEcheance(entity);
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void actChangementDejaAssureMemeRisque() {
		if( !selectedTaDossierRcdDTO.getAssureMemeRisque()) {//si l'assuré n'a pas été assuré même risque
			selectedTaDossierRcdDTO.setReprisePasse(false);//pas de reprise passe
		}
	}
	
	public void actChangementAntecedantDateResiliation() {
		if( selectedTaDossierRcdDTO.getAssureMemeRisque()) {//si l'assuré a  été assuré même risque et qu'il a rentré une date de résiliation
			//date reprise passé est égale à la date de résiliation antécédant
			if(isDateReprisePasseSuperieur12Mois()) {// si date de résiliation date de plus de 12 mois
//				selectedTaDossierRcdDTO.setReprisePasse(false);//pas de reprise passe
				Date plus1ans = new Date();
				//la date d'effet est de max 1 ans
				selectedTaDossierRcdDTO.getAntecedentRcdDateResiliation();
				Calendar c = Calendar.getInstance();
				c.setTime(plus1ans);
				c.add(Calendar.YEAR, -1);
				plus1ans = c.getTime();
				selectedTaDossierRcdDTO.setDateRepriseDuPasse(plus1ans);
				
			}else {
				selectedTaDossierRcdDTO.setDateRepriseDuPasse(selectedTaDossierRcdDTO.getAntecedentRcdDateResiliation());
			}
			
			
			
		}
	}
	public Boolean isDateReprisePasseSuperieur12Mois() {
		Boolean estSuperieur = false;
		Date now = new Date();
		LocalDateTime localDateTimePlus12Mois = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		localDateTimePlus12Mois = localDateTimePlus12Mois.minusMonths(12);
		
		LocalDateTime DateResil = selectedTaDossierRcdDTO.getAntecedentRcdDateResiliation().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		if(DateResil.isBefore(localDateTimePlus12Mois)) {
			estSuperieur = true;
			System.out.println(DateResil+" est avant "+localDateTimePlus12Mois+" donc on bloque le bouton reprise passé car plus de 12 mois" );
		}
		return estSuperieur;
		
	}

	public void actImprimer(ActionEvent actionEvent) {
//		try {
//			String pdf = editionService.genereAttestationAssurancePDF(taDossierRcd.getIdDossierRcd(), null);
//			
//			//Enfin on ouvre un onglet pour afficher le PDF
//			try {
//				String urlEncoded = URLEncoder.encode(pdf, "UTF-8");
//				//PrimeFaces.current().executeScript("window.open('/edition?fichier="+urlEncoded+"')");
//				RequestContext.getCurrentInstance().execute("window.open('/edition?fichier="+urlEncoded+"')");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
			
			if(selectedTaDossierRcdDTO.getDateDevis()==null) {
				selectedTaDossierRcdDTO.setDateDevis(new Date()); //date fixé au moment de la génération du PDF et non modifiable
				actEnregistrer(null);
			}

		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		sessionMap.put("contratrcpro", taDossierRcdService.findById(taDossierRcd.getIdDossierRcd()));
//		sessionMap.put("tarifpj", taTauxAssuranceService.findByCode(TaTauxAssurance.MONTANT_PROTECTION_JURIDIQUE));
		sessionMap.put("fraisgestion", taTFraisImpayeService.findByCode(TaTFraisImpaye.FRAIS_DOSSIER_YLYADE));
		sessionMap.put("fraisgestionpib", taTFraisImpayeService.findByCode(TaTFraisImpaye.FRAIS_DOSSIER_PIB));

		sessionMap.put("activitercpro", taActiviteService.selectAll());
		List<TaTauxRcproPib> listePib = new ArrayList<TaTauxRcproPib>();
		for (TaDossierRcdTauxPib pibEntite : taDossierRcd.getTaDevisRcProTauxPibs()) {
			TaTauxRcproPib pib = taTauxRcproPibService.findByCode(pibEntite.getCodeTauxRcProPib());
			listePib.add(pib);
		}
		System.out.println(listePib);
		sessionMap.put("listePib", listePib);
		
		
		new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));

			System.out.println("CourtierController.actImprimer()");
		} catch (FinderException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
//	public void actImprimerSinistralite(ActionEvent actionEvent) {
//		//a faire
//	} 
	
	public void calculeDureeInterruptionAssurance(SelectEvent e) {
		calculeDureeInterruptionAssurance();
	}
	
	public void calculeDureeInterruptionAssurance(ActionEvent e) {
		calculeDureeInterruptionAssurance();
	}
	
	public void calculeDureeInterruptionAssurance(AjaxBehaviorEvent e) {
		calculeDureeInterruptionAssurance();
	}
	
	public void calculeDureeInterruptionAssurance() {
//		selectedInteruptionAssurance = INTERUPTION_ASSURANCE_MOINS_6_MOIS;
		selectedInteruptionAssurance = INTERUPTION_ASSURANCE_0_2_ANS;
		
		LocalDate localDateVerif = null;
		LocalDate localDateMaintenant= new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		if(!selectedTaDossierRcdDTO.getAssureMemeRisque()) { 
			selectedTaDossierRcdDTO.setAssureMemeRisqueRcd(false);
			selectedTaDossierRcdDTO.setAssureMemeRisqueRce(false);
			//pas d'assurance avant, donc on calcule par rapport à la date de creation d'entreprise
			if(selectedInteruptionAssurance!=null && selectedTaDossierRcdDTO.getTaAssureDTO().getDateCreation()!=null) {
				LocalDate localDateCreationEts = selectedTaDossierRcdDTO.getTaAssureDTO().getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				localDateVerif = localDateCreationEts;
			}
		} else {
			//deja assuré, donc si toujours en cours (pas de majoration ==> -6mois, sinon calcul depuis la date de resiliation
			LocalDate localDateResiliationRcd = null;
			LocalDate localDateResiliationRce = null;
			//if(selectedTaDossierRcdDTO.getAntecedentRcdContratResilie() && selectedTaDossierRcdDTO.getAntecedentRcdDateResiliation()!=null) {
			if(selectedTaDossierRcdDTO.getAssureMemeRisqueRcd() && selectedAntecedantRCDEnCours.equals(ANTECEDANT_RCD_RESILIE) && selectedTaDossierRcdDTO.getAntecedentRcdDateResiliation()!=null) {
				localDateResiliationRcd = selectedTaDossierRcdDTO.getAntecedentRcdDateResiliation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			}
			//if(selectedTaDossierRcdDTO.getAntecedentRceContratResilie() && selectedTaDossierRcdDTO.getAntecedentRceDateResiliation()!=null) {
			if(selectedTaDossierRcdDTO.getAssureMemeRisqueRce() && selectedAntecedantRCDEnCours.equals(ANTECEDANT_RCE_RESILIE) && selectedTaDossierRcdDTO.getAntecedentRceDateResiliation()!=null) {
				localDateResiliationRce = selectedTaDossierRcdDTO.getAntecedentRceDateResiliation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			}
			
			if(localDateResiliationRcd!=null) {
				localDateVerif = localDateResiliationRcd;
			}
			if(localDateResiliationRce!=null && selectedTaDossierRcdDTO.getAntecedentRcdDateResiliation().before(selectedTaDossierRcdDTO.getAntecedentRceDateResiliation())) {
				localDateVerif = localDateResiliationRce;
			}
		}
		
		if(localDateVerif!=null) {
			Period intervalPeriod = Period.between(localDateVerif, localDateMaintenant);
		    int nbMois = intervalPeriod.getMonths();
		    for (int i = 0; i < intervalPeriod.getYears(); i++) {
		    	nbMois = nbMois+12;
			}
//		    if(nbMois<=6) {
//		    	selectedInteruptionAssurance = INTERUPTION_ASSURANCE_MOINS_6_MOIS;
//		    } else if (nbMois>6 && nbMois<=12) {
//		    	selectedInteruptionAssurance = INTERUPTION_ASSURANCE_6_12_MOIS;
//		    } else if (nbMois>12 && nbMois<=24) {
//		    	selectedInteruptionAssurance = INTERUPTION_ASSURANCE_12_24_MOIS;
//		    } else if (nbMois>24 && nbMois<=36) {
//		    	selectedInteruptionAssurance = INTERUPTION_ASSURANCE_24_36_MOIS;
//		    } else if (nbMois>36 && nbMois<=60) {
//		    	selectedInteruptionAssurance = INTERUPTION_ASSURANCE_36_60_MOIS;
//		    } else if (nbMois>60 && nbMois<=84) {
//		    	selectedInteruptionAssurance = INTERUPTION_ASSURANCE_60_84_MOIS;
//		    } else if (nbMois>84) {
//		    	selectedInteruptionAssurance = INTERUPTION_ASSURANCE_PLUS_84_MOIS;
//		    }
		    
			
			
		    
		    if(nbMois<=24) {
		    	selectedInteruptionAssurance = INTERUPTION_ASSURANCE_0_2_ANS;
		    } else if (nbMois>24 && nbMois<=60) {
		    	selectedInteruptionAssurance = INTERUPTION_ASSURANCE_2_5_ANS;
		    } else if (nbMois>60 && nbMois<=84) {
		    	selectedInteruptionAssurance = INTERUPTION_ASSURANCE_5_7_ANS;
		    } else if (nbMois > 84) {
		    	selectedInteruptionAssurance = INTERUPTION_ASSURANCE_PLUS_7_ANS;
		    } 
		    //JE TAFF ICI A LANCER ET VERIFIER
		    actRadioInteruptionAssurance();
		    actChangementDejaAssureMemeRisque();
		    actChangementAntecedantDateResiliation();
		   
		    
		}
	}
	
	public void calculeTauxSousTraitanceGlobal(ActionEvent e) {
		
//		selectedTaDossierRcdDTO.setCodeTSousTraitance(TaTSousTraitance.ST_0_30);
//		
//		if(totalPourcentageActiviteSousTraite!=null) {
//			if(totalPourcentageActiviteSousTraite.compareTo(new BigDecimal(30))<=0) {
//				selectedTaDossierRcdDTO.setCodeTSousTraitance(TaTSousTraitance.ST_0_30);
//			} else if (totalPourcentageActiviteSousTraite.compareTo(new BigDecimal(30))>0 && totalPourcentageActiviteSousTraite.compareTo(new BigDecimal(50))<=0) {
//				selectedTaDossierRcdDTO.setCodeTSousTraitance(TaTSousTraitance.ST_31_50);
//			} else {
//				selectedTaDossierRcdDTO.setCodeTSousTraitance(TaTSousTraitance.ST_51_100);
//			}
//		}
	}

	public boolean pasDejaOuvert() {
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_CONTRAT_RCPRO);
		return tabsCenter.ongletDejaOuvert(b)==null;
	} 
	
	public void onRowSimpleSelect(SelectEvent event) {

		if(pasDejaOuvert()==false){
			onRowSelect(event);

			autoCompleteMapDTOtoUI();
			if(ConstWeb.DEBUG) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage("Courtier", 
						"Chargement du tiers N°"+selectedTaDossierRcdDTO.getNumDossierPolice()
						)); }
		} else {
			tabViews.selectionneOngletCentre(LgrTab.TYPE_TAB_TIERS);
		}
	} 
	
	public void majMontantCommissionPrevu(AjaxBehaviorEvent e){
		majMontantCommissionPrevu();
	}
	
	public void majMontantCommissionPrevu(SlideEndEvent e){
		majMontantCommissionPrevu();
	}
	
	public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
	
	public void majMontantCommissionPrevu(){
		montantCommisionPrevu = BigDecimal.ZERO;
		BigDecimal commissionCourtier = BigDecimal.ZERO;
	
		//if(!erreurBloquante()) {
			majPrimeNettesEtActiviteRef();	
			
			if(primeBaseNette!=null && selectedTaDossierRcdDTO.getTauxCommission()!=null) {
				commissionCourtier = primeBaseNette.multiply(selectedTaDossierRcdDTO.getTauxCommission()).divide(new BigDecimal(100));
				selectedTaDossierRcdDTO.setMontantCommissionCourtier(commissionCourtier);
				logCalculPrime("Commission courtier (taux "+selectedTaDossierRcdDTO.getTauxCommission()+"% montant :"+commissionCourtier+"€) ");
			} else {
				logCalculPrime("Commission courtier (taux 0%) : ");
			}
		//}
		montantCommisionPrevu = commissionCourtier;
	}
	
	public void majPrimeNettesEtActiviteRef() {
			TaActivite taActiviteDeReference = null;
			List<Integer> l = new ArrayList<Integer>();
		
			logCalculPrime("Chiffre d'affaire prévisionnel : "+selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel());
			logCalculPrime("***");
			if(assurePib()) {
				logCalculPrime("Assuré PIB");
				logCalculPrime("Activité PIB sélectionnée : ");
	//			//algo calcul PIB
				//La	prime	de	base	est	calculée	sur	le	montant	du	chiffre	d’affaire,	qu’on	multiplie	par	le	taux	 ci-dessus	par	activité.
				BigDecimal primePib = null;
				BigDecimal primePibMax = BigDecimal.ZERO;
				for (TaTauxRcproPibDTO txPib : selectedTaTauxRcproPibDTOs) {
					logCalculPrime("- "+txPib.getLiblTauxRcproPib()+" - "+txPib.getTauxRcproPib());
	//					BigDecimal tx = txPib.getTauxRcproPib().divide(new BigDecimal(100)).add(new BigDecimal(1));
					BigDecimal tx = txPib.getTauxRcproPib().divide(new BigDecimal(100));
					primePib = selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel().multiply(tx);
					if(primePib.compareTo(primePibMax)>=0) {
						primePibMax = primePib; //on garde la prime la plus haute
					}
				}
				primeBaseNette = primePibMax;
			} else {
				//algo "normal"
				logCalculPrime("Assuré non PIB");
				logCalculPrime("Activité NON PIB sélectionnée : ");
				//la prime de base correspond à la prime de base la plus haute choisi dans les classes des activités choisis et en fonction du chiffre d'affaire
				if(selectedTaActiviteDTOs != null) {
					for (TaActiviteDTO a : selectedTaActiviteDTOs) {
						logCalculPrime("- "+a.getLiblActivite()+" - code : "+a.getCodeActivite()+" - classe : "+a.getCodeClasse());
						l.add(a.getId());
					}
				}
				
				
				r = chercheActiviteDeReference(l, selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel());
	//				primeBaseNette = taDossierRcdService.findMontantPrime(l, selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel());
	//				taActiviteDeReference =  taDossierRcdService.findActiviteDeReference(l, selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel());
				primeBaseNette = r.getMontantPrimeBase();
				taActiviteDeReference = r.getActiviteRef();
				if(taActiviteDeReference!=null) {
					logCalculPrime("Actvité de référence : "+taActiviteDeReference.getLiblActivite()+" - code : "+taActiviteDeReference.getCodeActivite()+" - classe : "+taActiviteDeReference.getTaClasse().getCodeClasse());
				}
			}
	}
	
	public void updateTab(){
		try {

			if(selectedTaDossierRcdDTOs!=null && selectedTaDossierRcdDTOs.length>0) {
				selectedTaDossierRcdDTO = selectedTaDossierRcdDTOs[0];
			}
			if(selectedTaDossierRcdDTO.getId()!=null && selectedTaDossierRcdDTO.getId()!=0) {
				taDossierRcd = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
			}
			if(taDossierRcd!=null) {
				TaDossierRcdMapper taDossierMapper = new TaDossierRcdMapper();
				taDossierMapper.mapEntityToDto(taDossierRcd, selectedTaDossierRcdDTO);
				mapperModelToUI.map(taDossierRcd, selectedTaDossierRcdDTO);	
				if(taSousDonnee == null) {// si taSousDonne est vide, on l'initialise 
					taSousDonnee = new TaSousDonnee();
					//initialisation des champs interruption assurance
					JSONObject jsonData;
					if(taSousDonnee.getJsonData()!=null) {
						 jsonData = new JSONObject(taSousDonnee.getJsonData());
					}else {
						 jsonData = new JSONObject();
					}
					jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_0_A_2_ANS, TaSousDonnee.VALEUR_FALSE);
					jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_2_A_5_ANS, TaSousDonnee.VALEUR_FALSE);
					jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_5_A_7_ANS, TaSousDonnee.VALEUR_FALSE);
					jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_PLUS_7_ANS, TaSousDonnee.VALEUR_FALSE);
					taSousDonnee.setJsonData(jsonData.toString());
				}
				if(taDossierRcd.getIdDossierRcd()!= null) {// taDossier possède un id
					if( taSousDonneeService.findByIdDossierRcd(taDossierRcd.getIdDossierRcd())!= null){//si un taSousDonnee existe pour ce dossier
						taSousDonnee = taSousDonneeService.findByIdDossierRcd(taDossierRcd.getIdDossierRcd());// on le récupère 
						JSONObject jsonData2 = new JSONObject(taSousDonnee.getJsonData());
						if(!jsonData2.has(TaSousDonnee.CLE_INTERRUP_ASSUR_0_A_2_ANS)) {
							jsonData2.put(TaSousDonnee.CLE_INTERRUP_ASSUR_0_A_2_ANS, TaSousDonnee.VALEUR_FALSE);
							jsonData2.put(TaSousDonnee.CLE_INTERRUP_ASSUR_2_A_5_ANS, TaSousDonnee.VALEUR_FALSE);
							jsonData2.put(TaSousDonnee.CLE_INTERRUP_ASSUR_5_A_7_ANS, TaSousDonnee.VALEUR_FALSE);
							jsonData2.put(TaSousDonnee.CLE_INTERRUP_ASSUR_PLUS_7_ANS, TaSousDonnee.VALEUR_FALSE);
						}
						taSousDonnee.setJsonData(jsonData2.toString());
					}else {//si il n'y a pas de taSousDonnee pour ce Dossier
						taSousDonnee = new TaSousDonnee();// on crée un nouveau taSousDonnee
						//initialisation des champs interruption assurance
						JSONObject jsonData;
						if(taSousDonnee.getJsonData()!=null) {
							 jsonData = new JSONObject(taSousDonnee.getJsonData());
						}else {
							 jsonData = new JSONObject();
						}
						jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_0_A_2_ANS, TaSousDonnee.VALEUR_FALSE);
						jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_2_A_5_ANS, TaSousDonnee.VALEUR_FALSE);
						jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_5_A_7_ANS, TaSousDonnee.VALEUR_FALSE);
						jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_PLUS_7_ANS, TaSousDonnee.VALEUR_FALSE);
						taSousDonnee.setJsonData(jsonData.toString());
						taSousDonnee.setTaDossierRcd(taDossierRcd); //et le lui atribue 
					}
				}
				
				//initialisation de tous les boutons radio gérés par de booleen
				//TODO A SUPPRIMER RAPIDEMENT ET A REMPLACER PAR DES LISTES/DONNEES EN BASE DE DONNEES
				initRadioAntecedantMotifRCDAssure();
				initRadioAntecedantMotifRCDAssureur();
				initRadioAntecedantMotifRCEAssure();
				initRadioAntecedantMotifRCEAssureur();
				initRadioAntecedantRCDEnCours();
				initRadioAntecedantRCDResilieParAssure();
				initRadioAntecedantRCDResilieParAssureur();
				initRadioAntecedantRCEEnCours();
				initRadioAntecedantRCEResilieParAssure();
				initRadioAntecedantRCEResilieParAssureur();
				initRadioInteruptionAssurance();
				initRadioMontantMarcheTravauxHTMin();
				initRadioSousTraitance1975();
				initRadioReprisePasse();
				initRadioProtectionJuridique();
				initRadioFranchise();
				
				taActiviteDTODisponible = taActiviteService.selectAllDTO();
				listeTaActiviteDTODetail = new ArrayList<>();
				selectedTaActiviteDTOs = new ArrayList<>();
				listeTaGedUtilisateurDTO = new ArrayList<>();
				listeTaGedUtilisateur = new ArrayList<>();
			
				gedContratRcProController.setMasterEntity(taDossierRcd);
				gedContratRcProController.refresh();
				
				listeTaGedUtilisateurDTO = initListeGed();
				listeByType = taListeRefDocService.findByType(TaTListeRefDoc.ID_TYPE_LISTE_REF_DOC_RCPRO);
				
				selectedTaDossierRcdDTO.getTaTStatut().clear();
				if(taDossierRcd.getTaTStatut()!=null && !taDossierRcd.getTaTStatut().isEmpty()) {//si l'entité a dèja des statut, on les ajoute au DTO
					for (TaTStatut s : taDossierRcd.getTaTStatut()) {
						selectedTaDossierRcdDTO.getTaTStatut().add(s);
					}
				}else {                                                                      
					//sinon, on remplit les statut du DTO et on rempli l'entité avec.
					if(taDossierRcd.getIdDossierRcd()!=null) {
//						donneStatut(selectedTaDossierRcdDTO);
						
						IDonneStatut Idossier = taDossierRcdService.donneStatut(selectedTaDossierRcdDTO);
						selectedTaDossierRcdDTO.getTaTStatut().clear();
			        	for (TaTStatut stat : Idossier.getTaTStatut()) {
			        		selectedTaDossierRcdDTO.getTaTStatut().add(stat);
						}
						
						
						if(!selectedTaDossierRcdDTO.getTaTStatut().isEmpty()) {
							for (TaTStatut s : selectedTaDossierRcdDTO.getTaTStatut()) {
								taDossierRcd.getTaTStatut().add(s);
							}
							taDossierRcd = taDossierRcdService.merge(taDossierRcd,ITaDossierRcdServiceRemote.validationContext);
						}
					}
				}
				autoCompleteMapDTOtoUI();
				calculPourcentageSoustraitance();
				majMontantCommissionPrevu();
				initAffichageAntecedent();
				
//				calculeDureeInterruptionAssurance();
//				calculeTauxSousTraitanceGlobal(null);
				listeAttestationNominative = taAttestationNominativeService.findAllByIdDossier(taDossierRcd.getIdDossierRcd());
				remplieListeEcheanceDTO();
				listeAvenantTaDossierRcdDTO = taDossierRcdService.findAllAvenantByNumPolice(taDossierRcd.getNumDossierPolice());
			
				
			}
		} catch (FinderException e) {
			e.printStackTrace();
		}
	}
	
	public void onTabShow() {
		System.out.println("ArticleController.onTabShow()");
	}
	
	public void autorisationChangementOnglet() {
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Executed", "Using RemoteCommand."));
		Boolean result=false;
//		   for(DashboardPage dp : dashboardPageList){
//		        if(dp.getModel() == 0){
//		            result= false;
//		        }
//		   }
		   RequestContext reqCtx = RequestContext.getCurrentInstance();        
		   reqCtx.addCallbackParam("returnedValue", result);
    }
	
	public void onTabChange(TabChangeEvent event) {

		idOngletPrecedent = idOngletActif;
		idOngletActif = event.getTab().getId();
		
		if(idOngletPrecedent!=null) {

			if(idOngletPrecedent.equals(ID_ONGLET_1_DECLARATION_ENGAGEMENT)) {
				validationOnglet1DeclarationEngagement();
			} else if(idOngletPrecedent.equals(ID_ONGLET_2_PROPOSANT_DECLARATION)) {
//				if(taTJuridiqueDTO!=null && taTJuridiqueDTO.getCodeTJuridique().equals("AE")) {
//					if(selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel()!=null && selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel().compareTo(new BigDecimal(80000))>0) {
//						tabViewContratRcPro.setActiveIndex(mapOngletIndex.get(ID_ONGLET_2_PROPOSANT_DECLARATION));
//						idOngletActif = ID_ONGLET_2_PROPOSANT_DECLARATION;
//						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Pour les auto entrepreneurs le chiffre d'affaire maximum est de 80 000€", null));
//					}
//					nbActiviteMax = NB_ACTIVITE_MAX_AUTO_ENTREPRENEUR_DEFAUT;
//				} else {
//					nbActiviteMax = NB_ACTIVITE_MAX_DEFAUT;
//				}
				validationOnglet2ProposantDeclaration();
			} else if(idOngletPrecedent.equals(ID_ONGLET_3_ANTECEDANT)) {
				validationOnglet3Antecedant();
			} else if(idOngletPrecedent.equals(ID_ONGLET_4_SINISTRALITE)) {
				validationOnglet4Sinistralite();
			} else if(idOngletPrecedent.equals(ID_ONGLET_5_ACTIVITE_DECLARE)) {
				validationOnglet5ActiviteDeclare();
			} else if(idOngletPrecedent.equals(ID_ONGLET_6_OPTION_PAIEMENT)) {
				validationOnglet6OptionPaiement();
			} else if(idOngletPrecedent.equals(ID_ONGLET_7_INFORMATION_LEGALE)) {
				validationOnglet7InformationLegale();
			} else if(idOngletPrecedent.equals(ID_ONGLET_8_PRIME)) {
				validationOnglet8Prime();
			} else if(idOngletPrecedent.equals(ID_ONGLET_9_GED_DOCUMENT)) {
				validationOnglet9GedDocument();
			}
			
		} else {
			//pas d'onglet actif, on est sur le 1er onglet (par defaut)
			if(affichePremierOnglet) {
				validationOnglet1DeclarationEngagement();
			} else {
				validationOnglet2ProposantDeclaration();
			}
		}
		
		if(idOngletActif.equals(ID_ONGLET_9_GED_DOCUMENT)) {
			listTaGedUtilisateurDTO = initListeGed();
		}
		
		/*
		 * Si trop lent voir au cas par cas pour :
		 * 	- soit mettre à jour uniquement l'onglet qui va s'afficher, 
		 *  - soit mettre à jour les propriété du masterEntity en fonction de ce qui vient d'être fait sur l'onlget précendent
		 * Par exemple mettre à jour la liste des controle dans l'article courant au lieu de recharger l'article entièrement
		 * 
		 * Sinon chercher ce que l'on peu faire avec un refresh JPA
		 */
//		updateTab();  //TODO en commentaire sinon déclenche un findById du masterEntity (ici le contrat) et "casse" l'onglet des antécédent sinistre qui sont reliés directement à l'entité
		
		if(modeEcran.dataSetEnModif()) {
			calculMontantPrime();
		}
	}
	
	public boolean disabledTab(String nomTab) {
		//return modeEcran.dataSetEnModif() ||  selectedTaDossierRcdDTO.getId()==0;
		return true;
	}
	
	public void onRowSelect(SelectEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_CONTRAT_RCPRO);
		b.setTitre("Contrat RCD");
		b.setTemplate("assurance/contrat_rcd.xhtml");
		b.setIdTab(LgrTab.ID_TAB_CONTRAT_RCPRO);
		b.setStyle(LgrTab.CSS_CLASS_TAB_CONTRAT_RCPRO);
		b.setParamId(LibConversion.integerToString(selectedTaDossierRcdDTO.getId()));

		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
		
		updateTab();
		scrollToTop();
		
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Courtier", 
				"Chargement du tiers N°"+selectedTaDossierRcdDTO.getNumDossierPolice()
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
	
	public void actDialogAssure(ActionEvent actionEvent) {
		
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
        
        RequestContext.getCurrentInstance().openDialog("assure/dialog_assure", options, params);
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
	
	public void actDialogAttestationNominative(ActionEvent actionEvent) {

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

		RequestContext.getCurrentInstance().openDialog("assurance/dialog_attestation_nominative", options, params);

	}
	
	public void handleReturnDialogTypeCourtier(SelectEvent event) {
		if(event!=null && event.getObject()!=null) {
			taTCourtier = (TaTCourtier) event.getObject();
			try {
				taTCourtierDTO = taTCourtierService.findByCodeDTO(taTCourtier.getCodeTCourtier());
			} catch (FinderException e) {
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
				e.printStackTrace();
			}
		}
	}
	
	public void handleReturnDialogAssure(SelectEvent event) {
		if(event!=null && event.getObject()!=null) {
			taAssure = (TaAssure) event.getObject();
			try {
				taAssureDTO = taAssureService.findByCodeDTO(taAssure.getCodeAssure());
			} catch (FinderException e) {
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
				e.printStackTrace();
			}
		}
	}
	
	public void actInsererSinistreAntecedent() {
		actInsererSinistreAntecedent(null) ;
	}
	
	public void actInsererAttestationNominative() {
		 actInsererAttestationNominative(null);
	}
	
	public void actModifierSinistreAntecedent() {
		actModifierSinistreAntecedent(null);
	}
	
	public void actSupprimerAntecedent() {
		actSupprimerAntecedent(null);
	}
	
	public void actAnnulerSinistreAntecedent() {
		actAnnulerSinistreAntecedent(null);
	}
	
	public void actEnregistrerSinistreAntecedent() {
		actEnregistrerSinistreAntecedent(null);
	}
	
	public void actEnregistrerAttestationNominative() {
		actEnregistrerAttestationNominative(null);
	}
	//////////////////////////////////
	public void actInsererSinistreAntecedent(ActionEvent actionEvent) {
		selectedTaSinstreAntecedent = new TaSinistreAntecedent();
		selectedTaSinstreAntecedent.setTaTGarantieSinistre(listeTaTGarantieSinistre.get(0));
	//	selectedTaSinstreAntecedent.setDateSinistre(new Date());
	}
	
	public void actInsererAttestationNominative(ActionEvent actionEvent) {
		selectedTaAttestationNominative = new TaAttestationNominative();
		TaReglementPrestation reg = new TaReglementPrestation();
		selectedTaAttestationNominative.setTaReglementPrestation(reg);
		selectedTaAttestationNominative.setDateEffet(selectedTaDossierRcdDTO.getDateEffet());
		selectedTaAttestationNominative.setDateDebut(selectedTaDossierRcdDTO.getDateDebutPremierePeriode());
		selectedTaAttestationNominative.setDateFin(selectedTaDossierRcdDTO.getDateEcheance());
		
		//activité de l'attestation pré-sélectionné
		if(selectedTaActiviteDTOAttestationNominative==null) {
			selectedTaActiviteDTOAttestationNominative = new ArrayList<>();
		}
		selectedTaActiviteDTOAttestationNominative.clear();
		selectedTaActiviteDTOAttestationNominative.addAll(selectedTaActiviteDTOs);
		
//		selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel()
		try {
			if(assurePib()) {
				selectedTaAttestationNominative.setTaTarifPrestation(taTarifPrestationService.findByCode(TaTarifPrestation.ATTESTATION_NOMINATIVE_PIB));	
			}else {
				selectedTaAttestationNominative.setTaTarifPrestation(taTarifPrestationService.findByCode(TaTarifPrestation.ATTESTATION_NOMINATIVE));
			}
			
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void actValidationCourtierAttestation(TaAttestationNominative attest){
		attest.setValidationCourtier(true);
		taAttestationNominativeService.merge(attest);
	}
	
	public void actModifierAttestation(TaAttestationNominative attest){
		//pré-remplir la liste des activités
		selectedTaActiviteDTOAttestationNominative.clear();
		if(attest!=null && attest.getTaAttestationActivites()!=null && !attest.getTaAttestationActivites().isEmpty()) {
			if(selectedTaActiviteDTOAttestationNominative==null) {
				selectedTaActiviteDTOAttestationNominative = new ArrayList<>();
			}
			selectedTaActiviteDTOAttestationNominative.clear();
			for (TaAttestationNominativeActivite act : attest.getTaAttestationActivites()) {
				for (TaActiviteDTO dto : selectedTaActiviteDTOs) {
					if(dto.getCodeActivite().equals(act.getTaActivite().getCodeActivite())) {
						selectedTaActiviteDTOAttestationNominative.add(dto);
					}
				}
			}
		}
	}
	
	public void actValidationYlyadeAttestation(TaAttestationNominative attest){
		attest.setValidationYlyade(true);
		taAttestationNominativeService.merge(attest);
	}
	
	public void actModifierSinistreAntecedent(ActionEvent actionEvent) {
		
	}
	
	public void actSupprimerAntecedent(ActionEvent actionEvent) {
		taDossierRcd.getTaSinistreAntecedent().remove(selectedTaSinstreAntecedent);
	}
	
	public void actAnnulerSinistreAntecedent(ActionEvent actionEvent) {
		
	}
	
	public void actEnregistrerSinistreAntecedent(ActionEvent actionEvent) {
		if(taDossierRcd.getTaSinistreAntecedent()==null) {
			taDossierRcd.setTaSinistreAntecedent(new ArrayList<TaSinistreAntecedent>());
		}
		taDossierRcd.getTaSinistreAntecedent().add(selectedTaSinstreAntecedent);
		selectedTaSinstreAntecedent.setTaDossierRcd(taDossierRcd);
		
		if(taDossierRcd.getTaSinistreAntecedent()!=null && !taDossierRcd.getTaSinistreAntecedent().isEmpty()) {
			selectedTaDossierRcdDTO.setNbSinistre5ans(taDossierRcd.getTaSinistreAntecedent().size());
			BigDecimal total = new BigDecimal(0);
			for (TaSinistreAntecedent taSinistreAntecedent : taDossierRcd.getTaSinistreAntecedent()) {
				total = total.add(taSinistreAntecedent.getMontantSinistre());
			}
			selectedTaDossierRcdDTO.setCoutGlobalSinistreRcd(total);
		} else {
			selectedTaDossierRcdDTO.setNbSinistre5ans(0);
			selectedTaDossierRcdDTO.setCoutGlobalSinistreRcd(new BigDecimal(0));
		}

	}
	
	public void actEnregistrerAttestationNominative(ActionEvent actionEvent) {
		try {
			
			if(selectedTaAttestationNominative.getEffectifChantier() > selectedTaDossierRcdDTO.getEffectifTotalExercice()) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Attestation nominative", "l'effectif est supérieur à l'effectif déclaré dans le contrat."));
				
			}else if(!(selectedTaAttestationNominative.getMontantMarcheHt().compareTo(selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel())<= 0)) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Attestation nominative", "le montant du marché HT est supérieur au chiffre d'affaire prévisionnel déclaré dans le contrat."));
			}else {
				
//				if(selectedTaAttestationNominative.getTaAttestationActivites()!=null 
//						&& !selectedTaAttestationNominative.getTaAttestationActivites().isEmpty()) {
					

					
					
					if(selectedTaAttestationNominative.getTaAttestationActivites()==null) {
						selectedTaAttestationNominative.setTaAttestationActivites(new ArrayList<TaAttestationNominativeActivite>());
					} else {
						selectedTaAttestationNominative.getTaAttestationActivites().clear();
					}
					for (TaActiviteDTO r : selectedTaActiviteDTOAttestationNominative) {
						boolean trouve = false;
						for(TaAttestationNominativeActivite actContrat : selectedTaAttestationNominative.getTaAttestationActivites()) {
							if(actContrat.getActivite().equals(r.getCodeActivite())) {
								trouve = true;
							}
						}
						if(!trouve) {
							//taDossierRcd.getTaDevisRcProActivites().add(taActiviteService.findById(r.getId()));
							TaAttestationNominativeActivite attestationNominativeActivite = new TaAttestationNominativeActivite();
							attestationNominativeActivite.setActivite(r.getCodeActivite());
							attestationNominativeActivite.setPourcentExerceEntreprise(r.getPourcentExerceEntreprise());
							attestationNominativeActivite.setPourcentSousTraite(r.getPourcentSousTraite());
							attestationNominativeActivite.setCommentaire(r.getCommentaire());
							attestationNominativeActivite.setTaActivite(taActiviteService.findById(r.getId()));
//							contratRcProActivite.setClasseAssocie(r.getCodeClasse());
							selectedTaAttestationNominative.getTaAttestationActivites().add(attestationNominativeActivite);
							attestationNominativeActivite.setTaAttestationNominative(selectedTaAttestationNominative);
						}
					}
	//				//supression des activités désélectionnées
	//				List<TaDossierRcdActivite> l = new ArrayList<>();
	//				if(taDossierRcd.getTaDevisRcProActivites()!=null) {
	//					for(TaDossierRcdActivite actContrat : taDossierRcd.getTaDevisRcProActivites()) { //recherche des activités à supprimer
	//						boolean trouve = false;
	//						for (TaActiviteDTO r : selectedTaDossierRcdDTO.getListeActivite()) {
	//							if(actContrat.getActivite().equals(r.getCodeActivite())) {
	//								trouve = true;
	//							}
	//							if(!trouve) {
	//								l.add(actContrat);
	//							}
	//						}
	//					}
	//					for(TaDossierRcdActivite a : l) { //supression des activités
	//						taDossierRcd.getTaDevisRcProActivites().remove(a);
	//					}
	//				}
//				}
					
				selectedTaAttestationNominative.setTaDossierRcd(taDossierRcd);
				taAttestationNominativeService.merge(selectedTaAttestationNominative);

				listeAttestationNominative = taAttestationNominativeService.findAllByIdDossier(taDossierRcd.getIdDossierRcd());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	public boolean afficheMotDePasse() {
		boolean affiche = false;
		if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_INSERTION)==0) {
			affiche = true;
		}
		return affiche;
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
				if(selectedTaDossierRcdDTO!=null && selectedTaDossierRcdDTO.getId()!=null  && selectedTaDossierRcdDTO.getId()!=0 ) retour = false;
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

	public void validateContratRcPro(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	
		String messageComplet = "";
		
		try {
		  //String selectedRadio = (String) value;
		 
		  String nomChamp =  (String) component.getAttributes().get("nomChamp");
		  
		  //msg = "Mon message d'erreur pour : "+nomChamp;
		  
//		  validateUIField(nomChamp,value);
//		  TaDossierRcdDTO dtoTemp=new TaDossierRcdDTO();
//		  PropertyUtils.setSimpleProperty(dtoTemp, nomChamp, value);
//		  taCourtierService.validateDTOProperty(dtoTemp, nomChamp, ITaDossierRcdServiceRemote.validationContext );
		  
//			//validation automatique via la JSR bean validation
		  ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		  Set<ConstraintViolation<TaDossierRcdDTO>> violations = factory.getValidator().validateValue(TaDossierRcdDTO.class,nomChamp,value);
			if (violations.size() > 0) {
				messageComplet = "Erreur de validation : ";
//				List<IStatus> statusList = new ArrayList<IStatus>();
				for (ConstraintViolation<TaDossierRcdDTO> cv : violations) {
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
		  
		  //selectedTaDossierRcdDTO.setAdresse1Adresse("abcd");
		  
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
			if(nomChamp.equals(Const.C_CODE_ASSURE)) {
				TaAssure entity = null;
				if(value!=null) {
					if(value instanceof TaAssure) {
						entity=(TaAssure) value;
					}else if (value instanceof TaAssureDTO){
						entity = taAssureService.findByCode(((TaAssureDTO)value).getCodeAssure());	
					}else{
						entity = taAssureService.findByCode((String)value);
					}
				} else {
					selectedTaDossierRcdDTO.setCodeAssure("");
					taAssureDTO.setCodeAssure("");
					taAssure=null;
				}						
				taDossierRcd.setTaAssure(entity);
				TaAssureDTO dto = new TaAssureDTO();
				mapperModelToUIAssure.map(entity, dto);
				selectedTaDossierRcdDTO.setTaAssureDTO(dto);
			} else if(nomChamp.equals(Const.C_CODE_COURTIER)) {
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
					selectedTaDossierRcdDTO.setCodeAssure("");
					taCourtierDTO.setCodeCourtier("");
					taCourtier=null;
				}						
				taDossierRcd.setTaCourtier(entity);
				//taTCourtier=entity;
			} else if(nomChamp.equals(Const.C_CODE_T_ASSURE)) {
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
					selectedTaDossierRcdDTO.getTaAssureDTO().setCodeTAssure("");
					taTAssureDTO.setCodeTAssure("");
					taTAssure=null;
				}						
				taDossierRcd.getTaAssure().setTaTAssure(entity);
				//taTAssure=entity;
				
			} else if(nomChamp.equals(Const.C_CODE_T_CIVILITE)) {
				taDossierRcd.getTaAssure().initContactEntrepriseAssure("test"); //création du ContactEntreprise par defaut s'il n'existe pas
				TaTCivilite entity = null;
				if(value!=null) {
					if(value instanceof TaTCivilite) {
						entity=(TaTCivilite) value;
					}else if (value instanceof TaTCiviliteDTO){
						entity = taTCiviliteService.findByCode(((TaTCiviliteDTO)value).getCodeTCivilite());	
					}else{
						entity = taTCiviliteService.findByCode((String)value);
					}
				} else {
					selectedTaDossierRcdDTO.getTaAssureDTO().setCodeTCivilite("");
					taTCiviliteDTO.setCodeTCivilite("");
					taTCivilite=null;
				}						
				taDossierRcd.getTaAssure().getTaContactEntreprise().setTaTCivilite(entity);
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
					selectedTaDossierRcdDTO.getTaAssureDTO().setCodeTJuridique("");
					taTJuridiqueDTO.setCodeTJuridique("");
					taTJuridique=null;
				}						
				taDossierRcd.getTaAssure().setTaTJuridique(entity);
			} else if(nomChamp.equals(Const.C_NUMERO_TEL)) {
				taDossierRcd.getTaAssure().initContactEntrepriseAssure("test"); //création du ContactEntreprise par defaut s'il n'existe pas
				taDossierRcd.getTaAssure().getTaContactEntreprise().initTelephoneDefaut(value);
				if(value!=null && !value.equals("")) { 
					PropertyUtils.setSimpleProperty(taAssure.getTaContactEntreprise().getTaTel(), nomChamp, value);
				}
				if(taDossierRcd.getTaAssure().getTaContactEntreprise().getTaTel()==null)selectedTaDossierRcdDTO.getTaAssureDTO().setNumeroTel(null);
				if(taDossierRcd.getTaAssure().getTaContactEntreprise().getTaTel()!=null &&
						taDossierRcd.getTaAssure().getTaContactEntreprise().getTaTel().getTaTTel()==null){
					TaTTel taTTel = new TaTTel();
					taTTel.setCodeTTel(ConstPreferences.TTEL_DEFAUT);
					if(!taTTel.getCodeTTel().equals("")){
						//TaTTelDAO taTTelDAO = new TaTTelDAO(getEm());
						taTTel=taTTelService.findByCode(taTTel.getCodeTTel());
						taDossierRcd.getTaAssure().getTaContactEntreprise().getTaTel().setTaTTel(taTTel);
					}
				}					
			}else if(nomChamp.equals(Const.C_ADRESSE_EMAIL)) {
				taDossierRcd.getTaAssure().initContactEntrepriseAssure("test"); //création du ContactEntreprise par defaut s'il n'existe pas
				taDossierRcd.getTaAssure().getTaContactEntreprise().initEmailDefaut(value);
				if(value!=null && !value.equals("")) {
					PropertyUtils.setSimpleProperty(taAssure.getTaContactEntreprise().getTaEmail(), nomChamp, value);
					taDossierRcd.getTaAssure().getTaUtilisateur().setIdentifiant((String) value);
				}
				if(taDossierRcd.getTaAssure().getTaContactEntreprise().getTaEmail()==null)selectedTaDossierRcdDTO.getTaAssureDTO().setAdresseEmail(null);
				if(taDossierRcd.getTaAssure().getTaContactEntreprise().getTaEmail()!=null &&
						taDossierRcd.getTaAssure().getTaContactEntreprise().getTaEmail().getTaTEmail()==null){
					TaTEmail taTEmail = new TaTEmail();
					taTEmail.setCodeTEmail(ConstPreferences.TEMAIL_DEFAUT);
					if(!taTEmail.getCodeTEmail().equals("")){
						taTEmail=taTEmailService.findByCode(taTEmail.getCodeTEmail());
						taDossierRcd.getTaAssure().getTaContactEntreprise().getTaEmail().setTaTEmail(taTEmail);
					}
				}
				taDossierRcd.getTaAssure().getTaUtilisateur().setIdentifiant((String)value);
			}else if(nomChamp.equals(Const.C_NOM)
					|| nomChamp.equals(Const.C_PRENOM)) {
				taDossierRcd.getTaAssure().initContactEntrepriseAssure("test"); //création du ContactEntreprise par defaut s'il n'existe pas
				if(value!=null && !value.equals("")) {
					PropertyUtils.setSimpleProperty(taDossierRcd.getTaAssure().getTaContactEntreprise(), nomChamp, value);
				}
//				if(taAssure.getTaContactEntreprise()==null)selectedTaAssureDTO.setAdresseEmail(null);
				
			}else if(nomChamp.equals(Const.C_ADRESSE_LIGNE_1)
					|| nomChamp.equals(Const.C_ADRESSE_LIGNE_2)
					|| nomChamp.equals(Const.C_ADRESSE_LIGNE_3)
					|| nomChamp.equals(Const.C_CODE_POSTAL)
					|| nomChamp.equals(Const.C_VILLE)
					|| nomChamp.equals(Const.C_PAYS)) {
				taDossierRcd.getTaAssure().initContactEntrepriseAssure("test"); //création du ContactEntreprise par defaut s'il n'existe pas
				
				taDossierRcd.getTaAssure().getTaContactEntreprise().initAdresseDefaut(value,adresseEstRempli());
				if(value!=null) {	
					if(taDossierRcd.getTaAssure().getTaContactEntreprise().getTaAdresse()!=null) {
						PropertyUtils.setSimpleProperty(taDossierRcd.getTaAssure().getTaContactEntreprise().getTaAdresse(), nomChamp, value);
					}
				}
				if(taDossierRcd.getTaAssure().getTaContactEntreprise().getTaAdresse()==null){
					initAdresseNull();
				}
				if(taDossierRcd.getTaAssure().getTaContactEntreprise().getTaAdresse()!=null &&
						taDossierRcd.getTaAssure().getTaContactEntreprise().getTaAdresse().getTaTAdresse()==null){
					TaTAdresse taTAdr = new TaTAdresse();
					taTAdr.setCodeTAdresse(ConstPreferences.TADR_DEFAUT);
					if(!taTAdr.getCodeTAdresse().equals("")){
						taTAdr = taTAdresseService.findByCode(taTAdr.getCodeTAdresse());
						taDossierRcd.getTaAssure().getTaContactEntreprise().getTaAdresse().setTaTAdresse(taTAdr);
					}
				}			
			} else if(nomChamp.equals(Const.C_IDENTIFIANT)
					|| nomChamp.equals(Const.C_PASSWORD)
					|| nomChamp.equals(Const.C_PASSWORD+"_CONFIRMATION")) {
				if(nomChamp.equals(Const.C_IDENTIFIANT)) {
					
					if(selectedTaDossierRcdDTO.getTaAssureDTO().getPassword()!=null && !selectedTaDossierRcdDTO.getTaAssureDTO().getPassword().equals("")) {
						taDossierRcd.getTaAssure().getTaUtilisateur().setPassword(taDossierRcd.getTaAssure().getTaUtilisateur().passwordHashSHA256_Base64(selectedTaDossierRcdDTO.getTaAssureDTO().getPassword()));
					}
					if(value!=null && !value.equals("")) { 
						PropertyUtils.setSimpleProperty(taDossierRcd.getTaAssure().getTaUtilisateur(), nomChamp, value);
					}
				} else if(nomChamp.equals(Const.C_PASSWORD)) {
					if(value!=null && !value.equals("")) {
					PropertyUtils.setSimpleProperty(taDossierRcd.getTaAssure().getTaUtilisateur(), nomChamp, taDossierRcd.getTaAssure().getTaUtilisateur().passwordHashSHA256_Base64((String)value));
					}
				}else if(nomChamp.equals(Const.C_PASSWORD+"_CONFIRMATION")) {
					PropertyUtils.setSimpleProperty(selectedTaDossierRcdDTO.getTaAssureDTO(),"passwordConfirm", taDossierRcd.getTaAssure().getTaUtilisateur().passwordHashSHA256_Base64((String)value));
					System.out.println("valeur de value avant hash "+value);
					
					value = taDossierRcd.getTaAssure().getTaUtilisateur().passwordHashSHA256_Base64((String)value);
					
					System.out.println("valeur de value aprés hash "+value);
					System.out.println("valeur de getPassword "+taDossierRcd.getTaAssure().getTaUtilisateur().getPassword());
					
					if(!value.equals(taDossierRcd.getTaAssure().getTaUtilisateur().getPassword())) {
						System.out.println("les hashs sont différents");
						FacesContext context = FacesContext.getCurrentInstance();  
						context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Tiers", "les champs mot de passe et confirmation de mot de passe doivent être identiques"));
						
					}
				}
				
			} else if(nomChamp.equals(Const.C_CODE_T_REGLEMENT)) {
				TaTReglement entity = null;
				if(value!=null) {
					if(value instanceof TaTReglement) {
						entity=(TaTReglement) value;
					}else if (value instanceof TaTReglementDTO){
						entity = taTReglementService.findByCode(((TaTReglementDTO)value).getCodeTReglement());	
					}else{
						entity = taTReglementService.findByCode((String)value);
					}
				} else {
					selectedTaDossierRcdDTO.setCodeTReglement("");
//					taTReglementDTO.setCodeTReglement("");
					taTReglement=null;
				}						
				taDossierRcd.setTaTReglement(entity);
			} else if(nomChamp.equals(Const.C_CODE_T_ECHEANCE)) {
				TaTEcheance entity = null;
				if(value!=null) {
					if(value instanceof TaTEcheance) {
						entity=(TaTEcheance) value;
					}else if (value instanceof TaTEcheanceDTO){
						entity = taTEcheanceService.findByCode(((TaTEcheanceDTO)value).getCodeTEcheance());	
					}else{
						entity = taTEcheanceService.findByCode((String)value);
					}
				} else {
					selectedTaDossierRcdDTO.setCodeEcheance("");
//					taTEcheanceDTO.setCodeTReglement("");
					taTEcheance=null;
				}						
				taDossierRcd.setTaTEcheance(entity);
			} else if(nomChamp.equals(Const.C_CODE_PART_SOUS_TRAITANCE)) {
				TaTSousTraitance entity = null;
				if(value!=null) {
					if(value instanceof TaTSousTraitance) {
						entity=(TaTSousTraitance) value;
					}else if (value instanceof TaTSousTraitanceDTO){
						entity = taTSousTraitanceService.findByCode(((TaTSousTraitanceDTO)value).getCodeTSousTraitance());	
					}else{
						entity = taTSousTraitanceService.findByCode((String)value);
					}
				} else {
					selectedTaDossierRcdDTO.setCodeTSousTraitance("");
//					taPartSoustraitanceDTO.setCodeTReglement("");
					taTSousTraitance=null;
				}						
				taDossierRcd.setTaTSousTraitance(entity);
			} else if(nomChamp.equals(Const.C_MONTANT_CHANTIER_PLUS_ELEVE)) {
//				LibConversion.stringToInteger(value, defaut);
//				TaPartSoustraitance entity = null;
				if(value!=null) {
//					if(value instanceof TaPartSoustraitance) {
//						entity=(TaPartSoustraitance) value;
//					}else if (value instanceof TaPartSoustraitanceDTO){
//						entity = taPartSoustraitanceService.findByCode(((TaPartSoustraitanceDTO)value).getCodePartSousTraitance());	
//					}else{
//						entity = taPartSoustraitanceService.findByCode((String)value);
//					}
				} else {
//					selectedTaDossierRcdDTO.setCodePartSousTraitance("");
////					taPartSoustraitanceDTO.setCodeTReglement("");
//					taPartSoustraitance=null;
				}						
//				taDossierRcd.setTaPartSoustraitance(entity);
			}   
			return false;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void initAdresseNull() {
		if(taAssure.getTaContactEntreprise().getTaAdresse()==null){
			selectedTaDossierRcdDTO.getTaAssureDTO().setAdresseLigne1(null);
			selectedTaDossierRcdDTO.getTaAssureDTO().setAdresseLigne2(null);
			selectedTaDossierRcdDTO.getTaAssureDTO().setAdresseLigne3(null);
			selectedTaDossierRcdDTO.getTaAssureDTO().setCodePostal(null);
			selectedTaDossierRcdDTO.getTaAssureDTO().setVille(null);
			selectedTaDossierRcdDTO.getTaAssureDTO().setPays(null);
		}
	}
	
	public void validationAttestationNominative() {
//		if(selectedTaDossierRcdDTO!=null) {
//			try {
//			
//				TaDossierRcd devis = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
//				devis.setValidationYlyade(true);
//				selectedTaDossierRcdDTO.setValidationYlyade(true);
//				actEnregistrer(null);
//			} catch (FinderException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}		
	}
	
	private boolean adresseEstRempli() {
		boolean retour=false;
		if(selectedTaDossierRcdDTO.getTaAssureDTO().getAdresseLigne1()!=null && !selectedTaDossierRcdDTO.getTaAssureDTO().getAdresseLigne1().equals(""))return true;
		if(selectedTaDossierRcdDTO.getTaAssureDTO().getAdresseLigne2()!=null && !selectedTaDossierRcdDTO.getTaAssureDTO().getAdresseLigne2().equals(""))return true;
		if(selectedTaDossierRcdDTO.getTaAssureDTO().getAdresseLigne3()!=null && !selectedTaDossierRcdDTO.getTaAssureDTO().getAdresseLigne3().equals(""))return true;
		if(selectedTaDossierRcdDTO.getTaAssureDTO().getCodePostal()!=null && !selectedTaDossierRcdDTO.getTaAssureDTO().getCodePostal().equals(""))return true;
		if(selectedTaDossierRcdDTO.getTaAssureDTO().getVille()!=null && !selectedTaDossierRcdDTO.getTaAssureDTO().getVille().equals(""))return true;
		if(selectedTaDossierRcdDTO.getTaAssureDTO().getPays()!=null && !selectedTaDossierRcdDTO.getTaAssureDTO().getPays().equals(""))return true;
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
	
	public List<TaAssure> assureAutoComplete(String query) {
        List<TaAssure> allValues = taAssureService.selectAll();
        List<TaAssure> filteredValues = new ArrayList<TaAssure>();
         
        for (int i = 0; i < allValues.size(); i++) {
        	TaAssure civ = allValues.get(i);
            if(query.equals("*") || civ.getCodeAssure().toLowerCase().contains(query.toLowerCase())) {
                filteredValues.add(civ);
            }
        }
         
        return filteredValues;
    }
	public List<TaAssureDTO> assureAutoCompleteLight(String query) {
        List<TaAssureDTO> allValues = taAssureService.selectAllDTO();
        List<TaAssureDTO> filteredValues = new ArrayList<TaAssureDTO>();
         
        for (int i = 0; i < allValues.size(); i++) {
        	TaAssureDTO civ = allValues.get(i);
            if(query.equals("*") || civ.getCodeAssure().toLowerCase().contains(query.toLowerCase())) {
                filteredValues.add(civ);
            }
        }
         
        return filteredValues;
    }
	
	public List<TaCourtier> courtierAutoComplete(String query) {
        List<TaCourtier> allValues = taCourtierService.selectAll();
        List<TaCourtier> filteredValues = new ArrayList<TaCourtier>();
         
        for (int i = 0; i < allValues.size(); i++) {
        	TaCourtier civ = allValues.get(i);
            if(query.equals("*") || civ.getCodeCourtier().toLowerCase().contains(query.toLowerCase())) {
                filteredValues.add(civ);
            }
        }
         
        return filteredValues;
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
	
	public TaDossierRcdDTO rempliDTO(){
		if(taDossierRcd!=null){			
			try {
				taDossierRcd=taDossierRcdService.findByCode(taDossierRcd.getNumDossierPolice());
				mapperModelToUI.map(taDossierRcd, selectedTaDossierRcdDTO);
				if(values.contains(selectedTaDossierRcdDTO))values.add(selectedTaDossierRcdDTO);
			} catch (FinderException e) {
				e.printStackTrace();
			}
		}
		return selectedTaDossierRcdDTO;
	}
	
	/*
	 * --------------------------------------------------------------------------------------
	 */
public List<TaGedUtilisateurDTO> initListeGed(){
		
		List<TaGedUtilisateurDTO> listeGedUtilisateur = new ArrayList<>();
		if( taDossierRcd !=null && taDossierRcd.getIdDossierRcd()!= null ) {
		List<TaListeRefDocDTO> listeRefDocNouveau = taListeRefDocService.findByTypeNotInGedUtilisateur(taDossierRcd.getIdDossierRcd(), TaTListeRefDoc.ID_TYPE_LISTE_REF_DOC_RCPRO);
		List<TaListeRefDocDTO> listeRefDocPresent = taListeRefDocService.findByTypeInGedUtilisateur(taDossierRcd.getIdDossierRcd(), TaTListeRefDoc.ID_TYPE_LISTE_REF_DOC_RCPRO);
		List<TaListeRefDocDTO> listeRefDoc = taListeRefDocService.findByType(TaTListeRefDoc.ID_TYPE_LISTE_REF_DOC_RCPRO);
		
		
		if(listeRefDocPresent!=null) {
				for (TaListeRefDocDTO item : listeRefDocPresent) {
					TaGedUtilisateurDTO gedUtilisateurPresent = new TaGedUtilisateurDTO();
					gedUtilisateurPresent = taGedUtilisateurService.findByIdDocAndByIdDevisRcProDTO(item.getId(), taDossierRcd.getIdDossierRcd());
					gedUtilisateurPresent.setIdListeRefDoc(item.getId());
					gedUtilisateurPresent.setLiblDoc(item.getLiblDoc());
					listeGedUtilisateur.add(gedUtilisateurPresent);
					
				}
				
				
			}
			if(listeRefDocNouveau!=null) {
				for (TaListeRefDocDTO item : listeRefDocNouveau) {
					TaGedUtilisateurDTO gedUtilisateurNouveau= new TaGedUtilisateurDTO();
					gedUtilisateurNouveau.setIdListeRefDoc(item.getId());
					gedUtilisateurNouveau.setLiblDoc(item.getLiblDoc());
					listeGedUtilisateur.add(gedUtilisateurNouveau);
					
				}
			}
		}else {
			List<TaListeRefDocDTO> listeRefDocNouveau = taListeRefDocService.findByType(TaTListeRefDoc.ID_TYPE_LISTE_REF_DOC_RCPRO);
			if(listeRefDocNouveau!=null) {
				for (TaListeRefDocDTO item : listeRefDocNouveau) {
					TaGedUtilisateurDTO gedUtilisateurNouveau= new TaGedUtilisateurDTO();
					gedUtilisateurNouveau.setIdListeRefDoc(item.getId());
					gedUtilisateurNouveau.setLiblDoc(item.getLiblDoc());
					listeGedUtilisateur.add(gedUtilisateurNouveau);
					
				}
			}
		}
		
		
		return listeGedUtilisateur;
		
	}
	
	
	public boolean findByIdDoc(Integer idDoc) throws FinderException {
		if(taDossierRcd!=null && taDossierRcd.getIdDossierRcd()!=null) {
			TaGedUtilisateur doc = taGedUtilisateurService.findById(idDoc);
			if(doc!=null && doc.getFichierDoc()!=null) {
				return true;
			}
			
//			return taGedUtilisateurService.findById(idDoc)!=null?true:false;
	 }
		return false;
	}
	
	
	
	public boolean isDocumentPresent(Integer idDoc) throws FinderException {
		if(idDoc!=null && taDossierRcd!=null && taDossierRcd.getIdDossierRcd() !=null) {
			TaGedUtilisateurDTO doc = taGedUtilisateurService.findByIdDTO(idDoc);
			
					if(doc!=null && doc.getFichierDoc() != null) {	
						
						return true;
					}	
							
			 }
		
		return false;
		
	}
	
	public boolean isDocumentValidateYlyade(Integer idDoc) throws FinderException {
		if(idDoc!=null && taDossierRcd!=null && taDossierRcd.getIdDossierRcd()!=null) {
			
//			TaGedUtilisateurDTO taGedUtilisateur = taGedUtilisateurService.findByIdDocAndByIdContratRcProDTO(idDoc, masterEntity.getIdDossierRcd());
			TaGedUtilisateurDTO doc = taGedUtilisateurService.findByIdDTO(idDoc);
			if(doc!=null && doc.getValidationYlyade()!=null) {
				return doc.getValidationYlyade();
			}
			
	 }
		return false;
	}
	
	public boolean isDocumentValidateSuperAssureur(Integer idDoc) throws FinderException {
		if(idDoc!=null && taDossierRcd!=null && taDossierRcd.getIdDossierRcd()!=null) {
			
//			TaGedUtilisateurDTO taGedUtilisateur = taGedUtilisateurService.findByIdDocAndByIdContratRcProDTO(idDoc, masterEntity.getIdDossierRcd());
			TaGedUtilisateurDTO doc = taGedUtilisateurService.findByIdDTO(idDoc);
			if(doc!=null && doc.getValidationSuperAssureur()!=null) {
				return doc.getValidationSuperAssureur();
			}
			
	 }
		return false;
	}
	
	public boolean isDocumentValidateCourtier(Integer idDoc) throws FinderException {
		if(idDoc!=null && taDossierRcd!=null && taDossierRcd.getIdDossierRcd()!=null) {
			
//			TaGedUtilisateurDTO taGedUtilisateur = taGedUtilisateurService.findByIdDocAndByIdContratRcProDTO(idDoc, masterEntity.getIdDossierRcd());
			TaGedUtilisateurDTO doc = taGedUtilisateurService.findByIdDTO(idDoc);
			if(doc!=null && doc.getValidationCourtier()!=null) {
				return doc.getValidationCourtier();
			}
			
	 }
		return false;
	}

	public void handleFileUpload(FileUploadEvent event) throws FinderException {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        try {
        	Integer param =  (Integer) event.getComponent().getAttributes().get("param"); // bar
        	TaGedUtilisateurDTO doc = taGedUtilisateurService.findByIdDTO(param);
        	
        	TaGedUtilisateur ged = taGedUtilisateurService.findById(param);
        	
//        	TaGedUtilisateur taGedUtilisateur = new TaGedUtilisateur();
        	
        	doc.setFichierDoc(IOUtils.toByteArray(event.getFile().getInputstream()));
        	doc.setNomFichier(event.getFile().getFileName());
        	doc.setTypeMime(event.getFile().getContentType());
        	doc.setTaille((int) event.getFile().getSize());
        	
        	ged.setFichierDoc(IOUtils.toByteArray(event.getFile().getInputstream()));
        	ged.setNomFichier(event.getFile().getFileName());
        	ged.setTypeMime(event.getFile().getContentType());
        	ged.setTaille((int) event.getFile().getSize());
        	
//			fichier.setBlobFichierMiniature(fichier.resizeImage(IOUtils.toByteArray(event.getFile().getInputstream()), event.getFile().getFileName(), 640, 480));
        	int hMax = 80;
        	int lMax = 350;
        	Image image = ImageIO.read(event.getFile().getInputstream());
        	//BufferedImage bi = TaFichier.resizeImageMax(image,lMax,hMax);
   	
//        	taGedUtilisateurDTO.setTaDossierRcd(masterEntity);    
//        	taGedUtilisateurDTO.setIdContratRcPro(masterEntity.getIdDossierRcd());  
//        	masterEntity.add(taGedUtilisateurDTO);

			Date now = new Date();
			doc.setDateDepot(now);
        	
			ged = taGedUtilisateurService.merge(ged);
			
			listTaGedUtilisateurDTO = initListeGed();
        	
        	//actEnregistrer(null);
//    		refresLogo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void supprimerDoc(ActionEvent actionEvent) {

		try {
			Integer param2 =  (Integer) actionEvent.getComponent().getAttributes().get("param");

//			TaGedUtilisateur taGedUtilisateur = taGedUtilisateurService.findById(param2);
			TaGedUtilisateurDTO taGedUtilisateurDTO = taGedUtilisateurService.findByIdDTO(param2);
//			taGedUtilisateurService.remove(taGedUtilisateurDTO);
			taGedUtilisateurDTO.setFichierDoc(null);
			taGedUtilisateurDTO.setNomFichier(null);
			taGedUtilisateurDTO.setTypeMime(null);
			taGedUtilisateurDTO.setTaille(null);
			taGedUtilisateurDTO.setDateDepot(null);
			
			TaGedUtilisateur taGedUtilisateur = taGedUtilisateurService.findById(param2);
			//taGedUtilisateurService.remove(taGedUtilisateur);
			taGedUtilisateur.setFichierDoc(null);
			taGedUtilisateur.setNomFichier(null);
			taGedUtilisateur.setTypeMime(null);
			taGedUtilisateur.setTaille(null);
			taGedUtilisateur.setDateDepot(null);
			taGedUtilisateur = taGedUtilisateurService.merge(taGedUtilisateur);
			
			listTaGedUtilisateurDTO = initListeGed();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "GedUtilisateur", e.getCause().getCause().getCause().getCause().getMessage()));
		}

	}
	
	
	
	
	public void validationYlyade(ActionEvent actionEvent) {

		try {
			Integer param =  (Integer) actionEvent.getComponent().getAttributes().get("param");

			TaGedUtilisateur taGedUtilisateur = taGedUtilisateurService.findByIdDocAndByIdDevisRcPro(param, taDossierRcd.getIdDossierRcd());
			if(taGedUtilisateur.getValidationYlyade()==null ||  taGedUtilisateur.getValidationYlyade()==false  ) {
			taGedUtilisateur.setValidationYlyade(true);
			taGedUtilisateurService.merge(taGedUtilisateur);
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "GedUtilisateur", e.getCause().getCause().getCause().getCause().getMessage()));
		}

	}
	
	public void validationSuperAssureur(ActionEvent actionEvent) {

		try {
			Integer param =  (Integer) actionEvent.getComponent().getAttributes().get("param");

			TaGedUtilisateur taGedUtilisateur = taGedUtilisateurService.findByIdDocAndByIdDevisRcPro(param, taDossierRcd.getIdDossierRcd());
			if(taGedUtilisateur.getValidationSuperAssureur()==null ||  taGedUtilisateur.getValidationSuperAssureur()==false  ) {
			taGedUtilisateur.setValidationSuperAssureur(true);
			taGedUtilisateurService.merge(taGedUtilisateur);
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "GedUtilisateur", e.getCause().getCause().getCause().getCause().getMessage()));
		}

	}
	
	public void validationCourtier(ActionEvent actionEvent) {

		try {
			Integer param =  (Integer) actionEvent.getComponent().getAttributes().get("param");

			TaGedUtilisateur taGedUtilisateur = taGedUtilisateurService.findByIdDocAndByIdDevisRcPro(param, taDossierRcd.getIdDossierRcd());
			if(taGedUtilisateur.getValidationCourtier()==null ||  taGedUtilisateur.getValidationCourtier()==false  ) {
			taGedUtilisateur.setValidationCourtier(true);
			taGedUtilisateurService.merge(taGedUtilisateur);
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "GedUtilisateur", e.getCause().getCause().getCause().getCause().getMessage()));
		}

	}

	
	public StreamedContent getFile() throws FinderException {
		
//		TaGedUtilisateurDTO taGedUtilisateur =  taGedUtilisateurService.findByIdDocAndByIdContratRcProDTO(selectedTaListeRefDocDTO.getId(), masterEntity.getIdDossierRcd());
		TaGedUtilisateurDTO taGedUtilisateur =  taGedUtilisateurService.findByIdDTO(selectedTaGedUtilisateurDTO.getId());
		InputStream stream = new ByteArrayInputStream(taGedUtilisateur.getFichierDoc());
		file = new DefaultStreamedContent(stream, "txt", taGedUtilisateur.getNomFichier());
        
        
        return file;
    }
	
	
	
	
	public void traiterContrat() throws FinderException {
		if(selectedTaDossierRcdDTO!=null) {
							
							taDossierRcd.setTraite(true);
							taDossierRcd.setContratEnCours(true);
							selectedTaDossierRcdDTO.setTraite(true);
							selectedTaDossierRcdDTO.setContratEnCours(true);
							
							TaDossierRcd contratOriginal; ;
							if(taDossierRcd.getContratOrigine() != null) {// Si ce contrat est un avenant
								if(taDossierRcd.getContratOrigine().getSuspenduAvenant() != true) { //Si le contrat d'origine n'est pas déja suspendu pour avenant (donc on est sur un 1er avenant)
									contratOriginal = taDossierRcd.getContratOrigine();
									contratOriginal.setContratEnCours(false);
									contratOriginal.setSuspenduAvenant(true);
									IDonneStatut dos = taDossierRcdService.donneStatut(contratOriginal);
									contratOriginal.getTaTStatut().clear();
									for (TaTStatut stat : dos.getTaTStatut()) {
										contratOriginal.getTaTStatut().add(stat);
									}
									taDossierRcdService.merge(contratOriginal);
								}//On est sur un 2eme avenant, on ne fait rien de plus au contrat d'orgine	
								
							}
							
							actEnregistrer(null);
			
		}		
	}
	
	public void remettreEnVigueur() {
		if(selectedTaDossierRcdDTO != null) {
			selectedTaDossierRcdDTO.setResilieAmiableContrat(null);
			selectedTaDossierRcdDTO.setResilieCessationActiviteContrat(null);
			selectedTaDossierRcdDTO.setResilieEcheanceContrat(null);
			selectedTaDossierRcdDTO.setResilieFausseDeclarationContrat(null);
			selectedTaDossierRcdDTO.setResilieNonPaiementContrat(null);
			selectedTaDossierRcdDTO.setResilieSansEffetContrat(null);
			selectedTaDossierRcdDTO.setMisEnDemeure(null);
			selectedTaDossierRcdDTO.setSuspenduNonPaiement(null);
			
			selectedTaDossierRcdDTO.setDateResiliation(null);
			
			taDossierRcd.setResilieAmiableContrat(null);
			taDossierRcd.setResilieCessationActiviteContrat(null);
			taDossierRcd.setResilieEcheanceContrat(null);
			taDossierRcd.setResilieFausseDeclarationContrat(null);
			taDossierRcd.setResilieNonPaiementContrat(null);
			taDossierRcd.setResilieSansEffetContrat(null);
			taDossierRcd.setMisEnDemeure(null);
			taDossierRcd.setSuspenduNonPaiement(null);
			
			taDossierRcd.setDateResiliation(null);
			
			selectedTaDossierRcdDTO.setContratEnCours(true);
			taDossierRcd.setContratEnCours(true);
			
			actEnregistrer(null);
		}
	}
	

	
	public void resilieEcheance() {
		resilieEcheance(null);
	}

	public void resilieEcheance(ActionEvent e)  {

		if(selectedTaDossierRcdDTO!=null && selectedTaDossierRcdDTO.getDateResiliation() != null) {
				taDossierRcd.setResilieEcheanceContrat(selectedTaDossierRcdDTO.getDateResiliation());
				selectedTaDossierRcdDTO.setResilieEcheanceContrat(selectedTaDossierRcdDTO.getDateResiliation());
				
				
				taDossierRcd.setDateResiliation(selectedTaDossierRcdDTO.getDateResiliation());
				
				selectedTaDossierRcdDTO.setContratEnCours(false);
				taDossierRcd.setContratEnCours(false);
				
				actEnregistrer(null);
				//envoi de mail
				envoiMailTestService.envoiMailResiliationEcheance(taDossierRcd.getTaCourtier(), taDossierRcd, null);
			
		}else {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Résiliation échouée", "Veuillez choisir une date de résiliation."));
		}		
	}
	public void resilieAmiable() {
		resilieAmiable(null);
	}
	public void resilieAmiable(ActionEvent e) {
		if(selectedTaDossierRcdDTO!=null && selectedTaDossierRcdDTO.getDateResiliation() != null) {
				Date date = new Date();
				taDossierRcd.setResilieAmiableContrat(selectedTaDossierRcdDTO.getDateResiliation());
				selectedTaDossierRcdDTO.setResilieAmiableContrat(selectedTaDossierRcdDTO.getDateResiliation());
				
				taDossierRcd.setDateResiliation(selectedTaDossierRcdDTO.getDateResiliation());
				
				selectedTaDossierRcdDTO.setContratEnCours(false);
				taDossierRcd.setContratEnCours(false);
				actEnregistrer(null);
				//envoi de mail
				envoiMailTestService.envoiMailResiliationAmiable(taDossierRcd.getTaCourtier(), taDossierRcd, null);
			
				
		}else {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Résiliation échouée", "Veuillez choisir une date de résiliation."));
		}			
	}
	public void resilieCessationActivite() {
		resilieCessationActivite(null);
	}
	public void resilieCessationActivite(ActionEvent e) {
		if(selectedTaDossierRcdDTO!=null && selectedTaDossierRcdDTO.getDateResiliation() != null) {
				taDossierRcd.setResilieCessationActiviteContrat(selectedTaDossierRcdDTO.getDateResiliation());
				selectedTaDossierRcdDTO.setResilieCessationActiviteContrat(selectedTaDossierRcdDTO.getDateResiliation());
				
				taDossierRcd.setDateResiliation(selectedTaDossierRcdDTO.getDateResiliation());
				
				selectedTaDossierRcdDTO.setContratEnCours(false);
				taDossierRcd.setContratEnCours(false);
				actEnregistrer(null);
				//envoi de mail
				envoiMailTestService.envoiMailResiliationCessation(taDossierRcd.getTaCourtier(), taDossierRcd, null);
			
		}		
	}
	public void resilieFausseDeclaration() {
		resilieFausseDeclaration(null);
	}
	public void resilieFausseDeclaration(ActionEvent e) {
		if(selectedTaDossierRcdDTO!=null && selectedTaDossierRcdDTO.getDateResiliation() != null) {
				taDossierRcd.setResilieFausseDeclarationContrat(selectedTaDossierRcdDTO.getDateResiliation());
				selectedTaDossierRcdDTO.setResilieFausseDeclarationContrat(selectedTaDossierRcdDTO.getDateResiliation());
				
				taDossierRcd.setDateResiliation(selectedTaDossierRcdDTO.getDateResiliation());
				
				selectedTaDossierRcdDTO.setContratEnCours(false);
				taDossierRcd.setContratEnCours(false);
				actEnregistrer(null);
				//envoi de mail
				envoiMailTestService.envoiMailResiliationFausseDecla(taDossierRcd.getTaCourtier(), taDossierRcd, null);
			
		}		
	}
	public void resilieSansEffet() {
		resilieSansEffet(null);
	}
	public void resilieSansEffet(ActionEvent event) {
		if(selectedTaDossierRcdDTO!=null && selectedTaDossierRcdDTO.getDateResiliation() != null) {
				taDossierRcd.setResilieSansEffetContrat(selectedTaDossierRcdDTO.getDateResiliation());
				selectedTaDossierRcdDTO.setResilieSansEffetContrat(selectedTaDossierRcdDTO.getDateResiliation());
				
				taDossierRcd.setDateResiliation(selectedTaDossierRcdDTO.getDateResiliation());
				
				selectedTaDossierRcdDTO.setContratEnCours(false);
				taDossierRcd.setContratEnCours(false);
				
				IDonneStatut Idossier;
				try {
					Idossier = taDossierRcdService.donneStatut(taDossierRcd);
					taDossierRcd.getTaTStatut().clear();
					selectedTaDossierRcdDTO.getTaTStatut().clear();
		        	for (TaTStatut stat : Idossier.getTaTStatut()) {
		        		taDossierRcd.getTaTStatut().add(stat);
		        		selectedTaDossierRcdDTO.getTaTStatut().add(stat);
					}
				} catch (FinderException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				
				TaEcheance e;
				try {
					e = taEcheanceService.findFirstEcheanceNonPayer(taDossierRcd.getIdDossierRcd());
					if(e != null) {
						e = taEcheanceService.donneFraisResilieSansEffet(e);
						
						
					}
					List<TaEcheance> listeEcheance = new ArrayList<TaEcheance>();
					for (TaEcheance ech : taDossierRcd.getTaEcheances()) {
						if(ech.getIdEcheance() == e.getIdEcheance()) {
							listeEcheance.add(e);
						}else {
							listeEcheance.add(ech);
						}
					}
					taDossierRcd.getTaEcheances().clear();
					taDossierRcd.setTaEcheances(listeEcheance);
				} catch (Exception e1) {
					System.out.println("échec du findFirstEcheanceNonPayer");
					e1.printStackTrace();
				}
				
				taDossierRcd = taDossierRcdService.merge(taDossierRcd);
				//envoi de mail
				envoiMailTestService.envoiMailResiliationSansEffetAnnuler(taDossierRcd.getTaCourtier(), taDossierRcd, null);
				remplieListeEcheanceDTO();
//				actEnregistrer(null);
			
		}		
	}
	
	/*
	 * ------------------------------------------------------------------------------------
	 */
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

	public TaDossierRcd getTaDossierRcd() {
		return taDossierRcd;
	}

	public void setTaDossierRcd(TaDossierRcd taCourtier) {
		this.taDossierRcd = taCourtier;
	}

	public TabView getTabViewContratRcPro() {
		return tabViewContratRcPro;
	}

	public void setTabViewContratRcPro(TabView tabViewContratRcPro) {
		this.tabViewContratRcPro = tabViewContratRcPro;
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
	
	public TaDossierRcdDTO[] getSelectedTaContratRcProDTOs() {
		return selectedTaDossierRcdDTOs;
	}

	public void setSelectedTaContratRcProDTOs(TaDossierRcdDTO[] selectedTaDossierRcdDTOs) {
		this.selectedTaDossierRcdDTOs = selectedTaDossierRcdDTOs;
	}

	public TaDossierRcdDTO getNewTaContratRcProDTO() {
		return newTaDossierRcdDTO;
	}

	public void setNewTaContratRcProDTO(TaDossierRcdDTO newTaDossierRcdDTO) {
		this.newTaDossierRcdDTO = newTaDossierRcdDTO;
	}

	public TaDossierRcdDTO getSelectedTaContratRcProDTO() {
		return selectedTaDossierRcdDTO;
	}

	public void setSelectedTaContratRcProDTO(TaDossierRcdDTO selectedTaDossierRcdDTO) {
		this.selectedTaDossierRcdDTO = selectedTaDossierRcdDTO;
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

	public TaGedUtilisateur getSelectedTaGedUtilisateur() {
		return selectedTaGedUtilisateur;
	}

	public void setSelectedTaGedUtilisateur(TaGedUtilisateur selectedTaGedUtilisateur) {
		this.selectedTaGedUtilisateur = selectedTaGedUtilisateur;
	}

	public TaListeRefDoc getSelectedTaListeRefDoc() {
		return selectedTaListeRefDoc;
	}

	public void setSelectedTaListeRefDoc(TaListeRefDoc selectedTaListeRefDoc) {
		this.selectedTaListeRefDoc = selectedTaListeRefDoc;
	}

	public TaAssureDTO getTaAssureDTO() {
		return taAssureDTO;
	}

	public void setTaAssureDTO(TaAssureDTO taAssureDTO) {
		this.taAssureDTO = taAssureDTO;
	}

	public TaAssure getTaAssure() {
		return taAssure;
	}

	public void setTaAssure(TaAssure taAssure) {
		this.taAssure = taAssure;
	}

	public TaCourtier getTaCourtier() {
		return taCourtier;
	}

	public void setTaCourtier(TaCourtier taCourtier) {
		this.taCourtier = taCourtier;
	}

	public TaCourtierDTO getTaCourtierDTO() {
		return taCourtierDTO;
	}

	public void setTaCourtierDTO(TaCourtierDTO taCourtierDTO) {
		this.taCourtierDTO = taCourtierDTO;
	}

	public List<TaActiviteDTO> getSelectedTaActiviteDTOs() {
		return selectedTaActiviteDTOs;
	}

	public void setSelectedTaActiviteDTOs(List<TaActiviteDTO> selectedTaActiviteDTOs) {
		this.selectedTaActiviteDTOs = selectedTaActiviteDTOs;
	}

	public List<TaActiviteDTO> getTaActiviteDTODisponible() {
		return taActiviteDTODisponible;
	}

	public void setTaActiviteDTODisponible(List<TaActiviteDTO> taActiviteDTODisponible) {
		this.taActiviteDTODisponible = taActiviteDTODisponible;
	}

	public GedContratRcdController getGedContratRcProController() {
		return gedContratRcProController;
	}

	public void setGedContratRcProController(GedContratRcdController gedContratRcProController) {
		this.gedContratRcProController = gedContratRcProController;
	}

	public TaDossierRcd getTaContratRcProDetail() {
		return taDossierRcd;
	}

	public void setTaContratRcProDetail(TaDossierRcd taContratRcPro) {
		this.taDossierRcd = taContratRcPro;
	}

	public TaSinistreAntecedent getSelectedTaSinstreAntecedent() {
		return selectedTaSinstreAntecedent;
	}

	public void setSelectedTaSinstreAntecedent(TaSinistreAntecedent selectedTaSinstreAntecedent) {
		this.selectedTaSinstreAntecedent = selectedTaSinstreAntecedent;
	}

	public List<TaSinistreAntecedent> getListeTaSinstreAntecedent() {
		return listeTaSinstreAntecedent;
	}

	public void setListeTaSinstreAntecedent(List<TaSinistreAntecedent> listeTaSinstreAntecedent) {
		this.listeTaSinstreAntecedent = listeTaSinstreAntecedent;
	}

	public List<TaTReglement> getListeTaTReglement() {
		return listeTaTReglement;
	}

	public void setListeTaTReglement(List<TaTReglement> listeTReglement) {
		this.listeTaTReglement = listeTReglement;
	}

	public List<TaTEcheance> getListeTaTEcheance() {
		return listeTaTEcheance;
	}

	public void setListeTaTEcheance(List<TaTEcheance> listeTEcheance) {
		this.listeTaTEcheance = listeTEcheance;
	}

	public TaTReglement getTaTReglement() {
		return taTReglement;
	}

	public void setTaTReglement(TaTReglement taTReglement) {
		this.taTReglement = taTReglement;
	}

	public TaTEcheance getTaTEcheance() {
		return taTEcheance;
	}

	public void setTaTEcheance(TaTEcheance taTEcheance) {
		this.taTEcheance = taTEcheance;
	}

	public List<TaTSousTraitance> getListeTaTSousTraitance() {
		return listeTaTSousTraitance;
	}

	public void setListeTaPartSoustraitance(List<TaTSousTraitance> listeTaTSousTraitance) {
		this.listeTaTSousTraitance = listeTaTSousTraitance;
	}

	public TaTSousTraitance getTaPartSoustraitance() {
		return taTSousTraitance;
	}

	public void setTaTSousTraitance(TaTSousTraitance taPartSoustraitance) {
		this.taTSousTraitance = taPartSoustraitance;
	}

	public List<TaTauxRcproPibDTO> getSelectedTaTauxRcproPibDTOs() {
		return selectedTaTauxRcproPibDTOs;
	}

	public void setSelectedTaTauxRcproPibDTOs(List<TaTauxRcproPibDTO> selectedTaTauxRcproPibDTOs) {
		this.selectedTaTauxRcproPibDTOs = selectedTaTauxRcproPibDTOs;
	}

	public List<TaTauxRcproPibDTO> getTaTauxRcproPibDTODisponible() {
		return taTauxRcproPibDTODisponible;
	}

	public void setTaTauxRcproPibDTODisponible(List<TaTauxRcproPibDTO> taTauxRcproPibDTODisponible) {
		this.taTauxRcproPibDTODisponible = taTauxRcproPibDTODisponible;
	}

	public List<TaTFranchise> getListeTaTFranchise() {
		return listeTaTFranchise;
	}

	public void setListeTaTFranchise(List<TaTFranchise> listeTaTFranchise) {
		this.listeTaTFranchise = listeTaTFranchise;
	}

	public TaTFranchise getTaTFranchise() {
		return taTFranchise;
	}

	public void setTaTFranchise(TaTFranchise taTFranchise) {
		this.taTFranchise = taTFranchise;
	}

	public String getLogDebugCalculPrime() {
		return logDebugCalculPrime;
	}

	public void setLogDebugCalculPrime(String logDebugCalculPrime) {
		this.logDebugCalculPrime = logDebugCalculPrime;
	}

	public TaTAssure getTaTAssure() {
		return taTAssure;
	}

	public void setTaTAssure(TaTAssure taTAssure) {
		this.taTAssure = taTAssure;
	}

	public TaTAssureDTO getTaTAssureDTO() {
		return taTAssureDTO;
	}

	public void setTaTAssureDTO(TaTAssureDTO taTAssureDTO) {
		this.taTAssureDTO = taTAssureDTO;
	}

	public TaAssure getTaAssureParamInsertion() {
		return taAssureParamInsertion;
	}

	public void setTaAssureParamInsertion(TaAssure taAssureParamInsertion) {
		this.taAssureParamInsertion = taAssureParamInsertion;
	}

	public DataTable getActiviteDatatable() {
		return activiteDatatable;
	}

	public void setActiviteDatatable(DataTable activiteDatatable) {
		this.activiteDatatable = activiteDatatable;
	}

	public String getLogDebugCalculPremierReglement() {
		return logDebugCalculPremierReglement;
	}

	public void setLogDebugCalculPremierReglement(String logDebugCalculPremierReglement) {
		this.logDebugCalculPremierReglement = logDebugCalculPremierReglement;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public String getSelectedMontantMarcheTravauxHTMin() {
		return selectedMontantMarcheTravauxHTMin;
	}

	public void setSelectedMontantMarcheTravauxHTMin(String selectedMontantMarcheTravauxHTMin) {
		this.selectedMontantMarcheTravauxHTMin = selectedMontantMarcheTravauxHTMin;
	}

	public String getSelectedSousTraitance1975() {
		return selectedSousTraitance1975;
	}

	public void setSelectedSousTraitance1975(String selectedSousTraitance1975) {
		this.selectedSousTraitance1975 = selectedSousTraitance1975;
	}

	public String getSelectedAntecedantRCDEnCours() {
		return selectedAntecedantRCDEnCours;
	}

	public void setSelectedAntecedantRCDEnCours(String selectedAntecedantRCDEnCours) {
		this.selectedAntecedantRCDEnCours = selectedAntecedantRCDEnCours;
	}

	public String getSelectedAntecedantRCEEnCours() {
		return selectedAntecedantRCEEnCours;
	}

	public void setSelectedAntecedantRCEEnCours(String selectedAntecedantRCEEnCours) {
		this.selectedAntecedantRCEEnCours = selectedAntecedantRCEEnCours;
	}

	public String getSelectedAntecedantMotifRCDAssure() {
		return selectedAntecedantMotifRCDAssure;
	}

	public void setSelectedAntecedantMotifRCDAssure(String selectedAntecedantMotifRCDAssure) {
		this.selectedAntecedantMotifRCDAssure = selectedAntecedantMotifRCDAssure;
	}

	public String getSelectedAntecedantMotifRCDAssureur() {
		return selectedAntecedantMotifRCDAssureur;
	}

	public void setSelectedAntecedantMotifRCDAssureur(String selectedAntecedantMotifRCDAssureur) {
		this.selectedAntecedantMotifRCDAssureur = selectedAntecedantMotifRCDAssureur;
	}

	public String getSelectedAntecedantMotifRCEAssure() {
		return selectedAntecedantMotifRCEAssure;
	}

	public void setSelectedAntecedantMotifRCEAssure(String selectedAntecedantMotifRCEAssure) {
		this.selectedAntecedantMotifRCEAssure = selectedAntecedantMotifRCEAssure;
	}

	public String getSelectedAntecedantMotifRCEAssureur() {
		return selectedAntecedantMotifRCEAssureur;
	}

	public void setSelectedAntecedantMotifRCEAssureur(String selectedAntecedantMotifRCEAssureur) {
		this.selectedAntecedantMotifRCEAssureur = selectedAntecedantMotifRCEAssureur;
	}

	public String getSelectedAntecedantRCDResilieParAssure() {
		return selectedAntecedantRCDResilieParAssure;
	}

	public void setSelectedAntecedantRCDResilieParAssure(String selectedAntecedantRCDResilieParAssure) {
		this.selectedAntecedantRCDResilieParAssure = selectedAntecedantRCDResilieParAssure;
	}

	public String getSelectedAntecedantRCDResilieParAssureur() {
		return selectedAntecedantRCDResilieParAssureur;
	}

	public void setSelectedAntecedantRCDResilieParAssureur(String selectedAntecedantRCDResilieParAssureur) {
		this.selectedAntecedantRCDResilieParAssureur = selectedAntecedantRCDResilieParAssureur;
	}

	public String getSelectedAntecedantRCEResilieParAssure() {
		return selectedAntecedantRCEResilieParAssure;
	}

	public void setSelectedAntecedantRCEResilieParAssure(String selectedAntecedantRCEResilieParAssure) {
		this.selectedAntecedantRCEResilieParAssure = selectedAntecedantRCEResilieParAssure;
	}

	public String getSelectedAntecedantRCEResilieParAssureur() {
		return selectedAntecedantRCEResilieParAssureur;
	}

	public void setSelectedAntecedantRCEResilieParAssureur(String selectedAntecedantRCEResilieParAssureur) {
		this.selectedAntecedantRCEResilieParAssureur = selectedAntecedantRCEResilieParAssureur;
	}

	public String getSelectedInteruptionAssurance() {
		return selectedInteruptionAssurance;
	}

	public void setSelectedInteruptionAssurance(String selectedInteruptionAssurance) {
		this.selectedInteruptionAssurance = selectedInteruptionAssurance;
	}

	public String getSelectedReprisePasse() {
		return selectedReprisePasse;
	}

	public void setSelectedReprisePasse(String selectedReprisePasse) {
		this.selectedReprisePasse = selectedReprisePasse;
	}

	public TaTAssure getTaTAssureParamInsertion() {
		return taTAssureParamInsertion;
	}

	public void setTaTAssureParamInsertion(TaTAssure taTAssureParamInsertion) {
		this.taTAssureParamInsertion = taTAssureParamInsertion;
	}

	public boolean isOnglet1Valide() {
		return onglet1Valide;
	}

	public void setOnglet1Valide(boolean onglet1Valide) {
		this.onglet1Valide = onglet1Valide;
	}

	public boolean isOnglet2Valide() {
		return onglet2Valide;
	}

	public void setOnglet2Valide(boolean onglet2Valide) {
		this.onglet2Valide = onglet2Valide;
	}

	public boolean isOnglet3Valide() {
		return onglet3Valide;
	}

	public void setOnglet3Valide(boolean onglet3Valide) {
		this.onglet3Valide = onglet3Valide;
	}

	public boolean isOnglet4Valide() {
		return onglet4Valide;
	}

	public void setOnglet4Valide(boolean onglet4Valide) {
		this.onglet4Valide = onglet4Valide;
	}

	public boolean isOnglet5Valide() {
		return onglet5Valide;
	}

	public void setOnglet5Valide(boolean onglet5Valide) {
		this.onglet5Valide = onglet5Valide;
	}

	public boolean isOnglet6Valide() {
		return onglet6Valide;
	}

	public void setOnglet6Valide(boolean onglet6Valide) {
		this.onglet6Valide = onglet6Valide;
	}

	public boolean isOnglet7Valide() {
		return onglet7Valide;
	}

	public void setOnglet7Valide(boolean onglet7Valide) {
		this.onglet7Valide = onglet7Valide;
	}

	public boolean isOnglet8Valide() {
		return onglet8Valide;
	}

	public void setOnglet8Valide(boolean onglet8Valide) {
		this.onglet8Valide = onglet8Valide;
	}

	public boolean isOnglet9Valide() {
		return onglet9Valide;
	}

	public void setOnglet9Valide(boolean onglet9Valide) {
		this.onglet9Valide = onglet9Valide;
	}

	public List<TaActiviteDTO> getListeTaActiviteDTODetail() {
		return listeTaActiviteDTODetail;
	}

	public void setListeTaActiviteDTODetail(List<TaActiviteDTO> listeTaActiviteDTODetail) {
		this.listeTaActiviteDTODetail = listeTaActiviteDTODetail;
	}

	public BigDecimal getTotalPourcentageActiviteEntreprise() {
		return totalPourcentageActiviteEntreprise;
	}

	public void setTotalPourcentageActiviteEntreprise(BigDecimal totalPourcentageActiviteEntreprise) {
		this.totalPourcentageActiviteEntreprise = totalPourcentageActiviteEntreprise;
	}

	public BigDecimal getTotalPourcentageActiviteSousTraite() {
		return totalPourcentageActiviteSousTraite;
	}

	public void setTotalPourcentageActiviteSousTraite(BigDecimal totalPourcentageActiviteSousTraite) {
		this.totalPourcentageActiviteSousTraite = totalPourcentageActiviteSousTraite;
	}

	public BigDecimal getTotalPourcentageActivite() {
		return totalPourcentageActivite;
	}

	public void setTotalPourcentageActivite(BigDecimal totalPourcentageActivite) {
		this.totalPourcentageActivite = totalPourcentageActivite;
	}

	public int getNbActiviteMax() {
		return nbActiviteMax;
	}

	public void setNbActiviteMax(int nbActiviteMax) {
		this.nbActiviteMax = nbActiviteMax;
	}

	public List<TaTGarantieSinistre> getListeTaTGarantieSinistre() {
		return listeTaTGarantieSinistre;
	}

	public void setListeTaTGarantieSinistre(List<TaTGarantieSinistre> listeTaTGarantieSinistre) {
		this.listeTaTGarantieSinistre = listeTaTGarantieSinistre;
	}

	public BigDecimal getMontantCommisionPrevu() {
		return montantCommisionPrevu;
	}

	public void setMontantCommisionPrevu(BigDecimal montantCommisionPrevu) {
		this.montantCommisionPrevu = montantCommisionPrevu;
	}
	public List<TaGedUtilisateurDTO> getListeTaGedUtilisateurDTO() {
		return listeTaGedUtilisateurDTO;
	}

	public void setListeTaGedUtilisateurDTO(List<TaGedUtilisateurDTO> listeTaGedUtilisateurDTO) {
		this.listeTaGedUtilisateurDTO = listeTaGedUtilisateurDTO;
	}

	public List<TaGedUtilisateur> getListeTaGedUtilisateur() {
		return listeTaGedUtilisateur;
	}

	public void setListeTaGedUtilisateur(List<TaGedUtilisateur> listeTaGedUtilisateur) {
		this.listeTaGedUtilisateur = listeTaGedUtilisateur;
	}

	public boolean isOnglet5_1Valide() {
		return onglet5_1Valide;
	}

	public void setOnglet5_1Valide(boolean onglet5_1Valide) {
		this.onglet5_1Valide = onglet5_1Valide;
	}

	public boolean isOnglet5_2Valide() {
		return onglet5_2Valide;
	}

	public void setOnglet5_2Valide(boolean onglet5_2Valide) {
		this.onglet5_2Valide = onglet5_2Valide;
	}

	public Date getDateLimiteRepriseDuPasse() {
		return dateLimiteRepriseDuPasse;
	}

	public void setDateLimiteRepriseDuPasse(Date dateLimiteRepriseDuPasse) {
		this.dateLimiteRepriseDuPasse = dateLimiteRepriseDuPasse;
	}

	public boolean isAfficheBaseAntecedentRcd() {
		return afficheBaseAntecedentRcd;
	}

	public void setAfficheBaseAntecedentRcd(boolean afficheBaseAntecedentRcd) {
		this.afficheBaseAntecedentRcd = afficheBaseAntecedentRcd;
	}

	public boolean isAfficheMotifResilieAssureRcd() {
		return afficheMotifResilieAssureRcd;
	}

	public void setAfficheMotifResilieAssureRcd(boolean afficheMotifResilieAssureRcd) {
		this.afficheMotifResilieAssureRcd = afficheMotifResilieAssureRcd;
	}

	public boolean isAfficheMotifResilieAssureurRcd() {
		return afficheMotifResilieAssureurRcd;
	}

	public void setAfficheMotifResilieAssureurRcd(boolean afficheMotifResilieAssureurRcd) {
		this.afficheMotifResilieAssureurRcd = afficheMotifResilieAssureurRcd;
	}

	public boolean isAfficheBaseAntecedentRce() {
		return afficheBaseAntecedentRce;
	}

	public void setAfficheBaseAntecedentRce(boolean afficheBaseAntecedentRce) {
		this.afficheBaseAntecedentRce = afficheBaseAntecedentRce;
	}

	public boolean isAfficheMotifResilieAssureRce() {
		return afficheMotifResilieAssureRce;
	}

	public void setAfficheMotifResilieAssureRce(boolean afficheMotifResilieAssureRce) {
		this.afficheMotifResilieAssureRce = afficheMotifResilieAssureRce;
	}

	public boolean isAfficheMotifResilieAssureurRce() {
		return afficheMotifResilieAssureurRce;
	}

	public void setAfficheMotifResilieAssureurRce(boolean afficheMotifResilieAssureurRce) {
		this.afficheMotifResilieAssureurRce = afficheMotifResilieAssureurRce;
	}

	public boolean isAfficheResilieRcd() {
		return afficheResilieRcd;
	}

	public void setAfficheResilieRcd(boolean afficheResilieRcd) {
		this.afficheResilieRcd = afficheResilieRcd;
	}

	public boolean isAfficheResilieRce() {
		return afficheResilieRce;
	}

	public void setAfficheResilieRce(boolean afficheResilieRce) {
		this.afficheResilieRce = afficheResilieRce;
	}

	public TaGedUtilisateurDTO getSelectedTaGedUtilisateurDTO() {
		return selectedTaGedUtilisateurDTO;
	}

	public void setSelectedTaGedUtilisateurDTO(TaGedUtilisateurDTO selectedTaGedUtilisateurDTO) {
		this.selectedTaGedUtilisateurDTO = selectedTaGedUtilisateurDTO;
	}

	public List<TaGedUtilisateurDTO> getListTaGedUtilisateurDTO() {
		return listTaGedUtilisateurDTO;
	}

	public void setListTaGedUtilisateurDTO(List<TaGedUtilisateurDTO> listTaGedUtilisateurDTO) {
		this.listTaGedUtilisateurDTO = listTaGedUtilisateurDTO;
	}

	public List<TaEcheanceDTO> getListeTaEcheanceDTO() {
		return listeTaEcheanceDTO;
	}

	public void setListeTaEcheanceDTO(List<TaEcheanceDTO> listeTaEcheanceDTO) {
		this.listeTaEcheanceDTO = listeTaEcheanceDTO;
	}

	public List<TaDossierRcdDTO> getListeAvenantTaDossierRcdDTO() {
		return listeAvenantTaDossierRcdDTO;
	}

	public void setListeAvenantTaDossierRcdDTO(List<TaDossierRcdDTO> listeAvenantTaDossierRcdDTO) {
		this.listeAvenantTaDossierRcdDTO = listeAvenantTaDossierRcdDTO;
	}

	public TaEcheanceDTO getSelectedTaEcheanceDTO() {
		return selectedTaEcheanceDTO;
	}

	public void setSelectedTaEcheanceDTO(TaEcheanceDTO selectedTaEcheanceDTO) {
		this.selectedTaEcheanceDTO = selectedTaEcheanceDTO;
	}

	public DevisRcdController getDevisRcdController() {
		return devisRcdController;
	}

	public void setDevisRcdController(DevisRcdController devisRcdController) {
		this.devisRcdController = devisRcdController;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public List<TaTStatut> getListeTaTStatut() {
		return listeTaTStatut;
	}

	public void setListeTaTStatut(List<TaTStatut> listeTaTStatut) {
		this.listeTaTStatut = listeTaTStatut;
	}

	public List<TaTStatut> getSelectedTStatut() {
		return selectedTStatut;
	}

	public void setSelectedTStatut(List<TaTStatut> selectedTStatut) {
		this.selectedTStatut = selectedTStatut;
	}


	public List<TaEcheance> getListeTaEcheance() {
		return listeTaEcheance;
	}

	public void setListeTaEcheance(List<TaEcheance> listeTaEcheance) {
		this.listeTaEcheance = listeTaEcheance;
	}

	public List<TaActiviteDTO> getSelectedTaActiviteDTOAttestationNominative() {
		return selectedTaActiviteDTOAttestationNominative;
	}

	public void setSelectedTaActiviteDTOAttestationNominative(
			List<TaActiviteDTO> selectedTaActiviteDTOAttestationNominative) {
		this.selectedTaActiviteDTOAttestationNominative = selectedTaActiviteDTOAttestationNominative;
	}

	public Boolean getAffichePremierOnglet() {
		return affichePremierOnglet;
	}

	public void setAffichePremierOnglet(Boolean affichePremierOnglet) {
		this.affichePremierOnglet = affichePremierOnglet;
	}

	public List<TaDossierRcdDTO> getValuesExtraction() {
		return valuesExtraction;
	}

	public void setValuesExtraction(List<TaDossierRcdDTO> valuesExtraction) {
		this.valuesExtraction = valuesExtraction;
	}


}  
