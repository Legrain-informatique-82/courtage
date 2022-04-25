package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTAssureDAO;
import fr.ylyade.courtage.model.TaTAssure;


public class TaTAssureDAO implements ITaTAssureDAO {

	private static final Logger logger = Logger.getLogger(TaTAssureDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTAssure p";
	
	public TaTAssureDAO(){
//		this(null);
	}

//	public TaTaTAssureDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTAssure.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTAssure());
//	}


	public void persist(TaTAssure transientInstance) {
		logger.debug("persisting TaTAssure instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTAssure persistentInstance) {
		logger.debug("removing TaTAssure instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTAssure()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTAssure merge(TaTAssure detachedInstance) {
		logger.debug("merging TaTAssure instance");
		try {
			TaTAssure result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTAssure findById(int id) {
		logger.debug("getting TaTAssure instance with id: " + id);
		try {
			TaTAssure instance = entityManager.find(TaTAssure.class, id);
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
	public List<TaTAssure> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTAssure> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTAssure entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTAssure> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTAssure> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTAssure> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTAssure> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTAssure value) throws Exception {
		BeanValidator<TaTAssure> validator = new BeanValidator<TaTAssure>(TaTAssure.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTAssure value, String propertyName) throws Exception {
		BeanValidator<TaTAssure> validator = new BeanValidator<TaTAssure>(TaTAssure.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTAssure transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTAssure findByCode(String code) {
		logger.debug("getting TaTAssure instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTAssure f where f.codeTAssure='"+code+"'");
				TaTAssure instance = (TaTAssure)query.getSingleResult();
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

