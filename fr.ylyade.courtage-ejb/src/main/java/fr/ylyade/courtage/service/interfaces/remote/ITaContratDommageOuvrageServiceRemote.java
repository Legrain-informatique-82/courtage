package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaContratDommageOuvrageDTO;
import fr.ylyade.courtage.model.TaContratDommageOuvrage;


@Remote
public interface ITaContratDommageOuvrageServiceRemote extends IGenericCRUDServiceRemote<TaContratDommageOuvrage,TaContratDommageOuvrageDTO>,
														IAbstractLgrDAOServer<TaContratDommageOuvrage>,IAbstractLgrDAOServerDTO<TaContratDommageOuvrageDTO>{
	public static final String validationContext = "CONTRAT_DOMMAGE_OUVRAGE";
	public Integer countActiveByIdCourtier(int idCourtier);

}
