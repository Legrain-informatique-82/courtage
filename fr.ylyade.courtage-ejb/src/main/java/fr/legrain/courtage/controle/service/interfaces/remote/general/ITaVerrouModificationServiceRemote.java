package fr.legrain.courtage.controle.service.interfaces.remote.general;

import javax.ejb.Remote;

import fr.legrain.controle.dto.TaVerrouModificationDTO;
import fr.legrain.controle.model.TaVerrouModification;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;

@Remote
public interface ITaVerrouModificationServiceRemote extends IGenericCRUDServiceRemote<TaVerrouModification,TaVerrouModificationDTO>,
														IAbstractLgrDAOServer<TaVerrouModification>,IAbstractLgrDAOServerDTO<TaVerrouModificationDTO>{
	public static final String validationContext = "VERROU_MODIFICATION";
}
