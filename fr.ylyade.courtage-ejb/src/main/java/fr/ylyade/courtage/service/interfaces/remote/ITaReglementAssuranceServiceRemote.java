package fr.ylyade.courtage.service.interfaces.remote;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaReglementAssuranceDTO;
import fr.ylyade.courtage.model.TaReglementAssurance;


@Remote
public interface ITaReglementAssuranceServiceRemote extends IGenericCRUDServiceRemote<TaReglementAssurance,TaReglementAssuranceDTO>,
														IAbstractLgrDAOServer<TaReglementAssurance>,IAbstractLgrDAOServerDTO<TaReglementAssuranceDTO>{
	public static final String validationContext = "REGLEMENT_ASSURANCE";	
	
	public String genereCode( Map<String, String> params);
	public void annuleCode(String code);
	public void verrouilleCode(String code);
	
	public List<TaReglementAssuranceDTO> findAllDTOByIdCourtier(Integer idCourtier);
	public List<TaReglementAssuranceDTO> findAllDTOByIdCourtierAndByMoisAndYear(Integer idCourtier, String mois, String annee);
	
	

}
