package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaActiviteDTO;
import fr.ylyade.courtage.model.TaActivite;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaActiviteMapper implements ILgrMapper<TaActiviteDTO, TaActivite> {

	@Override
	public TaActivite mapDtoToEntity(TaActiviteDTO dto, TaActivite entity) {
		if(entity==null)
			entity = new TaActivite();

		entity.setIdActivite(dto.getId()!=null?dto.getId():0);
		entity.setLiblActivite(dto.getLiblActivite());
		entity.setCodeActivite(dto.getCodeActivite());
		entity.setActif(dto.getActif());
		
		entity.setDescriptionActivite(dto.getDescriptionActivite());
		entity.setPosition(dto.getPosition());
		entity.setMaxCa(dto.getMaxCa());
		entity.setMinCa(dto.getMinCa());
		
		entity.setCommentaireObligatoire(dto.getCommentaireObligatoire());
		
		entity.setPrimeBase(dto.getPrimeBase());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaActiviteDTO mapEntityToDto(TaActivite entity, TaActiviteDTO dto) {
		if(dto==null)
			dto = new TaActiviteDTO();

		dto.setId(entity.getIdActivite());
		
		if (entity.getTaFamilleActivite()!=null) {
			dto.setCodeFamilleActivite(entity.getTaFamilleActivite().getCodeFamilleActivite());
			dto.setIdFamilleActivite(entity.getTaFamilleActivite().getIdFamilleActivite());
			dto.setLiblFamilleActivite(entity.getTaFamilleActivite().getLiblFamilleActivite());
			
		}
		
		if (entity.getTaClasse()!=null) {
			dto.setCodeClasse(entity.getTaClasse().getCodeClasse());
			dto.setIdClasse(entity.getTaClasse().getIdClasse());
			dto.setLiblClasse(entity.getTaClasse().getLiblClasse());
		}
		
		if (entity.getTaTFranchise()!=null) {
//			dto.setCodeClasse(entity.getTaClasse().getCodeClasse());
//			dto.setIdClasse(entity.getTaClasse().getIdClasse());
//			dto.setLiblClasse(entity.getTaClasse().getLiblClasse());
		}
		
		dto.setPosition(entity.getPosition());
		dto.setDescriptionActivite(entity.getDescriptionActivite());
		dto.setMaxCa(entity.getMaxCa());
		dto.setMinCa(entity.getMinCa());
		dto.setPrimeBase(entity.getPrimeBase());
		
		dto.setLiblActivite(entity.getLiblActivite());
		dto.setCodeActivite(entity.getCodeActivite());
		dto.setActif(entity.getActif());
		
		dto.setCommentaireObligatoire(entity.getCommentaireObligatoire());
//		dto.setTaTFranchise(entity.getTaTFranchise());
		
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
