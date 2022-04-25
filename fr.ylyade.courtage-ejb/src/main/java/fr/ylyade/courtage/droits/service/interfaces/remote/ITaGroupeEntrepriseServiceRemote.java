package fr.ylyade.courtage.droits.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.droits.model.TaGroupeEntreprise;
import fr.ylyade.courtage.droits.model.dto.TaGroupeEntrepriseDTO;

@Remote
public interface ITaGroupeEntrepriseServiceRemote extends IGenericCRUDServiceRemote<TaGroupeEntreprise,TaGroupeEntrepriseDTO>,
														IAbstractLgrDAOServer<TaGroupeEntreprise>,IAbstractLgrDAOServerDTO<TaGroupeEntrepriseDTO>{
	public static final String validationContext = "GROUPE_ENTREPRISE";
}
