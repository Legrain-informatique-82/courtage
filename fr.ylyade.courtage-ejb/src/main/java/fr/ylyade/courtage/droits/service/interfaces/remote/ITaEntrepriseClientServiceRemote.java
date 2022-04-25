package fr.ylyade.courtage.droits.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.droits.model.TaEntrepriseClient;
import fr.ylyade.courtage.droits.model.dto.TaEntrepriseClientDTO;

@Remote
public interface ITaEntrepriseClientServiceRemote extends IGenericCRUDServiceRemote<TaEntrepriseClient,TaEntrepriseClientDTO>,
														IAbstractLgrDAOServer<TaEntrepriseClient>,IAbstractLgrDAOServerDTO<TaEntrepriseClientDTO>{
	public static final String validationContext = "ENTREPRISE_CLIENT";
}
