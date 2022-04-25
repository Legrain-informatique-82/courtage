package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTDestinationUsageDAO;
import fr.ylyade.courtage.model.TaTDestinationUsage;


public class TaTDestinationUsageDAO implements ITaTDestinationUsageDAO {

	private static final Logger logger = Logger.getLogger(TaTDestinationUsageDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTDestinationUsage p";
	
	public TaTDestinationUsageDAO(){
//		this(null);
	}

//	public TaTaTDestinationUsageDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTDestinationUsage.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTDestinationUsage());
//	}


	public void persist(TaTDestinationUsage transientInstance) {
		logger.debug("persisting TaTDestinationUsage instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTDestinationUsage persistentInstance) {
		logger.debug("removing TaTDestinationUsage instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTDestinationUsage()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTDestinationUsage merge(TaTDestinationUsage detachedInstance) {
		logger.debug("merging TaTDestinationUsage instance");
		try {
			TaTDestinationUsage result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTDestinationUsage findById(int id) {
		logger.debug("getting TaTDestinationUsage instance with id: " + id);
		try {
			TaTDestinationUsage instance = entityManager.find(TaTDestinationUsage.class, id);
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
	public List<TaTDestinationUsage> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTDestinationUsage> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTDestinationUsage entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTDestinationUsage> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTDestinationUsage> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTDestinationUsage> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTDestinationUsage> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTDestinationUsage value) throws Exception {
		BeanValidator<TaTDestinationUsage> validator = new BeanValidator<TaTDestinationUsage>(TaTDestinationUsage.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTDestinationUsage value, String propertyName) throws Exception {
		BeanValidator<TaTDestinationUsage> validator = new BeanValidator<TaTDestinationUsage>(TaTDestinationUsage.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTDestinationUsage transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTDestinationUsage findByCode(String code) {
		logger.debug("getting TaTDestinationUsage instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTDestinationUsage f where f.codeTDestinationUsage='"+code+"'");
				TaTDestinationUsage instance = (TaTDestinationUsage)query.getSingleResult();
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

