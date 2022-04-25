package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaClasseDTO;
import fr.ylyade.courtage.model.TaClasse;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaClasseMapper implements ILgrMapper<TaClasseDTO, TaClasse> {

	@Override
	public TaClasse mapDtoToEntity(TaClasseDTO dto, TaClasse entity) {
		if(entity==null)
			entity = new TaClasse();

		entity.setIdClasse(dto.getId()!=null?dto.getId():0);
		entity.setCodeClasse(dto.getCodeClasse());
		entity.setLiblClasse(dto.getLiblClasse());
		entity.setCodeClasse(dto.getCodeClasse());
		
		

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaClasseDTO mapEntityToDto(TaClasse entity, TaClasseDTO dto) {
		if(dto==null)
			dto = new TaClasseDTO();

		dto.setId(entity.getIdClasse());
		dto.setCodeClasse(entity.getCodeClasse());
		dto.setLiblClasse(entity.getLiblClasse());
		dto.setCodeClasse(entity.getCodeClasse());

		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
