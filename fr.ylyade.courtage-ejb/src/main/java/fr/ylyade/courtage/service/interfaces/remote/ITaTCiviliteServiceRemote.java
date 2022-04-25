package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTCiviliteDTO;
import fr.ylyade.courtage.model.TaTCivilite;


@Remote
public interface ITaTCiviliteServiceRemote extends IGenericCRUDServiceRemote<TaTCivilite,TaTCiviliteDTO>,
														IAbstractLgrDAOServer<TaTCivilite>,IAbstractLgrDAOServerDTO<TaTCiviliteDTO>{
	public static final String validationContext = "T_CIVILITE";

}
