package fr.ylyade.courtage.droits.dao;

import fr.legrain.data.IGenericDAO;
import fr.ylyade.courtage.droits.model.TaUtilisateur;

//@Remote
public interface IUtilisateurDAO extends IGenericDAO<TaUtilisateur> {

	public TaUtilisateur ctrlSaisieEmail(String email);
		
	
}

