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
import fr.ylyade.courtage.dto.TaTAssuranceDTO;
import fr.ylyade.courtage.dto.TaTDocDTO;
import fr.ylyade.courtage.model.Const;
import fr.ylyade.courtage.model.TaTAssurance;
import fr.ylyade.courtage.model.TaTDoc;
import fr.ylyade.courtage.model.mapping.LgrDozerMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTAssuranceServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTDocServiceRemote;

@Named
@ViewScoped  
public class TypeDocController implements Serializable {  

	/**
	 * 
	 */
	private static final long serialVersionUID = 3981215550560204274L;
	private List<TaTDocDTO> values; 
	private List<TaTDocDTO> filteredValues; 
	private TaTDocDTO nouveau ;
	private TaTDocDTO selection ;
	
	private TaTDoc taTDoc = new TaTDoc();
	
	private String modeEcranDefaut;
	private static final String C_DIALOG = "dialog";

	private ModeObjetEcranServeur modeEcran = new ModeObjetEcranServeur();

	private @EJB ITaTDocServiceRemote taTDocService;
	private @EJB ITaTAssuranceServiceRemote taTAssuranceService;
	private LgrDozerMapper<TaTDocDTO,TaTDoc> mapperUIToModel  = new LgrDozerMapper<TaTDocDTO,TaTDoc>();
	private LgrDozerMapper<TaTDoc,TaTDocDTO> mapperModelToUI  = new LgrDozerMapper<TaTDoc,TaTDocDTO>();

	private TaTAssuranceDTO taTAssuranceDTO;
	private TaTAssurance taTAssurance;

	
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
		values = taTDocService.selectAllDTO();
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		modeEcranDefaut = params.get("modeEcranDefaut");
		if(modeEcranDefaut!=null && modeEcranDefaut.equals(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION))) {
			modeEcranDefaut = C_DIALOG;
			actInserer(null);
		} else if(modeEcranDefaut!=null && modeEcranDefaut.equals(modeEcran.modeString(EnumModeObjet.C_MO_EDITION))) {
			modeEcranDefaut = C_DIALOG;
			if(params.get("idEntity")!=null) {
				try {
					selection = taTDocService.findByIdDTO(LibConversion.stringToInteger(params.get("idEntity")));
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
	
	public TypeDocController() {  
	}  

	public void actAnnuler(ActionEvent actionEvent){
		
		if(values.size()>= 1){
			selection = values.get(0);
		}		
		nouveau = new TaTDocDTO();
		
		modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
	}

	public void actEnregistrer(ActionEvent actionEvent){
		try {
			TaTDocDTO retour = null;
			taTDoc=new TaTDoc();
			if(nouveau.getId()==null || taTDocService.findById(nouveau.getId()) == null){
				autoCompleteMapUIToDTO();
				mapperUIToModel.map(nouveau, taTDoc);
				taTDoc = taTDocService.merge(taTDoc, ITaTDocServiceRemote.validationContext);
				mapperModelToUI.map(taTDoc, nouveau);
				values= taTDocService.selectAllDTO();
				nouveau = values.get(0);
				nouveau = new TaTDocDTO();

				modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);

			}else{
				if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_EDITION)==0){
					taTDoc = taTDocService.findById(nouveau.getId());
					autoCompleteMapUIToDTO();
					mapperUIToModel.map(nouveau, taTDoc);
					taTDoc = taTDocService.merge(taTDoc, ITaTDocServiceRemote.validationContext);
					mapperModelToUI.map(taTDoc, nouveau);
					values= taTDocService.selectAllDTO();
					nouveau = values.get(0);
					nouveau = new TaTDocDTO();

					modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
				}
				else{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Code déja utilisé"));
				}
			}
			if(modeEcranDefaut!=null && modeEcranDefaut.equals(C_DIALOG)) {
				RequestContext.getCurrentInstance().closeDialog(taTDoc);
			}
		} catch(Exception e) {
			e=ThrowableExceptionLgr.renvoieCauseRuntimeException(e);
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "TypeDoc", e.getMessage()));
		}
	}
	
	public void actInserer(ActionEvent actionEvent){
		nouveau = new TaTDocDTO();
	
		modeEcran.setMode(EnumModeObjet.C_MO_INSERTION);
	}

	public void actModifier(ActionEvent actionEvent){
		nouveau = selection;
		autoCompleteMapDTOtoUI();
		modeEcran.setMode(EnumModeObjet.C_MO_EDITION);
	}

	public void actSupprimer(){
		TaTDoc taTDoc = new TaTDoc();
		try {
			if(selection!=null && selection.getId()!=null){
				taTDoc = taTDocService.findById(selection.getId());
			}

			taTDocService.remove(taTDoc);
			//values.remove(selection);
			values = taTDocService.selectAllDTO();
			
			if(!values.isEmpty()) {
				selection = values.get(0);
			}else {
				selection=new TaTDocDTO();
			}

			modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);

			if(ConstWeb.DEBUG) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage("Type doc", "actSupprimer"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Type doc", e.getCause().getCause().getCause().getCause().getMessage()));
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
			Set<ConstraintViolation<TaTDocDTO>> violations = factory.getValidator().validateValue(TaTDocDTO.class,nomChamp,value);
			if (violations.size() > 0) {
				messageComplet = "Erreur de validation : ";
				for (ConstraintViolation<TaTDocDTO> cv : violations) {
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
		if(taTAssurance!=null) {
			validateUIField(Const.C_CODE_T_ASSURANCE,taTAssurance.getCodeTAssurance());
			nouveau.setCodeTAssurance(taTAssurance.getCodeTAssurance());
		}
	}

	public void autoCompleteMapDTOtoUI() {
		try {
//			taTiers = null;
//			taTiersDTO = null;
			taTAssurance = null;
			taTAssuranceDTO = null;
			//			taTEntite = null;
//			if(selectedTaDevisDTO.getCodeTiers()!=null && !selectedTaDevisDTO.getCodeTiers().equals("")) {
//				taTiers = taTiersService.findByCode(selectedTaDevisDTO.getCodeTiers());
//				taTiersDTO = mapperModelToUITiers.map(taTiers, TaTiersDTO.class);
//			}
			if(nouveau.getCodeTAssurance()!=null && !nouveau.getCodeTAssurance().equals("")) {
				taTAssurance = taTAssuranceService.findByCode(nouveau.getCodeTAssurance());
				taTAssuranceDTO = taTAssuranceService.findByCodeDTO(nouveau.getCodeTAssurance());
			}
		} catch (FinderException e) {
			e.printStackTrace();
		}
	}

	public boolean validateUIField(String nomChamp,Object value) {
		
		try {
			if(nomChamp.equals(Const.C_CODE_T_DOC)) {
				boolean changement=false;
				if(selection.getCodeTDoc()!=null && value!=null && !selection.getCodeTDoc().equals("")) {
					if(value instanceof TaTDoc)
						changement=((TaTDoc) value).getCodeTDoc().equals(selection.getCodeTDoc());
					else if(value instanceof String)
					changement=!value.equals(selection.getCodeTDoc());
				}
				if(changement && modeEcran.dataSetEnModeModification()) {
					FacesContext context = FacesContext.getCurrentInstance();  
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Type de document", Const.C_MESSAGE_CHANGEMENT_CODE));
				}
			}
			if(nomChamp.equals(Const.C_CODE_T_ASSURANCE)) {
				TaTAssurance entity = null;
				if(value!=null){
					if(value instanceof TaTAssurance){
						entity=(TaTAssurance) value;
					}else if (value instanceof TaTAssuranceDTO){
						entity = taTAssuranceService.findByCode(((TaTAssuranceDTO)value).getCodeTAssurance());	
						
					}else{
						entity = taTAssuranceService.findByCode((String)value);
					}
				}else{
					nouveau.setCodeTAssurance("");
					taTAssuranceDTO.setCodeTAssurance("");
					taTAssurance=null;
				}						
				taTDoc.setTaTAssurance(entity);
				taTAssurance = entity;
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
        options.put("contentHeight", 350);
        options.put("modal", true);
        
        Map<String,List<String>> params = new HashMap<String,List<String>>();
        List<String> list = new ArrayList<String>();
        list.add(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION));
        params.put("modeEcranDefaut", list);
        
        RequestContext.getCurrentInstance().openDialog("parametres/dialog_type_doc", options, params);
	}
	
	public void handleReturnDialogTypes(SelectEvent event) {
		if(event!=null && event.getObject()!=null) {
			taTDoc = (TaTDoc) event.getObject();
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
        
        RequestContext.getCurrentInstance().openDialog("parametres/dialog_type_doc", options, params);

	}
	
	public List<TaTAssuranceDTO> typeAssuranceAutoCompleteLight(String query) {
        List<TaTAssuranceDTO> allValues = taTAssuranceService.selectAllDTO();
        List<TaTAssuranceDTO> filteredValues = new ArrayList<TaTAssuranceDTO>();
         
        for (int i = 0; i < allValues.size(); i++) {
        	TaTAssuranceDTO civ = allValues.get(i);
            if(query.equals("*") || civ.getCodeTAssurance().toLowerCase().contains(query.toLowerCase())) {
                filteredValues.add(civ);
            }
        }
	
        return filteredValues;
	}
	
	public boolean editable() {
		return !modeEcran.dataSetEnModif();
	}
	
public void actDialogTypeAssurance(ActionEvent actionEvent) {
		
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
        
        RequestContext.getCurrentInstance().openDialog("parametres/dialog_type_doc", options, params);
		
//		FacesContext context = FacesContext.getCurrentInstance();  
//		context.addMessage(null, new FacesMessage("Tiers", "actAbout")); 	    
	}
	
	public void handleReturnDialogTypeAssurance(SelectEvent event) {
		if(event!=null && event.getObject()!=null) {
			taTAssurance = (TaTAssurance) event.getObject();
			try {
				taTAssuranceDTO = taTAssuranceService.findByCodeDTO(taTAssurance.getCodeTAssurance());
				//setTaTAssuranceDTO(taTAssuranceService.findByCodeDTO(taTAssurance.getCodeTAssurance()));
			} catch (FinderException e) {
				// TODO Auto-generated catch block
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
		if(value instanceof TaTAssuranceDTO && ((TaTAssuranceDTO) value).getCodeTAssurance()!=null && ((TaTAssuranceDTO) value).getCodeTAssurance().equals(Const.C_AUCUN))value=null;	
		
		}
		
		validateUIField(nomChamp,value);
	}
	
	public void actFermerDialog(ActionEvent actionEvent) {
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	public List<TaTDocDTO> getValues(){  
		return values;
	}

	public TaTDocDTO getNouveau() {
		return nouveau;
	}

	public void setNouveau(TaTDocDTO newTaTDoc) {
		this.nouveau = newTaTDoc;
	}

	public TaTDocDTO getSelection() {
		return selection;
	}

	public void setSelection(TaTDocDTO selectedTaTDoc) {
		this.selection = selectedTaTDoc;
	}

	public void setValues(List<TaTDocDTO> values) {
		this.values = values;
	}

	public List<TaTDocDTO> getFilteredValues() {
		return filteredValues;
	}

	public void setFilteredValues(List<TaTDocDTO> filteredValues) {
		this.filteredValues = filteredValues;
	}

	public ModeObjetEcranServeur getModeEcran() {
		return modeEcran;
	}

	public TaTAssurance getTaTAssurance() {
		return taTAssurance;
	}

	public void setTaTAssurance(TaTAssurance taTAssurance) {
		this.taTAssurance = taTAssurance;
	}

	public TaTAssuranceDTO getTaTAssuranceDTO() {
		return taTAssuranceDTO;
	}

	public void setTaTAssuranceDTO(TaTAssuranceDTO taTAssuranceDTO) {
		this.taTAssuranceDTO = taTAssuranceDTO;
	}
	
}  
