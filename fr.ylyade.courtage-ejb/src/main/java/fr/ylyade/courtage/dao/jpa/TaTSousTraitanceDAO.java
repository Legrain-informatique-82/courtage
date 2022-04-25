package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTSousTraitanceDAO;
import fr.ylyade.courtage.model.TaTSousTraitance;


public class TaTSousTraitanceDAO implements ITaTSousTraitanceDAO {

	private static final Logger logger = Logger.getLogger(TaTSousTraitanceDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTSousTraitance p";
	
	public TaTSousTraitanceDAO(){
//		this(null);
	}

//	public TaTaTSousTraitanceDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTSousTraitance.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTSousTraitance());
//	}


	public void persist(TaTSousTraitance transientInstance) {
		logger.debug("persisting TaTSousTraitance instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTSousTraitance persistentInstance) {
		logger.debug("removing TaTSousTraitance instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTSousTraitance()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTSousTraitance merge(TaTSousTraitance detachedInstance) {
		logger.debug("merging TaTSousTraitance instance");
		try {
			TaTSousTraitance result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTSousTraitance findById(int id) {
		logger.debug("getting TaTSousTraitance instance with id: " + id);
		try {
			TaTSousTraitance instance = entityManager.find(TaTSousTraitance.class, id);
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
	public List<TaTSousTraitance> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTSousTraitance> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTSousTraitance entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTSousTraitance> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTSousTraitance> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTSousTraitance> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTSousTraitance> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTSousTraitance value) throws Exception {
		BeanValidator<TaTSousTraitance> validator = new BeanValidator<TaTSousTraitance>(TaTSousTraitance.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTSousTraitance value, String propertyName) throws Exception {
		BeanValidator<TaTSousTraitance> validator = new BeanValidator<TaTSousTraitance>(TaTSousTraitance.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTSousTraitance transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTSousTraitance findByCode(String code) {
		logger.debug("getting TaTSousTraitance instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTSousTraitance f where f.codeTSousTraitance='"+code+"'");
				TaTSousTraitance instance = (TaTSousTraitance)query.getSingleResult();
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

