package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTAssureDTO;
import fr.ylyade.courtage.model.TaTAssure;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTAssureMapper implements ILgrMapper<TaTAssureDTO, TaTAssure> {

	@Override
	public TaTAssure mapDtoToEntity(TaTAssureDTO dto, TaTAssure entity) {
		if(entity==null)
			entity = new TaTAssure();

		entity.setIdTAssure(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeTAssure(dto.getCodeTAssure());
		entity.setLiblTAssure(dto.getLiblTAssure());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTAssureDTO mapEntityToDto(TaTAssure entity, TaTAssureDTO dto) {
		if(dto==null)
			dto = new TaTAssureDTO();

		dto.setId(entity.getIdTAssure());
		
		dto.setCodeTAssure(entity.getCodeTAssure());
		dto.setLiblTAssure(entity.getLiblTAssure());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
