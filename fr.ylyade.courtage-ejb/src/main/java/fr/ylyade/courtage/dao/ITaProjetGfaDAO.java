package fr.ylyade.courtage.dao;

import fr.legrain.data.IGenericDAO;
import fr.ylyade.courtage.model.TaProjetGfa;

//@Remote
public interface ITaProjetGfaDAO extends IGenericDAO<TaProjetGfa> {	
	public Integer countActiveByIdCourtier(int idCourtier);
	
}

