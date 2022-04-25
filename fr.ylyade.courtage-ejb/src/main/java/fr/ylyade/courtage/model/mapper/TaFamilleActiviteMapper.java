package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaFamilleActiviteDTO;
import fr.ylyade.courtage.model.TaFamilleActivite;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaFamilleActiviteMapper implements ILgrMapper<TaFamilleActiviteDTO, TaFamilleActivite> {

	@Override
	public TaFamilleActivite mapDtoToEntity(TaFamilleActiviteDTO dto, TaFamilleActivite entity) {
		if(entity==null)
			entity = new TaFamilleActivite();

		entity.setIdFamilleActivite(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeFamilleActivite(dto.getCodeFamilleActivite());
		entity.setLiblFamilleActivite(dto.getLiblFamilleActivite());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaFamilleActiviteDTO mapEntityToDto(TaFamilleActivite entity, TaFamilleActiviteDTO dto) {
		if(dto==null)
			dto = new TaFamilleActiviteDTO();

		dto.setId(entity.getIdFamilleActivite());
		
		dto.setCodeFamilleActivite(entity.getCodeFamilleActivite());
		dto.setLiblFamilleActivite(entity.getLiblFamilleActivite());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
