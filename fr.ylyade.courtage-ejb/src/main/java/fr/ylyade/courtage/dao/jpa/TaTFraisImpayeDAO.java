package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTFraisImpayeDAO;
import fr.ylyade.courtage.model.TaTFraisImpaye;


public class TaTFraisImpayeDAO implements ITaTFraisImpayeDAO {

	private static final Logger logger = Logger.getLogger(TaTFraisImpayeDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTFraisImpaye p";
	
	public TaTFraisImpayeDAO(){
//		this(null);
	}

//	public TaTaTFraisImpayeDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTFraisImpaye.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTFraisImpaye());
//	}


	public void persist(TaTFraisImpaye transientInstance) {
		logger.debug("persisting TaTFraisImpaye instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTFraisImpaye persistentInstance) {
		logger.debug("removing TaTFraisImpaye instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTFraisImpaye()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTFraisImpaye merge(TaTFraisImpaye detachedInstance) {
		logger.debug("merging TaTFraisImpaye instance");
		try {
			TaTFraisImpaye result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTFraisImpaye findById(int id) {
		logger.debug("getting TaTFraisImpaye instance with id: " + id);
		try {
			TaTFraisImpaye instance = entityManager.find(TaTFraisImpaye.class, id);
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
	public List<TaTFraisImpaye> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTFraisImpaye> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTFraisImpaye entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTFraisImpaye> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTFraisImpaye> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTFraisImpaye> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTFraisImpaye> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTFraisImpaye value) throws Exception {
		BeanValidator<TaTFraisImpaye> validator = new BeanValidator<TaTFraisImpaye>(TaTFraisImpaye.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTFraisImpaye value, String propertyName) throws Exception {
		BeanValidator<TaTFraisImpaye> validator = new BeanValidator<TaTFraisImpaye>(TaTFraisImpaye.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTFraisImpaye transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTFraisImpaye findByCode(String code) {
		logger.debug("getting TaTFraisImpaye instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTFraisImpaye f where f.codeTFraisImpaye='"+code+"'");
				TaTFraisImpaye instance = (TaTFraisImpaye)query.getSingleResult();
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

