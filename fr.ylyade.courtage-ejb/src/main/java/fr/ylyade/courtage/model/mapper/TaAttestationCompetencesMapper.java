package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaAttestationCompetencesDTO;
import fr.ylyade.courtage.model.TaAttestationCompetences;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaAttestationCompetencesMapper implements ILgrMapper<TaAttestationCompetencesDTO, TaAttestationCompetences> {

	@Override
	public TaAttestationCompetences mapDtoToEntity(TaAttestationCompetencesDTO dto, TaAttestationCompetences entity) {
		if(entity==null)
			entity = new TaAttestationCompetences();

		entity.setIdAttestationCompetences(dto.getId()!=null?dto.getId():0);
		entity.setExEmployeurNom(dto.getExEmployeurNom());
		entity.setExEmployeurAdresse(dto.getExEmployeurAdresse());
		entity.setExEmploiQualite(dto.getExEmploiQualite());
		entity.setExEmploiActiviteExercee(dto.getExEmploiActiviteExercee());
		entity.setExEmployeurDateDebut(dto.getExEmployeurDateDebut());
		entity.setExEmployeurDateFin(dto.getExEmployeurDateFin());
		entity.setExEmployeurSiren(dto.getExEmployeurSiren());
		
		
		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaAttestationCompetencesDTO mapEntityToDto(TaAttestationCompetences entity, TaAttestationCompetencesDTO dto) {
		if(dto==null)
			dto = new TaAttestationCompetencesDTO();

		dto.setId(entity.getIdAttestationCompetences());
		dto.setExEmploiActiviteExercee(entity.getExEmploiActiviteExercee());
		dto.setExEmploiQualite(entity.getExEmploiQualite());
		dto.setExEmployeurAdresse(entity.getExEmployeurAdresse());
		dto.setExEmployeurDateDebut(entity.getExEmployeurDateDebut());
		dto.setExEmployeurDateFin(entity.getExEmployeurDateFin());
		dto.setExEmployeurNom(entity.getExEmployeurNom());
		dto.setExEmployeurSiren(entity.getExEmployeurSiren());
		
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
