package fr.ylyade.courtage.droits.model.mapper;

import fr.ylyade.courtage.droits.model.TaClientLegrain;
import fr.ylyade.courtage.droits.model.dto.TaClientLegrainDTO;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaClientLegrainMapper implements ILgrMapper<TaClientLegrainDTO, TaClientLegrain> {

	@Override
	public TaClientLegrain mapDtoToEntity(TaClientLegrainDTO dto, TaClientLegrain entity) {
		if(entity==null)
			entity = new TaClientLegrain();

		entity.setId(dto.getId()!=null?dto.getId():0);
		
//		entity.setLibelleDocument(dto.getLibelleDocument());
//		entity.setCodeDocument(dto.getCodeDocument());
		
//		if(entity.getTaTAdr()!=null) entity.getTaTAdr().setCodeTAdr(dto.getCodeTAdr());
		
		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaClientLegrainDTO mapEntityToDto(TaClientLegrain entity, TaClientLegrainDTO dto) {
		if(dto==null)
			dto = new TaClientLegrainDTO();

		dto.setId(entity.getId());
		
//		dto.setLibelleDocument(entity.getLibelleDocument());
//		dto.setCodeDocument(entity.getCodeDocument());
		
//		if(entity.getTaTAdr()!=null) dto.setCodeTAdr(entity.getTaTAdr().getCodeTAdr());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
