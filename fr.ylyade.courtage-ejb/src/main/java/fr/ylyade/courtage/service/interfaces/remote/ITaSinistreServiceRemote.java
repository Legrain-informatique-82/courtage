package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaSinistreDTO;
import fr.ylyade.courtage.model.TaSinistre;


@Remote
public interface ITaSinistreServiceRemote extends IGenericCRUDServiceRemote<TaSinistre,TaSinistreDTO>,
														IAbstractLgrDAOServer<TaSinistre>,IAbstractLgrDAOServerDTO<TaSinistreDTO>{
	public static final String validationContext = "SINISTRE";

}
