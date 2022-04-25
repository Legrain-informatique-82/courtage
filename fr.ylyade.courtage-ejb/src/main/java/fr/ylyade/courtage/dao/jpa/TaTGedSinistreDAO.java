package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTGedSinistreDAO;
import fr.ylyade.courtage.model.TaTGedSinistre;


public class TaTGedSinistreDAO implements ITaTGedSinistreDAO {

	private static final Logger logger = Logger.getLogger(TaTGedSinistreDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTGedSinistre p";
	
	public TaTGedSinistreDAO(){
//		this(null);
	}

//	public TaTaTGedSinistreDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTGedSinistre.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTGedSinistre());
//	}


	public void persist(TaTGedSinistre transientInstance) {
		logger.debug("persisting TaTGedSinistre instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTGedSinistre persistentInstance) {
		logger.debug("removing TaTGedSinistre instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTGedSinistre()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTGedSinistre merge(TaTGedSinistre detachedInstance) {
		logger.debug("merging TaTGedSinistre instance");
		try {
			TaTGedSinistre result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTGedSinistre findById(int id) {
		logger.debug("getting TaTGedSinistre instance with id: " + id);
		try {
			TaTGedSinistre instance = entityManager.find(TaTGedSinistre.class, id);
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
	public List<TaTGedSinistre> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTGedSinistre> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTGedSinistre entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTGedSinistre> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTGedSinistre> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTGedSinistre> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTGedSinistre> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTGedSinistre value) throws Exception {
		BeanValidator<TaTGedSinistre> validator = new BeanValidator<TaTGedSinistre>(TaTGedSinistre.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTGedSinistre value, String propertyName) throws Exception {
		BeanValidator<TaTGedSinistre> validator = new BeanValidator<TaTGedSinistre>(TaTGedSinistre.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTGedSinistre transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTGedSinistre findByCode(String code) {
		logger.debug("getting TaTGedSinistre instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTGedSinistre f where f.codeTGedSinistre='"+code+"'");
				TaTGedSinistre instance = (TaTGedSinistre)query.getSingleResult();
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

