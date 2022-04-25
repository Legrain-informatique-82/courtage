package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTLotRealiseDTO;
import fr.ylyade.courtage.model.TaTLotRealise;


@Remote
public interface ITaTLotRealiseServiceRemote extends IGenericCRUDServiceRemote<TaTLotRealise,TaTLotRealiseDTO>,
														IAbstractLgrDAOServer<TaTLotRealise>,IAbstractLgrDAOServerDTO<TaTLotRealiseDTO>{
	public static final String validationContext = "T_LOT_REALISE";

}
