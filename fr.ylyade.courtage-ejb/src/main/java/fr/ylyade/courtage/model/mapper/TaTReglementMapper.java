package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTReglementDTO;
import fr.ylyade.courtage.model.TaTReglement;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTReglementMapper implements ILgrMapper<TaTReglementDTO, TaTReglement> {

	@Override
	public TaTReglement mapDtoToEntity(TaTReglementDTO dto, TaTReglement entity) {
		if(entity==null)
			entity = new TaTReglement();

		entity.setIdTReglement(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeTReglement(dto.getCodeTReglement());
		entity.setLiblTReglement(dto.getLiblTReglement());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTReglementDTO mapEntityToDto(TaTReglement entity, TaTReglementDTO dto) {
		if(dto==null)
			dto = new TaTReglementDTO();

		dto.setId(entity.getIdTReglement());
		
		dto.setCodeTReglement(entity.getCodeTReglement());
		dto.setLiblTReglement(entity.getLiblTReglement());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
