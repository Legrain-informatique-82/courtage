package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTEmailDTO;
import fr.ylyade.courtage.model.TaTEmail;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTEmailMapper implements ILgrMapper<TaTEmailDTO, TaTEmail> {

	@Override
	public TaTEmail mapDtoToEntity(TaTEmailDTO dto, TaTEmail entity) {
		if(entity==null)
			entity = new TaTEmail();

		entity.setIdTEmail(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeTEmail(dto.getCodeTEmail());
		entity.setLiblTEmail(dto.getLiblTEmail());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTEmailDTO mapEntityToDto(TaTEmail entity, TaTEmailDTO dto) {
		if(dto==null)
			dto = new TaTEmailDTO();

		dto.setId(entity.getIdTEmail());
		
		dto.setCodeTEmail(entity.getCodeTEmail());
		dto.setLiblTEmail(entity.getLiblTEmail());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
