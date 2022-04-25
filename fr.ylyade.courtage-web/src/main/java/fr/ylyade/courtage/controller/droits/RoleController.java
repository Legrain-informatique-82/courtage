package fr.ylyade.courtage.controller.droits;

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
import fr.legrain.data.EnumModeObjet;
import fr.legrain.data.ModeObjetEcranServeur;
import fr.ylyade.courtage.app.ConstWeb;
import fr.ylyade.courtage.droits.model.TaRole;
import fr.ylyade.courtage.droits.model.dto.TaRoleDTO;
import fr.ylyade.courtage.droits.model.dto.TaUtilisateurDTO;
import fr.ylyade.courtage.droits.service.interfaces.remote.ITaRoleServiceRemote;
import fr.ylyade.courtage.droits.service.interfaces.remote.ITaUtilisateurServiceRemote;
import fr.ylyade.courtage.model.mapping.LgrDozerMapper;

@Named
@ViewScoped  
public class RoleController implements Serializable {  

	/**
	 * 
	 */
	private static final long serialVersionUID = -2010723616372041012L;
	private List<TaRoleDTO> values; 
	private List<TaRoleDTO> filteredValues; 
	private TaRoleDTO nouveau ;
	private TaRoleDTO selection ;
	private Boolean tableVide;
	
	private TaRole taRole = new TaRole();
	
	private static final String C_DIALOG = "dialog";
	
	private String modeEcranDefaut;
	private ModeObjetEcranServeur modeEcran = new ModeObjetEcranServeur();

	private @EJB ITaRoleServiceRemote taRoleService;
	
	private LgrDozerMapper<TaRoleDTO,TaRole> mapperUIToModel  = new LgrDozerMapper<TaRoleDTO,TaRole>();
	private LgrDozerMapper<TaRole,TaRoleDTO> mapperModelToUI  = new LgrDozerMapper<TaRole,TaRoleDTO>();

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
		this.setFilteredValues(values);
	}
	
	public void refresh(){
		values = taRoleService.selectAllDTO();
		modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
		
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		modeEcranDefaut = params.get("modeEcranDefaut");
		if(modeEcranDefaut!=null && modeEcranDefaut.equals(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION))) {
			modeEcranDefaut = C_DIALOG;
			actInserer(null);
		} else if(modeEcranDefaut!=null && modeEcranDefaut.equals(modeEcran.modeString(EnumModeObjet.C_MO_EDITION))) {
			modeEcranDefaut = C_DIALOG;
			if(params.get("idEntity")!=null) {
				try {
					selection = taRoleService.findByIdDTO(Integer.parseInt(params.get("idEntity")));
					taRole = taRoleService.findById(selection.getId());
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

	public RoleController() {  
	}  

	public void actAnnuler(ActionEvent actionEvent){
		if(values.size()>= 1){
			selection = values.get(0);
		}		
		nouveau = new TaRoleDTO();
		
		modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
	}
	
	public void autoCompleteMapUIToDTO() {
			
	}
	
	public void autoCompleteMapDTOtoUI() {
		
	}
		
	
	public void actDialogTypes(ActionEvent actionEvent) {

		Map<String,Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("draggable", true);
		options.put("resizable", false);
		options.put("contentHeight", 320);
		options.put("modal", true);

		Map<String,List<String>> params = new HashMap<String,List<String>>();
		List<String> list = new ArrayList<String>();
		list.add(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION));
		params.put("modeEcranDefaut", list);

		RequestContext.getCurrentInstance().openDialog("admin/dialog_roles", options, params);

	}
	
	public void actDialogModifier(ActionEvent actionEvent){

		nouveau = selection;
		//		modeEcran.setMode(EnumModeObjet.C_MO_EDITION);

		Map<String,Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("draggable", true);
		options.put("resizable", true);
		options.put("contentHeight", 620);

		Map<String,List<String>> params = new HashMap<String,List<String>>();
		List<String> list = new ArrayList<String>();
		list.add(modeEcran.modeString(EnumModeObjet.C_MO_EDITION));
		params.put("modeEcranDefaut", list);
		List<String> list2 = new ArrayList<String>();
		list2.add(new Integer(selection.getId()).toString());
		params.put("idEntity", list2);

		RequestContext.getCurrentInstance().openDialog("admin/dialog_roles", options, params);

	}

	public void handleReturnDialogTypes(SelectEvent event) {
		if(event!=null && event.getObject()!=null) {
			TaRole nouveau =  (TaRole) event.getObject();
//			nouveau =  (TaRoleDTO) event.getObject();
			
		}
		refresh();
	}
	
	public void actFermerDialog(ActionEvent actionEvent) {
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	public void actEnregistrer(ActionEvent actionEvent){

		try {

			autoCompleteMapUIToDTO();
			if(taRoleService.findByCode(nouveau.getLiblRole()) == null){
				mapperUIToModel.map(nouveau, taRole);
				taRole = taRoleService.merge(taRole, ITaRoleServiceRemote.validationContext);
				mapperModelToUI.map(taRole, nouveau);
				
				values = taRoleService.selectAllDTO();
				nouveau = values.get(0);
				nouveau = new TaRoleDTO();

				modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
			}else{
				if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_EDITION)==0){
					
					taRole = taRoleService.findById(nouveau.getId());
					mapperUIToModel.map(nouveau, taRole);
					taRole = taRoleService.merge(taRole, ITaUtilisateurServiceRemote.validationContext);
					mapperModelToUI.map(taRole, nouveau);
					
					values= taRoleService.selectAllDTO();
					nouveau = values.get(0);
					
					nouveau = new TaRoleDTO();

					modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
				} else{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Code déja utilisé"));
				}
			}
			if(modeEcranDefaut!=null && modeEcranDefaut.equals(C_DIALOG)) {
				RequestContext.getCurrentInstance().closeDialog(taRole);
			}
		} catch(Exception e) {
			e=ThrowableExceptionLgr.renvoieCauseRuntimeException(e);
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Role", e.getMessage()));
		}
	}

	public void actInserer(ActionEvent actionEvent){
		nouveau = new TaRoleDTO();
	
		modeEcran.setMode(EnumModeObjet.C_MO_INSERTION);
	}

	public void actModifier(ActionEvent actionEvent){
		nouveau = selection;
		
		modeEcran.setMode(EnumModeObjet.C_MO_EDITION);
	}

	public void actSupprimer(ActionEvent actionEvent){
		TaRole taRole = new TaRole();
		try {
			if(selection!=null && selection.getId()!=null){
				taRole = taRoleService.findById(selection.getId());
			}

			taRoleService.remove(taRole);
			//values.remove(selection);
			values = taRoleService.selectAllDTO();
			
			if(!values.isEmpty()) {
				selection = values.get(0);
			}else {
				selection=new TaRoleDTO();
			}

			modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);

			if(ConstWeb.DEBUG) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage("Role", "actSupprimer"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Role", e.getCause().getCause().getCause().getCause().getMessage()));
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
			Set<ConstraintViolation<TaUtilisateurDTO>> violations = factory.getValidator().validateValue(TaUtilisateurDTO.class,nomChamp,value);
			if (violations.size() > 0) {
				messageComplet = "Erreur de validation : ";
				for (ConstraintViolation<TaUtilisateurDTO> cv : violations) {
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
//			if(nomChamp.equals(Const.C_CODE_TVA)) {
//			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<TaRoleDTO> getValues(){  
		return values;
	}

	public TaRoleDTO getNouveau() {
		return nouveau;
	}

	public void setNouveau(TaRoleDTO newTaRoleDTO) {
		this.nouveau = newTaRoleDTO;
	}

	public TaRoleDTO getSelection() {
		return selection;
	}

	public void setSelection(TaRoleDTO selectedTaRoleDTO) {
		this.selection = selectedTaRoleDTO;
	}

	public void setValues(List<TaRoleDTO> values) {
		this.values = values;
	}

	public List<TaRoleDTO> getFilteredValues() {
		return filteredValues;
	}

	public void setFilteredValues(List<TaRoleDTO> filteredValues) {
		this.filteredValues = filteredValues;
	}

	public Boolean getTableVide() {
		return tableVide;
	}

	public void setTableVide(Boolean tableVide) {
		this.tableVide = tableVide;
	}

	public ModeObjetEcranServeur getModeEcran() {
		return modeEcran;
	}

	public TaRole getTaRole() {
		return taRole;
	}

	public void setTaRole(TaRole taRole) {
		this.taRole = taRole;
	}

}  
