package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaProjetDommageOuvrageDTO;
import fr.ylyade.courtage.model.TaProjetDommageOuvrage;


@Remote
public interface ITaProjetDommageOuvrageServiceRemote extends IGenericCRUDServiceRemote<TaProjetDommageOuvrage,TaProjetDommageOuvrageDTO>,
														IAbstractLgrDAOServer<TaProjetDommageOuvrage>,IAbstractLgrDAOServerDTO<TaProjetDommageOuvrageDTO>{
	public static final String validationContext = "PROJET_DOMMAGE_OUVRAGE";
	public Integer countActiveByIdCourtier(int idCourtier);

}
