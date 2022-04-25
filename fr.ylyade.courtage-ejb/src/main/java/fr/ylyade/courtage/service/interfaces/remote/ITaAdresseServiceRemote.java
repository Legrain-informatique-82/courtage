package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaAdresseDTO;
import fr.ylyade.courtage.model.TaAdresse;


@Remote
public interface ITaAdresseServiceRemote extends IGenericCRUDServiceRemote<TaAdresse,TaAdresseDTO>,
														IAbstractLgrDAOServer<TaAdresse>,IAbstractLgrDAOServerDTO<TaAdresseDTO>{
	public static final String validationContext = "ADRESSE";

}
