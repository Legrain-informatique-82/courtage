package fr.ylyade.courtage.service.interfaces.remote;

import java.util.List;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaDevisRcProDetailDTO;
import fr.ylyade.courtage.model.TaDossierRcd;


@Remote
public interface ITaDevisRcProDetailServiceRemote extends IGenericCRUDServiceRemote<TaDossierRcd,TaDevisRcProDetailDTO>,
														IAbstractLgrDAOServer<TaDossierRcd>,IAbstractLgrDAOServerDTO<TaDevisRcProDetailDTO>{
	public static final String validationContext = "DEVIS_RCPRO_DETAIL";
	
	
	public List<TaDossierRcd> findActiveByIdAssure(int idAssure, Boolean active);
	public List<TaDevisRcProDetailDTO> findActiveByIdAssureDTO(int idAssure, Boolean active);
	public Integer countActiveByIdCourtier(int idCourtier);

}
