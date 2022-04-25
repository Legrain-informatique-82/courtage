package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTGarantieSinistreDAO;
import fr.ylyade.courtage.model.TaTGarantieSinistre;


public class TaTGarantieSinistreDAO implements ITaTGarantieSinistreDAO {

	private static final Logger logger = Logger.getLogger(TaTGarantieSinistreDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTGarantieSinistre p";
	
	public TaTGarantieSinistreDAO(){
//		this(null);
	}

//	public TaTaTGarantieSinistreDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTGarantieSinistre.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTGarantieSinistre());
//	}


	public void persist(TaTGarantieSinistre transientInstance) {
		logger.debug("persisting TaTGarantieSinistre instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTGarantieSinistre persistentInstance) {
		logger.debug("removing TaTGarantieSinistre instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTGarantieSinistre()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTGarantieSinistre merge(TaTGarantieSinistre detachedInstance) {
		logger.debug("merging TaTGarantieSinistre instance");
		try {
			TaTGarantieSinistre result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTGarantieSinistre findById(int id) {
		logger.debug("getting TaTGarantieSinistre instance with id: " + id);
		try {
			TaTGarantieSinistre instance = entityManager.find(TaTGarantieSinistre.class, id);
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
	public List<TaTGarantieSinistre> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTGarantieSinistre> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTGarantieSinistre entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTGarantieSinistre> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTGarantieSinistre> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTGarantieSinistre> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTGarantieSinistre> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTGarantieSinistre value) throws Exception {
		BeanValidator<TaTGarantieSinistre> validator = new BeanValidator<TaTGarantieSinistre>(TaTGarantieSinistre.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTGarantieSinistre value, String propertyName) throws Exception {
		BeanValidator<TaTGarantieSinistre> validator = new BeanValidator<TaTGarantieSinistre>(TaTGarantieSinistre.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTGarantieSinistre transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTGarantieSinistre findByCode(String code) {
		logger.debug("getting TaTGarantieSinistre instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTGarantieSinistre f where f.codeTGarantieSinistre='"+code+"'");
				TaTGarantieSinistre instance = (TaTGarantieSinistre)query.getSingleResult();
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

