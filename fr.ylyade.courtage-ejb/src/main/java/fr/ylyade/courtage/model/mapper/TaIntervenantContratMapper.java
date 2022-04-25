package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaIntervenantContratDTO;
import fr.ylyade.courtage.model.TaIntervenantContrat;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaIntervenantContratMapper implements ILgrMapper<TaIntervenantContratDTO, TaIntervenantContrat> {

	@Override
	public TaIntervenantContrat mapDtoToEntity(TaIntervenantContratDTO dto, TaIntervenantContrat entity) {
		if(entity==null)
			entity = new TaIntervenantContrat();

		entity.setIdIntervenantContrat(dto.getId()!=null?dto.getId():0);
		
		entity.setAdresseEntreprise(dto.getAdresseEntreprise());
		entity.setAssureurDecennale(dto.getAssureurDecennale());
		entity.setCodeLot(dto.getCodeLot());
		entity.setNomEntreprise(dto.getNomEntreprise());
		entity.setNumContrat(dto.getNumContrat());
		entity.setNumSiret(dto.getNumSiret());
		entity.setTaContratDommageOuvrage(dto.getTaContratDommageOuvrage());
		entity.setTaTLotRealise(dto.getTaTLotRealise());
		
		

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaIntervenantContratDTO mapEntityToDto(TaIntervenantContrat entity, TaIntervenantContratDTO dto) {
		if(dto==null)
			dto = new TaIntervenantContratDTO();

		dto.setId(entity.getIdIntervenantContrat());
		
		dto.setAdresseEntreprise(entity.getAdresseEntreprise());
		dto.setAssureurDecennale(entity.getAssureurDecennale());
		dto.setCodeLot(entity.getCodeLot());
		dto.setNomEntreprise(entity.getNomEntreprise());
		dto.setNumContrat(entity.getNumContrat());
		dto.setNumSiret(entity.getNumSiret());
		dto.setTaContratDommageOuvrage(entity.getTaContratDommageOuvrage());
		dto.setTaTLotRealise(entity.getTaTLotRealise());
		
		
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
