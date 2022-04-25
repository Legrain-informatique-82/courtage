package fr.ylyade.courtage.service.interfaces.remote;

import java.util.List;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTStatutDTO;
import fr.ylyade.courtage.model.TaTStatut;


@Remote
public interface ITaTStatutServiceRemote extends IGenericCRUDServiceRemote<TaTStatut,TaTStatutDTO>,
														IAbstractLgrDAOServer<TaTStatut>,IAbstractLgrDAOServerDTO<TaTStatutDTO>{
	public static final String validationContext = "T_STATUS";
	
	public List<TaTStatut> findAllStatutByIdDossier(Integer idDossierRcd);

}
