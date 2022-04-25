package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaListeRefDocDTO;
import fr.ylyade.courtage.model.TaListeRefDoc;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaListeRefDocMapper implements ILgrMapper<TaListeRefDocDTO, TaListeRefDoc> {

	@Override
	public TaListeRefDoc mapDtoToEntity(TaListeRefDocDTO dto, TaListeRefDoc entity) {
		if(entity==null)
			entity = new TaListeRefDoc();

		entity.setIdListeRefDoc(dto.getId()!=null?dto.getId():0);
		entity.setDescription(dto.getDescription());
		
		entity.setCodeListeRefDoc(dto.getCodeListeRefDoc());
		entity.setActif(dto.getActif());
		entity.setObligatoire(dto.getObligatoire());
		
		
		
		entity.setLiblDoc(dto.getLiblDoc());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaListeRefDocDTO mapEntityToDto(TaListeRefDoc entity, TaListeRefDocDTO dto) {
		if(dto==null)
			dto = new TaListeRefDocDTO();

		dto.setId(entity.getIdListeRefDoc());
		dto.setDescription(entity.getDescription());
		dto.setActif(entity.getActif());
		dto.setObligatoire(entity.getObligatoire());
		
		dto.setCodeListeRefDoc(entity.getCodeListeRefDoc());
		
		if (entity.getTaTListeRefDoc()!=null) {
			dto.setCodeTListeRefDoc(entity.getTaTListeRefDoc().getCodeTListeRefDoc());
			dto.setIdTListeRefDoc(entity.getTaTListeRefDoc().getIdTListeRefDoc());
			dto.setLiblTListeRefDoc(entity.getTaTListeRefDoc().getLiblTListeRefDoc());
		}
		
		
		dto.setLiblDoc(entity.getLiblDoc());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
