package fr.ylyade.courtage.dao.jpa;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaDossierRcdDAO;
import fr.ylyade.courtage.dto.TaAssureDTO;
import fr.ylyade.courtage.dto.TaCourtierDTO;
import fr.ylyade.courtage.dto.TaDossierRcdDTO;
import fr.ylyade.courtage.model.TaActivite;
import fr.ylyade.courtage.model.TaAssure;
import fr.ylyade.courtage.model.TaCourtier;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaPalierClasse;
import fr.ylyade.courtage.model.TaTReglement;


public class TaDossierRcdDAO implements ITaDossierRcdDAO {

	private static final Logger logger = Logger.getLogger(TaDossierRcdDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaDossierRcd p";
	
	public TaDossierRcdDAO(){
//		this(null);
	}

	public List<TaDossierRcdDTO> findAllLight() {
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_LIGHT_PLUS);
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	public List<TaDossierRcdDTO> findAllAvecPiecesInvalides(){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_AVEC_PIECES_INVALIDES);
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findAllByIdCourtier(int idCourtier) {
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_BY_ID_COURTIER);
			
			query.setParameter("idCourtier", idCourtier);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findAllByIdAssure(int idAssure) {
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_BY_ID_ASSURE);
			
			query.setParameter("idAssure", idAssure);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findRefuseAssureur() {
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_REFUSE_ASSUREUR);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findRefuseAssureurByIdCourtier(int idCourtier){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_REFUSE_ASSUREUR_BY_ID_COURTIER);
			
			query.setParameter("idCourtier", idCourtier);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	

	public List<TaDossierRcdDTO> findAllAvenantByNumPolice(String numDossierPolice) {
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_AVENANT_BY_NUM_POLICE);
			
			query.setParameter("numDossierPolice", numDossierPolice);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findAllEnAttentePaiementYlyade(){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_PAIEMENT_YLYADE);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	public List<TaDossierRcdDTO> findAllEnAttentePaiementByIdCourtier(int idCourtier){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_PAIEMENT_BY_ID_COURTIER);
			
			query.setParameter("idCourtier", idCourtier);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
		}
	
	public List<TaDossierRcdDTO> findByCodeLight(String code) {
		logger.debug("getting TaCourtierDTO instance");
		try {
			Query query = null;
			if(code!=null && !code.equals("") && !code.equals("*")) {
				query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_BY_CODE_LIGHT);
				query.setParameter("numDevis", "%"+code+"%");
			} else {
				query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_LIGHT_ACTIF);
			}

			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findAllContratLight() {
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_LIGHT_PLUS);
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findAllContratLightPlusExtraction() {
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_LIGHT_PLUS_EXTRACTION);
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findAllContratByIdCourtier(int idCourtier) {
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_BY_ID_COURTIER);
			
			query.setParameter("idCourtier", idCourtier);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findAllContratATraiterByIdCourtier(int idCourtier) {
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_A_TRAITER_BY_ID_COURTIER);
			
			query.setParameter("idCourtier", idCourtier);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findAllContratATraiter(){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_A_TRAITER);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findAllAValiderYlyade(){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_A_VALIDER_YLYADE);		
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findAllAValiderEnAttentePaiementYlyade(){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_A_VALIDER_EN_ATTENTE_PAIEMENT_YLYADE);		
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	
	public List<TaDossierRcdDTO> findAllValidationYlyade(){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_VALIDATION_YLYADE);		
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO>  findAllAttenteValidationYlyadeByIdCourtier(int idCourtier){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_ATTENTE_VALIDATION_YLYADE_BY_ID_COURTIER);
			
			query.setParameter("idCourtier", idCourtier);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
		
	}
	
	
	public List<TaDossierRcdDTO>  findAllAttenteValidationAssureurByIdCourtier(int idCourtier){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_ATTENTE_VALIDATION_ASSUREUR_BY_ID_COURTIER);
			
			query.setParameter("idCourtier", idCourtier);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
		
	}
	
	
	
	
	public List<TaDossierRcdDTO> findAllPieceManquanteOuRefuseByIdCourtier(int idCourtier) {
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_PIECE_MANQUANTE_OU_REFUSE_BY_ID_COURTIER);
			
			query.setParameter("idCourtier", idCourtier);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	////////////////////////////////AVENANTS/////////////////////////

	///////COURTIER
	public List<TaDossierRcdDTO> findAllAvenantPieceManquanteOuRefuseByIdCourtier(int idCourtier) {
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_AVENANT_PIECE_MANQUANTE_OU_REFUSE_BY_ID_COURTIER);
			
			query.setParameter("idCourtier", idCourtier);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	/////////////////////////////////PAIEMENT ///////////////////////////////////
	
	//////////COURTIER
	
	public List<TaDossierRcdDTO> findAllEnAttenteEnvoiChequeByIdCourtier(int idCourtier){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_ENVOI_CHEQUE_BY_ID_COURTIER);
			query.setParameter("idCourtier", idCourtier);
			query.setParameter("codeTReglement", TaTReglement.CHEQUE);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findAllEnAttenteEncaissementChequeByIdCourtier(int idCourtier){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_ENCAISSEMENT_CHEQUE_BY_ID_COURTIER);
			query.setParameter("idCourtier", idCourtier);
			query.setParameter("codeTReglement", TaTReglement.CHEQUE);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findAllAvenantEnAttentePaiementByIdCourtier(int idCourtier){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_AVENANT_EN_ATTENTE_PAIEMENT_BY_ID_COURTIER);
			
			query.setParameter("idCourtier", idCourtier);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	////////YLYADE
	public List<TaDossierRcdDTO> findAllEnAttenteEnvoiCheque(){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_ENVOI_CHEQUE);
			query.setParameter("codeTReglement", TaTReglement.CHEQUE);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findAllEnAttenteReceptionCheque(){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_RECEPTION_CHEQUE);
			query.setParameter("codeTReglement", TaTReglement.CHEQUE);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	public List<TaDossierRcdDTO> findAllEnAttenteDepotCheque(){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_DEPOT_CHEQUE);
			query.setParameter("codeTReglement", TaTReglement.CHEQUE);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}	
	
	public List<TaDossierRcdDTO> findAllEnAttenteEncaissementCheque(){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_ENCAISSEMENT_CHEQUE);
			query.setParameter("codeTReglement", TaTReglement.CHEQUE);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	/////////VIREMENT
	///COURTIER

	public List<TaDossierRcdDTO> findAllEnAttenteVirementEffectueByIdCourtier(int idCourtier){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_VIREMENT_EFFECTUE_BY_ID_COURTIER);
			query.setParameter("idCourtier", idCourtier);
			query.setParameter("codeTReglement", TaTReglement.VIR);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	public List<TaDossierRcdDTO> findAllEnAttenteReceptionVirementByIdCourtier(int idCourtier){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_RECEPTION_VIREMENT_BY_ID_COURTIER);
			query.setParameter("idCourtier", idCourtier);
			query.setParameter("codeTReglement", TaTReglement.VIR);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	///YLYADE

	public List<TaDossierRcdDTO> findAllEnAttenteVirementEffectue(){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_VIREMENT_EFFECTUE);
			query.setParameter("codeTReglement", TaTReglement.VIR);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	public List<TaDossierRcdDTO> findAllEnAttenteReceptionVirement(){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_RECEPTION_VIREMENT);
			query.setParameter("codeTReglement", TaTReglement.VIR);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	public List<TaDossierRcdDTO>  findAllAvenantAttenteValidationAssureurByIdCourtier(int idCourtier){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_AVENANT_ATTENTE_VALIDATION_ASSUREUR_BY_ID_COURTIER);
			
			query.setParameter("idCourtier", idCourtier);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
		
	}
	
	public List<TaDossierRcdDTO>  findAllAvenantAttenteValidationYlyadeByIdCourtier(int idCourtier){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_AVENANT_ATTENTE_VALIDATION_YLYADE_BY_ID_COURTIER);
			
			query.setParameter("idCourtier", idCourtier);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
		
	}
	
	public List<TaDossierRcdDTO> findAvenantRefuseAssureurByIdCourtier(int idCourtier){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_AVENANT_REFUSE_ASSUREUR_BY_ID_COURTIER);
			
			query.setParameter("idCourtier", idCourtier);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	////YLYADE
	
	public List<TaDossierRcdDTO> findAvenantRefuseAssureur() {
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_AVENANT_REFUSE_ASSUREUR);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	public List<TaDossierRcdDTO> findAllAvenantEnAttentePaiementYlyade() {
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_AVENANT_EN_ATTENTE_PAIEMENT_YLYADE);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	public List<TaDossierRcdDTO> findAllAvenantValidationYlyade() {
		//donc en attente de validation assureur
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_AVENANT_VALIDATION_YLYADE);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findAllAvenantEnAttenteValidationYlyade() {
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_AVENANT_ATTENTE_VALIDATION_YLYADE);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	/////////////////////////////////FIN AVENANT////////////////////
	
	/////////////////////SOUMIS ETUDE//////////////////////////////
		//////COURTIER
		public List<TaDossierRcdDTO> findAllSoumisEtudeAttenteValidationYlyadeByIdCourtier(int idCourtier){
			try {
				Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_SOUMIS_ETUDE_ATTENTE_VALIDATION_YLYADE_BY_ID_COURTIER);
				
				query.setParameter("idCourtier", idCourtier);
				
				@SuppressWarnings("unchecked")
				List<TaDossierRcdDTO> l = query.getResultList();
				logger.debug("get successful");
				return l;

			} catch (RuntimeException re) {
				logger.error("get failed", re);
				throw re;
			}
		}
		
		public List<TaDossierRcdDTO> findAllSoumisEtudeAttenteValidationAssureurByIdCourtier(int idCourtier){
			try {
				Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_SOUMIS_ETUDE_ATTENTE_VALIDATION_ASSUREUR_BY_ID_COURTIER);
				
				query.setParameter("idCourtier", idCourtier);
				
				@SuppressWarnings("unchecked")
				List<TaDossierRcdDTO> l = query.getResultList();
				logger.debug("get successful");
				return l;

			} catch (RuntimeException re) {
				logger.error("get failed", re);
				throw re;
			}
		}
		public List<TaDossierRcdDTO> findAllSoumisEtudeValideByIdCourtier(int idCourtier){
			try {
				Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_SOUMIS_ETUDE_VALIDE_BY_ID_COURTIER);
				
				query.setParameter("idCourtier", idCourtier);
				
				@SuppressWarnings("unchecked")
				List<TaDossierRcdDTO> l = query.getResultList();
				logger.debug("get successful");
				return l;

			} catch (RuntimeException re) {
				logger.error("get failed", re);
				throw re;
			}
		}
		public List<TaDossierRcdDTO> findAllSoumisEtudeRefuseByIdCourtier(int idCourtier){
			try {
				Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_SOUMIS_ETUDE_REFUSE_BY_ID_COURTIER);
				
				query.setParameter("idCourtier", idCourtier);
				
				@SuppressWarnings("unchecked")
				List<TaDossierRcdDTO> l = query.getResultList();
				logger.debug("get successful");
				return l;

			} catch (RuntimeException re) {
				logger.error("get failed", re);
				throw re;
			}
		}
		
		public List<TaDossierRcdDTO> findAllSoumisEtudeRefuseDefinitifByIdCourtier(int idCourtier){
			try {
				Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_SOUMIS_ETUDE_REFUSE_DEFINITIF_BY_ID_COURTIER);
				
				query.setParameter("idCourtier", idCourtier);
				
				@SuppressWarnings("unchecked")
				List<TaDossierRcdDTO> l = query.getResultList();
				logger.debug("get successful");
				return l;

			} catch (RuntimeException re) {
				logger.error("get failed", re);
				throw re;
			}
		}
		
		//////YLYADE
		public List<TaDossierRcdDTO> findAllSoumisEtudeAttenteValidationYlyade(){
			try {
				Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_SOUMIS_ETUDE_ATTENTE_VALIDATION_YLYADE);
				
				
				@SuppressWarnings("unchecked")
				List<TaDossierRcdDTO> l = query.getResultList();
				logger.debug("get successful");
				return l;

			} catch (RuntimeException re) {
				logger.error("get failed", re);
				throw re;
			}
		}
		public List<TaDossierRcdDTO> findAllSoumisEtudeAttenteValidationAssureur(){
			try {
				Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_SOUMIS_ETUDE_ATTENTE_VALIDATION_ASSUREUR);
				
				
				@SuppressWarnings("unchecked")
				List<TaDossierRcdDTO> l = query.getResultList();
				logger.debug("get successful");
				return l;

			} catch (RuntimeException re) {
				logger.error("get failed", re);
				throw re;
			}
		}
		public List<TaDossierRcdDTO> findAllSoumisEtudeValide(){
			try {
				Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_SOUMIS_ETUDE_VALIDE);
				
				
				@SuppressWarnings("unchecked")
				List<TaDossierRcdDTO> l = query.getResultList();
				logger.debug("get successful");
				return l;

			} catch (RuntimeException re) {
				logger.error("get failed", re);
				throw re;
			}
		}
		public List<TaDossierRcdDTO> findAllSoumisEtudeRefuse(){
			try {
				Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_SOUMIS_ETUDE_REFUSE);
				
				
				@SuppressWarnings("unchecked")
				List<TaDossierRcdDTO> l = query.getResultList();
				logger.debug("get successful");
				return l;

			} catch (RuntimeException re) {
				logger.error("get failed", re);
				throw re;
			}
		}
		
		public List<TaDossierRcdDTO> findAllSoumisEtudeRefuseDefinitif(){
			try {
				Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_SOUMIS_ETUDE_REFUSE_DEFINITIF);
				
				
				@SuppressWarnings("unchecked")
				List<TaDossierRcdDTO> l = query.getResultList();
				logger.debug("get successful");
				return l;

			} catch (RuntimeException re) {
				logger.error("get failed", re);
				throw re;
			}
		}
		
	
	////////////////FIN SOUMIS A ETUDE //////////////////////////
		
		
	public List<TaDossierRcdDTO> findAllContratByIdAssure(int idAssure) {
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_BY_ID_ASSURE);
			
			query.setParameter("idAssure", idAssure);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findContratByCodeLight(String code) {
		logger.debug("getting TaCourtierDTO instance");
		try {
			Query query = null;
			if(code!=null && !code.equals("") && !code.equals("*")) {
				query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_CONTRAT_BY_CODE_LIGHT);
				query.setParameter("numDevis", "%"+code+"%");
			} else {
				query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_LIGHT_ACTIF);
			}

			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
///////////////////ESPACE PAIEMENT COURTIER
/////YLYADE
	public List<TaDossierRcdDTO> findAllContratArrivantTermes(Date todayPlus30){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_ARRIVANT_TERMES);
			
			query.setParameter("todayPlus30", todayPlus30);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	public List<TaDossierRcdDTO> findAllContratArrivantTermesAndPasDepasse(Date todayPlus30){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_ARRIVANT_TERMES_AND_PAS_DEPASSE);
			
			query.setParameter("todayPlus30", todayPlus30);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	public List<TaDossierRcdDTO> findAllContratArrivantTermesDansExactementXjours(Date todayPlus30){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_ARRIVANT_TERMES_DANS_EXACTEMENT_X_JOURS);
			
			query.setParameter("todayPlus30", todayPlus30);
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findAllContratTermesDepasseDepuisExactementXjours(Date todayMoinsX){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_TERMES_DEPASSE_DEPUIS_EXACTEMENT_X_JOURS);
			
			query.setParameter("todayMoinsX", todayMoinsX);
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findAllContratTermesDepassee(Date todayMoins10){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_TERMES_DEPASSEE);
			
			query.setParameter("todayMoins10", todayMoins10);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findAllContratArrivantEcheanceAnnuelle(Date todayPlus30){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_ARRIVANT_ECHEANCE_ANNUELLE);
			
			query.setParameter("todayPlus30", todayPlus30);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcd> findAllContratMisEnDemeure(Date todayMoins20){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_MIS_EN_DEMEURE);
			
			query.setParameter("todayMoins20", todayMoins20);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcd> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcd> findAllContratSuspendusNonPaiement(Date todayMoins30){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_SUSPENDUS_NON_PAIEMENT);
			
			query.setParameter("todayMoins30", todayMoins30);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcd> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcd> findAllContratSuspendusNonPaiement41Jours(String codeTStatut, Date todayMoins41){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_SUSPENDUS_NON_PAIEMENT_41_JOURS);
			
			query.setParameter("codeTStatut", codeTStatut);
			query.setParameter("todayMoins41", todayMoins41);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcd> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	public static final String FIND_ALL_CONTRAT_SUSPENDUS_NON_PAIEMENT_41_JOURS = "TaDossierRcd.findAllContratSuspendusNonPaiement41Jours";

	
/////COURTIER
	
	public List<TaDossierRcdDTO> findAllContratArrivantTermesByCourtierAndPasDepasseByCourtier(int idCourtier, Date todayPlus30){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_ARRIVANT_TERMES_AND_PAS_DEPASSE_BY_COURTIER);
			
			
			query.setParameter("idCourtier", idCourtier);
			query.setParameter("todayPlus30", todayPlus30);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findAllContratArrivantTermesByCourtier(int idCourtier, Date todayPlus30){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_ARRIVANT_TERMES_BY_COURTIER);
			
			
			query.setParameter("idCourtier", idCourtier);
			query.setParameter("todayPlus30", todayPlus30);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findAllContratTermesDepasseeByCourtier(int idCourtier, Date todayMoins10){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_TERMES_DEPASSEE_BY_COURTIER);
			
			
			query.setParameter("idCourtier", idCourtier);
			query.setParameter("todayMoins10", todayMoins10);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	
	public List<TaDossierRcdDTO> findAllContratArrivantEcheanceAnnuelleByCourtier(int idCourtier, Date todayPlus30){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_ARRIVANT_ECHEANCE_ANNUELLE_BY_COURTIER);
			
			
			query.setParameter("idCourtier", idCourtier);
			query.setParameter("todayPlus30", todayPlus30);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	

///////////////////RESILIATION
	/////YLYADE
	public List<TaDossierRcdDTO> findAllContratByStatut(String codeTStatut){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_BY_STATUT);
			
			query.setParameter("codeTStatut", codeTStatut);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	///////COURTIER
	public List<TaDossierRcdDTO> findAllContratByStatutAndByCourtier(int idCourtier, String codeTStatut){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_BY_STATUT_AND_BY_COURTIER);
			
			query.setParameter("idCourtier", idCourtier);
			query.setParameter("codeTStatut", codeTStatut);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
//////////////////FIN RESILIATION
	

	public List<TaDossierRcd> findAllContratEnCours(){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_EN_COURS);
	
			@SuppressWarnings("unchecked")
			List<TaDossierRcd> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
/////////ATTESTATION
	public List<TaDossierRcdDTO> findAllContratAttentePaiementAttestationNominativeByCourtier(int idCourtier){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_ATTENTE_PAIEMENT_ATTESTATION_NOMINATIVE_BY_COURTIER);
			
			query.setParameter("idCourtier", idCourtier);
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	public List<TaDossierRcdDTO> findAllContratAttentePaiementAttestationNominative(){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_ATTENTE_PAIEMENT_ATTESTATION_NOMINATIVE);
			
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcdDTO> findAllContratAttenteValidationAttestationNominative(){
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ALL_CONTRAT_ATTENTE_VALIDATION_ATTESTATION_NOMINATIVE);
			
			
			@SuppressWarnings("unchecked")
			List<TaDossierRcdDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	
	

	public void persist(TaDossierRcd transientInstance) {
		logger.debug("persisting TaDossierRcd instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaDossierRcd persistentInstance) {
		logger.debug("removing TaDossierRcd instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdDossierRcd()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaDossierRcd merge(TaDossierRcd detachedInstance) {
		logger.debug("merging TaDossierRcd instance");
		try {
			TaDossierRcd result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaDossierRcd findById(int id) {
		logger.debug("getting TaDossierRcd instance with id: " + id);
		try {
			TaDossierRcd instance = entityManager.find(TaDossierRcd.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaDossierRcd> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaDossierRcd> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaDossierRcd entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaDossierRcd> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaDossierRcd> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaDossierRcd> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaDossierRcd> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaDossierRcd value) throws Exception {
		BeanValidator<TaDossierRcd> validator = new BeanValidator<TaDossierRcd>(TaDossierRcd.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaDossierRcd value, String propertyName) throws Exception {
		BeanValidator<TaDossierRcd> validator = new BeanValidator<TaDossierRcd>(TaDossierRcd.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaDossierRcd transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaDossierRcd findByCode(String code) {
		logger.debug("getting TaDossierRcd instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaDossierRcd f where f.numDossierPolice='"+code+"'");
				TaDossierRcd instance = (TaDossierRcd)query.getSingleResult();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			return null;
		}
	}
	
	public BigDecimal findMontantPrime(List<Integer> listeIdActivite, BigDecimal caPrevisionnel) {
		BigDecimal prime = null;
		try {
			if(listeIdActivite!=null && !listeIdActivite.isEmpty()){
				Query query = entityManager.createQuery(""
						+ "select max(pc.montantPrimeBase) "
						+ "from TaActivite a join a.taClasse c join c.taPalierClasse pc "
						+ "where a.idActivite in :listeIdActivite "
						+ "and pc.palierMontantMin <= :caPrevisionnel1 and pc.palierMontantMax > :caPrevisionnel2");
				
				query.setParameter("listeIdActivite", listeIdActivite);
				query.setParameter("caPrevisionnel1", caPrevisionnel);
				query.setParameter("caPrevisionnel2", caPrevisionnel);
				
				//TaPalierClasse instance = (TaPalierClasse)query.getSingleResult();
				//List<TaPalierClasse> liste = (List<TaPalierClasse>)query.getResultList(); 
				List<BigDecimal> liste = (List<BigDecimal>)query.getResultList();
				if(liste!=null && !liste.isEmpty()) {
					prime = liste.get(0);
				}
				logger.debug("get successful");
				return prime;
			}
			return prime;
		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			return prime;
		}
	}
	
	public TaActivite findActiviteDeReference(List<Integer> listeIdActivite, BigDecimal caPrevisionnel) {
		TaActivite instance = null;
		try {
			if(listeIdActivite!=null && !listeIdActivite.isEmpty()){
				Query query = entityManager.createQuery(""
						+ "select a.idActivite,max(pc.montantPrimeBase) "
						+ "from TaActivite a join a.taClasse c join c.taPalierClasse pc "
						+ "where a.idActivite in :listeIdActivite"
						+ " and pc.palierMontantMin <= :caPrevisionnel1 and pc.palierMontantMax > :caPrevisionnel2 group by a.idActivite ");
				
				query.setParameter("listeIdActivite", listeIdActivite);
				query.setParameter("caPrevisionnel1", caPrevisionnel);
				query.setParameter("caPrevisionnel2", caPrevisionnel);
				
				//TaPalierClasse instance = (TaPalierClasse)query.getSingleResult();
				//List<TaPalierClasse> liste = (List<TaPalierClasse>)query.getResultList(); 
				instance = (TaActivite)query.getResultList();
				
				logger.debug("get successful");
				return instance;
			}
			return instance;
		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			return instance;
		}
	}
	
}

