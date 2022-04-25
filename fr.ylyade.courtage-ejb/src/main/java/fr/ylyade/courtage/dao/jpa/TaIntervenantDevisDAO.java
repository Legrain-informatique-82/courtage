package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaIntervenantDevisDAO;
import fr.ylyade.courtage.model.TaIntervenantDevis;


public class TaIntervenantDevisDAO implements ITaIntervenantDevisDAO {

	private static final Logger logger = Logger.getLogger(TaIntervenantDevisDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaIntervenantDevis p";
	
	public TaIntervenantDevisDAO(){
//		this(null);
	}

//	public TaTaIntervenantDevisDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaIntervenantDevis.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaIntervenantDevis());
//	}


	public void persist(TaIntervenantDevis transientInstance) {
		logger.debug("persisting TaIntervenantDevis instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaIntervenantDevis persistentInstance) {
		logger.debug("removing TaIntervenantDevis instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdIntervenantDevis()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaIntervenantDevis merge(TaIntervenantDevis detachedInstance) {
		logger.debug("merging TaIntervenantDevis instance");
		try {
			TaIntervenantDevis result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaIntervenantDevis findById(int id) {
		logger.debug("getting TaIntervenantDevis instance with id: " + id);
		try {
			TaIntervenantDevis instance = entityManager.find(TaIntervenantDevis.class, id);
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
	public List<TaIntervenantDevis> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaIntervenantDevis> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaIntervenantDevis entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaIntervenantDevis> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaIntervenantDevis> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaIntervenantDevis> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaIntervenantDevis> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaIntervenantDevis value) throws Exception {
		BeanValidator<TaIntervenantDevis> validator = new BeanValidator<TaIntervenantDevis>(TaIntervenantDevis.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaIntervenantDevis value, String propertyName) throws Exception {
		BeanValidator<TaIntervenantDevis> validator = new BeanValidator<TaIntervenantDevis>(TaIntervenantDevis.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaIntervenantDevis transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaIntervenantDevis findByCode(String code) {
		logger.debug("getting TaIntervenantDevis instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaIntervenantDevis f where f.identifiant='"+code+"'");
				TaIntervenantDevis instance = (TaIntervenantDevis)query.getSingleResult();
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

