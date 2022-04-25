package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTMaitriseOeuvreDTO;
import fr.ylyade.courtage.model.TaTMaitriseOeuvre;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTMaitriseOeuvreMapper implements ILgrMapper<TaTMaitriseOeuvreDTO, TaTMaitriseOeuvre> {

	@Override
	public TaTMaitriseOeuvre mapDtoToEntity(TaTMaitriseOeuvreDTO dto, TaTMaitriseOeuvre entity) {
		if(entity==null)
			entity = new TaTMaitriseOeuvre();

		entity.setIdTMaitriseOeuvre(dto.getId()!=null?dto.getId():0);
		
		entity.setLiblNature(dto.getLiblNature());
		entity.setLiblTitre(dto.getLiblTitre());
		entity.setCodeTMaitriseOeuvre(dto.getCodeTMaitriseOeuvre());
		
		

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTMaitriseOeuvreDTO mapEntityToDto(TaTMaitriseOeuvre entity, TaTMaitriseOeuvreDTO dto) {
		if(dto==null)
			dto = new TaTMaitriseOeuvreDTO();

		dto.setId(entity.getIdTMaitriseOeuvre());
		
		dto.setLiblNature(entity.getLiblNature());
		dto.setLiblTitre(entity.getLiblTitre());
		dto.setCodeTMaitriseOeuvre(entity.getCodeTMaitriseOeuvre());
	
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
