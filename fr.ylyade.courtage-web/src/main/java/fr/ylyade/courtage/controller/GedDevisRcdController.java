package fr.ylyade.courtage.controller;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import fr.legrain.bdg.lib.osgi.Const;

import fr.legrain.lib.data.EnumModeObjet;


import fr.ylyade.courtage.app.AbstractController;
import fr.ylyade.courtage.droits.service.interfaces.remote.ITaUtilisateurServiceRemote;
import fr.ylyade.courtage.dto.TaDossierRcdDTO;
import fr.ylyade.courtage.dto.TaGedUtilisateurDTO;
import fr.ylyade.courtage.dto.TaListeRefDocDTO;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaGedUtilisateur;
import fr.ylyade.courtage.model.TaListeRefDoc;
import fr.ylyade.courtage.model.TaTListeRefDoc;
import fr.ylyade.courtage.model.mapping.LgrDozerMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaDossierRcdServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaGedUtilisateurServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaListeRefDocServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTAssureServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTUtilisateurServiceRemote;

@Named
@ViewScoped  
public class GedDevisRcdController extends AbstractControllerGed implements Serializable {  
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3135270912706951468L;

	private TaListeRefDocDTO selectedTaListeRefDocDTO;
	
	private TaGedUtilisateurDTO selectedTaGedUtilisateurDTO;
	
	private TaGedUtilisateur taGedUtilisateur;
	
	private TaDossierRcd masterEntity;
	private List<TaListeRefDocDTO> listeByType;
	
	private List<TaGedUtilisateurDTO> listTaGedUtilisateurDTO;
	
	private @EJB ITaTAssureServiceRemote taTAssureService;
	private @EJB ITaDossierRcdServiceRemote taDevisRcProService;
	private @EJB ITaTUtilisateurServiceRemote taTUtilisateurService;
	
	
	private @EJB ITaUtilisateurServiceRemote taUtilisateurService;
	private @EJB ITaGedUtilisateurServiceRemote taGedUtilisateurService;
	
	private @EJB ITaListeRefDocServiceRemote taListeRefDocService;
	
	

	private LgrDozerMapper<TaDossierRcdDTO,TaDossierRcd> mapperUIToModel  = new LgrDozerMapper<TaDossierRcdDTO,TaDossierRcd>();
	private LgrDozerMapper<TaDossierRcd,TaDossierRcdDTO> mapperModelToUI  = new LgrDozerMapper<TaDossierRcd,TaDossierRcdDTO>();
	
//	private LgrDozerMapper<TaGedUtilisateurDTO,TaGedUtilisateur> mapperUIToModel  = new LgrDozerMapper<TaGedUtilisateurDTO,TaGedUtilisateur>();
//	private LgrDozerMapper<TaGedUtilisateur,TaGedUtilisateurDTO> mapperModelToUI  = new LgrDozerMapper<TaGedUtilisateur,TaGedUtilisateurDTO>();


	
	private StreamedContent file;

	public GedDevisRcdController() {  
		//values = getValues();
	}  

	@PostConstruct
	public void postConstruct(){
		refresh();
		
		
		
	}
	
	public boolean isMasterEntityId() {
		if(masterEntity != null && masterEntity.getIdDossierRcd() != null) {
			return true;
		}
		return false;
	}
	
	public void refresh(){
//		if(auth.getUser()==null) {
//			auth.setUser(sessionInfo.getUtilisateur());
//		}
//		if(auth.isAssure() && !auth.isDevLgr()) {
//			try {
//				taAssure = taAssureService.findByIdUtilisateur(auth.getUser().getIdUtilisateur());
//				selectedTaDevisRcProDTO = taAssureService.findByIdDTO(taAssure.getIdAssure());
//				updateTab();
//				scrollToTop();
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
//		} else {
//			values = taAssureService.findAllLight();
//		}
//		
//		modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
////    	infoEntreprise = taInfoEntrepriseService.findInstance();
////    	dateDebut = infoEntreprise.getDatedebInfoEntreprise();
////    	dateFin = infoEntreprise.getDatefinInfoEntreprise();
////
////    	dateDebutProforma = infoEntreprise.getDatedebInfoEntreprise();
////    	dateFinProforma = infoEntreprise.getDatefinInfoEntreprise();
		
		this.listTaGedUtilisateurDTO = initListeGed();
		
		setListeByType(taListeRefDocService.findByType(TaTListeRefDoc.ID_TYPE_LISTE_REF_DOC_RCPRO));
	}
	
	
	public List<TaGedUtilisateurDTO> initListeGed(){
		
		List<TaGedUtilisateurDTO> listeGedUtilisateur = new ArrayList<>();
		if( masterEntity !=null && masterEntity.getIdDossierRcd()!= null ) {
		List<TaListeRefDocDTO> listeRefDocNouveau = taListeRefDocService.findByTypeNotInGedUtilisateur(masterEntity.getIdDossierRcd(), TaTListeRefDoc.ID_TYPE_LISTE_REF_DOC_RCPRO);
		List<TaListeRefDocDTO> listeRefDocPresent = taListeRefDocService.findByTypeInGedUtilisateur(masterEntity.getIdDossierRcd(), TaTListeRefDoc.ID_TYPE_LISTE_REF_DOC_RCPRO);
		List<TaListeRefDocDTO> listeRefDoc = taListeRefDocService.findByType(TaTListeRefDoc.ID_TYPE_LISTE_REF_DOC_RCPRO);
		
		
		if(listeRefDocPresent!=null) {
				for (TaListeRefDocDTO item : listeRefDocPresent) {
					TaGedUtilisateurDTO gedUtilisateurPresent = new TaGedUtilisateurDTO();
					gedUtilisateurPresent = taGedUtilisateurService.findByIdDocAndByIdDevisRcProDTO(item.getId(), masterEntity.getIdDossierRcd());
					gedUtilisateurPresent.setIdListeRefDoc(item.getId());
					gedUtilisateurPresent.setLiblDoc(item.getLiblDoc());
					listeGedUtilisateur.add(gedUtilisateurPresent);
					
				}
				
				
			}
			if(listeRefDocNouveau!=null) {
				for (TaListeRefDocDTO item : listeRefDocNouveau) {
					TaGedUtilisateurDTO gedUtilisateurNouveau= new TaGedUtilisateurDTO();
					gedUtilisateurNouveau.setIdListeRefDoc(item.getId());
					gedUtilisateurNouveau.setLiblDoc(item.getLiblDoc());
					listeGedUtilisateur.add(gedUtilisateurNouveau);
					
				}
			}
		}else {
			List<TaListeRefDocDTO> listeRefDocNouveau = taListeRefDocService.findByType(TaTListeRefDoc.ID_TYPE_LISTE_REF_DOC_RCPRO);
			if(listeRefDocNouveau!=null) {
				for (TaListeRefDocDTO item : listeRefDocNouveau) {
					TaGedUtilisateurDTO gedUtilisateurNouveau= new TaGedUtilisateurDTO();
					gedUtilisateurNouveau.setIdListeRefDoc(item.getId());
					gedUtilisateurNouveau.setLiblDoc(item.getLiblDoc());
					listeGedUtilisateur.add(gedUtilisateurNouveau);
					
				}
			}
		}
		
		
		return listeGedUtilisateur;
		
	}
	
	
	public boolean findByIdDoc(Integer idDoc) throws FinderException {
		if(masterEntity!=null && masterEntity.getIdDossierRcd()!=null) {
			TaGedUtilisateur doc = taGedUtilisateurService.findById(idDoc);
			if(doc!=null && doc.getFichierDoc()!=null) {
				return true;
			}
			
//			return taGedUtilisateurService.findById(idDoc)!=null?true:false;
	 }
		return false;
	}
	
	
	
	public boolean isDocumentPresent(Integer idDoc) throws FinderException {
		if(idDoc!=null && masterEntity!=null && masterEntity.getIdDossierRcd() !=null) {
			TaGedUtilisateurDTO doc = taGedUtilisateurService.findByIdDTO(idDoc);
			
					if(doc!=null && doc.getFichierDoc() != null) {	
						
						return true;
					}	
							
			 }
		
		return false;
		
	}
	
	public boolean isDocumentValidateYlyade(Integer idDoc) throws FinderException {
		if(idDoc!=null && masterEntity!=null && masterEntity.getIdDossierRcd()!=null) {
			
//			TaGedUtilisateurDTO taGedUtilisateur = taGedUtilisateurService.findByIdDocAndByIdDevisRcProDTO(idDoc, masterEntity.getIdDossierRcd());
			TaGedUtilisateurDTO doc = taGedUtilisateurService.findByIdDTO(idDoc);
			if(doc!=null && doc.getValidationYlyade()!=null) {
				return doc.getValidationYlyade();
			}
			
	 }
		return false;
	}
	
	public boolean isDocumentValidateSuperAssureur(Integer idDoc) throws FinderException {
		if(idDoc!=null && masterEntity!=null && masterEntity.getIdDossierRcd()!=null) {
			
//			TaGedUtilisateurDTO taGedUtilisateur = taGedUtilisateurService.findByIdDocAndByIdDevisRcProDTO(idDoc, masterEntity.getIdDossierRcd());
			TaGedUtilisateurDTO doc = taGedUtilisateurService.findByIdDTO(idDoc);
			if(doc!=null && doc.getValidationSuperAssureur()!=null) {
				return doc.getValidationSuperAssureur();
			}
			
	 }
		return false;
	}
	
	public boolean isDocumentValidateCourtier(Integer idDoc) throws FinderException {
		if(idDoc!=null && masterEntity!=null && masterEntity.getIdDossierRcd()!=null) {
			
//			TaGedUtilisateurDTO taGedUtilisateur = taGedUtilisateurService.findByIdDocAndByIdDevisRcProDTO(idDoc, masterEntity.getIdDossierRcd());
			TaGedUtilisateurDTO doc = taGedUtilisateurService.findByIdDTO(idDoc);
			if(doc!=null && doc.getValidationCourtier()!=null) {
				return doc.getValidationCourtier();
			}
			
	 }
		return false;
	}

	public void handleFileUpload(FileUploadEvent event) throws FinderException {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        try {
        	Integer param =  (Integer) event.getComponent().getAttributes().get("param"); // bar
        	TaGedUtilisateurDTO doc = taGedUtilisateurService.findByIdDTO(param);
        	
        	
        	
//        	TaGedUtilisateur taGedUtilisateur = new TaGedUtilisateur();
        	
        	doc.setFichierDoc(IOUtils.toByteArray(event.getFile().getInputstream()));
        	String NomFichier1 = new String(event.getFile().getFileName().getBytes(Charset.defaultCharset()),"UTF-8");
        	String NomFichier2 = stripAccents(NomFichier1);
        	doc.setNomFichier(NomFichier2);
        	doc.setTypeMime(event.getFile().getContentType());
        	doc.setTaille((int) event.getFile().getSize());
        	
//			fichier.setBlobFichierMiniature(fichier.resizeImage(IOUtils.toByteArray(event.getFile().getInputstream()), event.getFile().getFileName(), 640, 480));
        	int hMax = 80;
        	int lMax = 350;
        	Image image = ImageIO.read(event.getFile().getInputstream());
        	//BufferedImage bi = TaFichier.resizeImageMax(image,lMax,hMax);

        	
//        	taGedUtilisateurDTO.setTaDossierRcd(masterEntity);
        	
//        	taGedUtilisateurDTO.setIdDevisRcPro(masterEntity.getIdDossierRcd());
        	
//        	masterEntity.add(taGedUtilisateurDTO);


        	
        	

			Date now = new Date();

			doc.setDateDepot(now);
        	
//			taGedUtilisateurDTO = taGedUtilisateurService.merge(taGedUtilisateurDTO);
        	
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

//			TaGedUtilisateur taGedUtilisateur = taGedUtilisateurService.findById(param2);
			TaGedUtilisateurDTO taGedUtilisateurDTO = taGedUtilisateurService.findByIdDTO(param2);
//			taGedUtilisateurService.remove(taGedUtilisateurDTO);
			taGedUtilisateurDTO.setFichierDoc(null);
			taGedUtilisateurDTO.setNomFichier(null);
			taGedUtilisateurDTO.setTypeMime(null);
			taGedUtilisateurDTO.setTaille(null);
			taGedUtilisateurDTO.setDateDepot(null);
			

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

			TaGedUtilisateur taGedUtilisateur = taGedUtilisateurService.findByIdDocAndByIdDevisRcPro(param, masterEntity.getIdDossierRcd());
			if(taGedUtilisateur.getValidationYlyade()==null ||  taGedUtilisateur.getValidationYlyade()==false  ) {
			taGedUtilisateur.setValidationYlyade(true);
			taGedUtilisateurService.merge(taGedUtilisateur);
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "GedUtilisateur", e.getCause().getCause().getCause().getCause().getMessage()));
		}

	}
	
	public void validationSuperAssureur(ActionEvent actionEvent) {

		try {
			Integer param =  (Integer) actionEvent.getComponent().getAttributes().get("param");

			TaGedUtilisateur taGedUtilisateur = taGedUtilisateurService.findByIdDocAndByIdDevisRcPro(param, masterEntity.getIdDossierRcd());
			if(taGedUtilisateur.getValidationSuperAssureur()==null ||  taGedUtilisateur.getValidationSuperAssureur()==false  ) {
			taGedUtilisateur.setValidationSuperAssureur(true);
			taGedUtilisateurService.merge(taGedUtilisateur);
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "GedUtilisateur", e.getCause().getCause().getCause().getCause().getMessage()));
		}

	}
	
	public void validationCourtier(ActionEvent actionEvent) {

		try {
			Integer param =  (Integer) actionEvent.getComponent().getAttributes().get("param");

			TaGedUtilisateur taGedUtilisateur = taGedUtilisateurService.findByIdDocAndByIdDevisRcPro(param, masterEntity.getIdDossierRcd());
			if(taGedUtilisateur.getValidationCourtier()==null ||  taGedUtilisateur.getValidationCourtier()==false  ) {
			taGedUtilisateur.setValidationCourtier(true);
			taGedUtilisateurService.merge(taGedUtilisateur);
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "GedUtilisateur", e.getCause().getCause().getCause().getCause().getMessage()));
		}

	}

	
	public StreamedContent getFile() throws FinderException {
		
//		TaGedUtilisateurDTO taGedUtilisateur =  taGedUtilisateurService.findByIdDocAndByIdDevisRcProDTO(selectedTaListeRefDocDTO.getId(), masterEntity.getIdDossierRcd());
		TaGedUtilisateurDTO taGedUtilisateur =  taGedUtilisateurService.findByIdDTO(selectedTaGedUtilisateurDTO.getId());
		InputStream stream = new ByteArrayInputStream(taGedUtilisateur.getFichierDoc());
		file = new DefaultStreamedContent(stream, taGedUtilisateur.getTypeMime(), taGedUtilisateur.getNomFichier());
        
        
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

	public TaDossierRcd getMasterEntity() {
		return masterEntity;
	}

	public void setMasterEntity(TaDossierRcd masterEntity) {
		this.masterEntity = masterEntity;
	}

	public List<TaListeRefDocDTO> getListeByType() {
		return listeByType;
	}

	public void setListeByType(List<TaListeRefDocDTO> listeByType) {
		this.listeByType = listeByType;
	}

	public TaGedUtilisateurDTO getSelectedTaGedUtilisateurDTO() {
		return selectedTaGedUtilisateurDTO;
	}

	public void setSelectedTaGedUtilisateurDTO(TaGedUtilisateurDTO selectedTaGedUtilisateurDTO) {
		this.selectedTaGedUtilisateurDTO = selectedTaGedUtilisateurDTO;
	}

	public TaGedUtilisateur getTaGedUtilisateur() {
		return taGedUtilisateur;
	}

	public void setTaGedUtilisateur(TaGedUtilisateur taGedUtilisateur) {
		this.taGedUtilisateur = taGedUtilisateur;
	}

	public List<TaGedUtilisateurDTO> getListTaGedUtilisateurDTO() {
		return listTaGedUtilisateurDTO;
	}

	public void setListTaGedUtilisateurDTO(List<TaGedUtilisateurDTO> listTaGedUtilisateurDTO) {
		this.listTaGedUtilisateurDTO = listTaGedUtilisateurDTO;
	}

}  
