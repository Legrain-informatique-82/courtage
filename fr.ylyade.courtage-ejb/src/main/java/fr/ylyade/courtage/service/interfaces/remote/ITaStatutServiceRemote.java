package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaStatutDTO;
import fr.ylyade.courtage.model.TaStatut;


@Remote
public interface ITaStatutServiceRemote extends IGenericCRUDServiceRemote<TaStatut,TaStatutDTO>,
														IAbstractLgrDAOServer<TaStatut>,IAbstractLgrDAOServerDTO<TaStatutDTO>{
	public static final String validationContext = "STATUT";

}
