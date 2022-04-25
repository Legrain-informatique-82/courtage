package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaDocumentPdfDTO;
import fr.ylyade.courtage.model.TaDocumentPdf;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaDocumentPdfMapper implements ILgrMapper<TaDocumentPdfDTO, TaDocumentPdf> {

	@Override
	public TaDocumentPdf mapDtoToEntity(TaDocumentPdfDTO dto, TaDocumentPdf entity) {
		if(entity==null)
			entity = new TaDocumentPdf();

		entity.setIdDocumentPdf(dto.getId()!=null?dto.getId():0);
		entity.setCodeDoc(dto.getCodeDoc());
		entity.setDescription(dto.getDescription());
		entity.setImgDoc(dto.getImgDoc());
		entity.setNumeroVersion(dto.getNumeroVersion());
		entity.setLiblDoc(dto.getLiblDoc());
		entity.setTaTDoc(dto.getTaTDoc());
		entity.setPositionListe(dto.getPositionListe());
		
		
		entity.setNomFichier(dto.getNomFichier());
		entity.setTaille(dto.getTaille());
		entity.setTypeMime(dto.getTypeMime());
		
		
		

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaDocumentPdfDTO mapEntityToDto(TaDocumentPdf entity, TaDocumentPdfDTO dto) {
		if(dto==null)
			dto = new TaDocumentPdfDTO();

		dto.setId(entity.getIdDocumentPdf());
		dto.setCodeDoc(entity.getCodeDoc());
		dto.setDescription(entity.getDescription());
		dto.setImgDoc(entity.getImgDoc());
		dto.setNumeroVersion(entity.getNumeroVersion());
		dto.setLiblDoc(entity.getLiblDoc());
		dto.setTaTDoc(entity.getTaTDoc());
		dto.setPositionListe(entity.getPositionListe());
		
		dto.setNomFichier(entity.getNomFichier());
		dto.setTaille(entity.getTaille());
		dto.setTypeMime(entity.getTypeMime());
		
		
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
