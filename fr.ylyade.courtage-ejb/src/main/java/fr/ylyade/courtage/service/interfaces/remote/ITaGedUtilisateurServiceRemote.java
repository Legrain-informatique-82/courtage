package fr.ylyade.courtage.service.interfaces.remote;

import java.util.List;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaGedUtilisateurDTO;
import fr.ylyade.courtage.model.TaGedUtilisateur;


@Remote
public interface ITaGedUtilisateurServiceRemote extends IGenericCRUDServiceRemote<TaGedUtilisateur,TaGedUtilisateurDTO>,
														IAbstractLgrDAOServer<TaGedUtilisateur>,IAbstractLgrDAOServerDTO<TaGedUtilisateurDTO>{
	public static final String validationContext = "GED_UTILISATEUR";
	
	public List<TaGedUtilisateur> findAllByIdDossier(Integer idDossier);
	
	public TaGedUtilisateurDTO findByIdDocAndByIdCourtierDTO(Integer idDoc, Integer idCourtier);
	public TaGedUtilisateur findByIdDocAndByIdCourtier(Integer idDoc, Integer idCourtier);
	
	public TaGedUtilisateurDTO findByIdDocAndByIdDevisRcProDTO(Integer idDoc, Integer idDevisRcPro);
	public TaGedUtilisateur findByIdDocAndByIdDevisRcPro(Integer idDoc, Integer idDevisRcPro);
	
	public TaGedUtilisateur findByCodeListeRefAndByIdDossier(String codeDoc, Integer idDossierRcd);
	
	
	public long countNbDocAttenduPourCourtier(String codeTListeRefDoc);
	public long countNbDocUploderPourCourtier(Integer idCourtier);
	public long countNbDocValiderPourCourtier(Integer idCourtier);
	
	

}
