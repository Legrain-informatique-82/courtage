package fr.ylyade.courtage.droits.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.droits.model.TaClientLegrain;
import fr.ylyade.courtage.droits.model.dto.TaClientLegrainDTO;

@Remote
public interface ITaClientLegrainServiceRemote extends IGenericCRUDServiceRemote<TaClientLegrain,TaClientLegrainDTO>,
														IAbstractLgrDAOServer<TaClientLegrain>,IAbstractLgrDAOServerDTO<TaClientLegrainDTO>{
	public static final String validationContext = "CLIENT_LEGRAIN";
}
