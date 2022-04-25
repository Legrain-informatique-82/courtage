package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTarifPrestationDTO;
import fr.ylyade.courtage.model.TaTarifPrestation;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTarifPrestationMapper implements ILgrMapper<TaTarifPrestationDTO, TaTarifPrestation> {

	@Override
	public TaTarifPrestation mapDtoToEntity(TaTarifPrestationDTO dto, TaTarifPrestation entity) {
		if(entity==null)
			entity = new TaTarifPrestation();

		entity.setIdTarifPrestation(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeTarifPrestation(dto.getCodeTarifPrestation());
		entity.setLiblPrestation(dto.getLiblPrestation());
		entity.setMontantPrestation(dto.getMontantPrestation());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTarifPrestationDTO mapEntityToDto(TaTarifPrestation entity, TaTarifPrestationDTO dto) {
		if(dto==null)
			dto = new TaTarifPrestationDTO();

		dto.setId(entity.getIdTarifPrestation());
		
		dto.setCodeTarifPrestation(entity.getCodeTarifPrestation());
		dto.setLiblPrestation(entity.getLiblPrestation());
		dto.setMontantPrestation(entity.getMontantPrestation());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
