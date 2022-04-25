package fr.ylyade.courtage.droits.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.droits.model.TaRRoleUtilisateur;
import fr.ylyade.courtage.droits.model.dto.TaRRoleUtilisateurDTO;

@Remote
public interface ITaRRoleUtilisateurServiceRemote extends IGenericCRUDServiceRemote<TaRRoleUtilisateur,TaRRoleUtilisateurDTO>,
														IAbstractLgrDAOServer<TaRRoleUtilisateur>,IAbstractLgrDAOServerDTO<TaRRoleUtilisateurDTO>{
	public static final String validationContext = "R_ROLE_UTILISATEUR";
}
