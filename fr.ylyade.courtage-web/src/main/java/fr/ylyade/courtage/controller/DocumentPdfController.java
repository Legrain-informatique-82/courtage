package fr.ylyade.courtage.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import fr.legrain.lib.data.ModeObjetEcranServeur;
import fr.ylyade.courtage.app.AbstractController;
import fr.ylyade.courtage.app.TabListModelBean;
import fr.ylyade.courtage.app.TabViewsBean;
import fr.ylyade.courtage.model.TaDocumentPdf;
import fr.ylyade.courtage.model.TaTFranchise;
import fr.ylyade.courtage.service.interfaces.remote.ITaDocumentPdfServiceRemote;


@ManagedBean
@ViewScoped
public class DocumentPdfController extends AbstractController {
	
	@ManagedProperty(value="#{tabListModelCenterBean}")
	private TabListModelBean tabsCenter;
	
	@ManagedProperty(value="#{tabViewsBean}")
	private TabViewsBean tabViews;
	
	
	protected ModeObjetEcranServeur modeEcran = new ModeObjetEcranServeur();
	
	private @EJB ITaDocumentPdfServiceRemote taDocumentPdfService;
	
	private List<TaDocumentPdf> values; 
	private StreamedContent file;
	private TaDocumentPdf selectedDoc;
	
	public DocumentPdfController() {}
	
	@PostConstruct
	public void postConstruct(){
		List<TaDocumentPdf> liste = taDocumentPdfService.selectAll();
		liste.sort(Comparator.comparing(TaDocumentPdf::getPositionListe));
		values = liste;
	}
	
//	public List<TaDocumentPdf> listeDoc() {
//		values = taDocumentPdfService.selectAll();
//		return values;
//	}
	


	public StreamedContent getFile() {
		
//		TaGedUtilisateurDTO taGedUtilisateur =  taGedUtilisateurService.findByIdDocAndByIdCourtierDTO(selectedTaListeRefDocDTO.getId(), masterEntity.getIdCourtier());
//
//		InputStream stream = new ByteArrayInputStream(taGedUtilisateur.getFichierDoc());
//		file = new DefaultStreamedContent(stream, "txt", taGedUtilisateur.getLiblDoc());
//		TaDocumentPdf doc = taDocumentPdfService.findById(param);
		InputStream stream = new ByteArrayInputStream (selectedDoc.getImgDoc());
//		file = new DefaultStreamedContent(stream, "pdf", selectedDoc.getLiblDoc());
		file = new DefaultStreamedContent(stream, "pdf", selectedDoc.getNomFichier());
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public TaDocumentPdf getSelectedDoc() {
		return selectedDoc;
	}

	public void setSelectedDoc(TaDocumentPdf selectedDoc) {
		this.selectedDoc = selectedDoc;
	}

	public List<TaDocumentPdf> getValues() {
		return values;
	}

	public void setValues(List<TaDocumentPdf> values) {
		this.values = values;
	}
	
	public TabListModelBean getTabsCenter() {
		return tabsCenter;
	}

	public void setTabsCenter(TabListModelBean tabsCenter) {
		this.tabsCenter = tabsCenter;
	}
	
	public TabViewsBean getTabViews() {
		return tabViews;
	}

	public void setTabViews(TabViewsBean tabViews) {
		this.tabViews = tabViews;
	}
	
	

}
