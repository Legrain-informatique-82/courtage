package fr.ylyade.courtage.service.interfaces.remote;

import java.util.Map;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaReglementPrestationDTO;
import fr.ylyade.courtage.model.TaReglementPrestation;


@Remote
public interface ITaReglementPrestationServiceRemote extends IGenericCRUDServiceRemote<TaReglementPrestation,TaReglementPrestationDTO>,
														IAbstractLgrDAOServer<TaReglementPrestation>,IAbstractLgrDAOServerDTO<TaReglementPrestationDTO>{
	public static final String validationContext = "REGLEMENT_PRESTATION";
	public String genereCode( Map<String, String> params);

}
