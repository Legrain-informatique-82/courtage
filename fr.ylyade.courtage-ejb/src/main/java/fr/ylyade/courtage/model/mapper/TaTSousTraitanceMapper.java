package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTSousTraitanceDTO;
import fr.ylyade.courtage.model.TaTSousTraitance;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTSousTraitanceMapper implements ILgrMapper<TaTSousTraitanceDTO, TaTSousTraitance> {

	@Override
	public TaTSousTraitance mapDtoToEntity(TaTSousTraitanceDTO dto, TaTSousTraitance entity) {
		if(entity==null)
			entity = new TaTSousTraitance();

		entity.setIdTSousTraitance(dto.getId()!=null?dto.getId():0);
		entity.setBaseMax(dto.getBaseMax());
		entity.setBaseMin(dto.getBaseMin());
		entity.setCodeTSousTraitance(dto.getCodeTSousTraitance());
		entity.setLiblTSousTraitance(dto.getLiblTSousTraitance());
		
		entity.setTaux(dto.getTaux());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTSousTraitanceDTO mapEntityToDto(TaTSousTraitance entity, TaTSousTraitanceDTO dto) {
		if(dto==null)
			dto = new TaTSousTraitanceDTO();

		dto.setId(entity.getIdTSousTraitance());
		
		dto.setBaseMax(entity.getBaseMax());
		dto.setBaseMin(entity.getBaseMin());
		dto.setTaux(entity.getTaux());
		dto.setCodeTSousTraitance(entity.getCodeTSousTraitance());
		dto.setLiblTSousTraitance(entity.getLiblTSousTraitance());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
