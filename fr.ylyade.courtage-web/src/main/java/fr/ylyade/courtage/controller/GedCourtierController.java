package fr.ylyade.courtage.controller;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.FinderException;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.imageio.ImageIO;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import fr.legrain.lib.data.EnumModeObjet;
import fr.ylyade.courtage.app.AbstractController;
import fr.ylyade.courtage.droits.service.interfaces.remote.ITaUtilisateurServiceRemote;
import fr.ylyade.courtage.dto.TaCourtierDTO;
import fr.ylyade.courtage.dto.TaGedUtilisateurDTO;
import fr.ylyade.courtage.dto.TaListeRefDocDTO;
import fr.ylyade.courtage.model.TaCourtier;
import fr.ylyade.courtage.model.TaGedUtilisateur;
import fr.ylyade.courtage.model.TaListeRefDoc;
import fr.ylyade.courtage.model.TaTListeRefDoc;
import fr.ylyade.courtage.model.mapping.LgrDozerMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaCourtierServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaGedUtilisateurServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaListeRefDocServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTCourtierServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTUtilisateurServiceRemote;

@Named
@ViewScoped  
public class GedCourtierController extends AbstractControllerGed implements Serializable {  

	/**
	 * 
	 */
	private static final long serialVersionUID = 4195968214384284938L;

	protected TaListeRefDocDTO selectedTaListeRefDocDTO;
	
	protected TaCourtier masterEntity;
	
//	private TaTListeRefDoc masterEntityDoc;


	protected List<TaListeRefDocDTO> listeByType;
	
	private @EJB ITaTCourtierServiceRemote taTCourtierService;
	private @EJB ITaCourtierServiceRemote taCourtierService;
	private @EJB ITaTUtilisateurServiceRemote taTUtilisateurService;
	
	
	private @EJB ITaUtilisateurServiceRemote taUtilisateurService;
	@EJB
	protected ITaGedUtilisateurServiceRemote taGedUtilisateurService;
	
	@EJB
	protected ITaListeRefDocServiceRemote taListeRefDocService;
	

	private LgrDozerMapper<TaCourtierDTO,TaCourtier> mapperUIToModel  = new LgrDozerMapper<TaCourtierDTO,TaCourtier>();
	private LgrDozerMapper<TaCourtier,TaCourtierDTO> mapperModelToUI  = new LgrDozerMapper<TaCourtier,TaCourtierDTO>();
	
	private StreamedContent file;
//	private TaGedUtilisateur nbDocAttendu;
	
	private long nbDocAttendu;
	
	private long nbDocUploader;
	private long nbDocValider;


	public GedCourtierController() {  
		//values = getValues();
	}  

	@PostConstruct
	public void postConstruct(){
		refresh();
		
		
	}
	public void refreshInfoDocGed() {
		
		
		
		nbDocAttendu  = taGedUtilisateurService.countNbDocAttenduPourCourtier(TaTListeRefDoc.TYPE_LISTE_REF_DOC_COURTIER);
		if(masterEntity != null && masterEntity.getIdCourtier() != null) {
			nbDocUploader = taGedUtilisateurService.countNbDocUploderPourCourtier(masterEntity.getIdCourtier());
		}else {
			nbDocUploader=0;
		}
		if(masterEntity != null && masterEntity.getIdCourtier() != null) {
			nbDocValider  = taGedUtilisateurService.countNbDocValiderPourCourtier(masterEntity.getIdCourtier());
		}else {
			nbDocValider=0;
		}
		
		
		//test
	}
	public void refresh(){
		
		setListeByType(taListeRefDocService.findByType(TaTListeRefDoc.ID_TYPE_LISTE_REF_DOC_COURTIER));
	}
	
	public boolean findByIdDocAndByIdCourtierDTO(Integer idDoc) {
		if(masterEntity!=null && masterEntity.getIdCourtier()!=null) {
			
			return taGedUtilisateurService.findByIdDocAndByIdCourtierDTO(idDoc, masterEntity.getIdCourtier())!=null?true:false;
	 }
		return false;
	}
	
	public boolean isDocumentValidateYlyade(Integer idDoc) {
		if(masterEntity!=null && masterEntity.getIdCourtier()!=null) {
			
			TaGedUtilisateurDTO taGedUtilisateur = taGedUtilisateurService.findByIdDocAndByIdCourtierDTO(idDoc, masterEntity.getIdCourtier());
			if(taGedUtilisateur!=null && taGedUtilisateur.getValidationYlyade()!=null) {
				return taGedUtilisateur.getValidationYlyade();
			}
			
	 }
		return false;
	}
	
	
//	public void ouvreDialogTelechargement(ActionEvent event) {
//		Integer param =  (Integer) event.getComponent().getAttributes().get("param");
//		String paramStr = String.valueOf(param);
//		Integer IdCourtier = masterEntity.getIdCourtier();
//		String IdCourtierStr = String.valueOf(IdCourtier);
//		Map<String,Object> options = new HashMap<String, Object>();
//        options.put("modal", true);
//        options.put("draggable", false);
//        options.put("resizable", false);
//        options.put("contentHeight", 320);        
//        Map<String,List<String>> params = new HashMap<String,List<String>>();
//        List<String> list = new ArrayList<String>();
//        List<String> list2 = new ArrayList<String>();
//        list.add(paramStr);
//        list2.add(IdCourtierStr);
//        params.put("param", list);
//        params.put("idCourtier", list2);
//        
//        RequestContext.getCurrentInstance().openDialog("courtier/dialog_upload_ged_courtier", options, params);
//		
//	}
	public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        try {
        	
        	TaGedUtilisateur taGedUtilisateur = new TaGedUtilisateur();
        	taGedUtilisateur.setFichierDoc(IOUtils.toByteArray(event.getFile().getInputstream()));
        	String NomFichier1 = new String(event.getFile().getFileName().getBytes(Charset.defaultCharset()),"UTF-8");
        	String NomFichier2 = stripAccents(NomFichier1);
        	taGedUtilisateur.setNomFichier(NomFichier2);
        	taGedUtilisateur.setTypeMime(event.getFile().getContentType());
        	taGedUtilisateur.setTaille((int) event.getFile().getSize());
        	
//			fichier.setBlobFichierMiniature(fichier.resizeImage(IOUtils.toByteArray(event.getFile().getInputstream()), event.getFile().getFileName(), 640, 480));
        	int hMax = 80;
        	int lMax = 350;
        	Image image = ImageIO.read(event.getFile().getInputstream());
        	//BufferedImage bi = TaFichier.resizeImageMax(image,lMax,hMax);
        	
//        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        	String type = "png";
//        	if(event.getFile().getFileName().endsWith(".jpg") || event.getFile().getFileName().endsWith(".jpeg")) {
//        		type = "jpg";
//        	} else if(event.getFile().getFileName().endsWith(".png")) {
//        		type = "png";
//        	} else if(event.getFile().getFileName().endsWith(".gif")) {
//        		type = "gif";
//        	} else if(event.getFile().getFileName().endsWith(".svg")) {
//        		type = "svg";
//        	}
////        	ImageIO.write(type, baos);
////        	byte[] bytes = baos.toByteArray();
//        	
//        	taGedUtilisateur.setFichierDoc(bytes);
        	
        	taGedUtilisateur.setTaCourtier(masterEntity);
        	
        	
        	

//        		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
//        		return params.get("country");

        	
//        		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        		 
//        		  String strparams = params.get("param");
//        		 Integer id = LibConversion.stringToInteger(strparams);
        		 
        	
//        	Integer id = LibConversion.stringToInteger(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param")) ;
        	
        	Integer param =  (Integer) event.getComponent().getAttributes().get("param"); // bar
        	
        	TaListeRefDoc result = null;
        	
			try {
				result = taListeRefDocService.findById(param);
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Date now = new Date();
        	taGedUtilisateur.setTaListeRefDoc(result);
        	taGedUtilisateur.setDateDepot(now);
        	
        	
        	
        	taGedUtilisateur = taGedUtilisateurService.merge(taGedUtilisateur);
        	refreshInfoDocGed();
         	
        	//actEnregistrer(null);
//    		refresLogo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void supprimerDoc(ActionEvent actionEvent) {

		try {
			Integer param2 =  (Integer) actionEvent.getComponent().getAttributes().get("param");

			TaGedUtilisateur taGedUtilisateur = taGedUtilisateurService.findByIdDocAndByIdCourtier(param2, masterEntity.getIdCourtier());
			taGedUtilisateurService.remove(taGedUtilisateur);
			refreshInfoDocGed();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "GedUtilisateur", e.getCause().getCause().getCause().getCause().getMessage()));
		}

	}
	
	public void validationYlyade(ActionEvent actionEvent) {

		try {
			Integer param =  (Integer) actionEvent.getComponent().getAttributes().get("param");

			TaGedUtilisateur taGedUtilisateur = taGedUtilisateurService.findByIdDocAndByIdCourtier(param, masterEntity.getIdCourtier());
			if(taGedUtilisateur.getValidationYlyade()==null ||  taGedUtilisateur.getValidationYlyade()==false  ) {
			taGedUtilisateur.setValidationYlyade(true);
			taGedUtilisateurService.merge(taGedUtilisateur);
			refreshInfoDocGed();
			
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "GedUtilisateur", e.getCause().getCause().getCause().getCause().getMessage()));
		}

	}
	
	
	public StreamedContent getFile() {
		
		TaGedUtilisateurDTO taGedUtilisateur =  taGedUtilisateurService.findByIdDocAndByIdCourtierDTO(selectedTaListeRefDocDTO.getId(), masterEntity.getIdCourtier());

		InputStream stream = new ByteArrayInputStream(taGedUtilisateur.getFichierDoc());
		file = new DefaultStreamedContent(stream, taGedUtilisateur.getTypeMime(), taGedUtilisateur.getNomFichier());
		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        return file;
        
    }
	
	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public TaListeRefDocDTO getSelectedTaListeRefDocDTO() {
		return selectedTaListeRefDocDTO;
	}

	public void setSelectedTaListeRefDocDTO(TaListeRefDocDTO selectedTaListeRefDoc) {
		this.selectedTaListeRefDocDTO = selectedTaListeRefDoc;
	}

	public TaCourtier getMasterEntity() {
		return masterEntity;
	}

	public void setMasterEntity(TaCourtier masterEntity) {
		this.masterEntity = masterEntity;
	}
	
//	public TaTListeRefDoc getMasterEntityDoc() {
//		return masterEntityDoc;
//	}
//
//	public void setMasterEntityDoc(TaTListeRefDoc masterEntityDoc) {
//		this.masterEntityDoc = masterEntityDoc;
//	}
	
	public List<TaListeRefDocDTO> getListeByType() {
		return listeByType;
	}

	public void setListeByType(List<TaListeRefDocDTO> listeByType) {
		this.listeByType = listeByType;
	}

	public long getNbDocAttendu() {
		return nbDocAttendu;
	}

	public void setNbDocAttendu(long nbDocAttendu) {
		this.nbDocAttendu = nbDocAttendu;
	}
	
	public long getNbDocUploader() {
		return nbDocUploader;
	}

	public void setNbDocUploader(int nbDocUploader) {
		this.nbDocUploader = nbDocUploader;
	}

	public long getNbDocValider() {
		return nbDocValider;
	}

	public void setNbDocValider(int nbDocValider) {
		this.nbDocValider = nbDocValider;
	}

//	public TaGedUtilisateur getNbDocAttendu() {
//		return nbDocAttendu;
//	}
//
//	public void setNbDocAttendu(TaGedUtilisateur nbDocAttendu) {
//		this.nbDocAttendu = nbDocAttendu;
//	}

}  
