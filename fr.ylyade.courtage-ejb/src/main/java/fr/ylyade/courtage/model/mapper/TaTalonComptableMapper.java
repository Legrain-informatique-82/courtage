package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTalonComptableDTO;
import fr.ylyade.courtage.model.TaTalonComptable;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTalonComptableMapper implements ILgrMapper<TaTalonComptableDTO, TaTalonComptable> {

	@Override
	public TaTalonComptable mapDtoToEntity(TaTalonComptableDTO dto, TaTalonComptable entity) {
		if(entity==null)
			entity = new TaTalonComptable();

		entity.setIdTalonComptable(dto.getId()!=null?dto.getId():0);
		
		entity.setCodePrestation(dto.getCodePrestation());
		entity.setLiblPrestation(dto.getLiblPrestation());
		entity.setMontant(dto.getMontant());
		entity.setImgTalonComptable(dto.getImgQuittance());
		entity.setTaReglementAssurance(dto.getTaReglementAssurance());
		entity.setTaReglementPrestation(dto.getTaReglementPrestation());
		
		entity.setNomFichier(dto.getNomFichier());
		entity.setTaille(dto.getTaille());
		entity.setTypeMime(dto.getTypeMime());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTalonComptableDTO mapEntityToDto(TaTalonComptable entity, TaTalonComptableDTO dto) {
		if(dto==null)
			dto = new TaTalonComptableDTO();

		dto.setId(entity.getIdTalonComptable());
		
		dto.setCodePrestation(entity.getCodePrestation());
		dto.setLiblPrestation(entity.getLiblPrestation());
		dto.setMontant(entity.getMontant());
		dto.setImgQuittance(entity.getImgTalonComptable());
		dto.setTaReglementAssurance(entity.getTaReglementAssurance());
		dto.setTaReglementPrestation(entity.getTaReglementPrestation());
		
		dto.setNomFichier(entity.getNomFichier());
		dto.setTaille(entity.getTaille());
		dto.setTypeMime(entity.getTypeMime());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
