package fr.ylyade.courtage.dao;

import java.util.List;

import fr.legrain.data.IGenericDAO;
import fr.ylyade.courtage.dto.TaFraisImpayeDTO;
import fr.ylyade.courtage.model.TaFraisImpaye;

//@Remote
public interface ITaFraisImpayeDAO extends IGenericDAO<TaFraisImpaye> {		
	public List<TaFraisImpayeDTO> findAllFraisImpayeRCDDTO();
	public List<TaFraisImpayeDTO> findAllFraisImpayeRCDIdDTO(Integer idRCD);
	public TaFraisImpayeDTO findFraisImpayeReglementRCDDTO(Integer idFraisImpayeRcd);
}

