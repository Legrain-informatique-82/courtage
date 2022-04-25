package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaHistoriquePrestationDAO;
import fr.ylyade.courtage.model.TaHistoriquePrestation;


public class TaHistoriquePrestationDAO implements ITaHistoriquePrestationDAO {

	private static final Logger logger = Logger.getLogger(TaHistoriquePrestationDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaHistoriquePrestation p";
	
	public TaHistoriquePrestationDAO(){
//		this(null);
	}

//	public TaTaHistoriquePrestationDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaHistoriquePrestation.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaHistoriquePrestation());
//	}


	public void persist(TaHistoriquePrestation transientInstance) {
		logger.debug("persisting TaHistoriquePrestation instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaHistoriquePrestation persistentInstance) {
		logger.debug("removing TaHistoriquePrestation instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdHistoriquePrestation()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaHistoriquePrestation merge(TaHistoriquePrestation detachedInstance) {
		logger.debug("merging TaHistoriquePrestation instance");
		try {
			TaHistoriquePrestation result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaHistoriquePrestation findById(int id) {
		logger.debug("getting TaHistoriquePrestation instance with id: " + id);
		try {
			TaHistoriquePrestation instance = entityManager.find(TaHistoriquePrestation.class, id);
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
	public List<TaHistoriquePrestation> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaHistoriquePrestation> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaHistoriquePrestation entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaHistoriquePrestation> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaHistoriquePrestation> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaHistoriquePrestation> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaHistoriquePrestation> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaHistoriquePrestation value) throws Exception {
		BeanValidator<TaHistoriquePrestation> validator = new BeanValidator<TaHistoriquePrestation>(TaHistoriquePrestation.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaHistoriquePrestation value, String propertyName) throws Exception {
		BeanValidator<TaHistoriquePrestation> validator = new BeanValidator<TaHistoriquePrestation>(TaHistoriquePrestation.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaHistoriquePrestation transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaHistoriquePrestation findByCode(String code) {
		logger.debug("getting TaHistoriquePrestation instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaHistoriquePrestation f where f.identifiant='"+code+"'");
				TaHistoriquePrestation instance = (TaHistoriquePrestation)query.getSingleResult();
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

