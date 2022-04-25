package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTConstructionDTO;
import fr.ylyade.courtage.model.TaTConstruction;


@Remote
public interface ITaTConstructionServiceRemote extends IGenericCRUDServiceRemote<TaTConstruction,TaTConstructionDTO>,
														IAbstractLgrDAOServer<TaTConstruction>,IAbstractLgrDAOServerDTO<TaTConstructionDTO>{
	public static final String validationContext = "T_CONSTRUCTION";

}
