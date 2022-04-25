package fr.ylyade.courtage.dao;

import fr.legrain.data.IGenericDAO;
import fr.ylyade.courtage.model.TaEmail;

//@Remote
public interface ITaEmailDAO extends IGenericDAO<TaEmail> {	
	
	public TaEmail findByAdresseEmail(String adresse);
	
}

