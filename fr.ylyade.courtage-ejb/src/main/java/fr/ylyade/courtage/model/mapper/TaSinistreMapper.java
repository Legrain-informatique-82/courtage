package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaSinistreDTO;
import fr.ylyade.courtage.model.TaSinistre;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaSinistreMapper implements ILgrMapper<TaSinistreDTO, TaSinistre> {

	@Override
	public TaSinistre mapDtoToEntity(TaSinistreDTO dto, TaSinistre entity) {
		if(entity==null)
			entity = new TaSinistre();

		entity.setIdSinistre(dto.getId()!=null?dto.getId():0);
		entity.setRefSinistre(dto.getRefSinistre());
		entity.setDateSinistre(dto.getDateSinistre());
		entity.setDateDeclaration(dto.getDateDeclaration());
		
		entity.setCodeAssurance(dto.getCodeAssurance());
		entity.setCodeAssure(dto.getCodeAssure());
		entity.setCodeContrat(dto.getCodeContrat());
		
		entity.setLiblSinistre(dto.getLiblSinistre());
		entity.setDescription(dto.getDescription());
		entity.setAdresseconstruction(dto.getAdresseconstruction());
		entity.setObjetTravauxAssure(dto.getObjetTravauxAssure());
		entity.setCoordonneesClient(dto.getCoordonneesClient());
		entity.setRenovation(dto.getRenovation());
		entity.setExtension(dto.getExtension());
		entity.setConstructionNeuve(dto.getConstructionNeuve());
		entity.setTravauxEtudeRealAssure(dto.getTravauxEtudeRealAssure());
		entity.setDebutTravaux(dto.getDebutTravaux());
		entity.setDateReceptionTravaux(dto.getDateReceptionTravaux());
		entity.setDateReglDefinitifTravaux(dto.getDateReglDefinitifTravaux());
		entity.setDateAchevementTravaux(dto.getDateAchevementTravaux());
		entity.setCommentaireReserve(dto.getCommentaireReserve());
		entity.setAssuranceDommageSouscrite(dto.getAssuranceDommageSouscrite());
		entity.setNumContratDommageOuvrage(dto.getNumContratDommageOuvrage());
		entity.setCompagnieAssurance(dto.getCompagnieAssurance());
		entity.setAssurePasseContratNom(dto.getAssurePasseContratNom());
		entity.setAssurePasseContratAdresse(dto.getAssurePasseContratAdresse());
		entity.setAutresEntrepriseIntervenus(dto.getAutresEntrepriseIntervenus());
		entity.setContratPasseAvecSousTraitant(dto.getContratPasseAvecSousTraitant());
		entity.setDescriptionInterventionSousTraitant(dto.getDescriptionInterventionSousTraitant());
		entity.setAdresseSousTraitant(dto.getAdresseSousTraitant());
		entity.setAssuranceSousTraitanceNom(dto.getAssuranceSousTraitanceNom());
		entity.setAssuranceSousTraitanceAdresse(dto.getAssuranceSousTraitanceAdresse());
		entity.setNomPersonneSubitPrejudice(dto.getNomPersonneSubitPrejudice());
		entity.setAdressePersonneSubitPrejudice(dto.getNomPersonneSubitPrejudice());
		entity.setNatureDateDommages(dto.getNatureDateDommages());
		entity.setClotureSinistre(dto.getClotureSinistre());
		entity.setTaAssure(dto.getTaAssure());
		entity.setTaDossierRcd(dto.getTaDossierRcd());
		
		
	

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaSinistreDTO mapEntityToDto(TaSinistre entity, TaSinistreDTO dto) {
		if(dto==null)
			dto = new TaSinistreDTO();

		dto.setId(entity.getIdSinistre());
		
		dto.setLiblSinistre(entity.getLiblSinistre());
		dto.setDescription(entity.getDescription());
		dto.setAdresseconstruction(entity.getAdresseconstruction());
		dto.setObjetTravauxAssure(entity.getObjetTravauxAssure());
		dto.setCoordonneesClient(entity.getCoordonneesClient());
		dto.setRenovation(entity.getRenovation());
		dto.setExtension(entity.getExtension());
		dto.setConstructionNeuve(entity.getConstructionNeuve());
		dto.setTravauxEtudeRealAssure(entity.getTravauxEtudeRealAssure());
		dto.setDebutTravaux(entity.getDebutTravaux());
		dto.setDateReceptionTravaux(entity.getDateReceptionTravaux());
		dto.setDateReglDefinitifTravaux(entity.getDateReglDefinitifTravaux());
		dto.setDateAchevementTravaux(entity.getDateAchevementTravaux());
		dto.setCommentaireReserve(entity.getCommentaireReserve());
		dto.setAssuranceDommageSouscrite(entity.getAssuranceDommageSouscrite());
		dto.setNumContratDommageOuvrage(entity.getNumContratDommageOuvrage());
		dto.setCompagnieAssurance(entity.getCompagnieAssurance());
		dto.setAssurePasseContratNom(entity.getAssurePasseContratNom());
		dto.setAssurePasseContratAdresse(entity.getAssurePasseContratAdresse());
		dto.setAutresEntrepriseIntervenus(entity.getAutresEntrepriseIntervenus());
		dto.setContratPasseAvecSousTraitant(entity.getContratPasseAvecSousTraitant());
		dto.setDescriptionInterventionSousTraitant(entity.getDescriptionInterventionSousTraitant());
		dto.setAdresseSousTraitant(entity.getAdresseSousTraitant());
		dto.setAssuranceSousTraitanceNom(entity.getAssuranceSousTraitanceNom());
		dto.setAssuranceSousTraitanceAdresse(entity.getAssuranceSousTraitanceAdresse());
		dto.setNomPersonneSubitPrejudice(entity.getNomPersonneSubitPrejudice());
		dto.setAdressePersonneSubitPrejudice(entity.getNomPersonneSubitPrejudice());
		dto.setNatureDateDommages(entity.getNatureDateDommages());
		dto.setClotureSinistre(entity.getClotureSinistre());
		dto.setTaAssure(entity.getTaAssure());
		dto.setTaDossierRcd(entity.getTaDossierRcd());
		
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
