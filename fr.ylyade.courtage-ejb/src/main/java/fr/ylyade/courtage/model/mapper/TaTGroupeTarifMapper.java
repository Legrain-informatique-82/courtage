package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTGroupeTarifDTO;
import fr.ylyade.courtage.model.TaTGroupeTarif;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTGroupeTarifMapper implements ILgrMapper<TaTGroupeTarifDTO, TaTGroupeTarif> {

	@Override
	public TaTGroupeTarif mapDtoToEntity(TaTGroupeTarifDTO dto, TaTGroupeTarif entity) {
		if(entity==null)
			entity = new TaTGroupeTarif();

		entity.setIdTGroupeTarif(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeGroupe(dto.getCodeGroupe());
		entity.setNomGroupe(dto.getNomGroupe());
		entity.setTauxGroupe(dto.getTauxGroupe());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTGroupeTarifDTO mapEntityToDto(TaTGroupeTarif entity, TaTGroupeTarifDTO dto) {
		if(dto==null)
			dto = new TaTGroupeTarifDTO();

		dto.setId(entity.getIdTGroupeTarif());
		dto.setNomGroupe(entity.getNomGroupe());
		dto.setTauxGroupe(entity.getTauxGroupe());
		
		dto.setCodeGroupe(entity.getCodeGroupe());
		
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
