package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTarifPrestationDTO;
import fr.ylyade.courtage.model.TaTarifPrestation;


@Remote
public interface ITaTarifPrestationServiceRemote extends IGenericCRUDServiceRemote<TaTarifPrestation,TaTarifPrestationDTO>,
														IAbstractLgrDAOServer<TaTarifPrestation>,IAbstractLgrDAOServerDTO<TaTarifPrestationDTO>{
	public static final String validationContext = "TARIF_PRESTATION";

}
