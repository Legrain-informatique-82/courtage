package fr.ylyade.courtage.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import fr.legrain.lib.data.LibConversion;
import fr.ylyade.courtage.droits.model.TaUtilisateur;
import fr.ylyade.courtage.droits.service.interfaces.remote.ITaUtilisateurServiceRemote;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.model.TaTAssure;
import fr.ylyade.courtage.service.interfaces.remote.ITaTAssureServiceRemote;

@Named
@ViewScoped 
public class MenuBean implements Serializable {

	private static final long serialVersionUID = -1236172556833874563L;

	@Inject @Named(value="tabListModelCenterBean")
	private TabListModelBean tabsCenter;

	@Inject @Named(value="tabListModelLeftBean")
	private TabListModelBean tabsLeft;

	@Inject @Named(value="tabListModelRightBean")
	private TabListModelBean tabsRight;

	@Inject @Named(value="tabListModelBottomBean")
	private TabListModelBean tabsBottom;
	
	@Inject @Named(value="ouvertureDocumentBean")
	private OuvertureDocumentBean ouvertureDocumentBean;
	
	@Inject SessionInfo sessionInfos;

	private @EJB ITaTAssureServiceRemote taTAssureService;

	@Inject @Named(value="tabViewsBean")
	private TabViewsBean tabViews;
	
	private TaUtilisateur u = null;

//	@EJB private ITaAutorisationsDossierServiceRemote taAutorisationDossierService;
	
	@EJB private ITaUtilisateurServiceRemote userService;
	
	@PostConstruct
	public void init() {
		SessionListener.updateSessionKeys();
		u = Auth.findUserInSession();
	}

	public void openUtilisateur(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet("fr.legrain.onglet.listeUtilisateur");
		b.setTitre("Utilisateurs");
		b.setTemplate("admin/utilisateurs.xhtml");
		b.setIdTab(LgrTab.ID_TAB_ADMIN_UTILISATEURS);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openRole(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet("fr.legrain.onglet.listeRole");
		b.setTitre("Roles");
		b.setTemplate("admin/roles.xhtml");
		b.setIdTab(LgrTab.ID_TAB_ADMIN_ROLES);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openBugs(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet("fr.legrain.onglet.bugs");
		b.setTitre("Bugs");
		b.setTemplate("admin/bugs.xhtml");
		b.setIdTab(LgrTab.ID_TAB_ADMIN_BUGS);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openMonCompteCourtier(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_COURTIER);
		b.setTitre("Courtier");
		b.setTemplate("courtier/courtier.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_COURTIER);
		b.setStyle(LgrTab.CSS_CLASS_TAB_COURTIER);
		b.setParamId(LibConversion.integerToString(sessionInfos.getUtilisateur().getIdUtilisateur()));
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	
	public void openDashBoardAccueilCourtier(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_DASHBOARD_COURTIER);
		b.setTitre("Tableau de bord Courtier");
		b.setTemplate("courtier/dash_courtier_accueil.xhtml");
		b.setIdTab(LgrTab.ID_TAB_DASHBOARD_COURTIER);
		b.setStyle(LgrTab.CSS_CLASS_TAB_COURTIER);
		b.setParamId(LibConversion.integerToString(sessionInfos.getUtilisateur().getIdUtilisateur()));
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	//SuperAssureur
	
//	public void openMonCompteAssureur(ActionEvent event) {  
//		LgrTab b = new LgrTab();
//		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_ASSUREUR);
//		b.setTitre("Assureur");
//		b.setTemplate("assureur/assureur.xhtml");
//		b.setIdTab(LgrTab.ID_TAB_TYPE_ASSUREUR);
//		b.setStyle(LgrTab.CSS_CLASS_TAB_ASSUREUR);
//		b.setParamId(LibConversion.integerToString(sessionInfos.getUtilisateur().getIdUtilisateur()));
//		tabsCenter.ajouterOnglet(b);
//		tabViews.selectionneOngletCentre(b);
//	}
	
	//SuperAssureur
	
	public void openDashBoardAccueilAssureur(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_DASHBOARD_ASSUREUR);
		b.setTitre("Tableau de bord Assureur");
		b.setTemplate("assureur/dash_super_assureur_accueil.xhtml");
		b.setIdTab(LgrTab.ID_TAB_DASHBOARD_ASSUREUR);
//		b.setStyle(LgrTab.CSS_CLASS_TAB_ASSUREUR);
		b.setStyle(LgrTab.CSS_CLASS_TAB_COURTIER);
		b.setParamId(LibConversion.integerToString(sessionInfos.getUtilisateur().getIdUtilisateur()));
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	//Ylyade
	
	public void openDashBoardAccueilYlyade(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_DASHBOARD_YLYADE);
		b.setTitre("Tableau de bord Novarisks");
		b.setTemplate("admin/dash_ylyade_accueil.xhtml");
		b.setIdTab(LgrTab.ID_TAB_DASHBOARD_YLYADE);
//		b.setStyle(LgrTab.CSS_CLASS_TAB_YLYADE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_COURTIER);
		b.setParamId(LibConversion.integerToString(sessionInfos.getUtilisateur().getIdUtilisateur()));
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openDevisRcPro(ActionEvent event) {  
//		LgrTab b = new LgrTab();
//		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_COURTIER);
//		b.setTitre("Courtier");
//		b.setTemplate("courtier/courtier.xhtml");
//		b.setIdTab(LgrTab.ID_TAB_TYPE_COURTIER);
//		b.setStyle(LgrTab.CSS_CLASS_TAB_COURTIER);
//		b.setParamId(LibConversion.integerToString(sessionInfos.getUtilisateur().getIdUtilisateur()));
//		tabsCenter.ajouterOnglet(b);
//		tabViews.selectionneOngletCentre(b);
	}
	
	public void openMonCompteAssure(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_ASSURE);
		b.setTitre("Assure");
		b.setTemplate("assure/assure.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_ASSURE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_ASSURE);
		b.setParamId(LibConversion.integerToString(sessionInfos.getUtilisateur().getIdUtilisateur()));
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}

	public void openDashBoardAccueilAssure(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_ASSURE);
		b.setTitre("Tableau de bord Assuré");
		b.setTemplate("assure/dash_assure_accueil.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_ASSURE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_ASSURE);
		b.setParamId(LibConversion.integerToString(sessionInfos.getUtilisateur().getIdUtilisateur()));
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	

	public void openListeDoc(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_DOC);
		b.setTitre("Liste Documents");
		b.setTemplate("documents/liste_documents.xhtml");
		b.setIdTab(LgrTab.ID_TAB_DOC);
		b.setStyle(LgrTab.CSS_CLASS_TAB_DOC);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
		}

	public void openDossierRCD(ActionEvent event) {  
//		LgrTab b = new LgrTab();
//		b.setTypeOnglet(LgrTab.TYPE_TAB_DEVIS_RCPRO);
//		b.setTitre("Devis RCD");
//		b.setTemplate("assurance/devis_rcd.xhtml");
//		b.setIdTab(LgrTab.ID_TAB_DEVIS_RCPRO);
//		b.setStyle(LgrTab.CSS_CLASS_TAB_DEVIS_RCPRO);
//		//b.setParamId(LibConversion.integerToString(sessionInfos.getUtilisateur().getIdUtilisateur()));
//		tabsCenter.ajouterOnglet(b);
//		tabViews.selectionneOngletCentre(b);
		
		ouvertureDocumentBean.setEvent(new LgrTabEvent());
		ouvertureDocumentBean.getEvent().setCodeObjet(null);
		ouvertureDocumentBean.getEvent().setData(null);
		ouvertureDocumentBean.getEvent().setCssLgrTab(LgrTab.CSS_CLASS_TAB_DEVIS_RCPRO);
		ouvertureDocumentBean.getEvent().setAfficheDoc(true);/* A remettre à true dès que l'on pourra afficher le document à la suite d'un dialogue*/
		ouvertureDocumentBean.creerDocument(null);

	}
	
	public void openDossierRCDEntrepriseBatiment(ActionEvent event) { 
		try {
			TaTAssure t = taTAssureService.findByCode(TaTAssure.ENTREPRISE_DU_BATIMENT);
			ouvertureDocumentBean.setEvent(new LgrTabEvent());
			ouvertureDocumentBean.getEvent().setCodeObjet(null);
			ouvertureDocumentBean.getEvent().setData(t);
			ouvertureDocumentBean.getEvent().setCssLgrTab(LgrTab.CSS_CLASS_TAB_DEVIS_RCPRO);
			ouvertureDocumentBean.getEvent().setAfficheDoc(true);/* A remettre à true dès que l'on pourra afficher le document à la suite d'un dialogue*/
			ouvertureDocumentBean.creerDocument(null);
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public void openDossierRCDPib(ActionEvent event) { 
		try {
			TaTAssure t = taTAssureService.findByCode(TaTAssure.PIB);
			ouvertureDocumentBean.setEvent(new LgrTabEvent());
			ouvertureDocumentBean.getEvent().setCodeObjet(null);
			ouvertureDocumentBean.getEvent().setData(t);
			ouvertureDocumentBean.getEvent().setCssLgrTab(LgrTab.CSS_CLASS_TAB_DEVIS_RCPRO);
			ouvertureDocumentBean.getEvent().setAfficheDoc(true);/* A remettre à true dès que l'on pourra afficher le document à la suite d'un dialogue*/
			ouvertureDocumentBean.creerDocument(null);
//			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("PIB","Prochainement disponible"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void openDossierRCDPiscine(ActionEvent event) { 
		try {
//			TaTAssure t = taTAssureService.findByCode(TaTAssure.PISCINE);
//			ouvertureDocumentBean.setEvent(new LgrTabEvent());
//			ouvertureDocumentBean.getEvent().setCodeObjet(null);
//			ouvertureDocumentBean.getEvent().setData(t);
//			ouvertureDocumentBean.getEvent().setCssLgrTab(LgrTab.CSS_CLASS_TAB_DEVIS_RCPRO);
//			ouvertureDocumentBean.getEvent().setAfficheDoc(true);/* A remettre à true dès que l'on pourra afficher le document à la suite d'un dialogue*/
//			ouvertureDocumentBean.creerDocument(null);
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("Piscine","Prochainement disponible"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void openMessageProchainementDispo(ActionEvent event) { 
		try {
//			TaTAssure t = taTAssureService.findByCode(TaTAssure.PISCINE);
//			ouvertureDocumentBean.setEvent(new LgrTabEvent());
//			ouvertureDocumentBean.getEvent().setCodeObjet(null);
//			ouvertureDocumentBean.getEvent().setData(t);
//			ouvertureDocumentBean.getEvent().setCssLgrTab(LgrTab.CSS_CLASS_TAB_DEVIS_RCPRO);
//			ouvertureDocumentBean.getEvent().setAfficheDoc(true);/* A remettre à true dès que l'on pourra afficher le document à la suite d'un dialogue*/
//			ouvertureDocumentBean.creerDocument(null);
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("Novarisks","Prochainement disponible. Nous contacter : 04 88 60 54 65 contact@novarisks-underwriting.fr"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void openTypeAdresse(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_ADRESSE);
		b.setTitre("Type Adresse");
		b.setTemplate("parametres/type_adresse.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_ADRESSE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeAssurance(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_ASSURANCE);
		b.setTitre("Type Assurance");
		b.setTemplate("parametres/type_assurance.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_ASSURANCE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}

	public void openTypeAssure(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_ASSURE);
		b.setTitre("Type Assure");
		b.setTemplate("parametres/type_assure.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_ASSURE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	public void openTypeCivilite(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_CIVILITE);
		b.setTitre("Type Civilité");
		b.setTemplate("parametres/type_civilite.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_CIVILITE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	public void openTypeCourtier(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_COURTIER);
		b.setTitre("Type Courtier");
		b.setTemplate("parametres/type_courtier.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_COURTIER);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	public void openTypeTel(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_TEL);
		b.setTitre("Type Téléphone");
		b.setTemplate("parametres/type_tel.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_TEL);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeFranchise(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_FRANCHISE);
		b.setTitre("Type Franchise");
		b.setTemplate("parametres/type_franchise.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_FRANCHISE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeGroupeTarif(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_GROUPE_TARIF);
		b.setTitre("Type GroupeTarif");
		b.setTemplate("parametres/type_groupetarif.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_GROUPE_TARIF);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeStatut(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_STATUT);
		b.setTitre("Type Statut");
		b.setTemplate("parametres/type_statut.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_STATUT);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeSousTraitance(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_SOUS_TRAITANCE);
		b.setTitre("Type Sous Traitance");
		b.setTemplate("parametres/type_sous_traitance.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_SOUS_TRAITANCE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeTravaux(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_TRAVAUX);
		b.setTitre("Type Travaux");
		b.setTemplate("parametres/type_travaux.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_TRAVAUX);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	public void openTypeRole(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_ROLE);
		b.setTitre("Type Role");
		b.setTemplate("parametres/type_role.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_ROLE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeListeRefDoc(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_LISTE_REF_DOC);
		b.setTitre("Type Liste Ref Doc");
		b.setTemplate("parametres/type_liste_ref_doc.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_LISTE_REF_DOC);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	public void openTypeJuridique(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_JURIDIQUE);
		b.setTitre("Type Juridique");
		b.setTemplate("parametres/type_juridique.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_JURIDIQUE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	public void openTypeEmail(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_EMAIL);
		b.setTitre("Type Email");
		b.setTemplate("parametres/type_email.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_EMAIL);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeEcheance(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_ECHEANCE);
		b.setTitre("Type Echéance");
		b.setTemplate("parametres/type_echeance.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_ECHEANCE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeActionDoc(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_ACTION_DOC);
		b.setTitre("Type ActionDoc");
		b.setTemplate("parametres/type_action_doc.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_ACTION_DOC);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeReglement(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_REGLEMENT);
		b.setTitre("Type Reglement");
		b.setTemplate("parametres/type_reglement.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_REGLEMENT);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeConstruction(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_CONSTRUCTION);
		b.setTitre("Type Construction");
		b.setTemplate("parametres/type_construction.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_CONSTRUCTION);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeDoc(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_DOC);
		b.setTitre("Type Doc");
		b.setTemplate("parametres/type_doc.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_DOC);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeDestinationUsage(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_DESTINATION_USAGE);
		b.setTitre("Type DestinationUsage");
		b.setTemplate("parametres/type_destination_usage.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_DESTINATION_USAGE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeLotRealise(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_LOT_REALISE);
		b.setTitre("Type LotRealise");
		b.setTemplate("parametres/type_lot_realise.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_LOT_REALISE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeFraisImpaye(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_FRAIS_IMPAYE);
		b.setTitre("Type FraisImpaye");
		b.setTemplate("parametres/type_frais_impaye.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_FRAIS_IMPAYE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeGedSinistre(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_GED_SINISTRE);
		b.setTitre("Type GedSinistre");
		b.setTemplate("parametres/type_ged_sinistre.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_GED_SINISTRE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeMaitriseOeuvre(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_MAITRISE_OEUVRE);
		b.setTitre("Type MaitiseOeuvre");
		b.setTemplate("parametres/type_maitrise_oeuvre.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_MAITRISE_OEUVRE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeTarifPrestation(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_TARIF_PRESTATION);
		b.setTitre("Type TarifPrestation");
		b.setTemplate("parametres/type_tarif_prestation.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_TARIF_PRESTATION);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeFamilleActivite(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_FAMILLE_ACTIVITE);
		b.setTitre("Type FamilleActivite");
		b.setTemplate("parametres/type_famille_activite.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_FAMILLE_ACTIVITE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeClasse(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_CLASSE);
		b.setTitre("Type Classe");
		b.setTemplate("parametres/type_classe.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_CLASSE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}

	public void openTypeActivite(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_ACTIVITE);
		b.setTitre("Type Activite");
		b.setTemplate("parametres/type_activite.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_ACTIVITE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openListeRefDoc(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_LISTE_REF_DOC);
		b.setTitre("Liste ref doc");
		b.setTemplate("parametres/liste_ref_doc.xhtml");
		b.setIdTab(LgrTab.ID_TAB_LISTE_REF_DOC);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypePalierClasse(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_PALIER_CLASSE);
		b.setTitre("Type PalierClasse");
		b.setTemplate("parametres/type_palier_classe.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_PALIER_CLASSE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void openTypeTauxAssurance(ActionEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_TYPE_TAUX_ASSURANCE);
		b.setTitre("Type TauxAssurance");
		b.setTemplate("parametres/type_taux_assurance.xhtml");
		b.setIdTab(LgrTab.ID_TAB_TYPE_TAUX_ASSURANCE);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PARAM);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
	}
	
	public void actDialogNousContacter(ActionEvent actionEvent) {
		
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
        //list.add(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION));
        params.put("modeEcranDefaut", list);
        
        RequestContext.getCurrentInstance().openDialog("/dialog_contact", options, params);
	}
	
	public TabViewsBean getTabViews() {
		return tabViews;
	}

	public void setTabViews(TabViewsBean tabViews) {
		this.tabViews = tabViews;
	}

	public TabListModelBean getTabsCenter() {
		return tabsCenter;
	}

	public void setTabsCenter(TabListModelBean tabsCenter) {
		this.tabsCenter = tabsCenter;
	}

	public TabListModelBean getTabsLeft() {
		return tabsLeft;
	}

	public void setTabsLeft(TabListModelBean tabsLeft) {
		this.tabsLeft = tabsLeft;
	}

	public TabListModelBean getTabsRight() {
		return tabsRight;
	}

	public void setTabsRight(TabListModelBean tabsRight) {
		this.tabsRight = tabsRight;
	}

	public TabListModelBean getTabsBottom() {
		return tabsBottom;
	}

	public void setTabsBottom(TabListModelBean tabsBottom) {
		this.tabsBottom = tabsBottom;
	}
}
