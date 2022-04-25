package fr.ylyade.courtage.service.interfaces.remote;

import java.util.List;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaListeRefDocDTO;
import fr.ylyade.courtage.model.TaListeRefDoc;


@Remote
public interface ITaListeRefDocServiceRemote extends IGenericCRUDServiceRemote<TaListeRefDoc,TaListeRefDocDTO>,
														IAbstractLgrDAOServer<TaListeRefDoc>,IAbstractLgrDAOServerDTO<TaListeRefDocDTO>{
	public static final String validationContext = "LISTE_REF_DOC";
	public List<TaListeRefDocDTO> findByType(int idTListeRefDoc);
	public List<TaListeRefDocDTO> findByTypeNotInGedUtilisateur(int idDossierRcd, int idTListeRefDoc);
	public List<TaListeRefDocDTO> findByTypeInGedUtilisateur(int idDossierRcd, int idTListeRefDoc);

}
