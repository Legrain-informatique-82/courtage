package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTMaitriseOeuvreDTO;
import fr.ylyade.courtage.model.TaTMaitriseOeuvre;


@Remote
public interface ITaTMaitriseOeuvreServiceRemote extends IGenericCRUDServiceRemote<TaTMaitriseOeuvre,TaTMaitriseOeuvreDTO>,
														IAbstractLgrDAOServer<TaTMaitriseOeuvre>,IAbstractLgrDAOServerDTO<TaTMaitriseOeuvreDTO>{
	public static final String validationContext = "T_MAITRISE_OEUVRE";

}
