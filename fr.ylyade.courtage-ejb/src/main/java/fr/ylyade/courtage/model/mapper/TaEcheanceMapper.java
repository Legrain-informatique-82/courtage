package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaEcheanceDTO;
import fr.ylyade.courtage.model.TaEcheance;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaEcheanceMapper implements ILgrMapper<TaEcheanceDTO, TaEcheance> {

	@Override
	public TaEcheance mapDtoToEntity(TaEcheanceDTO dto, TaEcheance entity) {
		if(entity==null)
			entity = new TaEcheance();

		entity.setIdEcheance(dto.getId()!=null?dto.getId():0);
		
		entity.setDateEcheance(dto.getDateEcheance());
		entity.setMontantEcheance(dto.getMontantEcheance());
		entity.setMajorationFrais(dto.getMajorationFrais());
		entity.setAvoir(dto.getAvoir());
		
//		entity.setTaContratRcProDetail(dto.getTaContratRcPro());
		entity.setTaReglementAssurance(dto.getTaReglementAssurance());
		entity.setTaContratDommageOuvrage(entity.getTaContratDommageOuvrage());
		entity.setTaContratGfa(dto.getTaContratGfa());
		
		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaEcheanceDTO mapEntityToDto(TaEcheance entity, TaEcheanceDTO dto) {
		if(dto==null)
			dto = new TaEcheanceDTO();

		dto.setId(entity.getIdEcheance());
		
		dto.setDateEcheance(entity.getDateEcheance());
		dto.setMontantEcheance(entity.getMontantEcheance());
		dto.setMajorationFrais(entity.getMajorationFrais());
		dto.setAvoir(entity.getAvoir());
		
//		dto.setTaContratRcPro(entity.getTaContratRcProDetail());
		dto.setTaReglementAssurance(entity.getTaReglementAssurance());
		dto.setTaContratDommageOuvrage(entity.getTaContratDommageOuvrage());
		dto.setTaContratGfa(entity.getTaContratGfa());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
