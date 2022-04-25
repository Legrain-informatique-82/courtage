package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaIntervenantDevisDTO;
import fr.ylyade.courtage.model.TaIntervenantDevis;


@Remote
public interface ITaIntervenantDevisServiceRemote extends IGenericCRUDServiceRemote<TaIntervenantDevis,TaIntervenantDevisDTO>,
														IAbstractLgrDAOServer<TaIntervenantDevis>,IAbstractLgrDAOServerDTO<TaIntervenantDevisDTO>{
	public static final String validationContext = "INTERVENANT_DEVIS";

}
