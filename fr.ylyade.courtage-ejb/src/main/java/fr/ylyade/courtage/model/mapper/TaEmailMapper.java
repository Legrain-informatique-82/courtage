package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaEmailDTO;
import fr.ylyade.courtage.model.TaEmail;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaEmailMapper implements ILgrMapper<TaEmailDTO, TaEmail> {

	@Override
	public TaEmail mapDtoToEntity(TaEmailDTO dto, TaEmail entity) {
		if(entity==null)
			entity = new TaEmail();

		entity.setIdEmail(dto.getId()!=null?dto.getId():0);
		entity.setAdresseEmail(dto.getAdresseEmail());
		entity.setTaContactEntreprise(dto.getTaContactEntreprise());
		entity.setTaTEmail(dto.getTaTEmail());
		
		entity.setNomFichier(dto.getNomFichier());
		entity.setTaille(dto.getTaille());
		entity.setTypeMime(dto.getTypeMime());
		
		

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaEmailDTO mapEntityToDto(TaEmail entity, TaEmailDTO dto) {
		if(dto==null)
			dto = new TaEmailDTO();

		dto.setId(entity.getIdEmail());
		dto.setAdresseEmail(entity.getAdresseEmail());
		dto.setTaContactEntreprise(entity.getTaContactEntreprise());
		dto.setTaTEmail(entity.getTaTEmail());
		
		dto.setNomFichier(entity.getNomFichier());
		dto.setTaille(entity.getTaille());
		dto.setTypeMime(entity.getTypeMime());
		
		
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
