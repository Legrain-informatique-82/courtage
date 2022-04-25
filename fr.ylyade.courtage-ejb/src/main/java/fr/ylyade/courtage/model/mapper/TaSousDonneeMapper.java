package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaSousDonneeDTO;
import fr.ylyade.courtage.model.TaSousDonnee;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaSousDonneeMapper implements ILgrMapper<TaSousDonneeDTO, TaSousDonnee> {

	@Override
	public TaSousDonnee mapDtoToEntity(TaSousDonneeDTO dto, TaSousDonnee entity) {
		if(entity==null)
			entity = new TaSousDonnee();

		entity.setIdSousDonnee(dto.getIdSousDonnee()!=null?dto.getIdSousDonnee():0);
		entity.setJsonData(dto.getJsonData());
		entity.setTaDossierRcd(dto.getTaDossierRcd());


		return entity;
	}

	@Override
	public TaSousDonneeDTO mapEntityToDto(TaSousDonnee entity, TaSousDonneeDTO dto) {
		if(dto==null)
			dto = new TaSousDonneeDTO();

		dto.setIdSousDonnee(entity.getIdSousDonnee());
		dto.setJsonData(entity.getJsonData());
		dto.setTaDossierRcd(entity.getTaDossierRcd());


		return dto;
	}

}
