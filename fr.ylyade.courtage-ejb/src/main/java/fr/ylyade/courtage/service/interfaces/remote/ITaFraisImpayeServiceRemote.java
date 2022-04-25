package fr.ylyade.courtage.service.interfaces.remote;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaFraisImpayeDTO;
import fr.ylyade.courtage.model.TaFraisImpaye;


@Remote
public interface ITaFraisImpayeServiceRemote extends IGenericCRUDServiceRemote<TaFraisImpaye,TaFraisImpayeDTO>,
														IAbstractLgrDAOServer<TaFraisImpaye>,IAbstractLgrDAOServerDTO<TaFraisImpayeDTO>{
	public static final String validationContext = "FraisImpaye";
	
	public String genereCode( Map<String, String> params);
	public void annuleCode(String code);
	public void verrouilleCode(String code);

	public List<TaFraisImpayeDTO> findAllFraisImpayeRCDDTO();
	public List<TaFraisImpayeDTO> findAllFraisImpayeRCDIdDTO(Integer idRCD);
	public TaFraisImpayeDTO findFraisImpayeReglementRCDDTO(Integer idFraisImpayeRcd);
}
