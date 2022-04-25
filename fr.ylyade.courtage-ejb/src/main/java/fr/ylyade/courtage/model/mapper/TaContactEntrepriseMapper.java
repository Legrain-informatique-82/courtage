package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaContactEntrepriseDTO;
import fr.ylyade.courtage.model.TaContactEntreprise;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaContactEntrepriseMapper implements ILgrMapper<TaContactEntrepriseDTO, TaContactEntreprise> {

	@Override
	public TaContactEntreprise mapDtoToEntity(TaContactEntrepriseDTO dto, TaContactEntreprise entity) {
		if(entity==null)
			entity = new TaContactEntreprise();

		entity.setIdContactEntreprise(dto.getId()!=null?dto.getId():0);
		entity.setNom(dto.getNom());
		entity.setPrenom(dto.getPrenom());
		entity.setFonction(dto.getFonction());
		entity.setTaTCivilite(dto.getTaTCivilite());
		entity.setTaCourtier(dto.getTaCourtier());
		entity.setTaAssure(dto.getTaAssure());
		
	

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaContactEntrepriseDTO mapEntityToDto(TaContactEntreprise entity, TaContactEntrepriseDTO dto) {
		if(dto==null)
			dto = new TaContactEntrepriseDTO();

		dto.setId(entity.getIdContactEntreprise());
		dto.setNom(entity.getNom());
		dto.setPrenom(entity.getPrenom());
		dto.setFonction(entity.getFonction());
		dto.setTaTCivilite(entity.getTaTCivilite());
		dto.setTaCourtier(entity.getTaCourtier());
		dto.setTaAssure(entity.getTaAssure());
		
		
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
