package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTActionDocDTO;
import fr.ylyade.courtage.model.TaTActionDoc;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTActionDocMapper implements ILgrMapper<TaTActionDocDTO, TaTActionDoc> {

	@Override
	public TaTActionDoc mapDtoToEntity(TaTActionDocDTO dto, TaTActionDoc entity) {
		if(entity==null)
			entity = new TaTActionDoc();

		entity.setIdTActionDoc(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeTActionDoc(dto.getCodeTActionDoc());
		entity.setLiblTActionDoc(dto.getLiblTActionDoc());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTActionDocDTO mapEntityToDto(TaTActionDoc entity, TaTActionDocDTO dto) {
		if(dto==null)
			dto = new TaTActionDocDTO();

		dto.setId(entity.getIdTActionDoc());
		
		dto.setCodeTActionDoc(entity.getCodeTActionDoc());
		dto.setLiblTActionDoc(entity.getLiblTActionDoc());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
