package fr.ylyade.courtage.dao;

import fr.legrain.data.IGenericDAO;
import fr.ylyade.courtage.model.TaProjetDommageOuvrage;

//@Remote
public interface ITaProjetDommageOuvrageDAO extends IGenericDAO<TaProjetDommageOuvrage> {		
	public Integer countActiveByIdCourtier(int idCourtier);
}

