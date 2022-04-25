package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTarifPrestationDAO;
import fr.ylyade.courtage.model.TaTarifPrestation;


public class TaTarifPrestationDAO implements ITaTarifPrestationDAO {

	private static final Logger logger = Logger.getLogger(TaTarifPrestationDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTarifPrestation p";
	
	public TaTarifPrestationDAO(){
//		this(null);
	}

//	public TaTaTarifPrestationDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTarifPrestation.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTarifPrestation());
//	}


	public void persist(TaTarifPrestation transientInstance) {
		logger.debug("persisting TaTarifPrestation instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTarifPrestation persistentInstance) {
		logger.debug("removing TaTarifPrestation instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTarifPrestation()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTarifPrestation merge(TaTarifPrestation detachedInstance) {
		logger.debug("merging TaTarifPrestation instance");
		try {
			TaTarifPrestation result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTarifPrestation findById(int id) {
		logger.debug("getting TaTarifPrestation instance with id: " + id);
		try {
			TaTarifPrestation instance = entityManager.find(TaTarifPrestation.class, id);
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
	public List<TaTarifPrestation> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTarifPrestation> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTarifPrestation entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTarifPrestation> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTarifPrestation> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTarifPrestation> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTarifPrestation> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTarifPrestation value) throws Exception {
		BeanValidator<TaTarifPrestation> validator = new BeanValidator<TaTarifPrestation>(TaTarifPrestation.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTarifPrestation value, String propertyName) throws Exception {
		BeanValidator<TaTarifPrestation> validator = new BeanValidator<TaTarifPrestation>(TaTarifPrestation.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTarifPrestation transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTarifPrestation findByCode(String code) {
		logger.debug("getting TaTarifPrestation instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTarifPrestation f where f.codeTarifPrestation='"+code+"'");
				TaTarifPrestation instance = (TaTarifPrestation)query.getSingleResult();
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

