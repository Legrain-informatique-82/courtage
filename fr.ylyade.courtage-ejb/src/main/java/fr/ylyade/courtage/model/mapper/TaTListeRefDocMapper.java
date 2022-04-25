package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTListeRefDocDTO;
import fr.ylyade.courtage.model.TaTListeRefDoc;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTListeRefDocMapper implements ILgrMapper<TaTListeRefDocDTO, TaTListeRefDoc> {

	@Override
	public TaTListeRefDoc mapDtoToEntity(TaTListeRefDocDTO dto, TaTListeRefDoc entity) {
		if(entity==null)
			entity = new TaTListeRefDoc();

		entity.setIdTListeRefDoc(dto.getId()!=null?dto.getId():0);
		entity.setCodeTListeRefDoc(dto.getCodeTListeRefDoc());
		
		entity.setLiblTListeRefDoc(dto.getLiblTListeRefDoc());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTListeRefDocDTO mapEntityToDto(TaTListeRefDoc entity, TaTListeRefDocDTO dto) {
		if(dto==null)
			dto = new TaTListeRefDocDTO();

		dto.setId(entity.getIdTListeRefDoc());
		
		dto.setLiblTListeRefDoc(entity.getLiblTListeRefDoc());
		dto.setCodeTListeRefDoc(entity.getCodeTListeRefDoc());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
