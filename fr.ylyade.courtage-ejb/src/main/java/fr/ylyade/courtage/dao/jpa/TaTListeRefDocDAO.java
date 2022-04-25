package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTListeRefDocDAO;
import fr.ylyade.courtage.model.TaTListeRefDoc;


public class TaTListeRefDocDAO implements ITaTListeRefDocDAO {

	private static final Logger logger = Logger.getLogger(TaTListeRefDocDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTListeRefDoc p";
	
	public TaTListeRefDocDAO(){
//		this(null);
	}

//	public TaTaTListeRefDocDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTListeRefDoc.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTListeRefDoc());
//	}


	public void persist(TaTListeRefDoc transientInstance) {
		logger.debug("persisting TaTListeRefDoc instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTListeRefDoc persistentInstance) {
		logger.debug("removing TaTListeRefDoc instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTListeRefDoc()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTListeRefDoc merge(TaTListeRefDoc detachedInstance) {
		logger.debug("merging TaTListeRefDoc instance");
		try {
			TaTListeRefDoc result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTListeRefDoc findById(int id) {
		logger.debug("getting TaTListeRefDoc instance with id: " + id);
		try {
			TaTListeRefDoc instance = entityManager.find(TaTListeRefDoc.class, id);
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
	public List<TaTListeRefDoc> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTListeRefDoc> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTListeRefDoc entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTListeRefDoc> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTListeRefDoc> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTListeRefDoc> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTListeRefDoc> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTListeRefDoc value) throws Exception {
		BeanValidator<TaTListeRefDoc> validator = new BeanValidator<TaTListeRefDoc>(TaTListeRefDoc.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTListeRefDoc value, String propertyName) throws Exception {
		BeanValidator<TaTListeRefDoc> validator = new BeanValidator<TaTListeRefDoc>(TaTListeRefDoc.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTListeRefDoc transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTListeRefDoc findByCode(String code) {
		logger.debug("getting TaTListeRefDoc instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTListeRefDoc f where f.codeTListeRefDoc='"+code+"'");
				TaTListeRefDoc instance = (TaTListeRefDoc)query.getSingleResult();
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

