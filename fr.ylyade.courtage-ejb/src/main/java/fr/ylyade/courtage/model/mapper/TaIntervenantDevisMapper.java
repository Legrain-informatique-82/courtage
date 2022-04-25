package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaIntervenantDevisDTO;
import fr.ylyade.courtage.model.TaIntervenantDevis;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaIntervenantDevisMapper implements ILgrMapper<TaIntervenantDevisDTO, TaIntervenantDevis> {

	@Override
	public TaIntervenantDevis mapDtoToEntity(TaIntervenantDevisDTO dto, TaIntervenantDevis entity) {
		if(entity==null)
			entity = new TaIntervenantDevis();

		entity.setIdIntervenantDevis(dto.getId()!=null?dto.getId():0);
		
		entity.setAdresseEntreprise(dto.getAdresseEntreprise());
		entity.setAssureurDecennale(dto.getAssureurDecennale());
		entity.setCodeLot(dto.getCodeLot());
		entity.setNomEntreprise(dto.getNomEntreprise());
		entity.setNumContrat(dto.getNumContrat());
		entity.setNumSiret(dto.getNumSiret());
		entity.setTaProjetDommageOuvrage(dto.getTaProjetDommageOuvrage());
		entity.setTaTLotRealise(dto.getTaTLotRealise());
		
		

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaIntervenantDevisDTO mapEntityToDto(TaIntervenantDevis entity, TaIntervenantDevisDTO dto) {
		if(dto==null)
			dto = new TaIntervenantDevisDTO();

		dto.setId(entity.getIdIntervenantDevis());
		
		dto.setAdresseEntreprise(entity.getAdresseEntreprise());
		dto.setAssureurDecennale(entity.getAssureurDecennale());
		dto.setCodeLot(entity.getCodeLot());
		dto.setNomEntreprise(entity.getNomEntreprise());
		dto.setNumContrat(entity.getNumContrat());
		dto.setNumSiret(entity.getNumSiret());
		dto.setTaProjetDommageOuvrage(entity.getTaProjetDommageOuvrage());
		dto.setTaTLotRealise(entity.getTaTLotRealise());
		
		
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
