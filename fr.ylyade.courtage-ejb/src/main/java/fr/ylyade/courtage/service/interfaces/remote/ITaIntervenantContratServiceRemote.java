package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaIntervenantContratDTO;
import fr.ylyade.courtage.model.TaIntervenantContrat;


@Remote
public interface ITaIntervenantContratServiceRemote extends IGenericCRUDServiceRemote<TaIntervenantContrat,TaIntervenantContratDTO>,
														IAbstractLgrDAOServer<TaIntervenantContrat>,IAbstractLgrDAOServerDTO<TaIntervenantContratDTO>{
	public static final String validationContext = "INTERVENANT_CONTRAT";

}
