package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTSousTraitanceDTO;
import fr.ylyade.courtage.model.TaTSousTraitance;


@Remote
public interface ITaTSousTraitanceServiceRemote extends IGenericCRUDServiceRemote<TaTSousTraitance,TaTSousTraitanceDTO>,
														IAbstractLgrDAOServer<TaTSousTraitance>,IAbstractLgrDAOServerDTO<TaTSousTraitanceDTO>{
	public static final String validationContext = "T_SOUS_TRAITANCE";

}
