package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaQuittanceDTO;
import fr.ylyade.courtage.model.TaQuittance;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaQuittanceMapper implements ILgrMapper<TaQuittanceDTO, TaQuittance> {

	@Override
	public TaQuittance mapDtoToEntity(TaQuittanceDTO dto, TaQuittance entity) {
		if(entity==null)
			entity = new TaQuittance();

		entity.setIdQuittance(dto.getId()!=null?dto.getId():0);
		
		entity.setCodePrestation(dto.getCodePrestation());
		entity.setLiblPrestation(dto.getLiblPrestation());
		entity.setMontant(dto.getMontant());
		entity.setImgQuittance(dto.getImgQuittance());
		entity.setTaReglementAssurance(dto.getTaReglementAssurance());
		entity.setTaReglementPrestation(dto.getTaReglementPrestation());
		
		entity.setNomFichier(dto.getNomFichier());
		entity.setTaille(dto.getTaille());
		entity.setTypeMime(dto.getTypeMime());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaQuittanceDTO mapEntityToDto(TaQuittance entity, TaQuittanceDTO dto) {
		if(dto==null)
			dto = new TaQuittanceDTO();

		dto.setId(entity.getIdQuittance());
		
		dto.setCodePrestation(entity.getCodePrestation());
		dto.setLiblPrestation(entity.getLiblPrestation());
		dto.setMontant(entity.getMontant());
		dto.setImgQuittance(entity.getImgQuittance());
		dto.setTaReglementAssurance(entity.getTaReglementAssurance());
		dto.setTaReglementPrestation(entity.getTaReglementPrestation());
		
		dto.setNomFichier(entity.getNomFichier());
		dto.setTaille(entity.getTaille());
		dto.setTypeMime(entity.getTypeMime());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
