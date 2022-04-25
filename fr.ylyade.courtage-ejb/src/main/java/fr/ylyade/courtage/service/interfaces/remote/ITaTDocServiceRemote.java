package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTDocDTO;
import fr.ylyade.courtage.model.TaTDoc;


@Remote
public interface ITaTDocServiceRemote extends IGenericCRUDServiceRemote<TaTDoc,TaTDocDTO>,
														IAbstractLgrDAOServer<TaTDoc>,IAbstractLgrDAOServerDTO<TaTDocDTO>{
	public static final String validationContext = "T_DOC";

}
