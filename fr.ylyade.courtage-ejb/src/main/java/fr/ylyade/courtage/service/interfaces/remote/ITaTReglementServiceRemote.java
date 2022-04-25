package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTReglementDTO;
import fr.ylyade.courtage.model.TaTReglement;


@Remote
public interface ITaTReglementServiceRemote extends IGenericCRUDServiceRemote<TaTReglement,TaTReglementDTO>,
														IAbstractLgrDAOServer<TaTReglement>,IAbstractLgrDAOServerDTO<TaTReglementDTO>{
	public static final String validationContext = "T_REGLEMENT";

}
