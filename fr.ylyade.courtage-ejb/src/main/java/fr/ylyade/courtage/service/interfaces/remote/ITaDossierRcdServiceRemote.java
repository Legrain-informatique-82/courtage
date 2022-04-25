package fr.ylyade.courtage.service.interfaces.remote;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.FinderException;
import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaDossierRcdDTO;
import fr.ylyade.courtage.model.IDonneStatut;
import fr.ylyade.courtage.model.TaActivite;
import fr.ylyade.courtage.model.TaDossierRcd;


@Remote
public interface ITaDossierRcdServiceRemote extends IGenericCRUDServiceRemote<TaDossierRcd,TaDossierRcdDTO>,
														IAbstractLgrDAOServer<TaDossierRcd>,IAbstractLgrDAOServerDTO<TaDossierRcdDTO>{
	public static final String validationContext = "DOSSIER_RCD";
	
	public String generePDF(int idDossierRcd);
	public String generePDF(int idDossierRcd, String modeleEdition);

	public String genereCode( Map<String, String> params);
	public void annuleCode(String code);
	public void verrouilleCode(String code);
	
	public IDonneStatut donneStatut(IDonneStatut dossier) throws FinderException;
	public TaDossierRcd genereAvenant(TaDossierRcd contrat);
	
	public List<TaDossierRcdDTO> findByCodeLight(String code);
	public List<TaDossierRcdDTO> findAllLight();
	public List<TaDossierRcdDTO> findAllByIdCourtier(int idCourtier);
	public List<TaDossierRcdDTO> findAllByIdAssure(int idAssure);
	
	public List<TaDossierRcdDTO> findRefuseAssureur();
	public List<TaDossierRcdDTO> findRefuseAssureurByIdCourtier(int idCourtier);
	public List<TaDossierRcdDTO> findAllPieceManquanteOuRefuseByIdCourtier(int idCourtier);
	public List<TaDossierRcdDTO> findAllAValiderYlyade();
	public List<TaDossierRcdDTO> findAllAValiderEnAttentePaiementYlyade();
	public List<TaDossierRcdDTO> findAllValidationYlyade();
	public List<TaDossierRcdDTO> findAllAttenteValidationYlyadeByIdCourtier(int idCourtier);
	public List<TaDossierRcdDTO>  findAllAttenteValidationAssureurByIdCourtier(int idCourtier);
	
	public List<TaDossierRcdDTO> findAllAvecPiecesInvalides();
	
	
	public List<TaDossierRcdDTO> findAllAvenantPieceManquanteOuRefuseByIdCourtier(int idCourtier);
	public List<TaDossierRcdDTO> findAllAvenantEnAttentePaiementByIdCourtier(int idCourtier);
	public List<TaDossierRcdDTO>  findAllAvenantAttenteValidationAssureurByIdCourtier(int idCourtier);
	public List<TaDossierRcdDTO>  findAllAvenantAttenteValidationYlyadeByIdCourtier(int idCourtier);
	public List<TaDossierRcdDTO> findAvenantRefuseAssureurByIdCourtier(int idCourtier);
	public List<TaDossierRcdDTO> findAvenantRefuseAssureur();
	public List<TaDossierRcdDTO> findAllAvenantEnAttentePaiementYlyade();
	public List<TaDossierRcdDTO> findAllAvenantValidationYlyade();
	public List<TaDossierRcdDTO> findAllAvenantEnAttenteValidationYlyade();
	
/////SOUMIS ETUDE
	//courtier
	public List<TaDossierRcdDTO> findAllSoumisEtudeAttenteValidationYlyadeByIdCourtier(int idCourtier);
	public List<TaDossierRcdDTO> findAllSoumisEtudeAttenteValidationAssureurByIdCourtier(int idCourtier);
	public List<TaDossierRcdDTO> findAllSoumisEtudeValideByIdCourtier(int idCourtier);
	public List<TaDossierRcdDTO> findAllSoumisEtudeRefuseByIdCourtier(int idCourtier);
	public List<TaDossierRcdDTO> findAllSoumisEtudeRefuseDefinitifByIdCourtier(int idCourtier);
	//ylyade
	public List<TaDossierRcdDTO> findAllSoumisEtudeAttenteValidationYlyade();
	public List<TaDossierRcdDTO> findAllSoumisEtudeAttenteValidationAssureur();
	public List<TaDossierRcdDTO> findAllSoumisEtudeValide();
	public List<TaDossierRcdDTO> findAllSoumisEtudeRefuse();
	public List<TaDossierRcdDTO> findAllSoumisEtudeRefuseDefinitif();
/////////////PAIEMENT
	public List<TaDossierRcdDTO> findAllEnAttentePaiementByIdCourtier(int idCourtier);
	public List<TaDossierRcdDTO> findAllEnAttentePaiementYlyade();
	public List<TaDossierRcdDTO> findAllEnAttenteEnvoiChequeByIdCourtier(int idCourtier);
	public List<TaDossierRcdDTO> findAllEnAttenteEncaissementChequeByIdCourtier(int idCourtier);
	public List<TaDossierRcdDTO> findAllEnAttenteEnvoiCheque();
	public List<TaDossierRcdDTO> findAllEnAttenteReceptionCheque();
	public List<TaDossierRcdDTO> findAllEnAttenteDepotCheque();
	public List<TaDossierRcdDTO> findAllEnAttenteEncaissementCheque();
	public List<TaDossierRcdDTO> findAllEnAttenteReceptionVirement();
	public List<TaDossierRcdDTO> findAllEnAttenteVirementEffectue();
	public List<TaDossierRcdDTO> findAllEnAttenteReceptionVirementByIdCourtier(int idCourtier);
	public List<TaDossierRcdDTO> findAllEnAttenteVirementEffectueByIdCourtier(int idCourtier);
	//////////ESPACE PAIEMENT CONTRATS
	public List<TaDossierRcdDTO> findAllContratArrivantTermes(Date todayPlus30);
	public List<TaDossierRcdDTO> findAllContratArrivantTermesDansExactementXjours(Date todayPlus30);
	public List<TaDossierRcdDTO> findAllContratArrivantTermesAndPasDepasse( Date todayPlus30);
	public List<TaDossierRcdDTO> findAllContratArrivantTermesByCourtierAndPasDepasseByCourtier(int idCourtier, Date todayPlus30);
	public List<TaDossierRcdDTO> findAllContratTermesDepasseDepuisExactementXjours(Date todayMoinsX);
	public List<TaDossierRcdDTO> findAllContratArrivantTermesByCourtier(int idCourtier,Date todayPlus30);
	public List<TaDossierRcdDTO> findAllContratTermesDepassee(Date todayMoins10);
	public List<TaDossierRcdDTO> findAllContratTermesDepasseeByCourtier(int idCourtier, Date todayMoins10);
	public List<TaDossierRcdDTO> findAllContratArrivantEcheanceAnnuelle(Date todayPlus30);
	public List<TaDossierRcdDTO> findAllContratArrivantEcheanceAnnuelleByCourtier(int idCourtier, Date todayPlus30);
	public List<TaDossierRcd> findAllContratMisEnDemeure(Date todayMoins20);
	public List<TaDossierRcd> findAllContratSuspendusNonPaiement(Date todayMoins30);
	public List<TaDossierRcd> findAllContratSuspendusNonPaiement41Jours(String codeTStatut, Date todayMoins41);
	//////////RESILIATION
	public List<TaDossierRcdDTO> findAllContratByStatut(String codeTStatut);
	public List<TaDossierRcdDTO> findAllContratByStatutAndByCourtier(int idCourtier, String codeTStatut);
	
	///////ATTESTATIONS
	public List<TaDossierRcdDTO> findAllContratAttentePaiementAttestationNominativeByCourtier(int idCourtier);
	public List<TaDossierRcdDTO> findAllContratAttentePaiementAttestationNominative();
	public List<TaDossierRcdDTO> findAllContratAttenteValidationAttestationNominative();
	
	public List<TaDossierRcd> findAllContratEnCours();
	
	public List<TaDossierRcdDTO> findContratByCodeLight(String code);
	public List<TaDossierRcdDTO> findAllContratLight();
	public List<TaDossierRcdDTO> findAllContratLightPlusExtraction();
	public List<TaDossierRcdDTO> findAllContratByIdCourtier(int idCourtier);
	public List<TaDossierRcdDTO> findAllContratATraiterByIdCourtier(int idCourtier);
	public List<TaDossierRcdDTO> findAllContratByIdAssure(int idAssure);
	
	public BigDecimal findMontantPrime(List<Integer> listeIdActivite, BigDecimal caPrevisionnel);
	public TaActivite findActiviteDeReference(List<Integer> listeIdActivite, BigDecimal caPrevisionnel);
	
	public List<TaDossierRcdDTO> findAllAvenantByNumPolice(String numDossierPolice);
	
	public List<TaDossierRcdDTO> findAllContratATraiter();
}
