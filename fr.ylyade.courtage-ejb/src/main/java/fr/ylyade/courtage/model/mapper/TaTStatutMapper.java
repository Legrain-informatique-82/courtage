package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTStatutDTO;
import fr.ylyade.courtage.model.TaTStatut;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTStatutMapper implements ILgrMapper<TaTStatutDTO, TaTStatut> {

	@Override
	public TaTStatut mapDtoToEntity(TaTStatutDTO dto, TaTStatut entity) {
		if(entity==null)
			entity = new TaTStatut();

		entity.setIdTStatut(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeTStatut(dto.getCodeTStatut());
		entity.setLiblTStatut(dto.getLiblTStatut());
		entity.setDureeStatut(dto.getDureeStatut());
		entity.setDureeNbJoursStatut(dto.getDureeNbJoursStatut());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTStatutDTO mapEntityToDto(TaTStatut entity, TaTStatutDTO dto) {
		if(dto==null)
			dto = new TaTStatutDTO();

		dto.setId(entity.getIdTStatut());
		
		dto.setCodeTStatut(entity.getCodeTStatut());
		dto.setLiblTStatut(entity.getLiblTStatut());
		dto.setDureeStatut(entity.getDureeStatut());
		dto.setDureeNbJoursStatut(entity.getDureeNbJoursStatut());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
