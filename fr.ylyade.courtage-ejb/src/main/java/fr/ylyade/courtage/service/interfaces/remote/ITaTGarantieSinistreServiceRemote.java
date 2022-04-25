package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTGarantieSinistreDTO;
import fr.ylyade.courtage.model.TaTGarantieSinistre;


@Remote
public interface ITaTGarantieSinistreServiceRemote extends IGenericCRUDServiceRemote<TaTGarantieSinistre,TaTGarantieSinistreDTO>,
														IAbstractLgrDAOServer<TaTGarantieSinistre>,IAbstractLgrDAOServerDTO<TaTGarantieSinistreDTO>{
	public static final String validationContext = "T_GARANTIE_SINISTRE";

}
