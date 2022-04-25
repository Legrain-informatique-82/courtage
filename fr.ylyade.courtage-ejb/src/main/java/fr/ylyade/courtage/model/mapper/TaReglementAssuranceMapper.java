package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaReglementAssuranceDTO;
import fr.ylyade.courtage.model.TaReglementAssurance;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaReglementAssuranceMapper implements ILgrMapper<TaReglementAssuranceDTO, TaReglementAssurance> {

	@Override
	public TaReglementAssurance mapDtoToEntity(TaReglementAssuranceDTO dto, TaReglementAssurance entity) {
		if(entity==null)
			entity = new TaReglementAssurance();

		entity.setIdReglementAssurance(dto.getId()!=null?dto.getId():0);
		
		entity.setRefReglement(dto.getRefReglement());
		entity.setValidationManuellePaiement(dto.getValidationPaiement());
		entity.setDefautPaiement(dto.getDefautPaiement());
		entity.setCodeTReglement(dto.getCodeTReglement());
		entity.setDateReglement(dto.getDateReglement());
		entity.setDateVirementEffectue(dto.getDateVirementEffectue());
		entity.setDateVirementRecu(dto.getDateVirementRecu());
		entity.setMontant(dto.getMontant());
		
		entity.setTaEcheance(dto.getTaEcheance());
		entity.setTaTReglement(dto.getTaTReglement());
		entity.setTaQuittance(dto.getTaQuittance());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaReglementAssuranceDTO mapEntityToDto(TaReglementAssurance entity, TaReglementAssuranceDTO dto) {
		if(dto==null)
			dto = new TaReglementAssuranceDTO();

		dto.setId(entity.getIdReglementAssurance());
		
		dto.setRefReglement(entity.getRefReglement());
		dto.setValidationPaiement(entity.getValidationManuellePaiement());
		dto.setDefautPaiement(entity.getDefautPaiement());
		dto.setCodeTReglement(entity.getCodeTReglement());
		dto.setDateReglement(entity.getDateReglement());
		dto.setMontant(entity.getMontant());
		dto.setDateVirementEffectue(entity.getDateVirementEffectue());
		dto.setDateVirementRecu(entity.getDateVirementRecu());
		
		String codeDossier = "";
		if(entity.getTaEcheance() != null) {
			if(entity.getTaEcheance().getTaDossierRcd() != null) {
				 codeDossier =entity.getTaEcheance().getTaDossierRcd().getNumDossierPolice();
				dto.setCodeDossier(codeDossier);
			
			}
		}
		
		

		
		dto.setTaEcheance(entity.getTaEcheance());
		dto.setTaTReglement(entity.getTaTReglement());
		dto.setTaQuittance(entity.getTaQuittance());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
