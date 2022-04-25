//package fr.ylyade.courtage.model.mapper;
//
//import fr.ylyade.courtage.dto.TaPartSoustraitanceDTO;
//import fr.ylyade.courtage.model.TaPartSoustraitance;
//import fr.ylyade.courtage.model.mapping.ILgrMapper;
//
//
//public class TaPartSoustraitanceMapper implements ILgrMapper<TaPartSoustraitanceDTO, TaPartSoustraitance> {
//
//	@Override
//	public TaPartSoustraitance mapDtoToEntity(TaPartSoustraitanceDTO dto, TaPartSoustraitance entity) {
//		if(entity==null)
//			entity = new TaPartSoustraitance();
//
//		entity.setIdPartSousTraitance(dto.getId()!=null?dto.getId():0);
//		entity.setLiblPartSousTraitance(dto.getLiblPartSousTraitance());
//		entity.setCodePartSousTraitance(dto.getCodePartSousTraitance());
//		entity.setPartMax(dto.getPartMax());
//		entity.setPartMin(dto.getPartMin());
//		entity.setTauxPartSousTraitance(dto.getTauxPartSousTraitance());
//		
//
//		entity.setVersionObj(dto.getVersionObj());
//
//		return entity;
//	}
//
//	@Override
//	public TaPartSoustraitanceDTO mapEntityToDto(TaPartSoustraitance entity, TaPartSoustraitanceDTO dto) {
//		if(dto==null)
//			dto = new TaPartSoustraitanceDTO();
//
//		dto.setId(entity.getIdPartSousTraitance());
//		
//		dto.setLiblPartSousTraitance(entity.getLiblPartSousTraitance());
//		dto.setCodePartSousTraitance(entity.getCodePartSousTraitance());
//		dto.setPartMax(entity.getPartMax());
//		dto.setPartMin(entity.getPartMin());
//		dto.setTauxPartSousTraitance(entity.getTauxPartSousTraitance());
//		
//		
//		dto.setVersionObj(entity.getVersionObj());
//
//		return dto;
//	}
//
//}
