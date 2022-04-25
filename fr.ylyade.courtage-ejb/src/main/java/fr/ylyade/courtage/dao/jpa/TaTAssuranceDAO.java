package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTAssuranceDAO;
import fr.ylyade.courtage.model.TaTAssurance;


public class TaTAssuranceDAO implements ITaTAssuranceDAO {

	private static final Logger logger = Logger.getLogger(TaTAssuranceDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTAssurance p";
	
	public TaTAssuranceDAO(){
//		this(null);
	}

//	public TaTaTAssuranceDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTAssurance.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTAssurance());
//	}


	public void persist(TaTAssurance transientInstance) {
		logger.debug("persisting TaTAssurance instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTAssurance persistentInstance) {
		logger.debug("removing TaTAssurance instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTAssurance()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTAssurance merge(TaTAssurance detachedInstance) {
		logger.debug("merging TaTAssurance instance");
		try {
			TaTAssurance result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTAssurance findById(int id) {
		logger.debug("getting TaTAssurance instance with id: " + id);
		try {
			TaTAssurance instance = entityManager.find(TaTAssurance.class, id);
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
	public List<TaTAssurance> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTAssurance> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTAssurance entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTAssurance> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTAssurance> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTAssurance> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTAssurance> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTAssurance value) throws Exception {
		BeanValidator<TaTAssurance> validator = new BeanValidator<TaTAssurance>(TaTAssurance.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTAssurance value, String propertyName) throws Exception {
		BeanValidator<TaTAssurance> validator = new BeanValidator<TaTAssurance>(TaTAssurance.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTAssurance transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTAssurance findByCode(String code) {
		logger.debug("getting TaTAssurance instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTAssurance f where f.codeTAssurance='"+code+"'");
				TaTAssurance instance = (TaTAssurance)query.getSingleResult();
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

