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
import fr.ylyade.courtage.dto.TaTMaitriseOeuvreDTO;
import fr.ylyade.courtage.model.Const;
import fr.ylyade.courtage.model.TaTMaitriseOeuvre;
import fr.ylyade.courtage.model.mapping.LgrDozerMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTMaitriseOeuvreServiceRemote;

@Named
@ViewScoped  
public class TypeMaitriseOeuvreController implements Serializable {  

	/**
	 * 
	 */
	private static final long serialVersionUID = -2058997507774120036L;
	private List<TaTMaitriseOeuvreDTO> values; 
	private List<TaTMaitriseOeuvreDTO> filteredValues; 
	private TaTMaitriseOeuvreDTO nouveau ;
	private TaTMaitriseOeuvreDTO selection ;
	
	private TaTMaitriseOeuvre taTMaitriseOeuvre = new TaTMaitriseOeuvre();
	
	private String modeEcranDefaut;
	private static final String C_DIALOG = "dialog";

	private ModeObjetEcranServeur modeEcran = new ModeObjetEcranServeur();

	private @EJB ITaTMaitriseOeuvreServiceRemote taTMaitriseOeuvreService;
	
	private LgrDozerMapper<TaTMaitriseOeuvreDTO,TaTMaitriseOeuvre> mapperUIToModel  = new LgrDozerMapper<TaTMaitriseOeuvreDTO,TaTMaitriseOeuvre>();
	private LgrDozerMapper<TaTMaitriseOeuvre,TaTMaitriseOeuvreDTO> mapperModelToUI  = new LgrDozerMapper<TaTMaitriseOeuvre,TaTMaitriseOeuvreDTO>();

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
		values = taTMaitriseOeuvreService.selectAllDTO();
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		modeEcranDefaut = params.get("modeEcranDefaut");
		if(modeEcranDefaut!=null && modeEcranDefaut.equals(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION))) {
			modeEcranDefaut = C_DIALOG;
			actInserer(null);
		} else if(modeEcranDefaut!=null && modeEcranDefaut.equals(modeEcran.modeString(EnumModeObjet.C_MO_EDITION))) {
			modeEcranDefaut = C_DIALOG;
			if(params.get("idEntity")!=null) {
				try {
					selection = taTMaitriseOeuvreService.findByIdDTO(LibConversion.stringToInteger(params.get("idEntity")));
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
	
	public TypeMaitriseOeuvreController() {  
	}  

	public void actAnnuler(ActionEvent actionEvent){
		
		if(values.size()>= 1){
			selection = values.get(0);
		}		
		nouveau = new TaTMaitriseOeuvreDTO();
		
		modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
	}

	public void actEnregistrer(ActionEvent actionEvent){
		try {
			TaTMaitriseOeuvreDTO retour = null;
			taTMaitriseOeuvre=new TaTMaitriseOeuvre();
			if(nouveau.getId()==null || taTMaitriseOeuvreService.findById(nouveau.getId()) == null){
				mapperUIToModel.map(nouveau, taTMaitriseOeuvre);
				taTMaitriseOeuvre = taTMaitriseOeuvreService.merge(taTMaitriseOeuvre, ITaTMaitriseOeuvreServiceRemote.validationContext);
				mapperModelToUI.map(taTMaitriseOeuvre, nouveau);
				values= taTMaitriseOeuvreService.selectAllDTO();
				nouveau = values.get(0);
				nouveau = new TaTMaitriseOeuvreDTO();

				modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);

			}else{
				if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_EDITION)==0){
					taTMaitriseOeuvre = taTMaitriseOeuvreService.findById(nouveau.getId());
					mapperUIToModel.map(nouveau, taTMaitriseOeuvre);
					taTMaitriseOeuvre = taTMaitriseOeuvreService.merge(taTMaitriseOeuvre, ITaTMaitriseOeuvreServiceRemote.validationContext);
					mapperModelToUI.map(taTMaitriseOeuvre, nouveau);
					values= taTMaitriseOeuvreService.selectAllDTO();
					nouveau = values.get(0);
					nouveau = new TaTMaitriseOeuvreDTO();

					modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
				}
				else{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Code déja utilisé"));
				}
			}
			if(modeEcranDefaut!=null && modeEcranDefaut.equals(C_DIALOG)) {
				RequestContext.getCurrentInstance().closeDialog(taTMaitriseOeuvre);
			}
		} catch(Exception e) {
			e=ThrowableExceptionLgr.renvoieCauseRuntimeException(e);
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "TypeMaitriseOeuvre", e.getMessage()));
		}
	}
	
	public void actInserer(ActionEvent actionEvent){
		nouveau = new TaTMaitriseOeuvreDTO();
	
		modeEcran.setMode(EnumModeObjet.C_MO_INSERTION);
	}

	public void actModifier(ActionEvent actionEvent){
		nouveau = selection;
		
		modeEcran.setMode(EnumModeObjet.C_MO_EDITION);
	}

	public void actSupprimer(){
		TaTMaitriseOeuvre taTMaitriseOeuvre = new TaTMaitriseOeuvre();
		try {
			if(selection!=null && selection.getId()!=null){
				taTMaitriseOeuvre = taTMaitriseOeuvreService.findById(selection.getId());
			}

			taTMaitriseOeuvreService.remove(taTMaitriseOeuvre);
			//values.remove(selection);
			values = taTMaitriseOeuvreService.selectAllDTO();
			
			if(!values.isEmpty()) {
				selection = values.get(0);
			}else {
				selection=new TaTMaitriseOeuvreDTO();
			}

			modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);

			if(ConstWeb.DEBUG) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage("Type maitrise oeuvre", "actSupprimer"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Type maitrise oeuvre", e.getCause().getCause().getCause().getCause().getMessage()));
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
			Set<ConstraintViolation<TaTMaitriseOeuvreDTO>> violations = factory.getValidator().validateValue(TaTMaitriseOeuvreDTO.class,nomChamp,value);
			if (violations.size() > 0) {
				messageComplet = "Erreur de validation : ";
				for (ConstraintViolation<TaTMaitriseOeuvreDTO> cv : violations) {
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

	public boolean validateUIField(String nomChamp,Object value) {
		try {
			if(nomChamp.equals(Const.C_CODE_T_MAITRISE_OEUVRE)) {
				boolean changement=false;
				if(selection.getCodeTMaitriseOeuvre()!=null && value!=null && !selection.getCodeTMaitriseOeuvre().equals("")) {
					if(value instanceof TaTMaitriseOeuvre)
						changement=((TaTMaitriseOeuvre) value).getCodeTMaitriseOeuvre().equals(selection.getCodeTMaitriseOeuvre());
					else if(value instanceof String)
					changement=!value.equals(selection.getCodeTMaitriseOeuvre());
				}
				if(changement && modeEcran.dataSetEnModeModification()) {
					FacesContext context = FacesContext.getCurrentInstance();  
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Type Maitrise oeuvre", Const.C_MESSAGE_CHANGEMENT_CODE));
				}
			}
			return false;

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
        
        RequestContext.getCurrentInstance().openDialog("parametres/dialog_type_maitrise_oeuvre", options, params);
	}
	
	public void handleReturnDialogTypes(SelectEvent event) {
		if(event!=null && event.getObject()!=null) {
			taTMaitriseOeuvre = (TaTMaitriseOeuvre) event.getObject();
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
        
        RequestContext.getCurrentInstance().openDialog("parametres/dialog_type_maitrise_oeuvre", options, params);

	}
	
	public void actFermerDialog(ActionEvent actionEvent) {
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	public List<TaTMaitriseOeuvreDTO> getValues(){  
		return values;
	}

	public TaTMaitriseOeuvreDTO getNouveau() {
		return nouveau;
	}

	public void setNouveau(TaTMaitriseOeuvreDTO newTaTMaitriseOeuvre) {
		this.nouveau = newTaTMaitriseOeuvre;
	}

	public TaTMaitriseOeuvreDTO getSelection() {
		return selection;
	}

	public void setSelection(TaTMaitriseOeuvreDTO selectedTaTMaitriseOeuvre) {
		this.selection = selectedTaTMaitriseOeuvre;
	}

	public void setValues(List<TaTMaitriseOeuvreDTO> values) {
		this.values = values;
	}

	public List<TaTMaitriseOeuvreDTO> getFilteredValues() {
		return filteredValues;
	}

	public void setFilteredValues(List<TaTMaitriseOeuvreDTO> filteredValues) {
		this.filteredValues = filteredValues;
	}

	public ModeObjetEcranServeur getModeEcran() {
		return modeEcran;
	}
	
}  
