package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTEmailDTO;
import fr.ylyade.courtage.model.TaTEmail;


@Remote
public interface ITaTEmailServiceRemote extends IGenericCRUDServiceRemote<TaTEmail,TaTEmailDTO>,
														IAbstractLgrDAOServer<TaTEmail>,IAbstractLgrDAOServerDTO<TaTEmailDTO>{
	public static final String validationContext = "T_EMAIL";

}
