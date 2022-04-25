package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTauxAssuranceDTO;
import fr.ylyade.courtage.model.TaTauxAssurance;


@Remote
public interface ITaTauxAssuranceServiceRemote extends IGenericCRUDServiceRemote<TaTauxAssurance,TaTauxAssuranceDTO>,
														IAbstractLgrDAOServer<TaTauxAssurance>,IAbstractLgrDAOServerDTO<TaTauxAssuranceDTO>{
	public static final String validationContext = "TAUX_ASSURANCE";

}
