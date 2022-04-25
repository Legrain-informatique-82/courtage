package fr.ylyade.courtage.dao;

import java.util.List;

import fr.legrain.data.IGenericDAO;
import fr.ylyade.courtage.dto.TaGedUtilisateurDTO;
import fr.ylyade.courtage.model.TaGedUtilisateur;

//@Remote
public interface ITaGedUtilisateurDAO extends IGenericDAO<TaGedUtilisateur> {
	
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

