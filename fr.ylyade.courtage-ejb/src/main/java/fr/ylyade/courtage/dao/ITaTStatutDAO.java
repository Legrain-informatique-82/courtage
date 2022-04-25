package fr.ylyade.courtage.dao;

import java.util.List;

import fr.legrain.data.IGenericDAO;
import fr.ylyade.courtage.model.TaTStatut;

//@Remote
public interface ITaTStatutDAO extends IGenericDAO<TaTStatut> {	
	public List<TaTStatut> findAllStatutByIdDossier(Integer idDossierRcd);
	
}

