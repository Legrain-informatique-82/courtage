package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTRoleDTO;
import fr.ylyade.courtage.model.TaTRole;


@Remote
public interface ITaTRoleServiceRemote extends IGenericCRUDServiceRemote<TaTRole,TaTRoleDTO>,
														IAbstractLgrDAOServer<TaTRole>,IAbstractLgrDAOServerDTO<TaTRoleDTO>{
	public static final String validationContext = "T_ROLE";

}
