package fr.ylyade.courtage.droits.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.droits.model.TaUtilisateur;
import fr.ylyade.courtage.droits.model.dto.TaUtilisateurDTO;


@Remote
public interface ITaUtilisateurServiceRemote extends IGenericCRUDServiceRemote<TaUtilisateur,TaUtilisateurDTO>,
														IAbstractLgrDAOServer<TaUtilisateur>,IAbstractLgrDAOServerDTO<TaUtilisateurDTO>{
	public static final String validationContext = "UTILISATEUR";
	public TaUtilisateur getSessionInfo();
	public TaUtilisateur ctrlSaisieEmail(String email);
	public String getTenantId();
}
