package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaEmailDAO;
import fr.ylyade.courtage.model.TaEmail;


public class TaEmailDAO implements ITaEmailDAO {

	private static final Logger logger = Logger.getLogger(TaEmailDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaEmail p";
	
	public TaEmailDAO(){
//		this(null);
	}

//	public TaTaEmailDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaEmail.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaEmail());
//	}


	public void persist(TaEmail transientInstance) {
		logger.debug("persisting TaEmail instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaEmail persistentInstance) {
		logger.debug("removing TaEmail instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdEmail()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaEmail merge(TaEmail detachedInstance) {
		logger.debug("merging TaEmail instance");
		try {
			TaEmail result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaEmail findById(int id) {
		logger.debug("getting TaEmail instance with id: " + id);
		try {
			TaEmail instance = entityManager.find(TaEmail.class, id);
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
	public List<TaEmail> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaEmail> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaEmail entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaEmail> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaEmail> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaEmail> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaEmail> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaEmail value) throws Exception {
		BeanValidator<TaEmail> validator = new BeanValidator<TaEmail>(TaEmail.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaEmail value, String propertyName) throws Exception {
		BeanValidator<TaEmail> validator = new BeanValidator<TaEmail>(TaEmail.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaEmail transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaEmail findByCode(String code) {
		logger.debug("getting TaEmail instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaEmail f where f.identifiant='"+code+"'");
				TaEmail instance = (TaEmail)query.getSingleResult();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			return null;
		}
	}
	
	public TaEmail findByAdresseEmail(String adresse) {
		logger.debug("getting TaEmail instance with username: " + adresse);
		try {
			if(!adresse.equals("")){
				Query query = entityManager.createQuery("select f from TaEmail f where f.adresseEmail='"+adresse+"'");
				TaEmail instance = (TaEmail)query.getSingleResult();
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

