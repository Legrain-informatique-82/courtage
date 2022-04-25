package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTFranchiseDAO;
import fr.ylyade.courtage.model.TaTFranchise;


public class TaTFranchiseDAO implements ITaTFranchiseDAO {

	private static final Logger logger = Logger.getLogger(TaTFranchiseDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTFranchise p";
	
	public TaTFranchiseDAO(){
//		this(null);
	}

//	public TaTaTFranchiseDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTFranchise.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTFranchise());
//	}


	public void persist(TaTFranchise transientInstance) {
		logger.debug("persisting TaTFranchise instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTFranchise persistentInstance) {
		logger.debug("removing TaTFranchise instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTFranchise()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTFranchise merge(TaTFranchise detachedInstance) {
		logger.debug("merging TaTFranchise instance");
		try {
			TaTFranchise result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTFranchise findById(int id) {
		logger.debug("getting TaTFranchise instance with id: " + id);
		try {
			TaTFranchise instance = entityManager.find(TaTFranchise.class, id);
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
	public List<TaTFranchise> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTFranchise> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTFranchise entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTFranchise> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTFranchise> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTFranchise> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTFranchise> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTFranchise value) throws Exception {
		BeanValidator<TaTFranchise> validator = new BeanValidator<TaTFranchise>(TaTFranchise.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTFranchise value, String propertyName) throws Exception {
		BeanValidator<TaTFranchise> validator = new BeanValidator<TaTFranchise>(TaTFranchise.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTFranchise transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTFranchise findByCode(String code) {
		logger.debug("getting TaTFranchise instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTFranchise f where f.codeTFranchise='"+code+"'");
				TaTFranchise instance = (TaTFranchise)query.getSingleResult();
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

