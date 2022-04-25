package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaSinistreAntecedentDAO;
import fr.ylyade.courtage.model.TaSinistreAntecedent;


public class TaSinistreAntecedentDAO implements ITaSinistreAntecedentDAO {

	private static final Logger logger = Logger.getLogger(TaSinistreAntecedentDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaSinistreAntecedent p";
	
	public TaSinistreAntecedentDAO(){
//		this(null);
	}

//	public TaTaSinistreAntecedentDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaSinistreAntecedent.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaSinistreAntecedent());
//	}


	public void persist(TaSinistreAntecedent transientInstance) {
		logger.debug("persisting TaSinistreAntecedent instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaSinistreAntecedent persistentInstance) {
		logger.debug("removing TaSinistreAntecedent instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdSinistreAntecedent()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaSinistreAntecedent merge(TaSinistreAntecedent detachedInstance) {
		logger.debug("merging TaSinistreAntecedent instance");
		try {
			TaSinistreAntecedent result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaSinistreAntecedent findById(int id) {
		logger.debug("getting TaSinistreAntecedent instance with id: " + id);
		try {
			TaSinistreAntecedent instance = entityManager.find(TaSinistreAntecedent.class, id);
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
	public List<TaSinistreAntecedent> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaSinistreAntecedent> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaSinistreAntecedent entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaSinistreAntecedent> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaSinistreAntecedent> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaSinistreAntecedent> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaSinistreAntecedent> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaSinistreAntecedent value) throws Exception {
		BeanValidator<TaSinistreAntecedent> validator = new BeanValidator<TaSinistreAntecedent>(TaSinistreAntecedent.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaSinistreAntecedent value, String propertyName) throws Exception {
		BeanValidator<TaSinistreAntecedent> validator = new BeanValidator<TaSinistreAntecedent>(TaSinistreAntecedent.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaSinistreAntecedent transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaSinistreAntecedent findByCode(String code) {
		logger.debug("getting TaSinistreAntecedent instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaSinistreAntecedent f where f.identifiant='"+code+"'");
				TaSinistreAntecedent instance = (TaSinistreAntecedent)query.getSingleResult();
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

