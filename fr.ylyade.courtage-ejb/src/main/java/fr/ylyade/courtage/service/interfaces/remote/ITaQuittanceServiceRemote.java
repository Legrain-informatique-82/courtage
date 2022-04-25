package fr.ylyade.courtage.service.interfaces.remote;

import java.util.Map;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaQuittanceDTO;
import fr.ylyade.courtage.model.TaQuittance;


@Remote
public interface ITaQuittanceServiceRemote extends IGenericCRUDServiceRemote<TaQuittance,TaQuittanceDTO>,
														IAbstractLgrDAOServer<TaQuittance>,IAbstractLgrDAOServerDTO<TaQuittanceDTO>{
	public static final String validationContext = "QUITTANCE";
	
	public String genereCode( Map<String, String> params);
	public void annuleCode(String code);
	public void verrouilleCode(String code);

}
