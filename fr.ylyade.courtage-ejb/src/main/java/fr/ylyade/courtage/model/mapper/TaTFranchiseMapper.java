package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaTFranchiseDTO;
import fr.ylyade.courtage.model.TaTFranchise;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaTFranchiseMapper implements ILgrMapper<TaTFranchiseDTO, TaTFranchise> {

	@Override
	public TaTFranchise mapDtoToEntity(TaTFranchiseDTO dto, TaTFranchise entity) {
		if(entity==null)
			entity = new TaTFranchise();

		entity.setIdTFranchise(dto.getId()!=null?dto.getId():0);
		
		entity.setCodeTFranchise(dto.getCodeTFranchise());
		entity.setLiblTFranchise(dto.getLiblTFranchise());
		entity.setMontant(dto.getMontant());
		entity.setTauxMontant(dto.getTauxMontant());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaTFranchiseDTO mapEntityToDto(TaTFranchise entity, TaTFranchiseDTO dto) {
		if(dto==null)
			dto = new TaTFranchiseDTO();

		dto.setId(entity.getIdTFranchise());
		
		dto.setCodeTFranchise(entity.getCodeTFranchise());
		dto.setLiblTFranchise(entity.getLiblTFranchise());
		dto.setMontant(entity.getMontant());
		dto.setTauxMontant(entity.getTauxMontant());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
