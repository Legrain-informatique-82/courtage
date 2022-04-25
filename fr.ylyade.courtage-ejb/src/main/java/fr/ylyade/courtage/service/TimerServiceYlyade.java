package fr.ylyade.courtage.service;


import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.FinderException;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import fr.ylyade.courtage.dto.TaCourtierDTO;
import fr.ylyade.courtage.dto.TaDossierRcdDTO;
import fr.ylyade.courtage.dto.TaEcheanceDTO;
import fr.ylyade.courtage.model.IDonneStatut;
import fr.ylyade.courtage.model.TaCourtier;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaEcheance;
import fr.ylyade.courtage.model.TaTStatut;
import fr.ylyade.courtage.service.interfaces.remote.ITaCourtierServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaDossierRcdServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaEcheanceServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaEnvoiMailTestServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaLgrMailjetServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITimerServiceYlyade;









@Stateless
public class TimerServiceYlyade implements ITimerServiceYlyade{

	
	@EJB private ITaDossierRcdServiceRemote taDossierRcdService;
	@EJB private ITaEcheanceServiceRemote taEcheanceServiceRemote;
	@EJB private ITaCourtierServiceRemote taCourtierServiceRemote;
	@EJB private ITaLgrMailjetServiceRemote lgrMailjetService;
	
	@EJB private ITaEnvoiMailTestServiceRemote envoiMailTestService;
	
	
	//Toutes les lundi à 2h
	@Schedule(second="0", minute="0",hour="2", dayOfWeek="Mon", persistent=false)
	public void doWorkEveryWeeks() throws FinderException {
		actEnvoiMailPourPiecesInavlides();
	}
	
	
			
	//Toutes les 1 et 15 de chaque mois (soit environ toutes les deux semaines) à 2 heure du mat
	@Schedule(second="0", minute="0",hour="2", dayOfMonth="1,15", month="*", persistent=false)
	public void doWorkEvery2weeks() throws FinderException {
		actEnvoiMailCourtierInactif();
		
	}
	
	//Toutes les 5 de chaque mois  à 2 heure du mat
	@Schedule(second="0", minute="0",hour="2", dayOfMonth="5", month="*", persistent=false)
	public void doWorkEveryMonth() throws FinderException {
		actEnvoiMailCourtierBordereaux();
		
	}
	
    


	//Toutes les jours à 2h du matin
	@Schedule(second="0", minute="0",hour="2", persistent=false)
	public void doWork() throws FinderException{
		//Mise en demeure de payé et envoi de mail au courtier
		actMiseEnDemeureContrat();
		//envoi de mail au courtier pour chaque contrat qui arrive a terme dans exactement 30 jours
		actEnvoiMailContratArrivantATerme();
		//Envoi de mail au courtier pour chaque contrat qui un terme dépassé depuis exactement 7 jours
		actEnvoiMailContratTermeDepasse1Semaine();
        //suspension des contrats non payés depuis 30 jours
        //et envoi de mail au courtier
        actSuspendNonPaiementContrat();
        //résiliation des contrat suspendu non payés depuis 41 jours
        //et envoi de mail au courtier
        actResilieNonPaiementContratSuspendu();        
    }
		
	//Envoi un mail de notification a tous les courtiers actifs et non suspendus
	//pour leur dire qu'il peuvent accéder au bordereau
	private void actEnvoiMailCourtierBordereaux() {
		List<TaCourtierDTO> listeCourtierDTO = new ArrayList<TaCourtierDTO>();
		listeCourtierDTO = taCourtierServiceRemote.findAllActif();
		
		for (TaCourtierDTO taCourtierDTO : listeCourtierDTO) {
			if(!taCourtierDTO.getSuspendu()) {
				TaCourtier taCourtier;
				try {
					taCourtier = taCourtierServiceRemote.findById(taCourtierDTO.getId());
					//envoi de mail
					envoiMailTestService.envoiMailCourtierBordereaux(taCourtier);
				} catch (FinderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				
			}
		}
		
	}
	
		
		
		
	public void actEnvoiMailContratArrivantATerme() {
		Date today = new Date();
		//Pour tester avec une date today custom
		//Calendar today2 = new GregorianCalendar(2019, 0, 21, 0, 0, 0)
//		Date today3 = today2.getTime();
//		LocalDate localToday= today3.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localToday= today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localTodayPlus30 = localToday.plusDays(30);
		Date todayPlus30 = Date.from(localTodayPlus30.atStartOfDay(ZoneId.systemDefault()).toInstant());
		List<TaDossierRcdDTO> listeContratArrivantATerme = taDossierRcdService.findAllContratArrivantTermesDansExactementXjours(todayPlus30);
		TaCourtier courtier = null;
		Map<String,List<TaDossierRcdDTO>> mapDossierATraiteParCourtier = new HashMap<>();
		//Envoi de mails aux courtiers si contrat arrive a terme ( dans moins de 30jours)
		for (TaDossierRcdDTO contrat : listeContratArrivantATerme) {
			try {
				String codeCourtier = contrat.getCodeCourtier();
				String cle = codeCourtier;
				//trie des contrats par courtiers
				if(!mapDossierATraiteParCourtier.keySet().contains(cle)) {
					mapDossierATraiteParCourtier.put(cle, new ArrayList<>());
				}
				mapDossierATraiteParCourtier.get(cle).add(contrat);
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Erreur dans l'envoi de mail au courtier pour un contrat arrivant à terme.");
			}
			
		}
		//Pour chaque courtier
		for (String codeCourtier : mapDossierATraiteParCourtier.keySet()) {
			//si il y a des echeances à traiter
			if(!mapDossierATraiteParCourtier.get(codeCourtier).isEmpty()) {
				
				try {
					//récupération du courtier
					courtier = taCourtierServiceRemote.findByCode(codeCourtier);
					envoiMailTestService.envoiMailBientotATerme(null, courtier, mapDossierATraiteParCourtier.get(codeCourtier) );
				} catch (FinderException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public void actMiseEnDemeureContrat() {
		Date today = new Date();
		LocalDate localToday= today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localTodayMoins20 = localToday.minusDays(20);
		Date todayMoins20 = Date.from(localTodayMoins20.atStartOfDay(ZoneId.systemDefault()).toInstant());
		List<TaDossierRcd> listeDossierMisEnDemeure = taDossierRcdService.findAllContratMisEnDemeure(todayMoins20);
        
        for (TaDossierRcd dos : listeDossierMisEnDemeure) {
        	boolean dejaMisEnDemeure = false;
        	boolean dejaSuspenduAvenant = false;
        	
        	for (TaTStatut stat : dos.getTaTStatut()) {
				if(stat.getCodeTStatut().equals(TaTStatut.miseDemeure)) {
					 dejaMisEnDemeure = true;
					 
				}
				if(stat.getCodeTStatut().equals(TaTStatut.suspenduAvenant)) {
					dejaSuspenduAvenant = true;
				}
			}
        	
        	if(dejaMisEnDemeure== false && dejaSuspenduAvenant== false && dos.getDateResiliation() == null) { // si le contrat n'est pas deja mis en demeure ou suspendu car avenant ou resilie
        		
	        		dos.setMisEnDemeure(today);
	        
	        	
	        	IDonneStatut Idossier;
				try {
					Idossier = taDossierRcdService.donneStatut(dos);
					dos.getTaTStatut().clear();
		        	for (TaTStatut stat : Idossier.getTaTStatut()) {
		        		dos.getTaTStatut().add(stat);
					}
				} catch (FinderException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	
	        	
	        	TaEcheance taEcheance = taEcheanceServiceRemote.findEcheanceNonPayerDepuis20Jours(dos.getIdDossierRcd(), todayMoins20);
	        	System.out.println("Code echeance : "+taEcheance.getCodeEcheance()+" Date debut : "+taEcheance.getDateDebutEcheance());
	        	taEcheance = taEcheanceServiceRemote.donneFraisMiseEnDemeure(taEcheance);
	        	System.out.println("Liste de frais impayé de cette échéance "+taEcheance.getTaFraisImpaye());
	        	
	        	
	        	
	        	
	        	
	        	try {
	        		dos = taDossierRcdService.merge(dos);
	        		//envoi de mail au courtier
	        		envoiMailTestService.envoiMailMiseEnDemeureImpaye(dos.getTaCourtier(), dos , taEcheance);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("erreur dans le merge sur le dossier (mis en demeure) "+dos.getIdDossierRcd()+" !");
				}
        		
        	}
        	
        	
		}
	}
	
	public void actSuspendNonPaiementContrat() {
		Date today = new Date();		
		LocalDate localToday= today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localTodayMoins30 = localToday.minusDays(30);
		Date todayMoins30 = Date.from(localTodayMoins30.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<TaDossierRcd> listeDossierSuspendusNonPaiement = taDossierRcdService.findAllContratSuspendusNonPaiement(todayMoins30);
        for (TaDossierRcd dos : listeDossierSuspendusNonPaiement) {
        	
        	
        	boolean dejaSuspenduNonPaiement = false;
        	boolean dejaSuspenduAvenant = false;
        	
        	for (TaTStatut stat : dos.getTaTStatut()) {
				if(stat.getCodeTStatut().equals(TaTStatut.miseDemeure)) {
					dejaSuspenduNonPaiement = true;
					 
				}
				if(stat.getCodeTStatut().equals(TaTStatut.suspenduAvenant)) {
					dejaSuspenduAvenant = true;
				}
			}
        	
        	
        	if(dejaSuspenduNonPaiement == false && dejaSuspenduAvenant== false && dos.getDateResiliation() == null) { //Si il est pas deja resilie ou suspendu non paiement/avenant
        		dos.setSuspenduNonPaiement(today);// il se suspend avec la date du jour
        		dos.setContratEnCours(false);
            	IDonneStatut Idossier;
    			try {
    				Idossier = taDossierRcdService.donneStatut(dos);
    				dos.getTaTStatut().clear();
    	        	for (TaTStatut stat : Idossier.getTaTStatut()) {
    	        		dos.getTaTStatut().add(stat);
    				}
    			} catch (FinderException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
            	
            	try {
            		//aller chercher l'échéance non payé en question comme sur mise en demeure
            		TaEcheance echeance = null;
            		echeance = taEcheanceServiceRemote.findEcheanceNonPayerDepuis41Jour(dos.getIdDossierRcd(), todayMoins30);
    	        	System.out.println("Code echeance : "+echeance.getCodeEcheance()+" Date debut : "+echeance.getDateDebutEcheance());
            		dos = taDossierRcdService.merge(dos);
            		envoiMailTestService.envoiMailSuspenduNonPaiement(dos.getTaCourtier(), dos, echeance);
    			} catch (Exception e) {
    				e.printStackTrace();
    				System.out.println("erreur dans le merge sur le dossier(suspendu non paiement) "+dos.getIdDossierRcd()+" !");
    			}
        	}
        	
        	
        	
		}
	}
	public void actResilieNonPaiementContratSuspendu() {
		Date today = new Date();
		LocalDate localToday= today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localTodayMoins41 = localToday.minusDays(41);
		Date todayMoins41 = Date.from(localTodayMoins41.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<TaDossierRcd> listeDossierSuspenduNonPaiementDepuis11Jours = taDossierRcdService.findAllContratSuspendusNonPaiement41Jours(TaTStatut.suspendu, todayMoins41);
        for (TaDossierRcd dos : listeDossierSuspenduNonPaiementDepuis11Jours) {
        	
        	boolean dejaResilieNonPaiement = false;
        	for (TaTStatut stat : dos.getTaTStatut()) {
				if(stat.getCodeTStatut().equals(TaTStatut.resilieNonPaiement)) {
					dejaResilieNonPaiement = true;
				}
			}
        	
        	if(dejaResilieNonPaiement == false && dos.getDateResiliation() == null) { // si il est pas déja résilié non paiement ou résilié pour autre chose
        		

	        	dos.setResilieNonPaiementContrat(today);
	        	dos.setDateResiliation(today);
	        	IDonneStatut Idossier;
				try {
					Idossier = taDossierRcdService.donneStatut(dos);
					dos.getTaTStatut().clear();
		        	for (TaTStatut stat : Idossier.getTaTStatut()) {
		        		dos.getTaTStatut().add(stat);
					}
				} catch (FinderException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	
	        	
	        	TaEcheance taEcheance = taEcheanceServiceRemote.findEcheanceNonPayerDepuis41Jour(dos.getIdDossierRcd(), todayMoins41);
	        	System.out.println("Code echeance : "+taEcheance.getCodeEcheance()+" Date debut : "+taEcheance.getDateDebutEcheance());
	        	taEcheance = taEcheanceServiceRemote.donneFraisResilieNonPaiement(taEcheance);
	        	System.out.println("Liste de frais impayé de cette échéance "+taEcheance.getTaFraisImpaye());
	        	
	        	try {
	        		dos = taDossierRcdService.merge(dos);
	        		//envoi de mail au courtier
	        		envoiMailTestService.envoiMailResiliationImpaye(dos.getTaCourtier(), dos, taEcheance);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("erreur dans le merge sur le dossier (mis en demeure) "+dos.getIdDossierRcd()+" !");
				}
        		
        	}
        	
        	
		}
	}
		
	 public void actEnvoiMailContratTermeDepasse1Semaine() {
		Date today = new Date();
		TaCourtier courtier;
		LocalDate localToday= today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localTodayMoins7 = localToday.minusDays(7);
		Date todayMoins7 = Date.from(localTodayMoins7.atStartOfDay(ZoneId.systemDefault()).toInstant());

		
		List<TaDossierRcdDTO> listeDossierTermeDepasse1Semaine = taDossierRcdService.findAllContratTermesDepasseDepuisExactementXjours(todayMoins7);
		Map<String,List<TaDossierRcdDTO>> mapDossierATraiteParCourtier = new HashMap<>();
		//Envoi de mail pour chaque contrat qui a un terme dépassé depuis exactement 7 jours
		for (TaDossierRcdDTO dos : listeDossierTermeDepasse1Semaine) {
			try {
				String codeCourtier = dos.getCodeCourtier();
				String cle = codeCourtier;
				//trie des contrats par courtiers
				if(!mapDossierATraiteParCourtier.keySet().contains(cle)) {
					mapDossierATraiteParCourtier.put(cle, new ArrayList<>());
				}
				mapDossierATraiteParCourtier.get(cle).add(dos);
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Erreur dans l'envoi de mail au courtier pour un contrat à terme dépassé.");
			}
		}
		//Pour chaque courtier
		for (String codeCourtier : mapDossierATraiteParCourtier.keySet()) {
			//si il y a des echeances à traiter
			if(!mapDossierATraiteParCourtier.get(codeCourtier).isEmpty()) {
				
				try {
					//récupération du courtier
					courtier = taCourtierServiceRemote.findByCode(codeCourtier);
					envoiMailTestService.envoiMailUneSemaineApresTerme(null, courtier, mapDossierATraiteParCourtier.get(codeCourtier) );
				} catch (FinderException e) {
					e.printStackTrace();
				}
				
			}
		}
	 }
	 
	public void actEnvoiMailPourPiecesInavlides() {
			List<TaDossierRcdDTO> listeDossierAvecPiecesInavlides = taDossierRcdService.findAllAvecPiecesInvalides();
			
			for (TaDossierRcdDTO taDossierRcdDTO : listeDossierAvecPiecesInavlides) {
				TaDossierRcd dossier;
				try {
					dossier = taDossierRcdService.findById(taDossierRcdDTO.getId());
					envoiMailTestService.envoiMailPieceInvalidesBis(dossier.getTaCourtier(), dossier);
				} catch (FinderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
	}
	
	public void actEnvoiMailCourtierInactif() {
		List<TaCourtierDTO> listeCourtierDTO = new ArrayList<TaCourtierDTO>();
		listeCourtierDTO = taCourtierServiceRemote.findAllInactif();
		
		for (TaCourtierDTO taCourtierDTO : listeCourtierDTO) {
			if(!taCourtierDTO.getSuspendu()) {
				TaCourtier taCourtier;
				try {
					taCourtier = taCourtierServiceRemote.findById(taCourtierDTO.getId());
					//envoi de mail
					envoiMailTestService.envoiMailCourtierInactif(taCourtier);
				} catch (FinderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}
	}

}
