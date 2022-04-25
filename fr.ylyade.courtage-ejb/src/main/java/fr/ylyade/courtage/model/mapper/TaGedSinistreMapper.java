package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaGedSinistreDTO;
import fr.ylyade.courtage.model.TaGedSinistre;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaGedSinistreMapper implements ILgrMapper<TaGedSinistreDTO, TaGedSinistre> {

	@Override
	public TaGedSinistre mapDtoToEntity(TaGedSinistreDTO dto, TaGedSinistre entity) {
		if(entity==null)
			entity = new TaGedSinistre();

		entity.setIdGedSinistre(dto.getId()!=null?dto.getId():0);
		
		entity.setImgDocSinistre(dto.getImgDocSinistre());
		entity.setRefSinistre(dto.getRefSinistre());
		entity.setCommentaire(dto.getCommentaire());
		entity.setTaTGedSinistre(dto.getTaTGedSinistre());
		entity.setTaSinistre(dto.getTaSinistre());
		
		entity.setNomFichier(dto.getNomFichier());
		entity.setTaille(dto.getTaille());
		entity.setTypeMime(dto.getTypeMime());
		
		

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaGedSinistreDTO mapEntityToDto(TaGedSinistre entity, TaGedSinistreDTO dto) {
		if(dto==null)
			dto = new TaGedSinistreDTO();

		dto.setId(entity.getIdGedSinistre());
		
		dto.setImgDocSinistre(entity.getImgDocSinistre());
		dto.setRefSinistre(entity.getRefSinistre());
		dto.setCommentaire(entity.getCommentaire());
		dto.setTaTGedSinistre(entity.getTaTGedSinistre());
		dto.setTaSinistre(entity.getTaSinistre());
		
		dto.setNomFichier(entity.getNomFichier());
		dto.setTaille(entity.getTaille());
		dto.setTypeMime(entity.getTypeMime());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
