package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaContratGfaDTO;
import fr.ylyade.courtage.model.TaContratGfa;


@Remote
public interface ITaContratGfaServiceRemote extends IGenericCRUDServiceRemote<TaContratGfa,TaContratGfaDTO>,
														IAbstractLgrDAOServer<TaContratGfa>,IAbstractLgrDAOServerDTO<TaContratGfaDTO>{
	public static final String validationContext = "CONTRAT_GFA";
	public Integer countActiveByIdCourtier(int idCourtier);

}
