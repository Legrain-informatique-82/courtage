package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaContactEntrepriseDTO;
import fr.ylyade.courtage.model.TaContactEntreprise;


@Remote
public interface ITaContactEntrepriseServiceRemote extends IGenericCRUDServiceRemote<TaContactEntreprise,TaContactEntrepriseDTO>,
														IAbstractLgrDAOServer<TaContactEntreprise>,IAbstractLgrDAOServerDTO<TaContactEntrepriseDTO>{
	public static final String validationContext = "T_CONTACT_ENTREPRISE";

}
