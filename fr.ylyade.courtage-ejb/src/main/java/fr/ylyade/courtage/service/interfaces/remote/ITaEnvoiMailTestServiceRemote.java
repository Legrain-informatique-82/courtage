package fr.ylyade.courtage.service.interfaces.remote;

import java.io.File;
import java.util.List;
import java.util.Map;

import fr.ylyade.courtage.droits.model.TaUtilisateur;
import fr.ylyade.courtage.dto.TaDossierRcdDTO;
import fr.ylyade.courtage.dto.TaEcheanceDTO;
import fr.ylyade.courtage.model.TaCourtier;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaReglementAssurance;
import fr.ylyade.courtage.model.TaEcheance;

public interface ITaEnvoiMailTestServiceRemote {
	
//	public void send(String fromName, String subject, String textPart, String[] destinataires);
	
	public void envoiMailCourtierBordereaux(String mail);
	public void envoiMailCourtierBordereaux(TaCourtier courtier);
	public void envoiMailCourtierBordereaux(String mail, TaCourtier courtier);
	
	public void envoiMailCreationCourtier(String mail);
	public void envoiMailCreationCourtier(TaCourtier courtier);
	public void envoiMailCreationCourtier(String mail, TaCourtier courtier);
	
	public void envoiMailChangementPass(TaUtilisateur utilisateur);
	public void envoiMailChangementPass(String mail);
	public void envoiMailChangementPass(String mail, TaUtilisateur utilisateur);
	
	public void envoiMailConfirmationChangementPass(TaUtilisateur utilisateur);
	public void envoiMailConfirmationChangementPass(String mail);
	public void envoiMailConfirmationChangementPass(String mail, TaUtilisateur utilisateur);
	
	public void envoiMailResiliationEcheance(String mail);
	public void envoiMailResiliationEcheance(TaCourtier courtier,TaDossierRcd dossier, TaEcheance taEcheance);
	public void envoiMailResiliationEcheance(String mail, TaCourtier courtier,TaDossierRcd dossier, TaEcheance taEcheance);
	
	public void envoiMailResiliationAmiable(String mail);
	public void envoiMailResiliationAmiable(TaCourtier courtier,TaDossierRcd dossier, TaEcheance taEcheance);
	public void envoiMailResiliationAmiable(String mail, TaCourtier courtier,TaDossierRcd dossier, TaEcheance taEcheance);
	
	public void envoiMailResiliationFausseDecla(String mail);
	public void envoiMailResiliationFausseDecla(TaCourtier courtier,TaDossierRcd dossier, TaEcheance taEcheance);
	public void envoiMailResiliationFausseDecla(String mail, TaCourtier courtier,TaDossierRcd dossier, TaEcheance taEcheance);
	
	public void envoiMailResiliationCessation(String mail);
	public void envoiMailResiliationCessation(TaCourtier courtier,TaDossierRcd dossier, TaEcheance taEcheance);
	public void envoiMailResiliationCessation(String mail, TaCourtier courtier,TaDossierRcd dossier, TaEcheance taEcheance);
	
	public void envoiMailResiliationSansEffetAnnuler(String mail);
	public void envoiMailResiliationSansEffetAnnuler(TaCourtier courtier,TaDossierRcd dossier, TaEcheance taEcheance);
	public void envoiMailResiliationSansEffetAnnuler(String mail, TaCourtier courtier,TaDossierRcd dossier, TaEcheance taEcheance);
	
	public void envoiMailResiliationImpaye(String mail);
	public void envoiMailResiliationImpaye(TaCourtier courtier,TaDossierRcd dossier, TaEcheance taEcheance);
	public void envoiMailResiliationImpaye(String mail, TaCourtier courtier,TaDossierRcd dossier, TaEcheance taEcheance);
	
	public void envoiMailBientotATerme(String mail);
	public void envoiMailBientotATerme(TaCourtier courtier, List<TaDossierRcdDTO> listeDossier);
	public void envoiMailBientotATerme(String mail, TaCourtier courtier, List<TaDossierRcdDTO> listeDossier);
	
	public void envoiMailMiseEnDemeureImpaye(String mail);
	public void envoiMailMiseEnDemeureImpaye(TaCourtier courtier, TaDossierRcd dossier, TaEcheance taEcheance);
	public void envoiMailMiseEnDemeureImpaye(String mail, TaCourtier courtier, TaDossierRcd dossier, TaEcheance taEcheance);
	
	public void envoiMailSuspenduNonPaiement(String mail);
	public void envoiMailSuspenduNonPaiement(TaCourtier courtier, TaDossierRcd dossier, TaEcheance taEcheance);
	public void envoiMailSuspenduNonPaiement(String mail, TaCourtier courtier,TaDossierRcd dossier, TaEcheance taEcheance);
	
	public void envoiMailValidationSuperAssureur(String mail);
	public void envoiMailValidationSuperAssureur(TaCourtier courtier, TaDossierRcd dossier);
	public void envoiMailValidationSuperAssureur(String mail, TaCourtier courtier, TaDossierRcd dossier);
	
	public void envoiMailActivationCourtier(String mail);
	public void envoiMailActivationCourtier(TaCourtier courtier);
	public void envoiMailActivationCourtier(String mail, TaCourtier courtier);
	
	public void envoiMailEnregistrementDevis(String mail);
	public void envoiMailEnregistrementDevis(TaCourtier courtier, TaDossierRcd dossier);
	public void envoiMailEnregistrementDevis(String mail, TaCourtier courtier, TaDossierRcd dossier);
	
	public void envoiMailPaiementComptant(String mail);
	public void envoiMailPaiementComptant(TaCourtier courtier,  TaReglementAssurance reglement);
	public void envoiMailPaiementComptant(String mail, TaCourtier courtier, TaReglementAssurance reglement);
	
	public void envoiMailEnregistrementDevisAvenant(String mail);
	public void envoiMailEnregistrementDevisAvenant(TaCourtier courtier, TaDossierRcd dossier);
	public void envoiMailEnregistrementDevisAvenant(String mail, TaCourtier courtier, TaDossierRcd dossier);
	
	public void envoiMailValidationSuperAssureurAvenant(String mail);
	public void envoiMailValidationSuperAssureurAvenant(TaCourtier courtier, TaDossierRcd dossier);
	public void envoiMailValidationSuperAssureurAvenant(String mail, TaCourtier courtier, TaDossierRcd dossier);
	
	public void envoiMailRefusSuperAssureurAvenant(String mail);
	public void envoiMailRefusSuperAssureurAvenant(TaCourtier courtier, TaDossierRcd dossier);
	public void envoiMailRefusSuperAssureurAvenant(String mail, TaCourtier courtier, TaDossierRcd dossier);
	
	public void envoiMailUneSemaineApresTerme(String mail);
	public void envoiMailUneSemaineApresTerme(TaCourtier courtier, List<TaDossierRcdDTO> listeDossier);
	public void envoiMailUneSemaineApresTerme(String mail, TaCourtier courtier, List<TaDossierRcdDTO> listeDossier);
	
	public void envoiMailCourtierInactif(String mail);
	public void envoiMailCourtierInactif(TaCourtier courtier);
	public void envoiMailCourtierInactif(String mail, TaCourtier courtier);
	
	public void envoiMailPieceInvalides(String mail);
	public void envoiMailPieceInvalides(TaCourtier courtier, TaDossierRcd dossier);
	public void envoiMailPieceInvalides(String mail, TaCourtier courtier, TaDossierRcd dossier);
	
	public void envoiMailPieceInvalidesBis(String mail);
	public void envoiMailPieceInvalidesBis(TaCourtier courtier, TaDossierRcd dossier);
	public void envoiMailPieceInvalidesBis(String mail, TaCourtier courtier, TaDossierRcd dossier);

	

}
