package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTCiviliteDAO;
import fr.ylyade.courtage.model.TaTCivilite;


public class TaTCiviliteDAO implements ITaTCiviliteDAO {

	private static final Logger logger = Logger.getLogger(TaTCiviliteDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTCivilite p";
	
	public TaTCiviliteDAO(){
//		this(null);
	}

//	public TaTaTCiviliteDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTCivilite.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTCivilite());
//	}


	public void persist(TaTCivilite transientInstance) {
		logger.debug("persisting TaTCivilite instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTCivilite persistentInstance) {
		logger.debug("removing TaTCivilite instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTCivilite()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTCivilite merge(TaTCivilite detachedInstance) {
		logger.debug("merging TaTCivilite instance");
		try {
			TaTCivilite result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTCivilite findById(int id) {
		logger.debug("getting TaTCivilite instance with id: " + id);
		try {
			TaTCivilite instance = entityManager.find(TaTCivilite.class, id);
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
	public List<TaTCivilite> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTCivilite> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTCivilite entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTCivilite> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTCivilite> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTCivilite> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTCivilite> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTCivilite value) throws Exception {
		BeanValidator<TaTCivilite> validator = new BeanValidator<TaTCivilite>(TaTCivilite.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTCivilite value, String propertyName) throws Exception {
		BeanValidator<TaTCivilite> validator = new BeanValidator<TaTCivilite>(TaTCivilite.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTCivilite transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTCivilite findByCode(String code) {
		logger.debug("getting TaTCivilite instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTCivilite f where f.codeTCivilite='"+code+"'");
				TaTCivilite instance = (TaTCivilite)query.getSingleResult();
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

