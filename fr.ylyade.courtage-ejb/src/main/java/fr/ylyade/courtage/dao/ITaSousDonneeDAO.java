package fr.ylyade.courtage.dao;

import fr.legrain.data.IGenericDAO;
import fr.ylyade.courtage.model.TaSousDonnee;

//@Remote
public interface ITaSousDonneeDAO extends IGenericDAO<TaSousDonnee> {		
	public TaSousDonnee findByIdDossierRcd(int idDossier);
}

