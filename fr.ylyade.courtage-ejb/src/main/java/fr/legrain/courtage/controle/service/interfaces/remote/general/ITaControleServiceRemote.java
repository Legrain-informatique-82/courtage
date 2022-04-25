package fr.legrain.courtage.controle.service.interfaces.remote.general;

import javax.ejb.Remote;

import fr.legrain.controle.dto.TaControleDTO;
import fr.legrain.controle.model.TaControle;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;

@Remote
public interface ITaControleServiceRemote extends IGenericCRUDServiceRemote<TaControle,TaControleDTO>,
														IAbstractLgrDAOServer<TaControle>,IAbstractLgrDAOServerDTO<TaControleDTO>{
	public static final String validationContext = "CONTROLE";
}
