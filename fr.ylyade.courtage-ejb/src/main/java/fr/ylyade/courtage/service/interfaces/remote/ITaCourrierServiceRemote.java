package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaCourrierDTO;
import fr.ylyade.courtage.model.TaCourrier;


@Remote
public interface ITaCourrierServiceRemote extends IGenericCRUDServiceRemote<TaCourrier,TaCourrierDTO>,
														IAbstractLgrDAOServer<TaCourrier>,IAbstractLgrDAOServerDTO<TaCourrierDTO>{
	public static final String validationContext = "COURRIER";

}
