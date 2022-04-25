package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTCourtierDAO;
import fr.ylyade.courtage.model.TaTCourtier;


public class TaTCourtierDAO implements ITaTCourtierDAO {

	private static final Logger logger = Logger.getLogger(TaTCourtierDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTCourtier p";
	
	public TaTCourtierDAO(){
//		this(null);
	}

//	public TaTaTCourtierDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTCourtier.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTCourtier());
//	}


	public void persist(TaTCourtier transientInstance) {
		logger.debug("persisting TaTCourtier instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTCourtier persistentInstance) {
		logger.debug("removing TaTCourtier instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTCourtier()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTCourtier merge(TaTCourtier detachedInstance) {
		logger.debug("merging TaTCourtier instance");
		try {
			TaTCourtier result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTCourtier findById(int id) {
		logger.debug("getting TaTCourtier instance with id: " + id);
		try {
			TaTCourtier instance = entityManager.find(TaTCourtier.class, id);
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
	public List<TaTCourtier> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTCourtier> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTCourtier entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTCourtier> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTCourtier> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTCourtier> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTCourtier> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTCourtier value) throws Exception {
		BeanValidator<TaTCourtier> validator = new BeanValidator<TaTCourtier>(TaTCourtier.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTCourtier value, String propertyName) throws Exception {
		BeanValidator<TaTCourtier> validator = new BeanValidator<TaTCourtier>(TaTCourtier.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTCourtier transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTCourtier findByCode(String code) {
		logger.debug("getting TaTCourtier instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTCourtier f where f.codeTCourtier='"+code+"'");
				TaTCourtier instance = (TaTCourtier)query.getSingleResult();
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

