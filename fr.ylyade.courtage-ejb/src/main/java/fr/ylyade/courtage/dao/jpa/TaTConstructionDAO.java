package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTConstructionDAO;
import fr.ylyade.courtage.model.TaTConstruction;


public class TaTConstructionDAO implements ITaTConstructionDAO {

	private static final Logger logger = Logger.getLogger(TaTConstructionDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTConstruction p";
	
	public TaTConstructionDAO(){
//		this(null);
	}

//	public TaTaTConstructionDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTConstruction.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTConstruction());
//	}


	public void persist(TaTConstruction transientInstance) {
		logger.debug("persisting TaTConstruction instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTConstruction persistentInstance) {
		logger.debug("removing TaTConstruction instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTConstruction()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTConstruction merge(TaTConstruction detachedInstance) {
		logger.debug("merging TaTConstruction instance");
		try {
			TaTConstruction result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTConstruction findById(int id) {
		logger.debug("getting TaTConstruction instance with id: " + id);
		try {
			TaTConstruction instance = entityManager.find(TaTConstruction.class, id);
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
	public List<TaTConstruction> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTConstruction> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTConstruction entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTConstruction> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTConstruction> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTConstruction> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTConstruction> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTConstruction value) throws Exception {
		BeanValidator<TaTConstruction> validator = new BeanValidator<TaTConstruction>(TaTConstruction.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTConstruction value, String propertyName) throws Exception {
		BeanValidator<TaTConstruction> validator = new BeanValidator<TaTConstruction>(TaTConstruction.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTConstruction transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTConstruction findByCode(String code) {
		logger.debug("getting TaTConstruction instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTConstruction f where f.codeConstruction='"+code+"'");
				TaTConstruction instance = (TaTConstruction)query.getSingleResult();
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

