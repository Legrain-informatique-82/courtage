package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTFraisImpayeDTO;
import fr.ylyade.courtage.model.TaTFraisImpaye;


@Remote
public interface ITaTFraisImpayeServiceRemote extends IGenericCRUDServiceRemote<TaTFraisImpaye,TaTFraisImpayeDTO>,
														IAbstractLgrDAOServer<TaTFraisImpaye>,IAbstractLgrDAOServerDTO<TaTFraisImpayeDTO>{
	public static final String validationContext = "T_FRAIS_IMPAYE";

}
