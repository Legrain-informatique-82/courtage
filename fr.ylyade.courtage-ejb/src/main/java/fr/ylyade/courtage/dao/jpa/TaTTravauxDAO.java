package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTTravauxDAO;
import fr.ylyade.courtage.model.TaTTravaux;


public class TaTTravauxDAO implements ITaTTravauxDAO {

	private static final Logger logger = Logger.getLogger(TaTTravauxDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTTravaux p";
	
	public TaTTravauxDAO(){
//		this(null);
	}

//	public TaTaTTravauxDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTTravaux.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTTravaux());
//	}


	public void persist(TaTTravaux transientInstance) {
		logger.debug("persisting TaTTravaux instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTTravaux persistentInstance) {
		logger.debug("removing TaTTravaux instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTTravaux()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTTravaux merge(TaTTravaux detachedInstance) {
		logger.debug("merging TaTTravaux instance");
		try {
			TaTTravaux result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTTravaux findById(int id) {
		logger.debug("getting TaTTravaux instance with id: " + id);
		try {
			TaTTravaux instance = entityManager.find(TaTTravaux.class, id);
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
	public List<TaTTravaux> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTTravaux> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTTravaux entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTTravaux> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTTravaux> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTTravaux> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTTravaux> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTTravaux value) throws Exception {
		BeanValidator<TaTTravaux> validator = new BeanValidator<TaTTravaux>(TaTTravaux.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTTravaux value, String propertyName) throws Exception {
		BeanValidator<TaTTravaux> validator = new BeanValidator<TaTTravaux>(TaTTravaux.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTTravaux transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTTravaux findByCode(String code) {
		logger.debug("getting TaTTravaux instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTTravaux f where f.codeTTravaux='"+code+"'");
				TaTTravaux instance = (TaTTravaux)query.getSingleResult();
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

