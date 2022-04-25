package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaEmailDTO;
import fr.ylyade.courtage.model.TaEmail;


@Remote
public interface ITaEmailServiceRemote extends IGenericCRUDServiceRemote<TaEmail,TaEmailDTO>,
														IAbstractLgrDAOServer<TaEmail>,IAbstractLgrDAOServerDTO<TaEmailDTO>{
	public static final String validationContext = "EMAIL";
	
	public TaEmail findByAdresseEmail(String adresse);

}
