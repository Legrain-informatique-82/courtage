package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaSinistreAntecedentDTO;
import fr.ylyade.courtage.model.TaSinistreAntecedent;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaSinistreAntecedentMapper implements ILgrMapper<TaSinistreAntecedentDTO, TaSinistreAntecedent> {

	@Override
	public TaSinistreAntecedent mapDtoToEntity(TaSinistreAntecedentDTO dto, TaSinistreAntecedent entity) {
		if(entity==null)
			entity = new TaSinistreAntecedent();

		entity.setIdSinistreAntecedent(dto.getId()!=null?dto.getId():0);
		
		entity.setDateSinistre(dto.getDateSinistre());
		entity.setTaDossierRcd(dto.getTaDevisRcPro());
		entity.setLiblSinistre(dto.getLiblSinistre());
		entity.setMontantSinistre(dto.getMontantSinistre());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaSinistreAntecedentDTO mapEntityToDto(TaSinistreAntecedent entity, TaSinistreAntecedentDTO dto) {
		if(dto==null)
			dto = new TaSinistreAntecedentDTO();

		dto.setId(entity.getIdSinistreAntecedent());
		dto.setDateSinistre(entity.getDateSinistre());
		dto.setTaDevisRcPro(entity.getTaDossierRcd());
		
		dto.setLiblSinistre(entity.getLiblSinistre());
		dto.setMontantSinistre(entity.getMontantSinistre());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
