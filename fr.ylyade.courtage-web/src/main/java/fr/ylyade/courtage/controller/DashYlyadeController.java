package fr.ylyade.courtage.controller;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.FinderException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import fr.ylyade.courtage.app.ConstWeb;
import fr.ylyade.courtage.dto.TaDossierRcdDTO;
import fr.ylyade.courtage.model.TaCourtier;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaEcheance;
import fr.ylyade.courtage.model.TaTStatut;
import fr.ylyade.courtage.service.TimerServiceYlyade;
import fr.ylyade.courtage.service.interfaces.remote.ITaCourtierServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaEcheanceServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaEnvoiMailTestServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITimerServiceYlyade;


@Named
@ViewScoped 
public class DashYlyadeController extends DevisRcdController{

	private static final long serialVersionUID = 6278107399024181110L;
	
	@EJB private ITaEnvoiMailTestServiceRemote envoiMailTestService;
	@EJB private ITaEcheanceServiceRemote echeanceService;
	@EJB private ITimerServiceYlyade timerServiceYlyade;
	@EJB private ITaCourtierServiceRemote courtierService;
	
	private List<TaDossierRcdDTO> listContratATraiter;
	private Integer nbContratATraiter;
	
	private List<TaDossierRcdDTO> listDossierRefuse;
	private Integer nbDossierRefuse;
	private List<TaDossierRcdDTO> listDossierAValider;
	private Integer nbDossierAValider;
	private List<TaDossierRcdDTO> listDossierAValiderEnAttentePaiement;
	private Integer nbDossierAValiderEnAttentePaiement;
	private List<TaDossierRcdDTO> listDossierEnAttenteValidationAssureur;
	private Integer nbDossierEnAttenteValidationAssureur;
	
	
	//Avenants
	private List<TaDossierRcdDTO> listAvenantRefuseAssureur;
	private Integer nbAvenantRefuseAssureur;
	private List<TaDossierRcdDTO> listAvenantEnAttentePaiementYlyade;
	private Integer nbAvenantEnAttentePaiementYlyade;
	private List<TaDossierRcdDTO> listAvenantEnAttenteValidationAssureur;
	private Integer nbAvenantEnAttenteValidationAssureur;
	private List<TaDossierRcdDTO> listAvenantEnAttenteValidationYlyade;
	private Integer nbAvenantEnAttenteValidationYlyade;
	
	///Paiement
	private List<TaDossierRcdDTO> listDevisAttentePaiementYlyade;
	private Integer nbDevisAttentePaiementYlyade;
	private List<TaDossierRcdDTO> listDevisAttenteEnvoiCheque;
	private Integer nbDevisAttenteEnvoiCheque;
	private List<TaDossierRcdDTO> listDevisAttenteReceptionCheque;
	private Integer nbDevisAttenteReceptionCheque;
	private List<TaDossierRcdDTO> listDevisAttenteDepotCheque;
	private Integer nbDevisAttenteDepotCheque;
	private List<TaDossierRcdDTO> listDevisAttenteEncaissementCheque;
	private Integer nbDevisAttenteEncaissementCheque;
	private List<TaDossierRcdDTO> listDevisAttenteVirementEffectue;
	private Integer nbDevisAttenteVirementEffectue;
	private List<TaDossierRcdDTO> listDevisAttenteReceptionVirement;
	private Integer nbDevisAttenteReceptionVirement;
	
	
	//Soumis Etude
	private List<TaDossierRcdDTO> listSoumisEtudeAttenteValidationYlyade;
	private Integer nbSoumisEtudeAttenteValidationYlyade;
	private List<TaDossierRcdDTO> listSoumisEtudeAttenteValidationAssureur;
	private Integer nbSoumisEtudeAttenteValidationAssureur;
	private List<TaDossierRcdDTO> listSoumisEtudeValide;
	private Integer nbSoumisEtudeValide;
	private List<TaDossierRcdDTO> listSoumisEtudeRefuse;
	private Integer nbSoumisEtudeRefuse;
	private List<TaDossierRcdDTO> listSoumisEtudeRefuseDefinitif;
	private Integer nbSoumisEtudeRefuseDefinitif;
	
	//Espace paiement (contrats)
	private List<TaDossierRcdDTO> listContratArrivantTermesYlyade;
	private Integer nbContratArrivantTermesYlyade;
	private List<TaDossierRcdDTO> listContratTermesDepasseeYlyade;
	private Integer nbContratTermesDepasseeYlyade;
	private List<TaDossierRcdDTO> listContratArrivantEcheanceAnnuelleYlyade;
	private Integer nbContratArrivantEcheanceAnnuelleYlyade;
	private List<TaDossierRcdDTO> listContratMisEnDemeureYlyade;
	private Integer nbContratMisEnDemeureYlyade;
	private List<TaDossierRcdDTO> listContratSuspendusNonPaiementYlyade;
	private Integer nbContratSuspendusNonPaiementYlyade;
	
	//Resiliation
	private List<TaDossierRcdDTO> listContratResilieEcheanceYlyade;
	private Integer nbContratResilieEcheanceYlyade;
	private List<TaDossierRcdDTO> listContratResilieCessationActiviteYlyade;
	private Integer nbContratResilieCessationActiviteYlyade;
	private List<TaDossierRcdDTO> listContratResilieNonPaiementYlyade;
	private Integer nbContratResilieNonPaiementYlyade;
	private List<TaDossierRcdDTO> listContratResilieSansEffetYlyade;
	private Integer nbContratResilieSansEffetYlyade;
	private List<TaDossierRcdDTO> listContratResilieAmiableYlyade;
	private Integer nbContratResilieAmiableYlyade;
	private List<TaDossierRcdDTO> listContratResilieFausseDeclarationYlyade;
	private Integer nbContratResilieFausseDeclarationYlyade;
	
	//Attestation
	private List<TaDossierRcdDTO> listContratAttentePaiementAttestationNominativeYlyade;
	private Integer nbContratAttentePaiementAttestationNominativeYlyade;
	private List<TaDossierRcdDTO> listContratAttenteValidationAttestationNominativeYlyade;
	private Integer nbContratAttenteValidationAttestationNominativeYlyade;
	
	private String adresseMailTest = "";
	
			
	@Override
	public void refresh() {
		
		List<TaDossierRcdDTO> listeContratATraiter = taDossierRcdService.findAllContratATraiter();
		
		List<TaDossierRcdDTO> liste = taDossierRcdService.findRefuseAssureur();
		List<TaDossierRcdDTO> listeDossierAValider = taDossierRcdService.findAllAValiderYlyade();
		List<TaDossierRcdDTO> listeDossierAValiderEnAttentePaiement = taDossierRcdService.findAllAValiderEnAttentePaiementYlyade();
		List<TaDossierRcdDTO> listeDossierEnAttenteValidationAssureur = taDossierRcdService.findAllValidationYlyade();
		
		//paiement
		List<TaDossierRcdDTO> listeDevisAttentePaiement = taDossierRcdService.findAllEnAttentePaiementYlyade();
		List<TaDossierRcdDTO> listeDevisAttenteEnvoiCheque = taDossierRcdService.findAllEnAttenteEnvoiCheque();
		List<TaDossierRcdDTO> listeDevisAttenteReceptionCheque = taDossierRcdService.findAllEnAttenteReceptionCheque();
		List<TaDossierRcdDTO> listeDevisAttenteDepotCheque = taDossierRcdService.findAllEnAttenteDepotCheque();
		List<TaDossierRcdDTO> listeDevisAttenteEncaissementCheque = taDossierRcdService.findAllEnAttenteEncaissementCheque();
		List<TaDossierRcdDTO> listeDevisAttenteVirementEffectue = taDossierRcdService.findAllEnAttenteVirementEffectue();
		List<TaDossierRcdDTO> listeDevisAttenteReceptionVirement = taDossierRcdService.findAllEnAttenteReceptionVirement();
		
		
		
		List<TaDossierRcdDTO> listeAvenantRefuseAssureur = taDossierRcdService.findAvenantRefuseAssureur();
		List<TaDossierRcdDTO> listeAvenantEnAttentePaiementYlyade =taDossierRcdService.findAllAvenantEnAttentePaiementYlyade();
		List<TaDossierRcdDTO> listeAvenantEnAttenteValidationAssureur =taDossierRcdService.findAllAvenantValidationYlyade();	
		List<TaDossierRcdDTO> listeAvenantEnAttenteValidationYlyade =  taDossierRcdService.findAllAvenantEnAttenteValidationYlyade();

		
		List<TaDossierRcdDTO> listeSoumisEtudeAttenteValidationYlyade= taDossierRcdService.findAllSoumisEtudeAttenteValidationYlyade();
		List<TaDossierRcdDTO> listeSoumisEtudeAttenteValidationAssureur= taDossierRcdService.findAllSoumisEtudeAttenteValidationAssureur();
		List<TaDossierRcdDTO> listeSoumisEtudeValide= taDossierRcdService.findAllSoumisEtudeValide();
		List<TaDossierRcdDTO> listeSoumisEtudeRefuse = taDossierRcdService.findAllSoumisEtudeRefuse();
		List<TaDossierRcdDTO> listeSoumisEtudeRefuseDefinitif = taDossierRcdService.findAllSoumisEtudeRefuseDefinitif();
		
		//Espace Paiement (contrat)
		Date today = new Date();
		LocalDate localToday= today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localTodayPlus30 = localToday.plusDays(30);
		LocalDate localTodayMoins10 = localToday.minusDays(10);
		Date todayPlus30 = Date.from(localTodayPlus30.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date todayMoins10 = Date.from(localTodayMoins10.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		//List<TaDossierRcdDTO> listeContratArrivantTermesYlyade = taDossierRcdService.findAllContratArrivantTermes(todayPlus30);
		List<TaDossierRcdDTO> listeContratArrivantTermesYlyade = taDossierRcdService.findAllContratArrivantTermesAndPasDepasse(todayPlus30);
		List<TaDossierRcdDTO> listeContratTermesDepasseeYlyade = taDossierRcdService.findAllContratTermesDepassee(todayMoins10);
		List<TaDossierRcdDTO> listeContratArrivantEcheanceAnnuelleYlyade = taDossierRcdService.findAllContratArrivantEcheanceAnnuelle(todayPlus30);
		List<TaDossierRcdDTO> listeContratMisEnDemeureYlyade =  taDossierRcdService.findAllContratByStatut(TaTStatut.miseDemeure);
		List<TaDossierRcdDTO> listeContratSuspendusNonPaiementYlyade = taDossierRcdService.findAllContratByStatut(TaTStatut.suspendu);
	
		
		
		
		//resiliation

		List<TaDossierRcdDTO> listeContratResilieEcheanceYlyade = taDossierRcdService.findAllContratByStatut(TaTStatut.resilieEcheance);
		List<TaDossierRcdDTO> listeContratResilieCessationActiviteYlyade = taDossierRcdService.findAllContratByStatut(TaTStatut.resilieCessationActivite);
		List<TaDossierRcdDTO> listeContratResilieNonPaiementYlyade = taDossierRcdService.findAllContratByStatut(TaTStatut.resilieNonPaiement);
		List<TaDossierRcdDTO> listeContratResilieSansEffetYlyade = taDossierRcdService.findAllContratByStatut(TaTStatut.sansEffet);
		List<TaDossierRcdDTO> listeContratResilieAmiableYlyade = taDossierRcdService.findAllContratByStatut(TaTStatut.resilieAmiable);
		List<TaDossierRcdDTO> listeContratResilieFausseDeclarationYlyade = taDossierRcdService.findAllContratByStatut(TaTStatut.resilieFausseDeclaration);
		

		//attestations
		List<TaDossierRcdDTO> listeContratAttentePaiementAttestationNominativeYlyade = taDossierRcdService.findAllContratAttentePaiementAttestationNominative();
		List<TaDossierRcdDTO> listeContratAttenteValidationAttestationNominativeYlyade = taDossierRcdService.findAllContratAttenteValidationAttestationNominative();
	
		
		
		if(listeContratATraiter != null) {
			listContratATraiter = listeContratATraiter;
			
		}else {
			listContratATraiter = new ArrayList<TaDossierRcdDTO>();
		}

		if(liste != null) {
			listDossierRefuse = liste;
			
		}else {
			listDossierRefuse = new ArrayList<TaDossierRcdDTO>();
		}
		
		if(listeDossierAValider != null) {
			listDossierAValider = listeDossierAValider;
			
		}else {
			listDossierAValider = new ArrayList<TaDossierRcdDTO>();
		}
		
		if(listeDossierAValiderEnAttentePaiement != null) {
			listDossierAValiderEnAttentePaiement = listeDossierAValiderEnAttentePaiement;
			
		}else {
			listDossierAValiderEnAttentePaiement = new ArrayList<TaDossierRcdDTO>();
		}
		
		if(listeDossierEnAttenteValidationAssureur != null) {
			listDossierEnAttenteValidationAssureur = listeDossierEnAttenteValidationAssureur;
			
		}else {
			listDossierEnAttenteValidationAssureur = new ArrayList<TaDossierRcdDTO>();
		}
		//Paiement
		if(listeDevisAttentePaiement != null) {
			listDevisAttentePaiementYlyade = listeDevisAttentePaiement;
		}else {
			listDevisAttentePaiementYlyade= new ArrayList<TaDossierRcdDTO>();
		}
		if(listeDevisAttenteEnvoiCheque != null) {
			listDevisAttenteEnvoiCheque = listeDevisAttenteEnvoiCheque;
			
		}else {
			listDevisAttenteEnvoiCheque = new ArrayList<TaDossierRcdDTO>();
		}
		if(listeDevisAttenteReceptionCheque != null) {
			listDevisAttenteReceptionCheque = listeDevisAttenteReceptionCheque;
			
		}else {
			listDevisAttenteReceptionCheque = new ArrayList<TaDossierRcdDTO>();
		}
		if(listeDevisAttenteDepotCheque != null) {
			listDevisAttenteDepotCheque = listeDevisAttenteDepotCheque;
			
		}else {
			listDevisAttenteDepotCheque = new ArrayList<TaDossierRcdDTO>();
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


		
		//avenant
		if(listeAvenantRefuseAssureur != null) {
			listAvenantRefuseAssureur = listeAvenantRefuseAssureur;
		}else {
			listAvenantRefuseAssureur= new ArrayList<TaDossierRcdDTO>();
		}
		if(listeAvenantEnAttentePaiementYlyade != null) {
			listAvenantEnAttentePaiementYlyade = listeAvenantEnAttentePaiementYlyade;
		}else {
			listAvenantEnAttentePaiementYlyade= new ArrayList<TaDossierRcdDTO>();
		}
		if(listeAvenantEnAttenteValidationAssureur != null) {
			listAvenantEnAttenteValidationAssureur = listeAvenantEnAttenteValidationAssureur;
		}else {
			listAvenantEnAttenteValidationAssureur= new ArrayList<TaDossierRcdDTO>();
		}
		if(listeAvenantEnAttenteValidationYlyade != null) {
			listAvenantEnAttenteValidationYlyade = listeAvenantEnAttenteValidationYlyade;
		}else {
			listAvenantEnAttenteValidationYlyade= new ArrayList<TaDossierRcdDTO>();
		}
		
		//soumis a etude
		if(listeSoumisEtudeAttenteValidationYlyade != null) {
			listSoumisEtudeAttenteValidationYlyade = listeSoumisEtudeAttenteValidationYlyade;
			
		}else {
			listSoumisEtudeAttenteValidationYlyade = new ArrayList<TaDossierRcdDTO>();
		}
		if(listeSoumisEtudeAttenteValidationAssureur != null) {
			listSoumisEtudeAttenteValidationAssureur = listeSoumisEtudeAttenteValidationAssureur;
			
		}else {
			listSoumisEtudeAttenteValidationAssureur = new ArrayList<TaDossierRcdDTO>();
		}
		if(listeSoumisEtudeValide != null) {
			listSoumisEtudeValide = listeSoumisEtudeValide;
			
		}else {
			listSoumisEtudeValide = new ArrayList<TaDossierRcdDTO>();
		}
		if(listeSoumisEtudeRefuse != null) {
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
		if(listeContratArrivantTermesYlyade != null) {
			listContratArrivantTermesYlyade = listeContratArrivantTermesYlyade;
			
		}else {
			listContratArrivantTermesYlyade = new ArrayList<TaDossierRcdDTO>();
		}
		if(listeContratTermesDepasseeYlyade != null) {
			listContratTermesDepasseeYlyade = listeContratTermesDepasseeYlyade;
			
		}else {
			listContratTermesDepasseeYlyade = new ArrayList<TaDossierRcdDTO>();
		}
		
		if(listeContratArrivantEcheanceAnnuelleYlyade != null) {
			listContratArrivantEcheanceAnnuelleYlyade = listeContratArrivantEcheanceAnnuelleYlyade;
			
		}else {
			listContratArrivantEcheanceAnnuelleYlyade = new ArrayList<TaDossierRcdDTO>();
		}
		
		if(listeContratMisEnDemeureYlyade != null) {
			listContratMisEnDemeureYlyade = listeContratMisEnDemeureYlyade;
			
		}else {
			listContratMisEnDemeureYlyade = new ArrayList<TaDossierRcdDTO>();
		}
		if(listeContratSuspendusNonPaiementYlyade != null) {
			listContratSuspendusNonPaiementYlyade = listeContratSuspendusNonPaiementYlyade;
			
		}else {
			listContratSuspendusNonPaiementYlyade = new ArrayList<TaDossierRcdDTO>();
		}
		
		
		//resiliation
		if(listeContratResilieEcheanceYlyade != null) {
			listContratResilieEcheanceYlyade = listeContratResilieEcheanceYlyade;
			
		}else {
			listContratResilieEcheanceYlyade = new ArrayList<TaDossierRcdDTO>();
		}
		
		if(listeContratResilieCessationActiviteYlyade != null) {
			listContratResilieCessationActiviteYlyade = listeContratResilieCessationActiviteYlyade;
			
		}else {
			listContratResilieCessationActiviteYlyade = new ArrayList<TaDossierRcdDTO>();
		}
		if(listeContratResilieNonPaiementYlyade != null) {
			listContratResilieNonPaiementYlyade = listeContratResilieNonPaiementYlyade;
			
		}else {
			listContratResilieNonPaiementYlyade = new ArrayList<TaDossierRcdDTO>();
		}
		if(listeContratResilieSansEffetYlyade != null) {
			listContratResilieSansEffetYlyade = listeContratResilieSansEffetYlyade;
			
		}else {
			listContratResilieSansEffetYlyade = new ArrayList<TaDossierRcdDTO>();
		}
		if(listeContratResilieAmiableYlyade != null) {
			listContratResilieAmiableYlyade = listeContratResilieAmiableYlyade;
			
		}else {
			listContratResilieAmiableYlyade = new ArrayList<TaDossierRcdDTO>();
		}
		if(listeContratResilieFausseDeclarationYlyade != null) {
			listContratResilieFausseDeclarationYlyade = listeContratResilieFausseDeclarationYlyade;
			
		}else {
			listContratResilieFausseDeclarationYlyade = new ArrayList<TaDossierRcdDTO>();
		}
		
		//attestation
		if(listeContratAttentePaiementAttestationNominativeYlyade != null) {
			setListContratAttentePaiementAttestationNominativeYlyade(listeContratAttentePaiementAttestationNominativeYlyade);
			
		}else {
			setListContratAttentePaiementAttestationNominativeYlyade(new ArrayList<TaDossierRcdDTO>());
		}
		
		if(listeContratAttenteValidationAttestationNominativeYlyade != null) {
			setListContratAttenteValidationAttestationNominativeYlyade(listeContratAttenteValidationAttestationNominativeYlyade);
			
		}else {
			setListContratAttenteValidationAttestationNominativeYlyade(new ArrayList<TaDossierRcdDTO>());
		}
		
		
		
		
		nbContratATraiter = listeContratATraiter.size();
		nbDossierAValider = listDossierAValider.size();
		nbDossierAValiderEnAttentePaiement = listDossierAValiderEnAttentePaiement.size();
		nbDossierRefuse = listDossierRefuse.size();
		nbDossierEnAttenteValidationAssureur = listDossierEnAttenteValidationAssureur.size();
		
		//paiement
		nbDevisAttentePaiementYlyade = listDevisAttentePaiementYlyade.size();
		nbDevisAttenteEnvoiCheque = listDevisAttenteEnvoiCheque.size();
		nbDevisAttenteReceptionCheque = listDevisAttenteReceptionCheque.size();
		nbDevisAttenteDepotCheque = listDevisAttenteDepotCheque.size();
		nbDevisAttenteEncaissementCheque = listDevisAttenteEncaissementCheque.size();
		nbDevisAttenteVirementEffectue = listDevisAttenteVirementEffectue.size();
		nbDevisAttenteReceptionVirement = listDevisAttenteReceptionVirement.size();
		 
		
		nbAvenantRefuseAssureur = listAvenantRefuseAssureur.size();
		nbAvenantEnAttentePaiementYlyade = listAvenantEnAttentePaiementYlyade.size();
		nbAvenantEnAttenteValidationAssureur = listAvenantEnAttenteValidationAssureur.size();
		nbAvenantEnAttenteValidationYlyade= listAvenantEnAttenteValidationYlyade.size();
		
		 
		nbSoumisEtudeAttenteValidationYlyade=listSoumisEtudeAttenteValidationYlyade.size();
		nbSoumisEtudeAttenteValidationAssureur = listSoumisEtudeAttenteValidationAssureur.size();
		nbSoumisEtudeValide = listSoumisEtudeValide.size();
		nbSoumisEtudeRefuse = listSoumisEtudeRefuse.size();
		nbSoumisEtudeRefuseDefinitif = listSoumisEtudeRefuseDefinitif.size();
		
		//espace paiement (contrats)
		nbContratArrivantTermesYlyade = listContratArrivantTermesYlyade.size();
		nbContratTermesDepasseeYlyade = listContratTermesDepasseeYlyade.size();
		nbContratArrivantEcheanceAnnuelleYlyade = listContratArrivantEcheanceAnnuelleYlyade.size();
		nbContratMisEnDemeureYlyade =  listContratMisEnDemeureYlyade.size();
		nbContratSuspendusNonPaiementYlyade =  listContratSuspendusNonPaiementYlyade.size();


		
		
		
		//resiliation
		nbContratResilieEcheanceYlyade = listContratResilieEcheanceYlyade.size();
		nbContratResilieCessationActiviteYlyade = listContratResilieCessationActiviteYlyade.size();
	    nbContratResilieNonPaiementYlyade = listContratResilieNonPaiementYlyade.size();
		nbContratResilieSansEffetYlyade = listContratResilieSansEffetYlyade.size();
		nbContratResilieAmiableYlyade = listContratResilieAmiableYlyade.size();
		nbContratResilieFausseDeclarationYlyade = listContratResilieFausseDeclarationYlyade.size();
		
		//attestation
		nbContratAttentePaiementAttestationNominativeYlyade = listContratAttentePaiementAttestationNominativeYlyade.size();
		nbContratAttenteValidationAttestationNominativeYlyade = listContratAttenteValidationAttestationNominativeYlyade.size();
		
		
	}

/////////////////////////////////
	public void actEnvoiMailCourtierBordereaux() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailCourtierBordereaux(adresseMailTest);
		}
	}
	public void actEnvoiMailCreationCourtier() {
		if(adresseMailTest != null) {
			//envoi de mail creationcourtier
			System.out.println(adresseMailTest);
			envoiMailTestService.envoiMailCreationCourtier(adresseMailTest);
		}
	}
	public void actEnvoiMailResiliationEcheance() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailResiliationEcheance(adresseMailTest);
		}
//		TaDossierRcd doss;
//		TaEcheance ech;
//		try {
//			doss = taDossierRcdService.findById(805);
//			ech = echeanceService.findById(468);
//			envoiMailTestService.envoiMailResiliationEcheance(doss.getTaCourtier(), doss, null);
//		} catch (FinderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	public void actEnvoiMailResiliationAmiable() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailResiliationAmiable(adresseMailTest);
		}
	}
	public void actEnvoiMailResiliationFausseDecla() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailResiliationFausseDecla(adresseMailTest);
		}
	}
	
	public void actEnvoiMailResiliationCessation() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailResiliationCessation(adresseMailTest);
		}
	}
	
	public void actEnvoiMailResiliationSansEffetAnnuler() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailResiliationSansEffetAnnuler(adresseMailTest);
		}
	}
	
	public void actEnvoiMailResiliationImpaye() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailResiliationImpaye(adresseMailTest);
		}
//		TaDossierRcd doss;
//		TaEcheance ech;
//		try {
//			doss = taDossierRcdService.findById(805);
//			ech = echeanceService.findById(468);
//			envoiMailTestService.envoiMailResiliationImpaye(doss.getTaCourtier(), doss, ech);
//		} catch (FinderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public void actEnvoiMailBientotATerme() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailBientotATerme(adresseMailTest);
		}
//		List<TaDossierRcdDTO> listeDossier = new ArrayList<TaDossierRcdDTO>();
//		TaDossierRcdDTO doss = null;
//		TaDossierRcdDTO doss2 = null;
//		TaDossierRcdDTO doss3 = null;
//		TaDossierRcdDTO doss4 = null;
//		TaDossierRcdDTO doss5 = null;
//		TaDossierRcdDTO doss6 = null;
//		TaDossierRcdDTO doss7 = null;
//		TaDossierRcdDTO doss8 = null;
//		TaDossierRcdDTO doss9 = null;
//		TaDossierRcdDTO doss10 = null;
//		TaDossierRcdDTO doss11 = null;
//		TaCourtier courtier = null;
//		try {
//			courtier = courtierService.findById(415);
//			doss = taDossierRcdService.findByIdDTO(805);
//			 doss2 = taDossierRcdService.findByIdDTO(806);
//			 doss3 = taDossierRcdService.findByIdDTO(807);
//			 doss4 = taDossierRcdService.findByIdDTO(808);
//			 doss5 = taDossierRcdService.findByIdDTO(809);
//			 doss6 = taDossierRcdService.findByIdDTO(810);
//			 doss7 = taDossierRcdService.findByIdDTO(811);
//			 doss8 = taDossierRcdService.findByIdDTO(812);
//			 doss9 = taDossierRcdService.findByIdDTO(804);
//			 doss10 = taDossierRcdService.findByIdDTO(803);
//			 doss11 = taDossierRcdService.findByIdDTO(802);
//			listeDossier.add(doss);
//			listeDossier.add(doss2); 
//			 listeDossier.add(doss3); 
//			 listeDossier.add(doss4);  
//			 listeDossier.add(doss5); 
//			 listeDossier.add(doss6);  
//			 listeDossier.add(doss7); 
//			 listeDossier.add(doss8);  
//			 listeDossier.add(doss9); 
//			 listeDossier.add(doss10);  
//			 listeDossier.add(doss11); 
//			envoiMailTestService.envoiMailBientotATerme(null,courtier, listeDossier);
//			//envoiMailTestService.envoiMailBientotATerme(null,courtier, null);
//		} catch (FinderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	public void actEnvoiMailMisEnDemeureImpaye() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailMiseEnDemeureImpaye(adresseMailTest);
		}
//		TaDossierRcd doss;
//		TaEcheance ech;
//		try {
//			doss = taDossierRcdService.findById(805);
//			ech = echeanceService.findById(468);
//			envoiMailTestService.envoiMailMiseEnDemeureImpaye(doss.getTaCourtier(), doss, ech);
//		} catch (FinderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	public void actEnvoiMailSuspenduNonPaiement() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailSuspenduNonPaiement(adresseMailTest);
		}
//		TaDossierRcd doss;
//		TaEcheance ech;
//		try {
//			doss = taDossierRcdService.findById(805);
//			ech = echeanceService.findById(468);
//			envoiMailTestService.envoiMailSuspenduNonPaiement(doss.getTaCourtier(), doss, ech);
//		} catch (FinderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	public void actEnvoiMailValidationSuperAssureur() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailValidationSuperAssureur(adresseMailTest);
		}
//		TaDossierRcd doss;
//		try {
//			doss = taDossierRcdService.findById(805);
//			envoiMailTestService.envoiMailValidationSuperAssureur(doss.getTaCourtier(), doss);
//		} catch (FinderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	public void actEnvoiMailValidationSuperAssureurAvenant() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailValidationSuperAssureurAvenant(adresseMailTest);
		}
	}
	public void actEnvoiMailRefusSuperAssureurAvenant() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailRefusSuperAssureurAvenant(adresseMailTest);
		}
	}
	public void actEnvoiMailActivationCourtier() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailActivationCourtier(adresseMailTest);
		}
	}
	public void actEnvoiMailEnregistrementDevis() {
//		if(adresseMailTest != null) {
//			envoiMailTestService.envoiMailEnregistrementDevis(adresseMailTest);
//		}
		TaDossierRcd doss;
		try {
			doss = taDossierRcdService.findById(809);
			envoiMailTestService.envoiMailEnregistrementDevis(doss.getTaCourtier(), doss);
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void actEnvoiMailPaiementComptant() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailPaiementComptant(adresseMailTest);
		}
	}
	public void actEnvoiMailEnregistrementDevisAvenant() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailEnregistrementDevisAvenant(adresseMailTest);
		}
	}
	public void actEnvoiMailUneSemaineApresTerme() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailUneSemaineApresTerme(adresseMailTest);
		}
//		List<TaDossierRcdDTO> listeDossier = new ArrayList<TaDossierRcdDTO>();
//		TaDossierRcdDTO doss = null;
//		TaDossierRcdDTO doss2 = null;
//		TaDossierRcdDTO doss3 = null;
//		TaDossierRcdDTO doss4 = null;
//		TaDossierRcdDTO doss5 = null;
//		TaDossierRcdDTO doss6 = null;
//		TaDossierRcdDTO doss7 = null;
//		TaDossierRcdDTO doss8 = null;
//		TaDossierRcdDTO doss9 = null;
//		TaDossierRcdDTO doss10 = null;
//		TaDossierRcdDTO doss11 = null;
//		TaCourtier courtier = null;
//		try {
//			courtier = courtierService.findById(415);
//			doss = taDossierRcdService.findByIdDTO(805);
//			 doss2 = taDossierRcdService.findByIdDTO(806);
//			 doss3 = taDossierRcdService.findByIdDTO(807);
//			 doss4 = taDossierRcdService.findByIdDTO(808);
//			 doss5 = taDossierRcdService.findByIdDTO(809);
//			 doss6 = taDossierRcdService.findByIdDTO(810);
//			 doss7 = taDossierRcdService.findByIdDTO(811);
//			 doss8 = taDossierRcdService.findByIdDTO(812);
//			 doss9 = taDossierRcdService.findByIdDTO(804);
//			 doss10 = taDossierRcdService.findByIdDTO(803);
//			 doss11 = taDossierRcdService.findByIdDTO(802);
//			listeDossier.add(doss);
//			listeDossier.add(doss2); 
//			 listeDossier.add(doss3); 
//			 listeDossier.add(doss4);  
//			 listeDossier.add(doss5); 
//			 listeDossier.add(doss6);  
//			 listeDossier.add(doss7); 
//			 listeDossier.add(doss8);  
//			 listeDossier.add(doss9); 
//			 listeDossier.add(doss10);  
//			 listeDossier.add(doss11); 
//			envoiMailTestService.envoiMailUneSemaineApresTerme(null,courtier, listeDossier);
//			//envoiMailTestService.envoiMailBientotATerme(null,courtier, null);
//		} catch (FinderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	public void actEnvoiMailChangementPass() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailChangementPass(adresseMailTest);
		}
	}
	public void actEnvoiMailConfirmationChangementPass() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailConfirmationChangementPass(adresseMailTest);
		}
	}
	public void actEnvoiMailCourtierInactif() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailCourtierInactif(adresseMailTest);
		}
	}
	public void actEnvoiMailPiecesInvalide() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailPieceInvalides(adresseMailTest);
		}
	}
	public void actEnvoiMailPiecesInvalideBis() {
		if(adresseMailTest != null) {
			envoiMailTestService.envoiMailPieceInvalidesBis(adresseMailTest);
		}
	}
	
	//test déclenchement CRON
	public void actEnvoiMailContratTermeDepasse1Semaine() {
		timerServiceYlyade.actEnvoiMailContratTermeDepasse1Semaine();
	}
	
	public void actDeclencheTimerEnvoiMailPiecesInvalide() {
		timerServiceYlyade.actEnvoiMailPourPiecesInavlides();
	}
	public void actDeclencheTimerEnvoiMailCourtierInactif() {
		timerServiceYlyade.actEnvoiMailCourtierInactif();
	}
	
	
	
	
	
	
	
	
	public void actImprimerListeContratATraiter(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Liste contrat à traiter", "actImprimer"));
		}
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			sessionMap.put("listeContratATraiter",listContratATraiter  );
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	public void actImprimerListeDossierAValider(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeDossierAValider",listDossierAValider  );
//		new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public void actImprimerListeDossierAttenteValidationAssureur(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeDossierAttenteValidationAssureur", listDossierEnAttenteValidationAssureur);
//		new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public void actImprimerListeDossierRefuseAssureur(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeDossierRefuseAssureur", listDossierRefuse);
//		new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actImprimerListeDevisEnAttentePaiement(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeDevisEnAttentePaiement", listDevisAttentePaiementYlyade);
//		new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actImprimerListeDevisEnAttenteEnvoiCheque(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeDevisEnAttenteEnvoiCheque", listDevisAttenteEnvoiCheque);
//		new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actImprimerListeDevisEnAttenteReceptionCheque(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeDevisEnAttenteReceptionCheque", listDevisAttenteReceptionCheque);
//		new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actImprimerListeDevisEnAttenteDepotCheque(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeDevisEnAttenteDepotCheque", listDevisAttenteDepotCheque);
//		new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actImprimerListeDevisEnAttenteEncaissementCheque(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeDevisEnAttenteEncaissementCheque", listDevisAttenteEncaissementCheque);
//		new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public void actImprimerListeDevisEnAttenteVirement(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeDevisEnAttenteVirement", listDevisAttenteVirementEffectue);
//		new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public void actImprimerListeDevisEnAttenteValidationVirement(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeDevisEnAttenteValidationVirement", listDevisAttenteReceptionVirement);
//		new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public void actImprimerListeAvenantEnAttenteValidationYlyade(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeAvenantEnAttenteValidationYlyade", listAvenantEnAttenteValidationYlyade);
//		new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actImprimerListeAvenantEnAttenteValidationAssureur(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeAvenantEnAttenteValidationAssureur", listAvenantEnAttenteValidationAssureur);
//		new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public void actImprimerListeAvenantEnAttentePaiement(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeAvenantEnAttentePaiement", listAvenantEnAttentePaiementYlyade);
//		new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actImprimerListeAvenantRefuse(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeAvenantRefuse", listAvenantRefuseAssureur);
//		new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actImprimerListeDevisSoumisEtudeAttenteValidationYlyade(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeDevisSoumisEtudeAttenteValidationYlyade", listSoumisEtudeAttenteValidationYlyade);
//		new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public void actImprimerListeDevisSoumisEtudeAttenteValidationCompagnie(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeDevisSoumisEtudeAttenteValidationCompagnie", listSoumisEtudeAttenteValidationAssureur);
//		new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public void actImprimerListeDevisSoumisEtudeValiderYlyade(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeDevisSoumisEtudeValiderYlyade", listSoumisEtudeValide);
//		new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public void actImprimerListeDevisSoumisEtudeRefuseYlyade(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeDevisSoumisEtudeRefuseYlyade", listSoumisEtudeRefuse);
//		new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public void actImprimerListeDevisSoumisEtudeRefuseDefinitifYlyade(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeDevisSoumisEtudeRefuseDefinitifYlyade", listSoumisEtudeRefuseDefinitif);
//		new File(taDossierRcdService.generePDF(taDossierRcd.getIdDossierRcd()));
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public void actImprimerListeDevisContratResilieNonPaiementYlyade(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeContratResilieNonPaiementYlyade", listContratResilieNonPaiementYlyade);
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void actImprimerListeDevisContratResilieEcheanceYlyade(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeContratResilieEcheanceYlyade", listContratResilieEcheanceYlyade);
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void actImprimerListeDevisContratResilieFausseDeclarationYlyade(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeContratResilieFausseDeclarationYlyade", listContratResilieFausseDeclarationYlyade);
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void actImprimerListeDevisContratResilieCessationActiviteYlyade(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeContratResilieCessationActiviteYlyade", listContratResilieCessationActiviteYlyade);
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void actImprimerListeDevisContratResilieSansEffetYlyade(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeContratResilieSansEffetYlyade", listContratResilieSansEffetYlyade);
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void actImprimerListeDevisContratResilieAmiableYlyade(ActionEvent actionEvent){
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeContratResilieAmiableYlyade", listContratResilieAmiableYlyade);
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actImprimerListeDevisContratArrivantTermesYlyade(ActionEvent actionEvent){
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeContratArrivantTermesYlyade", listContratArrivantTermesYlyade);
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void actImprimerListeDevisContratTermesDepasseeYlyade(ActionEvent actionEvent){
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeContratTermesDepasseeYlyade", listContratTermesDepasseeYlyade);
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void actImprimerListeDevisContratArrivantEcheanceAnnuelleYlyade(ActionEvent actionEvent){
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeContratArrivantEcheanceAnnuelleYlyade", listContratArrivantEcheanceAnnuelleYlyade);
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void actImprimerListeDevisContratMisEnDemeureYlyade(ActionEvent actionEvent){
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeContratMisEnDemeureYlyade", listContratMisEnDemeureYlyade);
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void actImprimerListeDevisContratSuspendusNonPaiementYlyade(ActionEvent actionEvent){
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeContratSuspendusNonPaiementYlyade", listContratSuspendusNonPaiementYlyade);
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void actImprimerListeDevisContratAttentePaiementAttestationNominativeYlyade(ActionEvent actionEvent){
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeContratAttentePaiementAttestationNominativeYlyade", listContratAttentePaiementAttestationNominativeYlyade);
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actImprimerListeContratValidationPaiementAttestationNominativeYlyade(ActionEvent actionEvent){
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
					
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		
		sessionMap.put("listeContratAttenteValidationAttestationNominativeYlyade", listContratAttenteValidationAttestationNominativeYlyade);
		
			System.out.println("DashYlyadeController.actImprimer()");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
////////////////////////////////////////////////	
	

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






	public List<TaDossierRcdDTO> getListDossierAValider() {
		return listDossierAValider;
	}






	public void setListDossierAValider(List<TaDossierRcdDTO> listDossierAValider) {
		this.listDossierAValider = listDossierAValider;
	}






	public Integer getNbDossierAValider() {
		return nbDossierAValider;
	}






	public void setNbDossierAValider(Integer nbDossierAValider) {
		this.nbDossierAValider = nbDossierAValider;
	}






	public List<TaDossierRcdDTO> getListDossierEnAttenteValidationAssureur() {
		return listDossierEnAttenteValidationAssureur;
	}






	public void setListDossierEnAttenteValidationAssureur(List<TaDossierRcdDTO> listDossierEnAttenteValidationAssureur) {
		this.listDossierEnAttenteValidationAssureur = listDossierEnAttenteValidationAssureur;
	}






	public Integer getNbDossierEnAttenteValidationAssureur() {
		return nbDossierEnAttenteValidationAssureur;
	}






	public void setNbDossierEnAttenteValidationAssureur(Integer nbDossierEnAttenteValidationAssureur) {
		this.nbDossierEnAttenteValidationAssureur = nbDossierEnAttenteValidationAssureur;
	}






	public List<TaDossierRcdDTO> getListDevisAttentePaiementYlyade() {
		return listDevisAttentePaiementYlyade;
	}






	public void setListDevisAttentePaiementYlyade(List<TaDossierRcdDTO> listDevisAttentePaiementYlyade) {
		this.listDevisAttentePaiementYlyade = listDevisAttentePaiementYlyade;
	}






	public Integer getNbDevisAttentePaiementYlyade() {
		return nbDevisAttentePaiementYlyade;
	}






	public void setNbDevisAttentePaiementYlyade(Integer nbDevisAttentePaiementYlyade) {
		this.nbDevisAttentePaiementYlyade = nbDevisAttentePaiementYlyade;
	}






	public List<TaDossierRcdDTO> getListAvenantRefuseAssureur() {
		return listAvenantRefuseAssureur;
	}






	public void setListAvenantRefuseAssureur(List<TaDossierRcdDTO> listAvenantRefuseAssureur) {
		this.listAvenantRefuseAssureur = listAvenantRefuseAssureur;
	}






	public Integer getNbAvenantRefuseAssureur() {
		return nbAvenantRefuseAssureur;
	}






	public void setNbAvenantRefuseAssureur(Integer nbAvenantRefuseAssureur) {
		this.nbAvenantRefuseAssureur = nbAvenantRefuseAssureur;
	}






	public List<TaDossierRcdDTO> getListAvenantEnAttentePaiementYlyade() {
		return listAvenantEnAttentePaiementYlyade;
	}






	public void setListAvenantEnAttentePaiementYlyade(List<TaDossierRcdDTO> listAvenantEnAttentePaiementYlyade) {
		this.listAvenantEnAttentePaiementYlyade = listAvenantEnAttentePaiementYlyade;
	}



	public List<TaDossierRcdDTO> getListAvenantEnAttenteValidationAssureur() {
		return listAvenantEnAttenteValidationAssureur;
	}






	public void setListAvenantEnAttenteValidationAssureur(List<TaDossierRcdDTO> listAvenantEnAttenteValidationAssureur) {
		this.listAvenantEnAttenteValidationAssureur = listAvenantEnAttenteValidationAssureur;
	}






	public Integer getNbAvenantEnAttenteValidationAssureur() {
		return nbAvenantEnAttenteValidationAssureur;
	}






	public void setNbAvenantEnAttenteValidationAssureur(Integer nbAvenantEnAttenteValidationAssureur) {
		this.nbAvenantEnAttenteValidationAssureur = nbAvenantEnAttenteValidationAssureur;
	}






	public Integer getNbAvenantEnAttentePaiementYlyade() {
		return nbAvenantEnAttentePaiementYlyade;
	}






	public void setNbAvenantEnAttentePaiementYlyade(Integer nbAvenantEnAttentePaiementYlyade) {
		this.nbAvenantEnAttentePaiementYlyade = nbAvenantEnAttentePaiementYlyade;
	}






	public List<TaDossierRcdDTO> getListSoumisEtudeAttenteValidationYlyade() {
		return listSoumisEtudeAttenteValidationYlyade;
	}






	public void setListSoumisEtudeAttenteValidationYlyade(List<TaDossierRcdDTO> listSoumisEtudeAttenteValidationYlyade) {
		this.listSoumisEtudeAttenteValidationYlyade = listSoumisEtudeAttenteValidationYlyade;
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






	public Integer getNbSoumisEtudeAttenteValidationYlyade() {
		return nbSoumisEtudeAttenteValidationYlyade;
	}






	public void setNbSoumisEtudeAttenteValidationYlyade(Integer nbSoumisEtudeAttenteValidationYlyade) {
		this.nbSoumisEtudeAttenteValidationYlyade = nbSoumisEtudeAttenteValidationYlyade;
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






	public List<TaDossierRcdDTO> getListAvenantEnAttenteValidationYlyade() {
		return listAvenantEnAttenteValidationYlyade;
	}






	public void setListAvenantEnAttenteValidationYlyade(List<TaDossierRcdDTO> listAvenantEnAttenteValidationYlyade) {
		this.listAvenantEnAttenteValidationYlyade = listAvenantEnAttenteValidationYlyade;
	}






	public Integer getNbAvenantEnAttenteValidationYlyade() {
		return nbAvenantEnAttenteValidationYlyade;
	}






	public void setNbAvenantEnAttenteValidationYlyade(Integer nbAvenantEnAttenteValidationYlyade) {
		this.nbAvenantEnAttenteValidationYlyade = nbAvenantEnAttenteValidationYlyade;
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

	public List<TaDossierRcdDTO> getListDevisAttenteReceptionCheque() {
		return listDevisAttenteReceptionCheque;
	}

	public void setListDevisAttenteReceptionCheque(List<TaDossierRcdDTO> listDevisAttenteReceptionCheque) {
		this.listDevisAttenteReceptionCheque = listDevisAttenteReceptionCheque;
	}

	public Integer getNbDevisAttenteReceptionCheque() {
		return nbDevisAttenteReceptionCheque;
	}

	public void setNbDevisAttenteReceptionCheque(Integer nbDevisAttenteReceptionCheque) {
		this.nbDevisAttenteReceptionCheque = nbDevisAttenteReceptionCheque;
	}

	public List<TaDossierRcdDTO> getListDevisAttenteDepotCheque() {
		return listDevisAttenteDepotCheque;
	}

	public void setListDevisAttenteDepotCheque(List<TaDossierRcdDTO> listDevisAttenteDepotCheque) {
		this.listDevisAttenteDepotCheque = listDevisAttenteDepotCheque;
	}

	public Integer getNbDevisAttenteDepotCheque() {
		return nbDevisAttenteDepotCheque;
	}

	public void setNbDevisAttenteDepotCheque(Integer nbDevisAttenteDepotCheque) {
		this.nbDevisAttenteDepotCheque = nbDevisAttenteDepotCheque;
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

	public List<TaDossierRcdDTO> getListContratResilieEcheanceYlyade() {
		return listContratResilieEcheanceYlyade;
	}

	public void setListContratResilieEcheanceYlyade(List<TaDossierRcdDTO> listContratResilieEcheanceYlyade) {
		this.listContratResilieEcheanceYlyade = listContratResilieEcheanceYlyade;
	}

	public Integer getNbContratResilieEcheanceYlyade() {
		return nbContratResilieEcheanceYlyade;
	}

	public void setNbContratResilieEcheanceYlyade(Integer nbContratResilieEcheanceYlyade) {
		this.nbContratResilieEcheanceYlyade = nbContratResilieEcheanceYlyade;
	}

	public List<TaDossierRcdDTO> getListContratResilieCessationActiviteYlyade() {
		return listContratResilieCessationActiviteYlyade;
	}

	public void setListContratResilieCessationActiviteYlyade(
			List<TaDossierRcdDTO> listContratResilieCessationActiviteYlyade) {
		this.listContratResilieCessationActiviteYlyade = listContratResilieCessationActiviteYlyade;
	}

	public Integer getNbContratResilieCessationActiviteYlyade() {
		return nbContratResilieCessationActiviteYlyade;
	}

	public void setNbContratResilieCessationActiviteYlyade(Integer nbContratResilieCessationActiviteYlyade) {
		this.nbContratResilieCessationActiviteYlyade = nbContratResilieCessationActiviteYlyade;
	}

	public List<TaDossierRcdDTO> getListContratResilieNonPaiementYlyade() {
		return listContratResilieNonPaiementYlyade;
	}

	public void setListContratResilieNonPaiementYlyade(List<TaDossierRcdDTO> listContratResilieNonPaiementYlyade) {
		this.listContratResilieNonPaiementYlyade = listContratResilieNonPaiementYlyade;
	}

	public Integer getNbContratResilieNonPaiementYlyade() {
		return nbContratResilieNonPaiementYlyade;
	}

	public void setNbContratResilieNonPaiementYlyade(Integer nbContratResilieNonPaiementYlyade) {
		this.nbContratResilieNonPaiementYlyade = nbContratResilieNonPaiementYlyade;
	}

	public List<TaDossierRcdDTO> getListContratResilieSansEffetYlyade() {
		return listContratResilieSansEffetYlyade;
	}

	public void setListContratResilieSansEffetYlyade(List<TaDossierRcdDTO> listContratResilieSansEffetYlyade) {
		this.listContratResilieSansEffetYlyade = listContratResilieSansEffetYlyade;
	}

	public Integer getNbContratResilieSansEffetYlyade() {
		return nbContratResilieSansEffetYlyade;
	}

	public void setNbContratResilieSansEffetYlyade(Integer nbContratResilieSansEffetYlyade) {
		this.nbContratResilieSansEffetYlyade = nbContratResilieSansEffetYlyade;
	}

	public List<TaDossierRcdDTO> getListContratResilieAmiableYlyade() {
		return listContratResilieAmiableYlyade;
	}

	public void setListContratResilieAmiableYlyade(List<TaDossierRcdDTO> listContratResilieAmiableYlyade) {
		this.listContratResilieAmiableYlyade = listContratResilieAmiableYlyade;
	}

	public Integer getNbContratResilieAmiableYlyade() {
		return nbContratResilieAmiableYlyade;
	}

	public void setNbContratResilieAmiableYlyade(Integer nbContratResilieAmiableYlyade) {
		this.nbContratResilieAmiableYlyade = nbContratResilieAmiableYlyade;
	}

	public List<TaDossierRcdDTO> getListContratResilieFausseDeclarationYlyade() {
		return listContratResilieFausseDeclarationYlyade;
	}

	public void setListContratResilieFausseDeclarationYlyade(
			List<TaDossierRcdDTO> listContratResilieFausseDeclarationYlyade) {
		this.listContratResilieFausseDeclarationYlyade = listContratResilieFausseDeclarationYlyade;
	}

	public Integer getNbContratResilieFausseDeclarationYlyade() {
		return nbContratResilieFausseDeclarationYlyade;
	}

	public void setNbContratResilieFausseDeclarationYlyade(Integer nbContratResilieFausseDeclarationYlyade) {
		this.nbContratResilieFausseDeclarationYlyade = nbContratResilieFausseDeclarationYlyade;
	}

	public List<TaDossierRcdDTO> getListContratArrivantTermesYlyade() {
		return listContratArrivantTermesYlyade;
	}

	public void setListContratArrivantTermesYlyade(List<TaDossierRcdDTO> listContratArrivantTermesYlyade) {
		this.listContratArrivantTermesYlyade = listContratArrivantTermesYlyade;
	}

	public Integer getNbContratArrivantTermesYlyade() {
		return nbContratArrivantTermesYlyade;
	}

	public void setNbContratArrivantTermesYlyade(Integer nbContratArrivantTermesYlyade) {
		this.nbContratArrivantTermesYlyade = nbContratArrivantTermesYlyade;
	}

	public List<TaDossierRcdDTO> getListContratTermesDepasseeYlyade() {
		return listContratTermesDepasseeYlyade;
	}

	public void setListContratTermesDepasseeYlyade(List<TaDossierRcdDTO> listContratTermesDepasseeYlyade) {
		this.listContratTermesDepasseeYlyade = listContratTermesDepasseeYlyade;
	}

	public Integer getNbContratTermesDepasseeYlyade() {
		return nbContratTermesDepasseeYlyade;
	}

	public void setNbContratTermesDepasseeYlyade(Integer nbContratTermesDepasseeYlyade) {
		this.nbContratTermesDepasseeYlyade = nbContratTermesDepasseeYlyade;
	}

	public List<TaDossierRcdDTO> getListContratArrivantEcheanceAnnuelleYlyade() {
		return listContratArrivantEcheanceAnnuelleYlyade;
	}

	public void setListContratArrivantEcheanceAnnuelleYlyade(List<TaDossierRcdDTO> listContratArrivantEcheanceAnnuelleYlyade) {
		this.listContratArrivantEcheanceAnnuelleYlyade = listContratArrivantEcheanceAnnuelleYlyade;
	}

	public Integer getNbContratArrivantEcheanceAnnuelleYlyade() {
		return nbContratArrivantEcheanceAnnuelleYlyade;
	}

	public void setNbContratArrivantEcheanceAnnuelleYlyade(Integer nbContratArrivantEcheanceAnnuelleYlyade) {
		this.nbContratArrivantEcheanceAnnuelleYlyade = nbContratArrivantEcheanceAnnuelleYlyade;
	}

	public List<TaDossierRcdDTO> getListContratMisEnDemeureYlyade() {
		return listContratMisEnDemeureYlyade;
	}

	public void setListContratMisEnDemeureYlyade(List<TaDossierRcdDTO> listContratMisEnDemeureYlyade) {
		this.listContratMisEnDemeureYlyade = listContratMisEnDemeureYlyade;
	}

	public Integer getNbContratMisEnDemeureYlyade() {
		return nbContratMisEnDemeureYlyade;
	}

	public void setNbContratMisEnDemeureYlyade(Integer nbContratMisEnDemeureYlyade) {
		this.nbContratMisEnDemeureYlyade = nbContratMisEnDemeureYlyade;
	}

	public List<TaDossierRcdDTO> getListContratSuspendusNonPaiementYlyade() {
		return listContratSuspendusNonPaiementYlyade;
	}

	public void setListContratSuspendusNonPaiementYlyade(List<TaDossierRcdDTO> listContratSuspendusNonPaiementYlyade) {
		this.listContratSuspendusNonPaiementYlyade = listContratSuspendusNonPaiementYlyade;
	}

	public Integer getNbContratSuspendusNonPaiementYlyade() {
		return nbContratSuspendusNonPaiementYlyade;
	}

	public void setNbContratSuspendusNonPaiementYlyade(Integer nbContratSuspendusNonPaiementYlyade) {
		this.nbContratSuspendusNonPaiementYlyade = nbContratSuspendusNonPaiementYlyade;
	}

	public List<TaDossierRcdDTO> getListContratAttentePaiementAttestationNominativeYlyade() {
		return listContratAttentePaiementAttestationNominativeYlyade;
	}

	public void setListContratAttentePaiementAttestationNominativeYlyade(
			List<TaDossierRcdDTO> listContratAttentePaiementAttestationNominativeYlyade) {
		this.listContratAttentePaiementAttestationNominativeYlyade = listContratAttentePaiementAttestationNominativeYlyade;
	}

	public Integer getNbContratAttentePaiementAttestationNominativeYlyade() {
		return nbContratAttentePaiementAttestationNominativeYlyade;
	}

	public void setNbContratAttentePaiementAttestationNominativeYlyade(
			Integer nbContratAttentePaiementAttestationNominativeYlyade) {
		this.nbContratAttentePaiementAttestationNominativeYlyade = nbContratAttentePaiementAttestationNominativeYlyade;
	}

	public List<TaDossierRcdDTO> getListContratAttenteValidationAttestationNominativeYlyade() {
		return listContratAttenteValidationAttestationNominativeYlyade;
	}

	public void setListContratAttenteValidationAttestationNominativeYlyade(
			List<TaDossierRcdDTO> listContratAttenteValidationAttestationNominativeYlyade) {
		this.listContratAttenteValidationAttestationNominativeYlyade = listContratAttenteValidationAttestationNominativeYlyade;
	}

	public Integer getNbContratAttenteValidationAttestationNominativeYlyade() {
		return nbContratAttenteValidationAttestationNominativeYlyade;
	}

	public void setNbContratAttenteValidationAttestationNominativeYlyade(
			Integer nbContratAttenteValidationAttestationNominativeYlyade) {
		this.nbContratAttenteValidationAttestationNominativeYlyade = nbContratAttenteValidationAttestationNominativeYlyade;
	}

	public String getAdresseMailTest() {
		return adresseMailTest;
	}

	public void setAdresseMailTest(String adresseMailTest) {
		this.adresseMailTest = adresseMailTest;
	}

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

	public List<TaDossierRcdDTO> getListDossierAValiderEnAttentePaiement() {
		return listDossierAValiderEnAttentePaiement;
	}

	public void setListDossierAValiderEnAttentePaiement(List<TaDossierRcdDTO> listDossierAValiderEnAttentePaiement) {
		this.listDossierAValiderEnAttentePaiement = listDossierAValiderEnAttentePaiement;
	}

	public Integer getNbDossierAValiderEnAttentePaiement() {
		return nbDossierAValiderEnAttentePaiement;
	}

	public void setNbDossierAValiderEnAttentePaiement(Integer nbDossierAValiderEnAttentePaiement) {
		this.nbDossierAValiderEnAttentePaiement = nbDossierAValiderEnAttentePaiement;
	}








}
