package fr.ylyade.courtage.service.interfaces.remote;

import javax.ejb.Remote;

import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServer;
import fr.legrain.courtage.service.interfaces.remote.general.IAbstractLgrDAOServerDTO;
import fr.legrain.courtage.service.interfaces.remote.general.IGenericCRUDServiceRemote;
import fr.ylyade.courtage.dto.TaDocumentPdfDTO;
import fr.ylyade.courtage.model.TaDocumentPdf;


@Remote
public interface ITaDocumentPdfServiceRemote extends IGenericCRUDServiceRemote<TaDocumentPdf,TaDocumentPdfDTO>,
														IAbstractLgrDAOServer<TaDocumentPdf>,IAbstractLgrDAOServerDTO<TaDocumentPdfDTO>{
	public static final String validationContext = "DOCUMENT_PDF";

}
