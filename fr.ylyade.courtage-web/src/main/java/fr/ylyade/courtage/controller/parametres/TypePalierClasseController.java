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
import org.primefaces.event.SelectEvent;

import fr.legrain.courtage.service.interfaces.remote.general.ThrowableExceptionLgr;
import fr.legrain.lib.data.EnumModeObjet;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModeObjetEcranServeur;
import fr.ylyade.courtage.app.ConstWeb;
import fr.ylyade.courtage.dto.TaClasseDTO;
import fr.ylyade.courtage.dto.TaPalierClasseDTO;
import fr.ylyade.courtage.model.Const;
import fr.ylyade.courtage.model.TaClasse;
import fr.ylyade.courtage.model.TaPalierClasse;
import fr.ylyade.courtage.model.mapping.LgrDozerMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaClasseServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaPalierClasseServiceRemote;

@Named
@ViewScoped  
public class TypePalierClasseController implements Serializable {  

	/**
	 * 
	 */
	private static final long serialVersionUID = -428694750131737805L;
	private List<TaPalierClasseDTO> values; 
	private List<TaPalierClasseDTO> filteredValues; 
	private TaPalierClasseDTO nouveau ;
	private TaPalierClasseDTO selection ;
	
	private TaPalierClasse taPalierClasse = new TaPalierClasse();
	
	private String modeEcranDefaut;
	private static final String C_DIALOG = "dialog";

	private ModeObjetEcranServeur modeEcran = new ModeObjetEcranServeur();

	private @EJB ITaPalierClasseServiceRemote taPalierClasseService;
	private @EJB ITaClasseServiceRemote taClasseService;
	private LgrDozerMapper<TaPalierClasseDTO,TaPalierClasse> mapperUIToModel  = new LgrDozerMapper<TaPalierClasseDTO,TaPalierClasse>();
	private LgrDozerMapper<TaPalierClasse,TaPalierClasseDTO> mapperModelToUI  = new LgrDozerMapper<TaPalierClasse,TaPalierClasseDTO>();

	private TaClasseDTO taClasseDTO;
	private TaClasse taClasse;

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
		values = taPalierClasseService.selectAllDTO();
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		modeEcranDefaut = params.get("modeEcranDefaut");
		if(modeEcranDefaut!=null && modeEcranDefaut.equals(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION))) {
			modeEcranDefaut = C_DIALOG;
			actInserer(null);
		} else if(modeEcranDefaut!=null && modeEcranDefaut.equals(modeEcran.modeString(EnumModeObjet.C_MO_EDITION))) {
			modeEcranDefaut = C_DIALOG;
			if(params.get("idEntity")!=null) {
				try {
					selection = taPalierClasseService.findByIdDTO(LibConversion.stringToInteger(params.get("idEntity")));
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
	
	public TypePalierClasseController() {  
	}  

	public void actAnnuler(ActionEvent actionEvent){
		
		if(values.size()>= 1){
			selection = values.get(0);
		}		
		nouveau = new TaPalierClasseDTO();
		
		modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
	}

	public void actEnregistrer(ActionEvent actionEvent){
		try {
			TaPalierClasseDTO retour = null;
			taPalierClasse=new TaPalierClasse();
			if(nouveau.getId()==null || taPalierClasseService.findById(nouveau.getId()) == null){
				autoCompleteMapUIToDTO();
				mapperUIToModel.map(nouveau, taPalierClasse);
				taPalierClasse = taPalierClasseService.merge(taPalierClasse, ITaPalierClasseServiceRemote.validationContext);
				mapperModelToUI.map(taPalierClasse, nouveau);
				values= taPalierClasseService.selectAllDTO();
				nouveau = values.get(0);
				nouveau = new TaPalierClasseDTO();

				modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);

			}else{
				if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_EDITION)==0){
					taPalierClasse = taPalierClasseService.findById(nouveau.getId());
					autoCompleteMapUIToDTO();
					mapperUIToModel.map(nouveau, taPalierClasse);
					taPalierClasse = taPalierClasseService.merge(taPalierClasse, ITaPalierClasseServiceRemote.validationContext);
					mapperModelToUI.map(taPalierClasse, nouveau);
					values= taPalierClasseService.selectAllDTO();
					nouveau = values.get(0);
					nouveau = new TaPalierClasseDTO();

					modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
				}
				else{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Code déja utilisé"));
				}
			}
			if(modeEcranDefaut!=null && modeEcranDefaut.equals(C_DIALOG)) {
				RequestContext.getCurrentInstance().closeDialog(taPalierClasse);
			}
		} catch(Exception e) {
			e=ThrowableExceptionLgr.renvoieCauseRuntimeException(e);
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Type palier de classe", e.getMessage()));
		}
	}
	
	public void actInserer(ActionEvent actionEvent){
		nouveau = new TaPalierClasseDTO();
	
		modeEcran.setMode(EnumModeObjet.C_MO_INSERTION);
	}

	public void actModifier(ActionEvent actionEvent){
		nouveau = selection;
		autoCompleteMapDTOtoUI();
		modeEcran.setMode(EnumModeObjet.C_MO_EDITION);
	}

	public void actSupprimer(){
		TaPalierClasse taPalierClasse = new TaPalierClasse();
		try {
			if(selection!=null && selection.getId()!=null){
				taPalierClasse = taPalierClasseService.findById(selection.getId());
			}

			taPalierClasseService.remove(taPalierClasse);
			//values.remove(selection);
			values = taPalierClasseService.selectAllDTO();
			
			if(!values.isEmpty()) {
				selection = values.get(0);
			}else {
				selection=new TaPalierClasseDTO();
			}

			modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);

			if(ConstWeb.DEBUG) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage("Type palier de classe", "actSupprimer"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Type palier de classe", e.getCause().getCause().getCause().getCause().getMessage()));
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
			Set<ConstraintViolation<TaPalierClasseDTO>> violations = factory.getValidator().validateValue(TaPalierClasseDTO.class,nomChamp,value);
			if (violations.size() > 0) {
				messageComplet = "Erreur de validation : ";
				for (ConstraintViolation<TaPalierClasseDTO> cv : violations) {
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
		
		if(taClasse!=null) {
			validateUIField(Const.C_CODE_CLASSE,taClasse.getCodeClasse());
			nouveau.setCodeClasse(taClasse.getCodeClasse());
		}
	}

	public void autoCompleteMapDTOtoUI() {
		try {

			taClasseDTO = null;
			taClasse = null;				
			
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
			if(nomChamp.equals(Const.C_CODE_PALIER_CLASSE)) {
				boolean changement=false;
				if(selection.getCodePalierClasse()!=null && value!=null && !selection.getCodePalierClasse().equals("")) {
					if(value instanceof TaPalierClasse)
						changement=((TaPalierClasse) value).getCodePalierClasse().equals(selection.getCodePalierClasse());
					else if(value instanceof String)
					changement=!value.equals(selection.getCodePalierClasse());
				}
				if(changement && modeEcran.dataSetEnModeModification()) {
					FacesContext context = FacesContext.getCurrentInstance();  
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Type de palier de classe", Const.C_MESSAGE_CHANGEMENT_CODE));
				}
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
				taPalierClasse.setTaClasse(entity);
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
        options.put("contentHeight", 420);
        options.put("modal", true);
        
        Map<String,List<String>> params = new HashMap<String,List<String>>();
        List<String> list = new ArrayList<String>();
        list.add(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION));
        params.put("modeEcranDefaut", list);
        
        RequestContext.getCurrentInstance().openDialog("parametres/dialog_type_palier_classe", options, params);
	}
	
	public void handleReturnDialogTypes(SelectEvent event) {
		if(event!=null && event.getObject()!=null) {
			taPalierClasse = (TaPalierClasse) event.getObject();
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
        options.put("contentHeight", 420);
        options.put("modal", true);
        
        Map<String,List<String>> params = new HashMap<String,List<String>>();
        List<String> list = new ArrayList<String>();
        list.add(modeEcran.modeString(EnumModeObjet.C_MO_EDITION));
        params.put("modeEcranDefaut", list);
        List<String> list2 = new ArrayList<String>();
        list2.add(LibConversion.integerToString(selection.getId()));
        params.put("idEntity", list2);
        
        RequestContext.getCurrentInstance().openDialog("parametres/dialog_type_palier_classe", options, params);

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
    
    RequestContext.getCurrentInstance().openDialog("parametres/dialog_type_palier_classe", options, params);
	    
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
		if(value instanceof TaClasseDTO && ((TaClasseDTO) value).getCodeClasse()!=null && ((TaClasseDTO) value).getCodeClasse().equals(Const.C_AUCUN))value=null;
		
		}
		
		validateUIField(nomChamp,value);
	}
	
	public void actFermerDialog(ActionEvent actionEvent) {
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	public List<TaPalierClasseDTO> getValues(){  
		return values;
	}

	public TaPalierClasseDTO getNouveau() {
		return nouveau;
	}

	public void setNouveau(TaPalierClasseDTO newTaPalierClasse) {
		this.nouveau = newTaPalierClasse;
	}

	public TaPalierClasseDTO getSelection() {
		return selection;
	}

	public void setSelection(TaPalierClasseDTO selectedTaPalierClasse) {
		this.selection = selectedTaPalierClasse;
	}

	public void setValues(List<TaPalierClasseDTO> values) {
		this.values = values;
	}

	public List<TaPalierClasseDTO> getFilteredValues() {
		return filteredValues;
	}

	public void setFilteredValues(List<TaPalierClasseDTO> filteredValues) {
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
