//package fr.ylyade.courtage.controller;
//
//import java.awt.Image;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Date;
//import java.util.List;
//
//import javax.annotation.PostConstruct;
//import javax.ejb.EJB;
//import javax.ejb.FinderException;
//import javax.faces.application.FacesMessage;
//import javax.faces.bean.ManagedBean;
//import javax.faces.view.ViewScoped;
//import javax.faces.context.FacesContext;
//import javax.faces.event.ActionEvent;
//import javax.imageio.ImageIO;
//
//import org.apache.commons.io.IOUtils;
//import org.primefaces.event.FileUploadEvent;
//import org.primefaces.model.DefaultStreamedContent;
//import org.primefaces.model.StreamedContent;
//
//import fr.ylyade.courtage.app.AbstractController;
//import fr.ylyade.courtage.droits.service.interfaces.remote.ITaUtilisateurServiceRemote;
//import fr.ylyade.courtage.dto.TaAssureDTO;
//import fr.ylyade.courtage.dto.TaGedUtilisateurDTO;
//import fr.ylyade.courtage.dto.TaListeRefDocDTO;
//import fr.ylyade.courtage.model.TaAssure;
//import fr.ylyade.courtage.model.TaDossierRcd;
//import fr.ylyade.courtage.model.TaGedUtilisateur;
//import fr.ylyade.courtage.model.TaListeRefDoc;
//import fr.ylyade.courtage.model.mapping.LgrDozerMapper;
//import fr.ylyade.courtage.service.interfaces.remote.ITaAssureServiceRemote;
//import fr.ylyade.courtage.service.interfaces.remote.ITaGedUtilisateurServiceRemote;
//import fr.ylyade.courtage.service.interfaces.remote.ITaListeRefDocServiceRemote;
//import fr.ylyade.courtage.service.interfaces.remote.ITaTAssureServiceRemote;
//import fr.ylyade.courtage.service.interfaces.remote.ITaTUtilisateurServiceRemote;
//
//@Named
//@ViewScoped  
//public class GedAssureController extends AbstractController {  
//	
//	private TaListeRefDocDTO selectedTaListeRefDocDTO;
//	
//	private TaDossierRcd masterEntity;
//	private List<TaListeRefDocDTO> listeByType;
//	
//	private @EJB ITaTAssureServiceRemote taTAssureService;
//	private @EJB ITaAssureServiceRemote taAssureService;
//	private @EJB ITaTUtilisateurServiceRemote taTUtilisateurService;
//	
//	
//	
//	private @EJB ITaUtilisateurServiceRemote taUtilisateurService;
//	private @EJB ITaGedUtilisateurServiceRemote taGedUtilisateurService;
//	
//	private @EJB ITaListeRefDocServiceRemote taListeRefDocService;
//	
//	
//
//	private LgrDozerMapper<TaAssureDTO,TaAssure> mapperUIToModel  = new LgrDozerMapper<TaAssureDTO,TaAssure>();
//	private LgrDozerMapper<TaAssure,TaAssureDTO> mapperModelToUI  = new LgrDozerMapper<TaAssure,TaAssureDTO>();
//	
//	private StreamedContent file;
//
//	public GedAssureController() {  
//		//values = getValues();
//	}  
//
//	@PostConstruct
//	public void postConstruct(){
//		refresh();
//	}
//	
//	
//	
//	public void refresh(){
////		if(auth.getUser()==null) {
////			auth.setUser(sessionInfo.getUtilisateur());
////		}
////		if(auth.isAssure() && !auth.isDevLgr()) {
////			try {
////				taAssure = taAssureService.findByIdUtilisateur(auth.getUser().getIdUtilisateur());
////				selectedTaAssureDTO = taAssureService.findByIdDTO(taAssure.getIdDevisRcPro());
////				updateTab();
////				scrollToTop();
////			} catch(Exception e) {
////				e.printStackTrace();
////			}
////		} else {
////			values = taAssureService.findAllLight();
////		}
////		
////		modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
//////    	infoEntreprise = taInfoEntrepriseService.findInstance();
//////    	dateDebut = infoEntreprise.getDatedebInfoEntreprise();
//////    	dateFin = infoEntreprise.getDatefinInfoEntreprise();
//////
//////    	dateDebutProforma = infoEntreprise.getDatedebInfoEntreprise();
//////    	dateFinProforma = infoEntreprise.getDatefinInfoEntreprise();
//		setListeByType(taListeRefDocService.findByType(1));
//	}
//	
//	public boolean findByIdDocAndByIdDevisRcProDTO(Integer idDoc) {
//		if(masterEntity!=null && masterEntity.getIdDevisRcPro()!=null) {
//			
//			return taGedUtilisateurService.findByIdDocAndByIdDevisRcProDTO(idDoc, masterEntity.getIdDevisRcPro())!=null?true:false;
//	 }
//		return false;
//	}
//	
//	public boolean isDocumentValidateYlyade(Integer idDoc) {
//		if(masterEntity!=null && masterEntity.getIdDevisRcPro()!=null) {
//			
//			TaGedUtilisateurDTO taGedUtilisateur = taGedUtilisateurService.findByIdDocAndByIdDevisRcProDTO(idDoc, masterEntity.getIdDevisRcPro());
//			if(taGedUtilisateur!=null && taGedUtilisateur.getValidationYlyade()!=null) {
//				return taGedUtilisateur.getValidationYlyade();
//			}
//			
//	 }
//		return false;
//	}
//
//	public void handleFileUpload(FileUploadEvent event) {
//        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
//        FacesContext.getCurrentInstance().addMessage(null, message);
//        
//        try {
//        	
//        	TaGedUtilisateur taGedUtilisateur = new TaGedUtilisateur();
//        	taGedUtilisateur.setFichierDoc(IOUtils.toByteArray(event.getFile().getInputstream()));
//        	taGedUtilisateur.setNomFichier(event.getFile().getFileName());
//        	taGedUtilisateur.setTypeMime(event.getFile().getContentType());
//        	taGedUtilisateur.setTaille((int) event.getFile().getSize());
//        	
////			fichier.setBlobFichierMiniature(fichier.resizeImage(IOUtils.toByteArray(event.getFile().getInputstream()), event.getFile().getFileName(), 640, 480));
//        	int hMax = 80;
//        	int lMax = 350;
//        	Image image = ImageIO.read(event.getFile().getInputstream());
//        	//BufferedImage bi = TaFichier.resizeImageMax(image,lMax,hMax);
//        	
////        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
////        	String type = "png";
////        	if(event.getFile().getFileName().endsWith(".jpg") || event.getFile().getFileName().endsWith(".jpeg")) {
////        		type = "jpg";
////        	} else if(event.getFile().getFileName().endsWith(".png")) {
////        		type = "png";
////        	} else if(event.getFile().getFileName().endsWith(".gif")) {
////        		type = "gif";
////        	} else if(event.getFile().getFileName().endsWith(".svg")) {
////        		type = "svg";
////        	}
//////        	ImageIO.write(type, baos);
//////        	byte[] bytes = baos.toByteArray();
////        	
////        	taGedUtilisateur.setFichierDoc(bytes);
//        	
//        	taGedUtilisateur.setTaAssure(masterEntity);
//        	
//        	
//
////        		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
////        		return params.get("country");
//
//        	
////        		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
////        		 
////        		  String strparams = params.get("param");
////        		 Integer id = LibConversion.stringToInteger(strparams);
//        		 
//        	
////        	Integer id = LibConversion.stringToInteger(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param")) ;
//        	
//        	Integer param =  (Integer) event.getComponent().getAttributes().get("param"); // bar
//        	
//        	TaListeRefDoc result = null;
//			try {
//				result = taListeRefDocService.findById(param);
//			} catch (FinderException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			Date now = new Date();
//        	taGedUtilisateur.setTaListeRefDoc(result);
//        	taGedUtilisateur.setDateDepot(now);
//        	
//        	taGedUtilisateur = taGedUtilisateurService.merge(taGedUtilisateur);
//        	
//        	//actEnregistrer(null);
////    		refresLogo();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public void supprimerDoc(ActionEvent actionEvent) {
//
//		try {
//			Integer param2 =  (Integer) actionEvent.getComponent().getAttributes().get("param");
//
//			TaGedUtilisateur taGedUtilisateur = taGedUtilisateurService.findByIdDocAndByIdAssure(param2, masterEntity.getIdDevisRcPro());
//			taGedUtilisateurService.remove(taGedUtilisateur);
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			FacesContext context = FacesContext.getCurrentInstance();  
//			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "GedUtilisateur", e.getCause().getCause().getCause().getCause().getMessage()));
//		}
//
//	}
//	
//	public void validationYlyade(ActionEvent actionEvent) {
//
//		try {
//			Integer param =  (Integer) actionEvent.getComponent().getAttributes().get("param");
//
//			TaGedUtilisateur taGedUtilisateur = taGedUtilisateurService.findByIdDocAndByIdAssure(param, masterEntity.getIdDevisRcPro());
//			if(taGedUtilisateur.getValidationYlyade()==null ||  taGedUtilisateur.getValidationYlyade()==false  ) {
//			taGedUtilisateur.setValidationYlyade(true);
//			taGedUtilisateurService.merge(taGedUtilisateur);
//			}
//			
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			FacesContext context = FacesContext.getCurrentInstance();  
//			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "GedUtilisateur", e.getCause().getCause().getCause().getCause().getMessage()));
//		}
//
//	}
//
//	
//	public StreamedContent getFile() {
//		
//		TaGedUtilisateurDTO taGedUtilisateur =  taGedUtilisateurService.findByIdDocAndByIdDevisRcProDTO(selectedTaListeRefDocDTO.getId(), masterEntity.getIdDevisRcPro());
//
//		InputStream stream = new ByteArrayInputStream(taGedUtilisateur.getFichierDoc());
//		file = new DefaultStreamedContent(stream, "txt", taGedUtilisateur.getLiblDoc());
//        
//        
//        return file;
//    }
//	public void setFile(StreamedContent file) {
//		this.file = file;
//	}
//
//
//	public TaListeRefDocDTO getSelectedTaListeRefDocDTO() {
//		return selectedTaListeRefDocDTO;
//	}
//
//	public void setSelectedTaListeRefDocDTO(TaListeRefDocDTO selectedTaListeRefDoc) {
//		this.selectedTaListeRefDocDTO = selectedTaListeRefDoc;
//	}
//
//	public TaAssure getMasterEntity() {
//		return masterEntity;
//	}
//
//	public void setMasterEntity(TaAssure masterEntity) {
//		this.masterEntity = masterEntity;
//	}
//
//	public List<TaListeRefDocDTO> getListeByType() {
//		return listeByType;
//	}
//
//	public void setListeByType(List<TaListeRefDocDTO> listeByType) {
//		this.listeByType = listeByType;
//	}
//
//}  
