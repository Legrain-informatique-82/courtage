package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaAssureDTO;
import fr.ylyade.courtage.model.TaAssure;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaAssureMapper implements ILgrMapper<TaAssureDTO, TaAssure> {

	@Override
	public TaAssure mapDtoToEntity(TaAssureDTO dto, TaAssure entity) {
		if(entity==null)
			entity = new TaAssure();

		entity.setIdAssure(dto.getId()!=null?dto.getId():0);
		entity.setCodeAssure(dto.getCodeAssure());
		entity.setCodeSiret(dto.getCodeSiret());
		entity.setCodeSiren(dto.getCodeSiren());
		entity.setCodeApe(dto.getCodeApe());
		entity.setTvaIntraComm(dto.getTvaIntraComm());
		entity.setQualifications(dto.getQualifications());
		entity.setDiplome(dto.getDiplome());
		entity.setRaisonSociale(dto.getRaisonSociale());
		entity.setDateCreation(dto.getDateCreation());
		entity.setDateClotureBilan(dto.getDateClotureBilan());
		entity.setActif(dto.getActif());
		entity.setClient(dto.getClient());
		entity.setActivitePrincipale(dto.getActivitePrincipale());
		entity.setDateNaissance(dto.getDateNaissance());
		entity.setCapital(dto.getCapital());
		entity.setImatRegistreCommSocieteVille(dto.getImatRegistreCommSocieteVille());
		entity.setNumImat(dto.getNumImat());
//		entity.setChiffreAffaire(dto.getChiffreAffaire());
//		entity.setChiffreAffaireExerciceAnterieur(dto.getChiffreAffaireExerciceAnterieur());
		entity.setChiffreAffaireExercicePrevisionnel(dto.getChiffreAffaireExercicePrevisionnel());
		entity.setEffectifTotalExercice(dto.getEffectifTotalExercice());
//		entity.setEffectifTotalExerciceAnterieur(dto.getEffectifTotalExerciceAnterieur());
//		entity.setTaAttestationCompetences(dto.getTaAttestationCompetences());
		
//		entity.setNomFichier(dto.getNomFichier());
//		entity.setTaille(dto.getTaille());
//		entity.setTypeMime(dto.getTypeMime());
				
		if(entity.getTaTAssure()!=null) {
			entity.getTaTAssure().setIdTAssure(dto.getIdTAssure());
			entity.getTaTAssure().setCodeTAssure(dto.getCodeTAssure());
			entity.getTaTAssure().setLiblTAssure(dto.getLiblTAssure());
		}
		
		if(entity.getTaCourtier()!=null) {
			entity.getTaCourtier().setIdCourtier(dto.getIdCourtier());
			entity.getTaCourtier().setCodeCourtier(dto.getCodeCourtier());
			entity.getTaCourtier().setNomDenominationSociale(dto.getNomDenominationSociale());
		}
		
		if(entity.getTaUtilisateur()!=null) {
			entity.getTaUtilisateur().setIdUtilisateur(dto.getIdUtilisateur());
			entity.getTaUtilisateur().setIdentifiant(dto.getIdentifiant());
			entity.getTaUtilisateur().setPassword(dto.getPassword());
		}
		
		if(entity.getTaTJuridique()!=null) {
			entity.getTaTJuridique().setIdTJuridique(dto.getIdTJuridique());
			entity.getTaTJuridique().setCodeTJuridique(dto.getCodeTJuridique());
			entity.getTaTJuridique().setLiblTJuridique(dto.getLiblTJuridique());
		}
		
		if(entity.getTaContactEntreprise()!=null) {
			entity.getTaContactEntreprise().setIdContactEntreprise(dto.getIdContactEntreprise());
			entity.getTaContactEntreprise().setNom(dto.getNom());
			entity.getTaContactEntreprise().setPrenom(dto.getPrenom());
			entity.getTaContactEntreprise().setFonction(dto.getTitreFonction());
			
			if(entity.getTaContactEntreprise().getTaAdresse()!=null) {
				entity.getTaContactEntreprise().getTaAdresse().setIdAdresse(dto.getIdAdresse());
				entity.getTaContactEntreprise().getTaAdresse().setAdresseLigne1(dto.getAdresseLigne1());
				entity.getTaContactEntreprise().getTaAdresse().setAdresseLigne2(dto.getAdresseLigne2());
				entity.getTaContactEntreprise().getTaAdresse().setAdresseLigne3(dto.getAdresseLigne3());
				entity.getTaContactEntreprise().getTaAdresse().setCodePostal(dto.getCodePostal());
				entity.getTaContactEntreprise().getTaAdresse().setVille(dto.getVille());
				entity.getTaContactEntreprise().getTaAdresse().setPays(dto.getPays());
			}
			if(entity.getTaContactEntreprise().getTaTel()!=null) {
				entity.getTaContactEntreprise().getTaTel().setIdTel(dto.getIdTel());
				entity.getTaContactEntreprise().getTaTel().setPosteTel(dto.getPosteTel());	
				entity.getTaContactEntreprise().getTaTel().setNumeroTel(dto.getNumeroTel());	
			}
			if(entity.getTaContactEntreprise().getTaEmail()!=null) {
				entity.getTaContactEntreprise().getTaEmail().setIdEmail(dto.getIdEmail());
				entity.getTaContactEntreprise().getTaEmail().setAdresseEmail(dto.getAdresseEmail());
			}
			if(entity.getTaContactEntreprise().getTaTCivilite()!=null) {
				entity.getTaContactEntreprise().getTaTCivilite().setIdTCivilite(dto.getIdTCivilite());
				entity.getTaContactEntreprise().getTaTCivilite().setCodeTCivilite(dto.getCodeTCivilite());
				entity.getTaContactEntreprise().getTaTCivilite().setLiblTCivilite(dto.getLiblTCivilite());
			}
		}
		
		
		
		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaAssureDTO mapEntityToDto(TaAssure entity, TaAssureDTO dto) {
		if(dto==null)
			dto = new TaAssureDTO();

		dto.setId(entity.getIdAssure());
		dto.setCodeAssure(entity.getCodeAssure());
		dto.setCodeSiret(entity.getCodeSiret());
		dto.setCodeSiren(entity.getCodeSiren());
		dto.setCodeApe(entity.getCodeApe());
		dto.setTvaIntraComm(entity.getTvaIntraComm());
		dto.setQualifications(entity.getQualifications());
		dto.setDiplome(entity.getDiplome());
		dto.setRaisonSociale(entity.getDiplome());
		dto.setDateCreation(entity.getDateCreation());
		dto.setDateClotureBilan(entity.getDateClotureBilan());
		dto.setActif(entity.getActif());
		dto.setClient(entity.getClient());
		dto.setActivitePrincipale(entity.getActivitePrincipale());
		dto.setDateNaissance(entity.getDateNaissance());
		dto.setCapital(entity.getCapital());
		dto.setImatRegistreCommSocieteVille(entity.getImatRegistreCommSocieteVille());
		dto.setNumImat(entity.getNumImat());
//		dto.setChiffreAffaire(entity.getChiffreAffaire());
//		dto.setChiffreAffaireExerciceAnterieur(entity.getChiffreAffaireExerciceAnterieur());
		dto.setChiffreAffaireExercicePrevisionnel(entity.getChiffreAffaireExercicePrevisionnel());
		dto.setEffectifTotalExercice(entity.getEffectifTotalExercice());
//		dto.setEffectifTotalExerciceAnterieur(entity.getEffectifTotalExerciceAnterieur());

//		dto.setTaAttestationCompetences(entity.getTaAttestationCompetences());
		
//		dto.setNomFichier(entity.getNomFichier());
//		dto.setTaille(entity.getTaille());
//		dto.setTypeMime(entity.getTypeMime());
		
		if(entity.getTaTAssure()!=null) {
			dto.setIdTAssure(entity.getTaTAssure().getIdTAssure());
			dto.setCodeTAssure(entity.getTaTAssure().getCodeTAssure());
			dto.setLiblTAssure(entity.getTaTAssure().getLiblTAssure());
		}
		
		if(entity.getTaCourtier()!=null) {
			dto.setIdCourtier(entity.getTaCourtier().getIdCourtier());
			dto.setCodeCourtier(entity.getTaCourtier().getCodeCourtier());
			dto.setNomDenominationSociale(entity.getTaCourtier().getNomDenominationSociale());
			
		}
		
		if(entity.getTaUtilisateur()!=null) {
			dto.setIdUtilisateur(entity.getTaUtilisateur().getIdUtilisateur());
			dto.setIdentifiant(entity.getTaUtilisateur().getIdentifiant());
			dto.setPassword(entity.getTaUtilisateur().getPassword());
		}
		
		if(entity.getTaTJuridique()!=null) {
			dto.setIdTJuridique(entity.getTaTJuridique().getIdTJuridique());
			dto.setCodeTJuridique(entity.getTaTJuridique().getCodeTJuridique());
			dto.setLiblTJuridique(entity.getTaTJuridique().getLiblTJuridique());
		}
		
		if(entity.getTaContactEntreprise()!=null) {
			dto.setIdContactEntreprise(entity.getTaContactEntreprise().getIdContactEntreprise());
			dto.setNom(entity.getTaContactEntreprise().getNom());
			dto.setPrenom(entity.getTaContactEntreprise().getPrenom());
			dto.setTitreFonction(entity.getTaContactEntreprise().getFonction());
			
			if(entity.getTaContactEntreprise().getTaAdresse()!=null) {
				dto.setIdAdresse(entity.getTaContactEntreprise().getTaAdresse().getIdAdresse());
				dto.setAdresseLigne1(entity.getTaContactEntreprise().getTaAdresse().getAdresseLigne1());
				dto.setAdresseLigne2(entity.getTaContactEntreprise().getTaAdresse().getAdresseLigne2());
				dto.setAdresseLigne3(entity.getTaContactEntreprise().getTaAdresse().getAdresseLigne3());
				dto.setCodePostal(entity.getTaContactEntreprise().getTaAdresse().getCodePostal());
				dto.setVille(entity.getTaContactEntreprise().getTaAdresse().getVille());
				dto.setPays(entity.getTaContactEntreprise().getTaAdresse().getPays());
			}
			if(entity.getTaContactEntreprise().getTaTel()!=null) {
				dto.setIdTel(entity.getTaContactEntreprise().getTaTel().getIdTel());
				dto.setPosteTel(entity.getTaContactEntreprise().getTaTel().getPosteTel());	
				dto.setNumeroTel(entity.getTaContactEntreprise().getTaTel().getNumeroTel());	
			}
			if(entity.getTaContactEntreprise().getTaEmail()!=null) {
				dto.setIdEmail(entity.getTaContactEntreprise().getTaEmail().getIdEmail());
				dto.setAdresseEmail(entity.getTaContactEntreprise().getTaEmail().getAdresseEmail());
			}
			if(entity.getTaContactEntreprise().getTaTCivilite()!=null) {
				dto.setIdTCivilite(entity.getTaContactEntreprise().getTaTCivilite().getIdTCivilite());
				dto.setCodeTCivilite(entity.getTaContactEntreprise().getTaTCivilite().getCodeTCivilite());
				dto.setLiblTCivilite(entity.getTaContactEntreprise().getTaTCivilite().getLiblTCivilite());
			}
		}
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
