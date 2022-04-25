package fr.ylyade.courtage.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.FinderException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import fr.legrain.lib.data.EnumModeObjet;
import fr.legrain.lib.data.ModeObjetEcranServeur;
import fr.ylyade.courtage.app.AbstractController;
import fr.ylyade.courtage.dao.jpa.TaDevisRcProDetailDAO;
import fr.ylyade.courtage.dto.TaDossierRcdDTO;
import fr.ylyade.courtage.dto.TaEcheanceDTO;
import fr.ylyade.courtage.dto.TaTauxRcproPibDTO;
import fr.ylyade.courtage.model.TaAttestationNominative;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaEcheance;
import fr.ylyade.courtage.model.TaFraisImpaye;
import fr.ylyade.courtage.model.TaQuittance;
import fr.ylyade.courtage.model.TaReglementAssurance;
import fr.ylyade.courtage.model.TaReglementPrestation;
import fr.ylyade.courtage.model.TaSousDonnee;
import fr.ylyade.courtage.model.TaTFranchise;
import fr.ylyade.courtage.model.TaTReglement;
import fr.ylyade.courtage.model.TaTStatut;
import fr.ylyade.courtage.model.TaTalonComptable;
import fr.ylyade.courtage.model.TaTauxAssurance;
import fr.ylyade.courtage.service.TaDossierRcdService;
import fr.ylyade.courtage.service.editionService;
import fr.ylyade.courtage.service.envoiMailTestService;
import fr.ylyade.courtage.service.interfaces.remote.IEditionServiceRemonte;
import fr.ylyade.courtage.service.interfaces.remote.ITaAttestationNominativeServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaDossierRcdServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaEcheanceServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaEnvoiMailTestServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaQuittanceServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaReglementAssuranceServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaReglementPrestationServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTReglementServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTStatutServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTalonComptableServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTarifPrestationServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTauxAssuranceServiceRemote;

public abstract class AbstractControllerRCD extends AbstractController {
	
	
	protected TaDossierRcd taDossierRcd = new TaDossierRcd();
	protected TaSousDonnee taSousDonnee = new TaSousDonnee();
	protected TaDossierRcdDTO[] selectedTaDossierRcdDTOs; 
	protected TaDossierRcdDTO newTaDossierRcdDTO = new TaDossierRcdDTO();
	protected TaDossierRcdDTO selectedTaDossierRcdDTO = new TaDossierRcdDTO();
	
	protected String selectedPJ = "";


	protected List<TaEcheanceDTO> listeTaEcheanceDTO = new ArrayList<TaEcheanceDTO>();
	protected List<TaDossierRcdDTO> listeAvenantTaDossierRcdDTO = new ArrayList<TaDossierRcdDTO>();
	protected List<TaAttestationNominative> listeAttestationNominative = new ArrayList<TaAttestationNominative>();
	protected TaEcheanceDTO selectedTaEcheanceDTO;
	protected TaAttestationNominative selectedTaAttestationNominative = new TaAttestationNominative();
	
	protected TaEcheanceDTO premiereEcheanceNonPayee;
	
	//PIB
	protected TaTauxRcproPibDTO selectedtxPib;
	protected TaTFranchise fran2000 = new TaTFranchise();
	protected TaTFranchise fran5000 = new TaTFranchise();
	
	
	protected @EJB ITaDossierRcdServiceRemote taDossierRcdService;
	protected @EJB ITaTauxAssuranceServiceRemote taTauxAssuranceService;
	protected @EJB ITaEcheanceServiceRemote taEcheanceService;
	protected @EJB ITaQuittanceServiceRemote taQuittanceService;
	protected @EJB ITaTalonComptableServiceRemote taTalonComptableService;
	protected @EJB ITaReglementAssuranceServiceRemote taReglementAssuranceService;
	protected @EJB ITaReglementPrestationServiceRemote taReglementPrestationService;
	protected @EJB ITaTStatutServiceRemote taTStatutService;
	protected @EJB ITaAttestationNominativeServiceRemote taAttestationNominativeService;
	protected @EJB ITaTReglementServiceRemote taTReglementService;
	protected @EJB ITaTarifPrestationServiceRemote taTarifPrestationService;
	
	@EJB protected ITaEnvoiMailTestServiceRemote envoiMailTestService;
	@EJB protected IEditionServiceRemonte editionService;
	
	protected String classBoutonEnregistrer = " ";
	protected String classBoutonSouscrire = " ";
	protected String classOngletGED = " ";
	
	
	abstract public void actEnregistrer(ActionEvent actionEvent);
	@PostConstruct
	public void initTaTFranchisePIB() {
		 BigDecimal num = new BigDecimal("20.00");
		 num.negate();
		
		fran2000.setCodeTFranchise("FR2000_PIB");
		fran2000.setLiblTFranchise("2000€");
		fran2000.setTauxMontant(num);
		fran2000.setMontant(new BigDecimal("2000.00"));
		
		fran5000.setCodeTFranchise("FR5000_PIB");
		fran5000.setLiblTFranchise("5000€");
		fran5000.setTauxMontant(num);
		fran5000.setMontant(new BigDecimal("5000.00"));
	}
	

	public void classPulseOngletGED(boolean pulse) {
		if(pulse) {
			classOngletGED = "pulse";
		}else {
			classOngletGED = " ";
		}
	}
	
	public void classPulseBoutonEnregistrer(boolean pulse) {
		if(pulse) {
			classBoutonEnregistrer = "pulse";
		}else {
			classBoutonEnregistrer = " ";
		}
	}

	public void classPulseBoutonSouscription(boolean pulse) {
		if(pulse) {
			classBoutonSouscrire = "pulse";
		}else {
			classBoutonSouscrire = " ";
		}
	}
	
	public void actDialoguePaiementEcheanceParCarte() {
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
        
        System.out.println("ContratRcdController.actChequeReglementEcheanceEncaisse() -- ID "+selectedTaEcheanceDTO.getId()+" - "+selectedTaEcheanceDTO.getMontantEcheance());
        
        TaEcheance e = null;
		try {
			e = taEcheanceService.findById(selectedTaEcheanceDTO.getId());
		} catch (FinderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
         
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.remove("echeance");
//		EmailParam email = new EmailParam();
//		email.setEmailExpediteur(null);
////		email.setNomExpediteur(taInfoEntrepriseService.findInstance().getNomInfoEntreprise()); 
////		email.setSubject(taInfoEntrepriseService.findInstance().getNomInfoEntreprise()+" Facture "+masterEntity.getCodeDocument()); 
////		email.setBodyPlain("Votre facture "+masterEntity.getCodeDocument());
////		email.setBodyHtml("Votre facture "+masterEntity.getCodeDocument());
//		email.setDestinataires(new String[]{taTiers.getTaEmail().getAdresseEmail()});
//		email.setEmailDestinataires(new TaEmail[]{taTiers.getTaEmail()});
//		
		//sessionMap.put(EmailParam.NOM_OBJET_EN_SESSION, email);
		sessionMap.put("echeance", e);
        
        RequestContext.getCurrentInstance().openDialog("paiement/dialog_paiement_cb", options, params);
	}
	
	public void actDialoguePaiementAttestationNominativeParCarte() {
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
       
        TaAttestationNominative attest = null;
//		try {
//			attest = taAttestationNominativeService.findById(selectedTaAttestationNominative.getIdAttestationNominative());
			attest = selectedTaAttestationNominative;
//		} catch (FinderException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
         
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.remove("attest");
		
		sessionMap.put("attest", attest);
        
        RequestContext.getCurrentInstance().openDialog("paiement/dialog_paiement_cb_attestation_nominative", options, params);
	}
	
	public void handleReturnDialoguePaiementEcheanceParCarte(SelectEvent event) {
		System.out.println("ContratRcdController.handleReturnDialoguePaiementEcheanceParCarte()");
		try {
			if(event!=null && event.getObject()!=null) {
				TaEcheance e = (TaEcheance) event.getObject();
				
				//taDossierRcd = taDossierRcdService.findById(taDossierRcd.getIdDossierRcd());
				
				for (TaEcheance taEcheance : taDossierRcd.getTaEcheances()) {
					if(taEcheance.getIdEcheance()==selectedTaEcheanceDTO.getId()) {
						taEcheance.setTaReglementAssurance(e.getTaReglementAssurance());
						taEcheance.getTaReglementAssurance().setTaEcheance(taEcheance);
					}
				}
				
//				actEnregistrer(null);
				actActionApresPaiementAccepte();
				
				remplieListeEcheanceDTO();
//				listeTaEcheanceDTO = taEcheanceService.findAllEcheanceRCDIdDTO(taDossierRcd.getIdDossierRcd());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void handleReturnDialoguePaiementAttestationNominativeParCarte(SelectEvent event) {
		System.out.println("ContratRcdController.handleReturnDialoguePaiementAttestationNominativeParCarte()");
		try {
			if(event!=null && event.getObject()!=null) {
				TaAttestationNominative a = (TaAttestationNominative) event.getObject();
				
				
				for (TaAttestationNominative attest : taAttestationNominativeService.findAllByIdDossier(taDossierRcd.getIdDossierRcd())) {
					if(attest.getIdAttestationNominative()==selectedTaAttestationNominative.getIdAttestationNominative()) {
						attest.setTaReglementPrestation(a.getTaReglementPrestation());
//						attest.getTaReglementPrestation().setTaEcheance(attest);
						attest.setMontantPaye(a.getTaReglementPrestation().getMontant());
						attest.getTaReglementPrestation().setValidationPaiement(true);
						taAttestationNominativeService.merge(attest);
					}
				}
				
				
				actEnregistrer(null);
//				actActionApresPaiementAccepte();
				listeAttestationNominative = taAttestationNominativeService.findAllByIdDossier(taDossierRcd.getIdDossierRcd());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actVirementReglementEcheanceEffectueCourtier(/*ActionEvent actionEvent*/) {
		System.out.println("ContratRcdController.actVirementReglementEcheanceEffectueCourtier() -- ID "+selectedTaEcheanceDTO.getId()+" - "+selectedTaEcheanceDTO.getMontantEcheance());
		TaReglementAssurance reg = new TaReglementAssurance();
//		reg.setDateEnvoiChequeParCourtier(new Date());
		reg.setDateVirementEffectue(new Date());
		reg.setRefReglement(taReglementAssuranceService.genereCode(null));
		selectedTaDossierRcdDTO.setDateVirementEffectue(reg.getDateVirementEffectue());
		for (TaEcheance taEcheance : taDossierRcd.getTaEcheances()) {
			if(taEcheance.getIdEcheance()==selectedTaEcheanceDTO.getId()) {
				reg.setTaTReglement(taEcheance.getTaTReglement());
				reg.setTaEcheance(taEcheance);
				if(taEcheance.getMontantEcheancePlusFrais()!=null) {
					reg.setMontant(taEcheance.getMontantEcheancePlusFrais());
				}else {
					reg.setMontant(taEcheance.getMontantEcheance());
				}
//				reg.setMontant(taEcheance.getMontantEcheance());
				taEcheance.setTaReglementAssurance(reg);
			}
		}
		actEnregistrer(null);
//		listeTaEcheanceDTO = taEcheanceService.findAllEcheanceRCDIdDTO(taDossierRcd.getIdDossierRcd());
		remplieListeEcheanceDTO();
	}
	
	public void actVirementReglementAttestationNominativeEffectueCourtier(/*ActionEvent actionEvent*/) {
		System.out.println("ContratRcdController.actVirementReglementAttestationNominativeEffectueCourtier()");
//		TaReglementPrestation reg = new TaReglementPrestation();
//		reg.setDateVirementEffectue(new Date());
		

		for (TaAttestationNominative attest : taAttestationNominativeService.findAllByIdDossier(taDossierRcd.getIdDossierRcd())) {
			if(attest .getIdAttestationNominative()==selectedTaAttestationNominative.getIdAttestationNominative()) {
				
//					attest.setTaReglementPrestation(a.getTaReglementPrestation());
				try {
					attest.getTaReglementPrestation().setTaTReglement(taTReglementService.findByCode(TaTReglement.VIR));
				} catch (FinderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//					attest.getTaReglementPrestation().setRefReglement(taReglementPrestationService.genereCode(null));
					attest.getTaReglementPrestation().setMontant(selectedTaAttestationNominative.getTaTarifPrestation().getMontantPrestation());
					attest.getTaReglementPrestation().setDateVirementEffectue(new Date());
					taAttestationNominativeService.merge(attest);
			
			}
		}
		actEnregistrer(null);
		listeAttestationNominative = taAttestationNominativeService.findAllByIdDossier(taDossierRcd.getIdDossierRcd());
	}
	
	public void actVirementReglementEcheanceValide(/*ActionEvent actionEvent*/) {
		System.out.println("ContratRcdController.actVirementReglementEcheanceValide() -- ID "+selectedTaEcheanceDTO.getId()+" - "+selectedTaEcheanceDTO.getMontantEcheance());
		
		
		for (TaEcheance taEcheance : taDossierRcd.getTaEcheances()) {
			if(taEcheance.getIdEcheance()==selectedTaEcheanceDTO.getId()) {
				taEcheance.getTaReglementAssurance().setDateReglement(new Date());
				taEcheance.getTaReglementAssurance().setValidationManuellePaiement(true);
				taEcheance.getTaReglementAssurance().setDateVirementRecu(new Date());
				selectedTaDossierRcdDTO.setDateVirementRecu(taEcheance.getTaReglementAssurance().getDateVirementRecu());
			}
		}

		actEnregistrer(null);
		actActionApresPaiementAccepte();
//		listeTaEcheanceDTO = taEcheanceService.findAllEcheanceRCDIdDTO(taDossierRcd.getIdDossierRcd());
		remplieListeEcheanceDTO();
		
        
	}
	
	public void actVirementReglementAttestationNominativeValide(/*ActionEvent actionEvent*/) {
		System.out.println("ContratRcdController.actVirementReglementAttestationNominativeValide()");
		
		
		for (TaAttestationNominative attest : taAttestationNominativeService.findAllByIdDossier(taDossierRcd.getIdDossierRcd())) {
			if(attest.getIdAttestationNominative()==selectedTaAttestationNominative.getIdAttestationNominative()) {
				attest.getTaReglementPrestation().setDateReglement(new Date());
//				attest.getTaReglementPrestation().setValidationManuellePaiement(true);
				attest.getTaReglementPrestation().setDateVirementRecu(new Date());
				attest.getTaReglementPrestation().setValidationPaiement(true);
				attest.setMontantPaye(attest.getTaReglementPrestation().getMontant());
				taAttestationNominativeService.merge(attest);
			}
		}

		actEnregistrer(null);
//		actActionApresPaiementAccepte();
//		listeTaEcheanceDTO = taEcheanceService.findAllEcheanceRCDIdDTO(taDossierRcd.getIdDossierRcd());
//		remplieListeEcheanceDTO();
		listeAttestationNominative = taAttestationNominativeService.findAllByIdDossier(taDossierRcd.getIdDossierRcd());
        
	}
	
	public void actChequeReglementEcheanceEnvoyeCourtier(/*ActionEvent actionEvent*/) {
		System.out.println("ContratRcdController.actChequeReglementEcheanceEnvoyeCourtier() -- ID "+selectedTaEcheanceDTO.getId()+" - "+selectedTaEcheanceDTO.getMontantEcheance());
		TaReglementAssurance reg = new TaReglementAssurance();
		reg.setDateEnvoiChequeParCourtier(new Date());
		selectedTaDossierRcdDTO.setDateEnvoiChequeParCourtier(reg.getDateEnvoiChequeParCourtier());
		reg.setRefReglement(taReglementAssuranceService.genereCode(null));
		
		
		for (TaEcheance taEcheance : taDossierRcd.getTaEcheances()) {
			if(taEcheance.getIdEcheance()==selectedTaEcheanceDTO.getId()) {
				reg.setTaTReglement(taEcheance.getTaTReglement());
				reg.setTaEcheance(taEcheance);
				if(taEcheance.getMontantEcheancePlusFrais()!=null) {
					reg.setMontant(taEcheance.getMontantEcheancePlusFrais());
				}else {
					reg.setMontant(taEcheance.getMontantEcheance());
				}
//				reg.setMontant(taEcheance.getMontantEcheance());
				taEcheance.setTaReglementAssurance(reg);
			}
		}
		actEnregistrer(null);
//		listeTaEcheanceDTO = taEcheanceService.findAllEcheanceRCDIdDTO(taDossierRcd.getIdDossierRcd());
		remplieListeEcheanceDTO();
	}
	
	public void actChequeReglementAttestationNominativeEnvoyeCourtier(/*ActionEvent actionEvent*/) {
		System.out.println("ContratRcdController.actChequeReglementAttestationNominativeEnvoyeCourtier()");
		
		for (TaAttestationNominative attest : taAttestationNominativeService.findAllByIdDossier(taDossierRcd.getIdDossierRcd())) {
			if(attest .getIdAttestationNominative()==selectedTaAttestationNominative.getIdAttestationNominative()) {
				
				try {
					attest.getTaReglementPrestation().setTaTReglement(taTReglementService.findByCode(TaTReglement.CHEQUE));
				} catch (FinderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					attest.getTaReglementPrestation().setDateEnvoiChequeParCourtier(new Date());
//					attest.getTaReglementPrestation().setRefReglement(taReglementPrestationService.genereCode(null));
					attest.getTaReglementPrestation().setMontant(selectedTaAttestationNominative.getTaTarifPrestation().getMontantPrestation());
					taAttestationNominativeService.merge(attest);
			
			}
		}
		actEnregistrer(null);
		listeAttestationNominative = taAttestationNominativeService.findAllByIdDossier(taDossierRcd.getIdDossierRcd());
	}
	
	public void actChequeReglementEcheanceRecu(/*ActionEvent actionEvent*/) {
		System.out.println("ContratRcdController.actChequeReglementEcheanceRecu() -- ID "+selectedTaEcheanceDTO.getId()+" - "+selectedTaEcheanceDTO.getMontantEcheance());
		
		for (TaEcheance taEcheance : taDossierRcd.getTaEcheances()) {
			if(taEcheance.getIdEcheance()==selectedTaEcheanceDTO.getId()) {
				taEcheance.getTaReglementAssurance().setDateReceptionCheque(new Date());
				selectedTaDossierRcdDTO.setDateReceptionCheque(taEcheance.getTaReglementAssurance().getDateReceptionCheque());
			}
		}
		actEnregistrer(null);
//		listeTaEcheanceDTO = taEcheanceService.findAllEcheanceRCDIdDTO(taDossierRcd.getIdDossierRcd());
		remplieListeEcheanceDTO();
	}
	
	public void actChequeReglementAttestationNominativeRecu(/*ActionEvent actionEvent*/) {
		System.out.println("ContratRcdController.actChequeReglementAttestationNominativeRecu() ");


		
		for (TaAttestationNominative attest : taAttestationNominativeService.findAllByIdDossier(taDossierRcd.getIdDossierRcd())) {
			if(attest .getIdAttestationNominative()==selectedTaAttestationNominative.getIdAttestationNominative()) {
				
					attest.getTaReglementPrestation().setDateReceptionCheque(new Date());

					taAttestationNominativeService.merge(attest);
			
			}
		}
		actEnregistrer(null);
		listeAttestationNominative = taAttestationNominativeService.findAllByIdDossier(taDossierRcd.getIdDossierRcd());
	}
	
	public void actChequeReglementEcheanceEncaisse(/*ActionEvent actionEvent*/) {
		System.out.println("ContratRcdController.actChequeReglementEcheanceEncaisse() -- ID "+selectedTaEcheanceDTO.getId()+" - "+selectedTaEcheanceDTO.getMontantEcheance());
		for (TaEcheance taEcheance : taDossierRcd.getTaEcheances()) {
			if(taEcheance.getIdEcheance()==selectedTaEcheanceDTO.getId()) {
//				taEcheance.getTaReglementAssurance().setDateEncaissementCheque(new Date());
				taEcheance.getTaReglementAssurance().setDateDepotCheque(new Date());
				selectedTaDossierRcdDTO.setDateDepotCheque(taEcheance.getTaReglementAssurance().getDateDepotCheque());
			}
		}
		actEnregistrer(null);
//		listeTaEcheanceDTO = taEcheanceService.findAllEcheanceRCDIdDTO(taDossierRcd.getIdDossierRcd());
		remplieListeEcheanceDTO();
	}
	
	public void actChequeReglementAttestationNominativeEncaisse(/*ActionEvent actionEvent*/) {
		System.out.println("ContratRcdController.actChequeReglementAttestationNominativeEncaisse() ");

		for (TaAttestationNominative attest : taAttestationNominativeService.findAllByIdDossier(taDossierRcd.getIdDossierRcd())) {
			if(attest .getIdAttestationNominative()==selectedTaAttestationNominative.getIdAttestationNominative()) {
				
					attest.getTaReglementPrestation().setDateDepotCheque(new Date());

					taAttestationNominativeService.merge(attest);
			
			}
		}
		actEnregistrer(null);
		listeAttestationNominative = taAttestationNominativeService.findAllByIdDossier(taDossierRcd.getIdDossierRcd());
	}
	
	public void actChequeReglementEcheanceValide(/*ActionEvent actionEvent*/) {		
		
		for (TaEcheance taEcheance : taDossierRcd.getTaEcheances()) {
			if(taEcheance.getIdEcheance()==selectedTaEcheanceDTO.getId()) {
				taEcheance.getTaReglementAssurance().setDateEncaissementCheque(new Date());
				taEcheance.getTaReglementAssurance().setDateReglement(new Date());
				selectedTaDossierRcdDTO.setDateEncaissementCheque(taEcheance.getTaReglementAssurance().getDateEncaissementCheque());
				taEcheance.getTaReglementAssurance().setValidationManuellePaiement(true);
			}
		}
		

//		actEnregistrer(null);
		actActionApresPaiementAccepte();
//		listeTaEcheanceDTO = taEcheanceService.findAllEcheanceRCDIdDTO(taDossierRcd.getIdDossierRcd());
		remplieListeEcheanceDTO();
        
	}
	
	public void actChequeReglementAttestationNominativeValide(/*ActionEvent actionEvent*/) {
		System.out.println("ContratRcdController.actChequeReglementAttestationNominativeValide()");
		
		
		for (TaAttestationNominative attest : taAttestationNominativeService.findAllByIdDossier(taDossierRcd.getIdDossierRcd())) {
			if(attest .getIdAttestationNominative()==selectedTaAttestationNominative.getIdAttestationNominative()) {
				
					attest.getTaReglementPrestation().setDateEncaissementCheque(new Date());
					attest.getTaReglementPrestation().setDateReglement(new Date());
					attest.getTaReglementPrestation().setValidationPaiement(true);
					attest.setMontantPaye(attest.getTaReglementPrestation().getMontant());

					taAttestationNominativeService.merge(attest);
			
			}
		}
		actEnregistrer(null);
		listeAttestationNominative = taAttestationNominativeService.findAllByIdDossier(taDossierRcd.getIdDossierRcd());
        
	}
	
	public void actActionApresPaiementAccepte() {
//		taDossierRcd.setContrat(true);
//		selectedTaDossierRcdDTO.setContrat(true);
		taDossierRcd.setPremierPaiementEffectue(true);
		selectedTaDossierRcdDTO.setPremierPaiementEffectue(true);
		
		for (TaEcheance taEcheance : taDossierRcd.getTaEcheances()) {
			if(taEcheance.getIdEcheance()==selectedTaEcheanceDTO.getId()) {
				//generer Quittance et talon comptable
				TaQuittance q = new TaQuittance();
				q.setTaReglementAssurance(taEcheance.getTaReglementAssurance());
				taEcheance.getTaReglementAssurance().setTaQuittance(q);
				q.setCodePrestation(taQuittanceService.genereCode(null));
				q.setLiblPrestation("Quittance de l'échéance : "+taEcheance.getIdEcheance());
				//virer les not null
				
				
				TaTalonComptable tc = new TaTalonComptable();
				tc.setTaReglementAssurance(taEcheance.getTaReglementAssurance());
				taEcheance.getTaReglementAssurance().setTaTalonComptable(tc);
				tc.setCodePrestation(taTalonComptableService.genereCode(null));
				tc.setLiblPrestation("Talon comptable de l'échéance : "+taEcheance.getIdEcheance());
				tc.setMontantCommission(taEcheance.getMontantCommission());
				tc.setMontantSurCommission(taEcheance.getMontantSurCommission());
				tc.setMontantFraisDeDossier(taEcheance.getMontantFraisDeDossier());
			}
		}
		
		for (TaTStatut stat : taDossierRcd.getTaTStatut()) {
			if(stat.getCodeTStatut().equals(TaTStatut.suspendu) || stat.getCodeTStatut().equals(TaTStatut.miseDemeure) ) {
				taDossierRcd.setSuspenduNonPaiement(null);
				taDossierRcd.setContratEnCours(true);
				taDossierRcd.setMisEnDemeure(null);
				selectedTaDossierRcdDTO.setMisEnDemeure(null);
				selectedTaDossierRcdDTO.setSuspenduNonPaiement(null);
				selectedTaDossierRcdDTO.setContratEnCours(true);
			}
		}
		

		
		actEnregistrer(null);
//		listeTaEcheanceDTO = taEcheanceService.findAllEcheanceRCDIdDTO(taDossierRcd.getIdDossierRcd());
		remplieListeEcheanceDTO();
		
		//envoi du mail avec le reglement et le courtier en param de l'échéance qui vient d'être réglée
		if(selectedTaEcheanceDTO != null ) {
			if(selectedTaEcheanceDTO.getId() != null && selectedTaEcheanceDTO.getId() != 0) {
				try {
					TaEcheance ech = taEcheanceService.findById(selectedTaEcheanceDTO.getId());
					if(ech != null) {
						if(ech.getTaReglementAssurance() != null) {
							TaReglementAssurance reg = ech.getTaReglementAssurance();
							envoiMailTestService.envoiMailPaiementComptant(taDossierRcd.getTaCourtier(), reg);
						}
						
					}
				} catch (FinderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	
	public void actImprimerQuittance(/*ActionEvent actionEvent*/) {
		System.out.println("ContratRcdController.actImprimerQuittance() -- ID "+selectedTaEcheanceDTO.getId()+" - "+selectedTaEcheanceDTO.getMontantEcheance());
			
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			sessionMap.remove("echeance");
			if(selectedTaEcheanceDTO != null) {
				sessionMap.put("echeance", taEcheanceService.findById(selectedTaEcheanceDTO.getId()));
			}
		
			System.out.println("CourtierController.actImprimer()");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void actImprimerAttestationNominative(/*ActionEvent actionEvent*/) {
//		System.out.println("ContratRcdController.actImprimerAttestationNominative() -- ID "+selectedTaEcheanceDTO.getId()+" - "+selectedTaEcheanceDTO.getMontantEcheance());
			
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			sessionMap.remove("attest");
			
			for (TaAttestationNominative attest : listeAttestationNominative) {
				if(attest.getIdAttestationNominative() == selectedTaAttestationNominative.getIdAttestationNominative()) {
					sessionMap.put("attest", attest);
				}
			}
		
//			System.out.println("CourtierController.actImprimer()");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void actImprimerTalonComptable(/*ActionEvent actionEvent*/) {
		System.out.println("ContratRcdController.actImprimerTalonComptable() -- ID "+selectedTaEcheanceDTO.getId()+" - "+selectedTaEcheanceDTO.getMontantEcheance());
		
		System.out.println("ContratRcdController.actImprimerQuittance() -- ID "+selectedTaEcheanceDTO.getId()+" - "+selectedTaEcheanceDTO.getMontantEcheance());
		
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			sessionMap.remove("echeance");
			if(selectedTaEcheanceDTO != null) {
				sessionMap.put("echeance", taEcheanceService.findById(selectedTaEcheanceDTO.getId()));
				sessionMap.put("inclusionFraisDossier", selectedTaDossierRcdDTO.getInclusionFraisDossier());
			}
		
			System.out.println("CourtierController.actImprimer()");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	public void actImprimerReleveDeSinistralite() {
		//System.out.println("ContratRcdController.actImprimerReleveDeSinistralite() -- ID "+selectedTaEcheanceDTO.getId()+" - "+selectedTaEcheanceDTO.getMontantEcheance());
			
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			sessionMap.remove("devisrcpro");
			
			sessionMap.put("devisrcpro", taDossierRcdService.findById(taDossierRcd.getIdDossierRcd()));
	
//			for (TaEcheance taEcheance : taDossierRcd.getTaEcheances()) {
//				if(taEcheance.getIdEcheance()==selectedTaEcheanceDTO.getId()) {
//					sessionMap.put("echeance", taEcheance);
//				}
//			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
//	public void actImprimerConditionsParticulieres() {
//		try {
//			String pdf = editionService.genereConditionParticulierePDF(taDossierRcd.getIdDossierRcd(), null);
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
//	}
	
	
	public void actImprimerConditionsParticulieres() {
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			sessionMap.remove("devisrcpro");
			sessionMap.remove("tarifpj");
			
			sessionMap.put("devisrcpro", taDossierRcdService.findById(taDossierRcd.getIdDossierRcd()));
			sessionMap.put("tarifpj", taTauxAssuranceService.findByCode(TaTauxAssurance.MONTANT_PROTECTION_JURIDIQUE));
		
			System.out.println("CourtierController.actImprimer()");
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void actImprimerConditionsParticulieresPib() {
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			sessionMap.remove("devisrcpro");
			sessionMap.remove("tarifpj");
			
			sessionMap.put("devisrcpro", taDossierRcdService.findById(taDossierRcd.getIdDossierRcd()));
			sessionMap.put("tarifpj", taTauxAssuranceService.findByCode(TaTauxAssurance.MONTANT_PROTECTION_JURIDIQUE));
		
			System.out.println("CourtierController.actImprimer()");
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for (TaEcheance taEcheance : taDossierRcd.getTaEcheances()) {
			if(taEcheance.getIdEcheance()==selectedTaEcheanceDTO.getId()) {
				taEcheance.getTaReglementAssurance().setDateReglement(new Date());
				taEcheance.getTaReglementAssurance().setValidationManuellePaiement(true);
			}
		}
		
	}
	
	public void actImprimerAvisEcheance() {
		try {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			sessionMap.remove("echeance");
			if(selectedTaEcheanceDTO != null) {
				sessionMap.put("echeance", taEcheanceService.findById(selectedTaEcheanceDTO.getId()));
			}
			
//			String pdf = editionService.genereAvisEcheancePDF(selectedTaEcheanceDTO.getId(), null);
//			
//			//Enfin on ouvre un onglet pour afficher le PDF
//			try {
//				String urlEncoded = URLEncoder.encode(pdf, "UTF-8");
//				//PrimeFaces.current().executeScript("window.open('/edition?fichier="+urlEncoded+"')");
//				RequestContext.getCurrentInstance().execute("window.open('/edition?fichier="+urlEncoded+"')");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
			
			
		
			System.out.println("CourtierController.actImprimer()");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void rempliePremiereEcheanceNonPaye(List<TaEcheanceDTO> listeEch) {
		for (TaEcheanceDTO taEcheanceDTO : listeEch) {
			if( (taEcheanceDTO.getValidationAutomatiquePaiement()==null && taEcheanceDTO.getValidationManuellePaiement()== null) ||
					(!taEcheanceDTO.getValidationAutomatiquePaiement() && !taEcheanceDTO.getValidationManuellePaiement()) ) {
				premiereEcheanceNonPayee = taEcheanceDTO;
				break;
			}
		}
	}
	
	public void remplieListeEcheanceDTO() {
		try {
			List<TaEcheanceDTO> listeTmpDTO = taEcheanceService.findAllEcheanceRCDIdDTO(taDossierRcd.getIdDossierRcd());
			listeTaEcheanceDTO.clear();
			for (TaEcheanceDTO taEcheanceDTO : listeTmpDTO) {
				TaEcheance echeance = taEcheanceService.findByCode(taEcheanceDTO.getCodeEcheance());
				for (TaFraisImpaye frais : echeance.getTaFraisImpaye()) {
					taEcheanceDTO.getTaFraisImpaye().add(frais);
				}
				
				listeTaEcheanceDTO.add(taEcheanceDTO);
			}
			rempliePremiereEcheanceNonPaye(listeTaEcheanceDTO);
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public List<TaEcheanceDTO> getListeTaEcheanceDTO() {
		return listeTaEcheanceDTO;
	}

	public void setListeTaEcheanceDTO(List<TaEcheanceDTO> listeTaEcheanceDTO) {
		this.listeTaEcheanceDTO = listeTaEcheanceDTO;
	}

	public TaEcheanceDTO getSelectedTaEcheanceDTO() {
		return selectedTaEcheanceDTO;
	}

	public void setSelectedTaEcheanceDTO(TaEcheanceDTO selectedTaEcheanceDTO) {
		this.selectedTaEcheanceDTO = selectedTaEcheanceDTO;
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

	public void setNewTaDossierRcdDTO(TaDossierRcdDTO newTaDossierRcdDTO) {
		this.newTaDossierRcdDTO = newTaDossierRcdDTO;
	}

	public TaDossierRcdDTO getSelectedTaDossierRcdDTO() {
		return selectedTaDossierRcdDTO;
	}

	public void setSelectedTaDossierRcdDTO(TaDossierRcdDTO selectedTaDossierRcdDTO) {
		this.selectedTaDossierRcdDTO = selectedTaDossierRcdDTO;
	}

	public List<TaDossierRcdDTO> getListeAvenantTaDossierRcdDTO() {
		return listeAvenantTaDossierRcdDTO;
	}

	public void setListeAvenantTaDossierRcdDTO(List<TaDossierRcdDTO> listeAvenantTaDossierRcdDTO) {
		this.listeAvenantTaDossierRcdDTO = listeAvenantTaDossierRcdDTO;
	}
	
	public TaDossierRcd getTaDossierRcd() {
		return taDossierRcd;
	}

	public void setTaDossierRcd(TaDossierRcd taDossierRcd) {
		this.taDossierRcd = taDossierRcd;
	}

	public List<TaAttestationNominative> getListeAttestationNominative() {
		return listeAttestationNominative;
	}

	public void setListeAttestationNominative(List<TaAttestationNominative> listeAttestationNominative) {
		this.listeAttestationNominative = listeAttestationNominative;
	}
	
	public TaAttestationNominative getSelectedTaAttestationNominative() {
		return selectedTaAttestationNominative;
	}

	public void setSelectedTaAttestationNominative(TaAttestationNominative selectedTaAttestationNominative) {
		this.selectedTaAttestationNominative = selectedTaAttestationNominative;
	}

	public TaSousDonnee getTaSousDonnee() {
		return taSousDonnee;
	}

	public void setTaSousDonnee(TaSousDonnee taSousDonnee) {
		this.taSousDonnee = taSousDonnee;
	}

	public String getSelectedPJ() {
		return selectedPJ;
	}

	public void setSelectedPJ(String selectedPJ) {
		this.selectedPJ = selectedPJ;
	}

	public TaEcheanceDTO getPremiereEcheanceNonPayee() {
		return premiereEcheanceNonPayee;
	}

	public void setPremiereEcheanceNonPayee(TaEcheanceDTO premiereEcheanceNonPayee) {
		this.premiereEcheanceNonPayee = premiereEcheanceNonPayee;
	}

	public TaTauxRcproPibDTO getSelectedtxPib() {
		return selectedtxPib;
	}

	public void setSelectedtxPib(TaTauxRcproPibDTO selectedtxPib) {
		this.selectedtxPib = selectedtxPib;
	}
	public String getClassBoutonEnregistrer() {
		return classBoutonEnregistrer;
	}
	public void setClassBoutonEnregistrer(String classBoutonEnregistrer) {
		this.classBoutonEnregistrer = classBoutonEnregistrer;
	}
	public String getClassBoutonSouscrire() {
		return classBoutonSouscrire;
	}
	public void setClassBoutonSouscrire(String classBoutonSouscrire) {
		this.classBoutonSouscrire = classBoutonSouscrire;
	}
	public String getClassOngletGED() {
		return classOngletGED;
	}
	public void setClassOngletGED(String classOngletGED) {
		this.classOngletGED = classOngletGED;
	}

}
