package fr.ylyade.courtage.droits.model.mapper;

import fr.ylyade.courtage.droits.model.TaUtilisateur;
import fr.ylyade.courtage.droits.model.dto.TaUtilisateurDTO;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaUtilisateurMapper implements ILgrMapper<TaUtilisateurDTO, TaUtilisateur> {

	@Override
	public TaUtilisateur mapDtoToEntity(TaUtilisateurDTO dto, TaUtilisateur entity) {
		if(entity==null)
			entity = new TaUtilisateur();

		entity.setIdUtilisateur(dto.getId()!=null?dto.getId():0);
		
		entity.setIdentifiant(dto.getIdentifiant());
		entity.setActif(dto.getActif());
//		entity.setAdminDossier(dto.getAdminDossier());
//		entity.setAutorisations(dto.getAutorisations());
//		entity.setDernierAcces(dto.getDernierAcces());
//		entity.setEmail(dto.getEmail());
//		entity.setNom(dto.getNom());
		entity.setPassword(dto.getPassword());
//		entity.setPrenom(dto.getPrenom());
		entity.setSysteme(dto.getSysteme());
		
//		entity.setTaEntreprise(dto.getCodeDocument());
//		entity.setRoles(dto.getCodeDocument());
		
		if(entity.getTaTUtilisateur()!=null) {
			entity.getTaTUtilisateur().setIdTUtilisateur(dto.getIdTUtilisateur());
			entity.getTaTUtilisateur().setCodeTUtilisateur(dto.getCodeTUtilisateur());
			entity.getTaTUtilisateur().setLiblTUtilisateur(dto.getLiblTUtilisateur());
		}
		
		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaUtilisateurDTO mapEntityToDto(TaUtilisateur entity, TaUtilisateurDTO dto) {
		if(dto==null)
			dto = new TaUtilisateurDTO();

		dto.setId(entity.getIdUtilisateur());
		
		dto.setIdentifiant(entity.getIdentifiant());
		dto.setActif(entity.getActif());
//		dto.setAdminDossier(entity.getAdminDossier());
//		dto.setAutorisations(entity.getAutorisations());
//		dto.setDernierAcces(entity.getDernierAcces());
//		dto.setEmail(entity.getEmail());
//		dto.setNom(entity.getNom());
		dto.setPassword(entity.getPassword());
//		dto.setPrenom(entity.getPrenom());
		dto.setSysteme(entity.getSysteme());
		
//		entity.setTaEntreprise(dto.getCodeDocument());
//		entity.setRoles(dto.getCodeDocument());
		
		if(entity.getTaTUtilisateur()!=null) {
			dto.setIdTUtilisateur(entity.getTaTUtilisateur().getIdTUtilisateur());
			dto.setCodeTUtilisateur(entity.getTaTUtilisateur().getCodeTUtilisateur());
			dto.setLiblTUtilisateur(entity.getTaTUtilisateur().getLiblTUtilisateur());
		}
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
