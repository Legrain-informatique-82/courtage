package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTLotRealiseDTO;
import fr.ylyade.courtage.model.TaTLotRealise;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTLotRealiseMapper implements ILgrMapper<TaTLotRealiseDTO, TaTLotRealise> {

	@Override
	public TaTLotRealise mapDtoToEntity(TaTLotRealiseDTO dto, TaTLotRealise entity) {
		if(entity==null)
			entity = new TaTLotRealise();

		entity.setIdLotRealise(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeLotRealise(dto.getCodeLotRealise());
		entity.setLiblLotRealise(dto.getLiblLotRealise());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTLotRealiseDTO mapEntityToDto(TaTLotRealise entity, TaTLotRealiseDTO dto) {
		if(dto==null)
			dto = new TaTLotRealiseDTO();

		dto.setId(entity.getIdLotRealise());
		
		dto.setCodeLotRealise(entity.getCodeLotRealise());
		dto.setLiblLotRealise(entity.getLiblLotRealise());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
