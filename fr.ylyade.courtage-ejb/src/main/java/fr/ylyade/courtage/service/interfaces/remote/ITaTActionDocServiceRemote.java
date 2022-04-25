package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTActionDocDTO;
import fr.ylyade.courtage.model.TaTActionDoc;


@Remote
public interface ITaTActionDocServiceRemote extends IGenericCRUDServiceRemote<TaTActionDoc,TaTActionDocDTO>,
														IAbstractLgrDAOServer<TaTActionDoc>,IAbstractLgrDAOServerDTO<TaTActionDocDTO>{
	public static final String validationContext = "T_ACTION_DOC";

}
