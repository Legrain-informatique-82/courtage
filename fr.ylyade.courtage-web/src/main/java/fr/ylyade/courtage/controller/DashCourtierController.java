package fr.ylyade.courtage.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import fr.ylyade.courtage.app.ConstWeb;
import fr.ylyade.courtage.dto.TaDossierRcdDTO;
import fr.ylyade.courtage.dto.TaReglementAssuranceDTO;
import fr.ylyade.courtage.dto.TaReglementPrestationDTO;
import fr.ylyade.courtage.model.TaTStatut;

@Named
@ViewScoped 
public class DashCourtierController extends DevisRcdController{

	private static final long serialVersionUID = 6278107399024181110L;
	
	private List<TaDossierRcdDTO> listContratATraiter;
	private Integer nbContratATraiter;
	private List<TaDossierRcdDTO> listDossierPieceManquanteOuRefuse;
	private Integer nbDossierPieceManquanteOuRefuse;
	private List<TaDossierRcdDTO> listDevisAttenteValidation;
	private Integer nbDevisAttenteValidation;
	private List<TaDossierRcdDTO> listDossierRefuse;
	private Integer nbDossierRefuse;
	
	private List<TaDossierRcdDTO> listDevisAttenteValidationAssureur;
	private Integer nbDevisAttenteValidationAssureur;
	
	///////PAIEMENTS
	private List<TaDossierRcdDTO> listDevisAttentePaiement;
	private Integer nbDevisAttentePaiement;
	private List<TaDossierRcdDTO> listDevisAttenteEnvoiCheque;
	private Integer nbDevisAttenteEnvoiCheque;
	private List<TaDossierRcdDTO> listDevisAttenteEncaissementCheque;
	private Integer nbDevisAttenteEncaissementCheque;
	private List<TaDossierRcdDTO> listDevisAttenteVirementEffectue;
	private Integer nbDevisAttenteVirementEffectue;
	private List<TaDossierRcdDTO> listDevisAttenteReceptionVirement;
	private Integer nbDevisAttenteReceptionVirement;
	
	
	
	//////AVENANTS
	private List<TaDossierRcdDTO> listAvenantPieceManquanteOuRefuse;
	private Integer nbAvenantPieceManquanteOuRefuse;
	private List<TaDossierRcdDTO> listAvenantAttentePaiement;
	private Integer nbAvenantAttentePaiement;
	private List<TaDossierRcdDTO> listAvenantAttenteValidationAssureur;
	private Integer nbAvenantAttenteValidationAssureur;
	private List<TaDossierRcdDTO> listAvenantAttenteValidationYlyade;
	private Integer nbAvenantAttenteValidationYlyade;
	private List<TaDossierRcdDTO> listAvenantRefuse;
	private Integer nbAvenantRefuse;
	
	
	
	///////SOUMIS A ETUDE
	private List<TaDossierRcdDTO> listSoumisEtudeAttenteValidation;
	private Integer nbSoumisEtudeAttenteValidation;
	private List<TaDossierRcdDTO> listSoumisEtudeAttenteValidationAssureur;
	private Integer nbSoumisEtudeAttenteValidationAssureur;
	private List<TaDossierRcdDTO> listSoumisEtudeValide;
	private Integer nbSoumisEtudeValide;
	private List<TaDossierRcdDTO> listSoumisEtudeRefuse;
	private Integer nbSoumisEtudeRefuse;
	private List<TaDossierRcdDTO> listSoumisEtudeRefuseDefinitif;
	private Integer nbSoumisEtudeRefuseDefinitif;
	
	//Espace paiement (contrats)
	private List<TaDossierRcdDTO> listContratArrivantTermesCourtier;
	private Integer nbContratArrivantTermesCourtier;
	private List<TaDossierRcdDTO> listContratTermesDepasseeCourtier;
	private Integer nbContratTermesDepasseeCourtier;
	private List<TaDossierRcdDTO> listContratArrivantEcheanceAnnuelleCourtier;
	private Integer nbContratArrivantEcheanceAnnuelleCourtier;
	private List<TaDossierRcdDTO> listContratMisEnDemeureCourtier;
	private Integer nbContratMisEnDemeureCourtier;
	private List<TaDossierRcdDTO> listContratSuspendusNonPaiementCourtier;
	private Integer nbContratSuspendusNonPaiementCourtier;
	
	//Resiliation
	private List<TaDossierRcdDTO> listContratResilieEcheanceCourtier;
	private Integer nbContratResilieEcheanceCourtier;
	private List<TaDossierRcdDTO> listContratResilieCessationActiviteCourtier;
	private Integer nbContratResilieCessationActiviteCourtier;
	private List<TaDossierRcdDTO> listContratResilieNonPaiementCourtier;
	private Integer nbContratResilieNonPaiementCourtier;
	private List<TaDossierRcdDTO> listContratResilieSansEffetCourtier;
	private Integer nbContratResilieSansEffetCourtier;
	private List<TaDossierRcdDTO> listContratResilieAmiableCourtier;
	private Integer nbContratResilieAmiableCourtier;
	private List<TaDossierRcdDTO> listContratResilieFausseDeclarationCourtier;
	private Integer nbContratResilieFausseDeclarationCourtier;
	
	//Attestation
	private List<TaDossierRcdDTO> listContratAttentePaiementAttestationNominative;
	private Integer nbContratAttentePaiementAttestationNominative;
	

		
	@Override
	public void refresh() {
		
		taCourtier = taCourtierService.findByIdUtilisateur(auth.getUser().getIdUtilisateur());
		if(taCourtier != null) {
			List<TaDossierRcdDTO> liste = taDossierRcdService.findAllContratATraiterByIdCourtier(taCourtier.getIdCourtier());
			List<TaDossierRcdDTO> listeDossierPieceManquanteOuRefuse = taDossierRcdService.findAllPieceManquanteOuRefuseByIdCourtier(taCourtier.getIdCourtier());
			List<TaDossierRcdDTO> listeDevisEnAttenteValidation = taDossierRcdService.findAllAttenteValidationYlyadeByIdCourtier(taCourtier.getIdCourtier());
			List<TaDossierRcdDTO> listeDossierRefuse = taDossierRcdService.findRefuseAssureurByIdCourtier(taCourtier.getIdCourtier());
			List<TaDossierRcdDTO> listeDevisAttenteValidationAssureur = taDossierRcdService.findAllAttenteValidationAssureurByIdCourtier(taCourtier.getIdCourtier());
			
			///PAIEMENT
			List<TaDossierRcdDTO> listeDevisAttenteEnvoiCheque = taDossierRcdService.findAllEnAttenteEnvoiChequeByIdCourtier(taCourtier.getIdCourtier());
			List<TaDossierRcdDTO> listeDevisAttentePaiement = taDossierRcdService.findAllEnAttentePaiementByIdCourtier(taCourtier.getIdCourtier());
			List<TaDossierRcdDTO> listeDevisAttenteEncaissementCheque = taDossierRcdService.findAllEnAttenteEncaissementChequeByIdCourtier(taCourtier.getIdCourtier());
			List<TaDossierRcdDTO> listeDevisAttenteVirementEffectue = taDossierRcdService.findAllEnAttenteVirementEffectueByIdCourtier(taCourtier.getIdCourtier());
			List<TaDossierRcdDTO> listeDevisAttenteReceptionVirement = taDossierRcdService.findAllEnAttenteReceptionVirementByIdCourtier(taCourtier.getIdCourtier());

			
			
			
			
			
			///Avenants
			List<TaDossierRcdDTO> listeAvenantPieceManquanteOuRefuse = taDossierRcdService.findAllAvenantPieceManquanteOuRefuseByIdCourtier(taCourtier.getIdCourtier());
			List<TaDossierRcdDTO> listeAvenantAttentePaiement = taDossierRcdService.findAllAvenantEnAttentePaiementByIdCourtier(taCourtier.getIdCourtier());
			List<TaDossierRcdDTO> listeAvenantAttenteValidationAssureur = taDossierRcdService.findAllAvenantAttenteValidationAssureurByIdCourtier(taCourtier.getIdCourtier());
			List<TaDossierRcdDTO> listeAvenantAttenteValidationYlyade = taDossierRcdService.findAllAvenantAttenteValidationYlyadeByIdCourtier(taCourtier.getIdCourtier());
			List<TaDossierRcdDTO> listeAvenantRefuse = taDossierRcdService.findAvenantRefuseAssureurByIdCourtier(taCourtier.getIdCourtier());
			
			///////SOUMIS A ETUDE
			List<TaDossierRcdDTO> listeSoumisEtudeAttenteValidation = taDossierRcdService.findAllSoumisEtudeAttenteValidationYlyadeByIdCourtier(taCourtier.getIdCourtier());
			List<TaDossierRcdDTO> listeSoumisEtudeAttenteValidationAssureur = taDossierRcdService.findAllSoumisEtudeAttenteValidationAssureurByIdCourtier(taCourtier.getIdCourtier());
			List<TaDossierRcdDTO> listeSoumisEtudeValide= taDossierRcdService.findAllSoumisEtudeValideByIdCourtier(taCourtier.getIdCourtier());
			List<TaDossierRcdDTO> listeSoumisEtudeRefuse= taDossierRcdService.findAllSoumisEtudeRefuseByIdCourtier(taCourtier.getIdCourtier());
			List<TaDossierRcdDTO> listeSoumisEtudeRefuseDefinitif = taDossierRcdService.findAllSoumisEtudeRefuseDefinitifByIdCourtier(taCourtier.getIdCourtier());
			
			//Espace Paiement (contrat)
			Date today = new Date();
			LocalDate localToday= today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate localTodayPlus30 = localToday.plusDays(30);
			LocalDate localTodayMoins10 = localToday.minusDays(10);
			Date todayPlus30 = Date.from(localTodayPlus30.atStartOfDay(ZoneId.systemDefault()).toInstant());
			Date todayMoins10 = Date.from(localTodayMoins10.atStartOfDay(ZoneId.systemDefault()).toInstant());
			//List<TaDossierRcdDTO> listeContratArrivantTermes = taDossierRcdService.findAllContratArrivantTermesByCourtier(taCourtier.getIdCourtier(),todayPlus30);
			List<TaDossierRcdDTO> listeContratArrivantTermes = taDossierRcdService.findAllContratArrivantTermesByCourtierAndPasDepasseByCourtier(taCourtier.getIdCourtier(),todayPlus30);
			List<TaDossierRcdDTO> listeContratTermesDepassee = taDossierRcdService.findAllContratTermesDepasseeByCourtier(taCourtier.getIdCourtier(),todayMoins10);
			List<TaDossierRcdDTO> listeContratArrivantEcheanceAnnuelle = taDossierRcdService.findAllContratArrivantEcheanceAnnuelleByCourtier(taCourtier.getIdCourtier(),todayPlus30);
			List<TaDossierRcdDTO> listeContratMisEnDemeure =  taDossierRcdService.findAllContratByStatutAndByCourtier(taCourtier.getIdCourtier(), TaTStatut.miseDemeure);
			List<TaDossierRcdDTO> listeContratSuspendusNonPaiement = taDossierRcdService.findAllContratByStatutAndByCourtier(taCourtier.getIdCourtier(),TaTStatut.suspendu);

			
			//resiliation

			List<TaDossierRcdDTO> listeContratResilieEcheance = taDossierRcdService.findAllContratByStatutAndByCourtier( taCourtier.getIdCourtier(),TaTStatut.resilieEcheance);
			List<TaDossierRcdDTO> listeContratResilieCessationActivite = taDossierRcdService.findAllContratByStatutAndByCourtier( taCourtier.getIdCourtier(),TaTStatut.resilieCessationActivite);
			List<TaDossierRcdDTO> listeContratResilieNonPaiement = taDossierRcdService.findAllContratByStatutAndByCourtier( taCourtier.getIdCourtier(),TaTStatut.resilieNonPaiement);
			List<TaDossierRcdDTO> listeContratResilieSansEffet = taDossierRcdService.findAllContratByStatutAndByCourtier( taCourtier.getIdCourtier(),TaTStatut.sansEffet);
			List<TaDossierRcdDTO> listeContratResilieAmiable = taDossierRcdService.findAllContratByStatutAndByCourtier( taCourtier.getIdCourtier(),TaTStatut.resilieAmiable);
			List<TaDossierRcdDTO> listeContratResilieFausseDeclaration = taDossierRcdService.findAllContratByStatutAndByCourtier( taCourtier.getIdCourtier(),TaTStatut.resilieFausseDeclaration);
			
			//attestations
			List<TaDossierRcdDTO> listeContratAttentePaiementAttestationNominative = taDossierRcdService.findAllContratAttentePaiementAttestationNominativeByCourtier(taCourtier.getIdCourtier());
			
			if(liste != null) {
				listContratATraiter = liste;
				
			}else {
				listContratATraiter = new ArrayList<TaDossierRcdDTO>();
			}
			
			
			if(listeDossierPieceManquanteOuRefuse != null) {
				listDossierPieceManquanteOuRefuse = listeDossierPieceManquanteOuRefuse;
			}else {
				listDossierPieceManquanteOuRefuse= new ArrayList<TaDossierRcdDTO>();
			}
			
			if(listeDevisEnAttenteValidation != null) {
				listDevisAttenteValidation = listeDevisEnAttenteValidation;
			}else {
				listDevisAttenteValidation= new ArrayList<TaDossierRcdDTO>();
			}
			
			if(listeDossierRefuse != null) {
				listDossierRefuse = listeDossierRefuse;
			}else {
				listDossierRefuse= new ArrayList<TaDossierRcdDTO>();
			}
			///////////PAIEMENT
			if(listeDevisAttentePaiement != null) {
				listDevisAttentePaiement = listeDevisAttentePaiement;
			}else {
				listDevisAttentePaiement= new ArrayList<TaDossierRcdDTO>();
			}
			if(listeDevisAttenteEnvoiCheque != null) {
				listDevisAttenteEnvoiCheque = listeDevisAttenteEnvoiCheque;
				
			}else {
				listDevisAttenteEnvoiCheque = new ArrayList<TaDossierRcdDTO>();
			}
			
			if(listeDevisAttenteValidationAssureur != null) {
				listDevisAttenteValidationAssureur = listeDevisAttenteValidationAssureur;
			}else {
				listDevisAttenteValidationAssureur = new ArrayList<TaDossierRcdDTO>();
			}
			
			if(listeDevisAttenteEncaissementCheque != null) {
				listDevisAttenteEncaissementCheque = listeDevisAttenteEncaissementCheque;
				
			}else {
				listDevisAttenteEncaissementCheque = new ArrayList<TaDossierRcdDTO>();
			}
			if(listeDevisAttenteVirementEffectue != null) {
				listDevisAttenteVirementEffectue = listeDevisAttenteVirementEffectue;
				
			}else {
				listDevisAttenteVirementEffectue = new ArrayList<TaDossierRcdDTO>();
			}
			if(listeDevisAttenteReceptionVirement != null) {
				listDevisAttenteReceptionVirement = listeDevisAttenteReceptionVirement;
				
			}else {
				listDevisAttenteReceptionVirement = new ArrayList<TaDossierRcdDTO>();
			}
			
			//AVENANT
			
			if(listeAvenantPieceManquanteOuRefuse != null) {
				listAvenantPieceManquanteOuRefuse = listeAvenantPieceManquanteOuRefuse;
			}else {
				listAvenantPieceManquanteOuRefuse = new ArrayList<TaDossierRcdDTO>();
				
			}if(listeAvenantAttentePaiement != null) {
				listAvenantAttentePaiement = listeAvenantAttentePaiement;
			}else {
				listAvenantAttentePaiement = new ArrayList<TaDossierRcdDTO>();
				
			}if(listeAvenantAttenteValidationAssureur != null) {
				listAvenantAttenteValidationAssureur = listeAvenantAttenteValidationAssureur;
			}else {
				listAvenantAttenteValidationAssureur = new ArrayList<TaDossierRcdDTO>();
				
			}if(listeAvenantAttenteValidationYlyade != null) {
				listAvenantAttenteValidationYlyade = listeAvenantAttenteValidationYlyade;
			}else {
				listAvenantAttenteValidationYlyade = new ArrayList<TaDossierRcdDTO>();
				
			}if(listeAvenantRefuse != null) {
				listAvenantRefuse = listeAvenantRefuse;
			}else {
				listAvenantRefuse = new ArrayList<TaDossierRcdDTO>();
			}
			
			//SOUMIS A ETUDE
			
					if(listeSoumisEtudeAttenteValidation != null) {
						listSoumisEtudeAttenteValidation = listeSoumisEtudeAttenteValidation;
					}else {
						listSoumisEtudeAttenteValidation = new ArrayList<TaDossierRcdDTO>();
						
					}if(listeSoumisEtudeAttenteValidationAssureur != null) {
						listSoumisEtudeAttenteValidationAssureur = listeSoumisEtudeAttenteValidationAssureur;
					}else {
						listSoumisEtudeAttenteValidationAssureur = new ArrayList<TaDossierRcdDTO>();
						
					}if(listeSoumisEtudeValide != null) {
						listSoumisEtudeValide = listeSoumisEtudeValide;
					}else {
						listSoumisEtudeValide = new ArrayList<TaDossierRcdDTO>();
						
					}if(listeSoumisEtudeRefuse != null) {
						listSoumisEtudeRefuse = listeSoumisEtudeRefuse;
					}else {
						listSoumisEtudeRefuse = new ArrayList<TaDossierRcdDTO>();
					}
					if(listeSoumisEtudeRefuseDefinitif != null) {
						listSoumisEtudeRefuseDefinitif = listeSoumisEtudeRefuseDefinitif;
						
					}else {
						listSoumisEtudeRefuseDefinitif = new ArrayList<TaDossierRcdDTO>();
					}
			
					
					//Espace Paiement
					if(listeContratArrivantTermes != null) {
						listContratArrivantTermesCourtier= listeContratArrivantTermes;
						
					}else {
						listContratArrivantTermesCourtier = new ArrayList<TaDossierRcdDTO>();
					}
					if(listeContratTermesDepassee != null) {
						listContratTermesDepasseeCourtier = listeContratTermesDepassee;
						
					}else {
						listContratTermesDepasseeCourtier= new ArrayList<TaDossierRcdDTO>();
					}
					
					if(listeContratArrivantEcheanceAnnuelle != null) {
						listContratArrivantEcheanceAnnuelleCourtier = listeContratArrivantEcheanceAnnuelle;
						
					}else {
						listContratArrivantEcheanceAnnuelleCourtier = new ArrayList<TaDossierRcdDTO>();
					}
					if(listeContratMisEnDemeure != null) {
						listContratMisEnDemeureCourtier = listeContratMisEnDemeure;
						
					}else {
						listContratMisEnDemeureCourtier = new ArrayList<TaDossierRcdDTO>();
					}
					if(listeContratSuspendusNonPaiement != null) {
						listContratSuspendusNonPaiementCourtier= listeContratSuspendusNonPaiement;
						
					}else {
						listContratSuspendusNonPaiementCourtier = new ArrayList<TaDossierRcdDTO>();
					}
					//resiliation
					if(listeContratResilieEcheance != null) {
						listContratResilieEcheanceCourtier = listeContratResilieEcheance;
						
					}else {
						listContratResilieEcheanceCourtier = new ArrayList<TaDossierRcdDTO>();
					}
					
					if(listeContratResilieCessationActivite != null) {
						listContratResilieCessationActiviteCourtier= listeContratResilieCessationActivite;
						
					}else {
						listContratResilieCessationActiviteCourtier = new ArrayList<TaDossierRcdDTO>();
					}
					if(listeContratResilieNonPaiement != null) {
						listContratResilieNonPaiementCourtier = listeContratResilieNonPaiement;
						
					}else {
						listContratResilieNonPaiementCourtier = new ArrayList<TaDossierRcdDTO>();
					}
					if(listeContratResilieSansEffet != null) {
						listContratResilieSansEffetCourtier = listeContratResilieSansEffet;
						
					}else {
						listContratResilieSansEffetCourtier = new ArrayList<TaDossierRcdDTO>();
					}
					if(listeContratResilieAmiable != null) {
						listContratResilieAmiableCourtier = listeContratResilieAmiable;
						
					}else {
						listContratResilieAmiableCourtier = new ArrayList<TaDossierRcdDTO>();
					}
					if(listeContratResilieFausseDeclaration != null) {
						listContratResilieFausseDeclarationCourtier = listeContratResilieFausseDeclaration;
						
					}else {
						listContratResilieFausseDeclarationCourtier = new ArrayList<TaDossierRcdDTO>();
					}
					
					//attestation
					if(listeContratAttentePaiementAttestationNominative != null) {
						setListContratAttentePaiementAttestationNominative(listeContratAttentePaiementAttestationNominative);
						
					}else {
						setListContratAttentePaiementAttestationNominative(new ArrayList<TaDossierRcdDTO>());
					}
			
			
			nbContratATraiter = listContratATraiter.size();
			nbDossierPieceManquanteOuRefuse = listDossierPieceManquanteOuRefuse.size();
			nbDevisAttenteValidation = listDevisAttenteValidation.size();
			nbDossierRefuse = listDossierRefuse.size();
			nbDevisAttenteValidationAssureur = listDevisAttenteValidationAssureur.size();
			
			//paiement
			nbDevisAttentePaiement = listDevisAttentePaiement.size();
			nbDevisAttenteEnvoiCheque = listDevisAttenteEnvoiCheque.size();
			nbDevisAttenteEncaissementCheque = listDevisAttenteEncaissementCheque.size();
			nbDevisAttenteVirementEffectue = listDevisAttenteVirementEffectue.size();
			nbDevisAttenteReceptionVirement = listDevisAttenteReceptionVirement.size();
			
			//avenants
			nbAvenantPieceManquanteOuRefuse = listAvenantPieceManquanteOuRefuse.size();
			nbAvenantAttentePaiement =  listAvenantAttentePaiement.size();
			nbAvenantAttenteValidationAssureur = listAvenantAttenteValidationAssureur.size();
			nbAvenantAttenteValidationYlyade = listAvenantAttenteValidationYlyade.size();
			nbAvenantRefuse =listAvenantRefuse.size();
			
			//soumis a etude
			nbSoumisEtudeAttenteValidation = listSoumisEtudeAttenteValidation.size(); 
			nbSoumisEtudeAttenteValidationAssureur = listSoumisEtudeAttenteValidationAssureur.size();
			nbSoumisEtudeValide = listSoumisEtudeValide.size();
			nbSoumisEtudeRefuse = listSoumisEtudeRefuse.size();
			nbSoumisEtudeRefuseDefinitif = listSoumisEtudeRefuseDefinitif.size();
			
			//espace paiement (contrats)
			nbContratArrivantTermesCourtier= listContratArrivantTermesCourtier.size();
			nbContratTermesDepasseeCourtier = listContratTermesDepasseeCourtier.size();
			nbContratArrivantEcheanceAnnuelleCourtier = listContratArrivantEcheanceAnnuelleCourtier.size();
			nbContratMisEnDemeureCourtier = listContratMisEnDemeureCourtier.size();
			nbContratSuspendusNonPaiementCourtier= listContratSuspendusNonPaiementCourtier.size();
			
			
			//resiliation
			nbContratResilieEcheanceCourtier = listContratResilieEcheanceCourtier.size();
			nbContratResilieCessationActiviteCourtier = listContratResilieCessationActiviteCourtier.size();
		    nbContratResilieNonPaiementCourtier = listContratResilieNonPaiementCourtier.size();
			nbContratResilieSansEffetCourtier = listContratResilieSansEffetCourtier.size();
			nbContratResilieAmiableCourtier = listContratResilieAmiableCourtier.size();
			nbContratResilieFausseDeclarationCourtier = listContratResilieFausseDeclarationCourtier.size();
			
			//attestation
			nbContratAttentePaiementAttestationNominative = listContratAttentePaiementAttestationNominative.size();
			
		}
		
				 
		  
	}
	

/////////////////////////////////
	
	public void actImprimerListeContratATraiter(ActionEvent actionEvent) {
	if(ConstWeb.DEBUG) {
	FacesContext context = FacesContext.getCurrentInstance();  
	context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
	}
	
	try {
	
	ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	Map<String, Object> sessionMap = externalContext.getSessionMap();
	
	
	sessionMap.put("listeContratATraiter",listContratATraiter  );
	
	
	} catch (Exception e) {
	e.printStackTrace();
	}
	} 	
	
	public void actImprimerListeDossierAvecPiecesAValider(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeDossierAvecPiecesAValider",listDossierPieceManquanteOuRefuse  );
			
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		} 	
	
	public void actImprimerListeDevisAttenteValidationYlyade(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeDevisAttenteValidationYlyade",listDevisAttenteValidation  );
			
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
	
	public void actImprimerListeDevisAttenteValidationCompagnie(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeDevisAttenteValidationCompagnie",listDevisAttenteValidationAssureur  );
			
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		} 
	
	public void actImprimerListeDossierRefuse(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeDossierRefuse",listDossierRefuse  );
			
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		} 	
	
	public void actImprimerListeDevisAttentePaiement(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeDevisAttentePaiement",listDevisAttentePaiement  );
			
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		} 	
	
	public void actImprimerListeDevisAttenteEnvoiCheque(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeDevisAttenteEnvoiCheque",listDevisAttenteEnvoiCheque  );
			
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		} 
	
	public void actImprimerListeDevisAttenteEncaissementCheque(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeDevisAttenteEncaissementCheque",listDevisAttenteEncaissementCheque  );
			
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
	
	public void actImprimerListeDevisAttenteReceptionVirement(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeDevisAttenteReceptionVirement",listDevisAttenteReceptionVirement  );
			
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		} 	
	
	public void actImprimerListeDevisAttenteVirementEffectue(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeDevisAttenteVirementEffectue",listDevisAttenteVirementEffectue  );
			
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		} 	
	
	public void actImprimerListeAvenantAvecPieceAValider(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeAvenantAvecPieceAValider",listAvenantPieceManquanteOuRefuse  );
			
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		} 
	
	public void actImprimerListeAvenantAttentePaiement(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeAvenantAttentePaiement",listAvenantAttentePaiement  );
			
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
	
	public void actImprimerListeAvenantAttenteValidationYlyade(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeAvenantAttenteValidationYlyade",listAvenantAttenteValidationYlyade  );
			
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		} 
	
	public void actImprimerListeAvenantAttenteValidationCompagnie(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeAvenantAttenteValidationCompagnie",listAvenantAttenteValidationAssureur  );
			
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		} 
	
	public void actImprimerListeAvenantRefuseCourtier(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeAvenantRefuseCourtier",listAvenantRefuse  );
			
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		} 	
	
	public void actImprimerListeSoumisEtudeAttenteValidationYlyade(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeSoumisEtudeAttenteValidationYlyade",listSoumisEtudeAttenteValidation  );
			
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		} 
	
	public void actImprimerListeSoumisEtudeAttenteValidationCompagnie(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeSoumisEtudeAttenteValidationCompagnie",listSoumisEtudeAttenteValidationAssureur  );
			
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		} 
	
	public void actImprimerListeSoumisEtudeValideCourtier(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeSoumisEtudeValideCourtier",listSoumisEtudeValide  );
			
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		} 	
	
	public void actImprimerListeSoumisEtudeRefuseCourtier(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeSoumisEtudeRefuseCourtier",listSoumisEtudeRefuse  );
			
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
	
	public void actImprimerListeSoumisEtudeRefuseDefinitifCourtier(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeSoumisEtudeRefuseDefinitifCourtier",listSoumisEtudeRefuseDefinitif  );
			
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		} 
	
	public void actImprimerListeDevisContratResilieNonPaiementCourtier(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
			}
			
			try {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			
			
			sessionMap.put("listeDevisContratResilieNonPaiementCourtier",listContratResilieNonPaiementCourtier  );
				
			
			} catch (Exception e) {
			e.printStackTrace();
			}
	}
	public void actImprimerListeDevisContratResilieEcheanceCourtier(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
			}
			
			try {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			
			
			sessionMap.put("listeDevisContratResilieEcheanceCourtier",listContratResilieNonPaiementCourtier  );
				
			
			} catch (Exception e) {
			e.printStackTrace();
			}
	}
	public void actImprimerListeDevisContratResilieFausseDeclarationCourtier(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
			}
			
			try {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			
			
			sessionMap.put("listeDevisContratResilieFausseDeclarationCourtier",listContratResilieFausseDeclarationCourtier  );
				
			
			} catch (Exception e) {
			e.printStackTrace();
			}
	}
	public void actImprimerListeDevisContratResilieCessationActiviteCourtier(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
			}
			
			try {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			
			
			sessionMap.put("listeDevisContratResilieCessationActiviteCourtier",listContratResilieCessationActiviteCourtier  );
				
			
			} catch (Exception e) {
			e.printStackTrace();
			}
	}
	public void actImprimerListeDevisContratResilieSansEffetCourtier(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
			}
			
			try {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			
			
			sessionMap.put("listeDevisContratResilieSansEffetCourtier",listContratResilieSansEffetCourtier  );
				
			
			} catch (Exception e) {
			e.printStackTrace();
			}
	}
	public void actImprimerListeDevisContratResilieAmiableCourtier(ActionEvent actionEvent){
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
			}
			
			try {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			
			
			sessionMap.put("listeDevisContratResilieAmiableCourtier",listContratResilieAmiableCourtier  );
				
			
			} catch (Exception e) {
			e.printStackTrace();
			}
	}
	
	public void actImprimerListeDevisContratArrivantTermesCourtier(ActionEvent actionEvent){
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
			}
			
			try {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			
			
			sessionMap.put("listeContratArrivantTermesCourtier",listContratArrivantTermesCourtier  );
				
			
			} catch (Exception e) {
			e.printStackTrace();
			}
	}
	public void actImprimerListeDevisContratTermesDepasseeCourtier(ActionEvent actionEvent){
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
			}
			
			try {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			
			
			sessionMap.put("listeContratTermesDepasseeCourtier",listContratTermesDepasseeCourtier  );
				
			
			} catch (Exception e) {
			e.printStackTrace();
			}
	}
	public void actImprimerListeDevisContratArrivantEcheanceAnnuelleCourtier(ActionEvent actionEvent){
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
			}
			
			try {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			
			
			sessionMap.put("listeContratArrivantEcheanceAnnuelleCourtier",listContratArrivantEcheanceAnnuelleCourtier  );
				
			
			} catch (Exception e) {
			e.printStackTrace();
			}
	}
	public void actImprimerListeDevisContratMisEnDemeureCourtier(ActionEvent actionEvent){
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
			}
			
			try {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			
			
			sessionMap.put("listeContratMisEnDemeureCourtier",listContratMisEnDemeureCourtier  );
				
			
			} catch (Exception e) {
			e.printStackTrace();
			}
	}
	public void actImprimerListeDevisContratSuspendusNonPaiementCourtier(ActionEvent actionEvent){
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
			}
			
			try {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			
			
			sessionMap.put("listeContratSuspendusNonPaiementCourtier",listContratSuspendusNonPaiementCourtier  );
				
			
			} catch (Exception e) {
			e.printStackTrace();
			}
	}
	public void actImprimerListeDevisContratAttentePaiementAttestationNominative(ActionEvent actionEvent){
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
			}
			
			try {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			
			
			sessionMap.put("listeContratAttentePaiementAttestationNominative",listContratAttentePaiementAttestationNominative  );
				
			
			} catch (Exception e) {
			e.printStackTrace();
			}
	}
/////////////////////////////////
	
	public List<TaDossierRcdDTO> getListContratATraiter() {
		return listContratATraiter;
	}

	public void setListContratATraiter(List<TaDossierRcdDTO> listContratATraiter) {
		this.listContratATraiter = listContratATraiter;
	}

	




	public Integer getNbContratATraiter() {
		return nbContratATraiter;
	}






	public void setNbContratATraiter(Integer nbContratATraiter) {
		this.nbContratATraiter = nbContratATraiter;
	}






	public List<TaDossierRcdDTO> getListDossierPieceManquanteOuRefuse() {
		return listDossierPieceManquanteOuRefuse;
	}






	public void setListDossierPieceManquanteOuRefuse(List<TaDossierRcdDTO> listDossierPieceManquanteOuRefuse) {
		this.listDossierPieceManquanteOuRefuse = listDossierPieceManquanteOuRefuse;
	}






	public Integer getNbDossierPieceManquanteOuRefuse() {
		return nbDossierPieceManquanteOuRefuse;
	}






	public void setNbDossierPieceManquanteOuRefuse(Integer nbDossierPieceManquanteOuRefuse) {
		this.nbDossierPieceManquanteOuRefuse = nbDossierPieceManquanteOuRefuse;
	}






	public List<TaDossierRcdDTO> getListDevisAttenteValidation() {
		return listDevisAttenteValidation;
	}






	public void setListDevisAttenteValidation(List<TaDossierRcdDTO> listDevisAttenteValidation) {
		this.listDevisAttenteValidation = listDevisAttenteValidation;
	}






	public Integer getNbDevisAttenteValidation() {
		return nbDevisAttenteValidation;
	}






	public void setNbDevisAttenteValidation(Integer nbDevisAttenteValidation) {
		this.nbDevisAttenteValidation = nbDevisAttenteValidation;
	}






	public List<TaDossierRcdDTO> getListDossierRefuse() {
		return listDossierRefuse;
	}






	public void setListDossierRefuse(List<TaDossierRcdDTO> listDossierRefuse) {
		this.listDossierRefuse = listDossierRefuse;
	}






	public Integer getNbDossierRefuse() {
		return nbDossierRefuse;
	}






	public void setNbDossierRefuse(Integer nbDossierRefuse) {
		this.nbDossierRefuse = nbDossierRefuse;
	}






	public List<TaDossierRcdDTO> getListDevisAttentePaiement() {
		return listDevisAttentePaiement;
	}






	public void setListDevisAttentePaiement(List<TaDossierRcdDTO> listDevisAttentePaiement) {
		this.listDevisAttentePaiement = listDevisAttentePaiement;
	}






	public Integer getNbDevisAttentePaiement() {
		return nbDevisAttentePaiement;
	}






	public void setNbDevisAttentePaiement(Integer nbDevisAttentePaiement) {
		this.nbDevisAttentePaiement = nbDevisAttentePaiement;
	}






	public Integer getNbDevisAttenteValidationAssureur() {
		return nbDevisAttenteValidationAssureur;
	}






	public void setNbDevisAttenteValidationAssureur(Integer nbDevisAttenteValidationAssureur) {
		this.nbDevisAttenteValidationAssureur = nbDevisAttenteValidationAssureur;
	}





	public List<TaDossierRcdDTO> getListDevisAttenteValidationAssureur() {
		return listDevisAttenteValidationAssureur;
	}






	public void setListDevisAttenteValidationAssureur(List<TaDossierRcdDTO> listDevisAttenteValidationAssureur) {
		this.listDevisAttenteValidationAssureur = listDevisAttenteValidationAssureur;
	}






	public List<TaDossierRcdDTO> getListAvenantPieceManquanteOuRefuse() {
		return listAvenantPieceManquanteOuRefuse;
	}






	public void setListAvenantPieceManquanteOuRefuse(List<TaDossierRcdDTO> listAvenantPieceManquanteOuRefuse) {
		this.listAvenantPieceManquanteOuRefuse = listAvenantPieceManquanteOuRefuse;
	}






	public Integer getNbAvenantPieceManquanteOuRefuse() {
		return nbAvenantPieceManquanteOuRefuse;
	}






	public void setNbAvenantPieceManquanteOuRefuse(Integer nbAvenantPieceManquanteOuRefuse) {
		this.nbAvenantPieceManquanteOuRefuse = nbAvenantPieceManquanteOuRefuse;
	}






	public List<TaDossierRcdDTO> getListAvenantAttentePaiement() {
		return listAvenantAttentePaiement;
	}






	public void setListAvenantAttentePaiement(List<TaDossierRcdDTO> listAvenantAttentePaiement) {
		this.listAvenantAttentePaiement = listAvenantAttentePaiement;
	}






	public Integer getNbAvenantAttentePaiement() {
		return nbAvenantAttentePaiement;
	}






	public void setNbAvenantAttentePaiement(Integer nbAvenantAttentePaiement) {
		this.nbAvenantAttentePaiement = nbAvenantAttentePaiement;
	}






	public List<TaDossierRcdDTO> getListAvenantAttenteValidationAssureur() {
		return listAvenantAttenteValidationAssureur;
	}






	public void setListAvenantAttenteValidationAssureur(List<TaDossierRcdDTO> listAvenantAttenteValidationAssureur) {
		this.listAvenantAttenteValidationAssureur = listAvenantAttenteValidationAssureur;
	}






	public Integer getNbAvenantAttenteValidationAssureur() {
		return nbAvenantAttenteValidationAssureur;
	}






	public void setNbAvenantAttenteValidationAssureur(Integer nbAvenantAttenteValidationAssureur) {
		this.nbAvenantAttenteValidationAssureur = nbAvenantAttenteValidationAssureur;
	}






	public List<TaDossierRcdDTO> getListAvenantAttenteValidationYlyade() {
		return listAvenantAttenteValidationYlyade;
	}






	public void setListAvenantAttenteValidationYlyade(List<TaDossierRcdDTO> listAvenantAttenteValidationYlyade) {
		this.listAvenantAttenteValidationYlyade = listAvenantAttenteValidationYlyade;
	}






	public Integer getNbAvenantAttenteValidationYlyade() {
		return nbAvenantAttenteValidationYlyade;
	}






	public void setNbAvenantAttenteValidationYlyade(Integer nbAvenantAttenteValidationYlyade) {
		this.nbAvenantAttenteValidationYlyade = nbAvenantAttenteValidationYlyade;
	}






	public List<TaDossierRcdDTO> getListAvenantRefuse() {
		return listAvenantRefuse;
	}






	public void setListAvenantRefuse(List<TaDossierRcdDTO> listAvenantRefuse) {
		this.listAvenantRefuse = listAvenantRefuse;
	}






	public Integer getNbAvenantRefuse() {
		return nbAvenantRefuse;
	}






	public void setNbAvenantRefuse(Integer nbAvenantRefuse) {
		this.nbAvenantRefuse = nbAvenantRefuse;
	}






	public List<TaDossierRcdDTO> getListSoumisEtudeAttenteValidation() {
		return listSoumisEtudeAttenteValidation;
	}






	public void setListSoumisEtudeAttenteValidation(List<TaDossierRcdDTO> listSoumisEtudeAttenteValidation) {
		this.listSoumisEtudeAttenteValidation = listSoumisEtudeAttenteValidation;
	}






	public List<TaDossierRcdDTO> getListSoumisEtudeAttenteValidationAssureur() {
		return listSoumisEtudeAttenteValidationAssureur;
	}






	public void setListSoumisEtudeAttenteValidationAssureur(List<TaDossierRcdDTO> listSoumisEtudeAttenteValidationAssureur) {
		this.listSoumisEtudeAttenteValidationAssureur = listSoumisEtudeAttenteValidationAssureur;
	}






	public List<TaDossierRcdDTO> getListSoumisEtudeValide() {
		return listSoumisEtudeValide;
	}






	public void setListSoumisEtudeValide(List<TaDossierRcdDTO> listSoumisEtudeValide) {
		this.listSoumisEtudeValide = listSoumisEtudeValide;
	}






	public List<TaDossierRcdDTO> getListSoumisEtudeRefuse() {
		return listSoumisEtudeRefuse;
	}






	public void setListSoumisEtudeRefuse(List<TaDossierRcdDTO> listSoumisEtudeRefuse) {
		this.listSoumisEtudeRefuse = listSoumisEtudeRefuse;
	}






	public Integer getNbSoumisEtudeAttenteValidation() {
		return nbSoumisEtudeAttenteValidation;
	}






	public void setNbSoumisEtudeAttenteValidation(Integer nbSoumisEtudeAttenteValidation) {
		this.nbSoumisEtudeAttenteValidation = nbSoumisEtudeAttenteValidation;
	}






	public Integer getNbSoumisEtudeAttenteValidationAssureur() {
		return nbSoumisEtudeAttenteValidationAssureur;
	}






	public void setNbSoumisEtudeAttenteValidationAssureur(Integer nbSoumisEtudeAttenteValidationAssureur) {
		this.nbSoumisEtudeAttenteValidationAssureur = nbSoumisEtudeAttenteValidationAssureur;
	}






	public Integer getNbSoumisEtudeValide() {
		return nbSoumisEtudeValide;
	}






	public void setNbSoumisEtudeValide(Integer nbSoumisEtudeValide) {
		this.nbSoumisEtudeValide = nbSoumisEtudeValide;
	}






	public Integer getNbSoumisEtudeRefuse() {
		return nbSoumisEtudeRefuse;
	}






	public void setNbSoumisEtudeRefuse(Integer nbSoumisEtudeRefuse) {
		this.nbSoumisEtudeRefuse = nbSoumisEtudeRefuse;
	}






	public List<TaDossierRcdDTO> getListSoumisEtudeRefuseDefinitif() {
		return listSoumisEtudeRefuseDefinitif;
	}






	public void setListSoumisEtudeRefuseDefinitif(List<TaDossierRcdDTO> listSoumisEtudeRefuseDefinitif) {
		this.listSoumisEtudeRefuseDefinitif = listSoumisEtudeRefuseDefinitif;
	}






	public Integer getNbSoumisEtudeRefuseDefinitif() {
		return nbSoumisEtudeRefuseDefinitif;
	}






	public void setNbSoumisEtudeRefuseDefinitif(Integer nbSoumisEtudeRefuseDefinitif) {
		this.nbSoumisEtudeRefuseDefinitif = nbSoumisEtudeRefuseDefinitif;
	}






	public List<TaDossierRcdDTO> getListDevisAttenteEnvoiCheque() {
		return listDevisAttenteEnvoiCheque;
	}






	public void setListDevisAttenteEnvoiCheque(List<TaDossierRcdDTO> listDevisAttenteEnvoiCheque) {
		this.listDevisAttenteEnvoiCheque = listDevisAttenteEnvoiCheque;
	}






	public Integer getNbDevisAttenteEnvoiCheque() {
		return nbDevisAttenteEnvoiCheque;
	}






	public void setNbDevisAttenteEnvoiCheque(Integer nbDevisAttenteEnvoiCheque) {
		this.nbDevisAttenteEnvoiCheque = nbDevisAttenteEnvoiCheque;
	}






	public List<TaDossierRcdDTO> getListDevisAttenteEncaissementCheque() {
		return listDevisAttenteEncaissementCheque;
	}






	public void setListDevisAttenteEncaissementCheque(List<TaDossierRcdDTO> listDevisAttenteEncaissementCheque) {
		this.listDevisAttenteEncaissementCheque = listDevisAttenteEncaissementCheque;
	}






	public Integer getNbDevisAttenteEncaissementCheque() {
		return nbDevisAttenteEncaissementCheque;
	}






	public void setNbDevisAttenteEncaissementCheque(Integer nbDevisAttenteEncaissementCheque) {
		this.nbDevisAttenteEncaissementCheque = nbDevisAttenteEncaissementCheque;
	}






	public List<TaDossierRcdDTO> getListDevisAttenteReceptionVirement() {
		return listDevisAttenteReceptionVirement;
	}






	public void setListDevisAttenteReceptionVirement(List<TaDossierRcdDTO> listDevisAttenteReceptionVirement) {
		this.listDevisAttenteReceptionVirement = listDevisAttenteReceptionVirement;
	}






	public Integer getNbDevisAttenteReceptionVirement() {
		return nbDevisAttenteReceptionVirement;
	}






	public void setNbDevisAttenteReceptionVirement(Integer nbDevisAttenteReceptionVirement) {
		this.nbDevisAttenteReceptionVirement = nbDevisAttenteReceptionVirement;
	}






	public List<TaDossierRcdDTO> getListDevisAttenteVirementEffectue() {
		return listDevisAttenteVirementEffectue;
	}






	public void setListDevisAttenteVirementEffectue(List<TaDossierRcdDTO> listDevisAttenteVirementEffectue) {
		this.listDevisAttenteVirementEffectue = listDevisAttenteVirementEffectue;
	}






	public Integer getNbDevisAttenteVirementEffectue() {
		return nbDevisAttenteVirementEffectue;
	}






	public void setNbDevisAttenteVirementEffectue(Integer nbDevisAttenteVirementEffectue) {
		this.nbDevisAttenteVirementEffectue = nbDevisAttenteVirementEffectue;
	}

	public List<TaDossierRcdDTO> getListContratResilieEcheanceCourtier() {
		return listContratResilieEcheanceCourtier;
	}

	public void setListContratResilieEcheanceCourtier(List<TaDossierRcdDTO> listContratResilieEcheanceCourtier) {
		this.listContratResilieEcheanceCourtier = listContratResilieEcheanceCourtier;
	}

	public Integer getNbContratResilieEcheanceCourtier() {
		return nbContratResilieEcheanceCourtier;
	}

	public void setNbContratResilieEcheanceCourtier(Integer nbContratResilieEcheanceCourtier) {
		this.nbContratResilieEcheanceCourtier = nbContratResilieEcheanceCourtier;
	}

	public List<TaDossierRcdDTO> getListContratResilieCessationActiviteCourtier() {
		return listContratResilieCessationActiviteCourtier;
	}

	public void setListContratResilieCessationActiviteCourtier(
			List<TaDossierRcdDTO> listContratResilieCessationActiviteCourtier) {
		this.listContratResilieCessationActiviteCourtier = listContratResilieCessationActiviteCourtier;
	}

	public Integer getNbContratResilieCessationActiviteCourtier() {
		return nbContratResilieCessationActiviteCourtier;
	}

	public void setNbContratResilieCessationActiviteCourtier(Integer nbContratResilieCessationActiviteCourtier) {
		this.nbContratResilieCessationActiviteCourtier = nbContratResilieCessationActiviteCourtier;
	}

	public List<TaDossierRcdDTO> getListContratResilieNonPaiementCourtier() {
		return listContratResilieNonPaiementCourtier;
	}

	public void setListContratResilieNonPaiementCourtier(List<TaDossierRcdDTO> listContratResilieNonPaiementCourtier) {
		this.listContratResilieNonPaiementCourtier = listContratResilieNonPaiementCourtier;
	}

	public Integer getNbContratResilieNonPaiementCourtier() {
		return nbContratResilieNonPaiementCourtier;
	}

	public void setNbContratResilieNonPaiementCourtier(Integer nbContratResilieNonPaiementCourtier) {
		this.nbContratResilieNonPaiementCourtier = nbContratResilieNonPaiementCourtier;
	}

	public List<TaDossierRcdDTO> getListContratResilieSansEffetCourtier() {
		return listContratResilieSansEffetCourtier;
	}

	public void setListContratResilieSansEffetCourtier(List<TaDossierRcdDTO> listContratResilieSansEffetCourtier) {
		this.listContratResilieSansEffetCourtier = listContratResilieSansEffetCourtier;
	}

	public Integer getNbContratResilieSansEffetCourtier() {
		return nbContratResilieSansEffetCourtier;
	}

	public void setNbContratResilieSansEffetCourtier(Integer nbContratResilieSansEffetCourtier) {
		this.nbContratResilieSansEffetCourtier = nbContratResilieSansEffetCourtier;
	}

	public List<TaDossierRcdDTO> getListContratResilieAmiableCourtier() {
		return listContratResilieAmiableCourtier;
	}

	public void setListContratResilieAmiableCourtier(List<TaDossierRcdDTO> listContratResilieAmiableCourtier) {
		this.listContratResilieAmiableCourtier = listContratResilieAmiableCourtier;
	}

	public Integer getNbContratResilieAmiableCourtier() {
		return nbContratResilieAmiableCourtier;
	}

	public void setNbContratResilieAmiableCourtier(Integer nbContratResilieAmiableCourtier) {
		this.nbContratResilieAmiableCourtier = nbContratResilieAmiableCourtier;
	}

	public List<TaDossierRcdDTO> getListContratResilieFausseDeclarationCourtier() {
		return listContratResilieFausseDeclarationCourtier;
	}

	public void setListContratResilieFausseDeclarationCourtier(
			List<TaDossierRcdDTO> listContratResilieFausseDeclarationCourtier) {
		this.listContratResilieFausseDeclarationCourtier = listContratResilieFausseDeclarationCourtier;
	}

	public Integer getNbContratResilieFausseDeclarationCourtier() {
		return nbContratResilieFausseDeclarationCourtier;
	}

	public void setNbContratResilieFausseDeclarationCourtier(Integer nbContratResilieFausseDeclarationCourtier) {
		this.nbContratResilieFausseDeclarationCourtier = nbContratResilieFausseDeclarationCourtier;
	}

	public List<TaDossierRcdDTO> getListContratArrivantTermesCourtier() {
		return listContratArrivantTermesCourtier;
	}

	public void setListContratArrivantTermesCourtier(List<TaDossierRcdDTO> listContratArrivantTermesCourtier) {
		this.listContratArrivantTermesCourtier = listContratArrivantTermesCourtier;
	}

	public Integer getNbContratArrivantTermesCourtier() {
		return nbContratArrivantTermesCourtier;
	}

	public void setNbContratArrivantTermesCourtier(Integer nbContratArrivantTermesCourtier) {
		this.nbContratArrivantTermesCourtier = nbContratArrivantTermesCourtier;
	}

	public List<TaDossierRcdDTO> getListContratTermesDepasseeCourtier() {
		return listContratTermesDepasseeCourtier;
	}

	public void setListContratTermesDepasseeCourtier(List<TaDossierRcdDTO> listContratTermesDepasseeCourtier) {
		this.listContratTermesDepasseeCourtier = listContratTermesDepasseeCourtier;
	}

	public Integer getNbContratTermesDepasseeCourtier() {
		return nbContratTermesDepasseeCourtier;
	}

	public void setNbContratTermesDepasseeCourtier(Integer nbContratTermesDepasseeCourtier) {
		this.nbContratTermesDepasseeCourtier = nbContratTermesDepasseeCourtier;
	}

	public List<TaDossierRcdDTO> getListContratArrivantEcheanceAnnuelleCourtier() {
		return listContratArrivantEcheanceAnnuelleCourtier;
	}

	public void setListContratArrivantEcheanceAnnuelleCourtier(
			List<TaDossierRcdDTO> listContratArrivantEcheanceAnnuelleCourtier) {
		this.listContratArrivantEcheanceAnnuelleCourtier = listContratArrivantEcheanceAnnuelleCourtier;
	}

	public Integer getNbContratArrivantEcheanceAnnuelleCourtier() {
		return nbContratArrivantEcheanceAnnuelleCourtier;
	}

	public void setNbContratArrivantEcheanceAnnuelleCourtier(Integer nbContratArrivantEcheanceAnnuelleCourtier) {
		this.nbContratArrivantEcheanceAnnuelleCourtier = nbContratArrivantEcheanceAnnuelleCourtier;
	}

	public List<TaDossierRcdDTO> getListContratMisEnDemeureCourtier() {
		return listContratMisEnDemeureCourtier;
	}

	public void setListContratMisEnDemeureCourtier(List<TaDossierRcdDTO> listContratMisEnDemeureCourtier) {
		this.listContratMisEnDemeureCourtier = listContratMisEnDemeureCourtier;
	}

	public Integer getNbContratMisEnDemeureCourtier() {
		return nbContratMisEnDemeureCourtier;
	}

	public void setNbContratMisEnDemeureCourtier(Integer nbContratMisEnDemeureCourtier) {
		this.nbContratMisEnDemeureCourtier = nbContratMisEnDemeureCourtier;
	}
	
	public List<TaDossierRcdDTO> getListContratSuspendusNonPaiementCourtier() {
		return listContratSuspendusNonPaiementCourtier;
	}

	public void setListContratSuspendusNonPaiementCourtier(List<TaDossierRcdDTO> listContratSuspendusNonPaiementCourtier) {
		this.listContratSuspendusNonPaiementCourtier = listContratSuspendusNonPaiementCourtier;
	}
	
	public Integer getNbContratSuspendusNonPaiementCourtier() {
		return nbContratSuspendusNonPaiementCourtier;
	}

	public void setNbContratSuspendusNonPaiementCourtier(Integer nbContratSuspendusNonPaiementCourtier) {
		this.nbContratSuspendusNonPaiementCourtier = nbContratSuspendusNonPaiementCourtier;
	}

	public List<TaDossierRcdDTO> getListContratAttentePaiementAttestationNominative() {
		return listContratAttentePaiementAttestationNominative;
	}

	public void setListContratAttentePaiementAttestationNominative(
			List<TaDossierRcdDTO> listContratAttentePaiementAttestationNominative) {
		this.listContratAttentePaiementAttestationNominative = listContratAttentePaiementAttestationNominative;
	}

	public Integer getNbContratAttentePaiementAttestationNominative() {
		return nbContratAttentePaiementAttestationNominative;
	}

	public void setNbContratAttentePaiementAttestationNominative(Integer nbContratAttentePaiementAttestationNominative) {
		this.nbContratAttentePaiementAttestationNominative = nbContratAttentePaiementAttestationNominative;
	}


}
