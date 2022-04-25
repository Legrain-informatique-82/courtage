package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTCourtierDTO;
import fr.ylyade.courtage.model.TaTCourtier;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTCourtierMapper implements ILgrMapper<TaTCourtierDTO, TaTCourtier> {

	@Override
	public TaTCourtier mapDtoToEntity(TaTCourtierDTO dto, TaTCourtier entity) {
		if(entity==null)
			entity = new TaTCourtier();

		entity.setIdTCourtier(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeTCourtier(dto.getCodeTCourtier());
		entity.setLiblTCourtier(dto.getLiblTCourtier());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTCourtierDTO mapEntityToDto(TaTCourtier entity, TaTCourtierDTO dto) {
		if(dto==null)
			dto = new TaTCourtierDTO();
		
		if(entity!=null) {
			dto.setId(entity.getIdTCourtier());
			
			dto.setCodeTCourtier(entity.getCodeTCourtier());
			dto.setLiblTCourtier(entity.getLiblTCourtier());
			
			dto.setVersionObj(entity.getVersionObj());
		}

		return dto;
	}

}
