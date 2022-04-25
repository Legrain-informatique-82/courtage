package fr.ylyade.courtage.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import fr.ylyade.courtage.app.ConstWeb;
import fr.ylyade.courtage.dto.TaDossierRcdDTO;

@Named
@ViewScoped 
public class DashAssureurController extends DevisRcdController{

	private static final long serialVersionUID = 6278107399024181110L;
	
	private List<TaDossierRcdDTO> listDossierAValider;
	private Integer nbDossierAValider;
	//soumis a etude
	private List<TaDossierRcdDTO> listSoumisEtudeAValider;
	private Integer nbSoumisEtudeAValider;
	private List<TaDossierRcdDTO> listSoumisEtudeValide;
	private Integer nbSoumisEtudeValide;
	private List<TaDossierRcdDTO> listSoumisEtudeRefuse;
	private Integer nbSoumisEtudeRefuse;
	private List<TaDossierRcdDTO> listSoumisEtudeRefuseDefinitif;
	private Integer nbSoumisEtudeRefuseDefinitif;
	//avenants
	private List<TaDossierRcdDTO> listAvenantAValider;
	private Integer nbAvenantAValider;
	
	@Override
	public void refresh() {
		

		List<TaDossierRcdDTO> listeDossierAValider = taDossierRcdService.findAllValidationYlyade();
		//soumis a etude
		List<TaDossierRcdDTO> listeSoumisEtudeAValider = taDossierRcdService.findAllSoumisEtudeAttenteValidationAssureur();
		List<TaDossierRcdDTO> listeSoumisEtudeValide = taDossierRcdService.findAllSoumisEtudeValide();
		List<TaDossierRcdDTO> listeSoumisEtudeRefuse = taDossierRcdService.findAllSoumisEtudeRefuse();
		List<TaDossierRcdDTO> listeSoumisEtudeRefuseDefinitif = taDossierRcdService.findAllSoumisEtudeRefuseDefinitif();
		//avenants
		List<TaDossierRcdDTO> listeAvenantAValider = taDossierRcdService.findAllAvenantValidationYlyade();
		
		if(listeDossierAValider != null) {
			listDossierAValider = listeDossierAValider;
			
		}else {
			listDossierAValider = new ArrayList<TaDossierRcdDTO>();
		}
		//soumis a etude
		if(listeSoumisEtudeAValider != null) {
			listSoumisEtudeAValider = listeSoumisEtudeAValider;
			
		}else {
			listSoumisEtudeAValider = new ArrayList<TaDossierRcdDTO>();
		}
		
		if(listeSoumisEtudeValide != null) {
			listSoumisEtudeValide = listeSoumisEtudeValide;
			
		}else {
			listSoumisEtudeValide = new ArrayList<TaDossierRcdDTO>();
		}
		if(listeSoumisEtudeRefuse != null) {
			listSoumisEtudeRefuse = listeSoumisEtudeRefuse;
			
		}else {
			listSoumisEtudeRefuse = new ArrayList<TaDossierRcdDTO>();
		}
		
		if(listeAvenantAValider != null) {
			listAvenantAValider = listeAvenantAValider;
			
		}else {
			listAvenantAValider = new ArrayList<TaDossierRcdDTO>();
		}
		if(listeSoumisEtudeRefuseDefinitif != null) {
			listSoumisEtudeRefuseDefinitif = listeSoumisEtudeRefuseDefinitif;
			
		}else {
			listSoumisEtudeRefuseDefinitif = new ArrayList<TaDossierRcdDTO>();
		}
			
		
		
	
		nbDossierAValider = listDossierAValider.size();
		
		nbSoumisEtudeAValider = listSoumisEtudeAValider.size();
		nbSoumisEtudeValide = listSoumisEtudeValide.size();
		nbSoumisEtudeRefuse = listSoumisEtudeRefuse.size();
		nbSoumisEtudeRefuseDefinitif = listSoumisEtudeRefuseDefinitif.size();
		
		nbAvenantAValider = listAvenantAValider.size();
		
	}

/////////////////////////////////
	
	public void actImprimerListeDossierAValiderCompagnie(ActionEvent actionEvent) {
	if(ConstWeb.DEBUG) {
	FacesContext context = FacesContext.getCurrentInstance();  
	context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
	}
	
	try {
	
	ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	Map<String, Object> sessionMap = externalContext.getSessionMap();
	
	
	sessionMap.put("listeDossierAValiderCompagnie",listDossierAValider  );
	
	
	} catch (Exception e) {
	e.printStackTrace();
	}
	} 		
	
	public void actImprimerListeAvenantAValiderCompagnie(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeAvenantAValiderCompagnie",listAvenantAValider  );
		
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
	
	public void actImprimerListeDevisContratSoumisEtudeAttenteValidationAssureur(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeSoumisEtudeAValider",listSoumisEtudeAValider  );
		
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		} 		
		
	public void actImprimerListeDevisContratSoumisEtudeValideAssureur(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeSoumisEtudeValide",listSoumisEtudeValide  );
		
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
	
	public void actImprimerListeDevisContratSoumisEtudeRefuseAssureur(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeSoumisEtudeRefuse",listSoumisEtudeRefuse  );
		
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
	
	public void actImprimerListeDevisContratSoumisEtudeRefuseDefinitifAssureur(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage("Proposition Rc Decennale", "actImprimer"));
		}
		
		try {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		
		sessionMap.put("listeSoumisEtudeRefuseDefinitif",listSoumisEtudeRefuseDefinitif  );
		
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		} 	
/////////////////////////////////	
	
	
	public List<TaDossierRcdDTO> getListDossierAValider() {
		return listDossierAValider;
	}

	public void setListDossierAValider(List<TaDossierRcdDTO> listDossierAValider) {
		this.listDossierAValider = listDossierAValider;
	}

	public Integer getNbDossierAValider() {
		return nbDossierAValider;
	}

	public void setNbDossierAValider(Integer nbDossierAValider) {
		this.nbDossierAValider = nbDossierAValider;
	}

	public List<TaDossierRcdDTO> getListSoumisEtudeAValider() {
		return listSoumisEtudeAValider;
	}

	public void setListSoumisEtudeAValider(List<TaDossierRcdDTO> listSoumisEtudeAValider) {
		this.listSoumisEtudeAValider = listSoumisEtudeAValider;
	}


	public List<TaDossierRcdDTO> getListSoumisEtudeValide() {
		return listSoumisEtudeValide;
	}

	public void setListSoumisEtudeValide(List<TaDossierRcdDTO> listSoumisEtudeValide) {
		this.listSoumisEtudeValide = listSoumisEtudeValide;
	}

	public Integer getNbSoumisEtudeValide() {
		return nbSoumisEtudeValide;
	}

	public void setNbSoumisEtudeValide(Integer nbSoumisEtudeValide) {
		this.nbSoumisEtudeValide = nbSoumisEtudeValide;
	}

	public List<TaDossierRcdDTO> getListSoumisEtudeRefuse() {
		return listSoumisEtudeRefuse;
	}

	public void setListSoumisEtudeRefuse(List<TaDossierRcdDTO> listSoumisEtudeRefuse) {
		this.listSoumisEtudeRefuse = listSoumisEtudeRefuse;
	}

	public Integer getNbSoumisEtudeRefuse() {
		return nbSoumisEtudeRefuse;
	}

	public void setNbSoumisEtudeRefuse(Integer nbSoumisEtudeRefuse) {
		this.nbSoumisEtudeRefuse = nbSoumisEtudeRefuse;
	}

	public Integer getNbSoumisEtudeAValider() {
		return nbSoumisEtudeAValider;
	}

	public void setNbSoumisEtudeAValider(Integer nbSoumisEtudeAValider) {
		this.nbSoumisEtudeAValider = nbSoumisEtudeAValider;
	}

	public List<TaDossierRcdDTO> getListAvenantAValider() {
		return listAvenantAValider;
	}

	public void setListAvenantAValider(List<TaDossierRcdDTO> listAvenantAValider) {
		this.listAvenantAValider = listAvenantAValider;
	}

	public Integer getNbAvenantAValider() {
		return nbAvenantAValider;
	}

	public void setNbAvenantAValider(Integer nbAvenantAValider) {
		this.nbAvenantAValider = nbAvenantAValider;
	}

	public List<TaDossierRcdDTO> getListSoumisEtudeRefuseDefinitif() {
		return listSoumisEtudeRefuseDefinitif;
	}

	public void setListSoumisEtudeRefuseDefinitif(List<TaDossierRcdDTO> listSoumisEtudeRefuseDefinitif) {
		this.listSoumisEtudeRefuseDefinitif = listSoumisEtudeRefuseDefinitif;
	}

	public Integer getNbSoumisEtudeRefuseDefinitif() {
		return nbSoumisEtudeRefuseDefinitif;
	}

	public void setNbSoumisEtudeRefuseDefinitif(Integer nbSoumisEtudeRefuseDefinitif) {
		this.nbSoumisEtudeRefuseDefinitif = nbSoumisEtudeRefuseDefinitif;
	}
	
	
	
	
	



}
