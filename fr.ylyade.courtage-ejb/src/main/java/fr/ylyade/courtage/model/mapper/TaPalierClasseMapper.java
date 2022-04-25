package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaPalierClasseDTO;
import fr.ylyade.courtage.model.TaPalierClasse;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaPalierClasseMapper implements ILgrMapper<TaPalierClasseDTO, TaPalierClasse> {

	@Override
	public TaPalierClasse mapDtoToEntity(TaPalierClasseDTO dto, TaPalierClasse entity) {
		if(entity==null)
			entity = new TaPalierClasse();

		entity.setIdPalierClasse(dto.getId()!=null?dto.getId():0);
		entity.setMontantPrimeBase(dto.getMontantPrimeBase());
		entity.setPalierMontantMax(dto.getPalierMontantMax());
		entity.setPalierMontantMin(dto.getPalierMontantMin());	
		entity.setCodePalierClasse(dto.getCodePalierClasse());
		
		

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaPalierClasseDTO mapEntityToDto(TaPalierClasse entity, TaPalierClasseDTO dto) {
		if(dto==null)
			dto = new TaPalierClasseDTO();

		dto.setId(entity.getIdPalierClasse());
		
		if (entity.getTaClasse()!=null) {
			dto.setCodeClasse(entity.getTaClasse().getCodeClasse());
			dto.setIdClasse(entity.getTaClasse().getIdClasse());
			dto.setLiblClasse(entity.getTaClasse().getLiblClasse());
		}
		
		dto.setMontantPrimeBase(entity.getMontantPrimeBase());
		dto.setPalierMontantMax(entity.getPalierMontantMax());
		dto.setPalierMontantMin(entity.getPalierMontantMin());	
		dto.setCodePalierClasse(entity.getCodePalierClasse());
		
		
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
