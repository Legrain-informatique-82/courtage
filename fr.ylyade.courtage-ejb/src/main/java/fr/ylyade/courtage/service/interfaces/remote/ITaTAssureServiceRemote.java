package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTAssureDTO;
import fr.ylyade.courtage.model.TaTAssure;


@Remote
public interface ITaTAssureServiceRemote extends IGenericCRUDServiceRemote<TaTAssure,TaTAssureDTO>,
														IAbstractLgrDAOServer<TaTAssure>,IAbstractLgrDAOServerDTO<TaTAssureDTO>{
	public static final String validationContext = "T_ASSURE";

}
