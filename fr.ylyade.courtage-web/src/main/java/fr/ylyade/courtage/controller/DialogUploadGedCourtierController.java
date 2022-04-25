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
import javax.faces.context.ExternalContext;
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
public class DialogUploadGedCourtierController extends AbstractControllerGed  implements Serializable {  

	/**
	 * 
	 */
	private static final long serialVersionUID = -820538083090802266L;
	
	@EJB
	protected ITaGedUtilisateurServiceRemote taGedUtilisateurService;
	
	@EJB
	protected ITaListeRefDocServiceRemote taListeRefDocService;
	
	@EJB
	protected ITaCourtierServiceRemote taCourtierService;
	
	private Integer selectedDocId;
	private Integer idCourtier;



	public DialogUploadGedCourtierController() {  
	}  

	@PostConstruct
	public void postConstruct(){
//		String paramStr = (String) RequestContext.getCurrentInstance().getAttributes().get("param");
//		String idCourtierStr = (String) RequestContext.getCurrentInstance().getAttributes().get("idCourtier");
		
//		String paramStr = (String) FacesContext.getCurrentInstance().getAttributes().get("param");
//		String idCourtierStr = (String) FacesContext.getCurrentInstance().getAttributes().get("idCourtier");
		
		
		Map<String, String> parameterMap =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		String paramStr = parameterMap.get("param");
		String idCourtierStr = parameterMap.get("idCourtier");
		
		
		if(paramStr != null) {
			selectedDocId = Integer.parseInt(paramStr);
		}
		if(idCourtierStr != null) {
			idCourtier = Integer.parseInt(idCourtierStr);
		}
		
		
		
		
	}

	
	

	public void handleFileUpload(FileUploadEvent event) throws FinderException {
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
        	
        	Image image = ImageIO.read(event.getFile().getInputstream());
        	
//        	Integer idCourtier =  (Integer) event.getComponent().getAttributes().get("IdCourtier"); // bar
        	
        	TaCourtier courtier = taCourtierService.findById(idCourtier);
//        	taGedUtilisateur.setTaCourtier(masterEntity);
        	taGedUtilisateur.setTaCourtier(courtier);

        	
//        	Integer param =  (Integer) event.getComponent().getAttributes().get("param"); // bar
        	
        	
        	TaListeRefDoc result = null;
        	
			try {
				result = taListeRefDocService.findById(selectedDocId);
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Date now = new Date();
        	taGedUtilisateur.setTaListeRefDoc(result);
        	taGedUtilisateur.setDateDepot(now);
        	
        	
        	
        	taGedUtilisateur = taGedUtilisateurService.merge(taGedUtilisateur);
//        	refreshInfoDocGed();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Integer getSelectedDocId() {
		return selectedDocId;
	}

	public void setSelectedDocId(Integer selectedDocId) {
		this.selectedDocId = selectedDocId;
	}

	public Integer getIdCourtier() {
		return idCourtier;
	}

	public void setIdCourtier(Integer idCourtier) {
		this.idCourtier = idCourtier;
	}




}



 
