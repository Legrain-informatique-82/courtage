package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaFamilleActiviteDTO;
import fr.ylyade.courtage.model.TaFamilleActivite;


@Remote
public interface ITaFamilleActiviteServiceRemote extends IGenericCRUDServiceRemote<TaFamilleActivite,TaFamilleActiviteDTO>,
														IAbstractLgrDAOServer<TaFamilleActivite>,IAbstractLgrDAOServerDTO<TaFamilleActiviteDTO>{
	public static final String validationContext = "FAMILLE_ACTIVITE";

}
