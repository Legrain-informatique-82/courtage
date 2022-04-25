package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTTravauxDTO;
import fr.ylyade.courtage.model.TaTTravaux;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTTravauxMapper implements ILgrMapper<TaTTravauxDTO, TaTTravaux> {

	@Override
	public TaTTravaux mapDtoToEntity(TaTTravauxDTO dto, TaTTravaux entity) {
		if(entity==null)
			entity = new TaTTravaux();

		entity.setIdTTravaux(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeTTravaux(dto.getCodeTTravaux());
		entity.setLiblTTravaux(dto.getLiblTTravaux());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTTravauxDTO mapEntityToDto(TaTTravaux entity, TaTTravauxDTO dto) {
		if(dto==null)
			dto = new TaTTravauxDTO();

		dto.setId(entity.getIdTTravaux());
		
		dto.setCodeTTravaux(entity.getCodeTTravaux());
		dto.setLiblTTravaux(entity.getLiblTTravaux());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
