package fr.ylyade.courtage.controller;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.faces.application.FacesMessage;
//import javax.faces.view.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonReader;
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
import fr.ylyade.courtage.dto.TaDevisRcProDetailDTO;
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
import fr.ylyade.courtage.model.TaClasse;
import fr.ylyade.courtage.model.TaCourtier;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaDossierRcdActivite;
import fr.ylyade.courtage.model.TaDossierRcdTauxPib;
import fr.ylyade.courtage.model.TaEcheance;
import fr.ylyade.courtage.model.TaFamilleActivite;
import fr.ylyade.courtage.model.TaGedUtilisateur;
import fr.ylyade.courtage.model.TaListeRefDoc;
import fr.ylyade.courtage.model.TaPalierClasse;
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
import fr.ylyade.courtage.model.TaTauxAssurance;
import fr.ylyade.courtage.model.TaTauxRcproPib;
import fr.ylyade.courtage.model.mapper.TaDossierRcdMapper;
import fr.ylyade.courtage.model.mapping.LgrDozerMapper;
import fr.ylyade.courtage.service.TaGedUtilisateurService;
import fr.ylyade.courtage.service.TaListeRefDocService;
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
import fr.ylyade.courtage.service.interfaces.remote.ITaLgrMailjetServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaListeRefDocServiceRemote;
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
import fr.ylyade.courtage.service.interfaces.remote.ITaTauxAssuranceServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTauxRcproPibServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTelServiceRemote;

@Named
@ViewScoped  
public class DevisRcdController extends AbstractControllerRCD implements Serializable {  

	private static final long serialVersionUID = 6278107399024181110L;

	@Inject @Named(value="tabListModelCenterBean")
	private TabListModelBean tabsCenter;
	
	@Inject @Named(value="tabViewsBean")
	private TabViewsBean tabViews;
	
	private Boolean affichePremierOnglet = false;

	private String paramId;
	
	private TabView tabViewDevisRcPro; //Le Binding sur les onglets "secondaire" semble ne pas fonctionné, les onglets ne sont pas générés au bon moment avec le c:forEach

	private List<TaDossierRcdDTO> values; 
	private TaGedUtilisateur selectedTaGedUtilisateur;
	private TaListeRefDoc selectedTaListeRefDoc;
	
	private @Inject SessionInfo sessionInfo;
	@Inject
	protected Auth auth;
	private @Inject GedDevisRcdController gedDevisRcProController;
	
	protected ModeObjetEcranServeur modeEcran = new ModeObjetEcranServeur();
	
	@EJB private ITaLgrMailjetServiceRemote lgrMailjetService;
	
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
	protected @EJB ITaDossierRcdServiceRemote taDossierRcdService;
	private @EJB ITaEcheanceServiceRemote taEcheanceService;
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
	private @EJB ITaTauxAssuranceServiceRemote taTauxAssuranceService;
	private @EJB ITaTFraisImpayeServiceRemote taTFraisImpayeService;
	
	private @EJB ITaUtilisateurServiceRemote taUtilisateurService;
	private @EJB ITaGedUtilisateurServiceRemote taGedUtilisateurService;
	
	private @EJB ITaListeRefDocServiceRemote taListeRefDocService;
	
	private @EJB ITaSousDonneeServiceRemote taSousDonneeService;
	
	@EJB private ITaEnvoiMailTestServiceRemote envoiMailTestService;
	
	//private @EJB TimerServiceYlyade timerService;
	
//	private TaDossierRcdDTO[] selectedTaDossierRcdDTOs; 
////	private TaDossierRcd taDossierRcd = new TaDossierRcd();
//	private TaDossierRcdDTO newTaDossierRcdDTO = new TaDossierRcdDTO();
//	private TaDossierRcdDTO selectedTaDossierRcdDTO = new TaDossierRcdDTO();
	private boolean etudePourCauseLimiteSinistre = false;
	

	private TimeZone timeZone;

	
	private Boolean blocageChampsApresPremiereActionReglement = false;

//	private List<TaDevisRcProDetailDTO> valuesDetail;
	
	
	private List<TaTStatut> listeTaTStatut =  new ArrayList<TaTStatut>();
	
	private List<TaTStatut> selectedTStatut = new ArrayList<TaTStatut>();
	
	
	
	private List<TaActiviteDTO> selectedTaActiviteDTOs = new ArrayList<TaActiviteDTO>();
	private List<TaActiviteDTO> listeTaActiviteDTODetail = new ArrayList<TaActiviteDTO>();
	
	private List<TaActiviteDTO> tmpselectedTaActiviteDTOs = new ArrayList<TaActiviteDTO>();
	private List<TaTauxRcproPibDTO> tmpselectedTaTauxRcproPibDTOs = new ArrayList<TaTauxRcproPibDTO>();
	
	private List<TaActiviteDTO> taActiviteDTODisponible = new ArrayList<TaActiviteDTO>();
	
	private List<TaTauxRcproPibDTO> selectedTaTauxRcproPibDTOs = new ArrayList<TaTauxRcproPibDTO>();
	private List<TaTauxRcproPibDTO> taTauxRcproPibDTODisponible = new ArrayList<TaTauxRcproPibDTO>();
	
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
	
	private static final int NB_ACTIVITE_MAX_DEFAUT = 12;
	private static final int NB_ACTIVITE_MAX_AUTO_ENTREPRENEUR_DEFAUT = 12;
	//private static final int NB_ACTIVITE_MAX_AUTO_ENTREPRENEUR_LIMITE = 3;
	private static final int NB_ACTIVITE_MAX_AUTO_ENTREPRENEUR_LIMITE = 12;
	private int nbActiviteMax = NB_ACTIVITE_MAX_DEFAUT;
	
	private DataTable activiteDatatable;
	private Date now = new Date();
	private Date dateLimiteRepriseDuPasse = new Date();
	private Date dateLimiteCreationEts = new GregorianCalendar(1900, 0, 1).getTime();
	private Date dateLimiteNaissance = new GregorianCalendar(1900, 0, 1).getTime();
	
	
	private Boolean pourEtude = false;
	
	
	/*
	 * --------------------------------------------------------------------
	 */
	private StreamedContent file;
	private TaListeRefDocDTO selectedTaListeRefDocDTO;
	
	private TaGedUtilisateurDTO selectedTaGedUtilisateurDTO;
	
	private TaGedUtilisateur taGedUtilisateur;
	
	private TaDossierRcd masterEntity;
	private List<TaListeRefDocDTO> listeByType;
	
	private List<TaGedUtilisateurDTO> listeTaGedUtilisateurDTO;
	private List<TaGedUtilisateur> listeTaGedUtilisateur;
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
	
	private boolean creationCompteClientAssure = false;
	
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
	private Boolean assureExistant = false;
	
	private Boolean enModifPrimeAssureur = false;
	

	
	private BigDecimal montantPj = BigDecimal.ZERO;
	private BigDecimal montantPjEtendue = BigDecimal.ZERO;
	private BigDecimal montantPjEtenduePIB = BigDecimal.ZERO;
	
	
	public DevisRcdController() {  
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
		
		try {
			montantPj = taTauxAssuranceService.findByCode("Pj").getTauxTauxAssurance();
			montantPjEtendue = taTauxAssuranceService.findByCode("Pj_ETENDUE").getTauxTauxAssurance();
			montantPjEtenduePIB = taTauxAssuranceService.findByCode("Pj_ETENDUE_PIB").getTauxTauxAssurance();
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		refresh();
		donneTimeZone();
	}
	
	public void refresh() {
		refresh(true);
//		try {
//			timerService.doWork();
//		} catch (FinderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	public void refresh(Boolean fermeOnglet){
		
		if(fermeOnglet) {
			//supprime tout les onglets excepté les dashboards
			List<LgrTab> listeOnglet = tabsCenter.getOnglets();
			List<LgrTab> tabASupprimer = new ArrayList<LgrTab>();
			for (LgrTab lgrTab : listeOnglet) {
				if(lgrTab.getIdTab() != "idTabDashboardCourtier"
						&& lgrTab.getIdTab() != "idTabDashboardYlyade"
						&& lgrTab.getIdTab() != "idTabDashboardAssureur") {
					tabASupprimer.add(lgrTab);
				}
			}
			
			for (LgrTab lgrTab : tabASupprimer) {
				tabsCenter.getOnglets().remove(lgrTab);
			}
		}
		

		
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
			values = taDossierRcdService.findAllByIdCourtier(taCourtier.getIdCourtier());
		}else if(auth.isAssure()) {
			taAssure = taAssureService.findByIdUtilisateur(auth.getUser().getIdUtilisateur());
			values = taDossierRcdService.findAllByIdAssure(taAssure.getIdAssure());
		} else if(auth.isYlyade())   {
			values = taDossierRcdService.findAllLight();
		}else if(auth.isSuperAssureur())   {
			values = taDossierRcdService.findAllLight();
		}
		
		

		
		
		listeTaTStatut = taTStatutService.selectAll();
		
		
		taActiviteDTODisponible = taActiviteService.selectAllDTO();
		taTauxRcproPibDTODisponible = taTauxRcproPibService.selectAllDTO();
		
		listeTaTReglement = taTReglementService.selectAll();
		listeTaTEcheance = taTEcheanceService.selectAll();
		
		List<TaTSousTraitance> listeSt = taTSousTraitanceService.selectAll();
		listeSt.sort(Comparator.comparing(TaTSousTraitance::getBaseMin));
		listeTaTSousTraitance = listeSt;
		
		List<TaTFranchise> liste = new ArrayList<TaTFranchise>();
		liste = taTFranchiseService.selectAll();
		liste.sort(Comparator.comparing(TaTFranchise::getMontant));
		listeTaTFranchise = liste;	
		listeTaTGarantieSinistre = taTGarantieSinistre.selectAll();
//		
		modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
//    	infoEntreprise = taInfoEntrepriseService.findInstance();
//    	dateDebut = infoEntreprise.getDatedebInfoEntreprise();
//    	dateFin = infoEntreprise.getDatefinInfoEntreprise();
//
//    	dateDebutProforma = infoEntreprise.getDatedebInfoEntreprise();
//    	dateFinProforma = infoEntreprise.getDatefinInfoEntreprise();

	}
	public void actEnvoiMailPiecesInvalides() {
		if(taDossierRcd != null) {
			envoiMailTestService.envoiMailPieceInvalides(taDossierRcd.getTaCourtier(), taDossierRcd);
		}
		
	}
	public List<TaDossierRcdDTO> getValues(){  
		return values;
	}
	
	public Date maxDatePlus12Mois() {
		LocalDate localDateNow = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Date dateMax = Date.from((localDateNow).plus(12, ChronoUnit.MONTHS).atStartOfDay(ZoneId.systemDefault()).toInstant());
		return dateMax;
	}
	
	
	public void initAffichageAntecedent(AjaxBehaviorEvent e) {
		initAffichageAntecedent();
	}
	
	public void initAffichageAntecedent() {
		selectedTaDossierRcdDTO.setAntecedentRcdContratEnCours(true);
//		selectedAntecedantRCDEnCours = ANTECEDANT_RCD_EN_COURS;
		if(selectedAntecedantRCDEnCours!=null) {
			if(selectedAntecedantRCDEnCours.equals(ANTECEDANT_RCD_EN_COURS)) {
				selectedTaDossierRcdDTO.setAntecedentRcdContratEnCours(true);
				selectedTaDossierRcdDTO.setAntecedentRcdContratResilie(false);
			} else if(selectedAntecedantRCDEnCours.equals(ANTECEDANT_RCD_RESILIE)) {
				selectedTaDossierRcdDTO.setAntecedentRcdContratEnCours(false);
				selectedTaDossierRcdDTO.setAntecedentRcdContratResilie(true);
			}
		}
		
		afficheBaseAntecedentRcd = selectedTaDossierRcdDTO.getAssureMemeRisqueRcd();
		afficheResilieRcd = afficheBaseAntecedentRcd && selectedAntecedantRCDEnCours.equals(ANTECEDANT_RCD_RESILIE);
		afficheMotifResilieAssureRcd = afficheResilieRcd && selectedAntecedantRCDResilieParAssure.equals(ANTECEDANT_RCD_RESILIE_PAR_ASSURE);
		if(afficheMotifResilieAssureRcd) {
			selectedTaDossierRcdDTO.setAntecedentRcdResilieAssure(true);
			if(selectedAntecedantMotifRCDAssure==null) {
				selectedAntecedantMotifRCDAssure = MOTIF_RESILIATION_ASSURE_ECHEANCE;
			}
			actRadioAntecedantMotifRCDAssure();
		}
		afficheMotifResilieAssureurRcd = afficheResilieRcd && selectedAntecedantRCDResilieParAssure.equals(ANTECEDANT_RCD_RESILIE_PAR_ASSUREUR);
		if(afficheMotifResilieAssureurRcd) {
			selectedTaDossierRcdDTO.setAntecedentRcdAssureur(true);
			if(selectedAntecedantMotifRCDAssureur==null) {
				selectedAntecedantMotifRCDAssureur = MOTIF_RESILIATION_ASSUREUR_NON_PAIEMENT;
			}
			actRadioAntecedantMotifRCDAssureur();
		}
		
		afficheBaseAntecedentRce = selectedTaDossierRcdDTO.getAssureMemeRisqueRce();
		afficheResilieRce = afficheBaseAntecedentRce && selectedAntecedantRCEEnCours.equals(ANTECEDANT_RCE_RESILIE);
		afficheMotifResilieAssureRce = afficheResilieRce && selectedAntecedantRCEResilieParAssure.equals(ANTECEDANT_RCE_RESILIE_PAR_ASSURE);
		if(afficheMotifResilieAssureRce) {
			selectedTaDossierRcdDTO.setAntecedentRceResilieAssure(true);
			selectedAntecedantMotifRCEAssure = MOTIF_RESILIATION_ASSURE_ECHEANCE;
			actRadioAntecedantMotifRCEAssure();
		}
		afficheMotifResilieAssureurRce = afficheResilieRce && selectedAntecedantRCEResilieParAssureur.equals(ANTECEDANT_RCE_RESILIE_PAR_ASSUREUR);
		if(afficheMotifResilieAssureurRce) {
			selectedTaDossierRcdDTO.setAntecedentRceAssureur(true);
			selectedAntecedantMotifRCEAssureur = MOTIF_RESILIATION_ASSUREUR_NON_PAIEMENT;
			actRadioAntecedantMotifRCEAssureur();
		}
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
    
    public String passwordComplex () {
		String password = selectedTaDossierRcdDTO.getTaAssureDTO().getPassword();
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
    
//	public void actModificationActivite(AjaxBehaviorEvent event) {
//	    for (TaActiviteDTO a : selectedTaActiviteDTOs) {
//			System.out.println("** "+a.getCodeActivite());
//		}
//	    for (TaActiviteDTO a : selectedTaDossierRcdDTO.getListeActivite()) {
//			System.out.println("-- "+a.getCodeActivite());
//		}
//	    System.out.println("*********************************");
//	    calculMontantPrime();
//	}
	
	public void actModificationActivitePib(AjaxBehaviorEvent event) {
		System.out.println("Taille activités séléctionnez : "+selectedTaTauxRcproPibDTOs.size());
		if (selectedTaTauxRcproPibDTOs.size() > 2) {
			selectedTaTauxRcproPibDTOs.remove(selectedTaTauxRcproPibDTOs.size() - 1);
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("Vous ne pouvez pas séléctionner plus de 2 activités."));
            return;
        }
		
		System.out.println("Taille activités séléctionnez : "+selectedTaTauxRcproPibDTOs.size());
		
	    for (TaTauxRcproPibDTO a : selectedTaTauxRcproPibDTOs) {
			System.out.println("** "+a.getCodeTauxRcproPib());
		}
	    for (TaTauxRcproPibDTO a : selectedTaDossierRcdDTO.getListeTauxRcProPib()) {
			System.out.println("-- "+a.getCodeTauxRcproPib());
		}
	    System.out.println("*********************************");
	    calculMontantPrime(false);
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
		pourEtude = false;
		BigDecimal montantTotal = BigDecimal.ZERO;
//		Si 1 sinistre coût maximum 15 000 € + 10%
//		Si 2 sinistres coût maximum 20 000 € + 15 %
//		Si 3 sinistre coût maximum 30 000 + 20 %
//		Au-delà de 4 sinistres, demande soumis à étude.
//		if(taDossierRcd.getTaSinistreAntecedent()!=null && !taDossierRcd.getTaSinistreAntecedent().isEmpty()) {
//			BigDecimal montantTotal = BigDecimal.ZERO;
//			for (TaSinistreAntecedent taSinistreAntecedent : taDossierRcd.getTaSinistreAntecedent()) {
//				montantTotal = montantTotal.add(taSinistreAntecedent.getMontantSinistre());
//			}
//			if(taDossierRcd.getTaSinistreAntecedent().size()==1) {
//				if(montantTotal.compareTo(new BigDecimal(15000))<=0) {
//					taux = new BigDecimal(10);
//				} else {
//					pourEtude = true;
//				}
//			} else if(taDossierRcd.getTaSinistreAntecedent().size()==2) {
//				if(montantTotal.compareTo(new BigDecimal(20000))<=0) {
//					taux = new BigDecimal(15);
//				} else {
//					pourEtude = true;
//				}
//			} else if(taDossierRcd.getTaSinistreAntecedent().size()==3) {
//				if(montantTotal.compareTo(new BigDecimal(30000))<=0) {
//					taux = new BigDecimal(20);
//				} else {
//					pourEtude = true;
//				}
//			} else { // plus de 4
//				pourEtude = true;
//			}
//		}
//		etudePourCauseLimiteSinistre = pourEtude;
//		selectedTaDossierRcdDTO.setTauxAntecedentSinistre(taux);
//		return taux;
		
		//Nouvelle règle sinistre pour Leader UnderWriting
//Si 0 sinistre et déjà assuré sur les 24 derniers mois -15%
//De 1 à 3 sinistres cout max 10 000 € +15%
//De 1 à 3 sinistres pour un montant maxi de 10 000 à 25 000 40%
//Au-delà de 3 sinistres, demande soumis à étude.
		
		if(taDossierRcd.getTaSinistreAntecedent()==null || taDossierRcd.getTaSinistreAntecedent().isEmpty()) { //si pas de sinistres 
			if(selectedTaDossierRcdDTO.getAssureMemeRisque()) {//et déjà assuré sur les 24 derniers mois
				taux = new BigDecimal(-15);
			}
		}else {// si il y a des sinsitres
			if(taDossierRcd.getTaSinistreAntecedent().size() <= 3) {// si 1 à 3 sinistres
				for (TaSinistreAntecedent taSinistreAntecedent : taDossierRcd.getTaSinistreAntecedent()) {
					montantTotal = montantTotal.add(taSinistreAntecedent.getMontantSinistre());
				}
				
				if(montantTotal.compareTo(new BigDecimal(10000))<=0) {//si cout <10 000€
					taux = new BigDecimal(15);
				}else if(montantTotal.compareTo(new BigDecimal(10000))>=0 && montantTotal.compareTo(new BigDecimal(25000))<=0){//si cout entre 10 000 et 25 000
					taux = new BigDecimal(40);
				}else {// si cout > 25 000
//					pourEtude = true;
					FacesContext context = FacesContext.getCurrentInstance();  
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sinitres", "le coût des sinistres choisis est supérieur à 25 000 €."));
					
				}
				
			}else {// plus de 3 sinsitres
//				pourEtude = true;
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sinitres", "Il y a plus de 3 sinitres."));
			}
		}
		etudePourCauseLimiteSinistre = pourEtude;
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
		System.out.println("DevisRcdController.actRadioMontantMarcheTravauxHTMin() "+selectedMontantMarcheTravauxHTMin);
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
		if(assurePib()) {
			JSONObject jsonDATA = new JSONObject(taSousDonnee.getJsonData());
			selectedPJ = jsonDATA.get(TaSousDonnee.CLE_PJ_CHOISIE).toString();
			if(selectedPJ.equals(TaSousDonnee.VALEUR_PJ_CHOISIE_PJPRO)) {//si PJPRO
				selectedTaDossierRcdDTO.setLettrePjNumPolice(TaSousDonnee.VALEUR_LETTRE_NUM_POLICE_PJPRO);	
			}else if(selectedPJ.equals(TaSousDonnee.VALEUR_PJ_CHOISIE_PJPROETENDUE_PIB)){//si PJPRO ETENDUE	
				selectedTaDossierRcdDTO.setLettrePjNumPolice(TaSousDonnee.VALEUR_LETTRE_NUM_POLICE_PJPROETENDUE);
			}
			
			
		}else {
			JSONObject jsonDATA = new JSONObject(taSousDonnee.getJsonData());
			selectedPJ = jsonDATA.get(TaSousDonnee.CLE_PJ_CHOISIE).toString();
			if(selectedPJ.equals(TaSousDonnee.VALEUR_PJ_CHOISIE_PJPRO)) {//si PJPRO
				selectedTaDossierRcdDTO.setLettrePjNumPolice(TaSousDonnee.VALEUR_LETTRE_NUM_POLICE_PJPRO);	
			}else if(selectedPJ.equals(TaSousDonnee.VALEUR_PJ_CHOISIE_PJPROETENDUE)){//si PJPRO ETENDUE	
				selectedTaDossierRcdDTO.setLettrePjNumPolice(TaSousDonnee.VALEUR_LETTRE_NUM_POLICE_PJPROETENDUE);
			}
			
		}
		
		
	}
	public void actRadioProtectionJuridique() {
		JSONObject jsonDATA = new JSONObject(taSousDonnee.getJsonData());
		if(assurePib()) {
			if(selectedPJ != null) {
				jsonDATA.put(TaSousDonnee.CLE_PJ_CHOISIE, selectedPJ);
				if(selectedPJ.equals(TaSousDonnee.VALEUR_PJ_CHOISIE_PJPRO)) {//si PJPRO
					selectedTaDossierRcdDTO.setLettrePjNumPolice(TaSousDonnee.VALEUR_LETTRE_NUM_POLICE_PJPRO);	
				}else if(selectedPJ.equals(TaSousDonnee.VALEUR_PJ_CHOISIE_PJPROETENDUE_PIB)){//si PJPRO ETENDUE	
					selectedTaDossierRcdDTO.setLettrePjNumPolice(TaSousDonnee.VALEUR_LETTRE_NUM_POLICE_PJPROETENDUE);
				}
			}else {
				jsonDATA.put(TaSousDonnee.CLE_PJ_CHOISIE, "");
				selectedTaDossierRcdDTO.setLettrePjNumPolice("");
			}
			
		}else {
			if(selectedPJ != null) {
				jsonDATA.put(TaSousDonnee.CLE_PJ_CHOISIE, selectedPJ);
				if(selectedPJ.equals(TaSousDonnee.VALEUR_PJ_CHOISIE_PJPRO)) {//si PJPRO
					selectedTaDossierRcdDTO.setLettrePjNumPolice(TaSousDonnee.VALEUR_LETTRE_NUM_POLICE_PJPRO);	
				}else if(selectedPJ.equals(TaSousDonnee.VALEUR_PJ_CHOISIE_PJPROETENDUE)){//si PJPRO ETENDUE	
					selectedTaDossierRcdDTO.setLettrePjNumPolice(TaSousDonnee.VALEUR_LETTRE_NUM_POLICE_PJPROETENDUE);
				}
			}else {
				jsonDATA.put(TaSousDonnee.CLE_PJ_CHOISIE, "");
				selectedTaDossierRcdDTO.setLettrePjNumPolice("");
			}
		}
		
		
		
		
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
    public static final BigDecimal TAUX_COMMISSION = new BigDecimal(10.00) ;
    public static final BigDecimal FRAIS_RCD_MAX = new BigDecimal(300.00) ;
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
				//si assure meme contrat est en cour pas de reprise du passe possible
				selectedTaDossierRcdDTO.setReprisePasse(false);
				
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
			else if(selectedAntecedantRCDResilieParAssure.equals(ANTECEDANT_RCD_RESILIE_PAR_ASSUREUR)) {
				selectedTaDossierRcdDTO.setAntecedentRcdResilieAssure(false);
				selectedTaDossierRcdDTO.setAntecedentRcdAssureur(true);
//				selectedAntecedantRCDResilieParAssure = ANTECEDANT_RCD_RESILIE_PAR_ASSUREUR;
				selectedAntecedantMotifRCDAssure = null;
			}
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
			
			actChangementAntecedantResilieNonPaiement();
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
			
			//bloquage par rapport à la date de création d'entreprise
			if(selectedTaDossierRcdDTO.getTaAssureDTO().getDateCreation() !=null 
					&& dateLimiteRepriseDuPasse.before(selectedTaDossierRcdDTO.getTaAssureDTO().getDateCreation())) {
				dateLimiteRepriseDuPasse = selectedTaDossierRcdDTO.getTaAssureDTO().getDateCreation();
			}
			//bloquage par rapport à la date de résiliation précédent contrat RCD
			if(selectedTaDossierRcdDTO.getAssureMemeRisque() //deja assure
					&& selectedTaDossierRcdDTO.getAssureMemeRisqueRcd() //rcd
					&& selectedAntecedantRCDEnCours!=null && selectedAntecedantRCDEnCours.equals(ANTECEDANT_RCD_RESILIE) //resilie
					&& selectedTaDossierRcdDTO.getAntecedentRcdDateResiliation()!=null //on a une date de resiliation
					&& dateLimiteRepriseDuPasse.before(selectedTaDossierRcdDTO.getAntecedentRcdDateResiliation())) {
				dateLimiteRepriseDuPasse = selectedTaDossierRcdDTO.getAntecedentRcdDateResiliation();
			}
			//bloquage par rapport à la date de résiliation précédent contrat RCE
			if(selectedTaDossierRcdDTO.getAssureMemeRisque() //deja assure
					&& selectedTaDossierRcdDTO.getAssureMemeRisqueRce() //rce
					&& selectedAntecedantRCEEnCours!=null && selectedAntecedantRCEEnCours.equals(ANTECEDANT_RCE_RESILIE) //resilie
					&& selectedTaDossierRcdDTO.getAntecedentRceDateResiliation()!=null //on a une date de resiliation
					&& dateLimiteRepriseDuPasse.before(selectedTaDossierRcdDTO.getAntecedentRceDateResiliation())) {
				dateLimiteRepriseDuPasse = selectedTaDossierRcdDTO.getAntecedentRceDateResiliation();
			}
		}
	}
	/*------------------------------------------------------------*
	 * ***** FIN GESTION DES BOUTONS RADIO A BASE DE BOOLEEN  ***** 
	 *------------------------------------------------------------*/
	
	public void logCalculPrime(String txt) {
		logCalculPrime(txt, true);
	}
	
	public void logCalculPrime(String txt, boolean log) {
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
			if(taDossierRcd.getTaAssure().getTaTAssure().getCodeTAssure().equals(TaTAssure.PIB)) {
//				System.out.println("*************** ***************** **************Dossier PIB****************** ******************** ******************");
				return true;
				
			}else {
//				System.out.println("*************** ***************** **************Dossier NON PIB****************** ******************** ******************");
				return false;
			}
			
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
		Boolean AutoEnt = false;
		if(taTJuridiqueDTO!=null && taTJuridiqueDTO.getCodeTJuridique().equals("AE")) {//si c'est un auto entrepreneur
			AutoEnt = true;
		}
		
		List<TaClasse> listeClasse = new ArrayList<>();
		List<TaActivite> listeActivite = new ArrayList<>();
		List<TaActivite> listeActiviteAvecPrimeDeBase = new ArrayList<>();
		try {
			//préparation
			for (Integer idActivte : listeIdActiviteSelectionne) {
				TaActivite a = taActiviteService.findById(idActivte);
				
				
				
				//si l'activite a un minimum de prime, l'ajouter à ce traitement uniquement si le CA est > au palier minimum pour cette activite
//				if(a.getPrimeBase()!=null) { //il y a une prime de base
//					if(a.getMinCa().compareTo(caPrevi)<=0
//							&& a.getMaxCa().compareTo(caPrevi)>=0) { //on est dans la fourchette de CA ou la prime de base s'applique
//						listeActiviteAvecPrimeDeBase.add(a);
//					} else {
//						listeClasse.add(a.getTaClasse());
//						listeActivite.add(a);
//					}
//				} else {
//					listeClasse.add(a.getTaClasse());
//					listeActivite.add(a);
//				}
				
				//Plus de prise en compte des primes de base
				listeClasse.add(a.getTaClasse());
				listeActivite.add(a);
			}
			
			//recherche des paliers uniquement dans les classes concernées
			for (TaClasse c : listeClasse) {
				for (TaPalierClasse pc : c.getTaPalierClasse()) {
					if(AutoEnt) {// si c'est un auto entrepreneur
						if(pc.getPalierMontantMin()==null && pc.getPalierMontantMax()==null) {//si c'est bien un palier pour AE (sans montant min et max)
							if(r.getPalierReferencePourCa()==null || r.getPalierReferencePourCa().getMontantPrimeBase().compareTo(pc.getMontantPrimeBase())<0) {
								//on garde uniquement le palier donnant la prime de base la plus haute
								r.setPalierReferencePourCa(pc);
								r.setMontantPrimeBase(pc.getMontantPrimeBase());
							}
						}
					}else {// si ce n'est pas un auto entrepreneur
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
			}
			
			////// 
			//Activité avec un minimum de prime (prime de base)
//			for (TaActivite taActivitePB : listeActiviteAvecPrimeDeBase) {
//			for (TaActivite taActivitePB : listeActivite) {
//				if(r.getPalierReferencePourCa()==null || r.getPalierReferencePourCa().getMontantPrimeBase().compareTo(taActivitePB.getPrimeBase())<0) {
//					//on garde uniquement la prime de base donnant la prime de base la plus haute
//					r.setPalierReferencePourCa(null);
//					r.setMontantPrimeBase(taActivitePB.getPrimeBase());
//				}
//			}
			//////
			
			if(r.getPalierReferencePourCa()!=null) {
				//en fonction du palier retenu on peu obtenir la classe qui de référence
				r.setClasseReference(r.getPalierReferencePourCa().getTaClasse());
			}
			
//			//recherche inverse (recherche des activiés sélectionnées qui ont amenées à choisir cette classe)
			//on supprime de la liste les activité qui n'appartiennent pas à la classe de référence
			List<TaActivite> listeActiviteAsupprimer = new ArrayList<>();
			for (TaActivite a : listeActivite) {
				if(r.getClasseReference()!=null) {
					if(a.getTaClasse().getIdClasse()!=r.getClasseReference().getIdClasse()) {
						listeActiviteAsupprimer.add(a);
					}
				}
				
			}
			listeActivite.removeAll(listeActiviteAsupprimer);
			
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
		
		if(affichePremierOnglet) {
			
			/*
			Toutes les questions n’ont pas d’incidence sur le tarif mais peux être bloquant.
			En effet sur les questions de 1, 2, 3, 4 si la réponse est non c’est bloquant (c’est-à-dire un
			refus d’acceptation)
			*/
			if(!selectedTaDossierRcdDTO.getExperienceActivite3ans() 
	//				|| !selectedTaDossierRcdDTO.getExperienceActivte5ans() 
					|| !selectedTaDossierRcdDTO.getExerceActiviteNomenclature() 
					|| !selectedTaDossierRcdDTO.getCoutOuvrageInferieur15k() 
					) {
				valide = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Une des réponses est bloquante."));
			}
			
			/*
			 * Sur les questions 5, 6, 7, 8, 9 (Montant de vos marchés) un choix possible. 
			 */
			//selectedMontantMarcheTravauxHTMin
	
			/*
			 * Sur les question 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21 et 22(le proposant déclare) si
			 * une réponse est non c’est bloquant (c’est-à-dire un refus d’acceptation)
			 * Sur toutes les questions la réponse est obligatoire sinon blocage.
			 */
			
			if(!selectedTaDossierRcdDTO.getInterventionConstructeurMaisonIndiv() 
					|| !selectedTaDossierRcdDTO.getInterventionContractantGeneral() 
					|| !selectedTaDossierRcdDTO.getInterventionBatiment() 
					|| !selectedTaDossierRcdDTO.getInterventionMaitreOeuvre() 
					|| !selectedTaDossierRcdDTO.getInterventionImmobilier() 
					|| !selectedTaDossierRcdDTO.getInterventionFabricantMatConstruction() 
					|| !selectedTaDossierRcdDTO.getActivitePrincNegoceFabrMatConstructionNonPose() 
					|| !selectedTaDossierRcdDTO.getTravauxTechniqueNonCourant() 
					|| !selectedTaDossierRcdDTO.getInterventionMonumentHistorique() 
					|| !selectedTaDossierRcdDTO.getAvisTechnique()
	//				|| !selectedTaDossierRcdDTO.getDocumentUnique() //non bloquant
					|| !selectedTaDossierRcdDTO.getRespectRegles() 
					//|| !selectedTaDossierRcdDTO.getRespectDispositionSousTraitance() 
					) {
				valide = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Declaration et engagement :","Une des réponses est bloquante."));
			}
		} else {
			//si on n'affiche pas le 1er onglet on ne vérifie pas son contenu.
		}
		
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
		
		
		
		if(selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel()==null
				|| taTJuridiqueDTO == null
				|| selectedTaDossierRcdDTO.getTaAssureDTO().getRaisonSociale()==null 
				|| selectedTaDossierRcdDTO.getTaAssureDTO().getRaisonSociale().isEmpty()
				|| selectedTaDossierRcdDTO.getTaAssureDTO().getCodeSiren()==null 
				|| selectedTaDossierRcdDTO.getTaAssureDTO().getCodeSiren().isEmpty()
				
				|| selectedTaDossierRcdDTO.getTaAssureDTO().getAdresseLigne1()==null 
				|| selectedTaDossierRcdDTO.getTaAssureDTO().getAdresseLigne1().isEmpty()
				|| selectedTaDossierRcdDTO.getTaAssureDTO().getCodePostal()==null 
				|| selectedTaDossierRcdDTO.getTaAssureDTO().getCodePostal().isEmpty() 
				|| selectedTaDossierRcdDTO.getTaAssureDTO().getVille()==null 
				|| selectedTaDossierRcdDTO.getTaAssureDTO().getVille().isEmpty() 
				|| selectedTaDossierRcdDTO.getTaAssureDTO().getNumeroTel()==null
				|| selectedTaDossierRcdDTO.getTaAssureDTO().getNumeroTel().isEmpty()
						
				|| ((selectedTaDossierRcdDTO.getTaAssureDTO().getAdresseEmail()==null || selectedTaDossierRcdDTO.getTaAssureDTO().getAdresseEmail().equals("")) && creationCompteClientAssure)
				
//				|| taTCiviliteDTO == null
				|| selectedTaDossierRcdDTO.getTaAssureDTO().getNom()==null
				|| selectedTaDossierRcdDTO.getTaAssureDTO().getNom().isEmpty()
				|| selectedTaDossierRcdDTO.getTaAssureDTO().getPrenom()==null
				|| selectedTaDossierRcdDTO.getTaAssureDTO().getPrenom().isEmpty()
				|| selectedTaDossierRcdDTO.getTaAssureDTO().getDateCreation()==null

				
				|| selectedTaDossierRcdDTO.getAnneesExperienceActivite()==null
				|| selectedTaDossierRcdDTO.getEffectifTotalExercice()==null
				
				|| selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel()==null
				) {
			valide = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,titreMessageOnglet,"Tous les champs sont obligatoires."));
		}
		
		if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_INSERTION)==0 && creationCompteClientAssure) { //on ne modifie pas le mdp en modification de devis
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
		if(selectedTaDossierRcdDTO.getEffectifTotalExercice()!=null) {
			
					 
					
			if (selectedTaDossierRcdDTO.getEffectifSurChantier()==null) {
				selectedTaDossierRcdDTO.setEffectifSurChantier(0);
			} 
			if (selectedTaDossierRcdDTO.getEffectifApprentis()==null) {					 
				selectedTaDossierRcdDTO.setEffectifApprentis(0);
				
			} 
			if (selectedTaDossierRcdDTO.getEffectifEncadrement()==null) {
				selectedTaDossierRcdDTO.setEffectifEncadrement(0);
			} 
			if (selectedTaDossierRcdDTO.getEffectifCommerciauxAdministratifs()==null) {
				selectedTaDossierRcdDTO.setEffectifCommerciauxAdministratifs(0);
			} 
			
			int total = 0;
				total += selectedTaDossierRcdDTO.getEffectifSurChantier();
				total += selectedTaDossierRcdDTO.getEffectifApprentis();
				total += selectedTaDossierRcdDTO.getEffectifEncadrement();
				total += selectedTaDossierRcdDTO.getEffectifCommerciauxAdministratifs();
			
			if(selectedTaDossierRcdDTO.getEffectifTotalExercice()!=total) {
				valide = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"La déclaration des effectifs de l'entreprise est incohérente."));
			}
		}
		
		//vérification CA de N-1 ou N-2 devrait être remplis par rapport à la date de création d'entreprise
		
		if(selectedTaDossierRcdDTO.getTaAssureDTO().getDateCreation()!=null) {
			LocalDate localDateMaintenant= new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate localDateCreationEts = selectedTaDossierRcdDTO.getTaAssureDTO().getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			Period intervalPeriod = Period.between(localDateCreationEts, localDateMaintenant);
		    int nbAnnee = intervalPeriod.getYears();
		    if(nbAnnee>=1 && selectedTaDossierRcdDTO.getCaTotalHtExerciceNMoins1()==null) {
		    	valide = false;
		    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,titreMessageOnglet,"Votre entreprise existe depuis plus d'un an, veuillez saisir le chiffre d'affaire N-1"));
		    }
		    if(nbAnnee>=2 && (selectedTaDossierRcdDTO.getCaTotalHtExerciceNMoins1()==null || selectedTaDossierRcdDTO.getCaTotalHtExerciceNMoins2()==null)) {
		    	valide = false;
		    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,titreMessageOnglet,"Votre entreprise existe depuis plus de deux ans, veuillez saisir les chiffres d'affaire N-1 et N-2"));
		    }
		}
	    
		
		if(taTJuridiqueDTO!=null && taTJuridiqueDTO.getCodeTJuridique().equals("AE")) {
			if(selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel()!=null && selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel().compareTo(new BigDecimal(80000))>0) {
//				tabViewDevisRcPro.setActiveIndex(mapOngletIndex.get(ID_ONGLET_2_PROPOSANT_DECLARATION));
//				idOngletActif = ID_ONGLET_2_PROPOSANT_DECLARATION;
				valide = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,titreMessageOnglet,"Pour les auto entrepreneurs le chiffre d'affaire maximum est de 80 000€"));
			}
			nbActiviteMax = NB_ACTIVITE_MAX_AUTO_ENTREPRENEUR_DEFAUT;
		} else {
			nbActiviteMax = NB_ACTIVITE_MAX_DEFAUT;
		}
		
		if(assurePib()) {//Si PIB
			//si CA prévisionnel supérieur à 1 000 000, blocage
			if(selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel()!=null && selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel().compareTo(Const.C_CA_PREVI_MAX_PIB)>0) {
				valide = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,titreMessageOnglet,"Le chiffre d'affaire prévisionnel maximum autorisé pour les PIB est de "+Const.C_CA_PREVI_MAX_PIB+" €"));
			}
			
		}else {//SI non PIB
			//si CA prévisionnel supérieur à 2 100 000, blocage
			if(selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel()!=null && selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel().compareTo(Const.C_CA_PREVI_MAX)>0) {
				valide = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,titreMessageOnglet,"Le chiffre d'affaire prévisionnel maximum autorisé est de "+Const.C_CA_PREVI_MAX+" €"));
			}
			
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
		if(selectedTaDossierRcdDTO.getAssureMemeRisque()) {
			if(selectedTaDossierRcdDTO.getAssureMemeRisqueRcd()) {
				if(selectedTaDossierRcdDTO.getAntecedentRcdNomAssureur()==null || selectedTaDossierRcdDTO.getAntecedentRcdNomAssureur().equals("")
						|| selectedTaDossierRcdDTO.getAntecedentRcdPoliceAssureur()==null || selectedTaDossierRcdDTO.getAntecedentRcdPoliceAssureur().equals("")
						) {
					valide = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,titreMessageOngletRCD,"Veuillez saisir le nom de votre ancien assureur ainsi que votre ancien numéro de police ."));
				}
				if(selectedTaDossierRcdDTO.getAntecedentRcdContratResilie() && selectedTaDossierRcdDTO.getAntecedentRcdDateResiliation()==null) {
					valide = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,titreMessageOngletRCD,"Veuillez saisir la date de résiliation de votre ancien contrat."));
				}
			} 
			if (selectedTaDossierRcdDTO.getAssureMemeRisqueRce()) {
				if(selectedTaDossierRcdDTO.getAntecedentRceNomAssureur()==null || selectedTaDossierRcdDTO.getAntecedentRceNomAssureur().equals("")
						|| selectedTaDossierRcdDTO.getAntecedentRcePoliceAssureur()==null || selectedTaDossierRcdDTO.getAntecedentRcePoliceAssureur().equals("")
						) {
					valide = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,titreMessageOngletRCE,"Veuillez saisir le nom de votre ancien assureur ainsi que votre ancien numéro de police ."));
				}
				if(selectedTaDossierRcdDTO.getAntecedentRceContratResilie() && selectedTaDossierRcdDTO.getAntecedentRceDateResiliation()==null) {
					valide = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,titreMessageOngletRCE,"Veuillez saisir la date de résiliation de votre ancien contrat."));
				}
			} 
//			else {
//				valide = false;
//			}
		}
		
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
//		    for (int i = 0; i < intervalPeriod.getYears(); i++) {
//		    	nbMois = nbMois+12;
//			}
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
		
		if(selectedInteruptionAssurance!= null && selectedInteruptionAssurance.equals(INTERUPTION_ASSURANCE_PLUS_7_ANS)) {
			valide = false;
	    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,titreMessageOnglet,"Refus : La période d'intérruption d'assurance est de plus de 7 ans."));
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
		if(assurePib()) {
			if(selectedTaDossierRcdDTO.getSinistraliteLiquidationSocieteDemandeuse() || selectedTaDossierRcdDTO.getSinistraliteLiquidationAutreSociete()){
				valide = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Pour les PIB, blocage si il y a redressement judiciaire."));
			}
		}
		

		if(selectedTaDossierRcdDTO.getSinistraliteRisqueRefusAssurance()
				) {
			valide = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Une des réponses est bloquante."));
		}
		
		if(selectedTaDossierRcdDTO.getSinistraliteMiseEnCause()) {
			if(taDossierRcd.getTaSinistreAntecedent()==null || taDossierRcd.getTaSinistreAntecedent().isEmpty()) {
				valide = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Veuillez saisir au moins 1 sinistre."));
			}
		}
		
		if(selectedTaDossierRcdDTO.getSinistraliteEvenementEngageantResp()) {
			if(selectedTaDossierRcdDTO.getSinistraliteEvenementFaits()==null || selectedTaDossierRcdDTO.getSinistraliteEvenementFaits().equals("")) {
				valide = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Veuillez décrire les faits."));
			}
		}
		
		//RAJOUT CAR PLUS DE SOUMMISSION A ETUDE
		if(taDossierRcd.getTaSinistreAntecedent()!=null && !taDossierRcd.getTaSinistreAntecedent().isEmpty()) { //si il y a des sinistres 
			BigDecimal montantTotal = BigDecimal.ZERO;

			if(taDossierRcd.getTaSinistreAntecedent().size() <= 3) {// si 1 à 3 sinistres
				for (TaSinistreAntecedent taSinistreAntecedent : taDossierRcd.getTaSinistreAntecedent()) {
					montantTotal = montantTotal.add(taSinistreAntecedent.getMontantSinistre());
				}
				
				if(montantTotal.compareTo(new BigDecimal(10000))<=0) {//si cout <10 000€
				}else if(montantTotal.compareTo(new BigDecimal(10000))>=0 && montantTotal.compareTo(new BigDecimal(25000))<=0){//si cout entre 10 000 et 25 000
				}else {// si cout > 25 000
//					pourEtude = true;
					valide = false;
					FacesContext context = FacesContext.getCurrentInstance();  
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sinitres : souscription impossible", "le coût des sinistres choisis est supérieur à 25 000 €."));
					
				}
				
			}else {// plus de 3 sinsitres
//				pourEtude = true;
				valide = false;
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sinitres : souscription impossible", "Il y a plus de 3 sinitres."));
			}
		}
		
		
		
		
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
			if(selectedTaActiviteDTOs==null || selectedTaActiviteDTOs.isEmpty()) {
				onglet5_1Valide = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Veuillez sélectionner au moins 1 activité."));
			} 
			
			
			for (TaActiviteDTO taActiviteDTO : listeTaActiviteDTODetail) {
				
				//MISE EN COMMENTAIRE DE TTE VERIF DE % DE SOUS TRAITANCE SUR LES ACTIVITES POUR LEADER UNDERWRITING
				//vérifier que tous les pourcentage de répartition sont bien remplis
//				if(taActiviteDTO.getPourcentTotalRepartition()==null) {
//					onglet5_2Valide = false;
//					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Veuillez renseigner les pourcentages de sous-traitance pour toutes les activités sélectionées."));
//				}
				
				//vérifier les commentaires obligatoires pour certaines activité
//				if(taActiviteDTO.getCommentaireObligatoire()!=null && taActiviteDTO.getCommentaireObligatoire()) {
//					if(taActiviteDTO.getCommentaire()==null || taActiviteDTO.getCommentaire().equals("")) {
//						onglet5_2Valide = false;
//						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Veuillez saisir un commentaire pour les activités sur lesquelle il est obligatoire."));
//					}
//				}
			}
//			calculeTauxSousTraitanceGlobal(null);
			
//			if(afficheQuestionExperienceActivte5ans() && !selectedTaDossierRcdDTO.getExperienceActivte5ans() ) {
//				onglet5_2Valide = false;
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"L' assuré doit bénéficier d'au moin 5 ans d'expérience dans les activités à garantir."));
//			}
			
//			BigDecimal totalAct = BigDecimal.ZERO;
//			if(totalPourcentageActiviteSousTraite!=null) {
//				totalAct = totalAct.add(totalPourcentageActiviteSousTraite);
//				if(totalPourcentageActiviteSousTraite.compareTo(new BigDecimal(100))>0) {
//					onglet5_2Valide = false;
//					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Le pourcentage total de sous-traitance dépasse 100%."));
//				}
//			}
//			if(totalPourcentageActiviteEntreprise!=null) {
//				totalAct = totalAct.add(totalPourcentageActiviteEntreprise);
//				if(totalPourcentageActiviteEntreprise.compareTo(new BigDecimal(100))>0) {
//					onglet5_2Valide = false;
//					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Le pourcentage total des activités de l'entreprise dépasse 100%."));
//				}
//			}
//			if(totalAct!=null && totalAct.compareTo(new BigDecimal(100))!=0) {
//				onglet5_2Valide = false;
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Le pourcentage total des activités doit être égal à 100%."));
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
						selectedTaDossierRcdDTO.setCodeFranchise(TaTFranchise.FR5000_PIB);
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
		if(selectedTaDossierRcdDTO.getReprisePasse()) {
			//reprise du passe apres date creation
//			if(selectedTaDossierRcdDTO.getDateRepriseDuPasse().before(selectedTaDossierRcdDTO.getTaAssureDTO().getDateCreation())) {
//				valide = false;
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"La date de reprise du passé doit être postérieure à la date de création de l'entreprise."));
//			}
			
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
		}
		
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
		
		
		
		if(selectedTaDossierRcdDTO.getDateEffet()==null ) {
			valide = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Veuillez saisir une date d'effet."));
		}
		
//		if(selectedTaDossierRcdDTO.getFraisRcPro()!=null && selectedTaDossierRcdDTO.getFraisRcPro().compareTo(FRAIS_RCD_MAX) > 0) {
//			valide = false;
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"les frais de courtiers ne peuvents dépasser 300 €."));
//		}
		
		onglet6Valide = valide;
		return valide;
	}
	
	public boolean validationOnglet7InformationLegale() {
		boolean valide = true;
		String titreMessageOnglet = "Information légale :";
		
		/*
		 * Toutes les réponses sont obligatoires.
		 */
		if(!selectedTaDossierRcdDTO.getInformerCaractereObligatoire() 
				|| !selectedTaDossierRcdDTO.getInformationPropositionPartieIntegranteContrat()
				|| !selectedTaDossierRcdDTO.getAutoriseAssureurCommuniquerReponses()
//				|| !selectedTaDossierRcdDTO.getOpposeUtilisationDonneesFinCommerciale() //non obligatoire
				) {
			valide = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titreMessageOnglet ,"Veuillez accepter toutes les informations légales."));
		}
		
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
		//boolean valide = true;
		validationOnglet1DeclarationEngagement();
		validationOnglet2ProposantDeclaration();
		validationOnglet3Antecedant();
		validationOnglet4Sinistralite();
		validationOnglet5ActiviteDeclare();
		validationOnglet6OptionPaiement();
		validationOnglet7InformationLegale();
		validationOnglet8Prime();
		validationOnglet9GedDocument();
		return !erreurBloquante();
	}
	
	public boolean erreurBloquante() {
		return !(onglet1Valide && onglet2Valide && onglet3Valide && onglet4Valide && onglet5Valide && onglet6Valide && onglet7Valide && onglet8Valide && onglet1Valide);
	}
	
	public void soumettreLeDevisAEtude(ActionEvent e) {
		selectedTaDossierRcdDTO.setSoumisEtude(true);
	}
	
	public boolean franchiseDesactive(TaTFranchise f) {
//		majPrimeNettesEtActiviteRef();
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
		calculMontantPrime(true);
		
	}
	public void calculMontantPrime(Boolean DisplayError) {
		if(DisplayError) {
			valideTousLesOnglets();
		}
		logDebugCalculPrime = "";
		logDebugCalculPremierReglement="";
		
		selectedTaDossierRcdDTO.setMontantPrime(null);
		selectedTaDossierRcdDTO.setMontantPremierReglement(null);
		
		if(pourEtude==true) {
			majPrimeNettesEtActiviteRef();
			if(pourEtude==true) {
				chercheTauxSinistre36mois();
			}
			
		}
		
		
		if((!erreurBloquante() && pourEtude==false) ||
				selectedTaDossierRcdDTO.getPrimeNetteYlyade()!=null ||
				selectedTaDossierRcdDTO.getPrimeNetteAssureur()!=null) {//si pas d'erreur bloquante et de mise en etude, on calcule. Ou si Ylyade fait une prop de prime, on calcule, ou si Assureur fait une prop de prime, on calcule.
//			pourEtude = false;
			BigDecimal primeAnnuelle = BigDecimal.ZERO;
			try {
				
				if(selectedTaDossierRcdDTO.getDateEffet()!=null) {
					logCalculPrime("Date effet : " + LibDate.dateToString(selectedTaDossierRcdDTO.getDateEffet()));
					//date d'échéance : 1an -1jour à partir de la date d'effet
					LocalDate localDateEffet = selectedTaDossierRcdDTO.getDateEffet().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					Date echeance = Date.from(LocalDate.of(localDateEffet.getYear(), localDateEffet.getMonthValue(),localDateEffet.getDayOfMonth()).plus(1, ChronoUnit.YEARS).minus(1, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault()).toInstant());
					selectedTaDossierRcdDTO.setDateEcheance(echeance);
					
					logCalculPrime("Date échéance : " + LibDate.dateToString(echeance));
					
					//debut période
					selectedTaDossierRcdDTO.setDateDebutPremierePeriode(selectedTaDossierRcdDTO.getDateEffet());
					logCalculPrime("Date début 1ère période : " + LibDate.dateToString(echeance));
				}
				
				majPrimeNettesEtActiviteRef();
				
				/*****************DEBUT SECTION 1.CALCUL DE LA PRIME*******************/
				logCalculPrime("<h1>1.CALCUL DE LA PRIME</h1>");
				logCalculPremierReglement("<h2>CALCUL DU PREMIER REGLEMENT</h2>");
				
				primeAnnuelle = primeBaseNette;
				
				logCalculPrime("***");
				logCalculPrime("Prime de base nette : <b>"+primeAnnuelle+" € </b>");
				logCalculPrime("***");
				/*****************FIN SECTION 1.CALCUL DE LA PRIME*******************/
					
				if(primeAnnuelle!=null) {
					
					logCalculPrime("<h3> Début des calculs préléminaires sur la base de la prime nette : </h3>");
					/***********Commission Ylyade 8% sur base nette***/
					BigDecimal commissionYlyade = BigDecimal.ZERO;
					BigDecimal tauxCommYlyade = BigDecimal.ZERO;
					
					/*****************DEBUT SECTION 2.COMMISSION NOVARISKS*******************/
					logCalculPrime("<h4>2.Calcul de la commission Novarisks</h4>");
					if(!assurePib()) {
						 tauxCommYlyade = Const.C_TAUX_COMMISSION_YLYADE;
						 logCalculPrime("le taux de commission non PIB Novarisks est de "+tauxCommYlyade+"%");
					}else {
						 tauxCommYlyade = Const.C_TAUX_COMMISSION_YLYADE_PIB;
						 logCalculPrime("le taux de commission <b>PIB</b> Novarisks est de "+tauxCommYlyade+"%");
					}
					commissionYlyade = primeBaseNette.multiply(tauxCommYlyade).divide(new BigDecimal(100));
					logCalculPrime("la commission Novarisks est égale à la prime de base nette multiplié par le taux de commission Novarisks,");
					logCalculPrime("la prime de base nette est à "+primeBaseNette+" €, donc :");
					logCalculPrime(primeBaseNette+" x ("+tauxCommYlyade+"/100) = <b>"+commissionYlyade+"</b>");
					logCalculPrime("la commission Novarisks est égale à "+commissionYlyade+" €.");
					
					JSONObject jsonDataComYlyade;
					if(taSousDonnee.getJsonData()!=null) {
						jsonDataComYlyade = new JSONObject(taSousDonnee.getJsonData());
					}else {
						jsonDataComYlyade = new JSONObject();
					}
					jsonDataComYlyade.put(TaSousDonnee.CLE_MONTANT_COMMISSION_YLYADE, commissionYlyade.toString());
					taSousDonnee.setJsonData(jsonDataComYlyade.toString());
					/*****************FIN SECTION 2.COMMISSION NOVARISKS*******************/
					

					
					
					
					/*****************DEBUT SECTION 3.COMMISSION COURTIER*******************/
					//Commission courtier (0 à 50% de la base nette)	:		
					BigDecimal commissionCourtier = BigDecimal.ZERO;
					logCalculPrime("<h4>3.Calcul de la commission courtier</h4>");
					if(selectedTaDossierRcdDTO.getTauxCommission()!=null) {
						commissionCourtier = primeBaseNette.multiply(selectedTaDossierRcdDTO.getTauxCommission()).divide(new BigDecimal(100));
						//Choix entre 0 et 50% avec International Insurance
						logCalculPrime("la commission courtier est égale à la prime de base nette multiplié par le taux de commission,");
						logCalculPrime("le taux de commission choisi par le courtier est de "+selectedTaDossierRcdDTO.getTauxCommission()+"%");
						logCalculPrime("la prime de base nette est à "+primeBaseNette+" €, donc :");
						logCalculPrime(primeBaseNette+" x ("+selectedTaDossierRcdDTO.getTauxCommission()+"/100) = <b>"+commissionCourtier+"</b>");
						logCalculPrime("la commission courtier est égale à "+commissionCourtier+" €.");
						selectedTaDossierRcdDTO.setMontantCommissionCourtier(commissionCourtier);
					} else {
						logCalculPrime("Commission courtier (taux 0%) : ");
					}
					
					/*****************FIN SECTION 3.COMMISSION COURTIER*******************/
					
					
					
					/*****************DEBUT SECTION 4.MAJORATION RESILIE NON PAIEMENT*******************/
					
					logCalculPrime("<h4>4.Calcul du montant résilisé pour non-paiement</h4>");
					//Résilié pour non-paiement	? +20%
					BigDecimal tauxResilieNonPaiement ;
					BigDecimal tauxResilieNonPaiementAssureur;
					BigDecimal tauxResilieNonPaiementYlyade;
					if(!assurePib()) {
						 tauxResilieNonPaiement = taTauxAssuranceService.findByCode(TaTauxAssurance.RESILIATION_NON_PAIEMENT).getTauxTauxAssurance();
						 tauxResilieNonPaiementAssureur = Const.C_TAUX_RESILIE_NON_PAIEMENT_ASSUREUR;
						 tauxResilieNonPaiementYlyade= Const.C_TAUX_RESILIE_NON_PAIEMENT_YLYADE;
					}else {
						// Pour les PIB c'est 35% mais 5% pour Ylyade
						 tauxResilieNonPaiement = taTauxAssuranceService.findByCode(TaTauxAssurance.RESILIATION_NON_PAIEMENT_PIB).getTauxTauxAssurance();
						 tauxResilieNonPaiementAssureur = Const.C_TAUX_RESILIE_NON_PAIEMENT_ASSUREUR_PIB;
						 tauxResilieNonPaiementYlyade= Const.C_TAUX_RESILIE_NON_PAIEMENT_YLYADE_PIB;
					}
					
					
					BigDecimal montantResilieNonPaiement = BigDecimal.ZERO;
					BigDecimal montantRNPAssureur = BigDecimal.ZERO;
					BigDecimal montantRNPYlyade = BigDecimal.ZERO;

					
					if( selectedTaDossierRcdDTO.getAssureMemeRisque()
						&& 
						(
							(
								selectedTaDossierRcdDTO.getAssureMemeRisqueRcd()!=null && selectedTaDossierRcdDTO.getAssureMemeRisqueRcd()
								&& selectedAntecedantRCDEnCours.equals(ANTECEDANT_RCD_RESILIE) 
								&& selectedAntecedantRCDResilieParAssureur.equals(ANTECEDANT_RCD_RESILIE_PAR_ASSUREUR) 
								&& selectedAntecedantMotifRCDAssureur.equals(MOTIF_RESILIATION_ASSUREUR_NON_PAIEMENT)
							) || (
								selectedTaDossierRcdDTO.getAssureMemeRisqueRce()!=null && selectedTaDossierRcdDTO.getAssureMemeRisqueRce()
								&& selectedAntecedantRCEEnCours.equals(ANTECEDANT_RCE_RESILIE) 
								&& selectedAntecedantRCEResilieParAssureur.equals(ANTECEDANT_RCE_RESILIE_PAR_ASSUREUR) 
								&& selectedAntecedantMotifRCEAssureur.equals(MOTIF_RESILIATION_ASSUREUR_NON_PAIEMENT)
							)
						)
					) {
						selectedTaDossierRcdDTO.setResilieNonPaiement(true);
						montantResilieNonPaiement = primeBaseNette.multiply(tauxResilieNonPaiement).divide(new BigDecimal(100));
						logCalculPrime("<b>Résilié pour non-paiement OUI</b> (taux "+tauxResilieNonPaiement+") : <b>"+montantResilieNonPaiement+" </b>");
						montantRNPAssureur = primeBaseNette.multiply(tauxResilieNonPaiementAssureur).divide(new BigDecimal(100));
						montantRNPYlyade = primeBaseNette.multiply(tauxResilieNonPaiementYlyade).divide(new BigDecimal(100));
						logCalculPrime("Taux résilié pour non paiement : "+tauxResilieNonPaiement+" %");
						logCalculPrime("Sachant que ces "+tauxResilieNonPaiement+" % sont en fait "+tauxResilieNonPaiementAssureur+" % pour l'assureur et "+tauxResilieNonPaiementYlyade+" % pour Novarisks");
						logCalculPrime("Donc ici <b>"+montantRNPAssureur+"</b> € pour l'assureur et <b>"+montantRNPYlyade+"</b> € pour Novarisks");
						// on stocke le montant pour ylyade(5%) et celui-pour l'assureur (15%) dans taSousDonnee
						JSONObject jsonDATAResilieNonPaiement = new JSONObject(taSousDonnee.getJsonData());
						jsonDATAResilieNonPaiement.put(TaSousDonnee.CLE_MONTANT_RESILIE_NON_PAIEMENT_ASSUREUR, montantRNPAssureur.toString());
						jsonDATAResilieNonPaiement.put(TaSousDonnee.CLE_MONTANT_RESILIE_NON_PAIEMENT_YLYADE, montantRNPYlyade.toString());
						taSousDonnee.setJsonData(jsonDATAResilieNonPaiement.toString());
						
						
					} else {
						logCalculPrime("Résilié pour non-paiement NON : "+montantResilieNonPaiement);
					}
					
					/*****************FIN SECTION 4.MAJORATION RESILIE NON PAIEMENT*******************/
						
					//Résilié pour fausse déclaration ?	+20%
	//				BigDecimal tauxResilieFausseDeclaration = taTauxAssuranceService.findByCode(TaTauxAssurance.RESILISATION_FAUSSE_DECLARATION).getTauxTauxAssurance();
	//				BigDecimal montantResilieFausseDeclaration = BigDecimal.ZERO;
	//				if(selectedTaDossierRcdDTO.getResilieFausseDeclaration()!=null && selectedTaDossierRcdDTO.getResilieFausseDeclaration()) {
	//					montantResilieFausseDeclaration = primeBaseNette.multiply(tauxResilieFausseDeclaration).divide(new BigDecimal(100));
	//					logCalculPrime("Résilié pour fausse déclaration OUI (taux "+tauxResilieFausseDeclaration+") : "+montantResilieFausseDeclaration);
	//				} else {
	//					logCalculPrime("Résilié pour fausse déclaration NON : "+montantResilieFausseDeclaration);
	//				}
							
					//Recours à	la sous-traitance : 0->30%	(0) x1 ** 30->100% : REFUS
					//finalement la sous-traitance n'entrainent pas de majo
					BigDecimal montantSousTraitance = BigDecimal.ZERO;
//					if(selectedTaDossierRcdDTO.getCodeTSousTraitance()!=null ) {
//						TaTSousTraitance st = taTSousTraitanceService.findByCode(selectedTaDossierRcdDTO.getCodeTSousTraitance());
//						taDossierRcd.setTaTSousTraitance(st);
//						if(st!=null && st.getTaux()!=null && st.getTaux().compareTo(BigDecimal.ZERO)!=0 && st.getCodeTSousTraitance()!= TaTSousTraitance.ST_30_100) {
//							BigDecimal tauxSousTraitance = st.getTaux().divide(new BigDecimal(100));
//							montantSousTraitance = primeBaseNette.multiply(tauxSousTraitance);
//							logCalculPrime("Recours	à la sous-traitance moins de 30% ("+selectedTaDossierRcdDTO.getCodeTSousTraitance()+" taux "+st.getTaux()+") : "+montantSousTraitance);
//						} else {
//							logCalculPrime("<b>Recours	à la sous-traitance plus de 30% donc REFUS</b>");
//						}
//		
//						if(!assurePib() && selectedTaDossierRcdDTO.getCodeTSousTraitance().equals(TaTSousTraitance.ST_30_100)) { //erreur
//							//La	sous-traitance	de	50	à	100	%	est	réservée	uniquement	aux	activités	de	PIB.
//						}
//					}
					
					//Risque par famille d'activité
//					logCalculPrime("<h4>Calcul du risque par famille :</h4>");
					BigDecimal montantRisqueParFamilleActivite = BigDecimal.ZERO;
	//				if(selectedTaDossierRcdDTO.getReprisePasse()!=null && selectedTaDossierRcdDTO.getReprisePasse()) {
						//BigDecimal tauxReprisePasse = taTauxAssuranceService.findByCode(TaTauxAssurance.REPRISE_DU_PASSE).getTauxTauxAssurance().divide(new BigDecimal(100));
//						BigDecimal tauxRisqueFamilleActivite = chercheTauxRisqueFamilleActivite().divide(new BigDecimal(100));
//						logCalculPrime("La régle est la suivante :");
//						logCalculPrime("Si les activités sont dans maximum 3 familles d’activités aucune incidence.");
//						logCalculPrime("Si les activités sont dans 4 familles, hors famille 2, + 10 % sur la prime de base nette.");
//						logCalculPrime("Si les activités sont dans 4 familles, dont famille 2, + 15 % sur la prime de base nette.");
//						logCalculPrime("Si les activités sont dans les 5 familles, + 20% sur la prime de base nette.");
//						logCalculPrime("<b>le taux de risque ici suivant les activités séléctionnés est de  : "+tauxRisqueFamilleActivite+" %</b>");
//						montantRisqueParFamilleActivite = primeBaseNette.multiply(tauxRisqueFamilleActivite);
//						logCalculPrime("Pour calculer le montant du risque par famille d'activité, il faut multiplié la prime de base nette par le taux de risque ci-dessus : ");
//						logCalculPrime("Donc "+primeBaseNette+" x "+tauxRisqueFamilleActivite+" = "+montantRisqueParFamilleActivite);
//						logCalculPrime(" Montant de risque par famille d'activité séléctionnés<b>"+montantRisqueParFamilleActivite+"</b> €");
//						logCalculPrime("Risque par famille d'activité (taux "+tauxRisqueFamilleActivite+") : "+montantRisqueParFamilleActivite);
	//				} else {
	//					logCalculPrime("Risque par famille d'activité : "+montantRisqueParFamilleActivite);
	//				}
						
					/*****************DEBUT SECTION 5.MAJORATION NOMBRE ACTIVITES*******************/
					//Nombre d'activité
				    logCalculPrime("<h4>5.Calcul du montant du nombres des activités :</h4>");
				    logCalculPrime("La régle est la suivante :");
					logCalculPrime("DE 0 à 12, aucune incidence");
					BigDecimal montantNombreActivite = BigDecimal.ZERO;
					logCalculPrime("Le montant due au nombre d'activités séléctionnés est donc de <b>"+montantNombreActivite+"</b> €");
					
					/*****************FIN SECTION 5.MAJORATION NOMBRE ACTIVITES*******************/

					
					/*****************DEBUT SECTION 6.MAJORATION INTERRUPTION ASSURANCE*******************/
					//Interruption d'assurance
					logCalculPrime("<h4>6.Calcul du montant due à l'interruption d'assurance séléctionnée</h4>");
					BigDecimal montantInteruptionAssurance = BigDecimal.ZERO;
					logCalculPrime("la règle est la suivante :");					
					//Nouvelle règles Leader Underwriting
//				      De 0 à 2 ans 0%
//				      De 2 à 5% 15%
//				      De 5 à 7 25%
//				      Plus de 7% blocage
					logCalculPrime("De 0 à 2 ans 0%");
					logCalculPrime("De 2 à 5%, +15%");
					logCalculPrime("De 5 à 7, +25%");
					BigDecimal tauxInteruptionAssurance = chercheTauxInterruptionAssurance().divide(new BigDecimal(100));
					logCalculPrime("<b>Ici le taux d'interruption d'assurance est de "+tauxInteruptionAssurance+" (taux divisé par 100).</b>");
					logCalculPrime("Pour calculer le montant due à l'interruption d'assurance séléctionnée, il faut multiplié la prime de base nette par ce taux, donc :");
					montantInteruptionAssurance = primeBaseNette.multiply(tauxInteruptionAssurance);
					logCalculPrime(primeBaseNette+" x "+tauxInteruptionAssurance+" = "+montantInteruptionAssurance);
					logCalculPrime(" le montant due à l'interruption d'assurance séléctionnée est donc de <b> "+montantInteruptionAssurance+" </b>€.");
					
					/*****************FIN SECTION 6.MAJORATION INTERRUPTION ASSURANCE*******************/

					
					/*****************DEBUT SECTION 7.MAJORATION ANTECEDANT SINISTRES*******************/
					//Antecedent sinistre
					BigDecimal montantAntecedantSinistre = BigDecimal.ZERO;
					logCalculPrime("<h4>7.Calcul du montant due aux antécédants sinistres</h4>");
					logCalculPrime("la règle est la suivante :");
//					logCalculPrime("Si 1 sinistre coût maximum 15 000 € + 10%");
//					logCalculPrime("Si 2 sinistres coût maximum 20 000 € + 15 %");
//					logCalculPrime("Si 3 sinistre coût maximum 30 000 + 20 %");
//					logCalculPrime("Au-delà de 4 sinistres, demande soumis à étude.");	
					
					//Nouvelles règles sinistre leader Underwriting
					logCalculPrime("Si 0 sinistres et déjà assuré sur les 24 derniers mois -15%");
					logCalculPrime("De 1 à 3 sinistres cout max 10 000 € +15%");
					logCalculPrime("De 1 à 3 sinistres pour un montant maxi de 10 000 à 25 000 40%");
					logCalculPrime("Au-delà de 3 sinistres, demande soumis à étude.");

					BigDecimal tauxAntecedantSinistre = chercheTauxSinistre36mois().divide(new BigDecimal(100));
					logCalculPrime("<b>Ici le taux selon les antécédants sinistres est de "+tauxAntecedantSinistre+" (taux divisé par 100)</b>");
					logCalculPrime("Pour calculer le montant due aux  antécédants sinistres, il faut multiplié la prime base nette par ce taux, donc :");
					montantAntecedantSinistre = primeBaseNette.multiply(tauxAntecedantSinistre);
					logCalculPrime(primeBaseNette+" x "+tauxAntecedantSinistre+" = "+montantAntecedantSinistre);
					logCalculPrime(" le montant due aux antécédants sinistres est de  <b>"+montantAntecedantSinistre+"</b> €.");
					
					/*****************FIN SECTION 7.MAJORATION ANTECEDANT SINISTRES*******************/
					
						
					/*****************DEBUT SECTION 8.MAJORATION REPRISE PASSE*******************/
					//Reprise du passé du passé //+15%
					logCalculPrime("<h4>8.Calcul du montant due à la reprise du passé</h4>");
					logCalculPrime("la règle est la suivante :");
//					logCalculPrime("De 0 à 3 mois, aucune incidence");
//					logCalculPrime("De 4 à 6 mois + 5 % sur la prime de base");
//					logCalculPrime("De 7 à 12 mois + 7,5 % sur la prime de base");
					//Nouvelle regle Leader Underwriting
					logCalculPrime("20 % pour un maximum d'un an");
					BigDecimal montantReprisePasse = BigDecimal.ZERO;
					BigDecimal montantReprisePasseAssureur = BigDecimal.ZERO;
					BigDecimal montantReprisePasseYlyade = BigDecimal.ZERO;
					if(selectedTaDossierRcdDTO.getReprisePasse()!=null && selectedTaDossierRcdDTO.getReprisePasse()) {
						//BigDecimal tauxReprisePasse = taTauxAssuranceService.findByCode(TaTauxAssurance.REPRISE_DU_PASSE).getTauxTauxAssurance().divide(new BigDecimal(100));
						BigDecimal tauxReprisePasse = chercheTauxReprisePasse().divide(new BigDecimal(100));
						BigDecimal tauxReprisePasseAssureur = Const.C_TAUX_REPRISE_PASSE_ASSUREUR.divide(new BigDecimal(100));
						BigDecimal tauxReprisePasseYlyade = Const.C_TAUX_REPRISE_PASSE_YLYADE.divide(new BigDecimal(100));
						logCalculPrime("<b>Ici le taux due à la reprise du passé est de "+tauxReprisePasse+" (taux divisé par 100)</b>");
						logCalculPrime("Pour calculer le montant due à la reprise du passé, il faut multiplié la prime base nette par ce taux, donc :");
						montantReprisePasse = primeBaseNette.multiply(tauxReprisePasse);
						montantReprisePasseAssureur = primeBaseNette.multiply(tauxReprisePasseAssureur);
						montantReprisePasseYlyade = primeBaseNette.multiply(tauxReprisePasseYlyade);
						logCalculPrime(primeBaseNette+" x "+tauxReprisePasse+" = "+montantReprisePasse);
						logCalculPrime(" le montant due à la reprise du passé est de <b> "+montantReprisePasse+" </b>€.");
						logCalculPrime(" Ces "+chercheTauxReprisePasse()+" % sont divisés : "+Const.C_TAUX_REPRISE_PASSE_ASSUREUR+" % pour l'assureur et "+Const.C_TAUX_REPRISE_PASSE_ASSUREUR+"% pour Novarisks.");
						logCalculPrime("Donc "+montantReprisePasseAssureur+" € pour l'assureur et "+montantReprisePasseYlyade+" € pour Novarisks");
						
						JSONObject jsonDATAReprisePasse = new JSONObject(taSousDonnee.getJsonData());
						jsonDATAReprisePasse.put(TaSousDonnee.CLE_MONTANT_REPRISE_PASSE_ASSUREUR, montantReprisePasseAssureur);
						jsonDATAReprisePasse.put(TaSousDonnee.CLE_MONTANT_REPRISE_PASSE_YLYADE, montantReprisePasseYlyade);
						taSousDonnee.setJsonData(jsonDATAReprisePasse.toString());
					} else {
						logCalculPrime("<b>Reprise	du passé du	passé NON : "+montantReprisePasse+" </b>");
					}
					/*****************FIN SECTION 8.MAJORATION REPRISE PASSE*******************/
					
					/*****************DEBUT SECTION 9.MAJORATION FRANCHISE*******************/
					
					//Franchise : 4 000€ x1 *** 2 000€ (+15%) x1.15	 *** 1 000€ (+20%) x1.20	
					//Si au moins une des activités sélectionnés possède une franchise obligatoire, on prend la franchise obligatoire la plus élevé 
					//(même si elle ne corrrespond pas à l'activité  qui sert pour le calcul de base), dans ce cas il faudrait désactivé les boutons radios
					logCalculPrime("<h4>9.Calcul du montant majoration franchise</h4>");
					logCalculPrime("la règle est la suivante :");
					if(assurePib()) {
						logCalculPrime("Selon l'activité PIB de référence, une franchise de 2000 ou de 5000€ est séléctionnée.");
					}else {
						logCalculPrime("Si au moins une des activités sélectionnés possède une franchise obligatoire, on prend la franchise obligatoire la plus élevé");
					}
					
					BigDecimal montantFranchise = BigDecimal.ZERO;
					if(selectedTaDossierRcdDTO.getCodeFranchise()!=null) {
						TaTFranchise f = null;
						if(!assurePib()) { //la franchise potentiellement fixe en fonction de l'activité qui sert au calcul de la prime de base, verification à faire
		//					if(taActiviteDeReference!=null && taActiviteDeReference.getTaTFranchise()!=null) {
		//						//il y a une franchise "fixe" pour cette activité, on prend cette franchise en compte et on met à jour le DTO/affichage si une franchise "non autorisé" etait sélectionnée
		//						f = taActiviteDeReference.getTaTFranchise();
		//						selectedTaDossierRcdDTO.setCodeFranchise(f.getCodeTFranchise());
		//						logCalculPrime("Franchise 'fixe' "+f.getCodeTFranchise());
		//					} 
							if(r!=null && r.getFranchiseObligatoire()!=null) {
								//il y a une franchise "fixe" pour cette activité, on prend cette franchise en compte et on met à jour le DTO/affichage si une franchise "non autorisé" etait sélectionnée
								logCalculPrime("<b>Ici il y a une franchise obligatoire</b>");
								f = r.getFranchiseObligatoire();
		
								if(taTFranchiseService.findByCode(selectedTaDossierRcdDTO.getCodeFranchise()).getMontant().compareTo(f.getMontant())<0) {
									//on prends la franchise minimum obligatoire seulement si la franchise selectionne actuellement est inferieure
									logCalculPrime("<b>On prend la franchise minimum obligatoire car la franchise selectionné est inférieure.</b>");
									selectedTaDossierRcdDTO.setCodeFranchise(f.getCodeTFranchise());
									selectedTaDossierRcdDTO.setFranchise(f.getMontant());
								} else {
									//sinon : la franchise selectionne est superieure a la franchise obligatoire, donc on prend la franchise selectionne
									logCalculPrime("<b>On prend la franchise selectionné car elle est supérieure la franchise minimum obligatoire.</b>");
									f = taTFranchiseService.findByCode(selectedTaDossierRcdDTO.getCodeFranchise());
									selectedTaDossierRcdDTO.setFranchise(f.getMontant());
								}
								
								//selectedTaDossierRcdDTO.setCodeFranchise(f.getCodeTFranchise());
								logCalculPrime("Franchise 'fixe' "+f.getCodeTFranchise());
							} 
							if(f==null) { //pas de franchise "fixe"
								f = taTFranchiseService.findByCode(selectedTaDossierRcdDTO.getCodeFranchise());
								selectedTaDossierRcdDTO.setFranchise(f.getMontant());
								logCalculPrime("<b>pas de franchise 'fixe' sur l'activité de référence, choix libre</b>");
							}
							
							if(f!=null && f.getTauxMontant().compareTo(BigDecimal.ZERO)!=0) {
								BigDecimal tauxFranchise = f.getTauxMontant().divide(new BigDecimal(100));
								montantFranchise = primeBaseNette.multiply(tauxFranchise);
								logCalculPrime("Pour calculer le montant de la franchise (non PIB), il faut multiplé la prime de base nette par le taux de la franchise, donc :");
								logCalculPrime(primeBaseNette+" x "+tauxFranchise+" = "+montantFranchise);
								logCalculPrime("Le montant de la majoration Franchise  est donc de : <b>"+montantFranchise+"</b> €");
								logCalculPrime("Franchise ("+f.getCodeTFranchise()+" taux "+f.getTauxMontant()+") : "+montantFranchise);
							} else {
								logCalculPrime("<b>Franchise de base (pas de majoration) : "+montantFranchise+"</b>");
							}
						} else {// SI PIB
							
							
							if(selectedTaDossierRcdDTO.getCodeFranchise().equals(TaTFranchise.FR2000_PIB)) {
								f = fran2000;
								logCalculPrime("Ici c'est la <b>franchise de 2000€.</b>");
								selectedTaDossierRcdDTO.setFranchise(f.getMontant());
							} else if (selectedTaDossierRcdDTO.getCodeFranchise().equals(TaTFranchise.FR5000_PIB)) {
								f = fran5000;
								logCalculPrime("Ici c'est la <b>franchise de 5000€.</b>");
								selectedTaDossierRcdDTO.setFranchise(f.getMontant());
								
							}
							logCalculPrime("<b>Pour les PIB, pas de majoration</b>");

						}
						
					}
					
					BigDecimal tauxTaxesFiscaleRcProAssurance = BigDecimal.ZERO;
					
					logCalculPrime("Taux de la taxe fiscale de la RC PRO Décennale (divisé par 100) : " +tauxTaxesFiscaleRcProAssurance);
					BigDecimal primeAvantTaxeDiverses = primeAnnuelle;
					selectedTaDossierRcdDTO.setTauxTaxeFiscale(tauxTaxesFiscaleRcProAssurance);				
					
					/*****************FIN SECTION 9.MAJORATION FRANCHISE*******************/
					
					
					/*****************DEBUT SECTION 10.MAJORATION DOM*******************/
					
					logCalculPrime("<h4>10.Calcul du montant intervention DOM</h4>");
					//Si intervention dans les DOM
					BigDecimal montantMajorationDOM = BigDecimal.ZERO;
					if(selectedTaDossierRcdDTO.getTerritorialiteLieuDomtom()) {
						BigDecimal montantTerritorialite = primeBaseNette;
						logCalculPrime("<b>L'intervention est dans les DOM (Guadeloupe, Martinique, Réunion, Guyane)</b> donc on ajoutera "+Const.C_TAUX_MULTIPLY_INTERVENTION_DOM+" % à la prime de base (dans les majorations diverses voir section 11");
						montantTerritorialite = montantTerritorialite.multiply(Const.C_TAUX_MULTIPLY_INTERVENTION_DOM).divide(new BigDecimal(100)) ; 
						montantMajorationDOM = montantTerritorialite; 
					}else {
						logCalculPrime("<b>L'intervention n'est pas dans les DOM (Guadeloupe, Martinique, Réunion, Guyane)</b> donc pas de majorations DOM.");
					}
					logCalculPrime("Le montant majoration DOM est donc de <b>"+montantMajorationDOM+" € </b>");
					
					/*****************FIN SECTION 10.MAJORATION DOM*******************/
					
					/*****************DEBUT SECTION 11.MAJORATION DIVERSES*******************/
					
					logCalculPrime("<h4>11.Calcul du montant des majorations diverses</h4>");//anciennement taxes fiscales 
					
					//Protection Judiciaire (PJ) ou PJ ETENDUE
					JSONObject jsonPJ = new JSONObject(taSousDonnee.getJsonData());
					String codePj = jsonPJ.getString(TaSousDonnee.CLE_PJ_CHOISIE);
					BigDecimal montantProtectionJuridique = BigDecimal.ZERO;
					BigDecimal montantProtectionJuridiqueHT = BigDecimal.ZERO;
					if(codePj.isEmpty()) {
					}else {
						montantProtectionJuridique = taTauxAssuranceService.findByCode(codePj).getTauxTauxAssurance();
						montantProtectionJuridiqueHT = taTauxAssuranceService.findByCode(codePj).getTauxHtTauxAssurance();
					}
					selectedTaDossierRcdDTO.setMontantProtectionJuridique(montantProtectionJuridique);							
					
					logCalculPrime("Pour calculer le montant des majorations diverses, il faut ajouter :");
					logCalculPrime(" le montant résilié non paiement :" +montantResilieNonPaiement);
					logCalculPrime(" + le montant de la sous traitance : "+montantSousTraitance);
					logCalculPrime(" + le montant du risque par famille d'activite: "+montantRisqueParFamilleActivite);
					logCalculPrime(" + le montant due au nombre d'activité: "+montantNombreActivite);
					logCalculPrime(" + le montant due à l'interruption assurance: "+montantInteruptionAssurance);
					logCalculPrime(" + le montant due aux antécédants sinistres: "+montantAntecedantSinistre);
					logCalculPrime(" + le montant due à la reprise du passé: "+montantReprisePasse);
					logCalculPrime(" + le montant due à la franchise :"+montantFranchise);
					logCalculPrime(" + le montant Protection juridique choisie :"+montantProtectionJuridique);
					logCalculPrime(" + le montant de la majoration DOM :"+montantMajorationDOM);
					BigDecimal montantTaxesFiscaleRcProAssurance = BigDecimal.ZERO;//est en fait le montant des majorations diverses
					 montantTaxesFiscaleRcProAssurance = (montantTaxesFiscaleRcProAssurance
																	.add(montantResilieNonPaiement)
																	.add(montantSousTraitance)
																	.add(montantRisqueParFamilleActivite)
																	.add(montantNombreActivite)
																	.add(montantInteruptionAssurance)
																	.add(montantAntecedantSinistre)
																	.add(montantReprisePasse)
																	.add(montantFranchise)
																	.add(montantProtectionJuridique)
																	.add(montantMajorationDOM)
																	);
					logCalculPrime(" le montant des majorations diverses est de <b>"+montantTaxesFiscaleRcProAssurance+" </b>€.");
					selectedTaDossierRcdDTO.setMontantTaxesDiversesAssurance(montantTaxesFiscaleRcProAssurance);
					selectedTaDossierRcdDTO.setMontantTaxeFiscale(montantTaxesFiscaleRcProAssurance);				
					//pour affichage on regroupe taxe fiscale et frais de gestion => taxes diverses
//					logCalculPrime(" (les taxes diverses sont calculé comme suit : le montant des taxes fiscales ( "+montantTaxesFiscaleRcProAssurance+" €) + le montant des frais de gestion Compagnie et Ylyade ("+montantFraisGestionCompagnieYlyade+" €))");
					//selectedTaDossierRcdDTO.setMontantTaxesDiversesAssurance(montantTaxesFiscaleRcProAssurance.add(montantFraisGestionCompagnieYlyade));
									
					/*****************FIN SECTION 11.MAJORATION DIVERSES*******************/
					
					/*****************DEBUT SECTION 12.TAXES 9% *******************/
					logCalculPrime("<h4>12.Calcul taxes 9%</h4>");
					BigDecimal tauxTaxes9 = Const.C_TAUX_TAXES_9;
					BigDecimal montantPrimePlusTaxesHT = BigDecimal.ZERO;
					BigDecimal montantTaxes9 = BigDecimal.ZERO;
					logCalculPrime("La règle : Prime de base nette + Montant des majorations diverses pour trouver le montant total HT");
					logCalculPrime("et le montant total HT multiplié par le taux de taxes ("+tauxTaxes9+"%) / 100 pour trouver le montant des taxes 9%");
					logCalculPrime("Donc prime de base nette : "+primeBaseNette);
					logCalculPrime(" + Montant des majorations diverses : "+montantTaxesFiscaleRcProAssurance);
					montantPrimePlusTaxesHT = primeBaseNette;
					montantPrimePlusTaxesHT = montantPrimePlusTaxesHT.add(montantTaxesFiscaleRcProAssurance);
					logCalculPrime(" =  <b>Montant total HT : "+montantPrimePlusTaxesHT+" € </b>");				
					logCalculPrime("Ensuite, "+montantPrimePlusTaxesHT+" multiplié par "+tauxTaxes9+"/100 donne :");
					montantTaxes9 = montantPrimePlusTaxesHT;
					montantTaxes9 = montantTaxes9.multiply(tauxTaxes9).divide(new BigDecimal(100));
					logCalculPrime("<b>Montant des taxes 9% : "+montantTaxes9+" €</b>");
					// on stocke le montant des taxes 9% dans taSousDonnee
					JSONObject jsonDATAtaxes9= new JSONObject(taSousDonnee.getJsonData());
					jsonDATAtaxes9.put(TaSousDonnee.CLE_MONTANT_TAXES_9, montantTaxes9.toString());
					taSousDonnee.setJsonData(jsonDATAtaxes9.toString());
					/*****************FIN SECTION 12.TAXES 9% *******************/
					
					/*****************DEBUT SECTION 13.CALCUL PRIME CODE *******************/
					logCalculPrime("<h4>13.Calcul prime code</h4>");
					BigDecimal montantDPRSA = taTauxAssuranceService.findByCode(TaTauxAssurance.MONTANT_DPRSA).getTauxTauxAssurance();
					BigDecimal montantDPRSAHT = taTauxAssuranceService.findByCode(TaTauxAssurance.MONTANT_DPRSA).getTauxHtTauxAssurance();
					//14 avril 2020 rajout jurilaw 60€
					BigDecimal montantJurilaw = new BigDecimal(TaSousDonnee.VALEUR_JURILAW);
					BigDecimal montantPrimeCode = BigDecimal.ZERO;
					JSONObject jsonDataJurilaw;
					if(taSousDonnee.getJsonData()!=null) {
						jsonDataJurilaw = new JSONObject(taSousDonnee.getJsonData());
					}else {
						jsonDataJurilaw = new JSONObject();
					}
					jsonDataJurilaw.put(TaSousDonnee.CLE_JURILAW, TaSousDonnee.VALEUR_JURILAW);
					
					taSousDonnee.setJsonData(jsonDataJurilaw.toString());	
					
					logCalculPrime("La règle : Le montant prime code est égale a la somme des valeurs ci-dessous :");
					logCalculPrime("le montant total HT : "+montantPrimePlusTaxesHT);
					logCalculPrime("le montant taxes 9% : "+montantTaxes9);
					montantPrimeCode = montantPrimePlusTaxesHT;
					montantPrimeCode = montantPrimeCode.add(montantTaxes9);
					if(assurePib()) {// PIB
						logCalculPrime("le montant DPRSA : "+montantDPRSA);
						logCalculPrime("le montant JURILAW : "+montantJurilaw);
						montantPrimeCode.add(montantDPRSA);
						montantPrimeCode.add(montantJurilaw);
					}
					
					logCalculPrime("<b>Le montant prime code est donc de  : "+montantPrimeCode+" € </b>");

					
					/*****************FIN SECTION 13.CALCUL PRIME CODE *******************/
					
					/*****************DEBUT SECTION 14.CALCUL MONTANT HT *******************/
					BigDecimal montantPrimeCodeHT = BigDecimal.ZERO;
					logCalculPrime("<h4>14.Calcul Montant HT</h4>");
					logCalculPrime("La règle : le prime code ("+montantPrimeCode+") /1.09 ");
					montantPrimeCodeHT = montantPrimeCode;
					montantPrimeCodeHT = montantPrimeCodeHT.divide(new BigDecimal(1.09), 2, RoundingMode.HALF_UP);
					logCalculPrime("<b> Montant HT : "+montantPrimeCodeHT+" € </b>");
					
					/*****************FIN SECTION 14.CALCUL MONTANT HT *******************/
					
					/*****************DEBUT SECTION 15.CALCUL SUR-COMMISSION COMPAGNIE *******************/
					BigDecimal montantSurCommissionCompagnie = BigDecimal.ZERO;
					BigDecimal tauxCommissionCompagnie = BigDecimal.ZERO;
					if(!assurePib()) {
						tauxCommissionCompagnie = Const.C_TAUX_COMMISSION_COMPAGNIE;
					}else {
						tauxCommissionCompagnie = Const.C_TAUX_COMMISSION_COMPAGNIE_PIB;
					}
					logCalculPrime("<h4>15.Calcul sur-commission compagnie</h4>");
					logCalculPrime("La règle : le montant HT ("+montantPrimeCodeHT+") multiplié par "+tauxCommissionCompagnie+" / 100 ");
					montantSurCommissionCompagnie = montantPrimeCodeHT;
					montantSurCommissionCompagnie = montantSurCommissionCompagnie.multiply(tauxCommissionCompagnie).divide(new BigDecimal(100));
					logCalculPrime("<b> Montant sur-commission compagnie : "+montantSurCommissionCompagnie+" €</b>");
					
					/*****************FIN SECTION 15.CALCUL SUR-COMMISSION COMPAGNIE *******************/
					
					
					
					
					
					logCalculPrime("<h2>Fin des calculs préléminaires sur la base de la prime nette.</h2>");

					
					/*****************DEBUT SECTION 16.CALCUL PRIME ANNUELLE PROPOSEE *******************/
					BigDecimal primeAnnuelleProposee = BigDecimal.ZERO;
					logCalculPrime("<h4>16.Calcul de la prime annuelle proposée</h4>");
					logCalculPrime("Pour calculer la prime annuelle proposée, il faut faire la somme des montants ci-dessous :");
					logCalculPrime("le montant prime code :"+montantPrimeCode+" €");
					logCalculPrime("le montant de la commission Novarisks :"+commissionYlyade+" €");
					logCalculPrime("le montant de la commission Courtier  :"+commissionCourtier+" €");
					logCalculPrime("le montant de la sur-commission Compagnie  :"+montantSurCommissionCompagnie+" €");
					primeAnnuelleProposee = primeAnnuelleProposee.add(montantPrimeCode)
													.add(commissionYlyade)
													.add(commissionCourtier)
													.add(montantSurCommissionCompagnie)
													;
					logCalculPrime("la prime annuelle proposée est donc de <b>"+primeAnnuelleProposee+"</b> €");
					
					/*****************FIN SECTION 16.CALCUL PRIME ANNUELLE PROPOSEE *******************/
					
					/*****************DEBUT SECTION 17.CALCUL PRIME ANNUELLE TTC *******************/
					BigDecimal primeAnnuelleProposeeTTC = BigDecimal.ZERO;
					logCalculPrime("<h4>17.Calcul de la prime annuelle TTC</h4>");
					logCalculPrime("Pour calculer la prime annuelle TTC, il faut soustraire à la prime annuelle proposée ("+primeAnnuelleProposee+") les montants ci-dessous : ");
					logCalculPrime("le montant de la PJ :"+montantProtectionJuridique+" €");
					logCalculPrime("le montant de la DPRSA :"+montantDPRSA+" €");
					primeAnnuelleProposeeTTC = primeAnnuelleProposee;
					primeAnnuelleProposeeTTC = primeAnnuelleProposeeTTC.subtract(montantProtectionJuridique)
													.subtract(montantDPRSA)
													;
					logCalculPrime("la prime annuelle TTC est donc de <b>"+primeAnnuelleProposeeTTC+"</b> €");
					selectedTaDossierRcdDTO.setTarifAnnuelTotalTTC(primeAnnuelleProposeeTTC);					
					selectedTaDossierRcdDTO.setMontantPrimeAnnuelleTTC(primeAnnuelleProposeeTTC);
					
					/*****************FIN SECTION 17.CALCUL PRIME ANNUELLE TTC *******************/
					
					/*****************DEBUT SECTION 18.RECAP MONTANT *******************/
					logCalculPrime("<h4>18.Récap Montant</h4>");
					logCalculPrime("Prime RC DECENNALE (prime annuelle TTC): "+primeAnnuelleProposeeTTC);
					BigDecimal montantPjDPRSA = BigDecimal.ZERO;
					montantPjDPRSA = montantPjDPRSA.add(montantProtectionJuridique).add(montantDPRSA);
					logCalculPrime("Prime PJ choisie et DPRSA : "+montantPjDPRSA);
					/*****************FIN SECTION 18.RECAP MONTANT *******************/
					
					
					/*****************DEBUT SECTION 19.CALCUL DES FRAIS DE FRACTIONNEMENT SUR LA BASE : (tarif annuel TTC) *******************/
					logCalculPrime("<h4>19.Calcul des frais de fractionnement sur la base : (tarif annuel TTC) </h4>"); 
					BigDecimal primeAnnuelleSansComYlyadeNiComCourtier = BigDecimal.ZERO; //pour pouvoir calculé les frais de fractionnement sans prendre en compte les commissions (demandé par ylyade le 19/11/2018) pour afficherce montant uniquement dans les logs pour savoir combien reverser à la compagnie
					primeAnnuelleSansComYlyadeNiComCourtier = selectedTaDossierRcdDTO.getTarifAnnuelTotalTTC().subtract(commissionYlyade).subtract(commissionCourtier);
					BigDecimal fraisFractionnementSansComYlyadeNiComCourtier = BigDecimal.ZERO;
					logCalculPrime("La prime annuelle TTC  est de <b>"+selectedTaDossierRcdDTO.getTarifAnnuelTotalTTC()+"</b> € ("+primeAnnuelleSansComYlyadeNiComCourtier+" € sans la comm Novarisks ("+commissionYlyade+") € ni la comm courtier ("+commissionCourtier+"))");
					logCalculPrime(" Régle des frais de fractionnement : ");
					logCalculPrime("Frais de fractionnement :	Annuel	x1 ** Semestriel (+4%)	x1.04 ** Trimestriel (+7%)	x1.07");
					//Frais de fractionnement :	Annuel	x1 ** Semestriel (+4%)	x1.04 ** Trimestriel (+7%)	x1.07
					BigDecimal fraisFractionnement = BigDecimal.ZERO;
					if(selectedTaDossierRcdDTO.getCodeEcheance()==null ) {
						selectedTaDossierRcdDTO.setCodeEcheance(TaTEcheance.ANNUEL);
					}
					TaTEcheance ech = taTEcheanceService.findByCode(selectedTaDossierRcdDTO.getCodeEcheance());
					if(ech!=null && ech.getTauxEcheance().compareTo(BigDecimal.ZERO)!=0) {
						fraisFractionnement = selectedTaDossierRcdDTO.getTarifAnnuelTotalTTC().multiply(ech.getTauxEcheance()).divide(new BigDecimal(100));
						fraisFractionnementSansComYlyadeNiComCourtier = primeAnnuelleSansComYlyadeNiComCourtier.multiply(ech.getTauxEcheance()).divide(new BigDecimal(100));
						logCalculPrime("Pour calculer les frais de fractionnement, il faut multipler le tarif annuel total TTC("+selectedTaDossierRcdDTO.getTarifAnnuelTotalTTC()+") "
								+ "par le taux échéance("+ech.getTauxEcheance()+"/100) ");
						logCalculPrime("les frais de fractionnement (calcul sur la prime <b>incluant les commissions</b>) s'élève donc à <b>"+fraisFractionnement+" </b>€");
						logCalculPrime("les frais de fractionnement (calcul sur la prime <b>excluant les commissions</b>) s'élève donc à <b>"+fraisFractionnementSansComYlyadeNiComCourtier+" </b>€");
//						logCalculPrime("Echeance ("+selectedTaDossierRcdDTO.getCodeEcheance()+" taux "+ech.getTauxEcheance()+"% montant :"+fraisFractionnement+"€) ");
					} else {
						logCalculPrime("<b>Echeance de base (annuelle, pas de majoration) :</b> ");
					}
					selectedTaDossierRcdDTO.setMontantFraisFractionnement(fraisFractionnement);
					/*****************FIN SECTION 19.CALCUL DES FRAIS DE FRACTIONNEMENT SUR LA BASE : (tarif annuel TTC) *******************/
					
					/*****************DEBUT SECTION 20.CALCUL DE LA PRIME ANNUELLE COMPLETE *******************/
					logCalculPrime("<h4>20.Calcul de la prime Annuelle complète</h4> ");
					logCalculPrime("Pour calculer la prime annuelle complète, il faut ajouter les frais de fractionnement ("+fraisFractionnement+" €) à la prime annuelle proposée ("+primeAnnuelleProposee+" €). ");
					selectedTaDossierRcdDTO.setPrimeAnnuelleComplete(primeAnnuelleProposee.add(fraisFractionnement));
					BigDecimal primeAnnuelleComplete = selectedTaDossierRcdDTO.getPrimeAnnuelleComplete();
					logCalculPrime("La prime annuelle complète est donc de <b>"+selectedTaDossierRcdDTO.getPrimeAnnuelleComplete()+"</b> €");
					/*****************FIN SECTION 20.CALCUL DE LA PRIME ANNUELLE COMPLETE *******************/
					
					/*****************DEBUT SECTION 21.CALCUL DU TARIF ANNUEL TTC + FRAIS FRACTIONNEMENT *******************/
					logCalculPrime("<h4> Calcul du tarif annuel TTC plus Frais de fractionnement </h4> ");
					logCalculPrime("Pour calculer le tarif annuel TTC plus Frais de fractionnement, il suffit d'ajouter les frais de fractionnement("+fraisFractionnement+" €) "
							+ "au tarif annuel total TTC ("+selectedTaDossierRcdDTO.getTarifAnnuelTotalTTC()+" €)");
					selectedTaDossierRcdDTO.setTarifAnnuelTtcPlusFraisFractionnement(selectedTaDossierRcdDTO.getTarifAnnuelTotalTTC().add(fraisFractionnement));
					logCalculPrime("Le tarif annuel TTC plus Frais de fractionnement est donc de <b>"+selectedTaDossierRcdDTO.getTarifAnnuelTtcPlusFraisFractionnement()+" </b>€");
					/*****************FIN SECTION 21.CALCUL DU TARIF ANNUEL TTC + FRAIS FRACTIONNEMENT *******************/
					
					
					
					/*****************CALCUL PREMIER REGLEMENT *******************/
					
					/*****************DEBUT SECTION 22.CALCUL DE L'ECHEANCE *******************/
					if(ech!=null && ech.getLiblTEcheance()!=null) {
						selectedTaDossierRcdDTO.setLiblTEcheance(ech.getLiblTEcheance());
					}
					
					if(selectedTaDossierRcdDTO.getDateEffet()!=null && ech!=null) {
						LocalDate localDateEffet = selectedTaDossierRcdDTO.getDateEffet().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						Date fin1erPeriode = null;
						if(ech.getCodeTEcheance().equals(TaTEcheance.ANNUEL)) {
							fin1erPeriode = Date.from(LocalDate.of(localDateEffet.getYear(), localDateEffet.getMonthValue(),localDateEffet.getDayOfMonth()).plus(1, ChronoUnit.YEARS).atStartOfDay(ZoneId.systemDefault()).toInstant());
						} else if(ech.getCodeTEcheance().equals(TaTEcheance.SEMESTRIEL)) {
							fin1erPeriode = Date.from(LocalDate.of(localDateEffet.getYear(), localDateEffet.getMonthValue(),localDateEffet.getDayOfMonth()).plus(6, ChronoUnit.MONTHS).atStartOfDay(ZoneId.systemDefault()).toInstant());
						} else if(ech.getCodeTEcheance().equals(TaTEcheance.TRIMESTRIEL)) {
							fin1erPeriode = Date.from(LocalDate.of(localDateEffet.getYear(), localDateEffet.getMonthValue(),localDateEffet.getDayOfMonth()).plus(3, ChronoUnit.MONTHS).atStartOfDay(ZoneId.systemDefault()).toInstant());
						}
						LocalDate localFin1erPeriode = fin1erPeriode.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						fin1erPeriode = Date.from(LocalDate.of(localFin1erPeriode.getYear(), localFin1erPeriode.getMonthValue(),localFin1erPeriode.getDayOfMonth()).minus(1, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault()).toInstant());
						selectedTaDossierRcdDTO.setDateFinPremierePeriode(fin1erPeriode);
						logCalculPrime("Date Fin 1ère période : " + LibDate.dateToString(fin1erPeriode));
					}
					
					
					BigDecimal uneEcheance = BigDecimal.ZERO;
					BigDecimal montantCommissionCourtierUneEcheance = BigDecimal.ZERO;
					BigDecimal montantCommissionYlyadeUneEcheance = BigDecimal.ZERO;
					logCalculPremierReglement("<h2>22.Calcul de l'échéance</h2>");
					if(selectedTaDossierRcdDTO.getCodeEcheance().equals(TaTEcheance.ANNUEL)) {
						logCalculPremierReglement("Puisque le type d'échéance choisi est annuel, l'échéance est égale à la prime annuelle (complète : prime annuelle + frais fractionnement). ");
						uneEcheance = primeAnnuelleComplete;
						logCalculPremierReglement("l'échéance est donc de <b>"+uneEcheance+"</b> €");
						montantCommissionCourtierUneEcheance = commissionCourtier;
						montantCommissionYlyadeUneEcheance = commissionYlyade;
						logCalculPremierReglement("le montant de commission du courtier sur une échéance est égal à la commission courtier.");
						logCalculPremierReglement("donc le montant de commission du courtier est de <b>"+montantCommissionCourtierUneEcheance+" </b>€");
						
						logCalculPremierReglement("le montant de commission Novarisks sur une échéance est égal à la commission Novarisks.");
						logCalculPremierReglement("donc le montant de commission Novarisks est de <b>"+montantCommissionYlyadeUneEcheance+" </b>€");
					} else if(selectedTaDossierRcdDTO.getCodeEcheance().equals(TaTEcheance.SEMESTRIEL)) {
						logCalculPremierReglement("Puisque le type d'échéance choisi est semestriel, l'échéance est égale à la prime annuelle complète (prime annuelle + frais fractionnement) divisé par 2 ");
						logCalculPremierReglement("l'échéance est donc :");
						uneEcheance = primeAnnuelleComplete.divide(new BigDecimal(2));
						logCalculPremierReglement(primeAnnuelleComplete+" / 2 = <b>"+uneEcheance+"</b> €");
						logCalculPremierReglement("le montant de commission du courtier sur une échéance est égal à la commission courtier divisé par 2.");
						montantCommissionCourtierUneEcheance = commissionCourtier.divide(new BigDecimal(2));
						logCalculPremierReglement("donc le montant de commission courtier est de "+commissionCourtier+" / 2 = <b>"+montantCommissionCourtierUneEcheance+"</b> €");
						
						logCalculPremierReglement("le montant de commission Novarisks sur une échéance est égal à la commission Novarisks divisé par 2.");
						montantCommissionYlyadeUneEcheance = commissionYlyade.divide(new BigDecimal(2));
						logCalculPremierReglement("donc le montant de commission Novarisks est de "+commissionYlyade+" / 2 = <b>"+montantCommissionYlyadeUneEcheance+"</b> €");
					} else if(selectedTaDossierRcdDTO.getCodeEcheance().equals(TaTEcheance.TRIMESTRIEL)) {
						logCalculPremierReglement("Puisque le type d'échéance choisi est trimestriel, l'échéance est égale à la prime annuelle complète (prime annuelle + frais fractionnement)divisé par 4. ");
						logCalculPremierReglement("l'échéance est donc :");
						uneEcheance = primeAnnuelleComplete.divide(new BigDecimal(4));
						logCalculPremierReglement(primeAnnuelleComplete+" / 4 = <b>"+uneEcheance+"</b> €");
						montantCommissionCourtierUneEcheance = commissionCourtier.divide(new BigDecimal(4));
						logCalculPremierReglement("le montant de commission du courtier sur une échéance est égal à la commission courtier divisé par 4.");
						logCalculPremierReglement("donc le montant de commission du courtier est de "+commissionCourtier+" / 4 = <b>"+montantCommissionCourtierUneEcheance+"</b> €");
						
						montantCommissionYlyadeUneEcheance = commissionYlyade.divide(new BigDecimal(4));
						logCalculPremierReglement("le montant de commission Novarisks sur une échéance est égal à la commission Novarisks divisé par 4.");
						logCalculPremierReglement("donc le montant de commission Novarisks est de "+commissionYlyade+" / 4 = <b>"+montantCommissionYlyadeUneEcheance+"</b> €");
					}

					JSONObject jsonDataComEchYlyade;
					if(taSousDonnee.getJsonData()!=null) {
						jsonDataComEchYlyade = new JSONObject(taSousDonnee.getJsonData());
					}else {
						jsonDataComEchYlyade = new JSONObject();
					}
					jsonDataComEchYlyade.put(TaSousDonnee.CLE_MONTANT_SUR_ECHEANCE_COMMISSION_YLYADE, montantCommissionYlyadeUneEcheance.toString());
					//rajout frais d'emission 14 avril 2020
					jsonDataComEchYlyade.put(TaSousDonnee.CLE_FRAIS_EMISSION_ASSUREUR, TaSousDonnee.VALEUR_FRAIS_EMISSION_ASSUREUR);
					BigDecimal montantFraisEmissionAssureur =  new BigDecimal(TaSousDonnee.VALEUR_FRAIS_EMISSION_ASSUREUR);
					
					taSousDonnee.setJsonData(jsonDataComEchYlyade.toString());
					
					taDossierRcd.setMontantPrime(uneEcheance);
					taDossierRcd.setMontantCommissionCourtierUneEcheance(montantCommissionCourtierUneEcheance);
 					selectedTaDossierRcdDTO.setMontantPrime(uneEcheance);
 					logCalculPremierReglement("<b>Montant d'une écheance ("+uneEcheance+"€) </b>");
 					
 					/*****************FIN SECTION 22.CALCUL DE L'ECHEANCE *******************/
 					
 					
 					/*****************DEBUT SECTION 23.CALCUL DU MONTANT DU 1ER REGLEMENT  *******************/
					BigDecimal montantFraisGestionCompagnieYlyade;
					if(!assurePib()) {
						//Frais de gestion compagnie (Ylyade) (FG), (montant fixe) : 150€ ===> inclus uniquement dans le 1er paiement
						// montantFraisGestionCompagnieYlyade = taTFraisImpayeService.findByCode(TaTFraisImpaye.FRAIS_DOSSIER_YLYADE).getMontant();
						BigDecimal montantFraisGestionNovarisks = new BigDecimal(TaSousDonnee.VALEUR_FRAIS_GESTION_ASSUREUR);
						BigDecimal montantFraisGestionAssureur = new BigDecimal(TaSousDonnee.VALEUR_FRAIS_GESTION_YLYADE);
						 montantFraisGestionCompagnieYlyade = montantFraisGestionNovarisks.add(montantFraisGestionAssureur);
					} else {
						//Frais de gestion compagnie PIB (Ylyade) (FG), (montant fixe) : 200€ ===> inclus uniquement dans le 1er paiement
						// montantFraisGestionCompagnieYlyade = taTFraisImpayeService.findByCode(TaTFraisImpaye.FRAIS_DOSSIER_PIB).getMontant();
						BigDecimal montantFraisGestionNovarisks = new BigDecimal(TaSousDonnee.VALEUR_FRAIS_GESTION_ASSUREUR_PIB);
						BigDecimal montantFraisGestionAssureur = new BigDecimal(TaSousDonnee.VALEUR_FRAIS_GESTION_YLYADE_PIB);
						 montantFraisGestionCompagnieYlyade = montantFraisGestionNovarisks.add(montantFraisGestionAssureur);
					}
					selectedTaDossierRcdDTO.setMontantFraisDeGestion(montantFraisGestionCompagnieYlyade);
					JSONObject jsonData;
					if(taSousDonnee.getJsonData()!=null) {
						 jsonData = new JSONObject(taSousDonnee.getJsonData());
					}else {
						 jsonData = new JSONObject();
					}
					if(!assurePib()) {
						jsonData.put(TaSousDonnee.CLE_FRAIS_GESTION_YLYADE, TaSousDonnee.VALEUR_FRAIS_GESTION_YLYADE);
						jsonData.put(TaSousDonnee.CLE_FRAIS_GESTION_ASSUREUR, TaSousDonnee.VALEUR_FRAIS_GESTION_ASSUREUR);
						taSousDonnee.setJsonData(jsonData.toString());
						logCalculPrime("Frais de gestion ( "+TaSousDonnee.VALEUR_FRAIS_GESTION_YLYADE+"€ pour Novarisks, "+TaSousDonnee.VALEUR_FRAIS_GESTION_ASSUREUR+"€ pour l'assureur : "+montantFraisGestionCompagnieYlyade+"€ ) ");
					}else {
						jsonData.put(TaSousDonnee.CLE_FRAIS_GESTION_YLYADE, TaSousDonnee.VALEUR_FRAIS_GESTION_YLYADE_PIB);
						jsonData.put(TaSousDonnee.CLE_FRAIS_GESTION_ASSUREUR, TaSousDonnee.VALEUR_FRAIS_GESTION_ASSUREUR_PIB);
						taSousDonnee.setJsonData(jsonData.toString());
						logCalculPrime("Frais de gestion ( "+TaSousDonnee.VALEUR_FRAIS_GESTION_YLYADE_PIB+"€ pour Novarisks, "+TaSousDonnee.VALEUR_FRAIS_GESTION_ASSUREUR_PIB+"€ pour l'assureur : "+montantFraisGestionCompagnieYlyade+"€ ) ");
					}
								
	//				//Frais de gestion compagnie () Super Assureur (FG), (montant fixe) : 150€ ===> inclus uniquement dans le 1er paiement
//					BigDecimal montantFraisGestionCompagnieSuperAssureur = taTFraisImpayeService.findByCode(TaTFraisImpaye.FRAIS_DOSSIER_COMPAGNIE_SUPERASSUREUR).getMontant();
					BigDecimal montantFraisGestionCompagnieSuperAssureur = new BigDecimal(0.00);	
					//Frais	de souscription, frais de dossier/courtier, montant fixe saisi par le courtier	
					BigDecimal fraisCourtier = BigDecimal.ZERO;
					if(selectedTaDossierRcdDTO.getFraisRcPro()!=null) {
						fraisCourtier = selectedTaDossierRcdDTO.getFraisRcPro();
						logCalculPrime("frais dossier/courtier ("+selectedTaDossierRcdDTO.getFraisRcPro()+"€) ");
					} else {
						logCalculPrime("frais dossier/courtier 0€ : "+fraisCourtier);
					}
					BigDecimal montant1erReglement = BigDecimal.ZERO;
					BigDecimal montantFraisSouscription = Const.C_MONTANT_FRAIS_SOUSCRIPTION;
					logCalculPremierReglement("<h2>23.Calcul du montant du 1er règlement</h2>");
					logCalculPremierReglement("Pour calculer le montant du 1er règlement il faut ajouter à l'échéance ("+uneEcheance+")"
							+ " le montant des frais de gestion compagnie Novarisks et Assureur("+montantFraisGestionCompagnieYlyade+")"
							+ " Les frais d'emission compagnie ("+montantFraisEmissionAssureur+")"
							+ " Les frais de souscription ("+montantFraisSouscription+")"
							);
					montant1erReglement = uneEcheance.add(montantFraisGestionCompagnieYlyade)
													 .add(montantFraisEmissionAssureur)
													 .add(montantFraisSouscription)
													 ;
					logCalculPremierReglement("le montant du premier règlement est donc de <b>"+montant1erReglement+"</b> €");
					if(selectedTaDossierRcdDTO.getInclusionFraisDossier()) {
						logCalculPremierReglement("<b>Puisque l'option inclure les frais de dossier est choisie</b>, on ajoute les frais de courtier ("+fraisCourtier+") au  montant du 1er règlement ("+montant1erReglement+") ");
						montant1erReglement = montant1erReglement.add(fraisCourtier);
						logCalculPremierReglement("Ce qui donne le calcul suivant pour le montant du 1er règlement : ");
						logCalculPremierReglement("Montant 1er règlement = une échéance :"+uneEcheance+" + les frais de gestion Novarisks et Assureur :"+montantFraisGestionCompagnieYlyade+ " + les frais de dossier (courtier) : "+fraisCourtier+"  = <b>"+montant1erReglement+" €</b>");
						logCalculPremierReglement("le montant du 1er règlement est donc de <b>"+montant1erReglement+" </b>€");
					} else {
						logCalculPremierReglement("<b>Puisque l'option inclure les frais de dossier n'est pas choisie</b> : ");
						logCalculPremierReglement("le calcul est le suivant pour le montant du 1er règlement : ");
						logCalculPremierReglement("Montant 1er règlement = une échéance : "+uneEcheance+" € + les frais de gestion Novarisks et Assureur : "+montantFraisGestionCompagnieYlyade+ "€  = <b>"+montant1erReglement+" €</b>");
						logCalculPremierReglement("le montant du 1er règlement est donc de <b>"+montant1erReglement+"</b> €");
					}
					
					selectedTaDossierRcdDTO.setMontantPremierReglement(montant1erReglement);
					
					/*****************FIN SECTION 23.CALCUL DU MONTANT DU 1ER REGLEMENT  *******************/

					
					logCalculPrime("***");
					logCalculPremierReglement("***");
					
					logCalculPrime("<h2>FIN CALCUL DE LA PRIME</h2>");	
					selectedTaDossierRcdDTO.setLogDetailCalculPrime(logDebugCalculPrime);
					selectedTaDossierRcdDTO.setLogDetailCalculPremierReglement(logDebugCalculPremierReglement);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			logCalculPrime("=====Une ou plusieurs erreurs bloquante empeche le calcul de la prime============");	
		}
			
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
			classPulseBoutonEnregistrer(false);
			switch (modeEcran.getMode()) {
			case C_MO_INSERTION:
				if(selectedTaDossierRcdDTO.getNumDossierPolice()!=null) {
					taDossierRcdService.annuleCode(selectedTaDossierRcdDTO.getNumDossierPolice());
					taAssureService.annuleCode(selectedTaDossierRcdDTO.getTaAssureDTO().getCodeAssure());
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
			System.out.println("Taille activités séléctionnez : "+selectedTaTauxRcproPibDTOs.size());
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
			if(selectedTaActiviteDTOs==null) {
				selectedTaActiviteDTOs = new ArrayList<TaActiviteDTO>(); 
			}
			if(taDossierRcd.getTaDevisRcProActivites()!=null && !taDossierRcd.getTaDevisRcProActivites().isEmpty()) {
				//for (TaActivite ru : taDossierRcd.getTaDevisRcProActivites()) {
				for (TaDossierRcdActivite ru : taDossierRcd.getTaDevisRcProActivites()) {
					for (TaActiviteDTO rdto : taActiviteDTODisponible) {
						//if(ru.getIdActivite()==rdto.getId()) {
						//if(ru.getIdDevisRcProActivite()==rdto.getId()) {
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
				System.out.println("Taille activités séléctionnez : "+selectedTaTauxRcproPibDTOs.size());
				for (TaDossierRcdTauxPib ru : taDossierRcd.getTaDevisRcProTauxPibs()) {// on boucle sur les activités PIB du dossier
					for (TaTauxRcproPibDTO rdto : taTauxRcproPibDTODisponible) {// on boucle sur toutes les activités disponible
						//if(ru.getIdActivite()==rdto.getId()) {
						//if(ru.getIdDevisRcProActivite()==rdto.getId()) {
						if(ru.getCodeTauxRcProPib().equals(rdto.getCodeTauxRcproPib())) {// si l'activité du dossier est identique a l'activité disponible
							if(!selectedTaTauxRcproPibDTOs.contains(rdto)) {//si dans les activités séléctionné il n'y a pas cette activité disponible
								selectedTaTauxRcproPibDTOs.add(rdto);// on ajoute cette activité aux activités séléctionnées.
								System.out.println("Taille activités séléctionnez : "+selectedTaTauxRcproPibDTOs.size());
							}
						}
					}
				}
				
				System.out.println("Taille activités séléctionnez : "+selectedTaTauxRcproPibDTOs.size());
			}
			
		} catch (FinderException e) {
			e.printStackTrace();
		}
	}

	public void actEnregistrer(ActionEvent actionEvent) {

			try {
				
				classPulseBoutonEnregistrer(false);
				
				
				if(!assurePib()) {
					taTFranchise = taTFranchiseService.findByCode(selectedTaDossierRcdDTO.getCodeFranchise());
					selectedTaDossierRcdDTO.setFranchise(taTFranchise.getMontant());
				}
				
				calculMontantPrime();
				
					
				autoCompleteMapUIToDTO();
//				donneStatut(selectedTaDossierRcdDTO);
//				taDossierRcdService.donneStatut(selectedTaDossierRcdDTO);
				IDonneStatut Idossier = taDossierRcdService.donneStatut(selectedTaDossierRcdDTO);
				selectedTaDossierRcdDTO.getTaTStatut().clear();
	        	for (TaTStatut stat : Idossier.getTaTStatut()) {
	        		selectedTaDossierRcdDTO.getTaTStatut().add(stat);
				}
				
				mapperUIToModel.map(selectedTaDossierRcdDTO, taDossierRcd);
				if(selectedTaDossierRcdDTO.getLettrePjNumPolice()!= null) {
					taDossierRcd.setLettrePjNumPolice(selectedTaDossierRcdDTO.getLettrePjNumPolice());
				}
				
				
				mapperUIToModelAssure.map(selectedTaDossierRcdDTO.getTaAssureDTO(), taDossierRcd.getTaAssure());
						
				mapperUIToModelDetail.map(selectedTaDossierRcdDTO, taDossierRcd);
				if(selectedTaDossierRcdDTO.getMontantPrime() == null) {
					taDossierRcd.setMontantPrime(null);
				}
				if(selectedTaDossierRcdDTO.getMontantPremierReglement() == null) {
					taDossierRcd.setMontantPremierReglement(null);
				}
				
				
				Date dateDevis = new Date();
				selectedTaDossierRcdDTO.setDateDevis(dateDevis); //date fixé au moment de la génération du PDF et non modifiable
				taDossierRcd.setDateDevis(dateDevis);
				
				if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_INSERTION)==0 
						&& creationCompteClientAssure) { //on ne modifie pas le mdp en modification de devis et on ne vérifie pas le mdp si pas de creation de compte assuré
					if(taDossierRcd.getTaAssure().getIdAssure()==null ||  taDossierRcd.getTaAssure().getIdAssure()==0) { //on ne modifie pas le mdp d'un assure deja existant
						if(selectedTaDossierRcdDTO.getTaAssureDTO().getPassword()!=null 
								&& !selectedTaDossierRcdDTO.getTaAssureDTO().getPassword().equals("")
								&& selectedTaDossierRcdDTO.getTaAssureDTO().getPassword().equals(selectedTaDossierRcdDTO.getTaAssureDTO().getPasswordConfirm())) {
							//TODO faire la vérification avec le mot de passe de confirmation
							//TODO verifier la complexité minimum
							if(creationCompteClientAssure) {
								TaUtilisateur taUtilisateur = new TaUtilisateur();
								TaTUtilisateur taTUtilisateur = null; 
								taTUtilisateur = taTUtilisateurService.findByCode(TaTUtilisateur.TYPE_UTILISATEUR_ASSURE);
								taUtilisateur.setTaTUtilisateur(taTUtilisateur);
								taUtilisateur.setAdmin(false);
								taUtilisateur.setSysteme(false);
								taDossierRcd.getTaAssure().setTaUtilisateur(taUtilisateur);
								
								taDossierRcd.getTaAssure().getTaUtilisateur().setPassword(taDossierRcd.getTaAssure().getTaUtilisateur().passwordHashSHA256_Base64(selectedTaDossierRcdDTO.getTaAssureDTO().getPassword()));
							}
						}else {
							throw new Exception("Mot de passe incomplet ou ne correspond pas à sa confirmation");
						}	
					}
				}
				if(selectedTaDossierRcdDTO.getTaAssureDTO().getAdresseEmail()!=null) {
					if(taDossierRcd.getTaAssure().getTaUtilisateur()!=null) {//on vient de créer un utilisateur (bloc au dessus) ou on est en modif d'un assure qui a deja un compte utilisateur
						taDossierRcd.getTaAssure().getTaUtilisateur().setIdentifiant(selectedTaDossierRcdDTO.getTaAssureDTO().getAdresseEmail());
					}
				}
				
				if(!assurePib()) {
					if(selectedTaDossierRcdDTO.getListeActivite()!=null 
							&& !selectedTaDossierRcdDTO.getListeActivite().isEmpty()) {
						

						
						
						if(taDossierRcd.getTaDevisRcProActivites()==null) {
							taDossierRcd.setTaDevisRcProActivites(new ArrayList<TaDossierRcdActivite>());
						} else {
							taDossierRcd.getTaDevisRcProActivites().clear();
						}
						for (TaActiviteDTO r : selectedTaDossierRcdDTO.getListeActivite()) {
							boolean trouve = false;
							for(TaDossierRcdActivite actDevis : taDossierRcd.getTaDevisRcProActivites()) {
								if(actDevis.getActivite().equals(r.getCodeActivite())) {
									trouve = true;
									actDevis.setActivite(r.getCodeActivite());
									actDevis.setPourcentExerceEntreprise(r.getPourcentExerceEntreprise());
									actDevis.setPourcentSousTraite(r.getPourcentSousTraite());
									actDevis.setCommentaire(r.getCommentaire());
									//actDevis.setTaActivite(taActiviteService.findById(r.getId()));
								}
							}
							if(!trouve) {
								//taDossierRcd.getTaDevisRcProActivites().add(taActiviteService.findById(r.getId()));
								TaDossierRcdActivite devisRcProActivite = new TaDossierRcdActivite();
								devisRcProActivite.setActivite(r.getCodeActivite());
								devisRcProActivite.setPourcentExerceEntreprise(r.getPourcentExerceEntreprise());
								devisRcProActivite.setPourcentSousTraite(r.getPourcentSousTraite());
								devisRcProActivite.setCommentaire(r.getCommentaire());
								devisRcProActivite.setTaActivite(taActiviteService.findById(r.getId()));
	//							devisRcProActivite.setClasseAssocie(r.getCodeClasse());
								taDossierRcd.getTaDevisRcProActivites().add(devisRcProActivite);
								devisRcProActivite.setTaDossierRcd(taDossierRcd);
							}
						}
		//				//supression des activités désélectionnées
		//				List<TaDossierRcdActivite> l = new ArrayList<>();
		//				if(taDossierRcd.getTaDevisRcProActivites()!=null) {
		//					for(TaDossierRcdActivite actDevis : taDossierRcd.getTaDevisRcProActivites()) { //recherche des activités à supprimer
		//						boolean trouve = false;
		//						for (TaActiviteDTO r : selectedTaDossierRcdDTO.getListeActivite()) {
		//							if(actDevis.getActivite().equals(r.getCodeActivite())) {
		//								trouve = true;
		//							}
		//							if(!trouve) {
		//								l.add(actDevis);
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
							&& !selectedTaDossierRcdDTO.getListeTauxRcProPib().isEmpty()) {//Si la liste d'activité PIB du DossierDTO n'est pas vide ou nulle
						if(taDossierRcd.getTaDevisRcProTauxPibs()==null) {// Si la liste d'activité PIB du Dossier (entité) est pas nulle
							taDossierRcd.setTaDevisRcProTauxPibs(new ArrayList<TaDossierRcdTauxPib>());// On créer une nouvelle liste
						} else {// Si elle n'est pas vide
							
							for(TaDossierRcdTauxPib a :taDossierRcd.getTaDevisRcProTauxPibs()) {
								a.setTaDossierRcd(null);
							}
							taDossierRcd.getTaDevisRcProTauxPibs().clear();//On la vide
						}
						for (TaTauxRcproPibDTO r : selectedTaDossierRcdDTO.getListeTauxRcProPib()) {//boucle sur les activités PIB du DossierDTO
							boolean trouve = false;
							for(TaDossierRcdTauxPib actDevis : taDossierRcd.getTaDevisRcProTauxPibs()) {//boucle sur les activités PIB du Dossier (entité)
								if(actDevis.getCodeTauxRcProPib().equals(r.getCodeTauxRcproPib())) {//si même activités
									trouve = true;//trouve
								}
							}
							if(!trouve) {//si aucune activité du dossier ne correspond à l'activité courante du DTO
								//taDossierRcd.getTaDevisRcProActivites().add(taActiviteService.findById(r.getId()));
								TaDossierRcdTauxPib devisRcProActivite = new TaDossierRcdTauxPib();//On créer une nouvelle activité
								devisRcProActivite.setCodeTauxRcProPib(r.getCodeTauxRcproPib());
								devisRcProActivite.setLiblTauxRcProPib(r.getLiblTauxRcproPib());
			//					devisRcProActivite.setClasseAssocie(r.getCodeClasse());
								taDossierRcd.getTaDevisRcProTauxPibs().add(devisRcProActivite);// on l'ajoute aux activités du dossier
								devisRcProActivite.setTaDossierRcd(taDossierRcd);
							}
						}
		//				//supression des activités désélectionnées
	//					List<TaDossierRcdTauxPib> l = new ArrayList<>();
	//					if(taDossierRcd.getTaDevisRcProTauxPibs()!=null) {
	//						for(TaDossierRcdTauxPib actDevis : taDossierRcd.getTaDevisRcProTauxPibs()) { //recherche des activités à supprimer
	//							boolean trouve = false;
	//							for (TaTauxRcproPibDTO r : selectedTaDossierRcdDTO.getListeTauxRcProPib()) {
	//								if(actDevis.getCodeTauxRcProPib().equals(r.getCodeTauxRcproPib())) {
	//									trouve = true;
	//								}
	//								if(!trouve) {
	//									l.add(actDevis);
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
					//this.setListeTaGedUtilisateurDTO(getlisteTaGedUtilisateurDTO());
					gedDevisRcProController.setMasterEntity(taDossierRcd);
					gedDevisRcProController.refresh();
					
					listeTaGedUtilisateurDTO = initListeGed();
					listeByType = taListeRefDocService.findByType(TaTListeRefDoc.ID_TYPE_LISTE_REF_DOC_RCPRO);
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
					//taDevisRcPro.setTaGedUtilisateur(listeTaGedUtilisateur);
				}
				if (taDossierRcd.getTaSinistreAntecedent() == null) {
					taDossierRcd.setTaSinistreAntecedent(new ArrayList<TaSinistreAntecedent>());
				} 
				if (taDossierRcd.getTaDevisRcProActivites() == null) {
					taDossierRcd.setTaDevisRcProActivites(new ArrayList<TaDossierRcdActivite>());
				} 
				
				taDossierRcd.getTaTStatut().clear();
				if(selectedTaDossierRcdDTO.getTaTStatut()!=null 
						&& !selectedTaDossierRcdDTO.getTaTStatut().isEmpty()) {
					
		            for (TaTStatut s : selectedTaDossierRcdDTO.getTaTStatut()) {
		            	taDossierRcd.getTaTStatut().add(s);
					}
				}
				
				
//				if(taDossierRcd.getTaDevisRcProTauxPibs()!= null) {
//					taDossierRcd.getTaDevisRcProTauxPibs().clear();
//				}
				//MERGE//
				Boolean refreshPremierech = false;
				/**REGENERATION DES ECHEANCES SI DOSSIER SOUMIS A SOUSCRIPTION MAIS PAS DE 1ER PAIEMENT**/
				if(taDossierRcd.getSoumisSouscription()  && !taDossierRcd.getPremierPaiementEffectue()) {
					if(taDossierRcd.getTaTReglement().getCodeTReglement().equals(TaTReglement.CHEQUE)) {
						if(selectedTaDossierRcdDTO.getDateEnvoiChequeParCourtier() == null) {
							genereEcheances();
							refreshPremierech = true;
						}else {
							blocageChampsApresPremiereActionReglement = true;
						}
					}else if (taDossierRcd.getTaTReglement().getCodeTReglement().equals(TaTReglement.VIR)) {
						if(selectedTaDossierRcdDTO.getDateVirementEffectue() == null) {
							genereEcheances();
							refreshPremierech = true;
						}else {
							blocageChampsApresPremiereActionReglement = true;
						}
					}else if (taDossierRcd.getTaTReglement().getCodeTReglement().equals(TaTReglement.CB)) {
						genereEcheances();
						refreshPremierech = true;
					}
					
					
				}else if(taDossierRcd.getPremierPaiementEffectue()) {
					blocageChampsApresPremiereActionReglement = true;
				}
				
				/**FIN REGENRATION D'ECHEANCE**/
				
				taDossierRcd = taDossierRcdService.merge(taDossierRcd,ITaDossierRcdServiceRemote.validationContext);
				if(taSousDonnee.getTaDossierRcd().getIdDossierRcd()==null) {
					taSousDonnee.setTaDossierRcd(taDossierRcd);
				}
				taSousDonnee = taSousDonneeService.merge(taSousDonnee);
				
				
				
				if(selectedTaDossierRcdDTO.getNumDossierPolice()!=null) {
					taDossierRcdService.annuleCode(selectedTaDossierRcdDTO.getNumDossierPolice());
				}
				
				
				/**REGENERATION DE LA PREMIERE ECHEANCE SI DOSSIER SOUMIS A SOUSCRIPTION MAIS PAS DE 1ER PAIEMENT**/
				if(refreshPremierech) {
					refreshPremireEcheance();
				}
				
				/**FIN REGENERATION DE LA PREMIERE ECHEANCE**/
				
				mapperModelToUI.map(taDossierRcd, selectedTaDossierRcdDTO);
				
				if(assurePib()) {
					selectedTaDossierRcdDTO.getListeTauxRcProPib().clear();
					if(taDossierRcd.getTaDevisRcProTauxPibs()!= null && !taDossierRcd.getTaDevisRcProTauxPibs().isEmpty()) {
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
					
				}
				
				selectedTaDossierRcdDTO.getTaTStatut().clear();
				if(taDossierRcd.getTaTStatut()!=null 
						&& !taDossierRcd.getTaTStatut().isEmpty()) {
					
		            for (TaTStatut s : taDossierRcd.getTaTStatut()) {
		            	selectedTaDossierRcdDTO.getTaTStatut().add(s);
					}
				}
				
				
				
				/******************VALIDATION DE L'ONGET ACTUEL************************/
				
				
				if(idOngletActif!=null) {

					if(idOngletActif.equals(ID_ONGLET_1_DECLARATION_ENGAGEMENT)) {
						validationOnglet1DeclarationEngagement();
					} else if(idOngletActif.equals(ID_ONGLET_2_PROPOSANT_DECLARATION)) {
						validationOnglet2ProposantDeclaration();
					} else if(idOngletActif.equals(ID_ONGLET_3_ANTECEDANT)) {
						validationOnglet3Antecedant();
					} else if(idOngletActif.equals(ID_ONGLET_4_SINISTRALITE)) {
						validationOnglet4Sinistralite();
					} else if(idOngletActif.equals(ID_ONGLET_5_ACTIVITE_DECLARE)) {
						validationOnglet5ActiviteDeclare();
					} else if(idOngletActif.equals(ID_ONGLET_6_OPTION_PAIEMENT)) {
						validationOnglet6OptionPaiement();
					} else if(idOngletActif.equals(ID_ONGLET_7_INFORMATION_LEGALE)) {
						validationOnglet7InformationLegale();
					} else if(idOngletActif.equals(ID_ONGLET_8_PRIME)) {
						validationOnglet8Prime();
					} else if(idOngletActif.equals(ID_ONGLET_9_GED_DOCUMENT)) {
						validationOnglet9GedDocument();
					}
					
				} else {
					//pas d'onglet actif, on est sur le 1er onglet (par defaut)
					validationOnglet1DeclarationEngagement();
				}
				
				
				
				/******************FIN VALIDATION ONGLET*******************************/
				
				
				
				
				
				
				autoCompleteMapDTOtoUI();
				
				
				
				if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_INSERTION)==0) {
					values.add(selectedTaDossierRcdDTO);
					selectedTaDossierRcdDTOs = null;
					
					//pour activer l'onglet ged rcd suite au 1er enregistrement
					gedDevisRcProController.setMasterEntity(taDossierRcd);
					gedDevisRcProController.refresh();
					
					// si le devis a la GED validé par le courtier
					if(selectedTaDossierRcdDTO.getValidationGlobaleGedCourtier() != null ) {
						classPulseOngletGED(false);
					}else {
						//si l'onglet GED est accessible
						if( gedDevisRcProController.isMasterEntityId()) {
							classPulseOngletGED(true);
						}else {
							classPulseOngletGED(false);
						}
					}
					
					
					//envoi de mail avec le devis au courter
					envoiMailTestService.envoiMailEnregistrementDevis(taDossierRcd.getTaCourtier(), taDossierRcd);
					
				//pas en insertion
				}else {
					// si le devis a la GED validé par le courtier
					if(selectedTaDossierRcdDTO.getValidationGlobaleGedCourtier() != null ) {
						classPulseOngletGED(false);
					}else {
						classPulseOngletGED(false);
						
					}
					
				}
	//			adresseController.refresh(null);
				modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
				if(enModifPrimeAssureur == true) {
					enModifPrimeAssureur = false;
				}
				
				
				
	
			} catch(Exception e) {
				e.printStackTrace();
				FacesContext context = FacesContext.getCurrentInstance();  
				//context.addMessage(null, new FacesMessage("Courtier", "actEnregistrer")); 
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Devis Rcd", e.getMessage()));
			
			}
//		}else {
//			FacesContext context = FacesContext.getCurrentInstance();  
//			context.addMessage(null, new FacesMessage("Assure", "Le mot de passe est différent de sa confirmation"));
//		}

		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Devis Rcd", "actEnregistrer"));
		}
	}
	public Boolean premierPaiementEffectue() {
		Boolean premierPaiementEffectue = false;
		Boolean statutTrouve = false;
		//si soumis a soucription
		if(selectedTaDossierRcdDTO.getSoumisSouscription()) {
			//si le dossier possède le statut en attente de paiement
			for (TaTStatut statut : selectedTaDossierRcdDTO.getTaTStatut()) {
				if(statut.getCodeTStatut().equals(TaTStatut.attentePremierPaiement)) {
					statutTrouve = true;
					return premierPaiementEffectue;
				}
			}
			
			if(!statutTrouve) {
				premierPaiementEffectue = true;
			}
			return premierPaiementEffectue;
			
		}else {
			return premierPaiementEffectue;
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
			
			
			
			
			
			//taDossierRcdService.verrouilleCode(selectedTaDossierRcdDTO.getNumDossierPolice());
			//validateUIField(Const.C_CODE_COURTIER,selectedTaDossierRcdDTO.getNumDossierPolice());//permet de verrouiller le code genere
			
			taActiviteDTODisponible = taActiviteService.selectAllDTO();
			listeTaActiviteDTODetail = new ArrayList<>();
			selectedTaActiviteDTOs = new ArrayList<>();
			listeTaGedUtilisateurDTO = new ArrayList<>();
			listeTaGedUtilisateur = new ArrayList<>();
			//Rajout Yann PIB
			selectedTaTauxRcproPibDTOs = new ArrayList<>();

			creationCompteClientAssure = false;
			
			selectedTaDossierRcdDTO.setCodeTReglement(ConstWeb.codeTReglementDefaut);
			selectedTaDossierRcdDTO.setCodeTSousTraitance(ConstWeb.codeTSousTraitanceDefaut);
			selectedTaDossierRcdDTO.setCodeFranchise(ConstWeb.codeFranchiseDefaut);
			selectedTaDossierRcdDTO.setCodeEcheance(ConstWeb.codeTEcheanceDefaut);
			selectedTaDossierRcdDTO.setTauxCommission(TAUX_COMMISSION);
			
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
			
			//réinitialisation des booleens dans le DTO
			actRadioAntecedantRCDResilieParAssure();
			actRadioAntecedantRCDResilieParAssureur();
			
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
			
			
//			autoCompleteMapDTOtoUI();
			
			modeEcran.setMode(EnumModeObjet.C_MO_INSERTION);
			scrollToTop();
			

			if(taAssureParamInsertion==null) {
				taAssure = new TaAssure();
				taDossierRcd.setTaAssure(taAssure);
				this.setAssureExistant(false);
				
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
		
				//taGenCodeExService.libereVerrouTout(new ArrayList<String>(SessionListener.getSessions().keySet()));
				selectedTaDossierRcdDTO.getTaAssureDTO().setCodeAssure(taAssureService.genereCode(null)); //ejb
				validateUIField(Const.C_CODE_COURTIER,selectedTaDossierRcdDTO.getTaAssureDTO().getCodeAssure());//permet de verrouiller le code genere

				
				//Code déplacer dans le actEnregistrer car la création d'un utilisateur n'est plus systématique
//				TaUtilisateur taUtilisateur = new TaUtilisateur();
//				TaTUtilisateur taTUtilisateur = null; 
//				taTUtilisateur = taTUtilisateurService.findByCode(TaTUtilisateur.TYPE_UTILISATEUR_ASSURE);
//				taUtilisateur.setTaTUtilisateur(taTUtilisateur);
//				taUtilisateur.setAdmin(false);
//				taUtilisateur.setSysteme(false);
//				taAssure.setTaUtilisateur(taUtilisateur);
				
				if(taCourtier!=null) {
					taAssure.setTaCourtier(taCourtier);
//					taAssure.setCodeAssure(taCourtier.getCodeCourtier());
				}
			} else {
				taAssure = taAssureParamInsertion;
				taDossierRcd.setTaAssure(taAssure);
				taAssureParamInsertion = null;
				this.setAssureExistant(true);
				
				//mapperModelToUIAssure.map(taAssure, selectedTaDossierRcdDTO.getTaAssureDTO());
			}
			/////
			HashMap<String, String> map = new HashMap<>(); 
			
			if(assurePib()) {// SI PIB
				map.put("PIB", "true");
//					listeTaTFranchise.clear();
//					listeTaTFranchise.add(fran2000);
//					listeTaTFranchise.add(fran5000);
					
				
			}else {
				map.put("PIB", "false");	
			}
			
			//rajout du prefixe avec la date de création du dossier
			String prefixe = "NOVAR-";
			String pattern = "yyyyMMdd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			if(taDossierRcd.getQuandCree() != null) {
				String date = simpleDateFormat.format(taDossierRcd.getQuandCree());
				prefixe+= date+"-";
			}else {				
				String date = simpleDateFormat.format(new Date());
				prefixe+= date+"-";
			}
			map.put("prefixe", prefixe);
			
			
			//va mettre Z derriere numPolice si PIB, S si non PIB (voir classe GenCodeExDao ligne 346)
			selectedTaDossierRcdDTO.setNumDossierPolice(taDossierRcdService.genereCode(map)); //ejb
			
			
			mapperModelToUI.map(taDossierRcd, selectedTaDossierRcdDTO);	
			autoCompleteMapDTOtoUI();
			
			initRadioFranchise();
			
			gedDevisRcProController.setMasterEntity(taDossierRcd);
			gedDevisRcProController.refresh();
			
			if (tabViewDevisRcPro != null) {
				tabViewDevisRcPro.setActiveIndex(0);
			}
			if(ConstWeb.DEBUG) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage("Devis Rcd", "actInserer"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actInsererAvenant(ActionEvent actionEvent) {
		try {
//			taDossierRcd = new TaDossierRcd();
//			if(taDossierRcd.getTaDevisRcProDetails()==null) {
//				taDossierRcd.setTaDevisRcProDetails(new ArrayList<TaDossierRcd>());
//			}
//			taDossierRcd.getTaDevisRcProDetails().add(taDossierRcd);
//			taDossierRcd.setTaDevisRcPro(taDossierRcd);
//			selectedTaDossierRcdDTO = new TaDossierRcdDTO();
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	public void genereEcheances() throws RemoveException {
		
		if(selectedTaDossierRcdDTO.getSoumisSouscription() == true) {
			/////////////////////////////////////
			//généré les échéances à payer
			/////////////////////////////////////
			if(taDossierRcd.getTaEcheances() != null && !taDossierRcd.getTaEcheances().isEmpty()) {
				for (TaEcheance ech : taDossierRcd.getTaEcheances()) {
					ech.setTaDossierRcd(null);
				}
			}
			
			taDossierRcd.setTaEcheances(new ArrayList<TaEcheance>());
			//1ère échéance
			//			taDossierRcd.getMontantPremierReglement();
			//			//échéance suivantes
			//			taDossierRcd.getMontantPrime();

			
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
			if(taDossierRcd.getNumeroAvenant() != null) {
			e = taEcheanceService.donneFraisAvenant(e);
			//Ajout des sommes des frais d'avenant dans taSousDonnee
			JSONObject jsonData;
			if(taSousDonnee.getJsonData()!=null) {
			jsonData = new JSONObject(taSousDonnee.getJsonData());
			}else {
			jsonData = new JSONObject();
			}
			jsonData.put(TaSousDonnee.CLE_FRAIS_AVENANT_YLYADE, TaSousDonnee.VALEUR_FRAIS_AVENANT_YLYADE);
			jsonData.put(TaSousDonnee.CLE_FRAIS_AVENANT_ASSUREUR, TaSousDonnee.VALEUR_FRAIS_AVENANT_ASSUREUR);
			taSousDonnee.setJsonData(jsonData.toString());
			}
			
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
			if(taDossierRcd.getNumeroAvenant() != null) {
			e = taEcheanceService.donneFraisAvenant(e);
			}
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
			if(taDossierRcd.getNumeroAvenant() != null) {
			e = taEcheanceService.donneFraisAvenant(e);
			}
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
			
			//taDossierRcd = taDossierRcdService.merge(taDossierRcd,ITaDossierRcdServiceRemote.validationContext);
			
			
//			refreshPremireEcheance();
		}
		
	}
	public void actSoumettreASouscription(ActionEvent actionEvent) throws FinderException {
//		if(taDossierRcd.getTaContratRcPro()==null) {
//			TaContratRcPro contrat = null;
//			if(taDossierRcd.getTaContratRcPro()!=null) {
//				contrat = taDossierRcd.getTaContratRcPro();
//			} else {
//				contrat = new TaContratRcPro();
//				contrat.setTaAssure(taDossierRcd.getTaAssure());
//				//contrat.setTaCourtier(taDossierRcd.getTaCourtier());
//				contrat.setTaDevisRcPro(taDossierRcd);
//				taDossierRcd.setTaContratRcPro(contrat);
//			}
//			/////////////////////////////////////
//			// faire le mapping
//			/////////////////////////////////////
//			TaContratRcProDetail taContratRcProDetail = taDossierRcd.genereContrat();
//			
//			
//	//		taContratRcProDetail.setTaDevisRcProDetail(taDossierRcd);
//	//		taDossierRcd.setTaContratRcProDetail(taContratRcProDetail);
//			
//			/////////////////////////////////////
//			// généré les échéances à payer
//			/////////////////////////////////////
//			//1ère échéance
//			taDossierRcd.getMontantPremierReglement();
//			//échéance suivantes
//			taDossierRcd.getMontantPrime();
//			
//			if(contrat.getTaContratRcProDetails()==null) {
//				contrat.setTaContratRcProDetails(new ArrayList<TaContratRcProDetail>());
//			}
//			contrat.getTaContratRcProDetails().add(taContratRcProDetail);
//			
//			contrat = taContratRcProService.merge(contrat,ITaContratRcProDetailServiceRemote.validationContext);
//		}
		actEnregistrer(null);
			if(valideTousLesOnglets()) {
				if(!pourEtude || (pourEtude && isValiderApresEtudeYlyadeOuAssureur() )) {
					if(taDossierRcd.getContrat()!=null && !taDossierRcd.getContrat() && taDossierRcd.getDateEffet()!=null) { //ce n'est pas deja un contrat
						taDossierRcd.setContrat(false);
						taDossierRcd.setSoumisSouscription(true);
						taDossierRcd.setDateSouscription(new Date());
						if(taDossierRcd.getTaSinistreAntecedent()==null) {
							taDossierRcd.setTaSinistreAntecedent(new ArrayList<TaSinistreAntecedent>());
						}
						
						selectedTaDossierRcdDTO.setContrat(false);
						selectedTaDossierRcdDTO.setSoumisSouscription(true);
						
						Date now = new Date();
						String nowString;
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à hh:mm:ss");
						nowString = dateFormat.format(now);
						FacesContext context = FacesContext.getCurrentInstance();
						ExternalContext externalContext = context.getExternalContext();
						TaUtilisateur user = (TaUtilisateur) externalContext.getSessionMap().get("userSession");
						System.out.println("************");
						System.out.println("************");
						System.out.println("************");
						System.out.println("************");
						System.out.println("************");
						System.out.println("************");
						System.out.println("************");
						System.out.println("************");
						try {
							System.out.println("******Soumis a souscription*********** Soumis a souscription DOSSIER id:"+selectedTaDossierRcdDTO.getId()+" numPolice : "+selectedTaDossierRcdDTO.getNumDossierPolice()+" le "+nowString+" par l'utilisateur connecté id:"+user.getIdUtilisateur()+" "+user.getIdentifiant()+", par le courtier "+taCourtier.getCodeCourtier()+" "+taCourtier.getNomDenominationSociale()+", Pour l'assuré "+taDossierRcd.getTaAssure().getCodeAssure()+" "+taDossierRcd.getTaAssure().getRaisonSociale()+"****************");

						} catch (Exception e) {
							System.out.println("******Soumis a souscription***********");
						}
						System.out.println("************");
						System.out.println("************");
						System.out.println("************");
						System.out.println("************");
						System.out.println("************");
						System.out.println("************");
						System.out.println("************");
						System.out.println("************");
			//			selectedTaDossierRcdDTO.setDateSouscription(new Date());
						
						try {
							genereEcheances();
						} catch (RemoveException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					classPulseBoutonSouscription(false);
					actEnregistrer(null);
					
					updateTab();
					
					refreshPremireEcheance();
					
				}else {
				
					
					RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("Souscription devis RCD impossible","Le devis doit être soumis à étude (sinistres ou chiffre d'affaire prévisonnel trop grand) ou n'a pas encore été validé dans le cas d'une soumission à étude. "));
					updateTab();
				}
				
			} else {
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("Souscription devis RCD impossible","Le devis est incomplet ou contient des erreur."));
				updateTab();
			}
		
	}
	
	public void actModifier() {
		actModifier(null);
	}
	public void actModifierPrimeAssureur(ActionEvent actionEvent) {
		 enModifPrimeAssureur = true;
	}
	public void actModifier(ActionEvent actionEvent) {
		
		modeEcran.setMode(EnumModeObjet.C_MO_EDITION);
		
		creationCompteClientAssure = false;
		selectedTaDossierRcdDTO.getTaAssureDTO().setPassword(null);
		selectedTaDossierRcdDTO.getTaAssureDTO().setPasswordConfirm(null);

		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Devis Rcd", "actModifier"));
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
			context.addMessage(null, new FacesMessage("Devis Rcd", "actAide"));
		}
	}
    
    public void nouveau(ActionEvent actionEvent) {  
    	LgrTab b = new LgrTab();
    	b.setTypeOnglet(LgrTab.TYPE_TAB_DEVIS_RCPRO);
		b.setTitre("Devis RCD");
		b.setTemplate("assurance/devis_rcd.xhtml");
		b.setIdTab(LgrTab.ID_TAB_DEVIS_RCPRO);
		b.setStyle(LgrTab.CSS_CLASS_TAB_DEVIS_RCPRO);
		b.setParamId("0");
		
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
		actInserer(actionEvent);
		
		if(ConstWeb.DEBUG) {
	    	FacesContext context = FacesContext.getCurrentInstance();  
		    context.addMessage(null, new FacesMessage("Devis Rcd", 
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
		TaDossierRcd taDossierRcd = new TaDossierRcd();
		try {
			if(selectedTaDossierRcdDTO!=null && selectedTaDossierRcdDTO.getId()!=null){
				taDossierRcd = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
			}

			taDossierRcdService.remove(taDossierRcd);
			values.remove(selectedTaDossierRcdDTO);
			Date now = new Date();
			String nowString;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à hh:mm:ss");
			nowString = dateFormat.format(now);
			FacesContext context2 = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context2.getExternalContext();
			TaUtilisateur user = (TaUtilisateur) externalContext.getSessionMap().get("userSession");
			System.out.println("************");
			System.out.println("************");
			System.out.println("************");
			System.out.println("************");
			System.out.println("************");
			System.out.println("************");
			System.out.println("************");
			System.out.println("************");
			try {
				System.out.println("******SUPPRESSION***********SUPPRESSION*********SUPPRESSION********** SUPPRESSION DOSSIER id:"+taDossierRcd.getIdDossierRcd()+" numPolice : "+taDossierRcd.getNumDossierPolice()+" le "+nowString+" par l'utilisateur connecté id:"+user.getIdUtilisateur()+" "+user.getIdentifiant()+", par le courtier "+taCourtier.getCodeCourtier()+" "+taCourtier.getNomDenominationSociale()+", Pour l'assuré "+taDossierRcd.getTaAssure().getCodeAssure()+" "+taDossierRcd.getTaAssure().getRaisonSociale()+"****************");

			} catch (Exception e) {
				System.out.println("******SUPPRESSION***********SUPPRESSION*********SUPPRESSION**********");
			}
			System.out.println("************");
			System.out.println("************");
			System.out.println("************");
			System.out.println("************");
			System.out.println("************");
			System.out.println("************");
			System.out.println("************");
			System.out.println("************");
			if(!values.isEmpty()) {
				selectedTaDossierRcdDTO = values.get(0);
			}else {
				selectedTaDossierRcdDTO=new TaDossierRcdDTO();
			}
			updateTab();
			modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);

			if(ConstWeb.DEBUG) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage("TaDossierRcd", "actSupprimer"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Devis Rcd", e.getCause().getCause().getCause().getCause().getMessage()));
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
		System.out.println("DevisRcdController.actChangementPaiement()");
	}
	
	public void actChangementEcheance() {
		System.out.println("DevisRcdController.actChangementEcheance()");
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
	
	public void actChangementPartSousTraitance() {
		System.out.println("DevisRcdController.actChangementPartSousTraitance()");
		validationOnglet6OptionPaiement();
	}
	
	public void actChangementFranchise() {
		System.out.println("DevisRcdController.actChangementEcheance()");
		//taTFranchiseService.findByCode(selectedTaDossierRcdDTO.codeFranchise);
	}

	public void actImprimer(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
			
//			if(selectedTaDossierRcdDTO.getDateDevis()==null) {
//				Date dateDevis = new Date();
//				selectedTaDossierRcdDTO.setDateDevis(dateDevis); //date fixé au moment de la génération du PDF et non modifiable
//				taDossierRcd.setDateDevis(dateDevis);
//				actEnregistrer(null);
//			}
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		sessionMap.put("devisrcpro", taDossierRcdService.findById(taDossierRcd.getIdDossierRcd()));
		sessionMap.put("tarifpj", taTauxAssuranceService.findByCode(TaTauxAssurance.MONTANT_PROTECTION_JURIDIQUE));
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

		////////////////////////////////////////////////////////////////////////////////////////
		//Test génération de fichier PDF
		
//		HashMap<String,Object> hm = new HashMap<>();
//		hm.put( "tiers", taCourtierService.findById(selectedTaDossierRcdDTO.getId()));
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
		
		//new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));
		////////////////////////////////////////////////////////////////////////////////////////
		//java.net.URL.setURLStreamHandlerFactory();
//		taCourtierService.generePDF(selectedTaDossierRcdDTO.getId());
		////////////////////////////////////////////////////////////////////////////////////////
		
//			session.setAttribute("tiers", taCourtierService.findById(selectedTaDossierRcdDTO.getId()));
			System.out.println("CourtierController.actImprimer()");
		} catch (FinderException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
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
		    actRadioInteruptionAssurance();
		    actChangementDejaAssureMemeRisque();
		    actChangementAntecedantDateResiliation();
		    
		}
	}
	
	public void calculeTauxSousTraitanceGlobal(ActionEvent e) {
		
//		selectedTaDossierRcdDTO.setCodeTSousTraitance(TaTSousTraitance.ST_0_30);
//		
//		if(totalPourcentageActiviteSousTraite!=null) {
//			selectedTaDossierRcdDTO.setPourcentSousTraitanceCalcul(totalPourcentageActiviteSousTraite);
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
		b.setTypeOnglet(LgrTab.TYPE_TAB_DEVIS_RCPRO);
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
//	 public void onInputChangedCommissionCourtier(ValueChangeEvent event) {
//	        selectedTaDossierRcdDTO.setTauxCommission((BigDecimal) event.getNewValue());
//	        majMontantCommissionPrevu();
//	    }
	public void majMontantCommissionPrevu(AjaxBehaviorEvent e){
		majMontantCommissionPrevu();
	}
	
	public void majMontantCommissionPrevuSlideEnd(SlideEndEvent e){
		System.out.println(e.getValue());
		selectedTaDossierRcdDTO.setTauxCommission(BigDecimal.valueOf(e.getValue()));
		majMontantCommissionPrevu();
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
			
			if(selectedTaDossierRcdDTO.getPrimeNetteAssureur()!=null) { //si l'assureur a mis une prime de base, on se sert de celle-ci
				primeBaseNette = selectedTaDossierRcdDTO.getPrimeNetteAssureur();
			}else {
				
				if(selectedTaDossierRcdDTO.getPrimeNetteYlyade()!=null){ // si l'assureur n'a pas rentré de prime mais que Ylyade l'a fait, on se sert de la leur.
					primeBaseNette = selectedTaDossierRcdDTO.getPrimeNetteYlyade();
				}else {
						
						if(assurePib()) {
							logCalculPrime("Assuré PIB");
							logCalculPrime("Activité(s) PIB sélectionnée(s) : ");
				//			//algo calcul PIB
							//La	prime	de	base	est	calculée	sur	le	montant	du	chiffre	d’affaire,	qu’on	multiplie	par	le	taux	 ci-dessus	par	activité.
							BigDecimal primePib = null;
							BigDecimal primePibMax = BigDecimal.ZERO;
							for (TaTauxRcproPibDTO txPib : selectedTaTauxRcproPibDTOs) {
								logCalculPrime("- "+txPib.getLiblTauxRcproPib()+" - "+txPib.getTauxRcproPib());
				//					BigDecimal tx = txPib.getTauxRcproPib().divide(new BigDecimal(100)).add(new BigDecimal(1));
								BigDecimal tx = txPib.getTauxRcproPib().divide(new BigDecimal(100));
								if(selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel()!= null) {
									primePib = selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel().multiply(tx);
									// on verifie que ce montant soit plus grand que la prime mini
									try {
										TaTauxRcproPib	activite = taTauxRcproPibService.findByCode(txPib.getCodeTauxRcproPib());
										if(primePib.compareTo(activite.getPrimeMin())<0) {
											primePib = activite.getPrimeMin();
										}
									} catch (FinderException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									if(primePib != null) {
										if(primePib.compareTo(primePibMax)>=0) {
											primePibMax = primePib; //on garde la prime la plus haute
											selectedtxPib = txPib;// on definit l'activité pib de référence
										}
									}	
										
									
								}
								
								
							}
							logCalculPrime("Activité PIB de référence : ");
							if(selectedtxPib != null && selectedtxPib.getLiblTauxRcproPib() != null && selectedtxPib.getTauxRcproPib()!=null) {
								logCalculPrime("- <b>"+selectedtxPib.getLiblTauxRcproPib()+" - "+selectedtxPib.getTauxRcproPib()+"</b>");
							}
							
							
							if(selectedTaDossierRcdDTO.getTaAssureDTO().getDomiciliationEtranger()) {//si entreprise à l'etranger, on multiplie par 3
//								logCalculPrime("<b>Entreprise à l'étranger</b> donc la prime de base nette est multiplié par "+Const.C_TAUX_MULTIPLY_ENTREPRISE_ETRANGERE+" : "+primePibMax+" X "+Const.C_TAUX_MULTIPLY_ENTREPRISE_ETRANGERE+"");
//								primeBaseNette = primePibMax.multiply(Const.C_TAUX_MULTIPLY_ENTREPRISE_ETRANGERE);
								logCalculPrime("Prime de base nette est donc de : <b>"+primeBaseNette+"</b> €");
							}else {
								primeBaseNette = primePibMax;
								logCalculPrime("<b>L'entreprise est en france</b> donc la prime de base est de "+primeBaseNette+"");
							}
							
							
							
						} else {
							//algo "normal"
							logCalculPrime("Assuré non PIB");
							logCalculPrime("Activité NON PIB sélectionnée : ");
							//la prime de base correspond à la prime de base la plus haute choisi dans les classes des activités choisis et en fonction du chiffre d'affaire
							for (TaActiviteDTO a : selectedTaActiviteDTOs) {
								logCalculPrime("- "+a.getLiblActivite()+" - code : "+a.getCodeActivite()+" - classe : "+a.getCodeClasse());
								l.add(a.getId());
							}
							
							r = chercheActiviteDeReference(l, selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel());
							
				//				primeBaseNette = taDossierRcdService.findMontantPrime(l, selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel());
				//				taActiviteDeReference =  taDossierRcdService.findActiviteDeReference(l, selectedTaDossierRcdDTO.getChiffreAffaireExercicePrevisionnel());
							
							if(selectedTaDossierRcdDTO.getTaAssureDTO().getDomiciliationEtranger()) {//si entreprise à l'etranger, on multiplie par 3
								logCalculPrime("<b>Entreprise à l'étranger</b> donc la prime de base nette est multiplié par "+Const.C_TAUX_MULTIPLY_ENTREPRISE_ETRANGERE+" : "+r.getMontantPrimeBase()+" X "+Const.C_TAUX_MULTIPLY_ENTREPRISE_ETRANGERE+"");
								if(r.getMontantPrimeBase()!=null) {
									primeBaseNette = r.getMontantPrimeBase().multiply(Const.C_TAUX_MULTIPLY_ENTREPRISE_ETRANGERE);
									logCalculPrime("Prime de base nette est donc de : <b>"+primeBaseNette+"</b> €");
								}
								
							}else {
								if(r.getMontantPrimeBase()!=null) {
									primeBaseNette = r.getMontantPrimeBase();
									logCalculPrime("<b>L'entreprise est en france</b> donc la prime de base est de "+primeBaseNette+"");
								}
								
							}
							
							//Si intervention en Corse
							if(selectedTaDossierRcdDTO.getTerritorialiteLieuCorse()) {
								BigDecimal montantTerritorialite = primeBaseNette;
								logCalculPrime("<b>L'intervention est en Corse</b> donc on ajoute "+Const.C_TAUX_MULTIPLY_INTERVENTION_CORSE+" % à la prime de base");
								montantTerritorialite = montantTerritorialite.add(montantTerritorialite.multiply(Const.C_TAUX_MULTIPLY_INTERVENTION_CORSE).divide(new BigDecimal(100))) ; 
								primeBaseNette = montantTerritorialite; 
							}
							
							
							
							
							
//							primeBaseNette = r.getMontantPrimeBase();
							if(taTJuridiqueDTO!=null && taTJuridiqueDTO.getCodeTJuridique().equals("AE")) {//si c'est un auto entrepreneur
								logCalculPrime("Prime de base nette de l'activité de référence <b>(Auto-Entrepreneur)</b>: "+primeBaseNette);
							}else {
								logCalculPrime("Prime de base nette de l'activité de référence (Non Auto-Entrepreneur): "+primeBaseNette);
							}
							
							taActiviteDeReference = r.getActiviteRef();
							if(taActiviteDeReference!=null) {
								logCalculPrime("Actvité de référence : "+taActiviteDeReference.getLiblActivite()+" - code : "+taActiviteDeReference.getCodeActivite()+" - classe : "+taActiviteDeReference.getTaClasse().getCodeClasse());
							}
						}
						selectedTaDossierRcdDTO.setPrimeNette(primeBaseNette);
						if(primeBaseNette == new BigDecimal(0.00) || primeBaseNette == null) {
//							pourEtude = true;
						}
				
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
				
				//initialisation de tous les boutons radio gérés par de booleen
				//TODO A SUPPRIMER RAPIDEMENT ET A REMPLACER PAR DES LISTES/DONNEES EN BASE DE DONNEES
				
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
				initRadioAntecedantRCDEnCours();
				initRadioAntecedantRCDResilieParAssure();
				initRadioAntecedantRCDResilieParAssureur();
				initRadioAntecedantMotifRCDAssure();
				initRadioAntecedantMotifRCDAssureur();
				initRadioAntecedantRCEEnCours();
				initRadioAntecedantRCEResilieParAssure();
				initRadioAntecedantRCEResilieParAssureur();
				initRadioAntecedantMotifRCEAssure();
				initRadioAntecedantMotifRCEAssureur();
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
			
				gedDevisRcProController.setMasterEntity(taDossierRcd);
				gedDevisRcProController.refresh();
				// si le devis a la GED validé par le courtier
				if(selectedTaDossierRcdDTO.getValidationGlobaleGedCourtier() != null ) {
					classPulseOngletGED(false);
				}else {
					//si l'onglet GED est accessible
					if( gedDevisRcProController.isMasterEntityId()) {
						classPulseOngletGED(true);
					}else {
						classPulseOngletGED(false);
					}
				}
				
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
//						taDossierRcdService.donneStatut(selectedTaDossierRcdDTO);
						IDonneStatut Idossier = taDossierRcdService.donneStatut(selectedTaDossierRcdDTO);
						selectedTaDossierRcdDTO.getTaTStatut().clear();
			        	for (TaTStatut stat : Idossier.getTaTStatut()) {
			        		selectedTaDossierRcdDTO.getTaTStatut().add(stat);
						}
						if(!selectedTaDossierRcdDTO.getTaTStatut().isEmpty()) {
							for (TaTStatut s : selectedTaDossierRcdDTO.getTaTStatut()) {
								taDossierRcd.getTaTStatut().add(s);
							}
//							if(taDossierRcd.getTaSinistreAntecedent() == null) {
//								 List<TaSinistreAntecedent> s = new ArrayList<TaSinistreAntecedent>();
//								taDossierRcd.setTaSinistreAntecedent(s);
//							}
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
				
				
				refreshPremireEcheance();
				remplieListeEcheanceDTO();
				
			}
		} catch (FinderException e) {
			e.printStackTrace();
		}
	}
	
	public void refreshPremireEcheance() {
		if(taDossierRcd.getTaEcheances()!=null && !taDossierRcd.getTaEcheances().isEmpty()) {// si il y a déjà des échéances au dossier
			TaEcheance premiereEch = null;
			// on prend la première
			premiereEch = taDossierRcd.getTaEcheances().get(0);
			for (TaEcheance e : taDossierRcd.getTaEcheances()) {// pour chaque échéances du dossier
				//si la date de debut de l'echeance est avant celle de la première échéance
				if(e.getDateDebutEcheance().before(premiereEch.getDateDebutEcheance())) {
					//celle ci devient la première échéance
					premiereEch = e;
				}
			}
			//remonte la première écheance ET son réglement dans un DTO
			TaEcheanceDTO d = taEcheanceService.findEcheanceReglementRCDDTO(premiereEch.getIdEcheance());
			//on vide la liste d'échéance DTO
			if(listeTaEcheanceDTO==null) {
				listeTaEcheanceDTO = new ArrayList<>();
			} else {
				listeTaEcheanceDTO.clear();
			}
			// on ne garde que la première échéance dans la liste 
			listeTaEcheanceDTO.add(d);
			
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
//						tabViewDevisRcPro.setActiveIndex(mapOngletIndex.get(ID_ONGLET_2_PROPOSANT_DECLARATION));
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
			listeTaGedUtilisateurDTO = initListeGed();
		}
		//si on est sur l'ecran de prime en modification, le bouton enregistrer pulse
		if(idOngletActif.equals(ID_ONGLET_8_PRIME)) {
			
			if(modeEcran.dataSetEnModif()) {
				classPulseBoutonEnregistrer(true);
			}
		}
//		majMontantCommissionPrevu();
		/*
		 * Si trop lent voir au cas par cas pour :
		 * 	- soit mettre à jour uniquement l'onglet qui va s'afficher, 
		 *  - soit mettre à jour les propriété du masterEntity en fonction de ce qui vient d'être fait sur l'onlget précendent
		 * Par exemple mettre à jour la liste des controle dans l'article courant au lieu de recharger l'article entièrement
		 * 
		 * Sinon chercher ce que l'on peu faire avec un refresh JPA
		 */
//		updateTab();  //TODO en commentaire sinon déclenche un findById du masterEntity (ici le devis) et "casse" l'onglet des antécédent sinistre qui sont reliés directement à l'entité
		
		if(modeEcran.dataSetEnModif()) {
			calculMontantPrime(false);
		}
	}
	
	public boolean disabledTab(String nomTab) {
		//return modeEcran.dataSetEnModif() ||  selectedTaDossierRcdDTO.getId()==0;
		return true;
	}
	
	public void onRowSelect(SelectEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_DEVIS_RCPRO);
		b.setTitre("Devis RCD");
		b.setTemplate("assurance/devis_rcd.xhtml");
		b.setIdTab(LgrTab.ID_TAB_DEVIS_RCPRO);
		b.setStyle(LgrTab.CSS_CLASS_TAB_DEVIS_RCPRO);
		b.setParamId(LibConversion.integerToString(selectedTaDossierRcdDTO.getId()));

		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
		
		creationCompteClientAssure = false;
		selectedTaDossierRcdDTO.getTaAssureDTO().setPassword(null);
		selectedTaDossierRcdDTO.getTaAssureDTO().setPasswordConfirm(null);
		
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
		return !modeEcran.dataSetEnModif();//bloque si pas en modif
	}
	
	public boolean editableSaufAvenantCourtier() {
		if(editable()) {//si pas en modif
			return true;// on bloque
		}else if (!editable()) { //si en modif
			if(selectedTaDossierRcdDTO.getNumeroAvenant() != null && !auth.isYlyade()) {//si c'est un avenant et que ce n'est pas Ylyade, on bloque
				return true;// on bloque
			}
		}
		return false;
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
	//////////////////////////////////
	public void actInsererSinistreAntecedent(ActionEvent actionEvent) {
		selectedTaSinstreAntecedent = new TaSinistreAntecedent();
		selectedTaSinstreAntecedent.setTaTGarantieSinistre(listeTaTGarantieSinistre.get(0));
	//	selectedTaSinstreAntecedent.setDateSinistre(new Date());
	}
	
	public void actModifierSinistreAntecedent(ActionEvent actionEvent) {
		
	}
	
	public void actSupprimerAntecedent(ActionEvent actionEvent) {
		taDossierRcd.getTaSinistreAntecedent().remove(selectedTaSinstreAntecedent);
		
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
		chercheTauxSinistre36mois();


	}
	
	public void actAnnulerSinistreAntecedent(ActionEvent actionEvent) {
		
	}
	
	public void actEnregistrerSinistreAntecedent(ActionEvent actionEvent) {
		try {
			if(taDossierRcd.getTaSinistreAntecedent()==null) {
	//			taDossierRcd.getTaSinistreAntecedent().clear();
	//			taDossierRcd.getTaSinistreAntecedent().addAll(new ArrayList<TaSinistreAntecedent>());
				taDossierRcd.setTaSinistreAntecedent(new ArrayList<TaSinistreAntecedent>());
			}
			taDossierRcd.getTaSinistreAntecedent().add(selectedTaSinstreAntecedent);
			selectedTaSinstreAntecedent.setTaDossierRcd(taDossierRcd);
//			selectedTaSinstreAntecedent.setTaTGarantieSinistre(taTGarantieSinistre.findById(selectedTaSinstreAntecedent.getTaTGarantieSinistre().getIdTGarantieSinistre()));
			
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
			chercheTauxSinistre36mois();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public boolean afficheMotDePasse() {
		boolean affiche = false;
		if((modeEcran.getMode().compareTo(EnumModeObjet.C_MO_INSERTION)==0 && assureExistant==false) || modeEcran.getMode().compareTo(EnumModeObjet.C_MO_EDITION)==0) {
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

	public void validateDevisRcPro(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	
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
					if(taTAssureDTO!= null) {
						taTAssureDTO.setCodeTAssure("");
					}
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
					PropertyUtils.setSimpleProperty(taDossierRcd.getTaAssure().getTaContactEntreprise().getTaTel(), nomChamp, value);
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
				if(taEmailService.findByAdresseEmail((String)value)!=null) {
					FacesContext context = FacesContext.getCurrentInstance();  
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Devis Rcd", "Un utilisateur avec l'adresse email "+value+" existe déjà. Pour créer un nouveau devis pour cet assuré/utilisateur  veuillez le sélectionner dans la liste pour ouvrir sa fiche et cliquer sur 'Nouveau devis RCD'"));
				}
				
				taDossierRcd.getTaAssure().initContactEntrepriseAssure("test"); //création du ContactEntreprise par defaut s'il n'existe pas
				taDossierRcd.getTaAssure().getTaContactEntreprise().initEmailDefaut(value);
				if(value!=null && !value.equals("")) {
					PropertyUtils.setSimpleProperty(taDossierRcd.getTaAssure().getTaContactEntreprise().getTaEmail(), nomChamp, value);
					if(taDossierRcd.getTaAssure().getTaUtilisateur() != null) {
						taDossierRcd.getTaAssure().getTaUtilisateur().setIdentifiant((String) value);
					}
					
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
				if(taDossierRcd.getTaAssure().getTaUtilisateur() != null) {
				taDossierRcd.getTaAssure().getTaUtilisateur().setIdentifiant((String)value);
				}
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
				if(taDossierRcd.getTaAssure().getTaUtilisateur()!=null) {
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
				// rajouter la régénration des échéances
				taDossierRcd.setTaTReglement(entity);
//				actEnregistrer(null);
//				genereEcheances();
//				actEnregistrer(null);
//				refreshPremireEcheance();
//				actModifier();
			} else if(nomChamp.equals(Const.C_CODE_T_ECHEANCE)) {
				TaTEcheance entity = null;
				if(value!=null) {
					if(value instanceof TaTEcheance) {
						if(selectedTaDossierRcdDTO.getSinistraliteLiquidationSocieteDemandeuse() || selectedTaDossierRcdDTO.getSinistraliteLiquidationAutreSociete()) {
							entity = taTEcheanceService.findByCode(TaTEcheance.ANNUEL);
							selectedTaDossierRcdDTO.setCodeTEcheance(TaTEcheance.ANNUEL);
						}else {
							entity=(TaTEcheance) value;
						}
					}else if (value instanceof TaTEcheanceDTO){
						if(selectedTaDossierRcdDTO.getSinistraliteLiquidationSocieteDemandeuse() || selectedTaDossierRcdDTO.getSinistraliteLiquidationAutreSociete()) {
							entity = taTEcheanceService.findByCode(TaTEcheance.ANNUEL);
							selectedTaDossierRcdDTO.setCodeTEcheance(TaTEcheance.ANNUEL);
						}else {
							entity = taTEcheanceService.findByCode(((TaTEcheanceDTO)value).getCodeTEcheance());
						}	
					}else{
						if(selectedTaDossierRcdDTO.getSinistraliteLiquidationSocieteDemandeuse() || selectedTaDossierRcdDTO.getSinistraliteLiquidationAutreSociete()) {
							entity = taTEcheanceService.findByCode(TaTEcheance.ANNUEL);
							selectedTaDossierRcdDTO.setCodeTEcheance(TaTEcheance.ANNUEL);
						}else {
							entity = taTEcheanceService.findByCode((String)value);
							selectedTaDossierRcdDTO.setCodeTEcheance((String)value);
							selectedTaDossierRcdDTO.setCodeEcheance((String)value);
						}
						
					}
				} else {
					selectedTaDossierRcdDTO.setCodeEcheance("");
//					taTEcheanceDTO.setCodeTReglement("");
					taTEcheance=null;
				}
				
				taDossierRcd.setTaTEcheance(entity);
//				actEnregistrer(null);
//				genereEcheances();
//				actEnregistrer(null);
//				refreshPremireEcheance();
//				actModifier();
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
			} else if(nomChamp.equals(Const.C_FRAIS_RC_PRO)) {
//				if(value!=null) {
//					if(selectedTaDossierRcdDTO.getFraisRcPro().compareTo(FRAIS_RCD_MAX) > 0 ) {
//						
//						System.out.println("frais supérieur à 300");
//						FacesContext context = FacesContext.getCurrentInstance();  
//						context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Tiers", "les frais de courtiers ne peuvent pas dépasser les 300 €"));
//						
//					}else {
//						
//					}
				
				
				
				
				
			}
			return false;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void initAdresseNull() {
		if(taDossierRcd.getTaAssure().getTaContactEntreprise().getTaAdresse()==null){
			selectedTaDossierRcdDTO.getTaAssureDTO().setAdresseLigne1(null);
			selectedTaDossierRcdDTO.getTaAssureDTO().setAdresseLigne2(null);
			selectedTaDossierRcdDTO.getTaAssureDTO().setAdresseLigne3(null);
			selectedTaDossierRcdDTO.getTaAssureDTO().setCodePostal(null);
			selectedTaDossierRcdDTO.getTaAssureDTO().setVille(null);
			selectedTaDossierRcdDTO.getTaAssureDTO().setPays(null);
		}
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
	public void setValidationGlobaleGedYlyade(ActionEvent actionEvent) throws FinderException {
					selectedTaDossierRcdDTO.setValidationGlobaleGedYlyade(true);
					taDossierRcd.setValidationGlobaleGedYlyade(true);
					actEnregistrer(null);
		
	}
	
	

	
	/*
	 * --------------------------------------------------------------------------------------
	 * 
	 */
	
	
	public void setValidationGlobaleGedCourtier(ActionEvent actionEvent) {
		try {
			taDossierRcd = taDossierRcdService.findById(taDossierRcd.getIdDossierRcd());
			selectedTaDossierRcdDTO.setValidationGlobaleGedCourtier(true);
			taDossierRcd.setValidationGlobaleGedCourtier(true);
			/**VOIR A FAIRE JUSTE UN MERGE **/
			classPulseBoutonSouscription(true);
			actEnregistrer(null);
			
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean isValidationGlobaleCourtier() throws FinderException {
		if(selectedTaDossierRcdDTO!=null) {
			if (selectedTaDossierRcdDTO.getValidationGlobaleGedCourtier()== null || selectedTaDossierRcdDTO.getValidationGlobaleGedCourtier()==false ) {

				
				int j=0; 
				TaListeRefDoc talistRefDoc = null;
				Boolean restantAValider = false;
				Boolean obligatoire = true;
            	Boolean dejaValider = true;
				TaGedUtilisateurDTO item = null;
				Boolean result = false;
				int size = listeTaGedUtilisateurDTO.size();
//				Boolean Valider;
				
				
                while (j< listeTaGedUtilisateurDTO.size() && obligatoire==true && dejaValider==true ){
                	item = listeTaGedUtilisateurDTO.get(j);
                	talistRefDoc = taListeRefDocService.findById(item.getIdListeRefDoc());
                	
//                	if(item.getObligatoire()!=null && item.getObligatoire()!=true) {
                		obligatoire = false;
//                	}else {
//		        		obligatoire = true;
//		        	}
                	if(item.getValidationCourtier()!=null && item.getValidationCourtier()!=true) {
                		dejaValider = false;
                	}else if(item.getValidationCourtier()==null) {
                		dejaValider = false;
                	}
                	
                	
                	if (obligatoire == true && dejaValider==false ) {
                		restantAValider = true;
                	}else if (obligatoire == false && dejaValider==false ) {
						restantAValider = false;
					}
                        j++;
                        
                }
                return restantAValider;
                
			}
		}
		return true;
	}
	
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
					gedUtilisateurPresent.setObligatoire(item.getObligatoire());
					listeGedUtilisateur.add(gedUtilisateurPresent);
					
				}
				
				
			}
			if(listeRefDocNouveau!=null) {
				for (TaListeRefDocDTO item : listeRefDocNouveau) {
					TaGedUtilisateurDTO gedUtilisateurNouveau= new TaGedUtilisateurDTO();
					gedUtilisateurNouveau.setIdListeRefDoc(item.getId());
					gedUtilisateurNouveau.setLiblDoc(item.getLiblDoc());
					gedUtilisateurNouveau.setObligatoire(item.getObligatoire());
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
					gedUtilisateurNouveau.setObligatoire(item.getObligatoire());
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
	
	
	public boolean isDocumentValidateYlyadeYes(Integer idDoc) throws FinderException {
		if(idDoc!=null && taDossierRcd!=null && taDossierRcd.getIdDossierRcd()!=null) {
			TaGedUtilisateur doc = taGedUtilisateurService.findById(idDoc);
			
//			for (TaGedUtilisateurDTO taGedUtilisateurDTO : listeTaGedUtilisateurDTO) {
//				if (taGedUtilisateurDTO.getId() == idDoc) {
//					if(taGedUtilisateurDTO!=null && taGedUtilisateurDTO.getValidationYlyade()!=null && taGedUtilisateurDTO.getFichierDoc()!=null) {
//					return taGedUtilisateurDTO.getValidationYlyade();
//						
//					}
//				}
//		}		
			
//			for (TaGedUtilisateurDTO taGedUtilisateurDTO : listeTaGedUtilisateurDTO) {
				
					if(doc!=null && doc.getValidationYlyade()!=null && doc.getFichierDoc()!=null) {
					return doc.getValidationYlyade();
						
				}
//		}		
			
	 }
		return false;
	}
	
	public boolean isDocumentValidateYlyadeNo(Integer idDoc) throws FinderException {
		if(idDoc!=null && taDossierRcd!=null && taDossierRcd.getIdDossierRcd()!=null) {
			TaGedUtilisateur doc = taGedUtilisateurService.findById(idDoc);
			
//			for (TaGedUtilisateurDTO taGedUtilisateurDTO : listeTaGedUtilisateurDTO) {
				
				if(doc!=null && doc.getValidationYlyade()!=null && doc.getFichierDoc()!=null) {
				return !doc.getValidationYlyade();
					
			}
//	}
			
//			for (TaGedUtilisateurDTO taGedUtilisateurDTO : listeTaGedUtilisateurDTO) {
//				if (taGedUtilisateurDTO.getId() == idDoc) {
//					if(taGedUtilisateurDTO!=null && taGedUtilisateurDTO.getValidationYlyade()!=null && taGedUtilisateurDTO.getFichierDoc()!=null) {
//					return !taGedUtilisateurDTO.getValidationYlyade();
//						
//					}
//				}
//		}		
			
	 }
		return false;
	}
	
	
	public Boolean isValidateSuperAssureur() throws FinderException {
		Boolean disableButton = false;
		if(selectedTaDossierRcdDTO!=null && selectedTaDossierRcdDTO.getId()!=null) {
			TaDossierRcd devis = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
			Boolean estValiderAssureurNull = true;
			
			
				
				if(devis!=null) {
					if(devis.getValidationSuperAssureur()==null) {
						estValiderAssureurNull=true;
						}else {
							estValiderAssureurNull=false;
						}	
				}
				
				
				
				if(estValiderAssureurNull==true) {
					disableButton = false;
				}else if (estValiderAssureurNull==false) {
					disableButton = true;
				}
				return disableButton;
			
	 }
		return disableButton;
	}
	

	
	public boolean isDocumentValidateCourtier(Integer idDoc) throws FinderException {
		if(idDoc!=null && taDossierRcd!=null && taDossierRcd.getIdDossierRcd()!=null) {
			
			for (TaGedUtilisateurDTO taGedUtilisateurDTO : listeTaGedUtilisateurDTO) {
				if (taGedUtilisateurDTO.getId() == idDoc) {
					if(taGedUtilisateurDTO!=null && taGedUtilisateurDTO.getValidationCourtier()!=null && taGedUtilisateurDTO.getFichierDoc()!=null) {
					return taGedUtilisateurDTO.getValidationCourtier();
					}
				}
			}
			
//			TaGedUtilisateurDTO taGedUtilisateur = taGedUtilisateurService.findByIdDocAndByIdDevisRcProDTO(idDoc, masterEntity.getIdDossierRcd());
//			TaGedUtilisateurDTO doc = taGedUtilisateurService.findByIdDTO(idDoc);
//			if(doc!=null && doc.getValidationCourtier()!=null && doc.getFichierDoc()!=null) {
//				return doc.getValidationCourtier();
//			}
			
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
        	String NomFichier1 = new String(event.getFile().getFileName().getBytes(Charset.defaultCharset()),"UTF-8");
        	String NomFichier2 = stripAccents(NomFichier1);
        	doc.setNomFichier(NomFichier2);
        	doc.setTypeMime(event.getFile().getContentType());
        	doc.setTaille((int) event.getFile().getSize());
        	
        	ged.setFichierDoc(IOUtils.toByteArray(event.getFile().getInputstream()));
        	ged.setNomFichier(NomFichier2);
        	ged.setTypeMime(event.getFile().getContentType());
        	ged.setTaille((int) event.getFile().getSize());
        	
//			fichier.setBlobFichierMiniature(fichier.resizeImage(IOUtils.toByteArray(event.getFile().getInputstream()), event.getFile().getFileName(), 640, 480));
        	int hMax = 80;
        	int lMax = 350;
        	Image image = ImageIO.read(event.getFile().getInputstream());
        	//BufferedImage bi = TaFichier.resizeImageMax(image,lMax,hMax);
   	
//        	taGedUtilisateurDTO.setTaDossierRcd(masterEntity);    
//        	taGedUtilisateurDTO.setIdDevisRcPro(masterEntity.getIdDossierRcd());  
//        	masterEntity.add(taGedUtilisateurDTO);

			Date now = new Date();
			doc.setDateDepot(now);
        	
			ged = taGedUtilisateurService.merge(ged);
			
			listeTaGedUtilisateurDTO = initListeGed();
        	
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
			taGedUtilisateurDTO.setValidationCourtier(null);
			taGedUtilisateurDTO.setValidationYlyade(null);
			taGedUtilisateurDTO.setValidationSuperAssureur(null);
			
			
			TaGedUtilisateur taGedUtilisateur = taGedUtilisateurService.findById(param2);
			//taGedUtilisateurService.remove(taGedUtilisateur);
			taGedUtilisateur.setFichierDoc(null);
			taGedUtilisateur.setNomFichier(null);
			taGedUtilisateur.setTypeMime(null);
			taGedUtilisateur.setTaille(null);
			taGedUtilisateur.setDateDepot(null);
			taGedUtilisateur.setValidationCourtier(null);
			taGedUtilisateur.setValidationYlyade(null);
			taGedUtilisateur.setValidationSuperAssureur(null);
			taGedUtilisateur = taGedUtilisateurService.merge(taGedUtilisateur);
			
			listeTaGedUtilisateurDTO = initListeGed();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "GedUtilisateur", e.getCause().getCause().getCause().getCause().getMessage()));
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
	
	
	public void validationDocumentYlyade(Boolean valider, int idDoc) {
		
		if(selectedTaDossierRcdDTO!=null) {
			try {
				TaGedUtilisateur taGedUtilisateur = taGedUtilisateurService.findById(idDoc);
				
				
				for (TaGedUtilisateurDTO taGedUtilisateurDTO : listeTaGedUtilisateurDTO) {
								
								if (taGedUtilisateurDTO.getId() == idDoc) {
									
									taGedUtilisateurDTO.setValidationYlyade(false);				
									
								}
								
							}
				
				
				if(valider == true) {
				
					try {
						
						
						taGedUtilisateur.setValidationYlyade(true);
						taGedUtilisateurService.merge(taGedUtilisateur);	

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						FacesContext context = FacesContext.getCurrentInstance();  
						context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "GedUtilisateur", e.getCause().getCause().getCause().getCause().getMessage()));
					}
				
				} else {
					try {
						selectedTaDossierRcdDTO.setValidationGlobaleGedCourtier(false);
						TaDossierRcd dossier = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
						dossier.setValidationGlobaleGedCourtier(false);
						taDossierRcdService.merge(dossier);
						taGedUtilisateur.setValidationYlyade(false);
						taGedUtilisateur.setValidationCourtier(false);
						taGedUtilisateurService.merge(taGedUtilisateur);
											

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						FacesContext context = FacesContext.getCurrentInstance();  
						context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "GedUtilisateur", e.getCause().getCause().getCause().getCause().getMessage()));
					}
				}
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
		
	
	public void validationYlyade() {
		if(selectedTaDossierRcdDTO!=null) {
			try {
			
				TaDossierRcd devis = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
				devis.setValidationYlyade(true);
				selectedTaDossierRcdDTO.setValidationYlyade(true);
				actEnregistrer(null);
//				taDossierRcdService.merge(devis);
				Date now = new Date();
				String nowString;
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à hh:mm:ss");
				nowString = dateFormat.format(now);
				FacesContext context2 = FacesContext.getCurrentInstance();
				ExternalContext externalContext = context2.getExternalContext();
				TaUtilisateur user = (TaUtilisateur) externalContext.getSessionMap().get("userSession");
				System.out.println("************");
				System.out.println("************");
				System.out.println("************");
				System.out.println("************");
				System.out.println("************");
				System.out.println("************");
				System.out.println("************");
				System.out.println("************");
				try {
					System.out.println("******VALIDATION YLYADE***********VALIDATION YLYADE*********VALIDATION YLYADE********** VALIDATION YLYADE POUR DOSSIER id:"+devis.getIdDossierRcd()+" numPolice : "+devis.getNumDossierPolice()+" le "+nowString+" par l'utilisateur connecté id:"+user.getIdUtilisateur()+" "+user.getIdentifiant()+", courtier du dossier : "+devis.getTaCourtier().getCodeCourtier()+" "+devis.getTaCourtier().getNomDenominationSociale()+", Pour l'assuré du dossier : "+taDossierRcd.getTaAssure().getCodeAssure()+" "+taDossierRcd.getTaAssure().getRaisonSociale()+"****************");

				} catch (Exception e) {
					System.out.println("******VALIDATION YLYADE***********VALIDATION YLYADE*********VALIDATION YLYADE**********");
				}
				System.out.println("************");
				System.out.println("************");
				System.out.println("************");
				System.out.println("************");
				System.out.println("************");
				System.out.println("************");
				System.out.println("************");
				System.out.println("************");
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}		
	}
	
	
	
	public Boolean validationGlobaleYlyade() throws FinderException {
		if(selectedTaDossierRcdDTO!=null) {
			if (selectedTaDossierRcdDTO.getValidationYlyade()== null
					&& listeTaGedUtilisateurDTO != null
					&& selectedTaDossierRcdDTO.getPremierPaiementEffectue() != false) {
				if(selectedTaDossierRcdDTO.getValidationGlobaleGedYlyade()==null || selectedTaDossierRcdDTO.getValidationGlobaleGedYlyade()== false) {
					return true;
				}else {
					
				
				int j=0; 
				TaListeRefDoc talistRefDoc = null;
				Boolean restantAValider = false;
				Boolean obligatoire = true;
		    	Boolean dejaValider = true;
				TaGedUtilisateurDTO item = null;
				Boolean result = false;
			    int size = listeTaGedUtilisateurDTO.size();
				
				
		//		Boolean Valider;
				
				
		        while (j< listeTaGedUtilisateurDTO.size() && obligatoire==true && dejaValider==true ){
			        	item = listeTaGedUtilisateurDTO.get(j);
			        	talistRefDoc = taListeRefDocService.findById(item.getIdListeRefDoc());
			        	
	//		        	if(item.getObligatoire()!=null && item.getObligatoire()!=true) {
			        		obligatoire = false;
	//		        	}else {
	//		        		obligatoire = true;
	//		        	}
			        	if(item.getValidationYlyade()!=null && item.getValidationYlyade()!=true) {
			        		dejaValider = false;
			        	}else if(item.getValidationYlyade()==null) {
			        		dejaValider = false;
			        	}
			        	
			        	
			        	if (obligatoire == true && dejaValider==false ) {
			        		restantAValider = true;
						}else if (obligatoire == false && dejaValider==false ) {
							restantAValider = false;
						}
			                j++;
			                
		        	}
		        	return restantAValider;
				}

			}
		}
		return true;
	}

	public void validationSuperAssureur(Boolean valider) {
		if(selectedTaDossierRcdDTO!=null) {
			try {
				
				if(valider == true) {
					System.out.println("DevisRcdController.validationSuperAssureur() - true");
				
					TaDossierRcd devis = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
					devis.setValidationSuperAssureur(true);
					devis.setContrat(true);
					devis.getTaAssure().setClient(true);
					selectedTaDossierRcdDTO.setValidationSuperAssureur(true);
					selectedTaDossierRcdDTO.setContrat(true);
					selectedTaDossierRcdDTO.getTaAssureDTO().setClient(true);
//					taDossierRcdService.merge(devis);
					
					Date now = new Date();
					String nowString;
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à hh:mm:ss");
					nowString = dateFormat.format(now);
					FacesContext context2 = FacesContext.getCurrentInstance();
					ExternalContext externalContext = context2.getExternalContext();
					TaUtilisateur user = (TaUtilisateur) externalContext.getSessionMap().get("userSession");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					try {
						System.out.println("******VALIDATION SUPER ASSUREUR***********VALIDATION SUPER ASSUREUR*********VALIDATION SUPER ASSUREUR********** VALIDATION SUPER ASSUREUR DONC CONTRAT POUR DOSSIER id:"+devis.getIdDossierRcd()+" numPolice : "+devis.getNumDossierPolice()+" le "+nowString+" par l'utilisateur connecté id:"+user.getIdUtilisateur()+" "+user.getIdentifiant()+", courtier du dossier : "+devis.getTaCourtier().getCodeCourtier()+" "+devis.getTaCourtier().getNomDenominationSociale()+", Pour l'assuré du dossier : "+taDossierRcd.getTaAssure().getCodeAssure()+" "+taDossierRcd.getTaAssure().getRaisonSociale()+"****************");

					} catch (Exception e) {
						System.out.println("******VALIDATION SUPER ASSUREUR***********VALIDATION SUPER ASSUREUR*********VALIDATION SUPER ASSUREUR**********");
					}
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					
					if(devis.getNumeroAvenant() != null) {
						envoiMailTestService.envoiMailValidationSuperAssureurAvenant(devis.getTaCourtier(), devis);
					}else {
						envoiMailTestService.envoiMailValidationSuperAssureur(devis.getTaCourtier(), devis);
					}
					
				
				} else {
					
					
					System.out.println("DevisRcdController.validationSuperAssureur() - false");
					TaDossierRcd devis = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
					devis.setValidationSuperAssureur(false);
					selectedTaDossierRcdDTO.setValidationSuperAssureur(false);
//					taDossierRcdService.merge(devis);
					
					Date now = new Date();
					String nowString;
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à hh:mm:ss");
					nowString = dateFormat.format(now);
					FacesContext context2 = FacesContext.getCurrentInstance();
					ExternalContext externalContext = context2.getExternalContext();
					TaUtilisateur user = (TaUtilisateur) externalContext.getSessionMap().get("userSession");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					try {
						System.out.println("******REFUS SUPER ASSUREUR***********REFUS SUPER ASSUREUR*********REFUS SUPER ASSUREUR********** REFUS SUPER ASSUREUR DU DOSSIER id:"+devis.getIdDossierRcd()+" numPolice : "+devis.getNumDossierPolice()+" le "+nowString+" par l'utilisateur connecté id:"+user.getIdUtilisateur()+" "+user.getIdentifiant()+", courtier du dossier : "+devis.getTaCourtier().getCodeCourtier()+" "+devis.getTaCourtier().getNomDenominationSociale()+", Pour l'assuré du dossier : "+taDossierRcd.getTaAssure().getCodeAssure()+" "+taDossierRcd.getTaAssure().getRaisonSociale()+"****************");
						
					} catch (Exception e) {
						System.out.println("******REFUS SUPER ASSUREUR***********REFUS SUPER ASSUREUR*********REFUS SUPER ASSUREUR**********");
					}
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					
					if(devis.getNumeroAvenant() != null) {
						envoiMailTestService.envoiMailRefusSuperAssureurAvenant(devis.getTaCourtier(), devis);
					}else {
						//envoiMailTestService.envoiMailRefusSuperAssureur(devis.getTaCourtier(), devis);
					}
					
					
				}
				actEnregistrer(null);
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void soumettreAExpertise() {
		if(selectedTaDossierRcdDTO!=null) {
			try {

					TaDossierRcd devis = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
					devis.setSoumisEtude(true);
					selectedTaDossierRcdDTO.setSoumisEtude(true);
//					taDossierRcdService.merge(devis);
					actEnregistrer(null);
					
					Date now = new Date();
					String nowString;
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à hh:mm:ss");
					nowString = dateFormat.format(now);
					FacesContext context2 = FacesContext.getCurrentInstance();
					ExternalContext externalContext = context2.getExternalContext();
					TaUtilisateur user = (TaUtilisateur) externalContext.getSessionMap().get("userSession");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					try {
						System.out.println("******SOUMISSION ETUDE DU COURTIER***********SOUMISSION ETUDE DU COURTIER*********SOUMISSION ETUDE DU COURTIER********** SOUMISSION ETUDE DU COURTIER POUR DOSSIER id:"+devis.getIdDossierRcd()+" numPolice : "+devis.getNumDossierPolice()+" le "+nowString+" par l'utilisateur connecté id:"+user.getIdUtilisateur()+" "+user.getIdentifiant()+", courtier du dossier : "+devis.getTaCourtier().getCodeCourtier()+" "+devis.getTaCourtier().getNomDenominationSociale()+", Pour l'assuré du dossier : "+taDossierRcd.getTaAssure().getCodeAssure()+" "+taDossierRcd.getTaAssure().getRaisonSociale()+"****************");

					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("******SOUMISSION ETUDE DU COURTIER***********SOUMISSION ETUDE DU COURTIER*********SOUMISSION ETUDE DU COURTIER**********");
					}
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
				
			} catch (FinderException e) {

				e.printStackTrace();
			}
			
		}
		
	}
	public void refusDefinitifYlyade() {
		if(selectedTaDossierRcdDTO!=null) {
			try {

					TaDossierRcd devis = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
					devis.setRefusDefinitifYlyade(true);;
					selectedTaDossierRcdDTO.setRefusDefinitifYlyade(true);
//					taDossierRcdService.merge(devis);
					actEnregistrer(null);
					Date now = new Date();
					String nowString;
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à hh:mm:ss");
					nowString = dateFormat.format(now);
					FacesContext context2 = FacesContext.getCurrentInstance();
					ExternalContext externalContext = context2.getExternalContext();
					TaUtilisateur user = (TaUtilisateur) externalContext.getSessionMap().get("userSession");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					try {
						System.out.println("******REFUS DEFINITIF YLYADE***********REFUS DEFINITIF YLYADE*********REFUS DEFINITIF YLYADE********** REFUS DEFINITIF YLYADE POUR DOSSIER id:"+devis.getIdDossierRcd()+" numPolice : "+devis.getNumDossierPolice()+" le "+nowString+" par l'utilisateur connecté id:"+user.getIdUtilisateur()+" "+user.getIdentifiant()+", courtier du dossier : "+devis.getTaCourtier().getCodeCourtier()+" "+devis.getTaCourtier().getNomDenominationSociale()+", Pour l'assuré du dossier : "+taDossierRcd.getTaAssure().getCodeAssure()+" "+taDossierRcd.getTaAssure().getRaisonSociale()+"****************");

					} catch (Exception e) {
						System.out.println("******REFUS DEFINITIF YLYADE***********REFUS DEFINITIF YLYADE*********REFUS DEFINITIF YLYADE**********");
					}
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					
				
			} catch (FinderException e) {

				e.printStackTrace();
			}
			
		}
		
	}
//	public Boolean isRefusDefinitifYlyade() throws FinderException {
//		Boolean disableButton = false;
//		if(selectedTaDossierRcdDTO!=null && selectedTaDossierRcdDTO.getId()!=null) {
//			TaDossierRcd devis = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
//			Boolean estRefusDefinitifYlyadeNull = true;
//			
//			
//				
//				if(devis!=null) {
//					if(devis.getRefusDefinitifYlyade()==null) {
//						estRefusDefinitifYlyadeNull=true;
//						}else {
//							estRefusDefinitifYlyadeNull=false;
//						}	
//				}
//				
//				
//				
//				if(estRefusDefinitifYlyadeNull==true) {
//					disableButton = false;
//				}else if (estRefusDefinitifYlyadeNull==false) {
//					disableButton = true;
//				}
//				return disableButton;
//			
//	 }
//		return disableButton;
//	}
	public void refusDefinitifAssureur() {
		if(selectedTaDossierRcdDTO!=null) {
			try {

					TaDossierRcd devis = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
					devis.setRefusDefinitifSuperAssureur(true);;
					selectedTaDossierRcdDTO.setRefusDefinitifSuperAssureur(true);
//					taDossierRcdService.merge(devis);	
					actEnregistrer(null);
					
					Date now = new Date();
					String nowString;
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à hh:mm:ss");
					nowString = dateFormat.format(now);
					FacesContext context2 = FacesContext.getCurrentInstance();
					ExternalContext externalContext = context2.getExternalContext();
					TaUtilisateur user = (TaUtilisateur) externalContext.getSessionMap().get("userSession");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					try {
						System.out.println("******REFUS DEFINITIF ASSUREUR***********REFUS DEFINITIF ASSUREUR*********REFUS DEFINITIF ASSUREUR********** REFUS DEFINITIF ASSUREUR POUR DOSSIER id:"+devis.getIdDossierRcd()+" numPolice : "+devis.getNumDossierPolice()+" le "+nowString+" par l'utilisateur connecté id:"+user.getIdUtilisateur()+" "+user.getIdentifiant()+", courtier du dossier : "+devis.getTaCourtier().getCodeCourtier()+" "+devis.getTaCourtier().getNomDenominationSociale()+", Pour l'assuré du dossier : "+taDossierRcd.getTaAssure().getCodeAssure()+" "+taDossierRcd.getTaAssure().getRaisonSociale()+"****************");

					} catch (Exception e) {
						System.out.println("******REFUS DEFINITIF ASSUREUR***********REFUS DEFINITIF ASSUREUR*********REFUS DEFINITIF ASSUREUR**********");
					}
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
				
			} catch (FinderException e) {

				e.printStackTrace();
			}
			
		}
		
	}
	
	
	public void soumettreAExpertiseAssureur() {
		if(selectedTaDossierRcdDTO!=null) {
			try {

					TaDossierRcd devis = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
					devis.setSoumisEtudeAssureur(true);
					selectedTaDossierRcdDTO.setSoumisEtudeAssureur(true);
//					taDossierRcdService.merge(devis);		
					actEnregistrer(null);
					
					Date now = new Date();
					String nowString;
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à hh:mm:ss");
					nowString = dateFormat.format(now);
					FacesContext context2 = FacesContext.getCurrentInstance();
					ExternalContext externalContext = context2.getExternalContext();
					TaUtilisateur user = (TaUtilisateur) externalContext.getSessionMap().get("userSession");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					try {
						System.out.println("******SOUMISSION ETUDE POUR ASSUREUR***********SOUMISSION ETUDE POUR ASSUREUR*********SOUMISSION ETUDE POUR ASSUREUR********** SOUMISSION ETUDE POUR ASSUREUR POUR DOSSIER id:"+devis.getIdDossierRcd()+" numPolice : "+devis.getNumDossierPolice()+" le "+nowString+" par l'utilisateur connecté id:"+user.getIdUtilisateur()+" "+user.getIdentifiant()+", courtier du dossier : "+devis.getTaCourtier().getCodeCourtier()+" "+devis.getTaCourtier().getNomDenominationSociale()+", Pour l'assuré du dossier : "+taDossierRcd.getTaAssure().getCodeAssure()+" "+taDossierRcd.getTaAssure().getRaisonSociale()+"****************");

					} catch (Exception e) {
						System.out.println("******SOUMISSION ETUDE POUR ASSUREUR***********SOUMISSION ETUDE POUR ASSUREUR*********SOUMISSION ETUDE POUR ASSUREUR**********");
					}
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
				
			} catch (FinderException e) {

				e.printStackTrace();
			}
			
		}
		
	}
	
	public void validationApresEtudeYlyade(Boolean valider) {
		if(selectedTaDossierRcdDTO!=null) {
			try {
				
				if(valider == true) {
				
				
					TaDossierRcd devis = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
					devis.setValidationApresetudeYlyade(true);
					selectedTaDossierRcdDTO.setValidationApresetudeYlyade(true);
//					taDossierRcdService.merge(devis);
					Date now = new Date();
					String nowString;
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à hh:mm:ss");
					nowString = dateFormat.format(now);
					FacesContext context2 = FacesContext.getCurrentInstance();
					ExternalContext externalContext = context2.getExternalContext();
					TaUtilisateur user = (TaUtilisateur) externalContext.getSessionMap().get("userSession");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					try {
						System.out.println("******VALIDATION APRES ETUDE YLYADE***********VALIDATION APRES ETUDE YLYADE*********VALIDATION APRES ETUDE YLYADE********** VALIDATION APRES ETUDE YLYADE POUR DOSSIER id:"+devis.getIdDossierRcd()+" numPolice : "+devis.getNumDossierPolice()+" le "+nowString+" par l'utilisateur connecté id:"+user.getIdUtilisateur()+" "+user.getIdentifiant()+", courtier du dossier : "+devis.getTaCourtier().getCodeCourtier()+" "+devis.getTaCourtier().getNomDenominationSociale()+", Pour l'assuré du dossier : "+taDossierRcd.getTaAssure().getCodeAssure()+" "+taDossierRcd.getTaAssure().getRaisonSociale()+"****************");

					} catch (Exception e) {
						System.out.println("******VALIDATION APRES ETUDE YLYADE***********VALIDATION APRES ETUDE YLYADE*********VALIDATION APRES ETUDE YLYADE**********");
					}
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
				
				} else {
					TaDossierRcd devis = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
					devis.setValidationApresetudeYlyade(false);
					selectedTaDossierRcdDTO.setValidationApresetudeYlyade(false);
//					taDossierRcdService.merge(devis);
					Date now = new Date();
					String nowString;
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à hh:mm:ss");
					nowString = dateFormat.format(now);
					FacesContext context2 = FacesContext.getCurrentInstance();
					ExternalContext externalContext = context2.getExternalContext();
					TaUtilisateur user = (TaUtilisateur) externalContext.getSessionMap().get("userSession");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					try {
						System.out.println("******REFUS APRES ETUDE YLYADE***********REFUS APRES ETUDE YLYADE*********REFUS APRES ETUDE YLYADE********** REFUS APRES ETUDE YLYADE POUR DOSSIER id:"+devis.getIdDossierRcd()+" numPolice : "+devis.getNumDossierPolice()+" le "+nowString+" par l'utilisateur connecté id:"+user.getIdUtilisateur()+" "+user.getIdentifiant()+", courtier du dossier : "+devis.getTaCourtier().getCodeCourtier()+" "+devis.getTaCourtier().getNomDenominationSociale()+", Pour l'assuré du dossier : "+taDossierRcd.getTaAssure().getCodeAssure()+" "+taDossierRcd.getTaAssure().getRaisonSociale()+"****************");

					} catch (Exception e) {
						System.out.println("******REFUS APRES ETUDE YLYADE***********REFUS APRES ETUDE YLYADE*********REFUS APRES ETUDE YLYADE**********");
					}
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
				}
				actEnregistrer(null);
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public Boolean isValidationApresEtudeYlyade() throws FinderException {
		Boolean disableButton = false;
		if(selectedTaDossierRcdDTO!=null && selectedTaDossierRcdDTO.getId()!=null) {
			TaDossierRcd devis = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
			Boolean estValideApresEtudeYlyadeNull = true;
			
			
				
				if(devis!=null) {
					if(devis.getValidationApresetudeYlyade()==null && devis.getSoumisEtudeAssureur()==null && devis.getRefusDefinitifYlyade()==null) {
						estValideApresEtudeYlyadeNull=true;
						}else {
							estValideApresEtudeYlyadeNull=false;
						}	
				}
				
				
				
				if(estValideApresEtudeYlyadeNull==true) {
					disableButton = false;
				}else if (estValideApresEtudeYlyadeNull==false) {
					disableButton = true;
				}
				return disableButton;
			
	 }
		return disableButton;
	}
	
	
	public boolean isSoumisEtudeMaisPasValiderYlyadeNiAssureur() throws FinderException {
		if(selectedTaDossierRcdDTO!=null && selectedTaDossierRcdDTO.getId()!=null) {
			TaDossierRcd devis = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
			
			if(devis!=null) {
				if(devis.getSoumisEtude()!=null) {
					if(devis.getSoumisEtude()==true) {
							if(devis.getValidationApresetudeYlyade() != null && devis.getValidationAssureurApresetude()!=null) {
								if(devis.getValidationApresetudeYlyade() != true && devis.getValidationAssureurApresetude()!=true) {
									return true;
								}
							}
							
						
						}else {
							return false;
						}
				}
			}
			
			
			}
		return false;
		
	}
	
	public boolean isValiderApresEtudeYlyadeOuAssureur() throws FinderException {
		if(selectedTaDossierRcdDTO!=null && selectedTaDossierRcdDTO.getId()!=null) {
			TaDossierRcd devis = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
			
				if(devis!=null) {
	
						if(devis.getValidationApresetudeYlyade() != null && devis.getValidationAssureurApresetude()!=null) {
							if(devis.getValidationApresetudeYlyade() == true || devis.getValidationAssureurApresetude()==true) {
										return true;
							}
						}			
				}
			}
		return false;
		
	}
	
	
	
	
	public void validationAssureurApresEtude(Boolean valider) {
		if(selectedTaDossierRcdDTO!=null) {
			try {
				
				if(valider == true) {
				
				
					TaDossierRcd devis = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
					devis.setValidationAssureurApresetude(true);
					selectedTaDossierRcdDTO.setValidationAssureurApresetude(true);
//					taDossierRcdService.merge(devis);
					Date now = new Date();
					String nowString;
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à hh:mm:ss");
					nowString = dateFormat.format(now);
					FacesContext context2 = FacesContext.getCurrentInstance();
					ExternalContext externalContext = context2.getExternalContext();
					TaUtilisateur user = (TaUtilisateur) externalContext.getSessionMap().get("userSession");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					try {
						System.out.println("******VALIDATION ASSUREUR APRES ETUDE ***********VALIDATION ASSUREUR APRES ETUDE *********VALIDATION ASSUREUR APRES ETUDE ********** VALIDATION ASSUREUR APRES ETUDE POUR DOSSIER id:"+devis.getIdDossierRcd()+" numPolice : "+devis.getNumDossierPolice()+" le "+nowString+" par l'utilisateur connecté id:"+user.getIdUtilisateur()+" "+user.getIdentifiant()+", courtier du dossier : "+devis.getTaCourtier().getCodeCourtier()+" "+devis.getTaCourtier().getNomDenominationSociale()+", Pour l'assuré du dossier : "+taDossierRcd.getTaAssure().getCodeAssure()+" "+taDossierRcd.getTaAssure().getRaisonSociale()+"****************");

					} catch (Exception e) {
						System.out.println("******VALIDATION ASSUREUR APRES ETUDE ***********VALIDATION ASSUREUR APRES ETUDE *********VALIDATION ASSUREUR APRES ETUDE **********");
					}
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
				
				} else {
					TaDossierRcd devis = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
					devis.setValidationAssureurApresetude(false);
					selectedTaDossierRcdDTO.setValidationAssureurApresetude(false);
//					taDossierRcdService.merge(devis);
					Date now = new Date();
					String nowString;
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à hh:mm:ss");
					nowString = dateFormat.format(now);
					FacesContext context2 = FacesContext.getCurrentInstance();
					ExternalContext externalContext = context2.getExternalContext();
					TaUtilisateur user = (TaUtilisateur) externalContext.getSessionMap().get("userSession");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("******REFUS APRES ETUDE ASSUREUR***********REFUS APRES ETUDE ASSUREUR*********REFUS APRES ETUDE ASSUREUR********** REFUS APRES ETUDE ASSUREUR POUR DOSSIER id:"+devis.getIdDossierRcd()+" numPolice : "+devis.getNumDossierPolice()+" le "+nowString+" par l'utilisateur connecté id:"+user.getIdUtilisateur()+" "+user.getIdentifiant()+", courtier du dossier : "+devis.getTaCourtier().getCodeCourtier()+" "+devis.getTaCourtier().getNomDenominationSociale()+", Pour l'assuré du dossier : "+taDossierRcd.getTaAssure().getCodeAssure()+" "+taDossierRcd.getTaAssure().getRaisonSociale()+"****************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
					System.out.println("************");
				}
				actEnregistrer(null);
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public Boolean isValidationAssureurApresEtude() throws FinderException {
		Boolean disableButton = false;
		if(selectedTaDossierRcdDTO!=null && selectedTaDossierRcdDTO.getId()!=null) {
			TaDossierRcd devis = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
			Boolean estValideAssureurApresEtudeNull = true;
			
			
				
				if(devis!=null) {
					if(devis.getValidationAssureurApresetude()==null && devis.getRefusDefinitifSuperAssureur()==null) {
						estValideAssureurApresEtudeNull=true;
						}else {
							estValideAssureurApresEtudeNull=false;
						}	
				}
				
				
				
				if(estValideAssureurApresEtudeNull==true) {
					disableButton = false;
				}else if (estValideAssureurApresEtudeNull==false) {
					disableButton = true;
				}
				return disableButton;
			
	 }
		return disableButton;
	}
	
	
	public Boolean isSoumisEtude() throws FinderException {
		Boolean disableButton = false;
		if(selectedTaDossierRcdDTO!=null && selectedTaDossierRcdDTO.getId()!=null) {
			TaDossierRcd devis = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
			Boolean estSoumisEtudeNull = true;
			
			
				
				if(devis!=null) {
					if(devis.getSoumisEtude()==null) {
						estSoumisEtudeNull=true;
						}else {
							estSoumisEtudeNull=false;
						}	
				}
				
				
				
				if(estSoumisEtudeNull==true) {
					disableButton = false;
				}else if (estSoumisEtudeNull==false) {
					disableButton = true;
				}
				return disableButton;
			
	 }
		return disableButton;
	}
	
	public Boolean isSoumisEtudeAssureur() throws FinderException {
		Boolean disableButton = false;
		if(selectedTaDossierRcdDTO!=null && selectedTaDossierRcdDTO.getId()!=null) {
			TaDossierRcd devis = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
			Boolean estSoumisEtudeAssureurNull = true;
			
			
				
				if(devis!=null) {
					if(devis.getSoumisEtudeAssureur()==null) {
						estSoumisEtudeAssureurNull=true;
						}else {
						estSoumisEtudeAssureurNull=false;
						}	
				}
				
				
				
				if(estSoumisEtudeAssureurNull==true) {
					disableButton = false;
				}else if (estSoumisEtudeAssureurNull==false) {
					disableButton = true;
				}
				return disableButton;
			
	 }
		return disableButton;
	}

	
	public void validationCourtier(ActionEvent actionEvent) {

		try {
			System.out.println(selectedTaActiviteDTOs);
			Integer param =  (Integer) actionEvent.getComponent().getAttributes().get("param");

//			TaGedUtilisateur taGedUtilisateur = taGedUtilisateurService.findByIdDocAndByIdDevisRcPro(param, taDevisRcPro.getIdDossierRcd());
			TaGedUtilisateur taGedUtilisateur = taGedUtilisateurService.findById(param);
//			TaGedUtilisateurDTO taGedUtilisateurDTO = taGedUtilisateurService.findByIdDTO(param);
//			listeTaGedUtilisateurDTO;
			
			for (TaGedUtilisateurDTO taGedUtilisateurDTO : listeTaGedUtilisateurDTO) {
				if (taGedUtilisateurDTO.getId() == param) {
					if((taGedUtilisateurDTO.getValidationCourtier()==null 
						|| taGedUtilisateurDTO.getValidationCourtier()==false) 
						&& taGedUtilisateurDTO.getFichierDoc()!=null){
						
							taGedUtilisateurDTO.setValidationCourtier(true);
					}
				}
				
			}
			if((taGedUtilisateur.getValidationCourtier()==null 
					||  taGedUtilisateur.getValidationCourtier()==false) 
					&& taGedUtilisateur.getFichierDoc()!=null  ) {
				taGedUtilisateur.setValidationCourtier(true);
				taGedUtilisateurService.merge(taGedUtilisateur);
//				actEnregistrer(null);
			}
			
			/***RAJOUT SUITE BUG VALIDATION COURTIER**/
//				
//					taGedUtilisateurService.merge(taGedUtilisateur);	
//			
//			
//					selectedTaDossierRcdDTO.setValidationGlobaleGedCourtier(false);
//					TaDossierRcd dossier = taDossierRcdService.findById(selectedTaDossierRcdDTO.getId());
//					dossier.setValidationGlobaleGedCourtier(false);
//					taDossierRcdService.merge(dossier);
//					taGedUtilisateur.setValidationYlyade(false);
//					taGedUtilisateur.setValidationCourtier(false);
//					taGedUtilisateurService.merge(taGedUtilisateur);
			System.out.println(selectedTaActiviteDTOs);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "GedUtilisateur", e.getCause().getCause().getCause().getCause().getMessage()));
		}

	}
	
	public void actEnregistrerCommentaireCourtierDocumentGed(/*ActionEvent actionEvent*/) {
		try {
			TaGedUtilisateur taGedUtilisateur = taGedUtilisateurService.findById(selectedTaGedUtilisateurDTO.getId());
			taGedUtilisateur.setCommentaireCourtier(selectedTaGedUtilisateurDTO.getCommentaireCourtier());
			taGedUtilisateurService.merge(taGedUtilisateur);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "GedUtilisateur", e.getCause().getCause().getCause().getCause().getMessage()));
		}
	}
	
	public void actEnregistrerCommentaireYlyadeDocumentGed(/*ActionEvent actionEvent*/) {
		try {
			TaGedUtilisateur taGedUtilisateur = taGedUtilisateurService.findById(selectedTaGedUtilisateurDTO.getId());
			taGedUtilisateur.setCommentaireYlyade(selectedTaGedUtilisateurDTO.getCommentaireYlyade());
			taGedUtilisateurService.merge(taGedUtilisateur);
			
			////////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////////
//			String subject = "Novarisks assurance - Nouveau commentaire dans la GED du dossier "+taDossierRcd.getNumDossierPolice();
//			String msgHtml = "Un nouveau commentaire vient d'être ajouter par Novarisks.<br>"
//					+"<br>"
//					+"Dossier : "+taDossierRcd.getNumDossierPolice()+"<br>"
//					+"<br>"
//					+"Document : "+taGedUtilisateur.getTaListeRefDoc().getLiblDoc()+"<br>"
//					+"<br>"
//					+"Commentaire Novarisks : "+taGedUtilisateur.getCommentaireYlyade()+" <br>"
//					+"<br>"
//					+"<br>"
//					+"Cordialement,<br>"
//					+"<br>"
//					+"Novarisks assurance.<br>"
//					+"<br>"
//					+"Novarisks assurance est un service en ligne dédié au courtage d'assurance. <br>"
//					;
//			String[] adressesEmail = {taDossierRcd.getTaCourtier().getTaContactEntreprise().getTaEmail().getAdresseEmail()};
//			lgrMailjetService.send(null, "Ylyade", subject, msgTxt, null, adressesEmail,null, null, null , "311444", variables  );
//			lgrMailjetService.send(null, "Novarisks", subject, null, msgHtml, adressesEmail,null, null, null );
			////////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////////

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "GedUtilisateur", e.getCause().getCause().getCause().getCause().getMessage()));
		}
	}
	
	public void actEnregistrerCommentaireAssureurDocumentGed(/*ActionEvent actionEvent*/) {
		try {
			TaGedUtilisateur taGedUtilisateur = taGedUtilisateurService.findById(selectedTaGedUtilisateurDTO.getId());
			taGedUtilisateur.setCommentaireSuperAssureur(selectedTaGedUtilisateurDTO.getCommentaireSuperAssureur());
			taGedUtilisateurService.merge(taGedUtilisateur);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "GedUtilisateur", e.getCause().getCause().getCause().getCause().getMessage()));
		}
	}
	
	public void actEnregistrerCommentaireCourtierGlobal(ActionEvent actionEvent) {
		actEnregistrer(null);
	}

	
	public StreamedContent getFile() throws FinderException {
//		TaGedUtilisateurDTO taGedUtilisateur =  taGedUtilisateurService.findByIdDocAndByIdDevisRcProDTO(selectedTaListeRefDocDTO.getId(), masterEntity.getIdDossierRcd());
		System.out.println(selectedTaActiviteDTOs);
		TaGedUtilisateurDTO taGedUtilisateur =  taGedUtilisateurService.findByIdDTO(selectedTaGedUtilisateurDTO.getId());
		InputStream stream = new ByteArrayInputStream(taGedUtilisateur.getFichierDoc());
		file = new DefaultStreamedContent(stream, "txt", taGedUtilisateur.getNomFichier());
		System.out.println(selectedTaActiviteDTOs);
        
        
        return file;
    }
	
	public StreamedContent afficheDocument(Integer idDocument) throws FinderException {
//		TaGedUtilisateurDTO taGedUtilisateur =  taGedUtilisateurService.findByIdDocAndByIdDevisRcProDTO(selectedTaListeRefDocDTO.getId(), masterEntity.getIdDossierRcd());
		System.out.println(selectedTaActiviteDTOs);
		TaGedUtilisateurDTO taGedUtilisateur =  taGedUtilisateurService.findByIdDTO(idDocument);
		InputStream stream = new ByteArrayInputStream(taGedUtilisateur.getFichierDoc());
		file = new DefaultStreamedContent(stream, "txt", taGedUtilisateur.getNomFichier());
		System.out.println(selectedTaActiviteDTOs);
        
        
        return file;
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



	public TabView getTabViewDevisRcPro() {
		return tabViewDevisRcPro;
	}

	public void setTabViewDevisRcPro(TabView tabViewDevisRcPro) {
		this.tabViewDevisRcPro = tabViewDevisRcPro;
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
	
	public TaDossierRcdDTO[] getSelectedTaDossierRcdDTOs() {
		return selectedTaDossierRcdDTOs;
	}

	public void setSelectedTaDossierRcdDTOs(TaDossierRcdDTO[] selectedTaDossierRcdDTOs) {
		this.selectedTaDossierRcdDTOs = selectedTaDossierRcdDTOs;
	}

	public TaDossierRcdDTO getNewTaDossierRcdDTO() {
		return newTaDossierRcdDTO;
	}

	public void setNewTaDossierRcdDTO(TaDossierRcdDTO newTaDevisRcProDTO) {
		this.newTaDossierRcdDTO = newTaDevisRcProDTO;
	}

	public TaDossierRcdDTO getSelectedTaDossierRcdDTO() {
		return selectedTaDossierRcdDTO;
	}

	public void setSelectedTaDossierRcdDTO(TaDossierRcdDTO selectedTaDossierRcdDTO) {
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

	public GedDevisRcdController getGedDevisRcProController() {
		return gedDevisRcProController;
	}

	public void setGedDevisRcProController(GedDevisRcdController gedDevisRcProController) {
		this.gedDevisRcProController = gedDevisRcProController;
	}

	public TaDossierRcd getTaDevisRcProDetail() {
		return taDossierRcd;
	}

	public void setTaDevisRcProDetail(TaDossierRcd taDevisRcPro) {
		this.taDossierRcd = taDevisRcPro;
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

	public List<TaGedUtilisateurDTO> getlisteTaGedUtilisateurDTO() {
		return listeTaGedUtilisateurDTO;
	}

	public void setlisteTaGedUtilisateurDTO(List<TaGedUtilisateurDTO> listeTaGedUtilisateurDTO) {
		this.listeTaGedUtilisateurDTO = listeTaGedUtilisateurDTO;
	}

	public Date getDateLimiteCreationEts() {
		return dateLimiteCreationEts;
	}

	public void setDateLimiteCreationEts(Date dateLimiteCreationEts) {
		this.dateLimiteCreationEts = dateLimiteCreationEts;
	}

	public Date getDateLimiteNaissance() {
		return dateLimiteNaissance;
	}

	public void setDateLimiteNaissance(Date dateLimiteNaissance) {
		this.dateLimiteNaissance = dateLimiteNaissance;
	}

	public boolean isCreationCompteClientAssure() {
		return creationCompteClientAssure;
	}

	public void setCreationCompteClientAssure(boolean creationCompteClientAssure) {
		this.creationCompteClientAssure = creationCompteClientAssure;
	}

	public Boolean getAssureExistant() {
		return assureExistant;
	}

	public void setAssureExistant(Boolean assureExistant) {
		this.assureExistant = assureExistant;
	}

	public Boolean getPourEtude() {
		return pourEtude;
	}

	public void setPourEtude(Boolean pourEtude) {
		this.pourEtude = pourEtude;
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

	public Boolean getEnModifPrimeAssureur() {
		return enModifPrimeAssureur;
	}

	public void setEnModifPrimeAssureur(Boolean enModifPrimeAssureur) {
		this.enModifPrimeAssureur = enModifPrimeAssureur;
	}

	public Boolean getAffichePremierOnglet() {
		return affichePremierOnglet;
	}

	public void setAffichePremierOnglet(Boolean affichePremierOnglet) {
		this.affichePremierOnglet = affichePremierOnglet;
	}

	public List<TaTauxRcproPibDTO> getTmpselectedTaTauxRcproPibDTOs() {
		return tmpselectedTaTauxRcproPibDTOs;
	}

	public void setTmpselectedTaTauxRcproPibDTOs(List<TaTauxRcproPibDTO> tmpselectedTaTauxRcproPibDTOs) {
		this.tmpselectedTaTauxRcproPibDTOs = tmpselectedTaTauxRcproPibDTOs;
	}

	public Boolean getBlocageChampsApresPremiereActionReglement() {
		return blocageChampsApresPremiereActionReglement;
	}

	public void setBlocageChampsApresPremiereActionReglement(Boolean blocageChampsApresPremiereActionReglement) {
		this.blocageChampsApresPremiereActionReglement = blocageChampsApresPremiereActionReglement;
	}

	public BigDecimal getMontantPj() {
		return montantPj;
	}

	public void setMontantPj(BigDecimal montantPj) {
		this.montantPj = montantPj;
	}

	public BigDecimal getMontantPjEtendue() {
		return montantPjEtendue;
	}

	public void setMontantPjEtendue(BigDecimal montantPjEtendue) {
		this.montantPjEtendue = montantPjEtendue;
	}

	public BigDecimal getMontantPjEtenduePIB() {
		return montantPjEtenduePIB;
	}

	public void setMontantPjEtenduePIB(BigDecimal montantPjEtenduePIB) {
		this.montantPjEtenduePIB = montantPjEtenduePIB;
	}

}  
