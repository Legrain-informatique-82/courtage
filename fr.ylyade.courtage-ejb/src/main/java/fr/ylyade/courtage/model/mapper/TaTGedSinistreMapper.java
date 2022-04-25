package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTGedSinistreDTO;
import fr.ylyade.courtage.model.TaTGedSinistre;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTGedSinistreMapper implements ILgrMapper<TaTGedSinistreDTO, TaTGedSinistre> {

	@Override
	public TaTGedSinistre mapDtoToEntity(TaTGedSinistreDTO dto, TaTGedSinistre entity) {
		if(entity==null)
			entity = new TaTGedSinistre();

		entity.setIdTGedSinistre(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeTGedSinistre(dto.getCodeTGedSinistre());
		entity.setLiblTGedSinistre(dto.getLiblTGedSinistre());
		entity.setDecriptionTGedSinistre(dto.getDecriptionTGedSinistre());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTGedSinistreDTO mapEntityToDto(TaTGedSinistre entity, TaTGedSinistreDTO dto) {
		if(dto==null)
			dto = new TaTGedSinistreDTO();

		dto.setId(entity.getIdTGedSinistre());
		
		dto.setCodeTGedSinistre(entity.getCodeTGedSinistre());
		dto.setLiblTGedSinistre(entity.getLiblTGedSinistre());
		dto.setDecriptionTGedSinistre(entity.getDecriptionTGedSinistre());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
