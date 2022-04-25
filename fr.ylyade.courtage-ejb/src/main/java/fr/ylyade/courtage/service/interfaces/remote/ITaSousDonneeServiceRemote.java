package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaSousDonneeDTO;
import fr.ylyade.courtage.model.TaSousDonnee;


@Remote
public interface ITaSousDonneeServiceRemote extends IGenericCRUDServiceRemote<TaSousDonnee,TaSousDonneeDTO>,
														IAbstractLgrDAOServer<TaSousDonnee>,IAbstractLgrDAOServerDTO<TaSousDonneeDTO>{
	public static final String validationContext = "SOUSDONNEE";
	
	public TaSousDonnee findByIdDossierRcd(int idDossier);

}
