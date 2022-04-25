package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTGedSinistreDTO;
import fr.ylyade.courtage.model.TaTGedSinistre;


@Remote
public interface ITaTGedSinistreServiceRemote extends IGenericCRUDServiceRemote<TaTGedSinistre,TaTGedSinistreDTO>,
														IAbstractLgrDAOServer<TaTGedSinistre>,IAbstractLgrDAOServerDTO<TaTGedSinistreDTO>{
	public static final String validationContext = "T_GED_SINISTRE";

}
