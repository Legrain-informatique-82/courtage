package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaContratGfaDAO;
import fr.ylyade.courtage.model.TaAssure;
import fr.ylyade.courtage.model.TaContratGfa;


public class TaContratGfaDAO implements ITaContratGfaDAO {

	private static final Logger logger = Logger.getLogger(TaContratGfaDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaContratGfa p";
	
	public TaContratGfaDAO(){
//		this(null);
	}

//	public TaTaContratGfaDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaContratGfa.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaContratGfa());
//	}


	public void persist(TaContratGfa transientInstance) {
		logger.debug("persisting TaContratGfa instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaContratGfa persistentInstance) {
		logger.debug("removing TaContratGfa instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdContratGfa()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaContratGfa merge(TaContratGfa detachedInstance) {
		logger.debug("merging TaContratGfa instance");
		try {
			TaContratGfa result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}
	
	public Integer countActiveByIdCourtier(int idCourtier) {
		try {
			Query query = entityManager.createNamedQuery(TaContratGfa.QN.COUNT_ACTIVE_BY_ID_COURTIER);/*Compte le nombre de ContratGfa par courtier*/
			
			query.setParameter("idCourtier", idCourtier);
			
			Integer instance = (Integer) query.getSingleResult();
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			return 0;
		}
	}

	public TaContratGfa findById(int id) {
		logger.debug("getting TaContratGfa instance with id: " + id);
		try {
			TaContratGfa instance = entityManager.find(TaContratGfa.class, id);
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
	public List<TaContratGfa> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaContratGfa> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaContratGfa entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaContratGfa> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaContratGfa> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaContratGfa> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaContratGfa> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaContratGfa value) throws Exception {
		BeanValidator<TaContratGfa> validator = new BeanValidator<TaContratGfa>(TaContratGfa.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaContratGfa value, String propertyName) throws Exception {
		BeanValidator<TaContratGfa> validator = new BeanValidator<TaContratGfa>(TaContratGfa.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaContratGfa transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaContratGfa findByCode(String code) {
		logger.debug("getting TaContratGfa instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaContratGfa f where f.codeContratGfa='"+code+"'");
				TaContratGfa instance = (TaContratGfa)query.getSingleResult();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			return null;
		}
	}
	
}

