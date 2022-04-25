package fr.ylyade.courtage.dao;

import java.util.List;

import fr.legrain.data.IGenericDAO;
import fr.ylyade.courtage.dto.TaCourtierDTO;
import fr.ylyade.courtage.model.TaCourtier;

//@Remote
public interface ITaCourtierDAO extends IGenericDAO<TaCourtier> {	
	
	public List<TaCourtierDTO> findByCodeLight(String code);
	public List<TaCourtierDTO> findAllLight();
	public List<TaCourtierDTO> findAllInactif();
	public List<TaCourtierDTO> findAllActif();
	
	public TaCourtier findByIdUtilisateur(int idUtilisateur);
	public TaCourtier findByIdentifiantAndByPassword(String identifiant, String password);
}

