package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTauxRcproPibDTO;
import fr.ylyade.courtage.model.TaTauxRcproPib;


@Remote
public interface ITaTauxRcproPibServiceRemote extends IGenericCRUDServiceRemote<TaTauxRcproPib,TaTauxRcproPibDTO>,
														IAbstractLgrDAOServer<TaTauxRcproPib>,IAbstractLgrDAOServerDTO<TaTauxRcproPibDTO>{
	public static final String validationContext = "TAUX_RCPRO_PIB";

}
