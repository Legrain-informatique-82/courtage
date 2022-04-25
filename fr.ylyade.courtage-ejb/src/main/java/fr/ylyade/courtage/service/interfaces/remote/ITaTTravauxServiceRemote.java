package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTTravauxDTO;
import fr.ylyade.courtage.model.TaTTravaux;


@Remote
public interface ITaTTravauxServiceRemote extends IGenericCRUDServiceRemote<TaTTravaux,TaTTravauxDTO>,
														IAbstractLgrDAOServer<TaTTravaux>,IAbstractLgrDAOServerDTO<TaTTravauxDTO>{
	public static final String validationContext = "T_TRAVAUX";

}
