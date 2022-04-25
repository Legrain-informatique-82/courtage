package fr.ylyade.courtage.service.interfaces.remote;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaCourtierDTO;
import fr.ylyade.courtage.model.TaCourtier;


@Remote
public interface ITaCourtierServiceRemote extends IGenericCRUDServiceRemote<TaCourtier,TaCourtierDTO>,
														IAbstractLgrDAOServer<TaCourtier>,IAbstractLgrDAOServerDTO<TaCourtierDTO>{
	public static final String validationContext = "COURTIER";
	
	public String genereCode( Map<String, String> params);
	public void annuleCode(String code);
	public void verrouilleCode(String code);
	
	public TaCourtier findByIdentifiantAndByPassword(String identifiant, String password);
	
	public List<TaCourtierDTO> findByCodeLight(String code);
	public List<TaCourtierDTO> findAllLight();
	public List<TaCourtierDTO> findAllInactif();
	public List<TaCourtierDTO> findAllActif();
	
	public TaCourtier findByIdUtilisateur(int idUtilisateur);

}
