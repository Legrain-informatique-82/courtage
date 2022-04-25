package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaHistoriquePrestationDTO;
import fr.ylyade.courtage.model.TaHistoriquePrestation;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaHistoriquePrestationMapper implements ILgrMapper<TaHistoriquePrestationDTO, TaHistoriquePrestation> {

	@Override
	public TaHistoriquePrestation mapDtoToEntity(TaHistoriquePrestationDTO dto, TaHistoriquePrestation entity) {
		if(entity==null)
			entity = new TaHistoriquePrestation();

		entity.setIdHistoriquePrestation(dto.getId()!=null?dto.getId():0);
		
		entity.setLiblPrestation(dto.getLiblPrestation());
		entity.setDatePrestation(dto.getDatePrestation());
		entity.setTaDossierRcd(dto.getTaDossierRcd());
		entity.setTaReglementPrestation(dto.getTaReglementPrestation());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaHistoriquePrestationDTO mapEntityToDto(TaHistoriquePrestation entity, TaHistoriquePrestationDTO dto) {
		if(dto==null)
			dto = new TaHistoriquePrestationDTO();

		dto.setId(entity.getIdHistoriquePrestation());
		
		dto.setLiblPrestation(entity.getLiblPrestation());
		dto.setDatePrestation(entity.getDatePrestation());
		dto.setTaDossierRcd(entity.getTaDossierRcd());
		dto.setTaReglementPrestation(entity.getTaReglementPrestation());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
