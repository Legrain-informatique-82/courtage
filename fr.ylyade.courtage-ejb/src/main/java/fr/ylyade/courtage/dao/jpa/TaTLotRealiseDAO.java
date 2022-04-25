package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTLotRealiseDAO;
import fr.ylyade.courtage.model.TaTLotRealise;


public class TaTLotRealiseDAO implements ITaTLotRealiseDAO {

	private static final Logger logger = Logger.getLogger(TaTLotRealiseDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTLotRealise p";
	
	public TaTLotRealiseDAO(){
//		this(null);
	}

//	public TaTaTLotRealiseDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTLotRealise.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTLotRealise());
//	}


	public void persist(TaTLotRealise transientInstance) {
		logger.debug("persisting TaTLotRealise instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTLotRealise persistentInstance) {
		logger.debug("removing TaTLotRealise instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdLotRealise()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTLotRealise merge(TaTLotRealise detachedInstance) {
		logger.debug("merging TaTLotRealise instance");
		try {
			TaTLotRealise result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTLotRealise findById(int id) {
		logger.debug("getting TaTLotRealise instance with id: " + id);
		try {
			TaTLotRealise instance = entityManager.find(TaTLotRealise.class, id);
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
	public List<TaTLotRealise> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTLotRealise> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTLotRealise entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTLotRealise> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTLotRealise> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTLotRealise> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTLotRealise> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTLotRealise value) throws Exception {
		BeanValidator<TaTLotRealise> validator = new BeanValidator<TaTLotRealise>(TaTLotRealise.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTLotRealise value, String propertyName) throws Exception {
		BeanValidator<TaTLotRealise> validator = new BeanValidator<TaTLotRealise>(TaTLotRealise.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTLotRealise transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTLotRealise findByCode(String code) {
		logger.debug("getting TaTLotRealise instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTLotRealise f where f.codeLotRealise='"+code+"'");
				TaTLotRealise instance = (TaTLotRealise)query.getSingleResult();
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

