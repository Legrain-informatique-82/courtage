package fr.ylyade.courtage.dao;

import fr.legrain.data.IGenericDAO;
import fr.ylyade.courtage.model.TaContratGfa;

//@Remote
public interface ITaContratGfaDAO extends IGenericDAO<TaContratGfa> {		
	public Integer countActiveByIdCourtier(int idCourtier);
}

