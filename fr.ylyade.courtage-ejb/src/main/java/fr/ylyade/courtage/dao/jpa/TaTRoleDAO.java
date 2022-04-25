package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTRoleDAO;
import fr.ylyade.courtage.model.TaTRole;


public class TaTRoleDAO implements ITaTRoleDAO {

	private static final Logger logger = Logger.getLogger(TaTRoleDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTRole p";
	
	public TaTRoleDAO(){
//		this(null);
	}

//	public TaTaTRoleDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTRole.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTRole());
//	}


	public void persist(TaTRole transientInstance) {
		logger.debug("persisting TaTRole instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTRole persistentInstance) {
		logger.debug("removing TaTRole instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTRole()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTRole merge(TaTRole detachedInstance) {
		logger.debug("merging TaTRole instance");
		try {
			TaTRole result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTRole findById(int id) {
		logger.debug("getting TaTRole instance with id: " + id);
		try {
			TaTRole instance = entityManager.find(TaTRole.class, id);
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
	public List<TaTRole> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTRole> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTRole entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTRole> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTRole> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTRole> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTRole> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTRole value) throws Exception {
		BeanValidator<TaTRole> validator = new BeanValidator<TaTRole>(TaTRole.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTRole value, String propertyName) throws Exception {
		BeanValidator<TaTRole> validator = new BeanValidator<TaTRole>(TaTRole.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTRole transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTRole findByCode(String code) {
		logger.debug("getting TaTRole instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTRole f where f.codeTRole='"+code+"'");
				TaTRole instance = (TaTRole)query.getSingleResult();
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

