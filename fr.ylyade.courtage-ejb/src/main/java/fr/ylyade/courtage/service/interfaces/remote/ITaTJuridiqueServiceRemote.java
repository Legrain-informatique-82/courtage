package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTJuridiqueDTO;
import fr.ylyade.courtage.model.TaTJuridique;


@Remote
public interface ITaTJuridiqueServiceRemote extends IGenericCRUDServiceRemote<TaTJuridique,TaTJuridiqueDTO>,
														IAbstractLgrDAOServer<TaTJuridique>,IAbstractLgrDAOServerDTO<TaTJuridiqueDTO>{
	public static final String validationContext = "T_JURIDIQUE";

}
