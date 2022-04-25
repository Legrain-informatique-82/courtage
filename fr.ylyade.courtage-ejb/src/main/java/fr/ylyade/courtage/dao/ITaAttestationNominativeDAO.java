package fr.ylyade.courtage.dao;

import java.util.List;

import fr.legrain.data.IGenericDAO;
import fr.ylyade.courtage.model.TaAttestationNominative;

//@Remote
public interface ITaAttestationNominativeDAO extends IGenericDAO<TaAttestationNominative> {	
	
	public List<TaAttestationNominative> findAllByIdDossier(Integer idDossierRcd);
	
}

