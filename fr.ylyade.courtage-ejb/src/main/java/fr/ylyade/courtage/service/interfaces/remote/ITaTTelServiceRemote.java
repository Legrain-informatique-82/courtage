package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTTelDTO;
import fr.ylyade.courtage.model.TaTTel;


@Remote
public interface ITaTTelServiceRemote extends IGenericCRUDServiceRemote<TaTTel,TaTTelDTO>,
														IAbstractLgrDAOServer<TaTTel>,IAbstractLgrDAOServerDTO<TaTTelDTO>{
	public static final String validationContext = "T_TEL";

}
