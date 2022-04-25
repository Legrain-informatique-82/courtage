package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaClasseDTO;
import fr.ylyade.courtage.model.TaClasse;


@Remote
public interface ITaClasseServiceRemote extends IGenericCRUDServiceRemote<TaClasse,TaClasseDTO>,
														IAbstractLgrDAOServer<TaClasse>,IAbstractLgrDAOServerDTO<TaClasseDTO>{
	public static final String validationContext = "T_CLASSE";

}
