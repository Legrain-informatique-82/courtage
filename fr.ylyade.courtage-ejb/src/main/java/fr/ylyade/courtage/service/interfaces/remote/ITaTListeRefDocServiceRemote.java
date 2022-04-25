package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTListeRefDocDTO;
import fr.ylyade.courtage.model.TaTListeRefDoc;


@Remote
public interface ITaTListeRefDocServiceRemote extends IGenericCRUDServiceRemote<TaTListeRefDoc,TaTListeRefDocDTO>,
														IAbstractLgrDAOServer<TaTListeRefDoc>,IAbstractLgrDAOServerDTO<TaTListeRefDocDTO>{
	public static final String validationContext = "T_LISTE_REF_DOC";

}
