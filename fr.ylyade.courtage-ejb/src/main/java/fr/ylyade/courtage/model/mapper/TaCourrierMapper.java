package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaCourrierDTO;
import fr.ylyade.courtage.model.TaCourrier;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaCourrierMapper implements ILgrMapper<TaCourrierDTO, TaCourrier> {

	@Override
	public TaCourrier mapDtoToEntity(TaCourrierDTO dto, TaCourrier entity) {
		if(entity==null)
			entity = new TaCourrier();

		entity.setIdCourrier(dto.getId()!=null?dto.getId():0);
		
		entity.setCourrier(dto.getCourrier());
		entity.setLiblCourrier(dto.getLiblCourrier());
		entity.setDateEnvoi(dto.getDateEnvoi());
		entity.setTaTActionDoc(dto.getTaTActionDoc());
		
		entity.setNomFichier(dto.getNomFichier());
		entity.setTaille(dto.getTaille());
		entity.setTypeMime(dto.getTypeMime());
		
		

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaCourrierDTO mapEntityToDto(TaCourrier entity, TaCourrierDTO dto) {
		if(dto==null)
			dto = new TaCourrierDTO();

		dto.setId(entity.getIdCourrier());
		
		dto.setCourrier(entity.getCourrier());
		dto.setLiblCourrier(entity.getLiblCourrier());
		dto.setDateEnvoi(dto.getDateEnvoi());
		dto.setTaTActionDoc(dto.getTaTActionDoc());
		
		dto.setNomFichier(entity.getNomFichier());
		dto.setTaille(entity.getTaille());
		dto.setTypeMime(entity.getTypeMime());
		
		
		
		
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
