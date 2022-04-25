package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTAdresseDTO;
import fr.ylyade.courtage.model.TaTAdresse;


@Remote
public interface ITaTAdresseServiceRemote extends IGenericCRUDServiceRemote<TaTAdresse,TaTAdresseDTO>,
														IAbstractLgrDAOServer<TaTAdresse>,IAbstractLgrDAOServerDTO<TaTAdresseDTO>{
	public static final String validationContext = "T_ADRESSE";

}
