package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTAssuranceDTO;
import fr.ylyade.courtage.model.TaTAssurance;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTAssuranceMapper implements ILgrMapper<TaTAssuranceDTO, TaTAssurance> {

	@Override
	public TaTAssurance mapDtoToEntity(TaTAssuranceDTO dto, TaTAssurance entity) {
		if(entity==null)
			entity = new TaTAssurance();

		entity.setIdTAssurance(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeTAssurance(dto.getCodeTAssurance());
		entity.setLiblTAssurance(dto.getLiblTAssurance());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTAssuranceDTO mapEntityToDto(TaTAssurance entity, TaTAssuranceDTO dto) {
		if(dto==null)
			dto = new TaTAssuranceDTO();

		dto.setId(entity.getIdTAssurance());
		
		dto.setCodeTAssurance(entity.getCodeTAssurance());
		dto.setLiblTAssurance(entity.getLiblTAssurance());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
