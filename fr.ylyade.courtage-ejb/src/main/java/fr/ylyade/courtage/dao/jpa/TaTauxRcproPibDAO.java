package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTauxRcproPibDAO;
import fr.ylyade.courtage.model.TaTauxRcproPib;


public class TaTauxRcproPibDAO implements ITaTauxRcproPibDAO {

	private static final Logger logger = Logger.getLogger(TaTauxRcproPibDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTauxRcproPib p";
	
	public TaTauxRcproPibDAO(){
//		this(null);
	}

//	public TaTaTauxRcproPibDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTauxRcproPib.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTauxRcproPib());
//	}


	public void persist(TaTauxRcproPib transientInstance) {
		logger.debug("persisting TaTauxRcproPib instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTauxRcproPib persistentInstance) {
		logger.debug("removing TaTauxRcproPib instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTauxRcproPib()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTauxRcproPib merge(TaTauxRcproPib detachedInstance) {
		logger.debug("merging TaTauxRcproPib instance");
		try {
			TaTauxRcproPib result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTauxRcproPib findById(int id) {
		logger.debug("getting TaTauxRcproPib instance with id: " + id);
		try {
			TaTauxRcproPib instance = entityManager.find(TaTauxRcproPib.class, id);
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
	public List<TaTauxRcproPib> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTauxRcproPib> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTauxRcproPib entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTauxRcproPib> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTauxRcproPib> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTauxRcproPib> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTauxRcproPib> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTauxRcproPib value) throws Exception {
		BeanValidator<TaTauxRcproPib> validator = new BeanValidator<TaTauxRcproPib>(TaTauxRcproPib.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTauxRcproPib value, String propertyName) throws Exception {
		BeanValidator<TaTauxRcproPib> validator = new BeanValidator<TaTauxRcproPib>(TaTauxRcproPib.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTauxRcproPib transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTauxRcproPib findByCode(String code) {
		logger.debug("getting TaTauxRcproPib instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTauxRcproPib f where f.codeTauxRcproPib='"+code+"'");
				TaTauxRcproPib instance = (TaTauxRcproPib)query.getSingleResult();
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

