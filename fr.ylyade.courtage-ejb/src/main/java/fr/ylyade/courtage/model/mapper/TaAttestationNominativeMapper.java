package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaAttestationNominativeDTO;
import fr.ylyade.courtage.model.TaAttestationNominative;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaAttestationNominativeMapper implements ILgrMapper<TaAttestationNominativeDTO, TaAttestationNominative> {

	@Override
	public TaAttestationNominative mapDtoToEntity(TaAttestationNominativeDTO dto, TaAttestationNominative entity) {
		if(entity==null)
			entity = new TaAttestationNominative();

		entity.setIdAttestationNominative(dto.getId()!=null?dto.getId():0);
		entity.setDateEffet(dto.getDateEffet());
		entity.setDateDebut(dto.getDateDebut());
		entity.setDateFin(dto.getDateFin());
		entity.setPoliceAssurance(dto.getPoliceAssurance());
		entity.setAdresseChantier(dto.getAdresseChantier());
		entity.setNomMaitreOuvrage(dto.getNomMaitreOuvrage());
		entity.setNatureOuvrage(dto.getNatureOuvrage());
		entity.setDateOuvertureChantier(dto.getDateOuvertureChantier());
		entity.setDateReceptionDefinitive(dto.getDateReceptionDefinitive());
		entity.setDateInterventionAssure(dto.getDateInterventionAssure());
		entity.setQualiteAssure(dto.getQualiteAssure());
		entity.setTravauxEffectue(dto.getTravauxEffectue());
		entity.setMontantMarcheHt(dto.getMontantMarcheHt());
		entity.setEffectifChantier(dto.getEffectifChantier());
		
//		entity.setCodeTarifPrestation(dto.getCodeTarifPrestation());
//		entity.setMontantPrestation(dto.getMontantPrestation());
		
		entity.setValidationCourtier(dto.isValidationCourtier());
		entity.setValidationYlyade(dto.isValidationYlyade());
		
		entity.setCoutTotalConstructionTtc(dto.getCoutTotalConstructionTtc());
		entity.setImgNominative(dto.getImgNominative());
		entity.setTaDossierRcd(dto.getTaDossierRcd());
		
		if(entity.getTaTarifPrestation()!=null) {
			entity.getTaTarifPrestation().setCodeTarifPrestation(dto.getCodeTarifPrestation());
			entity.getTaTarifPrestation().setMontantPrestation(dto.getMontantPrestation());
			entity.getTaTarifPrestation().setLiblPrestation(dto.getLiblPrestation());
		}
		
		entity.setNomFichier(dto.getNomFichier());
		entity.setTaille(dto.getTaille());
		entity.setTypeMime(dto.getTypeMime());
		
		entity.setMontantPaye(dto.getMontantPaye());
		
		
	
		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaAttestationNominativeDTO mapEntityToDto(TaAttestationNominative entity, TaAttestationNominativeDTO dto) {
		if(dto==null)
			dto = new TaAttestationNominativeDTO();

		dto.setId(entity.getIdAttestationNominative());
		dto.setDateEffet(entity.getDateEffet());
		dto.setDateDebut(entity.getDateDebut());
		dto.setDateFin(entity.getDateFin());
		dto.setPoliceAssurance(entity.getPoliceAssurance());
		dto.setAdresseChantier(entity.getAdresseChantier());
		dto.setNomMaitreOuvrage(entity.getNomMaitreOuvrage());
		dto.setNatureOuvrage(entity.getNatureOuvrage());
		dto.setDateOuvertureChantier(entity.getDateOuvertureChantier());
		dto.setDateReceptionDefinitive(entity.getDateReceptionDefinitive());
		dto.setDateInterventionAssure(entity.getDateInterventionAssure());
		dto.setQualiteAssure(entity.getQualiteAssure());
		dto.setTravauxEffectue(entity.getTravauxEffectue());
		dto.setMontantMarcheHt(entity.getMontantMarcheHt());
		dto.setEffectifChantier(entity.getEffectifChantier());
		
//		dto.setCodeTarifPrestation(entity.getCodeTarifPrestation());
//		dto.setMontantPrestation(entity.getMontantPrestation());
		
		dto.setValidationCourtier(entity.isValidationCourtier());
		dto.setValidationYlyade(entity.isValidationYlyade());
		
		dto.setCoutTotalConstructionTtc(entity.getCoutTotalConstructionTtc());
		dto.setImgNominative(entity.getImgNominative());
		dto.setTaDossierRcd(entity.getTaDossierRcd());
		
		dto.setNomFichier(entity.getNomFichier());
		dto.setTaille(entity.getTaille());
		dto.setTypeMime(entity.getTypeMime());
		
		dto.setMontantPaye(entity.getMontantPaye());
		
		if(entity.getTaTarifPrestation()!=null) dto.setMontantPrestation(entity.getTaTarifPrestation().getMontantPrestation());
		if(entity.getTaTarifPrestation()!=null) dto.setCodeTarifPrestation(entity.getTaTarifPrestation().getCodeTarifPrestation());
		if(entity.getTaTarifPrestation()!=null) dto.setLiblPrestation(entity.getTaTarifPrestation().getLiblPrestation());
		
		
		
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
