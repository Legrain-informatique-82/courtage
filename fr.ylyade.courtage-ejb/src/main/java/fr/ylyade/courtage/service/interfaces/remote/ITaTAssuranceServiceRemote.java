package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTAssuranceDTO;
import fr.ylyade.courtage.model.TaTAssurance;


@Remote
public interface ITaTAssuranceServiceRemote extends IGenericCRUDServiceRemote<TaTAssurance,TaTAssuranceDTO>,
														IAbstractLgrDAOServer<TaTAssurance>,IAbstractLgrDAOServerDTO<TaTAssuranceDTO>{
	public static final String validationContext = "T_ASSURANCE";

}
