package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaReglementPrestationDTO;
import fr.ylyade.courtage.model.TaReglementPrestation;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaReglementPrestationMapper implements ILgrMapper<TaReglementPrestationDTO, TaReglementPrestation> {

	@Override
	public TaReglementPrestation mapDtoToEntity(TaReglementPrestationDTO dto, TaReglementPrestation entity) {
		if(entity==null)
			entity = new TaReglementPrestation();

		entity.setIdReglementPrestation(dto.getId()!=null?dto.getId():0);
		
		entity.setRefReglement(dto.getRefReglement());
		entity.setValidationPaiement(dto.getValidationPaiement());
		entity.setDefautPaiement(dto.getDefautPaiement());
//		entity.setCodeTReglement(dto.getCodeTReglement());
		
		if(entity.getTaTReglement() != null) {
			entity.getTaTReglement().setCodeTReglement(dto.getCodeTReglement());
		}
		entity.setDateReglement(dto.getDateReglement());
		entity.setMontant(dto.getMontant());
		
		entity.setTaEcheance(dto.getTaEcheance());
		entity.setTaTReglement(dto.getTaTReglement());
		entity.setTaHistoriquePrestation(dto.getTaHistoriquePrestation());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaReglementPrestationDTO mapEntityToDto(TaReglementPrestation entity, TaReglementPrestationDTO dto) {
		if(dto==null)
			dto = new TaReglementPrestationDTO();

		dto.setId(entity.getIdReglementPrestation());
		
		dto.setRefReglement(entity.getRefReglement());
		dto.setValidationPaiement(entity.getValidationPaiement());
		dto.setDefautPaiement(entity.getDefautPaiement());
//		dto.setCodeTReglement(entity.getCodeTReglement());
		dto.setDateReglement(entity.getDateReglement());
		dto.setMontant(entity.getMontant());
		
		if(entity.getTaTReglement() != null) {
			dto.setCodeTReglement(entity.getTaTReglement().getCodeTReglement());
		}
		
		dto.setTaEcheance(entity.getTaEcheance());
		dto.setTaTReglement(entity.getTaTReglement());
		dto.setTaHistoriquePrestation(entity.getTaHistoriquePrestation());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
