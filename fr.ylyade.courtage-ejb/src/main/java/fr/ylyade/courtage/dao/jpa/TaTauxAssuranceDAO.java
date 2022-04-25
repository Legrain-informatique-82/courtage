package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTauxAssuranceDAO;
import fr.ylyade.courtage.model.TaTauxAssurance;


public class TaTauxAssuranceDAO implements ITaTauxAssuranceDAO {

	private static final Logger logger = Logger.getLogger(TaTauxAssuranceDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTauxAssurance p";
	
	public TaTauxAssuranceDAO(){
//		this(null);
	}

//	public TaTaTauxAssuranceDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTauxAssurance.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTauxAssurance());
//	}


	public void persist(TaTauxAssurance transientInstance) {
		logger.debug("persisting TaTauxAssurance instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTauxAssurance persistentInstance) {
		logger.debug("removing TaTauxAssurance instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTauxAssurance()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTauxAssurance merge(TaTauxAssurance detachedInstance) {
		logger.debug("merging TaTauxAssurance instance");
		try {
			TaTauxAssurance result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTauxAssurance findById(int id) {
		logger.debug("getting TaTauxAssurance instance with id: " + id);
		try {
			TaTauxAssurance instance = entityManager.find(TaTauxAssurance.class, id);
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
	public List<TaTauxAssurance> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTauxAssurance> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTauxAssurance entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTauxAssurance> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTauxAssurance> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTauxAssurance> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTauxAssurance> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTauxAssurance value) throws Exception {
		BeanValidator<TaTauxAssurance> validator = new BeanValidator<TaTauxAssurance>(TaTauxAssurance.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTauxAssurance value, String propertyName) throws Exception {
		BeanValidator<TaTauxAssurance> validator = new BeanValidator<TaTauxAssurance>(TaTauxAssurance.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTauxAssurance transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTauxAssurance findByCode(String code) {
		logger.debug("getting TaTauxAssurance instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTauxAssurance f where f.codeTauxAssurance='"+code+"'");
				TaTauxAssurance instance = (TaTauxAssurance)query.getSingleResult();
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

