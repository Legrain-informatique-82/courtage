package fr.ylyade.courtage.dao.jpa;


import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaEcheanceDAO;
import fr.ylyade.courtage.dto.TaAssureDTO;
import fr.ylyade.courtage.dto.TaEcheanceDTO;
import fr.ylyade.courtage.model.TaAssure;
import fr.ylyade.courtage.model.TaEcheance;


public class TaEcheanceDAO implements ITaEcheanceDAO {

	private static final Logger logger = Logger.getLogger(TaEcheanceDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaEcheance p";
	
	public TaEcheanceDAO(){
//		this(null);
	}

//	public TaTaEcheanceDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaEcheance.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaEcheance());
//	}


	public void persist(TaEcheance transientInstance) {
		logger.debug("persisting TaEcheance instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaEcheance persistentInstance) {
		logger.debug("removing TaEcheance instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdEcheance()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaEcheance merge(TaEcheance detachedInstance) {
		logger.debug("merging TaEcheance instance");
		try {
			TaEcheance result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaEcheance findById(int id) {
		logger.debug("getting TaEcheance instance with id: " + id);
		try {
			TaEcheance instance = entityManager.find(TaEcheance.class, id);
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
	public List<TaEcheance> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaEcheance> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaEcheance entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaEcheance> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaEcheance> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaEcheance> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaEcheance> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaEcheance value) throws Exception {
		BeanValidator<TaEcheance> validator = new BeanValidator<TaEcheance>(TaEcheance.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaEcheance value, String propertyName) throws Exception {
		BeanValidator<TaEcheance> validator = new BeanValidator<TaEcheance>(TaEcheance.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaEcheance transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaEcheance findByCode(String code) {
		logger.debug("getting TaEcheance instance with codeEcheance: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaEcheance f where f.codeEcheance='"+code+"'");
				TaEcheance instance = (TaEcheance)query.getSingleResult();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			return null;
		}
	}
	
	public List<TaEcheanceDTO> findAllEcheanceRCDDTO() {
		try {
			Query query = entityManager.createNamedQuery(TaEcheance.QN.FIND_ALL_ECHEANCE_RCD_DTO);
			
			@SuppressWarnings("unchecked")
			List<TaEcheanceDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	public List<TaEcheanceDTO> findAllEcheancesATermesDansExactementXJoursByIdDossier(Integer idDossierRcd, Date dateX){
		try {
			Query query = entityManager.createNamedQuery(TaEcheance.QN.FIND_ALL_ECHEANCES_A_TERMES_DANS_EXACTEMENT_X_JOURS_BY_ID_DOSSIER);
			
			query.setParameter("idDossierRcd", idDossierRcd);
			query.setParameter("dateX", dateX);
			
			@SuppressWarnings("unchecked")
			List<TaEcheanceDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaEcheanceDTO> findAllEcheanceRCDIdDTO(Integer idRCD) {
		try {
			Query query = entityManager.createNamedQuery(TaEcheance.QN.FIND_ALL_ECHEANCE_REGLEMENT_RCD_ID_DTO);
			
			query.setParameter("idDevis", idRCD);
			
			@SuppressWarnings("unchecked")
			List<TaEcheanceDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaEcheance> findAllEcheanceByIdDossier(Integer idDossierRcd) {
		try {
			Query query = entityManager.createNamedQuery(TaEcheance.QN.FIND_ALL_ECHEANCE_BY_ID_DOSSIER);
			
			query.setParameter("idDossierRcd", idDossierRcd);
			
			@SuppressWarnings("unchecked")
			List<TaEcheance> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	
	public TaEcheanceDTO findEcheanceReglementRCDDTO(Integer idEcheanceRcd) {
		try {
			Query query = entityManager.createNamedQuery(TaEcheance.QN.FIND_ECHEANCE_REGLEMENT_RCD_DTO);
			
			query.setParameter("idEcheance", idEcheanceRcd);
			
			@SuppressWarnings("unchecked")
			TaEcheanceDTO l = (TaEcheanceDTO) query.getSingleResult();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	public TaEcheance findEcheanceNonPayerDepuis20Jours(Integer idDossierRcd, Date todayMoins20) {
		try {
			Query query = entityManager.createNamedQuery(TaEcheance.QN.FIND_ECHEANCE_NON_PAYER_DEPUIS_20_JOURS);
			
			query.setParameter("idDossierRcd", idDossierRcd);
			query.setParameter("todayMoins20", todayMoins20);
			
			@SuppressWarnings("unchecked")
			TaEcheance l = (TaEcheance) query.getSingleResult();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaEcheance findEcheanceNonPayerDepuis41Jour(Integer idDossierRcd, Date todayMoins41) {
		try {
			Query query = entityManager.createNamedQuery(TaEcheance.QN.FIND_ECHEANCE_NON_PAYER_DEPUIS_X_JOURS);
			
			query.setParameter("idDossierRcd", idDossierRcd);
			query.setParameter("todayMoins41", todayMoins41);
			
			@SuppressWarnings("unchecked")
			TaEcheance l = (TaEcheance) query.getSingleResult();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaEcheance findFirstEcheanceNonPayer(Integer idDossierRcd) {
		try {
			Query query = entityManager.createNamedQuery(TaEcheance.QN.FIND_FIRST_ECHEANCE_NON_PAYER);
//			Query query = entityManager.createNativeQuery("SELECT e FROM ta_echeance e LEFT JOIN ta_dossier_rcd d ON e.id_devis_rc_pro_ta_devis_rc_pro = d.id_devis_rc_pro_detail WHERE d.id_devis_rc_pro_detail = 314 and e.id_reglement_assurance_ta_reglement_assurance is null limit 1");
					
			query.setParameter("idDossierRcd", idDossierRcd);
//			query.setParameter(1, idDossierRcd);
			
			query.setMaxResults(1);
			
			
			
			@SuppressWarnings("unchecked")
			List<TaEcheance> liste = query.getResultList();
//			TaEcheance l = (TaEcheance) query.getSingleResult();
			TaEcheance l = liste.get(0);

			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	

	
}

