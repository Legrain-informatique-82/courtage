package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaGedSinistreDAO;
import fr.ylyade.courtage.model.TaGedSinistre;


public class TaGedSinistreDAO implements ITaGedSinistreDAO {

	private static final Logger logger = Logger.getLogger(TaGedSinistreDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaGedSinistre p";
	
	public TaGedSinistreDAO(){
//		this(null);
	}

//	public TaTaGedSinistreDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaGedSinistre.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaGedSinistre());
//	}


	public void persist(TaGedSinistre transientInstance) {
		logger.debug("persisting TaGedSinistre instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaGedSinistre persistentInstance) {
		logger.debug("removing TaGedSinistre instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdGedSinistre()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaGedSinistre merge(TaGedSinistre detachedInstance) {
		logger.debug("merging TaGedSinistre instance");
		try {
			TaGedSinistre result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaGedSinistre findById(int id) {
		logger.debug("getting TaGedSinistre instance with id: " + id);
		try {
			TaGedSinistre instance = entityManager.find(TaGedSinistre.class, id);
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
	public List<TaGedSinistre> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaGedSinistre> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaGedSinistre entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaGedSinistre> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaGedSinistre> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaGedSinistre> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaGedSinistre> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaGedSinistre value) throws Exception {
		BeanValidator<TaGedSinistre> validator = new BeanValidator<TaGedSinistre>(TaGedSinistre.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaGedSinistre value, String propertyName) throws Exception {
		BeanValidator<TaGedSinistre> validator = new BeanValidator<TaGedSinistre>(TaGedSinistre.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaGedSinistre transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaGedSinistre findByCode(String code) {
		logger.debug("getting TaGedSinistre instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaGedSinistre f where f.identifiant='"+code+"'");
				TaGedSinistre instance = (TaGedSinistre)query.getSingleResult();
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

