package fr.ylyade.courtage.droits.model.mapper;

import fr.ylyade.courtage.droits.model.TaAuthURL;
import fr.ylyade.courtage.droits.model.dto.TaAuthURLDTO;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaAuthURLMapper implements ILgrMapper<TaAuthURLDTO, TaAuthURL> {

	@Override
	public TaAuthURL mapDtoToEntity(TaAuthURLDTO dto, TaAuthURL entity) {
		if(entity==null)
			entity = new TaAuthURL();

		entity.setId(dto.getId()!=null?dto.getId():0);
		
//		entity.setLibelleDocument(dto.getLibelleDocument());
//		entity.setCodeDocument(dto.getCodeDocument());
		
//		if(entity.getTaTAdr()!=null) entity.getTaTAdr().setCodeTAdr(dto.getCodeTAdr());
		
		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaAuthURLDTO mapEntityToDto(TaAuthURL entity, TaAuthURLDTO dto) {
		if(dto==null)
			dto = new TaAuthURLDTO();

		dto.setId(entity.getId());
		
//		dto.setLibelleDocument(entity.getLibelleDocument());
//		dto.setCodeDocument(entity.getCodeDocument());
		
//		if(entity.getTaTAdr()!=null) dto.setCodeTAdr(entity.getTaTAdr().getCodeTAdr());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
