package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaTUtilisateurDTO;
import fr.ylyade.courtage.model.TaTUtilisateur;


@Remote
public interface ITaTUtilisateurServiceRemote extends IGenericCRUDServiceRemote<TaTUtilisateur,TaTUtilisateurDTO>,
														IAbstractLgrDAOServer<TaTUtilisateur>,IAbstractLgrDAOServerDTO<TaTUtilisateurDTO>{
	public static final String validationContext = "T_UTILISATEUR";

}
