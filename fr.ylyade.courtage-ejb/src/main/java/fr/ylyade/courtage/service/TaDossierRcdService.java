package fr.ylyade.courtage.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;

import org.apache.log4j.Logger;
import org.hibernate.OptimisticLockException;


import fr.legrain.birt.AnalyseFileReport;
import fr.legrain.birt.BirtUtil;
import fr.legrain.courtage.controle.service.interfaces.remote.general.ITaGenCodeExServiceRemote;
import fr.legrain.data.AbstractApplicationDAOServer;
import fr.legrain.data.YlyadeProperties;
import fr.ylyade.courtage.dao.ITaDossierRcdDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaCourtierDTO;
import fr.ylyade.courtage.dto.TaDossierRcdDTO;
import fr.ylyade.courtage.model.IDonneStatut;
import fr.ylyade.courtage.model.TaActivite;
import fr.ylyade.courtage.model.TaAttestationNominative;
import fr.ylyade.courtage.model.TaCourtier;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaDossierRcdActivite;
import fr.ylyade.courtage.model.TaDossierRcdTauxPib;
import fr.ylyade.courtage.model.TaGedUtilisateur;
import fr.ylyade.courtage.model.TaSinistreAntecedent;
import fr.ylyade.courtage.model.TaTReglement;
import fr.ylyade.courtage.model.TaTStatut;
import fr.ylyade.courtage.model.TaTauxAssurance;
import fr.ylyade.courtage.model.mapper.TaDossierRcdMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaAttestationNominativeServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaDossierRcdServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaGedUtilisateurServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTStatutServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaTauxAssuranceServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaSousDonneeServiceRemote;



/**
 * Session Bean implementation class TaDevisRcProBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaDossierRcdService extends AbstractApplicationDAOServer<TaDossierRcd, TaDossierRcdDTO> implements ITaDossierRcdServiceRemote {

	static Logger logger = Logger.getLogger(TaDossierRcdService.class);

	@Inject private ITaDossierRcdDAO dao;
	private YlyadeProperties ylyadeProperties;
	
	@EJB private ITaGenCodeExServiceRemote gencode;
	@EJB private ITaTauxAssuranceServiceRemote taTauxAssuranceService;
	@EJB private ITaSousDonneeServiceRemote taSousDonneeService;
	@EJB private ITaGedUtilisateurServiceRemote taGedUtilisateurService;
	@EJB private ITaAttestationNominativeServiceRemote taAttestationNominativeService;
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;
	private String editionDefaut = null;
	
	protected @EJB ITaTStatutServiceRemote taTStatutService;

	/**
	 * Default constructor. 
	 */
	public TaDossierRcdService() {
		super(TaDossierRcd.class,TaDossierRcdDTO.class);
		ylyadeProperties = new YlyadeProperties();
		//editionDefaut = "/reports/devisRcProDetail/PropositionRcDecennale.rptdesign";
		editionDefaut = "/devisRcProDetail/PropositionRcDecennale.rptdesign";
		//editionDefaut = EditionProgrammeDefaut.EDITION_DEFAUT_FACTURE_FICHIER;
	}
	
	public BigDecimal findMontantPrime(List<Integer> listeIdActivite, BigDecimal caPrevisionnel) {
		return dao.findMontantPrime(listeIdActivite,caPrevisionnel);
	}
	
	public TaActivite findActiviteDeReference(List<Integer> listeIdActivite, BigDecimal caPrevisionnel) {
		return dao.findActiviteDeReference(listeIdActivite, caPrevisionnel);
	}
	
	public String generePDF(int idDossierRcd) {
		return generePDF(idDossierRcd,editionDefaut);
	}
	
	public String writingFileEdition(TaGedUtilisateur taEdition) {
		String localPath;
		localPath = ylyadeProperties.osTempDirDossier(null)+"/"+ylyadeProperties.tmpFileName(taEdition.getNomFichier());
		try {
			Files.write(Paths.get(localPath), taEdition.getFichierDoc());
			AnalyseFileReport afr = new AnalyseFileReport();
			afr.initializeBuildDesignReportConfig(localPath);
			afr.ajouteLogo();
			afr.closeDesignReportConfig();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return localPath;
		
	}
	
	public String generePDF(int idDossierRcd, String modeleEdition) {
		try {
			
			
			//rajout yann de code comme sur factureService (BDG)
			//String localPath = writingFileEdition(edition);
			//fin rajout code
			
			List<String> listResourcesPath = new ArrayList<String>();
			String theme = null;
			
			
			String localPath = ylyadeProperties.osTempDirDossier(null)+"/"+ylyadeProperties.tmpFileName("proposition_rcd.pdf");
			System.out.println(localPath);
			System.out.println(ylyadeProperties.urlDemoHttps()+modeleEdition);
			
			Map<String,Object> mapEdition = new HashMap<String,Object>();

				
			TaDossierRcd doc = findById(idDossierRcd);
					
			mapEdition.put("devisrcpro", doc);
			mapEdition.put("tarifpj", taTauxAssuranceService.findByCode(TaTauxAssurance.MONTANT_PROTECTION_JURIDIQUE));

			////////////////////////////////////////////////////////////////////////////////////////
			//Test génération de fichier PDF
			
			HashMap<String,Object> hm = new HashMap<>();

			hm.put("edition", mapEdition);
			
			BirtUtil.setAppContextEdition(hm);
			BirtUtil.startReportEngine();
			
			//Ne fonctionne pas sur le projet Ylyade pour l'instant
			BirtUtil.renderReportToFile(
					//ylyadeProperties.urlDemoHttps()+modeleEdition, //"https://dev.demo.promethee.biz:8443/reports/documents/facture/FicheFacture.rptdesign",
					modeleEdition,
					localPath, 
					new HashMap<>(), 
					BirtUtil.PDF_FORMAT,
					listResourcesPath,
					theme);
			 
			 return localPath;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String genereCode( Map<String, String> params) {
		try {
			return gencode.genereCodeJPA(TaDossierRcd.class.getSimpleName(),params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "NOUVEAU CODE";
	}

	public void annuleCode(String code) {
		try {

			gencode.annulerCodeGenere(gencode.findByCode(TaDossierRcd.class.getSimpleName()),code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void verrouilleCode(String code) {
		try {
			gencode.rentreCodeGenere(gencode.findByCode(TaDossierRcd.class.getSimpleName()),code, sessionInfo.getSessionID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<TaDossierRcdDTO> findAllAvecPiecesInvalides(){
		return dao.findAllAvecPiecesInvalides();
	}
	public List<TaDossierRcdDTO> findAllAvenantByNumPolice(String numDossierPolice) {
		return dao.findAllAvenantByNumPolice(numDossierPolice);
	}
	
	public List<TaDossierRcdDTO> findByCodeLight(String code) {
		return dao.findByCodeLight(code);
	}

	public List<TaDossierRcdDTO> findAllLight() {
		return dao.findAllLight();
	}
	
	public List<TaDossierRcdDTO> findAllByIdCourtier(int idCourtier){
		return dao.findAllByIdCourtier(idCourtier);	
	}
	
	public List<TaDossierRcdDTO> findContratByCodeLight(String code) {
		return dao.findContratByCodeLight(code);
	}

	public List<TaDossierRcdDTO> findAllContratLight() {
		return dao.findAllContratLight();
	}
	
	public List<TaDossierRcdDTO> findAllContratLightPlusExtraction() {
		return dao.findAllContratLightPlusExtraction();
	}
	
	public List<TaDossierRcdDTO> findAllContratByIdCourtier(int idCourtier){
		return dao.findAllContratByIdCourtier(idCourtier);	
	}
	public List<TaDossierRcdDTO> findAllContratATraiterByIdCourtier(int idCourtier){
		return dao.findAllContratATraiterByIdCourtier(idCourtier);
	}
	public List<TaDossierRcdDTO> findAllContratATraiter(){
		return dao.findAllContratATraiter();
	}
	public List<TaDossierRcdDTO> findRefuseAssureur() {
		return dao.findRefuseAssureur();
	}
	public List<TaDossierRcdDTO> findRefuseAssureurByIdCourtier(int idCourtier){
		return dao.findRefuseAssureurByIdCourtier(idCourtier);
	}
	public List<TaDossierRcdDTO> findAllPieceManquanteOuRefuseByIdCourtier(int idCourtier){
		return dao.findAllPieceManquanteOuRefuseByIdCourtier(idCourtier);
	}
	public List<TaDossierRcdDTO> findAllAValiderYlyade(){
		return dao.findAllAValiderYlyade();
	}
	public List<TaDossierRcdDTO> findAllAValiderEnAttentePaiementYlyade() {
		return dao.findAllAValiderEnAttentePaiementYlyade();
	}
	public List<TaDossierRcdDTO> findAllValidationYlyade(){
		return dao.findAllValidationYlyade();
	}
	public List<TaDossierRcdDTO> findAllByIdAssure(int idAssure){
		return dao.findAllByIdAssure(idAssure);	
	}
	
	public List<TaDossierRcdDTO> findAllContratByIdAssure(int idAssure){
		return dao.findAllContratByIdAssure(idAssure);	
	}
	public List<TaDossierRcdDTO>  findAllAttenteValidationYlyadeByIdCourtier(int idCourtier) {
		return dao.findAllAttenteValidationYlyadeByIdCourtier(idCourtier);
	}
	
	public List<TaDossierRcdDTO>  findAllAttenteValidationAssureurByIdCourtier(int idCourtier){
		return dao.findAllAttenteValidationAssureurByIdCourtier(idCourtier);
	}
	///////////PAIEMENT
	public List<TaDossierRcdDTO> findAllEnAttentePaiementByIdCourtier(int idCourtier){
		return dao.findAllEnAttentePaiementByIdCourtier(idCourtier);
	}
	public List<TaDossierRcdDTO> findAllEnAttentePaiementYlyade(){
		return dao.findAllEnAttentePaiementYlyade();
	}
	public List<TaDossierRcdDTO> findAllEnAttenteEnvoiChequeByIdCourtier(int idCourtier){
		return dao.findAllEnAttenteEnvoiChequeByIdCourtier(idCourtier);
	}
	public List<TaDossierRcdDTO> findAllEnAttenteEncaissementChequeByIdCourtier(int idCourtier){
		return dao.findAllEnAttenteEncaissementChequeByIdCourtier(idCourtier);
	}
	public List<TaDossierRcdDTO> findAllEnAttenteEnvoiCheque(){
		return dao.findAllEnAttenteEnvoiCheque();
	}
	public List<TaDossierRcdDTO> findAllEnAttenteReceptionCheque(){
		return dao.findAllEnAttenteReceptionCheque();
	}
	public List<TaDossierRcdDTO> findAllEnAttenteDepotCheque(){
		return dao.findAllEnAttenteDepotCheque();
	}
	public List<TaDossierRcdDTO> findAllEnAttenteEncaissementCheque(){
		return dao.findAllEnAttenteEncaissementCheque();
	}
	public List<TaDossierRcdDTO> findAllEnAttenteReceptionVirement(){
		return dao.findAllEnAttenteReceptionVirement();
	}
	public List<TaDossierRcdDTO> findAllEnAttenteVirementEffectue(){
		return dao.findAllEnAttenteVirementEffectue();
	}
	public List<TaDossierRcdDTO> findAllEnAttenteReceptionVirementByIdCourtier(int idCourtier){
		return dao.findAllEnAttenteReceptionVirementByIdCourtier(idCourtier);
	}
	public List<TaDossierRcdDTO> findAllEnAttenteVirementEffectueByIdCourtier(int idCourtier){
		return dao.findAllEnAttenteVirementEffectueByIdCourtier(idCourtier);
	}
	////AVENANTS
	public List<TaDossierRcdDTO> findAllAvenantPieceManquanteOuRefuseByIdCourtier(int idCourtier){
		return dao.findAllAvenantPieceManquanteOuRefuseByIdCourtier(idCourtier);
	}
	public List<TaDossierRcdDTO> findAllAvenantEnAttentePaiementByIdCourtier(int idCourtier){
		return dao.findAllAvenantEnAttentePaiementByIdCourtier(idCourtier);
	}
	public List<TaDossierRcdDTO>  findAllAvenantAttenteValidationAssureurByIdCourtier(int idCourtier){
		return dao.findAllAvenantAttenteValidationAssureurByIdCourtier(idCourtier);
	}
	public List<TaDossierRcdDTO>  findAllAvenantAttenteValidationYlyadeByIdCourtier(int idCourtier){
		return dao.findAllAvenantAttenteValidationYlyadeByIdCourtier(idCourtier);
	}
	public List<TaDossierRcdDTO> findAvenantRefuseAssureurByIdCourtier(int idCourtier){
		return dao.findAvenantRefuseAssureurByIdCourtier(idCourtier);
	}
	public List<TaDossierRcdDTO> findAvenantRefuseAssureur(){
		return dao.findAvenantRefuseAssureur();
	}
	public List<TaDossierRcdDTO> findAllAvenantEnAttentePaiementYlyade(){
		return dao.findAllAvenantEnAttentePaiementYlyade();
	}
	public List<TaDossierRcdDTO> findAllAvenantValidationYlyade(){
		return dao.findAllAvenantValidationYlyade();
	}
	public List<TaDossierRcdDTO> findAllAvenantEnAttenteValidationYlyade() {
		return dao.findAllAvenantEnAttenteValidationYlyade();
	}
	
/////SOUMIS ETUDE
	//courtier
	public List<TaDossierRcdDTO> findAllSoumisEtudeAttenteValidationYlyadeByIdCourtier(int idCourtier){
		return dao.findAllSoumisEtudeAttenteValidationYlyadeByIdCourtier(idCourtier);
	}
	public List<TaDossierRcdDTO> findAllSoumisEtudeAttenteValidationAssureurByIdCourtier(int idCourtier){
		return dao.findAllSoumisEtudeAttenteValidationAssureurByIdCourtier(idCourtier);
	}
	public List<TaDossierRcdDTO> findAllSoumisEtudeValideByIdCourtier(int idCourtier){
		return dao.findAllSoumisEtudeValideByIdCourtier(idCourtier);
	}
	public List<TaDossierRcdDTO> findAllSoumisEtudeRefuseByIdCourtier(int idCourtier){
		return dao.findAllSoumisEtudeRefuseByIdCourtier(idCourtier);
	}
	public List<TaDossierRcdDTO> findAllSoumisEtudeRefuseDefinitifByIdCourtier(int idCourtier){
		return dao.findAllSoumisEtudeRefuseDefinitifByIdCourtier(idCourtier);
	}
	//ylyade
	public List<TaDossierRcdDTO> findAllSoumisEtudeAttenteValidationYlyade(){
		return dao.findAllSoumisEtudeAttenteValidationYlyade();
	}
	public List<TaDossierRcdDTO> findAllSoumisEtudeAttenteValidationAssureur(){
		return dao.findAllSoumisEtudeAttenteValidationAssureur();
	}
	public List<TaDossierRcdDTO> findAllSoumisEtudeValide(){
		return dao.findAllSoumisEtudeValide();
	}
	public List<TaDossierRcdDTO> findAllSoumisEtudeRefuse(){
		return dao.findAllSoumisEtudeRefuse();
	}
	
	public List<TaDossierRcdDTO> findAllSoumisEtudeRefuseDefinitif(){
		return dao.findAllSoumisEtudeRefuseDefinitif();
	}
	//////////////ESPACE PAIEMENT CONTRATS
	public List<TaDossierRcdDTO> findAllContratArrivantTermes(Date todayPlus30){
		return dao.findAllContratArrivantTermes(todayPlus30);
	}
	public List<TaDossierRcdDTO> findAllContratArrivantTermesAndPasDepasse( Date todayPlus30){
		return dao.findAllContratArrivantTermesAndPasDepasse(todayPlus30);
	}
	public List<TaDossierRcdDTO> findAllContratArrivantTermesByCourtierAndPasDepasseByCourtier(int idCourtier, Date todayPlus30){
		return dao.findAllContratArrivantTermesByCourtierAndPasDepasseByCourtier(idCourtier, todayPlus30);
	}
	public List<TaDossierRcdDTO> findAllContratArrivantTermesDansExactementXjours(Date todayPlus30){
		return dao.findAllContratArrivantTermesDansExactementXjours(todayPlus30);
	}
	public List<TaDossierRcdDTO> findAllContratTermesDepasseDepuisExactementXjours(Date todayMoinsX) {
		return dao.findAllContratTermesDepasseDepuisExactementXjours(todayMoinsX);
	}
	public List<TaDossierRcdDTO> findAllContratArrivantTermesByCourtier(int idCourtier,Date todayPlus30){
		return dao.findAllContratArrivantTermesByCourtier(idCourtier, todayPlus30);
	}
	public List<TaDossierRcdDTO> findAllContratTermesDepassee(Date todayMoins10){
		return dao.findAllContratTermesDepassee(todayMoins10);
	}
	public List<TaDossierRcdDTO> findAllContratTermesDepasseeByCourtier(int idCourtier, Date todayMoins10){
		return dao.findAllContratTermesDepasseeByCourtier(idCourtier,todayMoins10);
	}
	//////////////RESILIATION
	public List<TaDossierRcdDTO> findAllContratByStatut(String codeTStatut){
		return dao.findAllContratByStatut(codeTStatut);
	}
	
	public List<TaDossierRcdDTO> findAllContratByStatutAndByCourtier(int idCourtier, String codeTStatut){
		return dao.findAllContratByStatutAndByCourtier(idCourtier, codeTStatut);
	}
	
	public List<TaDossierRcdDTO> findAllContratArrivantEcheanceAnnuelle(Date todayPlus30){
		return dao.findAllContratArrivantEcheanceAnnuelle(todayPlus30);
	}
	public List<TaDossierRcdDTO> findAllContratArrivantEcheanceAnnuelleByCourtier(int idCourtier, Date todayPlus30){
		return dao.findAllContratArrivantEcheanceAnnuelleByCourtier(idCourtier, todayPlus30);
	}
	
	public List<TaDossierRcd> findAllContratMisEnDemeure(Date todayMoins20){
		return dao.findAllContratMisEnDemeure(todayMoins20);
	}
	public List<TaDossierRcd> findAllContratSuspendusNonPaiement(Date todayMoins30){
		return dao.findAllContratSuspendusNonPaiement(todayMoins30);
	}
	public List<TaDossierRcd> findAllContratSuspendusNonPaiement41Jours(String codeTStatut, Date todayMoins41) {
		return dao.findAllContratSuspendusNonPaiement41Jours(codeTStatut, todayMoins41);
	}
	
	/////ATTESTATION
	public List<TaDossierRcdDTO> findAllContratAttentePaiementAttestationNominativeByCourtier(int idCourtier){
		return dao.findAllContratAttentePaiementAttestationNominativeByCourtier(idCourtier);
	}
	public List<TaDossierRcdDTO> findAllContratAttentePaiementAttestationNominative(){
		return dao.findAllContratAttentePaiementAttestationNominative();
	}
	public List<TaDossierRcdDTO> findAllContratAttenteValidationAttestationNominative(){
		return dao.findAllContratAttenteValidationAttestationNominative();
	}
	
	public List<TaDossierRcd> findAllContratEnCours(){
		return dao.findAllContratEnCours();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaDossierRcd transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaDossierRcd transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaDossierRcd persistentInstance) throws RemoveException {
		try {
			persistentInstance = findById(persistentInstance.getIdDossierRcd());
			taSousDonneeService.remove(taSousDonneeService.findByIdDossierRcd(persistentInstance.getIdDossierRcd()));
			dao.remove(persistentInstance);
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaDossierRcd merge(TaDossierRcd detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaDossierRcd merge(TaDossierRcd detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaDossierRcd findById(int id) throws FinderException {
		TaDossierRcd dos =  dao.findById(id);
//		List<TaAttestationNominative> liste = taAttestationNominativeService.findAllByIdDossier(id);
//		 dos.setTaAttestationNominative(liste);
		 return dos;
	}

	public TaDossierRcd findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaDossierRcd> selectAll() {
		return dao.selectAll();
	}
	
	
	public IDonneStatut donneStatut(IDonneStatut dossier) throws FinderException {
		dossier.getTaTStatut().clear(); // je vide la liste de statut du dossier
		
		//SI REFUSE DEFINITVEMENT, LE DOSSIER N'AS QUE CE STATUT : REFUSE DEFINITIF
		if((dossier.getRefusDefinitifSuperAssureur() == null || dossier.getRefusDefinitifSuperAssureur() == false ) && 
				(dossier.getRefusDefinitifYlyade() == null || dossier.getRefusDefinitifYlyade() == false)) {
			
			
			if(dossier.getContrat()== null || dossier.getContrat() == false) {//statuts pour les devis
				
				
					if(dossier.getSoumisEtude() == null || dossier.getSoumisEtude() == false ||  //statuts pour les devis non soumis à étude ou validé après étude
							(dossier.getSoumisEtude() != null && dossier.getSoumisEtude() == true) && ((dossier.getValidationApresetudeYlyade() != null && dossier.getValidationApresetudeYlyade() == true) ||
									(dossier.getValidationAssureurApresetude() != null && dossier.getValidationAssureurApresetude() == true))) { 
						
							if(dossier.getValidationGlobaleGedCourtier() == null || dossier.getValidationGlobaleGedCourtier()==false ) {
								TaTStatut taTStatut = null;
								try {
									taTStatut = taTStatutService.findByCode(TaTStatut.gedAValiderCourtier);
								} catch (FinderException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								dossier.getTaTStatut().add(taTStatut);
							}
							
							if((dossier.getValidationGlobaleGedYlyade() == null ||
									dossier.getValidationGlobaleGedYlyade()==false) &&
									((dossier.getSoumisSouscription() !=null && dossier.getSoumisSouscription() == true)) ) {
								TaTStatut taTStatut = null;
								try {
									taTStatut = taTStatutService.findByCode(TaTStatut.gedAValiderYlyade);
								} catch (FinderException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								dossier.getTaTStatut().add(taTStatut);
							}
							
							if(dossier.getPremierPaiementEffectue() == false ) {
								TaTStatut taTStatut = null;
								try {
									taTStatut = taTStatutService.findByCode(TaTStatut.attentePremierPaiement);
								} catch (FinderException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								dossier.getTaTStatut().add(taTStatut);
							}
							
							
							
							if((dossier.getSoumisSouscription() !=null && dossier.getSoumisSouscription() == true)) {
								TaTStatut taTStatut = null;
								try {
									taTStatut = taTStatutService.findByCode(TaTStatut.soumisSouscription);
								} catch (FinderException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								dossier.getTaTStatut().add(taTStatut);
							}
							
							
							if(dossier.getValidationYlyade() == null && 
									(dossier.getSoumisSouscription() !=null && dossier.getSoumisSouscription()==true ) &&
									(dossier.getValidationGlobaleGedYlyade()!= null && dossier.getValidationGlobaleGedYlyade()==true)&&
									(dossier.getPremierPaiementEffectue()!=null && dossier.getPremierPaiementEffectue() == true)){
								TaTStatut taTStatut = null;
								try {
									taTStatut = taTStatutService.findByCode(TaTStatut.attenteValidationYlyade);
								} catch (FinderException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								dossier.getTaTStatut().add(taTStatut);
								
							}
							
							
							if(dossier.getValidationSuperAssureur() == null && 
									(dossier.getValidationYlyade() != null && dossier.getValidationYlyade() == true)){
								TaTStatut taTStatut = null;
								try {
									taTStatut = taTStatutService.findByCode(TaTStatut.attenteValidationAssureur);
								} catch (FinderException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								dossier.getTaTStatut().add(taTStatut);
								
							}
							
							if(dossier.getValidationSuperAssureur() != null && dossier.getValidationSuperAssureur()== false) {
								TaTStatut taTStatut = null;
								try {
									taTStatut = taTStatutService.findByCode(TaTStatut.refuseAssureur);
								} catch (FinderException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								dossier.getTaTStatut().add(taTStatut);
							}
							
							if(dossier.getNumeroAvenant() != null) {
								TaTStatut taTStatut = null;
								try {
									taTStatut = taTStatutService.findByCode(TaTStatut.avenant);
								} catch (FinderException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								dossier.getTaTStatut().add(taTStatut);
							}
							
							
							if(dossier instanceof TaDossierRcdDTO) {//SI DTO PASSER EN PARAM
								
								if(((TaDossierRcdDTO) dossier).getCodeTReglement().equals(TaTReglement.CHEQUE) &&
										((TaDossierRcdDTO) dossier).getDateEnvoiChequeParCourtier() == null &&
										(dossier.getPremierPaiementEffectue() == null || dossier.getPremierPaiementEffectue()==false)) {
									TaTStatut taTStatut = null;
									try {
										taTStatut = taTStatutService.findByCode(TaTStatut.attenteEnvoiCheque);
									} catch (FinderException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									dossier.getTaTStatut().add(taTStatut);
									
								}
								
								
								if(((TaDossierRcdDTO) dossier).getCodeTReglement().equals(TaTReglement.CHEQUE) &&
										((TaDossierRcdDTO) dossier).getDateReceptionCheque() == null &&
										(dossier.getPremierPaiementEffectue() == null || dossier.getPremierPaiementEffectue()==false)) {
									TaTStatut taTStatut = null;
									try {
										taTStatut = taTStatutService.findByCode(TaTStatut.attenteReceptionCheque);
									} catch (FinderException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									dossier.getTaTStatut().add(taTStatut);
									
								}
								
								if(((TaDossierRcdDTO) dossier).getCodeTReglement().equals(TaTReglement.CHEQUE) &&
										((TaDossierRcdDTO) dossier).getDateDepotCheque() == null &&
										(dossier.getPremierPaiementEffectue() == null || dossier.getPremierPaiementEffectue()==false)) {
									TaTStatut taTStatut = null;
									try {
										taTStatut = taTStatutService.findByCode(TaTStatut.attenteDepotCheque);
									} catch (FinderException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									dossier.getTaTStatut().add(taTStatut);
									
								}
								
								if(((TaDossierRcdDTO) dossier).getCodeTReglement().equals(TaTReglement.CHEQUE) &&
										((TaDossierRcdDTO) dossier).getDateEncaissementCheque() == null &&
										(dossier.getPremierPaiementEffectue() == null || dossier.getPremierPaiementEffectue()==false)) {
									TaTStatut taTStatut = null;
									try {
										taTStatut = taTStatutService.findByCode(TaTStatut.attenteEncaissementCheque);
									} catch (FinderException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									dossier.getTaTStatut().add(taTStatut);
									
								}
								
								
								if(((TaDossierRcdDTO) dossier).getCodeTReglement().equals(TaTReglement.VIR) &&
										((TaDossierRcdDTO) dossier).getDateVirementEffectue() == null &&
										((TaDossierRcdDTO) dossier).getDateVirementRecu() == null) {
									TaTStatut taTStatut = null;
									try {
										taTStatut = taTStatutService.findByCode(TaTStatut.attenteVirement);
									} catch (FinderException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									dossier.getTaTStatut().add(taTStatut);
									
								}
								
								
								if(((TaDossierRcdDTO) dossier).getCodeTReglement().equals(TaTReglement.VIR) &&
										((TaDossierRcdDTO) dossier).getDateVirementEffectue() != null &&
										((TaDossierRcdDTO) dossier).getDateVirementRecu() == null) {
									TaTStatut taTStatut = null;
									try {
										taTStatut = taTStatutService.findByCode(TaTStatut.attenteValidationVirement);
									} catch (FinderException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									dossier.getTaTStatut().add(taTStatut);
									
								}
								
							}else {//SI ENTITE PASSER EN PARAM
								
								if(((TaDossierRcd) dossier).getTaTReglement().getCodeTReglement().equals(TaTReglement.CHEQUE) &&
										((TaDossierRcd) dossier).getTaEcheances().get(0).getTaReglementAssurance().getDateEnvoiChequeParCourtier() == null &&
										(dossier.getPremierPaiementEffectue() == null || dossier.getPremierPaiementEffectue()==false)) {
									TaTStatut taTStatut = null;
									try {
										taTStatut = taTStatutService.findByCode(TaTStatut.attenteEnvoiCheque);
									} catch (FinderException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									dossier.getTaTStatut().add(taTStatut);
									
								}
								
								if(((TaDossierRcd) dossier).getTaTReglement().getCodeTReglement().equals(TaTReglement.CHEQUE) &&
										((TaDossierRcd) dossier).getTaEcheances().get(0).getTaReglementAssurance().getDateReceptionCheque() == null &&
										(dossier.getPremierPaiementEffectue() == null || dossier.getPremierPaiementEffectue()==false)) {
									TaTStatut taTStatut = null;
									try {
										taTStatut = taTStatutService.findByCode(TaTStatut.attenteReceptionCheque);
									} catch (FinderException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									dossier.getTaTStatut().add(taTStatut);
									
								}
								
								if(((TaDossierRcd) dossier).getTaTReglement().getCodeTReglement().equals(TaTReglement.CHEQUE) &&
										((TaDossierRcd) dossier).getTaEcheances().get(0).getTaReglementAssurance().getDateDepotCheque() == null &&
										(dossier.getPremierPaiementEffectue() == null || dossier.getPremierPaiementEffectue()==false)) {
									TaTStatut taTStatut = null;
									try {
										taTStatut = taTStatutService.findByCode(TaTStatut.attenteDepotCheque);
									} catch (FinderException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									dossier.getTaTStatut().add(taTStatut);
									
								}
								
								if(((TaDossierRcd) dossier).getTaTReglement().getCodeTReglement().equals(TaTReglement.CHEQUE) &&
										((TaDossierRcd) dossier).getTaEcheances().get(0).getTaReglementAssurance().getDateEncaissementCheque() == null &&
										(dossier.getPremierPaiementEffectue() == null || dossier.getPremierPaiementEffectue()==false)) {
									TaTStatut taTStatut = null;
									try {
										taTStatut = taTStatutService.findByCode(TaTStatut.attenteEncaissementCheque);
									} catch (FinderException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									dossier.getTaTStatut().add(taTStatut);
									
								}
								
								if(((TaDossierRcd) dossier).getTaTReglement().getCodeTReglement().equals(TaTReglement.VIR) &&
										((TaDossierRcd) dossier).getTaEcheances().get(0).getTaReglementAssurance().getDateVirementEffectue() == null &&
										((TaDossierRcd) dossier).getTaEcheances().get(0).getTaReglementAssurance().getDateVirementRecu() == null) {
									TaTStatut taTStatut = null;
									try {
										taTStatut = taTStatutService.findByCode(TaTStatut.attenteVirement);
									} catch (FinderException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									dossier.getTaTStatut().add(taTStatut);
									
								}
								
								if(((TaDossierRcd) dossier).getTaTReglement().getCodeTReglement().equals(TaTReglement.VIR) &&
										((TaDossierRcd) dossier).getTaEcheances().get(0).getTaReglementAssurance().getDateVirementEffectue() != null &&
										((TaDossierRcd) dossier).getTaEcheances().get(0).getTaReglementAssurance().getDateVirementRecu() == null) {
									TaTStatut taTStatut = null;
									try {
										taTStatut = taTStatutService.findByCode(TaTStatut.attenteValidationVirement);
									} catch (FinderException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									dossier.getTaTStatut().add(taTStatut);
									
								}
								
							}
							
							
							
							
							

							



						
					}else {    //statuts pour les devis soumis à étude
						
						if(dossier.getSoumisEtude() !=null && dossier.getSoumisEtude() == true ) {
							TaTStatut taTStatut = null;
							try {
								taTStatut = taTStatutService.findByCode(TaTStatut.soumisEtudeYlyade);
							} catch (FinderException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							dossier.getTaTStatut().add(taTStatut);
						}
						
						if(dossier.getSoumisEtudeAssureur() !=null && dossier.getSoumisEtudeAssureur() == true ) {
							TaTStatut taTStatut = null;
							try {
								taTStatut = taTStatutService.findByCode(TaTStatut.soumisEtudeAssureur);
							} catch (FinderException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							dossier.getTaTStatut().add(taTStatut);
						}
						
						if(dossier.getValidationApresetudeYlyade() != null && dossier.getValidationApresetudeYlyade() == true ) {
							TaTStatut taTStatut = null;
							try {
								taTStatut = taTStatutService.findByCode(TaTStatut.validationApresEtudeYlyade);
							} catch (FinderException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							dossier.getTaTStatut().add(taTStatut);
						}else if(dossier.getValidationApresetudeYlyade() != null && dossier.getValidationApresetudeYlyade() == false) {
							TaTStatut taTStatut = null;
							try {
								taTStatut = taTStatutService.findByCode(TaTStatut.refusApresEtudeYlyade);
							} catch (FinderException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							dossier.getTaTStatut().add(taTStatut);
						}
						
						if(dossier.getValidationAssureurApresetude() != null && dossier.getValidationAssureurApresetude() == true ) {
							TaTStatut taTStatut = null;
							try {
								taTStatut = taTStatutService.findByCode(TaTStatut.validationApresEtudeAssureur);
							} catch (FinderException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							dossier.getTaTStatut().add(taTStatut);
						}else if(dossier.getValidationApresetudeYlyade() != null && dossier.getValidationApresetudeYlyade() == false) {
							TaTStatut taTStatut = null;
							try {
								taTStatut = taTStatutService.findByCode(TaTStatut.refusApresEtudeAssureur);
							} catch (FinderException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							dossier.getTaTStatut().add(taTStatut);
						}
						
						if(dossier.getValidationGlobaleGedYlyade() == null ||
								dossier.getValidationGlobaleGedYlyade()==false ) {
							TaTStatut taTStatut = null;
							try {
								taTStatut = taTStatutService.findByCode(TaTStatut.gedAValiderYlyade);
							} catch (FinderException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							dossier.getTaTStatut().add(taTStatut);
						}
						
						if(dossier.getValidationGlobaleGedCourtier() == null || dossier.getValidationGlobaleGedCourtier()==false ) {
							TaTStatut taTStatut = null;
							try {
								taTStatut = taTStatutService.findByCode(TaTStatut.gedAValiderCourtier);
							} catch (FinderException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							dossier.getTaTStatut().add(taTStatut);
						}
						
						if(dossier.getNumeroAvenant() != null) {
							TaTStatut taTStatut = null;
							try {
								taTStatut = taTStatutService.findByCode(TaTStatut.avenant);
							} catch (FinderException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							dossier.getTaTStatut().add(taTStatut);
						}
						
						
						
						
					}
					
					
					
					
				}else {                         // statuts pour les contrats
					
					
					if(dossier.getSuspenduAvenant() == true) { // si contrat suspendu car avenant
						TaTStatut taTStatut = null;
						try {
							taTStatut = taTStatutService.findByCode(TaTStatut.suspenduAvenant);
						} catch (FinderException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						dossier.getTaTStatut().add(taTStatut);
						
					}else {// si contrat n'est pas suspendu car avenant
						
						if(dossier.getContratEnCours()) {// si contrats en cours
							TaTStatut taTStatut = null;
							try {
								taTStatut = taTStatutService.findByCode(TaTStatut.enCours);
							} catch (FinderException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							dossier.getTaTStatut().add(taTStatut);
							
							if(dossier.getMisEnDemeure() != null) {
								TaTStatut taTStatut2 = null;
								try {
									taTStatut2 = taTStatutService.findByCode(TaTStatut.miseDemeure);
								} catch (FinderException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								dossier.getTaTStatut().add(taTStatut2);
							}
						}else { // si contrats pas en cours
							
							
							
							
							if(dossier.getTraite() == false) {
								TaTStatut taTStatut = null;
								try {
									taTStatut = taTStatutService.findByCode(TaTStatut.aTraiter);
								} catch (FinderException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								dossier.getTaTStatut().add(taTStatut);
							}
							
							if(dossier.getResilieAmiableContrat() != null) {
								TaTStatut taTStatut = null;
								try {
									taTStatut = taTStatutService.findByCode(TaTStatut.resilieAmiable);
								} catch (FinderException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								dossier.getTaTStatut().add(taTStatut);
							}
							
							if(dossier.getResilieCessationActiviteContrat() != null) {
								TaTStatut taTStatut = null;
								try {
									taTStatut = taTStatutService.findByCode(TaTStatut.resilieCessationActivite);
								} catch (FinderException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								dossier.getTaTStatut().add(taTStatut);
							}
							
							if(dossier.getResilieEcheanceContrat() != null) {
								TaTStatut taTStatut = null;
								try {
									taTStatut = taTStatutService.findByCode(TaTStatut.resilieEcheance);
								} catch (FinderException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								dossier.getTaTStatut().add(taTStatut);
							}
							if(dossier.getResilieFausseDeclarationContrat() != null) {
								TaTStatut taTStatut = null;
								try {
									taTStatut = taTStatutService.findByCode(TaTStatut.resilieFausseDeclaration);
								} catch (FinderException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								dossier.getTaTStatut().add(taTStatut);
							}
							if(dossier.getResilieNonPaiementContrat() != null) {
								TaTStatut taTStatut = null;
								try {
									taTStatut = taTStatutService.findByCode(TaTStatut.resilieNonPaiement);
								} catch (FinderException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								dossier.getTaTStatut().add(taTStatut);
							}else {
								if(dossier.getSuspenduNonPaiement() != null) {
									TaTStatut taTStatut2 = null;
									try {
										taTStatut2 = taTStatutService.findByCode(TaTStatut.suspendu);
									} catch (FinderException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									dossier.getTaTStatut().add(taTStatut2);
								}
							}
							if(dossier.getResilieSansEffetContrat() != null) {
								TaTStatut taTStatut = null;
								try {
									taTStatut = taTStatutService.findByCode(TaTStatut.sansEffet);
								} catch (FinderException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								dossier.getTaTStatut().add(taTStatut);
							}
							
						}
						
					}
					
					
					
					
					
					
					
				}
			
					
			
		}else {              //statut refus Definitif
			
			TaTStatut taTStatut = null;
			try {
				taTStatut = taTStatutService.findByCode(TaTStatut.refusDefinitif);
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dossier.getTaTStatut().add(taTStatut);
			
		}
		
		return dossier;
		
		
	}
	
	
	
	public TaDossierRcd genereAvenant(TaDossierRcd contrat) {
		TaDossierRcd d = new TaDossierRcd();
		
		d.setTaAssure(contrat.getTaAssure());
		d.setTaCourtier(contrat.getTaCourtier());
		d.setNumDossierPolice(contrat.getNumDossierPolice());
		d.setContratOrigine(contrat.getContratOrigine()!=null?contrat.getContratOrigine():contrat);
		d.setContratParentDirect(contrat);
		d.setDateDevis(new Date());
		d.setVersion(null);
		
		d.setTaTSousTraitance(contrat.getTaTSousTraitance());
		
		

		d.setResilieNonPaiement(contrat.getResilieNonPaiement());
		d.setResilieFausseDeclaration(contrat.getResilieFausseDeclaration());
		d.setDateEffet(contrat.getDateEffet());
		d.setDateResiliation(contrat.getDateResiliation());

		
		
//		d.setImgDevisRcd(contrat.getImgDevisRcd());
		d.setMontantPrime(contrat.getMontantPrime());
		
		d.setCodeSousTraitance(contrat.getCodeSousTraitance());
		d.setPourcentSoustraitance(contrat.getPourcentSoustraitance());
		
		d.setFraisRcPro(contrat.getFraisRcPro());
		d.setTauxCommission(contrat.getTauxCommission());
		d.setDejaAssurer(contrat.getDejaAssurer());
		d.setContratEnCours(false);
		d.setReprisePasse(contrat.getReprisePasse());
		
		
		//bloc Antécédants
		d.setAssureMemeRisque(contrat.getAssureMemeRisque());
		d.setAssureMemeRisqueRcd(contrat.getAssureMemeRisqueRcd());
		d.setAssureMemeRisqueRce(contrat.getAssureMemeRisqueRce());
		d.setAntecedentRcdNomAssureur(contrat.getAntecedentRcdNomAssureur());
		d.setAntecedentRceNomAssureur(contrat.getAntecedentRceNomAssureur());
		d.setAntecedentRcdPoliceAssureur(contrat.getAntecedentRcdPoliceAssureur());
		d.setAntecedentRcePoliceAssureur(contrat.getAntecedentRcePoliceAssureur());
		d.setAntecedentRcdDateResiliation(contrat.getAntecedentRcdDateResiliation());
		d.setAntecedentRceDateResiliation(contrat.getAntecedentRceDateResiliation());
		d.setMotifResiliation(contrat.getMotifResiliation());

		
		
		
		
		//bloc sinistres
		d.setNbSinistre5ans(contrat.getNbSinistre5ans());
		d.setNbSinistreTotal(contrat.getNbSinistreTotal());
		d.setMontantSinistre(contrat.getMontantSinistre());
		
		d.setSinistraliteLiquidationSocieteDemandeuse(contrat.getSinistraliteLiquidationSocieteDemandeuse());
		d.setSinistraliteLiquidationAutreSociete(contrat.getSinistraliteLiquidationAutreSociete());
		d.setSinistraliteRisqueRefusAssurance(contrat.getSinistraliteRisqueRefusAssurance());
		d.setSinistraliteMiseEnCause(contrat.getSinistraliteMiseEnCause());
		d.setSinistraliteEvenementEngageantResp(contrat.getSinistraliteEvenementEngageantResp());
            
            
            
            
		if(contrat.getTaSinistreAntecedent()!=null) {
			d.setTaSinistreAntecedent(new ArrayList<TaSinistreAntecedent>());
			TaSinistreAntecedent ac = null;
			for (TaSinistreAntecedent b : contrat.getTaSinistreAntecedent()) {
				ac = new TaSinistreAntecedent();
				ac.setAnneeDeclaration(b.getAnneeDeclaration());
				ac.setDateSinistre(b.getDateSinistre());
				ac.setLiblSinistre(b.getLiblSinistre());
				ac.setMontantSinistre(b.getMontantSinistre());
				ac.setNatureDommages(b.getNatureDommages());
				ac.setNbrSinistres(b.getNbrSinistres());
				ac.setPartResponsabilite(b.getPartResponsabilite());
				ac.setQuandCree(b.getQuandCree());
				ac.setQuandModif(b.getQuandModif());
				ac.setQuiCree(b.getQuiCree());
				ac.setQuiModif(b.getQuiModif());
				ac.setTaDossierRcd(d);
				ac.setTaTGarantieSinistre(b.getTaTGarantieSinistre());
			
				d.getTaSinistreAntecedent().add(ac);
			}
		}
		
		
		d.setMontantTravauxHtMax50k(contrat.getMontantTravauxHtMax50k());
		d.setMontantTravauxHtMax100k(contrat.getMontantTravauxHtMax100k());
		d.setMontantTravauxHtMax250k(contrat.getMontantTravauxHtMax250k());
		d.setMontantTravauxHtMax500k(contrat.getMontantTravauxHtMax500k());
		d.setMontantTravauxHtMax1m(contrat.getMontantTravauxHtMax1m());
		
		d.setAnneesExperienceActivite(contrat.getAnneesExperienceActivite());
//		d.setActivitePrincipale(activitePrincipale);
//		d.setChiffreAffaireExerciceAnterieur(chiffreAffaireExerciceAnterieur);
		d.setChiffreAffaireExercicePrevisionnel(contrat.getChiffreAffaireExercicePrevisionnel());
		
		d.setEffectifSurChantier(contrat.getEffectifSurChantier());
		d.setEffectifApprentis(contrat.getEffectifApprentis());
		d.setEffectifCommerciauxAdministratifs(contrat.getEffectifCommerciauxAdministratifs());
		d.setEffectifEncadrement(contrat.getEffectifEncadrement());
		d.setEffectifTotalExercice(contrat.getEffectifTotalExercice());
//		d.setEffectifTotalExerciceAnterieur(effectifTotalExerciceAnterieur);
		
		d.setCaTotalHtExerciceNMoins1(contrat.getCaTotalHtExerciceNMoins1());
		d.setCaTotalHtExerciceNMoins2(contrat.getCaTotalHtExerciceNMoins2());
		
		d.setInterventionChantierCoutMax(contrat.getInterventionChantierCoutMax());
		d.setDateResiliationContratPrecedent(contrat.getDateEffetContratPrecendentRcd());
		d.setMotifResiliationContratPrecedent(contrat.getMotifResiliationContratPrecedent());
//		d.setPrimeNette(primeNette);
//		d.setDateContrat(); //dateDevis
		d.setDateEcheance(contrat.getDateEcheance());
//		d.setMontantPremierReglement(montantPremierReglement);
//		d.setDateDebutPremierePeriode(dateDebutPremierePeriode);
//		d.setDateFinPremierePeriode(dateFinPremierePeriode);
//		d.setMontantTaxesDiversesAssurance(montantTaxesDiversesAssurance);
		d.setTaTReglement(contrat.getTaTReglement());
		d.setTaTEcheance(contrat.getTaTEcheance());
//		d.setTaTSousTraitance(taTSousTraitance);

		d.setNomFichier(contrat.getNomFichier());
		d.setTaille(contrat.getTaille());
		d.setTypeMime(contrat.getTypeMime());
		
		d.setCodeFranchise(contrat.getCodeFranchise());
		d.setFranchise(contrat.getFranchise());
		
		d.setCodeEcheance(contrat.getCodeEcheance());
		
		
		
		d.setAssureurPrecedentRcd(contrat.getAssureurPrecedentRcd());
		d.setDateEffetContratPrecendentRcd(contrat.getDateEffetContratPrecendentRcd());
		d.setPoliceContratPrecedentRcd(contrat.getPoliceContratPrecedentRcd());
		d.setCoutGlobalSinistreRcd(contrat.getCoutGlobalSinistreRcd());
				
		if(contrat.getTaDevisRcProActivites()!=null) {
			d.setTaDevisRcProActivites(new ArrayList<TaDossierRcdActivite>());
			TaDossierRcdActivite ac = null;
			for (TaDossierRcdActivite b : contrat.getTaDevisRcProActivites()) {
				ac = new TaDossierRcdActivite();
				ac.setActivite(b.getActivite());
				ac.setTaActivite(b.getTaActivite());
				ac.setTaDossierRcd(d);
				ac.setMontantPrimeBase(b.getMontantPrimeBase());
				ac.setPalierMontantMin(b.getPalierMontantMin());
				ac.setPalierMontantMax(b.getPalierMontantMax());
				ac.setClasseAssocie(b.getClasseAssocie());
				//TODO finir mapping
				d.getTaDevisRcProActivites().add(ac);
			}
		}
		List<TaGedUtilisateur> listeGed = taGedUtilisateurService.findAllByIdDossier(contrat.getIdDossierRcd());
		if(listeGed!=null) {
			d.setTaGedUtilisateur(new ArrayList<TaGedUtilisateur>());
			TaGedUtilisateur ged = null;
			for (TaGedUtilisateur g : listeGed) {
				ged = new TaGedUtilisateur();
				ged.setCommentaireCourtier(g.getCommentaireCourtier());
				ged.setCommentaireSuperAssureur(g.getCommentaireSuperAssureur());
				ged.setCommentaireYlyade(g.getCommentaireYlyade());
				ged.setDateControleCourtier(g.getDateControleCourtier());
				ged.setDateControleYlyade(g.getDateControleYlyade());
				ged.setDateDepot(g.getDateDepot());
				ged.setFichierDoc(g.getFichierDoc());
				ged.setNomFichier(g.getNomFichier());
				ged.setQuandCree(g.getQuandCree());
				ged.setQuandModif(g.getQuandModif());
				ged.setQuiCree(g.getQuiCree());
				ged.setQuiModif(g.getQuiModif());
				ged.setTaCourtier(g.getTaCourtier());
				ged.setTaDossierRcd(d);
				ged.setTaille(g.getTaille());
				ged.setTaListeRefDoc(g.getTaListeRefDoc());
				ged.setTypeMime(g.getTypeMime());
				
				//A VOIR SI ON RECUPERE LES VALIDATIONS DU CONTRAT D'ORIGINE OU S'IL FAUT RÉUPLOAD ET REVALIDER
				ged.setValidationCourtier(g.getValidationCourtier());
				ged.setValidationSuperAssureur(g.getValidationSuperAssureur());
				ged.setValidationYlyade(g.getValidationYlyade());
				
				
				//TODO finir mapping
				d.getTaGedUtilisateur().add(ged);
			}
		}
		
		if(contrat.getTaDevisRcProTauxPibs()!=null) {
			if(contrat.getTaDevisRcProTauxPibs()!=null) {
				for (TaDossierRcdTauxPib b : contrat.getTaDevisRcProTauxPibs()) {
					//TODO finir mapping
				}
			}
		}
		
		d.setTaTauxAssurance(contrat.getTaTauxAssurance());
		d.setTauxTauxAssurance(contrat.getTauxTauxAssurance());
		
		
		
		///////////////
		d.setExperienceActivite3ans(contrat.getExperienceActivite3ans());
		d.setExperienceActivte5ans(contrat.getExperienceActivte5ans());
		d.setExerceActiviteNomenclature(contrat.getExerceActiviteNomenclature());
		d.setCoutOuvrageInferieur15k(contrat.getCoutOuvrageInferieur15k());
		d.setInterventionConstructeurMaisonIndiv(contrat.getInterventionConstructeurMaisonIndiv());
		d.setInterventionContractantGeneral(contrat.getInterventionContractantGeneral());
		d.setInterventionBatiment(contrat.getInterventionBatiment());
		d.setInterventionMaitreOeuvre(contrat.getInterventionMaitreOeuvre());
		d.setInterventionImmobilier(contrat.getInterventionImmobilier());
		d.setInterventionFabricantMatConstruction(contrat.getInterventionFabricantMatConstruction());
		d.setActivitePrincNegoceFabrMatConstructionNonPose(contrat.getActivitePrincNegoceFabrMatConstructionNonPose());
		d.setTravauxTechniqueNonCourant(contrat.getTravauxTechniqueNonCourant());
		d.setInterventionMonumentHistorique(contrat.getInterventionMonumentHistorique());
		d.setAvisTechnique(contrat.getAvisTechnique());
		d.setDocumentUnique(contrat.getDocumentUnique());
		d.setRespectRegles(contrat.getRespectRegles());
		d.setRespectDispositionSousTraitance(contrat.getRespectDispositionSousTraitance());
		d.setQualibat(contrat.getQualibat());
		
		d.setTerritorialiteLieuCorse(contrat.getTerritorialiteLieuCorse());
		d.setTerritorialiteLieuDomtom(contrat.getTerritorialiteLieuDomtom());
		d.setTerritorialiteLieuFranceMetrop(contrat.getTerritorialiteLieuFranceMetrop());
		
		d.setInformerCaractereObligatoire(contrat.getInformerCaractereObligatoire());
		d.setInformationPropositionPartieIntegranteContrat(contrat.getInformationPropositionPartieIntegranteContrat());
		d.setAutoriseAssureurCommuniquerReponses(contrat.getAutoriseAssureurCommuniquerReponses());
		d.setOpposeUtilisationDonneesFinCommerciale(contrat.getOpposeUtilisationDonneesFinCommerciale());
		////////////////
		
		return d;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaDossierRcdDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaDossierRcdDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaDossierRcd> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaDossierRcdDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaDossierRcdDTO entityToDTO(TaDossierRcd entity) {
//		TaDossierRcdDTO dto = new TaDossierRcdDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaDossierRcdMapper mapper = new TaDossierRcdMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaDossierRcdDTO> listEntityToListDTO(List<TaDossierRcd> entity) {
		List<TaDossierRcdDTO> l = new ArrayList<TaDossierRcdDTO>();

		for (TaDossierRcd taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaDossierRcdDTO> selectAllDTO() {
		System.out.println("List of TaDossierRcdDTO EJB :");
		ArrayList<TaDossierRcdDTO> liste = new ArrayList<TaDossierRcdDTO>();

		List<TaDossierRcd> projects = selectAll();
		for(TaDossierRcd project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaDossierRcdDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaDossierRcdDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaDossierRcdDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaDossierRcdDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaDossierRcdDTO dto, String validationContext) throws EJBException {
		try {
			TaDossierRcdMapper mapper = new TaDossierRcdMapper();
			TaDossierRcd entity = null;
			if(dto.getId()!=null) {
				entity = dao.findById(dto.getId());
				if(dto.getVersionObj()!=entity.getVersionObj()) {
					throw new OptimisticLockException(entity,
							"L'objet à été modifié depuis le dernier accés. Client ID : "+dto.getId()+" - Client Version objet : "+dto.getVersionObj()+" -Serveur Version Objet : "+entity.getVersionObj());
				} else {
					 entity = mapper.mapDtoToEntity(dto,entity);
				}
			}
			
			//dao.merge(entity);
			dao.detach(entity); //pour passer les controles
			enregistrerMerge(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new CreateException(e.getMessage());
			throw new EJBException(e.getMessage());
		}
	}
	
	public void persistDTO(TaDossierRcdDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaDossierRcdDTO dto, String validationContext) throws CreateException {
		try {
			TaDossierRcdMapper mapper = new TaDossierRcdMapper();
			TaDossierRcd entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaDossierRcdDTO dto) throws RemoveException {
		try {
			TaDossierRcdMapper mapper = new TaDossierRcdMapper();
			TaDossierRcd entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaDossierRcd refresh(TaDossierRcd persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaDossierRcd value, String validationContext) /*throws ExceptLgr*/ {
		try {
			if(validationContext==null) validationContext="";
			validateAll(value,validationContext,false); //ancienne validation, extraction de l'annotation et appel
			//dao.validate(value); //validation automatique via la JSR bean validation
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateEntityPropertyValidationContext")
	public void validateEntityProperty(TaDossierRcd value, String propertyName, String validationContext) {
		try {
			if(validationContext==null) validationContext="";
			validate(value, propertyName, validationContext);
			//dao.validateField(value,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOValidationContext")
	public void validateDTO(TaDossierRcdDTO dto, String validationContext) {
		try {
			TaDossierRcdMapper mapper = new TaDossierRcdMapper();
			TaDossierRcd entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaDossierRcdDTO> validator = new BeanValidator<TaDossierRcdDTO>(TaDossierRcdDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaDossierRcdDTO dto, String propertyName, String validationContext) {
		try {
			TaDossierRcdMapper mapper = new TaDossierRcdMapper();
			TaDossierRcd entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaDossierRcdDTO> validator = new BeanValidator<TaDossierRcdDTO>(TaDossierRcdDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaDossierRcdDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaDossierRcdDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaDossierRcd value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaDossierRcd value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
