package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTAdresseDTO;
import fr.ylyade.courtage.model.TaTAdresse;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTAdresseMapper implements ILgrMapper<TaTAdresseDTO, TaTAdresse> {

	@Override
	public TaTAdresse mapDtoToEntity(TaTAdresseDTO dto, TaTAdresse entity) {
		if(entity==null)
			entity = new TaTAdresse();

		entity.setIdTAdresse(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeTAdresse(dto.getCodeTAdresse());
		entity.setLiblTAdresse(dto.getLiblTAdresse());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTAdresseDTO mapEntityToDto(TaTAdresse entity, TaTAdresseDTO dto) {
		if(dto==null)
			dto = new TaTAdresseDTO();

		dto.setId(entity.getIdTAdresse());
		
		dto.setCodeTAdresse(entity.getCodeTAdresse());
		dto.setLiblTAdresse(entity.getLiblTAdresse());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
