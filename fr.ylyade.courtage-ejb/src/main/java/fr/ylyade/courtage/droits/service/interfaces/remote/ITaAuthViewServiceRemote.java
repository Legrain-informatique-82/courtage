package fr.ylyade.courtage.droits.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.droits.model.TaAuthView;
import fr.ylyade.courtage.droits.model.dto.TaAuthViewDTO;

@Remote
public interface ITaAuthViewServiceRemote extends IGenericCRUDServiceRemote<TaAuthView,TaAuthViewDTO>,
														IAbstractLgrDAOServer<TaAuthView>,IAbstractLgrDAOServerDTO<TaAuthViewDTO>{
	public static final String validationContext = "AUTH_VIEW";
}
