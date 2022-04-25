package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTEcheanceDTO;
import fr.ylyade.courtage.model.TaTEcheance;


@Remote
public interface ITaTEcheanceServiceRemote extends IGenericCRUDServiceRemote<TaTEcheance,TaTEcheanceDTO>,
														IAbstractLgrDAOServer<TaTEcheance>,IAbstractLgrDAOServerDTO<TaTEcheanceDTO>{
	public static final String validationContext = "T_ECHEANCE";

}
