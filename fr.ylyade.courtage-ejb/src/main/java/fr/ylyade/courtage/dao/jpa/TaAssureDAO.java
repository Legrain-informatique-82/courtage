package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaAssureDAO;
import fr.ylyade.courtage.dto.TaAssureDTO;
import fr.ylyade.courtage.dto.TaCourtierDTO;
import fr.ylyade.courtage.model.TaAssure;
import fr.ylyade.courtage.model.TaCourtier;


public class TaAssureDAO implements ITaAssureDAO {

	private static final Logger logger = Logger.getLogger(TaAssureDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaAssure p";
	
	public TaAssureDAO(){
//		this(null);
	}

	public List<TaAssureDTO> findAllLight() {
		try {
			Query query = entityManager.createNamedQuery(TaAssure.QN.FIND_ALL_LIGHT_PLUS);
			@SuppressWarnings("unchecked")
			List<TaAssureDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaAssureDTO> findAllLightPlusExtraction() {
		try {
			Query query = entityManager.createNamedQuery(TaAssure.QN.FIND_ALL_LIGHT_PLUS_EXTRACTION);
			@SuppressWarnings("unchecked")
			List<TaAssureDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaAssureDTO> findAllByIdCourtier(int idCourtier) {
		try {
			Query query = entityManager.createNamedQuery(TaAssure.QN.FIND_ALL_BY_ID_COURTIER);
			
			query.setParameter("idCourtier", idCourtier);
			
			@SuppressWarnings("unchecked")
			List<TaAssureDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	
	public Integer countActiveByIdCourtier(int idCourtier) {
		try {
			Query query = entityManager.createNamedQuery(TaAssure.QN.COUNT_ACTIVE_BY_ID_COURTIER);/*Compte le nombre d'assurés actifs par courtier*/
			
			query.setParameter("idCourtier", idCourtier);
			
			Integer instance = (Integer) query.getSingleResult();
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			return 0;
		}
	}
	
	public List<TaAssureDTO> findByCodeLight(String code) {
		logger.debug("getting TaCourtierDTO instance");
		try {
			Query query = null;
			if(code!=null && !code.equals("") && !code.equals("*")) {
				query = entityManager.createNamedQuery(TaAssure.QN.FIND_BY_CODE_LIGHT);
				query.setParameter("codeAssure", "%"+code+"%");
			} else {
				query = entityManager.createNamedQuery(TaAssure.QN.FIND_ALL_LIGHT_ACTIF);
			}

			List<TaAssureDTO> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	
	


	public void persist(TaAssure transientInstance) {
		logger.debug("persisting TaAssure instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaAssure persistentInstance) {
		logger.debug("removing TaAssure instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdAssure()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaAssure merge(TaAssure detachedInstance) {
		logger.debug("merging TaAssure instance");
		try {
			TaAssure result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaAssure findById(int id) {
		logger.debug("getting TaAssure instance with id: " + id);
		try {
			TaAssure instance = entityManager.find(TaAssure.class, id);
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
	public List<TaAssure> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaAssure> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaAssure entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaAssure> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaAssure> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaAssure> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaAssure> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaAssure value) throws Exception {
		BeanValidator<TaAssure> validator = new BeanValidator<TaAssure>(TaAssure.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaAssure value, String propertyName) throws Exception {
		BeanValidator<TaAssure> validator = new BeanValidator<TaAssure>(TaAssure.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaAssure transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaAssure findByCode(String code) {
		logger.debug("getting TaAssure instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaAssure f where f.codeAssure='"+code+"'");
				TaAssure instance = (TaAssure)query.getSingleResult();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			return null;
		}
	}
	
	public TaAssure findByIdUtilisateur(int idUtilisateur) {
		try {
			Query query = entityManager.createQuery("select f from TaAssure f where f.taUtilisateur.idUtilisateur="+idUtilisateur);
			TaAssure instance = (TaAssure)query.getSingleResult();
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			return null;
		}
	}
	
	
	
}

