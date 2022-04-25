package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTActionDocDAO;
import fr.ylyade.courtage.model.TaTActionDoc;


public class TaTActionDocDAO implements ITaTActionDocDAO {

	private static final Logger logger = Logger.getLogger(TaTActionDocDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTActionDoc p";
	
	public TaTActionDocDAO(){
//		this(null);
	}

//	public TaTaTActionDocDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTActionDoc.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTActionDoc());
//	}


	public void persist(TaTActionDoc transientInstance) {
		logger.debug("persisting TaTActionDoc instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTActionDoc persistentInstance) {
		logger.debug("removing TaTActionDoc instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTActionDoc()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTActionDoc merge(TaTActionDoc detachedInstance) {
		logger.debug("merging TaTActionDoc instance");
		try {
			TaTActionDoc result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTActionDoc findById(int id) {
		logger.debug("getting TaTActionDoc instance with id: " + id);
		try {
			TaTActionDoc instance = entityManager.find(TaTActionDoc.class, id);
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
	public List<TaTActionDoc> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTActionDoc> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTActionDoc entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTActionDoc> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTActionDoc> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTActionDoc> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTActionDoc> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTActionDoc value) throws Exception {
		BeanValidator<TaTActionDoc> validator = new BeanValidator<TaTActionDoc>(TaTActionDoc.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTActionDoc value, String propertyName) throws Exception {
		BeanValidator<TaTActionDoc> validator = new BeanValidator<TaTActionDoc>(TaTActionDoc.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTActionDoc transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTActionDoc findByCode(String code) {
		logger.debug("getting TaTActionDoc instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTActionDoc f where f.codeTAction='"+code+"'");
				TaTActionDoc instance = (TaTActionDoc)query.getSingleResult();
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

