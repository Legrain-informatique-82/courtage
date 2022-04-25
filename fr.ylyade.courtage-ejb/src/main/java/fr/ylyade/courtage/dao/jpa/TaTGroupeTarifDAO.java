package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTGroupeTarifDAO;
import fr.ylyade.courtage.model.TaTGroupeTarif;


public class TaTGroupeTarifDAO implements ITaTGroupeTarifDAO {

	private static final Logger logger = Logger.getLogger(TaTGroupeTarifDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTGroupeTarif p";
	
	public TaTGroupeTarifDAO(){
//		this(null);
	}

//	public TaTaTGroupeTarifDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTGroupeTarif.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTGroupeTarif());
//	}


	public void persist(TaTGroupeTarif transientInstance) {
		logger.debug("persisting TaTGroupeTarif instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTGroupeTarif persistentInstance) {
		logger.debug("removing TaTGroupeTarif instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTGroupeTarif()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTGroupeTarif merge(TaTGroupeTarif detachedInstance) {
		logger.debug("merging TaTGroupeTarif instance");
		try {
			TaTGroupeTarif result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTGroupeTarif findById(int id) {
		logger.debug("getting TaTGroupeTarif instance with id: " + id);
		try {
			TaTGroupeTarif instance = entityManager.find(TaTGroupeTarif.class, id);
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
	public List<TaTGroupeTarif> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTGroupeTarif> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTGroupeTarif entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTGroupeTarif> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTGroupeTarif> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTGroupeTarif> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTGroupeTarif> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTGroupeTarif value) throws Exception {
		BeanValidator<TaTGroupeTarif> validator = new BeanValidator<TaTGroupeTarif>(TaTGroupeTarif.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTGroupeTarif value, String propertyName) throws Exception {
		BeanValidator<TaTGroupeTarif> validator = new BeanValidator<TaTGroupeTarif>(TaTGroupeTarif.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTGroupeTarif transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTGroupeTarif findByCode(String code) {
		logger.debug("getting TaTGroupeTarif instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTGroupeTarif f where f.codeGroupe='"+code+"'");
				TaTGroupeTarif instance = (TaTGroupeTarif)query.getSingleResult();
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

