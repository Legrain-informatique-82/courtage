package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaAccordTarifDTO;
import fr.ylyade.courtage.model.TaAccordTarif;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaAccordTarifMapper implements ILgrMapper<TaAccordTarifDTO, TaAccordTarif> {

	@Override
	public TaAccordTarif mapDtoToEntity(TaAccordTarifDTO dto, TaAccordTarif entity) {
		if(entity==null)
			entity = new TaAccordTarif();

		entity.setIdAccordeTarif(dto.getId()!=null?dto.getId():0);
		
		entity.setDateAccordTarif(dto.getDateAccordTarif());
		entity.setTaCourtier(dto.getTaCourtier());
		entity.setTaTGroupeTarif(dto.getTaTGroupeTarif());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaAccordTarifDTO mapEntityToDto(TaAccordTarif entity, TaAccordTarifDTO dto) {
		if(dto==null)
			dto = new TaAccordTarifDTO();

		dto.setId(entity.getIdAccordeTarif());
		
		dto.setDateAccordTarif(entity.getDateAccordTarif());
		dto.setTaCourtier(entity.getTaCourtier());
		dto.setTaTGroupeTarif(entity.getTaTGroupeTarif());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
