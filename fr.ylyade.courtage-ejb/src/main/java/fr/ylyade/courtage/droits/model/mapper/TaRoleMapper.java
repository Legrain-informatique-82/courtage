package fr.ylyade.courtage.droits.model.mapper;

import fr.ylyade.courtage.droits.model.TaRole;
import fr.ylyade.courtage.droits.model.dto.TaRoleDTO;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaRoleMapper implements ILgrMapper<TaRoleDTO, TaRole> {

	@Override
	public TaRole mapDtoToEntity(TaRoleDTO dto, TaRole entity) {
		if(entity==null)
			entity = new TaRole();

		entity.setIdRoles(dto.getId()!=null?dto.getId():0);
		
		entity.setLiblRole(dto.getLiblRole());
		entity.setDescription(dto.getDescription());
		
//		if(entity.getTaTAdr()!=null) entity.getTaTAdr().setCodeTAdr(dto.getCodeTAdr());
		
		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaRoleDTO mapEntityToDto(TaRole entity, TaRoleDTO dto) {
		if(dto==null)
			dto = new TaRoleDTO();

		dto.setId(entity.getIdRoles());
		
		dto.setLiblRole(entity.getLiblRole());
		dto.setDescription(entity.getDescription());
		
//		if(entity.getTaTAdr()!=null) dto.setCodeTAdr(entity.getTaTAdr().getCodeTAdr());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
