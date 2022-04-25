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
import fr.ylyade.courtage.dto.TaListeRefDocDTO;
import fr.ylyade.courtage.dto.TaTListeRefDocDTO;
import fr.ylyade.courtage.model.Const;
import fr.ylyade.courtage.model.TaListeRefDoc;
import fr.ylyade.courtage.model.TaTListeRefDoc;
import fr.ylyade.courtage.model.mapping.LgrDozerMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaListeRefDocServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTListeRefDocServiceRemote;

@Named
@ViewScoped  
public class ListeRefDocController implements Serializable {  

	/**
	 * 
	 */
	private static final long serialVersionUID = -6839352942988817167L;
	private List<TaListeRefDocDTO> values; 
	private List<TaListeRefDocDTO> filteredValues; 
	private TaListeRefDocDTO nouveau ;
	private TaListeRefDocDTO selection ;
	
	private List<TaListeRefDocDTO> listeByType;
	
	private TaListeRefDoc taListeRefDoc = new TaListeRefDoc();
	
	private String modeEcranDefaut;
	private static final String C_DIALOG = "dialog";

	private ModeObjetEcranServeur modeEcran = new ModeObjetEcranServeur();

	private @EJB ITaListeRefDocServiceRemote taListeRefDocService;
	private @EJB ITaTListeRefDocServiceRemote taTListeRefDocService;
	
	private LgrDozerMapper<TaListeRefDocDTO,TaListeRefDoc> mapperUIToModel  = new LgrDozerMapper<TaListeRefDocDTO,TaListeRefDoc>();
	private LgrDozerMapper<TaListeRefDoc,TaListeRefDocDTO> mapperModelToUI  = new LgrDozerMapper<TaListeRefDoc,TaListeRefDocDTO>();
	
	private TaTListeRefDocDTO taTListeRefDocDTO;
	private TaTListeRefDoc taTListeRefDoc;

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
	
	public void initOngletGed() {
		setListeByType(taListeRefDocService.findByType(2));
	}
	


	public void refresh(){
		values = taListeRefDocService.selectAllDTO();
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		modeEcranDefaut = params.get("modeEcranDefaut");
		if(modeEcranDefaut!=null && modeEcranDefaut.equals(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION))) {
			modeEcranDefaut = C_DIALOG;
			actInserer(null);
		} else if(modeEcranDefaut!=null && modeEcranDefaut.equals(modeEcran.modeString(EnumModeObjet.C_MO_EDITION))) {
			modeEcranDefaut = C_DIALOG;
			if(params.get("idEntity")!=null) {
				try {
					selection = taListeRefDocService.findByIdDTO(LibConversion.stringToInteger(params.get("idEntity")));
				} catch (FinderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			actModifier(null);
		} else {
			modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
		}
		initOngletGed();
	}
	
	public ListeRefDocController() {  
	}  
	
	public void actAnnuler(ActionEvent actionEvent){
		
		if(values.size()>= 1){
			selection = values.get(0);
		}		
		nouveau = new TaListeRefDocDTO();
		
		modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
	}

	public void actEnregistrer(ActionEvent actionEvent){
		try {
			TaListeRefDocDTO retour = null;
			taListeRefDoc=new TaListeRefDoc();
			if(nouveau.getId()==null || taListeRefDocService.findById(nouveau.getId()) == null){
				autoCompleteMapUIToDTO();
				mapperUIToModel.map(nouveau, taListeRefDoc);
				taListeRefDoc = taListeRefDocService.merge(taListeRefDoc, ITaListeRefDocServiceRemote.validationContext);
				mapperModelToUI.map(taListeRefDoc, nouveau);
				values= taListeRefDocService.selectAllDTO();
				nouveau = values.get(0);
				nouveau = new TaListeRefDocDTO();

				modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);

			}else{
				if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_EDITION)==0){
					taListeRefDoc = taListeRefDocService.findById(nouveau.getId());
					autoCompleteMapUIToDTO();
					mapperUIToModel.map(nouveau, taListeRefDoc);
					taListeRefDoc = taListeRefDocService.merge(taListeRefDoc, ITaListeRefDocServiceRemote.validationContext);
					mapperModelToUI.map(taListeRefDoc, nouveau);
					values= taListeRefDocService.selectAllDTO();
					nouveau = values.get(0);
					nouveau = new TaListeRefDocDTO();

					modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
				}
				else{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Code déja utilisé"));
				}
			}
			if(modeEcranDefaut!=null && modeEcranDefaut.equals(C_DIALOG)) {
				RequestContext.getCurrentInstance().closeDialog(taListeRefDoc);
			}
		} catch(Exception e) {
			e=ThrowableExceptionLgr.renvoieCauseRuntimeException(e);
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ListeRefDoc", e.getMessage()));
		}
	}
	
	public void actInserer(ActionEvent actionEvent){
		nouveau = new TaListeRefDocDTO();
	
		modeEcran.setMode(EnumModeObjet.C_MO_INSERTION);
	}

	public void actModifier(ActionEvent actionEvent){
		nouveau = selection;
		
		modeEcran.setMode(EnumModeObjet.C_MO_EDITION);
	}

	public void actSupprimer(){
		TaListeRefDoc taTListeRefDoc = new TaListeRefDoc();
		try {
			if(selection!=null && selection.getId()!=null){
				taTListeRefDoc = taListeRefDocService.findById(selection.getId());
			}

			taListeRefDocService.remove(taTListeRefDoc);
			//values.remove(selection);
			values = taListeRefDocService.selectAllDTO();
			
			if(!values.isEmpty()) {
				selection = values.get(0);
			}else {
				selection=new TaListeRefDocDTO();
			}

			modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);

			if(ConstWeb.DEBUG) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage("liste ref doc", "actSupprimer"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "liste ref doc", e.getCause().getCause().getCause().getCause().getMessage()));
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
			Set<ConstraintViolation<TaListeRefDocDTO>> violations = factory.getValidator().validateValue(TaListeRefDocDTO.class,nomChamp,value);
			if (violations.size() > 0) {
				messageComplet = "Erreur de validation : ";
				for (ConstraintViolation<TaListeRefDocDTO> cv : violations) {
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
		if(taTListeRefDoc!=null) {
			validateUIField(Const.C_CODE_T_LISTE_REF_DOC,taTListeRefDoc.getCodeTListeRefDoc());
			nouveau.setCodeTListeRefDoc(taTListeRefDoc.getCodeTListeRefDoc());
		}
	}

	public void autoCompleteMapDTOtoUI() {
		try {
//			taTiers = null;
//			taTiersDTO = null;
			taTListeRefDoc = null;
			taTListeRefDocDTO = null;
			//			taTEntite = null;
//			if(selectedTaDevisDTO.getCodeTiers()!=null && !selectedTaDevisDTO.getCodeTiers().equals("")) {
//				taTiers = taTiersService.findByCode(selectedTaDevisDTO.getCodeTiers());
//				taTiersDTO = mapperModelToUITiers.map(taTiers, TaTiersDTO.class);
//			}
			if(nouveau.getCodeTListeRefDoc()!=null && !nouveau.getCodeTListeRefDoc().equals("")) {
				taTListeRefDoc = taTListeRefDocService.findByCode(nouveau.getCodeTListeRefDoc());
				taTListeRefDocDTO = taTListeRefDocService.findByCodeDTO(nouveau.getCodeTListeRefDoc());
			}
		} catch (FinderException e) {
			e.printStackTrace();
		}
	}

	public boolean validateUIField(String nomChamp,Object value) {
		try {
			if(nomChamp.equals(Const.C_CODE_LISTE_REF_DOC)) {
				boolean changement=false;
				if(selection.getCodeListeRefDoc()!=null && value!=null && !selection.getCodeListeRefDoc().equals("")) {
					if(value instanceof TaListeRefDoc)
						changement=((TaListeRefDoc) value).getCodeListeRefDoc().equals(selection.getCodeListeRefDoc());
					else if(value instanceof String)
					changement=!value.equals(selection.getCodeListeRefDoc());
				}
				if(changement && modeEcran.dataSetEnModeModification()) {
					FacesContext context = FacesContext.getCurrentInstance();  
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "liste ref doc", Const.C_MESSAGE_CHANGEMENT_CODE));
				}
			}
			
			if(nomChamp.equals(Const.C_CODE_T_LISTE_REF_DOC)) {
				TaTListeRefDoc entity = null;
				if(value!=null){
					if(value instanceof TaTListeRefDoc){
						entity=(TaTListeRefDoc) value;
					}else if (value instanceof TaTListeRefDocDTO){
						entity = taTListeRefDocService.findByCode(((TaTListeRefDocDTO)value).getCodeTListeRefDoc());	
						
					}else{
						entity = taTListeRefDocService.findByCode((String)value);
					}
				}else{
					nouveau.setCodeTListeRefDoc("");
					taTListeRefDocDTO.setCodeTListeRefDoc("");
					taTListeRefDoc=null;
				}						
				taListeRefDoc.setTaTListeRefDoc(entity);
				taTListeRefDoc = entity;
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
        
        RequestContext.getCurrentInstance().openDialog("parametres/dialog_liste_ref_doc", options, params);
	}
	
	public void handleReturnDialogTypes(SelectEvent event) {
		if(event!=null && event.getObject()!=null) {
			taListeRefDoc = (TaListeRefDoc) event.getObject();
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
        
        RequestContext.getCurrentInstance().openDialog("parametres/dialog_liste_ref_doc", options, params);

	}
	
	public List<TaTListeRefDocDTO> typeListeRefDocAutoCompleteLight(String query) {
        List<TaTListeRefDocDTO> allValues = taTListeRefDocService.selectAllDTO();
        List<TaTListeRefDocDTO> filteredValues = new ArrayList<TaTListeRefDocDTO>();
         
        for (int i = 0; i < allValues.size(); i++) {
        	TaTListeRefDocDTO civ = allValues.get(i);
            if(query.equals("*") || civ.getCodeTListeRefDoc().toLowerCase().contains(query.toLowerCase())) {
                filteredValues.add(civ);
            }
        }
	
        return filteredValues;
	}
	
	public boolean editable() {
		return !modeEcran.dataSetEnModif();
	}
	
	public void actDialogTypeListeRefDoc(ActionEvent actionEvent) {
		
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
        
        RequestContext.getCurrentInstance().openDialog("parametres/dialog_liste_ref_doc", options, params);
		
//		FacesContext context = FacesContext.getCurrentInstance();  
//		context.addMessage(null, new FacesMessage("Tiers", "actAbout")); 	    
	}
	
	public void handleReturnDialogTypeListeRefDoc(SelectEvent event) {
		if(event!=null && event.getObject()!=null) {
			taTListeRefDoc = (TaTListeRefDoc) event.getObject();
			try {
				taTListeRefDocDTO = taTListeRefDocService.findByCodeDTO(taTListeRefDoc.getCodeTListeRefDoc());
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
		if(value instanceof TaTListeRefDocDTO && ((TaTListeRefDocDTO) value).getCodeTListeRefDoc()!=null && ((TaTListeRefDocDTO) value).getCodeTListeRefDoc().equals(Const.C_AUCUN))value=null;	
		
		}
		
		validateUIField(nomChamp,value);
	}
	
	public void actFermerDialog(ActionEvent actionEvent) {
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	public List<TaListeRefDocDTO> getValues(){  
		return values;
	}

	public TaListeRefDocDTO getNouveau() {
		return nouveau;
	}

	public void setNouveau(TaListeRefDocDTO newTaListeRefDoc) {
		this.nouveau = newTaListeRefDoc;
	}

	public TaListeRefDocDTO getSelection() {
		return selection;
	}

	public void setSelection(TaListeRefDocDTO selectedTaListeRefDoc) {
		this.selection = selectedTaListeRefDoc;
	}

	public void setValues(List<TaListeRefDocDTO> values) {
		this.values = values;
	}

	public List<TaListeRefDocDTO> getFilteredValues() {
		return filteredValues;
	}

	public void setFilteredValues(List<TaListeRefDocDTO> filteredValues) {
		this.filteredValues = filteredValues;
	}

	public ModeObjetEcranServeur getModeEcran() {
		return modeEcran;
	}

	public TaTListeRefDocDTO getTaTListeRefDocDTO() {
		return taTListeRefDocDTO;
	}

	public void setTaTListeRefDocDTO(TaTListeRefDocDTO taTListeRefDocDTO) {
		this.taTListeRefDocDTO = taTListeRefDocDTO;
	}

	public TaTListeRefDoc getTaTListeRefDoc() {
		return taTListeRefDoc;
	}

	public void setTaTListeRefDoc(TaTListeRefDoc taTListeRefDoc) {
		this.taTListeRefDoc = taTListeRefDoc;
	}

	public List<TaListeRefDocDTO> getListeByType() {
		return listeByType;
	}

	public void setListeByType(List<TaListeRefDocDTO> listeByType) {
		this.listeByType = listeByType;
	}
	
}  
