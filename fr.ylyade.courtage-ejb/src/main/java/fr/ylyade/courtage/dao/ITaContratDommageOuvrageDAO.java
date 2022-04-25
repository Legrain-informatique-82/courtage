package fr.ylyade.courtage.dao;

import fr.legrain.data.IGenericDAO;
import fr.ylyade.courtage.model.TaContratDommageOuvrage;

//@Remote
public interface ITaContratDommageOuvrageDAO extends IGenericDAO<TaContratDommageOuvrage> {	
	public Integer countActiveByIdCourtier(int idCourtier);
	
}

