package fr.ylyade.courtage.service.interfaces.remote;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTalonComptableDTO;
import fr.ylyade.courtage.model.TaTalonComptable;


@Remote
public interface ITaTalonComptableServiceRemote extends IGenericCRUDServiceRemote<TaTalonComptable,TaTalonComptableDTO>,
														IAbstractLgrDAOServer<TaTalonComptable>,IAbstractLgrDAOServerDTO<TaTalonComptableDTO>{
	public static final String validationContext = "TALON_COMPTABLE";
	
	public String genereCode( Map<String, String> params);
	public void annuleCode(String code);
	public void verrouilleCode(String code);

}
