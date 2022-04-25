package fr.ylyade.courtage.droits.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.droits.model.TaAuthURL;
import fr.ylyade.courtage.droits.model.dto.TaAuthURLDTO;



@Remote
public interface ITaAuthURLServiceRemote extends IGenericCRUDServiceRemote<TaAuthURL,TaAuthURLDTO>,
														IAbstractLgrDAOServer<TaAuthURL>,IAbstractLgrDAOServerDTO<TaAuthURLDTO>{
	public static final String validationContext = "AUTH_URL";
}
