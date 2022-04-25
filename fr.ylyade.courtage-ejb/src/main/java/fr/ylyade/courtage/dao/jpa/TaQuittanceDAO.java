package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaQuittanceDAO;
import fr.ylyade.courtage.model.TaQuittance;


public class TaQuittanceDAO implements ITaQuittanceDAO {

	private static final Logger logger = Logger.getLogger(TaQuittanceDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaQuittance p";
	
	public TaQuittanceDAO(){
//		this(null);
	}

//	public TaTaQuittanceDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaQuittance.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaQuittance());
//	}


	public void persist(TaQuittance transientInstance) {
		logger.debug("persisting TaQuittance instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaQuittance persistentInstance) {
		logger.debug("removing TaQuittance instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdQuittance()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaQuittance merge(TaQuittance detachedInstance) {
		logger.debug("merging TaQuittance instance");
		try {
			TaQuittance result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaQuittance findById(int id) {
		logger.debug("getting TaQuittance instance with id: " + id);
		try {
			TaQuittance instance = entityManager.find(TaQuittance.class, id);
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
	public List<TaQuittance> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaQuittance> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaQuittance entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaQuittance> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaQuittance> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaQuittance> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaQuittance> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaQuittance value) throws Exception {
		BeanValidator<TaQuittance> validator = new BeanValidator<TaQuittance>(TaQuittance.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaQuittance value, String propertyName) throws Exception {
		BeanValidator<TaQuittance> validator = new BeanValidator<TaQuittance>(TaQuittance.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaQuittance transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaQuittance findByCode(String code) {
		logger.debug("getting TaQuittance instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaQuittance f where f.codePrestation='"+code+"'");
				TaQuittance instance = (TaQuittance)query.getSingleResult();
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

