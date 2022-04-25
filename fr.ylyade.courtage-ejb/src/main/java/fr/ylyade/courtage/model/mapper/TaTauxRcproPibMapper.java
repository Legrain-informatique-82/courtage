package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTauxRcproPibDTO;
import fr.ylyade.courtage.model.TaTauxRcproPib;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTauxRcproPibMapper implements ILgrMapper<TaTauxRcproPibDTO, TaTauxRcproPib> {

	@Override
	public TaTauxRcproPib mapDtoToEntity(TaTauxRcproPibDTO dto, TaTauxRcproPib entity) {
		if(entity==null)
			entity = new TaTauxRcproPib();

		entity.setIdTauxRcproPib(dto.getId()!=null?dto.getId():0);
		entity.setLiblTauxRcproPib(dto.getLiblTauxRcproPib());
		entity.setCodeTauxRcproPib(dto.getCodeTauxRcproPib());
		entity.setTauxRcproPib(dto.getTauxRcproPib());
		entity.setDescriptionActivite(dto.getDescriptionActivite());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTauxRcproPibDTO mapEntityToDto(TaTauxRcproPib entity, TaTauxRcproPibDTO dto) {
		if(dto==null)
			dto = new TaTauxRcproPibDTO();

		dto.setId(entity.getIdTauxRcproPib());
		
		dto.setLiblTauxRcproPib(entity.getLiblTauxRcproPib());
		dto.setCodeTauxRcproPib(entity.getCodeTauxRcproPib());
		dto.setTauxRcproPib(entity.getTauxRcproPib());
		dto.setDescriptionActivite(entity.getDescriptionActivite());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
