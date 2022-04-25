package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaSinistreDAO;
import fr.ylyade.courtage.model.TaSinistre;


public class TaSinistreDAO implements ITaSinistreDAO {

	private static final Logger logger = Logger.getLogger(TaSinistreDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaSinistre p";
	
	public TaSinistreDAO(){
//		this(null);
	}

//	public TaTaSinistreDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaSinistre.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaSinistre());
//	}


	public void persist(TaSinistre transientInstance) {
		logger.debug("persisting TaSinistre instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaSinistre persistentInstance) {
		logger.debug("removing TaSinistre instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdSinistre()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaSinistre merge(TaSinistre detachedInstance) {
		logger.debug("merging TaSinistre instance");
		try {
			TaSinistre result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaSinistre findById(int id) {
		logger.debug("getting TaSinistre instance with id: " + id);
		try {
			TaSinistre instance = entityManager.find(TaSinistre.class, id);
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
	public List<TaSinistre> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaSinistre> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaSinistre entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaSinistre> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaSinistre> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaSinistre> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaSinistre> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaSinistre value) throws Exception {
		BeanValidator<TaSinistre> validator = new BeanValidator<TaSinistre>(TaSinistre.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaSinistre value, String propertyName) throws Exception {
		BeanValidator<TaSinistre> validator = new BeanValidator<TaSinistre>(TaSinistre.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaSinistre transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaSinistre findByCode(String code) {
		logger.debug("getting TaSinistre instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaSinistre f where f.refSinistre='"+code+"'");
				TaSinistre instance = (TaSinistre)query.getSingleResult();
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

