package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTConstructionDTO;
import fr.ylyade.courtage.model.TaTConstruction;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTConstructionMapper implements ILgrMapper<TaTConstructionDTO, TaTConstruction> {

	@Override
	public TaTConstruction mapDtoToEntity(TaTConstructionDTO dto, TaTConstruction entity) {
		if(entity==null)
			entity = new TaTConstruction();

		entity.setIdTConstruction(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeConstruction(dto.getCodeConstruction());
		entity.setLiblConstruction(dto.getLiblConstruction());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTConstructionDTO mapEntityToDto(TaTConstruction entity, TaTConstructionDTO dto) {
		if(dto==null)
			dto = new TaTConstructionDTO();

		dto.setId(entity.getIdTConstruction());
		
		dto.setCodeConstruction(entity.getCodeConstruction());
		dto.setLiblConstruction(entity.getLiblConstruction());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
