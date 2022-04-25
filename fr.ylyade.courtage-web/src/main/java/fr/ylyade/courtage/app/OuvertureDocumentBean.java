package fr.ylyade.courtage.app;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import fr.ylyade.courtage.controller.AssureController;
import fr.ylyade.courtage.controller.ContratRcdController;
import fr.ylyade.courtage.controller.CourtierController;
import fr.ylyade.courtage.controller.DevisRcdController;
import fr.ylyade.courtage.model.TaAssure;
import fr.ylyade.courtage.model.TaCourtier;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaTAssure;
import fr.ylyade.courtage.service.interfaces.remote.ITaAssureServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaCourtierServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaDossierRcdServiceRemote;

@Named
@Dependent
//@ViewScoped
public class OuvertureDocumentBean implements Serializable {

	private static final long serialVersionUID = 12814265716218582L;

	@Inject @Named(value="assureController")
	private AssureController assureController;	
	
	@Inject @Named(value="courtierController")
	private CourtierController courtierController;	
	
	@Inject @Named(value="devisRcdController")
	private DevisRcdController devisRcdController;	
	
	@Inject @Named(value="contratRcdController")
	private ContratRcdController contratRcdController;	
		
	@EJB private ITaAssureServiceRemote taAssureService;
	@EJB private ITaCourtierServiceRemote taCourtierService;
	@EJB private ITaDossierRcdServiceRemote taDossierRcdService;	
	
	private LgrTabEvent event = new LgrTabEvent();
	
	@PostConstruct
	public void init() {
		
	}

	public void onEventSelect(SelectEvent selectEvent) {
        event = (LgrTabEvent) selectEvent.getObject();
    }

	public void openDocument(ActionEvent e) {
		if(event.getCssLgrTab().equals(LgrTab.CSS_CLASS_TAB_ASSURE)) {
			assureController.setTaAssure(((TaAssure) event.getData()));
			assureController.rempliDTO();
			if(event.isAfficheDoc()) {
				assureController.onRowSelect(null);
			}
		}
		if(event.getCssLgrTab().equals(LgrTab.CSS_CLASS_TAB_COURTIER)) {
			courtierController.setTaCourtier(((TaCourtier) event.getData()));
			courtierController.rempliDTO();
			if(event.isAfficheDoc()) {
				courtierController.onRowSelect(null);
			}
		}
		if(event.getCssLgrTab().equals(LgrTab.CSS_CLASS_TAB_DEVIS_RCPRO)) {
			devisRcdController.setTaDossierRcd((TaDossierRcd) event.getData());
			devisRcdController.rempliDTO();
			if(event.isAfficheDoc()) {
				devisRcdController.onRowSelect(null);
			}
		}		
		if(event.getCssLgrTab().equals(LgrTab.CSS_CLASS_TAB_CONTRAT_RCPRO)) {
			contratRcdController.setTaDossierRcd((TaDossierRcd) event.getData());
			contratRcdController.rempliDTO();
			if(event.isAfficheDoc()) {
				contratRcdController.onRowSelect(null);
			}
		}		
	}
	
	public void creerDocument(ActionEvent e) {
		if(event.getCssLgrTab().equals(LgrTab.CSS_CLASS_TAB_ASSURE)) {
			assureController.setTaAssure(((TaAssure) event.getData()));
			assureController.rempliDTO();
			if(event.isAfficheDoc()) {
				assureController.onRowSelect(null);
			}
		}
		if(event.getCssLgrTab().equals(LgrTab.CSS_CLASS_TAB_COURTIER)) {
			courtierController.setTaCourtier(((TaCourtier) event.getData()));
			courtierController.rempliDTO();
			if(event.isAfficheDoc()) {
				courtierController.onRowSelect(null);
			}
		}
		if(event.getCssLgrTab().equals(LgrTab.CSS_CLASS_TAB_DEVIS_RCPRO)) {
			//devisRcdController.setTaDevisRcPro((TaDossierRcd) event.getData());
			//devisRcdController.rempliDTO();
			if(event.isAfficheDoc()) {
				devisRcdController.onRowSelect(null);
			}
			if(event.getData() instanceof TaAssure) {
				devisRcdController.setTaAssureParamInsertion((TaAssure) event.getData());
			} else if(event.getData() instanceof TaTAssure) {
				devisRcdController.setTaTAssureParamInsertion((TaTAssure) event.getData());
			
			}
			devisRcdController.actInserer(null);
		}		
//		if(event.getCssLgrTab().equals(LgrTab.CSS_CLASS_TAB_CONTRAT_RCPRO)) {
//			contratRcdController.setTaDevisRcPro((TaDossierRcd) event.getData());
//			contratRcdController.rempliDTO();
//			if(event.isAfficheDoc()) {
//				contratRcdController.onRowSelect(null);
//			}
//		}		
	}


	public LgrTabEvent getEvent() {
		return event;
	}


	public void setEvent(LgrTabEvent event) {
		this.event = event;
	}


		
		
//		public IDocumentTiers recupCodeDocument(String code,String type){
//			FacesContext context = FacesContext.getCurrentInstance();
//			if(code!=null && !code.isEmpty())
//				return documentValide(code,type);
//			return null;
//		}
//		
//		private IDocumentTiers documentValide(String code,String type){
//			try {	
//				IDocumentTiers document = null;
//				if(type.equals(TaFacture.TYPE_DOC)) {
//					document=taFactureService.findByCode(code);
//				}
//				if(type.equals(TaAvoir.TYPE_DOC)) {
//					document=taAvoirService.findByCode(code);
//				}
//
//				if(type.equals(TaDevis.TYPE_DOC)) {
//					document=taDevisService.findByCode(code);
//				}
//				if(type.equals(TaBonliv.TYPE_DOC)) {
//					document=taBonlivService.findByCode(code);
//				}
//				if(type.equals(TaBoncde.TYPE_DOC)) {
//					document=taBoncdeService.findByCode(code);
//				}
//				if(type.equals(TaAcompte.TYPE_DOC)) {
//					document=taAcompteService.findByCode(code);
//				}				
//				if(type.equals(TaProforma.TYPE_DOC)) {
//					document=taProformaService.findByCode(code);
//				}
//				if(type.equals(TaPrelevement.TYPE_DOC)) {
//					document=taPrelevementService.findByCode(code);
//				}
//				return document;
//			} catch (FinderException e) {
//				return null;
//			}
//		}
//		
//		public TaTiers recupCodetiers(String code){
//			FacesContext context = FacesContext.getCurrentInstance();
//			if(code!=null && !code.isEmpty())
//				try {
//					return taTiersService.findByCode(code);
//				} catch (FinderException e) {
//					context.addMessage(null, new FacesMessage("erreur", "code tiers vide")); 	
//				}
//			return null;
//		}
//		
//		public String getTypeDocumentFacture() {
//			return TaFacture.TYPE_DOC;
//		}
//		public String getTypeDocumentDevis() {
//			return TaDevis.TYPE_DOC;
//		}
//		public String getTypeDocumentAcompte() {
//			return TaAcompte.TYPE_DOC;
//		}
//		public String getTypeDocumentAvoir() {
//			return TaAvoir.TYPE_DOC;
//		}
//		public String getTypeDocumentApporteur() {
//			return TaApporteur.TYPE_DOC;
//		}
//		public String getTypeDocumentBonLiv() {
//			return TaBonliv.TYPE_DOC;
//		}
//		public String getTypeDocumentBoncde() {
//			return TaBoncde.TYPE_DOC;
//		}
//		public String getTypeDocumentReglement() {
//			return TaReglement.TYPE_DOC;
//		}
//		public String getTypeDocumentRemise() {
//			return TaRemise.TYPE_DOC;
//		}
//		public String getTypeDocumentPrelevement() {
//			return TaPrelevement.TYPE_DOC;
//		}
//		public String getTypeDocumentProforma() {
//			return TaProforma.TYPE_DOC;
//		}
//		public String getTypeDocumentBonReception() {
//			return TaBonReception.TYPE_DOC;
//		}
//		public String getTypeDocumentFabrication() {
//			return TaFabrication.TYPE_DOC;
//		}



		public AssureController getAssureController() {
			return assureController;
		}



		public void setAssureController(AssureController assureController) {
			this.assureController = assureController;
		}



		public CourtierController getCourtierController() {
			return courtierController;
		}



		public void setCourtierController(CourtierController courtierController) {
			this.courtierController = courtierController;
		}



		public DevisRcdController getDevisRcdController() {
			return devisRcdController;
		}



		public void setDevisRcdController(DevisRcdController devisRcdController) {
			this.devisRcdController = devisRcdController;
		}



		public ContratRcdController getContratRcdController() {
			return contratRcdController;
		}



		public void setContratRcdController(ContratRcdController contratRcdController) {
			this.contratRcdController = contratRcdController;
		}



		public ITaAssureServiceRemote getTaAssureService() {
			return taAssureService;
		}



		public void setTaAssureService(ITaAssureServiceRemote taAssureService) {
			this.taAssureService = taAssureService;
		}



		public ITaCourtierServiceRemote getTaCourtierService() {
			return taCourtierService;
		}



		public void setTaCourtierService(ITaCourtierServiceRemote taCourtierService) {
			this.taCourtierService = taCourtierService;
		}



		public ITaDossierRcdServiceRemote getTaDossierRcdService() {
			return taDossierRcdService;
		}



		public void setTaDossierRcdService(ITaDossierRcdServiceRemote taDossierRcdService) {
			this.taDossierRcdService = taDossierRcdService;
		}
		
}
