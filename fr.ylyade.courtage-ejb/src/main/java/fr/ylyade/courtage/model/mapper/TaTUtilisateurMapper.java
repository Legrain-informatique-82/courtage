package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTUtilisateurDTO;
import fr.ylyade.courtage.model.TaTUtilisateur;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTUtilisateurMapper implements ILgrMapper<TaTUtilisateurDTO, TaTUtilisateur> {

	@Override
	public TaTUtilisateur mapDtoToEntity(TaTUtilisateurDTO dto, TaTUtilisateur entity) {
		if(entity==null)
			entity = new TaTUtilisateur();

		entity.setIdTUtilisateur(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeTUtilisateur(dto.getCodeTUtilisateur());
		entity.setLiblTUtilisateur(dto.getLiblTUtilisateur());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTUtilisateurDTO mapEntityToDto(TaTUtilisateur entity, TaTUtilisateurDTO dto) {
		if(dto==null)
			dto = new TaTUtilisateurDTO();

		dto.setId(entity.getIdTUtilisateur());
		
		dto.setCodeTUtilisateur(entity.getCodeTUtilisateur());
		dto.setLiblTUtilisateur(entity.getLiblTUtilisateur());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
