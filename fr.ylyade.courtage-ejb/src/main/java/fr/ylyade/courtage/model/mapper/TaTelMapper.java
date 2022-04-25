package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTelDTO;
import fr.ylyade.courtage.model.TaTel;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTelMapper implements ILgrMapper<TaTelDTO, TaTel> {

	@Override
	public TaTel mapDtoToEntity(TaTelDTO dto, TaTel entity) {
		if(entity==null)
			entity = new TaTel();

		entity.setIdTel(dto.getId()!=null?dto.getId():0);
		
		entity.setNumeroTel(dto.getNumeroTel());
		entity.setPosteTel(dto.getPosteTel());
		entity.setTaContactEntreprise(dto.getTaContactEntreprise());
		
		

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTelDTO mapEntityToDto(TaTel entity, TaTelDTO dto) {
		if(dto==null)
			dto = new TaTelDTO();

		dto.setId(entity.getIdTel());
		
		dto.setNumeroTel(entity.getNumeroTel());
		dto.setPosteTel(entity.getPosteTel());
		dto.setTaContactEntreprise(entity.getTaContactEntreprise());
		
		
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
