package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaAdresseDTO;
import fr.ylyade.courtage.model.TaAdresse;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaAdresseMapper implements ILgrMapper<TaAdresseDTO, TaAdresse> {

	@Override
	public TaAdresse mapDtoToEntity(TaAdresseDTO dto, TaAdresse entity) {
		if(entity==null)
			entity = new TaAdresse();

		entity.setIdAdresse(dto.getId()!=null?dto.getId():0);
		
		entity.setAdresseLigne1(dto.getAdresseLigne1());
		entity.setAdresseLigne2(dto.getAdresseLigne2());
		entity.setAdresseLigne3(dto.getAdresseLigne3());
		entity.setCodePostal(dto.getCodePostal());
		entity.setVille(dto.getVille());
		entity.setPays(dto.getPays());
		entity.setTaTAdresse(dto.getTaTAdresse());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaAdresseDTO mapEntityToDto(TaAdresse entity, TaAdresseDTO dto) {
		if(dto==null)
			dto = new TaAdresseDTO();

		dto.setId(entity.getIdAdresse());
		
		dto.setAdresseLigne1(entity.getAdresseLigne1());
		dto.setAdresseLigne2(entity.getAdresseLigne2());
		dto.setAdresseLigne3(entity.getAdresseLigne3());
		dto.setCodePostal(entity.getCodePostal());
		dto.setVille(entity.getVille());
		dto.setPays(entity.getPays());
		dto.setTaTAdresse(entity.getTaTAdresse());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
