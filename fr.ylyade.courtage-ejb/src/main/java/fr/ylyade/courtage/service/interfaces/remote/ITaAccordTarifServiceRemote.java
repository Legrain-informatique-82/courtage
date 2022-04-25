package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaAccordTarifDTO;
import fr.ylyade.courtage.model.TaAccordTarif;


@Remote
public interface ITaAccordTarifServiceRemote extends IGenericCRUDServiceRemote<TaAccordTarif,TaAccordTarifDTO>,
														IAbstractLgrDAOServer<TaAccordTarif>,IAbstractLgrDAOServerDTO<TaAccordTarifDTO>{
	public static final String validationContext = "ACCORDTARIF";

}
