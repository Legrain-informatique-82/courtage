package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTFraisImpayeDTO;
import fr.ylyade.courtage.model.TaTFraisImpaye;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTFraisImpayeMapper implements ILgrMapper<TaTFraisImpayeDTO, TaTFraisImpaye> {

	@Override
	public TaTFraisImpaye mapDtoToEntity(TaTFraisImpayeDTO dto, TaTFraisImpaye entity) {
		if(entity==null)
			entity = new TaTFraisImpaye();

		entity.setIdTFraisImpaye(dto.getId()!=null?dto.getId():0);
		
		entity.setLiblFactureFraisImpaye(dto.getLiblFactureFraisImpaye());
		entity.setMontant(dto.getMontant());
		entity.setCodeTFraisImpaye(dto.getCodeTFraisImpaye());
		

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTFraisImpayeDTO mapEntityToDto(TaTFraisImpaye entity, TaTFraisImpayeDTO dto) {
		if(dto==null)
			dto = new TaTFraisImpayeDTO();

		dto.setId(entity.getIdTFraisImpaye());
		dto.setCodeTFraisImpaye(entity.getCodeTFraisImpaye());
		dto.setLiblFactureFraisImpaye(entity.getLiblFactureFraisImpaye());
		dto.setMontant(entity.getMontant());
		
		
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
