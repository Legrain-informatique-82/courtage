package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTauxAssuranceDTO;
import fr.ylyade.courtage.model.TaTauxAssurance;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTauxAssuranceMapper implements ILgrMapper<TaTauxAssuranceDTO, TaTauxAssurance> {

	@Override
	public TaTauxAssurance mapDtoToEntity(TaTauxAssuranceDTO dto, TaTauxAssurance entity) {
		if(entity==null)
			entity = new TaTauxAssurance();

		entity.setIdTauxAssurance(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeTauxAssurance(dto.getCodeTauxAssurance());
		entity.setLibelleTauxAssurance(dto.getLibelleTauxAssurance());
		entity.setTauxTauxAssurance(dto.getTauxTauxAssurance());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTauxAssuranceDTO mapEntityToDto(TaTauxAssurance entity, TaTauxAssuranceDTO dto) {
		if(dto==null)
			dto = new TaTauxAssuranceDTO();

		dto.setId(entity.getIdTauxAssurance());
		
		if (entity.getTaTAssurance()!=null) {
			dto.setCodeTAssurance(entity.getTaTAssurance().getCodeTAssurance());
			dto.setIdTAssurance(entity.getTaTAssurance().getIdTAssurance());
			dto.setLiblTAssurance(entity.getTaTAssurance().getLiblTAssurance());
		}
		
		dto.setCodeTauxAssurance(entity.getCodeTauxAssurance());
		dto.setLibelleTauxAssurance(entity.getLibelleTauxAssurance());
		dto.setTauxTauxAssurance(entity.getTauxTauxAssurance());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
