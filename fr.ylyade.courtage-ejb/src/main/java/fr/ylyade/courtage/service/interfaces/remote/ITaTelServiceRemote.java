package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTelDTO;
import fr.ylyade.courtage.model.TaTel;


@Remote
public interface ITaTelServiceRemote extends IGenericCRUDServiceRemote<TaTel,TaTelDTO>,
														IAbstractLgrDAOServer<TaTel>,IAbstractLgrDAOServerDTO<TaTelDTO>{
	public static final String validationContext = "TEL";

}
