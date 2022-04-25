package fr.ylyade.courtage.controller.parametres;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.FinderException;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.primefaces.context.RequestContext;
import org.primefaces.event.ReorderEvent;
import org.primefaces.event.SelectEvent;

import fr.legrain.courtage.service.interfaces.remote.general.ThrowableExceptionLgr;
import fr.legrain.lib.data.EnumModeObjet;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModeObjetEcranServeur;
import fr.ylyade.courtage.app.ConstWeb;
import fr.ylyade.courtage.dao.jpa.TaActiviteDAO;
import fr.ylyade.courtage.dto.TaActiviteDTO;
import fr.ylyade.courtage.dto.TaClasseDTO;
import fr.ylyade.courtage.dto.TaFamilleActiviteDTO;
import fr.ylyade.courtage.model.Const;
import fr.ylyade.courtage.model.TaActivite;
import fr.ylyade.courtage.model.TaClasse;
import fr.ylyade.courtage.model.TaFamilleActivite;
import fr.ylyade.courtage.model.mapping.LgrDozerMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaActiviteServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaClasseServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaFamilleActiviteServiceRemote;

@Named
@ViewScoped  
public class TypeActiviteController implements Serializable {  

	/**
	 * 
	 */
	private static final long serialVersionUID = 4135833681787831344L;
	private List<TaActiviteDTO> values; 
	private List<TaActiviteDTO> filteredValues; 
	private TaActiviteDTO nouveau ;
	private TaActiviteDTO selection ;
	
	private TaActivite taActivite = new TaActivite();
	
	private String modeEcranDefaut;
	private static final String C_DIALOG = "dialog";

	private ModeObjetEcranServeur modeEcran = new ModeObjetEcranServeur();

	private @EJB ITaActiviteServiceRemote taActiviteService;
	private @EJB ITaFamilleActiviteServiceRemote taFamilleActiviteService;
	private @EJB ITaClasseServiceRemote taClasseService;
	private LgrDozerMapper<TaActiviteDTO,TaActivite> mapperUIToModel  = new LgrDozerMapper<TaActiviteDTO,TaActivite>();
	private LgrDozerMapper<TaActivite,TaActiviteDTO> mapperModelToUI  = new LgrDozerMapper<TaActivite,TaActiviteDTO>();

	private TaFamilleActiviteDTO taFamilleActiviteDTO;
	private TaClasseDTO taClasseDTO;
	private TaFamilleActivite taFamilleActivite;
	private TaClasse taClasse;
	
	public TaFamilleActiviteDTO getTaFamilleActiviteDTO() {
		return taFamilleActiviteDTO;
	}

	public void setTaFamilleActiviteDTO(TaFamilleActiviteDTO taFamilleActiviteDTO) {
		this.taFamilleActiviteDTO = taFamilleActiviteDTO;
	}

	public TaFamilleActivite getTaFamilleActivite() {
		return taFamilleActivite;
	}

	public void setTaFamilleActivite(TaFamilleActivite taFamilleActivite) {
		this.taFamilleActivite = taFamilleActivite;
	}

	@PostConstruct
	public void postConstruct(){
		try {
			refresh();
	
			if(values != null && !values.isEmpty()){
				selection = values.get(0);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void refresh(){
		values = taActiviteService.selectAllDTO();
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		modeEcranDefaut = params.get("modeEcranDefaut");
		if(modeEcranDefaut!=null && modeEcranDefaut.equals(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION))) {
			modeEcranDefaut = C_DIALOG;
			actInserer(null);
		} else if(modeEcranDefaut!=null && modeEcranDefaut.equals(modeEcran.modeString(EnumModeObjet.C_MO_EDITION))) {
			modeEcranDefaut = C_DIALOG;
			if(params.get("idEntity")!=null) {
				try {
					selection = taActiviteService.findByIdDTO(LibConversion.stringToInteger(params.get("idEntity")));
				} catch (FinderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			actModifier(null);
		} else {
			modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
		}
	}
	
	public TypeActiviteController() {  
	}  

	public void actAnnuler(ActionEvent actionEvent){
		
		if(values.size()>= 1){
			selection = values.get(0);
		}		
		nouveau = new TaActiviteDTO();
		
		modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
	}

	public void actEnregistrer(ActionEvent actionEvent){
		try {
			TaActiviteDTO retour = null;
			taActivite=new TaActivite();
			if(nouveau.getId()==null || taActiviteService.findById(nouveau.getId()) == null){
				autoCompleteMapUIToDTO();
				mapperUIToModel.map(nouveau, taActivite);
				taActivite = taActiviteService.merge(taActivite, ITaActiviteServiceRemote.validationContext);
				mapperModelToUI.map(taActivite, nouveau);
				values= taActiviteService.selectAllDTO();
				nouveau = values.get(0);
				nouveau = new TaActiviteDTO();

				modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);

			}else{
				if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_EDITION)==0){
					taActivite = taActiviteService.findById(nouveau.getId());
					autoCompleteMapUIToDTO();
					mapperUIToModel.map(nouveau, taActivite);
					taActivite = taActiviteService.merge(taActivite, ITaActiviteServiceRemote.validationContext);
					mapperModelToUI.map(taActivite, nouveau);
					values= taActiviteService.selectAllDTO();
					nouveau = values.get(0);
					nouveau = new TaActiviteDTO();

					modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
				}
				else{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Code déja utilisé"));
				}
			}
			if(modeEcranDefaut!=null && modeEcranDefaut.equals(C_DIALOG)) {
				RequestContext.getCurrentInstance().closeDialog(taActivite);
			}
		} catch(Exception e) {
			e=ThrowableExceptionLgr.renvoieCauseRuntimeException(e);
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "TypeActivite", e.getMessage()));
		}
	}
	public void actModifierPositionListe(){
		try {
			
//				if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_EDITION)==0){
					taActivite = taActiviteService.findById(nouveau.getId());
					autoCompleteMapUIToDTO();
					mapperUIToModel.map(nouveau, taActivite);
					taActivite = taActiviteService.merge(taActivite, ITaActiviteServiceRemote.validationContext);
					mapperModelToUI.map(taActivite, nouveau);
					values= taActiviteService.selectAllDTO();
					nouveau = values.get(0);
					nouveau = new TaActiviteDTO();

					modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
//				}
//				else{
//					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Code déja utilisé"));
//				}
			
		} catch(Exception e) {
			e=ThrowableExceptionLgr.renvoieCauseRuntimeException(e);
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "TypeActivite", e.getMessage()));
		}
	}
	

	public void actInserer(ActionEvent actionEvent){
		nouveau = new TaActiviteDTO();
	
		modeEcran.setMode(EnumModeObjet.C_MO_INSERTION);
	}

	public void actModifier(ActionEvent actionEvent){
		nouveau = selection;
		autoCompleteMapDTOtoUI();
		modeEcran.setMode(EnumModeObjet.C_MO_EDITION);
	}

	public void actSupprimer(){
		TaActivite taActivite = new TaActivite();
		try {
			if(selection!=null && selection.getId()!=null){
				taActivite = taActiviteService.findById(selection.getId());
			}

			taActiviteService.remove(taActivite);
			//values.remove(selection);
			values = taActiviteService.selectAllDTO();
			
			if(!values.isEmpty()) {
				selection = values.get(0);
			}else {
				selection=new TaActiviteDTO();
			}

			modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);

			if(ConstWeb.DEBUG) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage("Type activite", "actSupprimer"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Type activite", e.getCause().getCause().getCause().getCause().getMessage()));
		}
	}
	
	public void onRowSelect(SelectEvent event) {  
		if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_CONSULTATION)==0) {
			nouveau = selection;
		}
	}

	public boolean etatBouton(String bouton) {
		boolean retour = true;
		switch (modeEcran.getMode()) {
		case C_MO_INSERTION:
			switch (bouton) {
			case "annuler":
			case "enregistrer":
			case "fermer":
				retour = false;
				break;
			default:
				break;
			}
			break;
		case C_MO_EDITION:
			switch (bouton) {
			case "annuler":
			case "enregistrer":
			case "fermer":
				retour = false;
				break;
			default:
				break;
			}
			break;
		case C_MO_CONSULTATION:
			switch (bouton) {
			case "supprimer":
			case "modifier":
			case "inserer":
			case "imprimer":
			case "fermer":
				retour = false;
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}

		return retour;
	}

	public void validateObject(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		String messageComplet = "";
		try {
			String nomChamp =  (String) component.getAttributes().get("nomChamp");
			//validation automatique via la JSR bean validation
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Set<ConstraintViolation<TaActiviteDTO>> violations = factory.getValidator().validateValue(TaActiviteDTO.class,nomChamp,value);
			if (violations.size() > 0) {
				messageComplet = "Erreur de validation : ";
				for (ConstraintViolation<TaActiviteDTO> cv : violations) {
					messageComplet+=" "+cv.getMessage()+"\n";
				}
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, messageComplet, messageComplet));
			} else {
				//aucune erreur de validation "automatique", on déclanche les validations du controller
				validateUIField(nomChamp,value);
			}
		} catch(Exception e) {
			//messageComplet += e.getMessage();
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, messageComplet, messageComplet));
		}
	}
	
	public void autoCompleteMapUIToDTO() {
		if(taFamilleActivite!=null) {
			validateUIField(Const.C_CODE_FAMILLE_ACTIVITE,taFamilleActivite.getCodeFamilleActivite());
			nouveau.setCodeFamilleActivite(taFamilleActivite.getCodeFamilleActivite());
		}
		
		if(taClasse!=null) {
			validateUIField(Const.C_CODE_CLASSE,taClasse.getCodeClasse());
			nouveau.setCodeClasse(taClasse.getCodeClasse());
		}
	}

	public void autoCompleteMapDTOtoUI() {
		try {

			taFamilleActivite = null;
			taFamilleActiviteDTO = null;
			taClasseDTO = null;
			taClasse = null;				

			if(nouveau.getCodeFamilleActivite()!=null && !nouveau.getCodeFamilleActivite().equals("")) {
				taFamilleActivite = taFamilleActiviteService.findByCode(nouveau.getCodeFamilleActivite());
				taFamilleActiviteDTO = taFamilleActiviteService.findByCodeDTO(nouveau.getCodeFamilleActivite());
			}
			
			if(nouveau.getCodeClasse()!=null && !nouveau.getCodeClasse().equals("")) {
				taClasse = taClasseService.findByCode(nouveau.getCodeClasse());
				taClasseDTO = taClasseService.findByCodeDTO(nouveau.getCodeClasse());
			}
			
		} catch (FinderException e) {
			e.printStackTrace();
		}
	}
	
	public boolean validateUIField(String nomChamp,Object value) {
		try {
			if(nomChamp.equals(Const.C_CODE_ACTIVITE)) {
				boolean changement=false;
				if(selection.getCodeActivite()!=null && value!=null && !selection.getCodeActivite().equals("")) {
					if(value instanceof TaActivite)
						changement=((TaActivite) value).getCodeActivite().equals(selection.getCodeActivite());
					else if(value instanceof String)
					changement=!value.equals(selection.getCodeActivite());
				}
				if(changement && modeEcran.dataSetEnModeModification()) {
					FacesContext context = FacesContext.getCurrentInstance();  
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Type d'activité", Const.C_MESSAGE_CHANGEMENT_CODE));
				}
			}
			
			if(nomChamp.equals(Const.C_CODE_FAMILLE_ACTIVITE)) {
				TaFamilleActivite entity = null;
				if(value!=null){
					if(value instanceof TaFamilleActivite){
						entity=(TaFamilleActivite) value;
					}else if (value instanceof TaFamilleActiviteDTO){
						entity = taFamilleActiviteService.findByCode(((TaFamilleActiviteDTO)value).getCodeFamilleActivite());	
						
					}else{
						entity = taFamilleActiviteService.findByCode((String)value);
					}
				}else{
					nouveau.setCodeFamilleActivite("");
					taFamilleActiviteDTO.setCodeFamilleActivite("");
					taFamilleActivite=null;
				}						
				taActivite.setTaFamilleActivite(entity);
				taFamilleActivite = entity;
			}
			
			
			if(nomChamp.equals(Const.C_CODE_CLASSE)) {
				TaClasse entity = null;
				if(value!=null){
					if(value instanceof TaClasse){
						entity=(TaClasse) value;
					}else if (value instanceof TaClasseDTO){
						entity = taClasseService.findByCode(((TaClasseDTO)value).getCodeClasse());	
						
					}else{
						entity = taClasseService.findByCode((String)value);
					}
				}else{
					nouveau.setCodeClasse("");
					taClasseDTO.setCodeClasse("");
					taClasse=null;
				}						
				taActivite.setTaClasse(entity);
				taClasse = entity;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}			

	public void actDialogTypes(ActionEvent actionEvent) {
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 320);
        options.put("modal", true);
        
        Map<String,List<String>> params = new HashMap<String,List<String>>();
        List<String> list = new ArrayList<String>();
        list.add(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION));
        params.put("modeEcranDefaut", list);
        
        RequestContext.getCurrentInstance().openDialog("parametres/dialog_type_activite", options, params);
	}
	
	public void handleReturnDialogTypes(SelectEvent event) {
		if(event!=null && event.getObject()!=null) {
			taActivite = (TaActivite) event.getObject();
		}
		refresh();
	}
	
	public void actDialogModifier(ActionEvent actionEvent){
		nouveau = selection;
//		modeEcran.setMode(EnumModeObjet.C_MO_EDITION);
		
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 320);
        options.put("modal", true);
        
        Map<String,List<String>> params = new HashMap<String,List<String>>();
        List<String> list = new ArrayList<String>();
        list.add(modeEcran.modeString(EnumModeObjet.C_MO_EDITION));
        params.put("modeEcranDefaut", list);
        List<String> list2 = new ArrayList<String>();
        list2.add(LibConversion.integerToString(selection.getId()));
        params.put("idEntity", list2);
        
        RequestContext.getCurrentInstance().openDialog("parametres/dialog_type_activite", options, params);

	}
	
	public List<TaFamilleActiviteDTO> typeFamilleActiviteAutoCompleteLight(String query) {
        List<TaFamilleActiviteDTO> allValues = taFamilleActiviteService.selectAllDTO();
        List<TaFamilleActiviteDTO> filteredValues = new ArrayList<TaFamilleActiviteDTO>();
         
        for (int i = 0; i < allValues.size(); i++) {
        	TaFamilleActiviteDTO civ = allValues.get(i);
            if(query.equals("*") || civ.getCodeFamilleActivite().toLowerCase().contains(query.toLowerCase())) {
                filteredValues.add(civ);
            }
        }
	
        return filteredValues;
	}
	
	public List<TaClasseDTO> typeClasseAutoCompleteLight(String query) {
        List<TaClasseDTO> allValues = taClasseService.selectAllDTO();
        List<TaClasseDTO> filteredValues = new ArrayList<TaClasseDTO>();
         
        for (int i = 0; i < allValues.size(); i++) {
        	TaClasseDTO civ = allValues.get(i);
            if(query.equals("*") || civ.getCodeClasse().toLowerCase().contains(query.toLowerCase())) {
                filteredValues.add(civ);
            }
        }
	
        return filteredValues;
	}
	
	public boolean editable() {
		return !modeEcran.dataSetEnModif();
	}
	
public void actDialogTypeFamilleActivite(ActionEvent actionEvent) {
		
//		RequestContext.getCurrentInstance().openDialog("aide");
    
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 640);
        options.put("modal", true);
        
        //Map<String,List<String>> params = null;
        Map<String,List<String>> params = new HashMap<String,List<String>>();
        List<String> list = new ArrayList<String>();
        list.add(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION));
        params.put("modeEcranDefaut", list);
        
        RequestContext.getCurrentInstance().openDialog("parametres/dialog_type_activite", options, params);
		
//		FacesContext context = FacesContext.getCurrentInstance();  
//		context.addMessage(null, new FacesMessage("Tiers", "actAbout")); 	    
	}

public void actDialogTypeClasse(ActionEvent actionEvent) {
	

    Map<String,Object> options = new HashMap<String, Object>();
    options.put("modal", true);
    options.put("draggable", false);
    options.put("resizable", false);
    options.put("contentHeight", 640);
    options.put("modal", true);
    
    //Map<String,List<String>> params = null;
    Map<String,List<String>> params = new HashMap<String,List<String>>();
    List<String> list = new ArrayList<String>();
    list.add(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION));
    params.put("modeEcranDefaut", list);
    
    RequestContext.getCurrentInstance().openDialog("parametres/dialog_type_activite", options, params);
	    
}
	
	public void handleReturnDialogTypeFamilleActivite(SelectEvent event) {
		if(event!=null && event.getObject()!=null) {
			taFamilleActivite = (TaFamilleActivite) event.getObject();
			try {
				taFamilleActiviteDTO = taFamilleActiviteService.findByCodeDTO(taFamilleActivite.getCodeFamilleActivite());
				//setTaTAssuranceDTO(taTAssuranceService.findByCodeDTO(taTAssurance.getCodeTAssurance()));
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void handleReturnDialogTypeClasse(SelectEvent event) {
		if(event!=null && event.getObject()!=null) {
			taClasse = (TaClasse) event.getObject();
			try {
				taClasseDTO = taClasseService.findByCodeDTO(taClasse.getCodeClasse());
				
			} catch (FinderException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	public void autcompleteSelection(SelectEvent event) {
		Object value = event.getObject();
//		FacesMessage msg = new FacesMessage("Selected", "Item:" + value);
		
		String nomChamp =  (String) event.getComponent().getAttributes().get("nomChamp");

		if(value!=null) {
		if(value instanceof String && value.equals(Const.C_AUCUN))value="";	
		if(value instanceof TaFamilleActiviteDTO && ((TaFamilleActiviteDTO) value).getCodeFamilleActivite()!=null && ((TaFamilleActiviteDTO) value).getCodeFamilleActivite().equals(Const.C_AUCUN))value=null;
		if(value instanceof TaClasseDTO && ((TaClasseDTO) value).getCodeClasse()!=null && ((TaClasseDTO) value).getCodeClasse().equals(Const.C_AUCUN))value=null;
		
		}
		
		validateUIField(nomChamp,value);
	}
	
	public void actFermerDialog(ActionEvent actionEvent) {
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	public void onRowReorder(ReorderEvent event) {
		int posDe = event.getFromIndex()+1;
		int posAp = event.getToIndex()+1;
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ligne déplacé", "de position: " + posDe + ", à position: " + posAp);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        int i  = 0;
        for (Object v : values) {
        	i++;
        	System.out.println(values.indexOf(v));
        	((TaActiviteDTO) v).setPosition(i);
        	nouveau = ((TaActiviteDTO) v);
        	actModifierPositionListe();
        }
        
    }
	
	public List<TaActiviteDTO> getValues(){  
		return values;
	}

	public TaActiviteDTO getNouveau() {
		return nouveau;
	}

	public void setNouveau(TaActiviteDTO newTaActivite) {
		this.nouveau = newTaActivite;
	}

	public TaActiviteDTO getSelection() {
		return selection;
	}

	public void setSelection(TaActiviteDTO selectedTaActivite) {
		this.selection = selectedTaActivite;
	}

	public void setValues(List<TaActiviteDTO> values) {
		this.values = values;
	}

	public List<TaActiviteDTO> getFilteredValues() {
		return filteredValues;
	}

	public void setFilteredValues(List<TaActiviteDTO> filteredValues) {
		this.filteredValues = filteredValues;
	}

	public ModeObjetEcranServeur getModeEcran() {
		return modeEcran;
	}

	public TaClasseDTO getTaClasseDTO() {
		return taClasseDTO;
	}

	public void setTaClasseDTO(TaClasseDTO taClasseDTO) {
		this.taClasseDTO = taClasseDTO;
	}

	public TaClasse getTaClasse() {
		return taClasse;
	}

	public void setTaClasse(TaClasse taClasse) {
		this.taClasse = taClasse;
	}
	
}  
