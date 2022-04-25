package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTRoleDTO;
import fr.ylyade.courtage.model.TaTRole;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTRoleMapper implements ILgrMapper<TaTRoleDTO, TaTRole> {

	@Override
	public TaTRole mapDtoToEntity(TaTRoleDTO dto, TaTRole entity) {
		if(entity==null)
			entity = new TaTRole();

		entity.setIdTRole(dto.getId()!=null?dto.getId():0);
		
		entity.setLiblTRole(dto.getLiblTRole());
		entity.setCodeTRole(dto.getCodeTRole());
		
		

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTRoleDTO mapEntityToDto(TaTRole entity, TaTRoleDTO dto) {
		if(dto==null)
			dto = new TaTRoleDTO();

		dto.setId(entity.getIdTRole());
		
		dto.setLiblTRole(entity.getLiblTRole());
		dto.setCodeTRole(entity.getCodeTRole());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
