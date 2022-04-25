package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTEcheanceDAO;
import fr.ylyade.courtage.model.TaTEcheance;


public class TaTEcheanceDAO implements ITaTEcheanceDAO {

	private static final Logger logger = Logger.getLogger(TaTEcheanceDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTEcheance p";
	
	public TaTEcheanceDAO(){
//		this(null);
	}

//	public TaTaTEcheanceDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTEcheance.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTEcheance());
//	}


	public void persist(TaTEcheance transientInstance) {
		logger.debug("persisting TaTEcheance instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTEcheance persistentInstance) {
		logger.debug("removing TaTEcheance instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTEcheance()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTEcheance merge(TaTEcheance detachedInstance) {
		logger.debug("merging TaTEcheance instance");
		try {
			TaTEcheance result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTEcheance findById(int id) {
		logger.debug("getting TaTEcheance instance with id: " + id);
		try {
			TaTEcheance instance = entityManager.find(TaTEcheance.class, id);
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
	public List<TaTEcheance> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTEcheance> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTEcheance entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTEcheance> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTEcheance> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTEcheance> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTEcheance> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTEcheance value) throws Exception {
		BeanValidator<TaTEcheance> validator = new BeanValidator<TaTEcheance>(TaTEcheance.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTEcheance value, String propertyName) throws Exception {
		BeanValidator<TaTEcheance> validator = new BeanValidator<TaTEcheance>(TaTEcheance.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTEcheance transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTEcheance findByCode(String code) {
		logger.debug("getting TaTEcheance instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTEcheance f where f.codeTEcheance='"+code+"'");
				TaTEcheance instance = (TaTEcheance)query.getSingleResult();
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

