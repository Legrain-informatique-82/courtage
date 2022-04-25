package fr.ylyade.courtage.droits.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.droits.model.TaRole;
import fr.ylyade.courtage.droits.model.dto.TaRoleDTO;

@Remote
public interface ITaRoleServiceRemote extends IGenericCRUDServiceRemote<TaRole,TaRoleDTO>,
														IAbstractLgrDAOServer<TaRole>,IAbstractLgrDAOServerDTO<TaRoleDTO>{
	public static final String validationContext = "ROLE";
}
