package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTReglementDAO;
import fr.ylyade.courtage.model.TaTReglement;


public class TaTReglementDAO implements ITaTReglementDAO {

	private static final Logger logger = Logger.getLogger(TaTReglementDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTReglement p";
	
	public TaTReglementDAO(){
//		this(null);
	}

//	public TaTaTReglementDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTReglement.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTReglement());
//	}


	public void persist(TaTReglement transientInstance) {
		logger.debug("persisting TaTReglement instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTReglement persistentInstance) {
		logger.debug("removing TaTReglement instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTReglement()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTReglement merge(TaTReglement detachedInstance) {
		logger.debug("merging TaTReglement instance");
		try {
			TaTReglement result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTReglement findById(int id) {
		logger.debug("getting TaTReglement instance with id: " + id);
		try {
			TaTReglement instance = entityManager.find(TaTReglement.class, id);
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
	public List<TaTReglement> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTReglement> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTReglement entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTReglement> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTReglement> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTReglement> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTReglement> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTReglement value) throws Exception {
		BeanValidator<TaTReglement> validator = new BeanValidator<TaTReglement>(TaTReglement.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTReglement value, String propertyName) throws Exception {
		BeanValidator<TaTReglement> validator = new BeanValidator<TaTReglement>(TaTReglement.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTReglement transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTReglement findByCode(String code) {
		logger.debug("getting TaTReglement instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTReglement f where f.codeTReglement='"+code+"'");
				TaTReglement instance = (TaTReglement)query.getSingleResult();
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

