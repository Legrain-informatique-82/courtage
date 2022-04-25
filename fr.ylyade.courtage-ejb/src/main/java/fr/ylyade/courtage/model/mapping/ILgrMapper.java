package fr.ylyade.courtage.model.mapping;

import fr.ylyade.courtage.dto.ModelObject;

public interface ILgrMapper<DTO extends ModelObject,Entity> {
	//public DTO entityToDto(Entity entity);
	//public Entity dtoToEntity(DTO dto);
	public Entity mapDtoToEntity(DTO dto,Entity entity);
	public DTO mapEntityToDto(Entity entity, DTO dto);
	
}
