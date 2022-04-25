package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaGedUtilisateurDTO;
import fr.ylyade.courtage.model.TaGedUtilisateur;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaGedUtilisateurMapper implements ILgrMapper<TaGedUtilisateurDTO, TaGedUtilisateur> {

	@Override
	public TaGedUtilisateur mapDtoToEntity(TaGedUtilisateurDTO dto, TaGedUtilisateur entity) {
		if(entity==null)
			entity = new TaGedUtilisateur();

		entity.setIdGedUtilisateur(dto.getId()!=null?dto.getId():0);
		entity.setCommentaireCourtier(dto.getCommentaireCourtier());
		entity.setCommentaireYlyade(dto.getCommentaireYlyade());
		entity.setCommentaireSuperAssureur(dto.getCommentaireSuperAssureur());
		entity.setDateControleCourtier(dto.getDateControleCourtier());
		entity.setDateControleYlyade(dto.getDateControleYlyade());
		entity.setDateDepot(dto.getDateDepot());
		entity.setFichierDoc(dto.getFichierDoc());
		
		entity.setValidationCourtier(dto.getValidationCourtier());
		entity.setValidationYlyade(dto.getValidationYlyade());
		entity.setValidationSuperAssureur(dto.getValidationSuperAssureur());
		
		entity.setNomFichier(dto.getNomFichier());
		entity.setTaille(dto.getTaille());
		entity.setTypeMime(dto.getTypeMime());
		
//		if(entity.getTaAssure()!=null) {
//		entity.getTaAssure().setIdAssure(dto.getIdAssure());
//		entity.getTaAssure().setCodeAssure(dto.getCodeAssure());
//		}
		
		if(entity.getTaDossierRcd()!=null) {
			entity.getTaDossierRcd().setIdDossierRcd(dto.getIdDevisRcPro());
			entity.getTaDossierRcd().setNumDossierPolice(dto.getNumDevis());
			}
		
		if(entity.getTaCourtier()!=null) {
		entity.getTaCourtier().setIdCourtier(dto.getIdCourtier());
		entity.getTaCourtier().setCodeCourtier(dto.getCodeCourtier());
		
		}
		
		if(entity.getTaListeRefDoc()!=null) {
		entity.getTaListeRefDoc().setIdListeRefDoc(dto.getIdListeRefDoc());
		entity.getTaListeRefDoc().setCodeListeRefDoc(dto.getCodeListeRefDoc());
		entity.getTaListeRefDoc().setLiblDoc(dto.getLiblDoc());
		entity.getTaListeRefDoc().setDescription(dto.getDescription());
		entity.getTaListeRefDoc().setObligatoire(dto.getObligatoire());
			
		}

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaGedUtilisateurDTO mapEntityToDto(TaGedUtilisateur entity, TaGedUtilisateurDTO dto) {
		if(dto==null)
			dto = new TaGedUtilisateurDTO();

		dto.setId(entity.getIdGedUtilisateur());
		
		dto.setCommentaireCourtier(entity.getCommentaireCourtier());
		dto.setCommentaireYlyade(entity.getCommentaireYlyade());
		dto.setCommentaireSuperAssureur(entity.getCommentaireSuperAssureur());
		dto.setDateControleCourtier(entity.getDateControleCourtier());
		dto.setDateControleYlyade(entity.getDateControleYlyade());
		dto.setDateDepot(entity.getDateDepot());
		dto.setFichierDoc(entity.getFichierDoc());
		
		dto.setValidationCourtier(entity.getValidationCourtier());
		dto.setValidationYlyade(entity.getValidationYlyade());
		dto.setValidationSuperAssureur(entity.getValidationSuperAssureur());
		
		dto.setNomFichier(entity.getNomFichier());
		dto.setTaille(entity.getTaille());
		dto.setTypeMime(entity.getTypeMime());
		
		if(entity.getTaCourtier()!=null) {
		dto.setIdCourtier(entity.getTaCourtier().getIdCourtier());
		dto.setCodeCourtier(entity.getTaCourtier().getCodeCourtier());
		}
		
//		if(entity.getTaAssure()!=null) {
//		dto.setIdAssure(entity.getTaAssure().getIdAssure());
//		dto.setCodeAssure(entity.getTaAssure().getCodeAssure());
//		}
		
		
		if(entity.getTaDossierRcd()!=null) {
			dto.setIdDevisRcPro(entity.getTaDossierRcd().getIdDossierRcd());
			dto.setNumDevis(entity.getTaDossierRcd().getNumDossierPolice());
			}
		
		if(entity.getTaListeRefDoc()!=null) {
		dto.setIdListeRefDoc(entity.getTaListeRefDoc().getIdListeRefDoc());
		dto.setCodeListeRefDoc(entity.getTaListeRefDoc().getCodeListeRefDoc());
		dto.setLiblDoc(entity.getTaListeRefDoc().getLiblDoc());
		dto.setDescription(entity.getTaListeRefDoc().getDescription());
		dto.setObligatoire(entity.getTaListeRefDoc().getObligatoire());
		}
		
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
