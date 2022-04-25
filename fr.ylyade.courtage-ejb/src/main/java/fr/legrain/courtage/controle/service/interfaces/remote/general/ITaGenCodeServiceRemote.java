package fr.legrain.courtage.controle.service.interfaces.remote.general;

import javax.ejb.Remote;

import fr.legrain.controle.dto.TaGenCodeDTO;
import fr.legrain.controle.model.TaGenCode;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;

@Remote
public interface ITaGenCodeServiceRemote extends IGenericCRUDServiceRemote<TaGenCode,TaGenCodeDTO>,
														IAbstractLgrDAOServer<TaGenCode>,IAbstractLgrDAOServerDTO<TaGenCodeDTO>{
	public static final String validationContext = "GEN_CODE";
}
