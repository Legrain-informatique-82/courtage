package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaProjetGfaDTO;
import fr.ylyade.courtage.model.TaProjetGfa;


@Remote
public interface ITaProjetGfaServiceRemote extends IGenericCRUDServiceRemote<TaProjetGfa,TaProjetGfaDTO>,
														IAbstractLgrDAOServer<TaProjetGfa>,IAbstractLgrDAOServerDTO<TaProjetGfaDTO>{
	public static final String validationContext = "PROJET_GFA";
	public Integer countActiveByIdCourtier(int idCourtier);

}
