package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaAdresseDAO;
import fr.ylyade.courtage.model.TaAdresse;


public class TaAdresseDAO implements ITaAdresseDAO {

	private static final Logger logger = Logger.getLogger(TaAdresseDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaAdresse p";
	
	public TaAdresseDAO(){
//		this(null);
	}

//	public TaTaAdresseDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaAdresse.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaAdresse());
//	}


	public void persist(TaAdresse transientInstance) {
		logger.debug("persisting TaAdresse instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaAdresse persistentInstance) {
		logger.debug("removing TaAdresse instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdAdresse()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaAdresse merge(TaAdresse detachedInstance) {
		logger.debug("merging TaAdresse instance");
		try {
			TaAdresse result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaAdresse findById(int id) {
		logger.debug("getting TaAdresse instance with id: " + id);
		try {
			TaAdresse instance = entityManager.find(TaAdresse.class, id);
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
	public List<TaAdresse> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaAdresse> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaAdresse entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaAdresse> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaAdresse> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaAdresse> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaAdresse> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaAdresse value) throws Exception {
		BeanValidator<TaAdresse> validator = new BeanValidator<TaAdresse>(TaAdresse.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaAdresse value, String propertyName) throws Exception {
		BeanValidator<TaAdresse> validator = new BeanValidator<TaAdresse>(TaAdresse.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaAdresse transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaAdresse findByCode(String code) {
		logger.debug("getting TaAdresse instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaAdresse f where f.codeAdresse='"+code+"'");
				TaAdresse instance = (TaAdresse)query.getSingleResult();
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

