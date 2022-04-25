package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTCiviliteDTO;
import fr.ylyade.courtage.model.TaTCivilite;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTCiviliteMapper implements ILgrMapper<TaTCiviliteDTO, TaTCivilite> {

	@Override
	public TaTCivilite mapDtoToEntity(TaTCiviliteDTO dto, TaTCivilite entity) {
		if(entity==null)
			entity = new TaTCivilite();

		entity.setIdTCivilite(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeTCivilite(dto.getCodeTCivilite());
		entity.setLiblTCivilite(dto.getLiblTCivilite());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTCiviliteDTO mapEntityToDto(TaTCivilite entity, TaTCiviliteDTO dto) {
		if(dto==null)
			dto = new TaTCiviliteDTO();

		dto.setId(entity.getIdTCivilite());
		
		dto.setCodeTCivilite(entity.getCodeTCivilite());
		dto.setLiblTCivilite(entity.getLiblTCivilite());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
