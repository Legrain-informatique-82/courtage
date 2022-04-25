package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTGroupeTarifDTO;
import fr.ylyade.courtage.model.TaTGroupeTarif;


@Remote
public interface ITaTGroupeTarifServiceRemote extends IGenericCRUDServiceRemote<TaTGroupeTarif,TaTGroupeTarifDTO>,
														IAbstractLgrDAOServer<TaTGroupeTarif>,IAbstractLgrDAOServerDTO<TaTGroupeTarifDTO>{
	public static final String validationContext = "T_GROUPE_TARIF";

}
