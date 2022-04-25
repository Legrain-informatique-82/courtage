package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaPalierClasseDTO;
import fr.ylyade.courtage.model.TaPalierClasse;


@Remote
public interface ITaPalierClasseServiceRemote extends IGenericCRUDServiceRemote<TaPalierClasse,TaPalierClasseDTO>,
														IAbstractLgrDAOServer<TaPalierClasse>,IAbstractLgrDAOServerDTO<TaPalierClasseDTO>{
	public static final String validationContext = "PALIER_CLASSE";

}
