package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTGarantieSinistreDTO;
import fr.ylyade.courtage.model.TaTGarantieSinistre;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTGarantieSinistreMapper implements ILgrMapper<TaTGarantieSinistreDTO, TaTGarantieSinistre> {

	@Override
	public TaTGarantieSinistre mapDtoToEntity(TaTGarantieSinistreDTO dto, TaTGarantieSinistre entity) {
		if(entity==null)
			entity = new TaTGarantieSinistre();

		entity.setIdTGarantieSinistre(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeTGarantieSinistre(dto.getCodeTGarantieSinistre());
		entity.setLiblTGarantieSinistre(dto.getLiblTGarantieSinistre());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTGarantieSinistreDTO mapEntityToDto(TaTGarantieSinistre entity, TaTGarantieSinistreDTO dto) {
		if(dto==null)
			dto = new TaTGarantieSinistreDTO();

		dto.setId(entity.getIdTGarantieSinistre());
		
		dto.setCodeTGarantieSinistre(entity.getCodeTGarantieSinistre());
		dto.setLiblTGarantieSinistre(entity.getLiblTGarantieSinistre());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
