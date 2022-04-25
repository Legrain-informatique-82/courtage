package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaProjetGfaDTO;
import fr.ylyade.courtage.model.TaProjetGfa;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaProjetGfaMapper implements ILgrMapper<TaProjetGfaDTO, TaProjetGfa> {

	@Override
	public TaProjetGfa mapDtoToEntity(TaProjetGfaDTO dto, TaProjetGfa entity) {
		if(entity==null)
			entity = new TaProjetGfa();

		entity.setIdProjetGfa(dto.getId()!=null?dto.getId():0);
		
		entity.setNumProjetGfa(dto.getNumProjetGfa());
		entity.setFraisCourtier(dto.getFraisCourtier());
		entity.setTauxCommissionCourtier(dto.getTauxCommissionCourtier());
		entity.setMontantCommissionCourtier(dto.getMontantCommissionCourtier());
		entity.setPrimeAssuranceNue(dto.getPrimeAssuranceNue());
		entity.setImgProjetGfa(dto.getImgProjetGfa());
		entity.setTauxGfa(dto.getTauxGfa());
		entity.setMontantChantierGfa(dto.getMontantChantierGfa());
		entity.setDateRealisation(dto.getDateRealisation());
		entity.setCommuneAssietteFonciere(dto.getCommuneAssietteFonciere());
		entity.setNbBatiments(dto.getNbBatiments());
		entity.setNbLogements(dto.getNbLogements());
		entity.setUsageDe(dto.getUsageDe());
		entity.setDateDelivrance(dto.getDateDelivrance());
		entity.setBeneficiaire(dto.getBeneficiaire());
		entity.setNumDelivrance(dto.getNumDelivrance());
		entity.setSurfacePlancher(dto.getSurfacePlancher());
		entity.setNbTerrains(dto.getNbTerrains());
		entity.setNbCommerces(dto.getNbCommerces());
		entity.setDestination(dto.getDestination());
		entity.setPrescriptionsParticulieres(dto.getPrescriptionsParticulieres());
		entity.setPrixRevientPrevisionnel(dto.getPrixRevientPrevisionnel());
		entity.setPrixVentePrevisionnel(dto.getPrixVentePrevisionnel());
		entity.setMontantPreCommercialisation(dto.getMontantPreCommercialisation());
		entity.setTerrainCadastre(dto.getTerrainCadastre());
		
		entity.setDateFinValiditeProjet(dto.getDateFinValiditeProjet());
		
		if(entity.getTaAssure()!=null) {
			entity.getTaAssure().setIdAssure(dto.getIdAssure());
			entity.getTaAssure().setCodeAssure(dto.getCodeAssure());
		}
		
		entity.setNomFichier(dto.getNomFichier());
		entity.setTaille(dto.getTaille());
		entity.setTypeMime(dto.getTypeMime());
		
		
		
		entity.setTaContratGfa(dto.getTaContratGfa());
		entity.setTaTAssurance(dto.getTaTAssurance());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaProjetGfaDTO mapEntityToDto(TaProjetGfa entity, TaProjetGfaDTO dto) {
		if(dto==null)
			dto = new TaProjetGfaDTO();

		dto.setId(entity.getIdProjetGfa());
		
		dto.setNumProjetGfa(entity.getNumProjetGfa());
		dto.setFraisCourtier(entity.getFraisCourtier());
		dto.setTauxCommissionCourtier(entity.getTauxCommissionCourtier());
		dto.setMontantCommissionCourtier(entity.getMontantCommissionCourtier());
		dto.setPrimeAssuranceNue(entity.getPrimeAssuranceNue());
		dto.setImgProjetGfa(entity.getImgProjetGfa());
		dto.setTauxGfa(entity.getTauxGfa());
		dto.setMontantChantierGfa(entity.getMontantChantierGfa());
		dto.setDateRealisation(entity.getDateRealisation());
		dto.setCommuneAssietteFonciere(entity.getCommuneAssietteFonciere());
		dto.setNbBatiments(entity.getNbBatiments());
		dto.setNbLogements(entity.getNbLogements());
		dto.setUsageDe(entity.getUsageDe());
		dto.setDateDelivrance(entity.getDateDelivrance());
		dto.setBeneficiaire(entity.getBeneficiaire());
		dto.setNumDelivrance(entity.getNumDelivrance());
		dto.setSurfacePlancher(entity.getSurfacePlancher());
		dto.setNbTerrains(entity.getNbTerrains());
		dto.setNbCommerces(entity.getNbCommerces());
		dto.setDestination(entity.getDestination());
		dto.setPrescriptionsParticulieres(entity.getPrescriptionsParticulieres());
		dto.setPrixRevientPrevisionnel(entity.getPrixRevientPrevisionnel());
		dto.setPrixVentePrevisionnel(entity.getPrixVentePrevisionnel());
		dto.setMontantPreCommercialisation(entity.getMontantPreCommercialisation());
		dto.setTerrainCadastre(entity.getTerrainCadastre());
		
		dto.setDateFinValiditeProjet(entity.getDateFinValiditeProjet());
		
		if(entity.getTaAssure()!=null) {
			dto.setIdAssure(entity.getTaAssure().getIdAssure());
			dto.setCodeAssure(entity.getTaAssure().getCodeAssure());
		}
		
		
		dto.setNomFichier(entity.getNomFichier());
		dto.setTaille(entity.getTaille());
		dto.setTypeMime(entity.getTypeMime());
		
	
		dto.setTaContratGfa(entity.getTaContratGfa());
		dto.setTaTAssurance(entity.getTaTAssurance());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
