package fr.ylyade.courtage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import fr.legrain.birt.BirtUtil;
import fr.legrain.data.YlyadeProperties;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaDossierRcdTauxPib;
import fr.ylyade.courtage.model.TaEcheance;
import fr.ylyade.courtage.model.TaTAssure;
import fr.ylyade.courtage.model.TaTFraisImpaye;
import fr.ylyade.courtage.model.TaTauxAssurance;
import fr.ylyade.courtage.model.TaTauxRcproPib;
import fr.ylyade.courtage.service.interfaces.remote.IEditionServiceRemonte;
import fr.ylyade.courtage.service.interfaces.remote.ITaActiviteServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaAttestationNominativeServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaDossierRcdServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaEcheanceServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaGedUtilisateurServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaSousDonneeServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTFraisImpayeServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTFranchiseServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTauxAssuranceServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTauxRcproPibServiceRemote;

@Stateless
public class editionService implements IEditionServiceRemonte{
	private YlyadeProperties ylyadeProperties;
	@EJB private ITaDossierRcdServiceRemote taDossierRcdService;
	@EJB private ITaEcheanceServiceRemote taEcheanceService;
	@EJB private ITaTauxAssuranceServiceRemote taTauxAssuranceService;
	@EJB private ITaSousDonneeServiceRemote taSousDonneeService;
	@EJB private ITaGedUtilisateurServiceRemote taGedUtilisateurService;
	@EJB private ITaAttestationNominativeServiceRemote taAttestationNominativeService;
	@EJB private ITaActiviteServiceRemote taActiviteService;
	@EJB private ITaTauxRcproPibServiceRemote taTauxRcproPibService;
	@EJB private ITaTFranchiseServiceRemote taTFranchiseService;
	@EJB private ITaTFraisImpayeServiceRemote taTFraisImpayeService;
	@Inject private	SessionInfo sessionInfo;
	
	private String editionDefautPropositionDevis = null;
	private String editionDefautPropositionDevisPIB = null;
	private String editionDefautCondititonParticuliere = null;
	private String editionDefautCondititonParticulierePIB = null;
	private String editionDefautAttestationAssurance = null;
	private String editionDefautAttestationAssurancePIB = null;
	private String editionDefautAvisEcheance = null;
	private String editionDefautAvisMiseEnDemeure = null;
	private String editionDefautAvisSuspensionGaranties = null;
	private String editionDefautAvisResiliationImpaye = null;
	private String editionDefautAvisResiliationEcheance = null;
	private String editionDefautAvisResiliationCessation = null;
	private String editionDefautAvisResiliationSansEffet = null;
	private String editionDefautAvisResiliationFausseDeclaration = null;
	private String editionDefautAvisResiliationAmiable = null;
	
	public editionService() {
		ylyadeProperties = new YlyadeProperties();
		setEditionDefautPropositionDevis("META-INF/resources/reports/PropositionTechniqueEtTarifaireAssurance/PropositionTechniqueEtTarifaireAssurance.rptdesign");
		setEditionDefautPropositionDevisPIB("META-INF/resources/reports/PropositionTechniqueEtTarifaireAssurance/PropositionTechniqueEtTarifaireAssurancePib.rptdesign");
		setEditionDefautCondititonParticuliere("META-INF/resources/reports/conditionsParticulieres/ConditionsParticulieres.rptdesign");
		setEditionDefautCondititonParticulierePIB("META-INF/resources/reports/conditionsParticulieres/ConditionsParticulieresPib.rptdesign");
		setEditionDefautAttestationAssurance("META-INF/resources/reports/attestation/AttestationAssuranceRcDecennale.rptdesign");  
		setEditionDefautAttestationAssurancePIB("META-INF/resources/reports/attestation/AttestationAssuranceRcDecennalePib.rptdesign");
		setEditionDefautAvisEcheance("META-INF/resources/reports/gestionComptable/AvisEcheance.rptdesign");
		setEditionDefautAvisMiseEnDemeure("META-INF/resources/reports/gestionComptable/AvisDeMiseEnDemeure.rptdesign");
		setEditionDefautAvisSuspensionGaranties("META-INF/resources/reports/gestionComptable/AvisDeSuspensionDeGaranties.rptdesign");
		setEditionDefautAvisResiliationImpaye("META-INF/resources/reports/gestionComptable/AvisDeResiliation.rptdesign");
		setEditionDefautAvisResiliationEcheance("META-INF/resources/reports/gestionComptable/AvisDeResiliationAEcheance.rptdesign");
		setEditionDefautAvisResiliationCessation("");
		setEditionDefautAvisResiliationSansEffet("");
		setEditionDefautAvisResiliationFausseDeclaration("");
		setEditionDefautAvisResiliationAmiable("");
	
		
	}
	
	
	public String generePropositionDevisPDF(int idDossierRcd, String modeleEdition) {
		try {
			List<String> listResourcesPath = new ArrayList<String>();
			listResourcesPath.add("META-INF/resources/reports/PropositionTechniqueEtTarifaireAssurance/");
			
			
			String localPath = ylyadeProperties.osTempDirDossier(null)+"/"+ylyadeProperties.tmpFileName("proposition.pdf");
			System.out.println(localPath);
			
			
			Map<String,Object> mapEdition = new HashMap<String,Object>();
	
				
			TaDossierRcd doc = taDossierRcdService.findById(idDossierRcd);
			
			if(modeleEdition == null) {
				if(assurePib(doc)) {
					modeleEdition = editionDefautPropositionDevisPIB;
				}else {
					modeleEdition = editionDefautPropositionDevis;
				}
			}

			mapEdition.put("fraisgestion", taTFraisImpayeService.findByCode(TaTFraisImpaye.FRAIS_DOSSIER_YLYADE));
			mapEdition.put("fraisgestionpib", taTFraisImpayeService.findByCode(TaTFraisImpaye.FRAIS_DOSSIER_PIB));
			mapEdition.put("activitercpro", taActiviteService.selectAll());
			mapEdition.put("devisrcpro", doc);
			mapEdition.put("tarifpj", taTauxAssuranceService.findByCode(TaTauxAssurance.MONTANT_PROTECTION_JURIDIQUE));
			
			List<TaTauxRcproPib> listePib = new ArrayList<TaTauxRcproPib>();
			for (TaDossierRcdTauxPib pibEntite : doc.getTaDevisRcProTauxPibs()) {
				TaTauxRcproPib pib = taTauxRcproPibService.findByCode(pibEntite.getCodeTauxRcProPib());
				listePib.add(pib);
			}
			System.out.println(listePib);
			mapEdition.put("listePib", listePib);
			 
			return generePDF(modeleEdition, localPath,listResourcesPath, mapEdition);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String genereAvisMiseEnDemeurePDF(int idDossier, int idEcheance, String modeleEdition) {
		try {
			List<String> listResourcesPath = new ArrayList<String>();
			listResourcesPath.add("META-INF/resources/reports/gestionComptable/");
			
			
			String localPath = ylyadeProperties.osTempDirDossier(null)+"/"+ylyadeProperties.tmpFileName("avis_mise_demeure.pdf");
			System.out.println(localPath);
			
			
			Map<String,Object> mapEdition = new HashMap<String,Object>();
	
				
			TaDossierRcd doc = taDossierRcdService.findById(idDossier);
			TaEcheance ech = taEcheanceService.findById(idEcheance);
			
			if(modeleEdition == null) {
					modeleEdition = editionDefautAvisMiseEnDemeure;
			}

			mapEdition.put("devisrcpro", doc);	
			mapEdition.put("echeance", ech);	
			return generePDF(modeleEdition, localPath,listResourcesPath, mapEdition);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String genereAvisSuspensionGarantiePDF(int idDossier, int idEcheance, String modeleEdition) {
		try {
			List<String> listResourcesPath = new ArrayList<String>();
			listResourcesPath.add("META-INF/resources/reports/gestionComptable/");
			
			
			String localPath = ylyadeProperties.osTempDirDossier(null)+"/"+ylyadeProperties.tmpFileName("avis_suspension_garanties.pdf");
			System.out.println(localPath);
			
			
			Map<String,Object> mapEdition = new HashMap<String,Object>();
	
				
			TaDossierRcd doc = taDossierRcdService.findById(idDossier);
			TaEcheance ech = taEcheanceService.findById(idEcheance);
			
			if(modeleEdition == null) {
					modeleEdition = editionDefautAvisSuspensionGaranties;
			}

			mapEdition.put("devisrcpro", doc);	
			mapEdition.put("echeance", ech);	
			return generePDF(modeleEdition, localPath,listResourcesPath, mapEdition);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String genereAvisResiliationImpayePDF(int idDossier, int idEcheance, String modeleEdition) {
		try {
			List<String> listResourcesPath = new ArrayList<String>();
			listResourcesPath.add("META-INF/resources/reports/gestionComptable/");
			
			
			String localPath = ylyadeProperties.osTempDirDossier(null)+"/"+ylyadeProperties.tmpFileName("avis_resiliation_impaye.pdf");
			System.out.println(localPath);
			
			
			Map<String,Object> mapEdition = new HashMap<String,Object>();
	
				
			TaDossierRcd doc = taDossierRcdService.findById(idDossier);
			TaEcheance ech = taEcheanceService.findById(idEcheance);
			
			if(modeleEdition == null) {
					modeleEdition = editionDefautAvisResiliationImpaye;
			}

			mapEdition.put("devisrcpro", doc);	
			mapEdition.put("echeance", ech);	
			return generePDF(modeleEdition, localPath,listResourcesPath, mapEdition);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String genereAvisResiliationAmiablePDF(int idDossier, int idEcheance, String modeleEdition) {
		try {
			List<String> listResourcesPath = new ArrayList<String>();
			listResourcesPath.add("META-INF/resources/reports/gestionComptable/");
			
			
			String localPath = ylyadeProperties.osTempDirDossier(null)+"/"+ylyadeProperties.tmpFileName("avis_resiliation_amiable.pdf");
			System.out.println(localPath);
			
			
			Map<String,Object> mapEdition = new HashMap<String,Object>();
	
				
			TaDossierRcd doc = taDossierRcdService.findById(idDossier);
			TaEcheance ech = null;
			//on prend la dernière échéance du dossier si pas d'échéance passé en param
			//cependant, voir la pertinence de l'objet echeance dans cette édition, elle ne semble pas nécéssaire
			if(idEcheance == 0) {
				ech = doc.getTaEcheances().get(doc.getTaEcheances().size()-1);
			}else {
			    ech = taEcheanceService.findById(idEcheance);
			}
			
			
			if(modeleEdition == null) {
					modeleEdition = editionDefautAvisResiliationAmiable;
			}

			mapEdition.put("devisrcpro", doc);	
			mapEdition.put("echeance", ech);	
			return generePDF(modeleEdition, localPath,listResourcesPath, mapEdition);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String genereAvisResiliationCessationPDF(int idDossier, int idEcheance, String modeleEdition) {
		try {
			List<String> listResourcesPath = new ArrayList<String>();
			listResourcesPath.add("META-INF/resources/reports/gestionComptable/");
			
			
			String localPath = ylyadeProperties.osTempDirDossier(null)+"/"+ylyadeProperties.tmpFileName("avis_resiliation_cessation.pdf");
			System.out.println(localPath);
			
			
			Map<String,Object> mapEdition = new HashMap<String,Object>();
	
				
			TaDossierRcd doc = taDossierRcdService.findById(idDossier);
			TaEcheance ech = null;
			//on prend la dernière échéance du dossier si pas d'échéance passé en param
			//cependant, voir la pertinence de l'objet echeance dans cette édition, elle ne semble pas nécéssaire
			if(idEcheance == 0) {
				ech = doc.getTaEcheances().get(doc.getTaEcheances().size()-1);
			}else {
			    ech = taEcheanceService.findById(idEcheance);
			}
			
			
			if(modeleEdition == null) {
					modeleEdition = editionDefautAvisResiliationCessation;
			}

			mapEdition.put("devisrcpro", doc);	
			mapEdition.put("echeance", ech);	
			return generePDF(modeleEdition, localPath,listResourcesPath, mapEdition);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String genereAvisResiliationSansEffetPDF(int idDossier, int idEcheance, String modeleEdition) {
		try {
			List<String> listResourcesPath = new ArrayList<String>();
			listResourcesPath.add("META-INF/resources/reports/gestionComptable/");
			
			
			String localPath = ylyadeProperties.osTempDirDossier(null)+"/"+ylyadeProperties.tmpFileName("avis_resiliation_sans_effet.pdf");
			System.out.println(localPath);
			
			
			Map<String,Object> mapEdition = new HashMap<String,Object>();
	
				
			TaDossierRcd doc = taDossierRcdService.findById(idDossier);
			TaEcheance ech = null;
			//on prend la dernière échéance du dossier si pas d'échéance passé en param
			//cependant, voir la pertinence de l'objet echeance dans cette édition, elle ne semble pas nécéssaire
			if(idEcheance == 0) {
				ech = doc.getTaEcheances().get(doc.getTaEcheances().size()-1);
			}else {
			    ech = taEcheanceService.findById(idEcheance);
			}
			
			
			if(modeleEdition == null) {
					modeleEdition = editionDefautAvisResiliationSansEffet;
			}

			mapEdition.put("devisrcpro", doc);	
			mapEdition.put("echeance", ech);	
			return generePDF(modeleEdition, localPath,listResourcesPath, mapEdition);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String genereAvisResiliationFausseDeclaPDF(int idDossier, int idEcheance, String modeleEdition) {
		try {
			List<String> listResourcesPath = new ArrayList<String>();
			listResourcesPath.add("META-INF/resources/reports/gestionComptable/");
			
			
			String localPath = ylyadeProperties.osTempDirDossier(null)+"/"+ylyadeProperties.tmpFileName("avis_resiliation_fausse_declaration.pdf");
			System.out.println(localPath);
			
			
			Map<String,Object> mapEdition = new HashMap<String,Object>();
	
				
			TaDossierRcd doc = taDossierRcdService.findById(idDossier);
			TaEcheance ech = null;
			//on prend la dernière échéance du dossier si pas d'échéance passé en param
			//cependant, voir la pertinence de l'objet echeance dans cette édition, elle ne semble pas nécéssaire
			if(idEcheance == 0) {
				ech = doc.getTaEcheances().get(doc.getTaEcheances().size()-1);
			}else {
			    ech = taEcheanceService.findById(idEcheance);
			}
			
			
			if(modeleEdition == null) {
					modeleEdition = editionDefautAvisResiliationFausseDeclaration;
			}

			mapEdition.put("devisrcpro", doc);	
			mapEdition.put("echeance", ech);	
			return generePDF(modeleEdition, localPath,listResourcesPath, mapEdition);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String genereAvisResiliationEcheancePDF(int idDossier, int idEcheance, String modeleEdition) {
		try {
			List<String> listResourcesPath = new ArrayList<String>();
			listResourcesPath.add("META-INF/resources/reports/gestionComptable/");
			
			
			String localPath = ylyadeProperties.osTempDirDossier(null)+"/"+ylyadeProperties.tmpFileName("avis_resiliation_echeance.pdf");
			System.out.println(localPath);
			
			
			Map<String,Object> mapEdition = new HashMap<String,Object>();
	
				
			TaDossierRcd doc = taDossierRcdService.findById(idDossier);
			TaEcheance ech = null;
			//on prend la dernière échéance du dossier si pas d'échéance passé en param
			//cependant, voir la pertinence de l'objet echeance dans cette édition, elle ne semble pas nécéssaire
			if(idEcheance == 0) {
				ech = doc.getTaEcheances().get(doc.getTaEcheances().size()-1);
			}else {
			    ech = taEcheanceService.findById(idEcheance);
			}
			
			
			if(modeleEdition == null) {
					modeleEdition = editionDefautAvisResiliationEcheance;
			}

			mapEdition.put("devisrcpro", doc);	
			mapEdition.put("echeance", ech);	
			return generePDF(modeleEdition, localPath,listResourcesPath, mapEdition);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String genereAvisEcheancePDF(int idEcheance, String modeleEdition) {
		try {
			List<String> listResourcesPath = new ArrayList<String>();
			listResourcesPath.add("META-INF/resources/reports/gestionComptable/");
			
			
			String localPath = ylyadeProperties.osTempDirDossier(null)+"/"+ylyadeProperties.tmpFileName("avis_echeance.pdf");
			System.out.println(localPath);
			
			
			Map<String,Object> mapEdition = new HashMap<String,Object>();
	
				
			TaEcheance doc = taEcheanceService.findById(idEcheance);
			
			if(modeleEdition == null) {
					modeleEdition = editionDefautAvisEcheance;
			}

			mapEdition.put("echeance", doc);		 
			return generePDF(modeleEdition, localPath,listResourcesPath, mapEdition);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String genereConditionParticulierePDF(int idDossierRcd, String modeleEdition) {
		try {
			List<String> listResourcesPath = new ArrayList<String>();
			listResourcesPath.add("META-INF/resources/reports/conditionsParticulieres/");
			
			
			String localPath = ylyadeProperties.osTempDirDossier(null)+"/"+ylyadeProperties.tmpFileName("conditions_particulieres.pdf");
			System.out.println(localPath);
			
			
			Map<String,Object> mapEdition = new HashMap<String,Object>();
	
				
			TaDossierRcd doc = taDossierRcdService.findById(idDossierRcd);
			
			if(modeleEdition == null) {
				if(assurePib(doc)) {
					modeleEdition = editionDefautCondititonParticulierePIB;
				}else {
					modeleEdition = editionDefautCondititonParticuliere;
				}
			}
			
//
//			mapEdition.put("fraisgestion", taTFraisImpayeService.findByCode(TaTFraisImpaye.FRAIS_DOSSIER_YLYADE));
//			mapEdition.put("fraisgestionpib", taTFraisImpayeService.findByCode(TaTFraisImpaye.FRAIS_DOSSIER_PIB));
//			mapEdition.put("activitercpro", taActiviteService.selectAll());
			mapEdition.put("devisrcpro", doc);
			mapEdition.put("tarifpj", taTauxAssuranceService.findByCode(TaTauxAssurance.MONTANT_PROTECTION_JURIDIQUE));
			
//			List<TaTauxRcproPib> listePib = new ArrayList<TaTauxRcproPib>();
//			for (TaDossierRcdTauxPib pibEntite : doc.getTaDevisRcProTauxPibs()) {
//				TaTauxRcproPib pib = taTauxRcproPibService.findByCode(pibEntite.getCodeTauxRcProPib());
//				listePib.add(pib);
//			}
//			System.out.println(listePib);
//			mapEdition.put("listePib", listePib);
			 
			return generePDF(modeleEdition, localPath,listResourcesPath, mapEdition);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String genereAttestationAssurancePDF(int idDossierRcd, String modeleEdition) {
		try {
			List<String> listResourcesPath = new ArrayList<String>();
			listResourcesPath.add("META-INF/resources/reports/attestation/");
			
			
			
			String localPath = ylyadeProperties.osTempDirDossier(null)+"/"+ylyadeProperties.tmpFileName("attestation_assurance.pdf");
			System.out.println(localPath);
			
			
			Map<String,Object> mapEdition = new HashMap<String,Object>();
	
				
			TaDossierRcd doc = taDossierRcdService.findById(idDossierRcd);
			
			if(modeleEdition == null) {
				if(assurePib(doc)) {
					modeleEdition = editionDefautAttestationAssurancePIB;
				}else {
					modeleEdition = editionDefautAttestationAssurance;
				}
			}
			
			
			mapEdition.put("fraisgestion", taTFraisImpayeService.findByCode(TaTFraisImpaye.FRAIS_DOSSIER_YLYADE));
			mapEdition.put("fraisgestionpib", taTFraisImpayeService.findByCode(TaTFraisImpaye.FRAIS_DOSSIER_PIB));
			mapEdition.put("activitercpro", taActiviteService.selectAll());
			mapEdition.put("contratrcpro", doc);
			mapEdition.put("tarifpj", taTauxAssuranceService.findByCode(TaTauxAssurance.MONTANT_PROTECTION_JURIDIQUE));
			
			List<TaTauxRcproPib> listePib = new ArrayList<TaTauxRcproPib>();
			for (TaDossierRcdTauxPib pibEntite : doc.getTaDevisRcProTauxPibs()) {
				TaTauxRcproPib pib = taTauxRcproPibService.findByCode(pibEntite.getCodeTauxRcProPib());
				listePib.add(pib);
			}
//			System.out.println(listePib);
			mapEdition.put("listePib", listePib);
			 
			return generePDF( modeleEdition, localPath,listResourcesPath, mapEdition);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String generePDF(String modeleEdition, String localPath, List<String> listResourcesPath, Map<String,Object> mapParametreEdition) {
		try {

			String theme = "defaultTheme";
			
			listResourcesPath.add("META-INF/resources/reports/img-reports/");
			
			Map<String,Object> mapEdition = new HashMap<String,Object>();
			
			HashMap<String,Object> hm = new HashMap<String,Object>();
			hm.putAll(mapParametreEdition);
			BirtUtil.setAppContextEdition(hm);
			BirtUtil.startReportEngine();
			BirtUtil.renderReportToFile(
					modeleEdition,
					localPath, 
					new HashMap<>(), 
					BirtUtil.PDF_FORMAT,
					listResourcesPath,
					theme);

			 return localPath;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	private boolean assurePib(TaDossierRcd taDossierRcd) {
		boolean r = false;
		if(taDossierRcd!=null && taDossierRcd.getTaAssure()!=null && taDossierRcd.getTaAssure().getTaTAssure()!=null) {
			return taDossierRcd.getTaAssure().getTaTAssure().getCodeTAssure().equals(TaTAssure.PIB);
		}
		return r;
	}


	public String getEditionDefautPropositionDevis() {
		return editionDefautPropositionDevis;
	}



	public void setEditionDefautPropositionDevis(String editionDefautPropositionDevis) {
		this.editionDefautPropositionDevis = editionDefautPropositionDevis;
	}


	public String getEditionDefautCondititonParticuliere() {
		return editionDefautCondititonParticuliere;
	}


	public void setEditionDefautCondititonParticuliere(String editionDefautCondititonParticuliere) {
		this.editionDefautCondititonParticuliere = editionDefautCondititonParticuliere;
	}


	public String getEditionDefautAttestationAssurance() {
		return editionDefautAttestationAssurance;
	}


	public void setEditionDefautAttestationAssurance(String editionDefautAttestationAssurance) {
		this.editionDefautAttestationAssurance = editionDefautAttestationAssurance;
	}


	public String getEditionDefautAttestationAssurancePIB() {
		return editionDefautAttestationAssurancePIB;
	}


	public void setEditionDefautAttestationAssurancePIB(String editionDefautAttestationAssurancePIB) {
		this.editionDefautAttestationAssurancePIB = editionDefautAttestationAssurancePIB;
	}


	public String getEditionDefautCondititonParticulierePIB() {
		return editionDefautCondititonParticulierePIB;
	}


	public void setEditionDefautCondititonParticulierePIB(String editionDefautCondititonParticulierePIB) {
		this.editionDefautCondititonParticulierePIB = editionDefautCondititonParticulierePIB;
	}


	public String getEditionDefautPropositionDevisPIB() {
		return editionDefautPropositionDevisPIB;
	}


	public void setEditionDefautPropositionDevisPIB(String editionDefautPropositionDevisPIB) {
		this.editionDefautPropositionDevisPIB = editionDefautPropositionDevisPIB;
	}


	public String getEditionDefautAvisEcheance() {
		return editionDefautAvisEcheance;
	}


	public void setEditionDefautAvisEcheance(String editionDefautAvisEcheance) {
		this.editionDefautAvisEcheance = editionDefautAvisEcheance;
	}


	public String getEditionDefautAvisMiseEnDemeure() {
		return editionDefautAvisMiseEnDemeure;
	}


	public void setEditionDefautAvisMiseEnDemeure(String editionDefautAvisMiseEnDemeure) {
		this.editionDefautAvisMiseEnDemeure = editionDefautAvisMiseEnDemeure;
	}


	public String getEditionDefautAvisSuspensionGaranties() {
		return editionDefautAvisSuspensionGaranties;
	}


	public void setEditionDefautAvisSuspensionGaranties(String editionDefautAvisSuspensionGaranties) {
		this.editionDefautAvisSuspensionGaranties = editionDefautAvisSuspensionGaranties;
	}


	public String getEditionDefautAvisResiliationImpaye() {
		return editionDefautAvisResiliationImpaye;
	}


	public void setEditionDefautAvisResiliationImpaye(String editionDefautAvisResiliationImpaye) {
		this.editionDefautAvisResiliationImpaye = editionDefautAvisResiliationImpaye;
	}


	public String getEditionDefautAvisResiliationEcheance() {
		return editionDefautAvisResiliationEcheance;
	}


	public void setEditionDefautAvisResiliationEcheance(String editionDefautAvisResiliationEcheance) {
		this.editionDefautAvisResiliationEcheance = editionDefautAvisResiliationEcheance;
	}


	public String getEditionDefautAvisResiliationCessation() {
		return editionDefautAvisResiliationCessation;
	}


	public void setEditionDefautAvisResiliationCessation(String editionDefautAvisResiliationCessation) {
		this.editionDefautAvisResiliationCessation = editionDefautAvisResiliationCessation;
	}


	public String getEditionDefautAvisResiliationSansEffet() {
		return editionDefautAvisResiliationSansEffet;
	}


	public void setEditionDefautAvisResiliationSansEffet(String editionDefautAvisResiliationSansEffet) {
		this.editionDefautAvisResiliationSansEffet = editionDefautAvisResiliationSansEffet;
	}


	public String getEditionDefautAvisResiliationFausseDeclaration() {
		return editionDefautAvisResiliationFausseDeclaration;
	}


	public void setEditionDefautAvisResiliationFausseDeclaration(
			String editionDefautAvisResiliationFausseDeclaration) {
		this.editionDefautAvisResiliationFausseDeclaration = editionDefautAvisResiliationFausseDeclaration;
	}


	public String getEditionDefautAvisResiliationAmiable() {
		return editionDefautAvisResiliationAmiable;
	}


	public void setEditionDefautAvisResiliationAmiable(String editionDefautAvisResiliationAmiable) {
		this.editionDefautAvisResiliationAmiable = editionDefautAvisResiliationAmiable;
	}
	
	
	
	
}
