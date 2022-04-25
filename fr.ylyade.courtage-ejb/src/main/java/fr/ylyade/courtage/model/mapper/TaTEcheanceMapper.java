package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTEcheanceDTO;
import fr.ylyade.courtage.model.TaTEcheance;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTEcheanceMapper implements ILgrMapper<TaTEcheanceDTO, TaTEcheance> {

	@Override
	public TaTEcheance mapDtoToEntity(TaTEcheanceDTO dto, TaTEcheance entity) {
		if(entity==null)
			entity = new TaTEcheance();

		entity.setIdTEcheance(dto.getId()!=null?dto.getId():0);
		
		entity.setTauxEcheance(dto.getTauxEcheance());
		entity.setCodeTEcheance(dto.getCodeTEcheance());
		entity.setLiblTEcheance(dto.getLiblTEcheance());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTEcheanceDTO mapEntityToDto(TaTEcheance entity, TaTEcheanceDTO dto) {
		if(dto==null)
			dto = new TaTEcheanceDTO();

		dto.setId(entity.getIdTEcheance());
		
		dto.setTauxEcheance(entity.getTauxEcheance());
		dto.setLiblTEcheance(entity.getLiblTEcheance());
		dto.setCodeTEcheance(entity.getCodeTEcheance());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
