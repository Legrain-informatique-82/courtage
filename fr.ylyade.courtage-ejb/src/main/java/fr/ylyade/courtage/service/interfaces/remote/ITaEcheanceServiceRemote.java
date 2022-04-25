package fr.ylyade.courtage.service.interfaces.remote;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaEcheanceDTO;
import fr.ylyade.courtage.model.TaEcheance;


@Remote
public interface ITaEcheanceServiceRemote extends IGenericCRUDServiceRemote<TaEcheance,TaEcheanceDTO>,
														IAbstractLgrDAOServer<TaEcheance>,IAbstractLgrDAOServerDTO<TaEcheanceDTO>{
	public static final String validationContext = "ECHEANCE";
	
	public String genereCode( Map<String, String> params);
	public void annuleCode(String code);
	public void verrouilleCode(String code);

	public List<TaEcheanceDTO> findAllEcheanceRCDDTO();
	public List<TaEcheanceDTO> findAllEcheanceRCDIdDTO(Integer idRCD);
	public TaEcheanceDTO findEcheanceReglementRCDDTO(Integer idEcheanceRcd);
	public List<TaEcheance> findAllEcheanceByIdDossier(Integer idDossierRcd);
	
	public TaEcheance donneFraisMiseEnDemeure(TaEcheance taEcheance);
	public TaEcheance donneFraisResilieNonPaiement(TaEcheance taEcheance);
	public TaEcheance donneFraisResilieSansEffet(TaEcheance taEcheance);
	public TaEcheance donneFraisAvenant(TaEcheance taEcheance);
	public TaEcheance findEcheanceNonPayerDepuis20Jours(Integer idDossierRcd, Date todayMoins20);
	public TaEcheance findEcheanceNonPayerDepuis41Jour(Integer idDossierRcd, Date todayMoins41);
	public TaEcheance findFirstEcheanceNonPayer(Integer idDossierRcd);
	public List<TaEcheanceDTO> findAllEcheancesATermesDansExactementXJoursByIdDossier(Integer idDossierRcd, Date dateX);
}
