package fr.ylyade.courtage.dao;

import java.util.List;

import fr.legrain.data.IGenericDAO;
import fr.ylyade.courtage.dto.TaAssureDTO;
import fr.ylyade.courtage.dto.TaCourtierDTO;
import fr.ylyade.courtage.model.TaAssure;

//@Remote
public interface ITaAssureDAO extends IGenericDAO<TaAssure> {		
	public List<TaAssureDTO> findByCodeLight(String code);
	public List<TaAssureDTO> findAllLight();
	public List<TaAssureDTO> findAllLightPlusExtraction();
	public List<TaAssureDTO> findAllByIdCourtier(int idCourtier);
	
	public Integer countActiveByIdCourtier(int idCourtier);
	
	public TaAssure findByIdUtilisateur(int idUtilisateur);
}

