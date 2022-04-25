package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaActiviteDTO;
import fr.ylyade.courtage.model.TaActivite;


@Remote
public interface ITaActiviteServiceRemote extends IGenericCRUDServiceRemote<TaActivite,TaActiviteDTO>,
														IAbstractLgrDAOServer<TaActivite>,IAbstractLgrDAOServerDTO<TaActiviteDTO>{
	public static final String validationContext = "ACTIVITE";

}
