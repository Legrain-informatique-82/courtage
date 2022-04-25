package fr.ylyade.courtage.dao;

import java.util.List;

import fr.legrain.data.IGenericDAO;
import fr.ylyade.courtage.dto.TaDevisRcProDetailDTO;
import fr.ylyade.courtage.model.TaDossierRcd;

//@Remote
public interface ITaDevisRcProDetailDAO extends IGenericDAO<TaDossierRcd> {	
	
	public List<TaDossierRcd> findActiveByIdAssure(int idAssure, Boolean active);
	public List<TaDevisRcProDetailDTO> findActiveByIdAssureDTO(int idAssure, Boolean active);
	public Integer countActiveByIdCourtier(int idCourtier);
	
}

