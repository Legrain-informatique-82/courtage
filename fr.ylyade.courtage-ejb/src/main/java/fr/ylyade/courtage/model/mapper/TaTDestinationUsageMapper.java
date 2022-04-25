package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTDestinationUsageDTO;
import fr.ylyade.courtage.model.TaTDestinationUsage;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTDestinationUsageMapper implements ILgrMapper<TaTDestinationUsageDTO, TaTDestinationUsage> {

	@Override
	public TaTDestinationUsage mapDtoToEntity(TaTDestinationUsageDTO dto, TaTDestinationUsage entity) {
		if(entity==null)
			entity = new TaTDestinationUsage();

		entity.setIdTDestinationUsage(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeTDestinationUsage(dto.getCodeTDestinationUsage());
		entity.setLiblTDestinationUsage(dto.getLiblTDestinationUsage());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTDestinationUsageDTO mapEntityToDto(TaTDestinationUsage entity, TaTDestinationUsageDTO dto) {
		if(dto==null)
			dto = new TaTDestinationUsageDTO();

		dto.setId(entity.getIdTDestinationUsage());
		
		dto.setCodeTDestinationUsage(entity.getCodeTDestinationUsage());
		dto.setLiblTDestinationUsage(entity.getLiblTDestinationUsage());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
