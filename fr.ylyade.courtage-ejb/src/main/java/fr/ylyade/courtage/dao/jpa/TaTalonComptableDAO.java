package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTalonComptableDAO;
import fr.ylyade.courtage.model.TaTalonComptable;


public class TaTalonComptableDAO implements ITaTalonComptableDAO {

	private static final Logger logger = Logger.getLogger(TaTalonComptableDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTalonComptable p";
	
	public TaTalonComptableDAO(){
//		this(null);
	}

//	public TaTaTalonComptableDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTalonComptable.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTalonComptable());
//	}


	public void persist(TaTalonComptable transientInstance) {
		logger.debug("persisting TaTalonComptable instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTalonComptable persistentInstance) {
		logger.debug("removing TaTalonComptable instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTalonComptable()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTalonComptable merge(TaTalonComptable detachedInstance) {
		logger.debug("merging TaTalonComptable instance");
		try {
			TaTalonComptable result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTalonComptable findById(int id) {
		logger.debug("getting TaTalonComptable instance with id: " + id);
		try {
			TaTalonComptable instance = entityManager.find(TaTalonComptable.class, id);
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
	public List<TaTalonComptable> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTalonComptable> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTalonComptable entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTalonComptable> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTalonComptable> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTalonComptable> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTalonComptable> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTalonComptable value) throws Exception {
		BeanValidator<TaTalonComptable> validator = new BeanValidator<TaTalonComptable>(TaTalonComptable.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTalonComptable value, String propertyName) throws Exception {
		BeanValidator<TaTalonComptable> validator = new BeanValidator<TaTalonComptable>(TaTalonComptable.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTalonComptable transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTalonComptable findByCode(String code) {
		logger.debug("getting TaTalonComptable instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTalonComptable f where f.codePrestation='"+code+"'");
				TaTalonComptable instance = (TaTalonComptable)query.getSingleResult();
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

