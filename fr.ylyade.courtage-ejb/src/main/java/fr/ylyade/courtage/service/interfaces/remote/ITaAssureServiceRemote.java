package fr.ylyade.courtage.service.interfaces.remote;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaAssureDTO;
import fr.ylyade.courtage.model.TaAssure;


@Remote
public interface ITaAssureServiceRemote extends IGenericCRUDServiceRemote<TaAssure,TaAssureDTO>,
														IAbstractLgrDAOServer<TaAssure>,IAbstractLgrDAOServerDTO<TaAssureDTO>{
	public static final String validationContext = "ASSURE";
	
	public TaAssure findByIdUtilisateur(int idUtilisateur);

	public String genereCode( Map<String, String> params);
	public void annuleCode(String code);
	public void verrouilleCode(String code);
	
	public List<TaAssureDTO> findByCodeLight(String code);
	public List<TaAssureDTO> findAllLight();
	public List<TaAssureDTO> findAllLightPlusExtraction();
	public List<TaAssureDTO> findAllByIdCourtier(int idCourtier);
	
	public Integer countActiveByIdCourtier(int idCourtier);
}
