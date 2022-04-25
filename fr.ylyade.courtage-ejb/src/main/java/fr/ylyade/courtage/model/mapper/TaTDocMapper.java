package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTDocDTO;
import fr.ylyade.courtage.model.TaTDoc;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTDocMapper implements ILgrMapper<TaTDocDTO, TaTDoc> {

	@Override
	public TaTDoc mapDtoToEntity(TaTDocDTO dto, TaTDoc entity) {
		if(entity==null)
			entity = new TaTDoc();

		entity.setIdTDoc(dto.getId()!=null?dto.getId():0);
		entity.setCodeTDoc(dto.getCodeTDoc());
		entity.setLiblTDoc(dto.getLiblTDoc());
		entity.setDescriptionTDoc(dto.getDescriptionTDoc());
		

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTDocDTO mapEntityToDto(TaTDoc entity, TaTDocDTO dto) {
		if(dto==null)
			dto = new TaTDocDTO();

		dto.setId(entity.getIdTDoc());
		dto.setCodeTDoc(entity.getCodeTDoc());
		
		if (entity.getTaTAssurance()!=null) {
			dto.setCodeTAssurance(entity.getTaTAssurance().getCodeTAssurance());
			dto.setIdTAssurance(entity.getTaTAssurance().getIdTAssurance());
			dto.setLiblTAssurance(entity.getTaTAssurance().getLiblTAssurance());
		}
		dto.setLiblTDoc(entity.getLiblTDoc());
		dto.setDescriptionTDoc(entity.getDescriptionTDoc());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
