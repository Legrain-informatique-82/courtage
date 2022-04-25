package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaCourrierDAO;
import fr.ylyade.courtage.model.TaCourrier;


public class TaCourrierDAO implements ITaCourrierDAO {

	private static final Logger logger = Logger.getLogger(TaCourrierDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaCourrier p";
	
	public TaCourrierDAO(){
//		this(null);
	}

	public void persist(TaCourrier transientInstance) {
		logger.debug("persisting TaCourrier instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaCourrier persistentInstance) {
		logger.debug("removing TaCourrier instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdCourrier()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaCourrier merge(TaCourrier detachedInstance) {
		logger.debug("merging TaCourrier instance");
		try {
			TaCourrier result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaCourrier findById(int id) {
		logger.debug("getting TaCourrier instance with id: " + id);
		try {
			TaCourrier instance = entityManager.find(TaCourrier.class, id);
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
	public List<TaCourrier> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaCourrier> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaCourrier entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaCourrier> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaCourrier> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaCourrier> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaCourrier> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaCourrier value) throws Exception {
		BeanValidator<TaCourrier> validator = new BeanValidator<TaCourrier>(TaCourrier.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaCourrier value, String propertyName) throws Exception {
		BeanValidator<TaCourrier> validator = new BeanValidator<TaCourrier>(TaCourrier.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaCourrier transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaCourrier findByCode(String code) {
		logger.debug("getting TaCourrier instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaCourrier f where f.codeCourrier='"+code+"'");
				TaCourrier instance = (TaCourrier)query.getSingleResult();
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

