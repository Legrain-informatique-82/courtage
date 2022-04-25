package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaGedSinistreDTO;
import fr.ylyade.courtage.model.TaGedSinistre;


@Remote
public interface ITaGedSinistreServiceRemote extends IGenericCRUDServiceRemote<TaGedSinistre,TaGedSinistreDTO>,
														IAbstractLgrDAOServer<TaGedSinistre>,IAbstractLgrDAOServerDTO<TaGedSinistreDTO>{
	public static final String validationContext = "GED_SINISTRE";

}
