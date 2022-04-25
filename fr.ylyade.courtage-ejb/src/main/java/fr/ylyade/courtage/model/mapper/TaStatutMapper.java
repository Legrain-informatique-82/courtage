package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaStatutDTO;
import fr.ylyade.courtage.model.TaStatut;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaStatutMapper implements ILgrMapper<TaStatutDTO, TaStatut> {

	@Override
	public TaStatut mapDtoToEntity(TaStatutDTO dto, TaStatut entity) {
		if(entity==null)
			entity = new TaStatut();

		entity.setIdStatut(dto.getId()!=null?dto.getId():0);
		entity.setStatut(dto.getStatut());
		entity.setTaTStatut(dto.getTaTStatut());
		
		

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaStatutDTO mapEntityToDto(TaStatut entity, TaStatutDTO dto) {
		if(dto==null)
			dto = new TaStatutDTO();

		dto.setId(entity.getIdStatut());
		dto.setStatut(entity.getStatut());
		dto.setTaTStatut(entity.getTaTStatut());
		
		
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
