package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaHistoriquePrestationDTO;
import fr.ylyade.courtage.model.TaHistoriquePrestation;


@Remote
public interface ITaHistoriquePrestationServiceRemote extends IGenericCRUDServiceRemote<TaHistoriquePrestation,TaHistoriquePrestationDTO>,
														IAbstractLgrDAOServer<TaHistoriquePrestation>,IAbstractLgrDAOServerDTO<TaHistoriquePrestationDTO>{
	public static final String validationContext = "HISTORIQUE_PRESTATION";

}
