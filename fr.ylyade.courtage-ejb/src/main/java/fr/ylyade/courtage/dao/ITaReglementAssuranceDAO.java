package fr.ylyade.courtage.dao;

import java.util.List;

import fr.legrain.data.IGenericDAO;
import fr.ylyade.courtage.dto.TaReglementAssuranceDTO;
import fr.ylyade.courtage.model.TaReglementAssurance;

//@Remote
public interface ITaReglementAssuranceDAO extends IGenericDAO<TaReglementAssurance> {	
	
	public List<TaReglementAssuranceDTO> findAllDTOByIdCourtier(Integer idCourtier);
	public List<TaReglementAssuranceDTO> findAllDTOByIdCourtierAndByMoisAndYear(Integer idCourtier, String mois, String annee);
	
}

