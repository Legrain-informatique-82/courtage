package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTCourtierDTO;
import fr.ylyade.courtage.model.TaTCourtier;


@Remote
public interface ITaTCourtierServiceRemote extends IGenericCRUDServiceRemote<TaTCourtier,TaTCourtierDTO>,
														IAbstractLgrDAOServer<TaTCourtier>,IAbstractLgrDAOServerDTO<TaTCourtierDTO>{
	public static final String validationContext = "T_COURTIER";

}
