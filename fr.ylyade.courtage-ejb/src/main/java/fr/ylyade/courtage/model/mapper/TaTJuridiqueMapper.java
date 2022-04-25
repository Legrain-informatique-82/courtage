package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTJuridiqueDTO;
import fr.ylyade.courtage.model.TaTJuridique;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTJuridiqueMapper implements ILgrMapper<TaTJuridiqueDTO, TaTJuridique> {

	@Override
	public TaTJuridique mapDtoToEntity(TaTJuridiqueDTO dto, TaTJuridique entity) {
		if(entity==null)
			entity = new TaTJuridique();

		entity.setIdTJuridique(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeTJuridique(dto.getCodeTJuridique());
		entity.setLiblTJuridique(dto.getLiblTJuridique());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTJuridiqueDTO mapEntityToDto(TaTJuridique entity, TaTJuridiqueDTO dto) {
		if(dto==null)
			dto = new TaTJuridiqueDTO();

		dto.setId(entity.getIdTJuridique());
		
		dto.setCodeTJuridique(entity.getCodeTJuridique());
		dto.setLiblTJuridique(entity.getLiblTJuridique());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
