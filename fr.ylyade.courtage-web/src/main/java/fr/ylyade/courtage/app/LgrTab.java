package fr.ylyade.courtage.app;

public class LgrTab {
	
	private String typeOnglet;
	private String titre;
	private String toolTipTitre;
	private String style = "tab tab-default";
	private String paramId;
	private String template;
	private Object data;
	private String idTab = null;
	
	public static final String ID_TAB_ADMIN_UTILISATEURS = "idTabAdminUtilisateurs";
	public static final String ID_TAB_ADMIN_UTILISATEURS_LOGIN = "idTabAdminUtilisateursLogin";
	public static final String ID_TAB_ADMIN_ROLES = "idTabAdminRoles";
	public static final String ID_TAB_ADMIN_BUGS = "idTabAdminBugs";
	
	public static final String ID_TAB_DASHBOARD_COURTIER = "idTabDashboardCourtier";
	public static final String ID_TAB_DASHBOARD_ASSUREUR="idTabDashboardAssureur";
	public static final String ID_TAB_DASHBOARD_YLYADE="idTabDashboardYlyade";
	
	public static final String ID_TAB_ACTION="idTabAction";
	
	public static final String ID_TAB_TYPE_ADRESSE = "idTabTypeAdresse";
	public static final String ID_TAB_TYPE_ASSURANCE = "idTabTypeAssurance";
	public static final String ID_TAB_TYPE_ASSURE = "idTabTypeAssure";
	public static final String ID_TAB_ASSURE = "idTabTypeAssure";
	public static final String ID_TAB_TYPE_CIVILITE = "idTabTypeCivilite";
	public static final String ID_TAB_TYPE_COURTIER = "idTabTypeCourtier";
	public static final String ID_TAB_COURTIER = "idTabCourtier";
	public static final String ID_TAB_DEVIS_RCPRO = "idTabDevisRcPro";
	public static final String ID_TAB_CONTRAT_RCPRO = "idTabContratRcPro";
	public static final String ID_TAB_TYPE_TEL = "idTabTypeTel";
	public static final String ID_TAB_TYPE_FRANCHISE = "idTabTypeFranchise";
	public static final String ID_TAB_TYPE_GROUPE_TARIF = "idTabTypeGroupeTarif";
	public static final String ID_TAB_TYPE_STATUT = "idTabTypeStatut";
	public static final String ID_TAB_TYPE_SOUS_TRAITANCE = "idTabTypeSousTraitance";
	public static final String ID_TAB_TYPE_TRAVAUX = "idTabTypeTravaux";
	public static final String ID_TAB_TYPE_ROLE = "idTabTypeRole";
	public static final String ID_TAB_TYPE_LISTE_REF_DOC = "idTabTypeListeRefDoc";
	public static final String ID_TAB_TYPE_JURIDIQUE = "idTabTypeJuridique";
	public static final String ID_TAB_TYPE_EMAIL = "idTabTypeEmail";
	public static final String ID_TAB_TYPE_ECHEANCE = "idTabTypeEcheance";
	public static final String ID_TAB_TYPE_ACTION_DOC = "idTabTypeActionDoc";
	public static final String ID_TAB_TYPE_REGLEMENT = "idTabTypeReglement";
	public static final String ID_TAB_TYPE_CONSTRUCTION = "idTabTypeConstruction";
	public static final String ID_TAB_TYPE_DOC = "idTabTypeDoc";
	public static final String ID_TAB_TYPE_DESTINATION_USAGE = "idTabTypeDestinationUsage";
	public static final String ID_TAB_TYPE_LOT_REALISE = "idTabTypeLotRealise";
	public static final String ID_TAB_TYPE_FRAIS_IMPAYE = "idTabTypeFraisImpaye";
	public static final String ID_TAB_TYPE_GED_SINISTRE = "idTabTypeGedSinistre";
	public static final String ID_TAB_TYPE_MAITRISE_OEUVRE = "idTabTypeMaitriseOeuvre";
	public static final String ID_TAB_TYPE_TARIF_PRESTATION = "idTabTypeTarifPrestation";
	public static final String ID_TAB_TYPE_FAMILLE_ACTIVITE = "idTabTypeFamilleActivite";
	public static final String ID_TAB_TYPE_CLASSE = "idTabTypeClasse";
	public static final String ID_TAB_TYPE_ACTIVITE = "idTabTypeActivite";
	public static final String ID_TAB_LISTE_REF_DOC = "idTabListeRefDoc";
	public static final String ID_TAB_TYPE_PALIER_CLASSE = "idTabPalierClasse";
	public static final String ID_TAB_TYPE_TAUX_ASSURANCE = "idTabTauxAssurance";
	public static final String ID_TAB_DOC = "idTabDoc";
	
	
		
	public static final String CSS_CLASS_TAB_PARAM = "tab tab-param";
	public static final String CSS_CLASS_TAB_TIERS = "tab tab-tiers";
	public static final String CSS_CLASS_TAB_COURTIER = "tab tab-coutier";
//	public static final String CSS_CLASS_TAB_ASSUREUR = "tab tab-assureur"
	public static final String CSS_CLASS_TAB_ASSURE = "tab tab-assure";
	public static final String CSS_CLASS_TAB_DEVIS_RCPRO = "tab tab-devis-rcpro";
	public static final String CSS_CLASS_TAB_CONTRAT_RCPRO = "tab tab-contrat_rcpro";
	public static final String CSS_CLASS_TAB_ARTICLE = "tab tab-article";
	public static final String CSS_CLASS_TAB_STOCK = "tab tab-stock";
	public static final String CSS_CLASS_TAB_RECEPTION = "tab tab-reception";
	public static final String CSS_CLASS_TAB_DEVIS = "tab tab-devis";
	public static final String CSS_CLASS_TAB_FACTURE = "tab tab-facture";
//	public static final String CSS_CLASS_TAB_DEVIS = "tab tab-param";
//	public static final String CSS_CLASS_TAB_FACTURE = "tab tab-param";
	public static final String CSS_CLASS_TAB_FABRICATION = "tab tab-fabrication";
	public static final String CSS_CLASS_TAB_INVENTAIRE = "tab tab-inventaire";
	public static final String CSS_CLASS_TAB_LOTS = "tab tab-lots";
	public static final String CSS_CLASS_TAB_CONTROLE_CONFORMITE = "tab tab-controle-conf";
	public static final String CSS_CLASS_TAB_TRACABILITE = "tab tab-param";
	public static final String Test = "test";
	public static final String CSS_CLASS_TAB_DOC = "tab tab-doc";
	
	public static final String TYPE_TAB_ARTICLE = "fr.legrain.onglet.article";
	public static final String TYPE_TAB_TIERS = "fr.legrain.onglet.tiers";
	
	public static final String TYPE_TAB_DASHBOARD_COURTIER = "fr.legrain.onglet.dashboardCourtier";
	public static final String TYPE_TAB_DASHBOARD_ASSUREUR = "fr.legrain.onglet.dashboardAssureur";
	public static final String TYPE_TAB_DASHBOARD_YLYADE = "fr.legrain.onglet.dashboardYlyade";
	
	public static final String TYPE_TAB_ACTION= "fr.legrain.onglet.action";
	
	public static final String TYPE_TAB_FABRICATION = "fr.legrain.onglet.creationFabrication";
	public static final String TYPE_TAB_MODELE_FABRICATION = "fr.legrain.onglet.creationModeleFabrication";
	public static final String TYPE_TAB_BON_RECEPTION = "fr.legrain.onglet.bonReception";
	public static final String TYPE_TAB_TYPE_WEB = "fr.legrain.onglet.listeTypeWeb";
	public static final String TYPE_TAB_MOUVEMENT_STOCK = "fr.legrain.onglet.mouvementStock";
	public static final String TYPE_TAB_MOUVEMENT_INVENTAIRE = "fr.legrain.onglet.inventaire";
	public static final String TYPE_TAB_DEVIS = "fr.legrain.onglet.devis";
	public static final String TYPE_TAB_FACTURE = "fr.legrain.onglet.facture";
	public static final String TYPE_TAB_TRACABILITE = "fr.legrain.onglet.tracabilite";
	public static final String TYPE_TAB_TYPE_ADRESSE = "fr.legrain.onglet.listeTypeAdresse";
	public static final String TYPE_TAB_TYPE_ASSURANCE = "fr.ylyade.onglet.listeTypeAssurance";
	public static final String TYPE_TAB_TYPE_ASSURE = "fr.ylyade.onglet.listeTypeAssure";
	public static final String TYPE_TAB_TYPE_CIVILITE = "fr.ylyade.onglet.listeTypeCivilite";
	public static final String TYPE_TAB_TYPE_COURTIER = "fr.ylyade.onglet.listeTypeCourtier";
	public static final String TYPE_TAB_COURTIER = "fr.ylyade.onglet.listeCourtier";
	public static final String TYPE_TAB_ASSURE = "fr.ylyade.onglet.listeAssure";
	
	public static final String TYPE_TAB_DEVIS_RCPRO = "fr.ylyade.onglet.devis.rcpro";
	public static final String TYPE_TAB_CONTRAT_RCPRO = "fr.ylyade.onglet.contrat.rcpro";
	public static final String TYPE_TAB_TYPE_TEL = "fr.ylyade.onglet.listeTypeTel";
	public static final String TYPE_TAB_TYPE_FRANCHISE = "fr.ylyade.onglet.listeTypeFranchise";
	public static final String TYPE_TAB_TYPE_GROUPE_TARIF = "fr.ylyade.onglet.listeTypeGroupeTarif";
	public static final String TYPE_TAB_TYPE_STATUT = "fr.ylyade.onglet.listeTypeStatut";
	public static final String TYPE_TAB_TYPE_SOUS_TRAITANCE = "fr.ylyade.onglet.listeTypeSousTraitance";
	public static final String TYPE_TAB_TYPE_TRAVAUX = "fr.ylyade.onglet.listeTypeTravaux";
	public static final String TYPE_TAB_TYPE_ROLE = "fr.ylyade.onglet.listeTypeRole";
	public static final String TYPE_TAB_TYPE_LISTE_REF_DOC = "fr.ylyade.onglet.listeTypeListeRefDoc";
	public static final String TYPE_TAB_TYPE_JURIDIQUE = "fr.ylyade.onglet.listeTypeJuridique";
	public static final String TYPE_TAB_TYPE_EMAIL = "fr.ylyade.onglet.listeTypeEmail";
	public static final String TYPE_TAB_TYPE_ECHEANCE = "fr.ylyade.onglet.listeTypeEcheance";
	public static final String TYPE_TAB_TYPE_ACTION_DOC = "fr.ylyade.onglet.listeTypeActionDoc";
	public static final String TYPE_TAB_TYPE_REGLEMENT = "fr.ylyade.onglet.listeTypeReglement";
	public static final String TYPE_TAB_TYPE_CONSTRUCTION = "fr.ylyade.onglet.listeTypeConstruction";
	public static final String TYPE_TAB_TYPE_DOC = "fr.ylyade.onglet.listeTypeDoc";
	public static final String TYPE_TAB_TYPE_DESTINATION_USAGE = "fr.ylyade.onglet.listeTypeDestinationUsage";
	public static final String TYPE_TAB_TYPE_LOT_REALISE = "fr.ylyade.onglet.listeTypeLotRealise";
	public static final String TYPE_TAB_TYPE_FRAIS_IMPAYE = "fr.ylyade.onglet.listeTypeFraisImpaye";
	public static final String TYPE_TAB_TYPE_GED_SINISTRE = "fr.ylyade.onglet.listeTypeGedSinistre";
	public static final String TYPE_TAB_TYPE_MAITRISE_OEUVRE = "fr.ylyade.onglet.listeTypeMaitriseOeuvre";
	public static final String TYPE_TAB_TYPE_TARIF_PRESTATION = "fr.ylyade.onglet.listeTypeTarifPrestation";
	public static final String TYPE_TAB_TYPE_FAMILLE_ACTIVITE = "fr.ylyade.onglet.listeTypeFamilleActivite";
	public static final String TYPE_TAB_TYPE_CLASSE = "fr.ylyade.onglet.listeTypeClasse";
	public static final String TYPE_TAB_TYPE_ACTIVITE = "fr.ylyade.onglet.listeTypeActivite";
	public static final String TYPE_TAB_LISTE_REF_DOC = "fr.ylyade.onglet.listeListeRefDoc";
	public static final String TYPE_TAB_TYPE_PALIER_CLASSE = "fr.ylyade.onglet.listeTypePalierClasse";
	public static final String TYPE_TAB_TYPE_TAUX_ASSURANCE = "fr.ylyade.onglet.listeTypeTauxAssurance";
	public static final String TYPE_TAB_DOC = "fr.ylyade.onglet.listeDoc";
	
	public LgrTab() {
		
	}
	
	public String getTypeOnglet() {
		return typeOnglet;
	}
	
	public void setTypeOnglet(String typeOnglet) {
		this.typeOnglet = typeOnglet;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getToolTipTitre() {
		return toolTipTitre;
	}

	public void setToolTipTitre(String toolTipTitre) {
		this.toolTipTitre = toolTipTitre;
	}

	public String getIdTab() {
		return idTab;
	}

	public void setIdTab(String idTab) {
		this.idTab = idTab;
	}
	
}
