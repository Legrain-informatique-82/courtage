package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaFraisImpayeDTO;
import fr.ylyade.courtage.model.TaFraisImpaye;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaFraisImpayeMapper implements ILgrMapper<TaFraisImpayeDTO, TaFraisImpaye> {

	@Override
	public TaFraisImpaye mapDtoToEntity(TaFraisImpayeDTO dto, TaFraisImpaye entity) {
		if(entity==null)
			entity = new TaFraisImpaye();

		entity.setIdFraisImpaye(dto.getIdFraisImpaye()!=null?dto.getIdFraisImpaye():0);
		entity.setVersionObj(dto.getVersionObj());
		return entity;
	}

	@Override
	public TaFraisImpayeDTO mapEntityToDto(TaFraisImpaye entity, TaFraisImpayeDTO dto) {
		if(dto==null)
			dto = new TaFraisImpayeDTO();

		dto.setIdFraisImpaye(entity.getIdFraisImpaye());
		dto.setVersionObj(entity.getVersionObj());


		return dto;
	}

}
