package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaSinistreAntecedentDTO;
import fr.ylyade.courtage.model.TaSinistreAntecedent;


@Remote
public interface ITaSinistreAntecedentServiceRemote extends IGenericCRUDServiceRemote<TaSinistreAntecedent,TaSinistreAntecedentDTO>,
														IAbstractLgrDAOServer<TaSinistreAntecedent>,IAbstractLgrDAOServerDTO<TaSinistreAntecedentDTO>{
	public static final String validationContext = "SINISTRE_ANTECEDANT";

}
