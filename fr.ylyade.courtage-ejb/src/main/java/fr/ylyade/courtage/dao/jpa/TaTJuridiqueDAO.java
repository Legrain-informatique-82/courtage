package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTJuridiqueDAO;
import fr.ylyade.courtage.model.TaTJuridique;


public class TaTJuridiqueDAO implements ITaTJuridiqueDAO {

	private static final Logger logger = Logger.getLogger(TaTJuridiqueDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTJuridique p";
	
	public TaTJuridiqueDAO(){
//		this(null);
	}

//	public TaTaTJuridiqueDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTJuridique.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTJuridique());
//	}


	public void persist(TaTJuridique transientInstance) {
		logger.debug("persisting TaTJuridique instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTJuridique persistentInstance) {
		logger.debug("removing TaTJuridique instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTJuridique()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTJuridique merge(TaTJuridique detachedInstance) {
		logger.debug("merging TaTJuridique instance");
		try {
			TaTJuridique result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTJuridique findById(int id) {
		logger.debug("getting TaTJuridique instance with id: " + id);
		try {
			TaTJuridique instance = entityManager.find(TaTJuridique.class, id);
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
	public List<TaTJuridique> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTJuridique> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTJuridique entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTJuridique> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTJuridique> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTJuridique> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTJuridique> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTJuridique value) throws Exception {
		BeanValidator<TaTJuridique> validator = new BeanValidator<TaTJuridique>(TaTJuridique.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTJuridique value, String propertyName) throws Exception {
		BeanValidator<TaTJuridique> validator = new BeanValidator<TaTJuridique>(TaTJuridique.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTJuridique transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTJuridique findByCode(String code) {
		logger.debug("getting TaTJuridique instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTJuridique f where f.codeTJuridique='"+code+"'");
				TaTJuridique instance = (TaTJuridique)query.getSingleResult();
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

