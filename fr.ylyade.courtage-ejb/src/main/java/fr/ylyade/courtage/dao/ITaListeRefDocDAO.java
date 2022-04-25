package fr.ylyade.courtage.dao;

import java.util.List;

import fr.legrain.data.IGenericDAO;
import fr.ylyade.courtage.dto.TaListeRefDocDTO;
import fr.ylyade.courtage.model.TaListeRefDoc;

//@Remote
public interface ITaListeRefDocDAO extends IGenericDAO<TaListeRefDoc> {
	
	public List<TaListeRefDocDTO> findByType(int idTListeRefDoc);

	public List<TaListeRefDocDTO> findByTypeNotInGedUtilisateur(int idDossierRcd, int idTListeRefDoc);

	public List<TaListeRefDocDTO> findByTypeInGedUtilisateur(int idDossierRcd, int idTListeRefDoc);

	
	
}

