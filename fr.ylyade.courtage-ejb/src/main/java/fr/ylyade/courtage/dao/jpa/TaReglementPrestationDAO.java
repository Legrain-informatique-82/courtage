package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaReglementPrestationDAO;
import fr.ylyade.courtage.model.TaReglementPrestation;


public class TaReglementPrestationDAO implements ITaReglementPrestationDAO {

	private static final Logger logger = Logger.getLogger(TaReglementPrestationDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaReglementPrestation p";
	
	public TaReglementPrestationDAO(){
//		this(null);
	}

//	public TaTaReglementPrestationDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaReglementPrestation.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaReglementPrestation());
//	}


	public void persist(TaReglementPrestation transientInstance) {
		logger.debug("persisting TaReglementPrestation instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaReglementPrestation persistentInstance) {
		logger.debug("removing TaReglementPrestation instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdReglementPrestation()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaReglementPrestation merge(TaReglementPrestation detachedInstance) {
		logger.debug("merging TaReglementPrestation instance");
		try {
			TaReglementPrestation result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaReglementPrestation findById(int id) {
		logger.debug("getting TaReglementPrestation instance with id: " + id);
		try {
			TaReglementPrestation instance = entityManager.find(TaReglementPrestation.class, id);
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
	public List<TaReglementPrestation> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaReglementPrestation> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaReglementPrestation entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaReglementPrestation> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaReglementPrestation> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaReglementPrestation> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaReglementPrestation> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaReglementPrestation value) throws Exception {
		BeanValidator<TaReglementPrestation> validator = new BeanValidator<TaReglementPrestation>(TaReglementPrestation.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaReglementPrestation value, String propertyName) throws Exception {
		BeanValidator<TaReglementPrestation> validator = new BeanValidator<TaReglementPrestation>(TaReglementPrestation.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaReglementPrestation transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaReglementPrestation findByCode(String code) {
		logger.debug("getting TaReglementPrestation instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaReglementPrestation f where f.refReglement='"+code+"'");
				TaReglementPrestation instance = (TaReglementPrestation)query.getSingleResult();
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

