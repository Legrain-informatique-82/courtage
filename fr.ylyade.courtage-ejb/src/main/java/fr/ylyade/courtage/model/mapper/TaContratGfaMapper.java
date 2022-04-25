package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaContratGfaDTO;
import fr.ylyade.courtage.model.TaContratGfa;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaContratGfaMapper implements ILgrMapper<TaContratGfaDTO, TaContratGfa> {

	@Override
	public TaContratGfa mapDtoToEntity(TaContratGfaDTO dto, TaContratGfa entity) {
		if(entity==null)
			entity = new TaContratGfa();

		entity.setIdContratGfa(dto.getId()!=null?dto.getId():0);
		
		entity.setFraisCourtier(dto.getFraisCourtier());
		entity.setTauxCommissionCourtier(dto.getTauxCommissionCourtier());
		entity.setMontantCommissionCourtier(dto.getMontantCommissionCourtier());
		entity.setPrimeAssuranceNue(dto.getPrimeAssuranceNue());
		entity.setImgContratGfa(dto.getImgContratGfa());
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
		entity.setNumPolice(dto.getNumPolice());
		entity.setContratActif(dto.getContratActif());
		entity.setDateSouscription(dto.getDateSouscription());
		entity.setDateRetractation(dto.getDateRetractation());
		entity.setRetractation(dto.getRetractation());
		entity.setTaProjetGfa(dto.getTaProjetGfa());
		entity.setTaEcheance(dto.getTaEcheance());
		
		entity.setDateFinContrat(dto.getDateFinContrat());
		
		
		if(entity.getTaAssure()!=null) {
			entity.getTaAssure().setIdAssure(dto.getIdAssure());
			entity.getTaAssure().setCodeAssure(dto.getCodeAssure());
		}
			
		
		entity.setNomFichier(dto.getNomFichier());
		entity.setTaille(dto.getTaille());
		entity.setTypeMime(dto.getTypeMime());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaContratGfaDTO mapEntityToDto(TaContratGfa entity, TaContratGfaDTO dto) {
		if(dto==null)
			dto = new TaContratGfaDTO();

		dto.setId(entity.getIdContratGfa());
		
		dto.setFraisCourtier(entity.getFraisCourtier());
		dto.setTauxCommissionCourtier(entity.getTauxCommissionCourtier());
		dto.setMontantCommissionCourtier(entity.getMontantCommissionCourtier());
		dto.setPrimeAssuranceNue(entity.getPrimeAssuranceNue());
		dto.setImgContratGfa(entity.getImgContratGfa());
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
		dto.setNumPolice(entity.getNumPolice());
		dto.setContratActif(entity.getContratActif());
		dto.setDateSouscription(entity.getDateSouscription());
		dto.setDateRetractation(entity.getDateRetractation());
		dto.setRetractation(entity.getRetractation());
		dto.setTaProjetGfa(entity.getTaProjetGfa());
		dto.setTaEcheance(entity.getTaEcheance());
		
		dto.setDateFinContrat(entity.getDateFinContrat());
		
		if(entity.getTaAssure()!=null) {
			dto.setIdAssure(entity.getTaAssure().getIdAssure());
			dto.setCodeAssure(entity.getTaAssure().getCodeAssure());
		}
		
		dto.setNomFichier(entity.getNomFichier());
		dto.setTaille(entity.getTaille());
		dto.setTypeMime(entity.getTypeMime());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
